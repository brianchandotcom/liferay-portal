/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.verify;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.ResourceLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.verify.model.VerifiableResourcedModel;
import com.liferay.portal.util.PortalInstances;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author     Raymond Augé
 * @author     James Lefeu
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 */
@Deprecated
public class VerifyResourcePermissions extends VerifyProcess {

	public void verify(VerifiableResourcedModel... verifiableResourcedModels)
		throws Exception {

		long[] companyIds = PortalInstances.getCompanyIdsBySQL();

		for (long companyId : companyIds) {
			Role role = RoleLocalServiceUtil.getRole(
				companyId, RoleConstants.OWNER);

			List<VerifyResourcedModelCallable> verifyResourcedModelCallables =
				new ArrayList<>(verifiableResourcedModels.length);

			for (VerifiableResourcedModel verifiableResourcedModel :
					verifiableResourcedModels) {

				VerifyResourcedModelCallable verifyResourcedModelCallable =
					new VerifyResourcedModelCallable(
						role, verifiableResourcedModel);

				verifyResourcedModelCallables.add(verifyResourcedModelCallable);
			}

			doVerify(verifyResourcedModelCallables);
		}
	}

	@Override
	protected void doVerify() throws Exception {
		Map<String, VerifiableResourcedModel> verifiableResourcedModelsMap =
			PortalBeanLocatorUtil.locate(VerifiableResourcedModel.class);

		Collection<VerifiableResourcedModel> verifiableResourcedModels =
			verifiableResourcedModelsMap.values();

		verify(
			verifiableResourcedModels.toArray(new VerifiableResourcedModel[0]));
	}

	private String _getVerifyResourcedModelSQL(
		boolean count, VerifiableResourcedModel verifiableResourcedModel,
		Role role) {

		StringBundler sb = new StringBundler(28);

		sb.append("select ");

		if (count) {
			sb.append("count(*)");
		}
		else {
			sb.append(verifiableResourcedModel.getTableName());
			sb.append(".");
			sb.append(verifiableResourcedModel.getPrimaryKeyColumnName());
			sb.append(", ");
			sb.append(verifiableResourcedModel.getTableName());
			sb.append(".");
			sb.append(verifiableResourcedModel.getUserIdColumnName());
		}

		sb.append(" from ");
		sb.append(verifiableResourcedModel.getTableName());
		sb.append(" left join ResourcePermission on (ResourcePermission.");
		sb.append("companyId = ");
		sb.append(role.getCompanyId());
		sb.append(" and ResourcePermission.name = '");
		sb.append(verifiableResourcedModel.getModelName());
		sb.append("' and ResourcePermission.scope = ");
		sb.append(ResourceConstants.SCOPE_INDIVIDUAL);
		sb.append(" and ResourcePermission.primKeyId = ");
		sb.append(verifiableResourcedModel.getTableName());
		sb.append(".");
		sb.append(verifiableResourcedModel.getPrimaryKeyColumnName());
		sb.append(" and ResourcePermission.roleId = ");
		sb.append(role.getRoleId());
		sb.append(") where ");
		sb.append(verifiableResourcedModel.getTableName());
		sb.append(".companyId = ");
		sb.append(role.getCompanyId());
		sb.append(" and ResourcePermission.primKeyId is NULL");

		return SQLTransformer.transform(sb.toString());
	}

	private void _verifyResourcedModel(
			Role role, VerifiableResourcedModel verifiableResourcedModel)
		throws Exception {

		int total = 0;

		try (LoggingTimer loggingTimer = new LoggingTimer(
				verifiableResourcedModel.getTableName());
			Connection con = DataAccess.getConnection();
			PreparedStatement ps = con.prepareStatement(
				_getVerifyResourcedModelSQL(
					true, verifiableResourcedModel, role));
			ResultSet rs = ps.executeQuery()) {

			if (rs.next()) {
				total = rs.getInt(1);
			}
		}

		if (total == 0) {
			return;
		}

		try (LoggingTimer loggingTimer = new LoggingTimer(
				verifiableResourcedModel.getTableName());
			Connection con = DataAccess.getConnection();
			PreparedStatement ps = con.prepareStatement(
				_getVerifyResourcedModelSQL(
					false, verifiableResourcedModel, role));
			ResultSet rs = ps.executeQuery()) {

			List<Future<Void>> futures = new ArrayList<>(total);

			ExecutorService executorService = Executors.newWorkStealingPool();

			try {
				for (int i = 1; rs.next(); i++) {
					long primKey = rs.getLong(
						verifiableResourcedModel.getPrimaryKeyColumnName());
					long userId = rs.getLong(
						verifiableResourcedModel.getUserIdColumnName());

					futures.add(
						executorService.submit(
							new AddResourcesCallable(
								role.getCompanyId(),
								verifiableResourcedModel.getModelName(),
								primKey, role.getRoleId(), userId, i, total)));
				}

				for (Future<Void> future : futures) {
					future.get();
				}
			}
			finally {
				executorService.shutdown();
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		VerifyResourcePermissions.class);

	private class AddResourcesCallable implements Callable<Void> {

		@Override
		public Void call() throws Exception {
			if (_log.isInfoEnabled() && ((_cur % 100) == 0)) {
				_log.info(
					StringBundler.concat(
						"Processed ", _cur, " of ", _total,
						" resource permissions for company = ", _companyId,
						" and model ", _modelName));
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"No resource found for {", _companyId, ", ", _modelName,
						", ", ResourceConstants.SCOPE_INDIVIDUAL, ", ",
						_primKey, ", ", _roleId, "}"));
			}

			try {
				ResourceLocalServiceUtil.addResources(
					_companyId, 0, _ownerId, _modelName,
					String.valueOf(_primKey), false, false, false);
			}
			catch (Exception exception) {
				_log.error(
					StringBundler.concat(
						"Unable to add resource for {", _companyId, ", ",
						_modelName, ", ", ResourceConstants.SCOPE_INDIVIDUAL,
						", ", _primKey, ", ", _roleId, "}"));
			}

			return null;
		}

		private AddResourcesCallable(
			long companyId, String modelName, long primKey, long roleId,
			long ownerId, int cur, int total) {

			_companyId = companyId;
			_modelName = modelName;
			_primKey = primKey;
			_roleId = roleId;
			_ownerId = ownerId;
			_cur = cur;
			_total = total;
		}

		private final long _companyId;
		private final long _cur;
		private final String _modelName;
		private final long _ownerId;
		private final long _primKey;
		private final long _roleId;
		private final long _total;

	}

	private class VerifyResourcedModelCallable implements Callable<Void> {

		@Override
		public Void call() throws Exception {
			_verifyResourcedModel(_role, _verifiableResourcedModel);

			return null;
		}

		private VerifyResourcedModelCallable(
			Role role, VerifiableResourcedModel verifiableResourcedModel) {

			_role = role;
			_verifiableResourcedModel = verifiableResourcedModel;
		}

		private final Role _role;
		private final VerifiableResourcedModel _verifiableResourcedModel;

	}

}
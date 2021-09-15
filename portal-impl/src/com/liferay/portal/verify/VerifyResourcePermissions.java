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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourceLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.verify.model.VerifiableResourcedModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author     Raymond Augé
 * @author     James Lefeu
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 */
@Deprecated
public class VerifyResourcePermissions extends VerifyProcess {

	public void verify(VerifiableResourcedModel... verifiableResourcedModels)
		throws Exception {

		CompanyLocalServiceUtil.forEachCompanyId(
			companyId -> {
				Role role = RoleLocalServiceUtil.getRole(
					companyId, RoleConstants.OWNER);

				processConcurrently(
					verifiableResourcedModels,
					verifiableResourcedModel -> _verifyResourcedModel(
						role, verifiableResourcedModel),
					null);
			});
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

		int total;

		try (LoggingTimer loggingTimer = new LoggingTimer(
				verifiableResourcedModel.getTableName());
			PreparedStatement preparedStatement = connection.prepareStatement(
				_getVerifyResourcedModelSQL(
					true, verifiableResourcedModel, role));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			if (resultSet.next()) {
				total = resultSet.getInt(1);
			}
			else {
				return;
			}
		}

		try (LoggingTimer loggingTimer = new LoggingTimer(
				verifiableResourcedModel.getTableName())) {

			AtomicInteger atomicInteger = new AtomicInteger();

			processConcurrently(
				_getVerifyResourcedModelSQL(
					false, verifiableResourcedModel, role),
				resultRow -> {
					long primKey = resultRow.get(
						verifiableResourcedModel.getPrimaryKeyColumnName());
					long ownerId = resultRow.get(
						verifiableResourcedModel.getUserIdColumnName());

					long companyId = role.getCompanyId();
					long roleId = role.getRoleId();

					String modelName = verifiableResourcedModel.getModelName();

					int count = atomicInteger.getAndIncrement();

					if (_log.isInfoEnabled() && ((count % 100) == 0)) {
						_log.info(
							StringBundler.concat(
								"Processed ", count, " of ", total,
								" resource permissions for company ", companyId,
								" and model ", modelName));
					}

					if (_log.isDebugEnabled()) {
						_log.debug(
							StringBundler.concat(
								"No resource found for {", companyId, ", ",
								modelName, ", ",
								ResourceConstants.SCOPE_INDIVIDUAL, ", ",
								primKey, ", ", roleId, "}"));
					}

					try {
						ResourceLocalServiceUtil.addResources(
							companyId, 0, ownerId, modelName,
							String.valueOf(primKey), false, false, false);
					}
					catch (Exception exception) {
						_log.error(
							StringBundler.concat(
								"Unable to add resource for {", companyId, ", ",
								modelName, ", ",
								ResourceConstants.SCOPE_INDIVIDUAL, ", ",
								primKey, ", ", roleId, "}"),
							exception);
					}
				},
				null);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		VerifyResourcePermissions.class);

}
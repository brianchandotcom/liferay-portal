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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.concurrent.ThrowableAwareRunnable;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.kernel.verify.model.VerifiableUUIDModel;
import com.liferay.portal.upgrade.AutoBatchPreparedStatementUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class VerifyUUID extends VerifyProcess {

	public static void verify(VerifiableUUIDModel ... verifiableUUIDModels)
		throws Exception {

		VerifyUUID verifyUUID = new VerifyUUID();

		verifyUUID.doVerify(verifiableUUIDModels);
	}

	@Override
	protected void doVerify() throws Exception {
		Map<String, VerifiableUUIDModel> verifiableUUIDModelsMap =
			PortalBeanLocatorUtil.locate(VerifiableUUIDModel.class);

		Collection<VerifiableUUIDModel> verifiableUUIDModels =
			verifiableUUIDModelsMap.values();

		doVerify(
			verifiableUUIDModels.toArray(
				new VerifiableUUIDModel[verifiableUUIDModels.size()]));
	}

	protected void doVerify(VerifiableUUIDModel... verifiableUUIDModels)
		throws Exception {

		List<VerifyUUIDRunnable> verifyUUIDRunnables = new ArrayList<>(
			verifiableUUIDModels.length);

		for (VerifiableUUIDModel verifiableUUIDModel : verifiableUUIDModels) {
			VerifyUUIDRunnable verifyUUIDRunnable = new VerifyUUIDRunnable(
				verifiableUUIDModel);

			verifyUUIDRunnables.add(verifyUUIDRunnable);
		}

		doVerify(verifyUUIDRunnables);
	}

	protected void verifyUUID(VerifiableUUIDModel verifiableUUIDModel)
		throws Exception {

		StringBundler sb = new StringBundler(6);

		sb.append("update ");
		sb.append(verifiableUUIDModel.getTableName());
		sb.append(" set uuid_ = ?");
		sb.append(" where ");
		sb.append(verifiableUUIDModel.getPrimaryKeyColumnName());
		sb.append(" = ?");

		try (LoggingTimer loggingTimer = new LoggingTimer(
				verifiableUUIDModel.getTableName());
			Connection con = DataAccess.getUpgradeOptimizedConnection();
			PreparedStatement ps1 = con.prepareStatement(
				"select " + verifiableUUIDModel.getPrimaryKeyColumnName() +
					" from " + verifiableUUIDModel.getTableName() +
						" where uuid_ is null or uuid_ = ''");
			ResultSet rs = ps1.executeQuery();
			PreparedStatement ps2 = AutoBatchPreparedStatementUtil.autoBatch(
				con.prepareStatement(sb.toString()))) {

			while (rs.next()) {
				long pk = rs.getLong(
					verifiableUUIDModel.getPrimaryKeyColumnName());

				ps2.setString(1, PortalUUIDUtil.generate());
				ps2.setLong(2, pk);

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

	private class VerifyUUIDRunnable extends ThrowableAwareRunnable {

		public VerifyUUIDRunnable(VerifiableUUIDModel verifiableUUIDModel) {
			_verifiableUUIDModel = verifiableUUIDModel;
		}

		@Override
		protected void doRun() throws Exception {
			verifyUUID(_verifiableUUIDModel);
		}

		private final VerifiableUUIDModel _verifiableUUIDModel;

	}

}
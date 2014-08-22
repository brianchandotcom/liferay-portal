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
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.verify.model.uuid.VerifiableUUIDModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Collection;

/**
 * @author Brian Wing Shun Chan
 */
public class VerifyUUID extends VerifyProcess {

	public static void verify(VerifiableUUIDModel ... verifiableUUIDModels)
		throws Exception {

		for (VerifiableUUIDModel verifiableUUIDModel : verifiableUUIDModels) {
			verifyModel(verifiableUUIDModel);
		}
	}

	protected static void updateUUID(
			VerifiableUUIDModel verifiableUUIDModel, long pk)
		throws Exception {

		String uuid = PortalUUIDUtil.generate();

		DB db = DBFactoryUtil.getDB();

		db.runSQL(
			"update " + verifiableUUIDModel.getTableName() + " set uuid_ = '" +
				uuid + "' where " +
				verifiableUUIDModel.getPrimaryKeyColumnName() + " = " + pk);
	}

	protected static void verifyModel(VerifiableUUIDModel verifiableUUIDModel)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			String tableName = verifiableUUIDModel.getTableName();
			String pkColumnName = verifiableUUIDModel.getPrimaryKeyColumnName();

			ps = con.prepareStatement(
				"select " + pkColumnName + " from " + tableName +
					" where uuid_ is null or uuid_ = ''");

			rs = ps.executeQuery();

			while (rs.next()) {
				long pk = rs.getLong(pkColumnName);

				updateUUID(verifiableUUIDModel, pk);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	@Override
	protected void doVerify() throws Exception {
		Collection<VerifiableUUIDModel> verifiableUUIDModels =
			(Collection<VerifiableUUIDModel>)PortalBeanLocatorUtil.locate(
				"verifiable.models.uuid");

		verify(
			verifiableUUIDModels.toArray(
				new VerifiableUUIDModel[verifiableUUIDModels.size()]));
	}

}
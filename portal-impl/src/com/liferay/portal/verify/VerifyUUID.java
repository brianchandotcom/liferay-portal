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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.verify.model.uuid.VerifiableUUIDModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Collection;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class VerifyUUID extends VerifyProcess {

	public static void verify(VerifiableUUIDModel ... verifiableUUIDModels)
		throws Exception {

		for (VerifiableUUIDModel verifiableUUIDModel : verifiableUUIDModels) {
			verifyUUID(verifiableUUIDModel);
		}
	}

	protected static void updateUUID(
			VerifiableUUIDModel verifiableUUIDModel, long primKey)
		throws Exception {

		DB db = DBFactoryUtil.getDB();

		StringBundler sb = new StringBundler(8);

		sb.append("update ");
		sb.append(verifiableUUIDModel.getTableName());
		sb.append(" set uuid_ = '");
		sb.append(PortalUUIDUtil.generate());
		sb.append("' where ");
		sb.append(verifiableUUIDModel.getPrimaryKeyColumnName());
		sb.append(" = ");
		sb.append(primKey);

		db.runSQL(sb.toString());
	}

	protected static void verifyUUID(VerifiableUUIDModel verifiableUUIDModel)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select " + verifiableUUIDModel.getPrimaryKeyColumnName() +
					" from " + verifiableUUIDModel.getTableName() +
						" where uuid_ is null or uuid_ = ''");

			rs = ps.executeQuery();

			while (rs.next()) {
				long pk = rs.getLong(
					verifiableUUIDModel.getPrimaryKeyColumnName());

				updateUUID(verifiableUUIDModel, pk);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	@Override
	protected void doVerify() throws Exception {
		Map<String, VerifiableUUIDModel> verifiableUUIDModelsMap =
			PortalBeanLocatorUtil.locate(VerifiableUUIDModel.class);

		Collection<VerifiableUUIDModel> verifiableUUIDModels =
			verifiableUUIDModelsMap.values();

		verify(
			verifiableUUIDModels.toArray(
				new VerifiableUUIDModel[verifiableUUIDModels.size()]));
	}

}
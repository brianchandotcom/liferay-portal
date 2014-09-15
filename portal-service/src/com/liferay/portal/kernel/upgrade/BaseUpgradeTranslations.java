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

package com.liferay.portal.kernel.upgrade;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.util.TranslationUpdater;
import com.liferay.portal.model.Company;
import com.liferay.portal.util.PortalUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseUpgradeTranslations extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		Iterable<Long[]> companyAndGroupIds = getCompanyAndGroupIds(
			PortalUtil.getClassNameId(Company.class));

		for (Long[] companyAndGroupId : companyAndGroupIds) {
			Long companyId = companyAndGroupId[0];
			Long groupId = companyAndGroupId[1];

			for (TranslationUpdater translationUpdater :
					getTranslationUpdaters()) {

				translationUpdater.update(companyId, groupId);
			}
		}
	}

	protected List<Long[]> getCompanyAndGroupIds(long classNameId)
		throws SQLException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select companyId, groupId from Group_ where classNameId = ?");

			ps.setLong(1, classNameId);

			rs = ps.executeQuery();

			List<Long[]> companyAndGroupIds = new ArrayList<Long[]>();

			while (rs.next()) {
				Long[] companyIdAndGroupId = new Long[2];

				companyIdAndGroupId[0] = rs.getLong(1);
				companyIdAndGroupId[1] = rs.getLong(2);

				companyAndGroupIds.add(companyIdAndGroupId);
			}

			return companyAndGroupIds;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected abstract Collection<TranslationUpdater> getTranslationUpdaters();

}
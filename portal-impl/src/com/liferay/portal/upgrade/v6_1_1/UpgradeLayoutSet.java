/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_1_1;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.image.HookFactory;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.image.Hook;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.impl.ImageImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Locale;

/**
 * @author Jorge Ferrer
 * @author Julio Camarero
 */
public class UpgradeLayoutSet extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(7);

			sb.append("select Group_.groupId, Group_.liveGroupId, ");
			sb.append("LayoutSet.layoutSetId from LayoutSet inner join ");
			sb.append("Group_ ON (LayoutSet.groupId = Group_.groupId and ");
			sb.append("Group_.liveGroupId > 0 and LayoutSet.logo = ?)");

			ps = con.prepareStatement(sb.toString());

			ps.setBoolean(1, true);

			rs = ps.executeQuery();

			while (rs.next()) {
				long groupId = rs.getLong("Group_.groupId");
				long layoutSetId = rs.getLong("LayoutSet.layoutSetId");

				runSQL(
					"update LayoutSet set logoId = 0 where groupId = " +
							groupId + " and layoutSetId = " + layoutSetId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

}
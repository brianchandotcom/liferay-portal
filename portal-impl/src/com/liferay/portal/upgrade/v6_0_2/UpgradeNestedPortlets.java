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

package com.liferay.portal.upgrade.v6_0_2;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Wesley Gong
 * @author Bijan Vakili
 * @author Douglas Wong
 * @author Brian Wing Shun Chan
 */
public class UpgradeNestedPortlets extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(_GET_LAYOUT);

			rs = ps.executeQuery();

			while (rs.next()) {
				long plid = rs.getLong("plid");
				String typeSettings = rs.getString("typeSettings");

				String newTypeSettings = typeSettings;

				Matcher matcher = _pattern.matcher(typeSettings);

				while (matcher.find()) {
					String nestedColumnIds = matcher.group();

					int underlineCount = StringUtil.count(
						nestedColumnIds, StringPool.UNDERLINE);

					if (underlineCount == _UNDERLINE_COUNT) {
						String newNestedColumnIds =
							"_" + matcher.group(1) + "_" + matcher.group(2);

						newTypeSettings = StringUtil.replace(
							newTypeSettings, nestedColumnIds,
							newNestedColumnIds);
					}
				}

				if (!newTypeSettings.equals(typeSettings)) {
					updateLayout(plid, newTypeSettings);
				}
			}
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}
	}

	protected void updateLayout(long plid, String typeSettings)
		throws Exception {

		PreparedStatement ps = null;

		try {
			ps = connection.prepareStatement(
				"update Layout set typeSettings = ? where plid = " + plid);

			ps.setString(1, typeSettings);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(ps);
		}
	}

	private static final String _GET_LAYOUT =
		"select plid, typeSettings from Layout where typeSettings like " +
			"'%nested-column-ids=" + PortletKeys.NESTED_PORTLETS +
				UpgradeNestedPortlets._INSTANCE_SEPARATOR + "%'";

	private static final String _INSTANCE_SEPARATOR = "_INSTANCE_";

	private static final int _UNDERLINE_COUNT = StringUtil.count(
		_INSTANCE_SEPARATOR, StringPool.UNDERLINE) + 1;

	private static final Pattern _pattern = Pattern.compile(
		"(" + PortletKeys.NESTED_PORTLETS +
			_INSTANCE_SEPARATOR + "[^_,\\s=]+_)([^_,\\s=]+)");

}
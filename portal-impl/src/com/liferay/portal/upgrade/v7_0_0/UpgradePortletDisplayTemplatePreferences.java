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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.portletdisplaytemplate.PortletDisplayTemplateManager;
import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.portlet.PortletPreferences;

/**
 * @author Eduardo Garcia
 */
public class UpgradePortletDisplayTemplatePreferences
	extends BaseUpgradePortletPreferences {

	protected String getTemplateKey(
			Connection con, long displayStyleGroupId, String displayStyle)
		throws Exception {

		String uuid = displayStyle.substring(DISPLAY_STYLE_PREFIX_6_2.length());

		String sql =
			"select templateKey from DDMTemplate where groupId = ? and uuid_" +
				" = ?";

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setLong(1, displayStyleGroupId);
			ps.setString(2, uuid);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getString("templateKey");
				}
			}

			return null;
		}
	}

	@Override
	protected String getUpdatePortletPreferencesWhereClause() {
		StringBundler sb = new StringBundler(5);

		sb.append("(preferences like '%");
		sb.append(DISPLAY_STYLE_PREFIX_6_2);
		sb.append("%')");

		return sb.toString();
	}

	protected void upgradeDisplayStyle(
			Connection con, PortletPreferences portletPreferences)
		throws Exception {

		String displayStyle = GetterUtil.getString(
			portletPreferences.getValue("displayStyle", null));

		if (Validator.isNull(displayStyle) ||
			!displayStyle.startsWith(DISPLAY_STYLE_PREFIX_6_2)) {

			return;
		}

		long displayStyleGroupId = GetterUtil.getLong(
			portletPreferences.getValue("displayStyleGroupId", null));

		String templateKey = getTemplateKey(
			con, displayStyleGroupId, displayStyle);

		if (templateKey != null) {
			portletPreferences.setValue(
				"displayStyle",
				PortletDisplayTemplateManager.DISPLAY_STYLE_PREFIX +
					templateKey);
		}
	}

	@Override
	protected String upgradePreferences(
			Connection con, long companyId, long ownerId, int ownerType,
			long plid, String portletId, String xml)
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		upgradeDisplayStyle(con, portletPreferences);

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

	@Override
	protected String upgradePreferences(
		long companyId, long ownerId, int ownerType, long plid,
		String portletId, String xml) {

		return null;
	}

	protected static final String DISPLAY_STYLE_PREFIX_6_2 = "ddmTemplate_";

}
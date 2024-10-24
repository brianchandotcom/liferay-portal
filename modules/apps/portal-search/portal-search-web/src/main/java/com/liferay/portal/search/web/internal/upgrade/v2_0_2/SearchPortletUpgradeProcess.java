/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.upgrade.v2_0_2;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.upgrade.BasePortletIdUpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author Petteri Karttunen
 */
public class SearchPortletUpgradeProcess extends BasePortletIdUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL("delete from Portlet where portletId = '" + _PORTLET_ID + "'");
		runSQL(
			"delete from PortletPreferences where portletId like '" +
				_PORTLET_ID + "%'");
		runSQL("delete from ResourceAction where name = '" + _PORTLET_ID + "'");
		runSQL(
			"delete from ResourcePermission where name = '" + _PORTLET_ID +
				"'");

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select plid, typeSettings from Layout where " +
					getTypeSettingsCriteria(_PORTLET_ID));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				updateLayout(
					resultSet.getLong("plid"),
					_updateTypeSettings(resultSet.getString("typeSettings")));
			}
		}
	}

	private String _updateTypeSettings(String typeSettings) {
		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.create(
				true
			).fastLoad(
				typeSettings
			).build();

		Set<String> keys = typeSettingsUnicodeProperties.keySet();

		Iterator<String> iterator = keys.iterator();

		while (iterator.hasNext()) {
			String key = iterator.next();

			if (!StringUtil.startsWith(
					key, LayoutTypePortletConstants.COLUMN_PREFIX) &&
				!StringUtil.startsWith(
					key, LayoutTypePortletConstants.NESTED_COLUMN_IDS)) {

				continue;
			}

			List<String> portletIds = new ArrayList<>();

			ArrayUtil.isNotEmptyForEach(
				StringUtil.split(
					typeSettingsUnicodeProperties.getProperty(key)),
				portletId -> {
					if (!portletId.startsWith(_PORTLET_ID)) {
						portletIds.add(portletId);
					}
				});

			if (portletIds.isEmpty()) {
				iterator.remove();

				continue;
			}

			typeSettingsUnicodeProperties.setProperty(
				key, String.join(",", portletIds) + StringPool.COMMA);
		}

		return typeSettingsUnicodeProperties.toString();
	}

	private static final String _PORTLET_ID =
		"com_liferay_portal_search_web_date_facet_portlet_DateFacetPortlet";

}
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

package com.liferay.exportimport.web.internal.upgrade.v1_0_1;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Vendel Toreki
 */
public class UpgradeStagingConfigurationClassNames extends UpgradeProcess {

	public UpgradeStagingConfigurationClassNames(
		GroupLocalService groupLocalService) {

		_groupLocalService = groupLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateStagingConfiguration();
	}

	protected void updateStagingConfiguration() throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				SQLTransformer.transform(
					"select groupId, companyId, typeSettings from Group_ " +
						"where liveGroupId = 0 and site = [$TRUE$] and " +
							"typeSettings like '%staged=true%'"))) {

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					long groupId = rs.getLong("groupId");
					long companyId = rs.getLong("companyId");
					String typeSettings = rs.getString("typeSettings");

					_updateStagingConfiguration(
						groupId, companyId, typeSettings);
				}
			}
		}
	}

	private Map<String, String> _createAdminPortletIdsMap(long companyId)
		throws Exception {

		Map<String, String> adminPortletIdsMap = new HashMap<>();

		try (PreparedStatement ps = connection.prepareStatement(
				"select portletId from Portlet where companyId = ? and " +
					"active_ = 1")) {

			ps.setLong(1, companyId);

			Set<String> allPortletIds = new HashSet<>();

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					String portletId = rs.getString("portletId");

					allPortletIds.add(portletId);
				}
			}

			for (String adminPortletId : allPortletIds) {
				if (adminPortletId.endsWith("AdminPortlet")) {
					String portletId = StringUtil.replace(
						adminPortletId, "AdminPortlet", "Portlet");

					if (allPortletIds.contains(portletId) &&
						!adminPortletIdsMap.containsKey(portletId)) {

						adminPortletIdsMap.put(portletId, adminPortletId);
					}
				}
			}
		}

		return adminPortletIdsMap;
	}

	private void _updateStagingConfiguration(
			long groupId, long companyId, String typeSettings)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Upgrading staging configuration portlet names in group " +
					groupId);
		}

		UnicodeProperties typeSettingsUnicodeProperties =
			new UnicodeProperties();

		typeSettingsUnicodeProperties.load(typeSettings);

		boolean staged = GetterUtil.getBoolean(
			typeSettingsUnicodeProperties.getProperty("staged"));

		if (!staged) {
			return;
		}

		Map<String, String> portletNamesMap = _createAdminPortletIdsMap(
			companyId);

		boolean changed = false;

		for (Map.Entry<String, String> entry : portletNamesMap.entrySet()) {
			String stagedKey = "staged-portlet_" + entry.getKey();
			String adminStagedKey = "staged-portlet_" + entry.getValue();

			if (typeSettingsUnicodeProperties.containsKey(stagedKey)) {
				String value = typeSettingsUnicodeProperties.getProperty(
					stagedKey);

				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Renaming key \"", stagedKey, "\" to \"",
							adminStagedKey, "\" with value \"", value, "\""));
				}

				typeSettingsUnicodeProperties.put(adminStagedKey, value);

				typeSettingsUnicodeProperties.remove(stagedKey);

				changed = true;
			}
		}

		if (changed) {
			_groupLocalService.updateGroup(
				groupId, typeSettingsUnicodeProperties.toString());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeStagingConfigurationClassNames.class);

	private final GroupLocalService _groupLocalService;

}
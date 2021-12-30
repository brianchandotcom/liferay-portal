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
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Vendel Toreki
 */
public class StagingConfigurationClassNamesUpgradeProcess
	extends UpgradeProcess {

	public StagingConfigurationClassNamesUpgradeProcess(
		GroupLocalService groupLocalService) {

		_groupLocalService = groupLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_updateStagingConfiguration();
	}

	private Map<String, String> _createAdminPortletIdsMap(long companyId)
		throws Exception {

		Map<String, String> adminPortletIdsMap = new HashMap<>();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				SQLTransformer.transform(
					"select portletId from Portlet where companyId = ? and " +
						"active_ = [$TRUE$]"))) {

			preparedStatement.setLong(1, companyId);

			Set<String> allPortletIds = new HashSet<>();

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					String portletId = resultSet.getString("portletId");

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

	private void _updateStagingConfiguration() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				SQLTransformer.transform(
					"select groupId, companyId, typeSettings from Group_ " +
						"where liveGroupId = 0 and site = [$TRUE$] and " +
							"typeSettings like '%staged=true%'"))) {

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					long groupId = resultSet.getLong("groupId");
					long companyId = resultSet.getLong("companyId");
					String typeSettings = resultSet.getString("typeSettings");

					_updateStagingConfiguration(
						groupId, companyId, typeSettings);
				}
			}
		}
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
			UnicodePropertiesBuilder.load(
				typeSettings
			).build();

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

			if (typeSettingsUnicodeProperties.containsKey(stagedKey)) {
				String adminStagedKey = "staged-portlet_" + entry.getValue();
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
		StagingConfigurationClassNamesUpgradeProcess.class);

	private final GroupLocalService _groupLocalService;

}
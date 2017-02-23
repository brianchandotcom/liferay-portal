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

package com.liferay.layout.admin.web.internal.upgrade.v_1_0_1;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Alec Shay
 */
public class UpgradeLayoutType extends UpgradeProcess {

	protected void addPortletPreference(
		StringBundler portletPreferences, String name, String value) {

		portletPreferences.append("<preference><name>");

		portletPreferences.append(name);

		portletPreferences.append("</name><value>");

		portletPreferences.append(value);

		portletPreferences.append("</value></preference>");
	}

	protected String createPortletPreferences(
			long companyId, long groupId, long plid, String articleId)
		throws Exception {

		String portletId =
			"com_liferay_journal_content_web_portlet_JournalContentPortlet";

		String portletPreferencesId =
			portletId + "_INSTANCE_" + PortletConstants.generateInstanceId();

		Portlet webContentPortlet = PortletLocalServiceUtil.getPortletById(
			portletId);

		String defaultPreferences = getDefaultPortletPreferences(
			groupId, articleId);

		PortletPreferencesLocalServiceUtil.addPortletPreferences(
			companyId, 0, PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid,
			portletPreferencesId, webContentPortlet, defaultPreferences);

		return portletPreferencesId;
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateLayouts();
	}

	protected String getArticleIdFromTypeSettings(String typeSettings)
		throws Exception {

		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		typeSettingsProperties.fastLoad(typeSettings);

		String articleId = typeSettingsProperties.getProperty("article-id");

		if (articleId == null) {
			return StringPool.BLANK;
		}

		return articleId;
	}

	protected long getAssetEntryIdForResource(long resourcePrimKey)
		throws Exception {

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			"com.liferay.portlet.journal.model.JournalArticle",
			resourcePrimKey);

		if (assetEntry == null) {
			assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
				"com.liferay.journal.model.JournalArticle", resourcePrimKey);
		}

		if (assetEntry == null) {
			throw new UpgradeException(
				"No asset entry for a journal article with classPK: " +
					resourcePrimKey);
		}

		return assetEntry.getEntryId();
	}

	protected String getDefaultPortletPreferences(
			long groupId, String articleId)
		throws Exception {

		if (Validator.isNull(articleId)) {
			return null;
		}

		long resourcePrimKey = 0;

		PreparedStatement ps = connection.prepareStatement(
			"select resourcePrimKey from JournalArticle where articleId = ?");

		ps.setString(1, articleId);

		try (ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				resourcePrimKey = rs.getLong("resourcePrimKey");
			}
		}

		if (resourcePrimKey == 0) {
			throw new UpgradeException("Missing article with ID: " + articleId);
		}

		long assetEntryId = getAssetEntryIdForResource(resourcePrimKey);
		StringBundler portletPreferences = new StringBundler(17);

		portletPreferences.append("<portlet-preferences>");

		addPortletPreference(portletPreferences, "articleId", articleId);
		addPortletPreference(
			portletPreferences, "assetEntryId", Long.toString(assetEntryId));
		addPortletPreference(
			portletPreferences, "groupId", Long.toString(groupId));

		portletPreferences.append("</portlet-preferences>");

		return portletPreferences.toString();
	}

	protected void updateLayouts() throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select companyId, groupId, plid, typeSettings from Layout " +
					"where type_ = ?")) {

			ps.setString(1, "article");

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					long companyId = rs.getLong("companyId");
					long groupId = rs.getLong("groupId");
					long plid = rs.getLong("plid");
					String typeSettings = rs.getString("typeSettings");

					String articleId = getArticleIdFromTypeSettings(
						typeSettings);

					String portletId = createPortletPreferences(
						companyId, groupId, plid, articleId);

					String updatedTypeSettings = updateTypeSettings(portletId);

					updateTypeForLayout(plid, updatedTypeSettings);
				}
			}
		}
	}

	protected void updateTypeForLayout(long plid, String typeSettings)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"update Layout set typeSettings = ?, type_ = ? where plid = ?"))
		{

			ps.setString(1, typeSettings);
			ps.setString(2, "portlet");
			ps.setLong(3, plid);

			ps.executeUpdate();
		}
	}

	protected String updateTypeSettings(String portletId) {
		UnicodeProperties newTypeSettings = new UnicodeProperties();

		newTypeSettings.put("column-1", portletId);

		newTypeSettings.put(
			LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID, "1_column");

		return newTypeSettings.toString();
	}

}
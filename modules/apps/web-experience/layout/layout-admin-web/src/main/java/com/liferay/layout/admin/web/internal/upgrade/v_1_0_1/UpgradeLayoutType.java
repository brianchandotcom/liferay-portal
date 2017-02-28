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
import com.liferay.portal.kernel.model.LayoutTemplateConstants;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.PortletPreferencesImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.portlet.PortletPreferences;

/**
 * @author Alec Shay
 */
public class UpgradeLayoutType extends UpgradeProcess {

	protected String addPortletPreferences(
			long companyId, long groupId, long plid, String articleId)
		throws Exception {

		String portletId =
			"com_liferay_journal_content_web_portlet_JournalContentPortlet";

		Portlet webContentPortlet = PortletLocalServiceUtil.getPortletById(
			portletId);

		String portletPreferences = getPortletPreferences(groupId, articleId);

		String portletPreferencesId =
			portletId + LayoutTemplateConstants.INSTANCE_SEPARATOR +
				PortletConstants.generateInstanceId();

		PortletPreferencesLocalServiceUtil.addPortletPreferences(
			companyId, 0, PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid,
			portletPreferencesId, webContentPortlet, portletPreferences);

		return portletPreferencesId;
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateLayouts();
	}

	protected String getArticleId(String typeSettings) throws Exception {
		UnicodeProperties typeSettingsProperties = new UnicodeProperties(true);

		typeSettingsProperties.fastLoad(typeSettings);

		return typeSettingsProperties.getProperty("article-id");
	}

	protected long getAssetEntryId(long resourcePrimKey) throws Exception {
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

	protected String getPortletPreferences(long groupId, String articleId)
		throws Exception {

		if (Validator.isNull(articleId)) {
			return null;
		}

		long resourcePrimKey = 0;

		try (PreparedStatement ps = connection.prepareStatement(
				"select resourcePrimKey from JournalArticle where articleId " +
					"= ?")) {

			ps.setString(1, articleId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					resourcePrimKey = rs.getLong("resourcePrimKey");
				}
			}
		}

		if (resourcePrimKey == 0) {
			throw new UpgradeException("Missing article with ID: " + articleId);
		}

		long assetEntryId = getAssetEntryId(resourcePrimKey);

		PortletPreferences portletPreferences = new PortletPreferencesImpl();

		portletPreferences.setValue("groupId", String.valueOf(groupId));
		portletPreferences.setValue("articleId", articleId);
		portletPreferences.setValue(
			"assetEntryId", String.valueOf(assetEntryId));

		return portletPreferences.toString();
	}

	protected String getTypeSettings(String portletId) {
		UnicodeProperties newTypeSettings = new UnicodeProperties(true);

		newTypeSettings.put("column-1", portletId);

		newTypeSettings.put(
			LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID, "1_column");

		return newTypeSettings.toString();
	}

	protected void updateLayout(long plid, String typeSettings)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"update Layout set typeSettings = ?, type_ = ? where plid = " +
					"?")) {

			ps.setString(1, typeSettings);
			ps.setString(2, "portlet");
			ps.setLong(3, plid);

			ps.executeUpdate();
		}
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

					String articleId = getArticleId(typeSettings);

					String portletId = addPortletPreferences(
						companyId, groupId, plid, articleId);

					String curTypeSettings = getTypeSettings(portletId);

					updateLayout(plid, curTypeSettings);
				}
			}
		}
	}

}
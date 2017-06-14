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
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.service.JournalArticleResourceLocalService;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
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

	public UpgradeLayoutType(
		JournalArticleResourceLocalService journalArticleResourceLocalService) {

		_journalArticleResourceLocalService =
			journalArticleResourceLocalService;
	}

	protected void addPortletPreferences(
			long companyId, long groupId, long plid, String portletId,
			String articleId)
		throws Exception {

		String portletPreferences = getPortletPreferences(groupId, articleId);

		PortletPreferencesLocalServiceUtil.addPortletPreferences(
			companyId, 0, PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid, portletId,
			null, portletPreferences);
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
			_CLASS_NAME, resourcePrimKey);

		if (assetEntry == null) {
			throw new UpgradeException(
				"Unable to find asset entry for a journal article with " +
					"classPK " + resourcePrimKey);
		}

		return assetEntry.getEntryId();
	}

	protected String getPortletId() {
		return PortletIdCodec.encode(_PORTLET_ID_JOURNAL_CONTENT);
	}

	protected String getPortletPreferences(long groupId, String articleId)
		throws Exception {

		if (Validator.isNull(articleId)) {
			return null;
		}

		long resourcePrimKey = getResourcePrimKey(groupId, articleId);

		PortletPreferences portletPreferences = new PortletPreferencesImpl();

		portletPreferences.setValue("articleId", articleId);

		long assetEntryId = getAssetEntryId(resourcePrimKey);

		portletPreferences.setValue(
			"assetEntryId", String.valueOf(assetEntryId));

		portletPreferences.setValue("groupId", String.valueOf(groupId));

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

	protected long getResourcePrimKey(long groupId, String articleId)
		throws Exception {

		JournalArticleResource journalArticleResource =
			_journalArticleResourceLocalService.fetchArticleResource(
				groupId, articleId);

		if (journalArticleResource == null) {
			throw new UpgradeException("Unable to get article " + articleId);
		}

		return journalArticleResource.getResourcePrimKey();
	}

	protected String getTypeSettings(String portletId) {
		UnicodeProperties newTypeSettings = new UnicodeProperties(true);

		newTypeSettings.put("column-1", portletId);
		newTypeSettings.put(
			LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID, "1_column");

		return newTypeSettings.toString();
	}

	protected void updateLayout(long plid, String portletId) throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"update Layout set typeSettings = ?, type_ = ? where plid = " +
					"?")) {

			ps.setString(1, getTypeSettings(portletId));
			ps.setString(2, "portlet");
			ps.setLong(3, plid);

			ps.executeUpdate();
		}
	}

	protected void updateLayouts() throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select plid, groupId, companyId, typeSettings from Layout " +
					"where type_ = ?")) {

			ps.setString(1, "article");

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					long plid = rs.getLong("plid");
					long groupId = rs.getLong("groupId");
					long companyId = rs.getLong("companyId");
					String typeSettings = rs.getString("typeSettings");

					String portletId = getPortletId();
					String articleId = getArticleId(typeSettings);

					addPortletPreferences(
						companyId, groupId, plid, portletId, articleId);

					updateLayout(plid, portletId);
				}
			}
		}
	}

	private static final String _CLASS_NAME =
		"com.liferay.journal.model.JournalArticle";

	private static final String _PORTLET_ID_JOURNAL_CONTENT =
		"com_liferay_journal_content_web_portlet_JournalContentPortlet";

	private final JournalArticleResourceLocalService
		_journalArticleResourceLocalService;

}
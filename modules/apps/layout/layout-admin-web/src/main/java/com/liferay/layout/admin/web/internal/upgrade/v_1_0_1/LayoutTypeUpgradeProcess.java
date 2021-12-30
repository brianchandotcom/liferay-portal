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
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.PortletPreferencesImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.portlet.PortletPreferences;

/**
 * @author Alec Shay
 */
public class LayoutTypeUpgradeProcess extends UpgradeProcess {

	public LayoutTypeUpgradeProcess(
		JournalArticleResourceLocalService journalArticleResourceLocalService) {

		_journalArticleResourceLocalService =
			journalArticleResourceLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_updateLayouts();
	}

	private void _addPortletPreferences(
			long companyId, long groupId, long plid, String portletId,
			String journalArticleId)
		throws Exception {

		String portletPreferences = _getPortletPreferences(
			groupId, journalArticleId);

		PortletPreferencesLocalServiceUtil.addPortletPreferences(
			companyId, 0, PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid, portletId,
			null, portletPreferences);
	}

	private long _getAssetEntryId(long resourcePrimKey) throws Exception {
		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			_CLASS_NAME, resourcePrimKey);

		if (assetEntry == null) {
			throw new UpgradeException(
				"Unable to find asset entry for a journal article with " +
					"classPK " + resourcePrimKey);
		}

		return assetEntry.getEntryId();
	}

	private String _getJournalArticleId(String typeSettings) throws Exception {
		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.create(
				true
			).fastLoad(
				typeSettings
			).build();

		return typeSettingsUnicodeProperties.getProperty("article-id");
	}

	private String _getPortletId() {
		return PortletIdCodec.encode(_PORTLET_ID_JOURNAL_CONTENT);
	}

	private String _getPortletPreferences(long groupId, String journalArticleId)
		throws Exception {

		if (Validator.isNull(journalArticleId)) {
			return null;
		}

		PortletPreferences portletPreferences = new PortletPreferencesImpl();

		portletPreferences.setValue("articleId", journalArticleId);
		portletPreferences.setValue("groupId", String.valueOf(groupId));

		JournalArticleResource journalArticleResource =
			_journalArticleResourceLocalService.fetchArticleResource(
				groupId, journalArticleId);

		if (journalArticleResource == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to locate journal article ", journalArticleId,
						" in group ", groupId));
			}
		}
		else {
			long assetEntryId = _getAssetEntryId(
				journalArticleResource.getResourcePrimKey());

			portletPreferences.setValue(
				"assetEntryId", String.valueOf(assetEntryId));
		}

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

	private String _getTypeSettings(String portletId) {
		return UnicodePropertiesBuilder.create(
			true
		).put(
			LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID, "1_column"
		).put(
			"column-1", portletId
		).buildString();
	}

	private void _updateLayout(long plid, String portletId) throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update Layout set typeSettings = ?, type_ = ? where plid = " +
					"?")) {

			preparedStatement.setString(1, _getTypeSettings(portletId));
			preparedStatement.setString(2, "portlet");
			preparedStatement.setLong(3, plid);

			preparedStatement.executeUpdate();
		}
	}

	private void _updateLayouts() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select plid, groupId, companyId, typeSettings from Layout " +
					"where type_ = ?")) {

			preparedStatement.setString(1, "article");

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					long plid = resultSet.getLong("plid");
					long groupId = resultSet.getLong("groupId");
					long companyId = resultSet.getLong("companyId");

					String typeSettings = resultSet.getString("typeSettings");

					String portletId = _getPortletId();

					_addPortletPreferences(
						companyId, groupId, plid, portletId,
						_getJournalArticleId(typeSettings));

					_updateLayout(plid, portletId);
				}
			}
		}
	}

	private static final String _CLASS_NAME =
		"com.liferay.journal.model.JournalArticle";

	private static final String _PORTLET_ID_JOURNAL_CONTENT =
		"com_liferay_journal_content_web_portlet_JournalContentPortlet";

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutTypeUpgradeProcess.class);

	private final JournalArticleResourceLocalService
		_journalArticleResourceLocalService;

}
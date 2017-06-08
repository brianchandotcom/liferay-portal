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

package com.liferay.journal.wysiwyg.upgrade;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.dynamic.data.mapping.util.DefaultDDMStructureHelper;
import com.liferay.journal.content.web.constants.JournalContentPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.journal.wysiwyg.upgrade.constants.JournalWYSIWYGConstants;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

/**
 * @author Peter Fellwock
 */
public class JournalWYSIWYGUpgradeHelper {

	public JournalWYSIWYGUpgradeHelper(
		AssetEntryLocalService assetEntryLocalService,
		DefaultDDMStructureHelper defaultDDMStructureHelper,
		GroupLocalService groupLocalService,
		JournalArticleLocalService journalArticleLocalService,
		JournalFolderLocalService journalFolderLocalService,
		LayoutLocalService layoutLocalService,
		PortletPreferencesLocalService portletPreferencesLocalService,
		UserLocalService userLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
		_defaultDDMStructureHelper = defaultDDMStructureHelper;
		_groupLocalService = groupLocalService;
		_journalArticleLocalService = journalArticleLocalService;
		_journalFolderLocalService = journalFolderLocalService;
		_layoutLocalService = layoutLocalService;
		_portletPreferencesLocalService = portletPreferencesLocalService;
		_userLocalService = userLocalService;
	}

	public void upgrade() {
		StringBundler sb = new StringBundler(9);

		sb.append("select PortletPreferences.portletPreferencesId, ");
		sb.append("PortletPreferences.companyId, PortletPreferences.plid, ");
		sb.append("Layout.groupId, PortletPreferences.portletId, ");
		sb.append("PortletPreferences.preferences, Layout.userId from ");
		sb.append("PortletPreferences inner join Layout on Layout.plid = ");
		sb.append("PortletPreferences.plid where ");
		sb.append("PortletPreferences.portletId like '");
		sb.append(JournalWYSIWYGConstants.PORTLET_ID_WYSIWYG);
		sb.append("%'");

		try (Connection con = DataAccess.getUpgradeOptimizedConnection();
			PreparedStatement ps = con.prepareStatement(sb.toString());
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long portletPreferencesId = rs.getLong("portletPreferencesId");
				long companyId = rs.getLong("companyId");
				long groupId = rs.getLong("groupId");
				long plid = rs.getLong("plid");
				long userId = rs.getLong("userId");

				if (userId == 0) {
					userId = _userLocalService.getDefaultUserId(companyId);
				}

				String portletId = rs.getString("portletId");
				String preferences = rs.getString("preferences");

				javax.portlet.PortletPreferences preferenceMap =
					PortletPreferencesFactoryUtil.fromXML(
						companyId, 0, 0, plid, portletId, preferences);

				String content = preferenceMap.getValue(
					"message", StringPool.BLANK);

				if (Validator.isNotNull(content)) {
					_migrateDataAndPortlet(
						portletPreferencesId, companyId, groupId, userId, plid,
						portletId, content);
				}
			}
		}
		catch (Exception e) {
			_log.error("Unable to process conversion for WYSIWYG Portlet ", e);
		}
	}

	private JournalArticle _convertWYSIWYGContent(
			long userId, long companyId, long groupId, String content,
			ServiceContext serviceContext)
		throws Exception {

		long journalFolderId = _getFolderId(userId, groupId, serviceContext);

		String title = _getTitleFromContent(content, 3);

		String defaultLocaleId = UpgradeProcessUtil.getDefaultLanguageId(
			companyId);

		Locale defaultLocale = LocaleUtil.fromLanguageId(defaultLocaleId);

		Map<Locale, String> titleMap = Collections.singletonMap(
			defaultLocale, title);

		String xmlContent = _getContentAsXml(content, companyId);

		JournalArticle journalArticle = _journalArticleLocalService.addArticle(
			userId, groupId, journalFolderId, titleMap, titleMap, xmlContent,
			JournalWYSIWYGConstants.DDM_STRUCTURE_KEY_WYSIWYG,
			JournalWYSIWYGConstants.DDM_TEMPLATE_KEY_WYSWIWYG, serviceContext);

		return journalArticle;
	}

	private void _createDDMStructure(long companyId, long userId)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(true);

		Group group = _groupLocalService.getCompanyGroup(companyId);

		serviceContext.setScopeGroupId(group.getGroupId());

		long defaultUserId = _userLocalService.getDefaultUserId(companyId);

		serviceContext.setUserId(defaultUserId);

		Class<?> clazz = getClass();

		_defaultDDMStructureHelper.addDDMStructures(
			userId, group.getGroupId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			clazz.getClassLoader(),
			"com/liferay/journal/wysiwyg/upgrade/internal/dependencies" +
				"/wysiwyg-web-content-structure.xml",
			serviceContext);
	}

	private String _getContentAsXml(String content, long companyId)
		throws Exception {

		String defaultLocale = UpgradeProcessUtil.getDefaultLanguageId(
			companyId);

		Document newDocument = SAXReaderUtil.createDocument();

		Element newRootElement = SAXReaderUtil.createElement("root");

		newRootElement.addAttribute("available-locales", defaultLocale);
		newRootElement.addAttribute("default-locale", defaultLocale);

		newDocument.add(newRootElement);

		Element dynamicElementElement = SAXReaderUtil.createElement(
			"dynamic-element");

		dynamicElementElement.addAttribute("name", "content");
		dynamicElementElement.addAttribute("type", "text_area");
		dynamicElementElement.addAttribute("index-type", "keyword");
		dynamicElementElement.addAttribute("instance-id", "rnev");

		newRootElement.add(dynamicElementElement);

		Element dynamicContentElement = SAXReaderUtil.createElement(
			"dynamic-content");

		dynamicContentElement.addAttribute("language-id", defaultLocale);
		dynamicContentElement.addCDATA(content);

		dynamicElementElement.add(dynamicContentElement);

		return newDocument.asXML();
	}

	private long _getFolderId(
			long userId, long groupId, ServiceContext serviceContext)
		throws PortalException {

		JournalFolder journalFolder = _journalFolderLocalService.fetchFolder(
			groupId, 0, JournalWYSIWYGConstants.FOLDER_NAME);

		if (journalFolder == null) {
			journalFolder = _journalFolderLocalService.addFolder(
				userId, groupId, 0, JournalWYSIWYGConstants.FOLDER_NAME,
				JournalWYSIWYGConstants.FOLDER_DESCRIPTION, serviceContext);
		}

		return journalFolder.getFolderId();
	}

	private String _getJournalPortletPreferences(JournalArticle article) {
		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(), article.getResourcePrimKey());

		StringBundler sb = new StringBundler(23);

		sb.append("<portlet-preferences><preference>");
		sb.append("<name>ddmTemplateKey</name>");
		sb.append("<value>BASIC-WEB-CONTENT</value>");
		sb.append("</preference><preference>");
		sb.append("<name>assetEntryId</name><value>");
		sb.append(assetEntry.getEntryId());
		sb.append("</value>");
		sb.append("</preference><preference>");
		sb.append("<name>userToolAssetAddonEntryKeys</name><value></value>");
		sb.append("</preference><preference>");
		sb.append("<name>enableViewCountIncrement</name><value>true</value>");
		sb.append("</preference><preference>");
		sb.append("<name>groupId</name><value>");
		sb.append(article.getGroupId());
		sb.append("</value>");
		sb.append("</preference><preference>");
		sb.append("<name>articleId</name><value>");
		sb.append(article.getArticleId());
		sb.append("</value>");
		sb.append("</preference><preference>");
		sb.append("<name>contentMetadataAssetAddonEntryKeys</name>");
		sb.append("<value></value>");
		sb.append("</preference></portlet-preferences>");

		return sb.toString();
	}

	private String _getTitleFromContent(String htmlText, int wordCount) {
		String text = HtmlUtil.extractText(htmlText);

		StringBuilder sb = new StringBuilder();

		int counter = 1;
		int spaceIndex = 0;

		while (counter <= wordCount) {
			if (text.indexOf(' ') > -1) {
				spaceIndex = text.indexOf(' ');

				sb.append(text.substring(0, spaceIndex + 1));

				text = text.substring(spaceIndex + 1, text.length());
			}
			else {
				sb.append(text.substring(0, text.length()));

				counter = wordCount + 1;
			}

			counter++;
		}

		return sb.toString();
	}

	private void _migrateDataAndPortlet(
		long portletPreferencesId, long companyId, long groupId, long userId,
		long plid, String portletId, String content) {

		try {
			_createDDMStructure(companyId, userId);

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);

			serviceContext.setScopeGroupId(groupId);

			JournalArticle journalArticle = _convertWYSIWYGContent(
				userId, companyId, groupId, content, serviceContext);

			if (journalArticle != null) {
				_updatePortletPreferences(
					portletPreferencesId, journalArticle, portletId);
				_updatePortletReferenceInLayout(plid);
			}
		}
		catch (Exception e) {
			_log.error("Unable to migrate WYSIWYG Data and Portlet", e);
		}
	}

	private void _updatePortletPreferences(
			long portletPreferencesId, JournalArticle journalArticle,
			String portletId)
		throws PortalException {

		String journalPortletId = StringUtil.replace(
			portletId, JournalWYSIWYGConstants.PORTLET_ID_WYSIWYG,
			JournalContentPortletKeys.JOURNAL_CONTENT);

		String journalPreference = _getJournalPortletPreferences(
			journalArticle);

		PortletPreferences modifiedPortletPreferences =
			_portletPreferencesLocalService.getPortletPreferences(
				portletPreferencesId);

		modifiedPortletPreferences.setPortletId(journalPortletId);
		modifiedPortletPreferences.setPreferences(journalPreference);

		_portletPreferencesLocalService.updatePortletPreferences(
			modifiedPortletPreferences);
	}

	private void _updatePortletReferenceInLayout(long plid)
		throws PortalException {

		Layout layout = _layoutLocalService.getLayout(plid);

		String typedSettings = layout.getTypeSettings();

		String updatedTypedSettings = StringUtil.replace(
			typedSettings, JournalWYSIWYGConstants.PORTLET_ID_WYSIWYG,
			JournalContentPortletKeys.JOURNAL_CONTENT);

		layout.setTypeSettings(updatedTypedSettings);

		_layoutLocalService.updateLayout(layout);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalWYSIWYGUpgradeHelper.class);

	private final AssetEntryLocalService _assetEntryLocalService;
	private final DefaultDDMStructureHelper _defaultDDMStructureHelper;
	private final GroupLocalService _groupLocalService;
	private final JournalArticleLocalService _journalArticleLocalService;
	private final JournalFolderLocalService _journalFolderLocalService;
	private final LayoutLocalService _layoutLocalService;
	private final PortletPreferencesLocalService
		_portletPreferencesLocalService;
	private final UserLocalService _userLocalService;

}
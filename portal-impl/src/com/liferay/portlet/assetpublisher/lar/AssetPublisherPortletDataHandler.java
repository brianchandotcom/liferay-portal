/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.assetpublisher.lar;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.lar.DataLevel;
import com.liferay.portal.kernel.lar.DefaultConfigurationPortletDataHandler;
import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.assetpublisher.util.AssetPublisher;
import com.liferay.portlet.assetpublisher.util.AssetPublisherUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.journal.model.JournalArticle;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * @author Julio Camarero
 */
public class AssetPublisherPortletDataHandler
	extends DefaultConfigurationPortletDataHandler {

	public AssetPublisherPortletDataHandler() {
		setDataLevel(DataLevel.PORTLET_INSTANCE);
		setPublishToLiveByDefault(true);
	}

	protected void updateExportClassNameIds(
			PortletPreferences portletPreferences, String key)
		throws Exception {

		String[] oldValues = portletPreferences.getValues(key, null);

		if (oldValues == null) {
			return;
		}

		String[] newValues = new String[oldValues.length];

		int i = 0;

		for (String oldValue : oldValues) {
			if (key.equals("anyAssetType") &&
				(oldValue.equals("false") || oldValue.equals("true"))) {

				newValues[i++] = oldValue;

				continue;
			}

			try {
				long classNameId = Long.parseLong(oldValue);

				String className = PortalUtil.getClassName(classNameId);

				newValues[i++] = className;
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to find class name ID for class name " +
							oldValue);
				}
			}
		}

		portletPreferences.setValues(key, newValues);
	}

	protected PortletPreferences updateExportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences, long plid)
		throws Exception {

		String anyAssetTypeClassName = StringPool.BLANK;

		long anyAssetType = GetterUtil.getLong(
			portletPreferences.getValue("anyAssetType", null));

		if (anyAssetType > 0) {
			anyAssetTypeClassName = PortalUtil.getClassName(anyAssetType);
		}

		Enumeration<String> enu = portletPreferences.getNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			String value = GetterUtil.getString(
				portletPreferences.getValue(name, null));

			if (name.equals("anyAssetType") || name.equals("classNameIds")) {
				updateExportClassNameIds(portletPreferences, name);
			}
			else if (name.equals(
						"anyClassTypeDLFileEntryAssetRendererFactory") ||
					 (name.equals("classTypeIds") &&
					  anyAssetTypeClassName.equals(
						  DLFileEntry.class.getName())) ||
					 name.equals(
						"classTypeIdsDLFileEntryAssetRendererFactory")) {

				ExportImportHelperUtil.updateExportPreferencesClassPKs(
					portletPreferences, name, DLFileEntryType.class.getName());
			}
			else if (name.equals(
						"anyClassTypeJournalArticleAssetRendererFactory") ||
					 (name.equals("classTypeIds") &&
					  anyAssetTypeClassName.equals(
						  JournalArticle.class.getName())) ||
					 name.equals(
						"classTypeIdsJournalArticleAssetRendererFactory")) {

				ExportImportHelperUtil.updateExportPreferencesClassPKs(
					portletPreferences, name, DDMStructure.class.getName());
			}
			else if (name.equals("assetVocabularyId")) {
				ExportImportHelperUtil.updateExportPreferencesClassPKs(
					portletPreferences, name, AssetVocabulary.class.getName());
			}
			else if (name.startsWith("queryName") &&
					 value.equalsIgnoreCase("assetCategories")) {

				String index = name.substring(9);

				ExportImportHelperUtil.updateExportPreferencesClassPKs(
					portletPreferences, "queryValues" + index,
					AssetCategory.class.getName());
			}
			else if (name.equals("scopeIds")) {
				updateExportScopeIds(
					portletDataContext, portletPreferences, name, plid);
			}
		}

		return portletPreferences;
	}

	protected void updateExportScopeIds(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences, String key, long plid)
		throws Exception {

		String[] oldValues = portletPreferences.getValues(key, null);

		if (oldValues == null) {
			return;
		}

		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		String companyGroupScopeId =
			AssetPublisher.SCOPE_ID_GROUP_PREFIX +
				portletDataContext.getCompanyGroupId();

		String[] newValues = new String[oldValues.length];

		for (int i = 0; i < oldValues.length; i++) {
			String oldValue = oldValues[i];

			if (oldValue.startsWith(AssetPublisher.SCOPE_ID_GROUP_PREFIX)) {
				newValues[i] = StringUtil.replace(
					oldValue, companyGroupScopeId,
					"[$COMPANY_GROUP_SCOPE_ID$]");
			}
			else if (oldValue.startsWith(
						AssetPublisher.SCOPE_ID_LAYOUT_PREFIX)) {

				// Legacy preferences

				String scopeIdSuffix = oldValue.substring(
					AssetPublisher.SCOPE_ID_LAYOUT_PREFIX.length());

				long scopeIdLayoutId = GetterUtil.getLong(scopeIdSuffix);

				Layout scopeIdLayout = LayoutLocalServiceUtil.getLayout(
					layout.getGroupId(), layout.isPrivateLayout(),
					scopeIdLayoutId);

				newValues[i] =
					AssetPublisher.SCOPE_ID_LAYOUT_UUID_PREFIX +
						scopeIdLayout.getUuid();
			}
			else {
				newValues[i] = oldValue;
			}
		}

		portletPreferences.setValues(key, newValues);
	}

	protected void updateImportClassNameIds(
			PortletPreferences portletPreferences, String key)
		throws Exception {

		String[] oldValues = portletPreferences.getValues(key, null);

		if (oldValues == null) {
			return;
		}

		String[] newValues = new String[oldValues.length];

		int i = 0;

		for (String oldValue : oldValues) {
			if (key.equals("anyAssetType") &&
				(oldValue.equals("false") || oldValue.equals("true"))) {

				newValues[i++] = oldValue;

				continue;
			}

			try {
				long classNameId = PortalUtil.getClassNameId(oldValue);

				newValues[i++] = String.valueOf(classNameId);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to find class name ID for class name " +
							oldValue);
				}
			}
		}

		portletPreferences.setValues(key, newValues);
	}

	protected PortletPreferences updateImportPortletPreferences(
			PortletDataContext portletDataContext, long companyId, long ownerId,
			int ownerType, long plid, String portletId,
			PortletPreferences portletPreferences, Layout layout)
		throws Exception {

		Company company = CompanyLocalServiceUtil.getCompanyById(companyId);

		Group companyGroup = company.getGroup();

		String anyAssetTypeClassName = portletPreferences.getValue(
			"anyAssetType", StringPool.BLANK);

		Enumeration<String> enu = portletPreferences.getNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			String value = GetterUtil.getString(
				portletPreferences.getValue(name, null));

			if (name.equals("anyAssetType") || name.equals("classNameIds")) {
				updateImportClassNameIds(
					portletPreferences, name);
			}
			else if (name.equals(
						"anyClassTypeDLFileEntryAssetRendererFactory") ||
					 (name.equals("classTypeIds") &&
					  anyAssetTypeClassName.equals(
						  DLFileEntry.class.getName())) ||
					 name.equals(
						"classTypeIdsDLFileEntryAssetRendererFactory")) {

				ExportImportHelperUtil.updateImportPreferencesClassPKs(
					portletDataContext, portletPreferences, name,
					DLFileEntryType.class, companyGroup.getGroupId());
			}
			else if (name.equals(
						"anyClassTypeJournalArticleAssetRendererFactory") ||
					 (name.equals("classTypeIds") &&
					  anyAssetTypeClassName.equals(
						  JournalArticle.class.getName())) ||
					 name.equals(
						"classTypeIdsJournalArticleAssetRendererFactory")) {

				ExportImportHelperUtil.updateImportPreferencesClassPKs(
					portletDataContext, portletPreferences, name,
					DDMStructure.class, companyGroup.getGroupId());
			}
			else if (name.equals("assetVocabularyId")) {
				ExportImportHelperUtil.updateImportPreferencesClassPKs(
					portletDataContext, portletPreferences, name,
					AssetVocabulary.class, companyGroup.getGroupId());
			}
			else if (name.startsWith("queryName") &&
					 value.equalsIgnoreCase("assetCategories")) {

				String index = name.substring(9, name.length());

				ExportImportHelperUtil.updateImportPreferencesClassPKs(
					portletDataContext, portletPreferences,
					"queryValues" + index, AssetCategory.class,
					companyGroup.getGroupId());
			}
			else if (name.equals("scopeIds")) {
				updateImportScopeIds(
					portletPreferences, name, companyGroup.getGroupId(),
					layout.getPlid());
			}
		}

		return portletPreferences;
	}

	protected void updateImportScopeIds(
			PortletPreferences portletPreferences, String key,
			long companyGroupId, long plid)
		throws Exception {

		String[] oldValues = portletPreferences.getValues(key, null);

		if (oldValues == null) {
			return;
		}

		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		String companyGroupScopeId =
			AssetPublisher.SCOPE_ID_GROUP_PREFIX + companyGroupId;

		List<String> newValues = new ArrayList<String>(oldValues.length);

		for (String oldValue : oldValues) {
			String newValue = StringUtil.replace(
				oldValue, "[$COMPANY_GROUP_SCOPE_ID$]", companyGroupScopeId);

			try {
				if (!AssetPublisherUtil.isScopeIdSelectable(
						PermissionThreadLocal.getPermissionChecker(), newValue,
						companyGroupId, layout)) {

					continue;
				}

				newValues.add(newValue);
			}
			catch (NoSuchGroupException nsge) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Ignoring scope " + newValue + "because the " +
							"referenced group was not found");
				}
			}
		}

		portletPreferences.setValues(
			key, newValues.toArray(new String[newValues.size()]));
	}

	private static Log _log = LogFactoryUtil.getLog(
		AssetPublisherPortletDataHandler.class);

}
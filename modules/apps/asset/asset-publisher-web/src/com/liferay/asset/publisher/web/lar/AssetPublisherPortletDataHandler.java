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

package com.liferay.asset.publisher.web.lar;

import com.liferay.asset.publisher.web.constants.AssetPublisherPortletKeys;
import com.liferay.asset.publisher.web.util.AssetPublisherUtil;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.lar.DataLevel;
import com.liferay.portal.kernel.lar.DefaultConfigurationPortletDataHandler;
import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandlerRegistryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.journal.model.JournalArticle;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;

/**
 * @author Julio Camarero
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AssetPublisherPortletKeys.ASSET_PUBLISHER
	},
	service = PortletDataHandler.class
)
public class AssetPublisherPortletDataHandler
	extends DefaultConfigurationPortletDataHandler {

	public AssetPublisherPortletDataHandler() {
		setDataLevel(DataLevel.PORTLET_INSTANCE);
		setPublishToLiveByDefault(true);
	}

	@Override
	protected PortletPreferences doProcessExportPortletPreferences(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		return updateExportPortletPreferences(
			portletDataContext, portletId, portletPreferences);
	}

	@Override
	protected PortletPreferences doProcessImportPortletPreferences(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		return updateImportPortletPreferences(
			portletDataContext, portletId, portletPreferences);
	}

	@Override
	protected String getDisplayStyle(
		PortletDataContext portletDataContext, String portletId,
		PortletPreferences portletPreferences) {

		try {
			TemplateHandler templateHandler =
				TemplateHandlerRegistryUtil.getTemplateHandler(
					AssetEntry.class.getName());

			if (Validator.isNotNull(templateHandler)) {
				return portletPreferences.getValue("displayStyle", null);
			}
		}
		catch (Exception e) {
		}

		return null;
	}

	@Override
	protected long getDisplayStyleGroupId(
		PortletDataContext portletDataContext, String portletId,
		PortletPreferences portletPreferences) {

		try {
			TemplateHandler templateHandler =
				TemplateHandlerRegistryUtil.getTemplateHandler(
					AssetEntry.class.getName());

			if (Validator.isNotNull(templateHandler)) {
				return GetterUtil.getLong(
					portletPreferences.getValue("displayStyleGroupId", null));
			}
		}
		catch (Exception e) {
		}

		return 0;
	}

	protected void restorePortletPreference(
			PortletDataContext portletDataContext, String name,
			PortletPreferences portletPreferences)
		throws Exception {

		Layout layout = LayoutLocalServiceUtil.getLayout(
			portletDataContext.getPlid());

		PortletPreferences originalPortletPreferences =
			PortletPreferencesFactoryUtil.getLayoutPortletSetup(
				layout, portletDataContext.getPortletId());

		String[] values = originalPortletPreferences.getValues(
			name, new String[] {StringPool.BLANK});

		portletPreferences.setValues(name, values);
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
				long classNameId = GetterUtil.getLong(oldValue);

				String className = PortalUtil.getClassName(classNameId);

				newValues[i++] = className;
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to get class name ID for class name " +
							oldValue);
				}
			}
		}

		portletPreferences.setValues(key, newValues);
	}

	protected PortletPreferences updateExportPortletPreferences(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		String anyAssetTypeClassName = StringPool.BLANK;

		long anyAssetType = GetterUtil.getLong(
			portletPreferences.getValue("anyAssetType", null));

		if (anyAssetType > 0) {
			anyAssetTypeClassName = PortalUtil.getClassName(anyAssetType);
		}

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			portletDataContext.getCompanyId(), portletId);

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

				ExportImportHelperUtil.updateExportPortletPreferencesClassPKs(
					portletDataContext, portlet, portletPreferences, name,
					DLFileEntryType.class.getName());
			}
			else if (name.equals(
						"anyClassTypeJournalArticleAssetRendererFactory") ||
					 (name.equals("classTypeIds") &&
					  anyAssetTypeClassName.equals(
						  JournalArticle.class.getName())) ||
					 name.equals(
						 "classTypeIdsJournalArticleAssetRendererFactory")) {

				ExportImportHelperUtil.updateExportPortletPreferencesClassPKs(
					portletDataContext, portlet, portletPreferences, name,
					DDMStructure.class.getName());
			}
			else if (name.equals("assetVocabularyId")) {
				long assetVocabularyId = GetterUtil.getLong(value);

				AssetVocabulary assetVocabulary =
					AssetVocabularyLocalServiceUtil.fetchAssetVocabulary(
						assetVocabularyId);

				if (assetVocabulary != null) {
					StagedModelDataHandlerUtil.exportReferenceStagedModel(
						portletDataContext, portletId, assetVocabulary);
				}

				ExportImportHelperUtil.updateExportPortletPreferencesClassPKs(
					portletDataContext, portlet, portletPreferences, name,
					AssetVocabulary.class.getName());
			}
			else if (name.startsWith("queryName") &&
					 StringUtil.equalsIgnoreCase(value, "assetCategories")) {

				String index = name.substring(9);

				long assetCategoryId = GetterUtil.getLong(
					portletPreferences.getValue("queryValues" + index, null));

				AssetCategory assetCategory =
					AssetCategoryLocalServiceUtil.fetchAssetCategory(
						assetCategoryId);

				if (assetCategory != null) {
					StagedModelDataHandlerUtil.exportReferenceStagedModel(
						portletDataContext, portletId, assetCategory);
				}

				ExportImportHelperUtil.updateExportPortletPreferencesClassPKs(
					portletDataContext, portlet, portletPreferences,
					"queryValues" + index, AssetCategory.class.getName());
			}
			else if (name.equals("scopeIds")) {
				updateExportScopeIds(
					portletDataContext, portletPreferences, name,
					portletDataContext.getPlid());
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
			AssetPublisherUtil.SCOPE_ID_GROUP_PREFIX +
				portletDataContext.getCompanyGroupId();

		String[] newValues = new String[oldValues.length];

		for (int i = 0; i < oldValues.length; i++) {
			String oldValue = oldValues[i];

			if (oldValue.startsWith(AssetPublisherUtil.SCOPE_ID_GROUP_PREFIX)) {
				newValues[i] = StringUtil.replace(
					oldValue, companyGroupScopeId,
					"[$COMPANY_GROUP_SCOPE_ID$]");
			}
			else if (oldValue.startsWith(
						AssetPublisherUtil.SCOPE_ID_LAYOUT_PREFIX)) {

				// Legacy preferences

				String scopeIdSuffix = oldValue.substring(
					AssetPublisherUtil.SCOPE_ID_LAYOUT_PREFIX.length());

				long scopeIdLayoutId = GetterUtil.getLong(scopeIdSuffix);

				Layout scopeIdLayout = LayoutLocalServiceUtil.getLayout(
					layout.getGroupId(), scopeIdLayoutId);

				newValues[i] =
					AssetPublisherUtil.SCOPE_ID_LAYOUT_UUID_PREFIX +
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
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		StagedModelDataHandlerUtil.importReferenceStagedModels(
			portletDataContext, AssetVocabulary.class);

		StagedModelDataHandlerUtil.importReferenceStagedModels(
			portletDataContext, AssetCategory.class);

		Company company = CompanyLocalServiceUtil.getCompanyById(
			portletDataContext.getCompanyId());

		Group companyGroup = company.getGroup();

		String anyAssetTypeClassName = portletPreferences.getValue(
			"anyAssetType", StringPool.BLANK);

		Enumeration<String> enu = portletPreferences.getNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			String value = GetterUtil.getString(
				portletPreferences.getValue(name, null));

			if (name.equals("anyAssetType") || name.equals("classNameIds")) {
				updateImportClassNameIds(portletPreferences, name);
			}
			else if (name.equals(
						"anyClassTypeDLFileEntryAssetRendererFactory") ||
					 (name.equals("classTypeIds") &&
					  anyAssetTypeClassName.equals(
						  DLFileEntry.class.getName())) ||
					 name.equals(
						 "classTypeIdsDLFileEntryAssetRendererFactory")) {

				ExportImportHelperUtil.updateImportPortletPreferencesClassPKs(
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

				ExportImportHelperUtil.updateImportPortletPreferencesClassPKs(
					portletDataContext, portletPreferences, name,
					DDMStructure.class, companyGroup.getGroupId());
			}
			else if (name.equals("assetVocabularyId")) {
				ExportImportHelperUtil.updateImportPortletPreferencesClassPKs(
					portletDataContext, portletPreferences, name,
					AssetVocabulary.class, companyGroup.getGroupId());
			}
			else if (name.startsWith("queryName") &&
					 StringUtil.equalsIgnoreCase(value, "assetCategories")) {

				String index = name.substring(9, name.length());

				ExportImportHelperUtil.updateImportPortletPreferencesClassPKs(
					portletDataContext, portletPreferences,
					"queryValues" + index, AssetCategory.class,
					companyGroup.getGroupId());
			}
			else if (name.equals("scopeIds")) {
				updateImportScopeIds(
					portletPreferences, name, companyGroup.getGroupId(),
					portletDataContext.getPlid());
			}
		}

		restorePortletPreference(
			portletDataContext, "notifiedAssetEntryIds", portletPreferences);

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
			AssetPublisherUtil.SCOPE_ID_GROUP_PREFIX + companyGroupId;

		List<String> newValues = new ArrayList<>(oldValues.length);

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
						"Ignoring scope " + newValue + " because the " +
							"referenced group was not found",
						nsge);
				}
			}
			catch (NoSuchLayoutException nsle) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Ignoring scope " + newValue + " because the " +
							"referenced layout was not found",
						nsle);
				}
			}
			catch (PrincipalException pe) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Ignoring scope " + newValue + " because the " +
							"referenced parent group no longer allows " +
								"sharing content with child sites",
						pe);
				}
			}
		}

		portletPreferences.setValues(
			key, newValues.toArray(new String[newValues.size()]));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetPublisherPortletDataHandler.class);

}
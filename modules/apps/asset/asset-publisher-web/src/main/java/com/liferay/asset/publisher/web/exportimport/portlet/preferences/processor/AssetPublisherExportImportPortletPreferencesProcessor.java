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

package com.liferay.asset.publisher.web.exportimport.portlet.preferences.processor;

import com.liferay.asset.publisher.web.constants.AssetPublisherPortletKeys;
import com.liferay.asset.publisher.web.util.AssetPublisherUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.persistence.DDMStructureUtil;
import com.liferay.exportimport.portlet.preferences.processor.Capability;
import com.liferay.exportimport.portlet.preferences.processor.ExportImportPortletPreferencesProcessor;
import com.liferay.exportimport.portlet.preferences.processor.base.BaseExportImportPortletPreferencesProcessor;
import com.liferay.exportimport.portlet.preferences.processor.capability.ReferencedStagedModelImporterCapability;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.LayoutLocalService;
import com.liferay.portal.service.OrganizationLocalService;
import com.liferay.portal.service.PortletLocalService;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetCategoryLocalService;
import com.liferay.portlet.asset.service.AssetVocabularyLocalService;
import com.liferay.portlet.asset.service.persistence.AssetCategoryUtil;
import com.liferay.portlet.asset.service.persistence.AssetVocabularyUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalService;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryTypeUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataException;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AssetPublisherPortletKeys.ASSET_PUBLISHER
	},
	service = ExportImportPortletPreferencesProcessor.class
)
public class AssetPublisherExportImportPortletPreferencesProcessor
	extends BaseExportImportPortletPreferencesProcessor {

	@Override
	public List<Capability> getExportCapabilities() {
		return ListUtil.toList(
			new Capability[] {
				_assetPublisherPortletDisplayTemplateExportCapability
			});
	}

	@Override
	public List<Capability> getImportCapabilities() {
		return ListUtil.toList(
			new Capability[] {
				_assetPublisherPortletDisplayTemplateImportCapability,
				_referencedStagedModelImporterCapability
			});
	}

	@Override
	public PortletPreferences processExportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		try {
			exportAssetObjects(portletDataContext, portletPreferences);

			return updateExportPortletPreferences(
				portletDataContext, portletDataContext.getPortletId(),
				portletPreferences);
		}
		catch (Exception e) {
			return portletPreferences;
		}
	}

	@Override
	public PortletPreferences processImportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		try {
			return updateImportPortletPreferences(
				portletDataContext, portletPreferences);
		}
		catch (Exception e) {
			return portletPreferences;
		}
	}

	protected void exportAssetObjects(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		Layout layout = _layoutLocalService.getLayout(
			portletDataContext.getPlid());

		long[] groupIds = AssetPublisherUtil.getGroupIds(
			portletPreferences, portletDataContext.getScopeGroupId(), layout);

		List<AssetEntry> assetEntries = AssetPublisherUtil.getAssetEntries(
			null, portletPreferences,
			PermissionThreadLocal.getPermissionChecker(), groupIds, false,
			false);

		for (AssetEntry assetEntry : assetEntries) {
			AssetRenderer<?> assetRenderer = assetEntry.getAssetRenderer();

			if ((assetRenderer == null) ||
				!(assetRenderer.getAssetObject() instanceof StagedModel)) {

				continue;
			}

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, portletDataContext.getPortletId(),
				(StagedModel)assetRenderer.getAssetObject());
		}
	}

	@Override
	protected String getExportPortletPreferencesUuid(
			PortletDataContext portletDataContext, Portlet portlet,
			String className, long primaryKeyLong)
		throws Exception {

		String uuid = null;

		Element rootElement = portletDataContext.getExportDataRootElement();

		if (className.equals(AssetCategory.class.getName())) {
			AssetCategory assetCategory =
				_assetCategoryLocalService.fetchCategory(primaryKeyLong);

			if (assetCategory != null) {
				uuid = assetCategory.getUuid();

				portletDataContext.addReferenceElement(
					portlet, rootElement, assetCategory,
					PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);
			}
		}
		else if (className.equals(AssetVocabulary.class.getName())) {
			AssetVocabulary assetVocabulary =
				_assetVocabularyLocalService.fetchAssetVocabulary(
					primaryKeyLong);

			if (assetVocabulary != null) {
				uuid = assetVocabulary.getUuid();

				portletDataContext.addReferenceElement(
					portlet, rootElement, assetVocabulary,
					PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);
			}
		}
		else if (className.equals(DDMStructure.class.getName())) {
			DDMStructure ddmStructure =
				_ddmStructureLocalService.fetchStructure(primaryKeyLong);

			if (ddmStructure != null) {
				uuid = ddmStructure.getUuid();

				portletDataContext.addReferenceElement(
					portlet, rootElement, ddmStructure,
					PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);
			}
		}
		else if (className.equals(DLFileEntryType.class.getName())) {
			DLFileEntryType dlFileEntryType =
				_dlFileEntryTypeLocalService.fetchFileEntryType(primaryKeyLong);

			if (dlFileEntryType != null) {
				uuid = dlFileEntryType.getUuid();

				portletDataContext.addReferenceElement(
					portlet, rootElement, dlFileEntryType,
					PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);
			}
		}
		else if (className.equals(Organization.class.getName())) {
			Organization organization =
				_organizationLocalService.fetchOrganization(primaryKeyLong);

			if (organization != null) {
				uuid = organization.getUuid();

				portletDataContext.addReferenceElement(
					portlet, rootElement, organization,
					PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);
			}
		}

		return uuid;
	}

	@Override
	protected Long getImportPortletPreferencesNewPrimaryKey(
			PortletDataContext portletDataContext, Class<?> clazz,
			long companyGroupId, Map<Long, Long> primaryKeys, String uuid)
		throws Exception {

		if (Validator.isNumber(uuid)) {
			long oldPrimaryKey = GetterUtil.getLong(uuid);

			return MapUtil.getLong(primaryKeys, oldPrimaryKey, oldPrimaryKey);
		}

		String className = clazz.getName();

		if (className.equals(AssetCategory.class.getName())) {
			AssetCategory assetCategory = AssetCategoryUtil.fetchByUUID_G(
				uuid, portletDataContext.getScopeGroupId());

			if (assetCategory == null) {
				assetCategory = AssetCategoryUtil.fetchByUUID_G(
					uuid, companyGroupId);
			}

			if (assetCategory != null) {
				return assetCategory.getCategoryId();
			}
		}
		else if (className.equals(AssetVocabulary.class.getName())) {
			AssetVocabulary assetVocabulary = AssetVocabularyUtil.fetchByUUID_G(
				uuid, portletDataContext.getScopeGroupId());

			if (assetVocabulary == null) {
				assetVocabulary = AssetVocabularyUtil.fetchByUUID_G(
					uuid, companyGroupId);
			}

			if (assetVocabulary != null) {
				return assetVocabulary.getVocabularyId();
			}
		}
		else if (className.equals(DDMStructure.class.getName())) {
			DDMStructure ddmStructure = DDMStructureUtil.fetchByUUID_G(
				uuid, portletDataContext.getScopeGroupId());

			if (ddmStructure == null) {
				ddmStructure = DDMStructureUtil.fetchByUUID_G(
					uuid, companyGroupId);
			}

			if (ddmStructure != null) {
				return ddmStructure.getStructureId();
			}
		}
		else if (className.equals(DLFileEntryType.class.getName())) {
			DLFileEntryType dlFileEntryType = DLFileEntryTypeUtil.fetchByUUID_G(
				uuid, portletDataContext.getScopeGroupId());

			if (dlFileEntryType == null) {
				dlFileEntryType = DLFileEntryTypeUtil.fetchByUUID_G(
					uuid, companyGroupId);
			}

			if (dlFileEntryType == null) {
				Element rootElement =
					portletDataContext.getImportDataRootElement();

				Element element = portletDataContext.getReferenceElement(
					rootElement, clazz, companyGroupId, uuid,
					PortletDataContext.REFERENCE_TYPE_DEPENDENCY);

				if (element != null) {
					String fileEntryTypeKey = element.attributeValue(
						"file-entry-type-key");

					boolean preloaded = GetterUtil.getBoolean(
						element.attributeValue("preloaded"));

					if (preloaded) {
						dlFileEntryType =
							_dlFileEntryTypeLocalService.fetchFileEntryType(
								companyGroupId, fileEntryTypeKey);
					}
				}
			}

			if (dlFileEntryType != null) {
				return dlFileEntryType.getFileEntryTypeId();
			}
		}

		return null;
	}

	protected void restorePortletPreference(
			PortletDataContext portletDataContext, String name,
			PortletPreferences portletPreferences)
		throws Exception {

		Layout layout = _layoutLocalService.getLayout(
			portletDataContext.getPlid());

		PortletPreferences originalPortletPreferences =
			PortletPreferencesFactoryUtil.getLayoutPortletSetup(
				layout, portletDataContext.getPortletId());

		String[] values = originalPortletPreferences.getValues(
			name, new String[] {StringPool.BLANK});

		portletPreferences.setValues(name, values);
	}

	@Reference(unbind = "-")
	protected void setAssetCategoryLocalService(
		AssetCategoryLocalService assetCategoryLocalService) {

		_assetCategoryLocalService = assetCategoryLocalService;
	}

	@Reference(unbind = "-")
	protected void setAssetPublisherPortletDisplayTemplateExportCapability(
		AssetPublisherPortletDisplayTemplateExportCapability
			assetPublisherPortletDisplayTemplateExportCapability) {

		_assetPublisherPortletDisplayTemplateExportCapability =
			assetPublisherPortletDisplayTemplateExportCapability;
	}

	@Reference(unbind = "-")
	protected void setAssetPublisherPortletDisplayTemplateImportCapability(
		AssetPublisherPortletDisplayTemplateImportCapability
			assetPublisherPortletDisplayTemplateImportCapability) {

		_assetPublisherPortletDisplayTemplateImportCapability =
			assetPublisherPortletDisplayTemplateImportCapability;
	}

	@Reference(unbind = "-")
	protected void setAssetVocabularyLocalService(
		AssetVocabularyLocalService assetVocabularyLocalService) {

		_assetVocabularyLocalService = assetVocabularyLocalService;
	}

	@Reference(unbind = "-")
	protected void setCompanyLocalService(
		CompanyLocalService companyLocalService) {

		_companyLocalService = companyLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	@Reference(unbind = "-")
	protected void setDLFileEntryTypeLocalService(
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService) {

		_dlFileEntryTypeLocalService = dlFileEntryTypeLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	@Reference(unbind = "-")
	protected void setOrganizationLocalService(
		OrganizationLocalService organizationLocalService) {

		_organizationLocalService = organizationLocalService;
	}

	@Reference(unbind = "-")
	protected void setPortletLocalService(
		PortletLocalService portletLocalService) {

		_portletLocalService = portletLocalService;
	}

	@Reference(unbind = "-")
	protected void setReferencedStagedModelImporterCapability(
		ReferencedStagedModelImporterCapability
			referencedStagedModelImporterCapability) {

		_referencedStagedModelImporterCapability =
			referencedStagedModelImporterCapability;
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

		Portlet portlet = _portletLocalService.getPortletById(
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

				updateExportPortletPreferencesClassPKs(
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

				updateExportPortletPreferencesClassPKs(
					portletDataContext, portlet, portletPreferences, name,
					DDMStructure.class.getName());
			}
			else if (name.equals("assetVocabularyId")) {
				long assetVocabularyId = GetterUtil.getLong(value);

				AssetVocabulary assetVocabulary =
					_assetVocabularyLocalService.fetchAssetVocabulary(
						assetVocabularyId);

				if (assetVocabulary != null) {
					StagedModelDataHandlerUtil.exportReferenceStagedModel(
						portletDataContext, portletId, assetVocabulary);
				}

				updateExportPortletPreferencesClassPKs(
					portletDataContext, portlet, portletPreferences, name,
					AssetVocabulary.class.getName());
			}
			else if (name.startsWith("queryName") &&
					 StringUtil.equalsIgnoreCase(value, "assetCategories")) {

				String index = name.substring(9);

				long assetCategoryId = GetterUtil.getLong(
					portletPreferences.getValue("queryValues" + index, null));

				AssetCategory assetCategory =
					_assetCategoryLocalService.fetchAssetCategory(
						assetCategoryId);

				if (assetCategory != null) {
					StagedModelDataHandlerUtil.exportReferenceStagedModel(
						portletDataContext, portletId, assetCategory);
				}

				updateExportPortletPreferencesClassPKs(
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

		Layout layout = _layoutLocalService.getLayout(plid);

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

				Layout scopeIdLayout = _layoutLocalService.getLayout(
					layout.getGroupId(), layout.isPrivateLayout(),
					scopeIdLayoutId);

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
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		Company company = _companyLocalService.getCompanyById(
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

				updateImportPortletPreferencesClassPKs(
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

				updateImportPortletPreferencesClassPKs(
					portletDataContext, portletPreferences, name,
					DDMStructure.class, companyGroup.getGroupId());
			}
			else if (name.equals("assetVocabularyId")) {
				updateImportPortletPreferencesClassPKs(
					portletDataContext, portletPreferences, name,
					AssetVocabulary.class, companyGroup.getGroupId());
			}
			else if (name.startsWith("queryName") &&
					 StringUtil.equalsIgnoreCase(value, "assetCategories")) {

				String index = name.substring(9, name.length());

				updateImportPortletPreferencesClassPKs(
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

		Layout layout = _layoutLocalService.getLayout(plid);

		String companyGroupScopeId =
			AssetPublisherUtil.SCOPE_ID_GROUP_PREFIX + companyGroupId;

		List<String> newValues = new ArrayList<>(oldValues.length);

		for (String oldValue : oldValues) {
			String newValue = StringUtil.replace(
				oldValue, "[$COMPANY_GROUP_SCOPE_ID$]", companyGroupScopeId);

			try {
				if (!AssetPublisherUtil.isScopeIdSelectable(
						PermissionThreadLocal.getPermissionChecker(), newValue,
						companyGroupId, layout, false)) {

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
		AssetPublisherExportImportPortletPreferencesProcessor.class);

	private volatile AssetCategoryLocalService _assetCategoryLocalService;
	private volatile AssetPublisherPortletDisplayTemplateExportCapability
		_assetPublisherPortletDisplayTemplateExportCapability;
	private volatile AssetPublisherPortletDisplayTemplateImportCapability
		_assetPublisherPortletDisplayTemplateImportCapability;
	private volatile AssetVocabularyLocalService _assetVocabularyLocalService;
	private volatile CompanyLocalService _companyLocalService;
	private volatile DDMStructureLocalService _ddmStructureLocalService;
	private volatile DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;
	private volatile LayoutLocalService _layoutLocalService;
	private volatile OrganizationLocalService _organizationLocalService;
	private volatile PortletLocalService _portletLocalService;
	private volatile ReferencedStagedModelImporterCapability
		_referencedStagedModelImporterCapability;

}
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

package com.liferay.asset.publisher.web.internal.exportimport.portlet.preferences.processor;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.asset.kernel.service.persistence.AssetCategoryUtil;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.kernel.service.persistence.AssetVocabularyUtil;
import com.liferay.asset.publisher.web.constants.AssetPublisherPortletKeys;
import com.liferay.asset.publisher.web.internal.configuration.AssetPublisherWebConfigurationValues;
import com.liferay.asset.publisher.web.util.AssetPublisherUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.document.library.kernel.service.persistence.DLFileEntryTypeUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.persistence.DDMStructureUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.portlet.preferences.processor.Capability;
import com.liferay.exportimport.portlet.preferences.processor.ExportImportPortletPreferencesProcessor;
import com.liferay.exportimport.portlet.preferences.processor.base.BaseExportImportPortletPreferencesProcessor;
import com.liferay.exportimport.portlet.preferences.processor.capability.ReferencedStagedModelImporterCapability;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.site.model.adapter.StagedGroup;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
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
				_assetPublisherPortletDisplayTemplateImportCapability
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
			importLayoutReferences(portletDataContext);

			_referencedStagedModelImporterCapability.process(
				portletDataContext, portletPreferences);

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

		List<AssetEntry> assetEntries = null;

		Layout layout = _layoutLocalService.getLayout(
			portletDataContext.getPlid());

		long[] groupIds = AssetPublisherUtil.getGroupIds(
			portletPreferences, portletDataContext.getScopeGroupId(), layout);

		String selectionStyle = portletPreferences.getValue(
			"selectionStyle", "dynamic");

		if (selectionStyle.equals("dynamic")) {
			if (!AssetPublisherWebConfigurationValues.DYNAMIC_EXPORT_ENABLED) {
				return;
			}

			AssetEntryQuery assetEntryQuery = getAssetEntryQuery(
				layout, portletDataContext.getCompanyGroupId(), groupIds,
				portletPreferences);

			long assetVocabularyId = GetterUtil.getLong(
				portletPreferences.getValue("assetVocabularyId", null));

			if (assetVocabularyId > 0) {
				mergeAnyCategoryIds(assetEntryQuery, assetVocabularyId);

				if (ArrayUtil.isEmpty(assetEntryQuery.getAnyCategoryIds())) {
					return;
				}
			}

			BaseModelSearchResult<AssetEntry> baseModelSearchResult =
				AssetPublisherUtil.getAssetEntries(
					assetEntryQuery, layout, portletPreferences,
					AssetPublisherPortletKeys.ASSET_PUBLISHER,
					LocaleUtil.getDefault(), TimeZoneUtil.getDefault(),
					portletDataContext.getCompanyId(),
					portletDataContext.getScopeGroupId(),
					UserConstants.USER_ID_DEFAULT,
					new HashMap<String, Serializable>(),
					assetEntryQuery.getStart(), assetEntryQuery.getEnd());

			assetEntries = baseModelSearchResult.getBaseModels();
		}
		else {
			if (!AssetPublisherWebConfigurationValues.DYNAMIC_EXPORT_ENABLED) {
				return;
			}

			assetEntries = AssetPublisherUtil.getAssetEntries(
				null, portletPreferences,
				PermissionThreadLocal.getPermissionChecker(), groupIds, false,
				false);
		}

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

	protected AssetEntryQuery getAssetEntryQuery(
			Layout layout, long companyId, long[] groupIds,
			PortletPreferences portletPreferences)
		throws Exception {

		AssetEntryQuery assetEntryQuery = AssetPublisherUtil.getAssetEntryQuery(
			portletPreferences, groupIds, null, null);

		long[] classNameIds = AssetPublisherUtil.getClassNameIds(
			portletPreferences,
			AssetRendererFactoryRegistryUtil.getClassNameIds(companyId, true));

		assetEntryQuery.setClassNameIds(classNameIds);

		long[] classTypeIds = GetterUtil.getLongValues(
			portletPreferences.getValues("classTypeIds", null));

		assetEntryQuery.setClassTypeIds(classTypeIds);

		assetEntryQuery.setEnablePermissions(false);

		int end = AssetPublisherWebConfigurationValues.DYNAMIC_EXPORT_LIMIT;

		if (end == 0) {
			end = QueryUtil.ALL_POS;
		}

		assetEntryQuery.setEnd(end);

		assetEntryQuery.setExcludeZeroViewCount(false);
		assetEntryQuery.setGroupIds(groupIds);

		boolean showOnlyLayoutAssets = GetterUtil.getBoolean(
			portletPreferences.getValue("showOnlyLayoutAssets", null));

		if (showOnlyLayoutAssets) {
			assetEntryQuery.setLayout(layout);
		}

		String orderByCol1 = GetterUtil.getString(
			portletPreferences.getValue("orderByColumn1", "modifiedDate"));

		assetEntryQuery.setOrderByCol1(orderByCol1);

		String orderByCol2 = GetterUtil.getString(
			portletPreferences.getValue("orderByColumn2", "title"));

		assetEntryQuery.setOrderByCol2(orderByCol2);

		String orderByType1 = GetterUtil.getString(
			portletPreferences.getValue("orderByType1", "DESC"));

		assetEntryQuery.setOrderByType1(orderByType1);

		String orderByType2 = GetterUtil.getString(
			portletPreferences.getValue("orderByType2", "ASC"));

		assetEntryQuery.setOrderByType2(orderByType2);

		int start = 0;

		if (end == 0) {
			start = QueryUtil.ALL_POS;
		}

		assetEntryQuery.setStart(start);

		return assetEntryQuery;
	}

	@Override
	protected String getExportPortletPreferencesValue(
			PortletDataContext portletDataContext, Portlet portlet,
			String className, long primaryKeyLong)
		throws Exception {

		String uuid = null;
		long groupId = 0L;

		Element rootElement = portletDataContext.getExportDataRootElement();

		if (className.equals(AssetCategory.class.getName())) {
			AssetCategory assetCategory =
				_assetCategoryLocalService.fetchCategory(primaryKeyLong);

			if (assetCategory != null) {
				uuid = assetCategory.getUuid();
				groupId = assetCategory.getGroupId();

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
				groupId = assetVocabulary.getGroupId();

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
				groupId = ddmStructure.getGroupId();

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
				groupId = dlFileEntryType.getGroupId();

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

		if (Validator.isNull(uuid)) {
			return null;
		}

		return StringUtil.merge(new Object[] {uuid, groupId}, StringPool.POUND);
	}

	@Override
	protected Long getImportPortletPreferencesNewValue(
			PortletDataContext portletDataContext, Class<?> clazz,
			long companyGroupId, Map<Long, Long> primaryKeys,
			String portletPreferencesOldValue)
		throws Exception {

		if (Validator.isNumber(portletPreferencesOldValue)) {
			long oldPrimaryKey = GetterUtil.getLong(portletPreferencesOldValue);

			return MapUtil.getLong(primaryKeys, oldPrimaryKey, oldPrimaryKey);
		}

		String className = clazz.getName();

		String[] oldValues = StringUtil.split(
			portletPreferencesOldValue, StringPool.POUND);

		String uuid = oldValues[0];

		long groupId = portletDataContext.getScopeGroupId();

		if (oldValues.length > 1) {
			Map<Long, Long> groupIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					Group.class);

			groupId = MapUtil.getLong(
				groupIds, GetterUtil.getLong(oldValues[1]));
		}

		if (className.equals(AssetCategory.class.getName())) {
			AssetCategory assetCategory = AssetCategoryUtil.fetchByUUID_G(
				uuid, groupId);

			if (assetCategory != null) {
				return assetCategory.getCategoryId();
			}
		}
		else if (className.equals(AssetVocabulary.class.getName())) {
			AssetVocabulary assetVocabulary = AssetVocabularyUtil.fetchByUUID_G(
				uuid, groupId);

			if (assetVocabulary != null) {
				return assetVocabulary.getVocabularyId();
			}
		}
		else if (className.equals(DDMStructure.class.getName())) {
			DDMStructure ddmStructure = DDMStructureUtil.fetchByUUID_G(
				uuid, groupId);

			if (ddmStructure != null) {
				return ddmStructure.getStructureId();
			}
		}
		else if (className.equals(DLFileEntryType.class.getName())) {
			DLFileEntryType dlFileEntryType = DLFileEntryTypeUtil.fetchByUUID_G(
				uuid, groupId);

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

	protected void importLayoutReferences(PortletDataContext portletDataContext)
		throws PortletDataException {

		Element importDataRootElement =
			portletDataContext.getImportDataRootElement();

		Element referencesElement = importDataRootElement.element("references");

		if (referencesElement == null) {
			return;
		}

		List<Element> referenceElements = referencesElement.elements();

		for (Element referenceElement : referenceElements) {
			String className = referenceElement.attributeValue("class-name");

			if (!className.equals(Layout.class.getName())) {
				continue;
			}

			long classPK = GetterUtil.getLong(
				referenceElement.attributeValue("class-pk"));

			StagedModelDataHandlerUtil.importReferenceStagedModel(
				portletDataContext, className, classPK);
		}
	}

	protected void mergeAnyCategoryIds(
		AssetEntryQuery assetEntryQuery, long assetVocabularyId) {

		List<AssetCategory> assetCategories =
			_assetCategoryLocalService.getVocabularyRootCategories(
				assetVocabularyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		long[] vocabularyCategoryIds = new long[0];

		for (AssetCategory assetCategory : assetCategories) {
			vocabularyCategoryIds = ArrayUtil.append(
				vocabularyCategoryIds, assetCategory.getCategoryId());
		}

		long[] originalAnyCategoryIds = assetEntryQuery.getAnyCategoryIds();

		if (ArrayUtil.isEmpty(originalAnyCategoryIds)) {
			assetEntryQuery.setAnyCategoryIds(vocabularyCategoryIds);
		}
		else {
			long[] newAnyCategoryIds = new long[0];

			for (int i = 0; i < originalAnyCategoryIds.length; i++) {
				if (ArrayUtil.contains(
						vocabularyCategoryIds, originalAnyCategoryIds[i])) {

					newAnyCategoryIds = ArrayUtil.append(
						newAnyCategoryIds, originalAnyCategoryIds[i]);
				}
			}

			assetEntryQuery.setAnyCategoryIds(newAnyCategoryIds);
		}
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
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
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

		Element rootElement = portletDataContext.getExportDataRootElement();

		Element groupIdMappingsElement = rootElement.addElement(
			"group-id-mappings");

		for (int i = 0; i < oldValues.length; i++) {
			String oldValue = oldValues[i];

			if (oldValue.startsWith(AssetPublisherUtil.SCOPE_ID_GROUP_PREFIX)) {
				newValues[i] = StringUtil.replace(
					oldValue, companyGroupScopeId,
					"[$COMPANY_GROUP_SCOPE_ID$]");

				if (newValues[i].contains("[$COMPANY_GROUP_SCOPE_ID$]")) {
					continue;
				}
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

				if (plid != scopeIdLayout.getPlid()) {
					StagedModelDataHandlerUtil.exportReferenceStagedModel(
						portletDataContext, portletDataContext.getPortletId(),
						scopeIdLayout);
				}

				newValues[i] =
					AssetPublisherUtil.SCOPE_ID_LAYOUT_UUID_PREFIX +
						scopeIdLayout.getUuid();
			}
			else if (oldValue.startsWith(
						AssetPublisherUtil.SCOPE_ID_LAYOUT_UUID_PREFIX)) {

				String scopeLayoutUuid = oldValue.substring(
					AssetPublisherUtil.SCOPE_ID_LAYOUT_UUID_PREFIX.length());

				Layout scopeUuidLayout =
					_layoutLocalService.getLayoutByUuidAndGroupId(
						scopeLayoutUuid, portletDataContext.getGroupId(),
						portletDataContext.isPrivateLayout());

				if (plid != scopeUuidLayout.getPlid()) {
					StagedModelDataHandlerUtil.exportReferenceStagedModel(
						portletDataContext, portletDataContext.getPortletId(),
						scopeUuidLayout);
				}

				newValues[i] = oldValue;
			}
			else {
				newValues[i] = oldValue;
			}

			long groupId = AssetPublisherUtil.getGroupIdFromScopeId(
				newValues[i], portletDataContext.getGroupId(),
				portletDataContext.isPrivateLayout());

			Group group = _groupLocalService.fetchGroup(groupId);

			long liveGroupId = 0;

			if (group != null) {
				liveGroupId = group.getLiveGroupId();

				if (group.isStagedRemotely()) {
					liveGroupId = group.getRemoteLiveGroupId();
				}
			}

			if ((groupId == 0) || (liveGroupId == 0)) {
				continue;
			}

			newValues[i] = String.valueOf(groupId);

			Element groupIdMappingElement = groupIdMappingsElement.addElement(
				"group-id-mapping");

			groupIdMappingElement.addAttribute(
				"group-id", String.valueOf(groupId));
			groupIdMappingElement.addAttribute(
				"live-group-id", String.valueOf(liveGroupId));
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
					portletDataContext, portletPreferences, name,
					companyGroup.getGroupId(), portletDataContext.getPlid());
			}
		}

		restorePortletPreference(
			portletDataContext, "notifiedAssetEntryIds", portletPreferences);

		return portletPreferences;
	}

	protected void updateImportScopeIds(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences, String key,
			long companyGroupId, long plid)
		throws Exception {

		String[] oldValues = portletPreferences.getValues(key, null);

		if (oldValues == null) {
			return;
		}

		StagedModelDataHandler<StagedGroup> stagedModelDataHandler =
			(StagedModelDataHandler<StagedGroup>)
				StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
					StagedGroup.class.getName());

		Element rootElement = portletDataContext.getImportDataRootElement();

		Element groupIdMappingsElement = rootElement.element(
			"group-id-mappings");

		for (Element groupIdMappingElement :
				groupIdMappingsElement.elements("group-id-mapping")) {

			stagedModelDataHandler.importMissingReference(
				portletDataContext, groupIdMappingElement);
		}

		Map<Long, Long> groupIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Group.class);

		Layout layout = _layoutLocalService.getLayout(plid);

		String companyGroupScopeId =
			AssetPublisherUtil.SCOPE_ID_GROUP_PREFIX + companyGroupId;

		List<String> newValues = new ArrayList<>(oldValues.length);

		for (String oldValue : oldValues) {
			String newValue = StringUtil.replace(
				oldValue, "[$COMPANY_GROUP_SCOPE_ID$]", companyGroupScopeId);

			if (Validator.isNumber(oldValue) &&
				groupIds.containsKey(Long.valueOf(oldValue))) {

				Group group = _groupLocalService.fetchGroup(
					groupIds.get(Long.valueOf(oldValue)));

				if (group != null) {
					newValue = AssetPublisherUtil.getScopeId(
						group, portletDataContext.getScopeGroupId());
				}
			}

			if (Validator.isNumber(newValue)) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Ignoring group " + newValue + " because it cannot " +
							"be converted to scope");
				}

				continue;
			}

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

	private AssetCategoryLocalService _assetCategoryLocalService;
	private AssetPublisherPortletDisplayTemplateExportCapability
		_assetPublisherPortletDisplayTemplateExportCapability;
	private AssetPublisherPortletDisplayTemplateImportCapability
		_assetPublisherPortletDisplayTemplateImportCapability;
	private AssetVocabularyLocalService _assetVocabularyLocalService;
	private CompanyLocalService _companyLocalService;
	private DDMStructureLocalService _ddmStructureLocalService;
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;
	private GroupLocalService _groupLocalService;
	private LayoutLocalService _layoutLocalService;
	private OrganizationLocalService _organizationLocalService;
	private PortletLocalService _portletLocalService;
	private ReferencedStagedModelImporterCapability
		_referencedStagedModelImporterCapability;

}
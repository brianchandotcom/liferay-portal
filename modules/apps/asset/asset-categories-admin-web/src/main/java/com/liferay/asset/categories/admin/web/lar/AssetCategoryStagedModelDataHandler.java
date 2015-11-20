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

package com.liferay.asset.categories.admin.web.lar;

import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetCategoryConstants;
import com.liferay.portlet.asset.model.AssetCategoryProperty;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetCategoryLocalService;
import com.liferay.portlet.asset.service.AssetCategoryPropertyLocalService;
import com.liferay.portlet.asset.service.AssetVocabularyLocalService;
import com.liferay.portlet.asset.service.persistence.AssetCategoryUtil;
import com.liferay.portlet.exportimport.lar.ExportImportPathUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;
import com.liferay.portlet.exportimport.lar.StagedModelModifiedDateComparator;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zsolt Berentey
 * @author Gergely Mathe
 * @author Mate Thurzo
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class AssetCategoryStagedModelDataHandler
	extends BaseStagedModelDataHandler<AssetCategory> {

	public static final String[] CLASS_NAMES = {AssetCategory.class.getName()};

	@Override
	public void deleteStagedModel(AssetCategory category)
		throws PortalException {

		_assetCategoryLocalService.deleteCategory(category);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		AssetCategory category = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (category != null) {
			deleteStagedModel(category);
		}
	}

	@Override
	public AssetCategory fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _assetCategoryLocalService.fetchAssetCategoryByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<AssetCategory> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _assetCategoryLocalService.getAssetCategoriesByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<AssetCategory>());
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(AssetCategory category) {
		return category.getTitleCurrentValue();
	}

	protected ServiceContext createServiceContext(
		PortletDataContext portletDataContext, AssetCategory category) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCreateDate(category.getCreateDate());
		serviceContext.setModifiedDate(category.getModifiedDate());
		serviceContext.setScopeGroupId(portletDataContext.getScopeGroupId());

		return serviceContext;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, AssetCategory category)
		throws Exception {

		if (category.getParentCategoryId() !=
				AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

			AssetCategory parentCategory =
				_assetCategoryLocalService.fetchAssetCategory(
					category.getParentCategoryId());

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, category, parentCategory,
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}
		else {
			AssetVocabulary vocabulary =
				_assetVocabularyLocalService.fetchAssetVocabulary(
					category.getVocabularyId());

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, category, vocabulary,
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}

		Element categoryElement = portletDataContext.getExportDataElement(
			category);

		category.setUserUuid(category.getUserUuid());

		List<AssetCategoryProperty> categoryProperties =
			_assetCategoryPropertyLocalService.getCategoryProperties(
				category.getCategoryId());

		for (AssetCategoryProperty categoryProperty : categoryProperties) {
			Element propertyElement = categoryElement.addElement("property");

			propertyElement.addAttribute(
				"userUuid", categoryProperty.getUserUuid());
			propertyElement.addAttribute("key", categoryProperty.getKey());
			propertyElement.addAttribute("value", categoryProperty.getValue());
		}

		String categoryPath = ExportImportPathUtil.getModelPath(category);

		categoryElement.addAttribute("path", categoryPath);

		portletDataContext.addReferenceElement(
			category, categoryElement, category,
			PortletDataContext.REFERENCE_TYPE_DEPENDENCY, false);

		portletDataContext.addPermissions(
			AssetCategory.class, category.getCategoryId());

		portletDataContext.addZipEntry(categoryPath, category);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long categoryId)
		throws Exception {

		AssetCategory existingCategory = fetchMissingReference(uuid, groupId);

		if (existingCategory == null) {
			return;
		}

		Map<Long, Long> categoryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				AssetCategory.class);

		categoryIds.put(categoryId, existingCategory.getCategoryId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, AssetCategory category)
		throws Exception {

		long userId = portletDataContext.getUserId(category.getUserUuid());

		Map<Long, Long> vocabularyIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				AssetVocabulary.class);

		long vocabularyId = MapUtil.getLong(
			vocabularyIds, category.getVocabularyId(),
			category.getVocabularyId());

		Map<Long, Long> categoryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				AssetCategory.class);

		long parentCategoryId = MapUtil.getLong(
			categoryIds, category.getParentCategoryId(),
			category.getParentCategoryId());

		Element categoryElement = portletDataContext.getImportDataElement(
			category);

		List<Element> propertyElements = categoryElement.elements("property");

		String[] properties = new String[propertyElements.size()];

		for (int i = 0; i < propertyElements.size(); i++) {
			Element propertyElement = propertyElements.get(i);

			String key = propertyElement.attributeValue("key");
			String value = propertyElement.attributeValue("value");

			properties[i] = key.concat(
				AssetCategoryConstants.PROPERTY_KEY_VALUE_SEPARATOR).concat(
					value);
		}

		ServiceContext serviceContext = createServiceContext(
			portletDataContext, category);

		AssetCategory importedCategory = null;

		AssetCategory existingCategory = fetchStagedModelByUuidAndGroupId(
			category.getUuid(), portletDataContext.getScopeGroupId());

		if (existingCategory == null) {
			String name = getCategoryName(
				null, portletDataContext.getScopeGroupId(), parentCategoryId,
				category.getName(), vocabularyId, 2);

			serviceContext.setUuid(category.getUuid());

			importedCategory = _assetCategoryLocalService.addCategory(
				userId, portletDataContext.getScopeGroupId(), parentCategoryId,
				getCategoryTitleMap(
					portletDataContext.getScopeGroupId(), category, name),
				category.getDescriptionMap(), vocabularyId, properties,
				serviceContext);
		}
		else {
			String name = getCategoryName(
				category.getUuid(), portletDataContext.getScopeGroupId(),
				parentCategoryId, category.getName(), vocabularyId, 2);

			importedCategory = _assetCategoryLocalService.updateCategory(
				userId, existingCategory.getCategoryId(), parentCategoryId,
				getCategoryTitleMap(
					portletDataContext.getScopeGroupId(), category, name),
				category.getDescriptionMap(), vocabularyId, properties,
				serviceContext);
		}

		categoryIds.put(
			category.getCategoryId(), importedCategory.getCategoryId());

		Map<String, String> categoryUuids =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				AssetCategory.class + ".uuid");

		categoryUuids.put(category.getUuid(), importedCategory.getUuid());

		portletDataContext.importPermissions(
			AssetCategory.class, category.getCategoryId(),
			importedCategory.getCategoryId());
	}

	protected String getCategoryName(
			String uuid, long groupId, long parentCategoryId, String name,
			long vocabularyId, int count)
		throws Exception {

		AssetCategory category = AssetCategoryUtil.fetchByG_P_N_V_First(
			groupId, parentCategoryId, name, vocabularyId, null);

		if ((category == null) ||
			(Validator.isNotNull(uuid) && uuid.equals(category.getUuid()))) {

			return name;
		}

		name = StringUtil.appendParentheticalSuffix(name, count);

		return getCategoryName(
			uuid, groupId, parentCategoryId, name, vocabularyId, ++count);
	}

	protected Map<Locale, String> getCategoryTitleMap(
			long groupId, AssetCategory category, String name)
		throws PortalException {

		Map<Locale, String> titleMap = category.getTitleMap();

		if (titleMap == null) {
			titleMap = new HashMap<>();
		}

		titleMap.put(PortalUtil.getSiteDefaultLocale(groupId), name);

		return titleMap;
	}

	@Reference(unbind = "-")
	protected void setAssetCategoryLocalService(
		AssetCategoryLocalService assetCategoryLocalService) {

		_assetCategoryLocalService = assetCategoryLocalService;
	}

	@Reference(unbind = "-")
	protected void setAssetCategoryPropertyLocalService(
		AssetCategoryPropertyLocalService assetCategoryPropertyLocalService) {

		_assetCategoryPropertyLocalService = assetCategoryPropertyLocalService;
	}

	@Reference(unbind = "-")
	protected void setAssetVocabularyLocalService(
		AssetVocabularyLocalService assetVocabularyLocalService) {

		_assetVocabularyLocalService = assetVocabularyLocalService;
	}

	private volatile AssetCategoryLocalService _assetCategoryLocalService;
	private volatile AssetCategoryPropertyLocalService
		_assetCategoryPropertyLocalService;
	private volatile AssetVocabularyLocalService _assetVocabularyLocalService;

}
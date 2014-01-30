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

package com.liferay.portlet.asset.lar;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetCategoryConstants;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;

/**
 * @author Zsolt Berentey
 * @author Gergely Mathe
 */
public class AssetCategoryStagedModelDataHandler
	extends BaseStagedModelDataHandler<AssetCategory> {

	public static final String[] CLASS_NAMES = {AssetCategory.class.getName()};

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws SystemException {

		AssetCategory category =
			AssetCategoryLocalServiceUtil.fetchAssetCategoryByUuidAndGroupId(
				uuid, groupId);

		if (category != null) {
			AssetCategoryLocalServiceUtil.deleteAssetCategory(category);
		}
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(AssetCategory category) {
		return category.getTitleCurrentValue();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, AssetCategory category)
		throws Exception {

		if (category.getParentCategoryId() !=
				AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

			AssetCategory parentCategory =
				AssetCategoryLocalServiceUtil.fetchAssetCategory(
					category.getParentCategoryId());

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, category, parentCategory,
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}
		else {
			AssetVocabulary assetVocabulary =
				AssetVocabularyLocalServiceUtil.fetchAssetVocabulary(
					category.getVocabularyId());

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, category, assetVocabulary,
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}

		exportAssetVocabulary(
			portletDataContext, assetVocabulariesElement,
			assetCategory.getVocabularyId());

		if (assetCategory.getParentCategoryId() !=
				AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

			exportAssetCategory(
				portletDataContext, assetVocabulariesElement,
				assetCategoriesElement, assetCategory.getParentCategoryId());
		}

		String path = getAssetCategoryPath(
			portletDataContext, assetCategory.getCategoryId());

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Element assetCategoryElement = assetCategoriesElement.addElement(
			"category");

		assetCategoryElement.addAttribute("path", path);

		assetCategory.setUserUuid(assetCategory.getUserUuid());

		portletDataContext.addZipEntry(path, assetCategory);

		List<AssetCategoryProperty> assetCategoryProperties =
			AssetCategoryPropertyLocalServiceUtil.getCategoryProperties(
				assetCategory.getCategoryId());

		for (AssetCategoryProperty assetCategoryProperty :
				assetCategoryProperties) {

			Element propertyElement = assetCategoryElement.addElement(
				"property");

			propertyElement.addAttribute(
				"userUuid", assetCategoryProperty.getUserUuid());
			propertyElement.addAttribute("key", assetCategoryProperty.getKey());
			propertyElement.addAttribute(
				"value", assetCategoryProperty.getValue());
		}

		portletDataContext.addPermissions(
			AssetCategory.class, assetCategory.getCategoryId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, AssetCategory category)
		throws Exception {

		if (category.getParentCategoryId() !=
				AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

			StagedModelDataHandlerUtil.importReferenceStagedModel(
				portletDataContext, category, AssetCategory.class,
				category.getParentCategoryId());
		}
		else {
			StagedModelDataHandlerUtil.importReferenceStagedModel(
				portletDataContext, category, AssetVocabulary.class,
				category.getVocabularyId());
		}

		long userId = portletDataContext.getUserId(assetCategory.getUserUuid());
		long groupId = portletDataContext.getGroupId();
		long assetVocabularyId = MapUtil.getLong(
			assetVocabularyPKs, assetCategory.getVocabularyId(),
			assetCategory.getVocabularyId());
		long parentAssetCategoryId = MapUtil.getLong(
			assetCategoryPKs, assetCategory.getParentCategoryId(),
			assetCategory.getParentCategoryId());

		if ((parentAssetCategoryId !=
				AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) &&
			(parentAssetCategoryId == assetCategory.getParentCategoryId())) {

			String path = getAssetCategoryPath(
				portletDataContext, parentAssetCategoryId);

			AssetCategory parentAssetCategory =
				(AssetCategory)portletDataContext.getZipEntryAsObject(path);

			Node parentCategoryNode =
				assetCategoryElement.getParent().selectSingleNode(
					"./category[@path='" + path + "']");

			if (parentCategoryNode != null) {
				importAssetCategory(
					portletDataContext, assetVocabularyPKs, assetCategoryPKs,
					assetCategoryUuids, (Element)parentCategoryNode,
					parentAssetCategory);

				parentAssetCategoryId = MapUtil.getLong(
					assetCategoryPKs, assetCategory.getParentCategoryId(),
					assetCategory.getParentCategoryId());
			}
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCreateDate(assetCategory.getCreateDate());
		serviceContext.setModifiedDate(assetCategory.getModifiedDate());
		serviceContext.setScopeGroupId(portletDataContext.getScopeGroupId());

		AssetCategory importedAssetCategory = null;

		if ((parentAssetCategoryId !=
				AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) &&
			(AssetCategoryUtil.fetchByPrimaryKey(parentAssetCategoryId) ==
				null)) {

			_log.error(
				"Could not find the parent category for category " +
					assetCategory.getCategoryId());

			return;
		}

		List<Element> propertyElements = assetCategoryElement.elements(
			"property");

		String[] properties = new String[propertyElements.size()];

		for (int i = 0; i < propertyElements.size(); i++) {
			Element propertyElement = propertyElements.get(i);

			String key = propertyElement.attributeValue("key");
			String value = propertyElement.attributeValue("value");

			properties[i] = key.concat(
				AssetCategoryConstants.PROPERTY_KEY_VALUE_SEPARATOR).concat(
					value);
		}

		AssetCategory existingAssetCategory = AssetCategoryUtil.fetchByUUID_G(
			assetCategory.getUuid(), groupId);

		if (existingAssetCategory == null) {
			existingAssetCategory = AssetCategoryUtil.fetchByUUID_G(
				assetCategory.getUuid(),
				portletDataContext.getCompanyGroupId());
		}

		if (existingAssetCategory == null) {
			String name = getAssetCategoryName(
				null, groupId, parentAssetCategoryId, assetCategory.getName(),
				assetCategory.getVocabularyId(), 2);

			serviceContext.setUuid(assetCategory.getUuid());

			importedAssetCategory =
				AssetCategoryLocalServiceUtil.addCategory(
					userId, parentAssetCategoryId,
					getAssetCategoryTitleMap(groupId, assetCategory, name),
					assetCategory.getDescriptionMap(), assetVocabularyId,
					properties, serviceContext);
		}
		else if (portletDataContext.isCompanyStagedGroupedModel(
					existingAssetCategory)) {

			return;
		}
		else {
			String name = getAssetCategoryName(
				assetCategory.getUuid(), groupId, parentAssetCategoryId,
				assetCategory.getName(), assetCategory.getVocabularyId(), 2);

			importedAssetCategory =
				AssetCategoryLocalServiceUtil.updateCategory(
					userId, existingAssetCategory.getCategoryId(),
					parentAssetCategoryId,
					getAssetCategoryTitleMap(groupId, assetCategory, name),
					assetCategory.getDescriptionMap(), assetVocabularyId,
					properties, serviceContext);
		}

		assetCategoryPKs.put(
			assetCategory.getCategoryId(),
			importedAssetCategory.getCategoryId());

		assetCategoryUuids.put(
			assetCategory.getUuid(), importedAssetCategory.getUuid());

		portletDataContext.importPermissions(
			AssetCategory.class, assetCategory.getCategoryId(),
			importedAssetCategory.getCategoryId());
	}

	protected String getAssetCategoryName(
			String uuid, long groupId, long parentCategoryId, String name,
			long vocabularyId, int count)
		throws Exception {

		AssetCategory assetCategory = AssetCategoryUtil.fetchByG_P_N_V_First(
			groupId, parentCategoryId, name, vocabularyId, null);

		if ((assetCategory == null) ||
			(Validator.isNotNull(uuid) &&
			 uuid.equals(assetCategory.getUuid()))) {

			return name;
		}

		name = StringUtil.appendParentheticalSuffix(name, count);

		return getAssetCategoryName(
			uuid, groupId, parentCategoryId, name, vocabularyId, ++count);
	}

	protected Map<Locale, String> getAssetCategoryTitleMap(
			long groupId, AssetCategory assetCategory, String name)
		throws PortalException, SystemException {

		Map<Locale, String> titleMap = assetCategory.getTitleMap();

		if (titleMap == null) {
			titleMap = new HashMap<Locale, String>();
		}

		titleMap.put(PortalUtil.getSiteDefaultLocale(groupId), name);

		return titleMap;
	}

	@Override
	protected boolean validateMissingReference(
			String uuid, long companyId, long groupId)
		throws Exception {

		AssetCategory category =
			AssetCategoryLocalServiceUtil.fetchAssetCategoryByUuidAndGroupId(
				uuid, groupId);

		if (category == null) {
			return false;
		}

		return true;
	}

}
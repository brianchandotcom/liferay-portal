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

package com.liferay.asset.categories.internal.service;

import com.liferay.asset.category.property.model.AssetCategoryProperty;
import com.liferay.asset.category.property.service.AssetCategoryPropertyLocalService;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceWrapper;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class AssetCategoryPropertyAssetCategoryLocalServiceWrapper
	extends AssetCategoryLocalServiceWrapper {

	public AssetCategoryPropertyAssetCategoryLocalServiceWrapper() {
		super(null);
	}

	public AssetCategoryPropertyAssetCategoryLocalServiceWrapper(
		AssetCategoryLocalService assetCategoryLocalService) {

		super(assetCategoryLocalService);
	}

	@Override
	public AssetCategory addCategory(
			long userId, long groupId, long parentCategoryId,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			long vocabularyId, String[] categoryProperties,
			ServiceContext serviceContext)
		throws PortalException {

		AssetCategory assetCategory = super.addCategory(
			userId, groupId, parentCategoryId, titleMap, descriptionMap,
			vocabularyId, categoryProperties, serviceContext);

		if (categoryProperties == null) {
			return assetCategory;
		}

		// Properties

		for (String categoryProperty : categoryProperties) {
			String[] categoryPropertyArray = StringUtil.split(
				categoryProperty,
				AssetCategoryConstants.PROPERTY_KEY_VALUE_SEPARATOR);

			if (categoryPropertyArray.length <= 1) {
				categoryPropertyArray = StringUtil.split(
					categoryProperty, CharPool.COLON);
			}

			String key = StringPool.BLANK;
			String value = StringPool.BLANK;

			if (categoryPropertyArray.length > 1) {
				key = GetterUtil.getString(categoryPropertyArray[0]);
				value = GetterUtil.getString(categoryPropertyArray[1]);
			}

			if (Validator.isNotNull(key)) {
				_assetCategoryPropertyLocalService.addCategoryProperty(
					userId, assetCategory.getCategoryId(), key, value);
			}
		}

		return assetCategory;
	}

	@Override
	public AssetCategory deleteCategory(
			AssetCategory category, boolean skipRebuildTree)
		throws PortalException {

		_assetCategoryPropertyLocalService.deleteCategoryProperties(
			category.getCategoryId());

		return super.deleteCategory(category, skipRebuildTree);
	}

	@Override
	public AssetCategory mergeCategories(long fromCategoryId, long toCategoryId)
		throws PortalException {

		List<AssetCategoryProperty> categoryProperties =
			_assetCategoryPropertyLocalService.getCategoryProperties(
				fromCategoryId);

		for (AssetCategoryProperty fromCategoryProperty : categoryProperties) {
			AssetCategoryProperty toCategoryProperty =
				_assetCategoryPropertyLocalService.fetchCategoryProperty(
					toCategoryId, fromCategoryProperty.getKey());

			if (toCategoryProperty == null) {
				fromCategoryProperty.setCategoryId(toCategoryId);

				_assetCategoryPropertyLocalService.updateAssetCategoryProperty(
					fromCategoryProperty);
			}
		}

		return super.mergeCategories(fromCategoryId, toCategoryId);
	}

	@Override
	public AssetCategory updateCategory(
			long userId, long categoryId, long parentCategoryId,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			long vocabularyId, String[] categoryProperties,
			ServiceContext serviceContext)
		throws PortalException {

		List<AssetCategoryProperty> oldCategoryProperties =
			_assetCategoryPropertyLocalService.getCategoryProperties(
				categoryId);

		oldCategoryProperties = ListUtil.copy(oldCategoryProperties);

		if (categoryProperties != null) {
			for (String categoryProperty : categoryProperties) {
				String[] categoryPropertyArray = StringUtil.split(
					categoryProperty,
					AssetCategoryConstants.PROPERTY_KEY_VALUE_SEPARATOR);

				if (categoryPropertyArray.length <= 1) {
					categoryPropertyArray = StringUtil.split(
						categoryProperty, CharPool.COLON);
				}

				String key = StringPool.BLANK;

				if (categoryPropertyArray.length > 0) {
					key = GetterUtil.getString(categoryPropertyArray[0]);
				}

				String value = StringPool.BLANK;

				if (categoryPropertyArray.length > 1) {
					value = GetterUtil.getString(categoryPropertyArray[1]);
				}

				if (Validator.isNotNull(key)) {
					boolean addCategoryProperty = true;

					AssetCategoryProperty oldCategoryProperty = null;

					Iterator<AssetCategoryProperty> iterator =
						oldCategoryProperties.iterator();

					while (iterator.hasNext()) {
						oldCategoryProperty = iterator.next();

						if ((categoryId ==
								oldCategoryProperty.getCategoryId()) &&
							key.equals(oldCategoryProperty.getKey())) {

							addCategoryProperty = false;

							if (!value.equals(oldCategoryProperty.getValue())) {
								_assetCategoryPropertyLocalService.
									updateCategoryProperty(
										userId,
										oldCategoryProperty.
											getCategoryPropertyId(),
										key, value);
							}

							iterator.remove();

							break;
						}
					}

					if (addCategoryProperty) {
						_assetCategoryPropertyLocalService.addCategoryProperty(
							userId, categoryId, key, value);
					}
				}
			}
		}

		for (AssetCategoryProperty categoryProperty : oldCategoryProperties) {
			_assetCategoryPropertyLocalService.deleteAssetCategoryProperty(
				categoryProperty);
		}

		return super.updateCategory(
			userId, categoryId, parentCategoryId, titleMap, descriptionMap,
			vocabularyId, categoryProperties, serviceContext);
	}

	@Reference
	private AssetCategoryPropertyLocalService
		_assetCategoryPropertyLocalService;

}
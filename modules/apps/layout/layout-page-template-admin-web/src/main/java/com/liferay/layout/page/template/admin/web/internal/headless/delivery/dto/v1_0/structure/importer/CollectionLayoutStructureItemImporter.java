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

package com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.structure.importer;

import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.headless.delivery.dto.v1_0.CollectionConfig;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.info.list.provider.InfoListProvider;
import com.liferay.info.list.provider.InfoListProviderTracker;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.layout.page.template.admin.web.internal.util.GenericsUtil;
import com.liferay.layout.util.structure.CollectionLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = LayoutStructureItemImporter.class)
public class CollectionLayoutStructureItemImporter
	extends BaseLayoutStructureItemImporter
	implements LayoutStructureItemImporter {

	@Override
	public LayoutStructureItem addLayoutStructureItem(
			Layout layout, LayoutStructure layoutStructure,
			PageElement pageElement, String parentItemId, int position)
		throws Exception {

		CollectionLayoutStructureItem collectionLayoutStructureItem =
			(CollectionLayoutStructureItem)
				layoutStructure.addCollectionLayoutStructureItem(
					parentItemId, position);

		Map<String, Object> definitionMap = getDefinitionMap(
			pageElement.getDefinition());

		if (definitionMap != null) {
			Map<String, Object> collectionConfig =
				(Map<String, Object>)definitionMap.get("collectionConfig");

			if (collectionConfig != null) {
				collectionLayoutStructureItem.setCollectionJSONObject(
					_getCollectionConfigAsJSONObject(collectionConfig));
			}

			collectionLayoutStructureItem.setNumberOfColumns(
				(Integer)definitionMap.get("numberOfColumns"));
			collectionLayoutStructureItem.setNumberOfColumns(
				(Integer)definitionMap.get("numberOfItems"));
		}

		return collectionLayoutStructureItem;
	}

	@Override
	public PageElement.Type getPageElementType() {
		return PageElement.Type.COLLECTION;
	}

	private JSONObject _getCollectionConfigAsJSONObject(
		Map<String, Object> collectionConfig) {

		String type = (String)collectionConfig.get("collectionType");

		if (Validator.isNull(type)) {
			return null;
		}

		Map<String, Object> collectionReference =
			(Map<String, Object>)collectionConfig.get("collectionReference");

		if (MapUtil.isEmpty(collectionConfig)) {
			return null;
		}

		if (Objects.equals(
				type, CollectionConfig.CollectionType.COLLECTION.getValue())) {

			return _getCollectionJSONObject(collectionReference);
		}
		else if (Objects.equals(
					type,
					CollectionConfig.CollectionType.COLLECTION_PROVIDER.
						getValue())) {

			return _getCollectionProviderJSONObject(collectionReference);
		}

		return null;
	}

	private JSONObject _getCollectionJSONObject(
		Map<String, Object> collectionReference) {

		Long classPK = _toClassPK(
			String.valueOf(collectionReference.get("classPK")));

		if (classPK == null) {
			return null;
		}

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.fetchAssetListEntry(classPK);

		if (assetListEntry == null) {
			return null;
		}

		return JSONUtil.put(
			"classNameId",
			_portal.getClassNameId(AssetListEntry.class.getName())
		).put(
			"classPK", String.valueOf(classPK)
		).put(
			"itemSubtype", assetListEntry.getAssetEntrySubtype()
		).put(
			"itemType", assetListEntry.getAssetEntryType()
		).put(
			"title", assetListEntry.getTitle()
		).put(
			"type", InfoListItemSelectorReturnType.class.getName()
		);
	}

	private JSONObject _getCollectionProviderJSONObject(
		Map<String, Object> collectionReference) {

		String className = (String)collectionReference.get("className");

		InfoListProvider infoListProvider =
			_infoListProviderTracker.getInfoListProvider(className);

		if (infoListProvider == null) {
			return null;
		}

		return JSONUtil.put(
			"itemType", GenericsUtil.getItemClassName(infoListProvider)
		).put(
			"key", className
		).put(
			"title", infoListProvider.getLabel(LocaleUtil.getDefault())
		).put(
			"type", InfoListProviderItemSelectorReturnType.class.getName()
		);
	}

	private Long _toClassPK(String classPKString) {
		if (Validator.isNull(classPKString)) {
			return null;
		}

		Long classPK = null;

		try {
			classPK = Long.parseLong(classPKString);
		}
		catch (NumberFormatException numberFormatException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					String.format(
						"Class PK could not be set since class PK %s could " +
							"not be parsed to a long",
						classPKString),
					numberFormatException);
			}

			return null;
		}

		return classPK;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CollectionLayoutStructureItemImporter.class);

	@Reference
	private AssetListEntryLocalService _assetListEntryLocalService;

	@Reference
	private InfoListProviderTracker _infoListProviderTracker;

	@Reference
	private Portal _portal;

}
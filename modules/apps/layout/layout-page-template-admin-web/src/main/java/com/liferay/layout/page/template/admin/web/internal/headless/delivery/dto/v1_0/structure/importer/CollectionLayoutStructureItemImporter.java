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
import com.liferay.headless.delivery.dto.v1_0.PageCollectionDefinition;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.info.collection.provider.RelatedInfoItemCollectionProvider;
import com.liferay.info.collection.provider.SingleFormVariationInfoCollectionProvider;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.layout.util.structure.CollectionStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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
			LayoutStructure layoutStructure,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext,
			PageElement pageElement, Set<String> warningMessages)
		throws Exception {

		CollectionStyledLayoutStructureItem
			collectionStyledLayoutStructureItem =
				(CollectionStyledLayoutStructureItem)
					layoutStructure.addCollectionStyledLayoutStructureItem(
						layoutStructureItemImporterContext.getParentItemId(),
						layoutStructureItemImporterContext.getPosition());

		Map<String, Object> definitionMap = getDefinitionMap(
			pageElement.getDefinition());

		if (definitionMap != null) {
			Map<String, Object> collectionConfig =
				(Map<String, Object>)definitionMap.get("collectionConfig");

			if (collectionConfig != null) {
				collectionStyledLayoutStructureItem.setCollectionJSONObject(
					_getCollectionConfigAsJSONObject(collectionConfig));
			}

			collectionStyledLayoutStructureItem.setDisplayAllItems(
				(Boolean)definitionMap.get("displayAllItems"));

			Boolean displayAllPages = (Boolean)definitionMap.get(
				"displayAllPages");
			Boolean showAllItems = (Boolean)definitionMap.get("showAllItems");

			if (displayAllPages == null) {
				displayAllPages = showAllItems;
			}

			collectionStyledLayoutStructureItem.setDisplayAllPages(
				displayAllPages);

			collectionStyledLayoutStructureItem.setListItemStyle(
				(String)definitionMap.get("listItemStyle"));
			collectionStyledLayoutStructureItem.setListStyle(
				(String)definitionMap.get("listStyle"));
			collectionStyledLayoutStructureItem.setNumberOfColumns(
				(Integer)definitionMap.get("numberOfColumns"));

			Integer numberOfItems = (Integer)definitionMap.get("numberOfItems");

			collectionStyledLayoutStructureItem.setNumberOfItems(numberOfItems);

			Integer numberOfItemsPerPage = (Integer)definitionMap.get(
				"numberOfItemsPerPage");

			if (numberOfItemsPerPage != null) {
				collectionStyledLayoutStructureItem.setNumberOfItemsPerPage(
					numberOfItemsPerPage);
			}

			Integer numberOfPages = (Integer)definitionMap.get("numberOfPages");

			if (numberOfPages == null) {
				if ((numberOfItemsPerPage != null) &&
					(numberOfItemsPerPage > 0)) {

					collectionStyledLayoutStructureItem.setNumberOfPages(
						(int)Math.ceil(
							numberOfItems / (double)numberOfItemsPerPage));
				}
			}
			else {
				collectionStyledLayoutStructureItem.setNumberOfPages(
					numberOfPages);
			}

			collectionStyledLayoutStructureItem.setPaginationType(
				_toPaginationType((String)definitionMap.get("paginationType")));

			collectionStyledLayoutStructureItem.setShowAllItems(showAllItems);

			collectionStyledLayoutStructureItem.setTemplateKey(
				(String)definitionMap.get("templateKey"));

			Map<String, Object> fragmentStyleMap =
				(Map<String, Object>)definitionMap.get("fragmentStyle");

			if (fragmentStyleMap != null) {
				JSONObject jsonObject = JSONUtil.put(
					"styles",
					toStylesJSONObject(
						layoutStructureItemImporterContext, fragmentStyleMap));

				collectionStyledLayoutStructureItem.updateItemConfig(
					jsonObject);
			}

			if (definitionMap.containsKey("fragmentViewports")) {
				List<Map<String, Object>> fragmentViewports =
					(List<Map<String, Object>>)definitionMap.get(
						"fragmentViewports");

				for (Map<String, Object> fragmentViewport : fragmentViewports) {
					JSONObject jsonObject = JSONUtil.put(
						(String)fragmentViewport.get("id"),
						toFragmentViewportStylesJSONObject(fragmentViewport));

					collectionStyledLayoutStructureItem.updateItemConfig(
						jsonObject);
				}
			}
		}

		return collectionStyledLayoutStructureItem;
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
			"classNameId", portal.getClassNameId(AssetListEntry.class.getName())
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

		InfoCollectionProvider<?> infoCollectionProvider =
			_infoItemServiceTracker.getInfoItemService(
				InfoCollectionProvider.class, className);

		if (infoCollectionProvider == null) {
			infoCollectionProvider = _infoItemServiceTracker.getInfoItemService(
				RelatedInfoItemCollectionProvider.class, className);
		}

		if (infoCollectionProvider == null) {
			return null;
		}

		return JSONUtil.put(
			"itemSubtype", _getItemSubtype(infoCollectionProvider)
		).put(
			"itemType", infoCollectionProvider.getCollectionItemClassName()
		).put(
			"key", className
		).put(
			"title", infoCollectionProvider.getLabel(LocaleUtil.getDefault())
		).put(
			"type", InfoListProviderItemSelectorReturnType.class.getName()
		);
	}

	private String _getItemSubtype(
		InfoCollectionProvider<?> infoCollectionProvider) {

		if (infoCollectionProvider instanceof
				SingleFormVariationInfoCollectionProvider) {

			SingleFormVariationInfoCollectionProvider<?>
				singleFormVariationInfoCollectionProvider =
					(SingleFormVariationInfoCollectionProvider<?>)
						infoCollectionProvider;

			return singleFormVariationInfoCollectionProvider.
				getFormVariationKey();
		}

		return null;
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
						"Unable to parse class PK %s to a long", classPKString),
					numberFormatException);
			}

			return null;
		}

		return classPK;
	}

	private String _toPaginationType(String paginationType) {
		if (Validator.isNull(paginationType) ||
			Objects.equals(
				paginationType,
				PageCollectionDefinition.PaginationType.NONE.getValue())) {

			return "none";
		}

		if (Objects.equals(
				paginationType,
				PageCollectionDefinition.PaginationType.NUMERIC.getValue()) ||
			Objects.equals(
				paginationType,
				PageCollectionDefinition.PaginationType.REGULAR.getValue())) {

			return "numeric";
		}

		if (Objects.equals(
				paginationType,
				PageCollectionDefinition.PaginationType.SIMPLE.getValue())) {

			return "simple";
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CollectionLayoutStructureItemImporter.class);

	@Reference
	private AssetListEntryLocalService _assetListEntryLocalService;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

}
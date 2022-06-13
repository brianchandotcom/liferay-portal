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

package com.liferay.headless.delivery.internal.dto.v1_0.mapper;

import com.liferay.headless.delivery.dto.v1_0.ClassNameReference;
import com.liferay.headless.delivery.dto.v1_0.ClassPKReference;
import com.liferay.headless.delivery.dto.v1_0.CollectionConfig;
import com.liferay.headless.delivery.dto.v1_0.CollectionViewport;
import com.liferay.headless.delivery.dto.v1_0.CollectionViewportDefinition;
import com.liferay.headless.delivery.dto.v1_0.PageCollectionDefinition;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.layout.responsive.ViewportSize;
import com.liferay.layout.util.structure.CollectionStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 */
@Component(service = LayoutStructureItemMapper.class)
public class CollectionLayoutStructureItemMapper
	extends BaseStyledLayoutStructureItemMapper {

	@Override
	public String getClassName() {
		return CollectionStyledLayoutStructureItem.class.getName();
	}

	@Override
	public PageElement getPageElement(
		long groupId, LayoutStructureItem layoutStructureItem,
		boolean saveInlineContent, boolean saveMappingConfiguration) {

		CollectionStyledLayoutStructureItem
			collectionStyledLayoutStructureItem =
				(CollectionStyledLayoutStructureItem)layoutStructureItem;

		return new PageElement() {
			{
				definition = new PageCollectionDefinition() {
					{
						collectionConfig = _getCollectionConfig(
							collectionStyledLayoutStructureItem);
						displayAllItems =
							collectionStyledLayoutStructureItem.
								isDisplayAllItems();
						displayAllPages =
							collectionStyledLayoutStructureItem.
								isDisplayAllPages();
						listItemStyle =
							collectionStyledLayoutStructureItem.
								getListItemStyle();
						listStyle =
							collectionStyledLayoutStructureItem.getListStyle();
						numberOfColumns =
							collectionStyledLayoutStructureItem.
								getNumberOfColumns();
						numberOfItems =
							collectionStyledLayoutStructureItem.
								getNumberOfItems();
						numberOfItemsPerPage =
							collectionStyledLayoutStructureItem.
								getNumberOfItemsPerPage();
						numberOfPages =
							collectionStyledLayoutStructureItem.
								getNumberOfPages();
						paginationType = _getPaginationType(
							collectionStyledLayoutStructureItem.
								getPaginationType());
						showAllItems =
							collectionStyledLayoutStructureItem.
								isShowAllItems();
						templateKey =
							collectionStyledLayoutStructureItem.
								getTemplateKey();

						setCollectionViewports(
							_getCollectionViewports(
								collectionStyledLayoutStructureItem));
						setFragmentViewports(
							() -> getFragmentViewPorts(
								collectionStyledLayoutStructureItem.
									getItemConfigJSONObject()));
					}
				};
				type = Type.COLLECTION;
			}
		};
	}

	private CollectionConfig _getCollectionConfig(
		CollectionStyledLayoutStructureItem
			collectionStyledLayoutStructureItem) {

		JSONObject jsonObject =
			collectionStyledLayoutStructureItem.getCollectionJSONObject();

		if (jsonObject == null) {
			return null;
		}

		String type = jsonObject.getString("type");

		if (Validator.isNull(type)) {
			return null;
		}

		if (Objects.equals(
				type, InfoListItemSelectorReturnType.class.getName())) {

			return new CollectionConfig() {
				{
					collectionReference = new ClassPKReference() {
						{
							className = portal.getClassName(
								jsonObject.getInt("classNameId"));
							classPK = jsonObject.getLong("classPK");
						}
					};
					collectionType = CollectionType.COLLECTION;
				}
			};
		}
		else if (Objects.equals(
					type,
					InfoListProviderItemSelectorReturnType.class.getName())) {

			return new CollectionConfig() {
				{
					collectionReference = new ClassNameReference() {
						{
							className = jsonObject.getString("key");
						}
					};
					collectionType = CollectionType.COLLECTION_PROVIDER;
				}
			};
		}

		return null;
	}

	private CollectionViewport[] _getCollectionViewports(
		CollectionStyledLayoutStructureItem
			collectionStyledLayoutStructureItem) {

		Map<String, JSONObject> collectionViewportConfigurationJSONObjects =
			collectionStyledLayoutStructureItem.
				getViewportConfigurationJSONObjects();

		if (MapUtil.isEmpty(collectionViewportConfigurationJSONObjects)) {
			return null;
		}

		List<CollectionViewport> collectionViewports = new ArrayList<>();

		collectionViewports.add(
			new CollectionViewport() {
				{
					collectionViewportDefinition =
						_toCollectionViewportDefinition(
							collectionViewportConfigurationJSONObjects,
							ViewportSize.MOBILE_LANDSCAPE);
					id = ViewportSize.MOBILE_LANDSCAPE.getViewportSizeId();
				}
			});
		collectionViewports.add(
			new CollectionViewport() {
				{
					collectionViewportDefinition =
						_toCollectionViewportDefinition(
							collectionViewportConfigurationJSONObjects,
							ViewportSize.PORTRAIT_MOBILE);
					id = ViewportSize.PORTRAIT_MOBILE.getViewportSizeId();
				}
			});
		collectionViewports.add(
			new CollectionViewport() {
				{
					collectionViewportDefinition =
						_toCollectionViewportDefinition(
							collectionViewportConfigurationJSONObjects,
							ViewportSize.TABLET);
					id = ViewportSize.TABLET.getViewportSizeId();
				}
			});

		return collectionViewports.toArray(new CollectionViewport[0]);
	}

	private PageCollectionDefinition.PaginationType _getPaginationType(
		String paginationType) {

		if (Validator.isNull(paginationType)) {
			return null;
		}

		if (Objects.equals(paginationType, "none")) {
			return PageCollectionDefinition.PaginationType.NONE;
		}

		if (Objects.equals(paginationType, "numeric") ||
			Objects.equals(paginationType, "regular")) {

			return PageCollectionDefinition.PaginationType.NUMERIC;
		}

		if (Objects.equals(paginationType, "simple")) {
			return PageCollectionDefinition.PaginationType.SIMPLE;
		}

		return null;
	}

	private CollectionViewportDefinition _toCollectionViewportDefinition(
		Map<String, JSONObject> collectionViewportConfigurationJSONObjects,
		ViewportSize viewportSize) {

		if (!collectionViewportConfigurationJSONObjects.containsKey(
				viewportSize.getViewportSizeId())) {

			return null;
		}

		JSONObject jsonObject = collectionViewportConfigurationJSONObjects.get(
			viewportSize.getViewportSizeId());

		return new CollectionViewportDefinition() {
			{
				setNumberOfColumns(
					() -> {
						if (!jsonObject.has("numberOfColumns")) {
							return null;
						}

						return jsonObject.getInt("numberOfColumns");
					});
			}
		};
	}

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.dto.v1_0.mapper;

import com.liferay.headless.delivery.dto.v1_0.ClassNameReference;
import com.liferay.headless.delivery.dto.v1_0.ClassPKReference;
import com.liferay.headless.delivery.dto.v1_0.CollectionConfig;
import com.liferay.headless.delivery.dto.v1_0.CollectionViewport;
import com.liferay.headless.delivery.dto.v1_0.CollectionViewportDefinition;
import com.liferay.headless.delivery.dto.v1_0.EmptyCollectionConfig;
import com.liferay.headless.delivery.dto.v1_0.Layout;
import com.liferay.headless.delivery.dto.v1_0.PageCollectionDefinition;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.layout.converter.AlignConverter;
import com.liferay.layout.converter.FlexWrapConverter;
import com.liferay.layout.converter.JustifyConverter;
import com.liferay.layout.responsive.ViewportSize;
import com.liferay.layout.util.structure.CollectionStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.collection.EmptyCollectionOptions;
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
						collectionViewports = _getCollectionViewports(
							collectionStyledLayoutStructureItem);
						displayAllItems =
							collectionStyledLayoutStructureItem.
								isDisplayAllItems();
						displayAllPages =
							collectionStyledLayoutStructureItem.
								isDisplayAllPages();
						emptyCollectionConfig = _getEmptyCollectionConfig(
							collectionStyledLayoutStructureItem);
						fragmentViewports = getFragmentViewPorts(
							collectionStyledLayoutStructureItem.
								getItemConfigJSONObject());
						layout = _toLayout(collectionStyledLayoutStructureItem);
						listItemStyle =
							collectionStyledLayoutStructureItem.
								getListItemStyle();
						listStyle =
							collectionStyledLayoutStructureItem.getListStyle();
						name = collectionStyledLayoutStructureItem.getName();
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

	private EmptyCollectionConfig _getEmptyCollectionConfig(
		CollectionStyledLayoutStructureItem
			collectionStyledLayoutStructureItem) {

		EmptyCollectionOptions emptyCollectionOptions =
			collectionStyledLayoutStructureItem.getEmptyCollectionOptions();

		if (emptyCollectionOptions == null) {
			return null;
		}

		return new EmptyCollectionConfig() {
			{
				displayMessage = emptyCollectionOptions.isDisplayMessage();
				message_i18n = emptyCollectionOptions.getMessage();
			}
		};
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

	private Layout _toLayout(
		CollectionStyledLayoutStructureItem
			collectionStyledLayoutStructureItem) {

		String formLayoutAlign = collectionStyledLayoutStructureItem.getAlign();
		String formLayoutFlexWrap =
			collectionStyledLayoutStructureItem.getFlexWrap();
		String formLayoutJustify =
			collectionStyledLayoutStructureItem.getJustify();

		if (Validator.isNull(formLayoutAlign) &&
			Validator.isNull(formLayoutFlexWrap) &&
			Validator.isNull(formLayoutJustify)) {

			return null;
		}

		return new Layout() {
			{
				setAlign(
					() -> {
						if (Validator.isNull(formLayoutAlign)) {
							return null;
						}

						return Align.create(
							AlignConverter.convertToExternalValue(
								formLayoutAlign));
					});
				setFlexWrap(
					() -> {
						if (Validator.isNull(formLayoutFlexWrap)) {
							return null;
						}

						return FlexWrap.create(
							FlexWrapConverter.convertToExternalValue(
								formLayoutFlexWrap));
					});
				setJustify(
					() -> {
						if (Validator.isNull(formLayoutJustify)) {
							return null;
						}

						return Justify.create(
							JustifyConverter.convertToExternalValue(
								formLayoutJustify));
					});
			}
		};
	}

}
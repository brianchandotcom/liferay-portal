/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.converter;

import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.headless.admin.site.dto.v1_0.ClassNameReference;
import com.liferay.headless.admin.site.dto.v1_0.CollectionDisplayListStyle;
import com.liferay.headless.admin.site.dto.v1_0.CollectionDisplayPageElementDefinition;
import com.liferay.headless.admin.site.dto.v1_0.CollectionDisplayViewport;
import com.liferay.headless.admin.site.dto.v1_0.CollectionDisplayViewportDefinition;
import com.liferay.headless.admin.site.dto.v1_0.CollectionItemExternalReference;
import com.liferay.headless.admin.site.dto.v1_0.CollectionReference;
import com.liferay.headless.admin.site.dto.v1_0.EmptyCollectionConfig;
import com.liferay.headless.admin.site.dto.v1_0.ListStyle;
import com.liferay.headless.admin.site.dto.v1_0.ListStyleDefinition;
import com.liferay.headless.admin.site.dto.v1_0.PageElementDefinition;
import com.liferay.headless.admin.site.dto.v1_0.Scope;
import com.liferay.headless.admin.site.dto.v1_0.TemplateListStyle;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.CollectionDisplayListStyleUtil;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.ItemScopeUtil;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.ViewportIdUtil;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.layout.converter.AlignConverter;
import com.liferay.layout.converter.FlexWrapConverter;
import com.liferay.layout.converter.JustifyConverter;
import com.liferay.layout.converter.VerticalAlignmentConverter;
import com.liferay.layout.util.CollectionPaginationUtil;
import com.liferay.layout.util.structure.CollectionStyledLayoutStructureItem;
import com.liferay.layout.util.structure.collection.EmptyCollectionOptions;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "dto.class.name=com.liferay.layout.util.structure.CollectionStyledLayoutStructureItem",
	service = DTOConverter.class
)
public class CollectionDisplayPageElementDefinitionDTOConverter
	implements DTOConverter
		<CollectionStyledLayoutStructureItem,
		 CollectionDisplayPageElementDefinition> {

	@Override
	public String getContentType() {
		return CollectionDisplayPageElementDefinition.class.getSimpleName();
	}

	@Override
	public CollectionDisplayPageElementDefinition toDTO(
			DTOConverterContext dtoConverterContext,
			CollectionStyledLayoutStructureItem
				collectionStyledLayoutStructureItem)
		throws Exception {

		Long companyId = (Long)dtoConverterContext.getAttribute("companyId");
		Long scopeGroupId = (Long)dtoConverterContext.getAttribute(
			"scopeGroupId");

		if ((companyId == null) || (scopeGroupId == null)) {
			throw new UnsupportedOperationException();
		}

		return new CollectionDisplayPageElementDefinition() {
			{
				setCollectionDisplayListStyle(
					() -> _toCollectionDisplayListStyle(
						collectionStyledLayoutStructureItem));
				setCollectionDisplayViewports(
					() -> _toCollectionDisplayViewports(
						collectionStyledLayoutStructureItem));
				setCollectionReference(
					() -> _toCollectionReference(
						collectionStyledLayoutStructureItem, companyId,
						scopeGroupId));
				setDisplayAllItems(
					collectionStyledLayoutStructureItem::isDisplayAllItems);
				setDisplayAllPages(
					collectionStyledLayoutStructureItem::isDisplayAllPages);
				setEmptyCollectionConfig(
					() -> _toEmptyCollectionOption(
						collectionStyledLayoutStructureItem));
				setHidden(
					() -> _toHidden(
						collectionStyledLayoutStructureItem.
							getStylesJSONObject()));
				setName(collectionStyledLayoutStructureItem::getName);
				setNumberOfItems(
					collectionStyledLayoutStructureItem::getNumberOfItems);
				setNumberOfItemsPerPage(
					collectionStyledLayoutStructureItem::
						getNumberOfItemsPerPage);
				setNumberOfPages(
					collectionStyledLayoutStructureItem::getNumberOfPages);
				setPaginationType(
					() -> {
						String paginationType =
							collectionStyledLayoutStructureItem.
								getPaginationType();

						if (Validator.isNull(paginationType)) {
							return null;
						}

						if (StringUtil.equalsIgnoreCase(
								paginationType,
								CollectionPaginationUtil.
									PAGINATION_TYPE_REGULAR)) {

							paginationType =
								CollectionPaginationUtil.
									PAGINATION_TYPE_NUMERIC;
						}

						return _internalToExternalValuesMap.get(paginationType);
					});
				setType(PageElementDefinition.Type.COLLECTION_DISPLAY);
			}
		};
	}

	private Scope _getItemScope(
			Long companyId, String itemExternalReferenceCode, long scopeGroupId)
		throws PortalException {

		if (Validator.isNull(itemExternalReferenceCode) ||
			(companyId == null)) {

			return null;
		}

		Group group = _groupLocalService.getGroupByExternalReferenceCode(
			itemExternalReferenceCode, companyId);

		if ((group == null) || (group.getGroupId() == scopeGroupId)) {
			return null;
		}

		return new Scope() {
			{
				setExternalReferenceCode(group::getExternalReferenceCode);
				setType(
					() -> {
						if (group.getType() == GroupConstants.TYPE_DEPOT) {
							return Type.ASSET_LIBRARY;
						}

						return Type.SITE;
					});
			}
		};
	}

	private CollectionDisplayListStyle _toCollectionDisplayListStyle(
		CollectionStyledLayoutStructureItem
			collectionStyledLayoutStructureItem) {

		if (Validator.isNull(
				collectionStyledLayoutStructureItem.getListStyle())) {

			return null;
		}

		String listStyle = CollectionDisplayListStyleUtil.toExternalValue(
			collectionStyledLayoutStructureItem.getListStyle());

		if (Validator.isNull(listStyle)) {
			return _toTemplateListStyle(collectionStyledLayoutStructureItem);
		}

		return _toListStyle(collectionStyledLayoutStructureItem);
	}

	private CollectionDisplayViewport _toCollectionDisplayViewport(
		CollectionDisplayViewport.Id collectionDisplayViewportId,
		Map<String, JSONObject>
			collectionDisplayViewportConfigurationJSONObjects) {

		String viewportId = ViewportIdUtil.toInternalValue(
			collectionDisplayViewportId.getValue());

		if (!collectionDisplayViewportConfigurationJSONObjects.containsKey(
				viewportId)) {

			return null;
		}

		JSONObject collectionDisplayViewportConfigurationJSONObject =
			collectionDisplayViewportConfigurationJSONObjects.get(viewportId);

		if (JSONUtil.isEmpty(
				collectionDisplayViewportConfigurationJSONObject)) {

			return null;
		}

		CollectionDisplayViewportDefinition
			collectionDisplayViewportDefinition =
				_toCollectionDisplayViewportDefinition(
					collectionDisplayViewportConfigurationJSONObject);

		if (collectionDisplayViewportDefinition == null) {
			return null;
		}

		CollectionDisplayViewport collectionDisplayViewport =
			new CollectionDisplayViewport();

		collectionDisplayViewport.setCollectionDisplayViewportDefinition(
			() -> collectionDisplayViewportDefinition);
		collectionDisplayViewport.setId(() -> collectionDisplayViewportId);

		return collectionDisplayViewport;
	}

	private CollectionDisplayViewportDefinition
		_toCollectionDisplayViewportDefinition(
			JSONObject collectionDisplayViewportConfigurationJSONObject) {

		String align =
			collectionDisplayViewportConfigurationJSONObject.getString(
				"align", null);
		String flexWrap =
			collectionDisplayViewportConfigurationJSONObject.getString(
				"flexWrap", null);
		String numberOfColumns =
			collectionDisplayViewportConfigurationJSONObject.getString(
				"numberOfColumns", null);

		if ((align == null) && (flexWrap == null) &&
			(numberOfColumns == null) &&
			JSONUtil.isEmpty(
				collectionDisplayViewportConfigurationJSONObject.getJSONObject(
					"styles"))) {

			return null;
		}

		CollectionDisplayViewportDefinition
			collectionDisplayViewportDefinition =
				new CollectionDisplayViewportDefinition();

		collectionDisplayViewportDefinition.setAlign(
			() -> {
				if (Validator.isNull(align)) {
					return null;
				}

				return CollectionDisplayViewportDefinition.Align.create(
					AlignConverter.convertToExternalValue(align));
			});
		collectionDisplayViewportDefinition.setFlexWrap(
			() -> {
				if (Validator.isNull(flexWrap)) {
					return null;
				}

				return CollectionDisplayViewportDefinition.FlexWrap.create(
					FlexWrapConverter.convertToExternalValue(flexWrap));
			});
		collectionDisplayViewportDefinition.setHidden(
			() -> _toHidden(
				collectionDisplayViewportConfigurationJSONObject.getJSONObject(
					"styles")));
		collectionDisplayViewportDefinition.setJustify(
			() -> {
				String justify =
					collectionDisplayViewportConfigurationJSONObject.getString(
						"justify", null);

				if (Validator.isNull(justify)) {
					return null;
				}

				return CollectionDisplayViewportDefinition.Justify.create(
					JustifyConverter.convertToExternalValue(justify));
			});
		collectionDisplayViewportDefinition.setNumberOfColumns(
			() -> {
				if (!collectionDisplayViewportConfigurationJSONObject.has(
						"numberOfColumns")) {

					return null;
				}

				return collectionDisplayViewportConfigurationJSONObject.getInt(
					"numberOfColumns");
			});

		return collectionDisplayViewportDefinition;
	}

	private CollectionDisplayViewport[] _toCollectionDisplayViewports(
		CollectionStyledLayoutStructureItem
			collectionStyledLayoutStructureItem) {

		Map<String, JSONObject>
			collectionDisplayViewportConfigurationJSONObjects =
				collectionStyledLayoutStructureItem.
					getViewportConfigurationJSONObjects();

		if (MapUtil.isEmpty(
				collectionDisplayViewportConfigurationJSONObjects)) {

			return null;
		}

		List<CollectionDisplayViewport> collectionDisplayViewports =
			new ArrayList<>() {
				{
					CollectionDisplayViewport collectionDisplayViewport =
						_toCollectionDisplayViewport(
							CollectionDisplayViewport.Id.LANDSCAPE_MOBILE,
							collectionDisplayViewportConfigurationJSONObjects);

					if (collectionDisplayViewport != null) {
						add(collectionDisplayViewport);
					}

					collectionDisplayViewport = _toCollectionDisplayViewport(
						CollectionDisplayViewport.Id.PORTRAIT_MOBILE,
						collectionDisplayViewportConfigurationJSONObjects);

					if (collectionDisplayViewport != null) {
						add(collectionDisplayViewport);
					}

					collectionDisplayViewport = _toCollectionDisplayViewport(
						CollectionDisplayViewport.Id.TABLET,
						collectionDisplayViewportConfigurationJSONObjects);

					if (collectionDisplayViewport != null) {
						add(collectionDisplayViewport);
					}
				}
			};

		return collectionDisplayViewports.toArray(
			new CollectionDisplayViewport[0]);
	}

	private CollectionItemExternalReference _toCollectionItemExternalReference(
		AssetListEntry assetListEntry, long companyId, JSONObject jsonObject,
		long scopeGroupId) {

		CollectionItemExternalReference collectionItemExternalReference =
			new CollectionItemExternalReference();

		if (assetListEntry != null) {
			collectionItemExternalReference.setExternalReferenceCode(
				assetListEntry::getExternalReferenceCode);
			collectionItemExternalReference.setScope(
				() -> ItemScopeUtil.getItemScope(
					assetListEntry.getGroupId(), scopeGroupId));

			return collectionItemExternalReference;
		}

		String externalReferenceCode = jsonObject.getString(
			"externalReferenceCode");

		if (Validator.isNull(externalReferenceCode)) {
			return null;
		}

		collectionItemExternalReference.setExternalReferenceCode(
			() -> externalReferenceCode);
		collectionItemExternalReference.setScope(
			() -> _getItemScope(
				companyId, jsonObject.getString("scopeExternalReferenceCode"),
				scopeGroupId));

		return collectionItemExternalReference;
	}

	private CollectionReference _toCollectionReference(
		CollectionStyledLayoutStructureItem collectionStyledLayoutStructureItem,
		long companyId, long scopeGroupId) {

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

			return _toCollectionItemExternalReference(
				_assetListEntryLocalService.fetchAssetListEntry(
					jsonObject.getLong("classPK")),
				companyId, jsonObject, scopeGroupId);
		}

		String key = jsonObject.getString("key", null);

		if (Validator.isNull(key)) {
			return null;
		}

		return new ClassNameReference() {
			{
				setClassName(() -> key);
				setCollectionType(CollectionType.COLLECTION_PROVIDER);
			}
		};
	}

	private EmptyCollectionConfig _toEmptyCollectionOption(
		CollectionStyledLayoutStructureItem
			collectionStyledLayoutStructureItem) {

		EmptyCollectionOptions emptyCollectionOptions =
			collectionStyledLayoutStructureItem.getEmptyCollectionOptions();

		if (emptyCollectionOptions == null) {
			return null;
		}

		return new EmptyCollectionConfig() {
			{
				setDisplayMessage(emptyCollectionOptions::isDisplayMessage);
				setMessage_i18n(emptyCollectionOptions::getMessage);
			}
		};
	}

	private boolean _toHidden(JSONObject stylesJSONObject) {
		if (JSONUtil.isEmpty(stylesJSONObject)) {
			return false;
		}

		String display = stylesJSONObject.getString("display", null);

		if ((display == null) || !StringUtil.equals(display, "none")) {
			return false;
		}

		return true;
	}

	private ListStyle _toListStyle(
		CollectionStyledLayoutStructureItem
			collectionStyledLayoutStructureItem) {

		ListStyle listStyle = new ListStyle();

		listStyle.setListStyleDefinition(
			() -> _toListStyleDefinition(collectionStyledLayoutStructureItem));
		listStyle.setListStyleType(
			() -> ListStyle.ListStyleType.create(
				CollectionDisplayListStyleUtil.toExternalValue(
					collectionStyledLayoutStructureItem.getListStyle())));

		return listStyle;
	}

	private ListStyleDefinition _toListStyleDefinition(
		CollectionStyledLayoutStructureItem
			collectionStyledLayoutStructureItem) {

		return new ListStyleDefinition() {
			{
				setAlign(
					() -> {
						String align =
							collectionStyledLayoutStructureItem.getAlign();

						if (Validator.isNull(align)) {
							return null;
						}

						return Align.create(
							AlignConverter.convertToExternalValue(align));
					});
				setFlexWrap(
					() -> {
						String flexWrap =
							collectionStyledLayoutStructureItem.getFlexWrap();

						if (Validator.isNull(flexWrap)) {
							return null;
						}

						return FlexWrap.create(
							FlexWrapConverter.convertToExternalValue(flexWrap));
					});
				setGutters(collectionStyledLayoutStructureItem::isGutters);
				setJustify(
					() -> {
						String justify =
							collectionStyledLayoutStructureItem.getJustify();

						if (Validator.isNull(justify)) {
							return null;
						}

						return Justify.create(
							JustifyConverter.convertToExternalValue(justify));
					});
				setNumberOfColumns(
					collectionStyledLayoutStructureItem::getNumberOfColumns);
				setVerticalAlignment(
					() -> {
						String verticalAlignment =
							collectionStyledLayoutStructureItem.
								getVerticalAlignment();

						if (Validator.isNull(verticalAlignment)) {
							return null;
						}

						return ListStyleDefinition.VerticalAlignment.create(
							VerticalAlignmentConverter.convertToExternalValue(
								verticalAlignment));
					});
			}
		};
	}

	private TemplateListStyle _toTemplateListStyle(
		CollectionStyledLayoutStructureItem
			collectionStyledLayoutStructureItem) {

		TemplateListStyle templateListStyle = new TemplateListStyle();

		templateListStyle.setListItemStyleClassName(
			collectionStyledLayoutStructureItem::getListItemStyle);
		templateListStyle.setListStyleClassName(
			collectionStyledLayoutStructureItem::getListStyle);
		templateListStyle.setTemplateKey(
			collectionStyledLayoutStructureItem::getTemplateKey);

		return templateListStyle;
	}

	private static final Map
		<String, CollectionDisplayPageElementDefinition.PaginationType>
			_internalToExternalValuesMap = HashMapBuilder.put(
				CollectionPaginationUtil.PAGINATION_TYPE_NONE,
				CollectionDisplayPageElementDefinition.PaginationType.NONE
			).put(
				CollectionPaginationUtil.PAGINATION_TYPE_NUMERIC,
				CollectionDisplayPageElementDefinition.PaginationType.NUMERIC
			).put(
				CollectionPaginationUtil.PAGINATION_TYPE_SIMPLE,
				CollectionDisplayPageElementDefinition.PaginationType.SIMPLE
			).build();

	@Reference
	private AssetListEntryLocalService _assetListEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

}
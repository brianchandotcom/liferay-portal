/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer;

import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalServiceUtil;
import com.liferay.headless.admin.site.dto.v1_0.ClassNameReference;
import com.liferay.headless.admin.site.dto.v1_0.CollectionItemExternalReference;
import com.liferay.headless.admin.site.dto.v1_0.CollectionListStyle;
import com.liferay.headless.admin.site.dto.v1_0.CollectionPageElementDefinition;
import com.liferay.headless.admin.site.dto.v1_0.CollectionReference;
import com.liferay.headless.admin.site.dto.v1_0.CollectionViewport;
import com.liferay.headless.admin.site.dto.v1_0.CollectionViewportDefinition;
import com.liferay.headless.admin.site.dto.v1_0.EmptyCollectionConfig;
import com.liferay.headless.admin.site.dto.v1_0.ListStyle;
import com.liferay.headless.admin.site.dto.v1_0.ListStyleDefinition;
import com.liferay.headless.admin.site.dto.v1_0.PageElement;
import com.liferay.headless.admin.site.dto.v1_0.TemplateListStyle;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.CollectionListStyleUtil;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.ItemScopeUtil;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.ViewportIdUtil;
import com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer.context.LayoutStructureItemImporterContext;
import com.liferay.headless.admin.site.internal.resource.v1_0.util.LayoutStructureUtil;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.info.collection.provider.SingleFormVariationInfoCollectionProvider;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.layout.converter.AlignConverter;
import com.liferay.layout.converter.FlexWrapConverter;
import com.liferay.layout.converter.JustifyConverter;
import com.liferay.layout.converter.VerticalAlignmentConverter;
import com.liferay.layout.util.CollectionPaginationUtil;
import com.liferay.layout.util.structure.CollectionStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.collection.EmptyCollectionOptions;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;

/**
 * @author Eudaldo Alonso
 */
public class CollectionLayoutStructureItemImporter
	implements LayoutStructureItemImporter {

	@Override
	public LayoutStructureItem addLayoutStructureItem(
			LayoutStructure layoutStructure,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext,
			PageElement pageElement)
		throws Exception {

		CollectionStyledLayoutStructureItem
			collectionStyledLayoutStructureItem =
				(CollectionStyledLayoutStructureItem)
					layoutStructure.addCollectionStyledLayoutStructureItem(
						pageElement.getExternalReferenceCode(),
						LayoutStructureUtil.getParentExternalReferenceCode(
							pageElement, layoutStructure),
						pageElement.getPosition());

		CollectionPageElementDefinition collectionPageElementDefinition =
			(CollectionPageElementDefinition)
				pageElement.getPageElementDefinition();

		if (collectionPageElementDefinition == null) {
			return collectionStyledLayoutStructureItem;
		}

		collectionStyledLayoutStructureItem.setCollectionJSONObject(
			_getCollectionJSONObject(
				collectionPageElementDefinition.getCollectionReference(),
				layoutStructureItemImporterContext));

		_setCollectionListStyle(
			collectionPageElementDefinition.getCollectionListStyle(),
			collectionStyledLayoutStructureItem);

		CollectionViewport[] collectionViewports =
			collectionPageElementDefinition.getCollectionViewports();

		if (ArrayUtil.isNotEmpty(collectionViewports)) {
			_setViewportConfiguration(
				CollectionViewport.Id.LANDSCAPE_MOBILE, collectionViewports,
				collectionStyledLayoutStructureItem);
			_setViewportConfiguration(
				CollectionViewport.Id.PORTRAIT_MOBILE, collectionViewports,
				collectionStyledLayoutStructureItem);
			_setViewportConfiguration(
				CollectionViewport.Id.TABLET, collectionViewports,
				collectionStyledLayoutStructureItem);
		}

		collectionStyledLayoutStructureItem.setDisplayAllItems(
			GetterUtil.getBoolean(
				collectionPageElementDefinition.getDisplayAllItems()));
		collectionStyledLayoutStructureItem.setEmptyCollectionOptions(
			_toEmptyCollectionOptions(
				collectionPageElementDefinition.getEmptyCollectionConfig()));
		collectionStyledLayoutStructureItem.setDisplayAllPages(
			GetterUtil.getBoolean(
				collectionPageElementDefinition.getDisplayAllPages(),
				Boolean.TRUE));
		collectionStyledLayoutStructureItem.setNumberOfItems(
			GetterUtil.getInteger(
				collectionPageElementDefinition.getNumberOfItems(), 5));
		collectionStyledLayoutStructureItem.setNumberOfItemsPerPage(
			GetterUtil.getInteger(
				collectionPageElementDefinition.getNumberOfItemsPerPage(), 5));
		collectionStyledLayoutStructureItem.setNumberOfPages(
			GetterUtil.getInteger(
				collectionPageElementDefinition.getNumberOfPages(), 20));
		collectionStyledLayoutStructureItem.setPaginationType(
			_toPaginationType(
				collectionPageElementDefinition.getPaginationType()));
		collectionStyledLayoutStructureItem.setName(
			collectionPageElementDefinition.getName());
		collectionStyledLayoutStructureItem.updateItemConfig(
			JSONUtil.put(
				"styles",
				_toStylesJSONObject(
					collectionPageElementDefinition.getHidden())));

		return collectionStyledLayoutStructureItem;
	}

	private JSONObject _getClassNameReferenceJSONObject(
		CollectionReference collectionReference,
		LayoutStructureItemImporterContext layoutStructureItemImporterContext) {

		InfoItemServiceRegistry infoItemServiceRegistry =
			layoutStructureItemImporterContext.getInfoItemServiceRegistry();

		if (infoItemServiceRegistry == null) {
			return JSONFactoryUtil.createJSONObject();
		}

		ClassNameReference classNameReference =
			(ClassNameReference)collectionReference;

		if (Validator.isNull(classNameReference.getClassName())) {
			return JSONFactoryUtil.createJSONObject();
		}

		InfoCollectionProvider infoCollectionProvider =
			infoItemServiceRegistry.getInfoItemService(
				InfoCollectionProvider.class,
				classNameReference.getClassName());

		if (infoCollectionProvider == null) {
			return JSONUtil.put(
				"key", classNameReference.getClassName()
			).put(
				"type", InfoListProviderItemSelectorReturnType.class.getName()
			);
		}

		return JSONUtil.put(
			"itemSubtype",
			() -> {
				if (!(infoCollectionProvider instanceof
						SingleFormVariationInfoCollectionProvider)) {

					return null;
				}

				SingleFormVariationInfoCollectionProvider<?>
					singleFormVariationInfoCollectionProvider =
						(SingleFormVariationInfoCollectionProvider<?>)
							infoCollectionProvider;

				return singleFormVariationInfoCollectionProvider.
					getFormVariationKey();
			}
		).put(
			"itemType", infoCollectionProvider.getCollectionItemClassName()
		).put(
			"key", infoCollectionProvider.getKey()
		).put(
			"title",
			() -> infoCollectionProvider.getLabel(LocaleUtil.getDefault())
		).put(
			"type", InfoListProviderItemSelectorReturnType.class.getName()
		);
	}

	private JSONObject _getCollectionItemExternalReferenceJSONObject(
			CollectionReference collectionReference,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext)
		throws Exception {

		CollectionItemExternalReference collectionItemExternalReference =
			(CollectionItemExternalReference)collectionReference;

		if (Validator.isNull(
				collectionItemExternalReference.getExternalReferenceCode())) {

			return JSONFactoryUtil.createJSONObject();
		}

		Long groupId = ItemScopeUtil.getItemGroupId(
			layoutStructureItemImporterContext.getCompanyId(),
			collectionItemExternalReference.getScope(),
			layoutStructureItemImporterContext.getGroupId());

		JSONObject jsonObject = JSONUtil.put(
			"externalReferenceCode",
			collectionItemExternalReference.getExternalReferenceCode()
		).put(
			"scopeExternalReferenceCode",
			ItemScopeUtil.getItemScopeExternalReferenceCode(
				collectionItemExternalReference.getScope(),
				layoutStructureItemImporterContext.getGroupId())
		).put(
			"type", InfoListItemSelectorReturnType.class.getName()
		);

		if (groupId == null) {
			return jsonObject;
		}

		AssetListEntry assetListEntry =
			AssetListEntryLocalServiceUtil.
				fetchAssetListEntryByExternalReferenceCode(
					collectionItemExternalReference.getExternalReferenceCode(),
					groupId);

		if (assetListEntry == null) {
			return jsonObject;
		}

		return jsonObject.put(
			"classNameId",
			String.valueOf(PortalUtil.getClassNameId(AssetListEntry.class))
		).put(
			"classPK", assetListEntry.getAssetListEntryId()
		).put(
			"itemSubtype", assetListEntry.getAssetEntrySubtype()
		).put(
			"itemType", assetListEntry.getAssetEntryType()
		).put(
			"title", assetListEntry.getTitle()
		);
	}

	private JSONObject _getCollectionJSONObject(
			CollectionReference collectionReference,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext)
		throws Exception {

		if (collectionReference == null) {
			return JSONFactoryUtil.createJSONObject();
		}

		if (collectionReference instanceof ClassNameReference) {
			return _getClassNameReferenceJSONObject(
				collectionReference, layoutStructureItemImporterContext);
		}

		return _getCollectionItemExternalReferenceJSONObject(
			collectionReference, layoutStructureItemImporterContext);
	}

	private CollectionViewport _getCollectionViewport(
		CollectionViewport.Id collectionViewportId,
		CollectionViewport[] collectionViewports) {

		for (CollectionViewport collectionViewport : collectionViewports) {
			if (Objects.equals(
					collectionViewportId, collectionViewport.getId())) {

				return collectionViewport;
			}
		}

		return null;
	}

	private void _setCollectionListStyle(
		CollectionListStyle collectionListStyle,
		CollectionStyledLayoutStructureItem
			collectionStyledLayoutStructureItem) {

		if (collectionListStyle != null) {
			if (collectionListStyle instanceof TemplateListStyle) {
				TemplateListStyle templateListStyle =
					(TemplateListStyle)collectionListStyle;

				collectionStyledLayoutStructureItem.setAlign(null);
				collectionStyledLayoutStructureItem.setFlexWrap(null);
				collectionStyledLayoutStructureItem.setJustify(null);
				collectionStyledLayoutStructureItem.setListItemStyle(
					templateListStyle.getListItemStyleClassName());
				collectionStyledLayoutStructureItem.setListStyle(
					templateListStyle.getListStyleClassName());
				collectionStyledLayoutStructureItem.setNumberOfColumns(1);
				collectionStyledLayoutStructureItem.setTemplateKey(
					templateListStyle.getTemplateKey());
				collectionStyledLayoutStructureItem.setVerticalAlignment(null);
			}
			else {
				ListStyle listStyle = (ListStyle)collectionListStyle;

				ListStyleDefinition listStyleDefinition =
					listStyle.getListStyleDefinition();

				String align = listStyleDefinition.getAlignAsString();

				if (align != null) {
					collectionStyledLayoutStructureItem.setAlign(
						AlignConverter.convertToInternalValue(align));
				}
				else {
					collectionStyledLayoutStructureItem.setAlign(null);
				}

				String flexWrap = listStyleDefinition.getFlexWrapAsString();

				if (flexWrap != null) {
					collectionStyledLayoutStructureItem.setFlexWrap(
						FlexWrapConverter.convertToInternalValue(flexWrap));
				}
				else {
					collectionStyledLayoutStructureItem.setFlexWrap(null);
				}

				collectionStyledLayoutStructureItem.setGutters(
					listStyleDefinition.getGutters());

				String justify = listStyleDefinition.getJustifyAsString();

				if (justify != null) {
					collectionStyledLayoutStructureItem.setJustify(
						JustifyConverter.convertToInternalValue(justify));
				}
				else {
					collectionStyledLayoutStructureItem.setJustify(null);
				}

				collectionStyledLayoutStructureItem.setListStyle(
					CollectionListStyleUtil.toInternalValue(
						listStyle.getListStyleTypeAsString()));

				collectionStyledLayoutStructureItem.setNumberOfColumns(
					GetterUtil.getInteger(
						listStyleDefinition.getNumberOfColumns(), 1));

				collectionStyledLayoutStructureItem.setVerticalAlignment(
					VerticalAlignmentConverter.convertToInternalValue(
						GetterUtil.getString(
							listStyleDefinition.
								getVerticalAlignmentAsString())));
			}
		}
		else {
			collectionStyledLayoutStructureItem.setAlign(null);
			collectionStyledLayoutStructureItem.setFlexWrap(null);
			collectionStyledLayoutStructureItem.setGutters(true);
			collectionStyledLayoutStructureItem.setJustify(null);
			collectionStyledLayoutStructureItem.setListItemStyle(null);
			collectionStyledLayoutStructureItem.setListStyle(null);
			collectionStyledLayoutStructureItem.setNumberOfColumns(1);
			collectionStyledLayoutStructureItem.setTemplateKey(null);
			collectionStyledLayoutStructureItem.setVerticalAlignment(null);
		}
	}

	private void _setViewportConfiguration(
		CollectionViewport.Id collectionViewportId,
		CollectionViewport[] collectionViewports,
		CollectionStyledLayoutStructureItem
			collectionStyledLayoutStructureItem) {

		CollectionViewport collectionViewport = _getCollectionViewport(
			collectionViewportId, collectionViewports);

		String viewportId = ViewportIdUtil.toInternalValue(
			collectionViewportId.getValue());

		if (collectionViewport != null) {
			collectionStyledLayoutStructureItem.setViewportConfiguration(
				viewportId, _toViewportJSONObject(collectionViewport));
		}
		else {
			collectionStyledLayoutStructureItem.setViewportConfiguration(
				viewportId, JSONFactoryUtil.createJSONObject());
		}
	}

	private EmptyCollectionOptions _toEmptyCollectionOptions(
		EmptyCollectionConfig emptyCollectionConfig) {

		if (emptyCollectionConfig == null) {
			return null;
		}

		return new EmptyCollectionOptions() {
			{
				setDisplayMessage(emptyCollectionConfig::getDisplayMessage);
				setMessage(emptyCollectionConfig::getMessage_i18n);
			}
		};
	}

	private String _toPaginationType(
		CollectionPageElementDefinition.PaginationType paginationType) {

		if (Objects.equals(
				paginationType,
				CollectionPageElementDefinition.PaginationType.NUMERIC)) {

			return CollectionPaginationUtil.PAGINATION_TYPE_NUMERIC;
		}

		if (Objects.equals(
				paginationType,
				CollectionPageElementDefinition.PaginationType.SIMPLE)) {

			return CollectionPaginationUtil.PAGINATION_TYPE_SIMPLE;
		}

		return CollectionPaginationUtil.PAGINATION_TYPE_NONE;
	}

	private JSONObject _toStylesJSONObject(Boolean hidden) {
		if ((hidden == null) || !hidden) {
			return JSONFactoryUtil.createJSONObject();
		}

		return JSONUtil.put("display", "none");
	}

	private JSONObject _toViewportJSONObject(
		CollectionViewport collectionViewport) {

		if (collectionViewport == null) {
			return JSONFactoryUtil.createJSONObject();
		}

		CollectionViewportDefinition collectionViewportDefinition =
			collectionViewport.getCollectionViewportDefinition();

		if (collectionViewportDefinition == null) {
			return JSONFactoryUtil.createJSONObject();
		}

		return JSONUtil.put(
			"align",
			AlignConverter.convertToInternalValue(
				collectionViewportDefinition.getAlignAsString())
		).put(
			"flexWrap",
			FlexWrapConverter.convertToInternalValue(
				collectionViewportDefinition.getFlexWrapAsString())
		).put(
			"justify",
			JustifyConverter.convertToInternalValue(
				collectionViewportDefinition.getJustifyAsString())
		).put(
			"numberOfColumns",
			() -> {
				Integer numberOfColumns =
					collectionViewportDefinition.getNumberOfColumns();

				if (numberOfColumns == null) {
					return null;
				}

				return numberOfColumns;
			}
		).put(
			"styles",
			() -> _toStylesJSONObject(collectionViewportDefinition.getHidden())
		);
	}

}
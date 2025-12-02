/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer;

import com.liferay.headless.admin.site.dto.v1_0.ClassNameReference;
import com.liferay.headless.admin.site.dto.v1_0.CollectionDisplayListStyle;
import com.liferay.headless.admin.site.dto.v1_0.CollectionDisplayPageElementDefinition;
import com.liferay.headless.admin.site.dto.v1_0.CollectionDisplayViewport;
import com.liferay.headless.admin.site.dto.v1_0.CollectionDisplayViewportDefinition;
import com.liferay.headless.admin.site.dto.v1_0.CollectionReference;
import com.liferay.headless.admin.site.dto.v1_0.CollectionSettings;
import com.liferay.headless.admin.site.dto.v1_0.EmptyCollectionConfig;
import com.liferay.headless.admin.site.dto.v1_0.ListStyle;
import com.liferay.headless.admin.site.dto.v1_0.ListStyleDefinition;
import com.liferay.headless.admin.site.dto.v1_0.PageElement;
import com.liferay.headless.admin.site.dto.v1_0.TemplateListStyle;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.CollectionDisplayListStyleUtil;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.CollectionUtil;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.ViewportIdUtil;
import com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer.context.LayoutStructureItemImporterContext;
import com.liferay.headless.admin.site.internal.resource.v1_0.util.LayoutStructureUtil;
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
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

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

		PageElement[] pageElements = pageElement.getPageElements();

		if ((pageElements != null) && (pageElements.length > 1)) {
			throw new UnsupportedOperationException();
		}

		String collectionItemItemId = PortalUUIDUtil.generate();

		if (ArrayUtil.isNotEmpty(pageElements)) {
			collectionItemItemId = pageElements[0].getExternalReferenceCode();
		}

		CollectionStyledLayoutStructureItem
			collectionStyledLayoutStructureItem =
				(CollectionStyledLayoutStructureItem)
					layoutStructure.addCollectionStyledLayoutStructureItem(
						collectionItemItemId,
						pageElement.getExternalReferenceCode(),
						LayoutStructureUtil.getParentExternalReferenceCode(
							pageElement, layoutStructure),
						pageElement.getPosition());

		CollectionDisplayPageElementDefinition
			collectionDisplayPageElementDefinition =
				(CollectionDisplayPageElementDefinition)
					pageElement.getPageElementDefinition();

		if (collectionDisplayPageElementDefinition == null) {
			return collectionStyledLayoutStructureItem;
		}

		collectionStyledLayoutStructureItem.setCollectionJSONObject(
			_getCollectionJSONObject(
				collectionDisplayPageElementDefinition.getCollectionSettings(),
				layoutStructureItemImporterContext));

		_setCollectionDisplayListStyle(
			collectionDisplayPageElementDefinition.
				getCollectionDisplayListStyle(),
			collectionStyledLayoutStructureItem);

		CollectionDisplayViewport[] collectionDisplayViewports =
			collectionDisplayPageElementDefinition.
				getCollectionDisplayViewports();

		if (ArrayUtil.isNotEmpty(collectionDisplayViewports)) {
			_setViewportConfiguration(
				CollectionDisplayViewport.Id.LANDSCAPE_MOBILE,
				collectionDisplayViewports,
				collectionStyledLayoutStructureItem);
			_setViewportConfiguration(
				CollectionDisplayViewport.Id.PORTRAIT_MOBILE,
				collectionDisplayViewports,
				collectionStyledLayoutStructureItem);
			_setViewportConfiguration(
				CollectionDisplayViewport.Id.TABLET, collectionDisplayViewports,
				collectionStyledLayoutStructureItem);
		}

		collectionStyledLayoutStructureItem.setDisplayAllItems(
			GetterUtil.getBoolean(
				collectionDisplayPageElementDefinition.getDisplayAllItems()));
		collectionStyledLayoutStructureItem.setEmptyCollectionOptions(
			_toEmptyCollectionOptions(
				collectionDisplayPageElementDefinition.
					getEmptyCollectionConfig()));
		collectionStyledLayoutStructureItem.setDisplayAllPages(
			GetterUtil.getBoolean(
				collectionDisplayPageElementDefinition.getDisplayAllPages(),
				Boolean.TRUE));
		collectionStyledLayoutStructureItem.setNumberOfItems(
			GetterUtil.getInteger(
				collectionDisplayPageElementDefinition.getNumberOfItems(), 5));
		collectionStyledLayoutStructureItem.setNumberOfItemsPerPage(
			GetterUtil.getInteger(
				collectionDisplayPageElementDefinition.
					getNumberOfItemsPerPage(),
				5));
		collectionStyledLayoutStructureItem.setNumberOfPages(
			GetterUtil.getInteger(
				collectionDisplayPageElementDefinition.getNumberOfPages(), 20));
		collectionStyledLayoutStructureItem.setPaginationType(
			_toPaginationType(
				collectionDisplayPageElementDefinition.getPaginationType()));
		collectionStyledLayoutStructureItem.setName(
			collectionDisplayPageElementDefinition.getName());

		return collectionStyledLayoutStructureItem;
	}

	private CollectionDisplayViewport _getCollectionDisplayViewport(
		CollectionDisplayViewport.Id collectionDisplayViewportId,
		CollectionDisplayViewport[] collectionDisplayViewports) {

		for (CollectionDisplayViewport collectionDisplayViewport :
				collectionDisplayViewports) {

			if (Objects.equals(
					collectionDisplayViewportId,
					collectionDisplayViewport.getId())) {

				return collectionDisplayViewport;
			}
		}

		return null;
	}

	private JSONObject _getCollectionJSONObject(
			CollectionSettings collectionSettings,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext)
		throws Exception {

		CollectionReference collectionReference =
			collectionSettings.getCollectionReference();

		if (collectionReference == null) {
			return JSONFactoryUtil.createJSONObject();
		}

		JSONObject collectionJSONObject =
			CollectionUtil.getCollectionJSONObject(
				collectionReference,
				layoutStructureItemImporterContext.getCompanyId(),
				layoutStructureItemImporterContext.getInfoItemServiceRegistry(),
				layoutStructureItemImporterContext.getGroupId());

		if (collectionReference instanceof ClassNameReference) {
			collectionJSONObject.put(
				"config", collectionSettings.getCollectionConfig());
		}

		return collectionJSONObject;
	}

	private void _setCollectionDisplayListStyle(
		CollectionDisplayListStyle collectionDisplayListStyle,
		CollectionStyledLayoutStructureItem
			collectionStyledLayoutStructureItem) {

		if (collectionDisplayListStyle != null) {
			if (collectionDisplayListStyle instanceof TemplateListStyle) {
				TemplateListStyle templateListStyle =
					(TemplateListStyle)collectionDisplayListStyle;

				collectionStyledLayoutStructureItem.setListItemStyle(
					templateListStyle.getListItemStyleClassName());
				collectionStyledLayoutStructureItem.setListStyle(
					templateListStyle.getListStyleClassName());
				collectionStyledLayoutStructureItem.setTemplateKey(
					templateListStyle.getTemplateKey());

				collectionStyledLayoutStructureItem.setVerticalAlignment(null);
			}
			else {
				ListStyle listStyle = (ListStyle)collectionDisplayListStyle;

				ListStyleDefinition listStyleDefinition =
					listStyle.getListStyleDefinition();

				collectionStyledLayoutStructureItem.setGutters(
					listStyleDefinition.getGutters());

				collectionStyledLayoutStructureItem.setListStyle(
					CollectionDisplayListStyleUtil.toInternalValue(
						listStyle.getListStyleTypeAsString()));

				collectionStyledLayoutStructureItem.setVerticalAlignment(
					VerticalAlignmentConverter.convertToInternalValue(
						GetterUtil.getString(
							listStyleDefinition.
								getVerticalAlignmentAsString())));
			}
		}
		else {
			collectionStyledLayoutStructureItem.setGutters(true);
			collectionStyledLayoutStructureItem.setListItemStyle(null);
			collectionStyledLayoutStructureItem.setListStyle(null);
			collectionStyledLayoutStructureItem.setTemplateKey(null);
			collectionStyledLayoutStructureItem.setVerticalAlignment(null);
		}
	}

	private void _setViewportConfiguration(
		CollectionDisplayViewport.Id collectionDisplayViewportId,
		CollectionDisplayViewport[] collectionDisplayViewports,
		CollectionStyledLayoutStructureItem
			collectionStyledLayoutStructureItem) {

		CollectionDisplayViewport collectionDisplayViewport =
			_getCollectionDisplayViewport(
				collectionDisplayViewportId, collectionDisplayViewports);

		String viewportId = ViewportIdUtil.toInternalValue(
			collectionDisplayViewportId.getValue());

		if (collectionDisplayViewport != null) {
			collectionStyledLayoutStructureItem.setViewportConfiguration(
				viewportId, _toViewportJSONObject(collectionDisplayViewport));
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
		CollectionDisplayPageElementDefinition.PaginationType paginationType) {

		if (Objects.equals(
				paginationType,
				CollectionDisplayPageElementDefinition.PaginationType.
					NUMERIC)) {

			return CollectionPaginationUtil.PAGINATION_TYPE_NUMERIC;
		}

		if (Objects.equals(
				paginationType,
				CollectionDisplayPageElementDefinition.PaginationType.SIMPLE)) {

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
		CollectionDisplayViewport collectionDisplayViewport) {

		if (collectionDisplayViewport == null) {
			return JSONFactoryUtil.createJSONObject();
		}

		CollectionDisplayViewportDefinition
			collectionDisplayViewportDefinition =
				collectionDisplayViewport.
					getCollectionDisplayViewportDefinition();

		if (collectionDisplayViewportDefinition == null) {
			return JSONFactoryUtil.createJSONObject();
		}

		return JSONUtil.put(
			"align",
			AlignConverter.convertToInternalValue(
				collectionDisplayViewportDefinition.getAlignAsString())
		).put(
			"flexWrap",
			FlexWrapConverter.convertToInternalValue(
				collectionDisplayViewportDefinition.getFlexWrapAsString())
		).put(
			"justify",
			JustifyConverter.convertToInternalValue(
				collectionDisplayViewportDefinition.getJustifyAsString())
		).put(
			"numberOfColumns",
			() -> {
				Integer numberOfColumns =
					collectionDisplayViewportDefinition.getNumberOfColumns();

				if (numberOfColumns == null) {
					return null;
				}

				return numberOfColumns;
			}
		).put(
			"styles",
			() -> _toStylesJSONObject(
				collectionDisplayViewportDefinition.getHidden())
		);
	}

}
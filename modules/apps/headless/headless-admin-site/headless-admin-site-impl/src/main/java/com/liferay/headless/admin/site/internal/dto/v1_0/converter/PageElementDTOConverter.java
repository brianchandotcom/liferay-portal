/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.converter;

import com.liferay.headless.admin.site.dto.v1_0.PageCollectionDefinition;
import com.liferay.headless.admin.site.dto.v1_0.PageCollectionItemDefinition;
import com.liferay.headless.admin.site.dto.v1_0.PageColumnDefinition;
import com.liferay.headless.admin.site.dto.v1_0.PageContainerDefinition;
import com.liferay.headless.admin.site.dto.v1_0.PageDropZoneDefinition;
import com.liferay.headless.admin.site.dto.v1_0.PageElement;
import com.liferay.headless.admin.site.dto.v1_0.PageFormDefinition;
import com.liferay.headless.admin.site.dto.v1_0.PageFragmentDropZoneDefinition;
import com.liferay.headless.admin.site.dto.v1_0.PageFragmentInstanceDefinition;
import com.liferay.headless.admin.site.dto.v1_0.PageRowDefinition;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "dto.class.name=com.liferay.layout.util.structure.LayoutStructureItem",
	service = DTOConverter.class
)
public class PageElementDTOConverter
	implements DTOConverter<LayoutStructureItem, PageElement> {

	@Override
	public String getContentType() {
		return PageElement.class.getSimpleName();
	}

	@Override
	public PageElement toDTO(
			DTOConverterContext dtoConverterContext,
			LayoutStructureItem layoutStructureItem)
		throws Exception {

		return new PageElement() {
			{
				setDefinition(() -> _getDefinition(layoutStructureItem));
				setExternalReferenceCode(layoutStructureItem::getItemId);
				setPageElements(() -> new PageElement[0]);
				setParentExternalReferenceCode(
					layoutStructureItem::getParentItemId);
				setPosition(() -> 0);
				setType(() -> _getType(layoutStructureItem.getItemType()));
			}
		};
	}

	private Object _getDefinition(LayoutStructureItem layoutStructureItem) {
		if (Objects.equals(
				layoutStructureItem.getItemType(),
				LayoutDataItemTypeConstants.TYPE_COLLECTION)) {

			return new PageCollectionDefinition();
		}

		if (Objects.equals(
				layoutStructureItem.getItemType(),
				LayoutDataItemTypeConstants.TYPE_COLLECTION_ITEM)) {

			return new PageCollectionItemDefinition();
		}

		if (Objects.equals(
				layoutStructureItem.getItemType(),
				LayoutDataItemTypeConstants.TYPE_COLUMN)) {

			return new PageColumnDefinition();
		}

		if (Objects.equals(
				layoutStructureItem.getItemType(),
				LayoutDataItemTypeConstants.TYPE_CONTAINER)) {

			return new PageContainerDefinition();
		}

		if (Objects.equals(
				layoutStructureItem.getItemType(),
				LayoutDataItemTypeConstants.TYPE_DROP_ZONE)) {

			return new PageDropZoneDefinition();
		}

		if (Objects.equals(
				layoutStructureItem.getItemType(),
				LayoutDataItemTypeConstants.TYPE_FORM)) {

			return new PageFormDefinition();
		}

		if (Objects.equals(
				layoutStructureItem.getItemType(),
				LayoutDataItemTypeConstants.TYPE_FORM_STEP) ||
			Objects.equals(
				layoutStructureItem.getItemType(),
				LayoutDataItemTypeConstants.TYPE_FORM_STEP_CONTAINER)) {

			throw new UnsupportedOperationException();
		}

		if (Objects.equals(
				layoutStructureItem.getItemType(),
				LayoutDataItemTypeConstants.TYPE_FRAGMENT)) {

			return new PageFragmentInstanceDefinition();
		}

		if (Objects.equals(
				layoutStructureItem.getItemType(),
				LayoutDataItemTypeConstants.TYPE_FRAGMENT_DROP_ZONE)) {

			return new PageFragmentDropZoneDefinition();
		}

		if (Objects.equals(
				layoutStructureItem.getItemType(),
				LayoutDataItemTypeConstants.TYPE_ROOT)) {

			throw new UnsupportedOperationException();
		}

		if (Objects.equals(
				layoutStructureItem.getItemType(),
				LayoutDataItemTypeConstants.TYPE_ROW)) {

			return new PageRowDefinition();
		}

		throw new UnsupportedOperationException();
	}

	private PageElement.Type _getType(String type) {
		if (_internalToExternalValuesMap.containsKey(type)) {
			return _internalToExternalValuesMap.get(type);
		}

		throw new UnsupportedOperationException();
	}

	private static final Map<String, PageElement.Type>
		_internalToExternalValuesMap = HashMapBuilder.put(
			LayoutDataItemTypeConstants.TYPE_COLLECTION,
			PageElement.Type.COLLECTION
		).put(
			LayoutDataItemTypeConstants.TYPE_COLLECTION_ITEM,
			PageElement.Type.COLLECTION_ITEM
		).put(
			LayoutDataItemTypeConstants.TYPE_COLUMN, PageElement.Type.COLUMN
		).put(
			LayoutDataItemTypeConstants.TYPE_CONTAINER,
			PageElement.Type.CONTAINER
		).put(
			LayoutDataItemTypeConstants.TYPE_DROP_ZONE,
			PageElement.Type.DROP_ZONE
		).put(
			LayoutDataItemTypeConstants.TYPE_FORM, PageElement.Type.FORM
		).put(
			LayoutDataItemTypeConstants.TYPE_FRAGMENT, PageElement.Type.FRAGMENT
		).put(
			LayoutDataItemTypeConstants.TYPE_FRAGMENT_DROP_ZONE,
			PageElement.Type.FRAGMENT_DROP_ZONE
		).put(
			LayoutDataItemTypeConstants.TYPE_ROW, PageElement.Type.ROW
		).build();

}
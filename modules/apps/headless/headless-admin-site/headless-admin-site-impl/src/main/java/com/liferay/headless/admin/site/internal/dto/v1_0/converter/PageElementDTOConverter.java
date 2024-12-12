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
import com.liferay.headless.admin.site.dto.v1_0.PageFormStepContainerDefinition;
import com.liferay.headless.admin.site.dto.v1_0.PageFormStepDefinition;
import com.liferay.headless.admin.site.dto.v1_0.PageFragmentDropZoneDefinition;
import com.liferay.headless.admin.site.dto.v1_0.PageFragmentInstanceDefinition;
import com.liferay.headless.admin.site.dto.v1_0.PageRowDefinition;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.PageElementTypeUtil;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.layout.util.structure.CollectionStyledLayoutStructureItem;
import com.liferay.layout.util.structure.ColumnLayoutStructureItem;
import com.liferay.layout.util.structure.ContainerStyledLayoutStructureItem;
import com.liferay.layout.util.structure.FormStepContainerStyledLayoutStructureItem;
import com.liferay.layout.util.structure.FormStyledLayoutStructureItem;
import com.liferay.layout.util.structure.FragmentDropZoneLayoutStructureItem;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.RowStyledLayoutStructureItem;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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

		if (dtoConverterContext == null) {
			throw new UnsupportedOperationException();
		}

		LayoutStructure layoutStructure =
			(LayoutStructure)dtoConverterContext.getAttribute(
				LayoutStructure.class.getName());

		if (layoutStructure == null) {
			throw new UnsupportedOperationException();
		}

		return new PageElement() {
			{
				setDefinition(() -> _getDefinition(layoutStructureItem));
				setExternalReferenceCode(layoutStructureItem::getItemId);
				setPageElements(
					() -> _getPageElements(
						dtoConverterContext, layoutStructure,
						layoutStructureItem));
				setParentExternalReferenceCode(
					layoutStructureItem::getParentItemId);
				setPosition(
					() -> {
						LayoutStructureItem parentLayoutStructureItem =
							layoutStructure.getLayoutStructureItem(
								layoutStructureItem.getParentItemId());

						List<String> childrenItemIds =
							parentLayoutStructureItem.getChildrenItemIds();

						return childrenItemIds.indexOf(
							layoutStructureItem.getItemId());
					});
				setType(
					() -> PageElementTypeUtil.toExternalType(
						layoutStructureItem.getItemType()));
			}
		};
	}

	private Object _getDefinition(LayoutStructureItem layoutStructureItem)
		throws Exception {

		if (Objects.equals(
				layoutStructureItem.getItemType(),
				LayoutDataItemTypeConstants.TYPE_COLLECTION)) {

			return _pageCollectionDefinitionDTOConverter.toDTO(
				(CollectionStyledLayoutStructureItem)layoutStructureItem);
		}

		if (Objects.equals(
				layoutStructureItem.getItemType(),
				LayoutDataItemTypeConstants.TYPE_COLLECTION_ITEM)) {

			return new PageCollectionItemDefinition();
		}

		if (Objects.equals(
				layoutStructureItem.getItemType(),
				LayoutDataItemTypeConstants.TYPE_COLUMN)) {

			return _pageColumnDefinitionDTOConverter.toDTO(
				(ColumnLayoutStructureItem)layoutStructureItem);
		}

		if (Objects.equals(
				layoutStructureItem.getItemType(),
				LayoutDataItemTypeConstants.TYPE_CONTAINER)) {

			return _pageContainerDefinitionDTOConverter.toDTO(
				(ContainerStyledLayoutStructureItem)layoutStructureItem);
		}

		if (Objects.equals(
				layoutStructureItem.getItemType(),
				LayoutDataItemTypeConstants.TYPE_DROP_ZONE)) {

			return new PageDropZoneDefinition();
		}

		if (Objects.equals(
				layoutStructureItem.getItemType(),
				LayoutDataItemTypeConstants.TYPE_FORM)) {

			return _pageFormDefinitionDTOConverter.toDTO(
				(FormStyledLayoutStructureItem)layoutStructureItem);
		}

		if (Objects.equals(
				layoutStructureItem.getItemType(),
				LayoutDataItemTypeConstants.TYPE_FORM_STEP)) {

			return new PageFormStepDefinition();
		}

		if (Objects.equals(
				layoutStructureItem.getItemType(),
				LayoutDataItemTypeConstants.TYPE_FORM_STEP_CONTAINER)) {

			return _pageFormStepContainerDefinitionDTOConverter.toDTO(
				(FormStepContainerStyledLayoutStructureItem)
					layoutStructureItem);
		}

		if (Objects.equals(
				layoutStructureItem.getItemType(),
				LayoutDataItemTypeConstants.TYPE_FRAGMENT)) {

			return _pageFragmentInstanceDefinitionDTOConverter.toDTO(
				(FragmentStyledLayoutStructureItem)layoutStructureItem);
		}

		if (Objects.equals(
				layoutStructureItem.getItemType(),
				LayoutDataItemTypeConstants.TYPE_FRAGMENT_DROP_ZONE)) {

			return _pageFragmentDropZoneDefinitionDTOConverter.toDTO(
				(FragmentDropZoneLayoutStructureItem)layoutStructureItem);
		}

		if (Objects.equals(
				layoutStructureItem.getItemType(),
				LayoutDataItemTypeConstants.TYPE_ROOT)) {

			throw new UnsupportedOperationException();
		}

		if (Objects.equals(
				layoutStructureItem.getItemType(),
				LayoutDataItemTypeConstants.TYPE_ROW)) {

			return _pageRowDefinitionDTOConverter.toDTO(
				(RowStyledLayoutStructureItem)layoutStructureItem);
		}

		throw new UnsupportedOperationException();
	}

	private PageElement[] _getPageElements(
		DTOConverterContext dtoConverterContext,
		LayoutStructure layoutStructure,
		LayoutStructureItem layoutStructureItem) {

		return TransformUtil.transformToArray(
			layoutStructureItem.getChildrenItemIds(),
			childrenItemId -> toDTO(
				dtoConverterContext,
				layoutStructure.getLayoutStructureItem(childrenItemId)),
			PageElement.class);
	}

	@Reference(
		target = "(component.name=com.liferay.headless.admin.site.internal.dto.v1_0.converter.PageCollectionDefinitionDTOConverter)"
	)
	private DTOConverter
		<CollectionStyledLayoutStructureItem, PageCollectionDefinition>
			_pageCollectionDefinitionDTOConverter;

	@Reference(
		target = "(component.name=com.liferay.headless.admin.site.internal.dto.v1_0.converter.PageColumnDefinitionDTOConverter)"
	)
	private DTOConverter<ColumnLayoutStructureItem, PageColumnDefinition>
		_pageColumnDefinitionDTOConverter;

	@Reference(
		target = "(component.name=com.liferay.headless.admin.site.internal.dto.v1_0.converter.PageContainerDefinitionDTOConverter)"
	)
	private DTOConverter
		<ContainerStyledLayoutStructureItem, PageContainerDefinition>
			_pageContainerDefinitionDTOConverter;

	@Reference(
		target = "(component.name=com.liferay.headless.admin.site.internal.dto.v1_0.converter.PageFormDefinitionDTOConverter)"
	)
	private DTOConverter<FormStyledLayoutStructureItem, PageFormDefinition>
		_pageFormDefinitionDTOConverter;

	@Reference(
		target = "(component.name=com.liferay.headless.admin.site.internal.dto.v1_0.converter.PageFormStepContainerDefinitionDTOConverter)"
	)
	private DTOConverter
		<FormStepContainerStyledLayoutStructureItem,
		 PageFormStepContainerDefinition>
			_pageFormStepContainerDefinitionDTOConverter;

	@Reference(
		target = "(component.name=com.liferay.headless.admin.site.internal.dto.v1_0.converter.PageFragmentDropZoneDefinitionDTOConverter)"
	)
	private DTOConverter
		<FragmentDropZoneLayoutStructureItem, PageFragmentDropZoneDefinition>
			_pageFragmentDropZoneDefinitionDTOConverter;

	@Reference(
		target = "(component.name=com.liferay.headless.admin.site.internal.dto.v1_0.converter.PageFragmentInstanceDefinitionDTOConverter)"
	)
	private DTOConverter
		<FragmentStyledLayoutStructureItem, PageFragmentInstanceDefinition>
			_pageFragmentInstanceDefinitionDTOConverter;

	@Reference(
		target = "(component.name=com.liferay.headless.admin.site.internal.dto.v1_0.converter.PageRowDefinitionDTOConverter)"
	)
	private DTOConverter<RowStyledLayoutStructureItem, PageRowDefinition>
		_pageRowDefinitionDTOConverter;

}
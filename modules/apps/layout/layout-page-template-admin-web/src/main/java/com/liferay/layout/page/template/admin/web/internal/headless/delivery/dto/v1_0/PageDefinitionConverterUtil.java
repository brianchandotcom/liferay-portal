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

package com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0;

import com.liferay.headless.delivery.dto.v1_0.ColumnDefinition;
import com.liferay.headless.delivery.dto.v1_0.FragmentImage;
import com.liferay.headless.delivery.dto.v1_0.PageDefinition;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.headless.delivery.dto.v1_0.RowDefinition;
import com.liferay.headless.delivery.dto.v1_0.SectionDefinition;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
import com.liferay.layout.page.template.util.LayoutDataConverter;
import com.liferay.layout.util.structure.ColumnLayoutStructureItem;
import com.liferay.layout.util.structure.ContainerLayoutStructureItem;
import com.liferay.layout.util.structure.DropZoneLayoutStructureItem;
import com.liferay.layout.util.structure.FragmentLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.RootLayoutStructureItem;
import com.liferay.layout.util.structure.RowLayoutStructureItem;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rubén Pulido
 */
public class PageDefinitionConverterUtil {

	public static PageDefinition toPageDefinition(Layout layout) {
		return new PageDefinition() {
			{
				dateCreated = layout.getCreateDate();
				dateModified = layout.getModifiedDate();
				name = layout.getName();
				pageElements = _toPageElements(layout);
			}
		};
	}

	private static PageElement _toPageElement(
		LayoutStructureItem layoutStructureItem) {

		if (layoutStructureItem instanceof ColumnLayoutStructureItem) {
			ColumnLayoutStructureItem columnLayoutStructureItem =
				(ColumnLayoutStructureItem)layoutStructureItem;

			return new PageElement() {
				{
					definition = new ColumnDefinition() {
						{
							size = columnLayoutStructureItem.getSize();
						}
					};
					type = PageElement.Type.COLUMN;
				}
			};
		}

		if (layoutStructureItem instanceof ContainerLayoutStructureItem) {
			ContainerLayoutStructureItem containerLayoutStructureItem =
				(ContainerLayoutStructureItem)layoutStructureItem;

			return new PageElement() {
				{
					definition = new SectionDefinition() {
						{
							backgroundColorCssClass = GetterUtil.getString(
								containerLayoutStructureItem.
									getBackgroundColorCssClass(),
								null);
							paddingBottom =
								containerLayoutStructureItem.getPaddingBottom();
							paddingHorizontal =
								containerLayoutStructureItem.
									getPaddingHorizontal();
							paddingTop =
								containerLayoutStructureItem.getPaddingTop();
							setBackgroundImage(
								() -> {
									String backgroundImageTitle =
										containerLayoutStructureItem.
											getBackgroundImageTitle();

									String backgroundImageURL =
										containerLayoutStructureItem.
											getBackgroundImageURL();

									if (Validator.isNull(
											backgroundImageTitle) ||
										Validator.isNull(backgroundImageURL)) {

										return null;
									}

									return new FragmentImage() {
										{
											title = backgroundImageTitle;
											url = backgroundImageURL;
										}
									};
								});
						}
					};
					type = PageElement.Type.SECTION;
				}
			};
		}

		if (layoutStructureItem instanceof DropZoneLayoutStructureItem) {
			return new PageElement() {
				{
					type = PageElement.Type.DROP_ZONE;
				}
			};
		}

		if (layoutStructureItem instanceof FragmentLayoutStructureItem) {
			return new PageElement() {
				{
					type = PageElement.Type.FRAGMENT;
				}
			};
		}

		if (layoutStructureItem instanceof RootLayoutStructureItem) {
			return new PageElement() {
				{
					type = PageElement.Type.ROOT;
				}
			};
		}

		if (layoutStructureItem instanceof RowLayoutStructureItem) {
			RowLayoutStructureItem rowLayoutStructureItem =
				(RowLayoutStructureItem)layoutStructureItem;

			return new PageElement() {
				{
					definition = new RowDefinition() {
						{
							gutters = rowLayoutStructureItem.isGutters();
							numberOfColumns =
								rowLayoutStructureItem.getNumberOfColumns();
						}
					};
					type = PageElement.Type.ROW;
				}
			};
		}

		return null;
	}

	private static PageElement[] _toPageElements(Layout layout) {
		List<PageElement> pageElements = new ArrayList<>();

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(),
					PortalUtil.getClassNameId(Layout.class), layout.getPlid());

		String layoutData = LayoutDataConverter.convert(
			layoutPageTemplateStructure.getData(0L));

		LayoutStructure layoutStructure = LayoutStructure.of(layoutData);

		for (LayoutStructureItem layoutStructureItem :
				layoutStructure.getLayoutStructureItems()) {

			pageElements.add(_toPageElement(layoutStructureItem));
		}

		return pageElements.toArray(new PageElement[0]);
	}

}
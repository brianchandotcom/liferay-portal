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

package com.liferay.layout.internal.template.util;

import com.liferay.layout.util.template.LayoutConverter;
import com.liferay.layout.util.template.LayoutData;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true, property = "layout.template.id=3_2_3_columns",
	service = LayoutConverter.class
)
public class ThreeTwoThreeColumnsLayoutConverter implements LayoutConverter {

	@Override
	public LayoutData convert(Layout layout) {
		return LayoutData.of(
			layout,
			layoutRow -> layoutRow.addLayoutColumns(
				layoutColumn -> {
					layoutColumn.addPortlets(
						LayoutTypePortletConstants.COLUMN_PREFIX + 1);
					layoutColumn.setSize(4);
				},
				layoutColumn -> {
					layoutColumn.addPortlets(
						LayoutTypePortletConstants.COLUMN_PREFIX + 2);
					layoutColumn.setSize(4);
				},
				layoutColumn -> {
					layoutColumn.addPortlets(
						LayoutTypePortletConstants.COLUMN_PREFIX + 3);
					layoutColumn.setSize(4);
				}),
			layoutRow -> layoutRow.addLayoutColumns(
				layoutColumn -> {
					layoutColumn.addPortlets(
						LayoutTypePortletConstants.COLUMN_PREFIX + 4);
					layoutColumn.setSize(4);
				},
				layoutColumn -> {
					layoutColumn.addPortlets(
						LayoutTypePortletConstants.COLUMN_PREFIX + 5);
					layoutColumn.setSize(8);
				}),
			layoutRow -> layoutRow.addLayoutColumns(
				layoutColumn -> {
					layoutColumn.addPortlets(
						LayoutTypePortletConstants.COLUMN_PREFIX + 6);
					layoutColumn.setSize(4);
				},
				layoutColumn -> {
					layoutColumn.addPortlets(
						LayoutTypePortletConstants.COLUMN_PREFIX + 7);
					layoutColumn.setSize(4);
				},
				layoutColumn -> {
					layoutColumn.addPortlets(
						LayoutTypePortletConstants.COLUMN_PREFIX + 8);
					layoutColumn.setSize(4);
				}));
	}

}
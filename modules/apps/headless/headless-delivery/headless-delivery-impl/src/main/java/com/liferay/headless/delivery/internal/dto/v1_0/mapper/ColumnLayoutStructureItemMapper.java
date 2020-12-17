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

import com.liferay.headless.delivery.dto.v1_0.ColumnViewport;
import com.liferay.headless.delivery.dto.v1_0.ColumnViewportDefinition;
import com.liferay.headless.delivery.dto.v1_0.PageColumnDefinition;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.layout.responsive.ViewportSize;
import com.liferay.layout.util.structure.ColumnLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 */
@Component(service = LayoutStructureItemMapper.class)
public class ColumnLayoutStructureItemMapper
	implements LayoutStructureItemMapper {

	@Override
	public String getClassName() {
		return ColumnLayoutStructureItem.class.getName();
	}

	@Override
	public PageElement getPageElement(
		long groupId, LayoutStructureItem layoutStructureItem,
		boolean saveInlineContent, boolean saveMappingConfiguration) {

		ColumnLayoutStructureItem columnLayoutStructureItem =
			(ColumnLayoutStructureItem)layoutStructureItem;

		return new PageElement() {
			{
				definition = new PageColumnDefinition() {
					{
						size = columnLayoutStructureItem.getSize();

						setColumnViewports(
							() -> {
								Map<String, JSONObject>
									columnViewportConfigurations =
										columnLayoutStructureItem.
											getViewportConfigurations();

								if (MapUtil.isEmpty(
										columnViewportConfigurations)) {

									return null;
								}

								ColumnViewport[] columnViewports =
									new ColumnViewport[3];

								columnViewports[0] = _toColumnViewport(
									columnViewportConfigurations,
									ViewportSize.MOBILE_LANDSCAPE);
								columnViewports[1] = _toColumnViewport(
									columnViewportConfigurations,
									ViewportSize.PORTRAIT_MOBILE);
								columnViewports[2] = _toColumnViewport(
									columnViewportConfigurations,
									ViewportSize.TABLET);

								return columnViewports;
							});
					}
				};
				type = Type.COLUMN;
			}
		};
	}

	private ColumnViewport _toColumnViewport(
		Map<String, JSONObject> columnViewportConfigurationsMap,
		ViewportSize viewportSize) {

		return new ColumnViewport() {
			{
				columnViewportDefinition =
					_toColumnViewportColumnViewportDefinition(
						columnViewportConfigurationsMap, viewportSize);
				id = viewportSize.getViewportSizeId();
			}
		};
	}

	private ColumnViewportDefinition _toColumnViewportColumnViewportDefinition(
		Map<String, JSONObject> columnViewportConfigurationsMap,
		ViewportSize viewportSize) {

		if (!columnViewportConfigurationsMap.containsKey(
				viewportSize.getViewportSizeId())) {

			return null;
		}

		JSONObject jsonObject = columnViewportConfigurationsMap.get(
			viewportSize.getViewportSizeId());

		return new ColumnViewportDefinition() {
			{
				setSize(
					() -> {
						if (!jsonObject.has("size")) {
							return null;
						}

						return jsonObject.getInt("size");
					});
			}
		};
	}

}
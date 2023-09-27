/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
									columnViewportConfigurationJSONObjects =
										columnLayoutStructureItem.
											getViewportConfigurationJSONObjects();

								if (MapUtil.isEmpty(
										columnViewportConfigurationJSONObjects)) {

									return null;
								}

								ColumnViewport[] columnViewports =
									new ColumnViewport[3];

								columnViewports[0] = _toColumnViewport(
									columnViewportConfigurationJSONObjects,
									ViewportSize.MOBILE_LANDSCAPE);
								columnViewports[1] = _toColumnViewport(
									columnViewportConfigurationJSONObjects,
									ViewportSize.PORTRAIT_MOBILE);
								columnViewports[2] = _toColumnViewport(
									columnViewportConfigurationJSONObjects,
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
		Map<String, JSONObject> columnViewportConfigurationJSONObjects,
		ViewportSize viewportSize) {

		return new ColumnViewport() {
			{
				columnViewportDefinition =
					_toColumnViewportColumnViewportDefinition(
						columnViewportConfigurationJSONObjects, viewportSize);
				id = viewportSize.getViewportSizeId();
			}
		};
	}

	private ColumnViewportDefinition _toColumnViewportColumnViewportDefinition(
		Map<String, JSONObject> columnViewportConfigurationJSONObjects,
		ViewportSize viewportSize) {

		if (!columnViewportConfigurationJSONObjects.containsKey(
				viewportSize.getViewportSizeId())) {

			return null;
		}

		JSONObject jsonObject = columnViewportConfigurationJSONObjects.get(
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
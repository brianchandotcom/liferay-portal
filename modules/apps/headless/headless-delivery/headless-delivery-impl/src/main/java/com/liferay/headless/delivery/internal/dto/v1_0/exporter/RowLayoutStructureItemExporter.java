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

package com.liferay.headless.delivery.internal.dto.v1_0.exporter;

import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.headless.delivery.dto.v1_0.PageRowDefinition;
import com.liferay.headless.delivery.dto.v1_0.RowViewport;
import com.liferay.headless.delivery.dto.v1_0.RowViewportDefinition;
import com.liferay.layout.responsive.ViewportSize;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.RowStyledLayoutStructureItem;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 */
@Component(service = LayoutStructureItemExporter.class)
public class RowLayoutStructureItemExporter
	extends BaseStyledLayoutStructureItemExporter {

	@Override
	public String getClassName() {
		return RowStyledLayoutStructureItem.class.getName();
	}

	@Override
	public PageElement getPageElement(
		long groupId, LayoutStructureItem layoutStructureItem,
		boolean saveInlineContent, boolean saveMappingConfiguration) {

		RowStyledLayoutStructureItem rowStyledLayoutStructureItem =
			(RowStyledLayoutStructureItem)layoutStructureItem;

		return new PageElement() {
			{
				definition = new PageRowDefinition() {
					{
						gutters = rowStyledLayoutStructureItem.isGutters();
						modulesPerRow =
							rowStyledLayoutStructureItem.getModulesPerRow();
						numberOfColumns =
							rowStyledLayoutStructureItem.getNumberOfColumns();
						reverseOrder = getReverseOrder();
						verticalAlignment = getVerticalAlignment();

						setFragmentStyle(
							() -> {
								JSONObject itemConfigJSONObject =
									rowStyledLayoutStructureItem.
										getItemConfigJSONObject();

								return toFragmentStyle(
									itemConfigJSONObject.getJSONObject(
										"styles"),
									saveMappingConfiguration);
							});
						setFragmentViewports(
							() -> {
								JSONObject itemConfigJSONObject =
									rowStyledLayoutStructureItem.
										getItemConfigJSONObject();

								return getFragmentViewPorts(
									itemConfigJSONObject);
							});
						setRowViewports(
							() -> {
								Map<String, JSONObject>
									rowViewportConfigurations =
										rowStyledLayoutStructureItem.
											getViewportConfigurations();

								if (MapUtil.isEmpty(
										rowViewportConfigurations)) {

									return null;
								}

								List<RowViewport> rowViewports =
									new ArrayList<RowViewport>() {
										{
											add(
												_toRowViewport(
													rowViewportConfigurations,
													ViewportSize.
														MOBILE_LANDSCAPE));
											add(
												_toRowViewport(
													rowViewportConfigurations,
													ViewportSize.
														PORTRAIT_MOBILE));
											add(
												_toRowViewport(
													rowViewportConfigurations,
													ViewportSize.TABLET));
										}
									};

								return rowViewports.toArray(new RowViewport[0]);
							});
					}
				};
				type = Type.ROW;
			}
		};
	}

	private RowViewport _toRowViewport(
		Map<String, JSONObject> rowViewportConfigurationsMap,
		ViewportSize viewportSize) {

		return new RowViewport() {
			{
				id = viewportSize.getViewportSizeId();
				rowViewportDefinition = _toRowViewportDefinition(
					rowViewportConfigurationsMap, viewportSize);
			}
		};
	}

	private RowViewportDefinition _toRowViewportDefinition(
		Map<String, JSONObject> rowViewportConfigurations,
		ViewportSize viewportSize) {

		if (!rowViewportConfigurations.containsKey(
				viewportSize.getViewportSizeId())) {

			return null;
		}

		JSONObject jsonObject = rowViewportConfigurations.get(
			viewportSize.getViewportSizeId());

		return new RowViewportDefinition() {
			{
				setModulesPerRow(
					() -> {
						if (!jsonObject.has("modulesPerRow")) {
							return null;
						}

						return jsonObject.getInt("modulesPerRow");
					});
				setReverseOrder(
					() -> {
						if (!jsonObject.has("reverseOrder")) {
							return null;
						}

						return jsonObject.getBoolean("reverseOrder");
					});
				setVerticalAlignment(
					() -> {
						if (!jsonObject.has("verticalAlignment")) {
							return null;
						}

						return jsonObject.getString("verticalAlignment");
					});
			}
		};
	}

}
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

import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.headless.delivery.dto.v1_0.PageRowDefinition;
import com.liferay.headless.delivery.dto.v1_0.RowViewport;
import com.liferay.headless.delivery.dto.v1_0.RowViewportDefinition;
import com.liferay.layout.responsive.ViewportSize;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.RowStyledLayoutStructureItem;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 */
@Component(service = LayoutStructureItemMapper.class)
public class RowLayoutStructureItemMapper
	extends BaseStyledLayoutStructureItemMapper {

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
						indexed = rowStyledLayoutStructureItem.isIndexed();
						modulesPerRow =
							rowStyledLayoutStructureItem.getModulesPerRow();
						numberOfColumns =
							rowStyledLayoutStructureItem.getNumberOfColumns();
						reverseOrder =
							rowStyledLayoutStructureItem.isReverseOrder();
						verticalAlignment =
							rowStyledLayoutStructureItem.getVerticalAlignment();

						setCssClasses(
							() -> {
								Set<String> cssClasses =
									rowStyledLayoutStructureItem.
										getCssClasses();

								if (SetUtil.isEmpty(cssClasses)) {
									return null;
								}

								return ArrayUtil.toStringArray(cssClasses);
							});
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
							() -> getFragmentViewPorts(
								rowStyledLayoutStructureItem.
									getItemConfigJSONObject()));
						setRowViewports(
							() -> {
								Map<String, JSONObject>
									rowViewportConfigurationJSONObjects =
										rowStyledLayoutStructureItem.
											getViewportConfigurationJSONObjects();

								if (MapUtil.isEmpty(
										rowViewportConfigurationJSONObjects)) {

									return null;
								}

								List<RowViewport> rowViewports =
									new ArrayList<RowViewport>() {
										{
											add(
												_toRowViewport(
													rowViewportConfigurationJSONObjects,
													ViewportSize.
														MOBILE_LANDSCAPE));
											add(
												_toRowViewport(
													rowViewportConfigurationJSONObjects,
													ViewportSize.
														PORTRAIT_MOBILE));
											add(
												_toRowViewport(
													rowViewportConfigurationJSONObjects,
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
		Map<String, JSONObject> rowViewportConfigurationJSONObjects,
		ViewportSize viewportSize) {

		return new RowViewport() {
			{
				id = viewportSize.getViewportSizeId();
				rowViewportDefinition = _toRowViewportDefinition(
					rowViewportConfigurationJSONObjects, viewportSize);
			}
		};
	}

	private RowViewportDefinition _toRowViewportDefinition(
		Map<String, JSONObject> rowViewportConfigurationJSONObjects,
		ViewportSize viewportSize) {

		if (!rowViewportConfigurationJSONObjects.containsKey(
				viewportSize.getViewportSizeId())) {

			return null;
		}

		JSONObject jsonObject = rowViewportConfigurationJSONObjects.get(
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
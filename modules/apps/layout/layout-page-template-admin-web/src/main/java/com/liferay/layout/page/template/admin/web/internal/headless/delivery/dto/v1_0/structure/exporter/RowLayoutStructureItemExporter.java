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

package com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.structure.exporter;

import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.headless.delivery.dto.v1_0.PageRowDefinition;
import com.liferay.headless.delivery.dto.v1_0.RowViewportConfig;
import com.liferay.headless.delivery.dto.v1_0.RowViewportConfigDefinition;
import com.liferay.layout.responsive.ViewportSize;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.RowLayoutStructureItem;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 */
@Component(service = LayoutStructureItemExporter.class)
public class RowLayoutStructureItemExporter
	implements LayoutStructureItemExporter {

	@Override
	public String getClassName() {
		return RowLayoutStructureItem.class.getName();
	}

	@Override
	public PageElement getPageElement(
		long groupId, LayoutStructureItem layoutStructureItem,
		boolean saveInlineContent, boolean saveMappingConfiguration) {

		RowLayoutStructureItem rowLayoutStructureItem =
			(RowLayoutStructureItem)layoutStructureItem;

		return new PageElement() {
			{
				definition = new PageRowDefinition() {
					{
						gutters = rowLayoutStructureItem.isGutters();
						modulesPerRow =
							rowLayoutStructureItem.getModulesPerRow();
						numberOfColumns =
							rowLayoutStructureItem.getNumberOfColumns();
						reverseOrder = getReverseOrder();
						verticalAlignment = getVerticalAlignment();

						setRowViewportConfig(
							() -> {
								Map<String, JSONObject>
									rowViewportConfigurations =
										rowLayoutStructureItem.
											getViewportConfigurations();

								if (MapUtil.isEmpty(
										rowViewportConfigurations)) {

									return null;
								}

								return new RowViewportConfig() {
									{
										landscapeMobile =
											_toRowViewportConfigDefinition(
												rowViewportConfigurations,
												ViewportSize.MOBILE_LANDSCAPE);
										portraitMobile =
											_toRowViewportConfigDefinition(
												rowViewportConfigurations,
												ViewportSize.PORTRAIT_MOBILE);
										tablet = _toRowViewportConfigDefinition(
											rowViewportConfigurations,
											ViewportSize.TABLET);
									}
								};
							});
					}
				};
				type = PageElement.Type.ROW;
			}
		};
	}

	private RowViewportConfigDefinition _toRowViewportConfigDefinition(
		Map<String, JSONObject> rowViewportConfigurations,
		ViewportSize viewportSize) {

		if (!rowViewportConfigurations.containsKey(
				viewportSize.getViewportSizeId())) {

			return null;
		}

		JSONObject jsonObject = rowViewportConfigurations.get(
			viewportSize.getViewportSizeId());

		return new RowViewportConfigDefinition() {
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
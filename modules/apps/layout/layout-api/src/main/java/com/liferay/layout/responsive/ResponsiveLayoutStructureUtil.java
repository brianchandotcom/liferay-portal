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

package com.liferay.layout.responsive;

import com.liferay.layout.util.structure.ColumnLayoutStructureItem;
import com.liferay.layout.util.structure.RowLayoutStructureItem;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;

/**
 * @author Pavel Savinov
 */
public class ResponsiveLayoutStructureUtil {

	public static String getColumnCssClass(
		RowLayoutStructureItem rowLayoutStructureItem,
		ColumnLayoutStructureItem columnLayoutStructureItem) {

		StringBundler sb = new StringBundler();

		int columnSize =
			columnLayoutStructureItem.getSize() *
				rowLayoutStructureItem.getNumberOfColumns() /
					rowLayoutStructureItem.getModulesPerRow();

		sb.append("col-lg-");
		sb.append(columnSize);

		Map<String, JSONObject> viewportSizeConfigurations =
			rowLayoutStructureItem.getViewportSizeConfigurations();

		for (ViewportSize viewportSize : ViewportSize.values()) {
			if (Objects.equals(viewportSize, ViewportSize.DESKTOP)) {
				continue;
			}

			int modulesPerRow = GetterUtil.getInteger(
				getResponsivePropertyValue(
					viewportSize, viewportSizeConfigurations, "modulesPerRow",
					rowLayoutStructureItem.getModulesPerRow()));

			columnSize =
				columnLayoutStructureItem.getSize() *
					rowLayoutStructureItem.getNumberOfColumns() / modulesPerRow;

			sb.append(StringPool.SPACE);
			sb.append("col");
			sb.append(viewportSize.getCssClassPrefix());
			sb.append(columnSize);
		}

		return sb.toString();
	}

	public static Object getResponsivePropertyValue(
		ViewportSize currentViewportSize,
		Map<String, JSONObject> viewportSizeConfigurations, String propertyName,
		Object defaultValue) {

		JSONObject viewportSizeConfigurationJSONObject =
			viewportSizeConfigurations.getOrDefault(
				currentViewportSize.getViewportSizeId(),
				JSONFactoryUtil.createJSONObject());

		if (viewportSizeConfigurationJSONObject.has(propertyName)) {
			return viewportSizeConfigurationJSONObject.get(propertyName);
		}

		ViewportSize[] viewportSizes = ViewportSize.values();

		Comparator comparator = Comparator.comparingInt(ViewportSize::getOrder);

		Arrays.sort(viewportSizes, comparator.reversed());

		for (ViewportSize viewportSize : viewportSizes) {
			viewportSizeConfigurationJSONObject =
				viewportSizeConfigurations.getOrDefault(
					viewportSize.getViewportSizeId(),
					JSONFactoryUtil.createJSONObject());

			if (viewportSizeConfigurationJSONObject.has(propertyName) &&
				(viewportSize.getOrder() < currentViewportSize.getOrder())) {

				return viewportSizeConfigurationJSONObject.get(propertyName);
			}
		}

		return defaultValue;
	}

	public static String getRowCssClass(
		RowLayoutStructureItem rowLayoutStructureItem) {

		StringBundler sb = new StringBundler();

		sb.append("align-items-lg-");
		sb.append(
			_getVerticalAlignmentCssClass(
				rowLayoutStructureItem.getVerticalAlignment()));

		Map<String, JSONObject> viewportSizeConfigurations =
			rowLayoutStructureItem.getViewportSizeConfigurations();

		for (ViewportSize viewportSize : ViewportSize.values()) {
			if (Objects.equals(viewportSize, ViewportSize.DESKTOP)) {
				continue;
			}

			String verticalAlignment = GetterUtil.getString(
				getResponsivePropertyValue(
					viewportSize, viewportSizeConfigurations,
					"verticalAlignment",
					rowLayoutStructureItem.getVerticalAlignment()));

			sb.append(StringPool.SPACE);
			sb.append("align-items");
			sb.append(viewportSize.getCssClassPrefix());
			sb.append(_getVerticalAlignmentCssClass(verticalAlignment));
		}

		sb.append(StringPool.SPACE);

		if (rowLayoutStructureItem.isReverseOrder() &&
			(rowLayoutStructureItem.getModulesPerRow() > 1)) {

			sb.append("flex-lg-row-reverse");
		}
		else if (rowLayoutStructureItem.isReverseOrder() &&
				 (rowLayoutStructureItem.getModulesPerRow() == 1)) {

			sb.append("flex-lg-column-reverse");
		}
		else {
			sb.append("flex-lg-row");
		}

		for (ViewportSize viewportSize : ViewportSize.values()) {
			if (Objects.equals(viewportSize, ViewportSize.DESKTOP)) {
				continue;
			}

			boolean reverseOrder = GetterUtil.getBoolean(
				getResponsivePropertyValue(
					viewportSize, viewportSizeConfigurations, "reverseOrder",
					rowLayoutStructureItem.isReverseOrder()));

			int modulesPerRow = GetterUtil.getInteger(
				getResponsivePropertyValue(
					viewportSize, viewportSizeConfigurations, "modulesPerRow",
					rowLayoutStructureItem.getModulesPerRow()));

			sb.append(StringPool.SPACE);

			if (reverseOrder) {
				sb.append("flex");
				sb.append(viewportSize.getCssClassPrefix());

				if (modulesPerRow > 1) {
					sb.append("row-reverse");
				}
				else if (modulesPerRow == 1) {
					sb.append("column-reverse");
				}
			}
			else {
				sb.append("flex");
				sb.append(viewportSize.getCssClassPrefix());
				sb.append("row");
			}
		}

		if (!rowLayoutStructureItem.isGutters()) {
			sb.append(StringPool.SPACE);
			sb.append("no-gutters");
		}

		return sb.toString();
	}

	private static String _getVerticalAlignmentCssClass(
		String verticalAlignment) {

		if (Objects.equals(verticalAlignment, "bottom")) {
			return "end";
		}
		else if (Objects.equals(verticalAlignment, "middle")) {
			return "center";
		}

		return "start";
	}

}
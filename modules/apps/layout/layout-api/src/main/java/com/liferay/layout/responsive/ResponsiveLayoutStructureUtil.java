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
import com.liferay.layout.util.structure.CommonStylesUtil;
import com.liferay.layout.util.structure.ContainerStyledLayoutStructureItem;
import com.liferay.layout.util.structure.RowStyledLayoutStructureItem;
import com.liferay.layout.util.structure.StyledLayoutStructureItem;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Pavel Savinov
 */
public class ResponsiveLayoutStructureUtil {

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #getColumnCssClass(ColumnLayoutStructureItem, RowStyledLayoutStructureItem)}
	 */
	@Deprecated
	public static String getColumnCssClass(
		ColumnLayoutStructureItem columnLayoutStructureItem) {

		return getColumnCssClass(columnLayoutStructureItem, null);
	}

	public static String getColumnCssClass(
		ColumnLayoutStructureItem columnLayoutStructureItem,
		RowStyledLayoutStructureItem rowStyledLayoutStructureItem) {

		StringBundler sb = new StringBundler();

		sb.append("col-lg-");
		sb.append(columnLayoutStructureItem.getSize());

		Map<String, JSONObject> columnViewportConfigurations =
			columnLayoutStructureItem.getViewportConfigurations();

		for (ViewportSize viewportSize : _viewportSizes) {
			if (Objects.equals(viewportSize, ViewportSize.DESKTOP)) {
				continue;
			}

			int columnSize = GetterUtil.getInteger(
				getResponsivePropertyValue(
					viewportSize, columnViewportConfigurations, "size",
					columnLayoutStructureItem.getSize()));

			sb.append(" col");
			sb.append(viewportSize.getCssClassPrefix());
			sb.append(columnSize);
		}

		if ((rowStyledLayoutStructureItem != null) &&
			Objects.equals(
				rowStyledLayoutStructureItem.getVerticalAlignment(),
				"middle")) {

			sb.append("d-flex flex-column ");
		}

		return sb.toString();
	}

	public static String getResponsiveCssClassValues(
			StyledLayoutStructureItem styledLayoutStructureItem)
		throws Exception {

		StringBundler sb = new StringBundler();

		Set<String> propertiesWithResponsiveValues = new HashSet<>();

		JSONObject itemConfigJSONObject =
			styledLayoutStructureItem.getItemConfigJSONObject();

		for (ViewportSize viewportSize : _sortedViewportSizes) {
			if (Objects.equals(viewportSize, ViewportSize.DESKTOP)) {
				continue;
			}

			JSONObject stylesJSONObject = _getStylesJSONObject(
				itemConfigJSONObject, viewportSize);

			for (String key : CommonStylesUtil.getResponsiveStyleNames()) {
				String value = stylesJSONObject.getString(key);

				if (Validator.isNull(value)) {
					if (!propertiesWithResponsiveValues.contains(key)) {
						continue;
					}

					value = GetterUtil.getString(
						CommonStylesUtil.getDefaultStyleValue(key));
				}

				propertiesWithResponsiveValues.add(key);

				if (styledLayoutStructureItem instanceof
						ContainerStyledLayoutStructureItem) {

					ContainerStyledLayoutStructureItem
						containerStyledLayoutStructureItem =
							(ContainerStyledLayoutStructureItem)
								styledLayoutStructureItem;

					if (Objects.equals(
							containerStyledLayoutStructureItem.getWidthType(),
							"fixed") &&
						(Objects.equals(key, "marginLeft") ||
						 Objects.equals(key, "marginRight"))) {

						continue;
					}
				}

				String cssClass = StringUtil.replace(
					CommonStylesUtil.getResponsiveTemplate(key),
					StringPool.OPEN_CURLY_BRACE, StringPool.CLOSE_CURLY_BRACE,
					HashMapBuilder.put(
						"value", value
					).put(
						"viewport", viewportSize.getCssClassPrefix()
					).build());

				if (sb.length() > 0) {
					sb.append(StringPool.SPACE);
				}

				sb.append(cssClass);
			}
		}

		return sb.toString();
	}

	public static Object getResponsivePropertyValue(
		ViewportSize currentViewportSize,
		Map<String, JSONObject> viewportConfigurations, String propertyName,
		Object defaultValue) {

		JSONObject viewportConfigurationJSONObject =
			viewportConfigurations.getOrDefault(
				currentViewportSize.getViewportSizeId(),
				JSONFactoryUtil.createJSONObject());

		if (viewportConfigurationJSONObject.has(propertyName)) {
			return viewportConfigurationJSONObject.get(propertyName);
		}

		for (ViewportSize viewportSize : _sortedViewportSizes) {
			if (viewportSize.getOrder() < currentViewportSize.getOrder()) {
				viewportConfigurationJSONObject =
					viewportConfigurations.getOrDefault(
						viewportSize.getViewportSizeId(),
						JSONFactoryUtil.createJSONObject());

				if (viewportConfigurationJSONObject.has(propertyName)) {
					return viewportConfigurationJSONObject.get(propertyName);
				}
			}
		}

		return defaultValue;
	}

	public static String getRowCssClass(
		RowStyledLayoutStructureItem rowStyledLayoutStructureItem) {

		StringBundler sb = new StringBundler();

		sb.append("align-items-lg-");
		sb.append(
			_getVerticalAlignmentCssClass(
				rowStyledLayoutStructureItem.getVerticalAlignment()));

		Map<String, JSONObject> rowViewportConfigurations =
			rowStyledLayoutStructureItem.getViewportConfigurations();

		for (ViewportSize viewportSize : _viewportSizes) {
			if (Objects.equals(viewportSize, ViewportSize.DESKTOP)) {
				continue;
			}

			String verticalAlignment = GetterUtil.getString(
				getResponsivePropertyValue(
					viewportSize, rowViewportConfigurations,
					"verticalAlignment",
					rowStyledLayoutStructureItem.getVerticalAlignment()));

			sb.append(" align-items");
			sb.append(viewportSize.getCssClassPrefix());
			sb.append(_getVerticalAlignmentCssClass(verticalAlignment));
		}

		sb.append(StringPool.SPACE);

		if (rowStyledLayoutStructureItem.isReverseOrder() &&
			(rowStyledLayoutStructureItem.getModulesPerRow() > 1)) {

			sb.append("flex-lg-row-reverse");
		}
		else if (rowStyledLayoutStructureItem.isReverseOrder() &&
				 (rowStyledLayoutStructureItem.getModulesPerRow() == 1)) {

			sb.append("flex-lg-column-reverse");
		}
		else {
			sb.append("flex-lg-row");
		}

		for (ViewportSize viewportSize : _viewportSizes) {
			if (Objects.equals(viewportSize, ViewportSize.DESKTOP)) {
				continue;
			}

			boolean reverseOrder = GetterUtil.getBoolean(
				getResponsivePropertyValue(
					viewportSize, rowViewportConfigurations, "reverseOrder",
					rowStyledLayoutStructureItem.isReverseOrder()));

			int modulesPerRow = GetterUtil.getInteger(
				getResponsivePropertyValue(
					viewportSize, rowViewportConfigurations, "modulesPerRow",
					rowStyledLayoutStructureItem.getModulesPerRow()));

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

		if (!rowStyledLayoutStructureItem.isGutters()) {
			sb.append(" no-gutters");
		}

		return sb.toString();
	}

	private static JSONObject _getStylesJSONObject(
		JSONObject itemConfigJSONObject, ViewportSize currentViewportSize) {

		JSONObject stylesJSONObject = JSONFactoryUtil.createJSONObject();

		for (ViewportSize viewportSize : _sortedViewportSizes) {
			if (viewportSize.getOrder() > currentViewportSize.getOrder()) {
				continue;
			}

			JSONObject jsonObject = null;

			if (Objects.equals(viewportSize, ViewportSize.DESKTOP)) {
				jsonObject = itemConfigJSONObject.getJSONObject("styles");
			}
			else {
				JSONObject viewportJSONObject =
					itemConfigJSONObject.getJSONObject(
						viewportSize.getViewportSizeId());

				if (viewportJSONObject == null) {
					continue;
				}

				jsonObject = viewportJSONObject.getJSONObject("styles");
			}

			Set<String> keys = jsonObject.keySet();

			for (String key : keys) {
				if (!jsonObject.isNull(key) &&
					Validator.isNotNull(jsonObject.get(key)) &&
					Validator.isNull(stylesJSONObject.get(key))) {

					stylesJSONObject.put(key, jsonObject.get(key));
				}
			}
		}

		return stylesJSONObject;
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

	private static final ViewportSize[] _sortedViewportSizes =
		ViewportSize.values();
	private static final ViewportSize[] _viewportSizes = ViewportSize.values();

	static {
		Comparator<ViewportSize> comparator = Comparator.comparingInt(
			ViewportSize::getOrder);

		Arrays.sort(_sortedViewportSizes, comparator.reversed());
	}

}
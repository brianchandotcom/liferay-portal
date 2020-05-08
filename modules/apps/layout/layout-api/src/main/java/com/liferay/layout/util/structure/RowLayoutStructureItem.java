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

package com.liferay.layout.util.structure;

import com.liferay.layout.responsive.ViewportSize;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Eudaldo Alonso
 */
public class RowLayoutStructureItem extends LayoutStructureItem {

	public RowLayoutStructureItem(String parentItemId) {
		super(parentItemId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof RowLayoutStructureItem)) {
			return false;
		}

		RowLayoutStructureItem rowLayoutStructureItem =
			(RowLayoutStructureItem)obj;

		if (!Objects.equals(_gutters, rowLayoutStructureItem._gutters) ||
			!Objects.equals(
				_modulesPerRow, rowLayoutStructureItem._modulesPerRow) ||
			!Objects.equals(
				_numberOfColumns, rowLayoutStructureItem._numberOfColumns) ||
			!Objects.equals(
				_reverseOrder, rowLayoutStructureItem._reverseOrder) ||
			!Objects.equals(
				_verticalAlignment,
				rowLayoutStructureItem._verticalAlignment)) {

			return false;
		}

		return super.equals(obj);
	}

	@Override
	public JSONObject getItemConfigJSONObject() {
		JSONObject jsonObject = JSONUtil.put(
			"gutters", _gutters
		).put(
			"modulesPerRow", _modulesPerRow
		).put(
			"numberOfColumns", _numberOfColumns
		).put(
			"reverseOrder", _reverseOrder
		).put(
			"verticalAlignment", _verticalAlignment
		);

		for (ViewportSize viewportSize : ViewportSize.values()) {
			if (viewportSize.equals(ViewportSize.DESKTOP)) {
				continue;
			}

			JSONObject configurationJSONObject =
				_viewportSizeConfigurations.getOrDefault(
					viewportSize.getViewportSizeId(),
					JSONFactoryUtil.createJSONObject());

			jsonObject.put(
				viewportSize.getViewportSizeId(),
				JSONUtil.put(
					"gutters",
					configurationJSONObject.getBoolean("gutters", _gutters)
				).put(
					"modulesPerRow",
					configurationJSONObject.get("modulesPerRow")
				).put(
					"numberOfColumns",
					configurationJSONObject.getInt(
						"numberOfColumns", _numberOfColumns)
				).put(
					"reverseOrder", configurationJSONObject.get("reverseOrder")
				).put(
					"verticalAlignment",
					configurationJSONObject.get("verticalAlignment")
				));
		}

		return jsonObject;
	}

	@Override
	public String getItemType() {
		return LayoutDataItemTypeConstants.TYPE_ROW;
	}

	public int getModulesPerRow() {
		return _modulesPerRow;
	}

	public int getNumberOfColumns() {
		return _numberOfColumns;
	}

	public String getVerticalAlignment() {
		return _verticalAlignment;
	}

	public Map<String, JSONObject> getViewportSizeConfigurations() {
		return _viewportSizeConfigurations;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, getItemId());
	}

	public boolean isGutters() {
		return _gutters;
	}

	public boolean isReverseOrder() {
		return _reverseOrder;
	}

	public void setGutters(boolean gutters) {
		_gutters = gutters;
	}

	public void setModulesPerRow(int modulesPerRow) {
		_modulesPerRow = modulesPerRow;
	}

	public void setNumberOfColumns(int numberOfColumns) {
		_numberOfColumns = numberOfColumns;
	}

	public void setReverseOrder(boolean reverseOrder) {
		_reverseOrder = reverseOrder;
	}

	public void setVerticalAlignment(String verticalAlignment) {
		_verticalAlignment = verticalAlignment;
	}

	public void setViewportSizeConfiguration(
		String viewportSizeId, JSONObject configurationJSONObject) {

		JSONObject currentConfigurationJSONObject =
			_viewportSizeConfigurations.getOrDefault(
				viewportSizeId, JSONFactoryUtil.createJSONObject());

		if (configurationJSONObject.has("gutters")) {
			currentConfigurationJSONObject.put(
				"gutters", configurationJSONObject.getBoolean("gutters"));
		}

		if (configurationJSONObject.has("modulesPerRow")) {
			currentConfigurationJSONObject.put(
				"modulesPerRow",
				configurationJSONObject.getInt("modulesPerRow"));
		}

		if (configurationJSONObject.has("numberOfColumns")) {
			currentConfigurationJSONObject.put(
				"numberOfColumns",
				configurationJSONObject.getInt("numberOfColumns"));
		}

		if (configurationJSONObject.has("reverseOrder")) {
			currentConfigurationJSONObject.put(
				"reverseOrder",
				configurationJSONObject.getBoolean("reverseOrder"));
		}

		if (configurationJSONObject.has("verticalAlignment")) {
			currentConfigurationJSONObject.put(
				"verticalAlignment",
				configurationJSONObject.getString("verticalAlignment"));
		}

		_viewportSizeConfigurations.put(
			viewportSizeId, currentConfigurationJSONObject);
	}

	@Override
	public void updateItemConfig(JSONObject itemConfigJSONObject) {
		if (itemConfigJSONObject.has("gutters")) {
			setGutters(itemConfigJSONObject.getBoolean("gutters"));
		}

		if (itemConfigJSONObject.has("modulesPerRow")) {
			setModulesPerRow(itemConfigJSONObject.getInt("modulesPerRow"));
		}

		if (itemConfigJSONObject.has("numberOfColumns")) {
			setNumberOfColumns(itemConfigJSONObject.getInt("numberOfColumns"));
		}

		if (itemConfigJSONObject.has("reverseOrder")) {
			setReverseOrder(itemConfigJSONObject.getBoolean("reverseOrder"));
		}

		if (itemConfigJSONObject.has("verticalAlignment")) {
			setVerticalAlignment(
				itemConfigJSONObject.getString("verticalAlignment"));
		}

		for (ViewportSize viewportSize : ViewportSize.values()) {
			if (viewportSize.equals(ViewportSize.DESKTOP)) {
				continue;
			}

			if (itemConfigJSONObject.has(viewportSize.getViewportSizeId())) {
				setViewportSizeConfiguration(
					viewportSize.getViewportSizeId(),
					itemConfigJSONObject.getJSONObject(
						viewportSize.getViewportSizeId()));
			}
		}
	}

	private boolean _gutters = true;
	private int _modulesPerRow = 3;
	private int _numberOfColumns;
	private boolean _reverseOrder;
	private String _verticalAlignment = "top";
	private Map<String, JSONObject> _viewportSizeConfigurations =
		new HashMap<>();

}
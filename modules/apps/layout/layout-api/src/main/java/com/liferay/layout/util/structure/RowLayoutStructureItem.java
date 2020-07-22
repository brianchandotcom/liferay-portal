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
public class RowLayoutStructureItem extends StyledLayoutStructureItem {

	public RowLayoutStructureItem(String parentItemId) {
		super(parentItemId);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof RowLayoutStructureItem)) {
			return false;
		}

		RowLayoutStructureItem rowLayoutStructureItem =
			(RowLayoutStructureItem)object;

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

		return super.equals(object);
	}

	@Override
	public JSONObject getItemConfigJSONObject() {
		JSONObject jsonObject = super.getItemConfigJSONObject();

		jsonObject.put(
			"gutters", _gutters
		).put(
			"modulesPerRow", getModulesPerRow()
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

			JSONObject viewportConfigurationJSONObject =
				_viewportConfigurations.getOrDefault(
					viewportSize.getViewportSizeId(),
					JSONFactoryUtil.createJSONObject());

			jsonObject.put(
				viewportSize.getViewportSizeId(),
				JSONUtil.put(
					"modulesPerRow",
					viewportConfigurationJSONObject.get("modulesPerRow")
				).put(
					"reverseOrder",
					viewportConfigurationJSONObject.get("reverseOrder")
				).put(
					"verticalAlignment",
					viewportConfigurationJSONObject.get("verticalAlignment")
				));
		}

		return jsonObject;
	}

	@Override
	public String getItemType() {
		return LayoutDataItemTypeConstants.TYPE_ROW;
	}

	public int getModulesPerRow() {
		if (_modulesPerRow == null) {
			return _numberOfColumns;
		}

		return _modulesPerRow;
	}

	public int getNumberOfColumns() {
		return _numberOfColumns;
	}

	public String getVerticalAlignment() {
		return _verticalAlignment;
	}

	public Map<String, JSONObject> getViewportConfigurations() {
		return _viewportConfigurations;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getViewportConfigurations()}
	 */
	@Deprecated
	public Map<String, JSONObject> getViewportSizeConfigurations() {
		return getViewportConfigurations();
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

	public void setViewportConfiguration(
		String viewportSizeId, JSONObject configurationJSONObject) {

		JSONObject currentConfigurationJSONObject =
			_viewportConfigurations.getOrDefault(
				viewportSizeId, JSONFactoryUtil.createJSONObject());

		if (configurationJSONObject.has("modulesPerRow")) {
			currentConfigurationJSONObject.put(
				"modulesPerRow",
				configurationJSONObject.getInt("modulesPerRow"));
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

		_viewportConfigurations.put(
			viewportSizeId, currentConfigurationJSONObject);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #setViewportConfiguration(String, JSONObject)}
	 */
	@Deprecated
	public void setViewportSizeConfiguration(
		String viewportSizeId, JSONObject configurationJSONObject) {

		setViewportConfiguration(viewportSizeId, configurationJSONObject);
	}

	@Override
	public void updateItemConfig(JSONObject itemConfigJSONObject) {
		super.updateItemConfig(itemConfigJSONObject);

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
				setViewportConfiguration(
					viewportSize.getViewportSizeId(),
					itemConfigJSONObject.getJSONObject(
						viewportSize.getViewportSizeId()));
			}
		}
	}

	private boolean _gutters = true;
	private Integer _modulesPerRow;
	private int _numberOfColumns;
	private boolean _reverseOrder;
	private String _verticalAlignment = "top";
	private Map<String, JSONObject> _viewportConfigurations = new HashMap<>();

}
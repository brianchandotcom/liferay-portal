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
public class ColumnLayoutStructureItem extends LayoutStructureItem {

	public ColumnLayoutStructureItem(String parentItemId) {
		super(parentItemId);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ColumnLayoutStructureItem)) {
			return false;
		}

		ColumnLayoutStructureItem columnLayoutStructureItem =
			(ColumnLayoutStructureItem)object;

		if (!Objects.equals(_size, columnLayoutStructureItem._size)) {
			return false;
		}

		return super.equals(object);
	}

	@Override
	public JSONObject getItemConfigJSONObject() {
		JSONObject jsonObject = JSONUtil.put("size", _size);

		for (ViewportSize viewportSize : _viewportSizes) {
			if (viewportSize.equals(ViewportSize.DESKTOP)) {
				continue;
			}

			jsonObject.put(
				viewportSize.getViewportSizeId(),
				JSONUtil.put(
					"size",
					() -> {
						JSONObject viewportConfigurationJSONObject =
							_viewportConfigurations.getOrDefault(
								viewportSize.getViewportSizeId(),
								JSONFactoryUtil.createJSONObject());

						return viewportConfigurationJSONObject.get("size");
					}));
		}

		return jsonObject;
	}

	@Override
	public String getItemType() {
		return LayoutDataItemTypeConstants.TYPE_COLUMN;
	}

	public int getSize() {
		return _size;
	}

	public Map<String, JSONObject> getViewportConfigurations() {
		return _viewportConfigurations;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, getItemId());
	}

	public void setSize(int size) {
		_size = size;
	}

	public void setViewportConfiguration(
		String viewportSizeId, JSONObject configurationJSONObject) {

		_viewportConfigurations.put(
			viewportSizeId,
			_viewportConfigurations.getOrDefault(
				viewportSizeId, JSONFactoryUtil.createJSONObject()
			).put(
				"size",
				() -> {
					if (configurationJSONObject.has("size")) {
						return configurationJSONObject.getInt("size");
					}

					return null;
				}
			));
	}

	@Override
	public void updateItemConfig(JSONObject itemConfigJSONObject) {
		if (itemConfigJSONObject.has("size")) {
			setSize(itemConfigJSONObject.getInt("size"));
		}

		for (ViewportSize viewportSize : _viewportSizes) {
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

	private static final ViewportSize[] _viewportSizes = ViewportSize.values();

	private int _size;
	private final Map<String, JSONObject> _viewportConfigurations =
		new HashMap<>();

}
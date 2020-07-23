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

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.List;
import java.util.Map;

/**
 * @author Pavel Savinov
 */
public abstract class StyledLayoutStructureItem extends LayoutStructureItem {

	public StyledLayoutStructureItem(String parentItemId) {
		super(parentItemId);
	}

	@Override
	public JSONObject getItemConfigJSONObject() {
		try {
			Map<String, Object> defaultValues =
				CommonStylesUtil.getDefaultStyleValues();

			for (Map.Entry<String, Object> entry : defaultValues.entrySet()) {
				if (!stylesJSONObject.has(entry.getKey())) {
					stylesJSONObject.put(entry.getKey(), entry.getValue());
				}
			}
		}
		catch (Exception exception) {
			_log.error("Unable to get default style values", exception);
		}

		return JSONUtil.put("styles", stylesJSONObject);
	}

	@Override
	public void updateItemConfig(JSONObject itemConfigJSONObject) {
		if (itemConfigJSONObject.has("styles")) {
			JSONObject newStylesJSONObject = itemConfigJSONObject.getJSONObject(
				"styles");

			try {
				List<String> availableStyleNames =
					CommonStylesUtil.getAvailableStyleNames();

				for (String styleName : availableStyleNames) {
					if (newStylesJSONObject.has(styleName)) {
						stylesJSONObject.put(
							styleName, newStylesJSONObject.get(styleName));
					}
				}
			}
			catch (Exception exception) {
				_log.error("Unable to get available style names", exception);
			}
		}
	}

	protected JSONObject stylesJSONObject = JSONFactoryUtil.createJSONObject();

	private static final Log _log = LogFactoryUtil.getLog(
		StyledLayoutStructureItem.class);

}
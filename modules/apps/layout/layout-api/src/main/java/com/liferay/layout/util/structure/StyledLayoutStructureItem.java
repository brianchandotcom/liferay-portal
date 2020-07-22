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

/**
 * @author Pavel Savinov
 */
public abstract class StyledLayoutStructureItem extends LayoutStructureItem {

	public StyledLayoutStructureItem(String parentItemId) {
		super(parentItemId);
	}

	@Override
	public JSONObject getItemConfigJSONObject() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (stylesJSONObject.length() > 0) {
			jsonObject.put("styles", stylesJSONObject);
		}

		return jsonObject;
	}

	@Override
	public void updateItemConfig(JSONObject itemConfigJSONObject) {
		if (itemConfigJSONObject.has("styles")) {
			stylesJSONObject = itemConfigJSONObject.getJSONObject("styles");
		}
	}

	protected JSONObject stylesJSONObject = JSONFactoryUtil.createJSONObject();

}
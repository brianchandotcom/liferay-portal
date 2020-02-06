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

import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

/**
 * @author Eudaldo Alonso
 */
public class RootLayoutStructureItem extends LayoutStructureItem {

	public RootLayoutStructureItem() {
		super(StringPool.BLANK);
	}

	@Override
	public String getItemType() {
		return LayoutDataItemTypeConstants.TYPE_ROOT;
	}

	@Override
	public void updateItemConfig(JSONObject itemConfigJSONObject) {
	}

	@Override
	protected JSONObject getItemConfigJSONObject() {
		return JSONFactoryUtil.createJSONObject();
	}

}
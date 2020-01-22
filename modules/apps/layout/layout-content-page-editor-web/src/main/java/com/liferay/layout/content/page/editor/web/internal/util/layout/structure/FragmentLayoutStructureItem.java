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

package com.liferay.layout.content.page.editor.web.internal.util.layout.structure;

import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

/**
 * @author Eudaldo Alonso
 */
public class FragmentLayoutStructureItem extends LayoutStructureItem {

	public FragmentLayoutStructureItem(String parentItemId) {
		super(parentItemId);
	}

	public long getFragmentEntryLinkId() {
		return _fragmentEntryLinkId;
	}

	@Override
	public String getItemType() {
		return LayoutDataItemTypeConstants.TYPE_FRAGMENT;
	}

	public void setFragmentEntryLinkId(long fragmentEntryLinkId) {
		_fragmentEntryLinkId = fragmentEntryLinkId;
	}

	@Override
	public void updateItemConfig(JSONObject itemConfigJSONObject) {
		if (itemConfigJSONObject.has("fragmentEntryLinkId")) {
			setFragmentEntryLinkId(
				itemConfigJSONObject.getLong("fragmentEntryLinkId"));
		}
	}

	@Override
	protected JSONObject getItemConfigJSONObject() {
		return JSONUtil.put("fragmentEntryLinkId", _fragmentEntryLinkId);
	}

	private long _fragmentEntryLinkId;

}
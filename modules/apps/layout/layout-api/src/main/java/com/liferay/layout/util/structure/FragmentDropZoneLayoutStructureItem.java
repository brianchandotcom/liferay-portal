/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.util.structure;

import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

/**
 * @author Eudaldo Alonso
 */
public class FragmentDropZoneLayoutStructureItem extends LayoutStructureItem {

	public FragmentDropZoneLayoutStructureItem(String parentItemId) {
		super(parentItemId);
	}

	public String getFragmentDropZoneId() {
		return _fragmentDropZoneId;
	}

	@Override
	public JSONObject getItemConfigJSONObject() {
		return JSONUtil.put("fragmentDropZoneId", _fragmentDropZoneId);
	}

	@Override
	public String getItemType() {
		return LayoutDataItemTypeConstants.TYPE_FRAGMENT_DROP_ZONE;
	}

	public void setFragmentDropZoneId(String fragmentDropZoneId) {
		_fragmentDropZoneId = fragmentDropZoneId;
	}

	@Override
	public void updateItemConfig(JSONObject itemConfigJSONObject) {
		if (itemConfigJSONObject.has("fragmentDropZoneId")) {
			setFragmentDropZoneId(
				itemConfigJSONObject.getString("fragmentDropZoneId"));
		}
	}

	private String _fragmentDropZoneId;

}
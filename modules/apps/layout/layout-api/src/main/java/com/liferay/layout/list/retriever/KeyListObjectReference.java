/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.list.retriever;

import com.liferay.portal.kernel.json.JSONObject;

/**
 * @author Eudaldo Alonso
 */
public class KeyListObjectReference implements ListObjectReference {

	public KeyListObjectReference(JSONObject jsonObject) {
		_key = jsonObject.getString("key");
		_title = jsonObject.getString("title");
		_itemType = jsonObject.getString("itemType");
	}

	@Override
	public String getItemType() {
		return _itemType;
	}

	public String getKey() {
		return _key;
	}

	public String getTitle() {
		return _title;
	}

	private final String _itemType;
	private final String _key;
	private final String _title;

}
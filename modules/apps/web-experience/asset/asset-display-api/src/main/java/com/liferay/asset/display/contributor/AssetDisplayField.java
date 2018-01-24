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

package com.liferay.asset.display.contributor;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.HashUtil;

import java.util.Objects;

/**
 * @author Jürgen Kappler
 */
public class AssetDisplayField {

	public AssetDisplayField(String key, String label) {
		_key = key;
		_label = label;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetDisplayField)) {
			return false;
		}

		AssetDisplayField assetDisplayField = (AssetDisplayField)obj;

		if (Objects.equals(_key, assetDisplayField._key) &&
			Objects.equals(_label, assetDisplayField._label)) {

			return true;
		}

		return false;
	}

	public String getKey() {
		return _key;
	}

	public String getLabel() {
		return _label;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _key);

		return HashUtil.hash(hash, _label);
	}

	public JSONObject toJSONObject() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("key", getKey());
		jsonObject.put("label", getLabel());

		return jsonObject;
	}

	private final String _key;
	private final String _label;

}
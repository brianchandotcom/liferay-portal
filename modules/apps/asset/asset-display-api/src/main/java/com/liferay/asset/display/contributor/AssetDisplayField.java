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

import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.Objects;

/**
 * @author     Jürgen Kappler
 * @deprecated As of Judson (7.1.x), replaced by {@link
 *             com.liferay.info.display.contributor.InfoDisplayField}
 */
@Deprecated
public class AssetDisplayField {

	public AssetDisplayField(String key, String label) {
		this(key, label, _TYPE);
	}

	public AssetDisplayField(String key, String label, String type) {
		_key = key;
		_label = label;
		_type = type;
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
			Objects.equals(_label, assetDisplayField._label) &&
			Objects.equals(_type, assetDisplayField._type)) {

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

	public String getType() {
		return _type;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _key);

		hash = HashUtil.hash(hash, _label);

		return HashUtil.hash(hash, _type);
	}

	public JSONObject toJSONObject() {
		return JSONUtil.put(
			"key", getKey()
		).put(
			"label", getLabel()
		).put(
			"type", getType()
		);
	}

	private static final String _TYPE = "text";

	private final String _key;
	private final String _label;
	private final String _type;

}
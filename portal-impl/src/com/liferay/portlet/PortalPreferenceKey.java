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

package com.liferay.portlet;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Preston Crary
 */
public class PortalPreferenceKey implements Serializable {

	public PortalPreferenceKey(String namespace, String key) {
		if (key == null) {
			throw new NullPointerException("Key is null");
		}

		if (Validator.isNull(namespace)) {
			_namespace = null;
		}
		else {
			_namespace = namespace;
		}

		_key = key;
	}

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}

		if (!(object instanceof PortalPreferenceKey)) {
			return false;
		}

		PortalPreferenceKey portalPreferenceKey = (PortalPreferenceKey)object;

		if (Objects.equals(portalPreferenceKey._namespace, _namespace) &&
			Objects.equals(portalPreferenceKey._key, _key)) {

			return true;
		}

		return false;
	}

	public String getKey() {
		return _key;
	}

	public String getNamespace() {
		return _namespace;
	}

	public String getNamespacedKey() {
		if (_namespace == null) {
			return _key;
		}

		return StringBundler.concat(_namespace, StringPool.POUND, _key);
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _namespace);

		return HashUtil.hash(hash, _key);
	}

	public boolean matchNamespace(String namespace) {
		if (Objects.equals(namespace, _namespace) ||
			(Validator.isNull(namespace) && (_namespace == null))) {

			return true;
		}

		return false;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{key=", _key, ", namespace=", _namespace, "}");
	}

	private final String _key;
	private final String _namespace;

}
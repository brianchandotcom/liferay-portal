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

import java.util.Objects;

/**
 * @author Preston Crary
 */
public class PortalPreferenceKey {

	public PortalPreferenceKey(String namespace, String key) {
		if (key == null) {
			throw new NullPointerException("Key is null");
		}

		_namespace = namespace;
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

		if ((Objects.equals(portalPreferenceKey._namespace, _namespace) ||
			 (Validator.isNull(portalPreferenceKey._namespace) &&
			  Validator.isNull(_namespace))) &&
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

	@Override
	public int hashCode() {
		int hash = 0;

		if (Validator.isNotNull(_namespace)) {
			hash = HashUtil.hash(0, _namespace);
		}

		return HashUtil.hash(hash, _key);
	}

	@Override
	public String toString() {
		if (Validator.isNull(_namespace)) {
			return _key;
		}

		return StringBundler.concat(_namespace, StringPool.POUND, _key);
	}

	private final String _key;
	private final String _namespace;

}
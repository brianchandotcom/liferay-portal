/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.closure;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;

/**
 * @author Preston Crary
 */
public class Node {

	public static final Node ROOT_NODE = new Node(0, 0);

	public Node(long classNameId, long primaryKey) {
		_classNameId = classNameId;
		_primaryKey = primaryKey;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Node)) {
			return false;
		}

		Node node = (Node)object;

		if ((_classNameId == node._classNameId) &&
			(_primaryKey == node._primaryKey)) {

			return true;
		}

		return false;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public long getPrimaryKey() {
		return _primaryKey;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _classNameId);

		return HashUtil.hash(hash, _primaryKey);
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{classNameId=", _classNameId, ", primaryKey=", _primaryKey, "}");
	}

	private final long _classNameId;
	private final long _primaryKey;

}
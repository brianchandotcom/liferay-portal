/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.cache.index;

import com.liferay.petra.lang.HashUtil;

import java.io.Serializable;

/**
 * @author Preston Crary
 */
public class TestKey implements Serializable {

	public TestKey(long indexedLong, long unindexedLong) {
		_indexedLong = indexedLong;
		_unindexedLong = unindexedLong;
	}

	@Override
	public boolean equals(Object object) {
		TestKey testKey = (TestKey)object;

		if ((testKey._indexedLong == _indexedLong) &&
			(testKey._unindexedLong == _unindexedLong)) {

			return true;
		}

		return false;
	}

	public Long getIndexedLong() {
		return _indexedLong;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, _indexedLong);

		return HashUtil.hash(hashCode, _unindexedLong);
	}

	private final long _indexedLong;
	private final long _unindexedLong;

}
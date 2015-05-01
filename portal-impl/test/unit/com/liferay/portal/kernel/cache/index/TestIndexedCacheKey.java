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

package com.liferay.portal.kernel.cache.index;

import com.liferay.portal.kernel.util.HashUtil;

/**
 * @author Preston Crary
 */
public class TestIndexedCacheKey implements IndexedCacheKey<Long> {

	public TestIndexedCacheKey(long indexedLong, long nonIndexedLong) {
		_indexedLong = indexedLong;
		_nonIndexedLong = nonIndexedLong;
	}

	@Override
	public boolean equals(Object obj) {
		TestIndexedCacheKey testIndexedCacheKey = (TestIndexedCacheKey)obj;

		if ((testIndexedCacheKey._indexedLong == _indexedLong) &&
			(testIndexedCacheKey._nonIndexedLong == _nonIndexedLong)) {

			return true;
		}

		return false;
	}

	@Override
	public Long getIndex() {
		return _indexedLong;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, _indexedLong);

		return HashUtil.hash(hashCode, _nonIndexedLong);
	}

	private final long _indexedLong;
	private final long _nonIndexedLong;

}
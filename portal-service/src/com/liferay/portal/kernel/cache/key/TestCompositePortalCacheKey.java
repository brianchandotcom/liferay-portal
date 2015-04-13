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

package com.liferay.portal.kernel.cache.key;

/**
 * @author Preston Crary
 */
public class TestCompositePortalCacheKey implements CompositePortalCacheKey {

	public TestCompositePortalCacheKey(String[] keyParts) {
		_keyParts = keyParts;
	}

	@Override
	public String getSimpleKey() {
		return _keyParts[0];
	}

	private final String[] _keyParts;

}
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.cache.ehcache;

import net.sf.ehcache.Ehcache;

/**
 * <p>
 * See http://issues.liferay.com/browse/LPS-11061.
 * </p>
 *
 * @author Shuyang Zhou
 */
public class JGroupsBootstrapCacheLoader
	extends net.sf.ehcache.distribution.jgroups.JGroupsBootstrapCacheLoader {

	public JGroupsBootstrapCacheLoader(
		boolean asynchronous, int maximumChunkSize) {

		super(asynchronous, maximumChunkSize);
	}

	@Override
	public Object clone() {
		return new JGroupsBootstrapCacheLoader(
			asynchronous, maximumChunkSizeBytes);
	}

	@Override
	public void load(Ehcache cache) {
		return;
	}

}
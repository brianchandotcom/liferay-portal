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

package com.liferay.portal.velocity;

import java.util.Iterator;

import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.ResourceCache;

/**
 * @author Brian Wing Shun Chan
 */
public class LiferayResourceCache implements ResourceCache {

	public Iterator<Object> enumerateKeys() {
		throw new RuntimeException("enumerateKeys is not implemented");
	}

	public Resource get(Object key) {
		return LiferayResourceCacheUtil.get(key.toString());
	}

	public void initialize(RuntimeServices rs) {
	}

	public Resource put(Object key, Resource resource) {
		LiferayResourceCacheUtil.put(key.toString(), resource);

		return resource;
	}

	public Resource remove(Object key) {
		Resource resource = get(key);

		if (resource != null) {
			LiferayResourceCacheUtil.remove(key.toString());
		}

		return resource;
	}

}
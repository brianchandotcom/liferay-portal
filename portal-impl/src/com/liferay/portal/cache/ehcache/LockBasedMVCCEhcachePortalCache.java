/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.model.MVCCModel;

import java.io.Serializable;

import net.sf.ehcache.Element;

/**
 * @author Shuyang Zhou
 */
public class LockBasedMVCCEhcachePortalCache
		<K extends Serializable, V extends MVCCModel>
	extends MVCCEhcachePortalCache<K, V> {

	public LockBasedMVCCEhcachePortalCache(
		EhcachePortalCache<K, V> ehcachePortalCache) {

		super(ehcachePortalCache);
	}

	@Override
	public void remove(K key) {
		ehcache.acquireWriteLockOnKey(key);

		try {
			super.remove(key);
		}
		finally {
			ehcache.releaseWriteLockOnKey(key);
		}
	}

	@Override
	protected void doPut(K key, V value, boolean quiet, int timeToLive) {
		Element newElement = null;

		if (timeToLive >= 0) {
			newElement = new Element(key, value, timeToLive);
		}
		else {
			newElement = new Element(key, value);
		}

		ehcache.acquireWriteLockOnKey(key);

		try {
			Element oldElement = ehcache.get(key);

			if (oldElement == null) {
				ehcache.put(newElement, quiet);

				return;
			}

			V oldValue = (V)oldElement.getObjectValue();

			if ((oldValue != null) &&
				(value.getMvccVersion() <= oldValue.getMvccVersion())) {

				return;
			}

			ehcache.put(newElement, quiet);
		}
		finally {
			ehcache.releaseWriteLockOnKey(key);
		}
	}

}
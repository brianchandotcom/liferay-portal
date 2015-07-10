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

package com.liferay.portal.cache;

import com.liferay.portal.kernel.cache.AbstractPortalCache;
import com.liferay.portal.kernel.cache.PortalCacheManager;

import java.io.Serializable;

import java.util.Collections;
import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class DummyPortalCache<K extends Serializable, V>
	extends AbstractPortalCache<K, V> {

	public DummyPortalCache(
		PortalCacheManager<K, V> portalCacheManager, String portalCacheName) {

		super(portalCacheManager);

		_portalCacheName = portalCacheName;
	}

	@Override
	public List<K> getKeys() {
		return Collections.emptyList();
	}

	@Override
	public String getName() {
		return _portalCacheName;
	}

	@Override
	public void removeAll() {
	}

	@Override
	protected V doGet(K key) {
		return null;
	}

	@Override
	protected void doPut(K key, V value, int timeToLive) {
	}

	@Override
	protected V doPutIfAbsent(K key, V value, int timeToLive) {
		return null;
	}

	@Override
	protected void doRemove(K key) {
	}

	@Override
	protected boolean doRemove(K key, V value) {
		return false;
	}

	@Override
	protected V doReplace(K key, V value, int timeToLive) {
		return null;
	}

	@Override
	protected boolean doReplace(K key, V oldValue, V newValue, int timeToLive) {
		return true;
	}

	private final String _portalCacheName;

}
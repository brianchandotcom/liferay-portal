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

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.nio.intraband.proxy.annotation.Proxy;

import java.io.Serializable;

/**
 * @author Tina Tian
 */
public interface LowLevelCache<K extends Serializable, V>
	extends PortalCache<K, V> {

	@Proxy
	public V putIfAbsent(K key, V value);

	@Proxy
	public V putIfAbsent(K key, V value, int timeToLive);

	@Proxy
	public boolean remove(K key, V value);

	@Proxy
	public V replace(K key, V value);

	@Proxy
	public V replace(K key, V value, int timeToLive);

	@Proxy
	public boolean replace(K key, V oldValue, V newValue);

	@Proxy
	public boolean replace(K key, V oldValue, V newValue, int timeToLive);

}
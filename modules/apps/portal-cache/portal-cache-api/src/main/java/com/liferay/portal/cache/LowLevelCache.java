/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache;

import com.liferay.portal.kernel.cache.PortalCache;

import java.io.Serializable;

/**
 * @author Tina Tian
 */
public interface LowLevelCache<K extends Serializable, V>
	extends PortalCache<K, V> {

	public V putIfAbsent(K key, V value);

	public V putIfAbsent(K key, V value, int timeToLive);

	public boolean remove(K key, V value);

	public V replace(K key, V value);

	public V replace(K key, V value, int timeToLive);

	public boolean replace(K key, V oldValue, V newValue);

	public boolean replace(K key, V oldValue, V newValue, int timeToLive);

}
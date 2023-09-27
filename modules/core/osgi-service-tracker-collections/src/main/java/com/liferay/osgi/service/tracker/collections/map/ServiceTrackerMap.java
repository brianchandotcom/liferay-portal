/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osgi.service.tracker.collections.map;

import java.io.Closeable;

import java.util.Collection;
import java.util.Set;

/**
 * @author Carlos Sierra Andrés
 */
public interface ServiceTrackerMap<K, R> extends Closeable {

	@Override
	public void close();

	public boolean containsKey(K key);

	public R getService(K key);

	public Set<K> keySet();

	public Collection<R> values();

}
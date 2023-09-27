/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osgi.service.tracker.collections.list;

import java.io.Closeable;

import java.util.Iterator;
import java.util.List;

/**
 * @author Adolfo Pérez
 */
public interface ServiceTrackerList<T> extends Closeable, Iterable<T> {

	@Override
	public void close();

	@Override
	public Iterator<T> iterator();

	public int size();

	public <E> E[] toArray(E[] array);

	public List<T> toList();

}
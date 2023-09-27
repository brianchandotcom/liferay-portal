/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.user.associated.data.web.internal.display;

/**
 * @author Drew Brokke
 */
public class ContainerDisplay<T> {

	public ContainerDisplay(T container) {
		_container = container;
	}

	public T getContainer() {
		return _container;
	}

	public long getCount() {
		return _count;
	}

	public void increment() {
		_count++;
	}

	private final T _container;
	private long _count;

}
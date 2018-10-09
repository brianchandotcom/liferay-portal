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

package com.liferay.bean.portlet.cdi.extension.internal;

/**
 * @author Neil Griffin
 */
public class URLGenerationListener {

	public URLGenerationListener(
		String listenerName, int ordinal, String listenerClassName) {

		if (listenerName == null) {
			_listenerName = listenerClassName;
		}
		else {
			_listenerName = listenerName;
		}

		_ordinal = ordinal;
		_listenerClassName = listenerClassName;
	}

	public String getListenerClassName() {
		return _listenerClassName;
	}

	public int getOrdinal() {
		return _ordinal;
	}

	private final String _listenerClassName;
	private final String _listenerName;
	private final int _ordinal;

}
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

package com.liferay.portal.kernel.portlet;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.WindowState;

/**
 * @author Brian Wing Shun Chan
 */
public class WindowStateFactory {

	public static WindowState getWindowState(String name) {
		return _instance._getWindowState(name);
	}

	private WindowStateFactory() {
		_windowStates = new HashMap<String, WindowState>();

		_windowStates.put(_NORMAL, LiferayWindowState.NORMAL);
		_windowStates.put(_MAXIMIZED, LiferayWindowState.MAXIMIZED);
		_windowStates.put(_MINIMIZED, LiferayWindowState.MINIMIZED);
		_windowStates.put(_EXCLUSIVE, LiferayWindowState.EXCLUSIVE);
		_windowStates.put(_POP_UP, LiferayWindowState.POP_UP);
	}

	private WindowState _getWindowState(String name) {
		WindowState windowState = _windowStates.get(name);

		if (windowState == null) {
			windowState = new WindowState(name);
		}

		return windowState;
	}

	private static final String _EXCLUSIVE =
		LiferayWindowState.EXCLUSIVE.toString();

	private static final String _MAXIMIZED = WindowState.MAXIMIZED.toString();

	private static final String _MINIMIZED = WindowState.MINIMIZED.toString();

	private static final String _NORMAL = WindowState.NORMAL.toString();

	private static final String _POP_UP = LiferayWindowState.POP_UP.toString();

	private static final WindowStateFactory _instance =
		new WindowStateFactory();

	private final Map<String, WindowState> _windowStates;

}
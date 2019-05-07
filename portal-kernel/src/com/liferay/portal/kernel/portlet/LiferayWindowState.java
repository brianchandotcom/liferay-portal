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

import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Zsolt Balogh
 */
public class LiferayWindowState extends WindowState {

	public static final WindowState EXCLUSIVE = new WindowState("exclusive");

	public static final WindowState POP_UP = new WindowState("pop_up");

	public static boolean isExclusive(HttpServletRequest httpServletRequest) {
		String state = _getWindowState(httpServletRequest);

		if ((state != null) && state.equals(EXCLUSIVE.toString())) {
			return true;
		}

		return false;
	}

	public static boolean isMaximized(HttpServletRequest httpServletRequest) {
		String state = _getWindowState(httpServletRequest);

		if ((state != null) && state.equals(WindowState.MAXIMIZED.toString())) {
			return true;
		}

		return false;
	}

	public static boolean isPopUp(HttpServletRequest httpServletRequest) {
		String state = _getWindowState(httpServletRequest);

		if ((state != null) && state.equals(POP_UP.toString())) {
			return true;
		}

		return false;
	}

	public static boolean isWindowStatePreserved(
		WindowState oldWindowState, WindowState newWindowState) {

		// Changes to EXCLUSIVE are always preserved

		if ((newWindowState != null) &&
			newWindowState.equals(LiferayWindowState.EXCLUSIVE)) {

			return true;
		}

		// Some window states are automatically preserved

		if ((oldWindowState != null) &&
			oldWindowState.equals(LiferayWindowState.POP_UP)) {

			return false;
		}

		return true;
	}

	public LiferayWindowState(String name) {
		super(name);
	}

	private static String _getWindowState(
		HttpServletRequest httpServletRequest) {

		WindowState windowState = (WindowState)httpServletRequest.getAttribute(
			WebKeys.WINDOW_STATE);

		if (windowState != null) {
			return windowState.toString();
		}

		return httpServletRequest.getParameter("p_p_state");
	}

}
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

package com.liferay.portal.kernel.xuggler;

/**
 * @author Alexander Chow
 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
 */
@Deprecated
public class XugglerUtil {

	public static Xuggler getXuggler() {
		return _xuggler;
	}

	public static void installNativeLibraries(String name) throws Exception {
		_xuggler.installNativeLibraries(name);
	}

	public static boolean isEnabled() {
		return _xuggler.isEnabled();
	}

	public static boolean isEnabled(boolean checkNativeLibraries) {
		return _xuggler.isEnabled(checkNativeLibraries);
	}

	public static boolean isNativeLibraryCopied() {
		return _xuggler.isNativeLibraryCopied();
	}

	public static boolean isNativeLibraryInstalled() {
		return _xuggler.isNativeLibraryInstalled();
	}

	public void setXuggler(Xuggler xuggler) {
		_xuggler = xuggler;
	}

	private static Xuggler _xuggler;

}
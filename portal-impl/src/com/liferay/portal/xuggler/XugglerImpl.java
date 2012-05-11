/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.xuggler;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.xuggler.Xuggler;
import com.liferay.portal.kernel.xuggler.XugglerInstallStatus;
import com.liferay.portal.util.JarUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.log4j.Log4JUtil;

import com.xuggle.ferry.JNILibraryLoader;
import com.xuggle.xuggler.IContainer;

/**
 * @author Alexander Chow
 */
public class XugglerImpl implements Xuggler {

	public void installNativeLibraries(
			String name, XugglerInstallStatus xugglerInstallStatus)
		throws Exception {

		try {
			String url = PropsValues.XUGGLER_JAR_URL + name;

			JarUtil.downloadAndInstallJar(
				false, url, name, xugglerInstallStatus);
		}
		catch (Exception e) {
			_log.error("Installation of jar " + name + " failed", e);

			throw e;
		}
	}

	public boolean isEnabled() {
		return isEnabled(true);
	}

	public boolean isEnabled(boolean checkNativeLibraries) {
		boolean enabled = false;

		try {
			enabled = PrefsPropsUtil.getBoolean(
				PropsKeys.XUGGLER_ENABLED, PropsValues.XUGGLER_ENABLED);
		}
		catch (Exception e) {
			_log.warn(e, e);
		}

		if (!checkNativeLibraries) {
			return enabled;
		}

		if (enabled) {
			return isNativeLibraryInstalled();
		}
		else {
			return false;
		}
	}

	public boolean isNativeLibraryInstalled() {
		if (_nativeLibraryInstalled) {
			return _nativeLibraryInstalled;
		}

		String originalLevel = Log4JUtil.getOriginalLevel(
			JNILibraryLoader.class.getName());

		try {
			Log4JUtil.setLevel(JNILibraryLoader.class.getName(), "OFF", false);

			IContainer.make();

			_nativeLibraryInstalled = true;
		}
		catch (NoClassDefFoundError ncdfe) {
			informAdministrator();
		}
		catch (UnsatisfiedLinkError ule) {
			informAdministrator();
		}
		finally {
			Log4JUtil.setLevel(
				JNILibraryLoader.class.getName(), originalLevel.toString(),
				false);
		}

		return _nativeLibraryInstalled;
	}

	protected void informAdministrator() {
		if (!_informed) {
			StringBundler sb = new StringBundler(5);

			sb.append("Liferay does not have the Xuggler native libraries ");
			sb.append("installed. In order to generate video and audio ");
			sb.append("previews, please follow the instructions for Xuggler ");
			sb.append("at: http://<server>/group/control_panel/manage/");
			sb.append("-/server/external-services");

			_log.error(sb.toString());

			_informed = true;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(XugglerImpl.class);

	private static boolean _informed;

	private static boolean _nativeLibraryInstalled;

}
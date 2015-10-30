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

package com.liferay.portal.xuggler;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ClassLoaderUtil;
import com.liferay.portal.kernel.util.ProgressStatusConstants;
import com.liferay.portal.kernel.util.ProgressTracker;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.xuggler.Xuggler;
import com.liferay.portal.util.JarUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.log4j.Log4JUtil;

import com.xuggle.ferry.JNILibraryLoader;
import com.xuggle.xuggler.IContainer;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author Alexander Chow
 */
public class XugglerImpl implements Xuggler {

	@Override
	public void installNativeLibraries(
			String name, ProgressTracker progressTracker)
		throws Exception {

		ClassLoader classLoader = ClassLoaderUtil.getPortalClassLoader();

		if (!(classLoader instanceof URLClassLoader)) {
			_log.error(
				"Unable to install JAR because the portal class loader is " +
					"not an instance of URLClassLoader");

			return;
		}

		try {
			if (progressTracker != null) {
				progressTracker.setStatus(ProgressStatusConstants.DOWNLOADING);
			}

			JarUtil.downloadAndInstallJar(
				new URL(PropsValues.XUGGLER_JAR_URL + name),
				PropsValues.LIFERAY_LIB_PORTAL_DIR, name,
				(URLClassLoader)classLoader);

			_isNativeLibraryCopied = true;
		}
		catch (Exception e) {
			_log.error("Unable to install jar " + name, e);

			throw e;
		}
	}

	@Override
	public boolean isEnabled() {
		return isEnabled(true);
	}

	@Override
	public boolean isEnabled(boolean checkNativeLibraries) {
		boolean enabled = false;

		try {
			enabled = PrefsPropsUtil.getBoolean(
				PropsKeys.XUGGLER_ENABLED, PropsValues.XUGGLER_ENABLED);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		if (!checkNativeLibraries) {
			return enabled;
		}

		if (enabled) {
			return isNativeLibraryInstalled();
		}

		return false;
	}

	@Override
	public boolean isNativeLibraryCopied() {
		return _isNativeLibraryCopied;
	}

	@Override
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
			informAdministrator(ncdfe.getMessage());
		}
		catch (UnsatisfiedLinkError ule) {
			informAdministrator(ule.getMessage());
		}
		finally {
			Log4JUtil.setLevel(
				JNILibraryLoader.class.getName(), originalLevel.toString(),
				false);
		}

		return _nativeLibraryInstalled;
	}

	protected void informAdministrator(String errorMessage) {
		if (!_informAdministrator) {
			return;
		}

		_informAdministrator = false;

		StringBundler sb = new StringBundler(7);

		sb.append("Liferay does not have the Xuggler native libraries ");
		sb.append("installed. In order to generate video and audio previews, ");
		sb.append("please follow the instructions for Xuggler in the Server ");
		sb.append("Administration section of the Control Panel at: ");
		sb.append("http://<server>/group/control_panel/manage/-/server/");
		sb.append("external-services. Error message is: ");
		sb.append(errorMessage);

		_log.error(sb.toString());
	}

	private static final Log _log = LogFactoryUtil.getLog(XugglerImpl.class);

	private static boolean _informAdministrator = true;
	private static boolean _nativeLibraryInstalled;
	private static boolean _isNativeLibraryCopied;

}
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

package com.liferay.portal.patcher;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.patcher.Patcher;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

/**
 * @author Zsolt Balogh
 * @author Brian Wing Shun Chan
 */
public class PatcherImpl implements Patcher {

	public boolean applyPatch(File patch) {
		File patchFolder = getPatchFolder();

		if (patchFolder == null) {
			return false;
		}

		try {
			FileUtil.copyFile(
				patch,
				new File(patchFolder + StringPool.SLASH + patch.getName()));

			return true;
		}
		catch (Exception e) {
			_log.error(
				"Could not copy " + patch.getAbsolutePath() + " patch to the " +
					"patch folder: " + patchFolder.getAbsolutePath());

			return false;
		}
	}

	public String[] getFixedIssues() {
		if (_fixedIssues != null) {
			return _fixedIssues;
		}

		Properties properties = getProperties();

		_fixedIssues = StringUtil.split(
			properties.getProperty(Patcher.FIXED_ISSUES));

		return _fixedIssues;
	}

	public String[] getInstalledPatches() {
		if (_installedPatches != null) {
			return _installedPatches;
		}

		Properties properties = getProperties();

		_installedPatches = StringUtil.split(
			properties.getProperty(Patcher.INSTALLED_PATCHES));

		return _installedPatches;
	}

	public File getPatchFolder() {
		if (_patchFolder != null) {
			return _patchFolder;
		}

		Properties properties = getProperties();

		String property = properties.getProperty(Patcher.PATCH_FOLDER);

		if (Validator.isNotNull(property)) {
			_patchFolder = new File(property);
		}

		if ((_patchFolder == null) || !_patchFolder.exists()) {
			_log.error("There is no valid patch folder configured");
		}

		return _patchFolder;
	}

	public Properties getProperties() {
		if (_properties != null) {
			return _properties;
		}

		_properties = new Properties();

		ClassLoader classLoader = PatcherImpl.class.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			Patcher.PATCHING_INFO_PROPERTIES);

		if (inputStream == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Could not load patching.properties");
			}

			return _properties;
		}

		try {
			_properties.load(inputStream);

			_configured = true;
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);
		}
		finally {
			try {
				inputStream.close();
			}
			catch (IOException ioe) {
			}
		}

		return _properties;
	}

	public boolean isConfigured() {
		return _configured;
	}

	private static Log _log = LogFactoryUtil.getLog(PatcherImpl.class);

	private static boolean _configured;
	private static String[] _fixedIssues;
	private static String[] _installedPatches;
	private static File _patchFolder;
	private static Properties _properties;

}
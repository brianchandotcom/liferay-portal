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

package com.liferay.portal.kernel.patcher;

import java.io.File;

import java.util.Properties;

/**
 * @author Zsolt Balogh
 * @author Brian Wing Shun Chan
 */
public class PatcherUtil {

	public static boolean applyPatch(File patchFile) {
		return _patcher.applyPatch(patchFile);
	}

	public static String[] getFixedIssues() {
		return _patcher.getFixedIssues();
	}

	public static String[] getInstalledPatches() {
		return _patcher.getInstalledPatches();
	}

	public static File getPatchDirectory() {
		return _patcher.getPatchDirectory();
	}

	public static Patcher getPatcher() {
		return _patcher;
	}

	public static int getPatchingToolVersion() {
		return _patcher.getPatchingToolVersion();
	}

	public static String getPatchingToolVersionDisplayName() {
		return _patcher.getPatchingToolVersionDisplayName();
	}

	public static String[] getPatchLevels() {
		return _patcher.getPatchLevels();
	}

	public static Properties getProperties() {
		return _patcher.getProperties();
	}

	public static String getSeparationId() {
		return _patcher.getSeparationId();
	}

	public static boolean isConfigured() {
		return _patcher.isConfigured();
	}

	public static boolean isSeparated() {
		return _patcher.isSeparated();
	}

	public void setPatcher(Patcher patcher) {
		_patcher = patcher;
	}

	private static Patcher _patcher;

}
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

package com.liferay.portal.kernel.patcher;

import java.io.File;

import java.util.Properties;

/**
 * @author Zsolt Balogh
 * @author Brian Wing Shun Chan
 */
public interface Patcher {

	public static final String FIXED_ISSUES = "fixed.issues";

	public static final String INSTALLED_PATCHES = "installed.patches";

	public static final String PATCH_FOLDER = "patch.folder";

	public static final String PATCHING_INFO_PROPERTIES = "patching.properties";

	public boolean applyPatch(File patch);

	public String[] getFixedIssues();

	public String[] getInstalledPatches();

	public File getPatchFolder();

	public Properties getProperties();

	public boolean isConfigured();

}
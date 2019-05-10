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

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Zsolt Balogh
 * @author Brian Wing Shun Chan
 * @author Zoltán Takács
 */
@ProviderType
public interface Patcher {

	public static final String PATCHER_PROPERTIES = "patcher.properties";

	public static final String PATCHER_SERVICE_PROPERTIES =
		"patcher-service.properties";

	public static final String PROPERTY_FIXED_ISSUES = "fixed.issues";

	public static final String PROPERTY_INSTALLED_PATCHES = "installed.patches";

	public static final String PROPERTY_PATCH_DIRECTORY = "patch.directory";

	public static final String PROPERTY_PATCH_LEVELS = "patch.levels";

	public static final String PROPERTY_PATCHING_TOOL_VERSION =
		"patching.tool.version";

	public static final String PROPERTY_PATCHING_TOOL_VERSION_DISPLAY_NAME =
		"patching.tool.version.display.name";

	public static final String PROPERTY_SEPARATED = "separated";

	public static final String PROPERTY_SEPARATION_ID = "separation.id";

	public boolean applyPatch(File patchFile);

	public String[] getFixedIssues();

	public String[] getInstalledPatches();

	public File getPatchDirectory();

	public int getPatchingToolVersion();

	public String getPatchingToolVersionDisplayName();

	public String[] getPatchLevels();

	public Properties getProperties();

	public String getSeparationId();

	public boolean hasInconsistentPatchLevels();

	public boolean isConfigured();

	public boolean isSeparated();

	public void verifyPatchLevels() throws PatchInconsistencyException;

}
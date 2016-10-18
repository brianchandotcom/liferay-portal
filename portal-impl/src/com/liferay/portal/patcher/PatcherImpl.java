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

package com.liferay.portal.patcher;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.patcher.PatchInconsistencyException;
import com.liferay.portal.kernel.patcher.Patcher;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * @author Zsolt Balogh
 * @author Brian Wing Shun Chan
 * @author Igor Beslic
 * @author Zoltán Takács
 */
@DoPrivileged
public class PatcherImpl implements Patcher {

	public PatcherImpl() {
		_propertiesMap = _getPropertiesMap(PATCHER_PROPERTIES);

		_fixedIssueKeys = Collections.unmodifiableList(
			ListUtil.fromString(
				_propertiesMap.get(PROPERTY_FIXED_ISSUES), StringPool.COMMA));

		_installedPatchNames = Collections.unmodifiableList(
			ListUtil.fromString(
				_propertiesMap.get(PROPERTY_INSTALLED_PATCHES),
				StringPool.COMMA));

		_patchDirectory = getPatchDirectory();

		_patchLevels = Collections.unmodifiableList(
			ListUtil.fromString(
				_propertiesMap.get(PROPERTY_PATCH_LEVELS), StringPool.COMMA));

		_patchingToolVersion = GetterUtil.getInteger(
			_propertiesMap.get(PROPERTY_PATCHING_TOOL_VERSION));

		_patchingToolVersionDisplayName = getPatchingToolVersionDisplayName();
	}

	@Override
	public boolean applyPatch(File patchFile) {
		File patchDirectory = getPatchDirectory();

		if (patchDirectory == null) {
			return false;
		}

		try {
			FileUtil.copyFile(
				patchFile,
				new File(
					patchDirectory + StringPool.SLASH + patchFile.getName()));

			return true;
		}
		catch (Exception e) {
			_log.error(
				"Unable to copy " + patchFile.getAbsolutePath() + " to " +
					patchDirectory.getAbsolutePath());

			return false;
		}
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getFixedIssuesList()}
	 */
	@Deprecated
	@Override
	public String[] getFixedIssues() {
		return ArrayUtil.toStringArray(getFixedIssuesList());
	}

	@Override
	public List<String> getFixedIssuesList() {
		return _fixedIssueKeys;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getInstalledPatchesList()}
	 */
	@Deprecated
	@Override
	public String[] getInstalledPatches() {
		return ArrayUtil.toStringArray(getInstalledPatchesList());
	}

	@Override
	public List<String> getInstalledPatchesList() {
		return _installedPatchNames;
	}

	@Override
	public File getPatchDirectory() {
		if (_patchDirectory != null) {
			return _patchDirectory;
		}

		String patchDirectoryName = _propertiesMap.get(
			PROPERTY_PATCH_DIRECTORY);

		File patchDirectory = null;

		if (Validator.isNotNull(patchDirectoryName)) {
			patchDirectory = new File(patchDirectoryName);

			if (!patchDirectory.exists()) {
				_log.error("The patch directory does not exist");
			}
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("The patch directory is not specified");
			}
		}

		return patchDirectory;
	}

	@Override
	public int getPatchingToolVersion() {
		return _patchingToolVersion;
	}

	@Override
	public String getPatchingToolVersionDisplayName() {
		if (_patchingToolVersionDisplayName != null) {
			return _patchingToolVersionDisplayName;
		}

		String patchingToolVersionDisplayName =
			"1.0." + getPatchingToolVersion();

		if (_propertiesMap.containsKey(
				PROPERTY_PATCHING_TOOL_VERSION_DISPLAY_NAME)) {

			patchingToolVersionDisplayName = _propertiesMap.get(
				PROPERTY_PATCHING_TOOL_VERSION_DISPLAY_NAME);
		}

		return patchingToolVersionDisplayName;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getPatchLevelsList()}
	 */
	@Deprecated
	@Override
	public String[] getPatchLevels() {
		return ArrayUtil.toStringArray(getPatchLevelsList());
	}

	@Override
	public List<String> getPatchLevelsList() {
		return _patchLevels;
	}

	@Override
	public Properties getProperties() {
		return PropertiesUtil.fromMap(_propertiesMap);
	}

	@Override
	public boolean hasInconsistentPatchLevels() {
		return _inconsistentPatchLevels;
	}

	@Override
	public boolean isConfigured() {
		return !_propertiesMap.isEmpty();
	}

	@Override
	public void verifyPatchLevels() throws PatchInconsistencyException {
		Map<String, String> portalKernelJARProperties = _getPropertiesMap(
			PATCHER_SERVICE_PROPERTIES);

		List<String> kernelJARPatches = _getInstalledPatches(
			portalKernelJARProperties);

		Collections.sort(kernelJARPatches);

		Map<String, String> portalImplJARProperties = _getPropertiesMap(
			PATCHER_PROPERTIES);

		List<String> portalImplJARPatches = _getInstalledPatches(
			portalImplJARProperties);

		Collections.sort(portalImplJARPatches);

		if (!portalImplJARPatches.equals(kernelJARPatches)) {
			_log.error("Inconsistent patch level detected");

			if (_log.isWarnEnabled()) {
				if (portalImplJARPatches.isEmpty()) {
					_log.warn(
						"There are no patches installed on portal-impl.jar");
				}
				else {
					_log.warn(
						"Patch level on portal-impl.jar: " +
							String.join(
								StringPool.COMMA_AND_SPACE,
								portalImplJARPatches));
				}

				if (kernelJARPatches.isEmpty()) {
					_log.warn(
						"There are no patches installed on portal-kernel.jar");
				}
				else {
					_log.warn(
						"Patch level on portal-kernel.jar: " +
							String.join(
								StringPool.COMMA_AND_SPACE, kernelJARPatches));
				}
			}

			_inconsistentPatchLevels = true;

			throw new PatchInconsistencyException();
		}
	}

	private List<String> _getInstalledPatches(
		Map<String, String> propertiesMap) {

		List<String> installedPatchNames = ListUtil.fromString(
			propertiesMap.get(PROPERTY_INSTALLED_PATCHES), StringPool.COMMA);

		return installedPatchNames;
	}

	private Map<String, String> _getPropertiesMap(String fileName) {
		if (Validator.isNull(fileName)) {
			fileName = PATCHER_PROPERTIES;
		}

		Properties properties = new Properties();

		Class<?> clazz = getClass();

		if (Objects.equals(fileName, PATCHER_SERVICE_PROPERTIES)) {
			clazz = clazz.getInterfaces()[0];
		}

		ClassLoader classLoader = clazz.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(fileName);

		if (inputStream == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to load " + fileName);
			}
		}
		else {
			try {
				properties.load(inputStream);
			}
			catch (IOException ioe) {
				_log.error(ioe, ioe);
			}
			finally {
				StreamUtil.cleanUp(inputStream);
			}
		}

		@SuppressWarnings("unchecked")
		Map<String, String> propertiesMap = PropertiesUtil.toMap(properties);

		return Collections.unmodifiableMap(propertiesMap);
	}

	private static final Log _log = LogFactoryUtil.getLog(PatcherImpl.class);

	private final List<String> _fixedIssueKeys;
	private boolean _inconsistentPatchLevels;
	private final List<String> _installedPatchNames;
	private final File _patchDirectory;
	private final int _patchingToolVersion;
	private final String _patchingToolVersionDisplayName;
	private final List<String> _patchLevels;
	private final Map<String, String> _propertiesMap;

}
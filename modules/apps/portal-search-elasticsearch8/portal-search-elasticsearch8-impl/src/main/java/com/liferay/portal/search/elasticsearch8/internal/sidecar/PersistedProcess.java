/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.sidecar;

import com.liferay.petra.process.ProcessConfig;

import java.io.Serializable;

import java.net.URL;

/**
 * @author Tina Tian
 */
public class PersistedProcess implements Serializable {

	public PersistedProcess(
		URL bundleURL, ProcessConfig processConfig, String processName,
		SidecarMainProcessCallable sidecarMainProcessCallable,
		StartSidecarProcessCallable startSidecarProcessCallable) {

		_bundleURL = bundleURL;
		_processConfig = processConfig;
		_processName = processName;
		_sidecarMainProcessCallable = sidecarMainProcessCallable;
		_startSidecarProcessCallable = startSidecarProcessCallable;
	}

	public URL getBundleURL() {
		return _bundleURL;
	}

	public ProcessConfig getProcessConfig() {
		return _processConfig;
	}

	public String getProcessName() {
		return _processName;
	}

	public SidecarMainProcessCallable getSidecarMainProcessCallable() {
		return _sidecarMainProcessCallable;
	}

	public StartSidecarProcessCallable getStartSidecarProcessCallable() {
		return _startSidecarProcessCallable;
	}

	private static final long serialVersionUID = 1L;

	private final URL _bundleURL;
	private final ProcessConfig _processConfig;
	private final String _processName;
	private final SidecarMainProcessCallable _sidecarMainProcessCallable;
	private final StartSidecarProcessCallable _startSidecarProcessCallable;

}
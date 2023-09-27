/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.project.templates.fragment.internal;

import com.beust.jcommander.Parameter;

import com.liferay.project.templates.extensions.ProjectTemplatesArgsExt;

/**
 * @author Gregory Amerson
 */
public class FragmentProjectTemplatesArgs implements ProjectTemplatesArgsExt {

	public String getHostBundleSymbolicName() {
		return _hostBundleSymbolicName;
	}

	public String getHostBundleVersion() {
		return _hostBundleVersion;
	}

	@Override
	public String getTemplateName() {
		return "fragment";
	}

	public void setHostBundleSymbolicName(String hostBundleSymbolicName) {
		_hostBundleSymbolicName = hostBundleSymbolicName;
	}

	public void setHostBundleVersion(String hostBundleVersion) {
		_hostBundleVersion = hostBundleVersion;
	}

	@Parameter(
		description = "If a new JSP hook fragment is generated, provide the name of the host bundle symbolic name.",
		names = "--host-bundle-symbolic-name", required = true
	)
	private String _hostBundleSymbolicName;

	@Parameter(
		description = "If a new JSP hook fragment is generated, provide the name of the host bundle version.",
		names = "--host-bundle-version", required = true
	)
	private String _hostBundleVersion;

}
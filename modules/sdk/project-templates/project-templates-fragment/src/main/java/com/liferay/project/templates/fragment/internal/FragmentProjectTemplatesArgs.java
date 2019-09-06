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
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

package com.liferay.gradle.plugins;

import com.liferay.gradle.plugins.internal.util.GradleUtil;
import com.liferay.gradle.plugins.util.PortalTools;
import com.liferay.gradle.util.Validator;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * @author Andrea Di Giorgi
 */
public abstract class BasePortalToolDefaultsPlugin
	<T extends Plugin<? extends Project>>
		extends BaseDefaultsPlugin<T> {

	protected void addPortalToolDependencies(Project project) {
		addPortalToolDependencies(
			project, getPortalToolConfigurationName(), getPortalToolName());
	}

	protected void addPortalToolDependencies(
		Project project, String configurationName, String portalToolName) {

		String portalToolVersion = PortalTools.getVersion(
			project, portalToolName);

		if (Validator.isNotNull(portalToolVersion)) {
			GradleUtil.addDependency(
				project, configurationName, getPortalToolGroup(),
				portalToolName, portalToolVersion);
		}
	}

	protected abstract String getPortalToolConfigurationName();

	protected String getPortalToolGroup() {
		return PortalTools.GROUP;
	}

	protected abstract String getPortalToolName();

}
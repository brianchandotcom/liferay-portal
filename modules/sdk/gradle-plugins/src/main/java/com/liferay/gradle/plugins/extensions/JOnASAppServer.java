/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.extensions;

import com.liferay.gradle.plugins.internal.util.FileUtil;
import com.liferay.gradle.plugins.internal.util.GradleUtil;

import java.io.File;

import org.gradle.api.Project;

/**
 * @author     Manuel de la Peña
 * @deprecated As of Mueller (7.2.x)
 */
@Deprecated
public class JOnASAppServer extends AppServer {

	public JOnASAppServer(Project project) {
		super("jonas", project);
	}

	@Override
	public void addAdditionalDependencies(String configurationName) {
		File dir = new File(getDir(), "lib/endorsed");

		GradleUtil.addDependency(
			project, configurationName, FileUtil.getJarsFileTree(project, dir));
	}

}
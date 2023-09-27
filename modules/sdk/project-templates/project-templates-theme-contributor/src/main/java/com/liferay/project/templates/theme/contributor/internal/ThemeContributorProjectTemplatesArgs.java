/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.project.templates.theme.contributor.internal;

import com.beust.jcommander.Parameter;

import com.liferay.project.templates.extensions.ProjectTemplatesArgsExt;

/**
 * @author Gregory Amerson
 */
public class ThemeContributorProjectTemplatesArgs
	implements ProjectTemplatesArgsExt {

	public String getContributorType() {
		return _contributorType;
	}

	@Override
	public String getTemplateName() {
		return "theme-contributor";
	}

	public void setContributorType(String contributorType) {
		_contributorType = contributorType;
	}

	@Parameter(
		description = "Used to identify your module as a Theme Contributor. Also, used to add the Liferay-Theme-Contributor-Type and Web-ContextPath bundle headers.",
		names = "--contributor-type", required = true
	)
	private String _contributorType;

}
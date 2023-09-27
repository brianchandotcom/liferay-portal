/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.project.templates.form.field.internal;

import com.beust.jcommander.Parameter;

import com.liferay.project.templates.extensions.ProjectTemplatesArgsExt;

/**
 * @author Marcos Martins
 */
public class FormFieldProjectTemplatesArgs implements ProjectTemplatesArgsExt {

	public String getJSFramework() {
		return _jsFramework;
	}

	@Override
	public String getTemplateName() {
		return "form-field";
	}

	public void setJSFramework(String jsFramework) {
		_jsFramework = jsFramework;
	}

	@Parameter(
		description = "Specify the javascript framework which will be used in the generated project. (metaljs)|(react)",
		names = "--js-framework"
	)
	private String _jsFramework;

}
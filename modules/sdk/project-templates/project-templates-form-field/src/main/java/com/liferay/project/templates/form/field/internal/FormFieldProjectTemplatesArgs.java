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
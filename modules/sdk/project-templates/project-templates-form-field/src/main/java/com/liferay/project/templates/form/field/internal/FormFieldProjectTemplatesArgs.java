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

	@Override
	public String getTemplateName() {
		return "form-field";
	}

	public boolean getReactTemplate() {
		return _reactTemplate;
	}

	public void setReactTemplate(boolean reactTemplate) {
		_reactTemplate = reactTemplate;
	}

	@Parameter(
		description = "Specify if the form field front end template is written in React.",
		names = "--react-template"
	)
	private boolean _reactTemplate;

}
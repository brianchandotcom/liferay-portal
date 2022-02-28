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

package com.liferay.fragment.renderer.collection.filter.internal;

import com.liferay.info.field.InfoField;
import com.liferay.portal.kernel.util.Validator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Víctor Galán
 */
@Component(
	service = FormFieldRenderer.class,
	property =
		"info.field.type=com.liferay.info.field.type.BooleanInfoFieldType")

public class BooleanFormFieldRenderer implements FormFieldRenderer {
	@Override
	public void render(
		String label, String placeholder, InfoField<?> infoField,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		httpServletRequest.setAttribute("name", infoField.getName());
		httpServletRequest.setAttribute(
			"label",
			Validator.isNull(label) ?
				infoField.getLabel(httpServletRequest.getLocale()) : label);
		httpServletRequest.setAttribute(
			"placeholder", placeholder);

		try {
			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher("/form_field_checkbox.jsp");

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception e) {
			System.out.println(e);
		}

	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.fragment.renderer.collection.filter.impl)"
	)
	private ServletContext _servletContext;
}

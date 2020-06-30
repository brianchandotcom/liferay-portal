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

package com.liferay.dynamic.data.mapping.form.renderer.internal.servlet.taglib;

import com.liferay.dynamic.data.mapping.form.renderer.internal.servlet.taglib.helper.BaseDDMFormFieldTypesDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bruno Basto
 */
@Component(immediate = true, service = DynamicInclude.class)
public class DDMFormFieldTypesDynamicInclude
	extends BaseDDMFormFieldTypesDynamicInclude {

	public static final String LIFERAY_SHARED_DDM_FORM_FIELD_TYPES_INCLUDED =
		"LIFERAY_SHARED_DDM_FORM_FIELD_TYPES_INCLUDED";

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (themeDisplay.isAjax()) {
			include(httpServletResponse);

			httpServletRequest.removeAttribute(
				LIFERAY_SHARED_DDM_FORM_FIELD_TYPES_INCLUDED);
		}
		else {
			httpServletRequest.setAttribute(
				LIFERAY_SHARED_DDM_FORM_FIELD_TYPES_INCLUDED, Boolean.TRUE);
		}
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			DDMFormFieldTypesDynamicInclude.class.getName());
	}

}
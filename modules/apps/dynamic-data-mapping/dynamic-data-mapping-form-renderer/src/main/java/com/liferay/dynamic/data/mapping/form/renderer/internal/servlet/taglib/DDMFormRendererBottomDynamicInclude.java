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

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bruno Basto
 */
@Component(immediate = true, service = DynamicInclude.class)
public class DDMFormRendererBottomDynamicInclude
	extends BaseDDMFormFieldTypesDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		Object value = httpServletRequest.getAttribute(
			DDMFormFieldTypesDynamicInclude.
				LIFERAY_SHARED_DDM_FORM_FIELD_TYPES_INCLUDED);

		if (value != null) {
			include(httpServletResponse);
		}

		httpServletRequest.removeAttribute(
			DDMFormFieldTypesDynamicInclude.
				LIFERAY_SHARED_DDM_FORM_FIELD_TYPES_INCLUDED);
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register("/html/common/themes/bottom.jsp#post");
	}

}
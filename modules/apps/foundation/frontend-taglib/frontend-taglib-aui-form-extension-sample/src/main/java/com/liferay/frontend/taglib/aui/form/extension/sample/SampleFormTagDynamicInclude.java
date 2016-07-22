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

package com.liferay.frontend.taglib.aui.form.extension.sample;

import com.liferay.portal.kernel.servlet.taglib.TagDynamicInclude;
import com.liferay.portal.kernel.util.PortletKeys;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Sierra Andrés
 */
@Component(immediate = true, service = TagDynamicInclude.class)
public class SampleFormTagDynamicInclude implements TagDynamicInclude {

	@Override
	public void include(
			HttpServletRequest request, HttpServletResponse response,
			String tagClassName, String tagDynamicId, String tagPoint)
		throws IOException {

		PrintWriter printWriter = response.getWriter();

		printWriter.println(
			"<h2>Liferay Portal Taglib AUI Form Extension Sample</h2><br />");
	}

	@Override
	public void register(TagDynamicIncludeRegistry tagDynamicIncludeRegistry) {
		tagDynamicIncludeRegistry.register(
			"com.liferay.taglib.aui.FormTag", PortletKeys.LOGIN + "-loginForm",
			"doStartTag#before");

		tagDynamicIncludeRegistry.register(
			"com.liferay.taglib.aui.FormTag",
			PortletKeys.LOGIN + "-loginFormModal", "doStartTag#before");
	}

}
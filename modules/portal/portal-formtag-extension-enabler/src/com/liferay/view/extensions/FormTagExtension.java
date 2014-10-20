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

package com.liferay.view.extensions;

import static com.liferay.portal.util.PortletKeys.LOGIN;

import com.liferay.kernel.servlet.taglib.TagExtension;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Sierra Andrés
 */
@Component(
	immediate = true, service = TagExtension.class
)
public class FormTagExtension implements TagExtension {

	@Override
	public void include(
		HttpServletRequest request, HttpServletResponse response,
		String tagClassName, String tagKey, String itemKey) {

		try {
			PrintWriter writer = response.getWriter();

			writer.println("<h2>extension</h2><br/>");
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void register(TagItemRegistry registry) {
		registry.register(
			"com.liferay.taglib.aui.FormTag", LOGIN + "-fm", "BEFORE_START");
		registry.register(
			"com.liferay.taglib.aui.FormTag", LOGIN + "-fm", "AFTER_END");
	}

}
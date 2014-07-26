/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.component.samples.portlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Raymond Augé
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=S11"
	},
	service = Portlet.class
)
public class Sample_11 extends BaseSamplePortlet {

	@Override
	public void render(RenderRequest request, RenderResponse response)
		throws PortletException, IOException {

		String include = "/META-INF/resources/html/hello.html";

		PrintWriter writer = response.getWriter();

		writer.print("Test portlet ");
		writer.print(getPortletConfig().getPortletName());
		writer.println("<br/>");
		writer.print("Includes (from classpath): ");
		writer.print(include);
		writer.println("<br/>");

		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(include);

		Reader reader = new InputStreamReader(inputStream);

		char[] buffer = new char[1024];

		int n = 0;

		while (-1 != (n = reader.read(buffer))) {
			writer.write(buffer, 0, n);
		}

		reader.close();
		writer.println();

		writer.flush();
		writer.close();
	}

}
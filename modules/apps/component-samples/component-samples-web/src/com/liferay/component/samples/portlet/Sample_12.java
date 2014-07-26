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
import java.io.PrintWriter;

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
		"com.liferay.portlet.css-class-wrapper=S12",
		"com.liferay.portlet.footer-portlet-css=/css/c.css",
		"com.liferay.portlet.footer-portlet-javascript=/js/b.js",
		"com.liferay.portlet.header-portlet-css=/css/e.css",
		"com.liferay.portlet.header-portlet-javascript=/js/d.js",
		"javax.portlet.name=S12"
	},
	service = Portlet.class
)
public class Sample_12 extends BaseSamplePortlet {

	@Override
	public void render(RenderRequest request, RenderResponse response)
		throws PortletException, IOException {

		PrintWriter writer = response.getWriter();

		writer.print("Test portlet ");
		writer.print(getPortletConfig().getPortletName());
		writer.println("<br/>");
		writer.println("<ul>");
		writer.print("<li class='a'>A</li>");
		writer.print("<li class='b'>B</li>");
		writer.print("<li class='c'>C</li>");
		writer.print("<li class='d'>D</li>");
		writer.print("<li class='e'>E</li>");
		writer.println("</ul>");

		writer.flush();
		writer.close();
	}

}
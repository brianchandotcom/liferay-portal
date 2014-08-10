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

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Map.Entry;
import java.util.Set;

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
		"javax.portlet.name=S6",
		"javax.portlet.portletModes=text/html;view,edit,edit_guest",
		"javax.portlet.windowStates=text/html;normal,pop_up"
	},
	service = Portlet.class
)
public class Sample_6 extends BaseSamplePortlet {

	@Override
	public void render(RenderRequest request, RenderResponse response)
		throws PortletException, IOException {

		com.liferay.portal.model.Portlet portlet =
			(com.liferay.portal.model.Portlet)request.getAttribute(
				WebKeys.RENDER_PORTLET);

		PrintWriter writer = response.getWriter();

		writer.print("Test portlet ");
		writer.print(getPortletConfig().getPortletName());
		writer.println("<br/>");
		writer.println("Portlet Modes:");
		writer.println("<ul>");

		for (Entry<String, Set<String>> entry :
				portlet.getPortletModes().entrySet()) {

			String mimeType = entry.getKey();

			Set<String> modes = entry.getValue();

			writer.print("<li>");
			writer.print(mimeType);
			writer.print(" = ");
			writer.print(StringUtil.merge(modes));
			writer.println("</li>");
		}

		writer.println("</ul>");
		writer.println("<br/>");
		writer.println("Window States:");
		writer.println("<ul>");

		for (Entry<String, Set<String>> entry :
				portlet.getWindowStates().entrySet()) {

			String mimeType = entry.getKey();

			Set<String> states = entry.getValue();

			writer.print("<li>");
			writer.print(mimeType);
			writer.print(" = ");
			writer.print(StringUtil.merge(states));
			writer.println("</li>");
		}

		writer.println("</ul>");

		writer.flush();
		writer.close();
	}

}
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

package com.liferay.frontend.taglib.servlet.taglib.soy;

import com.liferay.frontend.taglib.soy.servlet.taglib.TemplateRendererTag;

import java.util.Map;

/**
 * @author Chema Balsas
 */
public class ContextualSidebarTag extends TemplateRendererTag {

	@Override
	public int doStartTag() {
		Map<String, Object> context = getContext();

		if (context.get("visible") == null) {
			putValue("visible", true);
		}

		setTemplateNamespace("liferay.frontend.ContextualSidebar.render");

		return super.doStartTag();
	}

	@Override
	public String getModule() {
		return "frontend-taglib/contextual_sidebar/ContextualSidebar.es";
	}

	public void setBody(String body) {
		putHTMLValue("body", body);
	}

	public void setBodyClasses(String bodyClasses) {
		putValue("bodyClasses", bodyClasses);
	}

	public void setElementClasses(String elementClasses) {
		putValue("elementClasses", elementClasses);
	}

	public void setHeader(String header) {
		putHTMLValue("header", header);
	}

	public void setHeaderClasses(String headerClasses) {
		putValue("headerClasses", headerClasses);
	}

	public void setId(String id) {
		putValue("id", id);
	}

	public void setNamespace(String namespace) {
		putValue("namespace", namespace);
	}

	public void setVisible(Boolean visible) {
		putValue("visible", visible);
	}

}
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

package com.liferay.layout.taglib.servlet.taglib.soy;

import com.liferay.frontend.taglib.soy.servlet.taglib.TemplateRendererTag;
import com.liferay.portal.kernel.servlet.taglib.util.OutputData;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.ServletRequest;

/**
 * @author Chema Balsas
 */
public class ContextualSidebarTag extends TemplateRendererTag {

	@Override
	public int doStartTag() {
		setTemplateNamespace("layout.ContextualSidebar.render");

		_outputStylesheetLink();

		return super.doStartTag();
	}

	@Override
	public String getModule() {
		return "layout-taglib/contextual_sidebar/ContextualSidebar.es";
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

	private OutputData _getOutputData() {
		ServletRequest servletRequest = getRequest();

		OutputData outputData = (OutputData)servletRequest.getAttribute(
			WebKeys.OUTPUT_DATA);

		if (outputData == null) {
			outputData = new OutputData();

			servletRequest.setAttribute(WebKeys.OUTPUT_DATA, outputData);
		}

		return outputData;
	}

	private void _outputStylesheetLink() {
		OutputData outputData = _getOutputData();

		StringBundler sb = new StringBundler(4);

		sb.append("<link data-senna-track=\"temporary\" href=\"");
		sb.append(PortalUtil.getPathModule());
		sb.append("/layout-taglib/contextual_sidebar/ContextualSidebar.css");
		sb.append("\" rel=\"stylesheet\">");

		outputData.setData(
			ContextualSidebarTag.class.getName() + "_CSS", WebKeys.PAGE_TOP,
			sb);
	}

}
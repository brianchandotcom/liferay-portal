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

package com.liferay.wiki.web.internal.servlet.taglib;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.taglib.BaseJSPDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.constants.WikiWebKeys;
import com.liferay.wiki.model.WikiNode;

import java.io.IOException;

import javax.portlet.PortletRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = DynamicInclude.class)
public class WikiPortletHeaderJSPDynamicInclude extends BaseJSPDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		PortletRequest portletRequest =
			(PortletRequest)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		String mvcRenderCommandName = ParamUtil.getString(
			portletRequest, "mvcRenderCommandName");

		if (Validator.isNotNull(mvcRenderCommandName) &&
			!mvcRenderCommandName.equals("/wiki/view") &&
			!mvcRenderCommandName.equals("/wiki/view_pages") &&
			!mvcRenderCommandName.equals("/wiki/view_categorized_pages") &&
			!mvcRenderCommandName.equals("/wiki/view_draft_pages") &&
			!mvcRenderCommandName.equals("/wiki/view_orphan_pages") &&
			!mvcRenderCommandName.equals("/wiki/view_page_attachments") &&
			!mvcRenderCommandName.equals("/wiki/view_page_details") &&
			!mvcRenderCommandName.equals("/wiki/view_page_history") &&
			!mvcRenderCommandName.equals("/wiki/view_page_incoming_links") &&
			!mvcRenderCommandName.equals("/wiki/view_page_outgoing_links") &&
			!mvcRenderCommandName.equals("/wiki/view_recent_changes") &&
			!mvcRenderCommandName.equals("/wiki/view_tagged_pages") &&
			!mvcRenderCommandName.equals("/wiki/view_page_activities")) {

			return;
		}

		WikiNode node = (WikiNode)httpServletRequest.getAttribute(
			WikiWebKeys.WIKI_NODE);

		if (node == null) {
			return;
		}

		super.include(httpServletRequest, httpServletResponse, key);
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"portlet_header_" + WikiPortletKeys.WIKI);
	}

	@Override
	protected String getJspPath() {
		return "/dynamic_include/portlet_header.jsp";
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.wiki.web)", unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WikiPortletHeaderJSPDynamicInclude.class);

}
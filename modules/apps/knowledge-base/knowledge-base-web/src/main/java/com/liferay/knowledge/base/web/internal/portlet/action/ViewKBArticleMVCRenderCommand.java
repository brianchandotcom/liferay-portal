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

package com.liferay.knowledge.base.web.internal.portlet.action;

import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = {
		"javax.portlet.name=" + KBPortletKeys.KNOWLEDGE_BASE_ADMIN,
		"javax.portlet.name=" + KBPortletKeys.KNOWLEDGE_BASE_ARTICLE,
		"javax.portlet.name=" + KBPortletKeys.KNOWLEDGE_BASE_DISPLAY,
		"javax.portlet.name=" + KBPortletKeys.KNOWLEDGE_BASE_SEARCH,
		"javax.portlet.name=" + KBPortletKeys.KNOWLEDGE_BASE_SECTION,
		"mvc.command.name=/knowledge_base/view_kb_article"
	},
	service = MVCRenderCommand.class
)
public class ViewKBArticleMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		String rootPortletId = _getRootPortletId(renderRequest);

		if (rootPortletId.equals(KBPortletKeys.KNOWLEDGE_BASE_ADMIN)) {
			return "/admin/view_kb_article.jsp";
		}

		if (rootPortletId.equals(KBPortletKeys.KNOWLEDGE_BASE_ARTICLE)) {
			return "/article/view_kb_article.jsp";
		}

		if (rootPortletId.equals(KBPortletKeys.KNOWLEDGE_BASE_DISPLAY)) {
			return "/display/view_kb_article.jsp";
		}

		return "/admin/common/view_kb_article.jsp";
	}

	private String _getRootPortletId(RenderRequest renderRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return portletDisplay.getRootPortletId();
	}

}
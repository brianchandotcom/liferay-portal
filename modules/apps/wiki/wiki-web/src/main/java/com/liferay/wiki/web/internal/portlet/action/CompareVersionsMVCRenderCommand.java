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

package com.liferay.wiki.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.constants.WikiWebKeys;
import com.liferay.wiki.engine.WikiEngineRenderer;
import com.liferay.wiki.exception.NoSuchPageException;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Farache
 * @author Julio Camarero
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + WikiPortletKeys.WIKI,
		"javax.portlet.name=" + WikiPortletKeys.WIKI_ADMIN,
		"javax.portlet.name=" + WikiPortletKeys.WIKI_DISPLAY,
		"mvc.command.name=/wiki/compare_versions"
	},
	service = MVCRenderCommand.class
)
public class CompareVersionsMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			WikiNode node = ActionUtil.getNode(renderRequest);

			renderRequest.setAttribute(WikiWebKeys.WIKI_NODE, node);

			WikiPage page = ActionUtil.getPage(renderRequest);

			renderRequest.setAttribute(WikiWebKeys.WIKI_PAGE, page);

			ActionUtil.compareVersions(
				renderRequest, renderResponse, _wikiEngineRenderer);
		}
		catch (Exception e) {
			if (e instanceof NoSuchPageException) {
				SessionErrors.add(renderRequest, e.getClass());

				return "/wiki/error.jsp";
			}
			else {
				throw new PortletException(e);
			}
		}

		return "/wiki/compare_versions.jsp";
	}

	@Reference(unbind = "-")
	protected void setWikiEngineRenderer(
		WikiEngineRenderer wikiEngineRenderer) {

		_wikiEngineRenderer = wikiEngineRenderer;
	}

	private WikiEngineRenderer _wikiEngineRenderer;

}
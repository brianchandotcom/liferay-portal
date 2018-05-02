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
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.constants.WikiWebKeys;
import com.liferay.wiki.engine.WikiEngineRenderer;
import com.liferay.wiki.web.internal.portlet.toolbar.item.WikiPortletToolbarContributor;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + WikiPortletKeys.WIKI_ADMIN,
		"mvc.command.name=/wiki/view_pages"
	},
	service = MVCRenderCommand.class
)
public class ViewPagesMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		renderRequest.setAttribute(
			WikiWebKeys.WIKI_ENGINE_RENDERER, _wikiEngineRenderer);
		renderRequest.setAttribute(
			WikiWebKeys.WIKI_PORTLET_TOOLBAR_CONTRIBUTOR,
			_wikiPortletToolbarContributor);

		return ActionUtil.viewNode(renderRequest, "/wiki_admin/view_pages.jsp");
	}

	@Reference(unbind = "-")
	protected void setWikiEngineRenderer(
		WikiEngineRenderer wikiEngineRenderer) {

		_wikiEngineRenderer = wikiEngineRenderer;
	}

	@Reference(unbind = "-")
	protected void setWikiPortletToolbarContributor(
		WikiPortletToolbarContributor wikiPortletToolbarContributor) {

		_wikiPortletToolbarContributor = wikiPortletToolbarContributor;
	}

	private WikiEngineRenderer _wikiEngineRenderer;
	private WikiPortletToolbarContributor _wikiPortletToolbarContributor;

}
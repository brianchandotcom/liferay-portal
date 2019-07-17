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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.wiki.configuration.WikiGroupServiceConfiguration;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.constants.WikiWebKeys;
import com.liferay.wiki.engine.WikiEngineRenderer;
import com.liferay.wiki.exception.DuplicatePageException;
import com.liferay.wiki.exception.NoSuchNodeException;
import com.liferay.wiki.exception.NoSuchPageException;
import com.liferay.wiki.exception.PageTitleException;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.model.WikiPageConstants;
import com.liferay.wiki.service.WikiPageService;
import com.liferay.wiki.validator.WikiPageTitleValidator;
import com.liferay.wiki.web.internal.util.WikiWebComponentProvider;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + WikiPortletKeys.WIKI,
		"javax.portlet.name=" + WikiPortletKeys.WIKI_ADMIN,
		"javax.portlet.name=" + WikiPortletKeys.WIKI_DISPLAY,
		"mvc.command.name=/wiki/edit_page"
	},
	service = MVCRenderCommand.class
)
public class EditPageMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			renderRequest.setAttribute(
				WikiWebKeys.WIKI_ENGINE_RENDERER, _wikiEngineRenderer);

			renderRequest.setAttribute(
				WikiWebKeys.WIKI_PAGE_TITLE_VALIDATOR, _wikiPageTitleValidator);

			renderRequest.setAttribute(
				WikiWebKeys.WIKI_NODE, ActionUtil.getNode(renderRequest));

			if (!SessionErrors.contains(
					renderRequest, DuplicatePageException.class.getName())) {

				getPage(renderRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchNodeException ||
				e instanceof PageTitleException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				if (e instanceof PrincipalException) {
					return "/wiki/error.jsp";
				}
			}
			else if (e instanceof NoSuchPageException) {

				// Let edit_page.jsp handle this case

			}
			else {
				throw new PortletException(e);
			}
		}

		return "/wiki/edit_page.jsp";
	}

	protected void getPage(RenderRequest renderRequest) throws Exception {
		long nodeId = ParamUtil.getLong(renderRequest, "nodeId");
		String title = ParamUtil.getString(renderRequest, "title");
		double version = ParamUtil.getDouble(renderRequest, "version");
		boolean removeRedirect = ParamUtil.getBoolean(
			renderRequest, "removeRedirect");

		if (nodeId == 0) {
			WikiNode node = (WikiNode)renderRequest.getAttribute(
				WikiWebKeys.WIKI_NODE);

			if (node != null) {
				nodeId = node.getNodeId();
			}
		}

		WikiPage page = null;

		if (Validator.isNull(title)) {
			renderRequest.setAttribute(WikiWebKeys.WIKI_PAGE, page);

			return;
		}

		try {
			if (version == 0) {
				page = _wikiPageService.getPage(nodeId, title, null);
			}
			else {
				page = _wikiPageService.getPage(nodeId, title, version);
			}
		}
		catch (NoSuchPageException nspe1) {
			try {
				page = _wikiPageService.getPage(nodeId, title, false);
			}
			catch (NoSuchPageException nspe2) {
				WikiWebComponentProvider wikiWebComponentProvider =
					WikiWebComponentProvider.getWikiWebComponentProvider();

				WikiGroupServiceConfiguration wikiGroupServiceConfiguration =
					wikiWebComponentProvider.getWikiGroupServiceConfiguration();

				if (title.equals(
						wikiGroupServiceConfiguration.frontPageName()) &&
					(version == 0)) {

					ServiceContext serviceContext = new ServiceContext();

					page = _wikiPageService.addPage(
						nodeId, title, null, WikiPageConstants.NEW, true,
						serviceContext);
				}
				else {
					throw nspe2;
				}
			}
		}

		if (removeRedirect) {
			page.setContent(StringPool.BLANK);
			page.setRedirectTitle(StringPool.BLANK);
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_wikiPageModelResourcePermission.check(
			themeDisplay.getPermissionChecker(), page, ActionKeys.UPDATE);

		renderRequest.setAttribute(WikiWebKeys.WIKI_PAGE, page);
	}

	@Reference(unbind = "-")
	protected void setWikiEngineRenderer(
		WikiEngineRenderer wikiEngineRenderer) {

		_wikiEngineRenderer = wikiEngineRenderer;
	}

	@Reference(unbind = "-")
	protected void setWikiPageService(WikiPageService wikiPageService) {
		_wikiPageService = wikiPageService;
	}

	@Reference(unbind = "-")
	protected void setWikiPageTitleValidator(
		WikiPageTitleValidator wikiPageTitleValidator) {

		_wikiPageTitleValidator = wikiPageTitleValidator;
	}

	private WikiEngineRenderer _wikiEngineRenderer;

	@Reference(target = "(model.class.name=com.liferay.wiki.model.WikiNode)")
	private volatile ModelResourcePermission<WikiNode>
		_wikiNodeModelResourcePermission;

	@Reference(target = "(model.class.name=com.liferay.wiki.model.WikiPage)")
	private volatile ModelResourcePermission<WikiPage>
		_wikiPageModelResourcePermission;

	private WikiPageService _wikiPageService;
	private WikiPageTitleValidator _wikiPageTitleValidator;

}
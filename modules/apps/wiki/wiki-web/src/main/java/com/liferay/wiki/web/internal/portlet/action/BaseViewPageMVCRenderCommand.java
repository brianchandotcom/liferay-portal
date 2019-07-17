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
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.wiki.constants.WikiWebKeys;
import com.liferay.wiki.exception.NoSuchNodeException;
import com.liferay.wiki.exception.NoSuchPageException;
import com.liferay.wiki.model.WikiNode;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public abstract class BaseViewPageMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		long categoryId = ParamUtil.getLong(renderRequest, "categoryId");

		if (categoryId > 0) {
			return ActionUtil.viewNode(
				renderRequest, "/wiki/view_categorized_pages.jsp");
		}

		String tag = ParamUtil.getString(renderRequest, "tag");

		if (Validator.isNotNull(tag)) {
			return ActionUtil.viewNode(
				renderRequest, "/wiki/view_tagged_pages.jsp");
		}

		try {
			renderRequest.setAttribute(
				WikiWebKeys.WIKI_NODE, ActionUtil.getNode(renderRequest));

			renderRequest.setAttribute(
				WikiWebKeys.WIKI_PAGE, ActionUtil.getPage(renderRequest));
		}
		catch (Exception e) {
			if (e instanceof NoSuchNodeException ||
				e instanceof NoSuchPageException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return "/wiki/error.jsp";
			}

			throw new PortletException(e);
		}

		return getPath();
	}

	protected void getNode(RenderRequest renderRequest) throws Exception {
		WikiNode node = ActionUtil.getNode(renderRequest);

		if (node == null) {
			node = ActionUtil.getFirstVisibleNode(renderRequest);
		}

		renderRequest.setAttribute(WikiWebKeys.WIKI_NODE, node);
	}

	protected abstract String getPath();

}
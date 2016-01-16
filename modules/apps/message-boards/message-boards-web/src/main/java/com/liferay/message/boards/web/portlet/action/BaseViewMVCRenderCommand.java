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

package com.liferay.message.boards.web.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portlet.messageboards.exception.BannedUserException;
import com.liferay.portlet.messageboards.exception.NoSuchCategoryException;
import com.liferay.portlet.messageboards.model.MBCategory;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Iván Zaera
 */
public abstract class BaseViewMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			MBCategory category = ActionUtil.getCategory(renderRequest);

			renderRequest.setAttribute(
				WebKeys.MESSAGE_BOARDS_CATEGORY, category);
		}
		catch (BannedUserException | NoSuchCategoryException |
			   PrincipalException e) {

			SessionErrors.add(renderRequest, e.getClass());

			return "/message_boards/error.jsp";
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new PortletException(e);
		}

		return _jspPath;
	}

	protected BaseViewMVCRenderCommand(String jspPath) {
		_jspPath = jspPath;
	}

	private final String _jspPath;

}
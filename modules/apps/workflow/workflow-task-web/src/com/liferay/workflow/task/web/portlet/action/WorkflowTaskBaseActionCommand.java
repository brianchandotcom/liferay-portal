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

package com.liferay.workflow.task.web.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.ActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PortalUtil;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Leonardo Barros
 */
public abstract class WorkflowTaskBaseActionCommand implements ActionCommand {

	@Override
	public boolean processCommand(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws PortletException {

		try {
			doProcessCommand(portletRequest, portletResponse);

			setRedirectAttribute(portletRequest);

			return SessionErrors.isEmpty(portletRequest);
		}
		catch (PortletException pe) {
			throw pe;
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	protected abstract void doProcessCommand(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception;

	protected void setRedirectAttribute(PortletRequest portletRequest) {
		String redirect = ParamUtil.getString(portletRequest, "redirect");

		String closeRedirect = ParamUtil.getString(
			portletRequest, "closeRedirect");

		if (Validator.isNotNull(closeRedirect)) {
			redirect = HttpUtil.setParameter(
				redirect, "closeRedirect", closeRedirect);

			SessionMessages.add(
				portletRequest, PortalUtil.getPortletId(portletRequest) +
					SessionMessages.KEY_SUFFIX_CLOSE_REDIRECT, closeRedirect);
		}

		portletRequest.setAttribute(WebKeys.REDIRECT, redirect);
	}

}
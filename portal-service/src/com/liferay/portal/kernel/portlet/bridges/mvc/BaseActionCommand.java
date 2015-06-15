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

package com.liferay.portal.kernel.portlet.bridges.mvc;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletConfigFactoryUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BaseActionCommand implements ActionCommand {

	@Override
	public boolean processCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		try {
			doProcessCommand(actionRequest, actionResponse);

			return SessionErrors.isEmpty(actionRequest);
		}
		catch (PortletException pe) {
			throw pe;
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	protected abstract void doProcessCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception;

	protected PortletConfig getPortletConfig(ActionRequest actionRequest) {
		String portletId = PortalUtil.getPortletId(actionRequest);

		return PortletConfigFactoryUtil.get(portletId);
	}

	protected void hideDefaultErrorMessage(ActionRequest actionRequest) {
		SessionMessages.add(
			actionRequest,
			PortalUtil.getPortletId(actionRequest) +
				SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
	}

	protected void hideDefaultSuccessMessage(ActionRequest actionRequest) {
		SessionMessages.add(
			actionRequest,
			PortalUtil.getPortletId(actionRequest) +
				SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_SUCCESS_MESSAGE);
	}

}
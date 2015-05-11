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

package com.liferay.dynamic.data.mapping.web.portlet.action;

import javax.portlet.PortletRequest;

import com.liferay.dynamic.data.mapping.web.portlet.constants.DDMConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseActionCommand;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

/**
 * @author Leonardo Barros
 */
public abstract class DDMBaseActionCommand extends BaseActionCommand {

	protected String setRedirectAttribute(
		PortletRequest portletRequest, String redirect) {
		
		String closeRedirect = ParamUtil.getString(
			portletRequest, DDMConstants.CLOSE_REDIRECT);

		if (Validator.isNotNull(closeRedirect)) {
			redirect = HttpUtil.setParameter(
				redirect, DDMConstants.CLOSE_REDIRECT, closeRedirect);

			SessionMessages.add(
				portletRequest,
				PortalUtil.getPortletId(portletRequest) +
					SessionMessages.KEY_SUFFIX_CLOSE_REDIRECT,
				closeRedirect);
		}

		portletRequest.setAttribute(WebKeys.REDIRECT,redirect);
		
		return redirect;
	}

}

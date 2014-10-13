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

package com.liferay.portal.servlet.taglib.ui;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.taglib.util.PortalIncludeUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author     Brian Chan
 * @deprecated As of 6.2.0, replaced by {@link
 *             com.liferay.taglib.ui.InputPermissionsTag}
 */
@Deprecated
public class InputPermissionsTagUtil {

	public static void doEndTag(
			String page, String formName, String modelName,
			PageContext pageContext)
		throws JspException {

		try {
			HttpServletRequest request =
				(HttpServletRequest)pageContext.getRequest();

			request.setAttribute(
				"liferay-ui:input-permissions:formName", formName);

			if (modelName != null) {
				List<String> supportedActions =
					ResourceActionsUtil.getModelResourceActions(modelName);
				List<String> groupDefaultActions =
					ResourceActionsUtil.getModelResourceGroupDefaultActions(
						modelName);
				List<String> guestDefaultActions =
					ResourceActionsUtil.getModelResourceGuestDefaultActions(
						modelName);
				List<String> guestUnsupportedActions =
					ResourceActionsUtil.getModelResourceGuestUnsupportedActions(
						modelName);

				request.setAttribute(
					"liferay-ui:input-permissions:modelName", modelName);
				request.setAttribute(
					"liferay-ui:input-permissions:supportedActions",
					supportedActions);
				request.setAttribute(
					"liferay-ui:input-permissions:groupDefaultActions",
					groupDefaultActions);
				request.setAttribute(
					"liferay-ui:input-permissions:guestDefaultActions",
					guestDefaultActions);
				request.setAttribute(
					"liferay-ui:input-permissions:guestUnsupportedActions",
					guestUnsupportedActions);
			}

			PortalIncludeUtil.include(pageContext, page);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new JspException(e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		InputPermissionsTagUtil.class);

}
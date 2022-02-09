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

package com.liferay.frontend.taglib.servlet.taglib;

import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolvedPackageNameUtil;
import com.liferay.frontend.js.module.launcher.JSModuleResolver;
import com.liferay.frontend.taglib.internal.util.ServicesProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.TagResourceBundleUtil;

import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Adolfo Pérez
 */
public class DefineObjectsTag extends TagSupport {

	@Override
	public int doStartTag() throws JspException {
		HttpServletRequest httpServletRequest =
			(HttpServletRequest)pageContext.getRequest();

		PortletRequest portletRequest =
			(PortletRequest)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		if (portletRequest != null) {
			LiferayPortletRequest liferayPortletRequest =
				PortalUtil.getLiferayPortletRequest(portletRequest);

			PortletResponse portletResponse =
				(PortletResponse)httpServletRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_RESPONSE);

			if (portletResponse != null) {
				PortletURL currentURLObj = PortletURLUtil.getCurrent(
					liferayPortletRequest,
					PortalUtil.getLiferayPortletResponse(portletResponse));

				pageContext.setAttribute(
					"currentURL", currentURLObj.toString());
				pageContext.setAttribute("currentURLObj", currentURLObj);
			}

			pageContext.setAttribute(
				"windowState", liferayPortletRequest.getWindowState());
		}

		String npmResolvedPackageName = null;

		try {
			npmResolvedPackageName = NPMResolvedPackageNameUtil.get(
				pageContext.getServletContext());
		}
		catch (UnsupportedOperationException unsupportedOperationException1) {
			try {
				JSModuleResolver jsModuleResolver =
					ServicesProvider.getJSModuleResolver();

				npmResolvedPackageName = jsModuleResolver.resolveModule(
					pageContext.getServletContext(), null);
			}
			catch (UnsupportedOperationException
						unsupportedOperationException2) {

				if (_log.isDebugEnabled()) {
					_log.debug(unsupportedOperationException2);
				}
			}
		}

		if (Validator.isNotNull(npmResolvedPackageName)) {
			pageContext.setAttribute(
				"npmResolvedPackageName", npmResolvedPackageName);
		}

		if (_overrideResourceBundle != null) {
			pageContext.setAttribute("resourceBundle", _overrideResourceBundle);
		}
		else {
			pageContext.setAttribute(
				"resourceBundle",
				TagResourceBundleUtil.getResourceBundle(
					httpServletRequest,
					PortalUtil.getLocale(httpServletRequest)));
		}

		return SKIP_BODY;
	}

	public void setOverrideResourceBundle(
		ResourceBundle overrideResourceBundle) {

		_overrideResourceBundle = overrideResourceBundle;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefineObjectsTag.class);

	private ResourceBundle _overrideResourceBundle;

}
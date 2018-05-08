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

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.taglib.util.TagResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Adolfo Pérez
 */
public class DefineObjectsTag extends TagSupport {

	@Override
	public int doStartTag() throws JspException {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		if (portletRequest != null) {
			LiferayPortletRequest liferayPortletRequest =
				PortalUtil.getLiferayPortletRequest(portletRequest);

			PortletResponse portletResponse =
				(PortletResponse)request.getAttribute(
					JavaConstants.JAVAX_PORTLET_RESPONSE);

			if (portletResponse != null) {
				LiferayPortletResponse liferayPortletResponse =
					PortalUtil.getLiferayPortletResponse(portletResponse);

				PortletURL currentURLObj = PortletURLUtil.getCurrent(
					liferayPortletRequest, liferayPortletResponse);

				pageContext.setAttribute(
					"currentURL", currentURLObj.toString());
				pageContext.setAttribute("currentURLObj", currentURLObj);
			}

			pageContext.setAttribute(
				"windowState", liferayPortletRequest.getWindowState());
		}

		if (_overrideResourceBundle != null) {
			pageContext.setAttribute("resourceBundle", _overrideResourceBundle);
		}
		else {
			Locale locale = PortalUtil.getLocale(request);

			pageContext.setAttribute(
				"resourceBundle",
				TagResourceBundleUtil.getResourceBundle(request, locale));
		}

		return SKIP_BODY;
	}

	public void setOverrideResourceBundle(
		ResourceBundle overrideResourceBundle) {

		_overrideResourceBundle = overrideResourceBundle;
	}

	/**
	 * @deprecated As of 2.1.0, with no direct replacement
	 */
	@Deprecated
	protected PortletURL getCurrentURLObj() {
		LiferayPortletRequest liferayPortletRequest =
			getLiferayPortletRequest();

		LiferayPortletResponse liferayPortletResponse =
			getLiferayPortletResponse();

		if ((liferayPortletRequest == null) ||
			(liferayPortletResponse == null)) {

			return null;
		}

		return PortletURLUtil.getCurrent(
			liferayPortletRequest, liferayPortletResponse);
	}

	/**
	 * @deprecated As of 2.1.0, with no direct replacement
	 */
	@Deprecated
	protected LiferayPortletRequest getLiferayPortletRequest() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		if (portletRequest == null) {
			return null;
		}

		return PortalUtil.getLiferayPortletRequest(portletRequest);
	}

	/**
	 * @deprecated As of 2.1.0, with no direct replacement
	 */
	@Deprecated
	protected LiferayPortletResponse getLiferayPortletResponse() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		PortletResponse portletResponse = (PortletResponse)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);

		if (portletResponse == null) {
			return null;
		}

		return PortalUtil.getLiferayPortletResponse(portletResponse);
	}

	/**
	 * @deprecated As of 2.1.0, with no direct replacement
	 */
	@Deprecated
	protected ResourceBundle getResourceBundle() {
		if (_overrideResourceBundle != null) {
			return _overrideResourceBundle;
		}

		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		Locale locale = PortalUtil.getLocale(request);

		return TagResourceBundleUtil.getResourceBundle(request, locale);
	}

	/**
	 * @deprecated As of 2.1.0, with no direct replacement
	 */
	@Deprecated
	protected WindowState getWindowState() {
		LiferayPortletRequest liferayPortletRequest =
			getLiferayPortletRequest();

		if (liferayPortletRequest == null) {
			return null;
		}

		return liferayPortletRequest.getWindowState();
	}

	private ResourceBundle _overrideResourceBundle;

}
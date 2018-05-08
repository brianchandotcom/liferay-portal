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

package com.liferay.login.web.internal.servlet.taglib.include;

import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.include.PageInclude;
import com.liferay.taglib.ui.IconTag;

import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(
	immediate = true,
	property = {
		"login.web.navigation.position=pre", "service.ranking:Integer=100"
	},
	service = PageInclude.class
)
public class SignInNavigationPrePageInclude implements PageInclude {

	@Override
	public void include(PageContext pageContext) throws JspException {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		String mvcRenderCommandName = request.getParameter(
			"mvcRenderCommandName");

		if (Validator.isNull(mvcRenderCommandName) ||
			"/login/login".equals(mvcRenderCommandName)) {

			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String signInURL = themeDisplay.getURLSignIn();

		PortletConfig portletConfig = (PortletConfig)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG);

		String portletName = portletConfig.getPortletName();

		if (portletName.equals(PortletKeys.FAST_LOGIN)) {
			PortletURL fastLoginURL = PortletURLFactoryUtil.create(
				request, PortletKeys.FAST_LOGIN, PortletRequest.RENDER_PHASE);

			fastLoginURL.setParameter("saveLastPath", Boolean.FALSE.toString());
			fastLoginURL.setParameter("mvcRenderCommandName", "/login/login");

			try {
				fastLoginURL.setPortletMode(PortletMode.VIEW);
				fastLoginURL.setWindowState(LiferayWindowState.POP_UP);
			}
			catch (PortletException pe) {
				throw new JspException(pe);
			}

			signInURL = fastLoginURL.toString();
		}

		IconTag iconTag = new IconTag();

		iconTag.setIconCssClass("icon-signin");
		iconTag.setMessage("sign-in");
		iconTag.setUrl(signInURL);

		iconTag.doTag(pageContext);
	}

}
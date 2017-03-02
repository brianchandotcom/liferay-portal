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

import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.taglib.include.PageInclude;
import com.liferay.taglib.portlet.RenderURLTag;
import com.liferay.taglib.ui.IconTag;

import javax.portlet.PortletConfig;
import javax.portlet.WindowState;

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
		"login.web.navigation.position=pre", "service.ranking:Integer=200"
	},
	service = PageInclude.class
)
public class AnonymousNavigationPrePageInclude implements PageInclude {

	@Override
	public void include(PageContext pageContext) throws JspException {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		String mvcRenderCommandName = request.getParameter(
			"mvcRenderCommandName");

		if ((mvcRenderCommandName != null) &&
			mvcRenderCommandName.startsWith(
				"/login/create_anonymous_account")) {

			return;
		}

		PortletConfig portletConfig = (PortletConfig)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG);

		String portletName = portletConfig.getPortletName();

		if (!portletName.equals(PortletKeys.FAST_LOGIN)) {
			return;
		}

		RenderURLTag renderURLTag = new RenderURLTag();

		renderURLTag.setPageContext(pageContext);

		renderURLTag.addParam(
			"mvcRenderCommandName", "/login/create_anonymous_account");
		renderURLTag.setWindowState(WindowState.MAXIMIZED.toString());
		renderURLTag.setVar("anonymousURL");

		renderURLTag.doTag(pageContext);

		String anonymousURL = (String)pageContext.getAttribute("anonymousURL");

		IconTag iconTag = new IconTag();

		iconTag.setIconCssClass("icon-user");
		iconTag.setMessage("guest");
		iconTag.setUrl(anonymousURL);

		iconTag.doTag(pageContext);
	}

}
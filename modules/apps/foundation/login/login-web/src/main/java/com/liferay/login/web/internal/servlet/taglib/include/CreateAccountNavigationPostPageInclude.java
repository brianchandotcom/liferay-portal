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

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.include.PageInclude;
import com.liferay.taglib.ui.IconTag;

import javax.portlet.PortletConfig;

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
		"login.web.navigation.position=post", "service.ranking:Integer=200"
	},
	service = PageInclude.class
)
public class CreateAccountNavigationPostPageInclude implements PageInclude {

	@Override
	public void include(PageContext pageContext) throws JspException {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		String mvcRenderCommandName = request.getParameter(
			"mvcRenderCommandName");

		if ("/login/create_account".equals(mvcRenderCommandName)) {
			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Company company = themeDisplay.getCompany();

		if (!company.isStrangers()) {
			return;
		}

		PortletConfig portletConfig = (PortletConfig)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG);

		String portletName = portletConfig.getPortletName();

		if (portletName.equals(PortletKeys.FAST_LOGIN)) {
			return;
		}

		IconTag iconTag = new IconTag();

		iconTag.setIconCssClass("icon-plus");
		iconTag.setMessage("create-account");

		try {
			iconTag.setUrl(
				PortalUtil.getCreateAccountURL(request, themeDisplay));
		}
		catch (Exception e) {
			throw new JspException(e);
		}

		iconTag.doTag(pageContext);
	}

}
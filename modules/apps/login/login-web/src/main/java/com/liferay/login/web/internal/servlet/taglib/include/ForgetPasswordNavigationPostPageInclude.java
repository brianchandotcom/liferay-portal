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
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.include.PageInclude;
import com.liferay.taglib.portlet.RenderURLTag;
import com.liferay.taglib.ui.IconTag;

import java.util.Objects;

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
		"login.web.navigation.position=post", "service.ranking:Integer=100"
	},
	service = PageInclude.class
)
public class ForgetPasswordNavigationPostPageInclude implements PageInclude {

	@Override
	public void include(PageContext pageContext) throws JspException {
		HttpServletRequest httpServletRequest =
			(HttpServletRequest)pageContext.getRequest();

		String mvcRenderCommandName = httpServletRequest.getParameter(
			"mvcRenderCommandName");

		if (Objects.equals(mvcRenderCommandName, "/login/forgot_password")) {
			return;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Company company = themeDisplay.getCompany();

		if (!company.isSendPasswordResetLink()) {
			return;
		}

		RenderURLTag renderURLTag = new RenderURLTag();

		renderURLTag.setPageContext(pageContext);

		renderURLTag.addParam("mvcRenderCommandName", "/login/forgot_password");
		renderURLTag.addParam("saveLastPath", Boolean.FALSE.toString());
		renderURLTag.setVar("forgotPasswordURL");
		renderURLTag.setWindowState(WindowState.MAXIMIZED.toString());

		renderURLTag.doTag(pageContext);

		String forgetPasswordURL = (String)pageContext.getAttribute(
			"forgotPasswordURL");

		IconTag iconTag = new IconTag();

		iconTag.setMessage("forgot-password");
		iconTag.setUrl(forgetPasswordURL);

		iconTag.doTag(pageContext);
	}

}
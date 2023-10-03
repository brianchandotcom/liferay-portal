/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		renderURLTag.addParam("saveLastPath", Boolean.FALSE.toString());
		renderURLTag.addParam("mvcRenderCommandName", "/login/forgot_password");
		renderURLTag.setVar("forgotPasswordURL");
		renderURLTag.setWindowState(WindowState.MAXIMIZED.toString());

		renderURLTag.doTag(pageContext);

		String forgetPasswordURL = (String)pageContext.getAttribute(
			"forgotPasswordURL");

		IconTag iconTag = new IconTag();

		iconTag.setCssClass("text-4");
		iconTag.setMessage("forgot-password");
		iconTag.setUrl(forgetPasswordURL);

		iconTag.doTag(pageContext);
	}

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.login.web.internal.servlet.taglib.include;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.include.PageInclude;
import com.liferay.taglib.ui.IconTag;

import java.util.Objects;

import javax.portlet.PortletConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(
	property = {
		"login.web.navigation.position=post", "service.ranking:Integer=200"
	},
	service = PageInclude.class
)
public class CreateAccountNavigationPostPageInclude implements PageInclude {

	@Override
	public void include(PageContext pageContext) throws JspException {
		HttpServletRequest httpServletRequest =
			(HttpServletRequest)pageContext.getRequest();

		String mvcRenderCommandName = httpServletRequest.getParameter(
			"mvcRenderCommandName");

		if (Objects.equals(mvcRenderCommandName, "/login/create_account")) {
			return;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Company company = themeDisplay.getCompany();

		if (!company.isStrangers()) {
			return;
		}

		PortletConfig portletConfig =
			(PortletConfig)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG);

		String portletName = portletConfig.getPortletName();

		if (portletName.equals(PortletKeys.FAST_LOGIN)) {
			return;
		}

		IconTag iconTag = new IconTag();

		iconTag.setCssClass("text-4");
		iconTag.setMessage("create-account");

		try {
			iconTag.setUrl(
				_portal.getCreateAccountURL(httpServletRequest, themeDisplay));
		}
		catch (Exception exception) {
			throw new JspException(exception);
		}

		iconTag.doTag(pageContext);
	}

	@Reference
	private Portal _portal;

}
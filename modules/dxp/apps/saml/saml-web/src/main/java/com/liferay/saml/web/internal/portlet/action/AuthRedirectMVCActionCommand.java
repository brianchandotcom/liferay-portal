/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.constants.SamlPortletKeys;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 * @author Arthur Chan
 */
@Component(
	immediate = true,
	property = {
		"auth.token.ignore.mvc.action=true",
		"javax.portlet.name=" + SamlPortletKeys.SAML,
		"mvc.command.name=/saml/auth_redirect"
	},
	service = MVCActionCommand.class
)
public class AuthRedirectMVCActionCommand extends BaseSamlMVCActionCommand {

	@Override
	public boolean isEnabled() {
		if (super.isEnabled()) {
			return samlProviderConfigurationHelper.isRoleSp();
		}

		return false;
	}

	@Override
	@Reference(unbind = "-")
	public void setPortal(Portal portal) {
		super.setPortal(portal);
	}

	@Override
	@Reference(unbind = "-")
	public void setSamlProviderConfigurationHelper(
		SamlProviderConfigurationHelper samlProviderConfigurationHelper) {

		super.setSamlProviderConfigurationHelper(
			samlProviderConfigurationHelper);
	}

	@Override
	protected void doProcessAction(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		String redirect = ParamUtil.getString(httpServletRequest, "redirect");

		redirect = portal.escapeRedirect(redirect);

		if (Validator.isNull(redirect)) {
			redirect = portal.getHomeURL(httpServletRequest);
		}

		httpServletResponse.sendRedirect(redirect);
	}

}
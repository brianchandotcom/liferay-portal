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

package com.liferay.saml.web.internal.struts;

import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.runtime.servlet.profile.SingleLogoutProfile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(
	property = {
		"path=/portal/saml/slo", "path=/portal/saml/slo_logout",
		"path=/portal/saml/slo_soap"
	},
	service = StrutsAction.class
)
public class SingleLogoutAction extends BaseSamlStrutsAction {

	@Override
	public boolean isEnabled() {
		return _samlProviderConfigurationHelper.isEnabled();
	}

	@Override
	protected String doExecute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		String requestURI = httpServletRequest.getRequestURI();

		if (_samlProviderConfigurationHelper.isRoleIdp() &&
			requestURI.endsWith("/slo_logout")) {

			_singleLogoutProfile.processIdpLogout(
				httpServletRequest, httpServletResponse);
		}
		else {
			_singleLogoutProfile.processSingleLogout(
				httpServletRequest, httpServletResponse);
		}

		return null;
	}

	@Reference
	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

	@Reference
	private SingleLogoutProfile _singleLogoutProfile;

}
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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.saml.constants.SamlWebKeys;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;
import com.liferay.saml.persistence.service.SamlSpIdpConnectionLocalService;
import com.liferay.saml.runtime.configuration.SamlProviderConfiguration;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.web.internal.constants.SamlAdminPortletKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stian Sigvartsen
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SamlAdminPortletKeys.SAML_ADMIN,
		"mvc.command.name=/admin/edit_identity_provider_connection"
	},
	service = MVCRenderCommand.class
)
public class EditIdentityProviderConnectionMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		long samlSpIdpConnectionId = ParamUtil.getLong(
			renderRequest, "samlSpIdpConnectionId");

		renderRequest.setAttribute(
			SamlProviderConfigurationHelper.class.getName(),
			_samlProviderConfigurationHelper);

		SamlProviderConfiguration samlProviderConfiguration =
			_samlProviderConfigurationHelper.getSamlProviderConfiguration();

		long clockSkew;

		if (samlSpIdpConnectionId > 0) {
			try {
				SamlSpIdpConnection samlSpIdpConnection =
					_samlSpIdpConnectionLocalService.getSamlSpIdpConnection(
						samlSpIdpConnectionId);

				clockSkew = ParamUtil.getLong(
					renderRequest, "clockSkew",
					samlSpIdpConnection.getClockSkew());

				renderRequest.setAttribute(
					SamlWebKeys.SAML_SP_IDP_CONNECTION, samlSpIdpConnection);
			}
			catch (PortalException portalException) {
				throw new PortletException(portalException);
			}
		}
		else {
			clockSkew = ParamUtil.getLong(
				renderRequest, "clockSkew",
				samlProviderConfiguration.clockSkew());
		}

		renderRequest.setAttribute(SamlWebKeys.SAML_CLOCK_SKEW, clockSkew);

		return "/admin/edit_identity_provider_connection.jsp";
	}

	@Reference
	private Portal _portal;

	@Reference
	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

	@Reference
	private SamlSpIdpConnectionLocalService _samlSpIdpConnectionLocalService;

}
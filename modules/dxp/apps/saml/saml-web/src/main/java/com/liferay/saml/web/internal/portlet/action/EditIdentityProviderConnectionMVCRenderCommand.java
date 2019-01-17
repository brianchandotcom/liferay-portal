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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.saml.constants.SamlWebKeys;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;
import com.liferay.saml.persistence.service.SamlSpIdpConnectionLocalService;
import com.liferay.saml.runtime.configuration.SamlProviderConfiguration;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.web.internal.constants.SamlAdminPortletKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

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

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			renderRequest);

		SamlProviderConfiguration samlProviderConfiguration =
			_samlProviderConfigurationHelper.getSamlProviderConfiguration();

		String samlIdpEntityId = samlProviderConfiguration.defaultIdPEntityId();

		long clockSkew = ParamUtil.getLong(
			httpServletRequest, "clockSkew",
			samlProviderConfiguration.clockSkew());

		if (Validator.isNotNull(samlIdpEntityId)) {
			try {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				SamlSpIdpConnection samlSpIdpConnection =
					_samlSpIdpConnectionLocalService.getSamlSpIdpConnection(
						themeDisplay.getCompanyId(), samlIdpEntityId);

				clockSkew = ParamUtil.getLong(
					httpServletRequest, "clockSkew",
					samlSpIdpConnection.getClockSkew());

				renderRequest.setAttribute(
					SamlWebKeys.SAML_SP_IDP_CONNECTION, samlSpIdpConnection);
			}
			catch (Exception e) {
				String message =
					"Unable to calculate clock skew: " + e.getMessage();

				if (_log.isDebugEnabled()) {
					_log.debug(message, e);
				}
				else if (_log.isWarnEnabled()) {
					_log.warn(message);
				}
			}
		}

		renderRequest.setAttribute(SamlWebKeys.SAML_CLOCK_SKEW, clockSkew);

		return "/admin/edit_identity_provider_connection.jsp";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditIdentityProviderConnectionMVCRenderCommand.class);

	@Reference
	private Portal _portal;

	@Reference
	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

	@Reference
	private SamlSpIdpConnectionLocalService _samlSpIdpConnectionLocalService;

}
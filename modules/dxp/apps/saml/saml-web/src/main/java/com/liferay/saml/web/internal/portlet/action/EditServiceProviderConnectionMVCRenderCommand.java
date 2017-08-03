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

package com.liferay.saml.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.saml.constants.SamlWebKeys;
import com.liferay.saml.persistence.model.SamlIdpSpConnection;
import com.liferay.saml.persistence.service.SamlIdpSpConnectionLocalService;
import com.liferay.saml.runtime.configuration.SamlProviderConfiguration;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.web.internal.constants.SamlAdminPortletKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SamlAdminPortletKeys.SAML_ADMIN,
		"mvc.command.name=/admin/edit_service_provider_connection"
	},
	service = MVCRenderCommand.class
)
public class EditServiceProviderConnectionMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		long samlIdpSpConnectionId = ParamUtil.getLong(
			renderRequest, "samlIdpSpConnectionId");

		renderRequest.setAttribute(
			ClassUtil.getClassName(SamlProviderConfigurationHelper.class),
			_samlProviderConfigurationHelper);

		SamlProviderConfiguration samlProviderConfiguration =
			_samlProviderConfigurationHelper.getSamlProviderConfiguration();

		int assertionLifetime = ParamUtil.getInteger(
			renderRequest, "assertionLifetime",
			samlProviderConfiguration.defaultAssertionLifetime());

		if (samlIdpSpConnectionId > 0) {
			SamlIdpSpConnection samlIdpSpConnection =
				_samlIdpSpConnectionLocalService.fetchSamlIdpSpConnection(
					samlIdpSpConnectionId);

			if (samlIdpSpConnection != null) {
				assertionLifetime = ParamUtil.getInteger(
					renderRequest, "assertionLifetime",
					samlIdpSpConnection.getAssertionLifetime());

				renderRequest.setAttribute(
					SamlWebKeys.SAML_IDP_SP_CONNECTION, samlIdpSpConnection);
			}
		}

		renderRequest.setAttribute(
			SamlWebKeys.SAML_ASSERTION_LIFETIME, assertionLifetime);

		return "/admin/edit_service_provider_connection.jsp";
	}

	@Reference
	private Portal _portal;

	@Reference
	private SamlIdpSpConnectionLocalService _samlIdpSpConnectionLocalService;

	@Reference
	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

}
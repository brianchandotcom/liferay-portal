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

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.saml.constants.SamlWebKeys;
import com.liferay.saml.persistence.model.SamlIdpSpConnection;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;
import com.liferay.saml.persistence.service.SamlIdpSpConnectionLocalService;
import com.liferay.saml.persistence.service.SamlSpIdpConnectionLocalService;
import com.liferay.saml.runtime.certificate.CertificateTool;
import com.liferay.saml.runtime.configuration.SamlProviderConfiguration;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.runtime.metadata.LocalEntityManager;
import com.liferay.saml.web.internal.constants.SamlAdminPortletKeys;

import java.security.cert.X509Certificate;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SamlAdminPortletKeys.SAML_ADMIN,
		"mvc.command.name=/", "mvc.command.name=/admin"
	},
	service = MVCRenderCommand.class
)
public class DefaultViewMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		renderRequest.setAttribute(
			ClassUtil.getClassName(SamlProviderConfigurationHelper.class),
			_samlProviderConfigurationHelper);

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			renderRequest);

		String tabs1 = ParamUtil.getString(
			httpServletRequest, "tabs1", "general");

		if (tabs1.equals("general")) {
			renderGeneralTab(renderRequest, renderResponse);
		}
		else if (tabs1.equals("identity-provider-connection")) {
			renderIdentityProviderConnectionTab(
				renderRequest, renderResponse, httpServletRequest);
		}
		else if (tabs1.equals("service-provider-connections")) {
			renderViewServiceProviderConnections(
				renderRequest, renderResponse, httpServletRequest);
		}

		return "/admin/view.jsp";
	}

	protected void renderGeneralTab(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		String entityId = _localEntityManager.getLocalEntityId();

		renderRequest.setAttribute(SamlWebKeys.SAML_ENTITY_ID, entityId);

		if (renderRequest.getAttribute(SamlWebKeys.SAML_X509_CERTIFICATE) !=
				null) {

			return;
		}

		try {
			X509Certificate x509Certificate =
				_localEntityManager.getLocalEntityCertificate();

			if (x509Certificate != null) {
				renderRequest.setAttribute(
					SamlWebKeys.SAML_X509_CERTIFICATE, x509Certificate);
			}

			renderRequest.setAttribute(
				SamlWebKeys.SAML_CERTIFICATE_TOOL, _certificateTool);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to get local entity certificate", e);
			}

			renderRequest.setAttribute(
				SamlWebKeys.SAML_X509_CERTIFICATE_AUTH_NEEDED, Boolean.TRUE);
		}
	}

	protected void renderIdentityProviderConnectionTab(
		RenderRequest renderRequest, RenderResponse renderResponse,
		HttpServletRequest httpServletRequest) {

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
				if (_log.isDebugEnabled()) {
					_log.debug("Unable to calculate clock skew", e);
				}
			}
		}

		renderRequest.setAttribute(SamlWebKeys.SAML_CLOCK_SKEW, clockSkew);
	}

	protected void renderViewServiceProviderConnections(
		RenderRequest renderRequest, RenderResponse renderResponse,
		HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		int samlIdpSpConnectionsCount =
			_samlIdpSpConnectionLocalService.getSamlIdpSpConnectionsCount(
				themeDisplay.getCompanyId());

		PortletURL portletURL = renderResponse.createRenderURL();

		SearchContainer searchContainer = new SearchContainer(
			renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, 0,
			SearchContainer.DEFAULT_DELTA, portletURL, null, null);

		List<SamlIdpSpConnection> samlIdpSpConnections =
			_samlIdpSpConnectionLocalService.getSamlIdpSpConnections(
				themeDisplay.getCompanyId(), searchContainer.getStart(),
				searchContainer.getEnd());

		renderRequest.setAttribute(
			SamlWebKeys.SAML_IDP_SP_CONNECTIONS, samlIdpSpConnections);

		renderRequest.setAttribute(
			SamlWebKeys.SAML_IDP_SP_CONNECTIONS_COUNT,
			samlIdpSpConnectionsCount);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultViewMVCRenderCommand.class);

	@Reference
	private CertificateTool _certificateTool;

	@Reference
	private LocalEntityManager _localEntityManager;

	@Reference
	private Portal _portal;

	@Reference
	private SamlIdpSpConnectionLocalService _samlIdpSpConnectionLocalService;

	@Reference
	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

	@Reference
	private SamlSpIdpConnectionLocalService _samlSpIdpConnectionLocalService;

}
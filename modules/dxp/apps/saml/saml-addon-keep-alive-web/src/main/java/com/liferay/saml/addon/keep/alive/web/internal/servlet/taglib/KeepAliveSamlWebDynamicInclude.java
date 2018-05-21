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

package com.liferay.saml.addon.keep.alive.web.internal.servlet.taglib;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.saml.addon.keep.alive.web.internal.constants.SamlKeepAliveConstants;
import com.liferay.saml.constants.SamlWebKeys;
import com.liferay.saml.persistence.exception.NoSuchSpIdpConnectionException;
import com.liferay.saml.persistence.model.SamlIdpSpConnection;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;
import com.liferay.saml.persistence.service.SamlIdpSpConnectionLocalService;
import com.liferay.saml.persistence.service.SamlSpIdpConnectionLocalService;
import com.liferay.saml.runtime.configuration.SamlProviderConfiguration;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.util.PortletPropsKeys;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(immediate = true, service = DynamicInclude.class)
public class KeepAliveSamlWebDynamicInclude extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest request, HttpServletResponse response,
			String key)
		throws IOException {

		String keepAliveURL = null;

		if (_KEY_IDENTITY_PROVIDER.equals(key)) {
			keepAliveURL = getSpIdpKeepAliveUrl(request);
		}
		else {
			keepAliveURL = getIdpSpKeepAliveUrl(request);
		}

		request.setAttribute(SamlWebKeys.SAML_KEEP_ALIVE_URL, keepAliveURL);

		includeJSP(request, response, "/com.liferay.saml.web/keep_alive.jsp");
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(_KEY_IDENTITY_PROVIDER);
		dynamicIncludeRegistry.register(_KEY_SERVICE_PROVIDER);
	}

	protected String getIdpSpKeepAliveUrl(HttpServletRequest request) {
		String keepAliveURL = StringPool.BLANK;

		long samlIdpSpConnectionId = ParamUtil.getLong(
			request, "samlIdpSpConnectionId");

		if (samlIdpSpConnectionId > 0) {
			try {
				SamlIdpSpConnection samlIdpSpConnection =
					_samlIdpSpConnectionLocalService.getSamlIdpSpConnection(
						samlIdpSpConnectionId);

				ExpandoBridge expandoBridge =
					samlIdpSpConnection.getExpandoBridge();

				keepAliveURL = (String)expandoBridge.getAttribute(
					SamlKeepAliveConstants.EXPANDO_COLUMN_NAME_KEEP_ALIVE_URL);
			}
			catch (PortalException pe) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to get SP keep alive URL", pe);
				}
			}
		}

		return keepAliveURL;
	}

	protected String getSpIdpKeepAliveUrl(HttpServletRequest request) {
		String keepAliveURL = StringPool.BLANK;

		SamlProviderConfiguration samlProviderConfiguration =
			_samlProviderConfigurationHelper.getSamlProviderConfiguration();

		String defaultEntityId = samlProviderConfiguration.defaultIdPEntityId();

		if (!Validator.isBlank(defaultEntityId)) {
			try {
				ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
					WebKeys.THEME_DISPLAY);

				SamlSpIdpConnection samlSpIdpConnection =
					_samlSpIdpConnectionLocalService.getSamlSpIdpConnection(
						themeDisplay.getCompanyId(), defaultEntityId);

				ExpandoBridge expandoBridge =
					samlSpIdpConnection.getExpandoBridge();

				keepAliveURL = (String)expandoBridge.getAttribute(
					SamlKeepAliveConstants.EXPANDO_COLUMN_NAME_KEEP_ALIVE_URL);
			}
			catch (NoSuchSpIdpConnectionException nssice) {
				if (_log.isDebugEnabled()) {
					_log.debug("No SPIDP connection configured", nssice);
				}
			}
			catch (PortalException pe) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to get IDP keep alive URL", pe);
				}
			}
		}

		if ((keepAliveURL == null) ||
			keepAliveURL.equals(
				SamlKeepAliveConstants.EXPANDO_COLUMN_NAME_KEEP_ALIVE_URL)) {

			keepAliveURL = PropsUtil.get(
				PortletPropsKeys.SAML_IDP_METADATA_SESSION_KEEP_ALIVE_URL);
		}

		return keepAliveURL;
	}

	protected void includeJSP(
			HttpServletRequest request, HttpServletResponse response,
			String jspPath)
		throws IOException {

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(jspPath);

		try {
			requestDispatcher.include(request, response);
		}
		catch (ServletException se) {
			throw new IOException("Unable to include JSP " + jspPath, se);
		}
	}

	private static final String _KEY_IDENTITY_PROVIDER =
		"com.liferay.saml.web#/admin/edit_identity_provider_connection.jsp#" +
			"post";

	private static final String _KEY_SERVICE_PROVIDER =
		"com.liferay.saml.web#/admin/edit_service_provider_connection.jsp#post";

	private static final Log _log = LogFactoryUtil.getLog(
		KeepAliveSamlWebDynamicInclude.class);

	@Reference
	private SamlIdpSpConnectionLocalService _samlIdpSpConnectionLocalService;

	@Reference
	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

	@Reference
	private SamlSpIdpConnectionLocalService _samlSpIdpConnectionLocalService;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.saml.addon.keep.alive.web)"
	)
	private ServletContext _servletContext;

}
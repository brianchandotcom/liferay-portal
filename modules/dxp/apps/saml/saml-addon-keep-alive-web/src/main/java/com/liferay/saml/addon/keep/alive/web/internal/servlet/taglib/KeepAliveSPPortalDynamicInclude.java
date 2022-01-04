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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.saml.addon.keep.alive.web.internal.constants.SamlKeepAliveConstants;
import com.liferay.saml.constants.SamlWebKeys;
import com.liferay.saml.persistence.model.SamlPeerBinding;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;
import com.liferay.saml.persistence.model.SamlSpSession;
import com.liferay.saml.persistence.service.SamlPeerBindingLocalService;
import com.liferay.saml.persistence.service.SamlSpIdpConnectionLocalService;
import com.liferay.saml.persistence.service.SamlSpSessionLocalService;
import com.liferay.saml.runtime.configuration.SamlProviderConfiguration;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.util.PortletPropsKeys;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 */
@Component(immediate = true, service = DynamicInclude.class)
public class KeepAliveSPPortalDynamicInclude extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!_isEnabled(themeDisplay)) {
			return;
		}

		String keepAliveURL = _getConfiguredKeepAliveURL(httpServletRequest);

		if (Validator.isBlank(keepAliveURL)) {
			return;
		}

		SamlProviderConfiguration samlProviderConfiguration =
			_samlProviderConfigurationHelper.getSamlProviderConfiguration();

		keepAliveURL = _http.addParameter(
			keepAliveURL, "entityId", samlProviderConfiguration.entityId());

		try {
			PrintWriter printWriter = httpServletResponse.getWriter();

			printWriter.write("<script src=\"");
			printWriter.write(HtmlUtil.escapeHREF(keepAliveURL));
			printWriter.write("\" type=\"text/javascript\"></script>");
		}
		catch (IOException ioException) {
			throw new IOException(
				"Unable to include keep alive URL", ioException);
		}
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register("/html/common/themes/bottom.jsp#post");
	}

	private String _getConfiguredKeepAliveURL(
		HttpServletRequest httpServletRequest) {

		String keepAliveURL = null;

		try {
			SamlSpSession samlSpSession = _getSamlSpSession(
				httpServletRequest, _samlSpSessionLocalService);

			if (samlSpSession == null) {
				return StringPool.BLANK;
			}

			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			SamlPeerBinding samlPeerBinding =
				_samlPeerBindingLocalService.getSamlPeerBinding(
					samlSpSession.getSamlPeerBindingId());

			SamlSpIdpConnection samlSpIdpConnection =
				_samlSpIdpConnectionLocalService.getSamlSpIdpConnection(
					themeDisplay.getCompanyId(),
					samlPeerBinding.getSamlPeerEntityId());

			ExpandoBridge expandoBridge =
				samlSpIdpConnection.getExpandoBridge();

			keepAliveURL = (String)expandoBridge.getAttribute(
				SamlKeepAliveConstants.EXPANDO_COLUMN_NAME_KEEP_ALIVE_URL);
		}
		catch (PortalException portalException) {
			String message =
				"Unable to get IdP keep alive URL: " +
					portalException.getMessage();

			if (_log.isDebugEnabled()) {
				_log.debug(message, portalException);
			}
			else if (_log.isWarnEnabled()) {
				_log.warn(message);
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

	private SamlSpSession _getSamlSpSession(
		HttpServletRequest httpServletRequest,
		SamlSpSessionLocalService samlSpSessionLocalService) {

		String samlSpSessionKey = _getSamlSpSessionKey(httpServletRequest);

		if (Validator.isNotNull(samlSpSessionKey)) {
			SamlSpSession samlSpSession =
				samlSpSessionLocalService.fetchSamlSpSessionBySamlSpSessionKey(
					samlSpSessionKey);

			if (samlSpSession != null) {
				return samlSpSession;
			}
		}

		HttpSession httpSession = httpServletRequest.getSession();

		return samlSpSessionLocalService.fetchSamlSpSessionByJSessionId(
			httpSession.getId());
	}

	private String _getSamlSpSessionKey(HttpServletRequest httpServletRequest) {
		HttpSession httpSession = httpServletRequest.getSession();

		String samlSpSessionKey = (String)httpSession.getAttribute(
			SamlWebKeys.SAML_SP_SESSION_KEY);

		if (Validator.isNull(samlSpSessionKey)) {
			samlSpSessionKey = CookieKeys.getCookie(
				httpServletRequest, SamlWebKeys.SAML_SP_SESSION_KEY);
		}

		return samlSpSessionKey;
	}

	private boolean _isEnabled(ThemeDisplay themeDisplay) {
		if (!_samlProviderConfigurationHelper.isEnabled() ||
			!_samlProviderConfigurationHelper.isRoleSp() ||
			!themeDisplay.isSignedIn()) {

			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		KeepAliveSPPortalDynamicInclude.class);

	@Reference
	private Http _http;

	@Reference
	private SamlPeerBindingLocalService _samlPeerBindingLocalService;

	@Reference
	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

	@Reference
	private SamlSpIdpConnectionLocalService _samlSpIdpConnectionLocalService;

	@Reference
	private SamlSpSessionLocalService _samlSpSessionLocalService;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.saml.addon.keep.alive.web)"
	)
	private ServletContext _servletContext;

}
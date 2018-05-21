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
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.saml.addon.keep.alive.web.internal.constants.SamlKeepAliveConstants;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;
import com.liferay.saml.persistence.service.SamlSpIdpConnectionLocalService;
import com.liferay.saml.runtime.configuration.SamlProviderConfiguration;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.util.PortletPropsKeys;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 */
@Component(immediate = true, service = DynamicInclude.class)
public class KeepAliveSPPortalDynamicInclude extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest request, HttpServletResponse response,
			String key)
		throws IOException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!isEnabled(themeDisplay)) {
			return;
		}

		String keepAliveURL = getConfiguredKeepAliveURL(themeDisplay);

		if (Validator.isBlank(keepAliveURL)) {
			return;
		}

		SamlProviderConfiguration samlProviderConfiguration =
			_samlProviderConfigurationHelper.getSamlProviderConfiguration();

		keepAliveURL = _http.addParameter(
			keepAliveURL, "entityId", samlProviderConfiguration.entityId());

		try {
			PrintWriter printWriter = response.getWriter();

			printWriter.write("<script src=\"");
			printWriter.write(HtmlUtil.escapeHREF(keepAliveURL));
			printWriter.write("\" type=\"text/javascript\"></script>");
		}
		catch (IOException ioe) {
			throw new IOException("Unable to include keep alive URL", ioe);
		}
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register("/html/common/themes/bottom.jsp#post");
	}

	protected String getConfiguredKeepAliveURL(ThemeDisplay themeDisplay) {
		String keepAliveURL = null;

		try {
			SamlProviderConfiguration samlProviderConfiguration =
				_samlProviderConfigurationHelper.getSamlProviderConfiguration();

			SamlSpIdpConnection samlSpIdpConnection =
				_samlSpIdpConnectionLocalService.getSamlSpIdpConnection(
					themeDisplay.getCompanyId(),
					samlProviderConfiguration.defaultIdPEntityId());

			ExpandoBridge expandoBridge =
				samlSpIdpConnection.getExpandoBridge();

			keepAliveURL = (String)expandoBridge.getAttribute(
				SamlKeepAliveConstants.EXPANDO_COLUMN_NAME_KEEP_ALIVE_URL);
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get IDP keep alive URL", pe);
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

	protected boolean isEnabled(ThemeDisplay themeDisplay) {
		if (!_samlProviderConfigurationHelper.isEnabled()) {
			return false;
		}

		if (!_samlProviderConfigurationHelper.isRoleSp()) {
			return false;
		}

		if (!themeDisplay.isSignedIn()) {
			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		KeepAliveSPPortalDynamicInclude.class);

	@Reference
	private Http _http;

	@Reference
	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

	@Reference
	private SamlSpIdpConnectionLocalService _samlSpIdpConnectionLocalService;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.saml.addon.keep.alive.web)"
	)
	private ServletContext _servletContext;

}
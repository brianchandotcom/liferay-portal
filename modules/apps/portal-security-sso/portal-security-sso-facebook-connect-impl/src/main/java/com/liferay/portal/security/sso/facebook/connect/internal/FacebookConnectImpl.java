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

package com.liferay.portal.security.sso.facebook.connect.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.facebook.FacebookConnect;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.facebook.connect.configuration.FacebookConnectConfiguration;
import com.liferay.portal.security.sso.facebook.connect.constants.FacebookConnectConstants;
import com.liferay.portal.security.sso.facebook.connect.constants.FacebookConnectWebKeys;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Implements the OAuth protocol for Facebook Connect.
 *
 * <p>
 * This class is utilized by many of the other Facebook Connect classes via
 * {@link com.liferay.portal.facebook.FacebookConnectUtil}, which exposes all of
 * its methods statically.
 * </p>
 *
 * @author Wilson Man
 * @author Mika Koivisto
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
@Component(
	configurationPid = "com.liferay.portal.security.sso.facebook.connect.configuration.FacebookConnectConfiguration",
	immediate = true, service = FacebookConnect.class
)
@Deprecated
public class FacebookConnectImpl implements FacebookConnect {

	@Override
	public String getAccessToken(long companyId, String redirect, String code) {
		FacebookConnectConfiguration facebookConnectConfiguration =
			getFacebookConnectConfiguration(companyId);

		String url = facebookConnectConfiguration.oauthTokenURL();

		url = _http.addParameter(
			url, "client_id", facebookConnectConfiguration.appId());
		url = _http.addParameter(
			url, "client_secret", facebookConnectConfiguration.appSecret());
		url = _http.addParameter(url, "code", code);
		url = _http.addParameter(
			url, "redirect_uri",
			facebookConnectConfiguration.oauthRedirectURL());

		Http.Options options = new Http.Options();

		options.setLocation(url);

		try {
			String content = _http.URLtoString(options);

			JSONObject contentJSONObject = JSONFactoryUtil.createJSONObject(
				content);

			String accessToken = contentJSONObject.getString("access_token");

			if (Validator.isNotNull(accessToken)) {
				return accessToken;
			}

			if (_log.isDebugEnabled()) {
				String appSecret = facebookConnectConfiguration.appSecret();

				if (!appSecret.isEmpty()) {
					url = _http.setParameter(
						url, "client_secret",
						StringBundler.concat(
							appSecret.charAt(0), "...redacted...",
							appSecret.charAt(appSecret.length() - 1)));
				}

				_log.debug(
					StringBundler.concat(
						"Unable to get access token for URL ", url,
						" because of response:", content));
			}
		}
		catch (Exception exception) {
			throw new SystemException(
				"Unable to retrieve Facebook access token", exception);
		}

		return null;
	}

	@Override
	public String getAccessTokenURL(long companyId) {
		FacebookConnectConfiguration facebookConnectConfiguration =
			getFacebookConnectConfiguration(companyId);

		return facebookConnectConfiguration.oauthTokenURL();
	}

	@Override
	public String getAppId(long companyId) {
		FacebookConnectConfiguration facebookConnectConfiguration =
			getFacebookConnectConfiguration(companyId);

		return facebookConnectConfiguration.appId();
	}

	@Override
	public String getAppSecret(long companyId) {
		FacebookConnectConfiguration facebookConnectConfiguration =
			getFacebookConnectConfiguration(companyId);

		return facebookConnectConfiguration.appSecret();
	}

	@Override
	public String getAuthURL(long companyId) {
		FacebookConnectConfiguration facebookConnectConfiguration =
			getFacebookConnectConfiguration(companyId);

		return facebookConnectConfiguration.oauthAuthURL();
	}

	@Override
	public JSONObject getGraphResources(
		long companyId, String path, String accessToken, String fields) {

		try {
			String graphURL = getGraphURL(companyId);

			String url = _http.addParameter(
				graphURL.concat(path), "access_token", accessToken);

			if (Validator.isNotNull(fields)) {
				url = _http.addParameter(url, "fields", fields);
			}

			Http.Options options = new Http.Options();

			options.setLocation(url);

			String json = _http.URLtoString(options);

			return JSONFactoryUtil.createJSONObject(json);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}
		}

		return null;
	}

	@Override
	public String getGraphURL(long companyId) {
		FacebookConnectConfiguration facebookConnectConfiguration =
			getFacebookConnectConfiguration(companyId);

		return facebookConnectConfiguration.graphURL();
	}

	@Override
	public String getProfileImageURL(PortletRequest portletRequest) {
		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			portletRequest);

		httpServletRequest = _portal.getOriginalServletRequest(
			httpServletRequest);

		HttpSession session = httpServletRequest.getSession();

		String facebookId = (String)session.getAttribute(
			FacebookConnectWebKeys.FACEBOOK_USER_ID);

		if (Validator.isNull(facebookId)) {
			return null;
		}

		long companyId = _portal.getCompanyId(httpServletRequest);

		String token = (String)session.getAttribute(
			FacebookConnectWebKeys.FACEBOOK_ACCESS_TOKEN);

		JSONObject jsonObject = getGraphResources(
			companyId, "/me", token, "id,picture");

		return jsonObject.getString("picture");
	}

	@Override
	public String getRedirectURL(long companyId) {
		FacebookConnectConfiguration facebookConnectConfiguration =
			getFacebookConnectConfiguration(companyId);

		return facebookConnectConfiguration.oauthRedirectURL();
	}

	@Override
	public boolean isEnabled(long companyId) {
		FacebookConnectConfiguration facebookConnectConfiguration =
			getFacebookConnectConfiguration(companyId);

		return facebookConnectConfiguration.enabled();
	}

	@Override
	public boolean isVerifiedAccountRequired(long companyId) {
		FacebookConnectConfiguration facebookConnectConfiguration =
			getFacebookConnectConfiguration(companyId);

		return facebookConnectConfiguration.verifiedAccountRequired();
	}

	protected FacebookConnectConfiguration getFacebookConnectConfiguration(
		long companyId) {

		try {
			return _configurationProvider.getConfiguration(
				FacebookConnectConfiguration.class,
				new CompanyServiceSettingsLocator(
					companyId, FacebookConnectConstants.SERVICE_NAME));
		}
		catch (ConfigurationException configurationException) {
			_log.error(
				"Unable to get Facebook Connect configuration",
				configurationException);
		}

		return null;
	}

	@Reference(unbind = "-")
	protected void setConfigurationProvider(
		ConfigurationProvider configurationProvider) {

		_configurationProvider = configurationProvider;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FacebookConnectImpl.class);

	private ConfigurationProvider _configurationProvider;

	@Reference
	private Http _http;

	@Reference
	private Portal _portal;

}
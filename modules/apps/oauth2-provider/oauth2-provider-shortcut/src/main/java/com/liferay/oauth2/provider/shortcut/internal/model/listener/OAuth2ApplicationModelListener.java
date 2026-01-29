/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.shortcut.internal.model.listener;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.net.HttpURLConnection;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Nilton Vieira
 */
@Component(service = ModelListener.class)
public class OAuth2ApplicationModelListener
	extends BaseModelListener<OAuth2Application> {

	@Override
	public void onAfterUpdate(
			OAuth2Application originalOAuth2Application,
			OAuth2Application oAuth2Application)
		throws ModelListenerException {

		if (StringUtil.equals(
				originalOAuth2Application.getClientId(),
				oAuth2Application.getClientId()) &&
			StringUtil.equals(
				originalOAuth2Application.getClientSecret(),
				oAuth2Application.getClientSecret())) {

			return;
		}

		try {
			_postOAuth2Application(oAuth2Application);
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	@Override
	public void onBeforeRemove(OAuth2Application oAuth2Application)
		throws ModelListenerException {

		if (!StringUtil.equals(
				oAuth2Application.getExternalReferenceCode(),
				"ANALYTICS-CLOUD")) {

			return;
		}

		throw new ModelListenerException("Unable to delete OAuth2Application");
	}

	@Override
	public void onBeforeUpdate(
			OAuth2Application originalOAuth2Application,
			OAuth2Application oAuth2Application)
		throws ModelListenerException {

		if (!StringUtil.equals(
				originalOAuth2Application.getExternalReferenceCode(),
				"ANALYTICS-CLOUD")) {

			return;
		}

		if (!StringUtil.equals(
				originalOAuth2Application.getClientAuthenticationMethod(),
				oAuth2Application.getClientAuthenticationMethod()) ||
			(originalOAuth2Application.getClientCredentialUserId() !=
				oAuth2Application.getClientCredentialUserId()) ||
			(originalOAuth2Application.getClientProfile() !=
				oAuth2Application.getClientProfile()) ||
			!StringUtil.equals(
				originalOAuth2Application.getHomePageURL(),
				oAuth2Application.getHomePageURL()) ||
			(originalOAuth2Application.getOAuth2ApplicationScopeAliasesId() !=
				oAuth2Application.getOAuth2ApplicationScopeAliasesId()) ||
			!StringUtil.equals(
				originalOAuth2Application.getRedirectURIs(),
				oAuth2Application.getRedirectURIs())) {

			throw new ModelListenerException(
				"Unable to update OAuth2Application");
		}
	}

	private void _postOAuth2Application(OAuth2Application oAuth2Application)
		throws Exception {

		AnalyticsConfiguration analyticsConfiguration =
			_analyticsSettingsManager.getAnalyticsConfiguration(
				oAuth2Application.getCompanyId());

		if (Validator.isNull(analyticsConfiguration.token())) {
			return;
		}

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			new String(Base64.decode(analyticsConfiguration.token())));

		Http.Options options = new Http.Options();

		options.addPart("oAuthClientId", oAuth2Application.getClientId());
		options.addPart(
			"oAuthClientSecret", oAuth2Application.getClientSecret());
		options.setLocation(
			StringUtil.replace(
				jsonObject.getString("url"), "data_source/connect",
				"data_source/" +
					analyticsConfiguration.liferayAnalyticsDataSourceId()));
		options.setPost(true);

		_http.URLtoString(options);

		Http.Response response = options.getResponse();

		if (response.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new Exception("Unable to update analytics data source");
		}
	}

	@Reference
	private AnalyticsSettingsManager _analyticsSettingsManager;

	@Reference
	private Http _http;

	@Reference
	private JSONFactory _jsonFactory;

}
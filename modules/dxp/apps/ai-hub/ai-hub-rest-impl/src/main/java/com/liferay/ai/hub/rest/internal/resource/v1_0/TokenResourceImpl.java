/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.internal.resource.v1_0;

import com.liferay.ai.hub.configuration.AIHubConfiguration;
import com.liferay.ai.hub.rest.dto.v1_0.Token;
import com.liferay.ai.hub.rest.resource.v1_0.TokenResource;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Http;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Feliphe Marinho
 * @author Rafael Praxedes
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/token.properties",
	scope = ServiceScope.PROTOTYPE, service = TokenResource.class
)
public class TokenResourceImpl extends BaseTokenResourceImpl {

	@Override
	public Token postToken() throws Exception {
		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-62272")) {

			throw new UnsupportedOperationException();
		}

		Http.Options options = new Http.Options();

		AIHubConfiguration aiHubConfiguration =
			_configurationProvider.getCompanyConfiguration(
				AIHubConfiguration.class, contextCompany.getCompanyId());

		options.setLocation(
			aiHubConfiguration.serviceURL() + "/o/oauth2/token");

		options.setMethod(Http.Method.POST);

		options.addPart("client_id", aiHubConfiguration.clientId());
		options.addPart("client_secret", aiHubConfiguration.clientSecret());
		options.addPart("grant_type", "client_credentials");

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			_http.URLtoString(options));

		return new Token() {
			{
				setAccessToken(() -> jsonObject.getString("access_token"));
				setScope(() -> jsonObject.getString("scope"));
			}
		};
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Http _http;

	@Reference
	private JSONFactory _jsonFactory;

}
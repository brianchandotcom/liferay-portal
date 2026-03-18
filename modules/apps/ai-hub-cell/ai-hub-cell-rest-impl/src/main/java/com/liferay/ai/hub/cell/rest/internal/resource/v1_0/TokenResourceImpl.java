/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.cell.rest.internal.resource.v1_0;

import com.liferay.ai.hub.cell.configuration.AIHubCellConfiguration;
import com.liferay.ai.hub.cell.rest.dto.v1_0.Token;
import com.liferay.ai.hub.cell.rest.resource.v1_0.TokenResource;
import com.liferay.ai.hub.cell.security.JWTTokenUtil;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Http;

import java.util.concurrent.TimeUnit;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Feliphe Marinho
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

		AIHubCellConfiguration aiHubCellConfiguration =
			_configurationProvider.getCompanyConfiguration(
				AIHubCellConfiguration.class, contextCompany.getCompanyId());

		options.addPart("client_id", aiHubCellConfiguration.clientId());
		options.addPart("client_secret", aiHubCellConfiguration.clientSecret());

		options.addPart("grant_type", "client_credentials");
		options.setLocation(
			aiHubCellConfiguration.serviceURL() + "/o/oauth2/token");
		options.setMethod(Http.Method.POST);

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			_http.URLtoString(options));

		return new Token() {
			{
				setAccessToken(() -> jsonObject.getString("access_token"));
				setScope(() -> jsonObject.getString("scope"));
				setServiceURL(aiHubCellConfiguration::serviceURL);
				setUserToken(
					() -> JWTTokenUtil.generateToken(
						TimeUnit.MINUTES.toMillis(1),
						contextCompany.getVirtualHostname(),
						contextUser.getUserId()));
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
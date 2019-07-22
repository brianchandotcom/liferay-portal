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

package com.liferay.document.library.opener.one.drive.web.internal.oauth;

import com.github.scribejava.apis.MicrosoftAzureActiveDirectory20Api;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.builder.ServiceBuilderOAuth20;
import com.github.scribejava.core.oauth.OAuth20Service;

import com.liferay.document.library.opener.one.drive.web.internal.configuration.DLOneDriveCompanyConfiguration;
import com.liferay.document.library.opener.one.drive.web.internal.constants.DLOpenerOneDriveWebConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = OAuth2Manager.class)
public class OAuth2Manager {

	public AccessToken createAccessToken(
			long companyId, long userId, String code, String portalURL)
		throws Exception {

		try (OAuth20Service oAuth20Service = _getOAuth20Service(
				companyId, _getRedirectURI(portalURL))) {

			AccessToken accessToken = new AccessToken(
				oAuth20Service.getAccessToken(code));

			_accessTokenStore.add(companyId, userId, accessToken);

			return accessToken;
		}
		catch (IOException ioe) {
			throw new PortalException(ioe);
		}
	}

	public Optional<AccessToken> getAccessTokenOptional(
			long companyId, long userId)
		throws PortalException {

		Optional<AccessToken> accessTokenOptional =
			_accessTokenStore.getAccessTokenOptional(companyId, userId);

		if (!accessTokenOptional.isPresent()) {
			return Optional.empty();
		}

		AccessToken accessToken = accessTokenOptional.get();

		if (!accessToken.isValid()) {
			return Optional.of(
				refreshOAuth2AccessToken(companyId, userId, accessToken));
		}

		return Optional.of(accessToken);
	}

	public String getAuthorizationURL(
			long companyId, String portalURL, String state)
		throws PortalException {

		try (OAuth20Service oAuth20Service = _getOAuth20Service(
				companyId, _getRedirectURI(portalURL))) {

			return oAuth20Service.getAuthorizationUrl(state);
		}
		catch (IOException ioe) {
			throw new PortalException(ioe);
		}
	}

	public AccessToken refreshOAuth2AccessToken(
			long companyId, long userId, AccessToken accessToken)
		throws PortalException {

		try (OAuth20Service oAuth20Service = _getOAuth20Service(
				companyId, null)) {

			AccessToken newAccessToken = new AccessToken(
				oAuth20Service.refreshAccessToken(
					accessToken.getRefreshToken()));

			_accessTokenStore.add(companyId, userId, newAccessToken);

			return newAccessToken;
		}
		catch (ExecutionException | InterruptedException | IOException e) {
			throw new PortalException(e);
		}
	}

	public void revokeOAuth2AccessToken(long companyId, long userId) {
		Optional<AccessToken> accessTokenOptional =
			_accessTokenStore.getAccessTokenOptional(companyId, userId);

		if (!accessTokenOptional.isPresent()) {
			return;
		}

		_accessTokenStore.delete(companyId, userId);
	}

	private DLOneDriveCompanyConfiguration _getDLOneDriveCompanyConfiguration(
			long companyId)
		throws ConfigurationException {

		return _configurationProvider.getCompanyConfiguration(
			DLOneDriveCompanyConfiguration.class, companyId);
	}

	private OAuth20Service _getOAuth20Service(
			long companyId, String redirectURL)
		throws PortalException {

		DLOneDriveCompanyConfiguration dlOneDriveCompanyConfiguration =
			_getDLOneDriveCompanyConfiguration(companyId);

		ServiceBuilderOAuth20 serviceBuilderOAuth20 = new ServiceBuilder(
			dlOneDriveCompanyConfiguration.clientId()
		).apiSecret(
			dlOneDriveCompanyConfiguration.clientSecret()
		).callback(
			redirectURL
		).withScope(
			"https://graph.microsoft.com/.default"
		).apiKey(
			dlOneDriveCompanyConfiguration.clientId()
		);

		try (OAuth20Service oAuth20Service = serviceBuilderOAuth20.build(
				MicrosoftAzureActiveDirectory20Api.custom(
					dlOneDriveCompanyConfiguration.tenant()))) {

			return oAuth20Service;
		}
		catch (Exception e) {
			throw new PortalException(
				"The OAuth20Service could not be created", e);
		}
	}

	private String _getRedirectURI(String portalURL) {
		return portalURL + Portal.PATH_MODULE +
			DLOpenerOneDriveWebConstants.ONEDDRIVE_SERVLET_PATH;
	}

	private final AccessTokenStore _accessTokenStore = new AccessTokenStore();

	@Reference
	private ConfigurationProvider _configurationProvider;

}
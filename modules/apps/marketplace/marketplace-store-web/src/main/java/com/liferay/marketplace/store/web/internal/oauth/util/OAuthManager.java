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

package com.liferay.marketplace.store.web.internal.oauth.util;

import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
import com.liferay.marketplace.store.web.internal.configuration.MarketplaceStoreWebConfigurationValues;
import com.liferay.marketplace.store.web.internal.oauth.api.MarketplaceApi;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

import org.scribe.builder.api.Api;
import org.scribe.model.OAuthConfig;
import org.scribe.model.SignatureType;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

/**
 * @author Ryan Park
 */
public class OAuthManager {

	public OAuthManager(ExpandoValueLocalService expandoValueLocalService) {
		_expandoValueLocalService = expandoValueLocalService;
	}

	public void deleteAccessToken(User user) throws PortalException {
		_expandoValueLocalService.deleteValue(
			user.getCompanyId(), User.class.getName(), "MP", "accessSecret",
			user.getUserId());
		_expandoValueLocalService.deleteValue(
			user.getCompanyId(), User.class.getName(), "MP", "accessToken",
			user.getUserId());
	}

	public void deleteRequestToken(User user) throws PortalException {
		_expandoValueLocalService.deleteValue(
			user.getCompanyId(), User.class.getName(), "MP", "requestSecret",
			user.getUserId());
		_expandoValueLocalService.deleteValue(
			user.getCompanyId(), User.class.getName(), "MP", "requestToken",
			user.getUserId());
	}

	public Token getAccessToken(User user) throws PortalException {
		ExpandoValue secretExpandoValue = _expandoValueLocalService.getValue(
			user.getCompanyId(), User.class.getName(), "MP", "accessSecret",
			user.getUserId());
		ExpandoValue tokenExpandoValue = _expandoValueLocalService.getValue(
			user.getCompanyId(), User.class.getName(), "MP", "accessToken",
			user.getUserId());

		if ((secretExpandoValue == null) || (tokenExpandoValue == null)) {
			return null;
		}

		return new Token(
			tokenExpandoValue.getString(), secretExpandoValue.getString());
	}

	public OAuthService getOAuthService() {
		Api api = new MarketplaceApi();

		OAuthConfig oAuthConfig = new OAuthConfig(
			MarketplaceStoreWebConfigurationValues.MARKETPLACE_KEY,
			MarketplaceStoreWebConfigurationValues.MARKETPLACE_SECRET,
			MarketplaceStoreWebConfigurationValues.MARKETPLACE_URL,
			SignatureType.Header, null, null);

		return api.createService(oAuthConfig);
	}

	public Token getRequestToken(User user) throws PortalException {
		ExpandoValue secretExpandoValue = _expandoValueLocalService.getValue(
			user.getCompanyId(), User.class.getName(), "MP", "requestSecret",
			user.getUserId());
		ExpandoValue tokenExpandoValue = _expandoValueLocalService.getValue(
			user.getCompanyId(), User.class.getName(), "MP", "requestToken",
			user.getUserId());

		if ((secretExpandoValue == null) || (tokenExpandoValue == null)) {
			return null;
		}

		return new Token(
			tokenExpandoValue.getString(), secretExpandoValue.getString());
	}

	public void updateAccessToken(User user, Token token)
		throws PortalException {

		_expandoValueLocalService.addValue(
			user.getCompanyId(), User.class.getName(), "MP", "accessSecret",
			user.getUserId(), token.getSecret());
		_expandoValueLocalService.addValue(
			user.getCompanyId(), User.class.getName(), "MP", "accessToken",
			user.getUserId(), token.getToken());
	}

	public void updateRequestToken(User user, Token token)
		throws PortalException {

		_expandoValueLocalService.addValue(
			user.getCompanyId(), User.class.getName(), "MP", "requestSecret",
			user.getUserId(), token.getSecret());
		_expandoValueLocalService.addValue(
			user.getCompanyId(), User.class.getName(), "MP", "requestToken",
			user.getUserId(), token.getToken());
	}

	private final ExpandoValueLocalService _expandoValueLocalService;

}
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

package com.liferay.portal.security.sso.openid.connect.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectProviderMetadataFactory;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceException;

import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.nimbusds.oauth2.sdk.id.Issuer;
import com.nimbusds.openid.connect.sdk.SubjectType;
import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;

import java.io.IOException;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;

import net.minidev.json.JSONObject;

import org.apache.commons.lang3.time.StopWatch;

/**
 * @author Edward C. Han
 */
public class OpenIdConnectProviderMetadataFactoryImpl
	implements OpenIdConnectProviderMetadataFactory {

	public OpenIdConnectProviderMetadataFactoryImpl(
			String providerName, String issuerURL, String[] subjectTypes,
			String jwksURL, String authorizationEndPointURL,
			String tokenEndPointURL, String userInfoEndPointURL)
		throws OpenIdConnectServiceException.ProviderException {

		_providerName = providerName;

		try {
			Issuer issuer = new Issuer(issuerURL);

			List<SubjectType> subjectTypeList = new ArrayList<>();

			for (String subjectType : subjectTypes) {
				subjectTypeList.add(SubjectType.parse(subjectType));
			}

			URI jwksURI = new URI(jwksURL);
			URI authorizationEndpointURI = new URI(authorizationEndPointURL);
			URI tokenEndpointURI = new URI(tokenEndPointURL);
			URI userInfoEndpointURI = new URI(userInfoEndPointURL);

			_oidcProviderMetadata = new OIDCProviderMetadata(
				issuer, subjectTypeList, jwksURI);

			_oidcProviderMetadata.setAuthorizationEndpointURI(
				authorizationEndpointURI);
			_oidcProviderMetadata.setTokenEndpointURI(tokenEndpointURI);
			_oidcProviderMetadata.setUserInfoEndpointURI(userInfoEndpointURI);

			_discoveryEndPointURL = null;
			_cacheInMilliseconds = 0;
		}
		catch (URISyntaxException urise) {
			throw new OpenIdConnectServiceException.ProviderException(
				"Error processing configured URI for provider " +
					_providerName,
				urise);
		}
		catch (ParseException pe) {
			throw new OpenIdConnectServiceException.ProviderException(
				"Error processing configured SubjectType for provider " +
					_providerName,
				pe);
		}
	}

	public OpenIdConnectProviderMetadataFactoryImpl(
		String providerName, URL discoveryEndPointURL) {

		this(providerName, discoveryEndPointURL, 0);
	}

	public OpenIdConnectProviderMetadataFactoryImpl(
		String providerName, URL discoveryEndPointURL,
		long cacheInMilliseconds) {

		_providerName = providerName;
		_discoveryEndPointURL = discoveryEndPointURL;
		_cacheInMilliseconds = cacheInMilliseconds;
		_oidcProviderMetadata = null;
	}

	@Override
	public OIDCProviderMetadata getOIDCProviderMetadata()
		throws OpenIdConnectServiceException.ProviderException {

		long currentTime = System.currentTimeMillis();

		if (needsRefresh(currentTime)) {
			refresh(currentTime);
		}

		return _oidcProviderMetadata;
	}

	protected boolean needsRefresh(long time) {
		if (_oidcProviderMetadata == null) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"OIDC Provider " + _providerName +
						": No cached metadata found, refreshing");
			}

			return true;
		}

		long elapsedTime = time - _lastRefreshTimestamp;

		if ((_cacheInMilliseconds > 0) &&
			(elapsedTime > _cacheInMilliseconds)) {

			if (_log.isInfoEnabled()) {
				_log.info(
					"OIDC Provider " + _providerName +
						": Cached metadata expired, refreshing");
			}

			return true;
		}

		return false;
	}

	protected synchronized void refresh(long time)
		throws OpenIdConnectServiceException.ProviderException {

		if (needsRefresh(time)) {
			StopWatch stopWatch = new StopWatch();

			stopWatch.start();

			try {
				HTTPRequest httpRequest = new HTTPRequest(
					HTTPRequest.Method.GET, _discoveryEndPointURL);

				HTTPResponse httpResponse = httpRequest.send();

				JSONObject jsonObject = httpResponse.getContentAsJSONObject();

				_oidcProviderMetadata = OIDCProviderMetadata.parse(jsonObject);

				_lastRefreshTimestamp = time;
			}
			catch (IOException | ParseException e) {
				throw new OpenIdConnectServiceException.ProviderException(
					"Error getting OIDC Provider Metadata for provider " +
						_providerName,
					e);
			}
			finally {
				stopWatch.stop();

				if (_log.isInfoEnabled()) {
					_log.info(
						"Getting OIDCProvider Metadata from " +
							_discoveryEndPointURL + " took " +
								stopWatch.getTime() + "ms");
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OpenIdConnectProviderMetadataFactoryImpl.class);

	private final long _cacheInMilliseconds;
	private final URL _discoveryEndPointURL;
	private long _lastRefreshTimestamp;
	private OIDCProviderMetadata _oidcProviderMetadata;
	private final String _providerName;

}
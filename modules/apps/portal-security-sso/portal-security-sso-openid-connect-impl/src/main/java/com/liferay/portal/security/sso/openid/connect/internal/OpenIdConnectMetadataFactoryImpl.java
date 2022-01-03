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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceException;

import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.nimbusds.oauth2.sdk.id.Issuer;
import com.nimbusds.openid.connect.sdk.SubjectType;
import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;
import com.nimbusds.openid.connect.sdk.rp.OIDCClientMetadata;

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
public class OpenIdConnectMetadataFactoryImpl
	implements OpenIdConnectMetadataFactory {

	public OpenIdConnectMetadataFactoryImpl(
			String providerName, String registeredIdTokenSigningAlg,
			String[] idTokenSigningAlgValues, String issuerURL,
			String[] subjectTypes, String jwksURL,
			String authorizationEndPointURL, String tokenEndPointURL,
			String userInfoEndPointURL)
		throws OpenIdConnectServiceException.ProviderException {

		// TODO LPS-139642

		_providerName = providerName;

		_cacheInMilliseconds = 0;
		_discoveryEndPointURL = null;

		try {
			List<SubjectType> subjectTypesList = new ArrayList<>();

			for (String subjectType : subjectTypes) {
				subjectTypesList.add(SubjectType.parse(subjectType));
			}

			_oidcProviderMetadata = new OIDCProviderMetadata(
				new Issuer(issuerURL), subjectTypesList, new URI(jwksURL));

			_oidcProviderMetadata.setAuthorizationEndpointURI(
				new URI(authorizationEndPointURL));

			List<JWSAlgorithm> jwsAlgorithms = new ArrayList<>();

			for (String idTokenSigningAlgValue : idTokenSigningAlgValues) {
				jwsAlgorithms.add(JWSAlgorithm.parse(idTokenSigningAlgValue));
			}

			_oidcProviderMetadata.setIDTokenJWSAlgs(jwsAlgorithms);

			_oidcProviderMetadata.setTokenEndpointURI(
				new URI(tokenEndPointURL));
			_oidcProviderMetadata.setUserInfoEndpointURI(
				new URI(userInfoEndPointURL));

			_initOpenIdConnectClientMetadata(registeredIdTokenSigningAlg);

			_refreshClientMetadata(_oidcProviderMetadata);
		}
		catch (ParseException parseException) {
			throw new OpenIdConnectServiceException.ProviderException(
				StringBundler.concat(
					"Invalid subject types ", StringUtil.merge(subjectTypes),
					"for OpenId Connect provider \"", _providerName, "\": ",
					parseException.getMessage()),
				parseException);
		}
		catch (URISyntaxException uriSyntaxException) {
			throw new OpenIdConnectServiceException.ProviderException(
				StringBundler.concat(
					"Invalid URLs for OpenId Connect provider \"",
					_providerName, "\": ", uriSyntaxException.getMessage()),
				uriSyntaxException);
		}
	}

	public OpenIdConnectMetadataFactoryImpl(
		String providerName, URL discoveryEndPointURL) {

		this(providerName, discoveryEndPointURL, 0, null);
	}

	public OpenIdConnectMetadataFactoryImpl(
		String providerName, URL discoveryEndPointURL, long cacheInMilliseconds,
		String registeredIdTokenSigningAlg) {

		_providerName = providerName;
		_discoveryEndPointURL = discoveryEndPointURL;
		_cacheInMilliseconds = cacheInMilliseconds;

		_initOpenIdConnectClientMetadata(registeredIdTokenSigningAlg);
	}

	@Override
	public OIDCClientMetadata getOIDCClientMetadata() {
		return _oidcClientMetadata;
	}

	@Override
	public OIDCProviderMetadata getOIDCProviderMetadata()
		throws OpenIdConnectServiceException.ProviderException {

		long currentTime = System.currentTimeMillis();

		if (_needsRefresh(currentTime)) {
			_refresh(currentTime);
		}

		return _oidcProviderMetadata;
	}

	private void _initOpenIdConnectClientMetadata(
		String registeredIdTokenSigningAlg) {

		_oidcClientMetadata = new OIDCClientMetadata();

		_oidcClientMetadata.applyDefaults();

		if (!Validator.isBlank(registeredIdTokenSigningAlg)) {
			_oidcClientMetadata.setIDTokenJWSAlg(
				JWSAlgorithm.parse(registeredIdTokenSigningAlg));
		}
		else {
			if (_log.isWarnEnabled()) {
				JWSAlgorithm jwsAlgorithm =
					_oidcClientMetadata.getIDTokenJWSAlg();

				_log.warn(
					"Using the default ID token signing algorithm " +
						jwsAlgorithm.getName());
			}
		}
	}

	private boolean _needsRefresh(long time) {
		if (_oidcProviderMetadata == null) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Refreshing new OpenId Connect provider \"" +
						_providerName + "\"");
			}

			return true;
		}

		long elapsedTime = time - _lastRefreshTimestamp;

		if ((_cacheInMilliseconds > 0) &&
			(elapsedTime > _cacheInMilliseconds)) {

			if (_log.isInfoEnabled()) {
				_log.info(
					"Refreshing stale OpenId Connect provider \"" +
						_providerName + "\"");
			}

			return true;
		}

		return false;
	}

	private synchronized void _refresh(long time)
		throws OpenIdConnectServiceException.ProviderException {

		if (!_needsRefresh(time)) {
			return;
		}

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		try {
			HTTPRequest httpRequest = new HTTPRequest(
				HTTPRequest.Method.GET, _discoveryEndPointURL);

			HTTPResponse httpResponse = httpRequest.send();

			JSONObject jsonObject = httpResponse.getContentAsJSONObject();

			_oidcProviderMetadata = OIDCProviderMetadata.parse(jsonObject);

			_refreshClientMetadata(_oidcProviderMetadata);

			_lastRefreshTimestamp = time;
		}
		catch (IOException | ParseException exception) {
			throw new OpenIdConnectServiceException.ProviderException(
				StringBundler.concat(
					"Unable to get metadata for OpenId Connect provider \"",
					_providerName, "\" from ", _discoveryEndPointURL, ": ",
					exception.getMessage()),
				exception);
		}
		finally {
			stopWatch.stop();

			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"Getting OpenId Connect provider metadata from ",
						_discoveryEndPointURL, " took ", stopWatch.getTime(),
						"ms"));
			}
		}
	}

	/**
	 * Client metadata has nothing to with provider metadata, but in order to
	 * minimize potential breaking changes, we will leave this behavior even
	 * though it is goes beyond the specification.
	 */
	private synchronized void _refreshClientMetadata(
		OIDCProviderMetadata oidcProviderMetadata) {

		List<JWEAlgorithm> jweAlgorithms =
			oidcProviderMetadata.getIDTokenJWEAlgs();

		if (ListUtil.isNotEmpty(jweAlgorithms)) {
			_oidcClientMetadata.setIDTokenJWEAlg(jweAlgorithms.get(0));
		}

		_oidcClientMetadata.setJWKSetURI(oidcProviderMetadata.getJWKSetURI());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OpenIdConnectMetadataFactoryImpl.class);

	private final long _cacheInMilliseconds;
	private final URL _discoveryEndPointURL;
	private long _lastRefreshTimestamp;
	private OIDCClientMetadata _oidcClientMetadata;
	private OIDCProviderMetadata _oidcProviderMetadata;
	private final String _providerName;

}
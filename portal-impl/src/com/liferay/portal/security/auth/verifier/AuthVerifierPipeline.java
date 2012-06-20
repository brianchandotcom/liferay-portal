/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.auth.verifier;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.AuthenticationContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

import jodd.util.Wildcard;

/**
 * AuthVerifierPipeline is responsible for applying
 * matching portal.authentication.verifier.pipeline on the current
 * {@link AuthenticationContext} to get user from request.<br />
 * <br />
 * There are 3 main methods:<ul>
 *     <li>{@link #verifyRequest(AuthenticationContext)}</li>
 *     <li>{@link #register(AuthVerifierConfiguration)}</li>
 *     <li>{@link #unregister(AuthVerifierConfiguration)}</li>
 * </ul>
 *
 * @author Tomas Polesovsky
 */
public class AuthVerifierPipeline {

	/**
	 * Register new configuration into pipeline before existing verifiers
	 * @param configuration to be registered, must contain verifier and config
	 */
	public static void register(AuthVerifierConfiguration configuration) {
		if (Validator.isNull(configuration) ||
			Validator.isNull(configuration.getAuthVerifier()) ||
			Validator.isNull(configuration.getConfiguration())) {

			throw new IllegalArgumentException("AuthVerifierConfiguration, " +
				"authVerifier or configuration is null!");
		}

		_instance._register(configuration);
	}

	/**
	 * Removes configuration from the pipeline
	 * @param configuration to be removed from pipeline
	 */
	public static void unregister(AuthVerifierConfiguration configuration) {
		if (Validator.isNull(configuration)) {
			throw new IllegalArgumentException("AuthVerifierConfiguration is " +
				"null!");
		}

		_instance._unregister(configuration);
	}

	/**
	 * Use portal.authentication.verifier pipeline to obtain user from request.
	 * <br />
	 * <br />
	 * Filter all verifiers to those who are mapped to the current URL
	 * (please see portal.authentication.verifier.%VerifierSimpleName%.urls
	 * portal property). Then walk through the verifiers to find the first which
	 * returns {@link VerificationResult.State#SUCCESS} or
	 * {@link VerificationResult.State#INVALID_CREDENTIALS}. If there is such
	 * AuthVerifier then adds missing entries from the verifier's initial
	 * configuration into
	 * {@link VerificationResult#getAuthenticationSettings()} and returns.<br />
	 * <br />
	 * If there is no verifier that can extract user from request then returns
	 * {@link VerificationResult} with default Guest user.
	 *
	 * @param authCtx Not null context with HttpServletRequest and
	 *                HttpServletResponse
	 * @return Not null VerificationResult with authenticated or default user
	 */
	public static VerificationResult verifyRequest(
		AuthenticationContext authCtx) throws SystemException, PortalException {

		if (authCtx == null) {
			throw new IllegalStateException(
				"AuthenticationContext is not set!");
		}

		return _instance._verifyRequest(authCtx);
	}

	protected void _register(AuthVerifierConfiguration configuration) {
		_verifiersPipeline.add(0, configuration);
	}

	protected void _unregister(AuthVerifierConfiguration configuration) {
		_verifiersPipeline.remove(configuration);
	}

	protected VerificationResult _verifyRequest(AuthenticationContext authCtx)
		throws SystemException, PortalException {

		List<AuthVerifierConfiguration> matchingVerifiers =
			getMatchingVerifiers(authCtx);

		for (AuthVerifierConfiguration verifierConfig : matchingVerifiers) {

			AuthVerifier verifier = verifierConfig.getAuthVerifier();
			Properties authConfig = verifierConfig.getConfiguration();

			VerificationResult result = null;

			try {
				result = verifier.verify(authCtx, authConfig);
			} catch (Exception e) {
				_log.error("Exception in " + verifier.getClass().getName() +
					" during authentication verification, omitting.", e);

				continue;
			}

			if (result == null) {
				_log.error("Verifier didn't return any result! [AuthVerifier]:"
					+ " [" + verifier.getClass().getName() + "]");

				continue;
			}

			// continue only if verification state is N/A
			if (result.getState() !=
				VerificationResult.State.NOT_APPLICABLE) {

				Map<String, Object> mergedSettings = mergeSettings(
					authConfig, result.getAuthenticationSettings());

				result.setAuthenticationSettings(mergedSettings);

				return result;
			}
		}

		// fallback to guest
		return createGuestVerificationResult(authCtx);
	}

	protected boolean canApply(
		AuthVerifierConfiguration verifierConfig, String requestURI) {

		String[] urls = StringUtil.split(verifierConfig.getConfiguration()
			.getProperty("urls"));

		if (urls.length == 0) {
			_log.error("Verifier is missing its url configuration! " +
				"[AuthVerifier]: [" + verifierConfig.getAuthVerifier()
				.getClass().getName() + "]");

			return false;
		}

		return Wildcard.matchOne(requestURI, urls) > -1;
	}

	protected VerificationResult createGuestVerificationResult(
		AuthenticationContext authenticationContext) throws
		SystemException, PortalException {

		// TODO: cache guestVerificationResult per companyId
		long companyId = PortalUtil.getCompanyId(
			authenticationContext.getHttpServletRequest());

		long guestId = UserLocalServiceUtil.getDefaultUserId(companyId);

		VerificationResult result = new VerificationResult();

		result.setUserId(guestId);
		result.setState(VerificationResult.State.SUCCESS);

		return result;
	}

	protected List<AuthVerifierConfiguration> getMatchingVerifiers(
		AuthenticationContext authenticationContext) {

		List<AuthVerifierConfiguration> result =
			new ArrayList<AuthVerifierConfiguration>();

		String requestURI = authenticationContext.getHttpServletRequest()
			.getRequestURI();

		for (AuthVerifierConfiguration verifierConfig : _verifiersPipeline) {
			if (canApply(verifierConfig, requestURI)) {
				result.add(verifierConfig);
			}
		}

		return result;
	}

	protected void loadVerifiersPipeline() {
		List<AuthVerifierConfiguration> result =
			new ArrayList<AuthVerifierConfiguration>();

		for (String authVerifierClass :
			PropsValues.PORTAL_AUTHENTICATION_VERIFIER_PIPELINE) {

			AuthVerifierConfiguration authVerifierConfiguration =
				new AuthVerifierConfiguration();

			try {
				AuthVerifier authVerifier =
					(AuthVerifier) InstancePool.get(authVerifierClass);

				if (authVerifier == null) {
					_log.error("Couldn't instantiate " + authVerifierClass);
					continue;
				}

				String verifierConfigPrefix = _PORTAL_AUTHENTICATION_VERIFIER +
					authVerifier.getClass().getSimpleName() + ".";
				Properties verifierConfiguration = PropsUtil.getProperties(
					verifierConfigPrefix, true);

				authVerifierConfiguration.setAuthVerifier(authVerifier);
				authVerifierConfiguration.setConfiguration(
					verifierConfiguration);
				result.add(authVerifierConfiguration);
			} catch (Exception e) {
				_log.error("Couldn't initialize AuthVerifier: "
					+ authVerifierClass, e);
			}
		}

		_verifiersPipeline.addAll(result);
	}

	protected Map<String, Object> mergeSettings(
		Properties verifierConfiguration,
		Map<String, Object> verifierResultSettings) {

		Map<String, Object> result = new HashMap<String, Object>();

		if (verifierConfiguration != null) {
			for (String key : verifierConfiguration.stringPropertyNames()) {
				result.put(key, verifierConfiguration.getProperty(key));
			}
		}

		result.putAll(verifierResultSettings);

		return result;
	}

	private AuthVerifierPipeline() {
		_verifiersPipeline =
			new CopyOnWriteArrayList<AuthVerifierConfiguration>();

		loadVerifiersPipeline();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AuthVerifierPipeline.class);

	private static final String _PORTAL_AUTHENTICATION_VERIFIER =
		"portal.authentication.verifier.";
	private static AuthVerifierPipeline _instance = new AuthVerifierPipeline();
	private static List<AuthVerifierConfiguration> _verifiersPipeline;

}
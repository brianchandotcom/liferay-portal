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
import com.liferay.portal.security.auth.AuthenticationContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import jodd.util.Wildcard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Tomas Polesovsky
 */
public class PortalAuthenticationVerifierImpl implements PortalAuthenticationVerifier {

	/**
	 * Fills AuthenticationContext with configuration & VerificationResult
	 */
	public VerificationResult verifyRequest(AuthenticationContext authCtx)
		throws SystemException, PortalException {

		if (authCtx == null) {
			throw new IllegalStateException(
				"AuthenticationContext is not set!");
		}

		List<AuthVerifierConfiguration> matchingVerifiers =
			getMatchingVerifiers(authCtx);

		for (AuthVerifierConfiguration verifierConfiguration : matchingVerifiers) {
			AuthVerifier authVerifier = verifierConfiguration.getAuthVerifier();
			Properties authConfig = verifierConfiguration.getConfiguration();

			VerificationResult result = authVerifier.verify(authCtx, authConfig);

			if (result == null) {
				_log.error("Verifier didn't return any result! [AuthVerifier]:"
					+ " [" + authVerifier.getClass().getName() + "]");

				continue;
			}

			// continue only if verification state is N/A
			if (result.getState() !=
				VerificationResult.State.NOT_APPLICABLE) {

				Map<String, Object> mergedSettings = mergeSettings(authConfig,
					result.getAuthenticationSettings());

				result.setAuthenticationSettings(mergedSettings);

				return result;
			}
		}

		// fallback to guest
		return createGuestVerificationResult(authCtx);
	}

	protected Map<String, Object> mergeSettings(
		Properties verifierConfiguration,
		Map<String, Object> verifierResultSettings) {

		Map<String, Object> result = new HashMap<String, Object>();

		if(verifierConfiguration != null){
			for(String key : verifierConfiguration.stringPropertyNames()){
				result.put(key, verifierConfiguration.getProperty(key));
			}
		}

		result.putAll(verifierResultSettings);

		return result;
	}

	public VerificationResult createGuestVerificationResult(
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

	public List<AuthVerifierConfiguration> getMatchingVerifiers(
		AuthenticationContext authenticationContext){

		List<AuthVerifierConfiguration> result =
			new ArrayList<AuthVerifierConfiguration>();

		String requestURI = authenticationContext.getHttpServletRequest()
			.getRequestURI();

		for(AuthVerifierConfiguration verifierConfig : getVerifiersPipeline()){
			if(canApply(verifierConfig, requestURI)){
				result.add(verifierConfig);
			}
		}

		return result;
	}

	protected List<AuthVerifierConfiguration> getVerifiersPipeline() {
		if(_verifiersPipeline != null){
			return _verifiersPipeline;
		}

		List<AuthVerifierConfiguration> result = new ArrayList<AuthVerifierConfiguration>();

		String[] verifiersPipeline = PropsUtil.getArray(
			"portal.authentication.verifier.pipeline");

		for (String authVerifierClass : verifiersPipeline) {
			AuthVerifierConfiguration authVerifierConfiguration =
				new AuthVerifierConfiguration();

			AuthVerifier authVerifier =
				(AuthVerifier) InstancePool.get(authVerifierClass);

			String verifierConfigPrefix = "portal.authentication.verifier." +
				authVerifier.getClass().getSimpleName() + ".";
			Properties verifierConfiguration = PropsUtil.getProperties(
				verifierConfigPrefix, true);

			authVerifierConfiguration.setAuthVerifier(authVerifier);
			authVerifierConfiguration.setConfiguration(verifierConfiguration);
			result.add(authVerifierConfiguration);
		}

		_verifiersPipeline = result;

		return result;
	}

	protected boolean canApply(AuthVerifierConfiguration verifierConfig,
							String requestURI){

		String[] urls = StringUtil.split(verifierConfig.getConfiguration()
			.getProperty("urls"));

		if(urls.length == 0){
			_log.error("Verifier is missing its url configuration! " +
				"[AuthVerifier]: [" + verifierConfig.getAuthVerifier()
				.getClass().getName() + "]");

			return false;
		}

		return Wildcard.matchOne(requestURI, urls) > -1;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalAuthenticationVerifierImpl.class);

	private static List<AuthVerifierConfiguration> _verifiersPipeline;

}
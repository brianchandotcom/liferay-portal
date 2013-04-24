/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class AuthPipeline {

	public static int authenticateByEmailAddress(
			String key, long companyId, String emailAddress, String password,
			Map<String, String[]> headerMap, Map<String, String[]> parameterMap)
		throws AuthException {

		return _instance._authenticate(
			key, companyId, emailAddress, password,
			CompanyConstants.AUTH_TYPE_EA, headerMap, parameterMap);
	}

	public static int authenticateByScreenName(
			String key, long companyId, String screenName, String password,
			Map<String, String[]> headerMap, Map<String, String[]> parameterMap)
		throws AuthException {

		return _instance._authenticate(
			key, companyId, screenName, password, CompanyConstants.AUTH_TYPE_SN,
			headerMap, parameterMap);
	}

	public static int authenticateByUserId(
			String key, long companyId, long userId, String password,
			Map<String, String[]> headerMap, Map<String, String[]> parameterMap)
		throws AuthException {

		return _instance._authenticate(
			key, companyId, String.valueOf(userId), password,
			CompanyConstants.AUTH_TYPE_ID, headerMap, parameterMap);
	}

	public static void onFailureByEmailAddress(
			String key, long companyId, String emailAddress,
			Map<String, String[]> headerMap, Map<String, String[]> parameterMap)
		throws AuthException {

		_instance._onFailure(
			key, companyId, emailAddress, CompanyConstants.AUTH_TYPE_EA,
			headerMap, parameterMap);
	}

	public static void onFailureByScreenName(
			String key, long companyId, String screenName,
			Map<String, String[]> headerMap, Map<String, String[]> parameterMap)
		throws AuthException {

		_instance._onFailure(
			key, companyId, screenName, CompanyConstants.AUTH_TYPE_SN,
			headerMap, parameterMap);
	}

	public static void onFailureByUserId(
			String key, long companyId, long userId,
			Map<String, String[]> headerMap, Map<String, String[]> parameterMap)
		throws AuthException {

		_instance._onFailure(
			key, companyId, String.valueOf(userId),
			CompanyConstants.AUTH_TYPE_ID, headerMap, parameterMap);
	}

	public static void onMaxFailuresByEmailAddress(
			String key, long companyId, String emailAddress,
			Map<String, String[]> headerMap, Map<String, String[]> parameterMap)
		throws AuthException {

		onFailureByEmailAddress(
			key, companyId, emailAddress, headerMap, parameterMap);
	}

	public static void onMaxFailuresByScreenName(
			String key, long companyId, String screenName,
			Map<String, String[]> headerMap, Map<String, String[]> parameterMap)
		throws AuthException {

		onFailureByScreenName(
			key, companyId, screenName, headerMap, parameterMap);
	}

	public static void onMaxFailuresByUserId(
			String key, long companyId, long userId,
			Map<String, String[]> headerMap, Map<String, String[]> parameterMap)
		throws AuthException {

		onFailureByUserId(key, companyId, userId, headerMap, parameterMap);
	}

	public static void registerAuthenticator(
		String key, Authenticator authenticator) {

		_instance._registerAuthenticator(key, authenticator);
	}

	public static void registerAuthFailure(
		String key, AuthFailure authFailure) {

		_instance._registerAuthFailure(key, authFailure);
	}

	public static void unregisterAuthenticator(
		String key, Authenticator authenticator) {

		_instance._unregisterAuthenticator(key, authenticator);
	}

	public static void unregisterAuthFailure(
		String key, AuthFailure authFailure) {

		_instance._unregisterAuthFailure(key, authFailure);
	}

	private AuthPipeline() {

		// auth.pipeline.pre

		List<Authenticator> authenticators = new ArrayList<Authenticator>();

		for (String authenticatorClassName : PropsValues.AUTH_PIPELINE_PRE) {
			Authenticator authenticator = (Authenticator)InstancePool.get(
				authenticatorClassName);

			authenticators.add(authenticator);
		}

		_authenticators.put(
			PropsKeys.AUTH_PIPELINE_PRE,
			authenticators.toArray(new Authenticator[authenticators.size()]));

		// auth.pipeline.post

		authenticators.clear();

		for (String authenticatorClassName : PropsValues.AUTH_PIPELINE_POST) {
			Authenticator authenticator = (Authenticator)InstancePool.get(
				authenticatorClassName);

			authenticators.add(authenticator);
		}

		_authenticators.put(
			PropsKeys.AUTH_PIPELINE_POST,
			authenticators.toArray(new Authenticator[authenticators.size()]));

		// auth.failure

		List<AuthFailure> authFailures = new ArrayList<AuthFailure>();

		for (String authFailureClassName : PropsValues.AUTH_FAILURE) {
			AuthFailure authFailure = (AuthFailure)InstancePool.get(
				authFailureClassName);

			authFailures.add(authFailure);
		}

		_authFailures.put(
			PropsKeys.AUTH_FAILURE,
			authFailures.toArray(new AuthFailure[authFailures.size()]));

		// auth.max.failures

		authFailures.clear();

		for (String authFailureClassName : PropsValues.AUTH_MAX_FAILURES) {
			AuthFailure authFailure = (AuthFailure)InstancePool.get(
				authFailureClassName);

			authFailures.add(authFailure);
		}

		_authFailures.put(
			PropsKeys.AUTH_MAX_FAILURES,
			authFailures.toArray(new AuthFailure[authFailures.size()]));
	}

	private int _authenticate(
			String key, long companyId, String login, String password,
			String authType, Map<String, String[]> headerMap,
			Map<String, String[]> parameterMap)
		throws AuthException {

		boolean skipLiferayCheck = false;

		Authenticator[] authenticators = _authenticators.get(key);

		if ((authenticators == null) || (authenticators.length == 0)) {
			return Authenticator.SUCCESS;
		}

		for (Authenticator authenticator : authenticators) {
			try {
				int authResult = Authenticator.FAILURE;

				if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
					authResult = authenticator.authenticateByEmailAddress(
						companyId, login, password, headerMap, parameterMap);
				}
				else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
					authResult = authenticator.authenticateByScreenName(
						companyId, login, password, headerMap, parameterMap);
				}
				else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
					long userId = GetterUtil.getLong(login);

					authResult = authenticator.authenticateByUserId(
						companyId, userId, password, headerMap, parameterMap);
				}

				if (authResult == Authenticator.SKIP_LIFERAY_CHECK) {
					skipLiferayCheck = true;
				}
				else if (authResult != Authenticator.SUCCESS) {
					return authResult;
				}
			}
			catch (AuthException ae) {
				throw ae;
			}
			catch (Exception e) {
				throw new AuthException(e);
			}
		}

		if (skipLiferayCheck) {
			return Authenticator.SKIP_LIFERAY_CHECK;
		}

		return Authenticator.SUCCESS;
	}

	private void _onFailure(
			String key, long companyId, String login, String authType,
			Map<String, String[]> headerMap, Map<String, String[]> parameterMap)
		throws AuthException {

		AuthFailure[] authFailures = _authFailures.get(key);

		if ((authFailures == null) || (authFailures.length == 0)) {
			return;
		}

		for (AuthFailure authFailure : authFailures) {
			try {
				if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
					authFailure.onFailureByEmailAddress(
						companyId, login, headerMap, parameterMap);
				}
				else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
					authFailure.onFailureByScreenName(
						companyId, login, headerMap, parameterMap);
				}
				else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
					long userId = GetterUtil.getLong(login);

					authFailure.onFailureByUserId(
						companyId, userId, headerMap, parameterMap);
				}
			}
			catch (AuthException ae) {
				throw ae;
			}
			catch (Exception e) {
				throw new AuthException(e);
			}
		}
	}

	private void _registerAuthenticator(
		String key, Authenticator authenticator) {

		List<Authenticator> authenticators = ListUtil.fromArray(
			_authenticators.get(key));

		authenticators.add(authenticator);

		_authenticators.put(
			key,
			authenticators.toArray(new Authenticator[authenticators.size()]));
	}

	private void _registerAuthFailure(String key, AuthFailure authFailure) {
		List<AuthFailure> authFailures = ListUtil.fromArray(
			_authFailures.get(key));

		authFailures.add(authFailure);

		_authFailures.put(
			key, authFailures.toArray(new AuthFailure[authFailures.size()]));
	}

	private void _unregisterAuthenticator(
		String key, Authenticator authenticator) {

		List<Authenticator> authenticators = ListUtil.fromArray(
			_authenticators.get(key));

		if (authenticators.remove(authenticator)) {
			_authenticators.put(
				key,
				authenticators.toArray(
					new Authenticator[authenticators.size()]));
		}
	}

	private void _unregisterAuthFailure(String key, AuthFailure authFailure) {
		List<AuthFailure> authFailures = ListUtil.fromArray(
			_authFailures.get(key));

		if (authFailures.remove(authFailure)) {
			_authFailures.put(
				key,
				authFailures.toArray(new AuthFailure[authFailures.size()]));
		}
	}

	private static AuthPipeline _instance = new AuthPipeline();

	private Map<String, Authenticator[]> _authenticators =
		new HashMap<String, Authenticator[]>();
	private Map<String, AuthFailure[]> _authFailures =
		new HashMap<String, AuthFailure[]>();

}
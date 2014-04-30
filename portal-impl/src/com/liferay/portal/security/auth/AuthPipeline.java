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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.util.PropsValues;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.util.StringPlus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

	private static <T> Map<T, ServiceRegistration<T>> getMap() {
		return new ConcurrentHashMap<T, ServiceRegistration<T>>();
	}

	private AuthPipeline() {

		// auth.pipeline.pre

		_authenticators.put(PropsKeys.AUTH_PIPELINE_PRE, new Authenticator[0]);

		// auth.pipeline.post

		_authenticators.put(PropsKeys.AUTH_PIPELINE_POST, new Authenticator[0]);

		// auth.failure

		_authFailures.put(PropsKeys.AUTH_FAILURE, new AuthFailure[0]);

		// auth.max.failures

		_authFailures.put(PropsKeys.AUTH_MAX_FAILURES, new AuthFailure[0]);

		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(&(objectClass=" + Authenticator.class.getName() + ")(key=*))");

		_authenticatorServiceTracker = registry.trackServices(
			filter, new AuthenticatorServiceTrackerCustomizer());

		_authenticatorServiceTracker.open();

		filter = registry.getFilter(
			"(&(objectClass=" + AuthFailure.class.getName() + ")(key=*))");

		_authFailureServiceTracker = registry.trackServices(
			filter, new AuthFailureServiceTrackerCustomizer());

		_authFailureServiceTracker.open();
	}

	private int _authenticate(
			String key, long companyId, String login, String password,
			String authType, Map<String, String[]> headerMap,
			Map<String, String[]> parameterMap)
		throws AuthException {

		boolean skipLiferayCheck = false;

		Authenticator[] authenticators = _authenticators.get(key);

		if (ArrayUtil.isEmpty(authenticators)) {
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

		if (ArrayUtil.isEmpty(authFailures)) {
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

		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("key", key);

		ServiceRegistration<Authenticator> serviceRegistration =
			registry.registerService(
				Authenticator.class, authenticator, properties);

		_authenticatorServiceRegistrations.put(
			authenticator, serviceRegistration);
	}

	private void _registerAuthFailure(String key, AuthFailure authFailure) {
		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("key", key);

		ServiceRegistration<AuthFailure> serviceRegistration =
			registry.registerService(
				AuthFailure.class, authFailure, properties);

		_authFailureServiceRegistrations.put(authFailure, serviceRegistration);
	}

	private void _unregisterAuthenticator(
		String key, Authenticator authenticator) {

		ServiceRegistration<Authenticator> serviceRegistration =
			_authenticatorServiceRegistrations.remove(authenticator);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private void _unregisterAuthFailure(String key, AuthFailure authFailure) {
		ServiceRegistration<AuthFailure> serviceRegistration =
			_authFailureServiceRegistrations.remove(authFailure);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private static AuthPipeline _instance = new AuthPipeline();

	private Map<String, Authenticator[]> _authenticators =
		new HashMap<String, Authenticator[]>();
	private Map<String, AuthFailure[]> _authFailures =
		new HashMap<String, AuthFailure[]>();
	private Map<Authenticator, ServiceRegistration<Authenticator>>
		_authenticatorServiceRegistrations = getMap();
	private ServiceTracker<Authenticator, Authenticator>
		_authenticatorServiceTracker;
	private Map<AuthFailure, ServiceRegistration<AuthFailure>>
		_authFailureServiceRegistrations = getMap();
	private ServiceTracker<AuthFailure, AuthFailure> _authFailureServiceTracker;

	static {
		for (String authenticatorClassName : PropsValues.AUTH_PIPELINE_PRE) {
			Authenticator authenticator = (Authenticator)InstancePool.get(
				authenticatorClassName);

			_instance._registerAuthenticator(
				PropsKeys.AUTH_PIPELINE_PRE, authenticator);
		}

		for (String authenticatorClassName : PropsValues.AUTH_PIPELINE_POST) {
			Authenticator authenticator = (Authenticator)InstancePool.get(
				authenticatorClassName);

			_instance._registerAuthenticator(
				PropsKeys.AUTH_PIPELINE_POST, authenticator);
		}

		for (String authFailureClassName : PropsValues.AUTH_FAILURE) {
			AuthFailure authFailure = (AuthFailure)InstancePool.get(
				authFailureClassName);

			_instance._registerAuthFailure(PropsKeys.AUTH_FAILURE, authFailure);
		}

		for (String authFailureClassName : PropsValues.AUTH_MAX_FAILURES) {
			AuthFailure authFailure = (AuthFailure)InstancePool.get(
				authFailureClassName);

			_instance._registerAuthFailure(
				PropsKeys.AUTH_MAX_FAILURES, authFailure);
		}
	}

	private class AuthenticatorServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<Authenticator, Authenticator> {

		@Override
		public Authenticator addingService(
			ServiceReference<Authenticator> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			Authenticator authenticator = registry.getService(serviceReference);

			List<String> keys = StringPlus.asList(
				serviceReference.getProperty("key"));

			boolean added = false;

			for (String key : keys) {
				List<Authenticator> authenticators = Arrays.asList(
					_authenticators.get(key));

				if (authenticators == null) {

					// Unknown key? Do not register.

					continue;
				}

				added = true;

				authenticators.add(authenticator);

				_authenticators.put(
					key,
					authenticators.toArray(
						new Authenticator[authenticators.size()]));
			}

			if (!added) {

				// No registrations? Do not track.

				return null;
			}

			return authenticator;
		}

		@Override
		public void modifiedService(
			ServiceReference<Authenticator> serviceReference,
			Authenticator authenticator) {
		}

		@Override
		public void removedService(
			ServiceReference<Authenticator> serviceReference,
			Authenticator authenticator) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			List<String> keys = StringPlus.asList(
				serviceReference.getProperty("key"));

			for (String key : keys) {
				List<Authenticator> authenticators = Arrays.asList(
					_authenticators.get(key));

				if (authenticators == null) {
					continue;
				}

				if (authenticators.remove(authenticator)) {
					_authenticators.put(
						key,
						authenticators.toArray(
							new Authenticator[authenticators.size()]));
				}
			}
		}

	}

	private class AuthFailureServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<AuthFailure, AuthFailure> {

		@Override
		public AuthFailure addingService(
			ServiceReference<AuthFailure> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			AuthFailure authFailure = registry.getService(serviceReference);

			List<String> keys = StringPlus.asList(
				serviceReference.getProperty("key"));

			boolean added = false;

			for (String key : keys) {
				List<AuthFailure> authFailures = Arrays.asList(
					_authFailures.get(key));

				if (authFailures == null) {

					// Unknown key? Do not register.

					continue;
				}

				added = true;

				authFailures.add(authFailure);

				_authFailures.put(
					key,
					authFailures.toArray(new AuthFailure[authFailures.size()]));
			}

			if (!added) {

				// No registrations? Do not track.

				return null;
			}

			return authFailure;
		}

		@Override
		public void
			modifiedService(
				ServiceReference<AuthFailure> serviceReference,
				AuthFailure authFailure) {
		}

		@Override
		public void
			removedService(
				ServiceReference<AuthFailure> serviceReference,
				AuthFailure authFailure) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			List<String> keys = StringPlus.asList(
				serviceReference.getProperty("key"));

			for (String key : keys) {
				List<AuthFailure> authFailures = Arrays.asList(
					_authFailures.get(key));

				if (authFailures == null) {
					continue;
				}

				if (authFailures.remove(authFailure)) {
					_authFailures.put(
						key,
						authFailures.toArray(
							new AuthFailure[authFailures.size()]));
				}
			}
		}

	}

}
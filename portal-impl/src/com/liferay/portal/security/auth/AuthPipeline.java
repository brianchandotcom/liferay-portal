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
import com.liferay.portal.kernel.util.ListUtil;
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
import com.liferay.registry.collections.ServiceRegistrationMap;
import com.liferay.registry.util.StringPlus;

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

		_instance._unregisterAuthenticator(authenticator);
	}

	public static void unregisterAuthFailure(
		String key, AuthFailure authFailure) {

		_instance._unregisterAuthFailure(authFailure);
	}

	private AuthPipeline() {
		Registry registry = RegistryUtil.getRegistry();

		Filter authFailureFilter = registry.getFilter(
			"(&(key=*)(objectClass=" + AuthFailure.class.getName() + "))");

		_authFailureServiceTracker = registry.trackServices(
			authFailureFilter, new AuthFailureServiceTrackerCustomizer());

		_authFailureServiceTracker.open();

		_authFailures.put(PropsKeys.AUTH_FAILURE, new AuthFailure[0]);

		for (String authFailureClassName : PropsValues.AUTH_FAILURE) {
			AuthFailure authFailure = (AuthFailure)InstancePool.get(
				authFailureClassName);

			_registerAuthFailure(PropsKeys.AUTH_FAILURE, authFailure);
		}

		_authFailures.put(PropsKeys.AUTH_MAX_FAILURES, new AuthFailure[0]);

		for (String authFailureClassName : PropsValues.AUTH_MAX_FAILURES) {
			AuthFailure authFailure = (AuthFailure)InstancePool.get(
				authFailureClassName);

			_registerAuthFailure(PropsKeys.AUTH_MAX_FAILURES, authFailure);
		}

		Filter authenticatorFilter = registry.getFilter(
			"(&(key=*)(objectClass=" + Authenticator.class.getName() + "))");

		_authenticatorServiceTracker = registry.trackServices(
			authenticatorFilter, new AuthenticatorServiceTrackerCustomizer());

		_authenticatorServiceTracker.open();

		_authenticators.put(PropsKeys.AUTH_PIPELINE_POST, new Authenticator[0]);

		for (String authenticatorClassName : PropsValues.AUTH_PIPELINE_POST) {
			Authenticator authenticator = (Authenticator)InstancePool.get(
				authenticatorClassName);

			_registerAuthenticator(PropsKeys.AUTH_PIPELINE_POST, authenticator);
		}

		_authenticators.put(PropsKeys.AUTH_PIPELINE_PRE, new Authenticator[0]);

		for (String authenticatorClassName : PropsValues.AUTH_PIPELINE_PRE) {
			Authenticator authenticator = (Authenticator)InstancePool.get(
				authenticatorClassName);

			_registerAuthenticator(PropsKeys.AUTH_PIPELINE_PRE, authenticator);
		}
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

	private void _unregisterAuthenticator(Authenticator authenticator) {
		ServiceRegistration<Authenticator> serviceRegistration =
			_authenticatorServiceRegistrations.remove(authenticator);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private void _unregisterAuthFailure(AuthFailure authFailure) {
		ServiceRegistration<AuthFailure> serviceRegistration =
			_authFailureServiceRegistrations.remove(authFailure);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private static final AuthPipeline _instance = new AuthPipeline();

	private final Map<String, Authenticator[]> _authenticators =
		new HashMap<String, Authenticator[]>();
	private final Map<Authenticator, ServiceRegistration<Authenticator>>
		_authenticatorServiceRegistrations =
			new ServiceRegistrationMap<Authenticator>();
	private final ServiceTracker<Authenticator, Authenticator>
		_authenticatorServiceTracker;
	private final Map<String, AuthFailure[]> _authFailures =
		new HashMap<String, AuthFailure[]>();
	private final Map<AuthFailure, ServiceRegistration<AuthFailure>>
		_authFailureServiceRegistrations =
			new ServiceRegistrationMap<AuthFailure>();
	private final ServiceTracker<AuthFailure, AuthFailure>
		_authFailureServiceTracker;

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
				Authenticator[] authenticators = _authenticators.get(key);

				if (authenticators == null) {
					continue;
				}

				added = true;

				authenticators = ArrayUtil.append(
					authenticators, authenticator);

				_authenticators.put(key, authenticators);
			}

			if (!added) {
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
				Authenticator[] authenticators = _authenticators.get(key);

				if (authenticators == null) {
					continue;
				}

				List<Authenticator> authenticatorsList = ListUtil.toList(
					authenticators);

				if (authenticatorsList.remove(authenticator)) {
					_authenticators.put(
						key,
						authenticatorsList.toArray(
							new Authenticator[authenticatorsList.size()]));
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
				AuthFailure[] authFailures = _authFailures.get(key);

				if (authFailures == null) {
					continue;
				}

				added = true;

				authFailures = ArrayUtil.append(authFailures, authFailure);

				_authFailures.put(key, authFailures);
			}

			if (!added) {
				return null;
			}

			return authFailure;
		}

		@Override
		public void modifiedService(
			ServiceReference<AuthFailure> serviceReference,
			AuthFailure authFailure) {
		}

		@Override
		public void removedService(
			ServiceReference<AuthFailure> serviceReference,
			AuthFailure authFailure) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			List<String> keys = StringPlus.asList(
				serviceReference.getProperty("key"));

			for (String key : keys) {
				AuthFailure[] authFailures = _authFailures.get(key);

				if (authFailures == null) {
					continue;
				}

				List<AuthFailure> authFailuresList = ListUtil.fromArray(
					authFailures);

				if (authFailuresList.remove(authFailure)) {
					_authFailures.put(
						key,
						authFailuresList.toArray(
							new AuthFailure[authFailuresList.size()]));
				}
			}
		}

	}

}
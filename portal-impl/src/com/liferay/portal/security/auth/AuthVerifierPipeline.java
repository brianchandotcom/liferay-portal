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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;

import jodd.util.Wildcard;

/**
 * @author Tomas Polesovsky
 */
public class AuthVerifierPipeline {

	public static final String AUTH_TYPE = "auth.type";

	public static String getAuthVerifierPropertyName(String className) {
		String simpleClassName = StringUtil.extractLast(
			className, StringPool.PERIOD);

		return PropsKeys.AUTH_VERIFIER.concat(simpleClassName).concat(
			StringPool.PERIOD);
	}

	public static void register(
		AuthVerifierConfiguration authVerifierConfiguration) {

		_instance._register(authVerifierConfiguration);
	}

	public static void unregister(
		AuthVerifierConfiguration authVerifierConfiguration) {

		_instance._unregister(authVerifierConfiguration);
	}

	public static AuthVerifierResult verifyRequest(
			AccessControlContext accessControlContext)
		throws PortalException, SystemException {

		return _instance._verifyRequest(accessControlContext);
	}

	private AuthVerifierPipeline() {
		_initAuthVerifierConfigurations();
	}

	private AuthVerifierResult _createGuestVerificationResult(
			AccessControlContext accessControlContext)
		throws PortalException, SystemException {

		AuthVerifierResult authVerifierResult = new AuthVerifierResult();

		authVerifierResult.setState(AuthVerifierResult.State.SUCCESS);

		HttpServletRequest request = accessControlContext.getRequest();

		long companyId = PortalUtil.getCompanyId(request);

		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(companyId);

		authVerifierResult.setUserId(defaultUserId);

		return authVerifierResult;
	}

	private List<AuthVerifierConfiguration> _getAuthVerifierConfigurations(
		AccessControlContext accessControlContext) {

		HttpServletRequest request = accessControlContext.getRequest();

		List<AuthVerifierConfiguration> authVerifierConfigurations =
			new ArrayList<AuthVerifierConfiguration>();

		String requestURI = request.getRequestURI();

		String contextPath = request.getContextPath();

		requestURI = requestURI.substring(contextPath.length());

		for (AuthVerifierConfiguration authVerifierConfiguration :
				_authVerifierConfigurations) {

			authVerifierConfiguration = _mergeAuthVerifierConfiguration(
				authVerifierConfiguration, accessControlContext);

			if (_isMatchingRequestURI(authVerifierConfiguration, requestURI)) {
				authVerifierConfigurations.add(authVerifierConfiguration);
			}
		}

		return authVerifierConfigurations;
	}

	private void _initAuthVerifierConfigurations() {
		_authVerifierConfigurations =
			new CopyOnWriteArrayList<AuthVerifierConfiguration>();

		for (String authVerifierClassName :
				PropsValues.AUTH_VERIFIER_PIPELINE) {

			try {
				AuthVerifierConfiguration authVerifierConfiguration =
					new AuthVerifierConfiguration();

				AuthVerifier authVerifier =
					(AuthVerifier)InstanceFactory.newInstance(
						ClassLoaderUtil.getPortalClassLoader(),
						authVerifierClassName);

				authVerifierConfiguration.setAuthVerifier(authVerifier);

				authVerifierConfiguration.setAuthVerifierClassName(
					authVerifierClassName);

				Properties properties = PropsUtil.getProperties(
					getAuthVerifierPropertyName(authVerifierClassName), true);

				authVerifierConfiguration.setProperties(properties);

				_authVerifierConfigurations.add(authVerifierConfiguration);
			}
			catch (Exception e) {
				_log.error("Unable to initialize " + authVerifierClassName, e);
			}
		}
	}

	private boolean _isMatchingRequestURI(
		AuthVerifierConfiguration authVerifierConfiguration,
		String requestURI) {

		AuthVerifier authVerifier = authVerifierConfiguration.getAuthVerifier();

		Properties properties = authVerifierConfiguration.getProperties();

		String[] urlsExcludes = StringUtil.split(
			properties.getProperty("urls.excludes"));

		if ((urlsExcludes.length > 0) &&
			(Wildcard.matchOne(requestURI, urlsExcludes) > -1)) {

			return false;
		}

		String[] urlsIncludes = StringUtil.split(
			properties.getProperty("urls.includes"));

		if (urlsIncludes.length == 0) {
			Class<?> authVerifierClass = authVerifier.getClass();

			_log.error(
				"Auth verifier " + authVerifierClass.getName() +
					" does not have any URLs configured");

			return false;
		}

		return Wildcard.matchOne(requestURI, urlsIncludes) > -1;
	}

	private AuthVerifierConfiguration _mergeAuthVerifierConfiguration(
		AuthVerifierConfiguration authVerifierConfiguration,
		AccessControlContext accessControlContext) {

		Map<String, Object> settings = accessControlContext.getSettings();

		String authVerifierSettingsKey = getAuthVerifierPropertyName(
			authVerifierConfiguration.getAuthVerifierClassName());

		boolean merge = false;

		Set<String> settingsKeys = settings.keySet();

		Iterator<String> iterator = settingsKeys.iterator();

		while (iterator.hasNext() && !merge) {
			String settingsKey = iterator.next();

			if (settingsKey.startsWith(authVerifierSettingsKey)) {
				if (settings.get(settingsKey) instanceof String) {
					merge = true;
				}
			}
		}

		if (!merge) {
			return authVerifierConfiguration;
		}

		AuthVerifierConfiguration mergedAuthVerifierConfiguration =
			new AuthVerifierConfiguration();

		mergedAuthVerifierConfiguration.setAuthVerifier(
			authVerifierConfiguration.getAuthVerifier());

		Properties mergedProperties = new Properties(
			authVerifierConfiguration.getProperties());

		for (String settingsKey : settings.keySet()) {
			if (settingsKey.startsWith(authVerifierSettingsKey)) {
				Object settingsValue = settings.get(settingsKey);

				if (settingsValue instanceof String) {
					String propertiesKey = settingsKey.substring(
						authVerifierSettingsKey.length());

					mergedProperties.setProperty(
						propertiesKey, (String)settingsValue);
				}
			}
		}

		mergedAuthVerifierConfiguration.setProperties(mergedProperties);

		return mergedAuthVerifierConfiguration;
	}

	private Map<String, Object> _mergeSettings(
		Properties properties, Map<String, Object> settings) {

		Map<String, Object> mergedSettings = new HashMap<String, Object>(
			settings);

		if (properties != null) {
			for (Map.Entry<Object, Object> entry : properties.entrySet()) {
				mergedSettings.put((String)entry.getKey(), entry.getValue());
			}
		}

		return mergedSettings;
	}

	private void _register(
		AuthVerifierConfiguration authVerifierConfiguration) {

		if (authVerifierConfiguration == null) {
			throw new IllegalArgumentException(
				"Auth verifier configuration is null");
		}

		if (authVerifierConfiguration.getAuthVerifier() == null) {
			throw new IllegalArgumentException("Auth verifier is null");
		}

		if (authVerifierConfiguration.getAuthVerifierClassName() == null) {
			throw new IllegalArgumentException("Class name is null");
		}

		if (authVerifierConfiguration.getProperties() == null) {
			throw new IllegalArgumentException("Properties is null");
		}

		_authVerifierConfigurations.add(0, authVerifierConfiguration);
	}

	private void _unregister(
		AuthVerifierConfiguration authVerifierConfiguration) {

		if (authVerifierConfiguration == null) {
			throw new IllegalArgumentException(
				"Auth verifier configuration is null");
		}

		_authVerifierConfigurations.remove(authVerifierConfiguration);
	}

	private AuthVerifierResult _verifyRequest(
			AccessControlContext accessControlContext)
		throws PortalException, SystemException {

		if (accessControlContext == null) {
			throw new IllegalArgumentException(
				"Access control context is null");
		}

		List<AuthVerifierConfiguration> authVerifierConfigurations =
			_getAuthVerifierConfigurations(accessControlContext);

		for (AuthVerifierConfiguration authVerifierConfiguration :
				authVerifierConfigurations) {

			AuthVerifierResult authVerifierResult = null;

			AuthVerifier authVerifier =
				authVerifierConfiguration.getAuthVerifier();

			Properties properties = authVerifierConfiguration.getProperties();

			try {
				authVerifierResult = authVerifier.verify(
					accessControlContext, properties);
			}
			catch (Exception e) {
				Class<?> authVerifierClass = authVerifier.getClass();

				_log.error("Skipping " + authVerifierClass.getName(), e);

				continue;
			}

			if (authVerifierResult == null) {
				Class<?> authVerifierClass = authVerifier.getClass();

				_log.error(
					"Auth verifier " + authVerifierClass.getName() +
						" did not return an auth verifier result");

				continue;
			}

			if (authVerifierResult.getState() !=
					AuthVerifierResult.State.NOT_APPLICABLE) {

				Map<String, Object> settings = _mergeSettings(
					properties, authVerifierResult.getSettings());

				settings.put(AUTH_TYPE, authVerifier.getAuthType());

				authVerifierResult.setSettings(settings);

				return authVerifierResult;
			}
		}

		return _createGuestVerificationResult(accessControlContext);
	}

	private static Log _log = LogFactoryUtil.getLog(AuthVerifierPipeline.class);

	private static AuthVerifierPipeline _instance = new AuthVerifierPipeline();

	private List<AuthVerifierConfiguration> _authVerifierConfigurations;

}
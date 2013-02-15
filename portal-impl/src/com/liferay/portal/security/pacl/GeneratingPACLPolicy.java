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

package com.liferay.portal.security.pacl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.lang.PortalSecurityManagerThreadLocal;
import com.liferay.portal.security.pacl.checker.AuthorizationProperty;
import com.liferay.portal.security.pacl.checker.Checker;
import com.liferay.portal.security.pacl.checker.JNDIChecker;
import com.liferay.portal.security.pacl.checker.PortalServiceChecker;
import com.liferay.portal.security.pacl.checker.SQLChecker;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;

import java.lang.reflect.Method;

import java.security.Permission;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Raymond Augé
 */
public class GeneratingPACLPolicy extends ActivePACLPolicy {

	public GeneratingPACLPolicy(
		String servletContextName, ClassLoader classLoader,
		Properties properties) {

		super(servletContextName, classLoader, properties);
	}

	@Override
	public void checkPermission(Permission permission) {
		Checker checker = getChecker(permission.getClass());

		try {
			checker.checkPermission(permission);
		}
		catch (SecurityException se) {
			try {
				AuthorizationProperty authorizationProperty =
					checker.generateAuthorizationProperty(permission);

				trackGeneratedAuthorizationProperty(authorizationProperty);
			}
			catch (Exception e) {
				throw se;
			}
		}
	}

	@Override
	public boolean hasJNDI(String name) {
		JNDIChecker jndiChecker = getJndiChecker();

		if (!jndiChecker.hasJNDI(name)) {
			AuthorizationProperty authorizationProperty =
				jndiChecker.generateAuthorizationProperty(name);

			trackGeneratedAuthorizationProperty(authorizationProperty);
		}

		return true;
	}

	@Override
	public boolean hasPortalService(
		Object object, Method method, Object[] arguments) {

		PortalServiceChecker portalServiceChecker = getPortalServiceChecker();

		if (!portalServiceChecker.hasService(object, method, arguments)) {
			AuthorizationProperty authorizationProperty =
				portalServiceChecker.generateAuthorizationProperty(
					object, method, arguments);

			trackGeneratedAuthorizationProperty(authorizationProperty);
		}

		return true;
	}

	@Override
	public boolean hasSQL(String sql) {
		SQLChecker sqlChecker = getSqlChecker();

		if (!sqlChecker.hasSQL(sql)) {
			AuthorizationProperty authorizationProperty =
				sqlChecker.generateAuthorizationProperty(sql);

			trackGeneratedAuthorizationProperty(authorizationProperty);
		}

		return true;
	}

	private String generateProperties() {
		StringBundler sb = new StringBundler();

		for (Map.Entry<String, Set<String>> entry : _trackedProperties.entrySet()) {
			String key = entry.getKey();
			Set<String> valueSet = entry.getValue();

			sb.append(key);
			sb.append(StringPool.EQUAL);

			Set<String> sortedSet = new TreeSet<String>(valueSet);

			for (String value : sortedSet) {
				sb.append(StringPool.BACK_SLASH);
				sb.append(StringPool.NEW_LINE);
				sb.append(_INDENT);
				sb.append(value);
				sb.append(StringPool.COMMA);
			}

			sb.setIndex(sb.index() - 1);

			sb.append(StringPool.NEW_LINE.concat(StringPool.NEW_LINE));
		}

		if (sb.length() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	private void mergeUntrackedProperties() {

		// This is done so that the written policy is the complete picture
		// rather than only a list of the modified properties. The developer
		// therefore need only copy the entire policy.

		Properties properties = getProperties();

		Enumeration<Object> keys = properties.keys();

		while (keys.hasMoreElements()) {
			String key = (String)keys.nextElement();

			if (_trackedProperties.containsKey(key) ||
				!key.startsWith(_SECURITY_MANAGER_PREFIX) ||
				key.equals(_SECURITY_MANAGER_ENABLED) ||
				key.equals(_SECURITY_MANAGER_GENERATOR_DIR)) {

				continue;
			}

			Set<String> propertySet = getPropertySet(key);

			_trackedProperties.put(key, propertySet);
		}
	}

	private void trackGeneratedAuthorizationProperty(
		AuthorizationProperty authorizationProperty) {

		if (authorizationProperty == null) {
			return;
		}

		String key = authorizationProperty.getKey();
		String[] values = StringUtil.split(authorizationProperty.getValue());

		Set<String> existingProperties = getPropertySet(key);
		Set<String> trackedProperties = _trackedProperties.get(key);

		boolean changed = false;

		if (trackedProperties == null) {
			trackedProperties = new HashSet<String>(existingProperties);

			changed = true;
		}

		for (String value : values) {
			if (!trackedProperties.contains(value)) {
				trackedProperties.add(value);

				changed = true;
			}
		}

		if (!changed) {
			return;
		}

		_reentrantLock.lock();

		try {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"[PACL][" + getServletContextName() + "] adding " +
						"authorization property " + authorizationProperty);
			}

			// Only add new properties to the map if there was a change and we
			// have a lock

			_trackedProperties.put(key, trackedProperties);

			mergeUntrackedProperties();

			writePropertiesFile();
		}
		finally {
			_reentrantLock.unlock();
		}
	}

	private void writePropertiesFile() {
		boolean enabled = PortalSecurityManagerThreadLocal.isEnabled();

		try {
			PortalSecurityManagerThreadLocal.setEnabled(false);

			String fileName = getServletContextName().concat(_FILE_EXTENSION);

			String writePath = GetterUtil.getString(
				getProperty(_SECURITY_MANAGER_GENERATOR_DIR));

			if (Validator.isNull(writePath)) {
				writePath = PropsValues.LIFERAY_HOME.concat(
					File.separator).concat(_POLICY_DIR);
			}

			String properties = generateProperties();

			FileUtil.write(writePath, fileName, properties);
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);
		}
		finally {
			PortalSecurityManagerThreadLocal.setEnabled(enabled);
		}
	}

	private static final String _FILE_EXTENSION = ".policy";
	private static final String _INDENT =
		StringPool.THREE_SPACES + StringPool.SPACE;
	private static final String _POLICY_DIR = "pacl-policy";
	private static final String _SECURITY_MANAGER_ENABLED =
		"security-manager-enabled";
	private static final String _SECURITY_MANAGER_GENERATOR_DIR =
		"security-manager-generator-dir";
	private static final String _SECURITY_MANAGER_PREFIX = "security-manager-";

	private static Log _log = LogFactoryUtil.getLog(GeneratingPACLPolicy.class);

	private ConcurrentSkipListMap<String, Set<String>> _trackedProperties =
		new ConcurrentSkipListMap<String, Set<String>>();
	private ReentrantLock _reentrantLock = new ReentrantLock();

}
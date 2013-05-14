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

package com.liferay.portal.security.pacl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaDetector;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.SortedProperties;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.pacl.checker.Checker;
import com.liferay.portal.security.pacl.checker.FileChecker;

import java.io.FilePermission;

import java.net.MalformedURLException;
import java.net.URL;

import java.security.AllPermission;
import java.security.CodeSource;
import java.security.Permission;
import java.security.Permissions;
import java.security.Policy;
import java.security.ProtectionDomain;
import java.security.Provider;
import java.security.Security;
import java.security.URIParameter;
import java.security.cert.Certificate;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BasePACLPolicy implements PACLPolicy {

	public BasePACLPolicy(
		String servletContextName, ClassLoader classLoader,
		Properties properties) {

		_servletContextName = servletContextName;
		_classLoader = classLoader;
		_properties = properties;

		try {
			initCheckers();

			initPolicy(servletContextName, classLoader);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public ClassLoader getClassLoader() {
		return _classLoader;
	}

	@Override
	public Policy getPolicy() {
		return _policy;
	}

	@Override
	public Properties getProperties() {
		return _properties;
	}

	@Override
	public String getProperty(String key) {
		return _properties.getProperty(key);
	}

	@Override
	public String[] getPropertyArray(String key) {
		return StringUtil.split(getProperty(key));
	}

	@Override
	public boolean getPropertyBoolean(String key) {
		return GetterUtil.getBoolean(getProperty(key));
	}

	@Override
	public Set<String> getPropertySet(String key) {
		return new TreeSet<String>(SetUtil.fromArray(getPropertyArray(key)));
	}

	@Override
	public String getServletContextName() {
		return _servletContextName;
	}

	@Override
	public boolean isCheckablePermission(Permission permission) {
		Class<?> clazz = permission.getClass();

		return _checkers.containsKey(clazz.getName());
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{active=");
		sb.append(isActive());
		sb.append(", hashCode=");
		sb.append(hashCode());
		sb.append(", servletContextName=");
		sb.append(_servletContextName);
		sb.append("}");

		return sb.toString();
	}

	protected void checkForAllPermission(Policy policy, String rootDir)
		throws MalformedURLException {

		URL rootURL = new URL("file", null, rootDir);

		ProtectionDomain protectionDomain = new ProtectionDomain(
			new CodeSource(rootURL, new Certificate[0]), new Permissions());

		if (policy.implies(protectionDomain, new AllPermission())) {
			throw new IllegalStateException(
				"The plugin's Java policy tried to declared all " +
					"permissions");
		}
	}

	protected Checker getChecker(Class<? extends Permission> clazz) {
		return _checkers.get(clazz.getName());
	}

	protected Provider getProvider() {
		String providerName = "SUN";

		if (JavaDetector.isIBM() && JavaDetector.isJDK6()) {
			providerName = "Policy";
		}

		return Security.getProvider(providerName);
	}

	protected Checker initChecker(Checker checker) {
		checker.setPACLPolicy(this);

		checker.afterPropertiesSet();

		return checker;
	}

	protected void initCheckers() throws Exception {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		Properties portalProperties = PropsUtil.getProperties(
			"portal.security.manager.pacl.policy.checker", false);

		portalProperties = new SortedProperties(portalProperties);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Registering " + portalProperties.size() +
					" PACL policy checkers");
		}

		for (Map.Entry<Object, Object> entry : portalProperties.entrySet()) {
			String key = (String)entry.getKey();

			int x = key.indexOf("[");
			int y = key.indexOf("]");

			String permissionClassName = key.substring(x + 1, y);

			String checkerClassName = (String)entry.getValue();

			Class<?> checkerClass = classLoader.loadClass(checkerClassName);

			Checker checker = (Checker)checkerClass.newInstance();

			initChecker(checker);

			if (_log.isInfoEnabled()) {
				_log.info(
					"Registering permission " + permissionClassName +
						" with PACL policy " + checkerClassName);
			}

			_checkers.put(permissionClassName, checker);
		}
	}

	protected void initPolicy(
			String servletContextName, ClassLoader classLoader)
		throws Exception {

		ServletContext servletContext = ServletContextPool.get(
			servletContextName);

		if (servletContext == null) {
			return;
		}

		URL url = servletContext.getResource("/WEB-INF/java.policy");

		if (url == null) {
			return;
		}

		FileChecker fileChecker = (FileChecker)_checkers.get(
			FilePermission.class.getName());

		if (fileChecker == null) {
			return;
		}

		// Set a system property to match the servletContextName so that the
		// plugin can use it in it's Java security policy file for setting the
		// code base

		String rootDir = fileChecker.getRootDir();

		System.setProperty(servletContextName, rootDir);

		try {
			URIParameter parameter = new URIParameter(url.toURI());

			Policy policy = Policy.getInstance(
				"JavaPolicy", parameter, getProvider());

			checkForAllPermission(policy, rootDir);

			_policy = policy;
		}
		catch (Exception e) {
			_log.error("Unable to initialize Java policy " + url.toString(), e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(BasePACLPolicy.class);

	private Map<String, Checker> _checkers = new HashMap<String, Checker>();

	private ClassLoader _classLoader;
	private Policy _policy;
	private Properties _properties;
	private String _servletContextName;

}
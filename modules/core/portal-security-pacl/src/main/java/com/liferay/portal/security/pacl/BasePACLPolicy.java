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

package com.liferay.portal.security.pacl;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.url.URLContainer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.SortedProperties;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.pacl.checker.Checker;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BasePACLPolicy implements PACLPolicy {

	public BasePACLPolicy(
		String contextName, URLContainer urlContainer, ClassLoader classLoader,
		Properties properties) {

		_contextName = contextName;
		_urlContainer = urlContainer;
		_classLoader = classLoader;
		_properties = properties;

		try {
			initCheckers();
			initPolicy();
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
	public String getContextName() {
		return _contextName;
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
		return new TreeSet<>(SetUtil.fromArray(getPropertyArray(key)));
	}

	@Override
	public URLContainer getURLContainer() {
		return _urlContainer;
	}

	@Override
	public List<URL> getURLs() {
		return _urls;
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
		sb.append(", contextName=");
		sb.append(_contextName);
		sb.append(", hashCode=");
		sb.append(hashCode());
		sb.append("}");

		return sb.toString();
	}

	protected void checkForAllPermission(Policy policy, URL rootURL)
		throws MalformedURLException {

		CodeSource codeSource = new CodeSource(rootURL, new Certificate[0]);

		ProtectionDomain protectionDomain = new ProtectionDomain(
			codeSource, new Permissions());

		if (policy.implies(protectionDomain, new AllPermission())) {
			throw new IllegalStateException(
				"The plugin's Java policy tried to declared all permissions");
		}
	}

	protected Checker getChecker(Class<? extends Permission> clazz) {
		return _checkers.get(clazz.getName());
	}

	protected Provider getProvider() {
		String providerName = "SUN";

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
					StringBundler.concat(
						"Registering permission ", permissionClassName,
						" with PACL policy ", checkerClassName));
			}

			_checkers.put(permissionClassName, checker);
		}
	}

	protected void initPolicy() throws Exception {
		URL url = _urlContainer.getResource("/WEB-INF/java.policy");

		if (url == null) {
			return;
		}

		// Set a system property to match the servletContextName so that the
		// plugin can use it in it's Java security policy file for setting the
		// code base

		URL rootURL = _urlContainer.getResource(StringPool.SLASH);

		System.setProperty(_contextName, rootURL.getPath());

		try {
			URIParameter parameter = new URIParameter(url.toURI());

			Policy policy = Policy.getInstance(
				"JavaPolicy", parameter, getProvider());

			checkForAllPermission(policy, rootURL);

			_policy = policy;
		}
		catch (Exception e) {
			_log.error("Unable to initialize Java policy " + url.toString(), e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(BasePACLPolicy.class);

	private final Map<String, Checker> _checkers = new HashMap<>();
	private final ClassLoader _classLoader;
	private final String _contextName;
	private Policy _policy;
	private final Properties _properties;
	private final URLContainer _urlContainer;
	private final List<URL> _urls = new ArrayList<>();

}
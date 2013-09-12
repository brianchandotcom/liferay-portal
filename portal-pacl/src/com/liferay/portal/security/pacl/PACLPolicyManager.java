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

import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import java.security.AccessController;
import java.security.CodeSource;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class PACLPolicyManager {

	public static PACLPolicy buildPACLPolicy(
		String servletContextName, ClassLoader classLoader,
		Properties properties) {

		String value = properties.getProperty(
			"security-manager-enabled", "false");

		if (value.equals("generate")) {
			return new GeneratingPACLPolicy(
				servletContextName, classLoader, properties);
		}

		if (GetterUtil.getBoolean(value)) {
			return new ActivePACLPolicy(
				servletContextName, classLoader, properties);
		}
		else {
			return new InactivePACLPolicy(
				servletContextName, classLoader, properties);
		}
	}

	public static PACLPolicy getDefaultPACLPolicy() {
		return _defaultPACLPolicy;
	}

	public static PACLPolicy getPACLPolicy(ClassLoader classLoader) {
		if (classLoader == null) {
			return null;
		}

		return AccessController.doPrivileged(
			new PACLPolicyPrivilegedAction(classLoader));
	}

	public static PACLPolicy getPACLPolicy(URL location) {
		if (location == null) {
			return null;
		}

		return AccessController.doPrivileged(
			new PACLPolicyPrivilegedAction(location));
	}

	public static PACLPolicy getPACLPolicy(ProtectionDomain protectionDomain) {
		if (protectionDomain == null) {
			return null;
		}

		return AccessController.doPrivileged(
			new PACLPolicyPrivilegedAction(protectionDomain));
	}

	public static synchronized void register(
		ClassLoader classLoader, PACLPolicy paclPolicy) {

		List<URL> urLs = paclPolicy.getURLs();

		if (classLoader instanceof URLClassLoader) {
			URLClassLoader urlClassLoader = (URLClassLoader)classLoader;

			for (URL url : urlClassLoader.getURLs()) {
				urLs.add(url);

				_urlPACLPolicies.put(url, paclPolicy);
			}
		}

		ServletContext servletContext = ServletContextPool.get(
			paclPolicy.getServletContextName());

		String realPath = servletContext.getRealPath(StringPool.SLASH);

		try {
			URL url = new URL("file", "", -1, realPath);

			urLs.add(url);

			_urlPACLPolicies.put(url, paclPolicy);
		}
		catch (MalformedURLException e) {
		}

		_classLoaderPACLPolicies.put(classLoader, paclPolicy);
	}

	public static synchronized void unregister(ClassLoader classLoader) {
		PACLPolicy paclPolicy = _classLoaderPACLPolicies.remove(classLoader);

		for (URL url : paclPolicy.getURLs()) {
			_urlPACLPolicies.remove(url);
		}
	}

	private static PACLPolicy _defaultPACLPolicy = new InactivePACLPolicy(
		StringPool.BLANK, PACLPolicyManager.class.getClassLoader(),
		new Properties());
	private static Map<ClassLoader, PACLPolicy> _classLoaderPACLPolicies =
		new ConcurrentHashMap<ClassLoader, PACLPolicy>();
	private static Map<URL, PACLPolicy> _urlPACLPolicies =
		new ConcurrentHashMap<URL, PACLPolicy>();

	private static class PACLPolicyPrivilegedAction
		implements PrivilegedAction<PACLPolicy> {

		public PACLPolicyPrivilegedAction(ClassLoader classLoader) {
			_classLoader = classLoader;
		}

		public PACLPolicyPrivilegedAction(URL location) {
			_location = location;
		}

		public PACLPolicyPrivilegedAction(ProtectionDomain protectionDomain) {
			_protectionDomain = protectionDomain;

			_classLoader = _protectionDomain.getClassLoader();
		}

		@Override
		public PACLPolicy run() {
			PACLPolicy paclPolicy = getFromClassLoader();

			if (paclPolicy == null) {
				paclPolicy = getFromProtectionDomain();
			}

			if (paclPolicy == null) {
				paclPolicy = getFromLocation(_location);
			}

			return paclPolicy;
		}

		private PACLPolicy getFromClassLoader() {
			if (_classLoader == null) {
				return null;
			}

			PACLPolicy paclPolicy = _classLoaderPACLPolicies.get(_classLoader);

			while ((paclPolicy == null) && (_classLoader.getParent() != null)) {
				_classLoader = _classLoader.getParent();

				paclPolicy = _classLoaderPACLPolicies.get(_classLoader);
			}

			return paclPolicy;
		}

		private PACLPolicy getFromLocation(URL location) {
			if (location == null) {
				return null;
			}

			return _urlPACLPolicies.get(location);
		}

		private PACLPolicy getFromProtectionDomain() {
			if ((_protectionDomain == null) || (_classLoader != null)) {
				return null;
			}

			CodeSource codeSource = _protectionDomain.getCodeSource();

			if (codeSource == null) {
				return null;
			}

			URL location = codeSource.getLocation();

			return getFromLocation(location);
		}

		private ClassLoader _classLoader;
		private URL _location;
		private ProtectionDomain _protectionDomain;

	}

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.servlet.jsp.compiler.internal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.IOException;

import java.net.URL;
import java.net.URLClassLoader;

import java.util.Collections;
import java.util.Enumeration;

import org.osgi.framework.Bundle;

/**
 * @author Miguel Pastor
 */
public class JspBundleClassloader extends URLClassLoader {

	public JspBundleClassloader(Bundle... bundles) {
		super(new URL[0], PortalException.class.getClassLoader());

		if (bundles.length == 0) {
			throw new IllegalArgumentException(
				"At least one bundle is required");
		}

		_bundles = bundles;
	}

	@Override
	public URL findResource(String name) {
		for (Bundle bundle : _bundles) {
			URL url = bundle.getResource(name);

			if (url != null) {
				return url;
			}
		}

		return null;
	}

	@Override
	public Enumeration<URL> findResources(String name) {
		for (Bundle bundle : _bundles) {
			try {
				Enumeration<URL> enumeration = bundle.getResources(name);

				if ((enumeration != null) && enumeration.hasMoreElements()) {
					return enumeration;
				}
			}
			catch (IOException ioException) {
				if (_log.isDebugEnabled()) {
					_log.debug(ioException);
				}
			}
		}

		return Collections.enumeration(Collections.<URL>emptyList());
	}

	public Bundle[] getBundles() {
		return _bundles;
	}

	@Override
	public URL getResource(String name) {
		return findResource(name);
	}

	@Override
	public Enumeration<URL> getResources(String name) {
		return findResources(name);
	}

	@Override
	protected Class<?> loadClass(String name, boolean resolve)
		throws ClassNotFoundException {

		Class<?> clazz = null;

		for (Bundle bundle : _bundles) {
			try {
				clazz = bundle.loadClass(name);

				break;
			}
			catch (ClassNotFoundException classNotFoundException) {
				if (_log.isDebugEnabled()) {
					_log.debug(classNotFoundException);
				}
			}
		}

		if (clazz == null) {
			return super.loadClass(name, resolve);
		}

		if (resolve) {
			resolveClass(clazz);
		}

		return clazz;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JspBundleClassloader.class);

	static {
		ClassLoader.registerAsParallelCapable();
	}

	private final Bundle[] _bundles;

}
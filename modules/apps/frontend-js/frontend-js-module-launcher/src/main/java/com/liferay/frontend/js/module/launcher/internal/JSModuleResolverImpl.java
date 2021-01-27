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

package com.liferay.frontend.js.module.launcher.internal;

import com.liferay.frontend.js.module.launcher.JSModuleResolver;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.servlet.ServletContextClassLoaderPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.net.URL;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleReference;
import org.osgi.service.component.annotations.Component;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = JSModuleResolver.class)
public class JSModuleResolverImpl implements JSModuleResolver {

	public String resolveModule(Bundle bundle, String moduleName) {
		URL url = bundle.getEntry("META-INF/resources/package.json");

		if (url == null) {
			throw new UnsupportedOperationException(
				"Unable to find META-INF/resources/package.json in " +
					bundle.getSymbolicName());
		}

		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				StringUtil.read(url.openStream()));

			String packageName = jsonObject.getString("name");

			if (Validator.isNull(moduleName)) {
				return packageName;
			}

			return packageName + StringPool.SLASH + moduleName;
		}
		catch (Exception exception) {
			throw new RuntimeException("Unable to read " + url, exception);
		}
	}

	public String resolveModule(
		ServletContext servletContext, String moduleName) {

		ClassLoader classLoader = ServletContextClassLoaderPool.getClassLoader(
			servletContext.getServletContextName());

		if (!(classLoader instanceof BundleReference)) {
			return null;
		}

		BundleReference bundleReference = (BundleReference)classLoader;

		return resolveModule(bundleReference.getBundle(), moduleName);
	}

}
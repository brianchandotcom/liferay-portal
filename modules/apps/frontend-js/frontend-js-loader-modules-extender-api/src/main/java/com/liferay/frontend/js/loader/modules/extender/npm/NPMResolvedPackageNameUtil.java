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

package com.liferay.frontend.js.loader.modules.extender.npm;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.servlet.ServletContextClassLoaderPool;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.net.URL;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleReference;

/**
 * @author Iván Zaera Avellón
 */
public class NPMResolvedPackageNameUtil {

	/**
	 * Get the NPM resolved package name associated to the current portlet.
	 *
	 * The current portlet is inferred from the portletResource parameter or
	 * the {@link ServletContext} associated to the given request.
	 *
	 * @param  httpServletRequest
	 * @return
	 * @review
	 */
	public static String get(HttpServletRequest httpServletRequest) {
		ServletContext servletContext = httpServletRequest.getServletContext();

		String portletResource = ParamUtil.getString(
			httpServletRequest, "portletResource");

		if (Validator.isNotNull(portletResource)) {
			PortletBag portletBag = PortletBagPool.get(
				PortletIdCodec.decodePortletName(portletResource));

			if (portletBag != null) {
				servletContext = portletBag.getServletContext();
			}
		}

		return get(servletContext);
	}

	/**
	 * Get the NPM resolved package name associated to the bundle containing the
	 * given servlet context.
	 *
	 * @param  servletContext
	 * @return
	 * @review
	 */
	public static String get(ServletContext servletContext) {
		Object obj = servletContext.getAttribute(
			NPMResolvedPackageNameUtil.class.getName());

		if (obj instanceof String) {
			return (String)obj;
		}

		if (obj == _NULL_HOLDER) {
			return null;
		}

		String npmResolvedPackageName = _getNPMResolvedPackageName(
			ServletContextClassLoaderPool.getClassLoader(
				servletContext.getServletContextName()));

		if (npmResolvedPackageName == null) {
			servletContext.setAttribute(
				NPMResolvedPackageNameUtil.class.getName(), _NULL_HOLDER);
		}
		else {
			servletContext.setAttribute(
				NPMResolvedPackageNameUtil.class.getName(),
				npmResolvedPackageName);
		}

		return npmResolvedPackageName;
	}

	public static void set(
		ServletContext servletContext, String npmResolvedPackageName) {

		if (npmResolvedPackageName != null) {
			servletContext.setAttribute(
				NPMResolvedPackageNameUtil.class.getName(),
				npmResolvedPackageName);
		}
		else {
			servletContext.removeAttribute(
				NPMResolvedPackageNameUtil.class.getName());
		}
	}

	private static String _getNPMResolvedPackageName(
		Bundle bundle, NPMResolver npmResolver) {

		URL url = bundle.getResource("META-INF/resources/package.json");

		if (url == null) {
			return null;
		}

		try {
			String json = StringUtil.read(url.openStream());

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(json);

			String name = jsonObject.getString("name");

			return npmResolver.resolveModuleName(name);
		}
		catch (Exception e) {
			_log.error(
				"Unable to read META-INF/resources/package.json in " +
					bundle.getSymbolicName(),
				e);
		}

		return null;
	}

	private static String _getNPMResolvedPackageName(ClassLoader classLoader) {
		if (!(classLoader instanceof BundleReference)) {
			return null;
		}

		BundleReference bundleReference = (BundleReference)classLoader;

		Bundle bundle = bundleReference.getBundle();

		return _getNPMResolvedPackageName(
			bundle, NPMResolverUtil.getNPMResolver(bundle));
	}

	private static final Object _NULL_HOLDER = new Object();

	private static final Log _log = LogFactoryUtil.getLog(
		NPMResolvedPackageNameUtil.class);

}
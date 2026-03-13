/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.util;

import com.liferay.frontend.js.web.internal.configuration.FrontendCachingConfiguration;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Portal;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;

/**
 * This is a general utility class for the module.
 *
 * Among other things it is heavily used to manipulate URLs and paths so that
 * things work no matter how CDN, portal context and proxy path are configured.
 * There are some key concepts related to this:
 *
 * <ul>
 *   <li>
 *     <b>Web context path</b> is the value of a `Web-ContextPath` header in a
 *     BND file (eg: "/frontend-js-web").
 * 	 </li>
 *     <b>Servlet context name</b> is the same as web context path without the
 *     leading "/" (eg: "frontend-js-web").
 *   <li>
 *     <b>Servlet context path</b> is the path, relative to DXP server's root,
 *     where a web context lives (eg: "/o/frontend-js-web" or
 *     "/dxp/o/frontend-js-web").
 * 	 </li>
 *   <li>
 *     <b>File URI</b>: is the absolute path, relative to DXP server's root,
 *     to a public resource (eg: "/o/frontend-js-web/__liferay__/index.js" or
 *     "/dxp/o/frontend-js-web/__liferay__/index.js").
 * 	 </li>
 *   <li>
 *     <b>Servlet context resource path</b> is the absolute path to a
 *     `ServletContext`'s resource (eg: "/__liferay__/index.js").
 * 	 </li>
 *   <li>
 *     <b>Portal context path</b> is the context where DXP lives, the name of
 *     its webapp in Tomcat (eg: "" or "/dxp").
 * 	 </li>
 * </ul>
 *
 * @author Iván Zaera Avellón
 * @review
 */
public class FrontendJsWebUtil {

	public static void clearCache() {
		_baseURL = null;
		_portalContextPath = null;
	}

	/**
	 * Get the base URL to use when generating URLs to be consumed by the
	 * browser.
	 *
	 * The base URL contains the CDN path (if defined) followed by the
	 * proxy path (if defined).
	 *
	 * Examples: "", "/proxy", "http://cdn.com" or "http://cdn.com/proxy".
	 *
	 * @review
	 */
	public static String getBaseURL(
		HttpServletRequest httpServletRequest, Portal portal) {

		if (_baseURL != null) {
			return _baseURL;
		}

		StringBundler sb = new StringBundler(2);

		try {
			sb.append(portal.getCDNHost(httpServletRequest));
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}

		sb.append(portal.getPathProxy());

		_baseURL = sb.toString();

		return _baseURL;
	}

	public static FrontendCachingConfiguration getFrontendCachingConfiguration(
		long companyId, ConfigurationProvider configurationProvider) {

		try {
			return configurationProvider.getCompanyConfiguration(
				FrontendCachingConfiguration.class, companyId);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get frontend caching configuration for " +
						"company " + companyId,
					exception);
			}

			return ConfigurableUtil.createConfigurable(
				FrontendCachingConfiguration.class, new HashMap<>());
		}
	}

	/**
	 * Get the portal context path (without the CDN or proxy part).
	 *
	 * Examples: "", "/dxp".
	 *
	 * @review
	 */
	public static String getPortalContextPath(Portal portal) {
		if (_portalContextPath != null) {
			return _portalContextPath;
		}

		String contextPath = portal.getPathContext();

		String proxyPath = portal.getPathProxy();

		_portalContextPath = contextPath.substring(proxyPath.length());

		return _portalContextPath;
	}

	/**
	 * Get the servlet context name from a given a servlet context path.
	 *
	 * Examples: from "/o/frontend-js-web" or "/context/o/frontend-js-web" get
	 * "frontend-js-web".
	 *
	 * @review
	 */
	public static String getServletContextNameFromServletContextPath(
		Portal portal, String servletContextPath) {

		String portalContextPath = getPortalContextPath(portal);

		return servletContextPath.substring(portalContextPath.length() + 3);
	}

	/**
	 * Get the servlet context path from a given file URI.
	 *
	 * Examples: from "/o/frontend-js-web/__liferay__/index.js" get
	 * "/o/frontend-js-web", or from
	 * "/context/o/frontend-js-web/__liferay__/index.js" get
	 * "/context/o/frontend-js-web".
	 *
	 * @review
	 */
	public static String getServletContextPathFromFileURI(
		String fileURI, Portal portal) {

		String portalContextPath = getPortalContextPath(portal);

		int webContextPathStartIndex = portalContextPath.length() + 2;

		int webContextPathEndIndex = fileURI.indexOf(
			StringPool.SLASH, webContextPathStartIndex + 1);

		if (webContextPathEndIndex == -1) {
			webContextPathEndIndex = fileURI.length();
		}

		return fileURI.substring(0, webContextPathStartIndex) +
			fileURI.substring(webContextPathStartIndex, webContextPathEndIndex);
	}

	/**
	 * Get the servlet context path from a given a servlet context name.
	 *
	 * Examples: from "frontend-js-web" get "/o/frontend-js-web" or
	 * "/context/o/frontend-js-web".
	 *
	 * @review
	 */
	public static String getServletContextPathFromServletContextName(
		Portal portal, String servletContextName) {

		return StringBundler.concat(
			getPortalContextPath(portal), Portal.PATH_MODULE, StringPool.SLASH,
			servletContextName);
	}

	/**
	 * Get the path to a servlet context resource given its public file URI.
	 *
	 * Examples: from "/o/frontend-js-web/__liferay__/index.js" or
	 * "/context/o/frontend-js-web/__liferay__/index.js" get
	 * "/__liferay__/index.js".
	 *
	 * @review
	 */
	public static String getServletContextResourcePathFromFileURI(
		String fileURI, Portal portal) {

		String portalContextPath = getPortalContextPath(portal);

		int webContextPathStartIndex = portalContextPath.length() + 2;

		int webContextPathEndIndex = fileURI.indexOf(
			StringPool.SLASH, webContextPathStartIndex + 1);

		if (webContextPathEndIndex == -1) {
			throw new IllegalArgumentException(fileURI);
		}

		return fileURI.substring(webContextPathEndIndex);
	}

	/**
	 * Get the web context path part of a given file URI.
	 *
	 * Examples: from "/o/frontend-js-web/__liferay__/index.js" or
	 * "/context/o/frontend-js-web/__liferay__/index.js" get
	 * "/frontend-js-web".
	 *
	 * @review
	 */
	public static String getWebContextPathFromFileURI(
		Portal portal, String fileURI) {

		String portalContextPath = getPortalContextPath(portal);

		int webContextPathStartIndex = portalContextPath.length() + 2;

		int webContextPathEndIndex = fileURI.indexOf(
			StringPool.SLASH, webContextPathStartIndex + 1);

		return fileURI.substring(
			webContextPathStartIndex, webContextPathEndIndex);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FrontendJsWebUtil.class);

	private static volatile String _baseURL;
	private static volatile String _portalContextPath;

}
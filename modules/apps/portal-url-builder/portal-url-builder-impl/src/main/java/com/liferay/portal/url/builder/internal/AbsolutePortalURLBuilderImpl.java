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

package com.liferay.portal.url.builder.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.portlet.PortletDependency;
import com.liferay.portal.kernel.servlet.PortalWebResourceConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.ImageAbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.MainAbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.ModuleAbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.PortletDependencyAbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.ResourceAbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.WhiteboardAbsolutePortalURLBuilder;

import java.util.Dictionary;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.Bundle;

/**
 * @author Iván Zaera Avellón
 */
public class AbsolutePortalURLBuilderImpl implements AbsolutePortalURLBuilder {

	public AbsolutePortalURLBuilderImpl(
		Portal portal, HttpServletRequest httpServletRequest) {

		_portal = portal;
		_httpServletRequest = httpServletRequest;

		String pathContext = portal.getPathContext();

		String pathProxy = portal.getPathProxy();

		_pathContext = pathContext.substring(pathProxy.length());

		_pathImage = _pathContext + Portal.PATH_IMAGE;
		_pathMain = _pathContext + Portal.PATH_MAIN;
		_pathModule = _pathContext + Portal.PATH_MODULE;
	}

	@Override
	public ImageAbsolutePortalURLBuilder forImage(String relativeURL) {
		return new ImageAbsolutePortalURLBuilder() {

			@Override
			public String build() {
				return _build(
					_ignoreCDNHost, _ignorePathProxy, _pathImage, relativeURL);
			}

		};
	}

	@Override
	public MainAbsolutePortalURLBuilder forMain(String relativeURL) {
		return new MainAbsolutePortalURLBuilder() {

			@Override
			public String build() {
				return _build(true, _ignorePathProxy, _pathMain, relativeURL);
			}

		};
	}

	@Override
	public ModuleAbsolutePortalURLBuilder forModule(
		Bundle bundle, String relativeURL) {

		return new ModuleAbsolutePortalURLBuilder() {

			@Override
			public String build() {
				Dictionary<String, String> headers = bundle.getHeaders(
					StringPool.BLANK);

				String webContextPath = headers.get("Web-ContextPath");

				if (!webContextPath.endsWith(StringPool.SLASH)) {
					webContextPath += StringPool.SLASH;
				}

				return _build(
					_ignoreCDNHost, _ignorePathProxy,
					_pathModule + webContextPath, relativeURL);
			}

		};
	}

	@Override
	public PortletDependencyAbsolutePortalURLBuilder forPortletDependency(
		PortletDependency portletDependency, String cssURN,
		String javaScriptURN) {

		return new PortletDependencyAbsolutePortalURLBuilder() {

			@Override
			public String build() {
				StringBundler sb = new StringBundler(7);

				boolean ignoreCDNHost = _ignoreCDNHost;
				boolean ignorePathProxy = _ignorePathProxy;

				if (PortletDependency.Type.CSS == portletDependency.getType()) {
					String resourcePath = cssURN;

					if (Validator.isNull(resourcePath)) {
						resourcePath =
							PortalWebResourceConstants.RESOURCE_TYPE_CSS;
					}
					else {
						ignoreCDNHost = true;
						ignorePathProxy = true;
					}

					sb.append(resourcePath);
				}
				else if (PortletDependency.Type.JAVASCRIPT ==
							portletDependency.getType()) {

					String resourcePath = javaScriptURN;

					if (Validator.isNull(resourcePath)) {
						resourcePath =
							PortalWebResourceConstants.RESOURCE_TYPE_JS;
					}
					else {
						ignoreCDNHost = true;
						ignorePathProxy = true;
					}

					sb.append(resourcePath);
				}

				if (Validator.isNotNull(portletDependency.getScope())) {
					sb.append(StringPool.FORWARD_SLASH);
					sb.append(portletDependency.getScope());
				}

				if (Validator.isNotNull(portletDependency.getVersion())) {
					sb.append(StringPool.FORWARD_SLASH);
					sb.append(portletDependency.getVersion());
				}

				sb.append(StringPool.FORWARD_SLASH);
				sb.append(portletDependency.getName());

				return _build(
					ignoreCDNHost, ignorePathProxy, StringPool.BLANK,
					sb.toString());
			}

		};
	}

	@Override
	public ResourceAbsolutePortalURLBuilder forResource(String relativeURL) {
		return new ResourceAbsolutePortalURLBuilder() {

			@Override
			public String build() {
				return _build(
					_ignoreCDNHost, _ignorePathProxy, _pathContext,
					relativeURL);
			}

		};
	}

	@Override
	public WhiteboardAbsolutePortalURLBuilder forWhiteboard(
		String servletPattern) {

		return new WhiteboardAbsolutePortalURLBuilder() {

			@Override
			public String build() {
				return _build(
					true, _ignorePathProxy, _pathModule, servletPattern);
			}

		};
	}

	@Override
	public AbsolutePortalURLBuilder ignoreCDNHost() {
		_ignoreCDNHost = true;

		return this;
	}

	@Override
	public AbsolutePortalURLBuilder ignorePathProxy() {
		_ignorePathProxy = true;

		return this;
	}

	private String _build(
		boolean ignoreCDNHost, boolean ignorePathProxy, String pathPrefix,
		String relativeURL) {

		StringBundler sb = new StringBundler(6);

		String cdnHost = _getCDNHost(_httpServletRequest);

		if (!ignoreCDNHost && !Validator.isBlank(cdnHost)) {
			sb.append(cdnHost);
		}

		if (!ignorePathProxy) {
			sb.append(_getPathProxy());
		}

		if (!Validator.isBlank(pathPrefix)) {
			if (!pathPrefix.startsWith(StringPool.SLASH)) {
				sb.append(StringPool.SLASH);
			}

			if (pathPrefix.endsWith(StringPool.SLASH)) {
				sb.append(pathPrefix.substring(0, pathPrefix.length() - 1));
			}
			else {
				sb.append(pathPrefix);
			}
		}

		if (!relativeURL.startsWith(StringPool.SLASH)) {
			sb.append(StringPool.SLASH);
		}

		sb.append(relativeURL);

		return sb.toString();
	}

	private String _getCDNHost(HttpServletRequest httpServletRequest) {
		String cdnHost;

		try {
			cdnHost = _portal.getCDNHost(httpServletRequest);
		}
		catch (PortalException pe) {
			cdnHost = StringPool.BLANK;

			if (_log.isWarnEnabled()) {
				_log.warn("Unable to retrieve CDN host from request", pe);
			}
		}

		if (cdnHost.endsWith(StringPool.SLASH)) {
			cdnHost = cdnHost.substring(0, cdnHost.length() - 1);
		}

		return cdnHost;
	}

	private String _getPathProxy() {
		String pathProxy = _portal.getPathProxy();

		if (!Validator.isBlank(pathProxy) &&
			!pathProxy.startsWith(StringPool.SLASH)) {

			pathProxy = StringPool.SLASH + pathProxy;
		}

		if (pathProxy.endsWith(StringPool.SLASH)) {
			pathProxy = pathProxy.substring(0, pathProxy.length() - 1);
		}

		return pathProxy;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AbsolutePortalURLBuilderImpl.class);

	private final HttpServletRequest _httpServletRequest;
	private boolean _ignoreCDNHost;
	private boolean _ignorePathProxy;

	/**
	 * Points to the web context path of the Portal's webapp (doesn't contain
	 * the proxy, CDN, or any other kind of configurable path.
	 *
	 * @review
	 */
	private final String _pathContext;

	private final String _pathImage;
	private final String _pathMain;
	private final String _pathModule;
	private final Portal _portal;

}
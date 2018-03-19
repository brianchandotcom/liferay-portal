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

package com.liferay.portal.url.builder.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.url.builder.AbsolutePortalImageURLBuilder;
import com.liferay.portal.url.builder.AbsolutePortalMainURLBuilder;
import com.liferay.portal.url.builder.AbsolutePortalModuleURLBuilder;
import com.liferay.portal.url.builder.AbsolutePortalResourceURLBuilder;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilder;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera Avellón
 */
public class AbsolutePortalURLBuilderImpl implements AbsolutePortalURLBuilder {

	public AbsolutePortalURLBuilderImpl(
		Portal portal, HttpServletRequest request) {

		_portal = portal;
		_request = request;
	}

	@Override
	public AbsolutePortalImageURLBuilder forImage(String relativeURL) {
		return new AbsolutePortalImageURLBuilder() {

			@Override
			public String build() {
				return _build(_portal.getPathImage(), relativeURL);
			}

		};
	}

	@Override
	public AbsolutePortalMainURLBuilder forMain(String relativeURL) {
		return new AbsolutePortalMainURLBuilder() {

			@Override
			public String build() {
				return _build(_portal.getPathMain(), relativeURL);
			}

		};
	}

	@Override
	public AbsolutePortalModuleURLBuilder forModule(String relativeURL) {
		return new AbsolutePortalModuleURLBuilder() {

			@Override
			public String build() {
				return _build(_portal.getPathModule(), relativeURL);
			}

		};
	}

	@Override
	public AbsolutePortalResourceURLBuilder forResource(String relativeURL) {
		return new AbsolutePortalResourceURLBuilder() {

			@Override
			public String build() {
				return _build(StringPool.BLANK, relativeURL);
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

	private String _build(String pathPrefix, String relativeURL) {
		StringBundler sb = new StringBundler(6);

		if (!_ignoreCDNHost) {
			sb.append(_getCDNHost(_request));
		}

		if (!_ignorePathProxy) {
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

	private String _getCDNHost(HttpServletRequest request) {
		String cdnHost;

		try {
			cdnHost = _portal.getCDNHost(request);
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

	private boolean _ignoreCDNHost;
	private boolean _ignorePathProxy;
	private final Portal _portal;
	private final HttpServletRequest _request;

}
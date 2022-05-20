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
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.url.builder.ESModuleAbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.internal.util.CacheHelper;
import com.liferay.portal.url.builder.internal.util.URLUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera Avellón
 */
public class ESModuleAbsolutePortalURLBuilderImpl
	implements ESModuleAbsolutePortalURLBuilder {

	public ESModuleAbsolutePortalURLBuilderImpl(
		CacheHelper cacheHelper, String esModulePath, String cdnHost,
		HttpServletRequest httpServletRequest, String pathModule,
		String pathProxy, String webContextPath) {

		if (!esModulePath.startsWith(StringPool.SLASH)) {
			esModulePath = StringPool.SLASH + esModulePath;
		}

		if (!webContextPath.startsWith(StringPool.SLASH)) {
			webContextPath = StringPool.SLASH + webContextPath;
		}

		_cacheHelper = cacheHelper;
		_esModulePath = esModulePath;
		_cdnHost = cdnHost;
		_httpServletRequest = httpServletRequest;
		_pathModule = pathModule;
		_pathProxy = pathProxy;
		_webContextPath = webContextPath;
	}

	@Override
	public String build() {
		StringBundler sb = new StringBundler();

		URLUtil.appendURL(
			sb, _cdnHost, _ignoreCDNHost, _ignorePathProxy,
			_pathModule + _webContextPath + "/__liferay__", _pathProxy,
			_esModulePath);

		if (_cachePolicy == CachePolicy.NEVER) {
			_cacheHelper.appendNeverCacheParam(sb);
		}
		else if (_cachePolicy == CachePolicy.UNTIL_CHANGED) {
			_cacheHelper.appendLastRestartCacheParam(sb);
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		URLUtil.appendParam(sb, "languageId", themeDisplay.getLanguageId());

		return sb.toString();
	}

	@Override
	public ESModuleAbsolutePortalURLBuilder cache(CachePolicy cachePolicy) {
		_cachePolicy = cachePolicy;

		return this;
	}

	@Override
	public ESModuleAbsolutePortalURLBuilder ignoreCDNHost() {
		_ignoreCDNHost = true;

		return this;
	}

	@Override
	public ESModuleAbsolutePortalURLBuilder ignorePathProxy() {
		_ignorePathProxy = true;

		return this;
	}

	private final CacheHelper _cacheHelper;
	private CachePolicy _cachePolicy = CachePolicy.UNTIL_CHANGED;
	private final String _cdnHost;
	private final String _esModulePath;
	private final HttpServletRequest _httpServletRequest;
	private boolean _ignoreCDNHost;
	private boolean _ignorePathProxy;
	private final String _pathModule;
	private final String _pathProxy;
	private final String _webContextPath;

}
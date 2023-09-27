/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.language.servlet.filter.internal;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.servlet.BufferCacheServletResponse;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import java.util.Locale;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Carlos Sierra Andrés
 */
public class LanguageFilter extends BasePortalFilter {

	public LanguageFilter(ResourceBundleLoader resourceBundleLoader) {
		_resourceBundleLoader = resourceBundleLoader;
	}

	@Override
	protected void processFilter(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		String languageId = httpServletRequest.getParameter("languageId");

		if (languageId == null) {
			if (_log.isInfoEnabled()) {
				_log.info("The request parameter \"languageId\" is null");
			}

			processFilter(
				LanguageFilter.class.getName(), httpServletRequest,
				httpServletResponse, filterChain);

			return;
		}

		BufferCacheServletResponse bufferCacheServletResponse =
			new BufferCacheServletResponse(httpServletResponse);

		processFilter(
			LanguageFilter.class.getName(), httpServletRequest,
			bufferCacheServletResponse, filterChain);

		if (_log.isDebugEnabled()) {
			String completeURL = HttpComponentsUtil.getCompleteURL(
				httpServletRequest);

			_log.debug("Translating response " + completeURL);
		}

		String content = bufferCacheServletResponse.getString();

		content = _translateResponse(languageId, content);

		ServletResponseUtil.write(httpServletResponse, content);
	}

	private String _translateResponse(String languageId, String content) {
		Locale locale = LocaleUtil.fromLanguageId(languageId);

		return LanguageUtil.process(
			() -> _resourceBundleLoader.loadResourceBundle(locale), locale,
			content);
	}

	private static final Log _log = LogFactoryUtil.getLog(LanguageFilter.class);

	private final ResourceBundleLoader _resourceBundleLoader;

}
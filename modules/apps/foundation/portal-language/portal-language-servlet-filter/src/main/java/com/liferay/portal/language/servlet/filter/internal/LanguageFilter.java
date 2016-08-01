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

package com.liferay.portal.language.servlet.filter.internal;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BufferCacheServletResponse;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import java.util.Locale;
import java.util.ResourceBundle;

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
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		String languageId = request.getParameter("languageId");

		if (languageId == null) {
			if (_log.isInfoEnabled()) {
				_log.info("The request parameter \"languageId\" is null");
			}

			processFilter(
				LanguageFilter.class.getName(), request, response, filterChain);

			return;
		}

		BufferCacheServletResponse bufferCacheServletResponse =
			new BufferCacheServletResponse(response);

		processFilter(
			LanguageFilter.class.getName(), request, bufferCacheServletResponse,
			filterChain);

		if (_log.isDebugEnabled()) {
			String completeURL = HttpUtil.getCompleteURL(request);

			_log.debug("Translating response " + completeURL);
		}

		String content = bufferCacheServletResponse.getString();

		content = translateResponse(languageId, content);

		ServletResponseUtil.write(response, content);
	}

	protected String translateResponse(String languageId, String content) {
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(languageId);

		Locale locale = LocaleUtil.fromLanguageId(languageId);

		return LanguageUtil.process(resourceBundle, locale, content);
	}

	private static final Log _log = LogFactoryUtil.getLog(LanguageFilter.class);

	private final ResourceBundleLoader _resourceBundleLoader;

}
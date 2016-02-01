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

package com.liferay.portal.language.servlet.filter;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BufferCacheServletResponse;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import java.io.IOException;
import java.io.InputStreamReader;

import java.net.URL;

import java.util.Locale;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.Bundle;

/**
 * @author Carlos Sierra Andrés
 */
public class LanguageFilter extends BasePortalFilter {

	public LanguageFilter(Bundle bundle) {
		_bundle = bundle;
	}

	protected ResourceBundle getResourceBundle(Locale locale) {
		String languageId = LocaleUtil.toLanguageId(locale);

		ResourceBundle resourceBundle = _resourceBundles.get(languageId);

		if (resourceBundle != null) {
			return resourceBundle;
		}

		ResourceBundleUtil.loadResourceBundles(
			_resourceBundles, locale,
			new ResourceBundleLoader() {

				@Override
				public ResourceBundle loadResourceBundle(String languageId) {
					String name = null;

					if (Validator.isNull(languageId)) {
						name = "content/Language.properties";
					}
					else {
						name = "content/Language_" + languageId + ".properties";
					}

					URL url = _bundle.getResource(name);

					if (url == null) {
						return null;
					}

					try {
						InputStreamReader inputStreamReader =
							new InputStreamReader(
								url.openStream(), StringPool.UTF8);

						return new PropertyResourceBundle(inputStreamReader);
					}
					catch (IOException ioe) {
						return null;
					}
				}

			});

		return _resourceBundles.get(languageId);
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
		Locale locale = LocaleUtil.fromLanguageId(languageId);

		ResourceBundle resourceBundle = getResourceBundle(locale);

		if (resourceBundle == null) {
			resourceBundle = LanguageResources.getResourceBundle(locale);
		}
		else {
			resourceBundle = new AggregateResourceBundle(
				resourceBundle, LanguageResources.getResourceBundle(locale));
		}

		return LanguageUtil.process(resourceBundle, locale, content);
	}

	private static final Log _log = LogFactoryUtil.getLog(LanguageFilter.class);

	private final Bundle _bundle;
	private final Map<String, ResourceBundle> _resourceBundles =
		new ConcurrentHashMap<>();

}
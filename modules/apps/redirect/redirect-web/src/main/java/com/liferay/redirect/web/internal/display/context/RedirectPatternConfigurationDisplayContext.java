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

package com.liferay.redirect.web.internal.display.context;

import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.redirect.configuration.RedirectPatternConfigurationProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alicia García
 */
public class RedirectPatternConfigurationDisplayContext {

	public RedirectPatternConfigurationDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletResponse liferayPortletResponse,
		RedirectPatternConfigurationProvider
			redirectPatternConfigurationProvider,
		long scopePK) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_redirectPatternConfigurationProvider =
			redirectPatternConfigurationProvider;
		_scopePK = scopePK;
	}

	public Map<String, String> getPatterns() throws ConfigurationException {
		if (_patternsMap != null) {
			return _patternsMap;
		}

		_patternsMap =
			_redirectPatternConfigurationProvider.getRedirectionPatternsMap(
				_scopePK);

		return _patternsMap;
	}

	public String getRedirectPatternConfigurationURL() {
		return PortletURLBuilder.createActionURL(
			_liferayPortletResponse
		).setActionName(
			"/redirect_settings/edit_redirect_patterns"
		).setRedirect(
			PortalUtil.getCurrentURL(_httpServletRequest)
		).setParameter(
			"scopePK", _scopePK
		).buildString();
	}

	public Map<String, Object> getRedirectPatternData()
		throws ConfigurationException {

		List<Map<String, Object>> patternList = new ArrayList<>();

		Map<String, String> redirectionPatterns = getPatterns();

		redirectionPatterns.forEach(
			(source, destination) -> patternList.add(
				HashMapBuilder.<String, Object>put(
					"destination", destination
				).put(
					"source", source.toString()
				).build()));

		return HashMapBuilder.<String, Object>put(
			"patternList", patternList
		).put(
			"portletNamespace", _liferayPortletResponse.getNamespace()
		).build();
	}

	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private Map<String, String> _patternsMap;
	private final RedirectPatternConfigurationProvider
		_redirectPatternConfigurationProvider;
	private final long _scopePK;

}
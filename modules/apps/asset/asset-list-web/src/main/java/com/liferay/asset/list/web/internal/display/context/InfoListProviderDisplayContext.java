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

package com.liferay.asset.list.web.internal.display.context;

import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.list.provider.InfoListProvider;
import com.liferay.info.list.provider.InfoListProviderTracker;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class InfoListProviderDisplayContext {

	public InfoListProviderDisplayContext(
		InfoItemServiceTracker infoItemServiceTracker,
		InfoListProviderTracker infoListProviderTracker,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_infoItemServiceTracker = infoItemServiceTracker;
		_infoListProviderTracker = infoListProviderTracker;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(_renderRequest);

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public SearchContainer<InfoListProvider<?>> getSearchContainer() {
		SearchContainer<InfoListProvider<?>> searchContainer =
			new SearchContainer<>(
				_renderRequest, _getPortletURL(), null,
				LanguageUtil.get(
					_httpServletRequest, "there-are-no-collection-providers"));

		List<InfoListProvider<?>> infoListProviders =
			_infoListProviderTracker.getInfoListProviders();

		searchContainer.setResults(
			ListUtil.subList(
				infoListProviders, searchContainer.getStart(),
				searchContainer.getEnd()));
		searchContainer.setTotal(infoListProviders.size());

		return searchContainer;
	}

	public String getSubtitle(InfoListProvider<?> infoListProvider) {
		String className = _getClassName(infoListProvider);

		if (Validator.isNotNull(className)) {
			return ResourceActionsUtil.getModelResource(
				_themeDisplay.getLocale(), className);
		}

		return StringPool.BLANK;
	}

	public String getTitle(InfoListProvider<?> infoListProvider) {
		return infoListProvider.getLabel(_themeDisplay.getLocale());
	}

	private String _getClassName(InfoListProvider<?> infoListProvider) {
		Class<?> clazz = infoListProvider.getClass();

		Type[] genericInterfaceTypes = clazz.getGenericInterfaces();

		for (Type genericInterfaceType : genericInterfaceTypes) {
			ParameterizedType parameterizedType =
				(ParameterizedType)genericInterfaceType;

			Class<?> typeClazz =
				(Class<?>)parameterizedType.getActualTypeArguments()[0];

			return typeClazz.getName();
		}

		return StringPool.BLANK;
	}

	private PortletURL _getPortletURL() {
		PortletURL currentURLObj = PortletURLUtil.getCurrent(
			_renderRequest, _renderResponse);

		try {
			return PortletURLUtil.clone(currentURLObj, _renderResponse);
		}
		catch (PortletException portletException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portletException, portletException);
			}

			return PortletURLBuilder.createRenderURL(
				_renderResponse
			).setParameters(
				currentURLObj.getParameterMap()
			).build();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		InfoListProviderDisplayContext.class);

	private final HttpServletRequest _httpServletRequest;
	private final InfoItemServiceTracker _infoItemServiceTracker;
	private final InfoListProviderTracker _infoListProviderTracker;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}
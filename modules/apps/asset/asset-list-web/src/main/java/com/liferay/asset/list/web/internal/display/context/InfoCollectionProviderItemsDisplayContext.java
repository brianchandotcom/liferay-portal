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

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.list.provider.DefaultInfoListProviderContext;
import com.liferay.info.list.provider.InfoListProvider;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.reflect.GenericUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class InfoCollectionProviderItemsDisplayContext {

	public InfoCollectionProviderItemsDisplayContext(
		InfoItemServiceTracker infoItemServiceTracker,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_infoItemServiceTracker = infoItemServiceTracker;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getInfoCollectionItemsType(Object result) {
		String className = StringPool.BLANK;

		if (result instanceof AssetEntry) {
			AssetEntry assetEntry = (AssetEntry)result;

			className = PortalUtil.getClassName(assetEntry.getClassNameId());
		}
		else {
			className = GenericUtil.getGenericClassName(_getInfoListProvider());
		}

		return ResourceActionsUtil.getModelResource(
			_themeDisplay.getLocale(), className);
	}

	public String getInfoCollectionProviderClassName() {
		if (Validator.isNotNull(_infoCollectionProviderClassName)) {
			return _infoCollectionProviderClassName;
		}

		_infoCollectionProviderClassName = GenericUtil.getGenericClassName(
			_getInfoListProvider());

		return _infoCollectionProviderClassName;
	}

	public InfoItemFieldValuesProvider<Object>
		getInfoItemFieldValuesProvider() {

		if (_infoItemFieldValuesProvider != null) {
			return _infoItemFieldValuesProvider;
		}

		_infoItemFieldValuesProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFieldValuesProvider.class,
				GenericUtil.getGenericClassName(_getInfoListProvider()));

		return _infoItemFieldValuesProvider;
	}

	public SearchContainer<Object> getSearchContainer() {
		SearchContainer<Object> searchContainer = new SearchContainer<>(
			_renderRequest, _getPortletURL(), null,
			LanguageUtil.get(
				_httpServletRequest,
				"there-are-no-items-in-the-collection-provider"));

		InfoListProvider<?> infoListProvider = _getInfoListProvider();

		DefaultInfoListProviderContext defaultInfoListProviderContext =
			new DefaultInfoListProviderContext(
				_themeDisplay.getScopeGroup(), _themeDisplay.getUser());

		List<Object> infoList = (List<Object>)infoListProvider.getInfoList(
			defaultInfoListProviderContext);

		searchContainer.setResults(
			ListUtil.subList(
				infoList, searchContainer.getStart(),
				searchContainer.getEnd()));
		searchContainer.setTotal(infoList.size());

		return searchContainer;
	}

	public boolean isShowActions() {
		if (_showActions != null) {
			return _showActions;
		}

		_showActions = ParamUtil.get(_renderRequest, "showActions", false);

		return _showActions;
	}

	private String _getInfoCollectionProviderKey() {
		if (_infoCollectionProviderKey != null) {
			return _infoCollectionProviderKey;
		}

		String infoCollectionProviderKey = ParamUtil.getString(
			_renderRequest, "infoCollectionProviderKey");

		if (Validator.isNull(infoCollectionProviderKey)) {
			infoCollectionProviderKey = ParamUtil.getString(
				_renderRequest, "collectionPK");
		}

		_infoCollectionProviderKey = infoCollectionProviderKey;

		return _infoCollectionProviderKey;
	}

	private InfoListProvider<?> _getInfoListProvider() {
		if (_infoListProvider != null) {
			return _infoListProvider;
		}

		_infoListProvider = _infoItemServiceTracker.getInfoItemService(
			InfoListProvider.class, _getInfoCollectionProviderKey());

		return _infoListProvider;
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
		InfoCollectionProviderItemsDisplayContext.class);

	private final HttpServletRequest _httpServletRequest;
	private String _infoCollectionProviderClassName;
	private String _infoCollectionProviderKey;
	private InfoItemFieldValuesProvider<Object> _infoItemFieldValuesProvider;
	private final InfoItemServiceTracker _infoItemServiceTracker;
	private InfoListProvider<?> _infoListProvider;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private Boolean _showActions;
	private final ThemeDisplay _themeDisplay;

}
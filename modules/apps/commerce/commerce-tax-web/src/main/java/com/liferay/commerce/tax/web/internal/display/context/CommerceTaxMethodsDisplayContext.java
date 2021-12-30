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

package com.liferay.commerce.tax.web.internal.display.context;

import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.tax.CommerceTaxEngine;
import com.liferay.commerce.tax.model.CommerceTaxMethod;
import com.liferay.commerce.tax.service.CommerceTaxMethodService;
import com.liferay.commerce.tax.util.comparator.CommerceTaxMethodNameComparator;
import com.liferay.commerce.util.CommerceTaxEngineRegistry;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceTaxMethodsDisplayContext {

	public CommerceTaxMethodsDisplayContext(
		CommerceChannelLocalService commerceChannelLocalService,
		ModelResourcePermission<CommerceChannel>
			commerceChannelModelResourcePermission,
		CommerceTaxEngineRegistry commerceTaxEngineRegistry,
		CommerceTaxMethodService commerceTaxMethodService,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_commerceChannelLocalService = commerceChannelLocalService;
		_commerceChannelModelResourcePermission =
			commerceChannelModelResourcePermission;
		_commerceTaxEngineRegistry = commerceTaxEngineRegistry;
		_commerceTaxMethodService = commerceTaxMethodService;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public long getCommerceChannelId() throws PortalException {
		if (_commerceTaxMethod != null) {
			CommerceChannel commerceChannel =
				_commerceChannelLocalService.getCommerceChannelByGroupId(
					_commerceTaxMethod.getGroupId());

			return commerceChannel.getCommerceChannelId();
		}

		return ParamUtil.getLong(_renderRequest, "commerceChannelId");
	}

	public CommerceTaxMethod getCommerceTaxMethod() throws PortalException {
		if (_commerceTaxMethod != null) {
			return _commerceTaxMethod;
		}

		long commerceTaxMethodId = ParamUtil.getLong(
			_renderRequest, "commerceTaxMethodId");

		if (commerceTaxMethodId != 0) {
			return _commerceTaxMethodService.getCommerceTaxMethod(
				commerceTaxMethodId);
		}

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(
				getCommerceChannelId());

		_commerceTaxMethod = _commerceTaxMethodService.fetchCommerceTaxMethod(
			commerceChannel.getGroupId(), getCommerceTaxMethodEngineKey());

		return _commerceTaxMethod;
	}

	public String getCommerceTaxMethodEngineDescription(Locale locale) {
		CommerceTaxEngine commerceTaxEngine =
			_commerceTaxEngineRegistry.getCommerceTaxEngine(
				getCommerceTaxMethodEngineKey());

		return commerceTaxEngine.getDescription(locale);
	}

	public String getCommerceTaxMethodEngineKey() {
		if (_commerceTaxMethod != null) {
			return _commerceTaxMethod.getEngineKey();
		}

		return ParamUtil.getString(
			_renderRequest, "commerceTaxMethodEngineKey");
	}

	public String getCommerceTaxMethodEngineName(Locale locale) {
		CommerceTaxEngine commerceTaxEngine =
			_commerceTaxEngineRegistry.getCommerceTaxEngine(
				getCommerceTaxMethodEngineKey());

		return commerceTaxEngine.getName(locale);
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		String delta = ParamUtil.getString(_renderRequest, "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		portletURL.setParameter("navigation", _getNavigation());

		String screenNavigationEntryKey = getScreenNavigationEntryKey();

		if (Validator.isNotNull(screenNavigationEntryKey)) {
			portletURL.setParameter(
				"screenNavigationEntryKey", screenNavigationEntryKey);
		}

		return portletURL;
	}

	public String getScreenNavigationEntryKey() {
		return ParamUtil.getString(_renderRequest, "screenNavigationEntryKey");
	}

	public SearchContainer<CommerceTaxMethod> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Boolean active = null;
		String emptyResultsMessage = "there-are-no-tax-methods";

		String navigation = _getNavigation();

		if (navigation.equals("active")) {
			active = Boolean.TRUE;
			emptyResultsMessage = "there-are-no-active-tax-methods";
		}
		else if (navigation.equals("inactive")) {
			active = Boolean.FALSE;
			emptyResultsMessage = "there-are-no-inactive-tax-methods";
		}

		_searchContainer = new SearchContainer<>(
			_renderRequest, getPortletURL(), null, emptyResultsMessage);

		List<CommerceTaxMethod> results;

		if (active != null) {
			results = _commerceTaxMethodService.getCommerceTaxMethods(
				themeDisplay.getScopeGroupId(), active);
		}
		else {
			results = _commerceTaxMethodService.getCommerceTaxMethods(
				themeDisplay.getScopeGroupId());
		}

		if ((active == null) || !active) {
			results = _addDefaultCommerceTaxMethods(results);
		}

		results.sort(
			new CommerceTaxMethodNameComparator(themeDisplay.getLocale()));

		_searchContainer.setTotal(results.size());
		_searchContainer.setResults(results);

		return _searchContainer;
	}

	public boolean hasUpdateCommerceChannelPermission() throws PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(
				getCommerceChannelId());

		return _commerceChannelModelResourcePermission.contains(
			themeDisplay.getPermissionChecker(), commerceChannel,
			ActionKeys.UPDATE);
	}

	private List<CommerceTaxMethod> _addDefaultCommerceTaxMethods(
			List<CommerceTaxMethod> commerceTaxMethods)
		throws PortalException {

		commerceTaxMethods = ListUtil.copy(commerceTaxMethods);

		Map<String, CommerceTaxEngine> commerceTaxEngines =
			_commerceTaxEngineRegistry.getCommerceTaxEngines();

		Set<String> commerceEngineKeys = new TreeSet<>(
			commerceTaxEngines.keySet());

		for (CommerceTaxMethod commerceTaxMethod : commerceTaxMethods) {
			commerceEngineKeys.remove(commerceTaxMethod.getEngineKey());
		}

		for (String name : commerceEngineKeys) {
			CommerceTaxMethod commerceTaxMethod = _getDefaultCommerceTaxMethod(
				name);

			commerceTaxMethods.add(commerceTaxMethod);
		}

		return commerceTaxMethods;
	}

	private CommerceTaxMethod _getDefaultCommerceTaxMethod(String engineKey)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		CommerceTaxEngine commerceTaxEngine =
			_commerceTaxEngineRegistry.getCommerceTaxEngine(engineKey);

		CommerceTaxMethod commerceTaxMethod =
			_commerceTaxMethodService.createCommerceTaxMethod(
				themeDisplay.getScopeGroupId(), 0);

		Locale locale = LocaleUtil.getSiteDefault();

		commerceTaxMethod.setName(commerceTaxEngine.getName(locale), locale);
		commerceTaxMethod.setDescription(
			commerceTaxEngine.getDescription(locale), locale);

		commerceTaxMethod.setEngineKey(engineKey);

		return commerceTaxMethod;
	}

	private String _getNavigation() {
		return ParamUtil.getString(_renderRequest, "navigation");
	}

	private final CommerceChannelLocalService _commerceChannelLocalService;
	private final ModelResourcePermission<CommerceChannel>
		_commerceChannelModelResourcePermission;
	private final CommerceTaxEngineRegistry _commerceTaxEngineRegistry;
	private CommerceTaxMethod _commerceTaxMethod;
	private final CommerceTaxMethodService _commerceTaxMethodService;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer<CommerceTaxMethod> _searchContainer;

}
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

package com.liferay.commerce.account.group.admin.web.internal.display.context;

import com.liferay.account.model.AccountGroup;
import com.liferay.commerce.account.group.admin.web.internal.display.context.util.CommerceAccountGroupAdminRequestHelper;
import com.liferay.commerce.account.group.admin.web.internal.search.CommerceAccountGroupChecker;
import com.liferay.commerce.account.item.selector.criterion.CommerceAccountGroupAccountItemSelectorCriterion;
import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.commerce.account.model.CommerceAccountGroupCommerceAccountRel;
import com.liferay.commerce.account.service.CommerceAccountGroupCommerceAccountRelService;
import com.liferay.commerce.account.service.CommerceAccountGroupService;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.CustomAttributesUtil;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceAccountGroupAdminDisplayContext {

	public CommerceAccountGroupAdminDisplayContext(
		CommerceAccountGroupCommerceAccountRelService
			commerceAccountGroupCommerceAccountRelService,
		ModelResourcePermission<CommerceAccountGroup>
			commerceAccountGroupModelResourcePermission,
		CommerceAccountGroupService commerceAccountGroupService,
		ItemSelector itemSelector, RenderRequest renderRequest) {

		_commerceAccountGroupCommerceAccountRelService =
			commerceAccountGroupCommerceAccountRelService;
		_commerceAccountGroupModelResourcePermission =
			commerceAccountGroupModelResourcePermission;
		_commerceAccountGroupService = commerceAccountGroupService;
		_itemSelector = itemSelector;

		_commerceAccountGroupAdminRequestHelper =
			new CommerceAccountGroupAdminRequestHelper(renderRequest);

		_portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			renderRequest);
	}

	public CommerceAccountGroup getCommerceAccountGroup()
		throws PortalException {

		if (_commerceAccountGroup != null) {
			return _commerceAccountGroup;
		}

		long commerceAccountGroupId = ParamUtil.getLong(
			_commerceAccountGroupAdminRequestHelper.getRequest(),
			"commerceAccountGroupId");

		if (commerceAccountGroupId > 0) {
			_commerceAccountGroup =
				_commerceAccountGroupService.getCommerceAccountGroup(
					commerceAccountGroupId);
		}

		return _commerceAccountGroup;
	}

	public SearchContainer<CommerceAccountGroupCommerceAccountRel>
			getCommerceAccountGroupCommerceAccountRelSearchContainer()
		throws PortalException {

		if (_commerceAccountGroupCommerceAccountRelSearchContainer != null) {
			return _commerceAccountGroupCommerceAccountRelSearchContainer;
		}

		_commerceAccountGroupCommerceAccountRelSearchContainer =
			new SearchContainer<>(
				_commerceAccountGroupAdminRequestHelper.
					getLiferayPortletRequest(),
				getPortletURL(), null, "there-are-no-accounts");

		_commerceAccountGroupCommerceAccountRelSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(
				_commerceAccountGroupAdminRequestHelper.
					getLiferayPortletResponse()));

		int total =
			_commerceAccountGroupCommerceAccountRelService.
				getCommerceAccountGroupCommerceAccountRelsCount(
					getCommerceAccountGroupId());

		List<CommerceAccountGroupCommerceAccountRel> results =
			_commerceAccountGroupCommerceAccountRelService.
				getCommerceAccountGroupCommerceAccountRels(
					getCommerceAccountGroupId(),
					_commerceAccountGroupCommerceAccountRelSearchContainer.
						getStart(),
					_commerceAccountGroupCommerceAccountRelSearchContainer.
						getEnd());

		_commerceAccountGroupCommerceAccountRelSearchContainer.setTotal(total);
		_commerceAccountGroupCommerceAccountRelSearchContainer.setResults(
			results);

		return _commerceAccountGroupCommerceAccountRelSearchContainer;
	}

	public long getCommerceAccountGroupId() throws PortalException {
		CommerceAccountGroup commerceAccountGroup = getCommerceAccountGroup();

		if (commerceAccountGroup == null) {
			return 0;
		}

		return commerceAccountGroup.getCommerceAccountGroupId();
	}

	public String getItemSelectorUrl() throws PortalException {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(
				_commerceAccountGroupAdminRequestHelper.getRequest());

		CommerceAccountGroupAccountItemSelectorCriterion
			commerceAccountGroupAccountItemSelectorCriterion =
				new CommerceAccountGroupAccountItemSelectorCriterion();

		commerceAccountGroupAccountItemSelectorCriterion.
			setDesiredItemSelectorReturnTypes(
				Collections.<ItemSelectorReturnType>singletonList(
					new UUIDItemSelectorReturnType()));

		return PortletURLBuilder.create(
			_itemSelector.getItemSelectorURL(
				requestBackedPortletURLFactory, "commerceAccountsSelectItem",
				commerceAccountGroupAccountItemSelectorCriterion)
		).setParameter(
			"commerceAccountGroupId", getCommerceAccountGroupId()
		).buildString();
	}

	public PortletURL getPortletURL() {
		LiferayPortletResponse liferayPortletResponse =
			_commerceAccountGroupAdminRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		HttpServletRequest httpServletRequest =
			_commerceAccountGroupAdminRequestHelper.getRequest();

		long commerceAccountGroupId = ParamUtil.getLong(
			httpServletRequest, "commerceAccountGroupId");

		if (commerceAccountGroupId > 0) {
			portletURL.setParameter(
				"mvcRenderCommandName",
				"/commerce_account_group_admin/edit_commerce_account_group");
			portletURL.setParameter(
				"commerceAccountGroupId",
				String.valueOf(commerceAccountGroupId));
		}

		String redirect = ParamUtil.getString(httpServletRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		String screenNavigationEntryKey = ParamUtil.getString(
			httpServletRequest, "screenNavigationEntryKey");

		if (Validator.isNotNull(screenNavigationEntryKey)) {
			portletURL.setParameter(
				"screenNavigationEntryKey", screenNavigationEntryKey);
		}

		String delta = ParamUtil.getString(httpServletRequest, "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String deltaEntry = ParamUtil.getString(
			httpServletRequest, "deltaEntry");

		if (Validator.isNotNull(deltaEntry)) {
			portletURL.setParameter("deltaEntry", deltaEntry);
		}

		String keywords = ParamUtil.getString(httpServletRequest, "keywords");

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		return portletURL;
	}

	public SearchContainer<CommerceAccountGroup> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			_commerceAccountGroupAdminRequestHelper.getLiferayPortletRequest(),
			getPortletURL(), null, "there-are-no-account-groups");

		_setOrderByColAndType(
			CommerceAccountGroup.class, _searchContainer, "name", "asc");

		_searchContainer.setRowChecker(
			new CommerceAccountGroupChecker(
				_commerceAccountGroupAdminRequestHelper.
					getLiferayPortletResponse()));

		int total =
			_commerceAccountGroupService.searchCommerceAccountsGroupCount(
				_commerceAccountGroupAdminRequestHelper.getCompanyId(),
				_getKeywords());

		List<CommerceAccountGroup> results =
			_commerceAccountGroupService.searchCommerceAccountGroups(
				_commerceAccountGroupAdminRequestHelper.getCompanyId(),
				_getKeywords(), _searchContainer.getStart(),
				_searchContainer.getEnd(), null);

		_searchContainer.setTotal(total);
		_searchContainer.setResults(results);

		return _searchContainer;
	}

	public boolean hasCustomAttributesAvailable() throws Exception {
		return CustomAttributesUtil.hasCustomAttributes(
			_commerceAccountGroupAdminRequestHelper.getCompanyId(),
			AccountGroup.class.getName(), getCommerceAccountGroupId(), null);
	}

	public boolean hasPermission(long commerceAccountGroupId, String actionId)
		throws PortalException {

		return _commerceAccountGroupModelResourcePermission.contains(
			_commerceAccountGroupAdminRequestHelper.getPermissionChecker(),
			commerceAccountGroupId, actionId);
	}

	public boolean hasPermission(String actionId) {
		PortletResourcePermission portletResourcePermission =
			_commerceAccountGroupModelResourcePermission.
				getPortletResourcePermission();

		return portletResourcePermission.contains(
			_commerceAccountGroupAdminRequestHelper.getPermissionChecker(),
			null, actionId);
	}

	private String _getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(
			_commerceAccountGroupAdminRequestHelper.getRequest(), "keywords");

		return _keywords;
	}

	private void _setOrderByColAndType(
		Class<CommerceAccountGroup> clazz,
		SearchContainer<CommerceAccountGroup> searchContainer,
		String defaultOrderByCol, String defaultOrderByType) {

		HttpServletRequest httpServletRequest =
			_commerceAccountGroupAdminRequestHelper.getRequest();

		String orderByCol = ParamUtil.getString(
			httpServletRequest, searchContainer.getOrderByColParam());
		String orderByType = ParamUtil.getString(
			httpServletRequest, searchContainer.getOrderByTypeParam());

		String namespace =
			_commerceAccountGroupAdminRequestHelper.getPortletId();
		String prefix = TextFormatter.format(
			clazz.getSimpleName(), TextFormatter.K);

		if (Validator.isNotNull(orderByCol) &&
			Validator.isNotNull(orderByType)) {

			_portalPreferences.setValue(
				namespace, prefix + "-order-by-col", orderByCol);
			_portalPreferences.setValue(
				namespace, prefix + "-order-by-type", orderByType);
		}
		else {
			orderByCol = _portalPreferences.getValue(
				namespace, prefix + "-order-by-col", defaultOrderByCol);
			orderByType = _portalPreferences.getValue(
				namespace, prefix + "-order-by-type", defaultOrderByType);
		}

		searchContainer.setOrderByCol(orderByCol);
		searchContainer.setOrderByType(orderByType);
	}

	private CommerceAccountGroup _commerceAccountGroup;
	private final CommerceAccountGroupAdminRequestHelper
		_commerceAccountGroupAdminRequestHelper;
	private SearchContainer<CommerceAccountGroupCommerceAccountRel>
		_commerceAccountGroupCommerceAccountRelSearchContainer;
	private final CommerceAccountGroupCommerceAccountRelService
		_commerceAccountGroupCommerceAccountRelService;
	private final ModelResourcePermission<CommerceAccountGroup>
		_commerceAccountGroupModelResourcePermission;
	private final CommerceAccountGroupService _commerceAccountGroupService;
	private final ItemSelector _itemSelector;
	private String _keywords;
	private final PortalPreferences _portalPreferences;
	private SearchContainer<CommerceAccountGroup> _searchContainer;

}
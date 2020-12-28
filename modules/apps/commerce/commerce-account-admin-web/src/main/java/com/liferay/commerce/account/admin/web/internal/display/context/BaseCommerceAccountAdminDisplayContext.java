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

package com.liferay.commerce.account.admin.web.internal.display.context;

import com.liferay.commerce.account.admin.web.internal.display.context.util.CommerceAccountAdminRequestHelper;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountService;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public abstract class BaseCommerceAccountAdminDisplayContext<T> {

	public BaseCommerceAccountAdminDisplayContext(
		ModelResourcePermission<CommerceAccount>
			commerceAccountModelResourcePermission,
		CommerceAccountService commerceAccountService,
		RenderRequest renderRequest) {

		this.commerceAccountModelResourcePermission =
			commerceAccountModelResourcePermission;
		this.commerceAccountService = commerceAccountService;

		commerceAccountAdminRequestHelper =
			new CommerceAccountAdminRequestHelper(renderRequest);

		portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			renderRequest);
	}

	public CommerceAccount getCommerceAccount() throws PortalException {
		if (commerceAccount != null) {
			return commerceAccount;
		}

		long commerceAccountId = ParamUtil.getLong(
			commerceAccountAdminRequestHelper.getRequest(),
			"commerceAccountId");

		if (commerceAccountId > 0) {
			commerceAccount = commerceAccountService.getCommerceAccount(
				commerceAccountId);
		}

		return commerceAccount;
	}

	public long getCommerceAccountId() throws PortalException {
		CommerceAccount commerceAccount = getCommerceAccount();

		if (commerceAccount == null) {
			return 0;
		}

		return commerceAccount.getCommerceAccountId();
	}

	public long getParentCommerceAccountId() {
		return ParamUtil.getLong(
			commerceAccountAdminRequestHelper.getRequest(),
			"parentCommerceAccountId");
	}

	public PortletURL getPortletURL() {
		LiferayPortletResponse liferayPortletResponse =
			commerceAccountAdminRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		HttpServletRequest httpServletRequest =
			commerceAccountAdminRequestHelper.getRequest();

		String redirect = ParamUtil.getString(httpServletRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		String backURL = ParamUtil.getString(httpServletRequest, "backURL");

		if (Validator.isNotNull(backURL)) {
			portletURL.setParameter("backURL", backURL);
		}

		long commerceAccountId = ParamUtil.getLong(
			httpServletRequest, "commerceAccountId");

		if (commerceAccountId > 0) {
			portletURL.setParameter(
				"commerceAccountId", String.valueOf(commerceAccountId));
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

		String screenNavigationEntryKey = getScreenNavigationEntryKey();

		if (Validator.isNotNull(screenNavigationEntryKey)) {
			portletURL.setParameter(
				"screenNavigationEntryKey", screenNavigationEntryKey);
		}

		return portletURL;
	}

	public String getScreenNavigationEntryKey() {
		return ParamUtil.getString(
			commerceAccountAdminRequestHelper.getRequest(),
			"screenNavigationEntryKey");
	}

	public abstract SearchContainer<T> getSearchContainer()
		throws PortalException;

	public boolean hasPermission(long commerceAccountId, String actionId)
		throws PortalException {

		return commerceAccountModelResourcePermission.contains(
			commerceAccountAdminRequestHelper.getPermissionChecker(),
			commerceAccountId, actionId);
	}

	public boolean hasPermission(String actionId) {
		PortletResourcePermission portletResourcePermission =
			commerceAccountModelResourcePermission.
				getPortletResourcePermission();

		return portletResourcePermission.contains(
			commerceAccountAdminRequestHelper.getPermissionChecker(), null,
			actionId);
	}

	protected String getKeywords() {
		if (keywords != null) {
			return keywords;
		}

		keywords = ParamUtil.getString(
			commerceAccountAdminRequestHelper.getRequest(), "keywords");

		return keywords;
	}

	protected <T> void setOrderByColAndType(
		Class<T> clazz, SearchContainer<T> searchContainer,
		String defaultOrderByCol, String defaultOrderByType) {

		HttpServletRequest httpServletRequest =
			commerceAccountAdminRequestHelper.getRequest();

		String orderByCol = ParamUtil.getString(
			httpServletRequest, searchContainer.getOrderByColParam());
		String orderByType = ParamUtil.getString(
			httpServletRequest, searchContainer.getOrderByTypeParam());

		String namespace = commerceAccountAdminRequestHelper.getPortletId();
		String prefix = TextFormatter.format(
			clazz.getSimpleName(), TextFormatter.K);

		if (Validator.isNotNull(orderByCol) &&
			Validator.isNotNull(orderByType)) {

			portalPreferences.setValue(
				namespace, prefix + "-order-by-col", orderByCol);
			portalPreferences.setValue(
				namespace, prefix + "-order-by-type", orderByType);
		}
		else {
			orderByCol = portalPreferences.getValue(
				namespace, prefix + "-order-by-col", defaultOrderByCol);
			orderByType = portalPreferences.getValue(
				namespace, prefix + "-order-by-type", defaultOrderByType);
		}

		searchContainer.setOrderByCol(orderByCol);
		searchContainer.setOrderByType(orderByType);
	}

	protected CommerceAccount commerceAccount;
	protected final CommerceAccountAdminRequestHelper
		commerceAccountAdminRequestHelper;
	protected final ModelResourcePermission<CommerceAccount>
		commerceAccountModelResourcePermission;
	protected final CommerceAccountService commerceAccountService;
	protected String keywords;
	protected final PortalPreferences portalPreferences;

}
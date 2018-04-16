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

package com.liferay.commerce.order.web.internal.asset;

import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.web.security.permission.resource.CommerceOrderPermission;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletProvider.Action;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andrea Di Giorgi
 * @author Marco Leo
 */
public class CommerceOrderAssetRenderer
	extends BaseJSPAssetRenderer<CommerceOrder> {

	public CommerceOrderAssetRenderer(CommerceOrder commerceOrder) {
		_commerceOrder = commerceOrder;
	}

	@Override
	public CommerceOrder getAssetObject() {
		return _commerceOrder;
	}

	@Override
	public String getClassName() {
		return CommerceOrder.class.getName();
	}

	@Override
	public long getClassPK() {
		return _commerceOrder.getCommerceOrderId();
	}

	@Override
	public long getGroupId() {
		return _commerceOrder.getGroupId();
	}

	@Override
	public String getJspPath(
		HttpServletRequest httpServletRequest, String template) {

		return null;
	}

	@Override
	public String getSummary(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return StringPool.BLANK;
	}

	@Override
	public String getTitle(Locale locale) {
		ResourceBundleLoader resourceBundleLoader = getResourceBundleLoader();

		ResourceBundle resourceBundle = resourceBundleLoader.loadResourceBundle(
			locale);

		return LanguageUtil.format(
			resourceBundle, "order-x", _commerceOrder.getCommerceOrderId());
	}

	@Override
	public String getURLViewInContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			String noSuchEntryRedirect)
		throws Exception {

		Group group = GroupLocalServiceUtil.getGroup(
			_commerceOrder.getSiteGroupId());

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			liferayPortletRequest, group, CommerceOrder.class.getName(),
			Action.VIEW);

		portletURL.setParameter(
			"mvcRenderCommandName", "viewCommerceOrderDetail");
		portletURL.setParameter(
			"commerceOrderId",
			String.valueOf(_commerceOrder.getCommerceOrderId()));

		return portletURL.toString();
	}

	@Override
	public long getUserId() {
		return _commerceOrder.getUserId();
	}

	@Override
	public String getUserName() {
		return _commerceOrder.getUserName();
	}

	@Override
	public String getUuid() {
		return _commerceOrder.getUuid();
	}

	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker)
		throws PortalException {

		return CommerceOrderPermission.contains(
			permissionChecker, _commerceOrder, ActionKeys.UPDATE);
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker)
		throws PortalException {

		return CommerceOrderPermission.contains(
			permissionChecker, _commerceOrder, ActionKeys.VIEW);
	}

	@Override
	public boolean isPreviewInContext() {
		return true;
	}

	private final CommerceOrder _commerceOrder;

}
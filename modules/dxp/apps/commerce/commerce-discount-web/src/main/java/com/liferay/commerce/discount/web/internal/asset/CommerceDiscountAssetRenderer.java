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

package com.liferay.commerce.discount.web.internal.asset;

import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.web.internal.security.permission.resource.CommerceDiscountPermission;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletProvider.Action;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceDiscountAssetRenderer
	extends BaseJSPAssetRenderer<CommerceDiscount> {

	public CommerceDiscountAssetRenderer(CommerceDiscount commerceDiscount) {
		_commerceDiscount = commerceDiscount;
	}

	@Override
	public CommerceDiscount getAssetObject() {
		return _commerceDiscount;
	}

	@Override
	public String getClassName() {
		return CommerceDiscount.class.getName();
	}

	@Override
	public long getClassPK() {
		return _commerceDiscount.getCommerceDiscountId();
	}

	@Override
	public long getGroupId() {
		return _commerceDiscount.getGroupId();
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
		return _commerceDiscount.getTitle();
	}

	@Override
	public String getURLViewInContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			String noSuchEntryRedirect)
		throws Exception {

		Group group = GroupLocalServiceUtil.getGroup(
			_commerceDiscount.getGroupId());

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			liferayPortletRequest, group, CommerceDiscount.class.getName(),
			Action.VIEW);

		portletURL.setParameter("mvcRenderCommandName", "editCommerceDiscount");
		portletURL.setParameter(
			"commerceDiscountId",
			String.valueOf(_commerceDiscount.getCommerceDiscountId()));

		return portletURL.toString();
	}

	@Override
	public long getUserId() {
		return _commerceDiscount.getUserId();
	}

	@Override
	public String getUserName() {
		return _commerceDiscount.getUserName();
	}

	@Override
	public String getUuid() {
		return _commerceDiscount.getUuid();
	}

	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker)
		throws PortalException {

		return CommerceDiscountPermission.contains(
			permissionChecker, _commerceDiscount, ActionKeys.UPDATE);
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker)
		throws PortalException {

		return CommerceDiscountPermission.contains(
			permissionChecker, _commerceDiscount, ActionKeys.VIEW);
	}

	@Override
	public boolean isPreviewInContext() {
		return true;
	}

	private final CommerceDiscount _commerceDiscount;

}
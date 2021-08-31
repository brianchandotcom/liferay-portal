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

package com.liferay.commerce.order.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.order.web.internal.display.context.CommerceOrderTypeDisplayContext;
import com.liferay.commerce.order.web.internal.servlet.taglib.ui.constants.CommerceOrderTypeScreenNavigationConstants;
import com.liferay.commerce.service.CommerceOrderTypeService;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(
	enabled = false,
	property = {
		"screen.navigation.category.order:Integer=10",
		"screen.navigation.entry.order:Integer=10"
	},
	service = {ScreenNavigationCategory.class, ScreenNavigationEntry.class}
)
public class CommerceOrderTypeDetailsScreenNavigationCategory
	implements ScreenNavigationCategory,
			   ScreenNavigationEntry<CommerceOrderType> {

	@Override
	public String getCategoryKey() {
		return CommerceOrderTypeScreenNavigationConstants.CATEGORY_KEY_DETAILS;
	}

	@Override
	public String getEntryKey() {
		return CommerceOrderTypeScreenNavigationConstants.CATEGORY_KEY_DETAILS;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(
			resourceBundle,
			CommerceOrderTypeScreenNavigationConstants.CATEGORY_KEY_DETAILS);
	}

	@Override
	public String getScreenNavigationKey() {
		return CommerceOrderTypeScreenNavigationConstants.
			SCREEN_NAVIGATION_KEY_COMMERCE_ORDER_TYPE_GENERAL;
	}

	@Override
	public boolean isVisible(User user, CommerceOrderType commerceOrderType) {
		if (commerceOrderType == null) {
			return false;
		}

		boolean hasPermission = false;

		try {
			hasPermission = _commerceOrderTypeModelResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(),
				commerceOrderType.getCommerceOrderTypeId(), ActionKeys.UPDATE);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return hasPermission;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		CommerceOrderTypeDisplayContext commerceOrderTypeDisplayContext =
			new CommerceOrderTypeDisplayContext(
				httpServletRequest, _commerceOrderTypeModelResourcePermission,
				_commerceOrderTypeService, _portal);

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, commerceOrderTypeDisplayContext);

		_jspRenderer.renderJSP(
			httpServletRequest, httpServletResponse,
			"/commerce_order_type/details.jsp");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderTypeDetailsScreenNavigationCategory.class);

	@Reference(
		target = "(model.class.name=com.liferay.commerce.model.CommerceOrderType)"
	)
	private ModelResourcePermission<CommerceOrderType>
		_commerceOrderTypeModelResourcePermission;

	@Reference
	private CommerceOrderTypeService _commerceOrderTypeService;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Portal _portal;

}
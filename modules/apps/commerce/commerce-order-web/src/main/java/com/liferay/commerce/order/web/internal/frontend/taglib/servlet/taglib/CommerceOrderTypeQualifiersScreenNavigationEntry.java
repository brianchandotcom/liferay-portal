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
import com.liferay.commerce.order.web.internal.display.context.CommerceOrderTypeQualifiersDisplayContext;
import com.liferay.commerce.service.CommerceOrderTypeRelService;
import com.liferay.commerce.service.CommerceOrderTypeService;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pedro Victor Silvestre
 */
@Component(
	property = "screen.navigation.entry.order:Integer=10",
	service = ScreenNavigationEntry.class
)
public class CommerceOrderTypeQualifiersScreenNavigationEntry
	extends CommerceOrderTypeQualifiersScreenNavigationCategory
	implements ScreenNavigationEntry<CommerceOrderType> {

	@Override
	public String getEntryKey() {
		return getCategoryKey();
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
				_log.debug(exception);
			}
		}

		return hasPermission;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		CommerceOrderTypeQualifiersDisplayContext
			commerceOrderTypeQualifiersDisplayContext =
				new CommerceOrderTypeQualifiersDisplayContext(
					httpServletRequest,
					_commerceOrderTypeModelResourcePermission,
					_commerceOrderTypeService, _commerceOrderTypeRelService,
					_portal);

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			commerceOrderTypeQualifiersDisplayContext);

		_jspRenderer.renderJSP(
			httpServletRequest, httpServletResponse,
			"/commerce_order_type/qualifiers.jsp");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderTypeQualifiersScreenNavigationEntry.class);

	@Reference(
		target = "(model.class.name=com.liferay.commerce.model.CommerceOrderType)"
	)
	private ModelResourcePermission<CommerceOrderType>
		_commerceOrderTypeModelResourcePermission;

	@Reference
	private CommerceOrderTypeRelService _commerceOrderTypeRelService;

	@Reference
	private CommerceOrderTypeService _commerceOrderTypeService;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Portal _portal;

}
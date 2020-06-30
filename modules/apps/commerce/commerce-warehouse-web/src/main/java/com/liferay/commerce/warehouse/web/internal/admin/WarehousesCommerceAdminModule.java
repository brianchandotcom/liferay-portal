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

package com.liferay.commerce.warehouse.web.internal.admin;

import com.liferay.commerce.admin.CommerceAdminModule;
import com.liferay.commerce.inventory.constants.CommerceInventoryActionKeys;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseService;
import com.liferay.commerce.product.service.CommerceChannelRelService;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.commerce.service.CommerceCountryService;
import com.liferay.commerce.warehouse.web.internal.display.context.CommerceInventoryWarehousesDisplayContext;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = "commerce.admin.module.key=" + WarehousesCommerceAdminModule.KEY,
	service = CommerceAdminModule.class
)
public class WarehousesCommerceAdminModule implements CommerceAdminModule {

	public static final String KEY = "warehouses";

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "warehouses");
	}

	@Override
	public PortletURL getSearchURL(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		CommerceInventoryWarehousesDisplayContext
			commerceInventoryWarehousesDisplayContext =
				setCommerceInventoryWarehousesDisplayContext(
					_portal.getHttpServletRequest(renderRequest));

		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter("commerceAdminModuleKey", KEY);
		portletURL.setParameter(
			"countryTwoLettersISOCode",
			String.valueOf(
				commerceInventoryWarehousesDisplayContext.
					getCommerceCountryTwoLettersIsoCode()));

		return portletURL;
	}

	@Override
	public boolean isVisible(long groupId) throws PortalException {
		return PortalPermissionUtil.contains(
			PermissionThreadLocal.getPermissionChecker(),
			CommerceInventoryActionKeys.MANAGE_INVENTORY);
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		setCommerceInventoryWarehousesDisplayContext(httpServletRequest);

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/view.jsp");
	}

	protected CommerceInventoryWarehousesDisplayContext
		setCommerceInventoryWarehousesDisplayContext(
			HttpServletRequest httpServletRequest) {

		CommerceInventoryWarehousesDisplayContext
			commerceInventoryWarehousesDisplayContext =
				(CommerceInventoryWarehousesDisplayContext)
					httpServletRequest.getAttribute(
						WebKeys.PORTLET_DISPLAY_CONTEXT);

		if (commerceInventoryWarehousesDisplayContext == null) {
			commerceInventoryWarehousesDisplayContext =
				new CommerceInventoryWarehousesDisplayContext(
					_commerceChannelRelService, _commerceChannelService,
					_commerceCountryService, _commerceInventoryWarehouseService,
					httpServletRequest);

			httpServletRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT,
				commerceInventoryWarehousesDisplayContext);
		}

		return commerceInventoryWarehousesDisplayContext;
	}

	@Reference
	private CommerceChannelRelService _commerceChannelRelService;

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private CommerceCountryService _commerceCountryService;

	@Reference
	private CommerceInventoryWarehouseService
		_commerceInventoryWarehouseService;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.warehouse.web)"
	)
	private ServletContext _servletContext;

}
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

package com.liferay.commerce.cart.taglib.servlet.taglib.internal.servlet;

import com.liferay.commerce.service.CommerceOrderItemService;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = {})
public class ServletContextUtil {

	public static CommerceOrderItemService getCommerceOrderItemService() {
		return _servletContextUtil._getCommerceOrderItemService();
	}

	public static ServletContext getServletContext() {
		return _servletContextUtil._getServletContext();
	}

	@Activate
	protected void activate() {
		_servletContextUtil = this;
	}

	@Deactivate
	protected void deactivate() {
		_servletContextUtil = null;
	}

	@Reference(unbind = "-")
	protected void setCommerceOrderItemService(
		CommerceOrderItemService commerceOrderItemService) {

		_commerceOrderItemService = commerceOrderItemService;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.cart.taglib)",
		unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private CommerceOrderItemService _getCommerceOrderItemService() {
		return _commerceOrderItemService;
	}

	private ServletContext _getServletContext() {
		return _servletContext;
	}

	private static ServletContextUtil _servletContextUtil;

	private CommerceOrderItemService _commerceOrderItemService;
	private ServletContext _servletContext;

}
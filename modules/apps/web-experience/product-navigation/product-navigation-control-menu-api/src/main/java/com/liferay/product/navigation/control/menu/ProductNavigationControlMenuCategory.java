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

package com.liferay.product.navigation.control.menu;

import com.liferay.portal.kernel.exception.PortalException;

import javax.servlet.http.HttpServletRequest;

/**
 * Provides an interface defining categories that will be used by a specific
 * <code>product-navigation:control-menu</code> tag instance to render a new
 * category.
 * Control menu categories includes control menu entries, defined by {@link
 * ProductNavigationControlMenuEntry} implementations.
 *
 * <p>
 * Implementations must be registered in the OSGi Registry. The order of the
 * control menu categories is determined by the
 * <code>product.navigation.control.menu.category.order</code> property value.
 * The parent control menu category key can be defined by the
 * <code>product.navigation.control.menu.category.key</code> property value.
 * </p>
 *
 * @author Julio Camarero
 */
public interface ProductNavigationControlMenuCategory {

	/**
	 * Returns the key for the control menu category. This key needs to be
	 * unique in the scope of a control menu.
	 *
	 * <p>
	 * This key will be referred by the control menu entries to be added to
	 * this control menu category.
	 * </p>
	 *
	 * @return the key of the control menu category
	 */
	public String getKey();

	/**
	 * Returns <code>true</code> if the control menu category should be
	 * displayed in the context of specific request.
	 *
	 * @param  request the request with which the control menu category is
	 *         rendered
	 * @return <code>true</code> if the control menu category should be
	 *         displayed in current request context; <code>false</code>
	 *         otherwise
	 */
	public boolean hasAccessPermission(HttpServletRequest request)
		throws PortalException;

}
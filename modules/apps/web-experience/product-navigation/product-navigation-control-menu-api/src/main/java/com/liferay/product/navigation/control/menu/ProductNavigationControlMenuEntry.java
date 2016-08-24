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

import java.io.IOException;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Provides an interface defining entries that will be used by a specific
 * <code>product-navigation:control-menu</code> tag instance to render a new
 * control menu entry.
 * Control menu entries are included within control menu categories, defined
 * by {@link ProductNavigationControlMenuCategory} implementations.
 *
 * <p>
 * Implementations must be registered in the OSGi Registry. The order of the
 * control menu entries inside a category is determined by the
 * <code>product.navigation.control.menu.entry.order</code> property value and
 * the control menu category used to display that entry is determined by the
 * <code>product.navigation.control.menu.category.key</code> property value.
 * </p>
 *
 * @author Julio Camarero
 */
public interface ProductNavigationControlMenuEntry {

	/**
	 * Returns the data which should be used as the data attribute of
	 * <code>liferay-ui:icon</code> tag instance for the control menu entry.
	 *
	 * @param  request the request with which the control menu entry is
	 *         rendered
	 * @return the data attribute of <code>liferay-ui:icon</code> tag instance
	 *         for the control menu entry
	 */
	public Map<String, Object> getData(HttpServletRequest request);

	/**
	 * Returns the icon name which should be used as the icon attribute of
	 * <code>liferay-ui:icon</code> tag instance for the control menu entry.
	 *
	 * @param  request the request with which the control menu entry is
	 *         rendered
	 * @return the icon attribute of <code>liferay-ui:icon</code> tag instance
	 * 		   for the control menu entry
	 */
	public String getIcon(HttpServletRequest request);

	/**
	 * Returns the icon CSS class which should be used as the iconCssClass
	 * attribute of <code>liferay-ui:icon</code> tag instance for the control
	 * menu entry.
	 *
	 * @param  request the request with which the control menu entry is
	 *         rendered
	 * @return the iconCssClass attribute of <code>liferay-ui:icon</code> tag
	 *         instance for the control menu entry
	 */
	public String getIconCssClass(HttpServletRequest request);

	/**
	 * Returns the key for the control menu entry. This key needs to be
	 * unique in the scope of an control menu entry selector.
	 *
	 * @return the key of the control menu entry
	 */
	public String getKey();

	/**
	 * Defines the label that will be displayed in the user interface when the
	 * control menu entry is included in the tag instance.
	 *
	 * @param  locale the locale that the label should be retrieved for
	 * @return the label of the control menu entry
	 */
	public String getLabel(Locale locale);

	/**
	 * Returns the link CSS class which should be injected as the linkCssClass
	 * attribute of <code>liferay-ui:icon</code> tag instance for the control
	 * menu entry.
	 *
	 * @param  request the request with which the control menu entry is
	 *         rendered
	 * @return the linkCssClass attribute of <code>liferay-ui:icon</code> tag
	 *         instance for the control menu entry
	 */
	public String getLinkCssClass(HttpServletRequest request);

	/**
	 * Returns the markup view string which should be injected as the markupView
	 * attribute of <code>liferay-ui:icon</code> tag instance for the control
	 * menu entry.
	 *
	 * @param  request the request with which the control menu entry is
	 *         rendered
	 * @return the markupView attribute of <code>liferay-ui:icon</code> tag
	 *         instance for the control menu entry
	 */
	public String getMarkupView(HttpServletRequest request);

	/**
	 * Returns the URL which should be injected as the url attribute of
	 * <code>liferay-ui:icon</code> tag instance for the control menu entry.
	 *
	 * @param  request the request with which the control menu entry is
	 *         rendered
	 * @return the url attribute of <code>liferay-ui:icon</code> tag instance
	 *         for the control menu entry
	 */
	public String getURL(HttpServletRequest request);

	/**
	 * Renders the HTML that needs to be displayed when the control menu entry
	 * body is displayed.
	 *
	 * @param  request the request with which the control menu entry body is
	 *         rendered
	 * @param  response the response with which the control menu entry body is
	 *         rendered
	 * @throws IOException if an IO exception occurs
	 */
	public boolean includeBody(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException;

	/**
	 * Renders the HTML that needs to be displayed when the control menu entry
	 * icon is displayed.
	 *
	 * @param  request the request with which the control menu entry icon is
	 *         rendered
	 * @param  response the response with which the control menu entry icon is
	 *         rendered
	 * @throws IOException if an IO exception occurs
	 */
	public boolean includeIcon(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException;

	/**
	 * Returns <code>true</code> if the control menu entry should be
	 * displayed in the context of specific request.
	 *
	 * @param  request the request with which the control menu entry is
	 *         rendered
	 * @return <code>true</code> if the control menu entry should be
	 *         displayed in current request context; <code>false</code>
	 *         otherwise
	 */
	public boolean isShow(HttpServletRequest request) throws PortalException;

	/**
	 * Returns <code>true</code> if the control menu entry should be opened
	 * in a dialog window or in the current window.
	 *
	 * @return <code>true</code> if the control menu entry should be opened in a
	 *         dialog window; <code>false</code> otherwise
	 */
	public boolean isUseDialog();

}
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

package com.liferay.shopping.web.util;

import com.liferay.shopping.configuration.ShoppingGroupServiceConfiguration;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Peter Fellwock
 */
@Component(immediate = true)
public class ShoppingWebComponentProvider {

	public static ShoppingWebComponentProvider
		getShoppingWebComponentProvider() {

		return _shoppingWebComponentProvider;
	}

	public ShoppingGroupServiceConfiguration
		getShoppingGroupServiceConfiguration() {

		return _shoppingGroupServiceConfiguration;
	}

	@Activate
	protected void activate() {
		_shoppingWebComponentProvider = this;
	}

	@Deactivate
	protected void deactivate() {
		_shoppingWebComponentProvider = null;
	}

	@Reference
	protected void setShoppingGroupServiceConfiguration(
		ShoppingGroupServiceConfiguration shoppingGroupServiceConfiguration) {

		_shoppingGroupServiceConfiguration = shoppingGroupServiceConfiguration;
	}

	protected void unsetShoppingGroupServiceConfiguration(
		ShoppingGroupServiceConfiguration shoppingGroupServiceConfiguration) {

		_shoppingGroupServiceConfiguration = null;
	}

	private static ShoppingWebComponentProvider _shoppingWebComponentProvider;

	private ShoppingGroupServiceConfiguration
		_shoppingGroupServiceConfiguration;

}
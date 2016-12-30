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

package com.liferay.portal.security.sso.openid.connect;

import java.net.URL;

import java.util.Collection;

/**
 * @author Thuong Dinh
 */
public interface OpenIdConnectProviderRegistry {

	public static final String OPEN_ID_CONNECT_PROVIDER_NAME_DEFAULT = "google";

	public OpenIdConnectProvider getOpenIdConnectProvider(String name);

	public OpenIdConnectProvider getOpenIdConnectProvider(URL issuerUrl);

	public Collection<String> getOpenIdConnectProviderNames();

}
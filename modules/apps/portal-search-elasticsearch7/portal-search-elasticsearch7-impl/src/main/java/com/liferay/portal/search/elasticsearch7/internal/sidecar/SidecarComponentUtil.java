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

package com.liferay.portal.search.elasticsearch7.internal.sidecar;

import com.liferay.portal.search.elasticsearch7.internal.connection.SidecarElasticsearchConnectionManager;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Tina Tian
 */
@Component(immediate = true, service = {})
public class SidecarComponentUtil {

	public static void disableSidecarElasticsearchConnectionManager() {
		_componentContext.disableComponent(
			SidecarElasticsearchConnectionManager.class.getName());
	}

	public static void enableSidecarElasticsearchConnectionManager() {
		_componentContext.enableComponent(
			SidecarElasticsearchConnectionManager.class.getName());
	}

	@Activate
	protected void activate(ComponentContext componentContext) {
		_componentContext = componentContext;
	}

	private static ComponentContext _componentContext;

}
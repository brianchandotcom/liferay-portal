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

package com.liferay.server.manager.internal.servlet.filter;

import com.liferay.portal.kernel.servlet.PortalClassLoaderFilter;

import javax.servlet.Filter;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true,
	property = {
		"filter.init.basic_auth=true",
		"filter.init.filter-class=com.liferay.portal.servlet.filters.secure.SecureFilter",
		"filter.init.portal_property_prefix=server.manager.servlet.",
		"osgi.http.whiteboard.filter.name=com.liferay.server.manager.internal.servlet.filter.ServerManagerFilter",
		"osgi.http.whiteboard.filter.pattern=/server-manager/*"
	},
	service = Filter.class
)
public class ServerManagerFilter extends PortalClassLoaderFilter {
}
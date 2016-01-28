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

package com.liferay.portal.events;

import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.servlet.filters.init.InitFilter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

/**
 * @author Matthew Tambara
 */
public class InitStartupAction extends SimpleAction {

	@Override
	public void run(String[] ids) {
		Map<String, Object> properties = new HashMap<>();

		properties.put("dispatcher", new String[] {"FORWARD", "REQUEST"});
		properties.put("servlet-context-name", "");
		properties.put("servlet-filter-name", "Init Filter");
		properties.put("url-pattern", "/c/*");

		Registry registry = RegistryUtil.getRegistry();

		InitFilter initFilter = new InitFilter();

		ServiceRegistration<InitFilter> serviceRegistration =
			registry.registerService(
				Filter.class.getName(), initFilter, properties);

		initFilter.setServiceRegistration(serviceRegistration);
	}

}
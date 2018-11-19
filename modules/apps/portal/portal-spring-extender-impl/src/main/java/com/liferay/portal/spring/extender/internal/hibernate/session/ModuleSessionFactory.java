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

package com.liferay.portal.spring.extender.internal.hibernate.session;

import com.liferay.portal.dao.orm.hibernate.PortletSessionFactoryImpl;
import com.liferay.portal.spring.extender.internal.context.ModuleApplicationContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.wiring.BundleWiring;

import org.springframework.context.ApplicationContext;

/**
 * @author Miguel Pastor
 */
public class ModuleSessionFactory extends PortletSessionFactoryImpl {

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		ModuleApplicationContext moduleApplicationContext =
			(ModuleApplicationContext)applicationContext;

		BundleContext bundleContext =
			moduleApplicationContext.getBundleContext();

		Bundle bundle = bundleContext.getBundle();

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		ClassLoader classLoader = bundleWiring.getClassLoader();

		setSessionFactoryClassLoader(classLoader);
	}

}
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

package com.liferay.shopping.service.configuration.configurator;

import com.liferay.portal.service.configuration.ServiceComponentConfiguration;
import com.liferay.portal.service.configuration.configurator.ServiceConfigurator;
import com.liferay.portal.spring.extender.loader.ModuleResourceLoader;
import com.liferay.shopping.upgrade.ShoppingServiceUpgrade;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Peter Fellwock
 */
@Component(immediate = true, service = ShoppingServiceConfigurator.class)
public class ShoppingServiceConfigurator {

	@Activate
	protected void activate() throws Exception {
		_serviceConfigurator.initServices(getConfiguration(), getClassLoader());
	}

	@Deactivate
	protected void deactivate() throws Exception {
		_serviceConfigurator.destroyServices(
			getConfiguration(), getClassLoader());
	}

	protected ClassLoader getClassLoader() {
		Class<? extends ShoppingServiceConfigurator> clazz = getClass();

		return clazz.getClassLoader();
	}

	protected ServiceComponentConfiguration getConfiguration() {
		Bundle bundle = FrameworkUtil.getBundle(getClass());

		return new ModuleResourceLoader(bundle);
	}

	@Reference(unbind = "-")
	protected void setBookmarksServicesUpgrade(
		ShoppingServiceUpgrade shoppingServiceUpgrade) {
	}

	@Reference(unbind = "-")
	protected void setServiceConfigurator(
		ServiceConfigurator serviceConfigurator) {

		_serviceConfigurator = serviceConfigurator;
	}

	private ServiceConfigurator _serviceConfigurator;

}
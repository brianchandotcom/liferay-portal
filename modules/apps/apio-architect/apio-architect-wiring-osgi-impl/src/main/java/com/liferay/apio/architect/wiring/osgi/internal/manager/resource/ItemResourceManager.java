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

package com.liferay.apio.architect.wiring.osgi.internal.manager.resource;

import static com.liferay.apio.architect.wiring.osgi.internal.manager.TypeArgumentProperties.KEY_IDENTIFIER_CLASS;
import static com.liferay.apio.architect.wiring.osgi.internal.manager.util.ManagerUtil.createServiceTracker;
import static com.liferay.apio.architect.wiring.osgi.internal.manager.util.ManagerUtil.getTypeParamOrFail;

import com.liferay.apio.architect.representor.Representable;
import com.liferay.apio.architect.resource.ItemResource;
import com.liferay.apio.architect.router.ItemRouter;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Registers resources as {@link ItemResource}, instead of implementing a
 * register for each of the enclosing interfaces separately.
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class ItemResourceManager {

	@Activate
	protected void activate(BundleContext bundleContext) {
		String[] classes =
			{ItemRouter.class.getName(), Representable.class.getName()};

		_serviceTracker = createServiceTracker(
			bundleContext, ItemResource.class, classes,
			(properties, service) -> {
				Class<?> identifierClass = getTypeParamOrFail(
					service, ItemResource.class, 2);

				properties.put(KEY_IDENTIFIER_CLASS, identifierClass);
			});

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private ServiceTracker<ItemResource, ServiceRegistration<?>>
		_serviceTracker;

}
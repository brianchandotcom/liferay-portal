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

package com.liferay.segments.internal.context;

import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.segments.internal.odata.entity.ContextEntityModel;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Component;

/**
 * @author Eduardo García
 */
@Component(immediate = true, service = ContextRegistrar.class)
public class ContextRegistrar {

	public void register(
		BundleContext bundleContext, ContextEntityModel contextEntityModel) {

		_serviceRegistration = bundleContext.registerService(
			EntityModel.class, contextEntityModel,
			MapUtil.singletonDictionary(
				"entity.model.name", ContextEntityModel.NAME));
	}

	public void unregister() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();

			_serviceRegistration = null;
		}
	}

	private ServiceRegistration<EntityModel> _serviceRegistration;

}
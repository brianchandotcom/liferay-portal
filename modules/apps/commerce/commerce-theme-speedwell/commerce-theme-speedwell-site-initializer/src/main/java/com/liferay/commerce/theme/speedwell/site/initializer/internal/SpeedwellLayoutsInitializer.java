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

package com.liferay.commerce.theme.speedwell.site.initializer.internal;

import com.liferay.commerce.product.importer.CPFileImporter;
import com.liferay.commerce.theme.speedwell.site.initializer.internal.dependencies.resolver.SpeedwellDependencyResolver;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.service.ServiceContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Gianmarco Brunialti Masera
 */
@Component(service = SpeedwellLayoutsInitializer.class)
public class SpeedwellLayoutsInitializer {

	public void initialize(ServiceContext serviceContext) throws Exception {
		_cpFileImporter.cleanLayouts(serviceContext);

		_createLayouts(serviceContext);
	}

	private void _createLayouts(ServiceContext serviceContext)
		throws Exception {

		String json = _speedwellDependencyResolver.getJSON("layouts.json");

		JSONArray jsonArray = _jsonFactory.createJSONArray(json);

		_cpFileImporter.createLayouts(
			jsonArray, _speedwellDependencyResolver.getImageClassLoader(),
			_speedwellDependencyResolver.getImageDependencyPath(),
			serviceContext);
	}

	@Reference
	private CPFileImporter _cpFileImporter;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private SpeedwellDependencyResolver _speedwellDependencyResolver;

}
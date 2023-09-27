/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		JSONArray jsonArray = _jsonFactory.createJSONArray(
			_speedwellDependencyResolver.getJSON("layouts.json"));

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
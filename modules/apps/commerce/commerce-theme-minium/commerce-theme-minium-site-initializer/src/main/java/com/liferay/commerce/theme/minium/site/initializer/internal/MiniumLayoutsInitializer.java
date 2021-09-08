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

package com.liferay.commerce.theme.minium.site.initializer.internal;

import com.liferay.commerce.product.importer.CPFileImporter;
import com.liferay.commerce.theme.minium.SiteInitializerDependencyResolver;
import com.liferay.commerce.theme.minium.SiteInitializerDependencyResolverThreadLocal;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.service.ServiceContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(enabled = false, service = MiniumLayoutsInitializer.class)
public class MiniumLayoutsInitializer {

	public void initialize(ServiceContext serviceContext) throws Exception {
		SiteInitializerDependencyResolver siteInitializerDependencyResolver =
			SiteInitializerDependencyResolverThreadLocal.
				getSiteInitializerDependencyResolver();

		if (siteInitializerDependencyResolver != null) {
			_siteInitializerDependencyResolver =
				siteInitializerDependencyResolver;
		}
		else {
			_siteInitializerDependencyResolver =
				_defaultSiteInitializerDependencyResolver;
		}

		_cpFileImporter.cleanLayouts(serviceContext);

		_createLayouts(serviceContext);
	}

	private void _createLayouts(ServiceContext serviceContext)
		throws Exception {

		String json = _siteInitializerDependencyResolver.getJSON(
			"layouts.json");

		JSONArray jsonArray = _jsonFactory.createJSONArray(json);

		_cpFileImporter.createLayouts(
			jsonArray, _siteInitializerDependencyResolver.getImageClassLoader(),
			_siteInitializerDependencyResolver.getImageDependencyPath(),
			serviceContext);
	}

	@Reference
	private CPFileImporter _cpFileImporter;

	@Reference(
		target = "(site.initializer.key=" + MiniumSiteInitializer.KEY + ")"
	)
	private SiteInitializerDependencyResolver
		_defaultSiteInitializerDependencyResolver;

	@Reference
	private JSONFactory _jsonFactory;

	private SiteInitializerDependencyResolver
		_siteInitializerDependencyResolver;

}
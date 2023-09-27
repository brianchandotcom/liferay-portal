/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.theme.minium.site.initializer.internal;

import com.liferay.commerce.product.importer.CPFileImporter;
import com.liferay.commerce.theme.minium.SiteInitializerDependencyResolver;
import com.liferay.commerce.theme.minium.SiteInitializerDependencyResolverThreadLocal;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.service.ServiceContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(service = MiniumLayoutsInitializer.class)
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

		_cpFileImporter.createLayouts(
			_jsonFactory.createJSONArray(
				_siteInitializerDependencyResolver.getJSON("layouts.json")),
			_siteInitializerDependencyResolver.getImageClassLoader(),
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
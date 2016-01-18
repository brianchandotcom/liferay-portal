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

package com.liferay.portal.search.internal;

import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.model.Company;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Michael C. Han
 */
@Component(immediate = true)
public class CompanySearchEngineInitializer {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTracker = new ServiceTracker<>(
			bundleContext, Company.class,
			new CompanyServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_bundleContext = null;

		if (_serviceTracker != null) {
			_serviceTracker.close();
		}
	}

	@Reference(unbind = "-")
	protected void setSearchEngineHelper(
		SearchEngineHelper searchEngineHelper) {

		_searchEngineHelper = searchEngineHelper;
	}

	private BundleContext _bundleContext;
	private SearchEngineHelper _searchEngineHelper;
	private ServiceTracker<Company, Company> _serviceTracker;

	private class CompanyServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<Company, Company> {

		@Override
		public Company addingService(
			ServiceReference<Company> serviceReference) {

			Company company = _bundleContext.getService(serviceReference);

			_searchEngineHelper.initialize(company.getCompanyId());

			return company;
		}

		@Override
		public void modifiedService(
			ServiceReference<Company> reference, Company company) {
		}

		@Override
		public void removedService(
			ServiceReference<Company> reference, Company service) {
		}

	}

}
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

package com.liferay.portal.search.elasticsearch7.internal;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.service.CompanyLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = ElasticsearchEngineConfigurator.class)
public class ElasticsearchEngineConfigurator {

	public void configure(SearchEngine searchEngine) {
		for (Company company : _companyLocalService.getCompanies()) {
			searchEngine.initialize(company.getCompanyId());
		}

		searchEngine.initialize(CompanyConstants.SYSTEM);
	}

	public void unconfigure() {
	}

	@Reference
	private CompanyLocalService _companyLocalService;

}
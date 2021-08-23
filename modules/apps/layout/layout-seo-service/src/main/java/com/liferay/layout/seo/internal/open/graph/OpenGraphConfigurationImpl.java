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

package com.liferay.layout.seo.internal.open.graph;

import com.liferay.layout.seo.internal.configuration.LayoutSEOCompanyConfiguration;
import com.liferay.layout.seo.internal.configuration.LayoutSEOGroupConfiguration;
import com.liferay.layout.seo.model.LayoutSEOSite;
import com.liferay.layout.seo.open.graph.OpenGraphConfiguration;
import com.liferay.layout.seo.service.LayoutSEOSiteLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.CompanyLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = OpenGraphConfiguration.class)
public class OpenGraphConfigurationImpl implements OpenGraphConfiguration {

	@Override
	public boolean isLayoutTranslatedLanguagesEnabled(Company company)
		throws PortalException {

		LayoutSEOCompanyConfiguration layoutSEOCompanyConfiguration =
			_configurationProvider.getCompanyConfiguration(
				LayoutSEOCompanyConfiguration.class, company.getCompanyId());

		if (!layoutSEOCompanyConfiguration.enableOpenGraph()) {
			return false;
		}

		return layoutSEOCompanyConfiguration.enableLayoutTranslatedLanguages();
	}

	@Override
	public boolean isLayoutTranslatedLanguagesEnabled(Group group)
		throws PortalException {

		if (!isOpenGraphEnabled(group)) {
			return false;
		}

		if (isLayoutTranslatedLanguagesEnabled(
				_companyLocalService.getCompany(group.getCompanyId()))) {

			return true;
		}

		LayoutSEOGroupConfiguration layoutSEOGroupConfiguration =
			_configurationProvider.getGroupConfiguration(
				LayoutSEOGroupConfiguration.class, group.getGroupId());

		return layoutSEOGroupConfiguration.enableLayoutTranslatedLanguages();
	}

	@Override
	public boolean isOpenGraphEnabled(Company company) throws PortalException {
		LayoutSEOCompanyConfiguration layoutSEOCompanyConfiguration =
			_configurationProvider.getCompanyConfiguration(
				LayoutSEOCompanyConfiguration.class, company.getCompanyId());

		return layoutSEOCompanyConfiguration.enableOpenGraph();
	}

	@Override
	public boolean isOpenGraphEnabled(Group group) throws PortalException {
		if (!isOpenGraphEnabled(
				_companyLocalService.getCompany(group.getCompanyId()))) {

			return false;
		}

		LayoutSEOSite layoutSEOSite =
			_layoutSEOSiteLocalService.fetchLayoutSEOSiteByGroupId(
				group.getGroupId());

		if ((layoutSEOSite == null) || layoutSEOSite.isOpenGraphEnabled()) {
			return true;
		}

		return false;
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private LayoutSEOSiteLocalService _layoutSEOSiteLocalService;

}
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

package com.liferay.portal.properties.swapper.internal;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;

import java.io.InputStream;

import java.net.URL;

import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(enabled = false, immediate = true)
public class DefaultGuestGroupLogoSwapper {

	@Activate
	public void activate(BundleContext bundleContext) throws Exception {
		List<Company> companies = _companyLocalService.getCompanies(0, 1);

		Company company = companies.get(0);

		Group group = _groupLocalService.getGroup(
			company.getCompanyId(), GroupConstants.GUEST);

		LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
			group.getGroupId(), false);

		if (layoutSet.getLogoId() != 0) {
			return;
		}

		Bundle bundle = bundleContext.getBundle();

		URL url = bundle.getResource(
			"com/liferay/portal/properties/swapper/internal" +
				"/default_guest_group_logo.png");

		try (InputStream inputStream = url.openStream()) {
			_layoutSetLocalService.updateLogo(
				group.getGroupId(), false, true, inputStream);
		}
	}

	@Reference(unbind = "-")
	public void setCompanyLocalService(
		CompanyLocalService companyLocalService) {

		_companyLocalService = companyLocalService;
	}

	@Reference(unbind = "-")
	public void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	public void setLayoutSetLocalService(
		LayoutSetLocalService layoutSetLocalService) {

		_layoutSetLocalService = layoutSetLocalService;
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	private CompanyLocalService _companyLocalService;
	private GroupLocalService _groupLocalService;
	private LayoutSetLocalService _layoutSetLocalService;

}
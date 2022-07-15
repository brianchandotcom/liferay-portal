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

package com.liferay.address.internal.portal.instance.lifecycle;

import com.liferay.address.internal.osgi.commands.PortalAddressOSGiCommands;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CountryLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class PortalInstanceLifecycleListenerImpl
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstancePreunregistered(Company company)
		throws Exception {

		_countryLocalService.deleteCompanyCountries(company.getCompanyId());
	}

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		_portalAddressOSGiCommands.populateCompanyCountries(
			company.getCompanyId());
	}

	@Override
	public void portalInstanceUnregistered(Company company) throws Exception {
		super.portalInstanceUnregistered(company);
	}

	@Reference
	private CountryLocalService _countryLocalService;

	@Reference
	private PortalAddressOSGiCommands _portalAddressOSGiCommands;

}
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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.VirtualHost;
import com.liferay.portal.service.base.VirtualHostLocalServiceBaseImpl;

/**
 * @author Alexander Chow
 */
public class VirtualHostLocalServiceImpl
	extends VirtualHostLocalServiceBaseImpl {

	@Override
	public VirtualHost fetchVirtualHost(long companyId, long layoutSetId) {
		return virtualHostPersistence.fetchByC_L(companyId, layoutSetId);
	}

	@Override
	public VirtualHost fetchVirtualHost(String hostname) {
		return virtualHostPersistence.fetchByHostname(hostname);
	}

	@Override
	public VirtualHost getVirtualHost(long companyId, long layoutSetId)
		throws PortalException {

		return virtualHostPersistence.findByC_L(companyId, layoutSetId);
	}

	@Override
	public VirtualHost getVirtualHost(String hostname) throws PortalException {
		return virtualHostPersistence.findByHostname(hostname);
	}

	@Override
	public VirtualHost updateVirtualHost(
		long companyId, long layoutSetId, String hostname) {

		VirtualHost virtualHost = virtualHostPersistence.fetchByC_L(
			companyId, layoutSetId);

		if (virtualHost == null) {
			long virtualHostId = counterLocalService.increment();

			virtualHost = virtualHostPersistence.create(virtualHostId);

			virtualHost.setCompanyId(companyId);
			virtualHost.setLayoutSetId(layoutSetId);
		}

		virtualHost.setHostname(hostname);

		virtualHostPersistence.update(virtualHost);

		Company company = companyPersistence.fetchByPrimaryKey(companyId);

		if (company != null) {
			companyPersistence.clearCache(company);
		}

		LayoutSet layoutSet = layoutSetPersistence.fetchByPrimaryKey(
			layoutSetId);

		if (layoutSet != null) {
			layoutSetPersistence.clearCache(layoutSet);
		}

		return virtualHost;
	}

}
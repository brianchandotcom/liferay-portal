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

package com.liferay.portal.workflow.kaleo.service.wrapper;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.CompanyLocalServiceWrapper;
import com.liferay.portal.service.ServiceWrapper;
import com.liferay.portal.workflow.kaleo.manager.PortalKaleoManager;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class KaleoCompanyLocalServiceWrapper
	extends CompanyLocalServiceWrapper {

	public KaleoCompanyLocalServiceWrapper() {
		super(null);
	}

	public KaleoCompanyLocalServiceWrapper(
		CompanyLocalService companyLocalService) {

		super(companyLocalService);
	}

	@Override
	public Company checkCompany(String webId, String mx)
		throws PortalException {

		Company company = super.checkCompany(webId, mx);

		try {
			_portalKaleoManager.deployKaleoDefaults(company.getCompanyId());
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}

		return company;
	}

	@Reference(unbind = "-")
	protected void setPortalKaleoManager(
		PortalKaleoManager portalKaleoManager) {

		_portalKaleoManager = portalKaleoManager;
	}

	private volatile PortalKaleoManager _portalKaleoManager;

}
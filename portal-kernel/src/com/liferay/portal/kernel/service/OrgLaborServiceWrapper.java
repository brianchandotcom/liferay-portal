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

package com.liferay.portal.kernel.service;

/**
 * Provides a wrapper for {@link OrgLaborService}.
 *
 * @author Brian Wing Shun Chan
 * @see OrgLaborService
 * @generated
 */
public class OrgLaborServiceWrapper
	implements OrgLaborService, ServiceWrapper<OrgLaborService> {

	public OrgLaborServiceWrapper(OrgLaborService orgLaborService) {
		_orgLaborService = orgLaborService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link OrgLaborServiceUtil} to access the org labor remote service. Add custom service methods to <code>com.liferay.portal.service.impl.OrgLaborServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.liferay.portal.kernel.model.OrgLabor addOrgLabor(
			long organizationId, long typeId, int sunOpen, int sunClose,
			int monOpen, int monClose, int tueOpen, int tueClose, int wedOpen,
			int wedClose, int thuOpen, int thuClose, int friOpen, int friClose,
			int satOpen, int satClose)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _orgLaborService.addOrgLabor(
			organizationId, typeId, sunOpen, sunClose, monOpen, monClose,
			tueOpen, tueClose, wedOpen, wedClose, thuOpen, thuClose, friOpen,
			friClose, satOpen, satClose);
	}

	@Override
	public void deleteOrgLabor(long orgLaborId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_orgLaborService.deleteOrgLabor(orgLaborId);
	}

	@Override
	public com.liferay.portal.kernel.model.OrgLabor getOrgLabor(long orgLaborId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _orgLaborService.getOrgLabor(orgLaborId);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.OrgLabor>
			getOrgLabors(long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _orgLaborService.getOrgLabors(organizationId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _orgLaborService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.OrgLabor updateOrgLabor(
			long orgLaborId, long typeId, int sunOpen, int sunClose,
			int monOpen, int monClose, int tueOpen, int tueClose, int wedOpen,
			int wedClose, int thuOpen, int thuClose, int friOpen, int friClose,
			int satOpen, int satClose)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _orgLaborService.updateOrgLabor(
			orgLaborId, typeId, sunOpen, sunClose, monOpen, monClose, tueOpen,
			tueClose, wedOpen, wedClose, thuOpen, thuClose, friOpen, friClose,
			satOpen, satClose);
	}

	@Override
	public OrgLaborService getWrappedService() {
		return _orgLaborService;
	}

	@Override
	public void setWrappedService(OrgLaborService orgLaborService) {
		_orgLaborService = orgLaborService;
	}

	private OrgLaborService _orgLaborService;

}
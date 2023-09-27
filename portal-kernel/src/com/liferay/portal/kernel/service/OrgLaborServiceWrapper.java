/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public OrgLaborServiceWrapper() {
		this(null);
	}

	public OrgLaborServiceWrapper(OrgLaborService orgLaborService) {
		_orgLaborService = orgLaborService;
	}

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
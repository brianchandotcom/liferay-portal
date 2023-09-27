/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service;

/**
 * Provides a wrapper for {@link PortalService}.
 *
 * @author Brian Wing Shun Chan
 * @see PortalService
 * @generated
 */
public class PortalServiceWrapper
	implements PortalService, ServiceWrapper<PortalService> {

	public PortalServiceWrapper() {
		this(null);
	}

	public PortalServiceWrapper(PortalService portalService) {
		_portalService = portalService;
	}

	@Override
	public java.lang.String getAutoDeployDirectory() {
		return _portalService.getAutoDeployDirectory();
	}

	@Override
	public int getBuildNumber() {
		return _portalService.getBuildNumber();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _portalService.getOSGiServiceIdentifier();
	}

	@Override
	public java.lang.String getVersion() {
		return _portalService.getVersion();
	}

	@Override
	public void testAddClassName_Rollback(java.lang.String classNameValue) {
		_portalService.testAddClassName_Rollback(classNameValue);
	}

	@Override
	public void testAddClassName_Success(java.lang.String classNameValue) {
		_portalService.testAddClassName_Success(classNameValue);
	}

	@Override
	public void testAutoSyncHibernateSessionStateOnTxCreation() {
		_portalService.testAutoSyncHibernateSessionStateOnTxCreation();
	}

	@Override
	public void testDeleteClassName()
		throws com.liferay.portal.kernel.exception.PortalException {

		_portalService.testDeleteClassName();
	}

	@Override
	public int testGetBuildNumber() {
		return _portalService.testGetBuildNumber();
	}

	@Override
	public void testGetUserId() {
		_portalService.testGetUserId();
	}

	@Override
	public boolean testHasClassName() {
		return _portalService.testHasClassName();
	}

	@Override
	public PortalService getWrappedService() {
		return _portalService;
	}

	@Override
	public void setWrappedService(PortalService portalService) {
		_portalService = portalService;
	}

	private PortalService _portalService;

}
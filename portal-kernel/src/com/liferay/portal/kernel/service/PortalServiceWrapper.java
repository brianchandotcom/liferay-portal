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
 * Provides a wrapper for {@link PortalService}.
 *
 * @author Brian Wing Shun Chan
 * @see PortalService
 * @generated
 */
public class PortalServiceWrapper
	implements PortalService, ServiceWrapper<PortalService> {

	public PortalServiceWrapper(PortalService portalService) {
		_portalService = portalService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link PortalServiceUtil} to access the portal remote service. Add custom service methods to <code>com.liferay.portal.service.impl.PortalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
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
	public void testAddClassNameAndTestTransactionPortletBar_PortalRollback(
		java.lang.String transactionPortletBarText) {

		_portalService.
			testAddClassNameAndTestTransactionPortletBar_PortalRollback(
				transactionPortletBarText);
	}

	@Override
	public void testAddClassNameAndTestTransactionPortletBar_PortletRollback(
		java.lang.String transactionPortletBarText) {

		_portalService.
			testAddClassNameAndTestTransactionPortletBar_PortletRollback(
				transactionPortletBarText);
	}

	@Override
	public void testAddClassNameAndTestTransactionPortletBar_Success(
		java.lang.String transactionPortletBarText) {

		_portalService.testAddClassNameAndTestTransactionPortletBar_Success(
			transactionPortletBarText);
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
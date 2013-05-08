/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

/**
 * Provides a wrapper for {@link PortalService}.
 *
 * @author    Brian Wing Shun Chan
 * @see       PortalService
 * @generated
 */
public class PortalServiceWrapper implements PortalService,
	ServiceWrapper<PortalService> {
	public PortalServiceWrapper(PortalService portalService) {
		_portalService = portalService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _portalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_portalService.setBeanIdentifier(beanIdentifier);
	}

	public java.lang.String getAutoDeployDirectory()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portalService.getAutoDeployDirectory();
	}

	public int getBuildNumber() {
		return _portalService.getBuildNumber();
	}

	public void testAddClassName_Rollback(java.lang.String classNameValue)
		throws com.liferay.portal.kernel.exception.SystemException {
		_portalService.testAddClassName_Rollback(classNameValue);
	}

	public void testAddClassName_Success(java.lang.String classNameValue)
		throws com.liferay.portal.kernel.exception.SystemException {
		_portalService.testAddClassName_Success(classNameValue);
	}

	public void testAddClassNameAndTestTransactionPortletBar_PortalRollback(
		java.lang.String transactionPortletBarText)
		throws com.liferay.portal.kernel.exception.SystemException {
		_portalService.testAddClassNameAndTestTransactionPortletBar_PortalRollback(transactionPortletBarText);
	}

	public void testAddClassNameAndTestTransactionPortletBar_PortletRollback(
		java.lang.String transactionPortletBarText)
		throws com.liferay.portal.kernel.exception.SystemException {
		_portalService.testAddClassNameAndTestTransactionPortletBar_PortletRollback(transactionPortletBarText);
	}

	public void testAddClassNameAndTestTransactionPortletBar_Success(
		java.lang.String transactionPortletBarText)
		throws com.liferay.portal.kernel.exception.SystemException {
		_portalService.testAddClassNameAndTestTransactionPortletBar_Success(transactionPortletBarText);
	}

	public void testAutoSyncHibernateSessionStateOnTxCreation()
		throws com.liferay.portal.kernel.exception.SystemException {
		_portalService.testAutoSyncHibernateSessionStateOnTxCreation();
	}

	public void testCounterIncrement_Rollback()
		throws com.liferay.portal.kernel.exception.SystemException {
		_portalService.testCounterIncrement_Rollback();
	}

	public void testDeleteClassName()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_portalService.testDeleteClassName();
	}

	public int testGetBuildNumber() {
		return _portalService.testGetBuildNumber();
	}

	public void testGetUserId() {
		_portalService.testGetUserId();
	}

	public boolean testHasClassName()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portalService.testHasClassName();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public PortalService getWrappedPortalService() {
		return _portalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedPortalService(PortalService portalService) {
		_portalService = portalService;
	}

	public PortalService getWrappedService() {
		return _portalService;
	}

	public void setWrappedService(PortalService portalService) {
		_portalService = portalService;
	}

	private PortalService _portalService;
}
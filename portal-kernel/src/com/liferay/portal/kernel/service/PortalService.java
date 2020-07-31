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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for Portal. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see PortalServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface PortalService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.portal.service.impl.PortalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the portal remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link PortalServiceUtil} if injection and service tracking are not available.
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String getAutoDeployDirectory();

	@JSONWebService
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getBuildNumber();

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	@JSONWebService
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String getVersion();

	public void testAddClassName_Rollback(String classNameValue);

	public void testAddClassName_Success(String classNameValue);

	public void testAddClassNameAndTestTransactionPortletBar_PortalRollback(
		String transactionPortletBarText);

	public void testAddClassNameAndTestTransactionPortletBar_PortletRollback(
		String transactionPortletBarText);

	public void testAddClassNameAndTestTransactionPortletBar_Success(
		String transactionPortletBarText);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void testAutoSyncHibernateSessionStateOnTxCreation();

	public void testDeleteClassName() throws PortalException;

	public int testGetBuildNumber();

	public void testGetUserId();

	public boolean testHasClassName();

}
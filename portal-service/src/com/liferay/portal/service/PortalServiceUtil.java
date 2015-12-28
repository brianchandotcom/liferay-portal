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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the remote service utility for Portal. This utility wraps
 * {@link com.liferay.portal.service.impl.PortalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see PortalService
 * @see com.liferay.portal.service.base.PortalServiceBaseImpl
 * @see com.liferay.portal.service.impl.PortalServiceImpl
 * @generated
 */
@ProviderType
public class PortalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.PortalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static java.lang.String getAutoDeployDirectory() {
		return getService().getAutoDeployDirectory();
	}

	public static int getBuildNumber() {
		return getService().getBuildNumber();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.lang.String getVersion() {
		return getService().getVersion();
	}

	public static void testAddClassNameAndTestTransactionPortletBar_PortalRollback(
		java.lang.String transactionPortletBarText) {
		getService()
			.testAddClassNameAndTestTransactionPortletBar_PortalRollback(transactionPortletBarText);
	}

	public static void testAddClassNameAndTestTransactionPortletBar_PortletRollback(
		java.lang.String transactionPortletBarText) {
		getService()
			.testAddClassNameAndTestTransactionPortletBar_PortletRollback(transactionPortletBarText);
	}

	public static void testAddClassNameAndTestTransactionPortletBar_Success(
		java.lang.String transactionPortletBarText) {
		getService()
			.testAddClassNameAndTestTransactionPortletBar_Success(transactionPortletBarText);
	}

	public static void testAddClassName_Rollback(
		java.lang.String classNameValue) {
		getService().testAddClassName_Rollback(classNameValue);
	}

	public static void testAddClassName_Success(java.lang.String classNameValue) {
		getService().testAddClassName_Success(classNameValue);
	}

	public static void testAutoSyncHibernateSessionStateOnTxCreation() {
		getService().testAutoSyncHibernateSessionStateOnTxCreation();
	}

	public static void testDeleteClassName()
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().testDeleteClassName();
	}

	public static int testGetBuildNumber() {
		return getService().testGetBuildNumber();
	}

	public static void testGetUserId() {
		getService().testGetUserId();
	}

	public static boolean testHasClassName() {
		return getService().testHasClassName();
	}

	public static PortalService getService() {
		if (_service == null) {
			_service = (PortalService)PortalBeanLocatorUtil.locate(PortalService.class.getName());

			ReferenceRegistry.registerReference(PortalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static PortalService _service;
}
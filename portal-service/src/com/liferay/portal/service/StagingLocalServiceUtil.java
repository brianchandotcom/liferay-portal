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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for Staging. This utility wraps
 * {@link com.liferay.portal.service.impl.StagingLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see StagingLocalService
 * @see com.liferay.portal.service.base.StagingLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.StagingLocalServiceImpl
 * @generated
 */
public class StagingLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.StagingLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static void cleanup(long stagingRequestId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().cleanup(stagingRequestId);
	}

	public static long prepare(long userId, long groupId,
		java.lang.String checksum)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().prepare(userId, groupId, checksum);
	}

	public static void publish(long userId, long stagingRequestId,
		boolean privateLayout,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.publish(userId, stagingRequestId, privateLayout, parameterMap);
	}

	public static void stage(long userId, long stagingRequestId,
		java.lang.String fileName, byte[] byteBuffer)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().stage(userId, stagingRequestId, fileName, byteBuffer);
	}

	public static StagingLocalService getService() {
		if (_service == null) {
			_service = (StagingLocalService)PortalBeanLocatorUtil.locate(StagingLocalService.class.getName());

			ReferenceRegistry.registerReference(StagingLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(StagingLocalService service) {
	}

	private static StagingLocalService _service;
}
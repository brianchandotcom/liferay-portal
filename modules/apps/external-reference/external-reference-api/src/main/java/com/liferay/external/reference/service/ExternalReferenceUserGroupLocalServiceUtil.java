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

package com.liferay.external.reference.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for ExternalReferenceUserGroup. This utility wraps
 * {@link com.liferay.external.reference.service.impl.ExternalReferenceUserGroupLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ExternalReferenceUserGroupLocalService
 * @see com.liferay.external.reference.service.base.ExternalReferenceUserGroupLocalServiceBaseImpl
 * @see com.liferay.external.reference.service.impl.ExternalReferenceUserGroupLocalServiceImpl
 * @generated
 */
@ProviderType
public class ExternalReferenceUserGroupLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.external.reference.service.impl.ExternalReferenceUserGroupLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	* Add or update an user group.
	*
	* @param userId the primary key of the user
	* @param companyId the primary key of the user group's company
	* @param name the user group's name
	* @param description the user group's description
	* @param externalReferenceCode the user group's external reference code
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set expando bridge attributes for the
	user group.
	* @review
	* @return the user group
	*/
	public static com.liferay.portal.kernel.model.UserGroup upsertUserGroup(
		long userId, long companyId, String name, String description,
		String externalReferenceCode,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .upsertUserGroup(userId, companyId, name, description,
			externalReferenceCode, serviceContext);
	}

	public static ExternalReferenceUserGroupLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<ExternalReferenceUserGroupLocalService, ExternalReferenceUserGroupLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ExternalReferenceUserGroupLocalService.class);

		ServiceTracker<ExternalReferenceUserGroupLocalService, ExternalReferenceUserGroupLocalService> serviceTracker =
			new ServiceTracker<ExternalReferenceUserGroupLocalService, ExternalReferenceUserGroupLocalService>(bundle.getBundleContext(),
				ExternalReferenceUserGroupLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}
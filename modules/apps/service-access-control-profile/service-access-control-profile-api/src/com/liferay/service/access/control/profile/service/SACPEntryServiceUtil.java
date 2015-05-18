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

package com.liferay.service.access.control.profile.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for SACPEntry. This utility wraps
 * {@link com.liferay.service.access.control.profile.service.impl.SACPEntryServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see SACPEntryService
 * @see com.liferay.service.access.control.profile.service.base.SACPEntryServiceBaseImpl
 * @see com.liferay.service.access.control.profile.service.impl.SACPEntryServiceImpl
 * @generated
 */
@ProviderType
public class SACPEntryServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.service.access.control.profile.service.impl.SACPEntryServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.service.access.control.profile.model.SACPEntry addSACPEntry(
		long companyId, java.lang.String allowedServices,
		java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addSACPEntry(companyId, allowedServices, name, titleMap,
			serviceContext);
	}

	public static com.liferay.service.access.control.profile.model.SACPEntry deleteSACPEntry(
		com.liferay.service.access.control.profile.model.SACPEntry sacpEntry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteSACPEntry(sacpEntry);
	}

	public static com.liferay.service.access.control.profile.model.SACPEntry deleteSACPEntry(
		long sacpEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteSACPEntry(sacpEntryId);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	public static java.util.List<com.liferay.service.access.control.profile.model.SACPEntry> getCompanySACPEntries(
		long companyId, int start, int end) {
		return getService().getCompanySACPEntries(companyId, start, end);
	}

	public static java.util.List<com.liferay.service.access.control.profile.model.SACPEntry> getCompanySACPEntries(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.control.profile.model.SACPEntry> obc) {
		return getService().getCompanySACPEntries(companyId, start, end, obc);
	}

	public static int getCompanySACPEntriesCount(long companyId) {
		return getService().getCompanySACPEntriesCount(companyId);
	}

	public static com.liferay.service.access.control.profile.model.SACPEntry getSACPEntry(
		long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getSACPEntry(companyId, name);
	}

	public static com.liferay.service.access.control.profile.model.SACPEntry getSACPEntry(
		long sacpEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getSACPEntry(sacpEntryId);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static com.liferay.service.access.control.profile.model.SACPEntry updateSACPEntry(
		long sacpEntryId, java.lang.String allowedServices,
		java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateSACPEntry(sacpEntryId, allowedServices, name,
			titleMap, serviceContext);
	}

	public static SACPEntryService getService() {
		return _serviceTracker.getService();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(SACPEntryService service) {
	}

	private static ServiceTracker<SACPEntryService, SACPEntryService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(SACPEntryServiceUtil.class);

		_serviceTracker = new ServiceTracker<SACPEntryService, SACPEntryService>(bundle.getBundleContext(),
				SACPEntryService.class, null);

		_serviceTracker.open();
	}
}
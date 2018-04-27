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

package com.liferay.commerce.product.type.grouped.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CPDefinitionGroupedEntry. This utility wraps
 * {@link com.liferay.commerce.product.type.grouped.service.impl.CPDefinitionGroupedEntryServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Andrea Di Giorgi
 * @see CPDefinitionGroupedEntryService
 * @see com.liferay.commerce.product.type.grouped.service.base.CPDefinitionGroupedEntryServiceBaseImpl
 * @see com.liferay.commerce.product.type.grouped.service.impl.CPDefinitionGroupedEntryServiceImpl
 * @generated
 */
@ProviderType
public class CPDefinitionGroupedEntryServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.type.grouped.service.impl.CPDefinitionGroupedEntryServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static void addCPDefinitionGroupedEntries(long cpDefinitionId,
		long[] entryCPDefinitionIds,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addCPDefinitionGroupedEntries(cpDefinitionId,
			entryCPDefinitionIds, serviceContext);
	}

	public static com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry deleteCPDefinitionGroupedEntry(
		long cpDefinitionGroupedEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCPDefinitionGroupedEntry(cpDefinitionGroupedEntryId);
	}

	public static java.util.List<com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry> getCPDefinitionGroupedEntries(
		long cpDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCPDefinitionGroupedEntries(cpDefinitionId, start, end,
			orderByComparator);
	}

	public static int getCPDefinitionGroupedEntriesCount(long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPDefinitionGroupedEntriesCount(cpDefinitionId);
	}

	public static com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry getCPDefinitionGroupedEntry(
		long cpDefinitionGroupedEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCPDefinitionGroupedEntry(cpDefinitionGroupedEntryId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry updateCPDefinitionGroupedEntry(
		long cpDefinitionGroupedEntryId, double priority, int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCPDefinitionGroupedEntry(cpDefinitionGroupedEntryId,
			priority, quantity);
	}

	public static CPDefinitionGroupedEntryService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPDefinitionGroupedEntryService, CPDefinitionGroupedEntryService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPDefinitionGroupedEntryService.class);

		ServiceTracker<CPDefinitionGroupedEntryService, CPDefinitionGroupedEntryService> serviceTracker =
			new ServiceTracker<CPDefinitionGroupedEntryService, CPDefinitionGroupedEntryService>(bundle.getBundleContext(),
				CPDefinitionGroupedEntryService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}
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

package com.liferay.dynamic.data.mapping.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for DDMDataProviderInstance. This utility wraps
 * {@link com.liferay.dynamic.data.mapping.service.impl.DDMDataProviderInstanceServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DDMDataProviderInstanceService
 * @see com.liferay.dynamic.data.mapping.service.base.DDMDataProviderInstanceServiceBaseImpl
 * @see com.liferay.dynamic.data.mapping.service.impl.DDMDataProviderInstanceServiceImpl
 * @generated
 */
@ProviderType
public class DDMDataProviderInstanceServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.dynamic.data.mapping.service.impl.DDMDataProviderInstanceServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance addDataProviderInstance(
		long groupId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.dynamic.data.mapping.storage.DDMFormValues ddmFormValues,
		java.lang.String type,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addDataProviderInstance(groupId, nameMap, descriptionMap,
			ddmFormValues, type, serviceContext);
	}

	public static void deleteDataProviderInstance(long dataProviderInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteDataProviderInstance(dataProviderInstanceId);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance fetchDataProviderInstance(
		long dataProviderInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchDataProviderInstance(dataProviderInstanceId);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance getDataProviderInstance(
		long dataProviderInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getDataProviderInstance(dataProviderInstanceId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance> search(
		long companyId, long[] groupIds, java.lang.String keywords, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance> orderByComparator) {
		return getService()
				   .search(companyId, groupIds, keywords, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance> search(
		long companyId, long[] groupIds, java.lang.String name,
		java.lang.String description, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance> orderByComparator) {
		return getService()
				   .search(companyId, groupIds, name, description, andOperator,
			start, end, orderByComparator);
	}

	public static int searchCount(long companyId, long[] groupIds,
		java.lang.String keywords) {
		return getService().searchCount(companyId, groupIds, keywords);
	}

	public static int searchCount(long companyId, long[] groupIds,
		java.lang.String name, java.lang.String description, boolean andOperator) {
		return getService()
				   .searchCount(companyId, groupIds, name, description,
			andOperator);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance updateDataProviderInstance(
		long dataProviderInstanceId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.dynamic.data.mapping.storage.DDMFormValues ddmFormValues,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateDataProviderInstance(dataProviderInstanceId, nameMap,
			descriptionMap, ddmFormValues, serviceContext);
	}

	public static DDMDataProviderInstanceService getService() {
		return _serviceTracker.getService();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(DDMDataProviderInstanceService service) {
	}

	private static ServiceTracker<DDMDataProviderInstanceService, DDMDataProviderInstanceService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(DDMDataProviderInstanceServiceUtil.class);

		_serviceTracker = new ServiceTracker<DDMDataProviderInstanceService, DDMDataProviderInstanceService>(bundle.getBundleContext(),
				DDMDataProviderInstanceService.class, null);

		_serviceTracker.open();
	}
}
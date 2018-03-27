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
 * Provides the remote service utility for DDMFormInstance. This utility wraps
 * {@link com.liferay.dynamic.data.mapping.service.impl.DDMFormInstanceServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceService
 * @see com.liferay.dynamic.data.mapping.service.base.DDMFormInstanceServiceBaseImpl
 * @see com.liferay.dynamic.data.mapping.service.impl.DDMFormInstanceServiceImpl
 * @generated
 */
@ProviderType
public class DDMFormInstanceServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.dynamic.data.mapping.service.impl.DDMFormInstanceServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMFormInstance addFormInstance(
		long groupId, long ddmStructureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.dynamic.data.mapping.storage.DDMFormValues settingsDDMFormValues,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addFormInstance(groupId, ddmStructureId, nameMap,
			descriptionMap, settingsDDMFormValues, serviceContext);
	}

	public static void deleteFormInstance(long ddmFormInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteFormInstance(ddmFormInstanceId);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMFormInstance fetchFormInstance(
		long ddmFormInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchFormInstance(ddmFormInstanceId);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMFormInstance getFormInstance(
		long ddmFormInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getFormInstance(ddmFormInstanceId);
	}

	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMFormInstance> getFormInstances(
		long companyId, long groupId, int start, int end) {
		return getService().getFormInstances(companyId, groupId, start, end);
	}

	public static int getFormInstancesCount(long companyId, long groupId) {
		return getService().getFormInstancesCount(companyId, groupId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMFormInstance> search(
		long companyId, long groupId, java.lang.String keywords, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMFormInstance> orderByComparator) {
		return getService()
				   .search(companyId, groupId, keywords, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMFormInstance> search(
		long companyId, long groupId, java.lang.String[] names,
		java.lang.String[] descriptions, boolean andOperator, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMFormInstance> orderByComparator) {
		return getService()
				   .search(companyId, groupId, names, descriptions,
			andOperator, start, end, orderByComparator);
	}

	public static int searchCount(long companyId, long groupId,
		java.lang.String keywords) {
		return getService().searchCount(companyId, groupId, keywords);
	}

	public static int searchCount(long companyId, long groupId,
		java.lang.String[] names, java.lang.String[] descriptions,
		boolean andOperator) {
		return getService()
				   .searchCount(companyId, groupId, names, descriptions,
			andOperator);
	}

	/**
	* Updates the the record set's settings.
	*
	* @param formInstanceId the primary key of the form instance
	* @param settingsDDMFormValues the record set's settings. For more
	information see <code>DDMFormValues</code> in the
	<code>dynamic.data.mapping.api</code> module.
	* @return the record set
	* @throws PortalException if a portal exception occurred
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMFormInstance updateFormInstance(
		long formInstanceId,
		com.liferay.dynamic.data.mapping.storage.DDMFormValues settingsDDMFormValues)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateFormInstance(formInstanceId, settingsDDMFormValues);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMFormInstance updateFormInstance(
		long ddmFormInstanceId, long ddmStructureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.dynamic.data.mapping.storage.DDMFormValues settingsDDMFormValues,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateFormInstance(ddmFormInstanceId, ddmStructureId,
			nameMap, descriptionMap, settingsDDMFormValues, serviceContext);
	}

	public static DDMFormInstanceService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<DDMFormInstanceService, DDMFormInstanceService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(DDMFormInstanceService.class);

		ServiceTracker<DDMFormInstanceService, DDMFormInstanceService> serviceTracker =
			new ServiceTracker<DDMFormInstanceService, DDMFormInstanceService>(bundle.getBundleContext(),
				DDMFormInstanceService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}
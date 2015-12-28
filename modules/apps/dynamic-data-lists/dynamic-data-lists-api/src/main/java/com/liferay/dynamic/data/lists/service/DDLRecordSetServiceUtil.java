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

package com.liferay.dynamic.data.lists.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for DDLRecordSet. This utility wraps
 * {@link com.liferay.dynamic.data.lists.service.impl.DDLRecordSetServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordSetService
 * @see com.liferay.dynamic.data.lists.service.base.DDLRecordSetServiceBaseImpl
 * @see com.liferay.dynamic.data.lists.service.impl.DDLRecordSetServiceImpl
 * @generated
 */
@ProviderType
public class DDLRecordSetServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.dynamic.data.lists.service.impl.DDLRecordSetServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.dynamic.data.lists.model.DDLRecordSet addRecordSet(
		long groupId, long ddmStructureId, java.lang.String recordSetKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		int minDisplayRows, int scope,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addRecordSet(groupId, ddmStructureId, recordSetKey,
			nameMap, descriptionMap, minDisplayRows, scope, serviceContext);
	}

	public static void deleteRecordSet(long recordSetId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteRecordSet(recordSetId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.dynamic.data.lists.model.DDLRecordSet getRecordSet(
		long recordSetId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getRecordSet(recordSetId);
	}

	public static java.util.List<com.liferay.dynamic.data.lists.model.DDLRecordSet> getRecordSets(
		long[] groupIds) {
		return getService().getRecordSets(groupIds);
	}

	public static java.util.List<com.liferay.dynamic.data.lists.model.DDLRecordSet> search(
		long companyId, long groupId, java.lang.String keywords, int scope,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.lists.model.DDLRecordSet> orderByComparator) {
		return getService()
				   .search(companyId, groupId, keywords, scope, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.dynamic.data.lists.model.DDLRecordSet> search(
		long companyId, long groupId, java.lang.String name,
		java.lang.String description, int scope, boolean andOperator,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.lists.model.DDLRecordSet> orderByComparator) {
		return getService()
				   .search(companyId, groupId, name, description, scope,
			andOperator, start, end, orderByComparator);
	}

	public static int searchCount(long companyId, long groupId,
		java.lang.String keywords, int scope) {
		return getService().searchCount(companyId, groupId, keywords, scope);
	}

	public static int searchCount(long companyId, long groupId,
		java.lang.String name, java.lang.String description, int scope,
		boolean andOperator) {
		return getService()
				   .searchCount(companyId, groupId, name, description, scope,
			andOperator);
	}

	public static com.liferay.dynamic.data.lists.model.DDLRecordSet updateMinDisplayRows(
		long recordSetId, int minDisplayRows,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateMinDisplayRows(recordSetId, minDisplayRows,
			serviceContext);
	}

	public static com.liferay.dynamic.data.lists.model.DDLRecordSet updateRecordSet(
		long groupId, long ddmStructureId, java.lang.String recordSetKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		int minDisplayRows,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateRecordSet(groupId, ddmStructureId, recordSetKey,
			nameMap, descriptionMap, minDisplayRows, serviceContext);
	}

	public static com.liferay.dynamic.data.lists.model.DDLRecordSet updateRecordSet(
		long recordSetId, long ddmStructureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		int minDisplayRows,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateRecordSet(recordSetId, ddmStructureId, nameMap,
			descriptionMap, minDisplayRows, serviceContext);
	}

	public static com.liferay.dynamic.data.lists.model.DDLRecordSet updateRecordSet(
		long recordSetId,
		com.liferay.dynamic.data.mapping.storage.DDMFormValues settingsDDMFormValues)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().updateRecordSet(recordSetId, settingsDDMFormValues);
	}

	public static DDLRecordSetService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<DDLRecordSetService, DDLRecordSetService> _serviceTracker =
		ServiceTrackerFactory.open(DDLRecordSetService.class);
}
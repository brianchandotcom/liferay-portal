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

package com.liferay.depot.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for DepotEntry. This utility wraps
 * <code>com.liferay.depot.service.impl.DepotEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see DepotEntryLocalService
 * @generated
 */
public class DepotEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.depot.service.impl.DepotEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the depot entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param depotEntry the depot entry
	 * @return the depot entry that was added
	 */
	public static com.liferay.depot.model.DepotEntry addDepotEntry(
		com.liferay.depot.model.DepotEntry depotEntry) {

		return getService().addDepotEntry(depotEntry);
	}

	public static com.liferay.depot.model.DepotEntry addDepotEntry(
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addDepotEntry(
			nameMap, descriptionMap, serviceContext);
	}

	/**
	 * Creates a new depot entry with the primary key. Does not add the depot entry to the database.
	 *
	 * @param depotEntryId the primary key for the new depot entry
	 * @return the new depot entry
	 */
	public static com.liferay.depot.model.DepotEntry createDepotEntry(
		long depotEntryId) {

		return getService().createDepotEntry(depotEntryId);
	}

	/**
	 * Deletes the depot entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param depotEntry the depot entry
	 * @return the depot entry that was removed
	 */
	public static com.liferay.depot.model.DepotEntry deleteDepotEntry(
		com.liferay.depot.model.DepotEntry depotEntry) {

		return getService().deleteDepotEntry(depotEntry);
	}

	/**
	 * Deletes the depot entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param depotEntryId the primary key of the depot entry
	 * @return the depot entry that was removed
	 * @throws PortalException if a depot entry with the primary key could not be found
	 */
	public static com.liferay.depot.model.DepotEntry deleteDepotEntry(
			long depotEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteDepotEntry(depotEntryId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.depot.model.impl.DepotEntryModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.depot.model.impl.DepotEntryModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.depot.model.DepotEntry fetchDepotEntry(
		long depotEntryId) {

		return getService().fetchDepotEntry(depotEntryId);
	}

	/**
	 * Returns the depot entry matching the UUID and group.
	 *
	 * @param uuid the depot entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching depot entry, or <code>null</code> if a matching depot entry could not be found
	 */
	public static com.liferay.depot.model.DepotEntry
		fetchDepotEntryByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchDepotEntryByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.depot.model.DepotEntry fetchGroupDepotEntry(
		long groupId) {

		return getService().fetchGroupDepotEntry(groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the depot entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.depot.model.impl.DepotEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of depot entries
	 * @param end the upper bound of the range of depot entries (not inclusive)
	 * @return the range of depot entries
	 */
	public static java.util.List<com.liferay.depot.model.DepotEntry>
		getDepotEntries(int start, int end) {

		return getService().getDepotEntries(start, end);
	}

	/**
	 * Returns all the depot entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the depot entries
	 * @param companyId the primary key of the company
	 * @return the matching depot entries, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.depot.model.DepotEntry>
		getDepotEntriesByUuidAndCompanyId(String uuid, long companyId) {

		return getService().getDepotEntriesByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of depot entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the depot entries
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of depot entries
	 * @param end the upper bound of the range of depot entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching depot entries, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.depot.model.DepotEntry>
		getDepotEntriesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.depot.model.DepotEntry> orderByComparator) {

		return getService().getDepotEntriesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of depot entries.
	 *
	 * @return the number of depot entries
	 */
	public static int getDepotEntriesCount() {
		return getService().getDepotEntriesCount();
	}

	/**
	 * Returns the depot entry with the primary key.
	 *
	 * @param depotEntryId the primary key of the depot entry
	 * @return the depot entry
	 * @throws PortalException if a depot entry with the primary key could not be found
	 */
	public static com.liferay.depot.model.DepotEntry getDepotEntry(
			long depotEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDepotEntry(depotEntryId);
	}

	/**
	 * Returns the depot entry matching the UUID and group.
	 *
	 * @param uuid the depot entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching depot entry
	 * @throws PortalException if a matching depot entry could not be found
	 */
	public static com.liferay.depot.model.DepotEntry
			getDepotEntryByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDepotEntryByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static com.liferay.depot.model.DepotEntry getGroupDepotEntry(
			long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getGroupDepotEntry(groupId);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the depot entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param depotEntry the depot entry
	 * @return the depot entry that was updated
	 */
	public static com.liferay.depot.model.DepotEntry updateDepotEntry(
		com.liferay.depot.model.DepotEntry depotEntry) {

		return getService().updateDepotEntry(depotEntry);
	}

	public static com.liferay.depot.model.DepotEntry updateDepotEntry(
			long depotEntryId, java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			java.util.Map<String, Boolean> depotAppCustomizationMap,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsProperties,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateDepotEntry(
			depotEntryId, nameMap, descriptionMap, depotAppCustomizationMap,
			typeSettingsProperties, serviceContext);
	}

	public static DepotEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<DepotEntryLocalService, DepotEntryLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(DepotEntryLocalService.class);

		ServiceTracker<DepotEntryLocalService, DepotEntryLocalService>
			serviceTracker =
				new ServiceTracker
					<DepotEntryLocalService, DepotEntryLocalService>(
						bundle.getBundleContext(), DepotEntryLocalService.class,
						null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}
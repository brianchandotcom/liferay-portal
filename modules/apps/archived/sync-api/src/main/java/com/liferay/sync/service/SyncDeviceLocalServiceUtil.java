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

package com.liferay.sync.service;

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.sync.model.SyncDevice;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for SyncDevice. This utility wraps
 * <code>com.liferay.sync.service.impl.SyncDeviceLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see SyncDeviceLocalService
 * @generated
 */
public class SyncDeviceLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.sync.service.impl.SyncDeviceLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static SyncDevice addSyncDevice(
			long userId, String type, long buildNumber, String hostname,
			int featureSet)
		throws PortalException {

		return getService().addSyncDevice(
			userId, type, buildNumber, hostname, featureSet);
	}

	/**
	 * Adds the sync device to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SyncDeviceLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param syncDevice the sync device
	 * @return the sync device that was added
	 */
	public static SyncDevice addSyncDevice(SyncDevice syncDevice) {
		return getService().addSyncDevice(syncDevice);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Creates a new sync device with the primary key. Does not add the sync device to the database.
	 *
	 * @param syncDeviceId the primary key for the new sync device
	 * @return the new sync device
	 */
	public static SyncDevice createSyncDevice(long syncDeviceId) {
		return getService().createSyncDevice(syncDeviceId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the sync device with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SyncDeviceLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param syncDeviceId the primary key of the sync device
	 * @return the sync device that was removed
	 * @throws PortalException if a sync device with the primary key could not be found
	 */
	public static SyncDevice deleteSyncDevice(long syncDeviceId)
		throws PortalException {

		return getService().deleteSyncDevice(syncDeviceId);
	}

	/**
	 * Deletes the sync device from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SyncDeviceLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param syncDevice the sync device
	 * @return the sync device that was removed
	 */
	public static SyncDevice deleteSyncDevice(SyncDevice syncDevice) {
		return getService().deleteSyncDevice(syncDevice);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sync.model.impl.SyncDeviceModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sync.model.impl.SyncDeviceModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
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
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static SyncDevice fetchSyncDevice(long syncDeviceId) {
		return getService().fetchSyncDevice(syncDeviceId);
	}

	/**
	 * Returns the sync device with the matching UUID and company.
	 *
	 * @param uuid the sync device's UUID
	 * @param companyId the primary key of the company
	 * @return the matching sync device, or <code>null</code> if a matching sync device could not be found
	 */
	public static SyncDevice fetchSyncDeviceByUuidAndCompanyId(
		String uuid, long companyId) {

		return getService().fetchSyncDeviceByUuidAndCompanyId(uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
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

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the sync device with the primary key.
	 *
	 * @param syncDeviceId the primary key of the sync device
	 * @return the sync device
	 * @throws PortalException if a sync device with the primary key could not be found
	 */
	public static SyncDevice getSyncDevice(long syncDeviceId)
		throws PortalException {

		return getService().getSyncDevice(syncDeviceId);
	}

	/**
	 * Returns the sync device with the matching UUID and company.
	 *
	 * @param uuid the sync device's UUID
	 * @param companyId the primary key of the company
	 * @return the matching sync device
	 * @throws PortalException if a matching sync device could not be found
	 */
	public static SyncDevice getSyncDeviceByUuidAndCompanyId(
			String uuid, long companyId)
		throws PortalException {

		return getService().getSyncDeviceByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of all the sync devices.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sync.model.impl.SyncDeviceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sync devices
	 * @param end the upper bound of the range of sync devices (not inclusive)
	 * @return the range of sync devices
	 */
	public static List<SyncDevice> getSyncDevices(int start, int end) {
		return getService().getSyncDevices(start, end);
	}

	public static List<SyncDevice> getSyncDevices(
			long userId, int start, int end,
			OrderByComparator<SyncDevice> orderByComparator)
		throws PortalException {

		return getService().getSyncDevices(
			userId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of sync devices.
	 *
	 * @return the number of sync devices
	 */
	public static int getSyncDevicesCount() {
		return getService().getSyncDevicesCount();
	}

	public static List<SyncDevice> search(
		long companyId, String keywords, int start, int end,
		OrderByComparator<SyncDevice> orderByComparator) {

		return getService().search(
			companyId, keywords, start, end, orderByComparator);
	}

	public static void updateStatus(long syncDeviceId, int status)
		throws PortalException {

		getService().updateStatus(syncDeviceId, status);
	}

	public static SyncDevice updateSyncDevice(
			long syncDeviceId, String type, long buildNumber, int featureSet,
			String hostname, int status)
		throws PortalException {

		return getService().updateSyncDevice(
			syncDeviceId, type, buildNumber, featureSet, hostname, status);
	}

	/**
	 * Updates the sync device in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SyncDeviceLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param syncDevice the sync device
	 * @return the sync device that was updated
	 */
	public static SyncDevice updateSyncDevice(SyncDevice syncDevice) {
		return getService().updateSyncDevice(syncDevice);
	}

	public static SyncDeviceLocalService getService() {
		return _service;
	}

	private static volatile SyncDeviceLocalService _service;

}
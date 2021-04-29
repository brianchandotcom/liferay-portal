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

package com.liferay.dataset.view.service;

import com.liferay.dataset.view.model.DatasetViewStateEntry;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for DatasetViewStateEntry. This utility wraps
 * <code>com.liferay.dataset.view.service.impl.DatasetViewStateEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see DatasetViewStateEntryLocalService
 * @generated
 */
public class DatasetViewStateEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.dataset.view.service.impl.DatasetViewStateEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the dataset view state entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DatasetViewStateEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param datasetViewStateEntry the dataset view state entry
	 * @return the dataset view state entry that was added
	 */
	public static DatasetViewStateEntry addDatasetViewStateEntry(
		DatasetViewStateEntry datasetViewStateEntry) {

		return getService().addDatasetViewStateEntry(datasetViewStateEntry);
	}

	/**
	 * Creates a new dataset view state entry with the primary key. Does not add the dataset view state entry to the database.
	 *
	 * @param datasetViewStateEntryId the primary key for the new dataset view state entry
	 * @return the new dataset view state entry
	 */
	public static DatasetViewStateEntry createDatasetViewStateEntry(
		long datasetViewStateEntryId) {

		return getService().createDatasetViewStateEntry(
			datasetViewStateEntryId);
	}

	public static DatasetViewStateEntry createDatasetViewStateEntry(
		String json) {

		return getService().createDatasetViewStateEntry(json);
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
	 * Deletes the dataset view state entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DatasetViewStateEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param datasetViewStateEntry the dataset view state entry
	 * @return the dataset view state entry that was removed
	 */
	public static DatasetViewStateEntry deleteDatasetViewStateEntry(
		DatasetViewStateEntry datasetViewStateEntry) {

		return getService().deleteDatasetViewStateEntry(datasetViewStateEntry);
	}

	/**
	 * Deletes the dataset view state entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DatasetViewStateEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param datasetViewStateEntryId the primary key of the dataset view state entry
	 * @return the dataset view state entry that was removed
	 * @throws PortalException if a dataset view state entry with the primary key could not be found
	 */
	public static DatasetViewStateEntry deleteDatasetViewStateEntry(
			long datasetViewStateEntryId)
		throws PortalException {

		return getService().deleteDatasetViewStateEntry(
			datasetViewStateEntryId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dataset.view.model.impl.DatasetViewStateEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dataset.view.model.impl.DatasetViewStateEntryModelImpl</code>.
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

	public static DatasetViewStateEntry fetchDatasetViewStateEntry(
		long datasetViewStateEntryId) {

		return getService().fetchDatasetViewStateEntry(datasetViewStateEntryId);
	}

	/**
	 * Returns the dataset view state entry with the matching UUID and company.
	 *
	 * @param uuid the dataset view state entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching dataset view state entry, or <code>null</code> if a matching dataset view state entry could not be found
	 */
	public static DatasetViewStateEntry
		fetchDatasetViewStateEntryByUuidAndCompanyId(
			String uuid, long companyId) {

		return getService().fetchDatasetViewStateEntryByUuidAndCompanyId(
			uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the dataset view state entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dataset.view.model.impl.DatasetViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dataset view state entries
	 * @param end the upper bound of the range of dataset view state entries (not inclusive)
	 * @return the range of dataset view state entries
	 */
	public static List<DatasetViewStateEntry> getDatasetViewStateEntries(
		int start, int end) {

		return getService().getDatasetViewStateEntries(start, end);
	}

	/**
	 * Returns the number of dataset view state entries.
	 *
	 * @return the number of dataset view state entries
	 */
	public static int getDatasetViewStateEntriesCount() {
		return getService().getDatasetViewStateEntriesCount();
	}

	/**
	 * Returns the dataset view state entry with the primary key.
	 *
	 * @param datasetViewStateEntryId the primary key of the dataset view state entry
	 * @return the dataset view state entry
	 * @throws PortalException if a dataset view state entry with the primary key could not be found
	 */
	public static DatasetViewStateEntry getDatasetViewStateEntry(
			long datasetViewStateEntryId)
		throws PortalException {

		return getService().getDatasetViewStateEntry(datasetViewStateEntryId);
	}

	/**
	 * Returns the dataset view state entry with the matching UUID and company.
	 *
	 * @param uuid the dataset view state entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching dataset view state entry
	 * @throws PortalException if a matching dataset view state entry could not be found
	 */
	public static DatasetViewStateEntry
			getDatasetViewStateEntryByUuidAndCompanyId(
				String uuid, long companyId)
		throws PortalException {

		return getService().getDatasetViewStateEntryByUuidAndCompanyId(
			uuid, companyId);
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
	 * Updates the dataset view state entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DatasetViewStateEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param datasetViewStateEntry the dataset view state entry
	 * @return the dataset view state entry that was updated
	 */
	public static DatasetViewStateEntry updateDatasetViewStateEntry(
		DatasetViewStateEntry datasetViewStateEntry) {

		return getService().updateDatasetViewStateEntry(datasetViewStateEntry);
	}

	public static DatasetViewStateEntryLocalService getService() {
		return _service;
	}

	private static volatile DatasetViewStateEntryLocalService _service;

}
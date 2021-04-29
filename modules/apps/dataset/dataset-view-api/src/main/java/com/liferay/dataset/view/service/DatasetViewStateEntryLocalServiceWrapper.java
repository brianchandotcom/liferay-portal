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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DatasetViewStateEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DatasetViewStateEntryLocalService
 * @generated
 */
public class DatasetViewStateEntryLocalServiceWrapper
	implements DatasetViewStateEntryLocalService,
			   ServiceWrapper<DatasetViewStateEntryLocalService> {

	public DatasetViewStateEntryLocalServiceWrapper(
		DatasetViewStateEntryLocalService datasetViewStateEntryLocalService) {

		_datasetViewStateEntryLocalService = datasetViewStateEntryLocalService;
	}

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
	@Override
	public com.liferay.dataset.view.model.DatasetViewStateEntry
		addDatasetViewStateEntry(
			com.liferay.dataset.view.model.DatasetViewStateEntry
				datasetViewStateEntry) {

		return _datasetViewStateEntryLocalService.addDatasetViewStateEntry(
			datasetViewStateEntry);
	}

	/**
	 * Creates a new dataset view state entry with the primary key. Does not add the dataset view state entry to the database.
	 *
	 * @param datasetViewStateEntryId the primary key for the new dataset view state entry
	 * @return the new dataset view state entry
	 */
	@Override
	public com.liferay.dataset.view.model.DatasetViewStateEntry
		createDatasetViewStateEntry(long datasetViewStateEntryId) {

		return _datasetViewStateEntryLocalService.createDatasetViewStateEntry(
			datasetViewStateEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _datasetViewStateEntryLocalService.createPersistedModel(
			primaryKeyObj);
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
	@Override
	public com.liferay.dataset.view.model.DatasetViewStateEntry
		deleteDatasetViewStateEntry(
			com.liferay.dataset.view.model.DatasetViewStateEntry
				datasetViewStateEntry) {

		return _datasetViewStateEntryLocalService.deleteDatasetViewStateEntry(
			datasetViewStateEntry);
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
	@Override
	public com.liferay.dataset.view.model.DatasetViewStateEntry
			deleteDatasetViewStateEntry(long datasetViewStateEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _datasetViewStateEntryLocalService.deleteDatasetViewStateEntry(
			datasetViewStateEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _datasetViewStateEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _datasetViewStateEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _datasetViewStateEntryLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _datasetViewStateEntryLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _datasetViewStateEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _datasetViewStateEntryLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _datasetViewStateEntryLocalService.dynamicQueryCount(
			dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _datasetViewStateEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.dataset.view.model.DatasetViewStateEntry
		fetchDatasetViewStateEntry(long datasetViewStateEntryId) {

		return _datasetViewStateEntryLocalService.fetchDatasetViewStateEntry(
			datasetViewStateEntryId);
	}

	/**
	 * Returns the dataset view state entry with the matching UUID and company.
	 *
	 * @param uuid the dataset view state entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching dataset view state entry, or <code>null</code> if a matching dataset view state entry could not be found
	 */
	@Override
	public com.liferay.dataset.view.model.DatasetViewStateEntry
		fetchDatasetViewStateEntryByUuidAndCompanyId(
			String uuid, long companyId) {

		return _datasetViewStateEntryLocalService.
			fetchDatasetViewStateEntryByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _datasetViewStateEntryLocalService.getActionableDynamicQuery();
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
	@Override
	public java.util.List<com.liferay.dataset.view.model.DatasetViewStateEntry>
		getDatasetViewStateEntries(int start, int end) {

		return _datasetViewStateEntryLocalService.getDatasetViewStateEntries(
			start, end);
	}

	/**
	 * Returns the number of dataset view state entries.
	 *
	 * @return the number of dataset view state entries
	 */
	@Override
	public int getDatasetViewStateEntriesCount() {
		return _datasetViewStateEntryLocalService.
			getDatasetViewStateEntriesCount();
	}

	/**
	 * Returns the dataset view state entry with the primary key.
	 *
	 * @param datasetViewStateEntryId the primary key of the dataset view state entry
	 * @return the dataset view state entry
	 * @throws PortalException if a dataset view state entry with the primary key could not be found
	 */
	@Override
	public com.liferay.dataset.view.model.DatasetViewStateEntry
			getDatasetViewStateEntry(long datasetViewStateEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _datasetViewStateEntryLocalService.getDatasetViewStateEntry(
			datasetViewStateEntryId);
	}

	/**
	 * Returns the dataset view state entry with the matching UUID and company.
	 *
	 * @param uuid the dataset view state entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching dataset view state entry
	 * @throws PortalException if a matching dataset view state entry could not be found
	 */
	@Override
	public com.liferay.dataset.view.model.DatasetViewStateEntry
			getDatasetViewStateEntryByUuidAndCompanyId(
				String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _datasetViewStateEntryLocalService.
			getDatasetViewStateEntryByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _datasetViewStateEntryLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _datasetViewStateEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _datasetViewStateEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _datasetViewStateEntryLocalService.getPersistedModel(
			primaryKeyObj);
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
	@Override
	public com.liferay.dataset.view.model.DatasetViewStateEntry
		updateDatasetViewStateEntry(
			com.liferay.dataset.view.model.DatasetViewStateEntry
				datasetViewStateEntry) {

		return _datasetViewStateEntryLocalService.updateDatasetViewStateEntry(
			datasetViewStateEntry);
	}

	@Override
	public DatasetViewStateEntryLocalService getWrappedService() {
		return _datasetViewStateEntryLocalService;
	}

	@Override
	public void setWrappedService(
		DatasetViewStateEntryLocalService datasetViewStateEntryLocalService) {

		_datasetViewStateEntryLocalService = datasetViewStateEntryLocalService;
	}

	private DatasetViewStateEntryLocalService
		_datasetViewStateEntryLocalService;

}
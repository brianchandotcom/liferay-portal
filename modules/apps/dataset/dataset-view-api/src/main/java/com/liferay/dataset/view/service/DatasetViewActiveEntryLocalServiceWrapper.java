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
 * Provides a wrapper for {@link DatasetViewActiveEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DatasetViewActiveEntryLocalService
 * @generated
 */
public class DatasetViewActiveEntryLocalServiceWrapper
	implements DatasetViewActiveEntryLocalService,
			   ServiceWrapper<DatasetViewActiveEntryLocalService> {

	public DatasetViewActiveEntryLocalServiceWrapper(
		DatasetViewActiveEntryLocalService datasetViewActiveEntryLocalService) {

		_datasetViewActiveEntryLocalService =
			datasetViewActiveEntryLocalService;
	}

	/**
	 * Adds the dataset view active entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DatasetViewActiveEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param datasetViewActiveEntry the dataset view active entry
	 * @return the dataset view active entry that was added
	 */
	@Override
	public com.liferay.dataset.view.model.DatasetViewActiveEntry
		addDatasetViewActiveEntry(
			com.liferay.dataset.view.model.DatasetViewActiveEntry
				datasetViewActiveEntry) {

		return _datasetViewActiveEntryLocalService.addDatasetViewActiveEntry(
			datasetViewActiveEntry);
	}

	/**
	 * Creates a new dataset view active entry with the primary key. Does not add the dataset view active entry to the database.
	 *
	 * @param datasetViewActiveEntryId the primary key for the new dataset view active entry
	 * @return the new dataset view active entry
	 */
	@Override
	public com.liferay.dataset.view.model.DatasetViewActiveEntry
		createDatasetViewActiveEntry(long datasetViewActiveEntryId) {

		return _datasetViewActiveEntryLocalService.createDatasetViewActiveEntry(
			datasetViewActiveEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _datasetViewActiveEntryLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the dataset view active entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DatasetViewActiveEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param datasetViewActiveEntry the dataset view active entry
	 * @return the dataset view active entry that was removed
	 */
	@Override
	public com.liferay.dataset.view.model.DatasetViewActiveEntry
		deleteDatasetViewActiveEntry(
			com.liferay.dataset.view.model.DatasetViewActiveEntry
				datasetViewActiveEntry) {

		return _datasetViewActiveEntryLocalService.deleteDatasetViewActiveEntry(
			datasetViewActiveEntry);
	}

	/**
	 * Deletes the dataset view active entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DatasetViewActiveEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param datasetViewActiveEntryId the primary key of the dataset view active entry
	 * @return the dataset view active entry that was removed
	 * @throws PortalException if a dataset view active entry with the primary key could not be found
	 */
	@Override
	public com.liferay.dataset.view.model.DatasetViewActiveEntry
			deleteDatasetViewActiveEntry(long datasetViewActiveEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _datasetViewActiveEntryLocalService.deleteDatasetViewActiveEntry(
			datasetViewActiveEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _datasetViewActiveEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _datasetViewActiveEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _datasetViewActiveEntryLocalService.dynamicQuery();
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

		return _datasetViewActiveEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dataset.view.model.impl.DatasetViewActiveEntryModelImpl</code>.
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

		return _datasetViewActiveEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dataset.view.model.impl.DatasetViewActiveEntryModelImpl</code>.
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

		return _datasetViewActiveEntryLocalService.dynamicQuery(
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

		return _datasetViewActiveEntryLocalService.dynamicQueryCount(
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

		return _datasetViewActiveEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.dataset.view.model.DatasetViewActiveEntry
		fetchDatasetViewActiveEntry(long datasetViewActiveEntryId) {

		return _datasetViewActiveEntryLocalService.fetchDatasetViewActiveEntry(
			datasetViewActiveEntryId);
	}

	/**
	 * Returns the dataset view active entry with the matching UUID and company.
	 *
	 * @param uuid the dataset view active entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching dataset view active entry, or <code>null</code> if a matching dataset view active entry could not be found
	 */
	@Override
	public com.liferay.dataset.view.model.DatasetViewActiveEntry
		fetchDatasetViewActiveEntryByUuidAndCompanyId(
			String uuid, long companyId) {

		return _datasetViewActiveEntryLocalService.
			fetchDatasetViewActiveEntryByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _datasetViewActiveEntryLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the dataset view active entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dataset.view.model.impl.DatasetViewActiveEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dataset view active entries
	 * @param end the upper bound of the range of dataset view active entries (not inclusive)
	 * @return the range of dataset view active entries
	 */
	@Override
	public java.util.List<com.liferay.dataset.view.model.DatasetViewActiveEntry>
		getDatasetViewActiveEntries(int start, int end) {

		return _datasetViewActiveEntryLocalService.getDatasetViewActiveEntries(
			start, end);
	}

	/**
	 * Returns the number of dataset view active entries.
	 *
	 * @return the number of dataset view active entries
	 */
	@Override
	public int getDatasetViewActiveEntriesCount() {
		return _datasetViewActiveEntryLocalService.
			getDatasetViewActiveEntriesCount();
	}

	/**
	 * Returns the dataset view active entry with the primary key.
	 *
	 * @param datasetViewActiveEntryId the primary key of the dataset view active entry
	 * @return the dataset view active entry
	 * @throws PortalException if a dataset view active entry with the primary key could not be found
	 */
	@Override
	public com.liferay.dataset.view.model.DatasetViewActiveEntry
			getDatasetViewActiveEntry(long datasetViewActiveEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _datasetViewActiveEntryLocalService.getDatasetViewActiveEntry(
			datasetViewActiveEntryId);
	}

	/**
	 * Returns the dataset view active entry with the matching UUID and company.
	 *
	 * @param uuid the dataset view active entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching dataset view active entry
	 * @throws PortalException if a matching dataset view active entry could not be found
	 */
	@Override
	public com.liferay.dataset.view.model.DatasetViewActiveEntry
			getDatasetViewActiveEntryByUuidAndCompanyId(
				String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _datasetViewActiveEntryLocalService.
			getDatasetViewActiveEntryByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _datasetViewActiveEntryLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _datasetViewActiveEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _datasetViewActiveEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _datasetViewActiveEntryLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the dataset view active entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DatasetViewActiveEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param datasetViewActiveEntry the dataset view active entry
	 * @return the dataset view active entry that was updated
	 */
	@Override
	public com.liferay.dataset.view.model.DatasetViewActiveEntry
		updateDatasetViewActiveEntry(
			com.liferay.dataset.view.model.DatasetViewActiveEntry
				datasetViewActiveEntry) {

		return _datasetViewActiveEntryLocalService.updateDatasetViewActiveEntry(
			datasetViewActiveEntry);
	}

	@Override
	public DatasetViewActiveEntryLocalService getWrappedService() {
		return _datasetViewActiveEntryLocalService;
	}

	@Override
	public void setWrappedService(
		DatasetViewActiveEntryLocalService datasetViewActiveEntryLocalService) {

		_datasetViewActiveEntryLocalService =
			datasetViewActiveEntryLocalService;
	}

	private DatasetViewActiveEntryLocalService
		_datasetViewActiveEntryLocalService;

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link DataLimitEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DataLimitEntryLocalService
 * @generated
 */
public class DataLimitEntryLocalServiceWrapper
	implements DataLimitEntryLocalService,
			   ServiceWrapper<DataLimitEntryLocalService> {

	public DataLimitEntryLocalServiceWrapper() {
		this(null);
	}

	public DataLimitEntryLocalServiceWrapper(
		DataLimitEntryLocalService dataLimitEntryLocalService) {

		_dataLimitEntryLocalService = dataLimitEntryLocalService;
	}

	/**
	 * Adds the data limit entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DataLimitEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param dataLimitEntry the data limit entry
	 * @return the data limit entry that was added
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.DataLimitEntry
		addDataLimitEntry(
			com.liferay.portal.tools.service.builder.test.model.DataLimitEntry
				dataLimitEntry) {

		return _dataLimitEntryLocalService.addDataLimitEntry(dataLimitEntry);
	}

	/**
	 * Creates a new data limit entry with the primary key. Does not add the data limit entry to the database.
	 *
	 * @param dataLimitEntryId the primary key for the new data limit entry
	 * @return the new data limit entry
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.DataLimitEntry
		createDataLimitEntry(long dataLimitEntryId) {

		return _dataLimitEntryLocalService.createDataLimitEntry(
			dataLimitEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dataLimitEntryLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the data limit entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DataLimitEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param dataLimitEntry the data limit entry
	 * @return the data limit entry that was removed
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.DataLimitEntry
		deleteDataLimitEntry(
			com.liferay.portal.tools.service.builder.test.model.DataLimitEntry
				dataLimitEntry) {

		return _dataLimitEntryLocalService.deleteDataLimitEntry(dataLimitEntry);
	}

	/**
	 * Deletes the data limit entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DataLimitEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param dataLimitEntryId the primary key of the data limit entry
	 * @return the data limit entry that was removed
	 * @throws PortalException if a data limit entry with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.DataLimitEntry
			deleteDataLimitEntry(long dataLimitEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dataLimitEntryLocalService.deleteDataLimitEntry(
			dataLimitEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dataLimitEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _dataLimitEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _dataLimitEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _dataLimitEntryLocalService.dynamicQuery();
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

		return _dataLimitEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.DataLimitEntryModelImpl</code>.
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

		return _dataLimitEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.DataLimitEntryModelImpl</code>.
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

		return _dataLimitEntryLocalService.dynamicQuery(
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

		return _dataLimitEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _dataLimitEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.DataLimitEntry
		fetchDataLimitEntry(long dataLimitEntryId) {

		return _dataLimitEntryLocalService.fetchDataLimitEntry(
			dataLimitEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _dataLimitEntryLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the data limit entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.DataLimitEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of data limit entries
	 * @param end the upper bound of the range of data limit entries (not inclusive)
	 * @return the range of data limit entries
	 */
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.DataLimitEntry>
			getDataLimitEntries(int start, int end) {

		return _dataLimitEntryLocalService.getDataLimitEntries(start, end);
	}

	/**
	 * Returns the number of data limit entries.
	 *
	 * @return the number of data limit entries
	 */
	@Override
	public int getDataLimitEntriesCount() {
		return _dataLimitEntryLocalService.getDataLimitEntriesCount();
	}

	/**
	 * Returns the data limit entry with the primary key.
	 *
	 * @param dataLimitEntryId the primary key of the data limit entry
	 * @return the data limit entry
	 * @throws PortalException if a data limit entry with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.DataLimitEntry
			getDataLimitEntry(long dataLimitEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dataLimitEntryLocalService.getDataLimitEntry(dataLimitEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _dataLimitEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _dataLimitEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dataLimitEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the data limit entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DataLimitEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param dataLimitEntry the data limit entry
	 * @return the data limit entry that was updated
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.DataLimitEntry
		updateDataLimitEntry(
			com.liferay.portal.tools.service.builder.test.model.DataLimitEntry
				dataLimitEntry) {

		return _dataLimitEntryLocalService.updateDataLimitEntry(dataLimitEntry);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _dataLimitEntryLocalService.getBasePersistence();
	}

	@Override
	public DataLimitEntryLocalService getWrappedService() {
		return _dataLimitEntryLocalService;
	}

	@Override
	public void setWrappedService(
		DataLimitEntryLocalService dataLimitEntryLocalService) {

		_dataLimitEntryLocalService = dataLimitEntryLocalService;
	}

	private DataLimitEntryLocalService _dataLimitEntryLocalService;

}
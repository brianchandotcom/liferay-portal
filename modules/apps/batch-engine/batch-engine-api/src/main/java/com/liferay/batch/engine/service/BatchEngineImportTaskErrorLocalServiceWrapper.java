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

package com.liferay.batch.engine.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link BatchEngineImportTaskErrorLocalService}.
 *
 * @author Shuyang Zhou
 * @see BatchEngineImportTaskErrorLocalService
 * @generated
 */
public class BatchEngineImportTaskErrorLocalServiceWrapper
	implements BatchEngineImportTaskErrorLocalService,
			   ServiceWrapper<BatchEngineImportTaskErrorLocalService> {

	public BatchEngineImportTaskErrorLocalServiceWrapper() {
		this(null);
	}

	public BatchEngineImportTaskErrorLocalServiceWrapper(
		BatchEngineImportTaskErrorLocalService
			batchEngineImportTaskErrorLocalService) {

		_batchEngineImportTaskErrorLocalService =
			batchEngineImportTaskErrorLocalService;
	}

	/**
	 * Adds the batch engine import task error to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BatchEngineImportTaskErrorLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param batchEngineImportTaskError the batch engine import task error
	 * @return the batch engine import task error that was added
	 */
	@Override
	public com.liferay.batch.engine.model.BatchEngineImportTaskError
		addBatchEngineImportTaskError(
			com.liferay.batch.engine.model.BatchEngineImportTaskError
				batchEngineImportTaskError) {

		return _batchEngineImportTaskErrorLocalService.
			addBatchEngineImportTaskError(batchEngineImportTaskError);
	}

	@Override
	public com.liferay.batch.engine.model.BatchEngineImportTaskError
		addBatchEngineImportTaskError(
			long companyId, long userId, long batchEngineImportTaskId,
			String item, int itemIndex, String message) {

		return _batchEngineImportTaskErrorLocalService.
			addBatchEngineImportTaskError(
				companyId, userId, batchEngineImportTaskId, item, itemIndex,
				message);
	}

	/**
	 * Creates a new batch engine import task error with the primary key. Does not add the batch engine import task error to the database.
	 *
	 * @param batchEngineImportTaskErrorId the primary key for the new batch engine import task error
	 * @return the new batch engine import task error
	 */
	@Override
	public com.liferay.batch.engine.model.BatchEngineImportTaskError
		createBatchEngineImportTaskError(long batchEngineImportTaskErrorId) {

		return _batchEngineImportTaskErrorLocalService.
			createBatchEngineImportTaskError(batchEngineImportTaskErrorId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchEngineImportTaskErrorLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the batch engine import task error from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BatchEngineImportTaskErrorLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param batchEngineImportTaskError the batch engine import task error
	 * @return the batch engine import task error that was removed
	 */
	@Override
	public com.liferay.batch.engine.model.BatchEngineImportTaskError
		deleteBatchEngineImportTaskError(
			com.liferay.batch.engine.model.BatchEngineImportTaskError
				batchEngineImportTaskError) {

		return _batchEngineImportTaskErrorLocalService.
			deleteBatchEngineImportTaskError(batchEngineImportTaskError);
	}

	/**
	 * Deletes the batch engine import task error with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BatchEngineImportTaskErrorLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param batchEngineImportTaskErrorId the primary key of the batch engine import task error
	 * @return the batch engine import task error that was removed
	 * @throws PortalException if a batch engine import task error with the primary key could not be found
	 */
	@Override
	public com.liferay.batch.engine.model.BatchEngineImportTaskError
			deleteBatchEngineImportTaskError(long batchEngineImportTaskErrorId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchEngineImportTaskErrorLocalService.
			deleteBatchEngineImportTaskError(batchEngineImportTaskErrorId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchEngineImportTaskErrorLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _batchEngineImportTaskErrorLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _batchEngineImportTaskErrorLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _batchEngineImportTaskErrorLocalService.dynamicQuery();
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

		return _batchEngineImportTaskErrorLocalService.dynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.batch.engine.model.impl.BatchEngineImportTaskErrorModelImpl</code>.
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

		return _batchEngineImportTaskErrorLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.batch.engine.model.impl.BatchEngineImportTaskErrorModelImpl</code>.
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

		return _batchEngineImportTaskErrorLocalService.dynamicQuery(
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

		return _batchEngineImportTaskErrorLocalService.dynamicQueryCount(
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

		return _batchEngineImportTaskErrorLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.batch.engine.model.BatchEngineImportTaskError
		fetchBatchEngineImportTaskError(long batchEngineImportTaskErrorId) {

		return _batchEngineImportTaskErrorLocalService.
			fetchBatchEngineImportTaskError(batchEngineImportTaskErrorId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _batchEngineImportTaskErrorLocalService.
			getActionableDynamicQuery();
	}

	/**
	 * Returns the batch engine import task error with the primary key.
	 *
	 * @param batchEngineImportTaskErrorId the primary key of the batch engine import task error
	 * @return the batch engine import task error
	 * @throws PortalException if a batch engine import task error with the primary key could not be found
	 */
	@Override
	public com.liferay.batch.engine.model.BatchEngineImportTaskError
			getBatchEngineImportTaskError(long batchEngineImportTaskErrorId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchEngineImportTaskErrorLocalService.
			getBatchEngineImportTaskError(batchEngineImportTaskErrorId);
	}

	/**
	 * Returns a range of all the batch engine import task errors.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.batch.engine.model.impl.BatchEngineImportTaskErrorModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch engine import task errors
	 * @param end the upper bound of the range of batch engine import task errors (not inclusive)
	 * @return the range of batch engine import task errors
	 */
	@Override
	public java.util.List
		<com.liferay.batch.engine.model.BatchEngineImportTaskError>
			getBatchEngineImportTaskErrors(int start, int end) {

		return _batchEngineImportTaskErrorLocalService.
			getBatchEngineImportTaskErrors(start, end);
	}

	@Override
	public java.util.List
		<com.liferay.batch.engine.model.BatchEngineImportTaskError>
			getBatchEngineImportTaskErrors(long batchEngineImportTaskId) {

		return _batchEngineImportTaskErrorLocalService.
			getBatchEngineImportTaskErrors(batchEngineImportTaskId);
	}

	/**
	 * Returns the number of batch engine import task errors.
	 *
	 * @return the number of batch engine import task errors
	 */
	@Override
	public int getBatchEngineImportTaskErrorsCount() {
		return _batchEngineImportTaskErrorLocalService.
			getBatchEngineImportTaskErrorsCount();
	}

	@Override
	public int getBatchEngineImportTaskErrorsCount(
		long batchEngineImportTaskId) {

		return _batchEngineImportTaskErrorLocalService.
			getBatchEngineImportTaskErrorsCount(batchEngineImportTaskId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _batchEngineImportTaskErrorLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _batchEngineImportTaskErrorLocalService.
			getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchEngineImportTaskErrorLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the batch engine import task error in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BatchEngineImportTaskErrorLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param batchEngineImportTaskError the batch engine import task error
	 * @return the batch engine import task error that was updated
	 */
	@Override
	public com.liferay.batch.engine.model.BatchEngineImportTaskError
		updateBatchEngineImportTaskError(
			com.liferay.batch.engine.model.BatchEngineImportTaskError
				batchEngineImportTaskError) {

		return _batchEngineImportTaskErrorLocalService.
			updateBatchEngineImportTaskError(batchEngineImportTaskError);
	}

	@Override
	public BatchEngineImportTaskErrorLocalService getWrappedService() {
		return _batchEngineImportTaskErrorLocalService;
	}

	@Override
	public void setWrappedService(
		BatchEngineImportTaskErrorLocalService
			batchEngineImportTaskErrorLocalService) {

		_batchEngineImportTaskErrorLocalService =
			batchEngineImportTaskErrorLocalService;
	}

	private BatchEngineImportTaskErrorLocalService
		_batchEngineImportTaskErrorLocalService;

}
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

package com.liferay.batch.planner.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link BatchPlannerLogLocalService}.
 *
 * @author Igor Beslic
 * @see BatchPlannerLogLocalService
 * @generated
 */
public class BatchPlannerLogLocalServiceWrapper
	implements BatchPlannerLogLocalService,
			   ServiceWrapper<BatchPlannerLogLocalService> {

	public BatchPlannerLogLocalServiceWrapper(
		BatchPlannerLogLocalService batchPlannerLogLocalService) {

		_batchPlannerLogLocalService = batchPlannerLogLocalService;
	}

	/**
	 * Adds the batch planner log to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BatchPlannerLogLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param batchPlannerLog the batch planner log
	 * @return the batch planner log that was added
	 */
	@Override
	public com.liferay.batch.planner.model.BatchPlannerLog addBatchPlannerLog(
		com.liferay.batch.planner.model.BatchPlannerLog batchPlannerLog) {

		return _batchPlannerLogLocalService.addBatchPlannerLog(batchPlannerLog);
	}

	/**
	 * Creates a new batch planner log with the primary key. Does not add the batch planner log to the database.
	 *
	 * @param batchPlannerLogId the primary key for the new batch planner log
	 * @return the new batch planner log
	 */
	@Override
	public com.liferay.batch.planner.model.BatchPlannerLog
		createBatchPlannerLog(long batchPlannerLogId) {

		return _batchPlannerLogLocalService.createBatchPlannerLog(
			batchPlannerLogId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerLogLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the batch planner log from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BatchPlannerLogLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param batchPlannerLog the batch planner log
	 * @return the batch planner log that was removed
	 */
	@Override
	public com.liferay.batch.planner.model.BatchPlannerLog
		deleteBatchPlannerLog(
			com.liferay.batch.planner.model.BatchPlannerLog batchPlannerLog) {

		return _batchPlannerLogLocalService.deleteBatchPlannerLog(
			batchPlannerLog);
	}

	/**
	 * Deletes the batch planner log with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BatchPlannerLogLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param batchPlannerLogId the primary key of the batch planner log
	 * @return the batch planner log that was removed
	 * @throws PortalException if a batch planner log with the primary key could not be found
	 */
	@Override
	public com.liferay.batch.planner.model.BatchPlannerLog
			deleteBatchPlannerLog(long batchPlannerLogId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerLogLocalService.deleteBatchPlannerLog(
			batchPlannerLogId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerLogLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _batchPlannerLogLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _batchPlannerLogLocalService.dynamicQuery();
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

		return _batchPlannerLogLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.batch.planner.model.impl.BatchPlannerLogModelImpl</code>.
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

		return _batchPlannerLogLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.batch.planner.model.impl.BatchPlannerLogModelImpl</code>.
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

		return _batchPlannerLogLocalService.dynamicQuery(
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

		return _batchPlannerLogLocalService.dynamicQueryCount(dynamicQuery);
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

		return _batchPlannerLogLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.batch.planner.model.BatchPlannerLog fetchBatchPlannerLog(
		long batchPlannerLogId) {

		return _batchPlannerLogLocalService.fetchBatchPlannerLog(
			batchPlannerLogId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _batchPlannerLogLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the batch planner log with the primary key.
	 *
	 * @param batchPlannerLogId the primary key of the batch planner log
	 * @return the batch planner log
	 * @throws PortalException if a batch planner log with the primary key could not be found
	 */
	@Override
	public com.liferay.batch.planner.model.BatchPlannerLog getBatchPlannerLog(
			long batchPlannerLogId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerLogLocalService.getBatchPlannerLog(
			batchPlannerLogId);
	}

	/**
	 * Returns a range of all the batch planner logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.batch.planner.model.impl.BatchPlannerLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch planner logs
	 * @param end the upper bound of the range of batch planner logs (not inclusive)
	 * @return the range of batch planner logs
	 */
	@Override
	public java.util.List<com.liferay.batch.planner.model.BatchPlannerLog>
		getBatchPlannerLogs(int start, int end) {

		return _batchPlannerLogLocalService.getBatchPlannerLogs(start, end);
	}

	/**
	 * Returns the number of batch planner logs.
	 *
	 * @return the number of batch planner logs
	 */
	@Override
	public int getBatchPlannerLogsCount() {
		return _batchPlannerLogLocalService.getBatchPlannerLogsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _batchPlannerLogLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _batchPlannerLogLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerLogLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the batch planner log in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BatchPlannerLogLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param batchPlannerLog the batch planner log
	 * @return the batch planner log that was updated
	 */
	@Override
	public com.liferay.batch.planner.model.BatchPlannerLog
		updateBatchPlannerLog(
			com.liferay.batch.planner.model.BatchPlannerLog batchPlannerLog) {

		return _batchPlannerLogLocalService.updateBatchPlannerLog(
			batchPlannerLog);
	}

	@Override
	public BatchPlannerLogLocalService getWrappedService() {
		return _batchPlannerLogLocalService;
	}

	@Override
	public void setWrappedService(
		BatchPlannerLogLocalService batchPlannerLogLocalService) {

		_batchPlannerLogLocalService = batchPlannerLogLocalService;
	}

	private BatchPlannerLogLocalService _batchPlannerLogLocalService;

}
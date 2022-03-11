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

import com.liferay.batch.planner.model.BatchPlannerLog;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for BatchPlannerLog. This utility wraps
 * <code>com.liferay.batch.planner.service.impl.BatchPlannerLogLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Igor Beslic
 * @see BatchPlannerLogLocalService
 * @generated
 */
public class BatchPlannerLogLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.batch.planner.service.impl.BatchPlannerLogLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

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
	public static BatchPlannerLog addBatchPlannerLog(
		BatchPlannerLog batchPlannerLog) {

		return getService().addBatchPlannerLog(batchPlannerLog);
	}

	public static BatchPlannerLog addBatchPlannerLog(
			long userId, long batchPlannerPlanId, String batchEngineExportERC,
			String batchEngineImportERC, String dispatchTriggerERC, int size,
			int status)
		throws PortalException {

		return getService().addBatchPlannerLog(
			userId, batchPlannerPlanId, batchEngineExportERC,
			batchEngineImportERC, dispatchTriggerERC, size, status);
	}

	/**
	 * Creates a new batch planner log with the primary key. Does not add the batch planner log to the database.
	 *
	 * @param batchPlannerLogId the primary key for the new batch planner log
	 * @return the new batch planner log
	 */
	public static BatchPlannerLog createBatchPlannerLog(
		long batchPlannerLogId) {

		return getService().createBatchPlannerLog(batchPlannerLogId);
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
	 * Deletes the batch planner log from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BatchPlannerLogLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param batchPlannerLog the batch planner log
	 * @return the batch planner log that was removed
	 */
	public static BatchPlannerLog deleteBatchPlannerLog(
		BatchPlannerLog batchPlannerLog) {

		return getService().deleteBatchPlannerLog(batchPlannerLog);
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
	public static BatchPlannerLog deleteBatchPlannerLog(long batchPlannerLogId)
		throws PortalException {

		return getService().deleteBatchPlannerLog(batchPlannerLogId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.batch.planner.model.impl.BatchPlannerLogModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.batch.planner.model.impl.BatchPlannerLogModelImpl</code>.
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

	public static BatchPlannerLog fetchBatchPlannerLog(long batchPlannerLogId) {
		return getService().fetchBatchPlannerLog(batchPlannerLogId);
	}

	public static BatchPlannerLog fetchBatchPlannerLog(
		String batchEngineTaskERC, boolean export) {

		return getService().fetchBatchPlannerLog(batchEngineTaskERC, export);
	}

	public static BatchPlannerLog fetchBatchPlannerPlanBatchPlannerLog(
		long batchPlannerPlanId) {

		return getService().fetchBatchPlannerPlanBatchPlannerLog(
			batchPlannerPlanId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the batch planner log with the primary key.
	 *
	 * @param batchPlannerLogId the primary key of the batch planner log
	 * @return the batch planner log
	 * @throws PortalException if a batch planner log with the primary key could not be found
	 */
	public static BatchPlannerLog getBatchPlannerLog(long batchPlannerLogId)
		throws PortalException {

		return getService().getBatchPlannerLog(batchPlannerLogId);
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
	public static List<BatchPlannerLog> getBatchPlannerLogs(
		int start, int end) {

		return getService().getBatchPlannerLogs(start, end);
	}

	/**
	 * Returns the number of batch planner logs.
	 *
	 * @return the number of batch planner logs
	 */
	public static int getBatchPlannerLogsCount() {
		return getService().getBatchPlannerLogsCount();
	}

	public static int getBatchPlannerLogsCount(long batchPlannerPlanId) {
		return getService().getBatchPlannerLogsCount(batchPlannerPlanId);
	}

	public static BatchPlannerLog getBatchPlannerPlanBatchPlannerLog(
			long batchPlannerPlanId)
		throws PortalException {

		return getService().getBatchPlannerPlanBatchPlannerLog(
			batchPlannerPlanId);
	}

	public static List<BatchPlannerLog> getCompanyBatchPlannerLogs(
		long companyId, boolean export, int start, int end,
		OrderByComparator<BatchPlannerLog> orderByComparator) {

		return getService().getCompanyBatchPlannerLogs(
			companyId, export, start, end, orderByComparator);
	}

	public static List<BatchPlannerLog> getCompanyBatchPlannerLogs(
		long companyId, boolean export, int start, int end,
		OrderByComparator<BatchPlannerLog> orderByComparator,
		String searchByField, String searchByKeyword) {

		return getService().getCompanyBatchPlannerLogs(
			companyId, export, start, end, orderByComparator, searchByField,
			searchByKeyword);
	}

	public static List<BatchPlannerLog> getCompanyBatchPlannerLogs(
		long companyId, int start, int end,
		OrderByComparator<BatchPlannerLog> orderByComparator) {

		return getService().getCompanyBatchPlannerLogs(
			companyId, start, end, orderByComparator);
	}

	public static List<BatchPlannerLog> getCompanyBatchPlannerLogs(
		long companyId, int start, int end,
		OrderByComparator<BatchPlannerLog> orderByComparator,
		String searchByField, String searchByKeyword) {

		return getService().getCompanyBatchPlannerLogs(
			companyId, start, end, orderByComparator, searchByField,
			searchByKeyword);
	}

	public static int getCompanyBatchPlannerLogsCount(long companyId) {
		return getService().getCompanyBatchPlannerLogsCount(companyId);
	}

	public static int getCompanyBatchPlannerLogsCount(
		long companyId, boolean export) {

		return getService().getCompanyBatchPlannerLogsCount(companyId, export);
	}

	public static int getCompanyBatchPlannerLogsCount(
		long companyId, boolean export, String searchByField,
		String searchByKeyword) {

		return getService().getCompanyBatchPlannerLogsCount(
			companyId, export, searchByField, searchByKeyword);
	}

	public static int getCompanyBatchPlannerLogsCount(
		long companyId, String searchByField, String searchByKeyword) {

		return getService().getCompanyBatchPlannerLogsCount(
			companyId, searchByField, searchByKeyword);
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
	 * Updates the batch planner log in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BatchPlannerLogLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param batchPlannerLog the batch planner log
	 * @return the batch planner log that was updated
	 */
	public static BatchPlannerLog updateBatchPlannerLog(
		BatchPlannerLog batchPlannerLog) {

		return getService().updateBatchPlannerLog(batchPlannerLog);
	}

	public static BatchPlannerLog updateBatchPlannerLogSize(
			long batchPlannerLogId, int size)
		throws PortalException {

		return getService().updateBatchPlannerLogSize(batchPlannerLogId, size);
	}

	public static BatchPlannerLog updateBatchPlannerLogStatus(
			long batchPlannerLogId, int status)
		throws PortalException {

		return getService().updateBatchPlannerLogStatus(
			batchPlannerLogId, status);
	}

	public static BatchPlannerLogLocalService getService() {
		return _service;
	}

	private static volatile BatchPlannerLogLocalService _service;

}
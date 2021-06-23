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

package com.liferay.dispatch.service;

import com.liferay.dispatch.model.DispatchLog;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for DispatchLog. This utility wraps
 * <code>com.liferay.dispatch.service.impl.DispatchLogLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Matija Petanjek
 * @see DispatchLogLocalService
 * @generated
 */
public class DispatchLogLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.dispatch.service.impl.DispatchLogLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the dispatch log to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DispatchLogLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param dispatchLog the dispatch log
	 * @return the dispatch log that was added
	 */
	public static DispatchLog addDispatchLog(DispatchLog dispatchLog) {
		return getService().addDispatchLog(dispatchLog);
	}

	public static DispatchLog addDispatchLog(
			long userId, long dispatchTriggerId, java.util.Date endDate,
			String error, String output, java.util.Date startDate,
			com.liferay.dispatch.executor.DispatchTaskStatus dispatchTaskStatus)
		throws PortalException {

		return getService().addDispatchLog(
			userId, dispatchTriggerId, endDate, error, output, startDate,
			dispatchTaskStatus);
	}

	/**
	 * Creates a new dispatch log with the primary key. Does not add the dispatch log to the database.
	 *
	 * @param dispatchLogId the primary key for the new dispatch log
	 * @return the new dispatch log
	 */
	public static DispatchLog createDispatchLog(long dispatchLogId) {
		return getService().createDispatchLog(dispatchLogId);
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
	 * Deletes the dispatch log from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DispatchLogLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param dispatchLog the dispatch log
	 * @return the dispatch log that was removed
	 */
	public static DispatchLog deleteDispatchLog(DispatchLog dispatchLog) {
		return getService().deleteDispatchLog(dispatchLog);
	}

	/**
	 * Deletes the dispatch log with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DispatchLogLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param dispatchLogId the primary key of the dispatch log
	 * @return the dispatch log that was removed
	 * @throws PortalException if a dispatch log with the primary key could not be found
	 */
	public static DispatchLog deleteDispatchLog(long dispatchLogId)
		throws PortalException {

		return getService().deleteDispatchLog(dispatchLogId);
	}

	public static void deleteDispatchLogs(long dispatchTriggerId) {
		getService().deleteDispatchLogs(dispatchTriggerId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dispatch.model.impl.DispatchLogModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dispatch.model.impl.DispatchLogModelImpl</code>.
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

	public static DispatchLog fetchDispatchLog(long dispatchLogId) {
		return getService().fetchDispatchLog(dispatchLogId);
	}

	public static DispatchLog fetchLatestDispatchLog(long dispatchTriggerId) {
		return getService().fetchLatestDispatchLog(dispatchTriggerId);
	}

	public static DispatchLog fetchLatestDispatchLog(
		long dispatchTriggerId,
		com.liferay.dispatch.executor.DispatchTaskStatus dispatchTaskStatus) {

		return getService().fetchLatestDispatchLog(
			dispatchTriggerId, dispatchTaskStatus);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the dispatch log with the primary key.
	 *
	 * @param dispatchLogId the primary key of the dispatch log
	 * @return the dispatch log
	 * @throws PortalException if a dispatch log with the primary key could not be found
	 */
	public static DispatchLog getDispatchLog(long dispatchLogId)
		throws PortalException {

		return getService().getDispatchLog(dispatchLogId);
	}

	/**
	 * Returns a range of all the dispatch logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dispatch.model.impl.DispatchLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dispatch logs
	 * @param end the upper bound of the range of dispatch logs (not inclusive)
	 * @return the range of dispatch logs
	 */
	public static List<DispatchLog> getDispatchLogs(int start, int end) {
		return getService().getDispatchLogs(start, end);
	}

	public static List<DispatchLog> getDispatchLogs(
		long dispatchTriggerId, int start, int end) {

		return getService().getDispatchLogs(dispatchTriggerId, start, end);
	}

	public static List<DispatchLog> getDispatchLogs(
		long dispatchTriggerId, int start, int end,
		OrderByComparator<DispatchLog> orderByComparator) {

		return getService().getDispatchLogs(
			dispatchTriggerId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of dispatch logs.
	 *
	 * @return the number of dispatch logs
	 */
	public static int getDispatchLogsCount() {
		return getService().getDispatchLogsCount();
	}

	public static int getDispatchLogsCount(long dispatchTriggerId) {
		return getService().getDispatchLogsCount(dispatchTriggerId);
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
	 * Updates the dispatch log in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DispatchLogLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param dispatchLog the dispatch log
	 * @return the dispatch log that was updated
	 */
	public static DispatchLog updateDispatchLog(DispatchLog dispatchLog) {
		return getService().updateDispatchLog(dispatchLog);
	}

	public static DispatchLog updateDispatchLog(
			long dispatchLogId, java.util.Date endDate, String error,
			String output,
			com.liferay.dispatch.executor.DispatchTaskStatus dispatchTaskStatus)
		throws PortalException {

		return getService().updateDispatchLog(
			dispatchLogId, endDate, error, output, dispatchTaskStatus);
	}

	public static DispatchLogLocalService getService() {
		return _service;
	}

	private static volatile DispatchLogLocalService _service;

}
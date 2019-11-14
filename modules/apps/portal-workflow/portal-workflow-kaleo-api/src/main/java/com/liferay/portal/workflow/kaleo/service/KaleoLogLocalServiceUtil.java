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

package com.liferay.portal.workflow.kaleo.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for KaleoLog. This utility wraps
 * <code>com.liferay.portal.workflow.kaleo.service.impl.KaleoLogLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoLogLocalService
 * @generated
 */
public class KaleoLogLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.workflow.kaleo.service.impl.KaleoLogLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link KaleoLogLocalServiceUtil} to access the kaleo log local service. Add custom service methods to <code>com.liferay.portal.workflow.kaleo.service.impl.KaleoLogLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoLog
			addActionExecutionKaleoLog(
				com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken
					kaleoInstanceToken,
				com.liferay.portal.workflow.kaleo.model.KaleoAction kaleoAction,
				long startTime, long endTime, String comment,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addActionExecutionKaleoLog(
			kaleoInstanceToken, kaleoAction, startTime, endTime, comment,
			serviceContext);
	}

	/**
	 * Adds the kaleo log to the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoLog the kaleo log
	 * @return the kaleo log that was added
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoLog addKaleoLog(
		com.liferay.portal.workflow.kaleo.model.KaleoLog kaleoLog) {

		return getService().addKaleoLog(kaleoLog);
	}

	public static com.liferay.portal.workflow.kaleo.model.KaleoLog
			addNodeEntryKaleoLog(
				com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken
					kaleoInstanceToken,
				com.liferay.portal.workflow.kaleo.model.KaleoNode
					sourceKaleoNode,
				com.liferay.portal.workflow.kaleo.model.KaleoNode
					targetKaleoNode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addNodeEntryKaleoLog(
			kaleoInstanceToken, sourceKaleoNode, targetKaleoNode,
			serviceContext);
	}

	public static com.liferay.portal.workflow.kaleo.model.KaleoLog
			addNodeExitKaleoLog(
				com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken
					kaleoInstanceToken,
				com.liferay.portal.workflow.kaleo.model.KaleoNode
					departingKaleoNode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addNodeExitKaleoLog(
			kaleoInstanceToken, departingKaleoNode, serviceContext);
	}

	public static com.liferay.portal.workflow.kaleo.model.KaleoLog
			addTaskAssignmentKaleoLog(
				java.util.List
					<com.liferay.portal.workflow.kaleo.model.
						KaleoTaskAssignmentInstance>
							previousKaleoTaskAssignmentInstances,
				com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken
					kaleoTaskInstanceToken,
				String comment,
				java.util.Map<String, java.io.Serializable> workflowContext,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addTaskAssignmentKaleoLog(
			previousKaleoTaskAssignmentInstances, kaleoTaskInstanceToken,
			comment, workflowContext, serviceContext);
	}

	public static com.liferay.portal.workflow.kaleo.model.KaleoLog
			addTaskCompletionKaleoLog(
				com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken
					kaleoTaskInstanceToken,
				String comment,
				java.util.Map<String, java.io.Serializable> workflowContext,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addTaskCompletionKaleoLog(
			kaleoTaskInstanceToken, comment, workflowContext, serviceContext);
	}

	public static com.liferay.portal.workflow.kaleo.model.KaleoLog
			addTaskUpdateKaleoLog(
				com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken
					kaleoTaskInstanceToken,
				String comment,
				java.util.Map<String, java.io.Serializable> workflowContext,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addTaskUpdateKaleoLog(
			kaleoTaskInstanceToken, comment, workflowContext, serviceContext);
	}

	public static com.liferay.portal.workflow.kaleo.model.KaleoLog
			addWorkflowInstanceEndKaleoLog(
				com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken
					kaleoInstanceToken,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addWorkflowInstanceEndKaleoLog(
			kaleoInstanceToken, serviceContext);
	}

	public static com.liferay.portal.workflow.kaleo.model.KaleoLog
			addWorkflowInstanceStartKaleoLog(
				com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken
					kaleoInstanceToken,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addWorkflowInstanceStartKaleoLog(
			kaleoInstanceToken, serviceContext);
	}

	/**
	 * Creates a new kaleo log with the primary key. Does not add the kaleo log to the database.
	 *
	 * @param kaleoLogId the primary key for the new kaleo log
	 * @return the new kaleo log
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoLog
		createKaleoLog(long kaleoLogId) {

		return getService().createKaleoLog(kaleoLogId);
	}

	public static void deleteCompanyKaleoLogs(long companyId) {
		getService().deleteCompanyKaleoLogs(companyId);
	}

	public static void deleteKaleoDefinitionVersionKaleoLogs(
		long kaleoDefinitionVersionId) {

		getService().deleteKaleoDefinitionVersionKaleoLogs(
			kaleoDefinitionVersionId);
	}

	public static void deleteKaleoInstanceKaleoLogs(long kaleoInstanceId) {
		getService().deleteKaleoInstanceKaleoLogs(kaleoInstanceId);
	}

	/**
	 * Deletes the kaleo log from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoLog the kaleo log
	 * @return the kaleo log that was removed
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoLog
		deleteKaleoLog(
			com.liferay.portal.workflow.kaleo.model.KaleoLog kaleoLog) {

		return getService().deleteKaleoLog(kaleoLog);
	}

	/**
	 * Deletes the kaleo log with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoLogId the primary key of the kaleo log
	 * @return the kaleo log that was removed
	 * @throws PortalException if a kaleo log with the primary key could not be found
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoLog
			deleteKaleoLog(long kaleoLogId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteKaleoLog(kaleoLogId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoLogModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoLogModelImpl</code>.
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

	public static com.liferay.portal.workflow.kaleo.model.KaleoLog
		fetchKaleoLog(long kaleoLogId) {

		return getService().fetchKaleoLog(kaleoLogId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #getKaleoInstanceKaleoLogs(long, long, List, int, int,
	 OrderByComparator)}
	 */
	@Deprecated
	public static java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoLog>
			getKaleoInstanceKaleoLogs(
				long kaleoInstanceId, java.util.List<Integer> logTypes,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.workflow.kaleo.model.KaleoLog>
						orderByComparator) {

		return getService().getKaleoInstanceKaleoLogs(
			kaleoInstanceId, logTypes, start, end, orderByComparator);
	}

	public static java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoLog>
			getKaleoInstanceKaleoLogs(
				long companyId, long kaleoInstanceId,
				java.util.List<Integer> logTypes, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.workflow.kaleo.model.KaleoLog>
						orderByComparator) {

		return getService().getKaleoInstanceKaleoLogs(
			companyId, kaleoInstanceId, logTypes, start, end,
			orderByComparator);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #getKaleoInstanceKaleoLogsCount(long, long, List)}
	 */
	@Deprecated
	public static int getKaleoInstanceKaleoLogsCount(
		long kaleoInstanceId, java.util.List<Integer> logTypes) {

		return getService().getKaleoInstanceKaleoLogsCount(
			kaleoInstanceId, logTypes);
	}

	public static int getKaleoInstanceKaleoLogsCount(
		long companyId, long kaleoInstanceId,
		java.util.List<Integer> logTypes) {

		return getService().getKaleoInstanceKaleoLogsCount(
			companyId, kaleoInstanceId, logTypes);
	}

	/**
	 * Returns the kaleo log with the primary key.
	 *
	 * @param kaleoLogId the primary key of the kaleo log
	 * @return the kaleo log
	 * @throws PortalException if a kaleo log with the primary key could not be found
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoLog getKaleoLog(
			long kaleoLogId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getKaleoLog(kaleoLogId);
	}

	/**
	 * Returns a range of all the kaleo logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @return the range of kaleo logs
	 */
	public static java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoLog> getKaleoLogs(
			int start, int end) {

		return getService().getKaleoLogs(start, end);
	}

	/**
	 * Returns the number of kaleo logs.
	 *
	 * @return the number of kaleo logs
	 */
	public static int getKaleoLogsCount() {
		return getService().getKaleoLogsCount();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #getKaleoTaskInstanceTokenKaleoLogs(long, long, List, int,
	 int, OrderByComparator)}
	 */
	@Deprecated
	public static java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoLog>
			getKaleoTaskInstanceTokenKaleoLogs(
				long kaleoTaskInstanceTokenId, java.util.List<Integer> logTypes,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.workflow.kaleo.model.KaleoLog>
						orderByComparator) {

		return getService().getKaleoTaskInstanceTokenKaleoLogs(
			kaleoTaskInstanceTokenId, logTypes, start, end, orderByComparator);
	}

	public static java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoLog>
			getKaleoTaskInstanceTokenKaleoLogs(
				long companyId, long kaleoTaskInstanceTokenId,
				java.util.List<Integer> logTypes, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.workflow.kaleo.model.KaleoLog>
						orderByComparator) {

		return getService().getKaleoTaskInstanceTokenKaleoLogs(
			companyId, kaleoTaskInstanceTokenId, logTypes, start, end,
			orderByComparator);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #getKaleoTaskInstanceTokenKaleoLogsCount(long, long, List)}
	 */
	@Deprecated
	public static int getKaleoTaskInstanceTokenKaleoLogsCount(
		long kaleoTaskInstanceTokenId, java.util.List<Integer> logTypes) {

		return getService().getKaleoTaskInstanceTokenKaleoLogsCount(
			kaleoTaskInstanceTokenId, logTypes);
	}

	public static int getKaleoTaskInstanceTokenKaleoLogsCount(
		long companyId, long kaleoTaskInstanceTokenId,
		java.util.List<Integer> logTypes) {

		return getService().getKaleoTaskInstanceTokenKaleoLogsCount(
			companyId, kaleoTaskInstanceTokenId, logTypes);
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
	 * Updates the kaleo log in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoLog the kaleo log
	 * @return the kaleo log that was updated
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoLog
		updateKaleoLog(
			com.liferay.portal.workflow.kaleo.model.KaleoLog kaleoLog) {

		return getService().updateKaleoLog(kaleoLog);
	}

	public static KaleoLogLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<KaleoLogLocalService, KaleoLogLocalService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(KaleoLogLocalService.class);

		ServiceTracker<KaleoLogLocalService, KaleoLogLocalService>
			serviceTracker =
				new ServiceTracker<KaleoLogLocalService, KaleoLogLocalService>(
					bundle.getBundleContext(), KaleoLogLocalService.class,
					null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}
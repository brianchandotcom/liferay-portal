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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link KaleoTaskAssignmentInstanceLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTaskAssignmentInstanceLocalService
 * @generated
 */
public class KaleoTaskAssignmentInstanceLocalServiceWrapper
	implements KaleoTaskAssignmentInstanceLocalService,
			   ServiceWrapper<KaleoTaskAssignmentInstanceLocalService> {

	public KaleoTaskAssignmentInstanceLocalServiceWrapper(
		KaleoTaskAssignmentInstanceLocalService
			kaleoTaskAssignmentInstanceLocalService) {

		_kaleoTaskAssignmentInstanceLocalService =
			kaleoTaskAssignmentInstanceLocalService;
	}

	/**
	 * Adds the kaleo task assignment instance to the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTaskAssignmentInstance the kaleo task assignment instance
	 * @return the kaleo task assignment instance that was added
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance
		addKaleoTaskAssignmentInstance(
			com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance
				kaleoTaskAssignmentInstance) {

		return _kaleoTaskAssignmentInstanceLocalService.
			addKaleoTaskAssignmentInstance(kaleoTaskAssignmentInstance);
	}

	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance
			addKaleoTaskAssignmentInstance(
				long groupId,
				com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken
					kaleoTaskInstanceToken,
				String assigneeClassName, long assigneeClassPK,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskAssignmentInstanceLocalService.
			addKaleoTaskAssignmentInstance(
				groupId, kaleoTaskInstanceToken, assigneeClassName,
				assigneeClassPK, serviceContext);
	}

	@Override
	public java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance>
				addTaskAssignmentInstances(
					com.liferay.portal.workflow.kaleo.model.
						KaleoTaskInstanceToken kaleoTaskInstanceToken,
					java.util.Collection
						<com.liferay.portal.workflow.kaleo.model.
							KaleoTaskAssignment> kaleoTaskAssignments,
					java.util.Map<String, java.io.Serializable> workflowContext,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskAssignmentInstanceLocalService.
			addTaskAssignmentInstances(
				kaleoTaskInstanceToken, kaleoTaskAssignments, workflowContext,
				serviceContext);
	}

	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance
			assignKaleoTaskAssignmentInstance(
				com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken
					kaleoTaskInstanceToken,
				String assigneeClassName, long assigneeClassPK,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskAssignmentInstanceLocalService.
			assignKaleoTaskAssignmentInstance(
				kaleoTaskInstanceToken, assigneeClassName, assigneeClassPK,
				serviceContext);
	}

	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance
			completeKaleoTaskInstanceToken(
				long kaleoTaskInstanceTokenId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskAssignmentInstanceLocalService.
			completeKaleoTaskInstanceToken(
				kaleoTaskInstanceTokenId, serviceContext);
	}

	/**
	 * Creates a new kaleo task assignment instance with the primary key. Does not add the kaleo task assignment instance to the database.
	 *
	 * @param kaleoTaskAssignmentInstanceId the primary key for the new kaleo task assignment instance
	 * @return the new kaleo task assignment instance
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance
		createKaleoTaskAssignmentInstance(long kaleoTaskAssignmentInstanceId) {

		return _kaleoTaskAssignmentInstanceLocalService.
			createKaleoTaskAssignmentInstance(kaleoTaskAssignmentInstanceId);
	}

	@Override
	public void deleteCompanyKaleoTaskAssignmentInstances(long companyId) {
		_kaleoTaskAssignmentInstanceLocalService.
			deleteCompanyKaleoTaskAssignmentInstances(companyId);
	}

	@Override
	public void deleteKaleoDefinitionVersionKaleoTaskAssignmentInstances(
		long kaleoDefinitionId) {

		_kaleoTaskAssignmentInstanceLocalService.
			deleteKaleoDefinitionVersionKaleoTaskAssignmentInstances(
				kaleoDefinitionId);
	}

	@Override
	public void deleteKaleoInstanceKaleoTaskAssignmentInstances(
		long kaleoInstanceId) {

		_kaleoTaskAssignmentInstanceLocalService.
			deleteKaleoInstanceKaleoTaskAssignmentInstances(kaleoInstanceId);
	}

	/**
	 * Deletes the kaleo task assignment instance from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTaskAssignmentInstance the kaleo task assignment instance
	 * @return the kaleo task assignment instance that was removed
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance
		deleteKaleoTaskAssignmentInstance(
			com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance
				kaleoTaskAssignmentInstance) {

		return _kaleoTaskAssignmentInstanceLocalService.
			deleteKaleoTaskAssignmentInstance(kaleoTaskAssignmentInstance);
	}

	/**
	 * Deletes the kaleo task assignment instance with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTaskAssignmentInstanceId the primary key of the kaleo task assignment instance
	 * @return the kaleo task assignment instance that was removed
	 * @throws PortalException if a kaleo task assignment instance with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance
			deleteKaleoTaskAssignmentInstance(
				long kaleoTaskAssignmentInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskAssignmentInstanceLocalService.
			deleteKaleoTaskAssignmentInstance(kaleoTaskAssignmentInstanceId);
	}

	@Override
	public void deleteKaleoTaskAssignmentInstances(
		com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken
			kaleoTaskInstanceToken) {

		_kaleoTaskAssignmentInstanceLocalService.
			deleteKaleoTaskAssignmentInstances(kaleoTaskInstanceToken);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskAssignmentInstanceLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _kaleoTaskAssignmentInstanceLocalService.dynamicQuery();
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

		return _kaleoTaskAssignmentInstanceLocalService.dynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskAssignmentInstanceModelImpl</code>.
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

		return _kaleoTaskAssignmentInstanceLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskAssignmentInstanceModelImpl</code>.
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

		return _kaleoTaskAssignmentInstanceLocalService.dynamicQuery(
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

		return _kaleoTaskAssignmentInstanceLocalService.dynamicQueryCount(
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

		return _kaleoTaskAssignmentInstanceLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance
		fetchFirstKaleoTaskAssignmentInstance(
			long kaleoTaskInstanceTokenId,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.workflow.kaleo.model.
					KaleoTaskAssignmentInstance> orderByComparator) {

		return _kaleoTaskAssignmentInstanceLocalService.
			fetchFirstKaleoTaskAssignmentInstance(
				kaleoTaskInstanceTokenId, orderByComparator);
	}

	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance
		fetchFirstKaleoTaskAssignmentInstance(
			long kaleoTaskInstanceTokenId, String assigneeClassName,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.workflow.kaleo.model.
					KaleoTaskAssignmentInstance> orderByComparator) {

		return _kaleoTaskAssignmentInstanceLocalService.
			fetchFirstKaleoTaskAssignmentInstance(
				kaleoTaskInstanceTokenId, assigneeClassName, orderByComparator);
	}

	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance
		fetchKaleoTaskAssignmentInstance(long kaleoTaskAssignmentInstanceId) {

		return _kaleoTaskAssignmentInstanceLocalService.
			fetchKaleoTaskAssignmentInstance(kaleoTaskAssignmentInstanceId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _kaleoTaskAssignmentInstanceLocalService.
			getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _kaleoTaskAssignmentInstanceLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the kaleo task assignment instance with the primary key.
	 *
	 * @param kaleoTaskAssignmentInstanceId the primary key of the kaleo task assignment instance
	 * @return the kaleo task assignment instance
	 * @throws PortalException if a kaleo task assignment instance with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance
			getKaleoTaskAssignmentInstance(long kaleoTaskAssignmentInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskAssignmentInstanceLocalService.
			getKaleoTaskAssignmentInstance(kaleoTaskAssignmentInstanceId);
	}

	/**
	 * Returns a range of all the kaleo task assignment instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @return the range of kaleo task assignment instances
	 */
	@Override
	public java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance>
			getKaleoTaskAssignmentInstances(int start, int end) {

		return _kaleoTaskAssignmentInstanceLocalService.
			getKaleoTaskAssignmentInstances(start, end);
	}

	@Override
	public java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance>
			getKaleoTaskAssignmentInstances(long kaleoTaskInstanceTokenId) {

		return _kaleoTaskAssignmentInstanceLocalService.
			getKaleoTaskAssignmentInstances(kaleoTaskInstanceTokenId);
	}

	/**
	 * Returns the number of kaleo task assignment instances.
	 *
	 * @return the number of kaleo task assignment instances
	 */
	@Override
	public int getKaleoTaskAssignmentInstancesCount() {
		return _kaleoTaskAssignmentInstanceLocalService.
			getKaleoTaskAssignmentInstancesCount();
	}

	@Override
	public int getKaleoTaskAssignmentInstancesCount(
		long kaleoTaskInstanceTokenId) {

		return _kaleoTaskAssignmentInstanceLocalService.
			getKaleoTaskAssignmentInstancesCount(kaleoTaskInstanceTokenId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _kaleoTaskAssignmentInstanceLocalService.
			getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskAssignmentInstanceLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the kaleo task assignment instance in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTaskAssignmentInstance the kaleo task assignment instance
	 * @return the kaleo task assignment instance that was updated
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance
		updateKaleoTaskAssignmentInstance(
			com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance
				kaleoTaskAssignmentInstance) {

		return _kaleoTaskAssignmentInstanceLocalService.
			updateKaleoTaskAssignmentInstance(kaleoTaskAssignmentInstance);
	}

	@Override
	public KaleoTaskAssignmentInstanceLocalService getWrappedService() {
		return _kaleoTaskAssignmentInstanceLocalService;
	}

	@Override
	public void setWrappedService(
		KaleoTaskAssignmentInstanceLocalService
			kaleoTaskAssignmentInstanceLocalService) {

		_kaleoTaskAssignmentInstanceLocalService =
			kaleoTaskAssignmentInstanceLocalService;
	}

	private KaleoTaskAssignmentInstanceLocalService
		_kaleoTaskAssignmentInstanceLocalService;

}
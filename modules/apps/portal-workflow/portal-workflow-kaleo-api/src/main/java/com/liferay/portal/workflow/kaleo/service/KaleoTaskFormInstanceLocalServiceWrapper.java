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
 * Provides a wrapper for {@link KaleoTaskFormInstanceLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTaskFormInstanceLocalService
 * @generated
 */
public class KaleoTaskFormInstanceLocalServiceWrapper
	implements KaleoTaskFormInstanceLocalService,
			   ServiceWrapper<KaleoTaskFormInstanceLocalService> {

	public KaleoTaskFormInstanceLocalServiceWrapper(
		KaleoTaskFormInstanceLocalService kaleoTaskFormInstanceLocalService) {

		_kaleoTaskFormInstanceLocalService = kaleoTaskFormInstanceLocalService;
	}

	/**
	 * Adds the kaleo task form instance to the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTaskFormInstance the kaleo task form instance
	 * @return the kaleo task form instance that was added
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskFormInstance
		addKaleoTaskFormInstance(
			com.liferay.portal.workflow.kaleo.model.KaleoTaskFormInstance
				kaleoTaskFormInstance) {

		return _kaleoTaskFormInstanceLocalService.addKaleoTaskFormInstance(
			kaleoTaskFormInstance);
	}

	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskFormInstance
			addKaleoTaskFormInstance(
				long groupId, long kaleoTaskFormId, String formValues,
				com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken
					kaleoTaskInstanceToken,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskFormInstanceLocalService.addKaleoTaskFormInstance(
			groupId, kaleoTaskFormId, formValues, kaleoTaskInstanceToken,
			serviceContext);
	}

	@Override
	public int countKaleoTaskFormInstanceByKaleoTaskId(long kaleoTaskId) {
		return _kaleoTaskFormInstanceLocalService.
			countKaleoTaskFormInstanceByKaleoTaskId(kaleoTaskId);
	}

	/**
	 * Creates a new kaleo task form instance with the primary key. Does not add the kaleo task form instance to the database.
	 *
	 * @param kaleoTaskFormInstanceId the primary key for the new kaleo task form instance
	 * @return the new kaleo task form instance
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskFormInstance
		createKaleoTaskFormInstance(long kaleoTaskFormInstanceId) {

		return _kaleoTaskFormInstanceLocalService.createKaleoTaskFormInstance(
			kaleoTaskFormInstanceId);
	}

	@Override
	public void deleteCompanyKaleoTaskFormInstances(long companyId) {
		_kaleoTaskFormInstanceLocalService.deleteCompanyKaleoTaskFormInstances(
			companyId);
	}

	@Override
	public void deleteKaleoDefinitionVersionKaleoTaskFormInstances(
		long kaleoDefinitionVersionId) {

		_kaleoTaskFormInstanceLocalService.
			deleteKaleoDefinitionVersionKaleoTaskFormInstances(
				kaleoDefinitionVersionId);
	}

	@Override
	public void deleteKaleoInstanceKaleoTaskFormInstances(
		long kaleoInstanceId) {

		_kaleoTaskFormInstanceLocalService.
			deleteKaleoInstanceKaleoTaskFormInstances(kaleoInstanceId);
	}

	/**
	 * Deletes the kaleo task form instance from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTaskFormInstance the kaleo task form instance
	 * @return the kaleo task form instance that was removed
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskFormInstance
		deleteKaleoTaskFormInstance(
			com.liferay.portal.workflow.kaleo.model.KaleoTaskFormInstance
				kaleoTaskFormInstance) {

		return _kaleoTaskFormInstanceLocalService.deleteKaleoTaskFormInstance(
			kaleoTaskFormInstance);
	}

	/**
	 * Deletes the kaleo task form instance with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTaskFormInstanceId the primary key of the kaleo task form instance
	 * @return the kaleo task form instance that was removed
	 * @throws PortalException if a kaleo task form instance with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskFormInstance
			deleteKaleoTaskFormInstance(long kaleoTaskFormInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskFormInstanceLocalService.deleteKaleoTaskFormInstance(
			kaleoTaskFormInstanceId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskFormInstanceLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _kaleoTaskFormInstanceLocalService.dynamicQuery();
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

		return _kaleoTaskFormInstanceLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskFormInstanceModelImpl</code>.
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

		return _kaleoTaskFormInstanceLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskFormInstanceModelImpl</code>.
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

		return _kaleoTaskFormInstanceLocalService.dynamicQuery(
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

		return _kaleoTaskFormInstanceLocalService.dynamicQueryCount(
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

		return _kaleoTaskFormInstanceLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskFormInstance
		fetchKaleoTaskFormInstance(long kaleoTaskFormInstanceId) {

		return _kaleoTaskFormInstanceLocalService.fetchKaleoTaskFormInstance(
			kaleoTaskFormInstanceId);
	}

	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskFormInstance
		fetchKaleoTaskFormKaleoTaskFormInstance(long kaleoTaskFormId) {

		return _kaleoTaskFormInstanceLocalService.
			fetchKaleoTaskFormKaleoTaskFormInstance(kaleoTaskFormId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _kaleoTaskFormInstanceLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _kaleoTaskFormInstanceLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the kaleo task form instance with the primary key.
	 *
	 * @param kaleoTaskFormInstanceId the primary key of the kaleo task form instance
	 * @return the kaleo task form instance
	 * @throws PortalException if a kaleo task form instance with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskFormInstance
			getKaleoTaskFormInstance(long kaleoTaskFormInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskFormInstanceLocalService.getKaleoTaskFormInstance(
			kaleoTaskFormInstanceId);
	}

	/**
	 * Returns a range of all the kaleo task form instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskFormInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo task form instances
	 * @param end the upper bound of the range of kaleo task form instances (not inclusive)
	 * @return the range of kaleo task form instances
	 */
	@Override
	public java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoTaskFormInstance>
			getKaleoTaskFormInstances(int start, int end) {

		return _kaleoTaskFormInstanceLocalService.getKaleoTaskFormInstances(
			start, end);
	}

	/**
	 * Returns the number of kaleo task form instances.
	 *
	 * @return the number of kaleo task form instances
	 */
	@Override
	public int getKaleoTaskFormInstancesCount() {
		return _kaleoTaskFormInstanceLocalService.
			getKaleoTaskFormInstancesCount();
	}

	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskFormInstance
			getKaleoTaskFormKaleoTaskFormInstance(long kaleoTaskFormId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskFormInstanceLocalService.
			getKaleoTaskFormKaleoTaskFormInstance(kaleoTaskFormId);
	}

	@Override
	public java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoTaskFormInstance>
			getKaleoTaskKaleoTaskFormInstances(long kaleoTaskId) {

		return _kaleoTaskFormInstanceLocalService.
			getKaleoTaskKaleoTaskFormInstances(kaleoTaskId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _kaleoTaskFormInstanceLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskFormInstanceLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the kaleo task form instance in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTaskFormInstance the kaleo task form instance
	 * @return the kaleo task form instance that was updated
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskFormInstance
		updateKaleoTaskFormInstance(
			com.liferay.portal.workflow.kaleo.model.KaleoTaskFormInstance
				kaleoTaskFormInstance) {

		return _kaleoTaskFormInstanceLocalService.updateKaleoTaskFormInstance(
			kaleoTaskFormInstance);
	}

	@Override
	public KaleoTaskFormInstanceLocalService getWrappedService() {
		return _kaleoTaskFormInstanceLocalService;
	}

	@Override
	public void setWrappedService(
		KaleoTaskFormInstanceLocalService kaleoTaskFormInstanceLocalService) {

		_kaleoTaskFormInstanceLocalService = kaleoTaskFormInstanceLocalService;
	}

	private KaleoTaskFormInstanceLocalService
		_kaleoTaskFormInstanceLocalService;

}
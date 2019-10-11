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
 * Provides a wrapper for {@link KaleoInstanceLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoInstanceLocalService
 * @generated
 */
public class KaleoInstanceLocalServiceWrapper
	implements KaleoInstanceLocalService,
			   ServiceWrapper<KaleoInstanceLocalService> {

	public KaleoInstanceLocalServiceWrapper(
		KaleoInstanceLocalService kaleoInstanceLocalService) {

		_kaleoInstanceLocalService = kaleoInstanceLocalService;
	}

	/**
	 * Adds the kaleo instance to the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoInstance the kaleo instance
	 * @return the kaleo instance that was added
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoInstance
		addKaleoInstance(
			com.liferay.portal.workflow.kaleo.model.KaleoInstance
				kaleoInstance) {

		return _kaleoInstanceLocalService.addKaleoInstance(kaleoInstance);
	}

	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoInstance
			addKaleoInstance(
				long kaleoDefinitionVersionId, String kaleoDefinitionName,
				int kaleoDefinitionVersion,
				java.util.Map<String, java.io.Serializable> workflowContext,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoInstanceLocalService.addKaleoInstance(
			kaleoDefinitionVersionId, kaleoDefinitionName,
			kaleoDefinitionVersion, workflowContext, serviceContext);
	}

	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoInstance
			completeKaleoInstance(long kaleoInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoInstanceLocalService.completeKaleoInstance(
			kaleoInstanceId);
	}

	/**
	 * Creates a new kaleo instance with the primary key. Does not add the kaleo instance to the database.
	 *
	 * @param kaleoInstanceId the primary key for the new kaleo instance
	 * @return the new kaleo instance
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoInstance
		createKaleoInstance(long kaleoInstanceId) {

		return _kaleoInstanceLocalService.createKaleoInstance(kaleoInstanceId);
	}

	@Override
	public void deleteCompanyKaleoInstances(long companyId) {
		_kaleoInstanceLocalService.deleteCompanyKaleoInstances(companyId);
	}

	@Override
	public void deleteKaleoDefinitionVersionKaleoInstances(
		long kaleoDefinitionVersionId) {

		_kaleoInstanceLocalService.deleteKaleoDefinitionVersionKaleoInstances(
			kaleoDefinitionVersionId);
	}

	/**
	 * Deletes the kaleo instance from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoInstance the kaleo instance
	 * @return the kaleo instance that was removed
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoInstance
		deleteKaleoInstance(
			com.liferay.portal.workflow.kaleo.model.KaleoInstance
				kaleoInstance) {

		return _kaleoInstanceLocalService.deleteKaleoInstance(kaleoInstance);
	}

	/**
	 * Deletes the kaleo instance with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoInstanceId the primary key of the kaleo instance
	 * @return the kaleo instance that was removed
	 * @throws PortalException if a kaleo instance with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoInstance
			deleteKaleoInstance(long kaleoInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoInstanceLocalService.deleteKaleoInstance(kaleoInstanceId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoInstanceLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _kaleoInstanceLocalService.dynamicQuery();
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

		return _kaleoInstanceLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoInstanceModelImpl</code>.
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

		return _kaleoInstanceLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoInstanceModelImpl</code>.
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

		return _kaleoInstanceLocalService.dynamicQuery(
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

		return _kaleoInstanceLocalService.dynamicQueryCount(dynamicQuery);
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

		return _kaleoInstanceLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoInstance
		fetchKaleoInstance(long kaleoInstanceId) {

		return _kaleoInstanceLocalService.fetchKaleoInstance(kaleoInstanceId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _kaleoInstanceLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _kaleoInstanceLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the kaleo instance with the primary key.
	 *
	 * @param kaleoInstanceId the primary key of the kaleo instance
	 * @return the kaleo instance
	 * @throws PortalException if a kaleo instance with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoInstance
			getKaleoInstance(long kaleoInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoInstanceLocalService.getKaleoInstance(kaleoInstanceId);
	}

	/**
	 * Returns a range of all the kaleo instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @return the range of kaleo instances
	 */
	@Override
	public java.util.List<com.liferay.portal.workflow.kaleo.model.KaleoInstance>
		getKaleoInstances(int start, int end) {

		return _kaleoInstanceLocalService.getKaleoInstances(start, end);
	}

	@Override
	public java.util.List<com.liferay.portal.workflow.kaleo.model.KaleoInstance>
		getKaleoInstances(
			Long userId, String assetClassName, Long assetClassPK,
			Boolean completed, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.workflow.kaleo.model.KaleoInstance>
					orderByComparator,
			com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _kaleoInstanceLocalService.getKaleoInstances(
			userId, assetClassName, assetClassPK, completed, start, end,
			orderByComparator, serviceContext);
	}

	@Override
	public java.util.List<com.liferay.portal.workflow.kaleo.model.KaleoInstance>
		getKaleoInstances(
			Long userId, String[] assetClassNames, Boolean completed, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.workflow.kaleo.model.KaleoInstance>
					orderByComparator,
			com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _kaleoInstanceLocalService.getKaleoInstances(
			userId, assetClassNames, completed, start, end, orderByComparator,
			serviceContext);
	}

	@Override
	public java.util.List<com.liferay.portal.workflow.kaleo.model.KaleoInstance>
		getKaleoInstances(
			String kaleoDefinitionName, int kaleoDefinitionVersion,
			boolean completed, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.workflow.kaleo.model.KaleoInstance>
					orderByComparator,
			com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _kaleoInstanceLocalService.getKaleoInstances(
			kaleoDefinitionName, kaleoDefinitionVersion, completed, start, end,
			orderByComparator, serviceContext);
	}

	/**
	 * Returns the number of kaleo instances.
	 *
	 * @return the number of kaleo instances
	 */
	@Override
	public int getKaleoInstancesCount() {
		return _kaleoInstanceLocalService.getKaleoInstancesCount();
	}

	@Override
	public int getKaleoInstancesCount(
		long kaleoDefinitionVersionId, boolean completed) {

		return _kaleoInstanceLocalService.getKaleoInstancesCount(
			kaleoDefinitionVersionId, completed);
	}

	@Override
	public int getKaleoInstancesCount(
		Long userId, String assetClassName, Long assetClassPK,
		Boolean completed,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _kaleoInstanceLocalService.getKaleoInstancesCount(
			userId, assetClassName, assetClassPK, completed, serviceContext);
	}

	@Override
	public int getKaleoInstancesCount(
		Long userId, String[] assetClassNames, Boolean completed,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _kaleoInstanceLocalService.getKaleoInstancesCount(
			userId, assetClassNames, completed, serviceContext);
	}

	@Override
	public int getKaleoInstancesCount(
		String kaleoDefinitionName, int kaleoDefinitionVersion,
		boolean completed,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _kaleoInstanceLocalService.getKaleoInstancesCount(
			kaleoDefinitionName, kaleoDefinitionVersion, completed,
			serviceContext);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _kaleoInstanceLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoInstanceLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #search(Long,
	 String, String, String, String, String, Boolean, int, int,
	 OrderByComparator, ServiceContext)}
	 */
	@Deprecated
	@Override
	public java.util.List<com.liferay.portal.workflow.kaleo.model.KaleoInstance>
		search(
			Long userId, String assetClassName, String nodeName,
			String kaleoDefinitionName, Boolean completed, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.workflow.kaleo.model.KaleoInstance>
					orderByComparator,
			com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _kaleoInstanceLocalService.search(
			userId, assetClassName, nodeName, kaleoDefinitionName, completed,
			start, end, orderByComparator, serviceContext);
	}

	@Override
	public java.util.List<com.liferay.portal.workflow.kaleo.model.KaleoInstance>
		search(
			Long userId, String assetClassName, String assetTitle,
			String assetDescription, String nodeName,
			String kaleoDefinitionName, Boolean completed, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.workflow.kaleo.model.KaleoInstance>
					orderByComparator,
			com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _kaleoInstanceLocalService.search(
			userId, assetClassName, assetTitle, assetDescription, nodeName,
			kaleoDefinitionName, completed, start, end, orderByComparator,
			serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #searchCount(Long,
	 String, String, String, String, String, Boolean,
	 ServiceContext)}
	 */
	@Deprecated
	@Override
	public int searchCount(
		Long userId, String assetClassName, String nodeName,
		String kaleoDefinitionName, Boolean completed,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _kaleoInstanceLocalService.searchCount(
			userId, assetClassName, nodeName, kaleoDefinitionName, completed,
			serviceContext);
	}

	@Override
	public int searchCount(
		Long userId, String assetClassName, String assetTitle,
		String assetDescription, String nodeName, String kaleoDefinitionName,
		Boolean completed,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _kaleoInstanceLocalService.searchCount(
			userId, assetClassName, assetTitle, assetDescription, nodeName,
			kaleoDefinitionName, completed, serviceContext);
	}

	/**
	 * Updates the kaleo instance in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoInstance the kaleo instance
	 * @return the kaleo instance that was updated
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoInstance
		updateKaleoInstance(
			com.liferay.portal.workflow.kaleo.model.KaleoInstance
				kaleoInstance) {

		return _kaleoInstanceLocalService.updateKaleoInstance(kaleoInstance);
	}

	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoInstance
			updateKaleoInstance(
				long kaleoInstanceId, long rootKaleoInstanceTokenId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoInstanceLocalService.updateKaleoInstance(
			kaleoInstanceId, rootKaleoInstanceTokenId);
	}

	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoInstance
			updateKaleoInstance(
				long kaleoInstanceId,
				java.util.Map<String, java.io.Serializable> workflowContext,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoInstanceLocalService.updateKaleoInstance(
			kaleoInstanceId, workflowContext, serviceContext);
	}

	@Override
	public KaleoInstanceLocalService getWrappedService() {
		return _kaleoInstanceLocalService;
	}

	@Override
	public void setWrappedService(
		KaleoInstanceLocalService kaleoInstanceLocalService) {

		_kaleoInstanceLocalService = kaleoInstanceLocalService;
	}

	private KaleoInstanceLocalService _kaleoInstanceLocalService;

}
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
 * Provides a wrapper for {@link KaleoTransitionLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTransitionLocalService
 * @generated
 */
public class KaleoTransitionLocalServiceWrapper
	implements KaleoTransitionLocalService,
			   ServiceWrapper<KaleoTransitionLocalService> {

	public KaleoTransitionLocalServiceWrapper(
		KaleoTransitionLocalService kaleoTransitionLocalService) {

		_kaleoTransitionLocalService = kaleoTransitionLocalService;
	}

	/**
	 * Adds the kaleo transition to the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTransition the kaleo transition
	 * @return the kaleo transition that was added
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTransition
		addKaleoTransition(
			com.liferay.portal.workflow.kaleo.model.KaleoTransition
				kaleoTransition) {

		return _kaleoTransitionLocalService.addKaleoTransition(kaleoTransition);
	}

	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTransition
			addKaleoTransition(
				long kaleoDefinitionVersionId, long kaleoNodeId,
				com.liferay.portal.workflow.kaleo.definition.Transition
					transition,
				com.liferay.portal.workflow.kaleo.model.KaleoNode
					sourceKaleoNode,
				com.liferay.portal.workflow.kaleo.model.KaleoNode
					targetKaleoNode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTransitionLocalService.addKaleoTransition(
			kaleoDefinitionVersionId, kaleoNodeId, transition, sourceKaleoNode,
			targetKaleoNode, serviceContext);
	}

	/**
	 * Creates a new kaleo transition with the primary key. Does not add the kaleo transition to the database.
	 *
	 * @param kaleoTransitionId the primary key for the new kaleo transition
	 * @return the new kaleo transition
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTransition
		createKaleoTransition(long kaleoTransitionId) {

		return _kaleoTransitionLocalService.createKaleoTransition(
			kaleoTransitionId);
	}

	@Override
	public void deleteCompanyKaleoTransitions(long companyId) {
		_kaleoTransitionLocalService.deleteCompanyKaleoTransitions(companyId);
	}

	@Override
	public void deleteKaleoDefinitionVersionKaleoTransitions(
		long kaleoDefinitionVersionId) {

		_kaleoTransitionLocalService.
			deleteKaleoDefinitionVersionKaleoTransitions(
				kaleoDefinitionVersionId);
	}

	/**
	 * Deletes the kaleo transition from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTransition the kaleo transition
	 * @return the kaleo transition that was removed
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTransition
		deleteKaleoTransition(
			com.liferay.portal.workflow.kaleo.model.KaleoTransition
				kaleoTransition) {

		return _kaleoTransitionLocalService.deleteKaleoTransition(
			kaleoTransition);
	}

	/**
	 * Deletes the kaleo transition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTransitionId the primary key of the kaleo transition
	 * @return the kaleo transition that was removed
	 * @throws PortalException if a kaleo transition with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTransition
			deleteKaleoTransition(long kaleoTransitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTransitionLocalService.deleteKaleoTransition(
			kaleoTransitionId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTransitionLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _kaleoTransitionLocalService.dynamicQuery();
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

		return _kaleoTransitionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTransitionModelImpl</code>.
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

		return _kaleoTransitionLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTransitionModelImpl</code>.
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

		return _kaleoTransitionLocalService.dynamicQuery(
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

		return _kaleoTransitionLocalService.dynamicQueryCount(dynamicQuery);
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

		return _kaleoTransitionLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTransition
		fetchKaleoTransition(long kaleoTransitionId) {

		return _kaleoTransitionLocalService.fetchKaleoTransition(
			kaleoTransitionId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _kaleoTransitionLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTransition
			getDefaultKaleoTransition(long kaleoNodeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTransitionLocalService.getDefaultKaleoTransition(
			kaleoNodeId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _kaleoTransitionLocalService.
			getIndexableActionableDynamicQuery();
	}

	@Override
	public java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoTransition>
			getKaleoDefinitionVersionKaleoTransitions(
				long kaleoDefinitionVersionId) {

		return _kaleoTransitionLocalService.
			getKaleoDefinitionVersionKaleoTransitions(kaleoDefinitionVersionId);
	}

	/**
	 * Returns the kaleo transition with the primary key.
	 *
	 * @param kaleoTransitionId the primary key of the kaleo transition
	 * @return the kaleo transition
	 * @throws PortalException if a kaleo transition with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTransition
			getKaleoTransition(long kaleoTransitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTransitionLocalService.getKaleoTransition(
			kaleoTransitionId);
	}

	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTransition
			getKaleoTransition(long kaleoNodeId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTransitionLocalService.getKaleoTransition(
			kaleoNodeId, name);
	}

	/**
	 * Returns a range of all the kaleo transitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo transitions
	 * @param end the upper bound of the range of kaleo transitions (not inclusive)
	 * @return the range of kaleo transitions
	 */
	@Override
	public java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoTransition>
			getKaleoTransitions(int start, int end) {

		return _kaleoTransitionLocalService.getKaleoTransitions(start, end);
	}

	@Override
	public java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoTransition>
			getKaleoTransitions(long kaleoNodeId) {

		return _kaleoTransitionLocalService.getKaleoTransitions(kaleoNodeId);
	}

	/**
	 * Returns the number of kaleo transitions.
	 *
	 * @return the number of kaleo transitions
	 */
	@Override
	public int getKaleoTransitionsCount() {
		return _kaleoTransitionLocalService.getKaleoTransitionsCount();
	}

	@Override
	public int getKaleoTransitionsCount(long kaleoNodeId) {
		return _kaleoTransitionLocalService.getKaleoTransitionsCount(
			kaleoNodeId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _kaleoTransitionLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTransitionLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the kaleo transition in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTransition the kaleo transition
	 * @return the kaleo transition that was updated
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTransition
		updateKaleoTransition(
			com.liferay.portal.workflow.kaleo.model.KaleoTransition
				kaleoTransition) {

		return _kaleoTransitionLocalService.updateKaleoTransition(
			kaleoTransition);
	}

	@Override
	public KaleoTransitionLocalService getWrappedService() {
		return _kaleoTransitionLocalService;
	}

	@Override
	public void setWrappedService(
		KaleoTransitionLocalService kaleoTransitionLocalService) {

		_kaleoTransitionLocalService = kaleoTransitionLocalService;
	}

	private KaleoTransitionLocalService _kaleoTransitionLocalService;

}
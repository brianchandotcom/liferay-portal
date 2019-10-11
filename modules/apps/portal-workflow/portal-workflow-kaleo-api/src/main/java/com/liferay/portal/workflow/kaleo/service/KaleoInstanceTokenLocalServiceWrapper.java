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
 * Provides a wrapper for {@link KaleoInstanceTokenLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoInstanceTokenLocalService
 * @generated
 */
public class KaleoInstanceTokenLocalServiceWrapper
	implements KaleoInstanceTokenLocalService,
			   ServiceWrapper<KaleoInstanceTokenLocalService> {

	public KaleoInstanceTokenLocalServiceWrapper(
		KaleoInstanceTokenLocalService kaleoInstanceTokenLocalService) {

		_kaleoInstanceTokenLocalService = kaleoInstanceTokenLocalService;
	}

	/**
	 * Adds the kaleo instance token to the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoInstanceToken the kaleo instance token
	 * @return the kaleo instance token that was added
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken
		addKaleoInstanceToken(
			com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken
				kaleoInstanceToken) {

		return _kaleoInstanceTokenLocalService.addKaleoInstanceToken(
			kaleoInstanceToken);
	}

	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken
			addKaleoInstanceToken(
				long currentKaleoNodeId, long kaleoDefinitionVersionId,
				long kaleoInstanceId, long parentKaleoInstanceTokenId,
				java.util.Map<String, java.io.Serializable> workflowContext,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoInstanceTokenLocalService.addKaleoInstanceToken(
			currentKaleoNodeId, kaleoDefinitionVersionId, kaleoInstanceId,
			parentKaleoInstanceTokenId, workflowContext, serviceContext);
	}

	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken
			addKaleoInstanceToken(
				long parentKaleoInstanceTokenId,
				java.util.Map<String, java.io.Serializable> workflowContext,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoInstanceTokenLocalService.addKaleoInstanceToken(
			parentKaleoInstanceTokenId, workflowContext, serviceContext);
	}

	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken
			completeKaleoInstanceToken(long kaleoInstanceTokenId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoInstanceTokenLocalService.completeKaleoInstanceToken(
			kaleoInstanceTokenId);
	}

	/**
	 * Creates a new kaleo instance token with the primary key. Does not add the kaleo instance token to the database.
	 *
	 * @param kaleoInstanceTokenId the primary key for the new kaleo instance token
	 * @return the new kaleo instance token
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken
		createKaleoInstanceToken(long kaleoInstanceTokenId) {

		return _kaleoInstanceTokenLocalService.createKaleoInstanceToken(
			kaleoInstanceTokenId);
	}

	@Override
	public void deleteCompanyKaleoInstanceTokens(long companyId) {
		_kaleoInstanceTokenLocalService.deleteCompanyKaleoInstanceTokens(
			companyId);
	}

	@Override
	public void deleteKaleoDefinitionVersionKaleoInstanceTokens(
		long kaleoDefinitionVersionId) {

		_kaleoInstanceTokenLocalService.
			deleteKaleoDefinitionVersionKaleoInstanceTokens(
				kaleoDefinitionVersionId);
	}

	@Override
	public void deleteKaleoInstanceKaleoInstanceTokens(long kaleoInstanceId) {
		_kaleoInstanceTokenLocalService.deleteKaleoInstanceKaleoInstanceTokens(
			kaleoInstanceId);
	}

	/**
	 * Deletes the kaleo instance token from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoInstanceToken the kaleo instance token
	 * @return the kaleo instance token that was removed
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken
		deleteKaleoInstanceToken(
			com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken
				kaleoInstanceToken) {

		return _kaleoInstanceTokenLocalService.deleteKaleoInstanceToken(
			kaleoInstanceToken);
	}

	/**
	 * Deletes the kaleo instance token with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoInstanceTokenId the primary key of the kaleo instance token
	 * @return the kaleo instance token that was removed
	 * @throws PortalException if a kaleo instance token with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken
			deleteKaleoInstanceToken(long kaleoInstanceTokenId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoInstanceTokenLocalService.deleteKaleoInstanceToken(
			kaleoInstanceTokenId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoInstanceTokenLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _kaleoInstanceTokenLocalService.dynamicQuery();
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

		return _kaleoInstanceTokenLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoInstanceTokenModelImpl</code>.
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

		return _kaleoInstanceTokenLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoInstanceTokenModelImpl</code>.
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

		return _kaleoInstanceTokenLocalService.dynamicQuery(
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

		return _kaleoInstanceTokenLocalService.dynamicQueryCount(dynamicQuery);
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

		return _kaleoInstanceTokenLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken
		fetchKaleoInstanceToken(long kaleoInstanceTokenId) {

		return _kaleoInstanceTokenLocalService.fetchKaleoInstanceToken(
			kaleoInstanceTokenId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _kaleoInstanceTokenLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _kaleoInstanceTokenLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the kaleo instance token with the primary key.
	 *
	 * @param kaleoInstanceTokenId the primary key of the kaleo instance token
	 * @return the kaleo instance token
	 * @throws PortalException if a kaleo instance token with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken
			getKaleoInstanceToken(long kaleoInstanceTokenId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoInstanceTokenLocalService.getKaleoInstanceToken(
			kaleoInstanceTokenId);
	}

	/**
	 * Returns a range of all the kaleo instance tokens.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo instance tokens
	 * @param end the upper bound of the range of kaleo instance tokens (not inclusive)
	 * @return the range of kaleo instance tokens
	 */
	@Override
	public java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken>
			getKaleoInstanceTokens(int start, int end) {

		return _kaleoInstanceTokenLocalService.getKaleoInstanceTokens(
			start, end);
	}

	@Override
	public java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken>
			getKaleoInstanceTokens(
				long parentKaleoInstanceTokenId, java.util.Date completionDate,
				com.liferay.portal.kernel.service.ServiceContext
					serviceContext) {

		return _kaleoInstanceTokenLocalService.getKaleoInstanceTokens(
			parentKaleoInstanceTokenId, completionDate, serviceContext);
	}

	@Override
	public java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken>
			getKaleoInstanceTokens(
				long parentKaleoInstanceTokenId,
				com.liferay.portal.kernel.service.ServiceContext
					serviceContext) {

		return _kaleoInstanceTokenLocalService.getKaleoInstanceTokens(
			parentKaleoInstanceTokenId, serviceContext);
	}

	/**
	 * Returns the number of kaleo instance tokens.
	 *
	 * @return the number of kaleo instance tokens
	 */
	@Override
	public int getKaleoInstanceTokensCount() {
		return _kaleoInstanceTokenLocalService.getKaleoInstanceTokensCount();
	}

	@Override
	public int getKaleoInstanceTokensCount(
		long parentKaleoInstanceTokenId, java.util.Date completionDate,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _kaleoInstanceTokenLocalService.getKaleoInstanceTokensCount(
			parentKaleoInstanceTokenId, completionDate, serviceContext);
	}

	@Override
	public int getKaleoInstanceTokensCount(
		long parentKaleoInstanceTokenId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _kaleoInstanceTokenLocalService.getKaleoInstanceTokensCount(
			parentKaleoInstanceTokenId, serviceContext);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _kaleoInstanceTokenLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoInstanceTokenLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken
			getRootKaleoInstanceToken(
				long kaleoInstanceId,
				java.util.Map<String, java.io.Serializable> workflowContext,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoInstanceTokenLocalService.getRootKaleoInstanceToken(
			kaleoInstanceId, workflowContext, serviceContext);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
		Long userId, String assetClassName, String assetTitle,
		String assetDescription, String currentKaleoNodeName,
		String kaleoDefinitionName, Boolean completed, int start, int end,
		com.liferay.portal.kernel.search.Sort[] sorts,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _kaleoInstanceTokenLocalService.search(
			userId, assetClassName, assetTitle, assetDescription,
			currentKaleoNodeName, kaleoDefinitionName, completed, start, end,
			sorts, serviceContext);
	}

	@Override
	public int searchCount(
		Long userId, String assetClassName, String assetTitle,
		String assetDescription, String currentKaleoNodeName,
		String kaleoDefinitionName, Boolean completed,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _kaleoInstanceTokenLocalService.searchCount(
			userId, assetClassName, assetTitle, assetDescription,
			currentKaleoNodeName, kaleoDefinitionName, completed,
			serviceContext);
	}

	/**
	 * Updates the kaleo instance token in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoInstanceToken the kaleo instance token
	 * @return the kaleo instance token that was updated
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken
		updateKaleoInstanceToken(
			com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken
				kaleoInstanceToken) {

		return _kaleoInstanceTokenLocalService.updateKaleoInstanceToken(
			kaleoInstanceToken);
	}

	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken
			updateKaleoInstanceToken(
				long kaleoInstanceTokenId, long currentKaleoNodeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoInstanceTokenLocalService.updateKaleoInstanceToken(
			kaleoInstanceTokenId, currentKaleoNodeId);
	}

	@Override
	public KaleoInstanceTokenLocalService getWrappedService() {
		return _kaleoInstanceTokenLocalService;
	}

	@Override
	public void setWrappedService(
		KaleoInstanceTokenLocalService kaleoInstanceTokenLocalService) {

		_kaleoInstanceTokenLocalService = kaleoInstanceTokenLocalService;
	}

	private KaleoInstanceTokenLocalService _kaleoInstanceTokenLocalService;

}
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
 * Provides a wrapper for {@link KaleoTaskInstanceTokenLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTaskInstanceTokenLocalService
 * @generated
 */
public class KaleoTaskInstanceTokenLocalServiceWrapper
	implements KaleoTaskInstanceTokenLocalService,
			   ServiceWrapper<KaleoTaskInstanceTokenLocalService> {

	public KaleoTaskInstanceTokenLocalServiceWrapper(
		KaleoTaskInstanceTokenLocalService kaleoTaskInstanceTokenLocalService) {

		_kaleoTaskInstanceTokenLocalService =
			kaleoTaskInstanceTokenLocalService;
	}

	/**
	 * Adds the kaleo task instance token to the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTaskInstanceToken the kaleo task instance token
	 * @return the kaleo task instance token that was added
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken
		addKaleoTaskInstanceToken(
			com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken
				kaleoTaskInstanceToken) {

		return _kaleoTaskInstanceTokenLocalService.addKaleoTaskInstanceToken(
			kaleoTaskInstanceToken);
	}

	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken
			addKaleoTaskInstanceToken(
				long kaleoInstanceTokenId, long kaleoTaskId,
				String kaleoTaskName,
				java.util.Collection
					<com.liferay.portal.workflow.kaleo.model.
						KaleoTaskAssignment> kaleoTaskAssignments,
				java.util.Date dueDate,
				java.util.Map<String, java.io.Serializable> workflowContext,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskInstanceTokenLocalService.addKaleoTaskInstanceToken(
			kaleoInstanceTokenId, kaleoTaskId, kaleoTaskName,
			kaleoTaskAssignments, dueDate, workflowContext, serviceContext);
	}

	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken
			assignKaleoTaskInstanceToken(
				long kaleoTaskInstanceTokenId, String assigneeClassName,
				long assigneeClassPK,
				java.util.Map<String, java.io.Serializable> workflowContext,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskInstanceTokenLocalService.assignKaleoTaskInstanceToken(
			kaleoTaskInstanceTokenId, assigneeClassName, assigneeClassPK,
			workflowContext, serviceContext);
	}

	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken
			completeKaleoTaskInstanceToken(
				long kaleoTaskInstanceTokenId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskInstanceTokenLocalService.
			completeKaleoTaskInstanceToken(
				kaleoTaskInstanceTokenId, serviceContext);
	}

	/**
	 * Creates a new kaleo task instance token with the primary key. Does not add the kaleo task instance token to the database.
	 *
	 * @param kaleoTaskInstanceTokenId the primary key for the new kaleo task instance token
	 * @return the new kaleo task instance token
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken
		createKaleoTaskInstanceToken(long kaleoTaskInstanceTokenId) {

		return _kaleoTaskInstanceTokenLocalService.createKaleoTaskInstanceToken(
			kaleoTaskInstanceTokenId);
	}

	@Override
	public void deleteCompanyKaleoTaskInstanceTokens(long companyId) {
		_kaleoTaskInstanceTokenLocalService.
			deleteCompanyKaleoTaskInstanceTokens(companyId);
	}

	@Override
	public void deleteKaleoDefinitionVersionKaleoTaskInstanceTokens(
		long kaleoDefinitionVersionId) {

		_kaleoTaskInstanceTokenLocalService.
			deleteKaleoDefinitionVersionKaleoTaskInstanceTokens(
				kaleoDefinitionVersionId);
	}

	@Override
	public void deleteKaleoInstanceKaleoTaskInstanceTokens(
		long kaleoInstanceId) {

		_kaleoTaskInstanceTokenLocalService.
			deleteKaleoInstanceKaleoTaskInstanceTokens(kaleoInstanceId);
	}

	/**
	 * Deletes the kaleo task instance token from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTaskInstanceToken the kaleo task instance token
	 * @return the kaleo task instance token that was removed
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken
		deleteKaleoTaskInstanceToken(
			com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken
				kaleoTaskInstanceToken) {

		return _kaleoTaskInstanceTokenLocalService.deleteKaleoTaskInstanceToken(
			kaleoTaskInstanceToken);
	}

	/**
	 * Deletes the kaleo task instance token with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTaskInstanceTokenId the primary key of the kaleo task instance token
	 * @return the kaleo task instance token that was removed
	 * @throws PortalException if a kaleo task instance token with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken
			deleteKaleoTaskInstanceToken(long kaleoTaskInstanceTokenId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskInstanceTokenLocalService.deleteKaleoTaskInstanceToken(
			kaleoTaskInstanceTokenId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskInstanceTokenLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _kaleoTaskInstanceTokenLocalService.dynamicQuery();
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

		return _kaleoTaskInstanceTokenLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskInstanceTokenModelImpl</code>.
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

		return _kaleoTaskInstanceTokenLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskInstanceTokenModelImpl</code>.
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

		return _kaleoTaskInstanceTokenLocalService.dynamicQuery(
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

		return _kaleoTaskInstanceTokenLocalService.dynamicQueryCount(
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

		return _kaleoTaskInstanceTokenLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken
		fetchKaleoTaskInstanceToken(long kaleoTaskInstanceTokenId) {

		return _kaleoTaskInstanceTokenLocalService.fetchKaleoTaskInstanceToken(
			kaleoTaskInstanceTokenId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _kaleoTaskInstanceTokenLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken>
			getCompanyKaleoTaskInstanceTokens(
				long companyId, int start, int end) {

		return _kaleoTaskInstanceTokenLocalService.
			getCompanyKaleoTaskInstanceTokens(companyId, start, end);
	}

	@Override
	public int getCompanyKaleoTaskInstanceTokensCount(long companyId) {
		return _kaleoTaskInstanceTokenLocalService.
			getCompanyKaleoTaskInstanceTokensCount(companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _kaleoTaskInstanceTokenLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the kaleo task instance token with the primary key.
	 *
	 * @param kaleoTaskInstanceTokenId the primary key of the kaleo task instance token
	 * @return the kaleo task instance token
	 * @throws PortalException if a kaleo task instance token with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken
			getKaleoTaskInstanceToken(long kaleoTaskInstanceTokenId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskInstanceTokenLocalService.getKaleoTaskInstanceToken(
			kaleoTaskInstanceTokenId);
	}

	@Override
	public java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken>
			getKaleoTaskInstanceTokens(
				Boolean completed, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.workflow.kaleo.model.
						KaleoTaskInstanceToken> orderByComparator,
				com.liferay.portal.kernel.service.ServiceContext
					serviceContext) {

		return _kaleoTaskInstanceTokenLocalService.getKaleoTaskInstanceTokens(
			completed, start, end, orderByComparator, serviceContext);
	}

	/**
	 * Returns a range of all the kaleo task instance tokens.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo task instance tokens
	 * @param end the upper bound of the range of kaleo task instance tokens (not inclusive)
	 * @return the range of kaleo task instance tokens
	 */
	@Override
	public java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken>
			getKaleoTaskInstanceTokens(int start, int end) {

		return _kaleoTaskInstanceTokenLocalService.getKaleoTaskInstanceTokens(
			start, end);
	}

	@Override
	public java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken>
			getKaleoTaskInstanceTokens(
				java.util.List<Long> roleIds, Boolean completed, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.workflow.kaleo.model.
						KaleoTaskInstanceToken> orderByComparator,
				com.liferay.portal.kernel.service.ServiceContext
					serviceContext) {

		return _kaleoTaskInstanceTokenLocalService.getKaleoTaskInstanceTokens(
			roleIds, completed, start, end, orderByComparator, serviceContext);
	}

	@Override
	public java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken>
			getKaleoTaskInstanceTokens(
				long kaleoInstanceId, Boolean completed, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.workflow.kaleo.model.
						KaleoTaskInstanceToken> orderByComparator,
				com.liferay.portal.kernel.service.ServiceContext
					serviceContext) {

		return _kaleoTaskInstanceTokenLocalService.getKaleoTaskInstanceTokens(
			kaleoInstanceId, completed, start, end, orderByComparator,
			serviceContext);
	}

	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken
			getKaleoTaskInstanceTokens(long kaleoInstanceId, long kaleoTaskId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskInstanceTokenLocalService.getKaleoTaskInstanceTokens(
			kaleoInstanceId, kaleoTaskId);
	}

	@Override
	public java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken>
			getKaleoTaskInstanceTokens(
				String assigneeClassName, long assigneeClassPK,
				Boolean completed, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.workflow.kaleo.model.
						KaleoTaskInstanceToken> orderByComparator,
				com.liferay.portal.kernel.service.ServiceContext
					serviceContext) {

		return _kaleoTaskInstanceTokenLocalService.getKaleoTaskInstanceTokens(
			assigneeClassName, assigneeClassPK, completed, start, end,
			orderByComparator, serviceContext);
	}

	/**
	 * Returns the number of kaleo task instance tokens.
	 *
	 * @return the number of kaleo task instance tokens
	 */
	@Override
	public int getKaleoTaskInstanceTokensCount() {
		return _kaleoTaskInstanceTokenLocalService.
			getKaleoTaskInstanceTokensCount();
	}

	@Override
	public int getKaleoTaskInstanceTokensCount(
		Boolean completed,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _kaleoTaskInstanceTokenLocalService.
			getKaleoTaskInstanceTokensCount(completed, serviceContext);
	}

	@Override
	public int getKaleoTaskInstanceTokensCount(
		java.util.List<Long> roleIds, Boolean completed,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _kaleoTaskInstanceTokenLocalService.
			getKaleoTaskInstanceTokensCount(roleIds, completed, serviceContext);
	}

	@Override
	public int getKaleoTaskInstanceTokensCount(
		long kaleoInstanceId, Boolean completed,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _kaleoTaskInstanceTokenLocalService.
			getKaleoTaskInstanceTokensCount(
				kaleoInstanceId, completed, serviceContext);
	}

	@Override
	public int getKaleoTaskInstanceTokensCount(
		String assigneeClassName, long assigneeClassPK, Boolean completed,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _kaleoTaskInstanceTokenLocalService.
			getKaleoTaskInstanceTokensCount(
				assigneeClassName, assigneeClassPK, completed, serviceContext);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _kaleoTaskInstanceTokenLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskInstanceTokenLocalService.getPersistedModel(
			primaryKeyObj);
	}

	@Override
	public java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken>
			getSubmittingUserKaleoTaskInstanceTokens(
				long userId, Boolean completed, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.workflow.kaleo.model.
						KaleoTaskInstanceToken> orderByComparator,
				com.liferay.portal.kernel.service.ServiceContext
					serviceContext) {

		return _kaleoTaskInstanceTokenLocalService.
			getSubmittingUserKaleoTaskInstanceTokens(
				userId, completed, start, end, orderByComparator,
				serviceContext);
	}

	@Override
	public int getSubmittingUserKaleoTaskInstanceTokensCount(
		long userId, Boolean completed,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _kaleoTaskInstanceTokenLocalService.
			getSubmittingUserKaleoTaskInstanceTokensCount(
				userId, completed, serviceContext);
	}

	@Override
	public boolean hasPendingKaleoTaskForms(long kaleoTaskInstanceTokenId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskInstanceTokenLocalService.hasPendingKaleoTaskForms(
			kaleoTaskInstanceTokenId);
	}

	@Override
	public java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken> search(
			String keywords, Boolean completed, Boolean searchByUserRoles,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken>
					orderByComparator,
			com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _kaleoTaskInstanceTokenLocalService.search(
			keywords, completed, searchByUserRoles, start, end,
			orderByComparator, serviceContext);
	}

	@Override
	public java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken> search(
			String taskName, String assetType, Long[] assetPrimaryKeys,
			java.util.Date dueDateGT, java.util.Date dueDateLT,
			Boolean completed, Boolean searchByUserRoles, boolean andOperator,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken>
					orderByComparator,
			com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _kaleoTaskInstanceTokenLocalService.search(
			taskName, assetType, assetPrimaryKeys, dueDateGT, dueDateLT,
			completed, searchByUserRoles, andOperator, start, end,
			orderByComparator, serviceContext);
	}

	@Override
	public java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken> search(
			String assetTitle, String taskName, String[] assetTypes,
			Long[] assetPrimaryKeys, java.util.Date dueDateGT,
			java.util.Date dueDateLT, Boolean completed,
			Boolean searchByUserRoles, boolean andOperator, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken>
					orderByComparator,
			com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _kaleoTaskInstanceTokenLocalService.search(
			assetTitle, taskName, assetTypes, assetPrimaryKeys, dueDateGT,
			dueDateLT, completed, searchByUserRoles, andOperator, start, end,
			orderByComparator, serviceContext);
	}

	@Override
	public java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken> search(
			String keywords, String[] assetTypes, Boolean completed,
			Boolean searchByUserRoles, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken>
					orderByComparator,
			com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _kaleoTaskInstanceTokenLocalService.search(
			keywords, assetTypes, completed, searchByUserRoles, start, end,
			orderByComparator, serviceContext);
	}

	@Override
	public java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken> search(
			String assetTitle, String[] taskNames, String[] assetTypes,
			Long[] assetPrimaryKeys, Long[] assigneeClassPKs,
			java.util.Date dueDateGT, java.util.Date dueDateLT,
			Boolean completed, Long kaleoDefinitionId, Long[] kaleoInstanceIds,
			Boolean searchByUserRoles, boolean andOperator, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken>
					orderByComparator,
			com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _kaleoTaskInstanceTokenLocalService.search(
			assetTitle, taskNames, assetTypes, assetPrimaryKeys,
			assigneeClassPKs, dueDateGT, dueDateLT, completed,
			kaleoDefinitionId, kaleoInstanceIds, searchByUserRoles, andOperator,
			start, end, orderByComparator, serviceContext);
	}

	@Override
	public java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken> search(
			String assetTitle, String[] taskNames, String[] assetTypes,
			Long[] assetPrimaryKeys, Long[] assigneeClassPKs,
			java.util.Date dueDateGT, java.util.Date dueDateLT,
			Boolean completed, Long[] kaleoInstanceIds,
			Boolean searchByUserRoles, boolean andOperator, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken>
					orderByComparator,
			com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _kaleoTaskInstanceTokenLocalService.search(
			assetTitle, taskNames, assetTypes, assetPrimaryKeys,
			assigneeClassPKs, dueDateGT, dueDateLT, completed, kaleoInstanceIds,
			searchByUserRoles, andOperator, start, end, orderByComparator,
			serviceContext);
	}

	@Override
	public int searchCount(
		long kaleoInstanceId, Boolean completed, Boolean searchByUserRoles,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _kaleoTaskInstanceTokenLocalService.searchCount(
			kaleoInstanceId, completed, searchByUserRoles, serviceContext);
	}

	@Override
	public int searchCount(
		Long kaleoInstanceId, String assetTitle, String taskName,
		String[] assetTypes, Long[] assetPrimaryKeys, java.util.Date dueDateGT,
		java.util.Date dueDateLT, Boolean completed, Boolean searchByUserRoles,
		boolean andOperator,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _kaleoTaskInstanceTokenLocalService.searchCount(
			kaleoInstanceId, assetTitle, taskName, assetTypes, assetPrimaryKeys,
			dueDateGT, dueDateLT, completed, searchByUserRoles, andOperator,
			serviceContext);
	}

	@Override
	public int searchCount(
		String keywords, Boolean completed, Boolean searchByUserRoles,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _kaleoTaskInstanceTokenLocalService.searchCount(
			keywords, completed, searchByUserRoles, serviceContext);
	}

	@Override
	public int searchCount(
		String taskName, String assetType, Long[] assetPrimaryKeys,
		java.util.Date dueDateGT, java.util.Date dueDateLT, Boolean completed,
		Boolean searchByUserRoles, boolean andOperator,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _kaleoTaskInstanceTokenLocalService.searchCount(
			taskName, assetType, assetPrimaryKeys, dueDateGT, dueDateLT,
			completed, searchByUserRoles, andOperator, serviceContext);
	}

	@Override
	public int searchCount(
		String keywords, String[] assetTypes, Boolean completed,
		Boolean searchByUserRoles,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _kaleoTaskInstanceTokenLocalService.searchCount(
			keywords, assetTypes, completed, searchByUserRoles, serviceContext);
	}

	@Override
	public int searchCount(
		String assetTitle, String[] taskNames, String[] assetTypes,
		Long[] assetPrimaryKeys, Long[] assigneeClassPKs,
		java.util.Date dueDateGT, java.util.Date dueDateLT, Boolean completed,
		Long kaleoDefinitionId, Long[] kaleoInstanceIds,
		Boolean searchByUserRoles, boolean andOperator,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _kaleoTaskInstanceTokenLocalService.searchCount(
			assetTitle, taskNames, assetTypes, assetPrimaryKeys,
			assigneeClassPKs, dueDateGT, dueDateLT, completed,
			kaleoDefinitionId, kaleoInstanceIds, searchByUserRoles, andOperator,
			serviceContext);
	}

	@Override
	public int searchCount(
		String assetTitle, String[] taskNames, String[] assetTypes,
		Long[] assetPrimaryKeys, Long[] assigneeClassPKs,
		java.util.Date dueDateGT, java.util.Date dueDateLT, Boolean completed,
		Long[] kaleoInstanceIds, Boolean searchByUserRoles, boolean andOperator,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _kaleoTaskInstanceTokenLocalService.searchCount(
			assetTitle, taskNames, assetTypes, assetPrimaryKeys,
			assigneeClassPKs, dueDateGT, dueDateLT, completed, kaleoInstanceIds,
			searchByUserRoles, andOperator, serviceContext);
	}

	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken
			updateDueDate(
				long kaleoTaskInstanceTokenId, java.util.Date dueDate,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskInstanceTokenLocalService.updateDueDate(
			kaleoTaskInstanceTokenId, dueDate, serviceContext);
	}

	/**
	 * Updates the kaleo task instance token in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTaskInstanceToken the kaleo task instance token
	 * @return the kaleo task instance token that was updated
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken
		updateKaleoTaskInstanceToken(
			com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken
				kaleoTaskInstanceToken) {

		return _kaleoTaskInstanceTokenLocalService.updateKaleoTaskInstanceToken(
			kaleoTaskInstanceToken);
	}

	@Override
	public KaleoTaskInstanceTokenLocalService getWrappedService() {
		return _kaleoTaskInstanceTokenLocalService;
	}

	@Override
	public void setWrappedService(
		KaleoTaskInstanceTokenLocalService kaleoTaskInstanceTokenLocalService) {

		_kaleoTaskInstanceTokenLocalService =
			kaleoTaskInstanceTokenLocalService;
	}

	private KaleoTaskInstanceTokenLocalService
		_kaleoTaskInstanceTokenLocalService;

}
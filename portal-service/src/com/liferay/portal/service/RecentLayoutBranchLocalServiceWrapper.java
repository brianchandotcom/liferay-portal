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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

/**
 * Provides a wrapper for {@link RecentLayoutBranchLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see RecentLayoutBranchLocalService
 * @generated
 */
@ProviderType
public class RecentLayoutBranchLocalServiceWrapper
	implements RecentLayoutBranchLocalService,
		ServiceWrapper<RecentLayoutBranchLocalService> {
	public RecentLayoutBranchLocalServiceWrapper(
		RecentLayoutBranchLocalService recentLayoutBranchLocalService) {
		_recentLayoutBranchLocalService = recentLayoutBranchLocalService;
	}

	/**
	* Adds the recent layout branch to the database. Also notifies the appropriate model listeners.
	*
	* @param recentLayoutBranch the recent layout branch
	* @return the recent layout branch that was added
	*/
	@Override
	public com.liferay.portal.model.RecentLayoutBranch addRecentLayoutBranch(
		com.liferay.portal.model.RecentLayoutBranch recentLayoutBranch) {
		return _recentLayoutBranchLocalService.addRecentLayoutBranch(recentLayoutBranch);
	}

	/**
	* Creates a new recent layout branch with the primary key. Does not add the recent layout branch to the database.
	*
	* @param recentLayoutBranchId the primary key for the new recent layout branch
	* @return the new recent layout branch
	*/
	@Override
	public com.liferay.portal.model.RecentLayoutBranch createRecentLayoutBranch(
		long recentLayoutBranchId) {
		return _recentLayoutBranchLocalService.createRecentLayoutBranch(recentLayoutBranchId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _recentLayoutBranchLocalService.deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the recent layout branch from the database. Also notifies the appropriate model listeners.
	*
	* @param recentLayoutBranch the recent layout branch
	* @return the recent layout branch that was removed
	*/
	@Override
	public com.liferay.portal.model.RecentLayoutBranch deleteRecentLayoutBranch(
		com.liferay.portal.model.RecentLayoutBranch recentLayoutBranch) {
		return _recentLayoutBranchLocalService.deleteRecentLayoutBranch(recentLayoutBranch);
	}

	/**
	* Deletes the recent layout branch with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param recentLayoutBranchId the primary key of the recent layout branch
	* @return the recent layout branch that was removed
	* @throws PortalException if a recent layout branch with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.RecentLayoutBranch deleteRecentLayoutBranch(
		long recentLayoutBranchId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _recentLayoutBranchLocalService.deleteRecentLayoutBranch(recentLayoutBranchId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _recentLayoutBranchLocalService.dynamicQuery();
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
		return _recentLayoutBranchLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RecentLayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _recentLayoutBranchLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RecentLayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _recentLayoutBranchLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
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
		return _recentLayoutBranchLocalService.dynamicQueryCount(dynamicQuery);
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
		return _recentLayoutBranchLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.portal.model.RecentLayoutBranch fetchRecentLayoutBranch(
		long recentLayoutBranchId) {
		return _recentLayoutBranchLocalService.fetchRecentLayoutBranch(recentLayoutBranchId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _recentLayoutBranchLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _recentLayoutBranchLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _recentLayoutBranchLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _recentLayoutBranchLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the recent layout branch with the primary key.
	*
	* @param recentLayoutBranchId the primary key of the recent layout branch
	* @return the recent layout branch
	* @throws PortalException if a recent layout branch with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.RecentLayoutBranch getRecentLayoutBranch(
		long recentLayoutBranchId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _recentLayoutBranchLocalService.getRecentLayoutBranch(recentLayoutBranchId);
	}

	/**
	* Returns a range of all the recent layout branchs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RecentLayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of recent layout branchs
	* @param end the upper bound of the range of recent layout branchs (not inclusive)
	* @return the range of recent layout branchs
	*/
	@Override
	public java.util.List<com.liferay.portal.model.RecentLayoutBranch> getRecentLayoutBranchs(
		int start, int end) {
		return _recentLayoutBranchLocalService.getRecentLayoutBranchs(start, end);
	}

	/**
	* Returns the number of recent layout branchs.
	*
	* @return the number of recent layout branchs
	*/
	@Override
	public int getRecentLayoutBranchsCount() {
		return _recentLayoutBranchLocalService.getRecentLayoutBranchsCount();
	}

	/**
	* Updates the recent layout branch in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param recentLayoutBranch the recent layout branch
	* @return the recent layout branch that was updated
	*/
	@Override
	public com.liferay.portal.model.RecentLayoutBranch updateRecentLayoutBranch(
		com.liferay.portal.model.RecentLayoutBranch recentLayoutBranch) {
		return _recentLayoutBranchLocalService.updateRecentLayoutBranch(recentLayoutBranch);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public RecentLayoutBranchLocalService getWrappedRecentLayoutBranchLocalService() {
		return _recentLayoutBranchLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedRecentLayoutBranchLocalService(
		RecentLayoutBranchLocalService recentLayoutBranchLocalService) {
		_recentLayoutBranchLocalService = recentLayoutBranchLocalService;
	}

	@Override
	public RecentLayoutBranchLocalService getWrappedService() {
		return _recentLayoutBranchLocalService;
	}

	@Override
	public void setWrappedService(
		RecentLayoutBranchLocalService recentLayoutBranchLocalService) {
		_recentLayoutBranchLocalService = recentLayoutBranchLocalService;
	}

	private RecentLayoutBranchLocalService _recentLayoutBranchLocalService;
}
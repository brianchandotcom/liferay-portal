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
 * Provides a wrapper for {@link RecentLayoutSetBranchLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see RecentLayoutSetBranchLocalService
 * @generated
 */
@ProviderType
public class RecentLayoutSetBranchLocalServiceWrapper
	implements RecentLayoutSetBranchLocalService,
		ServiceWrapper<RecentLayoutSetBranchLocalService> {
	public RecentLayoutSetBranchLocalServiceWrapper(
		RecentLayoutSetBranchLocalService recentLayoutSetBranchLocalService) {
		_recentLayoutSetBranchLocalService = recentLayoutSetBranchLocalService;
	}

	/**
	* Adds the recent layout set branch to the database. Also notifies the appropriate model listeners.
	*
	* @param recentLayoutSetBranch the recent layout set branch
	* @return the recent layout set branch that was added
	*/
	@Override
	public com.liferay.portal.model.RecentLayoutSetBranch addRecentLayoutSetBranch(
		com.liferay.portal.model.RecentLayoutSetBranch recentLayoutSetBranch) {
		return _recentLayoutSetBranchLocalService.addRecentLayoutSetBranch(recentLayoutSetBranch);
	}

	@Override
	public com.liferay.portal.model.RecentLayoutSetBranch addRecentLayoutSetBranch(
		long userId, long layoutSetBranchId, long layoutSetId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _recentLayoutSetBranchLocalService.addRecentLayoutSetBranch(userId,
			layoutSetBranchId, layoutSetId);
	}

	/**
	* Creates a new recent layout set branch with the primary key. Does not add the recent layout set branch to the database.
	*
	* @param recentLayoutSetBranchId the primary key for the new recent layout set branch
	* @return the new recent layout set branch
	*/
	@Override
	public com.liferay.portal.model.RecentLayoutSetBranch createRecentLayoutSetBranch(
		long recentLayoutSetBranchId) {
		return _recentLayoutSetBranchLocalService.createRecentLayoutSetBranch(recentLayoutSetBranchId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _recentLayoutSetBranchLocalService.deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the recent layout set branch from the database. Also notifies the appropriate model listeners.
	*
	* @param recentLayoutSetBranch the recent layout set branch
	* @return the recent layout set branch that was removed
	*/
	@Override
	public com.liferay.portal.model.RecentLayoutSetBranch deleteRecentLayoutSetBranch(
		com.liferay.portal.model.RecentLayoutSetBranch recentLayoutSetBranch) {
		return _recentLayoutSetBranchLocalService.deleteRecentLayoutSetBranch(recentLayoutSetBranch);
	}

	/**
	* Deletes the recent layout set branch with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param recentLayoutSetBranchId the primary key of the recent layout set branch
	* @return the recent layout set branch that was removed
	* @throws PortalException if a recent layout set branch with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.RecentLayoutSetBranch deleteRecentLayoutSetBranch(
		long recentLayoutSetBranchId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _recentLayoutSetBranchLocalService.deleteRecentLayoutSetBranch(recentLayoutSetBranchId);
	}

	@Override
	public void deleteRecentLayoutSetBranches(long layoutSetBranchId) {
		_recentLayoutSetBranchLocalService.deleteRecentLayoutSetBranches(layoutSetBranchId);
	}

	@Override
	public void deleteUserRecentLayoutSetBranches(long userId) {
		_recentLayoutSetBranchLocalService.deleteUserRecentLayoutSetBranches(userId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _recentLayoutSetBranchLocalService.dynamicQuery();
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
		return _recentLayoutSetBranchLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RecentLayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _recentLayoutSetBranchLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RecentLayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _recentLayoutSetBranchLocalService.dynamicQuery(dynamicQuery,
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
		return _recentLayoutSetBranchLocalService.dynamicQueryCount(dynamicQuery);
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
		return _recentLayoutSetBranchLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.portal.model.RecentLayoutSetBranch fetchRecentLayoutSetBranch(
		long recentLayoutSetBranchId) {
		return _recentLayoutSetBranchLocalService.fetchRecentLayoutSetBranch(recentLayoutSetBranchId);
	}

	@Override
	public com.liferay.portal.model.RecentLayoutSetBranch fetchRecentLayoutSetBranch(
		long userId, long layoutSetId) {
		return _recentLayoutSetBranchLocalService.fetchRecentLayoutSetBranch(userId,
			layoutSetId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _recentLayoutSetBranchLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _recentLayoutSetBranchLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _recentLayoutSetBranchLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _recentLayoutSetBranchLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the recent layout set branch with the primary key.
	*
	* @param recentLayoutSetBranchId the primary key of the recent layout set branch
	* @return the recent layout set branch
	* @throws PortalException if a recent layout set branch with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.RecentLayoutSetBranch getRecentLayoutSetBranch(
		long recentLayoutSetBranchId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _recentLayoutSetBranchLocalService.getRecentLayoutSetBranch(recentLayoutSetBranchId);
	}

	/**
	* Returns a range of all the recent layout set branchs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RecentLayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of recent layout set branchs
	* @param end the upper bound of the range of recent layout set branchs (not inclusive)
	* @return the range of recent layout set branchs
	*/
	@Override
	public java.util.List<com.liferay.portal.model.RecentLayoutSetBranch> getRecentLayoutSetBranchs(
		int start, int end) {
		return _recentLayoutSetBranchLocalService.getRecentLayoutSetBranchs(start,
			end);
	}

	/**
	* Returns the number of recent layout set branchs.
	*
	* @return the number of recent layout set branchs
	*/
	@Override
	public int getRecentLayoutSetBranchsCount() {
		return _recentLayoutSetBranchLocalService.getRecentLayoutSetBranchsCount();
	}

	/**
	* Updates the recent layout set branch in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param recentLayoutSetBranch the recent layout set branch
	* @return the recent layout set branch that was updated
	*/
	@Override
	public com.liferay.portal.model.RecentLayoutSetBranch updateRecentLayoutSetBranch(
		com.liferay.portal.model.RecentLayoutSetBranch recentLayoutSetBranch) {
		return _recentLayoutSetBranchLocalService.updateRecentLayoutSetBranch(recentLayoutSetBranch);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public RecentLayoutSetBranchLocalService getWrappedRecentLayoutSetBranchLocalService() {
		return _recentLayoutSetBranchLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedRecentLayoutSetBranchLocalService(
		RecentLayoutSetBranchLocalService recentLayoutSetBranchLocalService) {
		_recentLayoutSetBranchLocalService = recentLayoutSetBranchLocalService;
	}

	@Override
	public RecentLayoutSetBranchLocalService getWrappedService() {
		return _recentLayoutSetBranchLocalService;
	}

	@Override
	public void setWrappedService(
		RecentLayoutSetBranchLocalService recentLayoutSetBranchLocalService) {
		_recentLayoutSetBranchLocalService = recentLayoutSetBranchLocalService;
	}

	private RecentLayoutSetBranchLocalService _recentLayoutSetBranchLocalService;
}
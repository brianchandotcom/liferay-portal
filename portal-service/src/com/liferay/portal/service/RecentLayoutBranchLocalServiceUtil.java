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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for RecentLayoutBranch. This utility wraps
 * {@link com.liferay.portal.service.impl.RecentLayoutBranchLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see RecentLayoutBranchLocalService
 * @see com.liferay.portal.service.base.RecentLayoutBranchLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.RecentLayoutBranchLocalServiceImpl
 * @generated
 */
@ProviderType
public class RecentLayoutBranchLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.RecentLayoutBranchLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.model.RecentLayoutBranch addRecentLayoutBranch(
		long companyId, long groupId, long userId, long layoutBranchId,
		long layoutSetBranchId, long plid) {
		return getService()
				   .addRecentLayoutBranch(companyId, groupId, userId,
			layoutBranchId, layoutSetBranchId, plid);
	}

	/**
	* Adds the recent layout branch to the database. Also notifies the appropriate model listeners.
	*
	* @param recentLayoutBranch the recent layout branch
	* @return the recent layout branch that was added
	*/
	public static com.liferay.portal.model.RecentLayoutBranch addRecentLayoutBranch(
		com.liferay.portal.model.RecentLayoutBranch recentLayoutBranch) {
		return getService().addRecentLayoutBranch(recentLayoutBranch);
	}

	/**
	* Creates a new recent layout branch with the primary key. Does not add the recent layout branch to the database.
	*
	* @param recentLayoutBranchId the primary key for the new recent layout branch
	* @return the new recent layout branch
	*/
	public static com.liferay.portal.model.RecentLayoutBranch createRecentLayoutBranch(
		long recentLayoutBranchId) {
		return getService().createRecentLayoutBranch(recentLayoutBranchId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the recent layout branch from the database. Also notifies the appropriate model listeners.
	*
	* @param recentLayoutBranch the recent layout branch
	* @return the recent layout branch that was removed
	*/
	public static com.liferay.portal.model.RecentLayoutBranch deleteRecentLayoutBranch(
		com.liferay.portal.model.RecentLayoutBranch recentLayoutBranch) {
		return getService().deleteRecentLayoutBranch(recentLayoutBranch);
	}

	/**
	* Deletes the recent layout branch with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param recentLayoutBranchId the primary key of the recent layout branch
	* @return the recent layout branch that was removed
	* @throws PortalException if a recent layout branch with the primary key could not be found
	*/
	public static com.liferay.portal.model.RecentLayoutBranch deleteRecentLayoutBranch(
		long recentLayoutBranchId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteRecentLayoutBranch(recentLayoutBranchId);
	}

	public static void deleteRecentLayoutBranches(
		com.liferay.portal.model.LayoutBranch layoutBranch) {
		getService().deleteRecentLayoutBranches(layoutBranch);
	}

	public static void deleteRecentLayoutBranches(
		com.liferay.portal.model.User user) {
		getService().deleteRecentLayoutBranches(user);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RecentLayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RecentLayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
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

	public static com.liferay.portal.model.RecentLayoutBranch fetchRecentLayoutBranch(
		long recentLayoutBranchId) {
		return getService().fetchRecentLayoutBranch(recentLayoutBranchId);
	}

	public static com.liferay.portal.model.RecentLayoutBranch fetchRecentLayoutBranch(
		long userId, long layoutSetBranchId, long plid) {
		return getService()
				   .fetchRecentLayoutBranch(userId, layoutSetBranchId, plid);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the recent layout branch with the primary key.
	*
	* @param recentLayoutBranchId the primary key of the recent layout branch
	* @return the recent layout branch
	* @throws PortalException if a recent layout branch with the primary key could not be found
	*/
	public static com.liferay.portal.model.RecentLayoutBranch getRecentLayoutBranch(
		long recentLayoutBranchId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getRecentLayoutBranch(recentLayoutBranchId);
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
	public static java.util.List<com.liferay.portal.model.RecentLayoutBranch> getRecentLayoutBranchs(
		int start, int end) {
		return getService().getRecentLayoutBranchs(start, end);
	}

	/**
	* Returns the number of recent layout branchs.
	*
	* @return the number of recent layout branchs
	*/
	public static int getRecentLayoutBranchsCount() {
		return getService().getRecentLayoutBranchsCount();
	}

	/**
	* Updates the recent layout branch in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param recentLayoutBranch the recent layout branch
	* @return the recent layout branch that was updated
	*/
	public static com.liferay.portal.model.RecentLayoutBranch updateRecentLayoutBranch(
		com.liferay.portal.model.RecentLayoutBranch recentLayoutBranch) {
		return getService().updateRecentLayoutBranch(recentLayoutBranch);
	}

	public static RecentLayoutBranchLocalService getService() {
		if (_service == null) {
			_service = (RecentLayoutBranchLocalService)PortalBeanLocatorUtil.locate(RecentLayoutBranchLocalService.class.getName());

			ReferenceRegistry.registerReference(RecentLayoutBranchLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(RecentLayoutBranchLocalService service) {
	}

	private static RecentLayoutBranchLocalService _service;
}
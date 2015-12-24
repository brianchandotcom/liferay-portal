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

package com.liferay.portal.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.model.RecentLayoutBranch;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the recent layout branch service. This utility wraps {@link com.liferay.portal.service.persistence.impl.RecentLayoutBranchPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RecentLayoutBranchPersistence
 * @see com.liferay.portal.service.persistence.impl.RecentLayoutBranchPersistenceImpl
 * @generated
 */
@ProviderType
public class RecentLayoutBranchUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(RecentLayoutBranch recentLayoutBranch) {
		getPersistence().clearCache(recentLayoutBranch);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<RecentLayoutBranch> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<RecentLayoutBranch> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<RecentLayoutBranch> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<RecentLayoutBranch> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static RecentLayoutBranch update(
		RecentLayoutBranch recentLayoutBranch) {
		return getPersistence().update(recentLayoutBranch);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static RecentLayoutBranch update(
		RecentLayoutBranch recentLayoutBranch, ServiceContext serviceContext) {
		return getPersistence().update(recentLayoutBranch, serviceContext);
	}

	/**
	* Returns all the recent layout branchs where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching recent layout branchs
	*/
	public static List<RecentLayoutBranch> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the recent layout branchs where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of recent layout branchs
	* @param end the upper bound of the range of recent layout branchs (not inclusive)
	* @return the range of matching recent layout branchs
	*/
	public static List<RecentLayoutBranch> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the recent layout branchs where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of recent layout branchs
	* @param end the upper bound of the range of recent layout branchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching recent layout branchs
	*/
	public static List<RecentLayoutBranch> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<RecentLayoutBranch> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the recent layout branchs where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of recent layout branchs
	* @param end the upper bound of the range of recent layout branchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching recent layout branchs
	*/
	public static List<RecentLayoutBranch> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<RecentLayoutBranch> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first recent layout branch in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching recent layout branch
	* @throws NoSuchRecentLayoutBranchException if a matching recent layout branch could not be found
	*/
	public static RecentLayoutBranch findByGroupId_First(long groupId,
		OrderByComparator<RecentLayoutBranch> orderByComparator)
		throws com.liferay.portal.NoSuchRecentLayoutBranchException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first recent layout branch in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching recent layout branch, or <code>null</code> if a matching recent layout branch could not be found
	*/
	public static RecentLayoutBranch fetchByGroupId_First(long groupId,
		OrderByComparator<RecentLayoutBranch> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last recent layout branch in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching recent layout branch
	* @throws NoSuchRecentLayoutBranchException if a matching recent layout branch could not be found
	*/
	public static RecentLayoutBranch findByGroupId_Last(long groupId,
		OrderByComparator<RecentLayoutBranch> orderByComparator)
		throws com.liferay.portal.NoSuchRecentLayoutBranchException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last recent layout branch in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching recent layout branch, or <code>null</code> if a matching recent layout branch could not be found
	*/
	public static RecentLayoutBranch fetchByGroupId_Last(long groupId,
		OrderByComparator<RecentLayoutBranch> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the recent layout branchs before and after the current recent layout branch in the ordered set where groupId = &#63;.
	*
	* @param recentLayoutBranchId the primary key of the current recent layout branch
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next recent layout branch
	* @throws NoSuchRecentLayoutBranchException if a recent layout branch with the primary key could not be found
	*/
	public static RecentLayoutBranch[] findByGroupId_PrevAndNext(
		long recentLayoutBranchId, long groupId,
		OrderByComparator<RecentLayoutBranch> orderByComparator)
		throws com.liferay.portal.NoSuchRecentLayoutBranchException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(recentLayoutBranchId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the recent layout branchs where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of recent layout branchs where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching recent layout branchs
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the recent layout branchs where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching recent layout branchs
	*/
	public static List<RecentLayoutBranch> findByUserId(long userId) {
		return getPersistence().findByUserId(userId);
	}

	/**
	* Returns a range of all the recent layout branchs where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of recent layout branchs
	* @param end the upper bound of the range of recent layout branchs (not inclusive)
	* @return the range of matching recent layout branchs
	*/
	public static List<RecentLayoutBranch> findByUserId(long userId, int start,
		int end) {
		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	* Returns an ordered range of all the recent layout branchs where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of recent layout branchs
	* @param end the upper bound of the range of recent layout branchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching recent layout branchs
	*/
	public static List<RecentLayoutBranch> findByUserId(long userId, int start,
		int end, OrderByComparator<RecentLayoutBranch> orderByComparator) {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the recent layout branchs where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of recent layout branchs
	* @param end the upper bound of the range of recent layout branchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching recent layout branchs
	*/
	public static List<RecentLayoutBranch> findByUserId(long userId, int start,
		int end, OrderByComparator<RecentLayoutBranch> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first recent layout branch in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching recent layout branch
	* @throws NoSuchRecentLayoutBranchException if a matching recent layout branch could not be found
	*/
	public static RecentLayoutBranch findByUserId_First(long userId,
		OrderByComparator<RecentLayoutBranch> orderByComparator)
		throws com.liferay.portal.NoSuchRecentLayoutBranchException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the first recent layout branch in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching recent layout branch, or <code>null</code> if a matching recent layout branch could not be found
	*/
	public static RecentLayoutBranch fetchByUserId_First(long userId,
		OrderByComparator<RecentLayoutBranch> orderByComparator) {
		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the last recent layout branch in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching recent layout branch
	* @throws NoSuchRecentLayoutBranchException if a matching recent layout branch could not be found
	*/
	public static RecentLayoutBranch findByUserId_Last(long userId,
		OrderByComparator<RecentLayoutBranch> orderByComparator)
		throws com.liferay.portal.NoSuchRecentLayoutBranchException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the last recent layout branch in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching recent layout branch, or <code>null</code> if a matching recent layout branch could not be found
	*/
	public static RecentLayoutBranch fetchByUserId_Last(long userId,
		OrderByComparator<RecentLayoutBranch> orderByComparator) {
		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the recent layout branchs before and after the current recent layout branch in the ordered set where userId = &#63;.
	*
	* @param recentLayoutBranchId the primary key of the current recent layout branch
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next recent layout branch
	* @throws NoSuchRecentLayoutBranchException if a recent layout branch with the primary key could not be found
	*/
	public static RecentLayoutBranch[] findByUserId_PrevAndNext(
		long recentLayoutBranchId, long userId,
		OrderByComparator<RecentLayoutBranch> orderByComparator)
		throws com.liferay.portal.NoSuchRecentLayoutBranchException {
		return getPersistence()
				   .findByUserId_PrevAndNext(recentLayoutBranchId, userId,
			orderByComparator);
	}

	/**
	* Removes all the recent layout branchs where userId = &#63; from the database.
	*
	* @param userId the user ID
	*/
	public static void removeByUserId(long userId) {
		getPersistence().removeByUserId(userId);
	}

	/**
	* Returns the number of recent layout branchs where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching recent layout branchs
	*/
	public static int countByUserId(long userId) {
		return getPersistence().countByUserId(userId);
	}

	/**
	* Returns all the recent layout branchs where layoutBranchId = &#63;.
	*
	* @param layoutBranchId the layout branch ID
	* @return the matching recent layout branchs
	*/
	public static List<RecentLayoutBranch> findByLayoutBranchId(
		long layoutBranchId) {
		return getPersistence().findByLayoutBranchId(layoutBranchId);
	}

	/**
	* Returns a range of all the recent layout branchs where layoutBranchId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutBranchId the layout branch ID
	* @param start the lower bound of the range of recent layout branchs
	* @param end the upper bound of the range of recent layout branchs (not inclusive)
	* @return the range of matching recent layout branchs
	*/
	public static List<RecentLayoutBranch> findByLayoutBranchId(
		long layoutBranchId, int start, int end) {
		return getPersistence().findByLayoutBranchId(layoutBranchId, start, end);
	}

	/**
	* Returns an ordered range of all the recent layout branchs where layoutBranchId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutBranchId the layout branch ID
	* @param start the lower bound of the range of recent layout branchs
	* @param end the upper bound of the range of recent layout branchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching recent layout branchs
	*/
	public static List<RecentLayoutBranch> findByLayoutBranchId(
		long layoutBranchId, int start, int end,
		OrderByComparator<RecentLayoutBranch> orderByComparator) {
		return getPersistence()
				   .findByLayoutBranchId(layoutBranchId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the recent layout branchs where layoutBranchId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutBranchId the layout branch ID
	* @param start the lower bound of the range of recent layout branchs
	* @param end the upper bound of the range of recent layout branchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching recent layout branchs
	*/
	public static List<RecentLayoutBranch> findByLayoutBranchId(
		long layoutBranchId, int start, int end,
		OrderByComparator<RecentLayoutBranch> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByLayoutBranchId(layoutBranchId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first recent layout branch in the ordered set where layoutBranchId = &#63;.
	*
	* @param layoutBranchId the layout branch ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching recent layout branch
	* @throws NoSuchRecentLayoutBranchException if a matching recent layout branch could not be found
	*/
	public static RecentLayoutBranch findByLayoutBranchId_First(
		long layoutBranchId,
		OrderByComparator<RecentLayoutBranch> orderByComparator)
		throws com.liferay.portal.NoSuchRecentLayoutBranchException {
		return getPersistence()
				   .findByLayoutBranchId_First(layoutBranchId, orderByComparator);
	}

	/**
	* Returns the first recent layout branch in the ordered set where layoutBranchId = &#63;.
	*
	* @param layoutBranchId the layout branch ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching recent layout branch, or <code>null</code> if a matching recent layout branch could not be found
	*/
	public static RecentLayoutBranch fetchByLayoutBranchId_First(
		long layoutBranchId,
		OrderByComparator<RecentLayoutBranch> orderByComparator) {
		return getPersistence()
				   .fetchByLayoutBranchId_First(layoutBranchId,
			orderByComparator);
	}

	/**
	* Returns the last recent layout branch in the ordered set where layoutBranchId = &#63;.
	*
	* @param layoutBranchId the layout branch ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching recent layout branch
	* @throws NoSuchRecentLayoutBranchException if a matching recent layout branch could not be found
	*/
	public static RecentLayoutBranch findByLayoutBranchId_Last(
		long layoutBranchId,
		OrderByComparator<RecentLayoutBranch> orderByComparator)
		throws com.liferay.portal.NoSuchRecentLayoutBranchException {
		return getPersistence()
				   .findByLayoutBranchId_Last(layoutBranchId, orderByComparator);
	}

	/**
	* Returns the last recent layout branch in the ordered set where layoutBranchId = &#63;.
	*
	* @param layoutBranchId the layout branch ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching recent layout branch, or <code>null</code> if a matching recent layout branch could not be found
	*/
	public static RecentLayoutBranch fetchByLayoutBranchId_Last(
		long layoutBranchId,
		OrderByComparator<RecentLayoutBranch> orderByComparator) {
		return getPersistence()
				   .fetchByLayoutBranchId_Last(layoutBranchId, orderByComparator);
	}

	/**
	* Returns the recent layout branchs before and after the current recent layout branch in the ordered set where layoutBranchId = &#63;.
	*
	* @param recentLayoutBranchId the primary key of the current recent layout branch
	* @param layoutBranchId the layout branch ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next recent layout branch
	* @throws NoSuchRecentLayoutBranchException if a recent layout branch with the primary key could not be found
	*/
	public static RecentLayoutBranch[] findByLayoutBranchId_PrevAndNext(
		long recentLayoutBranchId, long layoutBranchId,
		OrderByComparator<RecentLayoutBranch> orderByComparator)
		throws com.liferay.portal.NoSuchRecentLayoutBranchException {
		return getPersistence()
				   .findByLayoutBranchId_PrevAndNext(recentLayoutBranchId,
			layoutBranchId, orderByComparator);
	}

	/**
	* Removes all the recent layout branchs where layoutBranchId = &#63; from the database.
	*
	* @param layoutBranchId the layout branch ID
	*/
	public static void removeByLayoutBranchId(long layoutBranchId) {
		getPersistence().removeByLayoutBranchId(layoutBranchId);
	}

	/**
	* Returns the number of recent layout branchs where layoutBranchId = &#63;.
	*
	* @param layoutBranchId the layout branch ID
	* @return the number of matching recent layout branchs
	*/
	public static int countByLayoutBranchId(long layoutBranchId) {
		return getPersistence().countByLayoutBranchId(layoutBranchId);
	}

	/**
	* Returns the recent layout branch where userId = &#63; and layoutSetBranchId = &#63; and plid = &#63; or throws a {@link NoSuchRecentLayoutBranchException} if it could not be found.
	*
	* @param userId the user ID
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @return the matching recent layout branch
	* @throws NoSuchRecentLayoutBranchException if a matching recent layout branch could not be found
	*/
	public static RecentLayoutBranch findByU_L_P(long userId,
		long layoutSetBranchId, long plid)
		throws com.liferay.portal.NoSuchRecentLayoutBranchException {
		return getPersistence().findByU_L_P(userId, layoutSetBranchId, plid);
	}

	/**
	* Returns the recent layout branch where userId = &#63; and layoutSetBranchId = &#63; and plid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @return the matching recent layout branch, or <code>null</code> if a matching recent layout branch could not be found
	*/
	public static RecentLayoutBranch fetchByU_L_P(long userId,
		long layoutSetBranchId, long plid) {
		return getPersistence().fetchByU_L_P(userId, layoutSetBranchId, plid);
	}

	/**
	* Returns the recent layout branch where userId = &#63; and layoutSetBranchId = &#63; and plid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching recent layout branch, or <code>null</code> if a matching recent layout branch could not be found
	*/
	public static RecentLayoutBranch fetchByU_L_P(long userId,
		long layoutSetBranchId, long plid, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByU_L_P(userId, layoutSetBranchId, plid,
			retrieveFromCache);
	}

	/**
	* Removes the recent layout branch where userId = &#63; and layoutSetBranchId = &#63; and plid = &#63; from the database.
	*
	* @param userId the user ID
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @return the recent layout branch that was removed
	*/
	public static RecentLayoutBranch removeByU_L_P(long userId,
		long layoutSetBranchId, long plid)
		throws com.liferay.portal.NoSuchRecentLayoutBranchException {
		return getPersistence().removeByU_L_P(userId, layoutSetBranchId, plid);
	}

	/**
	* Returns the number of recent layout branchs where userId = &#63; and layoutSetBranchId = &#63; and plid = &#63;.
	*
	* @param userId the user ID
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @return the number of matching recent layout branchs
	*/
	public static int countByU_L_P(long userId, long layoutSetBranchId,
		long plid) {
		return getPersistence().countByU_L_P(userId, layoutSetBranchId, plid);
	}

	/**
	* Caches the recent layout branch in the entity cache if it is enabled.
	*
	* @param recentLayoutBranch the recent layout branch
	*/
	public static void cacheResult(RecentLayoutBranch recentLayoutBranch) {
		getPersistence().cacheResult(recentLayoutBranch);
	}

	/**
	* Caches the recent layout branchs in the entity cache if it is enabled.
	*
	* @param recentLayoutBranchs the recent layout branchs
	*/
	public static void cacheResult(List<RecentLayoutBranch> recentLayoutBranchs) {
		getPersistence().cacheResult(recentLayoutBranchs);
	}

	/**
	* Creates a new recent layout branch with the primary key. Does not add the recent layout branch to the database.
	*
	* @param recentLayoutBranchId the primary key for the new recent layout branch
	* @return the new recent layout branch
	*/
	public static RecentLayoutBranch create(long recentLayoutBranchId) {
		return getPersistence().create(recentLayoutBranchId);
	}

	/**
	* Removes the recent layout branch with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param recentLayoutBranchId the primary key of the recent layout branch
	* @return the recent layout branch that was removed
	* @throws NoSuchRecentLayoutBranchException if a recent layout branch with the primary key could not be found
	*/
	public static RecentLayoutBranch remove(long recentLayoutBranchId)
		throws com.liferay.portal.NoSuchRecentLayoutBranchException {
		return getPersistence().remove(recentLayoutBranchId);
	}

	public static RecentLayoutBranch updateImpl(
		RecentLayoutBranch recentLayoutBranch) {
		return getPersistence().updateImpl(recentLayoutBranch);
	}

	/**
	* Returns the recent layout branch with the primary key or throws a {@link NoSuchRecentLayoutBranchException} if it could not be found.
	*
	* @param recentLayoutBranchId the primary key of the recent layout branch
	* @return the recent layout branch
	* @throws NoSuchRecentLayoutBranchException if a recent layout branch with the primary key could not be found
	*/
	public static RecentLayoutBranch findByPrimaryKey(long recentLayoutBranchId)
		throws com.liferay.portal.NoSuchRecentLayoutBranchException {
		return getPersistence().findByPrimaryKey(recentLayoutBranchId);
	}

	/**
	* Returns the recent layout branch with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param recentLayoutBranchId the primary key of the recent layout branch
	* @return the recent layout branch, or <code>null</code> if a recent layout branch with the primary key could not be found
	*/
	public static RecentLayoutBranch fetchByPrimaryKey(
		long recentLayoutBranchId) {
		return getPersistence().fetchByPrimaryKey(recentLayoutBranchId);
	}

	public static java.util.Map<java.io.Serializable, RecentLayoutBranch> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the recent layout branchs.
	*
	* @return the recent layout branchs
	*/
	public static List<RecentLayoutBranch> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the recent layout branchs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of recent layout branchs
	* @param end the upper bound of the range of recent layout branchs (not inclusive)
	* @return the range of recent layout branchs
	*/
	public static List<RecentLayoutBranch> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the recent layout branchs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of recent layout branchs
	* @param end the upper bound of the range of recent layout branchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of recent layout branchs
	*/
	public static List<RecentLayoutBranch> findAll(int start, int end,
		OrderByComparator<RecentLayoutBranch> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the recent layout branchs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of recent layout branchs
	* @param end the upper bound of the range of recent layout branchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of recent layout branchs
	*/
	public static List<RecentLayoutBranch> findAll(int start, int end,
		OrderByComparator<RecentLayoutBranch> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the recent layout branchs from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of recent layout branchs.
	*
	* @return the number of recent layout branchs
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static RecentLayoutBranchPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (RecentLayoutBranchPersistence)PortalBeanLocatorUtil.locate(RecentLayoutBranchPersistence.class.getName());

			ReferenceRegistry.registerReference(RecentLayoutBranchUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static RecentLayoutBranchPersistence _persistence;
}
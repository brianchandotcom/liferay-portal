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
import com.liferay.portal.model.RecentLayoutSetBranch;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the recent layout set branch service. This utility wraps {@link com.liferay.portal.service.persistence.impl.RecentLayoutSetBranchPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RecentLayoutSetBranchPersistence
 * @see com.liferay.portal.service.persistence.impl.RecentLayoutSetBranchPersistenceImpl
 * @generated
 */
@ProviderType
public class RecentLayoutSetBranchUtil {
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
	public static void clearCache(RecentLayoutSetBranch recentLayoutSetBranch) {
		getPersistence().clearCache(recentLayoutSetBranch);
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
	public static List<RecentLayoutSetBranch> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<RecentLayoutSetBranch> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<RecentLayoutSetBranch> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static RecentLayoutSetBranch update(
		RecentLayoutSetBranch recentLayoutSetBranch) {
		return getPersistence().update(recentLayoutSetBranch);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static RecentLayoutSetBranch update(
		RecentLayoutSetBranch recentLayoutSetBranch,
		ServiceContext serviceContext) {
		return getPersistence().update(recentLayoutSetBranch, serviceContext);
	}

	/**
	* Returns all the recent layout set branchs where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching recent layout set branchs
	*/
	public static List<RecentLayoutSetBranch> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the recent layout set branchs where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of recent layout set branchs
	* @param end the upper bound of the range of recent layout set branchs (not inclusive)
	* @return the range of matching recent layout set branchs
	*/
	public static List<RecentLayoutSetBranch> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the recent layout set branchs where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of recent layout set branchs
	* @param end the upper bound of the range of recent layout set branchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching recent layout set branchs
	*/
	public static List<RecentLayoutSetBranch> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the recent layout set branchs where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of recent layout set branchs
	* @param end the upper bound of the range of recent layout set branchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching recent layout set branchs
	*/
	public static List<RecentLayoutSetBranch> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first recent layout set branch in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching recent layout set branch
	* @throws NoSuchRecentLayoutSetBranchException if a matching recent layout set branch could not be found
	*/
	public static RecentLayoutSetBranch findByGroupId_First(long groupId,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator)
		throws com.liferay.portal.NoSuchRecentLayoutSetBranchException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first recent layout set branch in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching recent layout set branch, or <code>null</code> if a matching recent layout set branch could not be found
	*/
	public static RecentLayoutSetBranch fetchByGroupId_First(long groupId,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last recent layout set branch in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching recent layout set branch
	* @throws NoSuchRecentLayoutSetBranchException if a matching recent layout set branch could not be found
	*/
	public static RecentLayoutSetBranch findByGroupId_Last(long groupId,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator)
		throws com.liferay.portal.NoSuchRecentLayoutSetBranchException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last recent layout set branch in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching recent layout set branch, or <code>null</code> if a matching recent layout set branch could not be found
	*/
	public static RecentLayoutSetBranch fetchByGroupId_Last(long groupId,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the recent layout set branchs before and after the current recent layout set branch in the ordered set where groupId = &#63;.
	*
	* @param recentLayoutSetBranchId the primary key of the current recent layout set branch
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next recent layout set branch
	* @throws NoSuchRecentLayoutSetBranchException if a recent layout set branch with the primary key could not be found
	*/
	public static RecentLayoutSetBranch[] findByGroupId_PrevAndNext(
		long recentLayoutSetBranchId, long groupId,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator)
		throws com.liferay.portal.NoSuchRecentLayoutSetBranchException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(recentLayoutSetBranchId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the recent layout set branchs where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of recent layout set branchs where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching recent layout set branchs
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the recent layout set branchs where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching recent layout set branchs
	*/
	public static List<RecentLayoutSetBranch> findByUserId(long userId) {
		return getPersistence().findByUserId(userId);
	}

	/**
	* Returns a range of all the recent layout set branchs where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of recent layout set branchs
	* @param end the upper bound of the range of recent layout set branchs (not inclusive)
	* @return the range of matching recent layout set branchs
	*/
	public static List<RecentLayoutSetBranch> findByUserId(long userId,
		int start, int end) {
		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	* Returns an ordered range of all the recent layout set branchs where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of recent layout set branchs
	* @param end the upper bound of the range of recent layout set branchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching recent layout set branchs
	*/
	public static List<RecentLayoutSetBranch> findByUserId(long userId,
		int start, int end,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator) {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the recent layout set branchs where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of recent layout set branchs
	* @param end the upper bound of the range of recent layout set branchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching recent layout set branchs
	*/
	public static List<RecentLayoutSetBranch> findByUserId(long userId,
		int start, int end,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first recent layout set branch in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching recent layout set branch
	* @throws NoSuchRecentLayoutSetBranchException if a matching recent layout set branch could not be found
	*/
	public static RecentLayoutSetBranch findByUserId_First(long userId,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator)
		throws com.liferay.portal.NoSuchRecentLayoutSetBranchException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the first recent layout set branch in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching recent layout set branch, or <code>null</code> if a matching recent layout set branch could not be found
	*/
	public static RecentLayoutSetBranch fetchByUserId_First(long userId,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator) {
		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the last recent layout set branch in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching recent layout set branch
	* @throws NoSuchRecentLayoutSetBranchException if a matching recent layout set branch could not be found
	*/
	public static RecentLayoutSetBranch findByUserId_Last(long userId,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator)
		throws com.liferay.portal.NoSuchRecentLayoutSetBranchException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the last recent layout set branch in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching recent layout set branch, or <code>null</code> if a matching recent layout set branch could not be found
	*/
	public static RecentLayoutSetBranch fetchByUserId_Last(long userId,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator) {
		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the recent layout set branchs before and after the current recent layout set branch in the ordered set where userId = &#63;.
	*
	* @param recentLayoutSetBranchId the primary key of the current recent layout set branch
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next recent layout set branch
	* @throws NoSuchRecentLayoutSetBranchException if a recent layout set branch with the primary key could not be found
	*/
	public static RecentLayoutSetBranch[] findByUserId_PrevAndNext(
		long recentLayoutSetBranchId, long userId,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator)
		throws com.liferay.portal.NoSuchRecentLayoutSetBranchException {
		return getPersistence()
				   .findByUserId_PrevAndNext(recentLayoutSetBranchId, userId,
			orderByComparator);
	}

	/**
	* Removes all the recent layout set branchs where userId = &#63; from the database.
	*
	* @param userId the user ID
	*/
	public static void removeByUserId(long userId) {
		getPersistence().removeByUserId(userId);
	}

	/**
	* Returns the number of recent layout set branchs where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching recent layout set branchs
	*/
	public static int countByUserId(long userId) {
		return getPersistence().countByUserId(userId);
	}

	/**
	* Returns all the recent layout set branchs where layoutSetBranchId = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @return the matching recent layout set branchs
	*/
	public static List<RecentLayoutSetBranch> findByLayoutSetBranchId(
		long layoutSetBranchId) {
		return getPersistence().findByLayoutSetBranchId(layoutSetBranchId);
	}

	/**
	* Returns a range of all the recent layout set branchs where layoutSetBranchId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param start the lower bound of the range of recent layout set branchs
	* @param end the upper bound of the range of recent layout set branchs (not inclusive)
	* @return the range of matching recent layout set branchs
	*/
	public static List<RecentLayoutSetBranch> findByLayoutSetBranchId(
		long layoutSetBranchId, int start, int end) {
		return getPersistence()
				   .findByLayoutSetBranchId(layoutSetBranchId, start, end);
	}

	/**
	* Returns an ordered range of all the recent layout set branchs where layoutSetBranchId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param start the lower bound of the range of recent layout set branchs
	* @param end the upper bound of the range of recent layout set branchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching recent layout set branchs
	*/
	public static List<RecentLayoutSetBranch> findByLayoutSetBranchId(
		long layoutSetBranchId, int start, int end,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator) {
		return getPersistence()
				   .findByLayoutSetBranchId(layoutSetBranchId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the recent layout set branchs where layoutSetBranchId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param start the lower bound of the range of recent layout set branchs
	* @param end the upper bound of the range of recent layout set branchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching recent layout set branchs
	*/
	public static List<RecentLayoutSetBranch> findByLayoutSetBranchId(
		long layoutSetBranchId, int start, int end,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByLayoutSetBranchId(layoutSetBranchId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first recent layout set branch in the ordered set where layoutSetBranchId = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching recent layout set branch
	* @throws NoSuchRecentLayoutSetBranchException if a matching recent layout set branch could not be found
	*/
	public static RecentLayoutSetBranch findByLayoutSetBranchId_First(
		long layoutSetBranchId,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator)
		throws com.liferay.portal.NoSuchRecentLayoutSetBranchException {
		return getPersistence()
				   .findByLayoutSetBranchId_First(layoutSetBranchId,
			orderByComparator);
	}

	/**
	* Returns the first recent layout set branch in the ordered set where layoutSetBranchId = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching recent layout set branch, or <code>null</code> if a matching recent layout set branch could not be found
	*/
	public static RecentLayoutSetBranch fetchByLayoutSetBranchId_First(
		long layoutSetBranchId,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator) {
		return getPersistence()
				   .fetchByLayoutSetBranchId_First(layoutSetBranchId,
			orderByComparator);
	}

	/**
	* Returns the last recent layout set branch in the ordered set where layoutSetBranchId = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching recent layout set branch
	* @throws NoSuchRecentLayoutSetBranchException if a matching recent layout set branch could not be found
	*/
	public static RecentLayoutSetBranch findByLayoutSetBranchId_Last(
		long layoutSetBranchId,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator)
		throws com.liferay.portal.NoSuchRecentLayoutSetBranchException {
		return getPersistence()
				   .findByLayoutSetBranchId_Last(layoutSetBranchId,
			orderByComparator);
	}

	/**
	* Returns the last recent layout set branch in the ordered set where layoutSetBranchId = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching recent layout set branch, or <code>null</code> if a matching recent layout set branch could not be found
	*/
	public static RecentLayoutSetBranch fetchByLayoutSetBranchId_Last(
		long layoutSetBranchId,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator) {
		return getPersistence()
				   .fetchByLayoutSetBranchId_Last(layoutSetBranchId,
			orderByComparator);
	}

	/**
	* Returns the recent layout set branchs before and after the current recent layout set branch in the ordered set where layoutSetBranchId = &#63;.
	*
	* @param recentLayoutSetBranchId the primary key of the current recent layout set branch
	* @param layoutSetBranchId the layout set branch ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next recent layout set branch
	* @throws NoSuchRecentLayoutSetBranchException if a recent layout set branch with the primary key could not be found
	*/
	public static RecentLayoutSetBranch[] findByLayoutSetBranchId_PrevAndNext(
		long recentLayoutSetBranchId, long layoutSetBranchId,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator)
		throws com.liferay.portal.NoSuchRecentLayoutSetBranchException {
		return getPersistence()
				   .findByLayoutSetBranchId_PrevAndNext(recentLayoutSetBranchId,
			layoutSetBranchId, orderByComparator);
	}

	/**
	* Removes all the recent layout set branchs where layoutSetBranchId = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID
	*/
	public static void removeByLayoutSetBranchId(long layoutSetBranchId) {
		getPersistence().removeByLayoutSetBranchId(layoutSetBranchId);
	}

	/**
	* Returns the number of recent layout set branchs where layoutSetBranchId = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @return the number of matching recent layout set branchs
	*/
	public static int countByLayoutSetBranchId(long layoutSetBranchId) {
		return getPersistence().countByLayoutSetBranchId(layoutSetBranchId);
	}

	/**
	* Returns the recent layout set branch where userId = &#63; and layoutSetId = &#63; or throws a {@link NoSuchRecentLayoutSetBranchException} if it could not be found.
	*
	* @param userId the user ID
	* @param layoutSetId the layout set ID
	* @return the matching recent layout set branch
	* @throws NoSuchRecentLayoutSetBranchException if a matching recent layout set branch could not be found
	*/
	public static RecentLayoutSetBranch findByU_L(long userId, long layoutSetId)
		throws com.liferay.portal.NoSuchRecentLayoutSetBranchException {
		return getPersistence().findByU_L(userId, layoutSetId);
	}

	/**
	* Returns the recent layout set branch where userId = &#63; and layoutSetId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID
	* @param layoutSetId the layout set ID
	* @return the matching recent layout set branch, or <code>null</code> if a matching recent layout set branch could not be found
	*/
	public static RecentLayoutSetBranch fetchByU_L(long userId, long layoutSetId) {
		return getPersistence().fetchByU_L(userId, layoutSetId);
	}

	/**
	* Returns the recent layout set branch where userId = &#63; and layoutSetId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID
	* @param layoutSetId the layout set ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching recent layout set branch, or <code>null</code> if a matching recent layout set branch could not be found
	*/
	public static RecentLayoutSetBranch fetchByU_L(long userId,
		long layoutSetId, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByU_L(userId, layoutSetId, retrieveFromCache);
	}

	/**
	* Removes the recent layout set branch where userId = &#63; and layoutSetId = &#63; from the database.
	*
	* @param userId the user ID
	* @param layoutSetId the layout set ID
	* @return the recent layout set branch that was removed
	*/
	public static RecentLayoutSetBranch removeByU_L(long userId,
		long layoutSetId)
		throws com.liferay.portal.NoSuchRecentLayoutSetBranchException {
		return getPersistence().removeByU_L(userId, layoutSetId);
	}

	/**
	* Returns the number of recent layout set branchs where userId = &#63; and layoutSetId = &#63;.
	*
	* @param userId the user ID
	* @param layoutSetId the layout set ID
	* @return the number of matching recent layout set branchs
	*/
	public static int countByU_L(long userId, long layoutSetId) {
		return getPersistence().countByU_L(userId, layoutSetId);
	}

	/**
	* Caches the recent layout set branch in the entity cache if it is enabled.
	*
	* @param recentLayoutSetBranch the recent layout set branch
	*/
	public static void cacheResult(RecentLayoutSetBranch recentLayoutSetBranch) {
		getPersistence().cacheResult(recentLayoutSetBranch);
	}

	/**
	* Caches the recent layout set branchs in the entity cache if it is enabled.
	*
	* @param recentLayoutSetBranchs the recent layout set branchs
	*/
	public static void cacheResult(
		List<RecentLayoutSetBranch> recentLayoutSetBranchs) {
		getPersistence().cacheResult(recentLayoutSetBranchs);
	}

	/**
	* Creates a new recent layout set branch with the primary key. Does not add the recent layout set branch to the database.
	*
	* @param recentLayoutSetBranchId the primary key for the new recent layout set branch
	* @return the new recent layout set branch
	*/
	public static RecentLayoutSetBranch create(long recentLayoutSetBranchId) {
		return getPersistence().create(recentLayoutSetBranchId);
	}

	/**
	* Removes the recent layout set branch with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param recentLayoutSetBranchId the primary key of the recent layout set branch
	* @return the recent layout set branch that was removed
	* @throws NoSuchRecentLayoutSetBranchException if a recent layout set branch with the primary key could not be found
	*/
	public static RecentLayoutSetBranch remove(long recentLayoutSetBranchId)
		throws com.liferay.portal.NoSuchRecentLayoutSetBranchException {
		return getPersistence().remove(recentLayoutSetBranchId);
	}

	public static RecentLayoutSetBranch updateImpl(
		RecentLayoutSetBranch recentLayoutSetBranch) {
		return getPersistence().updateImpl(recentLayoutSetBranch);
	}

	/**
	* Returns the recent layout set branch with the primary key or throws a {@link NoSuchRecentLayoutSetBranchException} if it could not be found.
	*
	* @param recentLayoutSetBranchId the primary key of the recent layout set branch
	* @return the recent layout set branch
	* @throws NoSuchRecentLayoutSetBranchException if a recent layout set branch with the primary key could not be found
	*/
	public static RecentLayoutSetBranch findByPrimaryKey(
		long recentLayoutSetBranchId)
		throws com.liferay.portal.NoSuchRecentLayoutSetBranchException {
		return getPersistence().findByPrimaryKey(recentLayoutSetBranchId);
	}

	/**
	* Returns the recent layout set branch with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param recentLayoutSetBranchId the primary key of the recent layout set branch
	* @return the recent layout set branch, or <code>null</code> if a recent layout set branch with the primary key could not be found
	*/
	public static RecentLayoutSetBranch fetchByPrimaryKey(
		long recentLayoutSetBranchId) {
		return getPersistence().fetchByPrimaryKey(recentLayoutSetBranchId);
	}

	public static java.util.Map<java.io.Serializable, RecentLayoutSetBranch> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the recent layout set branchs.
	*
	* @return the recent layout set branchs
	*/
	public static List<RecentLayoutSetBranch> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the recent layout set branchs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of recent layout set branchs
	* @param end the upper bound of the range of recent layout set branchs (not inclusive)
	* @return the range of recent layout set branchs
	*/
	public static List<RecentLayoutSetBranch> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the recent layout set branchs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of recent layout set branchs
	* @param end the upper bound of the range of recent layout set branchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of recent layout set branchs
	*/
	public static List<RecentLayoutSetBranch> findAll(int start, int end,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the recent layout set branchs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of recent layout set branchs
	* @param end the upper bound of the range of recent layout set branchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of recent layout set branchs
	*/
	public static List<RecentLayoutSetBranch> findAll(int start, int end,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the recent layout set branchs from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of recent layout set branchs.
	*
	* @return the number of recent layout set branchs
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static RecentLayoutSetBranchPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (RecentLayoutSetBranchPersistence)PortalBeanLocatorUtil.locate(RecentLayoutSetBranchPersistence.class.getName());

			ReferenceRegistry.registerReference(RecentLayoutSetBranchUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static RecentLayoutSetBranchPersistence _persistence;
}
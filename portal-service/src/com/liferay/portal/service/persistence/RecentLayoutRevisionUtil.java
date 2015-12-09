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
import com.liferay.portal.model.RecentLayoutRevision;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the recent layout revision service. This utility wraps {@link com.liferay.portal.service.persistence.impl.RecentLayoutRevisionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RecentLayoutRevisionPersistence
 * @see com.liferay.portal.service.persistence.impl.RecentLayoutRevisionPersistenceImpl
 * @generated
 */
@ProviderType
public class RecentLayoutRevisionUtil {
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
	public static void clearCache(RecentLayoutRevision recentLayoutRevision) {
		getPersistence().clearCache(recentLayoutRevision);
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
	public static List<RecentLayoutRevision> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<RecentLayoutRevision> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<RecentLayoutRevision> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<RecentLayoutRevision> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static RecentLayoutRevision update(
		RecentLayoutRevision recentLayoutRevision) {
		return getPersistence().update(recentLayoutRevision);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static RecentLayoutRevision update(
		RecentLayoutRevision recentLayoutRevision, ServiceContext serviceContext) {
		return getPersistence().update(recentLayoutRevision, serviceContext);
	}

	/**
	* Returns all the recent layout revisions where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching recent layout revisions
	*/
	public static List<RecentLayoutRevision> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the recent layout revisions where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of recent layout revisions
	* @param end the upper bound of the range of recent layout revisions (not inclusive)
	* @return the range of matching recent layout revisions
	*/
	public static List<RecentLayoutRevision> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the recent layout revisions where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of recent layout revisions
	* @param end the upper bound of the range of recent layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching recent layout revisions
	*/
	public static List<RecentLayoutRevision> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<RecentLayoutRevision> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the recent layout revisions where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of recent layout revisions
	* @param end the upper bound of the range of recent layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching recent layout revisions
	*/
	public static List<RecentLayoutRevision> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<RecentLayoutRevision> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first recent layout revision in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching recent layout revision
	* @throws NoSuchRecentLayoutRevisionException if a matching recent layout revision could not be found
	*/
	public static RecentLayoutRevision findByGroupId_First(long groupId,
		OrderByComparator<RecentLayoutRevision> orderByComparator)
		throws com.liferay.portal.NoSuchRecentLayoutRevisionException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first recent layout revision in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching recent layout revision, or <code>null</code> if a matching recent layout revision could not be found
	*/
	public static RecentLayoutRevision fetchByGroupId_First(long groupId,
		OrderByComparator<RecentLayoutRevision> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last recent layout revision in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching recent layout revision
	* @throws NoSuchRecentLayoutRevisionException if a matching recent layout revision could not be found
	*/
	public static RecentLayoutRevision findByGroupId_Last(long groupId,
		OrderByComparator<RecentLayoutRevision> orderByComparator)
		throws com.liferay.portal.NoSuchRecentLayoutRevisionException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last recent layout revision in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching recent layout revision, or <code>null</code> if a matching recent layout revision could not be found
	*/
	public static RecentLayoutRevision fetchByGroupId_Last(long groupId,
		OrderByComparator<RecentLayoutRevision> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the recent layout revisions before and after the current recent layout revision in the ordered set where groupId = &#63;.
	*
	* @param recentLayoutRevisionId the primary key of the current recent layout revision
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next recent layout revision
	* @throws NoSuchRecentLayoutRevisionException if a recent layout revision with the primary key could not be found
	*/
	public static RecentLayoutRevision[] findByGroupId_PrevAndNext(
		long recentLayoutRevisionId, long groupId,
		OrderByComparator<RecentLayoutRevision> orderByComparator)
		throws com.liferay.portal.NoSuchRecentLayoutRevisionException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(recentLayoutRevisionId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the recent layout revisions where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of recent layout revisions where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching recent layout revisions
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the recent layout revisions where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching recent layout revisions
	*/
	public static List<RecentLayoutRevision> findByUserId(long userId) {
		return getPersistence().findByUserId(userId);
	}

	/**
	* Returns a range of all the recent layout revisions where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of recent layout revisions
	* @param end the upper bound of the range of recent layout revisions (not inclusive)
	* @return the range of matching recent layout revisions
	*/
	public static List<RecentLayoutRevision> findByUserId(long userId,
		int start, int end) {
		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	* Returns an ordered range of all the recent layout revisions where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of recent layout revisions
	* @param end the upper bound of the range of recent layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching recent layout revisions
	*/
	public static List<RecentLayoutRevision> findByUserId(long userId,
		int start, int end,
		OrderByComparator<RecentLayoutRevision> orderByComparator) {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the recent layout revisions where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of recent layout revisions
	* @param end the upper bound of the range of recent layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching recent layout revisions
	*/
	public static List<RecentLayoutRevision> findByUserId(long userId,
		int start, int end,
		OrderByComparator<RecentLayoutRevision> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first recent layout revision in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching recent layout revision
	* @throws NoSuchRecentLayoutRevisionException if a matching recent layout revision could not be found
	*/
	public static RecentLayoutRevision findByUserId_First(long userId,
		OrderByComparator<RecentLayoutRevision> orderByComparator)
		throws com.liferay.portal.NoSuchRecentLayoutRevisionException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the first recent layout revision in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching recent layout revision, or <code>null</code> if a matching recent layout revision could not be found
	*/
	public static RecentLayoutRevision fetchByUserId_First(long userId,
		OrderByComparator<RecentLayoutRevision> orderByComparator) {
		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the last recent layout revision in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching recent layout revision
	* @throws NoSuchRecentLayoutRevisionException if a matching recent layout revision could not be found
	*/
	public static RecentLayoutRevision findByUserId_Last(long userId,
		OrderByComparator<RecentLayoutRevision> orderByComparator)
		throws com.liferay.portal.NoSuchRecentLayoutRevisionException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the last recent layout revision in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching recent layout revision, or <code>null</code> if a matching recent layout revision could not be found
	*/
	public static RecentLayoutRevision fetchByUserId_Last(long userId,
		OrderByComparator<RecentLayoutRevision> orderByComparator) {
		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the recent layout revisions before and after the current recent layout revision in the ordered set where userId = &#63;.
	*
	* @param recentLayoutRevisionId the primary key of the current recent layout revision
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next recent layout revision
	* @throws NoSuchRecentLayoutRevisionException if a recent layout revision with the primary key could not be found
	*/
	public static RecentLayoutRevision[] findByUserId_PrevAndNext(
		long recentLayoutRevisionId, long userId,
		OrderByComparator<RecentLayoutRevision> orderByComparator)
		throws com.liferay.portal.NoSuchRecentLayoutRevisionException {
		return getPersistence()
				   .findByUserId_PrevAndNext(recentLayoutRevisionId, userId,
			orderByComparator);
	}

	/**
	* Removes all the recent layout revisions where userId = &#63; from the database.
	*
	* @param userId the user ID
	*/
	public static void removeByUserId(long userId) {
		getPersistence().removeByUserId(userId);
	}

	/**
	* Returns the number of recent layout revisions where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching recent layout revisions
	*/
	public static int countByUserId(long userId) {
		return getPersistence().countByUserId(userId);
	}

	/**
	* Returns all the recent layout revisions where layoutRevisionId = &#63;.
	*
	* @param layoutRevisionId the layout revision ID
	* @return the matching recent layout revisions
	*/
	public static List<RecentLayoutRevision> findByLayoutRevisionId(
		long layoutRevisionId) {
		return getPersistence().findByLayoutRevisionId(layoutRevisionId);
	}

	/**
	* Returns a range of all the recent layout revisions where layoutRevisionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutRevisionId the layout revision ID
	* @param start the lower bound of the range of recent layout revisions
	* @param end the upper bound of the range of recent layout revisions (not inclusive)
	* @return the range of matching recent layout revisions
	*/
	public static List<RecentLayoutRevision> findByLayoutRevisionId(
		long layoutRevisionId, int start, int end) {
		return getPersistence()
				   .findByLayoutRevisionId(layoutRevisionId, start, end);
	}

	/**
	* Returns an ordered range of all the recent layout revisions where layoutRevisionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutRevisionId the layout revision ID
	* @param start the lower bound of the range of recent layout revisions
	* @param end the upper bound of the range of recent layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching recent layout revisions
	*/
	public static List<RecentLayoutRevision> findByLayoutRevisionId(
		long layoutRevisionId, int start, int end,
		OrderByComparator<RecentLayoutRevision> orderByComparator) {
		return getPersistence()
				   .findByLayoutRevisionId(layoutRevisionId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the recent layout revisions where layoutRevisionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutRevisionId the layout revision ID
	* @param start the lower bound of the range of recent layout revisions
	* @param end the upper bound of the range of recent layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching recent layout revisions
	*/
	public static List<RecentLayoutRevision> findByLayoutRevisionId(
		long layoutRevisionId, int start, int end,
		OrderByComparator<RecentLayoutRevision> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByLayoutRevisionId(layoutRevisionId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first recent layout revision in the ordered set where layoutRevisionId = &#63;.
	*
	* @param layoutRevisionId the layout revision ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching recent layout revision
	* @throws NoSuchRecentLayoutRevisionException if a matching recent layout revision could not be found
	*/
	public static RecentLayoutRevision findByLayoutRevisionId_First(
		long layoutRevisionId,
		OrderByComparator<RecentLayoutRevision> orderByComparator)
		throws com.liferay.portal.NoSuchRecentLayoutRevisionException {
		return getPersistence()
				   .findByLayoutRevisionId_First(layoutRevisionId,
			orderByComparator);
	}

	/**
	* Returns the first recent layout revision in the ordered set where layoutRevisionId = &#63;.
	*
	* @param layoutRevisionId the layout revision ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching recent layout revision, or <code>null</code> if a matching recent layout revision could not be found
	*/
	public static RecentLayoutRevision fetchByLayoutRevisionId_First(
		long layoutRevisionId,
		OrderByComparator<RecentLayoutRevision> orderByComparator) {
		return getPersistence()
				   .fetchByLayoutRevisionId_First(layoutRevisionId,
			orderByComparator);
	}

	/**
	* Returns the last recent layout revision in the ordered set where layoutRevisionId = &#63;.
	*
	* @param layoutRevisionId the layout revision ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching recent layout revision
	* @throws NoSuchRecentLayoutRevisionException if a matching recent layout revision could not be found
	*/
	public static RecentLayoutRevision findByLayoutRevisionId_Last(
		long layoutRevisionId,
		OrderByComparator<RecentLayoutRevision> orderByComparator)
		throws com.liferay.portal.NoSuchRecentLayoutRevisionException {
		return getPersistence()
				   .findByLayoutRevisionId_Last(layoutRevisionId,
			orderByComparator);
	}

	/**
	* Returns the last recent layout revision in the ordered set where layoutRevisionId = &#63;.
	*
	* @param layoutRevisionId the layout revision ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching recent layout revision, or <code>null</code> if a matching recent layout revision could not be found
	*/
	public static RecentLayoutRevision fetchByLayoutRevisionId_Last(
		long layoutRevisionId,
		OrderByComparator<RecentLayoutRevision> orderByComparator) {
		return getPersistence()
				   .fetchByLayoutRevisionId_Last(layoutRevisionId,
			orderByComparator);
	}

	/**
	* Returns the recent layout revisions before and after the current recent layout revision in the ordered set where layoutRevisionId = &#63;.
	*
	* @param recentLayoutRevisionId the primary key of the current recent layout revision
	* @param layoutRevisionId the layout revision ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next recent layout revision
	* @throws NoSuchRecentLayoutRevisionException if a recent layout revision with the primary key could not be found
	*/
	public static RecentLayoutRevision[] findByLayoutRevisionId_PrevAndNext(
		long recentLayoutRevisionId, long layoutRevisionId,
		OrderByComparator<RecentLayoutRevision> orderByComparator)
		throws com.liferay.portal.NoSuchRecentLayoutRevisionException {
		return getPersistence()
				   .findByLayoutRevisionId_PrevAndNext(recentLayoutRevisionId,
			layoutRevisionId, orderByComparator);
	}

	/**
	* Removes all the recent layout revisions where layoutRevisionId = &#63; from the database.
	*
	* @param layoutRevisionId the layout revision ID
	*/
	public static void removeByLayoutRevisionId(long layoutRevisionId) {
		getPersistence().removeByLayoutRevisionId(layoutRevisionId);
	}

	/**
	* Returns the number of recent layout revisions where layoutRevisionId = &#63;.
	*
	* @param layoutRevisionId the layout revision ID
	* @return the number of matching recent layout revisions
	*/
	public static int countByLayoutRevisionId(long layoutRevisionId) {
		return getPersistence().countByLayoutRevisionId(layoutRevisionId);
	}

	/**
	* Returns the recent layout revision where userId = &#63; and layoutSetBranchId = &#63; and plid = &#63; or throws a {@link NoSuchRecentLayoutRevisionException} if it could not be found.
	*
	* @param userId the user ID
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @return the matching recent layout revision
	* @throws NoSuchRecentLayoutRevisionException if a matching recent layout revision could not be found
	*/
	public static RecentLayoutRevision findByU_L_P(long userId,
		long layoutSetBranchId, long plid)
		throws com.liferay.portal.NoSuchRecentLayoutRevisionException {
		return getPersistence().findByU_L_P(userId, layoutSetBranchId, plid);
	}

	/**
	* Returns the recent layout revision where userId = &#63; and layoutSetBranchId = &#63; and plid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @return the matching recent layout revision, or <code>null</code> if a matching recent layout revision could not be found
	*/
	public static RecentLayoutRevision fetchByU_L_P(long userId,
		long layoutSetBranchId, long plid) {
		return getPersistence().fetchByU_L_P(userId, layoutSetBranchId, plid);
	}

	/**
	* Returns the recent layout revision where userId = &#63; and layoutSetBranchId = &#63; and plid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching recent layout revision, or <code>null</code> if a matching recent layout revision could not be found
	*/
	public static RecentLayoutRevision fetchByU_L_P(long userId,
		long layoutSetBranchId, long plid, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByU_L_P(userId, layoutSetBranchId, plid,
			retrieveFromCache);
	}

	/**
	* Removes the recent layout revision where userId = &#63; and layoutSetBranchId = &#63; and plid = &#63; from the database.
	*
	* @param userId the user ID
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @return the recent layout revision that was removed
	*/
	public static RecentLayoutRevision removeByU_L_P(long userId,
		long layoutSetBranchId, long plid)
		throws com.liferay.portal.NoSuchRecentLayoutRevisionException {
		return getPersistence().removeByU_L_P(userId, layoutSetBranchId, plid);
	}

	/**
	* Returns the number of recent layout revisions where userId = &#63; and layoutSetBranchId = &#63; and plid = &#63;.
	*
	* @param userId the user ID
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @return the number of matching recent layout revisions
	*/
	public static int countByU_L_P(long userId, long layoutSetBranchId,
		long plid) {
		return getPersistence().countByU_L_P(userId, layoutSetBranchId, plid);
	}

	/**
	* Caches the recent layout revision in the entity cache if it is enabled.
	*
	* @param recentLayoutRevision the recent layout revision
	*/
	public static void cacheResult(RecentLayoutRevision recentLayoutRevision) {
		getPersistence().cacheResult(recentLayoutRevision);
	}

	/**
	* Caches the recent layout revisions in the entity cache if it is enabled.
	*
	* @param recentLayoutRevisions the recent layout revisions
	*/
	public static void cacheResult(
		List<RecentLayoutRevision> recentLayoutRevisions) {
		getPersistence().cacheResult(recentLayoutRevisions);
	}

	/**
	* Creates a new recent layout revision with the primary key. Does not add the recent layout revision to the database.
	*
	* @param recentLayoutRevisionId the primary key for the new recent layout revision
	* @return the new recent layout revision
	*/
	public static RecentLayoutRevision create(long recentLayoutRevisionId) {
		return getPersistence().create(recentLayoutRevisionId);
	}

	/**
	* Removes the recent layout revision with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param recentLayoutRevisionId the primary key of the recent layout revision
	* @return the recent layout revision that was removed
	* @throws NoSuchRecentLayoutRevisionException if a recent layout revision with the primary key could not be found
	*/
	public static RecentLayoutRevision remove(long recentLayoutRevisionId)
		throws com.liferay.portal.NoSuchRecentLayoutRevisionException {
		return getPersistence().remove(recentLayoutRevisionId);
	}

	public static RecentLayoutRevision updateImpl(
		RecentLayoutRevision recentLayoutRevision) {
		return getPersistence().updateImpl(recentLayoutRevision);
	}

	/**
	* Returns the recent layout revision with the primary key or throws a {@link NoSuchRecentLayoutRevisionException} if it could not be found.
	*
	* @param recentLayoutRevisionId the primary key of the recent layout revision
	* @return the recent layout revision
	* @throws NoSuchRecentLayoutRevisionException if a recent layout revision with the primary key could not be found
	*/
	public static RecentLayoutRevision findByPrimaryKey(
		long recentLayoutRevisionId)
		throws com.liferay.portal.NoSuchRecentLayoutRevisionException {
		return getPersistence().findByPrimaryKey(recentLayoutRevisionId);
	}

	/**
	* Returns the recent layout revision with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param recentLayoutRevisionId the primary key of the recent layout revision
	* @return the recent layout revision, or <code>null</code> if a recent layout revision with the primary key could not be found
	*/
	public static RecentLayoutRevision fetchByPrimaryKey(
		long recentLayoutRevisionId) {
		return getPersistence().fetchByPrimaryKey(recentLayoutRevisionId);
	}

	public static java.util.Map<java.io.Serializable, RecentLayoutRevision> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the recent layout revisions.
	*
	* @return the recent layout revisions
	*/
	public static List<RecentLayoutRevision> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the recent layout revisions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of recent layout revisions
	* @param end the upper bound of the range of recent layout revisions (not inclusive)
	* @return the range of recent layout revisions
	*/
	public static List<RecentLayoutRevision> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the recent layout revisions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of recent layout revisions
	* @param end the upper bound of the range of recent layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of recent layout revisions
	*/
	public static List<RecentLayoutRevision> findAll(int start, int end,
		OrderByComparator<RecentLayoutRevision> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the recent layout revisions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of recent layout revisions
	* @param end the upper bound of the range of recent layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of recent layout revisions
	*/
	public static List<RecentLayoutRevision> findAll(int start, int end,
		OrderByComparator<RecentLayoutRevision> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the recent layout revisions from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of recent layout revisions.
	*
	* @return the number of recent layout revisions
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static RecentLayoutRevisionPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (RecentLayoutRevisionPersistence)PortalBeanLocatorUtil.locate(RecentLayoutRevisionPersistence.class.getName());

			ReferenceRegistry.registerReference(RecentLayoutRevisionUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setPersistence(RecentLayoutRevisionPersistence persistence) {
	}

	private static RecentLayoutRevisionPersistence _persistence;
}
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

package com.liferay.changeset.service.persistence;

import com.liferay.changeset.model.ChangesetCollection;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the changeset collection service. This utility wraps <code>com.liferay.changeset.service.persistence.impl.ChangesetCollectionPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ChangesetCollectionPersistence
 * @generated
 */
public class ChangesetCollectionUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(ChangesetCollection changesetCollection) {
		getPersistence().clearCache(changesetCollection);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, ChangesetCollection> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ChangesetCollection> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ChangesetCollection> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ChangesetCollection> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ChangesetCollection> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ChangesetCollection update(
		ChangesetCollection changesetCollection) {

		return getPersistence().update(changesetCollection);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ChangesetCollection update(
		ChangesetCollection changesetCollection,
		ServiceContext serviceContext) {

		return getPersistence().update(changesetCollection, serviceContext);
	}

	/**
	 * Returns all the changeset collections where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching changeset collections
	 */
	public static List<ChangesetCollection> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	 * Returns a range of all the changeset collections where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @return the range of matching changeset collections
	 */
	public static List<ChangesetCollection> findByGroupId(
		long groupId, int start, int end) {

		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the changeset collections where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching changeset collections
	 */
	public static List<ChangesetCollection> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<ChangesetCollection> orderByComparator) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the changeset collections where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching changeset collections
	 */
	public static List<ChangesetCollection> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<ChangesetCollection> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first changeset collection in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset collection
	 * @throws NoSuchCollectionException if a matching changeset collection could not be found
	 */
	public static ChangesetCollection findByGroupId_First(
			long groupId,
			OrderByComparator<ChangesetCollection> orderByComparator)
		throws com.liferay.changeset.exception.NoSuchCollectionException {

		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	 * Returns the first changeset collection in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset collection, or <code>null</code> if a matching changeset collection could not be found
	 */
	public static ChangesetCollection fetchByGroupId_First(
		long groupId,
		OrderByComparator<ChangesetCollection> orderByComparator) {

		return getPersistence().fetchByGroupId_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last changeset collection in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching changeset collection
	 * @throws NoSuchCollectionException if a matching changeset collection could not be found
	 */
	public static ChangesetCollection findByGroupId_Last(
			long groupId,
			OrderByComparator<ChangesetCollection> orderByComparator)
		throws com.liferay.changeset.exception.NoSuchCollectionException {

		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the last changeset collection in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching changeset collection, or <code>null</code> if a matching changeset collection could not be found
	 */
	public static ChangesetCollection fetchByGroupId_Last(
		long groupId,
		OrderByComparator<ChangesetCollection> orderByComparator) {

		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the changeset collections before and after the current changeset collection in the ordered set where groupId = &#63;.
	 *
	 * @param changesetCollectionId the primary key of the current changeset collection
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next changeset collection
	 * @throws NoSuchCollectionException if a changeset collection with the primary key could not be found
	 */
	public static ChangesetCollection[] findByGroupId_PrevAndNext(
			long changesetCollectionId, long groupId,
			OrderByComparator<ChangesetCollection> orderByComparator)
		throws com.liferay.changeset.exception.NoSuchCollectionException {

		return getPersistence().findByGroupId_PrevAndNext(
			changesetCollectionId, groupId, orderByComparator);
	}

	/**
	 * Removes all the changeset collections where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	 * Returns the number of changeset collections where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching changeset collections
	 */
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	 * Returns all the changeset collections where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching changeset collections
	 */
	public static List<ChangesetCollection> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the changeset collections where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @return the range of matching changeset collections
	 */
	public static List<ChangesetCollection> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the changeset collections where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching changeset collections
	 */
	public static List<ChangesetCollection> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<ChangesetCollection> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the changeset collections where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching changeset collections
	 */
	public static List<ChangesetCollection> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<ChangesetCollection> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first changeset collection in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset collection
	 * @throws NoSuchCollectionException if a matching changeset collection could not be found
	 */
	public static ChangesetCollection findByCompanyId_First(
			long companyId,
			OrderByComparator<ChangesetCollection> orderByComparator)
		throws com.liferay.changeset.exception.NoSuchCollectionException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first changeset collection in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset collection, or <code>null</code> if a matching changeset collection could not be found
	 */
	public static ChangesetCollection fetchByCompanyId_First(
		long companyId,
		OrderByComparator<ChangesetCollection> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last changeset collection in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching changeset collection
	 * @throws NoSuchCollectionException if a matching changeset collection could not be found
	 */
	public static ChangesetCollection findByCompanyId_Last(
			long companyId,
			OrderByComparator<ChangesetCollection> orderByComparator)
		throws com.liferay.changeset.exception.NoSuchCollectionException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last changeset collection in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching changeset collection, or <code>null</code> if a matching changeset collection could not be found
	 */
	public static ChangesetCollection fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<ChangesetCollection> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the changeset collections before and after the current changeset collection in the ordered set where companyId = &#63;.
	 *
	 * @param changesetCollectionId the primary key of the current changeset collection
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next changeset collection
	 * @throws NoSuchCollectionException if a changeset collection with the primary key could not be found
	 */
	public static ChangesetCollection[] findByCompanyId_PrevAndNext(
			long changesetCollectionId, long companyId,
			OrderByComparator<ChangesetCollection> orderByComparator)
		throws com.liferay.changeset.exception.NoSuchCollectionException {

		return getPersistence().findByCompanyId_PrevAndNext(
			changesetCollectionId, companyId, orderByComparator);
	}

	/**
	 * Removes all the changeset collections where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of changeset collections where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching changeset collections
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns all the changeset collections where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching changeset collections
	 */
	public static List<ChangesetCollection> findByG_U(
		long groupId, long userId) {

		return getPersistence().findByG_U(groupId, userId);
	}

	/**
	 * Returns a range of all the changeset collections where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @return the range of matching changeset collections
	 */
	public static List<ChangesetCollection> findByG_U(
		long groupId, long userId, int start, int end) {

		return getPersistence().findByG_U(groupId, userId, start, end);
	}

	/**
	 * Returns an ordered range of all the changeset collections where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching changeset collections
	 */
	public static List<ChangesetCollection> findByG_U(
		long groupId, long userId, int start, int end,
		OrderByComparator<ChangesetCollection> orderByComparator) {

		return getPersistence().findByG_U(
			groupId, userId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the changeset collections where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching changeset collections
	 */
	public static List<ChangesetCollection> findByG_U(
		long groupId, long userId, int start, int end,
		OrderByComparator<ChangesetCollection> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_U(
			groupId, userId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first changeset collection in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset collection
	 * @throws NoSuchCollectionException if a matching changeset collection could not be found
	 */
	public static ChangesetCollection findByG_U_First(
			long groupId, long userId,
			OrderByComparator<ChangesetCollection> orderByComparator)
		throws com.liferay.changeset.exception.NoSuchCollectionException {

		return getPersistence().findByG_U_First(
			groupId, userId, orderByComparator);
	}

	/**
	 * Returns the first changeset collection in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset collection, or <code>null</code> if a matching changeset collection could not be found
	 */
	public static ChangesetCollection fetchByG_U_First(
		long groupId, long userId,
		OrderByComparator<ChangesetCollection> orderByComparator) {

		return getPersistence().fetchByG_U_First(
			groupId, userId, orderByComparator);
	}

	/**
	 * Returns the last changeset collection in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching changeset collection
	 * @throws NoSuchCollectionException if a matching changeset collection could not be found
	 */
	public static ChangesetCollection findByG_U_Last(
			long groupId, long userId,
			OrderByComparator<ChangesetCollection> orderByComparator)
		throws com.liferay.changeset.exception.NoSuchCollectionException {

		return getPersistence().findByG_U_Last(
			groupId, userId, orderByComparator);
	}

	/**
	 * Returns the last changeset collection in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching changeset collection, or <code>null</code> if a matching changeset collection could not be found
	 */
	public static ChangesetCollection fetchByG_U_Last(
		long groupId, long userId,
		OrderByComparator<ChangesetCollection> orderByComparator) {

		return getPersistence().fetchByG_U_Last(
			groupId, userId, orderByComparator);
	}

	/**
	 * Returns the changeset collections before and after the current changeset collection in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param changesetCollectionId the primary key of the current changeset collection
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next changeset collection
	 * @throws NoSuchCollectionException if a changeset collection with the primary key could not be found
	 */
	public static ChangesetCollection[] findByG_U_PrevAndNext(
			long changesetCollectionId, long groupId, long userId,
			OrderByComparator<ChangesetCollection> orderByComparator)
		throws com.liferay.changeset.exception.NoSuchCollectionException {

		return getPersistence().findByG_U_PrevAndNext(
			changesetCollectionId, groupId, userId, orderByComparator);
	}

	/**
	 * Removes all the changeset collections where groupId = &#63; and userId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 */
	public static void removeByG_U(long groupId, long userId) {
		getPersistence().removeByG_U(groupId, userId);
	}

	/**
	 * Returns the number of changeset collections where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching changeset collections
	 */
	public static int countByG_U(long groupId, long userId) {
		return getPersistence().countByG_U(groupId, userId);
	}

	/**
	 * Returns the changeset collection where groupId = &#63; and name = &#63; or throws a <code>NoSuchCollectionException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching changeset collection
	 * @throws NoSuchCollectionException if a matching changeset collection could not be found
	 */
	public static ChangesetCollection findByG_N(long groupId, String name)
		throws com.liferay.changeset.exception.NoSuchCollectionException {

		return getPersistence().findByG_N(groupId, name);
	}

	/**
	 * Returns the changeset collection where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching changeset collection, or <code>null</code> if a matching changeset collection could not be found
	 */
	public static ChangesetCollection fetchByG_N(long groupId, String name) {
		return getPersistence().fetchByG_N(groupId, name);
	}

	/**
	 * Returns the changeset collection where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching changeset collection, or <code>null</code> if a matching changeset collection could not be found
	 */
	public static ChangesetCollection fetchByG_N(
		long groupId, String name, boolean useFinderCache) {

		return getPersistence().fetchByG_N(groupId, name, useFinderCache);
	}

	/**
	 * Removes the changeset collection where groupId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the changeset collection that was removed
	 */
	public static ChangesetCollection removeByG_N(long groupId, String name)
		throws com.liferay.changeset.exception.NoSuchCollectionException {

		return getPersistence().removeByG_N(groupId, name);
	}

	/**
	 * Returns the number of changeset collections where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching changeset collections
	 */
	public static int countByG_N(long groupId, String name) {
		return getPersistence().countByG_N(groupId, name);
	}

	/**
	 * Returns all the changeset collections where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching changeset collections
	 */
	public static List<ChangesetCollection> findByC_N(
		long companyId, String name) {

		return getPersistence().findByC_N(companyId, name);
	}

	/**
	 * Returns a range of all the changeset collections where companyId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @return the range of matching changeset collections
	 */
	public static List<ChangesetCollection> findByC_N(
		long companyId, String name, int start, int end) {

		return getPersistence().findByC_N(companyId, name, start, end);
	}

	/**
	 * Returns an ordered range of all the changeset collections where companyId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching changeset collections
	 */
	public static List<ChangesetCollection> findByC_N(
		long companyId, String name, int start, int end,
		OrderByComparator<ChangesetCollection> orderByComparator) {

		return getPersistence().findByC_N(
			companyId, name, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the changeset collections where companyId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching changeset collections
	 */
	public static List<ChangesetCollection> findByC_N(
		long companyId, String name, int start, int end,
		OrderByComparator<ChangesetCollection> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_N(
			companyId, name, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first changeset collection in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset collection
	 * @throws NoSuchCollectionException if a matching changeset collection could not be found
	 */
	public static ChangesetCollection findByC_N_First(
			long companyId, String name,
			OrderByComparator<ChangesetCollection> orderByComparator)
		throws com.liferay.changeset.exception.NoSuchCollectionException {

		return getPersistence().findByC_N_First(
			companyId, name, orderByComparator);
	}

	/**
	 * Returns the first changeset collection in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset collection, or <code>null</code> if a matching changeset collection could not be found
	 */
	public static ChangesetCollection fetchByC_N_First(
		long companyId, String name,
		OrderByComparator<ChangesetCollection> orderByComparator) {

		return getPersistence().fetchByC_N_First(
			companyId, name, orderByComparator);
	}

	/**
	 * Returns the last changeset collection in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching changeset collection
	 * @throws NoSuchCollectionException if a matching changeset collection could not be found
	 */
	public static ChangesetCollection findByC_N_Last(
			long companyId, String name,
			OrderByComparator<ChangesetCollection> orderByComparator)
		throws com.liferay.changeset.exception.NoSuchCollectionException {

		return getPersistence().findByC_N_Last(
			companyId, name, orderByComparator);
	}

	/**
	 * Returns the last changeset collection in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching changeset collection, or <code>null</code> if a matching changeset collection could not be found
	 */
	public static ChangesetCollection fetchByC_N_Last(
		long companyId, String name,
		OrderByComparator<ChangesetCollection> orderByComparator) {

		return getPersistence().fetchByC_N_Last(
			companyId, name, orderByComparator);
	}

	/**
	 * Returns the changeset collections before and after the current changeset collection in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param changesetCollectionId the primary key of the current changeset collection
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next changeset collection
	 * @throws NoSuchCollectionException if a changeset collection with the primary key could not be found
	 */
	public static ChangesetCollection[] findByC_N_PrevAndNext(
			long changesetCollectionId, long companyId, String name,
			OrderByComparator<ChangesetCollection> orderByComparator)
		throws com.liferay.changeset.exception.NoSuchCollectionException {

		return getPersistence().findByC_N_PrevAndNext(
			changesetCollectionId, companyId, name, orderByComparator);
	}

	/**
	 * Removes all the changeset collections where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 */
	public static void removeByC_N(long companyId, String name) {
		getPersistence().removeByC_N(companyId, name);
	}

	/**
	 * Returns the number of changeset collections where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching changeset collections
	 */
	public static int countByC_N(long companyId, String name) {
		return getPersistence().countByC_N(companyId, name);
	}

	/**
	 * Caches the changeset collection in the entity cache if it is enabled.
	 *
	 * @param changesetCollection the changeset collection
	 */
	public static void cacheResult(ChangesetCollection changesetCollection) {
		getPersistence().cacheResult(changesetCollection);
	}

	/**
	 * Caches the changeset collections in the entity cache if it is enabled.
	 *
	 * @param changesetCollections the changeset collections
	 */
	public static void cacheResult(
		List<ChangesetCollection> changesetCollections) {

		getPersistence().cacheResult(changesetCollections);
	}

	/**
	 * Creates a new changeset collection with the primary key. Does not add the changeset collection to the database.
	 *
	 * @param changesetCollectionId the primary key for the new changeset collection
	 * @return the new changeset collection
	 */
	public static ChangesetCollection create(long changesetCollectionId) {
		return getPersistence().create(changesetCollectionId);
	}

	/**
	 * Removes the changeset collection with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param changesetCollectionId the primary key of the changeset collection
	 * @return the changeset collection that was removed
	 * @throws NoSuchCollectionException if a changeset collection with the primary key could not be found
	 */
	public static ChangesetCollection remove(long changesetCollectionId)
		throws com.liferay.changeset.exception.NoSuchCollectionException {

		return getPersistence().remove(changesetCollectionId);
	}

	public static ChangesetCollection updateImpl(
		ChangesetCollection changesetCollection) {

		return getPersistence().updateImpl(changesetCollection);
	}

	/**
	 * Returns the changeset collection with the primary key or throws a <code>NoSuchCollectionException</code> if it could not be found.
	 *
	 * @param changesetCollectionId the primary key of the changeset collection
	 * @return the changeset collection
	 * @throws NoSuchCollectionException if a changeset collection with the primary key could not be found
	 */
	public static ChangesetCollection findByPrimaryKey(
			long changesetCollectionId)
		throws com.liferay.changeset.exception.NoSuchCollectionException {

		return getPersistence().findByPrimaryKey(changesetCollectionId);
	}

	/**
	 * Returns the changeset collection with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param changesetCollectionId the primary key of the changeset collection
	 * @return the changeset collection, or <code>null</code> if a changeset collection with the primary key could not be found
	 */
	public static ChangesetCollection fetchByPrimaryKey(
		long changesetCollectionId) {

		return getPersistence().fetchByPrimaryKey(changesetCollectionId);
	}

	/**
	 * Returns all the changeset collections.
	 *
	 * @return the changeset collections
	 */
	public static List<ChangesetCollection> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the changeset collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @return the range of changeset collections
	 */
	public static List<ChangesetCollection> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the changeset collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of changeset collections
	 */
	public static List<ChangesetCollection> findAll(
		int start, int end,
		OrderByComparator<ChangesetCollection> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the changeset collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of changeset collections
	 */
	public static List<ChangesetCollection> findAll(
		int start, int end,
		OrderByComparator<ChangesetCollection> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the changeset collections from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of changeset collections.
	 *
	 * @return the number of changeset collections
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ChangesetCollectionPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<ChangesetCollectionPersistence, ChangesetCollectionPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			ChangesetCollectionPersistence.class);

		ServiceTracker
			<ChangesetCollectionPersistence, ChangesetCollectionPersistence>
				serviceTracker =
					new ServiceTracker
						<ChangesetCollectionPersistence,
						 ChangesetCollectionPersistence>(
							 bundle.getBundleContext(),
							 ChangesetCollectionPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}
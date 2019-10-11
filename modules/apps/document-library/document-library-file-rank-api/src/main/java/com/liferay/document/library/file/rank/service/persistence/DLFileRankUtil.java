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

package com.liferay.document.library.file.rank.service.persistence;

import com.liferay.document.library.file.rank.model.DLFileRank;
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
 * The persistence utility for the document library file rank service. This utility wraps <code>com.liferay.document.library.file.rank.service.persistence.impl.DLFileRankPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileRankPersistence
 * @generated
 */
public class DLFileRankUtil {

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
	public static void clearCache(DLFileRank dlFileRank) {
		getPersistence().clearCache(dlFileRank);
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
	public static Map<Serializable, DLFileRank> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DLFileRank> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DLFileRank> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DLFileRank> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DLFileRank> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static DLFileRank update(DLFileRank dlFileRank) {
		return getPersistence().update(dlFileRank);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static DLFileRank update(
		DLFileRank dlFileRank, ServiceContext serviceContext) {

		return getPersistence().update(dlFileRank, serviceContext);
	}

	/**
	 * Returns all the document library file ranks where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching document library file ranks
	 */
	public static List<DLFileRank> findByUserId(long userId) {
		return getPersistence().findByUserId(userId);
	}

	/**
	 * Returns a range of all the document library file ranks where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileRankModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of document library file ranks
	 * @param end the upper bound of the range of document library file ranks (not inclusive)
	 * @return the range of matching document library file ranks
	 */
	public static List<DLFileRank> findByUserId(
		long userId, int start, int end) {

		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	 * Returns an ordered range of all the document library file ranks where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileRankModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of document library file ranks
	 * @param end the upper bound of the range of document library file ranks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file ranks
	 */
	public static List<DLFileRank> findByUserId(
		long userId, int start, int end,
		OrderByComparator<DLFileRank> orderByComparator) {

		return getPersistence().findByUserId(
			userId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the document library file ranks where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileRankModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of document library file ranks
	 * @param end the upper bound of the range of document library file ranks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file ranks
	 */
	public static List<DLFileRank> findByUserId(
		long userId, int start, int end,
		OrderByComparator<DLFileRank> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUserId(
			userId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first document library file rank in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file rank
	 * @throws NoSuchFileRankException if a matching document library file rank could not be found
	 */
	public static DLFileRank findByUserId_First(
			long userId, OrderByComparator<DLFileRank> orderByComparator)
		throws com.liferay.document.library.file.rank.exception.
			NoSuchFileRankException {

		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	 * Returns the first document library file rank in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file rank, or <code>null</code> if a matching document library file rank could not be found
	 */
	public static DLFileRank fetchByUserId_First(
		long userId, OrderByComparator<DLFileRank> orderByComparator) {

		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	 * Returns the last document library file rank in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file rank
	 * @throws NoSuchFileRankException if a matching document library file rank could not be found
	 */
	public static DLFileRank findByUserId_Last(
			long userId, OrderByComparator<DLFileRank> orderByComparator)
		throws com.liferay.document.library.file.rank.exception.
			NoSuchFileRankException {

		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	 * Returns the last document library file rank in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file rank, or <code>null</code> if a matching document library file rank could not be found
	 */
	public static DLFileRank fetchByUserId_Last(
		long userId, OrderByComparator<DLFileRank> orderByComparator) {

		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

	/**
	 * Returns the document library file ranks before and after the current document library file rank in the ordered set where userId = &#63;.
	 *
	 * @param fileRankId the primary key of the current document library file rank
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file rank
	 * @throws NoSuchFileRankException if a document library file rank with the primary key could not be found
	 */
	public static DLFileRank[] findByUserId_PrevAndNext(
			long fileRankId, long userId,
			OrderByComparator<DLFileRank> orderByComparator)
		throws com.liferay.document.library.file.rank.exception.
			NoSuchFileRankException {

		return getPersistence().findByUserId_PrevAndNext(
			fileRankId, userId, orderByComparator);
	}

	/**
	 * Removes all the document library file ranks where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	public static void removeByUserId(long userId) {
		getPersistence().removeByUserId(userId);
	}

	/**
	 * Returns the number of document library file ranks where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching document library file ranks
	 */
	public static int countByUserId(long userId) {
		return getPersistence().countByUserId(userId);
	}

	/**
	 * Returns all the document library file ranks where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @return the matching document library file ranks
	 */
	public static List<DLFileRank> findByFileEntryId(long fileEntryId) {
		return getPersistence().findByFileEntryId(fileEntryId);
	}

	/**
	 * Returns a range of all the document library file ranks where fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileRankModelImpl</code>.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of document library file ranks
	 * @param end the upper bound of the range of document library file ranks (not inclusive)
	 * @return the range of matching document library file ranks
	 */
	public static List<DLFileRank> findByFileEntryId(
		long fileEntryId, int start, int end) {

		return getPersistence().findByFileEntryId(fileEntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the document library file ranks where fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileRankModelImpl</code>.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of document library file ranks
	 * @param end the upper bound of the range of document library file ranks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file ranks
	 */
	public static List<DLFileRank> findByFileEntryId(
		long fileEntryId, int start, int end,
		OrderByComparator<DLFileRank> orderByComparator) {

		return getPersistence().findByFileEntryId(
			fileEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the document library file ranks where fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileRankModelImpl</code>.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of document library file ranks
	 * @param end the upper bound of the range of document library file ranks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file ranks
	 */
	public static List<DLFileRank> findByFileEntryId(
		long fileEntryId, int start, int end,
		OrderByComparator<DLFileRank> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByFileEntryId(
			fileEntryId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first document library file rank in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file rank
	 * @throws NoSuchFileRankException if a matching document library file rank could not be found
	 */
	public static DLFileRank findByFileEntryId_First(
			long fileEntryId, OrderByComparator<DLFileRank> orderByComparator)
		throws com.liferay.document.library.file.rank.exception.
			NoSuchFileRankException {

		return getPersistence().findByFileEntryId_First(
			fileEntryId, orderByComparator);
	}

	/**
	 * Returns the first document library file rank in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file rank, or <code>null</code> if a matching document library file rank could not be found
	 */
	public static DLFileRank fetchByFileEntryId_First(
		long fileEntryId, OrderByComparator<DLFileRank> orderByComparator) {

		return getPersistence().fetchByFileEntryId_First(
			fileEntryId, orderByComparator);
	}

	/**
	 * Returns the last document library file rank in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file rank
	 * @throws NoSuchFileRankException if a matching document library file rank could not be found
	 */
	public static DLFileRank findByFileEntryId_Last(
			long fileEntryId, OrderByComparator<DLFileRank> orderByComparator)
		throws com.liferay.document.library.file.rank.exception.
			NoSuchFileRankException {

		return getPersistence().findByFileEntryId_Last(
			fileEntryId, orderByComparator);
	}

	/**
	 * Returns the last document library file rank in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file rank, or <code>null</code> if a matching document library file rank could not be found
	 */
	public static DLFileRank fetchByFileEntryId_Last(
		long fileEntryId, OrderByComparator<DLFileRank> orderByComparator) {

		return getPersistence().fetchByFileEntryId_Last(
			fileEntryId, orderByComparator);
	}

	/**
	 * Returns the document library file ranks before and after the current document library file rank in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileRankId the primary key of the current document library file rank
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file rank
	 * @throws NoSuchFileRankException if a document library file rank with the primary key could not be found
	 */
	public static DLFileRank[] findByFileEntryId_PrevAndNext(
			long fileRankId, long fileEntryId,
			OrderByComparator<DLFileRank> orderByComparator)
		throws com.liferay.document.library.file.rank.exception.
			NoSuchFileRankException {

		return getPersistence().findByFileEntryId_PrevAndNext(
			fileRankId, fileEntryId, orderByComparator);
	}

	/**
	 * Removes all the document library file ranks where fileEntryId = &#63; from the database.
	 *
	 * @param fileEntryId the file entry ID
	 */
	public static void removeByFileEntryId(long fileEntryId) {
		getPersistence().removeByFileEntryId(fileEntryId);
	}

	/**
	 * Returns the number of document library file ranks where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @return the number of matching document library file ranks
	 */
	public static int countByFileEntryId(long fileEntryId) {
		return getPersistence().countByFileEntryId(fileEntryId);
	}

	/**
	 * Returns all the document library file ranks where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching document library file ranks
	 */
	public static List<DLFileRank> findByG_U(long groupId, long userId) {
		return getPersistence().findByG_U(groupId, userId);
	}

	/**
	 * Returns a range of all the document library file ranks where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileRankModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of document library file ranks
	 * @param end the upper bound of the range of document library file ranks (not inclusive)
	 * @return the range of matching document library file ranks
	 */
	public static List<DLFileRank> findByG_U(
		long groupId, long userId, int start, int end) {

		return getPersistence().findByG_U(groupId, userId, start, end);
	}

	/**
	 * Returns an ordered range of all the document library file ranks where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileRankModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of document library file ranks
	 * @param end the upper bound of the range of document library file ranks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file ranks
	 */
	public static List<DLFileRank> findByG_U(
		long groupId, long userId, int start, int end,
		OrderByComparator<DLFileRank> orderByComparator) {

		return getPersistence().findByG_U(
			groupId, userId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the document library file ranks where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileRankModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of document library file ranks
	 * @param end the upper bound of the range of document library file ranks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file ranks
	 */
	public static List<DLFileRank> findByG_U(
		long groupId, long userId, int start, int end,
		OrderByComparator<DLFileRank> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_U(
			groupId, userId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first document library file rank in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file rank
	 * @throws NoSuchFileRankException if a matching document library file rank could not be found
	 */
	public static DLFileRank findByG_U_First(
			long groupId, long userId,
			OrderByComparator<DLFileRank> orderByComparator)
		throws com.liferay.document.library.file.rank.exception.
			NoSuchFileRankException {

		return getPersistence().findByG_U_First(
			groupId, userId, orderByComparator);
	}

	/**
	 * Returns the first document library file rank in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file rank, or <code>null</code> if a matching document library file rank could not be found
	 */
	public static DLFileRank fetchByG_U_First(
		long groupId, long userId,
		OrderByComparator<DLFileRank> orderByComparator) {

		return getPersistence().fetchByG_U_First(
			groupId, userId, orderByComparator);
	}

	/**
	 * Returns the last document library file rank in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file rank
	 * @throws NoSuchFileRankException if a matching document library file rank could not be found
	 */
	public static DLFileRank findByG_U_Last(
			long groupId, long userId,
			OrderByComparator<DLFileRank> orderByComparator)
		throws com.liferay.document.library.file.rank.exception.
			NoSuchFileRankException {

		return getPersistence().findByG_U_Last(
			groupId, userId, orderByComparator);
	}

	/**
	 * Returns the last document library file rank in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file rank, or <code>null</code> if a matching document library file rank could not be found
	 */
	public static DLFileRank fetchByG_U_Last(
		long groupId, long userId,
		OrderByComparator<DLFileRank> orderByComparator) {

		return getPersistence().fetchByG_U_Last(
			groupId, userId, orderByComparator);
	}

	/**
	 * Returns the document library file ranks before and after the current document library file rank in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param fileRankId the primary key of the current document library file rank
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file rank
	 * @throws NoSuchFileRankException if a document library file rank with the primary key could not be found
	 */
	public static DLFileRank[] findByG_U_PrevAndNext(
			long fileRankId, long groupId, long userId,
			OrderByComparator<DLFileRank> orderByComparator)
		throws com.liferay.document.library.file.rank.exception.
			NoSuchFileRankException {

		return getPersistence().findByG_U_PrevAndNext(
			fileRankId, groupId, userId, orderByComparator);
	}

	/**
	 * Removes all the document library file ranks where groupId = &#63; and userId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 */
	public static void removeByG_U(long groupId, long userId) {
		getPersistence().removeByG_U(groupId, userId);
	}

	/**
	 * Returns the number of document library file ranks where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching document library file ranks
	 */
	public static int countByG_U(long groupId, long userId) {
		return getPersistence().countByG_U(groupId, userId);
	}

	/**
	 * Returns all the document library file ranks where groupId = &#63; and userId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param active the active
	 * @return the matching document library file ranks
	 */
	public static List<DLFileRank> findByG_U_A(
		long groupId, long userId, boolean active) {

		return getPersistence().findByG_U_A(groupId, userId, active);
	}

	/**
	 * Returns a range of all the document library file ranks where groupId = &#63; and userId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileRankModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param active the active
	 * @param start the lower bound of the range of document library file ranks
	 * @param end the upper bound of the range of document library file ranks (not inclusive)
	 * @return the range of matching document library file ranks
	 */
	public static List<DLFileRank> findByG_U_A(
		long groupId, long userId, boolean active, int start, int end) {

		return getPersistence().findByG_U_A(
			groupId, userId, active, start, end);
	}

	/**
	 * Returns an ordered range of all the document library file ranks where groupId = &#63; and userId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileRankModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param active the active
	 * @param start the lower bound of the range of document library file ranks
	 * @param end the upper bound of the range of document library file ranks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file ranks
	 */
	public static List<DLFileRank> findByG_U_A(
		long groupId, long userId, boolean active, int start, int end,
		OrderByComparator<DLFileRank> orderByComparator) {

		return getPersistence().findByG_U_A(
			groupId, userId, active, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the document library file ranks where groupId = &#63; and userId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileRankModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param active the active
	 * @param start the lower bound of the range of document library file ranks
	 * @param end the upper bound of the range of document library file ranks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file ranks
	 */
	public static List<DLFileRank> findByG_U_A(
		long groupId, long userId, boolean active, int start, int end,
		OrderByComparator<DLFileRank> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_U_A(
			groupId, userId, active, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first document library file rank in the ordered set where groupId = &#63; and userId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file rank
	 * @throws NoSuchFileRankException if a matching document library file rank could not be found
	 */
	public static DLFileRank findByG_U_A_First(
			long groupId, long userId, boolean active,
			OrderByComparator<DLFileRank> orderByComparator)
		throws com.liferay.document.library.file.rank.exception.
			NoSuchFileRankException {

		return getPersistence().findByG_U_A_First(
			groupId, userId, active, orderByComparator);
	}

	/**
	 * Returns the first document library file rank in the ordered set where groupId = &#63; and userId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file rank, or <code>null</code> if a matching document library file rank could not be found
	 */
	public static DLFileRank fetchByG_U_A_First(
		long groupId, long userId, boolean active,
		OrderByComparator<DLFileRank> orderByComparator) {

		return getPersistence().fetchByG_U_A_First(
			groupId, userId, active, orderByComparator);
	}

	/**
	 * Returns the last document library file rank in the ordered set where groupId = &#63; and userId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file rank
	 * @throws NoSuchFileRankException if a matching document library file rank could not be found
	 */
	public static DLFileRank findByG_U_A_Last(
			long groupId, long userId, boolean active,
			OrderByComparator<DLFileRank> orderByComparator)
		throws com.liferay.document.library.file.rank.exception.
			NoSuchFileRankException {

		return getPersistence().findByG_U_A_Last(
			groupId, userId, active, orderByComparator);
	}

	/**
	 * Returns the last document library file rank in the ordered set where groupId = &#63; and userId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file rank, or <code>null</code> if a matching document library file rank could not be found
	 */
	public static DLFileRank fetchByG_U_A_Last(
		long groupId, long userId, boolean active,
		OrderByComparator<DLFileRank> orderByComparator) {

		return getPersistence().fetchByG_U_A_Last(
			groupId, userId, active, orderByComparator);
	}

	/**
	 * Returns the document library file ranks before and after the current document library file rank in the ordered set where groupId = &#63; and userId = &#63; and active = &#63;.
	 *
	 * @param fileRankId the primary key of the current document library file rank
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file rank
	 * @throws NoSuchFileRankException if a document library file rank with the primary key could not be found
	 */
	public static DLFileRank[] findByG_U_A_PrevAndNext(
			long fileRankId, long groupId, long userId, boolean active,
			OrderByComparator<DLFileRank> orderByComparator)
		throws com.liferay.document.library.file.rank.exception.
			NoSuchFileRankException {

		return getPersistence().findByG_U_A_PrevAndNext(
			fileRankId, groupId, userId, active, orderByComparator);
	}

	/**
	 * Removes all the document library file ranks where groupId = &#63; and userId = &#63; and active = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param active the active
	 */
	public static void removeByG_U_A(
		long groupId, long userId, boolean active) {

		getPersistence().removeByG_U_A(groupId, userId, active);
	}

	/**
	 * Returns the number of document library file ranks where groupId = &#63; and userId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param active the active
	 * @return the number of matching document library file ranks
	 */
	public static int countByG_U_A(long groupId, long userId, boolean active) {
		return getPersistence().countByG_U_A(groupId, userId, active);
	}

	/**
	 * Returns the document library file rank where companyId = &#63; and userId = &#63; and fileEntryId = &#63; or throws a <code>NoSuchFileRankException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param fileEntryId the file entry ID
	 * @return the matching document library file rank
	 * @throws NoSuchFileRankException if a matching document library file rank could not be found
	 */
	public static DLFileRank findByC_U_F(
			long companyId, long userId, long fileEntryId)
		throws com.liferay.document.library.file.rank.exception.
			NoSuchFileRankException {

		return getPersistence().findByC_U_F(companyId, userId, fileEntryId);
	}

	/**
	 * Returns the document library file rank where companyId = &#63; and userId = &#63; and fileEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param fileEntryId the file entry ID
	 * @return the matching document library file rank, or <code>null</code> if a matching document library file rank could not be found
	 */
	public static DLFileRank fetchByC_U_F(
		long companyId, long userId, long fileEntryId) {

		return getPersistence().fetchByC_U_F(companyId, userId, fileEntryId);
	}

	/**
	 * Returns the document library file rank where companyId = &#63; and userId = &#63; and fileEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param fileEntryId the file entry ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching document library file rank, or <code>null</code> if a matching document library file rank could not be found
	 */
	public static DLFileRank fetchByC_U_F(
		long companyId, long userId, long fileEntryId, boolean useFinderCache) {

		return getPersistence().fetchByC_U_F(
			companyId, userId, fileEntryId, useFinderCache);
	}

	/**
	 * Removes the document library file rank where companyId = &#63; and userId = &#63; and fileEntryId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param fileEntryId the file entry ID
	 * @return the document library file rank that was removed
	 */
	public static DLFileRank removeByC_U_F(
			long companyId, long userId, long fileEntryId)
		throws com.liferay.document.library.file.rank.exception.
			NoSuchFileRankException {

		return getPersistence().removeByC_U_F(companyId, userId, fileEntryId);
	}

	/**
	 * Returns the number of document library file ranks where companyId = &#63; and userId = &#63; and fileEntryId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param fileEntryId the file entry ID
	 * @return the number of matching document library file ranks
	 */
	public static int countByC_U_F(
		long companyId, long userId, long fileEntryId) {

		return getPersistence().countByC_U_F(companyId, userId, fileEntryId);
	}

	/**
	 * Caches the document library file rank in the entity cache if it is enabled.
	 *
	 * @param dlFileRank the document library file rank
	 */
	public static void cacheResult(DLFileRank dlFileRank) {
		getPersistence().cacheResult(dlFileRank);
	}

	/**
	 * Caches the document library file ranks in the entity cache if it is enabled.
	 *
	 * @param dlFileRanks the document library file ranks
	 */
	public static void cacheResult(List<DLFileRank> dlFileRanks) {
		getPersistence().cacheResult(dlFileRanks);
	}

	/**
	 * Creates a new document library file rank with the primary key. Does not add the document library file rank to the database.
	 *
	 * @param fileRankId the primary key for the new document library file rank
	 * @return the new document library file rank
	 */
	public static DLFileRank create(long fileRankId) {
		return getPersistence().create(fileRankId);
	}

	/**
	 * Removes the document library file rank with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fileRankId the primary key of the document library file rank
	 * @return the document library file rank that was removed
	 * @throws NoSuchFileRankException if a document library file rank with the primary key could not be found
	 */
	public static DLFileRank remove(long fileRankId)
		throws com.liferay.document.library.file.rank.exception.
			NoSuchFileRankException {

		return getPersistence().remove(fileRankId);
	}

	public static DLFileRank updateImpl(DLFileRank dlFileRank) {
		return getPersistence().updateImpl(dlFileRank);
	}

	/**
	 * Returns the document library file rank with the primary key or throws a <code>NoSuchFileRankException</code> if it could not be found.
	 *
	 * @param fileRankId the primary key of the document library file rank
	 * @return the document library file rank
	 * @throws NoSuchFileRankException if a document library file rank with the primary key could not be found
	 */
	public static DLFileRank findByPrimaryKey(long fileRankId)
		throws com.liferay.document.library.file.rank.exception.
			NoSuchFileRankException {

		return getPersistence().findByPrimaryKey(fileRankId);
	}

	/**
	 * Returns the document library file rank with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fileRankId the primary key of the document library file rank
	 * @return the document library file rank, or <code>null</code> if a document library file rank with the primary key could not be found
	 */
	public static DLFileRank fetchByPrimaryKey(long fileRankId) {
		return getPersistence().fetchByPrimaryKey(fileRankId);
	}

	/**
	 * Returns all the document library file ranks.
	 *
	 * @return the document library file ranks
	 */
	public static List<DLFileRank> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the document library file ranks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileRankModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library file ranks
	 * @param end the upper bound of the range of document library file ranks (not inclusive)
	 * @return the range of document library file ranks
	 */
	public static List<DLFileRank> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the document library file ranks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileRankModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library file ranks
	 * @param end the upper bound of the range of document library file ranks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of document library file ranks
	 */
	public static List<DLFileRank> findAll(
		int start, int end, OrderByComparator<DLFileRank> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the document library file ranks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileRankModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library file ranks
	 * @param end the upper bound of the range of document library file ranks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of document library file ranks
	 */
	public static List<DLFileRank> findAll(
		int start, int end, OrderByComparator<DLFileRank> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the document library file ranks from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of document library file ranks.
	 *
	 * @return the number of document library file ranks
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static DLFileRankPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<DLFileRankPersistence, DLFileRankPersistence>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(DLFileRankPersistence.class);

		ServiceTracker<DLFileRankPersistence, DLFileRankPersistence>
			serviceTracker =
				new ServiceTracker
					<DLFileRankPersistence, DLFileRankPersistence>(
						bundle.getBundleContext(), DLFileRankPersistence.class,
						null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}
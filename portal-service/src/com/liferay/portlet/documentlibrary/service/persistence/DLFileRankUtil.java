/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.documentlibrary.model.DLFileRank;

import java.util.List;

/**
 * The persistence utility for the document library file rank service. This utility wraps {@link DLFileRankPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileRankPersistence
 * @see DLFileRankPersistenceImpl
 * @generated
 */
public class DLFileRankUtil {
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
	public static void clearCache(DLFileRank dlFileRank) {
		getPersistence().clearCache(dlFileRank);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DLFileRank> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DLFileRank> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DLFileRank> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static DLFileRank update(DLFileRank dlFileRank, boolean merge)
		throws SystemException {
		return getPersistence().update(dlFileRank, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static DLFileRank update(DLFileRank dlFileRank, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(dlFileRank, merge, serviceContext);
	}

	/**
	* Caches the document library file rank in the entity cache if it is enabled.
	*
	* @param dlFileRank the document library file rank
	*/
	public static void cacheResult(
		com.liferay.portlet.documentlibrary.model.DLFileRank dlFileRank) {
		getPersistence().cacheResult(dlFileRank);
	}

	/**
	* Caches the document library file ranks in the entity cache if it is enabled.
	*
	* @param dlFileRanks the document library file ranks
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> dlFileRanks) {
		getPersistence().cacheResult(dlFileRanks);
	}

	/**
	* Creates a new document library file rank with the primary key. Does not add the document library file rank to the database.
	*
	* @param fileRankId the primary key for the new document library file rank
	* @return the new document library file rank
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileRank create(
		long fileRankId) {
		return getPersistence().create(fileRankId);
	}

	/**
	* Removes the document library file rank with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileRankId the primary key of the document library file rank
	* @return the document library file rank that was removed
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileRankException if a document library file rank with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileRank remove(
		long fileRankId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileRankException {
		return getPersistence().remove(fileRankId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank updateImpl(
		com.liferay.portlet.documentlibrary.model.DLFileRank dlFileRank,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(dlFileRank, merge);
	}

	/**
	* Returns the document library file rank with the primary key or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileRankException} if it could not be found.
	*
	* @param fileRankId the primary key of the document library file rank
	* @return the document library file rank
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileRankException if a document library file rank with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileRank findByPrimaryKey(
		long fileRankId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileRankException {
		return getPersistence().findByPrimaryKey(fileRankId);
	}

	/**
	* Returns the document library file rank with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param fileRankId the primary key of the document library file rank
	* @return the document library file rank, or <code>null</code> if a document library file rank with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileRank fetchByPrimaryKey(
		long fileRankId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(fileRankId);
	}

	/**
	* Returns all the document library file ranks where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching document library file ranks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId);
	}

	/**
	* Returns a range of all the document library file ranks where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of document library file ranks
	* @param end the upper bound of the range of document library file ranks (not inclusive)
	* @return the range of matching document library file ranks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	* Returns an ordered range of all the document library file ranks where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of document library file ranks
	* @param end the upper bound of the range of document library file ranks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file ranks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

	/**
	* Returns the first document library file rank in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file rank
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileRankException if a matching document library file rank could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileRank findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileRankException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the last document library file rank in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file rank
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileRankException if a matching document library file rank could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileRank findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileRankException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the document library file ranks before and after the current document library file rank in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileRankId the primary key of the current document library file rank
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file rank
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileRankException if a document library file rank with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileRank[] findByUserId_PrevAndNext(
		long fileRankId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileRankException {
		return getPersistence()
				   .findByUserId_PrevAndNext(fileRankId, userId,
			orderByComparator);
	}

	/**
	* Returns all the document library file ranks where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @return the matching document library file ranks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> findByFileEntryId(
		long fileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByFileEntryId(fileEntryId);
	}

	/**
	* Returns a range of all the document library file ranks where fileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileEntryId the file entry ID
	* @param start the lower bound of the range of document library file ranks
	* @param end the upper bound of the range of document library file ranks (not inclusive)
	* @return the range of matching document library file ranks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> findByFileEntryId(
		long fileEntryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByFileEntryId(fileEntryId, start, end);
	}

	/**
	* Returns an ordered range of all the document library file ranks where fileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileEntryId the file entry ID
	* @param start the lower bound of the range of document library file ranks
	* @param end the upper bound of the range of document library file ranks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file ranks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> findByFileEntryId(
		long fileEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByFileEntryId(fileEntryId, start, end, orderByComparator);
	}

	/**
	* Returns the first document library file rank in the ordered set where fileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file rank
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileRankException if a matching document library file rank could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileRank findByFileEntryId_First(
		long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileRankException {
		return getPersistence()
				   .findByFileEntryId_First(fileEntryId, orderByComparator);
	}

	/**
	* Returns the last document library file rank in the ordered set where fileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file rank
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileRankException if a matching document library file rank could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileRank findByFileEntryId_Last(
		long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileRankException {
		return getPersistence()
				   .findByFileEntryId_Last(fileEntryId, orderByComparator);
	}

	/**
	* Returns the document library file ranks before and after the current document library file rank in the ordered set where fileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileRankId the primary key of the current document library file rank
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file rank
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileRankException if a document library file rank with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileRank[] findByFileEntryId_PrevAndNext(
		long fileRankId, long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileRankException {
		return getPersistence()
				   .findByFileEntryId_PrevAndNext(fileRankId, fileEntryId,
			orderByComparator);
	}

	/**
	* Returns all the document library file ranks where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the matching document library file ranks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> findByG_U(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_U(groupId, userId);
	}

	/**
	* Returns a range of all the document library file ranks where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of document library file ranks
	* @param end the upper bound of the range of document library file ranks (not inclusive)
	* @return the range of matching document library file ranks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> findByG_U(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_U(groupId, userId, start, end);
	}

	/**
	* Returns an ordered range of all the document library file ranks where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of document library file ranks
	* @param end the upper bound of the range of document library file ranks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file ranks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> findByG_U(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_U(groupId, userId, start, end, orderByComparator);
	}

	/**
	* Returns the first document library file rank in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file rank
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileRankException if a matching document library file rank could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileRank findByG_U_First(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileRankException {
		return getPersistence()
				   .findByG_U_First(groupId, userId, orderByComparator);
	}

	/**
	* Returns the last document library file rank in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file rank
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileRankException if a matching document library file rank could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileRank findByG_U_Last(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileRankException {
		return getPersistence()
				   .findByG_U_Last(groupId, userId, orderByComparator);
	}

	/**
	* Returns the document library file ranks before and after the current document library file rank in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileRankId the primary key of the current document library file rank
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file rank
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileRankException if a document library file rank with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileRank[] findByG_U_PrevAndNext(
		long fileRankId, long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileRankException {
		return getPersistence()
				   .findByG_U_PrevAndNext(fileRankId, groupId, userId,
			orderByComparator);
	}

	/**
	* Returns the document library file rank where companyId = &#63; and userId = &#63; and fileEntryId = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileRankException} if it could not be found.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param fileEntryId the file entry ID
	* @return the matching document library file rank
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileRankException if a matching document library file rank could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileRank findByC_U_F(
		long companyId, long userId, long fileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileRankException {
		return getPersistence().findByC_U_F(companyId, userId, fileEntryId);
	}

	/**
	* Returns the document library file rank where companyId = &#63; and userId = &#63; and fileEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param fileEntryId the file entry ID
	* @return the matching document library file rank, or <code>null</code> if a matching document library file rank could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileRank fetchByC_U_F(
		long companyId, long userId, long fileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_U_F(companyId, userId, fileEntryId);
	}

	/**
	* Returns the document library file rank where companyId = &#63; and userId = &#63; and fileEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param fileEntryId the file entry ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching document library file rank, or <code>null</code> if a matching document library file rank could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileRank fetchByC_U_F(
		long companyId, long userId, long fileEntryId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_U_F(companyId, userId, fileEntryId,
			retrieveFromCache);
	}

	/**
	* Returns all the document library file ranks.
	*
	* @return the document library file ranks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the document library file ranks.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of document library file ranks
	* @param end the upper bound of the range of document library file ranks (not inclusive)
	* @return the range of document library file ranks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the document library file ranks.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of document library file ranks
	* @param end the upper bound of the range of document library file ranks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of document library file ranks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the document library file ranks where userId = &#63; from the database.
	*
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUserId(userId);
	}

	/**
	* Removes all the document library file ranks where fileEntryId = &#63; from the database.
	*
	* @param fileEntryId the file entry ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByFileEntryId(long fileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByFileEntryId(fileEntryId);
	}

	/**
	* Removes all the document library file ranks where groupId = &#63; and userId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_U(groupId, userId);
	}

	/**
	* Removes the document library file rank where companyId = &#63; and userId = &#63; and fileEntryId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param fileEntryId the file entry ID
	* @return the document library file rank that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileRank removeByC_U_F(
		long companyId, long userId, long fileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileRankException {
		return getPersistence().removeByC_U_F(companyId, userId, fileEntryId);
	}

	/**
	* Removes all the document library file ranks from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of document library file ranks where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching document library file ranks
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserId(userId);
	}

	/**
	* Returns the number of document library file ranks where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @return the number of matching document library file ranks
	* @throws SystemException if a system exception occurred
	*/
	public static int countByFileEntryId(long fileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByFileEntryId(fileEntryId);
	}

	/**
	* Returns the number of document library file ranks where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the number of matching document library file ranks
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_U(groupId, userId);
	}

	/**
	* Returns the number of document library file ranks where companyId = &#63; and userId = &#63; and fileEntryId = &#63;.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param fileEntryId the file entry ID
	* @return the number of matching document library file ranks
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_U_F(long companyId, long userId, long fileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_U_F(companyId, userId, fileEntryId);
	}

	/**
	* Returns the number of document library file ranks.
	*
	* @return the number of document library file ranks
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static DLFileRankPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (DLFileRankPersistence)PortalBeanLocatorUtil.locate(DLFileRankPersistence.class.getName());

			ReferenceRegistry.registerReference(DLFileRankUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(DLFileRankPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(DLFileRankUtil.class, "_persistence");
	}

	private static DLFileRankPersistence _persistence;
}
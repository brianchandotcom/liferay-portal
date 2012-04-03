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

package com.liferay.portlet.ratings.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.ratings.model.RatingsEntry;

/**
 * The persistence interface for the ratings entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RatingsEntryPersistenceImpl
 * @see RatingsEntryUtil
 * @generated
 */
public interface RatingsEntryPersistence extends BasePersistence<RatingsEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link RatingsEntryUtil} to access the ratings entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the ratings entry in the entity cache if it is enabled.
	*
	* @param ratingsEntry the ratings entry
	*/
	public void cacheResult(
		com.liferay.portlet.ratings.model.RatingsEntry ratingsEntry);

	/**
	* Caches the ratings entries in the entity cache if it is enabled.
	*
	* @param ratingsEntries the ratings entries
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> ratingsEntries);

	/**
	* Creates a new ratings entry with the primary key. Does not add the ratings entry to the database.
	*
	* @param entryId the primary key for the new ratings entry
	* @return the new ratings entry
	*/
	public com.liferay.portlet.ratings.model.RatingsEntry create(long entryId);

	/**
	* Removes the ratings entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the ratings entry
	* @return the ratings entry that was removed
	* @throws com.liferay.portlet.ratings.NoSuchEntryException if a ratings entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.ratings.model.RatingsEntry remove(long entryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.ratings.NoSuchEntryException;

	public com.liferay.portlet.ratings.model.RatingsEntry updateImpl(
		com.liferay.portlet.ratings.model.RatingsEntry ratingsEntry,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the ratings entry with the primary key or throws a {@link com.liferay.portlet.ratings.NoSuchEntryException} if it could not be found.
	*
	* @param entryId the primary key of the ratings entry
	* @return the ratings entry
	* @throws com.liferay.portlet.ratings.NoSuchEntryException if a ratings entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.ratings.model.RatingsEntry findByPrimaryKey(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.ratings.NoSuchEntryException;

	/**
	* Returns the ratings entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param entryId the primary key of the ratings entry
	* @return the ratings entry, or <code>null</code> if a ratings entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.ratings.model.RatingsEntry fetchByPrimaryKey(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the ratings entries where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching ratings entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the ratings entries where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of ratings entries
	* @param end the upper bound of the range of ratings entries (not inclusive)
	* @return the range of matching ratings entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> findByC_C(
		long classNameId, long classPK, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the ratings entries where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of ratings entries
	* @param end the upper bound of the range of ratings entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching ratings entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first ratings entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ratings entry
	* @throws com.liferay.portlet.ratings.NoSuchEntryException if a matching ratings entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.ratings.model.RatingsEntry findByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.ratings.NoSuchEntryException;

	/**
	* Returns the last ratings entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ratings entry
	* @throws com.liferay.portlet.ratings.NoSuchEntryException if a matching ratings entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.ratings.model.RatingsEntry findByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.ratings.NoSuchEntryException;

	/**
	* Returns the ratings entries before and after the current ratings entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId the primary key of the current ratings entry
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next ratings entry
	* @throws com.liferay.portlet.ratings.NoSuchEntryException if a ratings entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.ratings.model.RatingsEntry[] findByC_C_PrevAndNext(
		long entryId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.ratings.NoSuchEntryException;

	/**
	* Returns the ratings entry where userId = &#63; and classNameId = &#63; and classPK = &#63; or throws a {@link com.liferay.portlet.ratings.NoSuchEntryException} if it could not be found.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching ratings entry
	* @throws com.liferay.portlet.ratings.NoSuchEntryException if a matching ratings entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.ratings.model.RatingsEntry findByU_C_C(
		long userId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.ratings.NoSuchEntryException;

	/**
	* Returns the ratings entry where userId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching ratings entry, or <code>null</code> if a matching ratings entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.ratings.model.RatingsEntry fetchByU_C_C(
		long userId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the ratings entry where userId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching ratings entry, or <code>null</code> if a matching ratings entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.ratings.model.RatingsEntry fetchByU_C_C(
		long userId, long classNameId, long classPK, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the ratings entries where classNameId = &#63; and classPK = &#63; and score = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param score the score
	* @return the matching ratings entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> findByC_C_S(
		long classNameId, long classPK, double score)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the ratings entries where classNameId = &#63; and classPK = &#63; and score = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param score the score
	* @param start the lower bound of the range of ratings entries
	* @param end the upper bound of the range of ratings entries (not inclusive)
	* @return the range of matching ratings entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> findByC_C_S(
		long classNameId, long classPK, double score, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the ratings entries where classNameId = &#63; and classPK = &#63; and score = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param score the score
	* @param start the lower bound of the range of ratings entries
	* @param end the upper bound of the range of ratings entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching ratings entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> findByC_C_S(
		long classNameId, long classPK, double score, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first ratings entry in the ordered set where classNameId = &#63; and classPK = &#63; and score = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param score the score
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ratings entry
	* @throws com.liferay.portlet.ratings.NoSuchEntryException if a matching ratings entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.ratings.model.RatingsEntry findByC_C_S_First(
		long classNameId, long classPK, double score,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.ratings.NoSuchEntryException;

	/**
	* Returns the last ratings entry in the ordered set where classNameId = &#63; and classPK = &#63; and score = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param score the score
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ratings entry
	* @throws com.liferay.portlet.ratings.NoSuchEntryException if a matching ratings entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.ratings.model.RatingsEntry findByC_C_S_Last(
		long classNameId, long classPK, double score,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.ratings.NoSuchEntryException;

	/**
	* Returns the ratings entries before and after the current ratings entry in the ordered set where classNameId = &#63; and classPK = &#63; and score = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId the primary key of the current ratings entry
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param score the score
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next ratings entry
	* @throws com.liferay.portlet.ratings.NoSuchEntryException if a ratings entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.ratings.model.RatingsEntry[] findByC_C_S_PrevAndNext(
		long entryId, long classNameId, long classPK, double score,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.ratings.NoSuchEntryException;

	/**
	* Returns all the ratings entries.
	*
	* @return the ratings entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the ratings entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of ratings entries
	* @param end the upper bound of the range of ratings entries (not inclusive)
	* @return the range of ratings entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the ratings entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of ratings entries
	* @param end the upper bound of the range of ratings entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of ratings entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the ratings entries where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the ratings entry where userId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the ratings entry that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.ratings.model.RatingsEntry removeByU_C_C(
		long userId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.ratings.NoSuchEntryException;

	/**
	* Removes all the ratings entries where classNameId = &#63; and classPK = &#63; and score = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param score the score
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_C_S(long classNameId, long classPK, double score)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the ratings entries from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of ratings entries where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching ratings entries
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of ratings entries where userId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching ratings entries
	* @throws SystemException if a system exception occurred
	*/
	public int countByU_C_C(long userId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of ratings entries where classNameId = &#63; and classPK = &#63; and score = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param score the score
	* @return the number of matching ratings entries
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_C_S(long classNameId, long classPK, double score)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of ratings entries.
	*
	* @return the number of ratings entries
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}
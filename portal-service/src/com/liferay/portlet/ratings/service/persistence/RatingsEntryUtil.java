/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.ratings.model.RatingsEntry;

import java.util.List;

/**
 * The persistence utility for the ratings entry service. This utility wraps {@link RatingsEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RatingsEntryPersistence
 * @see RatingsEntryPersistenceImpl
 * @generated
 */
public class RatingsEntryUtil {
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
	public static void clearCache(RatingsEntry ratingsEntry) {
		getPersistence().clearCache(ratingsEntry);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<RatingsEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<RatingsEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<RatingsEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static RatingsEntry update(RatingsEntry ratingsEntry)
		throws SystemException {
		return getPersistence().update(ratingsEntry);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static RatingsEntry update(RatingsEntry ratingsEntry,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(ratingsEntry, serviceContext);
	}

	/**
	* Returns all the ratings entries where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching ratings entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	* Returns a range of all the ratings entries where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.ratings.model.impl.RatingsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of ratings entries
	* @param end the upper bound of the range of ratings entries (not inclusive)
	* @return the range of matching ratings entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> findByC_C(
		long classNameId, long classPK, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_C(classNameId, classPK, start, end);
	}

	/**
	* Returns an ordered range of all the ratings entries where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.ratings.model.impl.RatingsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_C(classNameId, classPK, start, end,
			orderByComparator);
	}

	/**
	* Returns the first ratings entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ratings entry
	* @throws com.liferay.portlet.ratings.NoSuchEntryException if a matching ratings entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.ratings.model.RatingsEntry findByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.ratings.NoSuchEntryException {
		return getPersistence()
				   .findByC_C_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the first ratings entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ratings entry, or <code>null</code> if a matching ratings entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.ratings.model.RatingsEntry fetchByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_C_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last ratings entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ratings entry
	* @throws com.liferay.portlet.ratings.NoSuchEntryException if a matching ratings entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.ratings.model.RatingsEntry findByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.ratings.NoSuchEntryException {
		return getPersistence()
				   .findByC_C_Last(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last ratings entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ratings entry, or <code>null</code> if a matching ratings entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.ratings.model.RatingsEntry fetchByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_C_Last(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the ratings entries before and after the current ratings entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param entryId the primary key of the current ratings entry
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next ratings entry
	* @throws com.liferay.portlet.ratings.NoSuchEntryException if a ratings entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.ratings.model.RatingsEntry[] findByC_C_PrevAndNext(
		long entryId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.ratings.NoSuchEntryException {
		return getPersistence()
				   .findByC_C_PrevAndNext(entryId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Removes all the ratings entries where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	* Returns the number of ratings entries where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching ratings entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_C(classNameId, classPK);
	}

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
	public static com.liferay.portlet.ratings.model.RatingsEntry findByU_C_C(
		long userId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.ratings.NoSuchEntryException {
		return getPersistence().findByU_C_C(userId, classNameId, classPK);
	}

	/**
	* Returns the ratings entry where userId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching ratings entry, or <code>null</code> if a matching ratings entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.ratings.model.RatingsEntry fetchByU_C_C(
		long userId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByU_C_C(userId, classNameId, classPK);
	}

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
	public static com.liferay.portlet.ratings.model.RatingsEntry fetchByU_C_C(
		long userId, long classNameId, long classPK, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByU_C_C(userId, classNameId, classPK, retrieveFromCache);
	}

	/**
	* Removes the ratings entry where userId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the ratings entry that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.ratings.model.RatingsEntry removeByU_C_C(
		long userId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.ratings.NoSuchEntryException {
		return getPersistence().removeByU_C_C(userId, classNameId, classPK);
	}

	/**
	* Returns the number of ratings entries where userId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching ratings entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByU_C_C(long userId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByU_C_C(userId, classNameId, classPK);
	}

	/**
	* Returns all the ratings entries where classNameId = &#63; and classPK = &#63; and score = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param score the score
	* @return the matching ratings entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> findByC_C_S(
		long classNameId, long classPK, double score)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_C_S(classNameId, classPK, score);
	}

	/**
	* Returns a range of all the ratings entries where classNameId = &#63; and classPK = &#63; and score = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.ratings.model.impl.RatingsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> findByC_C_S(
		long classNameId, long classPK, double score, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_C_S(classNameId, classPK, score, start, end);
	}

	/**
	* Returns an ordered range of all the ratings entries where classNameId = &#63; and classPK = &#63; and score = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.ratings.model.impl.RatingsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> findByC_C_S(
		long classNameId, long classPK, double score, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_C_S(classNameId, classPK, score, start, end,
			orderByComparator);
	}

	/**
	* Returns the first ratings entry in the ordered set where classNameId = &#63; and classPK = &#63; and score = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param score the score
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ratings entry
	* @throws com.liferay.portlet.ratings.NoSuchEntryException if a matching ratings entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.ratings.model.RatingsEntry findByC_C_S_First(
		long classNameId, long classPK, double score,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.ratings.NoSuchEntryException {
		return getPersistence()
				   .findByC_C_S_First(classNameId, classPK, score,
			orderByComparator);
	}

	/**
	* Returns the first ratings entry in the ordered set where classNameId = &#63; and classPK = &#63; and score = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param score the score
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ratings entry, or <code>null</code> if a matching ratings entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.ratings.model.RatingsEntry fetchByC_C_S_First(
		long classNameId, long classPK, double score,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_C_S_First(classNameId, classPK, score,
			orderByComparator);
	}

	/**
	* Returns the last ratings entry in the ordered set where classNameId = &#63; and classPK = &#63; and score = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param score the score
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ratings entry
	* @throws com.liferay.portlet.ratings.NoSuchEntryException if a matching ratings entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.ratings.model.RatingsEntry findByC_C_S_Last(
		long classNameId, long classPK, double score,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.ratings.NoSuchEntryException {
		return getPersistence()
				   .findByC_C_S_Last(classNameId, classPK, score,
			orderByComparator);
	}

	/**
	* Returns the last ratings entry in the ordered set where classNameId = &#63; and classPK = &#63; and score = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param score the score
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ratings entry, or <code>null</code> if a matching ratings entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.ratings.model.RatingsEntry fetchByC_C_S_Last(
		long classNameId, long classPK, double score,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_C_S_Last(classNameId, classPK, score,
			orderByComparator);
	}

	/**
	* Returns the ratings entries before and after the current ratings entry in the ordered set where classNameId = &#63; and classPK = &#63; and score = &#63;.
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
	public static com.liferay.portlet.ratings.model.RatingsEntry[] findByC_C_S_PrevAndNext(
		long entryId, long classNameId, long classPK, double score,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.ratings.NoSuchEntryException {
		return getPersistence()
				   .findByC_C_S_PrevAndNext(entryId, classNameId, classPK,
			score, orderByComparator);
	}

	/**
	* Removes all the ratings entries where classNameId = &#63; and classPK = &#63; and score = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param score the score
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByC_C_S(long classNameId, long classPK,
		double score)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_C_S(classNameId, classPK, score);
	}

	/**
	* Returns the number of ratings entries where classNameId = &#63; and classPK = &#63; and score = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param score the score
	* @return the number of matching ratings entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_C_S(long classNameId, long classPK, double score)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_C_S(classNameId, classPK, score);
	}

	/**
	* Caches the ratings entry in the entity cache if it is enabled.
	*
	* @param ratingsEntry the ratings entry
	*/
	public static void cacheResult(
		com.liferay.portlet.ratings.model.RatingsEntry ratingsEntry) {
		getPersistence().cacheResult(ratingsEntry);
	}

	/**
	* Caches the ratings entries in the entity cache if it is enabled.
	*
	* @param ratingsEntries the ratings entries
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> ratingsEntries) {
		getPersistence().cacheResult(ratingsEntries);
	}

	/**
	* Creates a new ratings entry with the primary key. Does not add the ratings entry to the database.
	*
	* @param entryId the primary key for the new ratings entry
	* @return the new ratings entry
	*/
	public static com.liferay.portlet.ratings.model.RatingsEntry create(
		long entryId) {
		return getPersistence().create(entryId);
	}

	/**
	* Removes the ratings entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the ratings entry
	* @return the ratings entry that was removed
	* @throws com.liferay.portlet.ratings.NoSuchEntryException if a ratings entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.ratings.model.RatingsEntry remove(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.ratings.NoSuchEntryException {
		return getPersistence().remove(entryId);
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry updateImpl(
		com.liferay.portlet.ratings.model.RatingsEntry ratingsEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(ratingsEntry);
	}

	/**
	* Returns the ratings entry with the primary key or throws a {@link com.liferay.portlet.ratings.NoSuchEntryException} if it could not be found.
	*
	* @param entryId the primary key of the ratings entry
	* @return the ratings entry
	* @throws com.liferay.portlet.ratings.NoSuchEntryException if a ratings entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.ratings.model.RatingsEntry findByPrimaryKey(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.ratings.NoSuchEntryException {
		return getPersistence().findByPrimaryKey(entryId);
	}

	/**
	* Returns the ratings entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param entryId the primary key of the ratings entry
	* @return the ratings entry, or <code>null</code> if a ratings entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.ratings.model.RatingsEntry fetchByPrimaryKey(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(entryId);
	}

	/**
	* Returns all the ratings entries.
	*
	* @return the ratings entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the ratings entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.ratings.model.impl.RatingsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ratings entries
	* @param end the upper bound of the range of ratings entries (not inclusive)
	* @return the range of ratings entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the ratings entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.ratings.model.impl.RatingsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ratings entries
	* @param end the upper bound of the range of ratings entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of ratings entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the ratings entries from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of ratings entries.
	*
	* @return the number of ratings entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static RatingsEntryPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (RatingsEntryPersistence)PortalBeanLocatorUtil.locate(RatingsEntryPersistence.class.getName());

			ReferenceRegistry.registerReference(RatingsEntryUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(RatingsEntryPersistence persistence) {
	}

	private static RatingsEntryPersistence _persistence;
}
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.social.model.SocialActivityStatsEntry;

import java.util.List;

/**
 * The persistence utility for the social activity stats entry service. This utility wraps {@link SocialActivityStatsEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityStatsEntryPersistence
 * @see SocialActivityStatsEntryPersistenceImpl
 * @generated
 */
public class SocialActivityStatsEntryUtil {
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
	public static void clearCache(
		SocialActivityStatsEntry socialActivityStatsEntry) {
		getPersistence().clearCache(socialActivityStatsEntry);
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
	public static List<SocialActivityStatsEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SocialActivityStatsEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SocialActivityStatsEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static SocialActivityStatsEntry remove(
		SocialActivityStatsEntry socialActivityStatsEntry)
		throws SystemException {
		return getPersistence().remove(socialActivityStatsEntry);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static SocialActivityStatsEntry update(
		SocialActivityStatsEntry socialActivityStatsEntry, boolean merge)
		throws SystemException {
		return getPersistence().update(socialActivityStatsEntry, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static SocialActivityStatsEntry update(
		SocialActivityStatsEntry socialActivityStatsEntry, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence()
				   .update(socialActivityStatsEntry, merge, serviceContext);
	}

	/**
	* Caches the social activity stats entry in the entity cache if it is enabled.
	*
	* @param socialActivityStatsEntry the social activity stats entry
	*/
	public static void cacheResult(
		com.liferay.portlet.social.model.SocialActivityStatsEntry socialActivityStatsEntry) {
		getPersistence().cacheResult(socialActivityStatsEntry);
	}

	/**
	* Caches the social activity stats entries in the entity cache if it is enabled.
	*
	* @param socialActivityStatsEntries the social activity stats entries
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.social.model.SocialActivityStatsEntry> socialActivityStatsEntries) {
		getPersistence().cacheResult(socialActivityStatsEntries);
	}

	/**
	* Creates a new social activity stats entry with the primary key. Does not add the social activity stats entry to the database.
	*
	* @param activityStatsEntryId the primary key for the new social activity stats entry
	* @return the new social activity stats entry
	*/
	public static com.liferay.portlet.social.model.SocialActivityStatsEntry create(
		long activityStatsEntryId) {
		return getPersistence().create(activityStatsEntryId);
	}

	/**
	* Removes the social activity stats entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param activityStatsEntryId the primary key of the social activity stats entry
	* @return the social activity stats entry that was removed
	* @throws com.liferay.portlet.social.NoSuchActivityStatsEntryException if a social activity stats entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialActivityStatsEntry remove(
		long activityStatsEntryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityStatsEntryException {
		return getPersistence().remove(activityStatsEntryId);
	}

	public static com.liferay.portlet.social.model.SocialActivityStatsEntry updateImpl(
		com.liferay.portlet.social.model.SocialActivityStatsEntry socialActivityStatsEntry,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(socialActivityStatsEntry, merge);
	}

	/**
	* Returns the social activity stats entry with the primary key or throws a {@link com.liferay.portlet.social.NoSuchActivityStatsEntryException} if it could not be found.
	*
	* @param activityStatsEntryId the primary key of the social activity stats entry
	* @return the social activity stats entry
	* @throws com.liferay.portlet.social.NoSuchActivityStatsEntryException if a social activity stats entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialActivityStatsEntry findByPrimaryKey(
		long activityStatsEntryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityStatsEntryException {
		return getPersistence().findByPrimaryKey(activityStatsEntryId);
	}

	/**
	* Returns the social activity stats entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param activityStatsEntryId the primary key of the social activity stats entry
	* @return the social activity stats entry, or <code>null</code> if a social activity stats entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialActivityStatsEntry fetchByPrimaryKey(
		long activityStatsEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(activityStatsEntryId);
	}

	/**
	* Returns all the social activity stats entries where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching social activity stats entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialActivityStatsEntry> findByCN_CP(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCN_CP(classNameId, classPK);
	}

	/**
	* Returns a range of all the social activity stats entries where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social activity stats entries
	* @param end the upper bound of the range of social activity stats entries (not inclusive)
	* @return the range of matching social activity stats entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialActivityStatsEntry> findByCN_CP(
		long classNameId, long classPK, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCN_CP(classNameId, classPK, start, end);
	}

	/**
	* Returns an ordered range of all the social activity stats entries where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social activity stats entries
	* @param end the upper bound of the range of social activity stats entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity stats entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialActivityStatsEntry> findByCN_CP(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCN_CP(classNameId, classPK, start, end,
			orderByComparator);
	}

	/**
	* Returns the first social activity stats entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity stats entry
	* @throws com.liferay.portlet.social.NoSuchActivityStatsEntryException if a matching social activity stats entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialActivityStatsEntry findByCN_CP_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityStatsEntryException {
		return getPersistence()
				   .findByCN_CP_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last social activity stats entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity stats entry
	* @throws com.liferay.portlet.social.NoSuchActivityStatsEntryException if a matching social activity stats entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialActivityStatsEntry findByCN_CP_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityStatsEntryException {
		return getPersistence()
				   .findByCN_CP_Last(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the social activity stats entries before and after the current social activity stats entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param activityStatsEntryId the primary key of the current social activity stats entry
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity stats entry
	* @throws com.liferay.portlet.social.NoSuchActivityStatsEntryException if a social activity stats entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialActivityStatsEntry[] findByCN_CP_PrevAndNext(
		long activityStatsEntryId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityStatsEntryException {
		return getPersistence()
				   .findByCN_CP_PrevAndNext(activityStatsEntryId, classNameId,
			classPK, orderByComparator);
	}

	/**
	* Returns all the social activity stats entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param classType the class type
	* @return the matching social activity stats entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialActivityStatsEntry> findByG_CN_CP_CT(
		long groupId, long classNameId, long classPK, int classType)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_CN_CP_CT(groupId, classNameId, classPK, classType);
	}

	/**
	* Returns a range of all the social activity stats entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param classType the class type
	* @param start the lower bound of the range of social activity stats entries
	* @param end the upper bound of the range of social activity stats entries (not inclusive)
	* @return the range of matching social activity stats entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialActivityStatsEntry> findByG_CN_CP_CT(
		long groupId, long classNameId, long classPK, int classType, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_CN_CP_CT(groupId, classNameId, classPK, classType,
			start, end);
	}

	/**
	* Returns an ordered range of all the social activity stats entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param classType the class type
	* @param start the lower bound of the range of social activity stats entries
	* @param end the upper bound of the range of social activity stats entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity stats entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialActivityStatsEntry> findByG_CN_CP_CT(
		long groupId, long classNameId, long classPK, int classType, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_CN_CP_CT(groupId, classNameId, classPK, classType,
			start, end, orderByComparator);
	}

	/**
	* Returns the first social activity stats entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param classType the class type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity stats entry
	* @throws com.liferay.portlet.social.NoSuchActivityStatsEntryException if a matching social activity stats entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialActivityStatsEntry findByG_CN_CP_CT_First(
		long groupId, long classNameId, long classPK, int classType,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityStatsEntryException {
		return getPersistence()
				   .findByG_CN_CP_CT_First(groupId, classNameId, classPK,
			classType, orderByComparator);
	}

	/**
	* Returns the last social activity stats entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param classType the class type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity stats entry
	* @throws com.liferay.portlet.social.NoSuchActivityStatsEntryException if a matching social activity stats entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialActivityStatsEntry findByG_CN_CP_CT_Last(
		long groupId, long classNameId, long classPK, int classType,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityStatsEntryException {
		return getPersistence()
				   .findByG_CN_CP_CT_Last(groupId, classNameId, classPK,
			classType, orderByComparator);
	}

	/**
	* Returns the social activity stats entries before and after the current social activity stats entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param activityStatsEntryId the primary key of the current social activity stats entry
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param classType the class type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity stats entry
	* @throws com.liferay.portlet.social.NoSuchActivityStatsEntryException if a social activity stats entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialActivityStatsEntry[] findByG_CN_CP_CT_PrevAndNext(
		long activityStatsEntryId, long groupId, long classNameId,
		long classPK, int classType,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityStatsEntryException {
		return getPersistence()
				   .findByG_CN_CP_CT_PrevAndNext(activityStatsEntryId, groupId,
			classNameId, classPK, classType, orderByComparator);
	}

	/**
	* Returns the social activity stats entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63; and statName = &#63; and statPeriodEnd = &#63; or throws a {@link com.liferay.portlet.social.NoSuchActivityStatsEntryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param classType the class type
	* @param statName the stat name
	* @param statPeriodEnd the stat period end
	* @return the matching social activity stats entry
	* @throws com.liferay.portlet.social.NoSuchActivityStatsEntryException if a matching social activity stats entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialActivityStatsEntry findByG_CN_CP_CT_SN_SPE(
		long groupId, long classNameId, long classPK, int classType,
		java.lang.String statName, int statPeriodEnd)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityStatsEntryException {
		return getPersistence()
				   .findByG_CN_CP_CT_SN_SPE(groupId, classNameId, classPK,
			classType, statName, statPeriodEnd);
	}

	/**
	* Returns the social activity stats entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63; and statName = &#63; and statPeriodEnd = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param classType the class type
	* @param statName the stat name
	* @param statPeriodEnd the stat period end
	* @return the matching social activity stats entry, or <code>null</code> if a matching social activity stats entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialActivityStatsEntry fetchByG_CN_CP_CT_SN_SPE(
		long groupId, long classNameId, long classPK, int classType,
		java.lang.String statName, int statPeriodEnd)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_CN_CP_CT_SN_SPE(groupId, classNameId, classPK,
			classType, statName, statPeriodEnd);
	}

	/**
	* Returns the social activity stats entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63; and statName = &#63; and statPeriodEnd = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param classType the class type
	* @param statName the stat name
	* @param statPeriodEnd the stat period end
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching social activity stats entry, or <code>null</code> if a matching social activity stats entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialActivityStatsEntry fetchByG_CN_CP_CT_SN_SPE(
		long groupId, long classNameId, long classPK, int classType,
		java.lang.String statName, int statPeriodEnd, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_CN_CP_CT_SN_SPE(groupId, classNameId, classPK,
			classType, statName, statPeriodEnd, retrieveFromCache);
	}

	/**
	* Returns the social activity stats entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63; and statName = &#63; and statPeriodStart = &#63; or throws a {@link com.liferay.portlet.social.NoSuchActivityStatsEntryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param classType the class type
	* @param statName the stat name
	* @param statPeriodStart the stat period start
	* @return the matching social activity stats entry
	* @throws com.liferay.portlet.social.NoSuchActivityStatsEntryException if a matching social activity stats entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialActivityStatsEntry findByG_CN_CP_CT_SN_SPS(
		long groupId, long classNameId, long classPK, int classType,
		java.lang.String statName, int statPeriodStart)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityStatsEntryException {
		return getPersistence()
				   .findByG_CN_CP_CT_SN_SPS(groupId, classNameId, classPK,
			classType, statName, statPeriodStart);
	}

	/**
	* Returns the social activity stats entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63; and statName = &#63; and statPeriodStart = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param classType the class type
	* @param statName the stat name
	* @param statPeriodStart the stat period start
	* @return the matching social activity stats entry, or <code>null</code> if a matching social activity stats entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialActivityStatsEntry fetchByG_CN_CP_CT_SN_SPS(
		long groupId, long classNameId, long classPK, int classType,
		java.lang.String statName, int statPeriodStart)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_CN_CP_CT_SN_SPS(groupId, classNameId, classPK,
			classType, statName, statPeriodStart);
	}

	/**
	* Returns the social activity stats entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63; and statName = &#63; and statPeriodStart = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param classType the class type
	* @param statName the stat name
	* @param statPeriodStart the stat period start
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching social activity stats entry, or <code>null</code> if a matching social activity stats entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialActivityStatsEntry fetchByG_CN_CP_CT_SN_SPS(
		long groupId, long classNameId, long classPK, int classType,
		java.lang.String statName, int statPeriodStart,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_CN_CP_CT_SN_SPS(groupId, classNameId, classPK,
			classType, statName, statPeriodStart, retrieveFromCache);
	}

	/**
	* Returns all the social activity stats entries.
	*
	* @return the social activity stats entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialActivityStatsEntry> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the social activity stats entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of social activity stats entries
	* @param end the upper bound of the range of social activity stats entries (not inclusive)
	* @return the range of social activity stats entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialActivityStatsEntry> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the social activity stats entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of social activity stats entries
	* @param end the upper bound of the range of social activity stats entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of social activity stats entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialActivityStatsEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the social activity stats entries where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCN_CP(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCN_CP(classNameId, classPK);
	}

	/**
	* Removes all the social activity stats entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param classType the class type
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_CN_CP_CT(long groupId, long classNameId,
		long classPK, int classType)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence()
			.removeByG_CN_CP_CT(groupId, classNameId, classPK, classType);
	}

	/**
	* Removes the social activity stats entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63; and statName = &#63; and statPeriodEnd = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param classType the class type
	* @param statName the stat name
	* @param statPeriodEnd the stat period end
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_CN_CP_CT_SN_SPE(long groupId,
		long classNameId, long classPK, int classType,
		java.lang.String statName, int statPeriodEnd)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityStatsEntryException {
		getPersistence()
			.removeByG_CN_CP_CT_SN_SPE(groupId, classNameId, classPK,
			classType, statName, statPeriodEnd);
	}

	/**
	* Removes the social activity stats entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63; and statName = &#63; and statPeriodStart = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param classType the class type
	* @param statName the stat name
	* @param statPeriodStart the stat period start
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_CN_CP_CT_SN_SPS(long groupId,
		long classNameId, long classPK, int classType,
		java.lang.String statName, int statPeriodStart)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityStatsEntryException {
		getPersistence()
			.removeByG_CN_CP_CT_SN_SPS(groupId, classNameId, classPK,
			classType, statName, statPeriodStart);
	}

	/**
	* Removes all the social activity stats entries from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of social activity stats entries where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching social activity stats entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCN_CP(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCN_CP(classNameId, classPK);
	}

	/**
	* Returns the number of social activity stats entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param classType the class type
	* @return the number of matching social activity stats entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_CN_CP_CT(long groupId, long classNameId,
		long classPK, int classType)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByG_CN_CP_CT(groupId, classNameId, classPK, classType);
	}

	/**
	* Returns the number of social activity stats entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63; and statName = &#63; and statPeriodEnd = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param classType the class type
	* @param statName the stat name
	* @param statPeriodEnd the stat period end
	* @return the number of matching social activity stats entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_CN_CP_CT_SN_SPE(long groupId, long classNameId,
		long classPK, int classType, java.lang.String statName,
		int statPeriodEnd)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByG_CN_CP_CT_SN_SPE(groupId, classNameId, classPK,
			classType, statName, statPeriodEnd);
	}

	/**
	* Returns the number of social activity stats entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63; and statName = &#63; and statPeriodStart = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param classType the class type
	* @param statName the stat name
	* @param statPeriodStart the stat period start
	* @return the number of matching social activity stats entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_CN_CP_CT_SN_SPS(long groupId, long classNameId,
		long classPK, int classType, java.lang.String statName,
		int statPeriodStart)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByG_CN_CP_CT_SN_SPS(groupId, classNameId, classPK,
			classType, statName, statPeriodStart);
	}

	/**
	* Returns the number of social activity stats entries.
	*
	* @return the number of social activity stats entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static SocialActivityStatsEntryPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (SocialActivityStatsEntryPersistence)PortalBeanLocatorUtil.locate(SocialActivityStatsEntryPersistence.class.getName());

			ReferenceRegistry.registerReference(SocialActivityStatsEntryUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(SocialActivityStatsEntryPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(SocialActivityStatsEntryUtil.class,
			"_persistence");
	}

	private static SocialActivityStatsEntryPersistence _persistence;
}
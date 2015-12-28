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

package com.liferay.portlet.social.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.social.model.SocialActivityCounter;

import java.util.List;

/**
 * The persistence utility for the social activity counter service. This utility wraps {@link com.liferay.portlet.social.service.persistence.impl.SocialActivityCounterPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityCounterPersistence
 * @see com.liferay.portlet.social.service.persistence.impl.SocialActivityCounterPersistenceImpl
 * @generated
 */
@ProviderType
public class SocialActivityCounterUtil {
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
	public static void clearCache(SocialActivityCounter socialActivityCounter) {
		getPersistence().clearCache(socialActivityCounter);
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
	public static List<SocialActivityCounter> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SocialActivityCounter> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SocialActivityCounter> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<SocialActivityCounter> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static SocialActivityCounter update(
		SocialActivityCounter socialActivityCounter) {
		return getPersistence().update(socialActivityCounter);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static SocialActivityCounter update(
		SocialActivityCounter socialActivityCounter,
		ServiceContext serviceContext) {
		return getPersistence().update(socialActivityCounter, serviceContext);
	}

	/**
	* Returns all the social activity counters where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching social activity counters
	*/
	public static List<SocialActivityCounter> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the social activity counters where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityCounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of social activity counters
	* @param end the upper bound of the range of social activity counters (not inclusive)
	* @return the range of matching social activity counters
	*/
	public static List<SocialActivityCounter> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the social activity counters where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityCounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of social activity counters
	* @param end the upper bound of the range of social activity counters (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity counters
	*/
	public static List<SocialActivityCounter> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<SocialActivityCounter> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the social activity counters where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityCounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of social activity counters
	* @param end the upper bound of the range of social activity counters (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activity counters
	*/
	public static List<SocialActivityCounter> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<SocialActivityCounter> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first social activity counter in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity counter
	* @throws NoSuchActivityCounterException if a matching social activity counter could not be found
	*/
	public static SocialActivityCounter findByGroupId_First(long groupId,
		OrderByComparator<SocialActivityCounter> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityCounterException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first social activity counter in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	*/
	public static SocialActivityCounter fetchByGroupId_First(long groupId,
		OrderByComparator<SocialActivityCounter> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last social activity counter in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity counter
	* @throws NoSuchActivityCounterException if a matching social activity counter could not be found
	*/
	public static SocialActivityCounter findByGroupId_Last(long groupId,
		OrderByComparator<SocialActivityCounter> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityCounterException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last social activity counter in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	*/
	public static SocialActivityCounter fetchByGroupId_Last(long groupId,
		OrderByComparator<SocialActivityCounter> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the social activity counters before and after the current social activity counter in the ordered set where groupId = &#63;.
	*
	* @param activityCounterId the primary key of the current social activity counter
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity counter
	* @throws NoSuchActivityCounterException if a social activity counter with the primary key could not be found
	*/
	public static SocialActivityCounter[] findByGroupId_PrevAndNext(
		long activityCounterId, long groupId,
		OrderByComparator<SocialActivityCounter> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityCounterException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(activityCounterId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the social activity counters where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of social activity counters where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching social activity counters
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the social activity counters where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching social activity counters
	*/
	public static List<SocialActivityCounter> findByC_C(long classNameId,
		long classPK) {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	* Returns a range of all the social activity counters where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityCounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social activity counters
	* @param end the upper bound of the range of social activity counters (not inclusive)
	* @return the range of matching social activity counters
	*/
	public static List<SocialActivityCounter> findByC_C(long classNameId,
		long classPK, int start, int end) {
		return getPersistence().findByC_C(classNameId, classPK, start, end);
	}

	/**
	* Returns an ordered range of all the social activity counters where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityCounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social activity counters
	* @param end the upper bound of the range of social activity counters (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity counters
	*/
	public static List<SocialActivityCounter> findByC_C(long classNameId,
		long classPK, int start, int end,
		OrderByComparator<SocialActivityCounter> orderByComparator) {
		return getPersistence()
				   .findByC_C(classNameId, classPK, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the social activity counters where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityCounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social activity counters
	* @param end the upper bound of the range of social activity counters (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activity counters
	*/
	public static List<SocialActivityCounter> findByC_C(long classNameId,
		long classPK, int start, int end,
		OrderByComparator<SocialActivityCounter> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_C(classNameId, classPK, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first social activity counter in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity counter
	* @throws NoSuchActivityCounterException if a matching social activity counter could not be found
	*/
	public static SocialActivityCounter findByC_C_First(long classNameId,
		long classPK, OrderByComparator<SocialActivityCounter> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityCounterException {
		return getPersistence()
				   .findByC_C_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the first social activity counter in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	*/
	public static SocialActivityCounter fetchByC_C_First(long classNameId,
		long classPK, OrderByComparator<SocialActivityCounter> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last social activity counter in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity counter
	* @throws NoSuchActivityCounterException if a matching social activity counter could not be found
	*/
	public static SocialActivityCounter findByC_C_Last(long classNameId,
		long classPK, OrderByComparator<SocialActivityCounter> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityCounterException {
		return getPersistence()
				   .findByC_C_Last(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last social activity counter in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	*/
	public static SocialActivityCounter fetchByC_C_Last(long classNameId,
		long classPK, OrderByComparator<SocialActivityCounter> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_Last(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the social activity counters before and after the current social activity counter in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param activityCounterId the primary key of the current social activity counter
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity counter
	* @throws NoSuchActivityCounterException if a social activity counter with the primary key could not be found
	*/
	public static SocialActivityCounter[] findByC_C_PrevAndNext(
		long activityCounterId, long classNameId, long classPK,
		OrderByComparator<SocialActivityCounter> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityCounterException {
		return getPersistence()
				   .findByC_C_PrevAndNext(activityCounterId, classNameId,
			classPK, orderByComparator);
	}

	/**
	* Removes all the social activity counters where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	*/
	public static void removeByC_C(long classNameId, long classPK) {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	* Returns the number of social activity counters where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching social activity counters
	*/
	public static int countByC_C(long classNameId, long classPK) {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	* Returns all the social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ownerType the owner type
	* @return the matching social activity counters
	*/
	public static List<SocialActivityCounter> findByG_C_C_O(long groupId,
		long classNameId, long classPK, int ownerType) {
		return getPersistence()
				   .findByG_C_C_O(groupId, classNameId, classPK, ownerType);
	}

	/**
	* Returns a range of all the social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityCounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ownerType the owner type
	* @param start the lower bound of the range of social activity counters
	* @param end the upper bound of the range of social activity counters (not inclusive)
	* @return the range of matching social activity counters
	*/
	public static List<SocialActivityCounter> findByG_C_C_O(long groupId,
		long classNameId, long classPK, int ownerType, int start, int end) {
		return getPersistence()
				   .findByG_C_C_O(groupId, classNameId, classPK, ownerType,
			start, end);
	}

	/**
	* Returns an ordered range of all the social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityCounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ownerType the owner type
	* @param start the lower bound of the range of social activity counters
	* @param end the upper bound of the range of social activity counters (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity counters
	*/
	public static List<SocialActivityCounter> findByG_C_C_O(long groupId,
		long classNameId, long classPK, int ownerType, int start, int end,
		OrderByComparator<SocialActivityCounter> orderByComparator) {
		return getPersistence()
				   .findByG_C_C_O(groupId, classNameId, classPK, ownerType,
			start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityCounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ownerType the owner type
	* @param start the lower bound of the range of social activity counters
	* @param end the upper bound of the range of social activity counters (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activity counters
	*/
	public static List<SocialActivityCounter> findByG_C_C_O(long groupId,
		long classNameId, long classPK, int ownerType, int start, int end,
		OrderByComparator<SocialActivityCounter> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_C_C_O(groupId, classNameId, classPK, ownerType,
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first social activity counter in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ownerType the owner type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity counter
	* @throws NoSuchActivityCounterException if a matching social activity counter could not be found
	*/
	public static SocialActivityCounter findByG_C_C_O_First(long groupId,
		long classNameId, long classPK, int ownerType,
		OrderByComparator<SocialActivityCounter> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityCounterException {
		return getPersistence()
				   .findByG_C_C_O_First(groupId, classNameId, classPK,
			ownerType, orderByComparator);
	}

	/**
	* Returns the first social activity counter in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ownerType the owner type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	*/
	public static SocialActivityCounter fetchByG_C_C_O_First(long groupId,
		long classNameId, long classPK, int ownerType,
		OrderByComparator<SocialActivityCounter> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_C_O_First(groupId, classNameId, classPK,
			ownerType, orderByComparator);
	}

	/**
	* Returns the last social activity counter in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ownerType the owner type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity counter
	* @throws NoSuchActivityCounterException if a matching social activity counter could not be found
	*/
	public static SocialActivityCounter findByG_C_C_O_Last(long groupId,
		long classNameId, long classPK, int ownerType,
		OrderByComparator<SocialActivityCounter> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityCounterException {
		return getPersistence()
				   .findByG_C_C_O_Last(groupId, classNameId, classPK,
			ownerType, orderByComparator);
	}

	/**
	* Returns the last social activity counter in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ownerType the owner type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	*/
	public static SocialActivityCounter fetchByG_C_C_O_Last(long groupId,
		long classNameId, long classPK, int ownerType,
		OrderByComparator<SocialActivityCounter> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_C_O_Last(groupId, classNameId, classPK,
			ownerType, orderByComparator);
	}

	/**
	* Returns the social activity counters before and after the current social activity counter in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	*
	* @param activityCounterId the primary key of the current social activity counter
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ownerType the owner type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity counter
	* @throws NoSuchActivityCounterException if a social activity counter with the primary key could not be found
	*/
	public static SocialActivityCounter[] findByG_C_C_O_PrevAndNext(
		long activityCounterId, long groupId, long classNameId, long classPK,
		int ownerType,
		OrderByComparator<SocialActivityCounter> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityCounterException {
		return getPersistence()
				   .findByG_C_C_O_PrevAndNext(activityCounterId, groupId,
			classNameId, classPK, ownerType, orderByComparator);
	}

	/**
	* Removes all the social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ownerType the owner type
	*/
	public static void removeByG_C_C_O(long groupId, long classNameId,
		long classPK, int ownerType) {
		getPersistence()
			.removeByG_C_C_O(groupId, classNameId, classPK, ownerType);
	}

	/**
	* Returns the number of social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ownerType the owner type
	* @return the number of matching social activity counters
	*/
	public static int countByG_C_C_O(long groupId, long classNameId,
		long classPK, int ownerType) {
		return getPersistence()
				   .countByG_C_C_O(groupId, classNameId, classPK, ownerType);
	}

	/**
	* Returns the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and startPeriod = &#63; or throws a {@link NoSuchActivityCounterException} if it could not be found.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param name the name
	* @param ownerType the owner type
	* @param startPeriod the start period
	* @return the matching social activity counter
	* @throws NoSuchActivityCounterException if a matching social activity counter could not be found
	*/
	public static SocialActivityCounter findByG_C_C_N_O_S(long groupId,
		long classNameId, long classPK, java.lang.String name, int ownerType,
		int startPeriod)
		throws com.liferay.portlet.social.NoSuchActivityCounterException {
		return getPersistence()
				   .findByG_C_C_N_O_S(groupId, classNameId, classPK, name,
			ownerType, startPeriod);
	}

	/**
	* Returns the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and startPeriod = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param name the name
	* @param ownerType the owner type
	* @param startPeriod the start period
	* @return the matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	*/
	public static SocialActivityCounter fetchByG_C_C_N_O_S(long groupId,
		long classNameId, long classPK, java.lang.String name, int ownerType,
		int startPeriod) {
		return getPersistence()
				   .fetchByG_C_C_N_O_S(groupId, classNameId, classPK, name,
			ownerType, startPeriod);
	}

	/**
	* Returns the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and startPeriod = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param name the name
	* @param ownerType the owner type
	* @param startPeriod the start period
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	*/
	public static SocialActivityCounter fetchByG_C_C_N_O_S(long groupId,
		long classNameId, long classPK, java.lang.String name, int ownerType,
		int startPeriod, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_C_C_N_O_S(groupId, classNameId, classPK, name,
			ownerType, startPeriod, retrieveFromCache);
	}

	/**
	* Removes the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and startPeriod = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param name the name
	* @param ownerType the owner type
	* @param startPeriod the start period
	* @return the social activity counter that was removed
	*/
	public static SocialActivityCounter removeByG_C_C_N_O_S(long groupId,
		long classNameId, long classPK, java.lang.String name, int ownerType,
		int startPeriod)
		throws com.liferay.portlet.social.NoSuchActivityCounterException {
		return getPersistence()
				   .removeByG_C_C_N_O_S(groupId, classNameId, classPK, name,
			ownerType, startPeriod);
	}

	/**
	* Returns the number of social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and startPeriod = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param name the name
	* @param ownerType the owner type
	* @param startPeriod the start period
	* @return the number of matching social activity counters
	*/
	public static int countByG_C_C_N_O_S(long groupId, long classNameId,
		long classPK, java.lang.String name, int ownerType, int startPeriod) {
		return getPersistence()
				   .countByG_C_C_N_O_S(groupId, classNameId, classPK, name,
			ownerType, startPeriod);
	}

	/**
	* Returns the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and endPeriod = &#63; or throws a {@link NoSuchActivityCounterException} if it could not be found.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param name the name
	* @param ownerType the owner type
	* @param endPeriod the end period
	* @return the matching social activity counter
	* @throws NoSuchActivityCounterException if a matching social activity counter could not be found
	*/
	public static SocialActivityCounter findByG_C_C_N_O_E(long groupId,
		long classNameId, long classPK, java.lang.String name, int ownerType,
		int endPeriod)
		throws com.liferay.portlet.social.NoSuchActivityCounterException {
		return getPersistence()
				   .findByG_C_C_N_O_E(groupId, classNameId, classPK, name,
			ownerType, endPeriod);
	}

	/**
	* Returns the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and endPeriod = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param name the name
	* @param ownerType the owner type
	* @param endPeriod the end period
	* @return the matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	*/
	public static SocialActivityCounter fetchByG_C_C_N_O_E(long groupId,
		long classNameId, long classPK, java.lang.String name, int ownerType,
		int endPeriod) {
		return getPersistence()
				   .fetchByG_C_C_N_O_E(groupId, classNameId, classPK, name,
			ownerType, endPeriod);
	}

	/**
	* Returns the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and endPeriod = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param name the name
	* @param ownerType the owner type
	* @param endPeriod the end period
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	*/
	public static SocialActivityCounter fetchByG_C_C_N_O_E(long groupId,
		long classNameId, long classPK, java.lang.String name, int ownerType,
		int endPeriod, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_C_C_N_O_E(groupId, classNameId, classPK, name,
			ownerType, endPeriod, retrieveFromCache);
	}

	/**
	* Removes the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and endPeriod = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param name the name
	* @param ownerType the owner type
	* @param endPeriod the end period
	* @return the social activity counter that was removed
	*/
	public static SocialActivityCounter removeByG_C_C_N_O_E(long groupId,
		long classNameId, long classPK, java.lang.String name, int ownerType,
		int endPeriod)
		throws com.liferay.portlet.social.NoSuchActivityCounterException {
		return getPersistence()
				   .removeByG_C_C_N_O_E(groupId, classNameId, classPK, name,
			ownerType, endPeriod);
	}

	/**
	* Returns the number of social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and endPeriod = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param name the name
	* @param ownerType the owner type
	* @param endPeriod the end period
	* @return the number of matching social activity counters
	*/
	public static int countByG_C_C_N_O_E(long groupId, long classNameId,
		long classPK, java.lang.String name, int ownerType, int endPeriod) {
		return getPersistence()
				   .countByG_C_C_N_O_E(groupId, classNameId, classPK, name,
			ownerType, endPeriod);
	}

	/**
	* Caches the social activity counter in the entity cache if it is enabled.
	*
	* @param socialActivityCounter the social activity counter
	*/
	public static void cacheResult(SocialActivityCounter socialActivityCounter) {
		getPersistence().cacheResult(socialActivityCounter);
	}

	/**
	* Caches the social activity counters in the entity cache if it is enabled.
	*
	* @param socialActivityCounters the social activity counters
	*/
	public static void cacheResult(
		List<SocialActivityCounter> socialActivityCounters) {
		getPersistence().cacheResult(socialActivityCounters);
	}

	/**
	* Creates a new social activity counter with the primary key. Does not add the social activity counter to the database.
	*
	* @param activityCounterId the primary key for the new social activity counter
	* @return the new social activity counter
	*/
	public static SocialActivityCounter create(long activityCounterId) {
		return getPersistence().create(activityCounterId);
	}

	/**
	* Removes the social activity counter with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param activityCounterId the primary key of the social activity counter
	* @return the social activity counter that was removed
	* @throws NoSuchActivityCounterException if a social activity counter with the primary key could not be found
	*/
	public static SocialActivityCounter remove(long activityCounterId)
		throws com.liferay.portlet.social.NoSuchActivityCounterException {
		return getPersistence().remove(activityCounterId);
	}

	public static SocialActivityCounter updateImpl(
		SocialActivityCounter socialActivityCounter) {
		return getPersistence().updateImpl(socialActivityCounter);
	}

	/**
	* Returns the social activity counter with the primary key or throws a {@link NoSuchActivityCounterException} if it could not be found.
	*
	* @param activityCounterId the primary key of the social activity counter
	* @return the social activity counter
	* @throws NoSuchActivityCounterException if a social activity counter with the primary key could not be found
	*/
	public static SocialActivityCounter findByPrimaryKey(long activityCounterId)
		throws com.liferay.portlet.social.NoSuchActivityCounterException {
		return getPersistence().findByPrimaryKey(activityCounterId);
	}

	/**
	* Returns the social activity counter with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param activityCounterId the primary key of the social activity counter
	* @return the social activity counter, or <code>null</code> if a social activity counter with the primary key could not be found
	*/
	public static SocialActivityCounter fetchByPrimaryKey(
		long activityCounterId) {
		return getPersistence().fetchByPrimaryKey(activityCounterId);
	}

	public static java.util.Map<java.io.Serializable, SocialActivityCounter> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the social activity counters.
	*
	* @return the social activity counters
	*/
	public static List<SocialActivityCounter> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the social activity counters.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityCounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social activity counters
	* @param end the upper bound of the range of social activity counters (not inclusive)
	* @return the range of social activity counters
	*/
	public static List<SocialActivityCounter> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the social activity counters.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityCounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social activity counters
	* @param end the upper bound of the range of social activity counters (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of social activity counters
	*/
	public static List<SocialActivityCounter> findAll(int start, int end,
		OrderByComparator<SocialActivityCounter> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the social activity counters.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityCounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social activity counters
	* @param end the upper bound of the range of social activity counters (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of social activity counters
	*/
	public static List<SocialActivityCounter> findAll(int start, int end,
		OrderByComparator<SocialActivityCounter> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the social activity counters from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of social activity counters.
	*
	* @return the number of social activity counters
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static SocialActivityCounterPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (SocialActivityCounterPersistence)PortalBeanLocatorUtil.locate(SocialActivityCounterPersistence.class.getName());

			ReferenceRegistry.registerReference(SocialActivityCounterUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static SocialActivityCounterPersistence _persistence;
}
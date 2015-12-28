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

import com.liferay.portlet.social.model.SocialActivityLimit;

import java.util.List;

/**
 * The persistence utility for the social activity limit service. This utility wraps {@link com.liferay.portlet.social.service.persistence.impl.SocialActivityLimitPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityLimitPersistence
 * @see com.liferay.portlet.social.service.persistence.impl.SocialActivityLimitPersistenceImpl
 * @generated
 */
@ProviderType
public class SocialActivityLimitUtil {
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
	public static void clearCache(SocialActivityLimit socialActivityLimit) {
		getPersistence().clearCache(socialActivityLimit);
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
	public static List<SocialActivityLimit> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SocialActivityLimit> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SocialActivityLimit> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<SocialActivityLimit> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static SocialActivityLimit update(
		SocialActivityLimit socialActivityLimit) {
		return getPersistence().update(socialActivityLimit);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static SocialActivityLimit update(
		SocialActivityLimit socialActivityLimit, ServiceContext serviceContext) {
		return getPersistence().update(socialActivityLimit, serviceContext);
	}

	/**
	* Returns all the social activity limits where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching social activity limits
	*/
	public static List<SocialActivityLimit> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the social activity limits where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityLimitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of social activity limits
	* @param end the upper bound of the range of social activity limits (not inclusive)
	* @return the range of matching social activity limits
	*/
	public static List<SocialActivityLimit> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the social activity limits where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityLimitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of social activity limits
	* @param end the upper bound of the range of social activity limits (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity limits
	*/
	public static List<SocialActivityLimit> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<SocialActivityLimit> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the social activity limits where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityLimitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of social activity limits
	* @param end the upper bound of the range of social activity limits (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activity limits
	*/
	public static List<SocialActivityLimit> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<SocialActivityLimit> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first social activity limit in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity limit
	* @throws NoSuchActivityLimitException if a matching social activity limit could not be found
	*/
	public static SocialActivityLimit findByGroupId_First(long groupId,
		OrderByComparator<SocialActivityLimit> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityLimitException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first social activity limit in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity limit, or <code>null</code> if a matching social activity limit could not be found
	*/
	public static SocialActivityLimit fetchByGroupId_First(long groupId,
		OrderByComparator<SocialActivityLimit> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last social activity limit in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity limit
	* @throws NoSuchActivityLimitException if a matching social activity limit could not be found
	*/
	public static SocialActivityLimit findByGroupId_Last(long groupId,
		OrderByComparator<SocialActivityLimit> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityLimitException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last social activity limit in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity limit, or <code>null</code> if a matching social activity limit could not be found
	*/
	public static SocialActivityLimit fetchByGroupId_Last(long groupId,
		OrderByComparator<SocialActivityLimit> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the social activity limits before and after the current social activity limit in the ordered set where groupId = &#63;.
	*
	* @param activityLimitId the primary key of the current social activity limit
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity limit
	* @throws NoSuchActivityLimitException if a social activity limit with the primary key could not be found
	*/
	public static SocialActivityLimit[] findByGroupId_PrevAndNext(
		long activityLimitId, long groupId,
		OrderByComparator<SocialActivityLimit> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityLimitException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(activityLimitId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the social activity limits where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of social activity limits where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching social activity limits
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the social activity limits where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching social activity limits
	*/
	public static List<SocialActivityLimit> findByUserId(long userId) {
		return getPersistence().findByUserId(userId);
	}

	/**
	* Returns a range of all the social activity limits where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityLimitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of social activity limits
	* @param end the upper bound of the range of social activity limits (not inclusive)
	* @return the range of matching social activity limits
	*/
	public static List<SocialActivityLimit> findByUserId(long userId,
		int start, int end) {
		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	* Returns an ordered range of all the social activity limits where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityLimitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of social activity limits
	* @param end the upper bound of the range of social activity limits (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity limits
	*/
	public static List<SocialActivityLimit> findByUserId(long userId,
		int start, int end,
		OrderByComparator<SocialActivityLimit> orderByComparator) {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the social activity limits where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityLimitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of social activity limits
	* @param end the upper bound of the range of social activity limits (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activity limits
	*/
	public static List<SocialActivityLimit> findByUserId(long userId,
		int start, int end,
		OrderByComparator<SocialActivityLimit> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first social activity limit in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity limit
	* @throws NoSuchActivityLimitException if a matching social activity limit could not be found
	*/
	public static SocialActivityLimit findByUserId_First(long userId,
		OrderByComparator<SocialActivityLimit> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityLimitException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the first social activity limit in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity limit, or <code>null</code> if a matching social activity limit could not be found
	*/
	public static SocialActivityLimit fetchByUserId_First(long userId,
		OrderByComparator<SocialActivityLimit> orderByComparator) {
		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the last social activity limit in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity limit
	* @throws NoSuchActivityLimitException if a matching social activity limit could not be found
	*/
	public static SocialActivityLimit findByUserId_Last(long userId,
		OrderByComparator<SocialActivityLimit> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityLimitException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the last social activity limit in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity limit, or <code>null</code> if a matching social activity limit could not be found
	*/
	public static SocialActivityLimit fetchByUserId_Last(long userId,
		OrderByComparator<SocialActivityLimit> orderByComparator) {
		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the social activity limits before and after the current social activity limit in the ordered set where userId = &#63;.
	*
	* @param activityLimitId the primary key of the current social activity limit
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity limit
	* @throws NoSuchActivityLimitException if a social activity limit with the primary key could not be found
	*/
	public static SocialActivityLimit[] findByUserId_PrevAndNext(
		long activityLimitId, long userId,
		OrderByComparator<SocialActivityLimit> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityLimitException {
		return getPersistence()
				   .findByUserId_PrevAndNext(activityLimitId, userId,
			orderByComparator);
	}

	/**
	* Removes all the social activity limits where userId = &#63; from the database.
	*
	* @param userId the user ID
	*/
	public static void removeByUserId(long userId) {
		getPersistence().removeByUserId(userId);
	}

	/**
	* Returns the number of social activity limits where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching social activity limits
	*/
	public static int countByUserId(long userId) {
		return getPersistence().countByUserId(userId);
	}

	/**
	* Returns all the social activity limits where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching social activity limits
	*/
	public static List<SocialActivityLimit> findByC_C(long classNameId,
		long classPK) {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	* Returns a range of all the social activity limits where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityLimitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social activity limits
	* @param end the upper bound of the range of social activity limits (not inclusive)
	* @return the range of matching social activity limits
	*/
	public static List<SocialActivityLimit> findByC_C(long classNameId,
		long classPK, int start, int end) {
		return getPersistence().findByC_C(classNameId, classPK, start, end);
	}

	/**
	* Returns an ordered range of all the social activity limits where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityLimitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social activity limits
	* @param end the upper bound of the range of social activity limits (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity limits
	*/
	public static List<SocialActivityLimit> findByC_C(long classNameId,
		long classPK, int start, int end,
		OrderByComparator<SocialActivityLimit> orderByComparator) {
		return getPersistence()
				   .findByC_C(classNameId, classPK, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the social activity limits where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityLimitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social activity limits
	* @param end the upper bound of the range of social activity limits (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activity limits
	*/
	public static List<SocialActivityLimit> findByC_C(long classNameId,
		long classPK, int start, int end,
		OrderByComparator<SocialActivityLimit> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_C(classNameId, classPK, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first social activity limit in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity limit
	* @throws NoSuchActivityLimitException if a matching social activity limit could not be found
	*/
	public static SocialActivityLimit findByC_C_First(long classNameId,
		long classPK, OrderByComparator<SocialActivityLimit> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityLimitException {
		return getPersistence()
				   .findByC_C_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the first social activity limit in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity limit, or <code>null</code> if a matching social activity limit could not be found
	*/
	public static SocialActivityLimit fetchByC_C_First(long classNameId,
		long classPK, OrderByComparator<SocialActivityLimit> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last social activity limit in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity limit
	* @throws NoSuchActivityLimitException if a matching social activity limit could not be found
	*/
	public static SocialActivityLimit findByC_C_Last(long classNameId,
		long classPK, OrderByComparator<SocialActivityLimit> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityLimitException {
		return getPersistence()
				   .findByC_C_Last(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last social activity limit in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity limit, or <code>null</code> if a matching social activity limit could not be found
	*/
	public static SocialActivityLimit fetchByC_C_Last(long classNameId,
		long classPK, OrderByComparator<SocialActivityLimit> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_Last(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the social activity limits before and after the current social activity limit in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param activityLimitId the primary key of the current social activity limit
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity limit
	* @throws NoSuchActivityLimitException if a social activity limit with the primary key could not be found
	*/
	public static SocialActivityLimit[] findByC_C_PrevAndNext(
		long activityLimitId, long classNameId, long classPK,
		OrderByComparator<SocialActivityLimit> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityLimitException {
		return getPersistence()
				   .findByC_C_PrevAndNext(activityLimitId, classNameId,
			classPK, orderByComparator);
	}

	/**
	* Removes all the social activity limits where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	*/
	public static void removeByC_C(long classNameId, long classPK) {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	* Returns the number of social activity limits where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching social activity limits
	*/
	public static int countByC_C(long classNameId, long classPK) {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	* Returns the social activity limit where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and activityType = &#63; and activityCounterName = &#63; or throws a {@link NoSuchActivityLimitException} if it could not be found.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param activityType the activity type
	* @param activityCounterName the activity counter name
	* @return the matching social activity limit
	* @throws NoSuchActivityLimitException if a matching social activity limit could not be found
	*/
	public static SocialActivityLimit findByG_U_C_C_A_A(long groupId,
		long userId, long classNameId, long classPK, int activityType,
		java.lang.String activityCounterName)
		throws com.liferay.portlet.social.NoSuchActivityLimitException {
		return getPersistence()
				   .findByG_U_C_C_A_A(groupId, userId, classNameId, classPK,
			activityType, activityCounterName);
	}

	/**
	* Returns the social activity limit where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and activityType = &#63; and activityCounterName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param activityType the activity type
	* @param activityCounterName the activity counter name
	* @return the matching social activity limit, or <code>null</code> if a matching social activity limit could not be found
	*/
	public static SocialActivityLimit fetchByG_U_C_C_A_A(long groupId,
		long userId, long classNameId, long classPK, int activityType,
		java.lang.String activityCounterName) {
		return getPersistence()
				   .fetchByG_U_C_C_A_A(groupId, userId, classNameId, classPK,
			activityType, activityCounterName);
	}

	/**
	* Returns the social activity limit where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and activityType = &#63; and activityCounterName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param activityType the activity type
	* @param activityCounterName the activity counter name
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching social activity limit, or <code>null</code> if a matching social activity limit could not be found
	*/
	public static SocialActivityLimit fetchByG_U_C_C_A_A(long groupId,
		long userId, long classNameId, long classPK, int activityType,
		java.lang.String activityCounterName, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_U_C_C_A_A(groupId, userId, classNameId, classPK,
			activityType, activityCounterName, retrieveFromCache);
	}

	/**
	* Removes the social activity limit where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and activityType = &#63; and activityCounterName = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param activityType the activity type
	* @param activityCounterName the activity counter name
	* @return the social activity limit that was removed
	*/
	public static SocialActivityLimit removeByG_U_C_C_A_A(long groupId,
		long userId, long classNameId, long classPK, int activityType,
		java.lang.String activityCounterName)
		throws com.liferay.portlet.social.NoSuchActivityLimitException {
		return getPersistence()
				   .removeByG_U_C_C_A_A(groupId, userId, classNameId, classPK,
			activityType, activityCounterName);
	}

	/**
	* Returns the number of social activity limits where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and activityType = &#63; and activityCounterName = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param activityType the activity type
	* @param activityCounterName the activity counter name
	* @return the number of matching social activity limits
	*/
	public static int countByG_U_C_C_A_A(long groupId, long userId,
		long classNameId, long classPK, int activityType,
		java.lang.String activityCounterName) {
		return getPersistence()
				   .countByG_U_C_C_A_A(groupId, userId, classNameId, classPK,
			activityType, activityCounterName);
	}

	/**
	* Caches the social activity limit in the entity cache if it is enabled.
	*
	* @param socialActivityLimit the social activity limit
	*/
	public static void cacheResult(SocialActivityLimit socialActivityLimit) {
		getPersistence().cacheResult(socialActivityLimit);
	}

	/**
	* Caches the social activity limits in the entity cache if it is enabled.
	*
	* @param socialActivityLimits the social activity limits
	*/
	public static void cacheResult(
		List<SocialActivityLimit> socialActivityLimits) {
		getPersistence().cacheResult(socialActivityLimits);
	}

	/**
	* Creates a new social activity limit with the primary key. Does not add the social activity limit to the database.
	*
	* @param activityLimitId the primary key for the new social activity limit
	* @return the new social activity limit
	*/
	public static SocialActivityLimit create(long activityLimitId) {
		return getPersistence().create(activityLimitId);
	}

	/**
	* Removes the social activity limit with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param activityLimitId the primary key of the social activity limit
	* @return the social activity limit that was removed
	* @throws NoSuchActivityLimitException if a social activity limit with the primary key could not be found
	*/
	public static SocialActivityLimit remove(long activityLimitId)
		throws com.liferay.portlet.social.NoSuchActivityLimitException {
		return getPersistence().remove(activityLimitId);
	}

	public static SocialActivityLimit updateImpl(
		SocialActivityLimit socialActivityLimit) {
		return getPersistence().updateImpl(socialActivityLimit);
	}

	/**
	* Returns the social activity limit with the primary key or throws a {@link NoSuchActivityLimitException} if it could not be found.
	*
	* @param activityLimitId the primary key of the social activity limit
	* @return the social activity limit
	* @throws NoSuchActivityLimitException if a social activity limit with the primary key could not be found
	*/
	public static SocialActivityLimit findByPrimaryKey(long activityLimitId)
		throws com.liferay.portlet.social.NoSuchActivityLimitException {
		return getPersistence().findByPrimaryKey(activityLimitId);
	}

	/**
	* Returns the social activity limit with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param activityLimitId the primary key of the social activity limit
	* @return the social activity limit, or <code>null</code> if a social activity limit with the primary key could not be found
	*/
	public static SocialActivityLimit fetchByPrimaryKey(long activityLimitId) {
		return getPersistence().fetchByPrimaryKey(activityLimitId);
	}

	public static java.util.Map<java.io.Serializable, SocialActivityLimit> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the social activity limits.
	*
	* @return the social activity limits
	*/
	public static List<SocialActivityLimit> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the social activity limits.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityLimitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social activity limits
	* @param end the upper bound of the range of social activity limits (not inclusive)
	* @return the range of social activity limits
	*/
	public static List<SocialActivityLimit> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the social activity limits.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityLimitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social activity limits
	* @param end the upper bound of the range of social activity limits (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of social activity limits
	*/
	public static List<SocialActivityLimit> findAll(int start, int end,
		OrderByComparator<SocialActivityLimit> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the social activity limits.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityLimitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social activity limits
	* @param end the upper bound of the range of social activity limits (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of social activity limits
	*/
	public static List<SocialActivityLimit> findAll(int start, int end,
		OrderByComparator<SocialActivityLimit> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the social activity limits from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of social activity limits.
	*
	* @return the number of social activity limits
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static SocialActivityLimitPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (SocialActivityLimitPersistence)PortalBeanLocatorUtil.locate(SocialActivityLimitPersistence.class.getName());

			ReferenceRegistry.registerReference(SocialActivityLimitUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static SocialActivityLimitPersistence _persistence;
}
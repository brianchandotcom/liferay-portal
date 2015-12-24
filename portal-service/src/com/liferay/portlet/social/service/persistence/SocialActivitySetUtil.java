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

import com.liferay.portlet.social.model.SocialActivitySet;

import java.util.List;

/**
 * The persistence utility for the social activity set service. This utility wraps {@link com.liferay.portlet.social.service.persistence.impl.SocialActivitySetPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivitySetPersistence
 * @see com.liferay.portlet.social.service.persistence.impl.SocialActivitySetPersistenceImpl
 * @generated
 */
@ProviderType
public class SocialActivitySetUtil {
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
	public static void clearCache(SocialActivitySet socialActivitySet) {
		getPersistence().clearCache(socialActivitySet);
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
	public static List<SocialActivitySet> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SocialActivitySet> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SocialActivitySet> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<SocialActivitySet> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static SocialActivitySet update(SocialActivitySet socialActivitySet) {
		return getPersistence().update(socialActivitySet);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static SocialActivitySet update(
		SocialActivitySet socialActivitySet, ServiceContext serviceContext) {
		return getPersistence().update(socialActivitySet, serviceContext);
	}

	/**
	* Returns all the social activity sets where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching social activity sets
	*/
	public static List<SocialActivitySet> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the social activity sets where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of social activity sets
	* @param end the upper bound of the range of social activity sets (not inclusive)
	* @return the range of matching social activity sets
	*/
	public static List<SocialActivitySet> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the social activity sets where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of social activity sets
	* @param end the upper bound of the range of social activity sets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity sets
	*/
	public static List<SocialActivitySet> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<SocialActivitySet> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the social activity sets where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of social activity sets
	* @param end the upper bound of the range of social activity sets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activity sets
	*/
	public static List<SocialActivitySet> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<SocialActivitySet> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first social activity set in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity set
	* @throws NoSuchActivitySetException if a matching social activity set could not be found
	*/
	public static SocialActivitySet findByGroupId_First(long groupId,
		OrderByComparator<SocialActivitySet> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivitySetException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first social activity set in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity set, or <code>null</code> if a matching social activity set could not be found
	*/
	public static SocialActivitySet fetchByGroupId_First(long groupId,
		OrderByComparator<SocialActivitySet> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last social activity set in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity set
	* @throws NoSuchActivitySetException if a matching social activity set could not be found
	*/
	public static SocialActivitySet findByGroupId_Last(long groupId,
		OrderByComparator<SocialActivitySet> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivitySetException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last social activity set in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity set, or <code>null</code> if a matching social activity set could not be found
	*/
	public static SocialActivitySet fetchByGroupId_Last(long groupId,
		OrderByComparator<SocialActivitySet> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the social activity sets before and after the current social activity set in the ordered set where groupId = &#63;.
	*
	* @param activitySetId the primary key of the current social activity set
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity set
	* @throws NoSuchActivitySetException if a social activity set with the primary key could not be found
	*/
	public static SocialActivitySet[] findByGroupId_PrevAndNext(
		long activitySetId, long groupId,
		OrderByComparator<SocialActivitySet> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivitySetException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(activitySetId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the social activity sets where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of social activity sets where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching social activity sets
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the social activity sets where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching social activity sets
	*/
	public static List<SocialActivitySet> findByUserId(long userId) {
		return getPersistence().findByUserId(userId);
	}

	/**
	* Returns a range of all the social activity sets where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of social activity sets
	* @param end the upper bound of the range of social activity sets (not inclusive)
	* @return the range of matching social activity sets
	*/
	public static List<SocialActivitySet> findByUserId(long userId, int start,
		int end) {
		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	* Returns an ordered range of all the social activity sets where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of social activity sets
	* @param end the upper bound of the range of social activity sets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity sets
	*/
	public static List<SocialActivitySet> findByUserId(long userId, int start,
		int end, OrderByComparator<SocialActivitySet> orderByComparator) {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the social activity sets where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of social activity sets
	* @param end the upper bound of the range of social activity sets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activity sets
	*/
	public static List<SocialActivitySet> findByUserId(long userId, int start,
		int end, OrderByComparator<SocialActivitySet> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first social activity set in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity set
	* @throws NoSuchActivitySetException if a matching social activity set could not be found
	*/
	public static SocialActivitySet findByUserId_First(long userId,
		OrderByComparator<SocialActivitySet> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivitySetException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the first social activity set in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity set, or <code>null</code> if a matching social activity set could not be found
	*/
	public static SocialActivitySet fetchByUserId_First(long userId,
		OrderByComparator<SocialActivitySet> orderByComparator) {
		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the last social activity set in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity set
	* @throws NoSuchActivitySetException if a matching social activity set could not be found
	*/
	public static SocialActivitySet findByUserId_Last(long userId,
		OrderByComparator<SocialActivitySet> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivitySetException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the last social activity set in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity set, or <code>null</code> if a matching social activity set could not be found
	*/
	public static SocialActivitySet fetchByUserId_Last(long userId,
		OrderByComparator<SocialActivitySet> orderByComparator) {
		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the social activity sets before and after the current social activity set in the ordered set where userId = &#63;.
	*
	* @param activitySetId the primary key of the current social activity set
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity set
	* @throws NoSuchActivitySetException if a social activity set with the primary key could not be found
	*/
	public static SocialActivitySet[] findByUserId_PrevAndNext(
		long activitySetId, long userId,
		OrderByComparator<SocialActivitySet> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivitySetException {
		return getPersistence()
				   .findByUserId_PrevAndNext(activitySetId, userId,
			orderByComparator);
	}

	/**
	* Removes all the social activity sets where userId = &#63; from the database.
	*
	* @param userId the user ID
	*/
	public static void removeByUserId(long userId) {
		getPersistence().removeByUserId(userId);
	}

	/**
	* Returns the number of social activity sets where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching social activity sets
	*/
	public static int countByUserId(long userId) {
		return getPersistence().countByUserId(userId);
	}

	/**
	* Returns all the social activity sets where groupId = &#63; and userId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param type the type
	* @return the matching social activity sets
	*/
	public static List<SocialActivitySet> findByG_U_T(long groupId,
		long userId, int type) {
		return getPersistence().findByG_U_T(groupId, userId, type);
	}

	/**
	* Returns a range of all the social activity sets where groupId = &#63; and userId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param type the type
	* @param start the lower bound of the range of social activity sets
	* @param end the upper bound of the range of social activity sets (not inclusive)
	* @return the range of matching social activity sets
	*/
	public static List<SocialActivitySet> findByG_U_T(long groupId,
		long userId, int type, int start, int end) {
		return getPersistence().findByG_U_T(groupId, userId, type, start, end);
	}

	/**
	* Returns an ordered range of all the social activity sets where groupId = &#63; and userId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param type the type
	* @param start the lower bound of the range of social activity sets
	* @param end the upper bound of the range of social activity sets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity sets
	*/
	public static List<SocialActivitySet> findByG_U_T(long groupId,
		long userId, int type, int start, int end,
		OrderByComparator<SocialActivitySet> orderByComparator) {
		return getPersistence()
				   .findByG_U_T(groupId, userId, type, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the social activity sets where groupId = &#63; and userId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param type the type
	* @param start the lower bound of the range of social activity sets
	* @param end the upper bound of the range of social activity sets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activity sets
	*/
	public static List<SocialActivitySet> findByG_U_T(long groupId,
		long userId, int type, int start, int end,
		OrderByComparator<SocialActivitySet> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_U_T(groupId, userId, type, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first social activity set in the ordered set where groupId = &#63; and userId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity set
	* @throws NoSuchActivitySetException if a matching social activity set could not be found
	*/
	public static SocialActivitySet findByG_U_T_First(long groupId,
		long userId, int type,
		OrderByComparator<SocialActivitySet> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivitySetException {
		return getPersistence()
				   .findByG_U_T_First(groupId, userId, type, orderByComparator);
	}

	/**
	* Returns the first social activity set in the ordered set where groupId = &#63; and userId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity set, or <code>null</code> if a matching social activity set could not be found
	*/
	public static SocialActivitySet fetchByG_U_T_First(long groupId,
		long userId, int type,
		OrderByComparator<SocialActivitySet> orderByComparator) {
		return getPersistence()
				   .fetchByG_U_T_First(groupId, userId, type, orderByComparator);
	}

	/**
	* Returns the last social activity set in the ordered set where groupId = &#63; and userId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity set
	* @throws NoSuchActivitySetException if a matching social activity set could not be found
	*/
	public static SocialActivitySet findByG_U_T_Last(long groupId, long userId,
		int type, OrderByComparator<SocialActivitySet> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivitySetException {
		return getPersistence()
				   .findByG_U_T_Last(groupId, userId, type, orderByComparator);
	}

	/**
	* Returns the last social activity set in the ordered set where groupId = &#63; and userId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity set, or <code>null</code> if a matching social activity set could not be found
	*/
	public static SocialActivitySet fetchByG_U_T_Last(long groupId,
		long userId, int type,
		OrderByComparator<SocialActivitySet> orderByComparator) {
		return getPersistence()
				   .fetchByG_U_T_Last(groupId, userId, type, orderByComparator);
	}

	/**
	* Returns the social activity sets before and after the current social activity set in the ordered set where groupId = &#63; and userId = &#63; and type = &#63;.
	*
	* @param activitySetId the primary key of the current social activity set
	* @param groupId the group ID
	* @param userId the user ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity set
	* @throws NoSuchActivitySetException if a social activity set with the primary key could not be found
	*/
	public static SocialActivitySet[] findByG_U_T_PrevAndNext(
		long activitySetId, long groupId, long userId, int type,
		OrderByComparator<SocialActivitySet> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivitySetException {
		return getPersistence()
				   .findByG_U_T_PrevAndNext(activitySetId, groupId, userId,
			type, orderByComparator);
	}

	/**
	* Removes all the social activity sets where groupId = &#63; and userId = &#63; and type = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param type the type
	*/
	public static void removeByG_U_T(long groupId, long userId, int type) {
		getPersistence().removeByG_U_T(groupId, userId, type);
	}

	/**
	* Returns the number of social activity sets where groupId = &#63; and userId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param type the type
	* @return the number of matching social activity sets
	*/
	public static int countByG_U_T(long groupId, long userId, int type) {
		return getPersistence().countByG_U_T(groupId, userId, type);
	}

	/**
	* Returns all the social activity sets where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @return the matching social activity sets
	*/
	public static List<SocialActivitySet> findByC_C_T(long classNameId,
		long classPK, int type) {
		return getPersistence().findByC_C_T(classNameId, classPK, type);
	}

	/**
	* Returns a range of all the social activity sets where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param start the lower bound of the range of social activity sets
	* @param end the upper bound of the range of social activity sets (not inclusive)
	* @return the range of matching social activity sets
	*/
	public static List<SocialActivitySet> findByC_C_T(long classNameId,
		long classPK, int type, int start, int end) {
		return getPersistence()
				   .findByC_C_T(classNameId, classPK, type, start, end);
	}

	/**
	* Returns an ordered range of all the social activity sets where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param start the lower bound of the range of social activity sets
	* @param end the upper bound of the range of social activity sets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity sets
	*/
	public static List<SocialActivitySet> findByC_C_T(long classNameId,
		long classPK, int type, int start, int end,
		OrderByComparator<SocialActivitySet> orderByComparator) {
		return getPersistence()
				   .findByC_C_T(classNameId, classPK, type, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the social activity sets where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param start the lower bound of the range of social activity sets
	* @param end the upper bound of the range of social activity sets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activity sets
	*/
	public static List<SocialActivitySet> findByC_C_T(long classNameId,
		long classPK, int type, int start, int end,
		OrderByComparator<SocialActivitySet> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_C_T(classNameId, classPK, type, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first social activity set in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity set
	* @throws NoSuchActivitySetException if a matching social activity set could not be found
	*/
	public static SocialActivitySet findByC_C_T_First(long classNameId,
		long classPK, int type,
		OrderByComparator<SocialActivitySet> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivitySetException {
		return getPersistence()
				   .findByC_C_T_First(classNameId, classPK, type,
			orderByComparator);
	}

	/**
	* Returns the first social activity set in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity set, or <code>null</code> if a matching social activity set could not be found
	*/
	public static SocialActivitySet fetchByC_C_T_First(long classNameId,
		long classPK, int type,
		OrderByComparator<SocialActivitySet> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_T_First(classNameId, classPK, type,
			orderByComparator);
	}

	/**
	* Returns the last social activity set in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity set
	* @throws NoSuchActivitySetException if a matching social activity set could not be found
	*/
	public static SocialActivitySet findByC_C_T_Last(long classNameId,
		long classPK, int type,
		OrderByComparator<SocialActivitySet> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivitySetException {
		return getPersistence()
				   .findByC_C_T_Last(classNameId, classPK, type,
			orderByComparator);
	}

	/**
	* Returns the last social activity set in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity set, or <code>null</code> if a matching social activity set could not be found
	*/
	public static SocialActivitySet fetchByC_C_T_Last(long classNameId,
		long classPK, int type,
		OrderByComparator<SocialActivitySet> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_T_Last(classNameId, classPK, type,
			orderByComparator);
	}

	/**
	* Returns the social activity sets before and after the current social activity set in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param activitySetId the primary key of the current social activity set
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity set
	* @throws NoSuchActivitySetException if a social activity set with the primary key could not be found
	*/
	public static SocialActivitySet[] findByC_C_T_PrevAndNext(
		long activitySetId, long classNameId, long classPK, int type,
		OrderByComparator<SocialActivitySet> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivitySetException {
		return getPersistence()
				   .findByC_C_T_PrevAndNext(activitySetId, classNameId,
			classPK, type, orderByComparator);
	}

	/**
	* Removes all the social activity sets where classNameId = &#63; and classPK = &#63; and type = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	*/
	public static void removeByC_C_T(long classNameId, long classPK, int type) {
		getPersistence().removeByC_C_T(classNameId, classPK, type);
	}

	/**
	* Returns the number of social activity sets where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @return the number of matching social activity sets
	*/
	public static int countByC_C_T(long classNameId, long classPK, int type) {
		return getPersistence().countByC_C_T(classNameId, classPK, type);
	}

	/**
	* Returns all the social activity sets where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param type the type
	* @return the matching social activity sets
	*/
	public static List<SocialActivitySet> findByG_U_C_T(long groupId,
		long userId, long classNameId, int type) {
		return getPersistence().findByG_U_C_T(groupId, userId, classNameId, type);
	}

	/**
	* Returns a range of all the social activity sets where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param type the type
	* @param start the lower bound of the range of social activity sets
	* @param end the upper bound of the range of social activity sets (not inclusive)
	* @return the range of matching social activity sets
	*/
	public static List<SocialActivitySet> findByG_U_C_T(long groupId,
		long userId, long classNameId, int type, int start, int end) {
		return getPersistence()
				   .findByG_U_C_T(groupId, userId, classNameId, type, start, end);
	}

	/**
	* Returns an ordered range of all the social activity sets where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param type the type
	* @param start the lower bound of the range of social activity sets
	* @param end the upper bound of the range of social activity sets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity sets
	*/
	public static List<SocialActivitySet> findByG_U_C_T(long groupId,
		long userId, long classNameId, int type, int start, int end,
		OrderByComparator<SocialActivitySet> orderByComparator) {
		return getPersistence()
				   .findByG_U_C_T(groupId, userId, classNameId, type, start,
			end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the social activity sets where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param type the type
	* @param start the lower bound of the range of social activity sets
	* @param end the upper bound of the range of social activity sets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activity sets
	*/
	public static List<SocialActivitySet> findByG_U_C_T(long groupId,
		long userId, long classNameId, int type, int start, int end,
		OrderByComparator<SocialActivitySet> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_U_C_T(groupId, userId, classNameId, type, start,
			end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first social activity set in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity set
	* @throws NoSuchActivitySetException if a matching social activity set could not be found
	*/
	public static SocialActivitySet findByG_U_C_T_First(long groupId,
		long userId, long classNameId, int type,
		OrderByComparator<SocialActivitySet> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivitySetException {
		return getPersistence()
				   .findByG_U_C_T_First(groupId, userId, classNameId, type,
			orderByComparator);
	}

	/**
	* Returns the first social activity set in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity set, or <code>null</code> if a matching social activity set could not be found
	*/
	public static SocialActivitySet fetchByG_U_C_T_First(long groupId,
		long userId, long classNameId, int type,
		OrderByComparator<SocialActivitySet> orderByComparator) {
		return getPersistence()
				   .fetchByG_U_C_T_First(groupId, userId, classNameId, type,
			orderByComparator);
	}

	/**
	* Returns the last social activity set in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity set
	* @throws NoSuchActivitySetException if a matching social activity set could not be found
	*/
	public static SocialActivitySet findByG_U_C_T_Last(long groupId,
		long userId, long classNameId, int type,
		OrderByComparator<SocialActivitySet> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivitySetException {
		return getPersistence()
				   .findByG_U_C_T_Last(groupId, userId, classNameId, type,
			orderByComparator);
	}

	/**
	* Returns the last social activity set in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity set, or <code>null</code> if a matching social activity set could not be found
	*/
	public static SocialActivitySet fetchByG_U_C_T_Last(long groupId,
		long userId, long classNameId, int type,
		OrderByComparator<SocialActivitySet> orderByComparator) {
		return getPersistence()
				   .fetchByG_U_C_T_Last(groupId, userId, classNameId, type,
			orderByComparator);
	}

	/**
	* Returns the social activity sets before and after the current social activity set in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	*
	* @param activitySetId the primary key of the current social activity set
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity set
	* @throws NoSuchActivitySetException if a social activity set with the primary key could not be found
	*/
	public static SocialActivitySet[] findByG_U_C_T_PrevAndNext(
		long activitySetId, long groupId, long userId, long classNameId,
		int type, OrderByComparator<SocialActivitySet> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivitySetException {
		return getPersistence()
				   .findByG_U_C_T_PrevAndNext(activitySetId, groupId, userId,
			classNameId, type, orderByComparator);
	}

	/**
	* Removes all the social activity sets where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param type the type
	*/
	public static void removeByG_U_C_T(long groupId, long userId,
		long classNameId, int type) {
		getPersistence().removeByG_U_C_T(groupId, userId, classNameId, type);
	}

	/**
	* Returns the number of social activity sets where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param type the type
	* @return the number of matching social activity sets
	*/
	public static int countByG_U_C_T(long groupId, long userId,
		long classNameId, int type) {
		return getPersistence()
				   .countByG_U_C_T(groupId, userId, classNameId, type);
	}

	/**
	* Returns all the social activity sets where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @return the matching social activity sets
	*/
	public static List<SocialActivitySet> findByU_C_C_T(long userId,
		long classNameId, long classPK, int type) {
		return getPersistence().findByU_C_C_T(userId, classNameId, classPK, type);
	}

	/**
	* Returns a range of all the social activity sets where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param start the lower bound of the range of social activity sets
	* @param end the upper bound of the range of social activity sets (not inclusive)
	* @return the range of matching social activity sets
	*/
	public static List<SocialActivitySet> findByU_C_C_T(long userId,
		long classNameId, long classPK, int type, int start, int end) {
		return getPersistence()
				   .findByU_C_C_T(userId, classNameId, classPK, type, start, end);
	}

	/**
	* Returns an ordered range of all the social activity sets where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param start the lower bound of the range of social activity sets
	* @param end the upper bound of the range of social activity sets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity sets
	*/
	public static List<SocialActivitySet> findByU_C_C_T(long userId,
		long classNameId, long classPK, int type, int start, int end,
		OrderByComparator<SocialActivitySet> orderByComparator) {
		return getPersistence()
				   .findByU_C_C_T(userId, classNameId, classPK, type, start,
			end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the social activity sets where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param start the lower bound of the range of social activity sets
	* @param end the upper bound of the range of social activity sets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activity sets
	*/
	public static List<SocialActivitySet> findByU_C_C_T(long userId,
		long classNameId, long classPK, int type, int start, int end,
		OrderByComparator<SocialActivitySet> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByU_C_C_T(userId, classNameId, classPK, type, start,
			end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first social activity set in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity set
	* @throws NoSuchActivitySetException if a matching social activity set could not be found
	*/
	public static SocialActivitySet findByU_C_C_T_First(long userId,
		long classNameId, long classPK, int type,
		OrderByComparator<SocialActivitySet> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivitySetException {
		return getPersistence()
				   .findByU_C_C_T_First(userId, classNameId, classPK, type,
			orderByComparator);
	}

	/**
	* Returns the first social activity set in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity set, or <code>null</code> if a matching social activity set could not be found
	*/
	public static SocialActivitySet fetchByU_C_C_T_First(long userId,
		long classNameId, long classPK, int type,
		OrderByComparator<SocialActivitySet> orderByComparator) {
		return getPersistence()
				   .fetchByU_C_C_T_First(userId, classNameId, classPK, type,
			orderByComparator);
	}

	/**
	* Returns the last social activity set in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity set
	* @throws NoSuchActivitySetException if a matching social activity set could not be found
	*/
	public static SocialActivitySet findByU_C_C_T_Last(long userId,
		long classNameId, long classPK, int type,
		OrderByComparator<SocialActivitySet> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivitySetException {
		return getPersistence()
				   .findByU_C_C_T_Last(userId, classNameId, classPK, type,
			orderByComparator);
	}

	/**
	* Returns the last social activity set in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity set, or <code>null</code> if a matching social activity set could not be found
	*/
	public static SocialActivitySet fetchByU_C_C_T_Last(long userId,
		long classNameId, long classPK, int type,
		OrderByComparator<SocialActivitySet> orderByComparator) {
		return getPersistence()
				   .fetchByU_C_C_T_Last(userId, classNameId, classPK, type,
			orderByComparator);
	}

	/**
	* Returns the social activity sets before and after the current social activity set in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param activitySetId the primary key of the current social activity set
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity set
	* @throws NoSuchActivitySetException if a social activity set with the primary key could not be found
	*/
	public static SocialActivitySet[] findByU_C_C_T_PrevAndNext(
		long activitySetId, long userId, long classNameId, long classPK,
		int type, OrderByComparator<SocialActivitySet> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivitySetException {
		return getPersistence()
				   .findByU_C_C_T_PrevAndNext(activitySetId, userId,
			classNameId, classPK, type, orderByComparator);
	}

	/**
	* Removes all the social activity sets where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; from the database.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	*/
	public static void removeByU_C_C_T(long userId, long classNameId,
		long classPK, int type) {
		getPersistence().removeByU_C_C_T(userId, classNameId, classPK, type);
	}

	/**
	* Returns the number of social activity sets where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @return the number of matching social activity sets
	*/
	public static int countByU_C_C_T(long userId, long classNameId,
		long classPK, int type) {
		return getPersistence()
				   .countByU_C_C_T(userId, classNameId, classPK, type);
	}

	/**
	* Caches the social activity set in the entity cache if it is enabled.
	*
	* @param socialActivitySet the social activity set
	*/
	public static void cacheResult(SocialActivitySet socialActivitySet) {
		getPersistence().cacheResult(socialActivitySet);
	}

	/**
	* Caches the social activity sets in the entity cache if it is enabled.
	*
	* @param socialActivitySets the social activity sets
	*/
	public static void cacheResult(List<SocialActivitySet> socialActivitySets) {
		getPersistence().cacheResult(socialActivitySets);
	}

	/**
	* Creates a new social activity set with the primary key. Does not add the social activity set to the database.
	*
	* @param activitySetId the primary key for the new social activity set
	* @return the new social activity set
	*/
	public static SocialActivitySet create(long activitySetId) {
		return getPersistence().create(activitySetId);
	}

	/**
	* Removes the social activity set with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param activitySetId the primary key of the social activity set
	* @return the social activity set that was removed
	* @throws NoSuchActivitySetException if a social activity set with the primary key could not be found
	*/
	public static SocialActivitySet remove(long activitySetId)
		throws com.liferay.portlet.social.NoSuchActivitySetException {
		return getPersistence().remove(activitySetId);
	}

	public static SocialActivitySet updateImpl(
		SocialActivitySet socialActivitySet) {
		return getPersistence().updateImpl(socialActivitySet);
	}

	/**
	* Returns the social activity set with the primary key or throws a {@link NoSuchActivitySetException} if it could not be found.
	*
	* @param activitySetId the primary key of the social activity set
	* @return the social activity set
	* @throws NoSuchActivitySetException if a social activity set with the primary key could not be found
	*/
	public static SocialActivitySet findByPrimaryKey(long activitySetId)
		throws com.liferay.portlet.social.NoSuchActivitySetException {
		return getPersistence().findByPrimaryKey(activitySetId);
	}

	/**
	* Returns the social activity set with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param activitySetId the primary key of the social activity set
	* @return the social activity set, or <code>null</code> if a social activity set with the primary key could not be found
	*/
	public static SocialActivitySet fetchByPrimaryKey(long activitySetId) {
		return getPersistence().fetchByPrimaryKey(activitySetId);
	}

	public static java.util.Map<java.io.Serializable, SocialActivitySet> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the social activity sets.
	*
	* @return the social activity sets
	*/
	public static List<SocialActivitySet> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the social activity sets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social activity sets
	* @param end the upper bound of the range of social activity sets (not inclusive)
	* @return the range of social activity sets
	*/
	public static List<SocialActivitySet> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the social activity sets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social activity sets
	* @param end the upper bound of the range of social activity sets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of social activity sets
	*/
	public static List<SocialActivitySet> findAll(int start, int end,
		OrderByComparator<SocialActivitySet> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the social activity sets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social activity sets
	* @param end the upper bound of the range of social activity sets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of social activity sets
	*/
	public static List<SocialActivitySet> findAll(int start, int end,
		OrderByComparator<SocialActivitySet> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the social activity sets from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of social activity sets.
	*
	* @return the number of social activity sets
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static SocialActivitySetPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (SocialActivitySetPersistence)PortalBeanLocatorUtil.locate(SocialActivitySetPersistence.class.getName());

			ReferenceRegistry.registerReference(SocialActivitySetUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static SocialActivitySetPersistence _persistence;
}
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

import com.liferay.portlet.social.model.SocialActivity;

import java.util.List;

/**
 * The persistence utility for the social activity service. This utility wraps {@link com.liferay.portlet.social.service.persistence.impl.SocialActivityPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityPersistence
 * @see com.liferay.portlet.social.service.persistence.impl.SocialActivityPersistenceImpl
 * @generated
 */
@ProviderType
public class SocialActivityUtil {
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
	public static void clearCache(SocialActivity socialActivity) {
		getPersistence().clearCache(socialActivity);
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
	public static List<SocialActivity> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SocialActivity> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SocialActivity> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<SocialActivity> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static SocialActivity update(SocialActivity socialActivity) {
		return getPersistence().update(socialActivity);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static SocialActivity update(SocialActivity socialActivity,
		ServiceContext serviceContext) {
		return getPersistence().update(socialActivity, serviceContext);
	}

	/**
	* Returns all the social activities where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching social activities
	*/
	public static List<SocialActivity> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the social activities where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @return the range of matching social activities
	*/
	public static List<SocialActivity> findByGroupId(long groupId, int start,
		int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the social activities where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activities
	*/
	public static List<SocialActivity> findByGroupId(long groupId, int start,
		int end, OrderByComparator<SocialActivity> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the social activities where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activities
	*/
	public static List<SocialActivity> findByGroupId(long groupId, int start,
		int end, OrderByComparator<SocialActivity> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first social activity in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public static SocialActivity findByGroupId_First(long groupId,
		OrderByComparator<SocialActivity> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first social activity in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public static SocialActivity fetchByGroupId_First(long groupId,
		OrderByComparator<SocialActivity> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last social activity in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public static SocialActivity findByGroupId_Last(long groupId,
		OrderByComparator<SocialActivity> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last social activity in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public static SocialActivity fetchByGroupId_Last(long groupId,
		OrderByComparator<SocialActivity> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the social activities before and after the current social activity in the ordered set where groupId = &#63;.
	*
	* @param activityId the primary key of the current social activity
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity
	* @throws NoSuchActivityException if a social activity with the primary key could not be found
	*/
	public static SocialActivity[] findByGroupId_PrevAndNext(long activityId,
		long groupId, OrderByComparator<SocialActivity> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(activityId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the social activities where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of social activities where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching social activities
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the social activities where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching social activities
	*/
	public static List<SocialActivity> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the social activities where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @return the range of matching social activities
	*/
	public static List<SocialActivity> findByCompanyId(long companyId,
		int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the social activities where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activities
	*/
	public static List<SocialActivity> findByCompanyId(long companyId,
		int start, int end, OrderByComparator<SocialActivity> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the social activities where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activities
	*/
	public static List<SocialActivity> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<SocialActivity> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first social activity in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public static SocialActivity findByCompanyId_First(long companyId,
		OrderByComparator<SocialActivity> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first social activity in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public static SocialActivity fetchByCompanyId_First(long companyId,
		OrderByComparator<SocialActivity> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last social activity in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public static SocialActivity findByCompanyId_Last(long companyId,
		OrderByComparator<SocialActivity> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last social activity in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public static SocialActivity fetchByCompanyId_Last(long companyId,
		OrderByComparator<SocialActivity> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the social activities before and after the current social activity in the ordered set where companyId = &#63;.
	*
	* @param activityId the primary key of the current social activity
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity
	* @throws NoSuchActivityException if a social activity with the primary key could not be found
	*/
	public static SocialActivity[] findByCompanyId_PrevAndNext(
		long activityId, long companyId,
		OrderByComparator<SocialActivity> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(activityId, companyId,
			orderByComparator);
	}

	/**
	* Removes all the social activities where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of social activities where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching social activities
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns all the social activities where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching social activities
	*/
	public static List<SocialActivity> findByUserId(long userId) {
		return getPersistence().findByUserId(userId);
	}

	/**
	* Returns a range of all the social activities where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @return the range of matching social activities
	*/
	public static List<SocialActivity> findByUserId(long userId, int start,
		int end) {
		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	* Returns an ordered range of all the social activities where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activities
	*/
	public static List<SocialActivity> findByUserId(long userId, int start,
		int end, OrderByComparator<SocialActivity> orderByComparator) {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the social activities where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activities
	*/
	public static List<SocialActivity> findByUserId(long userId, int start,
		int end, OrderByComparator<SocialActivity> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first social activity in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public static SocialActivity findByUserId_First(long userId,
		OrderByComparator<SocialActivity> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the first social activity in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public static SocialActivity fetchByUserId_First(long userId,
		OrderByComparator<SocialActivity> orderByComparator) {
		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the last social activity in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public static SocialActivity findByUserId_Last(long userId,
		OrderByComparator<SocialActivity> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the last social activity in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public static SocialActivity fetchByUserId_Last(long userId,
		OrderByComparator<SocialActivity> orderByComparator) {
		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the social activities before and after the current social activity in the ordered set where userId = &#63;.
	*
	* @param activityId the primary key of the current social activity
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity
	* @throws NoSuchActivityException if a social activity with the primary key could not be found
	*/
	public static SocialActivity[] findByUserId_PrevAndNext(long activityId,
		long userId, OrderByComparator<SocialActivity> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence()
				   .findByUserId_PrevAndNext(activityId, userId,
			orderByComparator);
	}

	/**
	* Removes all the social activities where userId = &#63; from the database.
	*
	* @param userId the user ID
	*/
	public static void removeByUserId(long userId) {
		getPersistence().removeByUserId(userId);
	}

	/**
	* Returns the number of social activities where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching social activities
	*/
	public static int countByUserId(long userId) {
		return getPersistence().countByUserId(userId);
	}

	/**
	* Returns all the social activities where activitySetId = &#63;.
	*
	* @param activitySetId the activity set ID
	* @return the matching social activities
	*/
	public static List<SocialActivity> findByActivitySetId(long activitySetId) {
		return getPersistence().findByActivitySetId(activitySetId);
	}

	/**
	* Returns a range of all the social activities where activitySetId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param activitySetId the activity set ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @return the range of matching social activities
	*/
	public static List<SocialActivity> findByActivitySetId(long activitySetId,
		int start, int end) {
		return getPersistence().findByActivitySetId(activitySetId, start, end);
	}

	/**
	* Returns an ordered range of all the social activities where activitySetId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param activitySetId the activity set ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activities
	*/
	public static List<SocialActivity> findByActivitySetId(long activitySetId,
		int start, int end, OrderByComparator<SocialActivity> orderByComparator) {
		return getPersistence()
				   .findByActivitySetId(activitySetId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the social activities where activitySetId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param activitySetId the activity set ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activities
	*/
	public static List<SocialActivity> findByActivitySetId(long activitySetId,
		int start, int end,
		OrderByComparator<SocialActivity> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByActivitySetId(activitySetId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first social activity in the ordered set where activitySetId = &#63;.
	*
	* @param activitySetId the activity set ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public static SocialActivity findByActivitySetId_First(long activitySetId,
		OrderByComparator<SocialActivity> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence()
				   .findByActivitySetId_First(activitySetId, orderByComparator);
	}

	/**
	* Returns the first social activity in the ordered set where activitySetId = &#63;.
	*
	* @param activitySetId the activity set ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public static SocialActivity fetchByActivitySetId_First(
		long activitySetId, OrderByComparator<SocialActivity> orderByComparator) {
		return getPersistence()
				   .fetchByActivitySetId_First(activitySetId, orderByComparator);
	}

	/**
	* Returns the last social activity in the ordered set where activitySetId = &#63;.
	*
	* @param activitySetId the activity set ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public static SocialActivity findByActivitySetId_Last(long activitySetId,
		OrderByComparator<SocialActivity> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence()
				   .findByActivitySetId_Last(activitySetId, orderByComparator);
	}

	/**
	* Returns the last social activity in the ordered set where activitySetId = &#63;.
	*
	* @param activitySetId the activity set ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public static SocialActivity fetchByActivitySetId_Last(long activitySetId,
		OrderByComparator<SocialActivity> orderByComparator) {
		return getPersistence()
				   .fetchByActivitySetId_Last(activitySetId, orderByComparator);
	}

	/**
	* Returns the social activities before and after the current social activity in the ordered set where activitySetId = &#63;.
	*
	* @param activityId the primary key of the current social activity
	* @param activitySetId the activity set ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity
	* @throws NoSuchActivityException if a social activity with the primary key could not be found
	*/
	public static SocialActivity[] findByActivitySetId_PrevAndNext(
		long activityId, long activitySetId,
		OrderByComparator<SocialActivity> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence()
				   .findByActivitySetId_PrevAndNext(activityId, activitySetId,
			orderByComparator);
	}

	/**
	* Removes all the social activities where activitySetId = &#63; from the database.
	*
	* @param activitySetId the activity set ID
	*/
	public static void removeByActivitySetId(long activitySetId) {
		getPersistence().removeByActivitySetId(activitySetId);
	}

	/**
	* Returns the number of social activities where activitySetId = &#63;.
	*
	* @param activitySetId the activity set ID
	* @return the number of matching social activities
	*/
	public static int countByActivitySetId(long activitySetId) {
		return getPersistence().countByActivitySetId(activitySetId);
	}

	/**
	* Returns the social activity where mirrorActivityId = &#63; or throws a {@link NoSuchActivityException} if it could not be found.
	*
	* @param mirrorActivityId the mirror activity ID
	* @return the matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public static SocialActivity findByMirrorActivityId(long mirrorActivityId)
		throws com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence().findByMirrorActivityId(mirrorActivityId);
	}

	/**
	* Returns the social activity where mirrorActivityId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param mirrorActivityId the mirror activity ID
	* @return the matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public static SocialActivity fetchByMirrorActivityId(long mirrorActivityId) {
		return getPersistence().fetchByMirrorActivityId(mirrorActivityId);
	}

	/**
	* Returns the social activity where mirrorActivityId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param mirrorActivityId the mirror activity ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public static SocialActivity fetchByMirrorActivityId(
		long mirrorActivityId, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByMirrorActivityId(mirrorActivityId, retrieveFromCache);
	}

	/**
	* Removes the social activity where mirrorActivityId = &#63; from the database.
	*
	* @param mirrorActivityId the mirror activity ID
	* @return the social activity that was removed
	*/
	public static SocialActivity removeByMirrorActivityId(long mirrorActivityId)
		throws com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence().removeByMirrorActivityId(mirrorActivityId);
	}

	/**
	* Returns the number of social activities where mirrorActivityId = &#63;.
	*
	* @param mirrorActivityId the mirror activity ID
	* @return the number of matching social activities
	*/
	public static int countByMirrorActivityId(long mirrorActivityId) {
		return getPersistence().countByMirrorActivityId(mirrorActivityId);
	}

	/**
	* Returns all the social activities where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @return the matching social activities
	*/
	public static List<SocialActivity> findByClassNameId(long classNameId) {
		return getPersistence().findByClassNameId(classNameId);
	}

	/**
	* Returns a range of all the social activities where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @return the range of matching social activities
	*/
	public static List<SocialActivity> findByClassNameId(long classNameId,
		int start, int end) {
		return getPersistence().findByClassNameId(classNameId, start, end);
	}

	/**
	* Returns an ordered range of all the social activities where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activities
	*/
	public static List<SocialActivity> findByClassNameId(long classNameId,
		int start, int end, OrderByComparator<SocialActivity> orderByComparator) {
		return getPersistence()
				   .findByClassNameId(classNameId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the social activities where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activities
	*/
	public static List<SocialActivity> findByClassNameId(long classNameId,
		int start, int end,
		OrderByComparator<SocialActivity> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByClassNameId(classNameId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first social activity in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public static SocialActivity findByClassNameId_First(long classNameId,
		OrderByComparator<SocialActivity> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence()
				   .findByClassNameId_First(classNameId, orderByComparator);
	}

	/**
	* Returns the first social activity in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public static SocialActivity fetchByClassNameId_First(long classNameId,
		OrderByComparator<SocialActivity> orderByComparator) {
		return getPersistence()
				   .fetchByClassNameId_First(classNameId, orderByComparator);
	}

	/**
	* Returns the last social activity in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public static SocialActivity findByClassNameId_Last(long classNameId,
		OrderByComparator<SocialActivity> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence()
				   .findByClassNameId_Last(classNameId, orderByComparator);
	}

	/**
	* Returns the last social activity in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public static SocialActivity fetchByClassNameId_Last(long classNameId,
		OrderByComparator<SocialActivity> orderByComparator) {
		return getPersistence()
				   .fetchByClassNameId_Last(classNameId, orderByComparator);
	}

	/**
	* Returns the social activities before and after the current social activity in the ordered set where classNameId = &#63;.
	*
	* @param activityId the primary key of the current social activity
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity
	* @throws NoSuchActivityException if a social activity with the primary key could not be found
	*/
	public static SocialActivity[] findByClassNameId_PrevAndNext(
		long activityId, long classNameId,
		OrderByComparator<SocialActivity> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence()
				   .findByClassNameId_PrevAndNext(activityId, classNameId,
			orderByComparator);
	}

	/**
	* Removes all the social activities where classNameId = &#63; from the database.
	*
	* @param classNameId the class name ID
	*/
	public static void removeByClassNameId(long classNameId) {
		getPersistence().removeByClassNameId(classNameId);
	}

	/**
	* Returns the number of social activities where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @return the number of matching social activities
	*/
	public static int countByClassNameId(long classNameId) {
		return getPersistence().countByClassNameId(classNameId);
	}

	/**
	* Returns all the social activities where receiverUserId = &#63;.
	*
	* @param receiverUserId the receiver user ID
	* @return the matching social activities
	*/
	public static List<SocialActivity> findByReceiverUserId(long receiverUserId) {
		return getPersistence().findByReceiverUserId(receiverUserId);
	}

	/**
	* Returns a range of all the social activities where receiverUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param receiverUserId the receiver user ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @return the range of matching social activities
	*/
	public static List<SocialActivity> findByReceiverUserId(
		long receiverUserId, int start, int end) {
		return getPersistence().findByReceiverUserId(receiverUserId, start, end);
	}

	/**
	* Returns an ordered range of all the social activities where receiverUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param receiverUserId the receiver user ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activities
	*/
	public static List<SocialActivity> findByReceiverUserId(
		long receiverUserId, int start, int end,
		OrderByComparator<SocialActivity> orderByComparator) {
		return getPersistence()
				   .findByReceiverUserId(receiverUserId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the social activities where receiverUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param receiverUserId the receiver user ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activities
	*/
	public static List<SocialActivity> findByReceiverUserId(
		long receiverUserId, int start, int end,
		OrderByComparator<SocialActivity> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByReceiverUserId(receiverUserId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first social activity in the ordered set where receiverUserId = &#63;.
	*
	* @param receiverUserId the receiver user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public static SocialActivity findByReceiverUserId_First(
		long receiverUserId, OrderByComparator<SocialActivity> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence()
				   .findByReceiverUserId_First(receiverUserId, orderByComparator);
	}

	/**
	* Returns the first social activity in the ordered set where receiverUserId = &#63;.
	*
	* @param receiverUserId the receiver user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public static SocialActivity fetchByReceiverUserId_First(
		long receiverUserId, OrderByComparator<SocialActivity> orderByComparator) {
		return getPersistence()
				   .fetchByReceiverUserId_First(receiverUserId,
			orderByComparator);
	}

	/**
	* Returns the last social activity in the ordered set where receiverUserId = &#63;.
	*
	* @param receiverUserId the receiver user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public static SocialActivity findByReceiverUserId_Last(
		long receiverUserId, OrderByComparator<SocialActivity> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence()
				   .findByReceiverUserId_Last(receiverUserId, orderByComparator);
	}

	/**
	* Returns the last social activity in the ordered set where receiverUserId = &#63;.
	*
	* @param receiverUserId the receiver user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public static SocialActivity fetchByReceiverUserId_Last(
		long receiverUserId, OrderByComparator<SocialActivity> orderByComparator) {
		return getPersistence()
				   .fetchByReceiverUserId_Last(receiverUserId, orderByComparator);
	}

	/**
	* Returns the social activities before and after the current social activity in the ordered set where receiverUserId = &#63;.
	*
	* @param activityId the primary key of the current social activity
	* @param receiverUserId the receiver user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity
	* @throws NoSuchActivityException if a social activity with the primary key could not be found
	*/
	public static SocialActivity[] findByReceiverUserId_PrevAndNext(
		long activityId, long receiverUserId,
		OrderByComparator<SocialActivity> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence()
				   .findByReceiverUserId_PrevAndNext(activityId,
			receiverUserId, orderByComparator);
	}

	/**
	* Removes all the social activities where receiverUserId = &#63; from the database.
	*
	* @param receiverUserId the receiver user ID
	*/
	public static void removeByReceiverUserId(long receiverUserId) {
		getPersistence().removeByReceiverUserId(receiverUserId);
	}

	/**
	* Returns the number of social activities where receiverUserId = &#63;.
	*
	* @param receiverUserId the receiver user ID
	* @return the number of matching social activities
	*/
	public static int countByReceiverUserId(long receiverUserId) {
		return getPersistence().countByReceiverUserId(receiverUserId);
	}

	/**
	* Returns all the social activities where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching social activities
	*/
	public static List<SocialActivity> findByC_C(long classNameId, long classPK) {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	* Returns a range of all the social activities where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @return the range of matching social activities
	*/
	public static List<SocialActivity> findByC_C(long classNameId,
		long classPK, int start, int end) {
		return getPersistence().findByC_C(classNameId, classPK, start, end);
	}

	/**
	* Returns an ordered range of all the social activities where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activities
	*/
	public static List<SocialActivity> findByC_C(long classNameId,
		long classPK, int start, int end,
		OrderByComparator<SocialActivity> orderByComparator) {
		return getPersistence()
				   .findByC_C(classNameId, classPK, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the social activities where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activities
	*/
	public static List<SocialActivity> findByC_C(long classNameId,
		long classPK, int start, int end,
		OrderByComparator<SocialActivity> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_C(classNameId, classPK, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first social activity in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public static SocialActivity findByC_C_First(long classNameId,
		long classPK, OrderByComparator<SocialActivity> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence()
				   .findByC_C_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the first social activity in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public static SocialActivity fetchByC_C_First(long classNameId,
		long classPK, OrderByComparator<SocialActivity> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last social activity in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public static SocialActivity findByC_C_Last(long classNameId, long classPK,
		OrderByComparator<SocialActivity> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence()
				   .findByC_C_Last(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last social activity in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public static SocialActivity fetchByC_C_Last(long classNameId,
		long classPK, OrderByComparator<SocialActivity> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_Last(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the social activities before and after the current social activity in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param activityId the primary key of the current social activity
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity
	* @throws NoSuchActivityException if a social activity with the primary key could not be found
	*/
	public static SocialActivity[] findByC_C_PrevAndNext(long activityId,
		long classNameId, long classPK,
		OrderByComparator<SocialActivity> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence()
				   .findByC_C_PrevAndNext(activityId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Removes all the social activities where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	*/
	public static void removeByC_C(long classNameId, long classPK) {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	* Returns the number of social activities where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching social activities
	*/
	public static int countByC_C(long classNameId, long classPK) {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	* Returns all the social activities where mirrorActivityId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param mirrorActivityId the mirror activity ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching social activities
	*/
	public static List<SocialActivity> findByM_C_C(long mirrorActivityId,
		long classNameId, long classPK) {
		return getPersistence()
				   .findByM_C_C(mirrorActivityId, classNameId, classPK);
	}

	/**
	* Returns a range of all the social activities where mirrorActivityId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param mirrorActivityId the mirror activity ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @return the range of matching social activities
	*/
	public static List<SocialActivity> findByM_C_C(long mirrorActivityId,
		long classNameId, long classPK, int start, int end) {
		return getPersistence()
				   .findByM_C_C(mirrorActivityId, classNameId, classPK, start,
			end);
	}

	/**
	* Returns an ordered range of all the social activities where mirrorActivityId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param mirrorActivityId the mirror activity ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activities
	*/
	public static List<SocialActivity> findByM_C_C(long mirrorActivityId,
		long classNameId, long classPK, int start, int end,
		OrderByComparator<SocialActivity> orderByComparator) {
		return getPersistence()
				   .findByM_C_C(mirrorActivityId, classNameId, classPK, start,
			end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the social activities where mirrorActivityId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param mirrorActivityId the mirror activity ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activities
	*/
	public static List<SocialActivity> findByM_C_C(long mirrorActivityId,
		long classNameId, long classPK, int start, int end,
		OrderByComparator<SocialActivity> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByM_C_C(mirrorActivityId, classNameId, classPK, start,
			end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first social activity in the ordered set where mirrorActivityId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param mirrorActivityId the mirror activity ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public static SocialActivity findByM_C_C_First(long mirrorActivityId,
		long classNameId, long classPK,
		OrderByComparator<SocialActivity> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence()
				   .findByM_C_C_First(mirrorActivityId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns the first social activity in the ordered set where mirrorActivityId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param mirrorActivityId the mirror activity ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public static SocialActivity fetchByM_C_C_First(long mirrorActivityId,
		long classNameId, long classPK,
		OrderByComparator<SocialActivity> orderByComparator) {
		return getPersistence()
				   .fetchByM_C_C_First(mirrorActivityId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns the last social activity in the ordered set where mirrorActivityId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param mirrorActivityId the mirror activity ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public static SocialActivity findByM_C_C_Last(long mirrorActivityId,
		long classNameId, long classPK,
		OrderByComparator<SocialActivity> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence()
				   .findByM_C_C_Last(mirrorActivityId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns the last social activity in the ordered set where mirrorActivityId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param mirrorActivityId the mirror activity ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public static SocialActivity fetchByM_C_C_Last(long mirrorActivityId,
		long classNameId, long classPK,
		OrderByComparator<SocialActivity> orderByComparator) {
		return getPersistence()
				   .fetchByM_C_C_Last(mirrorActivityId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns the social activities before and after the current social activity in the ordered set where mirrorActivityId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param activityId the primary key of the current social activity
	* @param mirrorActivityId the mirror activity ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity
	* @throws NoSuchActivityException if a social activity with the primary key could not be found
	*/
	public static SocialActivity[] findByM_C_C_PrevAndNext(long activityId,
		long mirrorActivityId, long classNameId, long classPK,
		OrderByComparator<SocialActivity> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence()
				   .findByM_C_C_PrevAndNext(activityId, mirrorActivityId,
			classNameId, classPK, orderByComparator);
	}

	/**
	* Removes all the social activities where mirrorActivityId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param mirrorActivityId the mirror activity ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	*/
	public static void removeByM_C_C(long mirrorActivityId, long classNameId,
		long classPK) {
		getPersistence().removeByM_C_C(mirrorActivityId, classNameId, classPK);
	}

	/**
	* Returns the number of social activities where mirrorActivityId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param mirrorActivityId the mirror activity ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching social activities
	*/
	public static int countByM_C_C(long mirrorActivityId, long classNameId,
		long classPK) {
		return getPersistence()
				   .countByM_C_C(mirrorActivityId, classNameId, classPK);
	}

	/**
	* Returns all the social activities where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @return the matching social activities
	*/
	public static List<SocialActivity> findByC_C_T(long classNameId,
		long classPK, int type) {
		return getPersistence().findByC_C_T(classNameId, classPK, type);
	}

	/**
	* Returns a range of all the social activities where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @return the range of matching social activities
	*/
	public static List<SocialActivity> findByC_C_T(long classNameId,
		long classPK, int type, int start, int end) {
		return getPersistence()
				   .findByC_C_T(classNameId, classPK, type, start, end);
	}

	/**
	* Returns an ordered range of all the social activities where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activities
	*/
	public static List<SocialActivity> findByC_C_T(long classNameId,
		long classPK, int type, int start, int end,
		OrderByComparator<SocialActivity> orderByComparator) {
		return getPersistence()
				   .findByC_C_T(classNameId, classPK, type, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the social activities where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activities
	*/
	public static List<SocialActivity> findByC_C_T(long classNameId,
		long classPK, int type, int start, int end,
		OrderByComparator<SocialActivity> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_C_T(classNameId, classPK, type, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first social activity in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public static SocialActivity findByC_C_T_First(long classNameId,
		long classPK, int type,
		OrderByComparator<SocialActivity> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence()
				   .findByC_C_T_First(classNameId, classPK, type,
			orderByComparator);
	}

	/**
	* Returns the first social activity in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public static SocialActivity fetchByC_C_T_First(long classNameId,
		long classPK, int type,
		OrderByComparator<SocialActivity> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_T_First(classNameId, classPK, type,
			orderByComparator);
	}

	/**
	* Returns the last social activity in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public static SocialActivity findByC_C_T_Last(long classNameId,
		long classPK, int type,
		OrderByComparator<SocialActivity> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence()
				   .findByC_C_T_Last(classNameId, classPK, type,
			orderByComparator);
	}

	/**
	* Returns the last social activity in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public static SocialActivity fetchByC_C_T_Last(long classNameId,
		long classPK, int type,
		OrderByComparator<SocialActivity> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_T_Last(classNameId, classPK, type,
			orderByComparator);
	}

	/**
	* Returns the social activities before and after the current social activity in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param activityId the primary key of the current social activity
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity
	* @throws NoSuchActivityException if a social activity with the primary key could not be found
	*/
	public static SocialActivity[] findByC_C_T_PrevAndNext(long activityId,
		long classNameId, long classPK, int type,
		OrderByComparator<SocialActivity> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence()
				   .findByC_C_T_PrevAndNext(activityId, classNameId, classPK,
			type, orderByComparator);
	}

	/**
	* Removes all the social activities where classNameId = &#63; and classPK = &#63; and type = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	*/
	public static void removeByC_C_T(long classNameId, long classPK, int type) {
		getPersistence().removeByC_C_T(classNameId, classPK, type);
	}

	/**
	* Returns the number of social activities where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @return the number of matching social activities
	*/
	public static int countByC_C_T(long classNameId, long classPK, int type) {
		return getPersistence().countByC_C_T(classNameId, classPK, type);
	}

	/**
	* Returns all the social activities where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @return the matching social activities
	*/
	public static List<SocialActivity> findByG_U_C_C_T_R(long groupId,
		long userId, long classNameId, long classPK, int type,
		long receiverUserId) {
		return getPersistence()
				   .findByG_U_C_C_T_R(groupId, userId, classNameId, classPK,
			type, receiverUserId);
	}

	/**
	* Returns a range of all the social activities where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @return the range of matching social activities
	*/
	public static List<SocialActivity> findByG_U_C_C_T_R(long groupId,
		long userId, long classNameId, long classPK, int type,
		long receiverUserId, int start, int end) {
		return getPersistence()
				   .findByG_U_C_C_T_R(groupId, userId, classNameId, classPK,
			type, receiverUserId, start, end);
	}

	/**
	* Returns an ordered range of all the social activities where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activities
	*/
	public static List<SocialActivity> findByG_U_C_C_T_R(long groupId,
		long userId, long classNameId, long classPK, int type,
		long receiverUserId, int start, int end,
		OrderByComparator<SocialActivity> orderByComparator) {
		return getPersistence()
				   .findByG_U_C_C_T_R(groupId, userId, classNameId, classPK,
			type, receiverUserId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the social activities where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activities
	*/
	public static List<SocialActivity> findByG_U_C_C_T_R(long groupId,
		long userId, long classNameId, long classPK, int type,
		long receiverUserId, int start, int end,
		OrderByComparator<SocialActivity> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_U_C_C_T_R(groupId, userId, classNameId, classPK,
			type, receiverUserId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first social activity in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public static SocialActivity findByG_U_C_C_T_R_First(long groupId,
		long userId, long classNameId, long classPK, int type,
		long receiverUserId, OrderByComparator<SocialActivity> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence()
				   .findByG_U_C_C_T_R_First(groupId, userId, classNameId,
			classPK, type, receiverUserId, orderByComparator);
	}

	/**
	* Returns the first social activity in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public static SocialActivity fetchByG_U_C_C_T_R_First(long groupId,
		long userId, long classNameId, long classPK, int type,
		long receiverUserId, OrderByComparator<SocialActivity> orderByComparator) {
		return getPersistence()
				   .fetchByG_U_C_C_T_R_First(groupId, userId, classNameId,
			classPK, type, receiverUserId, orderByComparator);
	}

	/**
	* Returns the last social activity in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public static SocialActivity findByG_U_C_C_T_R_Last(long groupId,
		long userId, long classNameId, long classPK, int type,
		long receiverUserId, OrderByComparator<SocialActivity> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence()
				   .findByG_U_C_C_T_R_Last(groupId, userId, classNameId,
			classPK, type, receiverUserId, orderByComparator);
	}

	/**
	* Returns the last social activity in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public static SocialActivity fetchByG_U_C_C_T_R_Last(long groupId,
		long userId, long classNameId, long classPK, int type,
		long receiverUserId, OrderByComparator<SocialActivity> orderByComparator) {
		return getPersistence()
				   .fetchByG_U_C_C_T_R_Last(groupId, userId, classNameId,
			classPK, type, receiverUserId, orderByComparator);
	}

	/**
	* Returns the social activities before and after the current social activity in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63;.
	*
	* @param activityId the primary key of the current social activity
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity
	* @throws NoSuchActivityException if a social activity with the primary key could not be found
	*/
	public static SocialActivity[] findByG_U_C_C_T_R_PrevAndNext(
		long activityId, long groupId, long userId, long classNameId,
		long classPK, int type, long receiverUserId,
		OrderByComparator<SocialActivity> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence()
				   .findByG_U_C_C_T_R_PrevAndNext(activityId, groupId, userId,
			classNameId, classPK, type, receiverUserId, orderByComparator);
	}

	/**
	* Removes all the social activities where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	*/
	public static void removeByG_U_C_C_T_R(long groupId, long userId,
		long classNameId, long classPK, int type, long receiverUserId) {
		getPersistence()
			.removeByG_U_C_C_T_R(groupId, userId, classNameId, classPK, type,
			receiverUserId);
	}

	/**
	* Returns the number of social activities where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @return the number of matching social activities
	*/
	public static int countByG_U_C_C_T_R(long groupId, long userId,
		long classNameId, long classPK, int type, long receiverUserId) {
		return getPersistence()
				   .countByG_U_C_C_T_R(groupId, userId, classNameId, classPK,
			type, receiverUserId);
	}

	/**
	* Returns the social activity where groupId = &#63; and userId = &#63; and createDate = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; or throws a {@link NoSuchActivityException} if it could not be found.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param createDate the create date
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @return the matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public static SocialActivity findByG_U_CD_C_C_T_R(long groupId,
		long userId, long createDate, long classNameId, long classPK, int type,
		long receiverUserId)
		throws com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence()
				   .findByG_U_CD_C_C_T_R(groupId, userId, createDate,
			classNameId, classPK, type, receiverUserId);
	}

	/**
	* Returns the social activity where groupId = &#63; and userId = &#63; and createDate = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param createDate the create date
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @return the matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public static SocialActivity fetchByG_U_CD_C_C_T_R(long groupId,
		long userId, long createDate, long classNameId, long classPK, int type,
		long receiverUserId) {
		return getPersistence()
				   .fetchByG_U_CD_C_C_T_R(groupId, userId, createDate,
			classNameId, classPK, type, receiverUserId);
	}

	/**
	* Returns the social activity where groupId = &#63; and userId = &#63; and createDate = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param createDate the create date
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public static SocialActivity fetchByG_U_CD_C_C_T_R(long groupId,
		long userId, long createDate, long classNameId, long classPK, int type,
		long receiverUserId, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_U_CD_C_C_T_R(groupId, userId, createDate,
			classNameId, classPK, type, receiverUserId, retrieveFromCache);
	}

	/**
	* Removes the social activity where groupId = &#63; and userId = &#63; and createDate = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param createDate the create date
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @return the social activity that was removed
	*/
	public static SocialActivity removeByG_U_CD_C_C_T_R(long groupId,
		long userId, long createDate, long classNameId, long classPK, int type,
		long receiverUserId)
		throws com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence()
				   .removeByG_U_CD_C_C_T_R(groupId, userId, createDate,
			classNameId, classPK, type, receiverUserId);
	}

	/**
	* Returns the number of social activities where groupId = &#63; and userId = &#63; and createDate = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param createDate the create date
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @return the number of matching social activities
	*/
	public static int countByG_U_CD_C_C_T_R(long groupId, long userId,
		long createDate, long classNameId, long classPK, int type,
		long receiverUserId) {
		return getPersistence()
				   .countByG_U_CD_C_C_T_R(groupId, userId, createDate,
			classNameId, classPK, type, receiverUserId);
	}

	/**
	* Caches the social activity in the entity cache if it is enabled.
	*
	* @param socialActivity the social activity
	*/
	public static void cacheResult(SocialActivity socialActivity) {
		getPersistence().cacheResult(socialActivity);
	}

	/**
	* Caches the social activities in the entity cache if it is enabled.
	*
	* @param socialActivities the social activities
	*/
	public static void cacheResult(List<SocialActivity> socialActivities) {
		getPersistence().cacheResult(socialActivities);
	}

	/**
	* Creates a new social activity with the primary key. Does not add the social activity to the database.
	*
	* @param activityId the primary key for the new social activity
	* @return the new social activity
	*/
	public static SocialActivity create(long activityId) {
		return getPersistence().create(activityId);
	}

	/**
	* Removes the social activity with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param activityId the primary key of the social activity
	* @return the social activity that was removed
	* @throws NoSuchActivityException if a social activity with the primary key could not be found
	*/
	public static SocialActivity remove(long activityId)
		throws com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence().remove(activityId);
	}

	public static SocialActivity updateImpl(SocialActivity socialActivity) {
		return getPersistence().updateImpl(socialActivity);
	}

	/**
	* Returns the social activity with the primary key or throws a {@link NoSuchActivityException} if it could not be found.
	*
	* @param activityId the primary key of the social activity
	* @return the social activity
	* @throws NoSuchActivityException if a social activity with the primary key could not be found
	*/
	public static SocialActivity findByPrimaryKey(long activityId)
		throws com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence().findByPrimaryKey(activityId);
	}

	/**
	* Returns the social activity with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param activityId the primary key of the social activity
	* @return the social activity, or <code>null</code> if a social activity with the primary key could not be found
	*/
	public static SocialActivity fetchByPrimaryKey(long activityId) {
		return getPersistence().fetchByPrimaryKey(activityId);
	}

	public static java.util.Map<java.io.Serializable, SocialActivity> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the social activities.
	*
	* @return the social activities
	*/
	public static List<SocialActivity> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the social activities.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @return the range of social activities
	*/
	public static List<SocialActivity> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the social activities.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of social activities
	*/
	public static List<SocialActivity> findAll(int start, int end,
		OrderByComparator<SocialActivity> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the social activities.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of social activities
	*/
	public static List<SocialActivity> findAll(int start, int end,
		OrderByComparator<SocialActivity> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the social activities from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of social activities.
	*
	* @return the number of social activities
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static SocialActivityPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (SocialActivityPersistence)PortalBeanLocatorUtil.locate(SocialActivityPersistence.class.getName());

			ReferenceRegistry.registerReference(SocialActivityUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static SocialActivityPersistence _persistence;
}
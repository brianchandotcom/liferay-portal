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

import com.liferay.portlet.social.model.SocialActivitySetting;

import java.util.List;

/**
 * The persistence utility for the social activity setting service. This utility wraps {@link com.liferay.portlet.social.service.persistence.impl.SocialActivitySettingPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivitySettingPersistence
 * @see com.liferay.portlet.social.service.persistence.impl.SocialActivitySettingPersistenceImpl
 * @generated
 */
@ProviderType
public class SocialActivitySettingUtil {
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
	public static void clearCache(SocialActivitySetting socialActivitySetting) {
		getPersistence().clearCache(socialActivitySetting);
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
	public static List<SocialActivitySetting> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SocialActivitySetting> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SocialActivitySetting> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<SocialActivitySetting> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static SocialActivitySetting update(
		SocialActivitySetting socialActivitySetting) {
		return getPersistence().update(socialActivitySetting);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static SocialActivitySetting update(
		SocialActivitySetting socialActivitySetting,
		ServiceContext serviceContext) {
		return getPersistence().update(socialActivitySetting, serviceContext);
	}

	/**
	* Returns all the social activity settings where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching social activity settings
	*/
	public static List<SocialActivitySetting> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the social activity settings where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of social activity settings
	* @param end the upper bound of the range of social activity settings (not inclusive)
	* @return the range of matching social activity settings
	*/
	public static List<SocialActivitySetting> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the social activity settings where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of social activity settings
	* @param end the upper bound of the range of social activity settings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity settings
	*/
	public static List<SocialActivitySetting> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<SocialActivitySetting> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the social activity settings where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of social activity settings
	* @param end the upper bound of the range of social activity settings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activity settings
	*/
	public static List<SocialActivitySetting> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<SocialActivitySetting> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first social activity setting in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity setting
	* @throws NoSuchActivitySettingException if a matching social activity setting could not be found
	*/
	public static SocialActivitySetting findByGroupId_First(long groupId,
		OrderByComparator<SocialActivitySetting> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivitySettingException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first social activity setting in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity setting, or <code>null</code> if a matching social activity setting could not be found
	*/
	public static SocialActivitySetting fetchByGroupId_First(long groupId,
		OrderByComparator<SocialActivitySetting> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last social activity setting in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity setting
	* @throws NoSuchActivitySettingException if a matching social activity setting could not be found
	*/
	public static SocialActivitySetting findByGroupId_Last(long groupId,
		OrderByComparator<SocialActivitySetting> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivitySettingException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last social activity setting in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity setting, or <code>null</code> if a matching social activity setting could not be found
	*/
	public static SocialActivitySetting fetchByGroupId_Last(long groupId,
		OrderByComparator<SocialActivitySetting> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the social activity settings before and after the current social activity setting in the ordered set where groupId = &#63;.
	*
	* @param activitySettingId the primary key of the current social activity setting
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity setting
	* @throws NoSuchActivitySettingException if a social activity setting with the primary key could not be found
	*/
	public static SocialActivitySetting[] findByGroupId_PrevAndNext(
		long activitySettingId, long groupId,
		OrderByComparator<SocialActivitySetting> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivitySettingException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(activitySettingId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the social activity settings where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of social activity settings where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching social activity settings
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the social activity settings where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the matching social activity settings
	*/
	public static List<SocialActivitySetting> findByG_C(long groupId,
		long classNameId) {
		return getPersistence().findByG_C(groupId, classNameId);
	}

	/**
	* Returns a range of all the social activity settings where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of social activity settings
	* @param end the upper bound of the range of social activity settings (not inclusive)
	* @return the range of matching social activity settings
	*/
	public static List<SocialActivitySetting> findByG_C(long groupId,
		long classNameId, int start, int end) {
		return getPersistence().findByG_C(groupId, classNameId, start, end);
	}

	/**
	* Returns an ordered range of all the social activity settings where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of social activity settings
	* @param end the upper bound of the range of social activity settings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity settings
	*/
	public static List<SocialActivitySetting> findByG_C(long groupId,
		long classNameId, int start, int end,
		OrderByComparator<SocialActivitySetting> orderByComparator) {
		return getPersistence()
				   .findByG_C(groupId, classNameId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the social activity settings where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of social activity settings
	* @param end the upper bound of the range of social activity settings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activity settings
	*/
	public static List<SocialActivitySetting> findByG_C(long groupId,
		long classNameId, int start, int end,
		OrderByComparator<SocialActivitySetting> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_C(groupId, classNameId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first social activity setting in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity setting
	* @throws NoSuchActivitySettingException if a matching social activity setting could not be found
	*/
	public static SocialActivitySetting findByG_C_First(long groupId,
		long classNameId,
		OrderByComparator<SocialActivitySetting> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivitySettingException {
		return getPersistence()
				   .findByG_C_First(groupId, classNameId, orderByComparator);
	}

	/**
	* Returns the first social activity setting in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity setting, or <code>null</code> if a matching social activity setting could not be found
	*/
	public static SocialActivitySetting fetchByG_C_First(long groupId,
		long classNameId,
		OrderByComparator<SocialActivitySetting> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_First(groupId, classNameId, orderByComparator);
	}

	/**
	* Returns the last social activity setting in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity setting
	* @throws NoSuchActivitySettingException if a matching social activity setting could not be found
	*/
	public static SocialActivitySetting findByG_C_Last(long groupId,
		long classNameId,
		OrderByComparator<SocialActivitySetting> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivitySettingException {
		return getPersistence()
				   .findByG_C_Last(groupId, classNameId, orderByComparator);
	}

	/**
	* Returns the last social activity setting in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity setting, or <code>null</code> if a matching social activity setting could not be found
	*/
	public static SocialActivitySetting fetchByG_C_Last(long groupId,
		long classNameId,
		OrderByComparator<SocialActivitySetting> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_Last(groupId, classNameId, orderByComparator);
	}

	/**
	* Returns the social activity settings before and after the current social activity setting in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param activitySettingId the primary key of the current social activity setting
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity setting
	* @throws NoSuchActivitySettingException if a social activity setting with the primary key could not be found
	*/
	public static SocialActivitySetting[] findByG_C_PrevAndNext(
		long activitySettingId, long groupId, long classNameId,
		OrderByComparator<SocialActivitySetting> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivitySettingException {
		return getPersistence()
				   .findByG_C_PrevAndNext(activitySettingId, groupId,
			classNameId, orderByComparator);
	}

	/**
	* Removes all the social activity settings where groupId = &#63; and classNameId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	*/
	public static void removeByG_C(long groupId, long classNameId) {
		getPersistence().removeByG_C(groupId, classNameId);
	}

	/**
	* Returns the number of social activity settings where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the number of matching social activity settings
	*/
	public static int countByG_C(long groupId, long classNameId) {
		return getPersistence().countByG_C(groupId, classNameId);
	}

	/**
	* Returns all the social activity settings where groupId = &#63; and activityType = &#63;.
	*
	* @param groupId the group ID
	* @param activityType the activity type
	* @return the matching social activity settings
	*/
	public static List<SocialActivitySetting> findByG_A(long groupId,
		int activityType) {
		return getPersistence().findByG_A(groupId, activityType);
	}

	/**
	* Returns a range of all the social activity settings where groupId = &#63; and activityType = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param activityType the activity type
	* @param start the lower bound of the range of social activity settings
	* @param end the upper bound of the range of social activity settings (not inclusive)
	* @return the range of matching social activity settings
	*/
	public static List<SocialActivitySetting> findByG_A(long groupId,
		int activityType, int start, int end) {
		return getPersistence().findByG_A(groupId, activityType, start, end);
	}

	/**
	* Returns an ordered range of all the social activity settings where groupId = &#63; and activityType = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param activityType the activity type
	* @param start the lower bound of the range of social activity settings
	* @param end the upper bound of the range of social activity settings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity settings
	*/
	public static List<SocialActivitySetting> findByG_A(long groupId,
		int activityType, int start, int end,
		OrderByComparator<SocialActivitySetting> orderByComparator) {
		return getPersistence()
				   .findByG_A(groupId, activityType, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the social activity settings where groupId = &#63; and activityType = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param activityType the activity type
	* @param start the lower bound of the range of social activity settings
	* @param end the upper bound of the range of social activity settings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activity settings
	*/
	public static List<SocialActivitySetting> findByG_A(long groupId,
		int activityType, int start, int end,
		OrderByComparator<SocialActivitySetting> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_A(groupId, activityType, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first social activity setting in the ordered set where groupId = &#63; and activityType = &#63;.
	*
	* @param groupId the group ID
	* @param activityType the activity type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity setting
	* @throws NoSuchActivitySettingException if a matching social activity setting could not be found
	*/
	public static SocialActivitySetting findByG_A_First(long groupId,
		int activityType,
		OrderByComparator<SocialActivitySetting> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivitySettingException {
		return getPersistence()
				   .findByG_A_First(groupId, activityType, orderByComparator);
	}

	/**
	* Returns the first social activity setting in the ordered set where groupId = &#63; and activityType = &#63;.
	*
	* @param groupId the group ID
	* @param activityType the activity type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity setting, or <code>null</code> if a matching social activity setting could not be found
	*/
	public static SocialActivitySetting fetchByG_A_First(long groupId,
		int activityType,
		OrderByComparator<SocialActivitySetting> orderByComparator) {
		return getPersistence()
				   .fetchByG_A_First(groupId, activityType, orderByComparator);
	}

	/**
	* Returns the last social activity setting in the ordered set where groupId = &#63; and activityType = &#63;.
	*
	* @param groupId the group ID
	* @param activityType the activity type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity setting
	* @throws NoSuchActivitySettingException if a matching social activity setting could not be found
	*/
	public static SocialActivitySetting findByG_A_Last(long groupId,
		int activityType,
		OrderByComparator<SocialActivitySetting> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivitySettingException {
		return getPersistence()
				   .findByG_A_Last(groupId, activityType, orderByComparator);
	}

	/**
	* Returns the last social activity setting in the ordered set where groupId = &#63; and activityType = &#63;.
	*
	* @param groupId the group ID
	* @param activityType the activity type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity setting, or <code>null</code> if a matching social activity setting could not be found
	*/
	public static SocialActivitySetting fetchByG_A_Last(long groupId,
		int activityType,
		OrderByComparator<SocialActivitySetting> orderByComparator) {
		return getPersistence()
				   .fetchByG_A_Last(groupId, activityType, orderByComparator);
	}

	/**
	* Returns the social activity settings before and after the current social activity setting in the ordered set where groupId = &#63; and activityType = &#63;.
	*
	* @param activitySettingId the primary key of the current social activity setting
	* @param groupId the group ID
	* @param activityType the activity type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity setting
	* @throws NoSuchActivitySettingException if a social activity setting with the primary key could not be found
	*/
	public static SocialActivitySetting[] findByG_A_PrevAndNext(
		long activitySettingId, long groupId, int activityType,
		OrderByComparator<SocialActivitySetting> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivitySettingException {
		return getPersistence()
				   .findByG_A_PrevAndNext(activitySettingId, groupId,
			activityType, orderByComparator);
	}

	/**
	* Removes all the social activity settings where groupId = &#63; and activityType = &#63; from the database.
	*
	* @param groupId the group ID
	* @param activityType the activity type
	*/
	public static void removeByG_A(long groupId, int activityType) {
		getPersistence().removeByG_A(groupId, activityType);
	}

	/**
	* Returns the number of social activity settings where groupId = &#63; and activityType = &#63;.
	*
	* @param groupId the group ID
	* @param activityType the activity type
	* @return the number of matching social activity settings
	*/
	public static int countByG_A(long groupId, int activityType) {
		return getPersistence().countByG_A(groupId, activityType);
	}

	/**
	* Returns all the social activity settings where groupId = &#63; and classNameId = &#63; and activityType = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param activityType the activity type
	* @return the matching social activity settings
	*/
	public static List<SocialActivitySetting> findByG_C_A(long groupId,
		long classNameId, int activityType) {
		return getPersistence().findByG_C_A(groupId, classNameId, activityType);
	}

	/**
	* Returns a range of all the social activity settings where groupId = &#63; and classNameId = &#63; and activityType = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param activityType the activity type
	* @param start the lower bound of the range of social activity settings
	* @param end the upper bound of the range of social activity settings (not inclusive)
	* @return the range of matching social activity settings
	*/
	public static List<SocialActivitySetting> findByG_C_A(long groupId,
		long classNameId, int activityType, int start, int end) {
		return getPersistence()
				   .findByG_C_A(groupId, classNameId, activityType, start, end);
	}

	/**
	* Returns an ordered range of all the social activity settings where groupId = &#63; and classNameId = &#63; and activityType = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param activityType the activity type
	* @param start the lower bound of the range of social activity settings
	* @param end the upper bound of the range of social activity settings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity settings
	*/
	public static List<SocialActivitySetting> findByG_C_A(long groupId,
		long classNameId, int activityType, int start, int end,
		OrderByComparator<SocialActivitySetting> orderByComparator) {
		return getPersistence()
				   .findByG_C_A(groupId, classNameId, activityType, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the social activity settings where groupId = &#63; and classNameId = &#63; and activityType = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param activityType the activity type
	* @param start the lower bound of the range of social activity settings
	* @param end the upper bound of the range of social activity settings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activity settings
	*/
	public static List<SocialActivitySetting> findByG_C_A(long groupId,
		long classNameId, int activityType, int start, int end,
		OrderByComparator<SocialActivitySetting> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_C_A(groupId, classNameId, activityType, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first social activity setting in the ordered set where groupId = &#63; and classNameId = &#63; and activityType = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param activityType the activity type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity setting
	* @throws NoSuchActivitySettingException if a matching social activity setting could not be found
	*/
	public static SocialActivitySetting findByG_C_A_First(long groupId,
		long classNameId, int activityType,
		OrderByComparator<SocialActivitySetting> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivitySettingException {
		return getPersistence()
				   .findByG_C_A_First(groupId, classNameId, activityType,
			orderByComparator);
	}

	/**
	* Returns the first social activity setting in the ordered set where groupId = &#63; and classNameId = &#63; and activityType = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param activityType the activity type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity setting, or <code>null</code> if a matching social activity setting could not be found
	*/
	public static SocialActivitySetting fetchByG_C_A_First(long groupId,
		long classNameId, int activityType,
		OrderByComparator<SocialActivitySetting> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_A_First(groupId, classNameId, activityType,
			orderByComparator);
	}

	/**
	* Returns the last social activity setting in the ordered set where groupId = &#63; and classNameId = &#63; and activityType = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param activityType the activity type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity setting
	* @throws NoSuchActivitySettingException if a matching social activity setting could not be found
	*/
	public static SocialActivitySetting findByG_C_A_Last(long groupId,
		long classNameId, int activityType,
		OrderByComparator<SocialActivitySetting> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivitySettingException {
		return getPersistence()
				   .findByG_C_A_Last(groupId, classNameId, activityType,
			orderByComparator);
	}

	/**
	* Returns the last social activity setting in the ordered set where groupId = &#63; and classNameId = &#63; and activityType = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param activityType the activity type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity setting, or <code>null</code> if a matching social activity setting could not be found
	*/
	public static SocialActivitySetting fetchByG_C_A_Last(long groupId,
		long classNameId, int activityType,
		OrderByComparator<SocialActivitySetting> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_A_Last(groupId, classNameId, activityType,
			orderByComparator);
	}

	/**
	* Returns the social activity settings before and after the current social activity setting in the ordered set where groupId = &#63; and classNameId = &#63; and activityType = &#63;.
	*
	* @param activitySettingId the primary key of the current social activity setting
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param activityType the activity type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity setting
	* @throws NoSuchActivitySettingException if a social activity setting with the primary key could not be found
	*/
	public static SocialActivitySetting[] findByG_C_A_PrevAndNext(
		long activitySettingId, long groupId, long classNameId,
		int activityType,
		OrderByComparator<SocialActivitySetting> orderByComparator)
		throws com.liferay.portlet.social.NoSuchActivitySettingException {
		return getPersistence()
				   .findByG_C_A_PrevAndNext(activitySettingId, groupId,
			classNameId, activityType, orderByComparator);
	}

	/**
	* Removes all the social activity settings where groupId = &#63; and classNameId = &#63; and activityType = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param activityType the activity type
	*/
	public static void removeByG_C_A(long groupId, long classNameId,
		int activityType) {
		getPersistence().removeByG_C_A(groupId, classNameId, activityType);
	}

	/**
	* Returns the number of social activity settings where groupId = &#63; and classNameId = &#63; and activityType = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param activityType the activity type
	* @return the number of matching social activity settings
	*/
	public static int countByG_C_A(long groupId, long classNameId,
		int activityType) {
		return getPersistence().countByG_C_A(groupId, classNameId, activityType);
	}

	/**
	* Returns the social activity setting where groupId = &#63; and classNameId = &#63; and activityType = &#63; and name = &#63; or throws a {@link NoSuchActivitySettingException} if it could not be found.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param activityType the activity type
	* @param name the name
	* @return the matching social activity setting
	* @throws NoSuchActivitySettingException if a matching social activity setting could not be found
	*/
	public static SocialActivitySetting findByG_C_A_N(long groupId,
		long classNameId, int activityType, java.lang.String name)
		throws com.liferay.portlet.social.NoSuchActivitySettingException {
		return getPersistence()
				   .findByG_C_A_N(groupId, classNameId, activityType, name);
	}

	/**
	* Returns the social activity setting where groupId = &#63; and classNameId = &#63; and activityType = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param activityType the activity type
	* @param name the name
	* @return the matching social activity setting, or <code>null</code> if a matching social activity setting could not be found
	*/
	public static SocialActivitySetting fetchByG_C_A_N(long groupId,
		long classNameId, int activityType, java.lang.String name) {
		return getPersistence()
				   .fetchByG_C_A_N(groupId, classNameId, activityType, name);
	}

	/**
	* Returns the social activity setting where groupId = &#63; and classNameId = &#63; and activityType = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param activityType the activity type
	* @param name the name
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching social activity setting, or <code>null</code> if a matching social activity setting could not be found
	*/
	public static SocialActivitySetting fetchByG_C_A_N(long groupId,
		long classNameId, int activityType, java.lang.String name,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_C_A_N(groupId, classNameId, activityType, name,
			retrieveFromCache);
	}

	/**
	* Removes the social activity setting where groupId = &#63; and classNameId = &#63; and activityType = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param activityType the activity type
	* @param name the name
	* @return the social activity setting that was removed
	*/
	public static SocialActivitySetting removeByG_C_A_N(long groupId,
		long classNameId, int activityType, java.lang.String name)
		throws com.liferay.portlet.social.NoSuchActivitySettingException {
		return getPersistence()
				   .removeByG_C_A_N(groupId, classNameId, activityType, name);
	}

	/**
	* Returns the number of social activity settings where groupId = &#63; and classNameId = &#63; and activityType = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param activityType the activity type
	* @param name the name
	* @return the number of matching social activity settings
	*/
	public static int countByG_C_A_N(long groupId, long classNameId,
		int activityType, java.lang.String name) {
		return getPersistence()
				   .countByG_C_A_N(groupId, classNameId, activityType, name);
	}

	/**
	* Caches the social activity setting in the entity cache if it is enabled.
	*
	* @param socialActivitySetting the social activity setting
	*/
	public static void cacheResult(SocialActivitySetting socialActivitySetting) {
		getPersistence().cacheResult(socialActivitySetting);
	}

	/**
	* Caches the social activity settings in the entity cache if it is enabled.
	*
	* @param socialActivitySettings the social activity settings
	*/
	public static void cacheResult(
		List<SocialActivitySetting> socialActivitySettings) {
		getPersistence().cacheResult(socialActivitySettings);
	}

	/**
	* Creates a new social activity setting with the primary key. Does not add the social activity setting to the database.
	*
	* @param activitySettingId the primary key for the new social activity setting
	* @return the new social activity setting
	*/
	public static SocialActivitySetting create(long activitySettingId) {
		return getPersistence().create(activitySettingId);
	}

	/**
	* Removes the social activity setting with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param activitySettingId the primary key of the social activity setting
	* @return the social activity setting that was removed
	* @throws NoSuchActivitySettingException if a social activity setting with the primary key could not be found
	*/
	public static SocialActivitySetting remove(long activitySettingId)
		throws com.liferay.portlet.social.NoSuchActivitySettingException {
		return getPersistence().remove(activitySettingId);
	}

	public static SocialActivitySetting updateImpl(
		SocialActivitySetting socialActivitySetting) {
		return getPersistence().updateImpl(socialActivitySetting);
	}

	/**
	* Returns the social activity setting with the primary key or throws a {@link NoSuchActivitySettingException} if it could not be found.
	*
	* @param activitySettingId the primary key of the social activity setting
	* @return the social activity setting
	* @throws NoSuchActivitySettingException if a social activity setting with the primary key could not be found
	*/
	public static SocialActivitySetting findByPrimaryKey(long activitySettingId)
		throws com.liferay.portlet.social.NoSuchActivitySettingException {
		return getPersistence().findByPrimaryKey(activitySettingId);
	}

	/**
	* Returns the social activity setting with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param activitySettingId the primary key of the social activity setting
	* @return the social activity setting, or <code>null</code> if a social activity setting with the primary key could not be found
	*/
	public static SocialActivitySetting fetchByPrimaryKey(
		long activitySettingId) {
		return getPersistence().fetchByPrimaryKey(activitySettingId);
	}

	public static java.util.Map<java.io.Serializable, SocialActivitySetting> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the social activity settings.
	*
	* @return the social activity settings
	*/
	public static List<SocialActivitySetting> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the social activity settings.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social activity settings
	* @param end the upper bound of the range of social activity settings (not inclusive)
	* @return the range of social activity settings
	*/
	public static List<SocialActivitySetting> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the social activity settings.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social activity settings
	* @param end the upper bound of the range of social activity settings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of social activity settings
	*/
	public static List<SocialActivitySetting> findAll(int start, int end,
		OrderByComparator<SocialActivitySetting> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the social activity settings.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social activity settings
	* @param end the upper bound of the range of social activity settings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of social activity settings
	*/
	public static List<SocialActivitySetting> findAll(int start, int end,
		OrderByComparator<SocialActivitySetting> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the social activity settings from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of social activity settings.
	*
	* @return the number of social activity settings
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static SocialActivitySettingPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (SocialActivitySettingPersistence)PortalBeanLocatorUtil.locate(SocialActivitySettingPersistence.class.getName());

			ReferenceRegistry.registerReference(SocialActivitySettingUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static SocialActivitySettingPersistence _persistence;
}
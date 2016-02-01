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

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.social.exception.NoSuchActivitySettingException;
import com.liferay.portlet.social.model.SocialActivitySetting;

/**
 * The persistence interface for the social activity setting service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.social.service.persistence.impl.SocialActivitySettingPersistenceImpl
 * @see SocialActivitySettingUtil
 * @generated
 */
@ProviderType
public interface SocialActivitySettingPersistence extends BasePersistence<SocialActivitySetting> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SocialActivitySettingUtil} to access the social activity setting persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the social activity settings where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching social activity settings
	*/
	public java.util.List<SocialActivitySetting> findByGroupId(long groupId);

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
	public java.util.List<SocialActivitySetting> findByGroupId(long groupId,
		int start, int end);

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
	public java.util.List<SocialActivitySetting> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivitySetting> orderByComparator);

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
	public java.util.List<SocialActivitySetting> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivitySetting> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social activity setting in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity setting
	* @throws NoSuchActivitySettingException if a matching social activity setting could not be found
	*/
	public SocialActivitySetting findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivitySetting> orderByComparator)
		throws NoSuchActivitySettingException;

	/**
	* Returns the first social activity setting in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity setting, or <code>null</code> if a matching social activity setting could not be found
	*/
	public SocialActivitySetting fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivitySetting> orderByComparator);

	/**
	* Returns the last social activity setting in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity setting
	* @throws NoSuchActivitySettingException if a matching social activity setting could not be found
	*/
	public SocialActivitySetting findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivitySetting> orderByComparator)
		throws NoSuchActivitySettingException;

	/**
	* Returns the last social activity setting in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity setting, or <code>null</code> if a matching social activity setting could not be found
	*/
	public SocialActivitySetting fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivitySetting> orderByComparator);

	/**
	* Returns the social activity settings before and after the current social activity setting in the ordered set where groupId = &#63;.
	*
	* @param activitySettingId the primary key of the current social activity setting
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity setting
	* @throws NoSuchActivitySettingException if a social activity setting with the primary key could not be found
	*/
	public SocialActivitySetting[] findByGroupId_PrevAndNext(
		long activitySettingId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivitySetting> orderByComparator)
		throws NoSuchActivitySettingException;

	/**
	* Removes all the social activity settings where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of social activity settings where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching social activity settings
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the social activity settings where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the matching social activity settings
	*/
	public java.util.List<SocialActivitySetting> findByG_C(long groupId,
		long classNameId);

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
	public java.util.List<SocialActivitySetting> findByG_C(long groupId,
		long classNameId, int start, int end);

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
	public java.util.List<SocialActivitySetting> findByG_C(long groupId,
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivitySetting> orderByComparator);

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
	public java.util.List<SocialActivitySetting> findByG_C(long groupId,
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivitySetting> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social activity setting in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity setting
	* @throws NoSuchActivitySettingException if a matching social activity setting could not be found
	*/
	public SocialActivitySetting findByG_C_First(long groupId,
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivitySetting> orderByComparator)
		throws NoSuchActivitySettingException;

	/**
	* Returns the first social activity setting in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity setting, or <code>null</code> if a matching social activity setting could not be found
	*/
	public SocialActivitySetting fetchByG_C_First(long groupId,
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivitySetting> orderByComparator);

	/**
	* Returns the last social activity setting in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity setting
	* @throws NoSuchActivitySettingException if a matching social activity setting could not be found
	*/
	public SocialActivitySetting findByG_C_Last(long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivitySetting> orderByComparator)
		throws NoSuchActivitySettingException;

	/**
	* Returns the last social activity setting in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity setting, or <code>null</code> if a matching social activity setting could not be found
	*/
	public SocialActivitySetting fetchByG_C_Last(long groupId,
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivitySetting> orderByComparator);

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
	public SocialActivitySetting[] findByG_C_PrevAndNext(
		long activitySettingId, long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivitySetting> orderByComparator)
		throws NoSuchActivitySettingException;

	/**
	* Removes all the social activity settings where groupId = &#63; and classNameId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	*/
	public void removeByG_C(long groupId, long classNameId);

	/**
	* Returns the number of social activity settings where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the number of matching social activity settings
	*/
	public int countByG_C(long groupId, long classNameId);

	/**
	* Returns all the social activity settings where groupId = &#63; and activityType = &#63;.
	*
	* @param groupId the group ID
	* @param activityType the activity type
	* @return the matching social activity settings
	*/
	public java.util.List<SocialActivitySetting> findByG_A(long groupId,
		int activityType);

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
	public java.util.List<SocialActivitySetting> findByG_A(long groupId,
		int activityType, int start, int end);

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
	public java.util.List<SocialActivitySetting> findByG_A(long groupId,
		int activityType, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivitySetting> orderByComparator);

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
	public java.util.List<SocialActivitySetting> findByG_A(long groupId,
		int activityType, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivitySetting> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social activity setting in the ordered set where groupId = &#63; and activityType = &#63;.
	*
	* @param groupId the group ID
	* @param activityType the activity type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity setting
	* @throws NoSuchActivitySettingException if a matching social activity setting could not be found
	*/
	public SocialActivitySetting findByG_A_First(long groupId,
		int activityType,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivitySetting> orderByComparator)
		throws NoSuchActivitySettingException;

	/**
	* Returns the first social activity setting in the ordered set where groupId = &#63; and activityType = &#63;.
	*
	* @param groupId the group ID
	* @param activityType the activity type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity setting, or <code>null</code> if a matching social activity setting could not be found
	*/
	public SocialActivitySetting fetchByG_A_First(long groupId,
		int activityType,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivitySetting> orderByComparator);

	/**
	* Returns the last social activity setting in the ordered set where groupId = &#63; and activityType = &#63;.
	*
	* @param groupId the group ID
	* @param activityType the activity type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity setting
	* @throws NoSuchActivitySettingException if a matching social activity setting could not be found
	*/
	public SocialActivitySetting findByG_A_Last(long groupId, int activityType,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivitySetting> orderByComparator)
		throws NoSuchActivitySettingException;

	/**
	* Returns the last social activity setting in the ordered set where groupId = &#63; and activityType = &#63;.
	*
	* @param groupId the group ID
	* @param activityType the activity type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity setting, or <code>null</code> if a matching social activity setting could not be found
	*/
	public SocialActivitySetting fetchByG_A_Last(long groupId,
		int activityType,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivitySetting> orderByComparator);

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
	public SocialActivitySetting[] findByG_A_PrevAndNext(
		long activitySettingId, long groupId, int activityType,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivitySetting> orderByComparator)
		throws NoSuchActivitySettingException;

	/**
	* Removes all the social activity settings where groupId = &#63; and activityType = &#63; from the database.
	*
	* @param groupId the group ID
	* @param activityType the activity type
	*/
	public void removeByG_A(long groupId, int activityType);

	/**
	* Returns the number of social activity settings where groupId = &#63; and activityType = &#63;.
	*
	* @param groupId the group ID
	* @param activityType the activity type
	* @return the number of matching social activity settings
	*/
	public int countByG_A(long groupId, int activityType);

	/**
	* Returns all the social activity settings where groupId = &#63; and classNameId = &#63; and activityType = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param activityType the activity type
	* @return the matching social activity settings
	*/
	public java.util.List<SocialActivitySetting> findByG_C_A(long groupId,
		long classNameId, int activityType);

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
	public java.util.List<SocialActivitySetting> findByG_C_A(long groupId,
		long classNameId, int activityType, int start, int end);

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
	public java.util.List<SocialActivitySetting> findByG_C_A(long groupId,
		long classNameId, int activityType, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivitySetting> orderByComparator);

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
	public java.util.List<SocialActivitySetting> findByG_C_A(long groupId,
		long classNameId, int activityType, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivitySetting> orderByComparator,
		boolean retrieveFromCache);

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
	public SocialActivitySetting findByG_C_A_First(long groupId,
		long classNameId, int activityType,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivitySetting> orderByComparator)
		throws NoSuchActivitySettingException;

	/**
	* Returns the first social activity setting in the ordered set where groupId = &#63; and classNameId = &#63; and activityType = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param activityType the activity type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity setting, or <code>null</code> if a matching social activity setting could not be found
	*/
	public SocialActivitySetting fetchByG_C_A_First(long groupId,
		long classNameId, int activityType,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivitySetting> orderByComparator);

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
	public SocialActivitySetting findByG_C_A_Last(long groupId,
		long classNameId, int activityType,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivitySetting> orderByComparator)
		throws NoSuchActivitySettingException;

	/**
	* Returns the last social activity setting in the ordered set where groupId = &#63; and classNameId = &#63; and activityType = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param activityType the activity type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity setting, or <code>null</code> if a matching social activity setting could not be found
	*/
	public SocialActivitySetting fetchByG_C_A_Last(long groupId,
		long classNameId, int activityType,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivitySetting> orderByComparator);

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
	public SocialActivitySetting[] findByG_C_A_PrevAndNext(
		long activitySettingId, long groupId, long classNameId,
		int activityType,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivitySetting> orderByComparator)
		throws NoSuchActivitySettingException;

	/**
	* Removes all the social activity settings where groupId = &#63; and classNameId = &#63; and activityType = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param activityType the activity type
	*/
	public void removeByG_C_A(long groupId, long classNameId, int activityType);

	/**
	* Returns the number of social activity settings where groupId = &#63; and classNameId = &#63; and activityType = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param activityType the activity type
	* @return the number of matching social activity settings
	*/
	public int countByG_C_A(long groupId, long classNameId, int activityType);

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
	public SocialActivitySetting findByG_C_A_N(long groupId, long classNameId,
		int activityType, java.lang.String name)
		throws NoSuchActivitySettingException;

	/**
	* Returns the social activity setting where groupId = &#63; and classNameId = &#63; and activityType = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param activityType the activity type
	* @param name the name
	* @return the matching social activity setting, or <code>null</code> if a matching social activity setting could not be found
	*/
	public SocialActivitySetting fetchByG_C_A_N(long groupId, long classNameId,
		int activityType, java.lang.String name);

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
	public SocialActivitySetting fetchByG_C_A_N(long groupId, long classNameId,
		int activityType, java.lang.String name, boolean retrieveFromCache);

	/**
	* Removes the social activity setting where groupId = &#63; and classNameId = &#63; and activityType = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param activityType the activity type
	* @param name the name
	* @return the social activity setting that was removed
	*/
	public SocialActivitySetting removeByG_C_A_N(long groupId,
		long classNameId, int activityType, java.lang.String name)
		throws NoSuchActivitySettingException;

	/**
	* Returns the number of social activity settings where groupId = &#63; and classNameId = &#63; and activityType = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param activityType the activity type
	* @param name the name
	* @return the number of matching social activity settings
	*/
	public int countByG_C_A_N(long groupId, long classNameId, int activityType,
		java.lang.String name);

	/**
	* Caches the social activity setting in the entity cache if it is enabled.
	*
	* @param socialActivitySetting the social activity setting
	*/
	public void cacheResult(SocialActivitySetting socialActivitySetting);

	/**
	* Caches the social activity settings in the entity cache if it is enabled.
	*
	* @param socialActivitySettings the social activity settings
	*/
	public void cacheResult(
		java.util.List<SocialActivitySetting> socialActivitySettings);

	/**
	* Creates a new social activity setting with the primary key. Does not add the social activity setting to the database.
	*
	* @param activitySettingId the primary key for the new social activity setting
	* @return the new social activity setting
	*/
	public SocialActivitySetting create(long activitySettingId);

	/**
	* Removes the social activity setting with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param activitySettingId the primary key of the social activity setting
	* @return the social activity setting that was removed
	* @throws NoSuchActivitySettingException if a social activity setting with the primary key could not be found
	*/
	public SocialActivitySetting remove(long activitySettingId)
		throws NoSuchActivitySettingException;

	public SocialActivitySetting updateImpl(
		SocialActivitySetting socialActivitySetting);

	/**
	* Returns the social activity setting with the primary key or throws a {@link NoSuchActivitySettingException} if it could not be found.
	*
	* @param activitySettingId the primary key of the social activity setting
	* @return the social activity setting
	* @throws NoSuchActivitySettingException if a social activity setting with the primary key could not be found
	*/
	public SocialActivitySetting findByPrimaryKey(long activitySettingId)
		throws NoSuchActivitySettingException;

	/**
	* Returns the social activity setting with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param activitySettingId the primary key of the social activity setting
	* @return the social activity setting, or <code>null</code> if a social activity setting with the primary key could not be found
	*/
	public SocialActivitySetting fetchByPrimaryKey(long activitySettingId);

	@Override
	public java.util.Map<java.io.Serializable, SocialActivitySetting> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the social activity settings.
	*
	* @return the social activity settings
	*/
	public java.util.List<SocialActivitySetting> findAll();

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
	public java.util.List<SocialActivitySetting> findAll(int start, int end);

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
	public java.util.List<SocialActivitySetting> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivitySetting> orderByComparator);

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
	public java.util.List<SocialActivitySetting> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivitySetting> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the social activity settings from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of social activity settings.
	*
	* @return the number of social activity settings
	*/
	public int countAll();
}
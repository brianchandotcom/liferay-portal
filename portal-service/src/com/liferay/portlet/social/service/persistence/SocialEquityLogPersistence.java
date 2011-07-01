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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.social.model.SocialEquityLog;

/**
 * The persistence interface for the social equity log service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialEquityLogPersistenceImpl
 * @see SocialEquityLogUtil
 * @generated
 */
public interface SocialEquityLogPersistence extends BasePersistence<SocialEquityLog> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SocialEquityLogUtil} to access the social equity log persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the social equity log in the entity cache if it is enabled.
	*
	* @param socialEquityLog the social equity log
	*/
	public void cacheResult(
		com.liferay.portlet.social.model.SocialEquityLog socialEquityLog);

	/**
	* Caches the social equity logs in the entity cache if it is enabled.
	*
	* @param socialEquityLogs the social equity logs
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.social.model.SocialEquityLog> socialEquityLogs);

	/**
	* Creates a new social equity log with the primary key. Does not add the social equity log to the database.
	*
	* @param equityLogId the primary key for the new social equity log
	* @return the new social equity log
	*/
	public com.liferay.portlet.social.model.SocialEquityLog create(
		long equityLogId);

	/**
	* Removes the social equity log with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param equityLogId the primary key of the social equity log
	* @return the social equity log that was removed
	* @throws com.liferay.portlet.social.NoSuchEquityLogException if a social equity log with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityLog remove(
		long equityLogId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException;

	public com.liferay.portlet.social.model.SocialEquityLog updateImpl(
		com.liferay.portlet.social.model.SocialEquityLog socialEquityLog,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the social equity log with the primary key or throws a {@link com.liferay.portlet.social.NoSuchEquityLogException} if it could not be found.
	*
	* @param equityLogId the primary key of the social equity log
	* @return the social equity log
	* @throws com.liferay.portlet.social.NoSuchEquityLogException if a social equity log with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityLog findByPrimaryKey(
		long equityLogId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException;

	/**
	* Returns the social equity log with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param equityLogId the primary key of the social equity log
	* @return the social equity log, or <code>null</code> if a social equity log with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityLog fetchByPrimaryKey(
		long equityLogId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the social equity logs where assetEntryId = &#63; and type = &#63; and active = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param type the type
	* @param active the active
	* @return the matching social equity logs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByAEI_T_A(
		long assetEntryId, int type, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the social equity logs where assetEntryId = &#63; and type = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param assetEntryId the asset entry ID
	* @param type the type
	* @param active the active
	* @param start the lower bound of the range of social equity logs
	* @param end the upper bound of the range of social equity logs (not inclusive)
	* @return the range of matching social equity logs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByAEI_T_A(
		long assetEntryId, int type, boolean active, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the social equity logs where assetEntryId = &#63; and type = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param assetEntryId the asset entry ID
	* @param type the type
	* @param active the active
	* @param start the lower bound of the range of social equity logs
	* @param end the upper bound of the range of social equity logs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social equity logs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByAEI_T_A(
		long assetEntryId, int type, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first social equity log in the ordered set where assetEntryId = &#63; and type = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param assetEntryId the asset entry ID
	* @param type the type
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social equity log
	* @throws com.liferay.portlet.social.NoSuchEquityLogException if a matching social equity log could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityLog findByAEI_T_A_First(
		long assetEntryId, int type, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException;

	/**
	* Returns the last social equity log in the ordered set where assetEntryId = &#63; and type = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param assetEntryId the asset entry ID
	* @param type the type
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social equity log
	* @throws com.liferay.portlet.social.NoSuchEquityLogException if a matching social equity log could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityLog findByAEI_T_A_Last(
		long assetEntryId, int type, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException;

	/**
	* Returns the social equity logs before and after the current social equity log in the ordered set where assetEntryId = &#63; and type = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param equityLogId the primary key of the current social equity log
	* @param assetEntryId the asset entry ID
	* @param type the type
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social equity log
	* @throws com.liferay.portlet.social.NoSuchEquityLogException if a social equity log with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityLog[] findByAEI_T_A_PrevAndNext(
		long equityLogId, long assetEntryId, int type, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException;

	/**
	* Returns all the social equity logs where assetEntryId = &#63; and actionId = &#63; and active = &#63; and extraData = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param actionId the action ID
	* @param active the active
	* @param extraData the extra data
	* @return the matching social equity logs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByAEI_AID_A_E(
		long assetEntryId, java.lang.String actionId, boolean active,
		java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the social equity logs where assetEntryId = &#63; and actionId = &#63; and active = &#63; and extraData = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param assetEntryId the asset entry ID
	* @param actionId the action ID
	* @param active the active
	* @param extraData the extra data
	* @param start the lower bound of the range of social equity logs
	* @param end the upper bound of the range of social equity logs (not inclusive)
	* @return the range of matching social equity logs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByAEI_AID_A_E(
		long assetEntryId, java.lang.String actionId, boolean active,
		java.lang.String extraData, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the social equity logs where assetEntryId = &#63; and actionId = &#63; and active = &#63; and extraData = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param assetEntryId the asset entry ID
	* @param actionId the action ID
	* @param active the active
	* @param extraData the extra data
	* @param start the lower bound of the range of social equity logs
	* @param end the upper bound of the range of social equity logs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social equity logs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByAEI_AID_A_E(
		long assetEntryId, java.lang.String actionId, boolean active,
		java.lang.String extraData, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first social equity log in the ordered set where assetEntryId = &#63; and actionId = &#63; and active = &#63; and extraData = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param assetEntryId the asset entry ID
	* @param actionId the action ID
	* @param active the active
	* @param extraData the extra data
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social equity log
	* @throws com.liferay.portlet.social.NoSuchEquityLogException if a matching social equity log could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityLog findByAEI_AID_A_E_First(
		long assetEntryId, java.lang.String actionId, boolean active,
		java.lang.String extraData,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException;

	/**
	* Returns the last social equity log in the ordered set where assetEntryId = &#63; and actionId = &#63; and active = &#63; and extraData = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param assetEntryId the asset entry ID
	* @param actionId the action ID
	* @param active the active
	* @param extraData the extra data
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social equity log
	* @throws com.liferay.portlet.social.NoSuchEquityLogException if a matching social equity log could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityLog findByAEI_AID_A_E_Last(
		long assetEntryId, java.lang.String actionId, boolean active,
		java.lang.String extraData,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException;

	/**
	* Returns the social equity logs before and after the current social equity log in the ordered set where assetEntryId = &#63; and actionId = &#63; and active = &#63; and extraData = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param equityLogId the primary key of the current social equity log
	* @param assetEntryId the asset entry ID
	* @param actionId the action ID
	* @param active the active
	* @param extraData the extra data
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social equity log
	* @throws com.liferay.portlet.social.NoSuchEquityLogException if a social equity log with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityLog[] findByAEI_AID_A_E_PrevAndNext(
		long equityLogId, long assetEntryId, java.lang.String actionId,
		boolean active, java.lang.String extraData,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException;

	/**
	* Returns all the social equity logs where userId = &#63; and assetEntryId = &#63; and actionId = &#63; and active = &#63; and extraData = &#63;.
	*
	* @param userId the user ID
	* @param assetEntryId the asset entry ID
	* @param actionId the action ID
	* @param active the active
	* @param extraData the extra data
	* @return the matching social equity logs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByU_AEI_AID_A_E(
		long userId, long assetEntryId, java.lang.String actionId,
		boolean active, java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the social equity logs where userId = &#63; and assetEntryId = &#63; and actionId = &#63; and active = &#63; and extraData = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param assetEntryId the asset entry ID
	* @param actionId the action ID
	* @param active the active
	* @param extraData the extra data
	* @param start the lower bound of the range of social equity logs
	* @param end the upper bound of the range of social equity logs (not inclusive)
	* @return the range of matching social equity logs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByU_AEI_AID_A_E(
		long userId, long assetEntryId, java.lang.String actionId,
		boolean active, java.lang.String extraData, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the social equity logs where userId = &#63; and assetEntryId = &#63; and actionId = &#63; and active = &#63; and extraData = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param assetEntryId the asset entry ID
	* @param actionId the action ID
	* @param active the active
	* @param extraData the extra data
	* @param start the lower bound of the range of social equity logs
	* @param end the upper bound of the range of social equity logs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social equity logs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByU_AEI_AID_A_E(
		long userId, long assetEntryId, java.lang.String actionId,
		boolean active, java.lang.String extraData, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first social equity log in the ordered set where userId = &#63; and assetEntryId = &#63; and actionId = &#63; and active = &#63; and extraData = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param assetEntryId the asset entry ID
	* @param actionId the action ID
	* @param active the active
	* @param extraData the extra data
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social equity log
	* @throws com.liferay.portlet.social.NoSuchEquityLogException if a matching social equity log could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityLog findByU_AEI_AID_A_E_First(
		long userId, long assetEntryId, java.lang.String actionId,
		boolean active, java.lang.String extraData,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException;

	/**
	* Returns the last social equity log in the ordered set where userId = &#63; and assetEntryId = &#63; and actionId = &#63; and active = &#63; and extraData = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param assetEntryId the asset entry ID
	* @param actionId the action ID
	* @param active the active
	* @param extraData the extra data
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social equity log
	* @throws com.liferay.portlet.social.NoSuchEquityLogException if a matching social equity log could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityLog findByU_AEI_AID_A_E_Last(
		long userId, long assetEntryId, java.lang.String actionId,
		boolean active, java.lang.String extraData,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException;

	/**
	* Returns the social equity logs before and after the current social equity log in the ordered set where userId = &#63; and assetEntryId = &#63; and actionId = &#63; and active = &#63; and extraData = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param equityLogId the primary key of the current social equity log
	* @param userId the user ID
	* @param assetEntryId the asset entry ID
	* @param actionId the action ID
	* @param active the active
	* @param extraData the extra data
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social equity log
	* @throws com.liferay.portlet.social.NoSuchEquityLogException if a social equity log with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityLog[] findByU_AEI_AID_A_E_PrevAndNext(
		long equityLogId, long userId, long assetEntryId,
		java.lang.String actionId, boolean active, java.lang.String extraData,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException;

	/**
	* Returns all the social equity logs where userId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63;.
	*
	* @param userId the user ID
	* @param actionId the action ID
	* @param actionDate the action date
	* @param active the active
	* @param type the type
	* @param extraData the extra data
	* @return the matching social equity logs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByU_AID_AD_A_T_E(
		long userId, java.lang.String actionId, int actionDate, boolean active,
		int type, java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the social equity logs where userId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param actionId the action ID
	* @param actionDate the action date
	* @param active the active
	* @param type the type
	* @param extraData the extra data
	* @param start the lower bound of the range of social equity logs
	* @param end the upper bound of the range of social equity logs (not inclusive)
	* @return the range of matching social equity logs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByU_AID_AD_A_T_E(
		long userId, java.lang.String actionId, int actionDate, boolean active,
		int type, java.lang.String extraData, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the social equity logs where userId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param actionId the action ID
	* @param actionDate the action date
	* @param active the active
	* @param type the type
	* @param extraData the extra data
	* @param start the lower bound of the range of social equity logs
	* @param end the upper bound of the range of social equity logs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social equity logs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByU_AID_AD_A_T_E(
		long userId, java.lang.String actionId, int actionDate, boolean active,
		int type, java.lang.String extraData, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first social equity log in the ordered set where userId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param actionId the action ID
	* @param actionDate the action date
	* @param active the active
	* @param type the type
	* @param extraData the extra data
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social equity log
	* @throws com.liferay.portlet.social.NoSuchEquityLogException if a matching social equity log could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityLog findByU_AID_AD_A_T_E_First(
		long userId, java.lang.String actionId, int actionDate, boolean active,
		int type, java.lang.String extraData,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException;

	/**
	* Returns the last social equity log in the ordered set where userId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param actionId the action ID
	* @param actionDate the action date
	* @param active the active
	* @param type the type
	* @param extraData the extra data
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social equity log
	* @throws com.liferay.portlet.social.NoSuchEquityLogException if a matching social equity log could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityLog findByU_AID_AD_A_T_E_Last(
		long userId, java.lang.String actionId, int actionDate, boolean active,
		int type, java.lang.String extraData,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException;

	/**
	* Returns the social equity logs before and after the current social equity log in the ordered set where userId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param equityLogId the primary key of the current social equity log
	* @param userId the user ID
	* @param actionId the action ID
	* @param actionDate the action date
	* @param active the active
	* @param type the type
	* @param extraData the extra data
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social equity log
	* @throws com.liferay.portlet.social.NoSuchEquityLogException if a social equity log with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityLog[] findByU_AID_AD_A_T_E_PrevAndNext(
		long equityLogId, long userId, java.lang.String actionId,
		int actionDate, boolean active, int type, java.lang.String extraData,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException;

	/**
	* Returns all the social equity logs where assetEntryId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param actionId the action ID
	* @param actionDate the action date
	* @param active the active
	* @param type the type
	* @param extraData the extra data
	* @return the matching social equity logs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByAEI_AID_AD_A_T_E(
		long assetEntryId, java.lang.String actionId, int actionDate,
		boolean active, int type, java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the social equity logs where assetEntryId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param assetEntryId the asset entry ID
	* @param actionId the action ID
	* @param actionDate the action date
	* @param active the active
	* @param type the type
	* @param extraData the extra data
	* @param start the lower bound of the range of social equity logs
	* @param end the upper bound of the range of social equity logs (not inclusive)
	* @return the range of matching social equity logs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByAEI_AID_AD_A_T_E(
		long assetEntryId, java.lang.String actionId, int actionDate,
		boolean active, int type, java.lang.String extraData, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the social equity logs where assetEntryId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param assetEntryId the asset entry ID
	* @param actionId the action ID
	* @param actionDate the action date
	* @param active the active
	* @param type the type
	* @param extraData the extra data
	* @param start the lower bound of the range of social equity logs
	* @param end the upper bound of the range of social equity logs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social equity logs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByAEI_AID_AD_A_T_E(
		long assetEntryId, java.lang.String actionId, int actionDate,
		boolean active, int type, java.lang.String extraData, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first social equity log in the ordered set where assetEntryId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param assetEntryId the asset entry ID
	* @param actionId the action ID
	* @param actionDate the action date
	* @param active the active
	* @param type the type
	* @param extraData the extra data
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social equity log
	* @throws com.liferay.portlet.social.NoSuchEquityLogException if a matching social equity log could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityLog findByAEI_AID_AD_A_T_E_First(
		long assetEntryId, java.lang.String actionId, int actionDate,
		boolean active, int type, java.lang.String extraData,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException;

	/**
	* Returns the last social equity log in the ordered set where assetEntryId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param assetEntryId the asset entry ID
	* @param actionId the action ID
	* @param actionDate the action date
	* @param active the active
	* @param type the type
	* @param extraData the extra data
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social equity log
	* @throws com.liferay.portlet.social.NoSuchEquityLogException if a matching social equity log could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityLog findByAEI_AID_AD_A_T_E_Last(
		long assetEntryId, java.lang.String actionId, int actionDate,
		boolean active, int type, java.lang.String extraData,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException;

	/**
	* Returns the social equity logs before and after the current social equity log in the ordered set where assetEntryId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param equityLogId the primary key of the current social equity log
	* @param assetEntryId the asset entry ID
	* @param actionId the action ID
	* @param actionDate the action date
	* @param active the active
	* @param type the type
	* @param extraData the extra data
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social equity log
	* @throws com.liferay.portlet.social.NoSuchEquityLogException if a social equity log with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityLog[] findByAEI_AID_AD_A_T_E_PrevAndNext(
		long equityLogId, long assetEntryId, java.lang.String actionId,
		int actionDate, boolean active, int type, java.lang.String extraData,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException;

	/**
	* Returns the social equity log where userId = &#63; and assetEntryId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63; or throws a {@link com.liferay.portlet.social.NoSuchEquityLogException} if it could not be found.
	*
	* @param userId the user ID
	* @param assetEntryId the asset entry ID
	* @param actionId the action ID
	* @param actionDate the action date
	* @param active the active
	* @param type the type
	* @param extraData the extra data
	* @return the matching social equity log
	* @throws com.liferay.portlet.social.NoSuchEquityLogException if a matching social equity log could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityLog findByU_AEI_AID_AD_A_T_E(
		long userId, long assetEntryId, java.lang.String actionId,
		int actionDate, boolean active, int type, java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException;

	/**
	* Returns the social equity log where userId = &#63; and assetEntryId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID
	* @param assetEntryId the asset entry ID
	* @param actionId the action ID
	* @param actionDate the action date
	* @param active the active
	* @param type the type
	* @param extraData the extra data
	* @return the matching social equity log, or <code>null</code> if a matching social equity log could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityLog fetchByU_AEI_AID_AD_A_T_E(
		long userId, long assetEntryId, java.lang.String actionId,
		int actionDate, boolean active, int type, java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the social equity log where userId = &#63; and assetEntryId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID
	* @param assetEntryId the asset entry ID
	* @param actionId the action ID
	* @param actionDate the action date
	* @param active the active
	* @param type the type
	* @param extraData the extra data
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching social equity log, or <code>null</code> if a matching social equity log could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityLog fetchByU_AEI_AID_AD_A_T_E(
		long userId, long assetEntryId, java.lang.String actionId,
		int actionDate, boolean active, int type, java.lang.String extraData,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the social equity logs.
	*
	* @return the social equity logs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the social equity logs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of social equity logs
	* @param end the upper bound of the range of social equity logs (not inclusive)
	* @return the range of social equity logs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the social equity logs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of social equity logs
	* @param end the upper bound of the range of social equity logs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of social equity logs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the social equity logs where assetEntryId = &#63; and type = &#63; and active = &#63; from the database.
	*
	* @param assetEntryId the asset entry ID
	* @param type the type
	* @param active the active
	* @throws SystemException if a system exception occurred
	*/
	public void removeByAEI_T_A(long assetEntryId, int type, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the social equity logs where assetEntryId = &#63; and actionId = &#63; and active = &#63; and extraData = &#63; from the database.
	*
	* @param assetEntryId the asset entry ID
	* @param actionId the action ID
	* @param active the active
	* @param extraData the extra data
	* @throws SystemException if a system exception occurred
	*/
	public void removeByAEI_AID_A_E(long assetEntryId,
		java.lang.String actionId, boolean active, java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the social equity logs where userId = &#63; and assetEntryId = &#63; and actionId = &#63; and active = &#63; and extraData = &#63; from the database.
	*
	* @param userId the user ID
	* @param assetEntryId the asset entry ID
	* @param actionId the action ID
	* @param active the active
	* @param extraData the extra data
	* @throws SystemException if a system exception occurred
	*/
	public void removeByU_AEI_AID_A_E(long userId, long assetEntryId,
		java.lang.String actionId, boolean active, java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the social equity logs where userId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63; from the database.
	*
	* @param userId the user ID
	* @param actionId the action ID
	* @param actionDate the action date
	* @param active the active
	* @param type the type
	* @param extraData the extra data
	* @throws SystemException if a system exception occurred
	*/
	public void removeByU_AID_AD_A_T_E(long userId, java.lang.String actionId,
		int actionDate, boolean active, int type, java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the social equity logs where assetEntryId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63; from the database.
	*
	* @param assetEntryId the asset entry ID
	* @param actionId the action ID
	* @param actionDate the action date
	* @param active the active
	* @param type the type
	* @param extraData the extra data
	* @throws SystemException if a system exception occurred
	*/
	public void removeByAEI_AID_AD_A_T_E(long assetEntryId,
		java.lang.String actionId, int actionDate, boolean active, int type,
		java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the social equity log where userId = &#63; and assetEntryId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63; from the database.
	*
	* @param userId the user ID
	* @param assetEntryId the asset entry ID
	* @param actionId the action ID
	* @param actionDate the action date
	* @param active the active
	* @param type the type
	* @param extraData the extra data
	* @throws SystemException if a system exception occurred
	*/
	public void removeByU_AEI_AID_AD_A_T_E(long userId, long assetEntryId,
		java.lang.String actionId, int actionDate, boolean active, int type,
		java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException;

	/**
	* Removes all the social equity logs from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social equity logs where assetEntryId = &#63; and type = &#63; and active = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param type the type
	* @param active the active
	* @return the number of matching social equity logs
	* @throws SystemException if a system exception occurred
	*/
	public int countByAEI_T_A(long assetEntryId, int type, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social equity logs where assetEntryId = &#63; and actionId = &#63; and active = &#63; and extraData = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param actionId the action ID
	* @param active the active
	* @param extraData the extra data
	* @return the number of matching social equity logs
	* @throws SystemException if a system exception occurred
	*/
	public int countByAEI_AID_A_E(long assetEntryId, java.lang.String actionId,
		boolean active, java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social equity logs where userId = &#63; and assetEntryId = &#63; and actionId = &#63; and active = &#63; and extraData = &#63;.
	*
	* @param userId the user ID
	* @param assetEntryId the asset entry ID
	* @param actionId the action ID
	* @param active the active
	* @param extraData the extra data
	* @return the number of matching social equity logs
	* @throws SystemException if a system exception occurred
	*/
	public int countByU_AEI_AID_A_E(long userId, long assetEntryId,
		java.lang.String actionId, boolean active, java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social equity logs where userId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63;.
	*
	* @param userId the user ID
	* @param actionId the action ID
	* @param actionDate the action date
	* @param active the active
	* @param type the type
	* @param extraData the extra data
	* @return the number of matching social equity logs
	* @throws SystemException if a system exception occurred
	*/
	public int countByU_AID_AD_A_T_E(long userId, java.lang.String actionId,
		int actionDate, boolean active, int type, java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social equity logs where assetEntryId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param actionId the action ID
	* @param actionDate the action date
	* @param active the active
	* @param type the type
	* @param extraData the extra data
	* @return the number of matching social equity logs
	* @throws SystemException if a system exception occurred
	*/
	public int countByAEI_AID_AD_A_T_E(long assetEntryId,
		java.lang.String actionId, int actionDate, boolean active, int type,
		java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social equity logs where userId = &#63; and assetEntryId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63;.
	*
	* @param userId the user ID
	* @param assetEntryId the asset entry ID
	* @param actionId the action ID
	* @param actionDate the action date
	* @param active the active
	* @param type the type
	* @param extraData the extra data
	* @return the number of matching social equity logs
	* @throws SystemException if a system exception occurred
	*/
	public int countByU_AEI_AID_AD_A_T_E(long userId, long assetEntryId,
		java.lang.String actionId, int actionDate, boolean active, int type,
		java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social equity logs.
	*
	* @return the number of social equity logs
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public SocialEquityLog remove(SocialEquityLog socialEquityLog)
		throws SystemException;
}
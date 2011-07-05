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

import com.liferay.portlet.social.model.SocialEquityLog;

import java.util.List;

/**
 * The persistence utility for the social equity log service. This utility wraps {@link SocialEquityLogPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialEquityLogPersistence
 * @see SocialEquityLogPersistenceImpl
 * @generated
 */
public class SocialEquityLogUtil {
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
	public static void clearCache(SocialEquityLog socialEquityLog) {
		getPersistence().clearCache(socialEquityLog);
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
	public static List<SocialEquityLog> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SocialEquityLog> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SocialEquityLog> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static SocialEquityLog remove(SocialEquityLog socialEquityLog)
		throws SystemException {
		return getPersistence().remove(socialEquityLog);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static SocialEquityLog update(SocialEquityLog socialEquityLog,
		boolean merge) throws SystemException {
		return getPersistence().update(socialEquityLog, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static SocialEquityLog update(SocialEquityLog socialEquityLog,
		boolean merge, ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(socialEquityLog, merge, serviceContext);
	}

	/**
	* Caches the social equity log in the entity cache if it is enabled.
	*
	* @param socialEquityLog the social equity log
	*/
	public static void cacheResult(
		com.liferay.portlet.social.model.SocialEquityLog socialEquityLog) {
		getPersistence().cacheResult(socialEquityLog);
	}

	/**
	* Caches the social equity logs in the entity cache if it is enabled.
	*
	* @param socialEquityLogs the social equity logs
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.social.model.SocialEquityLog> socialEquityLogs) {
		getPersistence().cacheResult(socialEquityLogs);
	}

	/**
	* Creates a new social equity log with the primary key. Does not add the social equity log to the database.
	*
	* @param equityLogId the primary key for the new social equity log
	* @return the new social equity log
	*/
	public static com.liferay.portlet.social.model.SocialEquityLog create(
		long equityLogId) {
		return getPersistence().create(equityLogId);
	}

	/**
	* Removes the social equity log with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param equityLogId the primary key of the social equity log
	* @return the social equity log that was removed
	* @throws com.liferay.portlet.social.NoSuchEquityLogException if a social equity log with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityLog remove(
		long equityLogId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence().remove(equityLogId);
	}

	public static com.liferay.portlet.social.model.SocialEquityLog updateImpl(
		com.liferay.portlet.social.model.SocialEquityLog socialEquityLog,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(socialEquityLog, merge);
	}

	/**
	* Returns the social equity log with the primary key or throws a {@link com.liferay.portlet.social.NoSuchEquityLogException} if it could not be found.
	*
	* @param equityLogId the primary key of the social equity log
	* @return the social equity log
	* @throws com.liferay.portlet.social.NoSuchEquityLogException if a social equity log with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityLog findByPrimaryKey(
		long equityLogId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence().findByPrimaryKey(equityLogId);
	}

	/**
	* Returns the social equity log with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param equityLogId the primary key of the social equity log
	* @return the social equity log, or <code>null</code> if a social equity log with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityLog fetchByPrimaryKey(
		long equityLogId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(equityLogId);
	}

	/**
	* Returns all the social equity logs where assetEntryId = &#63; and type = &#63; and active = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param type the type
	* @param active the active
	* @return the matching social equity logs
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByAEI_T_A(
		long assetEntryId, int type, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByAEI_T_A(assetEntryId, type, active);
	}

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
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByAEI_T_A(
		long assetEntryId, int type, boolean active, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByAEI_T_A(assetEntryId, type, active, start, end);
	}

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
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByAEI_T_A(
		long assetEntryId, int type, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByAEI_T_A(assetEntryId, type, active, start, end,
			orderByComparator);
	}

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
	public static com.liferay.portlet.social.model.SocialEquityLog findByAEI_T_A_First(
		long assetEntryId, int type, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence()
				   .findByAEI_T_A_First(assetEntryId, type, active,
			orderByComparator);
	}

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
	public static com.liferay.portlet.social.model.SocialEquityLog findByAEI_T_A_Last(
		long assetEntryId, int type, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence()
				   .findByAEI_T_A_Last(assetEntryId, type, active,
			orderByComparator);
	}

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
	public static com.liferay.portlet.social.model.SocialEquityLog[] findByAEI_T_A_PrevAndNext(
		long equityLogId, long assetEntryId, int type, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence()
				   .findByAEI_T_A_PrevAndNext(equityLogId, assetEntryId, type,
			active, orderByComparator);
	}

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
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByAEI_AID_A_E(
		long assetEntryId, java.lang.String actionId, boolean active,
		java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByAEI_AID_A_E(assetEntryId, actionId, active, extraData);
	}

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
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByAEI_AID_A_E(
		long assetEntryId, java.lang.String actionId, boolean active,
		java.lang.String extraData, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByAEI_AID_A_E(assetEntryId, actionId, active,
			extraData, start, end);
	}

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
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByAEI_AID_A_E(
		long assetEntryId, java.lang.String actionId, boolean active,
		java.lang.String extraData, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByAEI_AID_A_E(assetEntryId, actionId, active,
			extraData, start, end, orderByComparator);
	}

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
	public static com.liferay.portlet.social.model.SocialEquityLog findByAEI_AID_A_E_First(
		long assetEntryId, java.lang.String actionId, boolean active,
		java.lang.String extraData,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence()
				   .findByAEI_AID_A_E_First(assetEntryId, actionId, active,
			extraData, orderByComparator);
	}

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
	public static com.liferay.portlet.social.model.SocialEquityLog findByAEI_AID_A_E_Last(
		long assetEntryId, java.lang.String actionId, boolean active,
		java.lang.String extraData,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence()
				   .findByAEI_AID_A_E_Last(assetEntryId, actionId, active,
			extraData, orderByComparator);
	}

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
	public static com.liferay.portlet.social.model.SocialEquityLog[] findByAEI_AID_A_E_PrevAndNext(
		long equityLogId, long assetEntryId, java.lang.String actionId,
		boolean active, java.lang.String extraData,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence()
				   .findByAEI_AID_A_E_PrevAndNext(equityLogId, assetEntryId,
			actionId, active, extraData, orderByComparator);
	}

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
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByU_AEI_AID_A_E(
		long userId, long assetEntryId, java.lang.String actionId,
		boolean active, java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByU_AEI_AID_A_E(userId, assetEntryId, actionId, active,
			extraData);
	}

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
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByU_AEI_AID_A_E(
		long userId, long assetEntryId, java.lang.String actionId,
		boolean active, java.lang.String extraData, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByU_AEI_AID_A_E(userId, assetEntryId, actionId, active,
			extraData, start, end);
	}

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
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByU_AEI_AID_A_E(
		long userId, long assetEntryId, java.lang.String actionId,
		boolean active, java.lang.String extraData, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByU_AEI_AID_A_E(userId, assetEntryId, actionId, active,
			extraData, start, end, orderByComparator);
	}

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
	public static com.liferay.portlet.social.model.SocialEquityLog findByU_AEI_AID_A_E_First(
		long userId, long assetEntryId, java.lang.String actionId,
		boolean active, java.lang.String extraData,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence()
				   .findByU_AEI_AID_A_E_First(userId, assetEntryId, actionId,
			active, extraData, orderByComparator);
	}

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
	public static com.liferay.portlet.social.model.SocialEquityLog findByU_AEI_AID_A_E_Last(
		long userId, long assetEntryId, java.lang.String actionId,
		boolean active, java.lang.String extraData,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence()
				   .findByU_AEI_AID_A_E_Last(userId, assetEntryId, actionId,
			active, extraData, orderByComparator);
	}

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
	public static com.liferay.portlet.social.model.SocialEquityLog[] findByU_AEI_AID_A_E_PrevAndNext(
		long equityLogId, long userId, long assetEntryId,
		java.lang.String actionId, boolean active, java.lang.String extraData,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence()
				   .findByU_AEI_AID_A_E_PrevAndNext(equityLogId, userId,
			assetEntryId, actionId, active, extraData, orderByComparator);
	}

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
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByU_AID_AD_A_T_E(
		long userId, java.lang.String actionId, int actionDate, boolean active,
		int type, java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByU_AID_AD_A_T_E(userId, actionId, actionDate, active,
			type, extraData);
	}

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
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByU_AID_AD_A_T_E(
		long userId, java.lang.String actionId, int actionDate, boolean active,
		int type, java.lang.String extraData, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByU_AID_AD_A_T_E(userId, actionId, actionDate, active,
			type, extraData, start, end);
	}

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
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByU_AID_AD_A_T_E(
		long userId, java.lang.String actionId, int actionDate, boolean active,
		int type, java.lang.String extraData, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByU_AID_AD_A_T_E(userId, actionId, actionDate, active,
			type, extraData, start, end, orderByComparator);
	}

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
	public static com.liferay.portlet.social.model.SocialEquityLog findByU_AID_AD_A_T_E_First(
		long userId, java.lang.String actionId, int actionDate, boolean active,
		int type, java.lang.String extraData,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence()
				   .findByU_AID_AD_A_T_E_First(userId, actionId, actionDate,
			active, type, extraData, orderByComparator);
	}

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
	public static com.liferay.portlet.social.model.SocialEquityLog findByU_AID_AD_A_T_E_Last(
		long userId, java.lang.String actionId, int actionDate, boolean active,
		int type, java.lang.String extraData,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence()
				   .findByU_AID_AD_A_T_E_Last(userId, actionId, actionDate,
			active, type, extraData, orderByComparator);
	}

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
	public static com.liferay.portlet.social.model.SocialEquityLog[] findByU_AID_AD_A_T_E_PrevAndNext(
		long equityLogId, long userId, java.lang.String actionId,
		int actionDate, boolean active, int type, java.lang.String extraData,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence()
				   .findByU_AID_AD_A_T_E_PrevAndNext(equityLogId, userId,
			actionId, actionDate, active, type, extraData, orderByComparator);
	}

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
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByAEI_AID_AD_A_T_E(
		long assetEntryId, java.lang.String actionId, int actionDate,
		boolean active, int type, java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByAEI_AID_AD_A_T_E(assetEntryId, actionId, actionDate,
			active, type, extraData);
	}

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
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByAEI_AID_AD_A_T_E(
		long assetEntryId, java.lang.String actionId, int actionDate,
		boolean active, int type, java.lang.String extraData, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByAEI_AID_AD_A_T_E(assetEntryId, actionId, actionDate,
			active, type, extraData, start, end);
	}

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
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByAEI_AID_AD_A_T_E(
		long assetEntryId, java.lang.String actionId, int actionDate,
		boolean active, int type, java.lang.String extraData, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByAEI_AID_AD_A_T_E(assetEntryId, actionId, actionDate,
			active, type, extraData, start, end, orderByComparator);
	}

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
	public static com.liferay.portlet.social.model.SocialEquityLog findByAEI_AID_AD_A_T_E_First(
		long assetEntryId, java.lang.String actionId, int actionDate,
		boolean active, int type, java.lang.String extraData,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence()
				   .findByAEI_AID_AD_A_T_E_First(assetEntryId, actionId,
			actionDate, active, type, extraData, orderByComparator);
	}

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
	public static com.liferay.portlet.social.model.SocialEquityLog findByAEI_AID_AD_A_T_E_Last(
		long assetEntryId, java.lang.String actionId, int actionDate,
		boolean active, int type, java.lang.String extraData,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence()
				   .findByAEI_AID_AD_A_T_E_Last(assetEntryId, actionId,
			actionDate, active, type, extraData, orderByComparator);
	}

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
	public static com.liferay.portlet.social.model.SocialEquityLog[] findByAEI_AID_AD_A_T_E_PrevAndNext(
		long equityLogId, long assetEntryId, java.lang.String actionId,
		int actionDate, boolean active, int type, java.lang.String extraData,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence()
				   .findByAEI_AID_AD_A_T_E_PrevAndNext(equityLogId,
			assetEntryId, actionId, actionDate, active, type, extraData,
			orderByComparator);
	}

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
	public static com.liferay.portlet.social.model.SocialEquityLog findByU_AEI_AID_AD_A_T_E(
		long userId, long assetEntryId, java.lang.String actionId,
		int actionDate, boolean active, int type, java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence()
				   .findByU_AEI_AID_AD_A_T_E(userId, assetEntryId, actionId,
			actionDate, active, type, extraData);
	}

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
	public static com.liferay.portlet.social.model.SocialEquityLog fetchByU_AEI_AID_AD_A_T_E(
		long userId, long assetEntryId, java.lang.String actionId,
		int actionDate, boolean active, int type, java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByU_AEI_AID_AD_A_T_E(userId, assetEntryId, actionId,
			actionDate, active, type, extraData);
	}

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
	public static com.liferay.portlet.social.model.SocialEquityLog fetchByU_AEI_AID_AD_A_T_E(
		long userId, long assetEntryId, java.lang.String actionId,
		int actionDate, boolean active, int type, java.lang.String extraData,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByU_AEI_AID_AD_A_T_E(userId, assetEntryId, actionId,
			actionDate, active, type, extraData, retrieveFromCache);
	}

	/**
	* Returns all the social equity logs.
	*
	* @return the social equity logs
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

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
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

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
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the social equity logs where assetEntryId = &#63; and type = &#63; and active = &#63; from the database.
	*
	* @param assetEntryId the asset entry ID
	* @param type the type
	* @param active the active
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByAEI_T_A(long assetEntryId, int type,
		boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByAEI_T_A(assetEntryId, type, active);
	}

	/**
	* Removes all the social equity logs where assetEntryId = &#63; and actionId = &#63; and active = &#63; and extraData = &#63; from the database.
	*
	* @param assetEntryId the asset entry ID
	* @param actionId the action ID
	* @param active the active
	* @param extraData the extra data
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByAEI_AID_A_E(long assetEntryId,
		java.lang.String actionId, boolean active, java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence()
			.removeByAEI_AID_A_E(assetEntryId, actionId, active, extraData);
	}

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
	public static void removeByU_AEI_AID_A_E(long userId, long assetEntryId,
		java.lang.String actionId, boolean active, java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence()
			.removeByU_AEI_AID_A_E(userId, assetEntryId, actionId, active,
			extraData);
	}

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
	public static void removeByU_AID_AD_A_T_E(long userId,
		java.lang.String actionId, int actionDate, boolean active, int type,
		java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence()
			.removeByU_AID_AD_A_T_E(userId, actionId, actionDate, active, type,
			extraData);
	}

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
	public static void removeByAEI_AID_AD_A_T_E(long assetEntryId,
		java.lang.String actionId, int actionDate, boolean active, int type,
		java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence()
			.removeByAEI_AID_AD_A_T_E(assetEntryId, actionId, actionDate,
			active, type, extraData);
	}

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
	public static void removeByU_AEI_AID_AD_A_T_E(long userId,
		long assetEntryId, java.lang.String actionId, int actionDate,
		boolean active, int type, java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		getPersistence()
			.removeByU_AEI_AID_AD_A_T_E(userId, assetEntryId, actionId,
			actionDate, active, type, extraData);
	}

	/**
	* Removes all the social equity logs from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of social equity logs where assetEntryId = &#63; and type = &#63; and active = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param type the type
	* @param active the active
	* @return the number of matching social equity logs
	* @throws SystemException if a system exception occurred
	*/
	public static int countByAEI_T_A(long assetEntryId, int type, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByAEI_T_A(assetEntryId, type, active);
	}

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
	public static int countByAEI_AID_A_E(long assetEntryId,
		java.lang.String actionId, boolean active, java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByAEI_AID_A_E(assetEntryId, actionId, active, extraData);
	}

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
	public static int countByU_AEI_AID_A_E(long userId, long assetEntryId,
		java.lang.String actionId, boolean active, java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByU_AEI_AID_A_E(userId, assetEntryId, actionId,
			active, extraData);
	}

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
	public static int countByU_AID_AD_A_T_E(long userId,
		java.lang.String actionId, int actionDate, boolean active, int type,
		java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByU_AID_AD_A_T_E(userId, actionId, actionDate, active,
			type, extraData);
	}

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
	public static int countByAEI_AID_AD_A_T_E(long assetEntryId,
		java.lang.String actionId, int actionDate, boolean active, int type,
		java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByAEI_AID_AD_A_T_E(assetEntryId, actionId, actionDate,
			active, type, extraData);
	}

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
	public static int countByU_AEI_AID_AD_A_T_E(long userId, long assetEntryId,
		java.lang.String actionId, int actionDate, boolean active, int type,
		java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByU_AEI_AID_AD_A_T_E(userId, assetEntryId, actionId,
			actionDate, active, type, extraData);
	}

	/**
	* Returns the number of social equity logs.
	*
	* @return the number of social equity logs
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static SocialEquityLogPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (SocialEquityLogPersistence)PortalBeanLocatorUtil.locate(SocialEquityLogPersistence.class.getName());

			ReferenceRegistry.registerReference(SocialEquityLogUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(SocialEquityLogPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(SocialEquityLogUtil.class,
			"_persistence");
	}

	private static SocialEquityLogPersistence _persistence;
}
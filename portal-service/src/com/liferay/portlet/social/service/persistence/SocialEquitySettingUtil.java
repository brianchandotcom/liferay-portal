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

import com.liferay.portlet.social.model.SocialEquitySetting;

import java.util.List;

/**
 * The persistence utility for the social equity setting service. This utility wraps {@link SocialEquitySettingPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialEquitySettingPersistence
 * @see SocialEquitySettingPersistenceImpl
 * @generated
 */
public class SocialEquitySettingUtil {
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
	public static void clearCache(SocialEquitySetting socialEquitySetting) {
		getPersistence().clearCache(socialEquitySetting);
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
	public static List<SocialEquitySetting> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SocialEquitySetting> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SocialEquitySetting> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static SocialEquitySetting remove(
		SocialEquitySetting socialEquitySetting) throws SystemException {
		return getPersistence().remove(socialEquitySetting);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static SocialEquitySetting update(
		SocialEquitySetting socialEquitySetting, boolean merge)
		throws SystemException {
		return getPersistence().update(socialEquitySetting, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static SocialEquitySetting update(
		SocialEquitySetting socialEquitySetting, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence()
				   .update(socialEquitySetting, merge, serviceContext);
	}

	/**
	* Caches the social equity setting in the entity cache if it is enabled.
	*
	* @param socialEquitySetting the social equity setting
	*/
	public static void cacheResult(
		com.liferay.portlet.social.model.SocialEquitySetting socialEquitySetting) {
		getPersistence().cacheResult(socialEquitySetting);
	}

	/**
	* Caches the social equity settings in the entity cache if it is enabled.
	*
	* @param socialEquitySettings the social equity settings
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.social.model.SocialEquitySetting> socialEquitySettings) {
		getPersistence().cacheResult(socialEquitySettings);
	}

	/**
	* Creates a new social equity setting with the primary key. Does not add the social equity setting to the database.
	*
	* @param equitySettingId the primary key for the new social equity setting
	* @return the new social equity setting
	*/
	public static com.liferay.portlet.social.model.SocialEquitySetting create(
		long equitySettingId) {
		return getPersistence().create(equitySettingId);
	}

	/**
	* Removes the social equity setting with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param equitySettingId the primary key of the social equity setting
	* @return the social equity setting that was removed
	* @throws com.liferay.portlet.social.NoSuchEquitySettingException if a social equity setting with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquitySetting remove(
		long equitySettingId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquitySettingException {
		return getPersistence().remove(equitySettingId);
	}

	public static com.liferay.portlet.social.model.SocialEquitySetting updateImpl(
		com.liferay.portlet.social.model.SocialEquitySetting socialEquitySetting,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(socialEquitySetting, merge);
	}

	/**
	* Returns the social equity setting with the primary key or throws a {@link com.liferay.portlet.social.NoSuchEquitySettingException} if it could not be found.
	*
	* @param equitySettingId the primary key of the social equity setting
	* @return the social equity setting
	* @throws com.liferay.portlet.social.NoSuchEquitySettingException if a social equity setting with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquitySetting findByPrimaryKey(
		long equitySettingId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquitySettingException {
		return getPersistence().findByPrimaryKey(equitySettingId);
	}

	/**
	* Returns the social equity setting with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param equitySettingId the primary key of the social equity setting
	* @return the social equity setting, or <code>null</code> if a social equity setting with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquitySetting fetchByPrimaryKey(
		long equitySettingId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(equitySettingId);
	}

	/**
	* Returns all the social equity settings where groupId = &#63; and classNameId = &#63; and actionId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param actionId the action ID
	* @return the matching social equity settings
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialEquitySetting> findByG_C_A(
		long groupId, long classNameId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_C_A(groupId, classNameId, actionId);
	}

	/**
	* Returns a range of all the social equity settings where groupId = &#63; and classNameId = &#63; and actionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param actionId the action ID
	* @param start the lower bound of the range of social equity settings
	* @param end the upper bound of the range of social equity settings (not inclusive)
	* @return the range of matching social equity settings
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialEquitySetting> findByG_C_A(
		long groupId, long classNameId, java.lang.String actionId, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C_A(groupId, classNameId, actionId, start, end);
	}

	/**
	* Returns an ordered range of all the social equity settings where groupId = &#63; and classNameId = &#63; and actionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param actionId the action ID
	* @param start the lower bound of the range of social equity settings
	* @param end the upper bound of the range of social equity settings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social equity settings
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialEquitySetting> findByG_C_A(
		long groupId, long classNameId, java.lang.String actionId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C_A(groupId, classNameId, actionId, start, end,
			orderByComparator);
	}

	/**
	* Returns the first social equity setting in the ordered set where groupId = &#63; and classNameId = &#63; and actionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param actionId the action ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social equity setting
	* @throws com.liferay.portlet.social.NoSuchEquitySettingException if a matching social equity setting could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquitySetting findByG_C_A_First(
		long groupId, long classNameId, java.lang.String actionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquitySettingException {
		return getPersistence()
				   .findByG_C_A_First(groupId, classNameId, actionId,
			orderByComparator);
	}

	/**
	* Returns the last social equity setting in the ordered set where groupId = &#63; and classNameId = &#63; and actionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param actionId the action ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social equity setting
	* @throws com.liferay.portlet.social.NoSuchEquitySettingException if a matching social equity setting could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquitySetting findByG_C_A_Last(
		long groupId, long classNameId, java.lang.String actionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquitySettingException {
		return getPersistence()
				   .findByG_C_A_Last(groupId, classNameId, actionId,
			orderByComparator);
	}

	/**
	* Returns the social equity settings before and after the current social equity setting in the ordered set where groupId = &#63; and classNameId = &#63; and actionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param equitySettingId the primary key of the current social equity setting
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param actionId the action ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social equity setting
	* @throws com.liferay.portlet.social.NoSuchEquitySettingException if a social equity setting with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquitySetting[] findByG_C_A_PrevAndNext(
		long equitySettingId, long groupId, long classNameId,
		java.lang.String actionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquitySettingException {
		return getPersistence()
				   .findByG_C_A_PrevAndNext(equitySettingId, groupId,
			classNameId, actionId, orderByComparator);
	}

	/**
	* Returns the social equity setting where groupId = &#63; and classNameId = &#63; and actionId = &#63; and type = &#63; or throws a {@link com.liferay.portlet.social.NoSuchEquitySettingException} if it could not be found.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param actionId the action ID
	* @param type the type
	* @return the matching social equity setting
	* @throws com.liferay.portlet.social.NoSuchEquitySettingException if a matching social equity setting could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquitySetting findByG_C_A_T(
		long groupId, long classNameId, java.lang.String actionId, int type)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquitySettingException {
		return getPersistence()
				   .findByG_C_A_T(groupId, classNameId, actionId, type);
	}

	/**
	* Returns the social equity setting where groupId = &#63; and classNameId = &#63; and actionId = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param actionId the action ID
	* @param type the type
	* @return the matching social equity setting, or <code>null</code> if a matching social equity setting could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquitySetting fetchByG_C_A_T(
		long groupId, long classNameId, java.lang.String actionId, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_C_A_T(groupId, classNameId, actionId, type);
	}

	/**
	* Returns the social equity setting where groupId = &#63; and classNameId = &#63; and actionId = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param actionId the action ID
	* @param type the type
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching social equity setting, or <code>null</code> if a matching social equity setting could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquitySetting fetchByG_C_A_T(
		long groupId, long classNameId, java.lang.String actionId, int type,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_C_A_T(groupId, classNameId, actionId, type,
			retrieveFromCache);
	}

	/**
	* Returns all the social equity settings.
	*
	* @return the social equity settings
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialEquitySetting> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the social equity settings.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of social equity settings
	* @param end the upper bound of the range of social equity settings (not inclusive)
	* @return the range of social equity settings
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialEquitySetting> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the social equity settings.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of social equity settings
	* @param end the upper bound of the range of social equity settings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of social equity settings
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialEquitySetting> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the social equity settings where groupId = &#63; and classNameId = &#63; and actionId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param actionId the action ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_C_A(long groupId, long classNameId,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_C_A(groupId, classNameId, actionId);
	}

	/**
	* Removes the social equity setting where groupId = &#63; and classNameId = &#63; and actionId = &#63; and type = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param actionId the action ID
	* @param type the type
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_C_A_T(long groupId, long classNameId,
		java.lang.String actionId, int type)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquitySettingException {
		getPersistence().removeByG_C_A_T(groupId, classNameId, actionId, type);
	}

	/**
	* Removes all the social equity settings from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of social equity settings where groupId = &#63; and classNameId = &#63; and actionId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param actionId the action ID
	* @return the number of matching social equity settings
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_C_A(long groupId, long classNameId,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_C_A(groupId, classNameId, actionId);
	}

	/**
	* Returns the number of social equity settings where groupId = &#63; and classNameId = &#63; and actionId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param actionId the action ID
	* @param type the type
	* @return the number of matching social equity settings
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_C_A_T(long groupId, long classNameId,
		java.lang.String actionId, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByG_C_A_T(groupId, classNameId, actionId, type);
	}

	/**
	* Returns the number of social equity settings.
	*
	* @return the number of social equity settings
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static SocialEquitySettingPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (SocialEquitySettingPersistence)PortalBeanLocatorUtil.locate(SocialEquitySettingPersistence.class.getName());

			ReferenceRegistry.registerReference(SocialEquitySettingUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(SocialEquitySettingPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(SocialEquitySettingUtil.class,
			"_persistence");
	}

	private static SocialEquitySettingPersistence _persistence;
}
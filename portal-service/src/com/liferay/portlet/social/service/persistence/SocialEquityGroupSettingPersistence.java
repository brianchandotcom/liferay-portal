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

import com.liferay.portlet.social.model.SocialEquityGroupSetting;

/**
 * The persistence interface for the social equity group setting service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialEquityGroupSettingPersistenceImpl
 * @see SocialEquityGroupSettingUtil
 * @generated
 */
public interface SocialEquityGroupSettingPersistence extends BasePersistence<SocialEquityGroupSetting> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SocialEquityGroupSettingUtil} to access the social equity group setting persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the social equity group setting in the entity cache if it is enabled.
	*
	* @param socialEquityGroupSetting the social equity group setting
	*/
	public void cacheResult(
		com.liferay.portlet.social.model.SocialEquityGroupSetting socialEquityGroupSetting);

	/**
	* Caches the social equity group settings in the entity cache if it is enabled.
	*
	* @param socialEquityGroupSettings the social equity group settings
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.social.model.SocialEquityGroupSetting> socialEquityGroupSettings);

	/**
	* Creates a new social equity group setting with the primary key. Does not add the social equity group setting to the database.
	*
	* @param equityGroupSettingId the primary key for the new social equity group setting
	* @return the new social equity group setting
	*/
	public com.liferay.portlet.social.model.SocialEquityGroupSetting create(
		long equityGroupSettingId);

	/**
	* Removes the social equity group setting with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param equityGroupSettingId the primary key of the social equity group setting
	* @return the social equity group setting that was removed
	* @throws com.liferay.portlet.social.NoSuchEquityGroupSettingException if a social equity group setting with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityGroupSetting remove(
		long equityGroupSettingId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityGroupSettingException;

	public com.liferay.portlet.social.model.SocialEquityGroupSetting updateImpl(
		com.liferay.portlet.social.model.SocialEquityGroupSetting socialEquityGroupSetting,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the social equity group setting with the primary key or throws a {@link com.liferay.portlet.social.NoSuchEquityGroupSettingException} if it could not be found.
	*
	* @param equityGroupSettingId the primary key of the social equity group setting
	* @return the social equity group setting
	* @throws com.liferay.portlet.social.NoSuchEquityGroupSettingException if a social equity group setting with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityGroupSetting findByPrimaryKey(
		long equityGroupSettingId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityGroupSettingException;

	/**
	* Returns the social equity group setting with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param equityGroupSettingId the primary key of the social equity group setting
	* @return the social equity group setting, or <code>null</code> if a social equity group setting with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityGroupSetting fetchByPrimaryKey(
		long equityGroupSettingId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the social equity group setting where groupId = &#63; and classNameId = &#63; and type = &#63; or throws a {@link com.liferay.portlet.social.NoSuchEquityGroupSettingException} if it could not be found.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param type the type
	* @return the matching social equity group setting
	* @throws com.liferay.portlet.social.NoSuchEquityGroupSettingException if a matching social equity group setting could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityGroupSetting findByG_C_T(
		long groupId, long classNameId, int type)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityGroupSettingException;

	/**
	* Returns the social equity group setting where groupId = &#63; and classNameId = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param type the type
	* @return the matching social equity group setting, or <code>null</code> if a matching social equity group setting could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityGroupSetting fetchByG_C_T(
		long groupId, long classNameId, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the social equity group setting where groupId = &#63; and classNameId = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param type the type
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching social equity group setting, or <code>null</code> if a matching social equity group setting could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityGroupSetting fetchByG_C_T(
		long groupId, long classNameId, int type, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the social equity group settings.
	*
	* @return the social equity group settings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialEquityGroupSetting> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the social equity group settings.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of social equity group settings
	* @param end the upper bound of the range of social equity group settings (not inclusive)
	* @return the range of social equity group settings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialEquityGroupSetting> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the social equity group settings.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of social equity group settings
	* @param end the upper bound of the range of social equity group settings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of social equity group settings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialEquityGroupSetting> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the social equity group setting where groupId = &#63; and classNameId = &#63; and type = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param type the type
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_C_T(long groupId, long classNameId, int type)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityGroupSettingException;

	/**
	* Removes all the social equity group settings from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social equity group settings where groupId = &#63; and classNameId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param type the type
	* @return the number of matching social equity group settings
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_C_T(long groupId, long classNameId, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social equity group settings.
	*
	* @return the number of social equity group settings
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public SocialEquityGroupSetting remove(
		SocialEquityGroupSetting socialEquityGroupSetting)
		throws SystemException;
}
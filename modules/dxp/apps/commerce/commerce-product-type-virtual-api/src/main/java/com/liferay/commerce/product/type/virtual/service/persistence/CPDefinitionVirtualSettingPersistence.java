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

package com.liferay.commerce.product.type.virtual.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.type.virtual.exception.NoSuchCPDefinitionVirtualSettingException;
import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the cp definition virtual setting service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.type.virtual.service.persistence.impl.CPDefinitionVirtualSettingPersistenceImpl
 * @see CPDefinitionVirtualSettingUtil
 * @generated
 */
@ProviderType
public interface CPDefinitionVirtualSettingPersistence extends BasePersistence<CPDefinitionVirtualSetting> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPDefinitionVirtualSettingUtil} to access the cp definition virtual setting persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the cp definition virtual settings where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cp definition virtual settings
	*/
	public java.util.List<CPDefinitionVirtualSetting> findByUuid(String uuid);

	/**
	* Returns a range of all the cp definition virtual settings where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionVirtualSettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp definition virtual settings
	* @param end the upper bound of the range of cp definition virtual settings (not inclusive)
	* @return the range of matching cp definition virtual settings
	*/
	public java.util.List<CPDefinitionVirtualSetting> findByUuid(String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the cp definition virtual settings where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionVirtualSettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp definition virtual settings
	* @param end the upper bound of the range of cp definition virtual settings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition virtual settings
	*/
	public java.util.List<CPDefinitionVirtualSetting> findByUuid(String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionVirtualSetting> orderByComparator);

	/**
	* Returns an ordered range of all the cp definition virtual settings where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionVirtualSettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp definition virtual settings
	* @param end the upper bound of the range of cp definition virtual settings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition virtual settings
	*/
	public java.util.List<CPDefinitionVirtualSetting> findByUuid(String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionVirtualSetting> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp definition virtual setting in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition virtual setting
	* @throws NoSuchCPDefinitionVirtualSettingException if a matching cp definition virtual setting could not be found
	*/
	public CPDefinitionVirtualSetting findByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionVirtualSetting> orderByComparator)
		throws NoSuchCPDefinitionVirtualSettingException;

	/**
	* Returns the first cp definition virtual setting in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition virtual setting, or <code>null</code> if a matching cp definition virtual setting could not be found
	*/
	public CPDefinitionVirtualSetting fetchByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionVirtualSetting> orderByComparator);

	/**
	* Returns the last cp definition virtual setting in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition virtual setting
	* @throws NoSuchCPDefinitionVirtualSettingException if a matching cp definition virtual setting could not be found
	*/
	public CPDefinitionVirtualSetting findByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionVirtualSetting> orderByComparator)
		throws NoSuchCPDefinitionVirtualSettingException;

	/**
	* Returns the last cp definition virtual setting in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition virtual setting, or <code>null</code> if a matching cp definition virtual setting could not be found
	*/
	public CPDefinitionVirtualSetting fetchByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionVirtualSetting> orderByComparator);

	/**
	* Returns the cp definition virtual settings before and after the current cp definition virtual setting in the ordered set where uuid = &#63;.
	*
	* @param CPDefinitionVirtualSettingId the primary key of the current cp definition virtual setting
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition virtual setting
	* @throws NoSuchCPDefinitionVirtualSettingException if a cp definition virtual setting with the primary key could not be found
	*/
	public CPDefinitionVirtualSetting[] findByUuid_PrevAndNext(
		long CPDefinitionVirtualSettingId, String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionVirtualSetting> orderByComparator)
		throws NoSuchCPDefinitionVirtualSettingException;

	/**
	* Removes all the cp definition virtual settings where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(String uuid);

	/**
	* Returns the number of cp definition virtual settings where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cp definition virtual settings
	*/
	public int countByUuid(String uuid);

	/**
	* Returns the cp definition virtual setting where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPDefinitionVirtualSettingException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp definition virtual setting
	* @throws NoSuchCPDefinitionVirtualSettingException if a matching cp definition virtual setting could not be found
	*/
	public CPDefinitionVirtualSetting findByUUID_G(String uuid, long groupId)
		throws NoSuchCPDefinitionVirtualSettingException;

	/**
	* Returns the cp definition virtual setting where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp definition virtual setting, or <code>null</code> if a matching cp definition virtual setting could not be found
	*/
	public CPDefinitionVirtualSetting fetchByUUID_G(String uuid, long groupId);

	/**
	* Returns the cp definition virtual setting where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp definition virtual setting, or <code>null</code> if a matching cp definition virtual setting could not be found
	*/
	public CPDefinitionVirtualSetting fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the cp definition virtual setting where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cp definition virtual setting that was removed
	*/
	public CPDefinitionVirtualSetting removeByUUID_G(String uuid, long groupId)
		throws NoSuchCPDefinitionVirtualSettingException;

	/**
	* Returns the number of cp definition virtual settings where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cp definition virtual settings
	*/
	public int countByUUID_G(String uuid, long groupId);

	/**
	* Returns all the cp definition virtual settings where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cp definition virtual settings
	*/
	public java.util.List<CPDefinitionVirtualSetting> findByUuid_C(
		String uuid, long companyId);

	/**
	* Returns a range of all the cp definition virtual settings where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionVirtualSettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition virtual settings
	* @param end the upper bound of the range of cp definition virtual settings (not inclusive)
	* @return the range of matching cp definition virtual settings
	*/
	public java.util.List<CPDefinitionVirtualSetting> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the cp definition virtual settings where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionVirtualSettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition virtual settings
	* @param end the upper bound of the range of cp definition virtual settings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition virtual settings
	*/
	public java.util.List<CPDefinitionVirtualSetting> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionVirtualSetting> orderByComparator);

	/**
	* Returns an ordered range of all the cp definition virtual settings where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionVirtualSettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition virtual settings
	* @param end the upper bound of the range of cp definition virtual settings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition virtual settings
	*/
	public java.util.List<CPDefinitionVirtualSetting> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionVirtualSetting> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp definition virtual setting in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition virtual setting
	* @throws NoSuchCPDefinitionVirtualSettingException if a matching cp definition virtual setting could not be found
	*/
	public CPDefinitionVirtualSetting findByUuid_C_First(String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionVirtualSetting> orderByComparator)
		throws NoSuchCPDefinitionVirtualSettingException;

	/**
	* Returns the first cp definition virtual setting in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition virtual setting, or <code>null</code> if a matching cp definition virtual setting could not be found
	*/
	public CPDefinitionVirtualSetting fetchByUuid_C_First(String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionVirtualSetting> orderByComparator);

	/**
	* Returns the last cp definition virtual setting in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition virtual setting
	* @throws NoSuchCPDefinitionVirtualSettingException if a matching cp definition virtual setting could not be found
	*/
	public CPDefinitionVirtualSetting findByUuid_C_Last(String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionVirtualSetting> orderByComparator)
		throws NoSuchCPDefinitionVirtualSettingException;

	/**
	* Returns the last cp definition virtual setting in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition virtual setting, or <code>null</code> if a matching cp definition virtual setting could not be found
	*/
	public CPDefinitionVirtualSetting fetchByUuid_C_Last(String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionVirtualSetting> orderByComparator);

	/**
	* Returns the cp definition virtual settings before and after the current cp definition virtual setting in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CPDefinitionVirtualSettingId the primary key of the current cp definition virtual setting
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition virtual setting
	* @throws NoSuchCPDefinitionVirtualSettingException if a cp definition virtual setting with the primary key could not be found
	*/
	public CPDefinitionVirtualSetting[] findByUuid_C_PrevAndNext(
		long CPDefinitionVirtualSettingId, String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionVirtualSetting> orderByComparator)
		throws NoSuchCPDefinitionVirtualSettingException;

	/**
	* Removes all the cp definition virtual settings where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(String uuid, long companyId);

	/**
	* Returns the number of cp definition virtual settings where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cp definition virtual settings
	*/
	public int countByUuid_C(String uuid, long companyId);

	/**
	* Returns the cp definition virtual setting where CPDefinitionId = &#63; or throws a {@link NoSuchCPDefinitionVirtualSettingException} if it could not be found.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the matching cp definition virtual setting
	* @throws NoSuchCPDefinitionVirtualSettingException if a matching cp definition virtual setting could not be found
	*/
	public CPDefinitionVirtualSetting findByCPDefinitionId(long CPDefinitionId)
		throws NoSuchCPDefinitionVirtualSettingException;

	/**
	* Returns the cp definition virtual setting where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the matching cp definition virtual setting, or <code>null</code> if a matching cp definition virtual setting could not be found
	*/
	public CPDefinitionVirtualSetting fetchByCPDefinitionId(long CPDefinitionId);

	/**
	* Returns the cp definition virtual setting where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param CPDefinitionId the cp definition ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp definition virtual setting, or <code>null</code> if a matching cp definition virtual setting could not be found
	*/
	public CPDefinitionVirtualSetting fetchByCPDefinitionId(
		long CPDefinitionId, boolean retrieveFromCache);

	/**
	* Removes the cp definition virtual setting where CPDefinitionId = &#63; from the database.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the cp definition virtual setting that was removed
	*/
	public CPDefinitionVirtualSetting removeByCPDefinitionId(
		long CPDefinitionId) throws NoSuchCPDefinitionVirtualSettingException;

	/**
	* Returns the number of cp definition virtual settings where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the number of matching cp definition virtual settings
	*/
	public int countByCPDefinitionId(long CPDefinitionId);

	/**
	* Caches the cp definition virtual setting in the entity cache if it is enabled.
	*
	* @param cpDefinitionVirtualSetting the cp definition virtual setting
	*/
	public void cacheResult(
		CPDefinitionVirtualSetting cpDefinitionVirtualSetting);

	/**
	* Caches the cp definition virtual settings in the entity cache if it is enabled.
	*
	* @param cpDefinitionVirtualSettings the cp definition virtual settings
	*/
	public void cacheResult(
		java.util.List<CPDefinitionVirtualSetting> cpDefinitionVirtualSettings);

	/**
	* Creates a new cp definition virtual setting with the primary key. Does not add the cp definition virtual setting to the database.
	*
	* @param CPDefinitionVirtualSettingId the primary key for the new cp definition virtual setting
	* @return the new cp definition virtual setting
	*/
	public CPDefinitionVirtualSetting create(long CPDefinitionVirtualSettingId);

	/**
	* Removes the cp definition virtual setting with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDefinitionVirtualSettingId the primary key of the cp definition virtual setting
	* @return the cp definition virtual setting that was removed
	* @throws NoSuchCPDefinitionVirtualSettingException if a cp definition virtual setting with the primary key could not be found
	*/
	public CPDefinitionVirtualSetting remove(long CPDefinitionVirtualSettingId)
		throws NoSuchCPDefinitionVirtualSettingException;

	public CPDefinitionVirtualSetting updateImpl(
		CPDefinitionVirtualSetting cpDefinitionVirtualSetting);

	/**
	* Returns the cp definition virtual setting with the primary key or throws a {@link NoSuchCPDefinitionVirtualSettingException} if it could not be found.
	*
	* @param CPDefinitionVirtualSettingId the primary key of the cp definition virtual setting
	* @return the cp definition virtual setting
	* @throws NoSuchCPDefinitionVirtualSettingException if a cp definition virtual setting with the primary key could not be found
	*/
	public CPDefinitionVirtualSetting findByPrimaryKey(
		long CPDefinitionVirtualSettingId)
		throws NoSuchCPDefinitionVirtualSettingException;

	/**
	* Returns the cp definition virtual setting with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPDefinitionVirtualSettingId the primary key of the cp definition virtual setting
	* @return the cp definition virtual setting, or <code>null</code> if a cp definition virtual setting with the primary key could not be found
	*/
	public CPDefinitionVirtualSetting fetchByPrimaryKey(
		long CPDefinitionVirtualSettingId);

	@Override
	public java.util.Map<java.io.Serializable, CPDefinitionVirtualSetting> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the cp definition virtual settings.
	*
	* @return the cp definition virtual settings
	*/
	public java.util.List<CPDefinitionVirtualSetting> findAll();

	/**
	* Returns a range of all the cp definition virtual settings.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionVirtualSettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition virtual settings
	* @param end the upper bound of the range of cp definition virtual settings (not inclusive)
	* @return the range of cp definition virtual settings
	*/
	public java.util.List<CPDefinitionVirtualSetting> findAll(int start, int end);

	/**
	* Returns an ordered range of all the cp definition virtual settings.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionVirtualSettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition virtual settings
	* @param end the upper bound of the range of cp definition virtual settings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cp definition virtual settings
	*/
	public java.util.List<CPDefinitionVirtualSetting> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionVirtualSetting> orderByComparator);

	/**
	* Returns an ordered range of all the cp definition virtual settings.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionVirtualSettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition virtual settings
	* @param end the upper bound of the range of cp definition virtual settings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cp definition virtual settings
	*/
	public java.util.List<CPDefinitionVirtualSetting> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionVirtualSetting> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the cp definition virtual settings from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of cp definition virtual settings.
	*
	* @return the number of cp definition virtual settings
	*/
	public int countAll();

	@Override
	public java.util.Set<String> getBadColumnNames();
}
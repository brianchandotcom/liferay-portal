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

import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the cp definition virtual setting service. This utility wraps {@link com.liferay.commerce.product.type.virtual.service.persistence.impl.CPDefinitionVirtualSettingPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPDefinitionVirtualSettingPersistence
 * @see com.liferay.commerce.product.type.virtual.service.persistence.impl.CPDefinitionVirtualSettingPersistenceImpl
 * @generated
 */
@ProviderType
public class CPDefinitionVirtualSettingUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(
		CPDefinitionVirtualSetting cpDefinitionVirtualSetting) {
		getPersistence().clearCache(cpDefinitionVirtualSetting);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CPDefinitionVirtualSetting> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CPDefinitionVirtualSetting> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CPDefinitionVirtualSetting> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CPDefinitionVirtualSetting> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CPDefinitionVirtualSetting update(
		CPDefinitionVirtualSetting cpDefinitionVirtualSetting) {
		return getPersistence().update(cpDefinitionVirtualSetting);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CPDefinitionVirtualSetting update(
		CPDefinitionVirtualSetting cpDefinitionVirtualSetting,
		ServiceContext serviceContext) {
		return getPersistence()
				   .update(cpDefinitionVirtualSetting, serviceContext);
	}

	/**
	* Returns all the cp definition virtual settings where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cp definition virtual settings
	*/
	public static List<CPDefinitionVirtualSetting> findByUuid(
		java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

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
	public static List<CPDefinitionVirtualSetting> findByUuid(
		java.lang.String uuid, int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

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
	public static List<CPDefinitionVirtualSetting> findByUuid(
		java.lang.String uuid, int start, int end,
		OrderByComparator<CPDefinitionVirtualSetting> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

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
	public static List<CPDefinitionVirtualSetting> findByUuid(
		java.lang.String uuid, int start, int end,
		OrderByComparator<CPDefinitionVirtualSetting> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cp definition virtual setting in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition virtual setting
	* @throws NoSuchCPDefinitionVirtualSettingException if a matching cp definition virtual setting could not be found
	*/
	public static CPDefinitionVirtualSetting findByUuid_First(
		java.lang.String uuid,
		OrderByComparator<CPDefinitionVirtualSetting> orderByComparator)
		throws com.liferay.commerce.product.type.virtual.exception.NoSuchCPDefinitionVirtualSettingException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first cp definition virtual setting in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition virtual setting, or <code>null</code> if a matching cp definition virtual setting could not be found
	*/
	public static CPDefinitionVirtualSetting fetchByUuid_First(
		java.lang.String uuid,
		OrderByComparator<CPDefinitionVirtualSetting> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last cp definition virtual setting in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition virtual setting
	* @throws NoSuchCPDefinitionVirtualSettingException if a matching cp definition virtual setting could not be found
	*/
	public static CPDefinitionVirtualSetting findByUuid_Last(
		java.lang.String uuid,
		OrderByComparator<CPDefinitionVirtualSetting> orderByComparator)
		throws com.liferay.commerce.product.type.virtual.exception.NoSuchCPDefinitionVirtualSettingException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last cp definition virtual setting in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition virtual setting, or <code>null</code> if a matching cp definition virtual setting could not be found
	*/
	public static CPDefinitionVirtualSetting fetchByUuid_Last(
		java.lang.String uuid,
		OrderByComparator<CPDefinitionVirtualSetting> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the cp definition virtual settings before and after the current cp definition virtual setting in the ordered set where uuid = &#63;.
	*
	* @param CPDefinitionVirtualSettingId the primary key of the current cp definition virtual setting
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition virtual setting
	* @throws NoSuchCPDefinitionVirtualSettingException if a cp definition virtual setting with the primary key could not be found
	*/
	public static CPDefinitionVirtualSetting[] findByUuid_PrevAndNext(
		long CPDefinitionVirtualSettingId, java.lang.String uuid,
		OrderByComparator<CPDefinitionVirtualSetting> orderByComparator)
		throws com.liferay.commerce.product.type.virtual.exception.NoSuchCPDefinitionVirtualSettingException {
		return getPersistence()
				   .findByUuid_PrevAndNext(CPDefinitionVirtualSettingId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the cp definition virtual settings where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of cp definition virtual settings where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cp definition virtual settings
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the cp definition virtual setting where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPDefinitionVirtualSettingException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp definition virtual setting
	* @throws NoSuchCPDefinitionVirtualSettingException if a matching cp definition virtual setting could not be found
	*/
	public static CPDefinitionVirtualSetting findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.commerce.product.type.virtual.exception.NoSuchCPDefinitionVirtualSettingException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp definition virtual setting where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp definition virtual setting, or <code>null</code> if a matching cp definition virtual setting could not be found
	*/
	public static CPDefinitionVirtualSetting fetchByUUID_G(
		java.lang.String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp definition virtual setting where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp definition virtual setting, or <code>null</code> if a matching cp definition virtual setting could not be found
	*/
	public static CPDefinitionVirtualSetting fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the cp definition virtual setting where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cp definition virtual setting that was removed
	*/
	public static CPDefinitionVirtualSetting removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.commerce.product.type.virtual.exception.NoSuchCPDefinitionVirtualSettingException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of cp definition virtual settings where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cp definition virtual settings
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the cp definition virtual settings where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cp definition virtual settings
	*/
	public static List<CPDefinitionVirtualSetting> findByUuid_C(
		java.lang.String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

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
	public static List<CPDefinitionVirtualSetting> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

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
	public static List<CPDefinitionVirtualSetting> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CPDefinitionVirtualSetting> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

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
	public static List<CPDefinitionVirtualSetting> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CPDefinitionVirtualSetting> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cp definition virtual setting in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition virtual setting
	* @throws NoSuchCPDefinitionVirtualSettingException if a matching cp definition virtual setting could not be found
	*/
	public static CPDefinitionVirtualSetting findByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<CPDefinitionVirtualSetting> orderByComparator)
		throws com.liferay.commerce.product.type.virtual.exception.NoSuchCPDefinitionVirtualSettingException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first cp definition virtual setting in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition virtual setting, or <code>null</code> if a matching cp definition virtual setting could not be found
	*/
	public static CPDefinitionVirtualSetting fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<CPDefinitionVirtualSetting> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cp definition virtual setting in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition virtual setting
	* @throws NoSuchCPDefinitionVirtualSettingException if a matching cp definition virtual setting could not be found
	*/
	public static CPDefinitionVirtualSetting findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		OrderByComparator<CPDefinitionVirtualSetting> orderByComparator)
		throws com.liferay.commerce.product.type.virtual.exception.NoSuchCPDefinitionVirtualSettingException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cp definition virtual setting in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition virtual setting, or <code>null</code> if a matching cp definition virtual setting could not be found
	*/
	public static CPDefinitionVirtualSetting fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		OrderByComparator<CPDefinitionVirtualSetting> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

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
	public static CPDefinitionVirtualSetting[] findByUuid_C_PrevAndNext(
		long CPDefinitionVirtualSettingId, java.lang.String uuid,
		long companyId,
		OrderByComparator<CPDefinitionVirtualSetting> orderByComparator)
		throws com.liferay.commerce.product.type.virtual.exception.NoSuchCPDefinitionVirtualSettingException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(CPDefinitionVirtualSettingId,
			uuid, companyId, orderByComparator);
	}

	/**
	* Removes all the cp definition virtual settings where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of cp definition virtual settings where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cp definition virtual settings
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns the cp definition virtual setting where CPDefinitionId = &#63; or throws a {@link NoSuchCPDefinitionVirtualSettingException} if it could not be found.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the matching cp definition virtual setting
	* @throws NoSuchCPDefinitionVirtualSettingException if a matching cp definition virtual setting could not be found
	*/
	public static CPDefinitionVirtualSetting findByCPDefinitionId(
		long CPDefinitionId)
		throws com.liferay.commerce.product.type.virtual.exception.NoSuchCPDefinitionVirtualSettingException {
		return getPersistence().findByCPDefinitionId(CPDefinitionId);
	}

	/**
	* Returns the cp definition virtual setting where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the matching cp definition virtual setting, or <code>null</code> if a matching cp definition virtual setting could not be found
	*/
	public static CPDefinitionVirtualSetting fetchByCPDefinitionId(
		long CPDefinitionId) {
		return getPersistence().fetchByCPDefinitionId(CPDefinitionId);
	}

	/**
	* Returns the cp definition virtual setting where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param CPDefinitionId the cp definition ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp definition virtual setting, or <code>null</code> if a matching cp definition virtual setting could not be found
	*/
	public static CPDefinitionVirtualSetting fetchByCPDefinitionId(
		long CPDefinitionId, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByCPDefinitionId(CPDefinitionId, retrieveFromCache);
	}

	/**
	* Removes the cp definition virtual setting where CPDefinitionId = &#63; from the database.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the cp definition virtual setting that was removed
	*/
	public static CPDefinitionVirtualSetting removeByCPDefinitionId(
		long CPDefinitionId)
		throws com.liferay.commerce.product.type.virtual.exception.NoSuchCPDefinitionVirtualSettingException {
		return getPersistence().removeByCPDefinitionId(CPDefinitionId);
	}

	/**
	* Returns the number of cp definition virtual settings where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the number of matching cp definition virtual settings
	*/
	public static int countByCPDefinitionId(long CPDefinitionId) {
		return getPersistence().countByCPDefinitionId(CPDefinitionId);
	}

	/**
	* Caches the cp definition virtual setting in the entity cache if it is enabled.
	*
	* @param cpDefinitionVirtualSetting the cp definition virtual setting
	*/
	public static void cacheResult(
		CPDefinitionVirtualSetting cpDefinitionVirtualSetting) {
		getPersistence().cacheResult(cpDefinitionVirtualSetting);
	}

	/**
	* Caches the cp definition virtual settings in the entity cache if it is enabled.
	*
	* @param cpDefinitionVirtualSettings the cp definition virtual settings
	*/
	public static void cacheResult(
		List<CPDefinitionVirtualSetting> cpDefinitionVirtualSettings) {
		getPersistence().cacheResult(cpDefinitionVirtualSettings);
	}

	/**
	* Creates a new cp definition virtual setting with the primary key. Does not add the cp definition virtual setting to the database.
	*
	* @param CPDefinitionVirtualSettingId the primary key for the new cp definition virtual setting
	* @return the new cp definition virtual setting
	*/
	public static CPDefinitionVirtualSetting create(
		long CPDefinitionVirtualSettingId) {
		return getPersistence().create(CPDefinitionVirtualSettingId);
	}

	/**
	* Removes the cp definition virtual setting with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDefinitionVirtualSettingId the primary key of the cp definition virtual setting
	* @return the cp definition virtual setting that was removed
	* @throws NoSuchCPDefinitionVirtualSettingException if a cp definition virtual setting with the primary key could not be found
	*/
	public static CPDefinitionVirtualSetting remove(
		long CPDefinitionVirtualSettingId)
		throws com.liferay.commerce.product.type.virtual.exception.NoSuchCPDefinitionVirtualSettingException {
		return getPersistence().remove(CPDefinitionVirtualSettingId);
	}

	public static CPDefinitionVirtualSetting updateImpl(
		CPDefinitionVirtualSetting cpDefinitionVirtualSetting) {
		return getPersistence().updateImpl(cpDefinitionVirtualSetting);
	}

	/**
	* Returns the cp definition virtual setting with the primary key or throws a {@link NoSuchCPDefinitionVirtualSettingException} if it could not be found.
	*
	* @param CPDefinitionVirtualSettingId the primary key of the cp definition virtual setting
	* @return the cp definition virtual setting
	* @throws NoSuchCPDefinitionVirtualSettingException if a cp definition virtual setting with the primary key could not be found
	*/
	public static CPDefinitionVirtualSetting findByPrimaryKey(
		long CPDefinitionVirtualSettingId)
		throws com.liferay.commerce.product.type.virtual.exception.NoSuchCPDefinitionVirtualSettingException {
		return getPersistence().findByPrimaryKey(CPDefinitionVirtualSettingId);
	}

	/**
	* Returns the cp definition virtual setting with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPDefinitionVirtualSettingId the primary key of the cp definition virtual setting
	* @return the cp definition virtual setting, or <code>null</code> if a cp definition virtual setting with the primary key could not be found
	*/
	public static CPDefinitionVirtualSetting fetchByPrimaryKey(
		long CPDefinitionVirtualSettingId) {
		return getPersistence().fetchByPrimaryKey(CPDefinitionVirtualSettingId);
	}

	public static java.util.Map<java.io.Serializable, CPDefinitionVirtualSetting> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the cp definition virtual settings.
	*
	* @return the cp definition virtual settings
	*/
	public static List<CPDefinitionVirtualSetting> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<CPDefinitionVirtualSetting> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<CPDefinitionVirtualSetting> findAll(int start, int end,
		OrderByComparator<CPDefinitionVirtualSetting> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<CPDefinitionVirtualSetting> findAll(int start, int end,
		OrderByComparator<CPDefinitionVirtualSetting> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the cp definition virtual settings from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of cp definition virtual settings.
	*
	* @return the number of cp definition virtual settings
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CPDefinitionVirtualSettingPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPDefinitionVirtualSettingPersistence, CPDefinitionVirtualSettingPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPDefinitionVirtualSettingPersistence.class);

		ServiceTracker<CPDefinitionVirtualSettingPersistence, CPDefinitionVirtualSettingPersistence> serviceTracker =
			new ServiceTracker<CPDefinitionVirtualSettingPersistence, CPDefinitionVirtualSettingPersistence>(bundle.getBundleContext(),
				CPDefinitionVirtualSettingPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}
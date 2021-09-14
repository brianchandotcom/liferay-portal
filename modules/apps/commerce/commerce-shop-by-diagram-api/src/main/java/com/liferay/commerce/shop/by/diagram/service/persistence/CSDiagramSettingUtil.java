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

package com.liferay.commerce.shop.by.diagram.service.persistence;

import com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the cs diagram setting service. This utility wraps <code>com.liferay.commerce.shop.by.diagram.service.persistence.impl.CSDiagramSettingPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CSDiagramSettingPersistence
 * @generated
 */
public class CSDiagramSettingUtil {

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
	public static void clearCache(CSDiagramSetting csDiagramSetting) {
		getPersistence().clearCache(csDiagramSetting);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, CSDiagramSetting> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CSDiagramSetting> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CSDiagramSetting> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CSDiagramSetting> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CSDiagramSetting> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CSDiagramSetting update(CSDiagramSetting csDiagramSetting) {
		return getPersistence().update(csDiagramSetting);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CSDiagramSetting update(
		CSDiagramSetting csDiagramSetting, ServiceContext serviceContext) {

		return getPersistence().update(csDiagramSetting, serviceContext);
	}

	/**
	 * Returns all the cs diagram settings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cs diagram settings
	 */
	public static List<CSDiagramSetting> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the cs diagram settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cs diagram settings
	 * @param end the upper bound of the range of cs diagram settings (not inclusive)
	 * @return the range of matching cs diagram settings
	 */
	public static List<CSDiagramSetting> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the cs diagram settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cs diagram settings
	 * @param end the upper bound of the range of cs diagram settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cs diagram settings
	 */
	public static List<CSDiagramSetting> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CSDiagramSetting> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cs diagram settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cs diagram settings
	 * @param end the upper bound of the range of cs diagram settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cs diagram settings
	 */
	public static List<CSDiagramSetting> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CSDiagramSetting> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cs diagram setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cs diagram setting
	 * @throws NoSuchCSDiagramSettingException if a matching cs diagram setting could not be found
	 */
	public static CSDiagramSetting findByUuid_First(
			String uuid, OrderByComparator<CSDiagramSetting> orderByComparator)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCSDiagramSettingException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first cs diagram setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cs diagram setting, or <code>null</code> if a matching cs diagram setting could not be found
	 */
	public static CSDiagramSetting fetchByUuid_First(
		String uuid, OrderByComparator<CSDiagramSetting> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last cs diagram setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cs diagram setting
	 * @throws NoSuchCSDiagramSettingException if a matching cs diagram setting could not be found
	 */
	public static CSDiagramSetting findByUuid_Last(
			String uuid, OrderByComparator<CSDiagramSetting> orderByComparator)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCSDiagramSettingException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last cs diagram setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cs diagram setting, or <code>null</code> if a matching cs diagram setting could not be found
	 */
	public static CSDiagramSetting fetchByUuid_Last(
		String uuid, OrderByComparator<CSDiagramSetting> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the cs diagram settings before and after the current cs diagram setting in the ordered set where uuid = &#63;.
	 *
	 * @param CSDiagramSettingId the primary key of the current cs diagram setting
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cs diagram setting
	 * @throws NoSuchCSDiagramSettingException if a cs diagram setting with the primary key could not be found
	 */
	public static CSDiagramSetting[] findByUuid_PrevAndNext(
			long CSDiagramSettingId, String uuid,
			OrderByComparator<CSDiagramSetting> orderByComparator)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCSDiagramSettingException {

		return getPersistence().findByUuid_PrevAndNext(
			CSDiagramSettingId, uuid, orderByComparator);
	}

	/**
	 * Removes all the cs diagram settings where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of cs diagram settings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cs diagram settings
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the cs diagram settings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cs diagram settings
	 */
	public static List<CSDiagramSetting> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the cs diagram settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cs diagram settings
	 * @param end the upper bound of the range of cs diagram settings (not inclusive)
	 * @return the range of matching cs diagram settings
	 */
	public static List<CSDiagramSetting> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the cs diagram settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cs diagram settings
	 * @param end the upper bound of the range of cs diagram settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cs diagram settings
	 */
	public static List<CSDiagramSetting> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CSDiagramSetting> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cs diagram settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cs diagram settings
	 * @param end the upper bound of the range of cs diagram settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cs diagram settings
	 */
	public static List<CSDiagramSetting> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CSDiagramSetting> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cs diagram setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cs diagram setting
	 * @throws NoSuchCSDiagramSettingException if a matching cs diagram setting could not be found
	 */
	public static CSDiagramSetting findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CSDiagramSetting> orderByComparator)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCSDiagramSettingException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first cs diagram setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cs diagram setting, or <code>null</code> if a matching cs diagram setting could not be found
	 */
	public static CSDiagramSetting fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CSDiagramSetting> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last cs diagram setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cs diagram setting
	 * @throws NoSuchCSDiagramSettingException if a matching cs diagram setting could not be found
	 */
	public static CSDiagramSetting findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CSDiagramSetting> orderByComparator)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCSDiagramSettingException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last cs diagram setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cs diagram setting, or <code>null</code> if a matching cs diagram setting could not be found
	 */
	public static CSDiagramSetting fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CSDiagramSetting> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the cs diagram settings before and after the current cs diagram setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CSDiagramSettingId the primary key of the current cs diagram setting
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cs diagram setting
	 * @throws NoSuchCSDiagramSettingException if a cs diagram setting with the primary key could not be found
	 */
	public static CSDiagramSetting[] findByUuid_C_PrevAndNext(
			long CSDiagramSettingId, String uuid, long companyId,
			OrderByComparator<CSDiagramSetting> orderByComparator)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCSDiagramSettingException {

		return getPersistence().findByUuid_C_PrevAndNext(
			CSDiagramSettingId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the cs diagram settings where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of cs diagram settings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cs diagram settings
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the cs diagram setting where CPDefinitionId = &#63; or throws a <code>NoSuchCSDiagramSettingException</code> if it could not be found.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching cs diagram setting
	 * @throws NoSuchCSDiagramSettingException if a matching cs diagram setting could not be found
	 */
	public static CSDiagramSetting findByCPDefinitionId(long CPDefinitionId)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCSDiagramSettingException {

		return getPersistence().findByCPDefinitionId(CPDefinitionId);
	}

	/**
	 * Returns the cs diagram setting where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching cs diagram setting, or <code>null</code> if a matching cs diagram setting could not be found
	 */
	public static CSDiagramSetting fetchByCPDefinitionId(long CPDefinitionId) {
		return getPersistence().fetchByCPDefinitionId(CPDefinitionId);
	}

	/**
	 * Returns the cs diagram setting where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cs diagram setting, or <code>null</code> if a matching cs diagram setting could not be found
	 */
	public static CSDiagramSetting fetchByCPDefinitionId(
		long CPDefinitionId, boolean useFinderCache) {

		return getPersistence().fetchByCPDefinitionId(
			CPDefinitionId, useFinderCache);
	}

	/**
	 * Removes the cs diagram setting where CPDefinitionId = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the cs diagram setting that was removed
	 */
	public static CSDiagramSetting removeByCPDefinitionId(long CPDefinitionId)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCSDiagramSettingException {

		return getPersistence().removeByCPDefinitionId(CPDefinitionId);
	}

	/**
	 * Returns the number of cs diagram settings where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the number of matching cs diagram settings
	 */
	public static int countByCPDefinitionId(long CPDefinitionId) {
		return getPersistence().countByCPDefinitionId(CPDefinitionId);
	}

	/**
	 * Caches the cs diagram setting in the entity cache if it is enabled.
	 *
	 * @param csDiagramSetting the cs diagram setting
	 */
	public static void cacheResult(CSDiagramSetting csDiagramSetting) {
		getPersistence().cacheResult(csDiagramSetting);
	}

	/**
	 * Caches the cs diagram settings in the entity cache if it is enabled.
	 *
	 * @param csDiagramSettings the cs diagram settings
	 */
	public static void cacheResult(List<CSDiagramSetting> csDiagramSettings) {
		getPersistence().cacheResult(csDiagramSettings);
	}

	/**
	 * Creates a new cs diagram setting with the primary key. Does not add the cs diagram setting to the database.
	 *
	 * @param CSDiagramSettingId the primary key for the new cs diagram setting
	 * @return the new cs diagram setting
	 */
	public static CSDiagramSetting create(long CSDiagramSettingId) {
		return getPersistence().create(CSDiagramSettingId);
	}

	/**
	 * Removes the cs diagram setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CSDiagramSettingId the primary key of the cs diagram setting
	 * @return the cs diagram setting that was removed
	 * @throws NoSuchCSDiagramSettingException if a cs diagram setting with the primary key could not be found
	 */
	public static CSDiagramSetting remove(long CSDiagramSettingId)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCSDiagramSettingException {

		return getPersistence().remove(CSDiagramSettingId);
	}

	public static CSDiagramSetting updateImpl(
		CSDiagramSetting csDiagramSetting) {

		return getPersistence().updateImpl(csDiagramSetting);
	}

	/**
	 * Returns the cs diagram setting with the primary key or throws a <code>NoSuchCSDiagramSettingException</code> if it could not be found.
	 *
	 * @param CSDiagramSettingId the primary key of the cs diagram setting
	 * @return the cs diagram setting
	 * @throws NoSuchCSDiagramSettingException if a cs diagram setting with the primary key could not be found
	 */
	public static CSDiagramSetting findByPrimaryKey(long CSDiagramSettingId)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCSDiagramSettingException {

		return getPersistence().findByPrimaryKey(CSDiagramSettingId);
	}

	/**
	 * Returns the cs diagram setting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CSDiagramSettingId the primary key of the cs diagram setting
	 * @return the cs diagram setting, or <code>null</code> if a cs diagram setting with the primary key could not be found
	 */
	public static CSDiagramSetting fetchByPrimaryKey(long CSDiagramSettingId) {
		return getPersistence().fetchByPrimaryKey(CSDiagramSettingId);
	}

	/**
	 * Returns all the cs diagram settings.
	 *
	 * @return the cs diagram settings
	 */
	public static List<CSDiagramSetting> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the cs diagram settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cs diagram settings
	 * @param end the upper bound of the range of cs diagram settings (not inclusive)
	 * @return the range of cs diagram settings
	 */
	public static List<CSDiagramSetting> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the cs diagram settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cs diagram settings
	 * @param end the upper bound of the range of cs diagram settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cs diagram settings
	 */
	public static List<CSDiagramSetting> findAll(
		int start, int end,
		OrderByComparator<CSDiagramSetting> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cs diagram settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cs diagram settings
	 * @param end the upper bound of the range of cs diagram settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cs diagram settings
	 */
	public static List<CSDiagramSetting> findAll(
		int start, int end,
		OrderByComparator<CSDiagramSetting> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the cs diagram settings from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of cs diagram settings.
	 *
	 * @return the number of cs diagram settings
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CSDiagramSettingPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CSDiagramSettingPersistence, CSDiagramSettingPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CSDiagramSettingPersistence.class);

		ServiceTracker<CSDiagramSettingPersistence, CSDiagramSettingPersistence>
			serviceTracker =
				new ServiceTracker
					<CSDiagramSettingPersistence, CSDiagramSettingPersistence>(
						bundle.getBundleContext(),
						CSDiagramSettingPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}
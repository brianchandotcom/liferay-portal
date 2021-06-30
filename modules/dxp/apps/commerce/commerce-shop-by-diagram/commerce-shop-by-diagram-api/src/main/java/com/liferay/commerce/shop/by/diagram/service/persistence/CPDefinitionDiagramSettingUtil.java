/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.shop.by.diagram.service.persistence;

import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting;
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
 * The persistence utility for the cp definition diagram setting service. This utility wraps <code>com.liferay.commerce.shop.by.diagram.service.persistence.impl.CPDefinitionDiagramSettingPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Andrea Sbarra
 * @see CPDefinitionDiagramSettingPersistence
 * @generated
 */
public class CPDefinitionDiagramSettingUtil {

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
		CPDefinitionDiagramSetting cpDefinitionDiagramSetting) {

		getPersistence().clearCache(cpDefinitionDiagramSetting);
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
	public static Map<Serializable, CPDefinitionDiagramSetting>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CPDefinitionDiagramSetting> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CPDefinitionDiagramSetting> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CPDefinitionDiagramSetting> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CPDefinitionDiagramSetting> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CPDefinitionDiagramSetting update(
		CPDefinitionDiagramSetting cpDefinitionDiagramSetting) {

		return getPersistence().update(cpDefinitionDiagramSetting);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CPDefinitionDiagramSetting update(
		CPDefinitionDiagramSetting cpDefinitionDiagramSetting,
		ServiceContext serviceContext) {

		return getPersistence().update(
			cpDefinitionDiagramSetting, serviceContext);
	}

	/**
	 * Returns all the cp definition diagram settings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp definition diagram settings
	 */
	public static List<CPDefinitionDiagramSetting> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the cp definition diagram settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition diagram settings
	 * @param end the upper bound of the range of cp definition diagram settings (not inclusive)
	 * @return the range of matching cp definition diagram settings
	 */
	public static List<CPDefinitionDiagramSetting> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the cp definition diagram settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition diagram settings
	 * @param end the upper bound of the range of cp definition diagram settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition diagram settings
	 */
	public static List<CPDefinitionDiagramSetting> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPDefinitionDiagramSetting> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp definition diagram settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition diagram settings
	 * @param end the upper bound of the range of cp definition diagram settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition diagram settings
	 */
	public static List<CPDefinitionDiagramSetting> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPDefinitionDiagramSetting> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cp definition diagram setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition diagram setting
	 * @throws NoSuchCPDefinitionDiagramSettingException if a matching cp definition diagram setting could not be found
	 */
	public static CPDefinitionDiagramSetting findByUuid_First(
			String uuid,
			OrderByComparator<CPDefinitionDiagramSetting> orderByComparator)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCPDefinitionDiagramSettingException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first cp definition diagram setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition diagram setting, or <code>null</code> if a matching cp definition diagram setting could not be found
	 */
	public static CPDefinitionDiagramSetting fetchByUuid_First(
		String uuid,
		OrderByComparator<CPDefinitionDiagramSetting> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last cp definition diagram setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition diagram setting
	 * @throws NoSuchCPDefinitionDiagramSettingException if a matching cp definition diagram setting could not be found
	 */
	public static CPDefinitionDiagramSetting findByUuid_Last(
			String uuid,
			OrderByComparator<CPDefinitionDiagramSetting> orderByComparator)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCPDefinitionDiagramSettingException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last cp definition diagram setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition diagram setting, or <code>null</code> if a matching cp definition diagram setting could not be found
	 */
	public static CPDefinitionDiagramSetting fetchByUuid_Last(
		String uuid,
		OrderByComparator<CPDefinitionDiagramSetting> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the cp definition diagram settings before and after the current cp definition diagram setting in the ordered set where uuid = &#63;.
	 *
	 * @param CPDefinitionDiagramSettingId the primary key of the current cp definition diagram setting
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition diagram setting
	 * @throws NoSuchCPDefinitionDiagramSettingException if a cp definition diagram setting with the primary key could not be found
	 */
	public static CPDefinitionDiagramSetting[] findByUuid_PrevAndNext(
			long CPDefinitionDiagramSettingId, String uuid,
			OrderByComparator<CPDefinitionDiagramSetting> orderByComparator)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCPDefinitionDiagramSettingException {

		return getPersistence().findByUuid_PrevAndNext(
			CPDefinitionDiagramSettingId, uuid, orderByComparator);
	}

	/**
	 * Removes all the cp definition diagram settings where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of cp definition diagram settings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp definition diagram settings
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the cp definition diagram settings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp definition diagram settings
	 */
	public static List<CPDefinitionDiagramSetting> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the cp definition diagram settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition diagram settings
	 * @param end the upper bound of the range of cp definition diagram settings (not inclusive)
	 * @return the range of matching cp definition diagram settings
	 */
	public static List<CPDefinitionDiagramSetting> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the cp definition diagram settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition diagram settings
	 * @param end the upper bound of the range of cp definition diagram settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition diagram settings
	 */
	public static List<CPDefinitionDiagramSetting> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPDefinitionDiagramSetting> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp definition diagram settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition diagram settings
	 * @param end the upper bound of the range of cp definition diagram settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition diagram settings
	 */
	public static List<CPDefinitionDiagramSetting> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPDefinitionDiagramSetting> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cp definition diagram setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition diagram setting
	 * @throws NoSuchCPDefinitionDiagramSettingException if a matching cp definition diagram setting could not be found
	 */
	public static CPDefinitionDiagramSetting findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CPDefinitionDiagramSetting> orderByComparator)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCPDefinitionDiagramSettingException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first cp definition diagram setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition diagram setting, or <code>null</code> if a matching cp definition diagram setting could not be found
	 */
	public static CPDefinitionDiagramSetting fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CPDefinitionDiagramSetting> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last cp definition diagram setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition diagram setting
	 * @throws NoSuchCPDefinitionDiagramSettingException if a matching cp definition diagram setting could not be found
	 */
	public static CPDefinitionDiagramSetting findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CPDefinitionDiagramSetting> orderByComparator)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCPDefinitionDiagramSettingException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last cp definition diagram setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition diagram setting, or <code>null</code> if a matching cp definition diagram setting could not be found
	 */
	public static CPDefinitionDiagramSetting fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CPDefinitionDiagramSetting> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the cp definition diagram settings before and after the current cp definition diagram setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CPDefinitionDiagramSettingId the primary key of the current cp definition diagram setting
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition diagram setting
	 * @throws NoSuchCPDefinitionDiagramSettingException if a cp definition diagram setting with the primary key could not be found
	 */
	public static CPDefinitionDiagramSetting[] findByUuid_C_PrevAndNext(
			long CPDefinitionDiagramSettingId, String uuid, long companyId,
			OrderByComparator<CPDefinitionDiagramSetting> orderByComparator)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCPDefinitionDiagramSettingException {

		return getPersistence().findByUuid_C_PrevAndNext(
			CPDefinitionDiagramSettingId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the cp definition diagram settings where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of cp definition diagram settings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp definition diagram settings
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the cp definition diagram setting where CPDefinitionId = &#63; or throws a <code>NoSuchCPDefinitionDiagramSettingException</code> if it could not be found.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching cp definition diagram setting
	 * @throws NoSuchCPDefinitionDiagramSettingException if a matching cp definition diagram setting could not be found
	 */
	public static CPDefinitionDiagramSetting findByCPDefinitionId(
			long CPDefinitionId)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCPDefinitionDiagramSettingException {

		return getPersistence().findByCPDefinitionId(CPDefinitionId);
	}

	/**
	 * Returns the cp definition diagram setting where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching cp definition diagram setting, or <code>null</code> if a matching cp definition diagram setting could not be found
	 */
	public static CPDefinitionDiagramSetting fetchByCPDefinitionId(
		long CPDefinitionId) {

		return getPersistence().fetchByCPDefinitionId(CPDefinitionId);
	}

	/**
	 * Returns the cp definition diagram setting where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp definition diagram setting, or <code>null</code> if a matching cp definition diagram setting could not be found
	 */
	public static CPDefinitionDiagramSetting fetchByCPDefinitionId(
		long CPDefinitionId, boolean useFinderCache) {

		return getPersistence().fetchByCPDefinitionId(
			CPDefinitionId, useFinderCache);
	}

	/**
	 * Removes the cp definition diagram setting where CPDefinitionId = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the cp definition diagram setting that was removed
	 */
	public static CPDefinitionDiagramSetting removeByCPDefinitionId(
			long CPDefinitionId)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCPDefinitionDiagramSettingException {

		return getPersistence().removeByCPDefinitionId(CPDefinitionId);
	}

	/**
	 * Returns the number of cp definition diagram settings where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the number of matching cp definition diagram settings
	 */
	public static int countByCPDefinitionId(long CPDefinitionId) {
		return getPersistence().countByCPDefinitionId(CPDefinitionId);
	}

	/**
	 * Caches the cp definition diagram setting in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionDiagramSetting the cp definition diagram setting
	 */
	public static void cacheResult(
		CPDefinitionDiagramSetting cpDefinitionDiagramSetting) {

		getPersistence().cacheResult(cpDefinitionDiagramSetting);
	}

	/**
	 * Caches the cp definition diagram settings in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionDiagramSettings the cp definition diagram settings
	 */
	public static void cacheResult(
		List<CPDefinitionDiagramSetting> cpDefinitionDiagramSettings) {

		getPersistence().cacheResult(cpDefinitionDiagramSettings);
	}

	/**
	 * Creates a new cp definition diagram setting with the primary key. Does not add the cp definition diagram setting to the database.
	 *
	 * @param CPDefinitionDiagramSettingId the primary key for the new cp definition diagram setting
	 * @return the new cp definition diagram setting
	 */
	public static CPDefinitionDiagramSetting create(
		long CPDefinitionDiagramSettingId) {

		return getPersistence().create(CPDefinitionDiagramSettingId);
	}

	/**
	 * Removes the cp definition diagram setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPDefinitionDiagramSettingId the primary key of the cp definition diagram setting
	 * @return the cp definition diagram setting that was removed
	 * @throws NoSuchCPDefinitionDiagramSettingException if a cp definition diagram setting with the primary key could not be found
	 */
	public static CPDefinitionDiagramSetting remove(
			long CPDefinitionDiagramSettingId)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCPDefinitionDiagramSettingException {

		return getPersistence().remove(CPDefinitionDiagramSettingId);
	}

	public static CPDefinitionDiagramSetting updateImpl(
		CPDefinitionDiagramSetting cpDefinitionDiagramSetting) {

		return getPersistence().updateImpl(cpDefinitionDiagramSetting);
	}

	/**
	 * Returns the cp definition diagram setting with the primary key or throws a <code>NoSuchCPDefinitionDiagramSettingException</code> if it could not be found.
	 *
	 * @param CPDefinitionDiagramSettingId the primary key of the cp definition diagram setting
	 * @return the cp definition diagram setting
	 * @throws NoSuchCPDefinitionDiagramSettingException if a cp definition diagram setting with the primary key could not be found
	 */
	public static CPDefinitionDiagramSetting findByPrimaryKey(
			long CPDefinitionDiagramSettingId)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCPDefinitionDiagramSettingException {

		return getPersistence().findByPrimaryKey(CPDefinitionDiagramSettingId);
	}

	/**
	 * Returns the cp definition diagram setting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPDefinitionDiagramSettingId the primary key of the cp definition diagram setting
	 * @return the cp definition diagram setting, or <code>null</code> if a cp definition diagram setting with the primary key could not be found
	 */
	public static CPDefinitionDiagramSetting fetchByPrimaryKey(
		long CPDefinitionDiagramSettingId) {

		return getPersistence().fetchByPrimaryKey(CPDefinitionDiagramSettingId);
	}

	/**
	 * Returns all the cp definition diagram settings.
	 *
	 * @return the cp definition diagram settings
	 */
	public static List<CPDefinitionDiagramSetting> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the cp definition diagram settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition diagram settings
	 * @param end the upper bound of the range of cp definition diagram settings (not inclusive)
	 * @return the range of cp definition diagram settings
	 */
	public static List<CPDefinitionDiagramSetting> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the cp definition diagram settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition diagram settings
	 * @param end the upper bound of the range of cp definition diagram settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp definition diagram settings
	 */
	public static List<CPDefinitionDiagramSetting> findAll(
		int start, int end,
		OrderByComparator<CPDefinitionDiagramSetting> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp definition diagram settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition diagram settings
	 * @param end the upper bound of the range of cp definition diagram settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp definition diagram settings
	 */
	public static List<CPDefinitionDiagramSetting> findAll(
		int start, int end,
		OrderByComparator<CPDefinitionDiagramSetting> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the cp definition diagram settings from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of cp definition diagram settings.
	 *
	 * @return the number of cp definition diagram settings
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CPDefinitionDiagramSettingPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CPDefinitionDiagramSettingPersistence,
		 CPDefinitionDiagramSettingPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CPDefinitionDiagramSettingPersistence.class);

		ServiceTracker
			<CPDefinitionDiagramSettingPersistence,
			 CPDefinitionDiagramSettingPersistence> serviceTracker =
				new ServiceTracker
					<CPDefinitionDiagramSettingPersistence,
					 CPDefinitionDiagramSettingPersistence>(
						 bundle.getBundleContext(),
						 CPDefinitionDiagramSettingPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.persistence;

import com.liferay.commerce.product.model.CPConfigurationList;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the cp configuration list service. This utility wraps <code>com.liferay.commerce.product.service.persistence.impl.CPConfigurationListPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPConfigurationListPersistence
 * @generated
 */
public class CPConfigurationListUtil {

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
	public static void clearCache(CPConfigurationList cpConfigurationList) {
		getPersistence().clearCache(cpConfigurationList);
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
	public static Map<Serializable, CPConfigurationList> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CPConfigurationList> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CPConfigurationList> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CPConfigurationList> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CPConfigurationList update(
		CPConfigurationList cpConfigurationList) {

		return getPersistence().update(cpConfigurationList);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CPConfigurationList update(
		CPConfigurationList cpConfigurationList,
		ServiceContext serviceContext) {

		return getPersistence().update(cpConfigurationList, serviceContext);
	}

	/**
	 * Returns all the cp configuration lists where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp configuration lists
	 */
	public static List<CPConfigurationList> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the cp configuration lists where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @return the range of matching cp configuration lists
	 */
	public static List<CPConfigurationList> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration lists
	 */
	public static List<CPConfigurationList> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration lists
	 */
	public static List<CPConfigurationList> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cp configuration list in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	public static CPConfigurationList findByUuid_First(
			String uuid,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPConfigurationListException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first cp configuration list in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	public static CPConfigurationList fetchByUuid_First(
		String uuid, OrderByComparator<CPConfigurationList> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last cp configuration list in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	public static CPConfigurationList findByUuid_Last(
			String uuid,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPConfigurationListException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last cp configuration list in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	public static CPConfigurationList fetchByUuid_Last(
		String uuid, OrderByComparator<CPConfigurationList> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the cp configuration lists before and after the current cp configuration list in the ordered set where uuid = &#63;.
	 *
	 * @param CPConfigurationListId the primary key of the current cp configuration list
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp configuration list
	 * @throws NoSuchCPConfigurationListException if a cp configuration list with the primary key could not be found
	 */
	public static CPConfigurationList[] findByUuid_PrevAndNext(
			long CPConfigurationListId, String uuid,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPConfigurationListException {

		return getPersistence().findByUuid_PrevAndNext(
			CPConfigurationListId, uuid, orderByComparator);
	}

	/**
	 * Removes all the cp configuration lists where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of cp configuration lists where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp configuration lists
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the cp configuration list where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchCPConfigurationListException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	public static CPConfigurationList findByUUID_G(String uuid, long groupId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPConfigurationListException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the cp configuration list where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	public static CPConfigurationList fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the cp configuration list where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	public static CPConfigurationList fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the cp configuration list where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the cp configuration list that was removed
	 */
	public static CPConfigurationList removeByUUID_G(String uuid, long groupId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPConfigurationListException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of cp configuration lists where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching cp configuration lists
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the cp configuration lists where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp configuration lists
	 */
	public static List<CPConfigurationList> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the cp configuration lists where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @return the range of matching cp configuration lists
	 */
	public static List<CPConfigurationList> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration lists
	 */
	public static List<CPConfigurationList> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration lists
	 */
	public static List<CPConfigurationList> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cp configuration list in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	public static CPConfigurationList findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPConfigurationListException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first cp configuration list in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	public static CPConfigurationList fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last cp configuration list in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	public static CPConfigurationList findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPConfigurationListException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last cp configuration list in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	public static CPConfigurationList fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the cp configuration lists before and after the current cp configuration list in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CPConfigurationListId the primary key of the current cp configuration list
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp configuration list
	 * @throws NoSuchCPConfigurationListException if a cp configuration list with the primary key could not be found
	 */
	public static CPConfigurationList[] findByUuid_C_PrevAndNext(
			long CPConfigurationListId, String uuid, long companyId,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPConfigurationListException {

		return getPersistence().findByUuid_C_PrevAndNext(
			CPConfigurationListId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the cp configuration lists where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of cp configuration lists where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp configuration lists
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the cp configuration lists where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching cp configuration lists
	 */
	public static List<CPConfigurationList> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the cp configuration lists where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @return the range of matching cp configuration lists
	 */
	public static List<CPConfigurationList> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration lists
	 */
	public static List<CPConfigurationList> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration lists
	 */
	public static List<CPConfigurationList> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cp configuration list in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	public static CPConfigurationList findByCompanyId_First(
			long companyId,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPConfigurationListException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first cp configuration list in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	public static CPConfigurationList fetchByCompanyId_First(
		long companyId,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last cp configuration list in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	public static CPConfigurationList findByCompanyId_Last(
			long companyId,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPConfigurationListException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last cp configuration list in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	public static CPConfigurationList fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the cp configuration lists before and after the current cp configuration list in the ordered set where companyId = &#63;.
	 *
	 * @param CPConfigurationListId the primary key of the current cp configuration list
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp configuration list
	 * @throws NoSuchCPConfigurationListException if a cp configuration list with the primary key could not be found
	 */
	public static CPConfigurationList[] findByCompanyId_PrevAndNext(
			long CPConfigurationListId, long companyId,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPConfigurationListException {

		return getPersistence().findByCompanyId_PrevAndNext(
			CPConfigurationListId, companyId, orderByComparator);
	}

	/**
	 * Removes all the cp configuration lists where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of cp configuration lists where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching cp configuration lists
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns the cp configuration list where parentCPConfigurationListId = &#63; or throws a <code>NoSuchCPConfigurationListException</code> if it could not be found.
	 *
	 * @param parentCPConfigurationListId the parent cp configuration list ID
	 * @return the matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	public static CPConfigurationList findByParentCPConfigurationListId(
			long parentCPConfigurationListId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPConfigurationListException {

		return getPersistence().findByParentCPConfigurationListId(
			parentCPConfigurationListId);
	}

	/**
	 * Returns the cp configuration list where parentCPConfigurationListId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param parentCPConfigurationListId the parent cp configuration list ID
	 * @return the matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	public static CPConfigurationList fetchByParentCPConfigurationListId(
		long parentCPConfigurationListId) {

		return getPersistence().fetchByParentCPConfigurationListId(
			parentCPConfigurationListId);
	}

	/**
	 * Returns the cp configuration list where parentCPConfigurationListId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param parentCPConfigurationListId the parent cp configuration list ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	public static CPConfigurationList fetchByParentCPConfigurationListId(
		long parentCPConfigurationListId, boolean useFinderCache) {

		return getPersistence().fetchByParentCPConfigurationListId(
			parentCPConfigurationListId, useFinderCache);
	}

	/**
	 * Removes the cp configuration list where parentCPConfigurationListId = &#63; from the database.
	 *
	 * @param parentCPConfigurationListId the parent cp configuration list ID
	 * @return the cp configuration list that was removed
	 */
	public static CPConfigurationList removeByParentCPConfigurationListId(
			long parentCPConfigurationListId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPConfigurationListException {

		return getPersistence().removeByParentCPConfigurationListId(
			parentCPConfigurationListId);
	}

	/**
	 * Returns the number of cp configuration lists where parentCPConfigurationListId = &#63;.
	 *
	 * @param parentCPConfigurationListId the parent cp configuration list ID
	 * @return the number of matching cp configuration lists
	 */
	public static int countByParentCPConfigurationListId(
		long parentCPConfigurationListId) {

		return getPersistence().countByParentCPConfigurationListId(
			parentCPConfigurationListId);
	}

	/**
	 * Returns all the cp configuration lists where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @return the matching cp configuration lists
	 */
	public static List<CPConfigurationList> findByG_C(
		long groupId, long companyId) {

		return getPersistence().findByG_C(groupId, companyId);
	}

	/**
	 * Returns a range of all the cp configuration lists where groupId = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @return the range of matching cp configuration lists
	 */
	public static List<CPConfigurationList> findByG_C(
		long groupId, long companyId, int start, int end) {

		return getPersistence().findByG_C(groupId, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where groupId = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration lists
	 */
	public static List<CPConfigurationList> findByG_C(
		long groupId, long companyId, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return getPersistence().findByG_C(
			groupId, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where groupId = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration lists
	 */
	public static List<CPConfigurationList> findByG_C(
		long groupId, long companyId, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_C(
			groupId, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cp configuration list in the ordered set where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	public static CPConfigurationList findByG_C_First(
			long groupId, long companyId,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPConfigurationListException {

		return getPersistence().findByG_C_First(
			groupId, companyId, orderByComparator);
	}

	/**
	 * Returns the first cp configuration list in the ordered set where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	public static CPConfigurationList fetchByG_C_First(
		long groupId, long companyId,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return getPersistence().fetchByG_C_First(
			groupId, companyId, orderByComparator);
	}

	/**
	 * Returns the last cp configuration list in the ordered set where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	public static CPConfigurationList findByG_C_Last(
			long groupId, long companyId,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPConfigurationListException {

		return getPersistence().findByG_C_Last(
			groupId, companyId, orderByComparator);
	}

	/**
	 * Returns the last cp configuration list in the ordered set where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	public static CPConfigurationList fetchByG_C_Last(
		long groupId, long companyId,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return getPersistence().fetchByG_C_Last(
			groupId, companyId, orderByComparator);
	}

	/**
	 * Returns the cp configuration lists before and after the current cp configuration list in the ordered set where groupId = &#63; and companyId = &#63;.
	 *
	 * @param CPConfigurationListId the primary key of the current cp configuration list
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp configuration list
	 * @throws NoSuchCPConfigurationListException if a cp configuration list with the primary key could not be found
	 */
	public static CPConfigurationList[] findByG_C_PrevAndNext(
			long CPConfigurationListId, long groupId, long companyId,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPConfigurationListException {

		return getPersistence().findByG_C_PrevAndNext(
			CPConfigurationListId, groupId, companyId, orderByComparator);
	}

	/**
	 * Returns all the cp configuration lists where groupId = any &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @return the matching cp configuration lists
	 */
	public static List<CPConfigurationList> findByG_C(
		long[] groupIds, long companyId) {

		return getPersistence().findByG_C(groupIds, companyId);
	}

	/**
	 * Returns a range of all the cp configuration lists where groupId = any &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @return the range of matching cp configuration lists
	 */
	public static List<CPConfigurationList> findByG_C(
		long[] groupIds, long companyId, int start, int end) {

		return getPersistence().findByG_C(groupIds, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where groupId = any &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration lists
	 */
	public static List<CPConfigurationList> findByG_C(
		long[] groupIds, long companyId, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return getPersistence().findByG_C(
			groupIds, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where groupId = &#63; and companyId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration lists
	 */
	public static List<CPConfigurationList> findByG_C(
		long[] groupIds, long companyId, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_C(
			groupIds, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the cp configuration lists where groupId = &#63; and companyId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 */
	public static void removeByG_C(long groupId, long companyId) {
		getPersistence().removeByG_C(groupId, companyId);
	}

	/**
	 * Returns the number of cp configuration lists where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @return the number of matching cp configuration lists
	 */
	public static int countByG_C(long groupId, long companyId) {
		return getPersistence().countByG_C(groupId, companyId);
	}

	/**
	 * Returns the number of cp configuration lists where groupId = any &#63; and companyId = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @return the number of matching cp configuration lists
	 */
	public static int countByG_C(long[] groupIds, long companyId) {
		return getPersistence().countByG_C(groupIds, companyId);
	}

	/**
	 * Returns the cp configuration list where groupId = &#63; and masterCPConfigurationList = &#63; or throws a <code>NoSuchCPConfigurationListException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param masterCPConfigurationList the master cp configuration list
	 * @return the matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	public static CPConfigurationList findByG_M(
			long groupId, boolean masterCPConfigurationList)
		throws com.liferay.commerce.product.exception.
			NoSuchCPConfigurationListException {

		return getPersistence().findByG_M(groupId, masterCPConfigurationList);
	}

	/**
	 * Returns the cp configuration list where groupId = &#63; and masterCPConfigurationList = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param masterCPConfigurationList the master cp configuration list
	 * @return the matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	public static CPConfigurationList fetchByG_M(
		long groupId, boolean masterCPConfigurationList) {

		return getPersistence().fetchByG_M(groupId, masterCPConfigurationList);
	}

	/**
	 * Returns the cp configuration list where groupId = &#63; and masterCPConfigurationList = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param masterCPConfigurationList the master cp configuration list
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	public static CPConfigurationList fetchByG_M(
		long groupId, boolean masterCPConfigurationList,
		boolean useFinderCache) {

		return getPersistence().fetchByG_M(
			groupId, masterCPConfigurationList, useFinderCache);
	}

	/**
	 * Removes the cp configuration list where groupId = &#63; and masterCPConfigurationList = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param masterCPConfigurationList the master cp configuration list
	 * @return the cp configuration list that was removed
	 */
	public static CPConfigurationList removeByG_M(
			long groupId, boolean masterCPConfigurationList)
		throws com.liferay.commerce.product.exception.
			NoSuchCPConfigurationListException {

		return getPersistence().removeByG_M(groupId, masterCPConfigurationList);
	}

	/**
	 * Returns the number of cp configuration lists where groupId = &#63; and masterCPConfigurationList = &#63;.
	 *
	 * @param groupId the group ID
	 * @param masterCPConfigurationList the master cp configuration list
	 * @return the number of matching cp configuration lists
	 */
	public static int countByG_M(
		long groupId, boolean masterCPConfigurationList) {

		return getPersistence().countByG_M(groupId, masterCPConfigurationList);
	}

	/**
	 * Returns all the cp configuration lists where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching cp configuration lists
	 */
	public static List<CPConfigurationList> findByLtD_S(
		Date displayDate, int status) {

		return getPersistence().findByLtD_S(displayDate, status);
	}

	/**
	 * Returns a range of all the cp configuration lists where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @return the range of matching cp configuration lists
	 */
	public static List<CPConfigurationList> findByLtD_S(
		Date displayDate, int status, int start, int end) {

		return getPersistence().findByLtD_S(displayDate, status, start, end);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration lists
	 */
	public static List<CPConfigurationList> findByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return getPersistence().findByLtD_S(
			displayDate, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration lists
	 */
	public static List<CPConfigurationList> findByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByLtD_S(
			displayDate, status, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cp configuration list in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	public static CPConfigurationList findByLtD_S_First(
			Date displayDate, int status,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPConfigurationListException {

		return getPersistence().findByLtD_S_First(
			displayDate, status, orderByComparator);
	}

	/**
	 * Returns the first cp configuration list in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	public static CPConfigurationList fetchByLtD_S_First(
		Date displayDate, int status,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return getPersistence().fetchByLtD_S_First(
			displayDate, status, orderByComparator);
	}

	/**
	 * Returns the last cp configuration list in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	public static CPConfigurationList findByLtD_S_Last(
			Date displayDate, int status,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPConfigurationListException {

		return getPersistence().findByLtD_S_Last(
			displayDate, status, orderByComparator);
	}

	/**
	 * Returns the last cp configuration list in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	public static CPConfigurationList fetchByLtD_S_Last(
		Date displayDate, int status,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return getPersistence().fetchByLtD_S_Last(
			displayDate, status, orderByComparator);
	}

	/**
	 * Returns the cp configuration lists before and after the current cp configuration list in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param CPConfigurationListId the primary key of the current cp configuration list
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp configuration list
	 * @throws NoSuchCPConfigurationListException if a cp configuration list with the primary key could not be found
	 */
	public static CPConfigurationList[] findByLtD_S_PrevAndNext(
			long CPConfigurationListId, Date displayDate, int status,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPConfigurationListException {

		return getPersistence().findByLtD_S_PrevAndNext(
			CPConfigurationListId, displayDate, status, orderByComparator);
	}

	/**
	 * Removes all the cp configuration lists where displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 */
	public static void removeByLtD_S(Date displayDate, int status) {
		getPersistence().removeByLtD_S(displayDate, status);
	}

	/**
	 * Returns the number of cp configuration lists where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching cp configuration lists
	 */
	public static int countByLtD_S(Date displayDate, int status) {
		return getPersistence().countByLtD_S(displayDate, status);
	}

	/**
	 * Returns all the cp configuration lists where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching cp configuration lists
	 */
	public static List<CPConfigurationList> findByG_C_S(
		long groupId, long companyId, int status) {

		return getPersistence().findByG_C_S(groupId, companyId, status);
	}

	/**
	 * Returns a range of all the cp configuration lists where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @return the range of matching cp configuration lists
	 */
	public static List<CPConfigurationList> findByG_C_S(
		long groupId, long companyId, int status, int start, int end) {

		return getPersistence().findByG_C_S(
			groupId, companyId, status, start, end);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration lists
	 */
	public static List<CPConfigurationList> findByG_C_S(
		long groupId, long companyId, int status, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return getPersistence().findByG_C_S(
			groupId, companyId, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration lists
	 */
	public static List<CPConfigurationList> findByG_C_S(
		long groupId, long companyId, int status, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_C_S(
			groupId, companyId, status, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first cp configuration list in the ordered set where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	public static CPConfigurationList findByG_C_S_First(
			long groupId, long companyId, int status,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPConfigurationListException {

		return getPersistence().findByG_C_S_First(
			groupId, companyId, status, orderByComparator);
	}

	/**
	 * Returns the first cp configuration list in the ordered set where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	public static CPConfigurationList fetchByG_C_S_First(
		long groupId, long companyId, int status,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return getPersistence().fetchByG_C_S_First(
			groupId, companyId, status, orderByComparator);
	}

	/**
	 * Returns the last cp configuration list in the ordered set where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	public static CPConfigurationList findByG_C_S_Last(
			long groupId, long companyId, int status,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPConfigurationListException {

		return getPersistence().findByG_C_S_Last(
			groupId, companyId, status, orderByComparator);
	}

	/**
	 * Returns the last cp configuration list in the ordered set where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	public static CPConfigurationList fetchByG_C_S_Last(
		long groupId, long companyId, int status,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return getPersistence().fetchByG_C_S_Last(
			groupId, companyId, status, orderByComparator);
	}

	/**
	 * Returns the cp configuration lists before and after the current cp configuration list in the ordered set where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param CPConfigurationListId the primary key of the current cp configuration list
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp configuration list
	 * @throws NoSuchCPConfigurationListException if a cp configuration list with the primary key could not be found
	 */
	public static CPConfigurationList[] findByG_C_S_PrevAndNext(
			long CPConfigurationListId, long groupId, long companyId,
			int status,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPConfigurationListException {

		return getPersistence().findByG_C_S_PrevAndNext(
			CPConfigurationListId, groupId, companyId, status,
			orderByComparator);
	}

	/**
	 * Returns all the cp configuration lists where groupId = any &#63; and companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching cp configuration lists
	 */
	public static List<CPConfigurationList> findByG_C_S(
		long[] groupIds, long companyId, int status) {

		return getPersistence().findByG_C_S(groupIds, companyId, status);
	}

	/**
	 * Returns a range of all the cp configuration lists where groupId = any &#63; and companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @return the range of matching cp configuration lists
	 */
	public static List<CPConfigurationList> findByG_C_S(
		long[] groupIds, long companyId, int status, int start, int end) {

		return getPersistence().findByG_C_S(
			groupIds, companyId, status, start, end);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where groupId = any &#63; and companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration lists
	 */
	public static List<CPConfigurationList> findByG_C_S(
		long[] groupIds, long companyId, int status, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return getPersistence().findByG_C_S(
			groupIds, companyId, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where groupId = &#63; and companyId = &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration lists
	 */
	public static List<CPConfigurationList> findByG_C_S(
		long[] groupIds, long companyId, int status, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_C_S(
			groupIds, companyId, status, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Removes all the cp configuration lists where groupId = &#63; and companyId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 */
	public static void removeByG_C_S(long groupId, long companyId, int status) {
		getPersistence().removeByG_C_S(groupId, companyId, status);
	}

	/**
	 * Returns the number of cp configuration lists where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching cp configuration lists
	 */
	public static int countByG_C_S(long groupId, long companyId, int status) {
		return getPersistence().countByG_C_S(groupId, companyId, status);
	}

	/**
	 * Returns the number of cp configuration lists where groupId = any &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching cp configuration lists
	 */
	public static int countByG_C_S(
		long[] groupIds, long companyId, int status) {

		return getPersistence().countByG_C_S(groupIds, companyId, status);
	}

	/**
	 * Returns all the cp configuration lists where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching cp configuration lists
	 */
	public static List<CPConfigurationList> findByG_C_NotS(
		long groupId, long companyId, int status) {

		return getPersistence().findByG_C_NotS(groupId, companyId, status);
	}

	/**
	 * Returns a range of all the cp configuration lists where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @return the range of matching cp configuration lists
	 */
	public static List<CPConfigurationList> findByG_C_NotS(
		long groupId, long companyId, int status, int start, int end) {

		return getPersistence().findByG_C_NotS(
			groupId, companyId, status, start, end);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration lists
	 */
	public static List<CPConfigurationList> findByG_C_NotS(
		long groupId, long companyId, int status, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return getPersistence().findByG_C_NotS(
			groupId, companyId, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration lists
	 */
	public static List<CPConfigurationList> findByG_C_NotS(
		long groupId, long companyId, int status, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_C_NotS(
			groupId, companyId, status, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first cp configuration list in the ordered set where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	public static CPConfigurationList findByG_C_NotS_First(
			long groupId, long companyId, int status,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPConfigurationListException {

		return getPersistence().findByG_C_NotS_First(
			groupId, companyId, status, orderByComparator);
	}

	/**
	 * Returns the first cp configuration list in the ordered set where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	public static CPConfigurationList fetchByG_C_NotS_First(
		long groupId, long companyId, int status,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return getPersistence().fetchByG_C_NotS_First(
			groupId, companyId, status, orderByComparator);
	}

	/**
	 * Returns the last cp configuration list in the ordered set where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	public static CPConfigurationList findByG_C_NotS_Last(
			long groupId, long companyId, int status,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPConfigurationListException {

		return getPersistence().findByG_C_NotS_Last(
			groupId, companyId, status, orderByComparator);
	}

	/**
	 * Returns the last cp configuration list in the ordered set where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	public static CPConfigurationList fetchByG_C_NotS_Last(
		long groupId, long companyId, int status,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return getPersistence().fetchByG_C_NotS_Last(
			groupId, companyId, status, orderByComparator);
	}

	/**
	 * Returns the cp configuration lists before and after the current cp configuration list in the ordered set where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param CPConfigurationListId the primary key of the current cp configuration list
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp configuration list
	 * @throws NoSuchCPConfigurationListException if a cp configuration list with the primary key could not be found
	 */
	public static CPConfigurationList[] findByG_C_NotS_PrevAndNext(
			long CPConfigurationListId, long groupId, long companyId,
			int status,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPConfigurationListException {

		return getPersistence().findByG_C_NotS_PrevAndNext(
			CPConfigurationListId, groupId, companyId, status,
			orderByComparator);
	}

	/**
	 * Returns all the cp configuration lists where groupId = any &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching cp configuration lists
	 */
	public static List<CPConfigurationList> findByG_C_NotS(
		long[] groupIds, long companyId, int status) {

		return getPersistence().findByG_C_NotS(groupIds, companyId, status);
	}

	/**
	 * Returns a range of all the cp configuration lists where groupId = any &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @return the range of matching cp configuration lists
	 */
	public static List<CPConfigurationList> findByG_C_NotS(
		long[] groupIds, long companyId, int status, int start, int end) {

		return getPersistence().findByG_C_NotS(
			groupIds, companyId, status, start, end);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where groupId = any &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration lists
	 */
	public static List<CPConfigurationList> findByG_C_NotS(
		long[] groupIds, long companyId, int status, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return getPersistence().findByG_C_NotS(
			groupIds, companyId, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where groupId = &#63; and companyId = &#63; and status &ne; &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration lists
	 */
	public static List<CPConfigurationList> findByG_C_NotS(
		long[] groupIds, long companyId, int status, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_C_NotS(
			groupIds, companyId, status, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Removes all the cp configuration lists where groupId = &#63; and companyId = &#63; and status &ne; &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 */
	public static void removeByG_C_NotS(
		long groupId, long companyId, int status) {

		getPersistence().removeByG_C_NotS(groupId, companyId, status);
	}

	/**
	 * Returns the number of cp configuration lists where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching cp configuration lists
	 */
	public static int countByG_C_NotS(
		long groupId, long companyId, int status) {

		return getPersistence().countByG_C_NotS(groupId, companyId, status);
	}

	/**
	 * Returns the number of cp configuration lists where groupId = any &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching cp configuration lists
	 */
	public static int countByG_C_NotS(
		long[] groupIds, long companyId, int status) {

		return getPersistence().countByG_C_NotS(groupIds, companyId, status);
	}

	/**
	 * Returns the cp configuration list where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchCPConfigurationListException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	public static CPConfigurationList findByERC_C(
			String externalReferenceCode, long companyId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPConfigurationListException {

		return getPersistence().findByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns the cp configuration list where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	public static CPConfigurationList fetchByERC_C(
		String externalReferenceCode, long companyId) {

		return getPersistence().fetchByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns the cp configuration list where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	public static CPConfigurationList fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache) {

		return getPersistence().fetchByERC_C(
			externalReferenceCode, companyId, useFinderCache);
	}

	/**
	 * Removes the cp configuration list where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the cp configuration list that was removed
	 */
	public static CPConfigurationList removeByERC_C(
			String externalReferenceCode, long companyId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPConfigurationListException {

		return getPersistence().removeByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns the number of cp configuration lists where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching cp configuration lists
	 */
	public static int countByERC_C(
		String externalReferenceCode, long companyId) {

		return getPersistence().countByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Caches the cp configuration list in the entity cache if it is enabled.
	 *
	 * @param cpConfigurationList the cp configuration list
	 */
	public static void cacheResult(CPConfigurationList cpConfigurationList) {
		getPersistence().cacheResult(cpConfigurationList);
	}

	/**
	 * Caches the cp configuration lists in the entity cache if it is enabled.
	 *
	 * @param cpConfigurationLists the cp configuration lists
	 */
	public static void cacheResult(
		List<CPConfigurationList> cpConfigurationLists) {

		getPersistence().cacheResult(cpConfigurationLists);
	}

	/**
	 * Creates a new cp configuration list with the primary key. Does not add the cp configuration list to the database.
	 *
	 * @param CPConfigurationListId the primary key for the new cp configuration list
	 * @return the new cp configuration list
	 */
	public static CPConfigurationList create(long CPConfigurationListId) {
		return getPersistence().create(CPConfigurationListId);
	}

	/**
	 * Removes the cp configuration list with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPConfigurationListId the primary key of the cp configuration list
	 * @return the cp configuration list that was removed
	 * @throws NoSuchCPConfigurationListException if a cp configuration list with the primary key could not be found
	 */
	public static CPConfigurationList remove(long CPConfigurationListId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPConfigurationListException {

		return getPersistence().remove(CPConfigurationListId);
	}

	public static CPConfigurationList updateImpl(
		CPConfigurationList cpConfigurationList) {

		return getPersistence().updateImpl(cpConfigurationList);
	}

	/**
	 * Returns the cp configuration list with the primary key or throws a <code>NoSuchCPConfigurationListException</code> if it could not be found.
	 *
	 * @param CPConfigurationListId the primary key of the cp configuration list
	 * @return the cp configuration list
	 * @throws NoSuchCPConfigurationListException if a cp configuration list with the primary key could not be found
	 */
	public static CPConfigurationList findByPrimaryKey(
			long CPConfigurationListId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPConfigurationListException {

		return getPersistence().findByPrimaryKey(CPConfigurationListId);
	}

	/**
	 * Returns the cp configuration list with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPConfigurationListId the primary key of the cp configuration list
	 * @return the cp configuration list, or <code>null</code> if a cp configuration list with the primary key could not be found
	 */
	public static CPConfigurationList fetchByPrimaryKey(
		long CPConfigurationListId) {

		return getPersistence().fetchByPrimaryKey(CPConfigurationListId);
	}

	/**
	 * Returns all the cp configuration lists.
	 *
	 * @return the cp configuration lists
	 */
	public static List<CPConfigurationList> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the cp configuration lists.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @return the range of cp configuration lists
	 */
	public static List<CPConfigurationList> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp configuration lists
	 */
	public static List<CPConfigurationList> findAll(
		int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp configuration lists
	 */
	public static List<CPConfigurationList> findAll(
		int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the cp configuration lists from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of cp configuration lists.
	 *
	 * @return the number of cp configuration lists
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CPConfigurationListPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		CPConfigurationListPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile CPConfigurationListPersistence _persistence;

}
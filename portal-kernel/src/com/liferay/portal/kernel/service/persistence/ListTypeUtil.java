/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the list type service. This utility wraps <code>com.liferay.portal.service.persistence.impl.ListTypePersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ListTypePersistence
 * @generated
 */
public class ListTypeUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(ListType listType) {
		getPersistence().clearCache(listType);
	}

	/**
	 * @see BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, ListType> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ListType> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ListType> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ListType> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ListType> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ListType update(ListType listType) {
		return getPersistence().update(listType);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ListType update(
		ListType listType, ServiceContext serviceContext) {

		return getPersistence().update(listType, serviceContext);
	}

	/**
	 * Returns all the list types where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching list types
	 */
	public static List<ListType> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the list types where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of list types
	 * @param end the upper bound of the range of list types (not inclusive)
	 * @return the range of matching list types
	 */
	public static List<ListType> findByUuid(String uuid, int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the list types where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of list types
	 * @param end the upper bound of the range of list types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching list types
	 */
	public static List<ListType> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ListType> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the list types where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of list types
	 * @param end the upper bound of the range of list types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching list types
	 */
	public static List<ListType> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ListType> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first list type in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type
	 * @throws NoSuchListTypeException if a matching list type could not be found
	 */
	public static ListType findByUuid_First(
			String uuid, OrderByComparator<ListType> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchListTypeException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first list type in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type, or <code>null</code> if a matching list type could not be found
	 */
	public static ListType fetchByUuid_First(
		String uuid, OrderByComparator<ListType> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last list type in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type
	 * @throws NoSuchListTypeException if a matching list type could not be found
	 */
	public static ListType findByUuid_Last(
			String uuid, OrderByComparator<ListType> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchListTypeException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last list type in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type, or <code>null</code> if a matching list type could not be found
	 */
	public static ListType fetchByUuid_Last(
		String uuid, OrderByComparator<ListType> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the list types before and after the current list type in the ordered set where uuid = &#63;.
	 *
	 * @param listTypeId the primary key of the current list type
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next list type
	 * @throws NoSuchListTypeException if a list type with the primary key could not be found
	 */
	public static ListType[] findByUuid_PrevAndNext(
			long listTypeId, String uuid,
			OrderByComparator<ListType> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchListTypeException {

		return getPersistence().findByUuid_PrevAndNext(
			listTypeId, uuid, orderByComparator);
	}

	/**
	 * Removes all the list types where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of list types where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching list types
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the list types where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching list types
	 */
	public static List<ListType> findByUuid_C(String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the list types where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of list types
	 * @param end the upper bound of the range of list types (not inclusive)
	 * @return the range of matching list types
	 */
	public static List<ListType> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the list types where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of list types
	 * @param end the upper bound of the range of list types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching list types
	 */
	public static List<ListType> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ListType> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the list types where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of list types
	 * @param end the upper bound of the range of list types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching list types
	 */
	public static List<ListType> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ListType> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first list type in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type
	 * @throws NoSuchListTypeException if a matching list type could not be found
	 */
	public static ListType findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ListType> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchListTypeException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first list type in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type, or <code>null</code> if a matching list type could not be found
	 */
	public static ListType fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ListType> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last list type in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type
	 * @throws NoSuchListTypeException if a matching list type could not be found
	 */
	public static ListType findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ListType> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchListTypeException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last list type in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type, or <code>null</code> if a matching list type could not be found
	 */
	public static ListType fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ListType> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the list types before and after the current list type in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param listTypeId the primary key of the current list type
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next list type
	 * @throws NoSuchListTypeException if a list type with the primary key could not be found
	 */
	public static ListType[] findByUuid_C_PrevAndNext(
			long listTypeId, String uuid, long companyId,
			OrderByComparator<ListType> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchListTypeException {

		return getPersistence().findByUuid_C_PrevAndNext(
			listTypeId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the list types where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of list types where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching list types
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the list types where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching list types
	 */
	public static List<ListType> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the list types where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of list types
	 * @param end the upper bound of the range of list types (not inclusive)
	 * @return the range of matching list types
	 */
	public static List<ListType> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the list types where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of list types
	 * @param end the upper bound of the range of list types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching list types
	 */
	public static List<ListType> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<ListType> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the list types where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of list types
	 * @param end the upper bound of the range of list types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching list types
	 */
	public static List<ListType> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<ListType> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first list type in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type
	 * @throws NoSuchListTypeException if a matching list type could not be found
	 */
	public static ListType findByCompanyId_First(
			long companyId, OrderByComparator<ListType> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchListTypeException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first list type in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type, or <code>null</code> if a matching list type could not be found
	 */
	public static ListType fetchByCompanyId_First(
		long companyId, OrderByComparator<ListType> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last list type in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type
	 * @throws NoSuchListTypeException if a matching list type could not be found
	 */
	public static ListType findByCompanyId_Last(
			long companyId, OrderByComparator<ListType> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchListTypeException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last list type in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type, or <code>null</code> if a matching list type could not be found
	 */
	public static ListType fetchByCompanyId_Last(
		long companyId, OrderByComparator<ListType> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the list types before and after the current list type in the ordered set where companyId = &#63;.
	 *
	 * @param listTypeId the primary key of the current list type
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next list type
	 * @throws NoSuchListTypeException if a list type with the primary key could not be found
	 */
	public static ListType[] findByCompanyId_PrevAndNext(
			long listTypeId, long companyId,
			OrderByComparator<ListType> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchListTypeException {

		return getPersistence().findByCompanyId_PrevAndNext(
			listTypeId, companyId, orderByComparator);
	}

	/**
	 * Removes all the list types where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of list types where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching list types
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns all the list types where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching list types
	 */
	public static List<ListType> findByC_T(long companyId, String type) {
		return getPersistence().findByC_T(companyId, type);
	}

	/**
	 * Returns a range of all the list types where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of list types
	 * @param end the upper bound of the range of list types (not inclusive)
	 * @return the range of matching list types
	 */
	public static List<ListType> findByC_T(
		long companyId, String type, int start, int end) {

		return getPersistence().findByC_T(companyId, type, start, end);
	}

	/**
	 * Returns an ordered range of all the list types where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of list types
	 * @param end the upper bound of the range of list types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching list types
	 */
	public static List<ListType> findByC_T(
		long companyId, String type, int start, int end,
		OrderByComparator<ListType> orderByComparator) {

		return getPersistence().findByC_T(
			companyId, type, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the list types where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of list types
	 * @param end the upper bound of the range of list types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching list types
	 */
	public static List<ListType> findByC_T(
		long companyId, String type, int start, int end,
		OrderByComparator<ListType> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByC_T(
			companyId, type, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first list type in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type
	 * @throws NoSuchListTypeException if a matching list type could not be found
	 */
	public static ListType findByC_T_First(
			long companyId, String type,
			OrderByComparator<ListType> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchListTypeException {

		return getPersistence().findByC_T_First(
			companyId, type, orderByComparator);
	}

	/**
	 * Returns the first list type in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type, or <code>null</code> if a matching list type could not be found
	 */
	public static ListType fetchByC_T_First(
		long companyId, String type,
		OrderByComparator<ListType> orderByComparator) {

		return getPersistence().fetchByC_T_First(
			companyId, type, orderByComparator);
	}

	/**
	 * Returns the last list type in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type
	 * @throws NoSuchListTypeException if a matching list type could not be found
	 */
	public static ListType findByC_T_Last(
			long companyId, String type,
			OrderByComparator<ListType> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchListTypeException {

		return getPersistence().findByC_T_Last(
			companyId, type, orderByComparator);
	}

	/**
	 * Returns the last list type in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type, or <code>null</code> if a matching list type could not be found
	 */
	public static ListType fetchByC_T_Last(
		long companyId, String type,
		OrderByComparator<ListType> orderByComparator) {

		return getPersistence().fetchByC_T_Last(
			companyId, type, orderByComparator);
	}

	/**
	 * Returns the list types before and after the current list type in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param listTypeId the primary key of the current list type
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next list type
	 * @throws NoSuchListTypeException if a list type with the primary key could not be found
	 */
	public static ListType[] findByC_T_PrevAndNext(
			long listTypeId, long companyId, String type,
			OrderByComparator<ListType> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchListTypeException {

		return getPersistence().findByC_T_PrevAndNext(
			listTypeId, companyId, type, orderByComparator);
	}

	/**
	 * Removes all the list types where companyId = &#63; and type = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 */
	public static void removeByC_T(long companyId, String type) {
		getPersistence().removeByC_T(companyId, type);
	}

	/**
	 * Returns the number of list types where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching list types
	 */
	public static int countByC_T(long companyId, String type) {
		return getPersistence().countByC_T(companyId, type);
	}

	/**
	 * Returns the list type where companyId = &#63; and name = &#63; and type = &#63; or throws a <code>NoSuchListTypeException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param type the type
	 * @return the matching list type
	 * @throws NoSuchListTypeException if a matching list type could not be found
	 */
	public static ListType findByC_N_T(long companyId, String name, String type)
		throws com.liferay.portal.kernel.exception.NoSuchListTypeException {

		return getPersistence().findByC_N_T(companyId, name, type);
	}

	/**
	 * Returns the list type where companyId = &#63; and name = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param type the type
	 * @return the matching list type, or <code>null</code> if a matching list type could not be found
	 */
	public static ListType fetchByC_N_T(
		long companyId, String name, String type) {

		return getPersistence().fetchByC_N_T(companyId, name, type);
	}

	/**
	 * Returns the list type where companyId = &#63; and name = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param type the type
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching list type, or <code>null</code> if a matching list type could not be found
	 */
	public static ListType fetchByC_N_T(
		long companyId, String name, String type, boolean useFinderCache) {

		return getPersistence().fetchByC_N_T(
			companyId, name, type, useFinderCache);
	}

	/**
	 * Removes the list type where companyId = &#63; and name = &#63; and type = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param type the type
	 * @return the list type that was removed
	 */
	public static ListType removeByC_N_T(
			long companyId, String name, String type)
		throws com.liferay.portal.kernel.exception.NoSuchListTypeException {

		return getPersistence().removeByC_N_T(companyId, name, type);
	}

	/**
	 * Returns the number of list types where companyId = &#63; and name = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param type the type
	 * @return the number of matching list types
	 */
	public static int countByC_N_T(long companyId, String name, String type) {
		return getPersistence().countByC_N_T(companyId, name, type);
	}

	/**
	 * Caches the list type in the entity cache if it is enabled.
	 *
	 * @param listType the list type
	 */
	public static void cacheResult(ListType listType) {
		getPersistence().cacheResult(listType);
	}

	/**
	 * Caches the list types in the entity cache if it is enabled.
	 *
	 * @param listTypes the list types
	 */
	public static void cacheResult(List<ListType> listTypes) {
		getPersistence().cacheResult(listTypes);
	}

	/**
	 * Creates a new list type with the primary key. Does not add the list type to the database.
	 *
	 * @param listTypeId the primary key for the new list type
	 * @return the new list type
	 */
	public static ListType create(long listTypeId) {
		return getPersistence().create(listTypeId);
	}

	/**
	 * Removes the list type with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param listTypeId the primary key of the list type
	 * @return the list type that was removed
	 * @throws NoSuchListTypeException if a list type with the primary key could not be found
	 */
	public static ListType remove(long listTypeId)
		throws com.liferay.portal.kernel.exception.NoSuchListTypeException {

		return getPersistence().remove(listTypeId);
	}

	public static ListType updateImpl(ListType listType) {
		return getPersistence().updateImpl(listType);
	}

	/**
	 * Returns the list type with the primary key or throws a <code>NoSuchListTypeException</code> if it could not be found.
	 *
	 * @param listTypeId the primary key of the list type
	 * @return the list type
	 * @throws NoSuchListTypeException if a list type with the primary key could not be found
	 */
	public static ListType findByPrimaryKey(long listTypeId)
		throws com.liferay.portal.kernel.exception.NoSuchListTypeException {

		return getPersistence().findByPrimaryKey(listTypeId);
	}

	/**
	 * Returns the list type with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param listTypeId the primary key of the list type
	 * @return the list type, or <code>null</code> if a list type with the primary key could not be found
	 */
	public static ListType fetchByPrimaryKey(long listTypeId) {
		return getPersistence().fetchByPrimaryKey(listTypeId);
	}

	/**
	 * Returns all the list types.
	 *
	 * @return the list types
	 */
	public static List<ListType> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the list types.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of list types
	 * @param end the upper bound of the range of list types (not inclusive)
	 * @return the range of list types
	 */
	public static List<ListType> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the list types.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of list types
	 * @param end the upper bound of the range of list types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of list types
	 */
	public static List<ListType> findAll(
		int start, int end, OrderByComparator<ListType> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the list types.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of list types
	 * @param end the upper bound of the range of list types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of list types
	 */
	public static List<ListType> findAll(
		int start, int end, OrderByComparator<ListType> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the list types from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of list types.
	 *
	 * @return the number of list types
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ListTypePersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(ListTypePersistence persistence) {
		_persistence = persistence;
	}

	private static volatile ListTypePersistence _persistence;

}
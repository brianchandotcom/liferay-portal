/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.persistence;

import com.liferay.commerce.product.model.CPDisplayLayout;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the cp display layout service. This utility wraps <code>com.liferay.commerce.product.service.persistence.impl.CPDisplayLayoutPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPDisplayLayoutPersistence
 * @generated
 */
public class CPDisplayLayoutUtil {

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
	public static void clearCache(CPDisplayLayout cpDisplayLayout) {
		getPersistence().clearCache(cpDisplayLayout);
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
	public static Map<Serializable, CPDisplayLayout> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CPDisplayLayout> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CPDisplayLayout> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CPDisplayLayout> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CPDisplayLayout> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CPDisplayLayout update(CPDisplayLayout cpDisplayLayout) {
		return getPersistence().update(cpDisplayLayout);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CPDisplayLayout update(
		CPDisplayLayout cpDisplayLayout, ServiceContext serviceContext) {

		return getPersistence().update(cpDisplayLayout, serviceContext);
	}

	/**
	 * Returns all the cp display layouts where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp display layouts
	 */
	public static List<CPDisplayLayout> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the cp display layouts where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDisplayLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @return the range of matching cp display layouts
	 */
	public static List<CPDisplayLayout> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the cp display layouts where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDisplayLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp display layouts
	 */
	public static List<CPDisplayLayout> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPDisplayLayout> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp display layouts where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDisplayLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp display layouts
	 */
	public static List<CPDisplayLayout> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPDisplayLayout> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cp display layout in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp display layout
	 * @throws NoSuchCPDisplayLayoutException if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout findByUuid_First(
			String uuid, OrderByComparator<CPDisplayLayout> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDisplayLayoutException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first cp display layout in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp display layout, or <code>null</code> if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout fetchByUuid_First(
		String uuid, OrderByComparator<CPDisplayLayout> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last cp display layout in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp display layout
	 * @throws NoSuchCPDisplayLayoutException if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout findByUuid_Last(
			String uuid, OrderByComparator<CPDisplayLayout> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDisplayLayoutException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last cp display layout in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp display layout, or <code>null</code> if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout fetchByUuid_Last(
		String uuid, OrderByComparator<CPDisplayLayout> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the cp display layouts before and after the current cp display layout in the ordered set where uuid = &#63;.
	 *
	 * @param CPDisplayLayoutId the primary key of the current cp display layout
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp display layout
	 * @throws NoSuchCPDisplayLayoutException if a cp display layout with the primary key could not be found
	 */
	public static CPDisplayLayout[] findByUuid_PrevAndNext(
			long CPDisplayLayoutId, String uuid,
			OrderByComparator<CPDisplayLayout> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDisplayLayoutException {

		return getPersistence().findByUuid_PrevAndNext(
			CPDisplayLayoutId, uuid, orderByComparator);
	}

	/**
	 * Removes all the cp display layouts where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of cp display layouts where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp display layouts
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the cp display layout where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchCPDisplayLayoutException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp display layout
	 * @throws NoSuchCPDisplayLayoutException if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout findByUUID_G(String uuid, long groupId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDisplayLayoutException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the cp display layout where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp display layout, or <code>null</code> if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the cp display layout where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp display layout, or <code>null</code> if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the cp display layout where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the cp display layout that was removed
	 */
	public static CPDisplayLayout removeByUUID_G(String uuid, long groupId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDisplayLayoutException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of cp display layouts where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching cp display layouts
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the cp display layouts where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp display layouts
	 */
	public static List<CPDisplayLayout> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the cp display layouts where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDisplayLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @return the range of matching cp display layouts
	 */
	public static List<CPDisplayLayout> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the cp display layouts where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDisplayLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp display layouts
	 */
	public static List<CPDisplayLayout> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPDisplayLayout> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp display layouts where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDisplayLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp display layouts
	 */
	public static List<CPDisplayLayout> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPDisplayLayout> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cp display layout in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp display layout
	 * @throws NoSuchCPDisplayLayoutException if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CPDisplayLayout> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDisplayLayoutException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first cp display layout in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp display layout, or <code>null</code> if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CPDisplayLayout> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last cp display layout in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp display layout
	 * @throws NoSuchCPDisplayLayoutException if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CPDisplayLayout> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDisplayLayoutException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last cp display layout in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp display layout, or <code>null</code> if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CPDisplayLayout> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the cp display layouts before and after the current cp display layout in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CPDisplayLayoutId the primary key of the current cp display layout
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp display layout
	 * @throws NoSuchCPDisplayLayoutException if a cp display layout with the primary key could not be found
	 */
	public static CPDisplayLayout[] findByUuid_C_PrevAndNext(
			long CPDisplayLayoutId, String uuid, long companyId,
			OrderByComparator<CPDisplayLayout> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDisplayLayoutException {

		return getPersistence().findByUuid_C_PrevAndNext(
			CPDisplayLayoutId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the cp display layouts where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of cp display layouts where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp display layouts
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the cp display layouts where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching cp display layouts
	 */
	public static List<CPDisplayLayout> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	 * Returns a range of all the cp display layouts where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDisplayLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @return the range of matching cp display layouts
	 */
	public static List<CPDisplayLayout> findByGroupId(
		long groupId, int start, int end) {

		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the cp display layouts where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDisplayLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp display layouts
	 */
	public static List<CPDisplayLayout> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CPDisplayLayout> orderByComparator) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp display layouts where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDisplayLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp display layouts
	 */
	public static List<CPDisplayLayout> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CPDisplayLayout> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cp display layout in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp display layout
	 * @throws NoSuchCPDisplayLayoutException if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout findByGroupId_First(
			long groupId, OrderByComparator<CPDisplayLayout> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDisplayLayoutException {

		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	 * Returns the first cp display layout in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp display layout, or <code>null</code> if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout fetchByGroupId_First(
		long groupId, OrderByComparator<CPDisplayLayout> orderByComparator) {

		return getPersistence().fetchByGroupId_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last cp display layout in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp display layout
	 * @throws NoSuchCPDisplayLayoutException if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout findByGroupId_Last(
			long groupId, OrderByComparator<CPDisplayLayout> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDisplayLayoutException {

		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the last cp display layout in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp display layout, or <code>null</code> if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout fetchByGroupId_Last(
		long groupId, OrderByComparator<CPDisplayLayout> orderByComparator) {

		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the cp display layouts before and after the current cp display layout in the ordered set where groupId = &#63;.
	 *
	 * @param CPDisplayLayoutId the primary key of the current cp display layout
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp display layout
	 * @throws NoSuchCPDisplayLayoutException if a cp display layout with the primary key could not be found
	 */
	public static CPDisplayLayout[] findByGroupId_PrevAndNext(
			long CPDisplayLayoutId, long groupId,
			OrderByComparator<CPDisplayLayout> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDisplayLayoutException {

		return getPersistence().findByGroupId_PrevAndNext(
			CPDisplayLayoutId, groupId, orderByComparator);
	}

	/**
	 * Removes all the cp display layouts where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	 * Returns the number of cp display layouts where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching cp display layouts
	 */
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	 * Returns all the cp display layouts where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the matching cp display layouts
	 */
	public static List<CPDisplayLayout> findByG_C(
		long groupId, long classNameId) {

		return getPersistence().findByG_C(groupId, classNameId);
	}

	/**
	 * Returns a range of all the cp display layouts where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDisplayLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @return the range of matching cp display layouts
	 */
	public static List<CPDisplayLayout> findByG_C(
		long groupId, long classNameId, int start, int end) {

		return getPersistence().findByG_C(groupId, classNameId, start, end);
	}

	/**
	 * Returns an ordered range of all the cp display layouts where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDisplayLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp display layouts
	 */
	public static List<CPDisplayLayout> findByG_C(
		long groupId, long classNameId, int start, int end,
		OrderByComparator<CPDisplayLayout> orderByComparator) {

		return getPersistence().findByG_C(
			groupId, classNameId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp display layouts where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDisplayLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp display layouts
	 */
	public static List<CPDisplayLayout> findByG_C(
		long groupId, long classNameId, int start, int end,
		OrderByComparator<CPDisplayLayout> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_C(
			groupId, classNameId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first cp display layout in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp display layout
	 * @throws NoSuchCPDisplayLayoutException if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout findByG_C_First(
			long groupId, long classNameId,
			OrderByComparator<CPDisplayLayout> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDisplayLayoutException {

		return getPersistence().findByG_C_First(
			groupId, classNameId, orderByComparator);
	}

	/**
	 * Returns the first cp display layout in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp display layout, or <code>null</code> if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout fetchByG_C_First(
		long groupId, long classNameId,
		OrderByComparator<CPDisplayLayout> orderByComparator) {

		return getPersistence().fetchByG_C_First(
			groupId, classNameId, orderByComparator);
	}

	/**
	 * Returns the last cp display layout in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp display layout
	 * @throws NoSuchCPDisplayLayoutException if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout findByG_C_Last(
			long groupId, long classNameId,
			OrderByComparator<CPDisplayLayout> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDisplayLayoutException {

		return getPersistence().findByG_C_Last(
			groupId, classNameId, orderByComparator);
	}

	/**
	 * Returns the last cp display layout in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp display layout, or <code>null</code> if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout fetchByG_C_Last(
		long groupId, long classNameId,
		OrderByComparator<CPDisplayLayout> orderByComparator) {

		return getPersistence().fetchByG_C_Last(
			groupId, classNameId, orderByComparator);
	}

	/**
	 * Returns the cp display layouts before and after the current cp display layout in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param CPDisplayLayoutId the primary key of the current cp display layout
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp display layout
	 * @throws NoSuchCPDisplayLayoutException if a cp display layout with the primary key could not be found
	 */
	public static CPDisplayLayout[] findByG_C_PrevAndNext(
			long CPDisplayLayoutId, long groupId, long classNameId,
			OrderByComparator<CPDisplayLayout> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDisplayLayoutException {

		return getPersistence().findByG_C_PrevAndNext(
			CPDisplayLayoutId, groupId, classNameId, orderByComparator);
	}

	/**
	 * Removes all the cp display layouts where groupId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 */
	public static void removeByG_C(long groupId, long classNameId) {
		getPersistence().removeByG_C(groupId, classNameId);
	}

	/**
	 * Returns the number of cp display layouts where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the number of matching cp display layouts
	 */
	public static int countByG_C(long groupId, long classNameId) {
		return getPersistence().countByG_C(groupId, classNameId);
	}

	/**
	 * Returns all the cp display layouts where groupId = &#63; and layoutPageTemplateEntryUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryUuid the layout page template entry uuid
	 * @return the matching cp display layouts
	 */
	public static List<CPDisplayLayout> findByG_LPTEU(
		long groupId, String layoutPageTemplateEntryUuid) {

		return getPersistence().findByG_LPTEU(
			groupId, layoutPageTemplateEntryUuid);
	}

	/**
	 * Returns a range of all the cp display layouts where groupId = &#63; and layoutPageTemplateEntryUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDisplayLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryUuid the layout page template entry uuid
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @return the range of matching cp display layouts
	 */
	public static List<CPDisplayLayout> findByG_LPTEU(
		long groupId, String layoutPageTemplateEntryUuid, int start, int end) {

		return getPersistence().findByG_LPTEU(
			groupId, layoutPageTemplateEntryUuid, start, end);
	}

	/**
	 * Returns an ordered range of all the cp display layouts where groupId = &#63; and layoutPageTemplateEntryUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDisplayLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryUuid the layout page template entry uuid
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp display layouts
	 */
	public static List<CPDisplayLayout> findByG_LPTEU(
		long groupId, String layoutPageTemplateEntryUuid, int start, int end,
		OrderByComparator<CPDisplayLayout> orderByComparator) {

		return getPersistence().findByG_LPTEU(
			groupId, layoutPageTemplateEntryUuid, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp display layouts where groupId = &#63; and layoutPageTemplateEntryUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDisplayLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryUuid the layout page template entry uuid
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp display layouts
	 */
	public static List<CPDisplayLayout> findByG_LPTEU(
		long groupId, String layoutPageTemplateEntryUuid, int start, int end,
		OrderByComparator<CPDisplayLayout> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_LPTEU(
			groupId, layoutPageTemplateEntryUuid, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first cp display layout in the ordered set where groupId = &#63; and layoutPageTemplateEntryUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryUuid the layout page template entry uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp display layout
	 * @throws NoSuchCPDisplayLayoutException if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout findByG_LPTEU_First(
			long groupId, String layoutPageTemplateEntryUuid,
			OrderByComparator<CPDisplayLayout> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDisplayLayoutException {

		return getPersistence().findByG_LPTEU_First(
			groupId, layoutPageTemplateEntryUuid, orderByComparator);
	}

	/**
	 * Returns the first cp display layout in the ordered set where groupId = &#63; and layoutPageTemplateEntryUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryUuid the layout page template entry uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp display layout, or <code>null</code> if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout fetchByG_LPTEU_First(
		long groupId, String layoutPageTemplateEntryUuid,
		OrderByComparator<CPDisplayLayout> orderByComparator) {

		return getPersistence().fetchByG_LPTEU_First(
			groupId, layoutPageTemplateEntryUuid, orderByComparator);
	}

	/**
	 * Returns the last cp display layout in the ordered set where groupId = &#63; and layoutPageTemplateEntryUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryUuid the layout page template entry uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp display layout
	 * @throws NoSuchCPDisplayLayoutException if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout findByG_LPTEU_Last(
			long groupId, String layoutPageTemplateEntryUuid,
			OrderByComparator<CPDisplayLayout> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDisplayLayoutException {

		return getPersistence().findByG_LPTEU_Last(
			groupId, layoutPageTemplateEntryUuid, orderByComparator);
	}

	/**
	 * Returns the last cp display layout in the ordered set where groupId = &#63; and layoutPageTemplateEntryUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryUuid the layout page template entry uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp display layout, or <code>null</code> if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout fetchByG_LPTEU_Last(
		long groupId, String layoutPageTemplateEntryUuid,
		OrderByComparator<CPDisplayLayout> orderByComparator) {

		return getPersistence().fetchByG_LPTEU_Last(
			groupId, layoutPageTemplateEntryUuid, orderByComparator);
	}

	/**
	 * Returns the cp display layouts before and after the current cp display layout in the ordered set where groupId = &#63; and layoutPageTemplateEntryUuid = &#63;.
	 *
	 * @param CPDisplayLayoutId the primary key of the current cp display layout
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryUuid the layout page template entry uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp display layout
	 * @throws NoSuchCPDisplayLayoutException if a cp display layout with the primary key could not be found
	 */
	public static CPDisplayLayout[] findByG_LPTEU_PrevAndNext(
			long CPDisplayLayoutId, long groupId,
			String layoutPageTemplateEntryUuid,
			OrderByComparator<CPDisplayLayout> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDisplayLayoutException {

		return getPersistence().findByG_LPTEU_PrevAndNext(
			CPDisplayLayoutId, groupId, layoutPageTemplateEntryUuid,
			orderByComparator);
	}

	/**
	 * Removes all the cp display layouts where groupId = &#63; and layoutPageTemplateEntryUuid = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryUuid the layout page template entry uuid
	 */
	public static void removeByG_LPTEU(
		long groupId, String layoutPageTemplateEntryUuid) {

		getPersistence().removeByG_LPTEU(groupId, layoutPageTemplateEntryUuid);
	}

	/**
	 * Returns the number of cp display layouts where groupId = &#63; and layoutPageTemplateEntryUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryUuid the layout page template entry uuid
	 * @return the number of matching cp display layouts
	 */
	public static int countByG_LPTEU(
		long groupId, String layoutPageTemplateEntryUuid) {

		return getPersistence().countByG_LPTEU(
			groupId, layoutPageTemplateEntryUuid);
	}

	/**
	 * Returns all the cp display layouts where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @return the matching cp display layouts
	 */
	public static List<CPDisplayLayout> findByG_L(
		long groupId, String layoutUuid) {

		return getPersistence().findByG_L(groupId, layoutUuid);
	}

	/**
	 * Returns a range of all the cp display layouts where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDisplayLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @return the range of matching cp display layouts
	 */
	public static List<CPDisplayLayout> findByG_L(
		long groupId, String layoutUuid, int start, int end) {

		return getPersistence().findByG_L(groupId, layoutUuid, start, end);
	}

	/**
	 * Returns an ordered range of all the cp display layouts where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDisplayLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp display layouts
	 */
	public static List<CPDisplayLayout> findByG_L(
		long groupId, String layoutUuid, int start, int end,
		OrderByComparator<CPDisplayLayout> orderByComparator) {

		return getPersistence().findByG_L(
			groupId, layoutUuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp display layouts where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDisplayLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp display layouts
	 */
	public static List<CPDisplayLayout> findByG_L(
		long groupId, String layoutUuid, int start, int end,
		OrderByComparator<CPDisplayLayout> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_L(
			groupId, layoutUuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cp display layout in the ordered set where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp display layout
	 * @throws NoSuchCPDisplayLayoutException if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout findByG_L_First(
			long groupId, String layoutUuid,
			OrderByComparator<CPDisplayLayout> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDisplayLayoutException {

		return getPersistence().findByG_L_First(
			groupId, layoutUuid, orderByComparator);
	}

	/**
	 * Returns the first cp display layout in the ordered set where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp display layout, or <code>null</code> if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout fetchByG_L_First(
		long groupId, String layoutUuid,
		OrderByComparator<CPDisplayLayout> orderByComparator) {

		return getPersistence().fetchByG_L_First(
			groupId, layoutUuid, orderByComparator);
	}

	/**
	 * Returns the last cp display layout in the ordered set where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp display layout
	 * @throws NoSuchCPDisplayLayoutException if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout findByG_L_Last(
			long groupId, String layoutUuid,
			OrderByComparator<CPDisplayLayout> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDisplayLayoutException {

		return getPersistence().findByG_L_Last(
			groupId, layoutUuid, orderByComparator);
	}

	/**
	 * Returns the last cp display layout in the ordered set where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp display layout, or <code>null</code> if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout fetchByG_L_Last(
		long groupId, String layoutUuid,
		OrderByComparator<CPDisplayLayout> orderByComparator) {

		return getPersistence().fetchByG_L_Last(
			groupId, layoutUuid, orderByComparator);
	}

	/**
	 * Returns the cp display layouts before and after the current cp display layout in the ordered set where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * @param CPDisplayLayoutId the primary key of the current cp display layout
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp display layout
	 * @throws NoSuchCPDisplayLayoutException if a cp display layout with the primary key could not be found
	 */
	public static CPDisplayLayout[] findByG_L_PrevAndNext(
			long CPDisplayLayoutId, long groupId, String layoutUuid,
			OrderByComparator<CPDisplayLayout> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDisplayLayoutException {

		return getPersistence().findByG_L_PrevAndNext(
			CPDisplayLayoutId, groupId, layoutUuid, orderByComparator);
	}

	/**
	 * Removes all the cp display layouts where groupId = &#63; and layoutUuid = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 */
	public static void removeByG_L(long groupId, String layoutUuid) {
		getPersistence().removeByG_L(groupId, layoutUuid);
	}

	/**
	 * Returns the number of cp display layouts where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @return the number of matching cp display layouts
	 */
	public static int countByG_L(long groupId, String layoutUuid) {
		return getPersistence().countByG_L(groupId, layoutUuid);
	}

	/**
	 * Returns all the cp display layouts where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching cp display layouts
	 */
	public static List<CPDisplayLayout> findByC_C(
		long classNameId, long classPK) {

		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	 * Returns a range of all the cp display layouts where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDisplayLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @return the range of matching cp display layouts
	 */
	public static List<CPDisplayLayout> findByC_C(
		long classNameId, long classPK, int start, int end) {

		return getPersistence().findByC_C(classNameId, classPK, start, end);
	}

	/**
	 * Returns an ordered range of all the cp display layouts where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDisplayLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp display layouts
	 */
	public static List<CPDisplayLayout> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<CPDisplayLayout> orderByComparator) {

		return getPersistence().findByC_C(
			classNameId, classPK, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp display layouts where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDisplayLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp display layouts
	 */
	public static List<CPDisplayLayout> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<CPDisplayLayout> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_C(
			classNameId, classPK, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first cp display layout in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp display layout
	 * @throws NoSuchCPDisplayLayoutException if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout findByC_C_First(
			long classNameId, long classPK,
			OrderByComparator<CPDisplayLayout> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDisplayLayoutException {

		return getPersistence().findByC_C_First(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the first cp display layout in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp display layout, or <code>null</code> if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout fetchByC_C_First(
		long classNameId, long classPK,
		OrderByComparator<CPDisplayLayout> orderByComparator) {

		return getPersistence().fetchByC_C_First(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the last cp display layout in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp display layout
	 * @throws NoSuchCPDisplayLayoutException if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout findByC_C_Last(
			long classNameId, long classPK,
			OrderByComparator<CPDisplayLayout> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDisplayLayoutException {

		return getPersistence().findByC_C_Last(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the last cp display layout in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp display layout, or <code>null</code> if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout fetchByC_C_Last(
		long classNameId, long classPK,
		OrderByComparator<CPDisplayLayout> orderByComparator) {

		return getPersistence().fetchByC_C_Last(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the cp display layouts before and after the current cp display layout in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param CPDisplayLayoutId the primary key of the current cp display layout
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp display layout
	 * @throws NoSuchCPDisplayLayoutException if a cp display layout with the primary key could not be found
	 */
	public static CPDisplayLayout[] findByC_C_PrevAndNext(
			long CPDisplayLayoutId, long classNameId, long classPK,
			OrderByComparator<CPDisplayLayout> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDisplayLayoutException {

		return getPersistence().findByC_C_PrevAndNext(
			CPDisplayLayoutId, classNameId, classPK, orderByComparator);
	}

	/**
	 * Removes all the cp display layouts where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public static void removeByC_C(long classNameId, long classPK) {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	 * Returns the number of cp display layouts where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching cp display layouts
	 */
	public static int countByC_C(long classNameId, long classPK) {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	 * Returns all the cp display layouts where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching cp display layouts
	 */
	public static List<CPDisplayLayout> findByC_C_LPTEU(
		long classNameId, long classPK) {

		return getPersistence().findByC_C_LPTEU(classNameId, classPK);
	}

	/**
	 * Returns a range of all the cp display layouts where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDisplayLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @return the range of matching cp display layouts
	 */
	public static List<CPDisplayLayout> findByC_C_LPTEU(
		long classNameId, long classPK, int start, int end) {

		return getPersistence().findByC_C_LPTEU(
			classNameId, classPK, start, end);
	}

	/**
	 * Returns an ordered range of all the cp display layouts where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDisplayLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp display layouts
	 */
	public static List<CPDisplayLayout> findByC_C_LPTEU(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<CPDisplayLayout> orderByComparator) {

		return getPersistence().findByC_C_LPTEU(
			classNameId, classPK, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp display layouts where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDisplayLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp display layouts
	 */
	public static List<CPDisplayLayout> findByC_C_LPTEU(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<CPDisplayLayout> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_C_LPTEU(
			classNameId, classPK, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first cp display layout in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp display layout
	 * @throws NoSuchCPDisplayLayoutException if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout findByC_C_LPTEU_First(
			long classNameId, long classPK,
			OrderByComparator<CPDisplayLayout> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDisplayLayoutException {

		return getPersistence().findByC_C_LPTEU_First(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the first cp display layout in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp display layout, or <code>null</code> if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout fetchByC_C_LPTEU_First(
		long classNameId, long classPK,
		OrderByComparator<CPDisplayLayout> orderByComparator) {

		return getPersistence().fetchByC_C_LPTEU_First(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the last cp display layout in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp display layout
	 * @throws NoSuchCPDisplayLayoutException if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout findByC_C_LPTEU_Last(
			long classNameId, long classPK,
			OrderByComparator<CPDisplayLayout> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDisplayLayoutException {

		return getPersistence().findByC_C_LPTEU_Last(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the last cp display layout in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp display layout, or <code>null</code> if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout fetchByC_C_LPTEU_Last(
		long classNameId, long classPK,
		OrderByComparator<CPDisplayLayout> orderByComparator) {

		return getPersistence().fetchByC_C_LPTEU_Last(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the cp display layouts before and after the current cp display layout in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param CPDisplayLayoutId the primary key of the current cp display layout
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp display layout
	 * @throws NoSuchCPDisplayLayoutException if a cp display layout with the primary key could not be found
	 */
	public static CPDisplayLayout[] findByC_C_LPTEU_PrevAndNext(
			long CPDisplayLayoutId, long classNameId, long classPK,
			OrderByComparator<CPDisplayLayout> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDisplayLayoutException {

		return getPersistence().findByC_C_LPTEU_PrevAndNext(
			CPDisplayLayoutId, classNameId, classPK, orderByComparator);
	}

	/**
	 * Removes all the cp display layouts where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public static void removeByC_C_LPTEU(long classNameId, long classPK) {
		getPersistence().removeByC_C_LPTEU(classNameId, classPK);
	}

	/**
	 * Returns the number of cp display layouts where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching cp display layouts
	 */
	public static int countByC_C_LPTEU(long classNameId, long classPK) {
		return getPersistence().countByC_C_LPTEU(classNameId, classPK);
	}

	/**
	 * Returns all the cp display layouts where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching cp display layouts
	 */
	public static List<CPDisplayLayout> findByC_C_L(
		long classNameId, long classPK) {

		return getPersistence().findByC_C_L(classNameId, classPK);
	}

	/**
	 * Returns a range of all the cp display layouts where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDisplayLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @return the range of matching cp display layouts
	 */
	public static List<CPDisplayLayout> findByC_C_L(
		long classNameId, long classPK, int start, int end) {

		return getPersistence().findByC_C_L(classNameId, classPK, start, end);
	}

	/**
	 * Returns an ordered range of all the cp display layouts where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDisplayLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp display layouts
	 */
	public static List<CPDisplayLayout> findByC_C_L(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<CPDisplayLayout> orderByComparator) {

		return getPersistence().findByC_C_L(
			classNameId, classPK, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp display layouts where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDisplayLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp display layouts
	 */
	public static List<CPDisplayLayout> findByC_C_L(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<CPDisplayLayout> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_C_L(
			classNameId, classPK, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first cp display layout in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp display layout
	 * @throws NoSuchCPDisplayLayoutException if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout findByC_C_L_First(
			long classNameId, long classPK,
			OrderByComparator<CPDisplayLayout> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDisplayLayoutException {

		return getPersistence().findByC_C_L_First(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the first cp display layout in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp display layout, or <code>null</code> if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout fetchByC_C_L_First(
		long classNameId, long classPK,
		OrderByComparator<CPDisplayLayout> orderByComparator) {

		return getPersistence().fetchByC_C_L_First(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the last cp display layout in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp display layout
	 * @throws NoSuchCPDisplayLayoutException if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout findByC_C_L_Last(
			long classNameId, long classPK,
			OrderByComparator<CPDisplayLayout> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDisplayLayoutException {

		return getPersistence().findByC_C_L_Last(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the last cp display layout in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp display layout, or <code>null</code> if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout fetchByC_C_L_Last(
		long classNameId, long classPK,
		OrderByComparator<CPDisplayLayout> orderByComparator) {

		return getPersistence().fetchByC_C_L_Last(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the cp display layouts before and after the current cp display layout in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param CPDisplayLayoutId the primary key of the current cp display layout
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp display layout
	 * @throws NoSuchCPDisplayLayoutException if a cp display layout with the primary key could not be found
	 */
	public static CPDisplayLayout[] findByC_C_L_PrevAndNext(
			long CPDisplayLayoutId, long classNameId, long classPK,
			OrderByComparator<CPDisplayLayout> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDisplayLayoutException {

		return getPersistence().findByC_C_L_PrevAndNext(
			CPDisplayLayoutId, classNameId, classPK, orderByComparator);
	}

	/**
	 * Removes all the cp display layouts where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public static void removeByC_C_L(long classNameId, long classPK) {
		getPersistence().removeByC_C_L(classNameId, classPK);
	}

	/**
	 * Returns the number of cp display layouts where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching cp display layouts
	 */
	public static int countByC_C_L(long classNameId, long classPK) {
		return getPersistence().countByC_C_L(classNameId, classPK);
	}

	/**
	 * Returns the cp display layout where groupId = &#63; and classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchCPDisplayLayoutException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching cp display layout
	 * @throws NoSuchCPDisplayLayoutException if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout findByG_C_C(
			long groupId, long classNameId, long classPK)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDisplayLayoutException {

		return getPersistence().findByG_C_C(groupId, classNameId, classPK);
	}

	/**
	 * Returns the cp display layout where groupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching cp display layout, or <code>null</code> if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout fetchByG_C_C(
		long groupId, long classNameId, long classPK) {

		return getPersistence().fetchByG_C_C(groupId, classNameId, classPK);
	}

	/**
	 * Returns the cp display layout where groupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp display layout, or <code>null</code> if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout fetchByG_C_C(
		long groupId, long classNameId, long classPK, boolean useFinderCache) {

		return getPersistence().fetchByG_C_C(
			groupId, classNameId, classPK, useFinderCache);
	}

	/**
	 * Removes the cp display layout where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the cp display layout that was removed
	 */
	public static CPDisplayLayout removeByG_C_C(
			long groupId, long classNameId, long classPK)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDisplayLayoutException {

		return getPersistence().removeByG_C_C(groupId, classNameId, classPK);
	}

	/**
	 * Returns the number of cp display layouts where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching cp display layouts
	 */
	public static int countByG_C_C(
		long groupId, long classNameId, long classPK) {

		return getPersistence().countByG_C_C(groupId, classNameId, classPK);
	}

	/**
	 * Caches the cp display layout in the entity cache if it is enabled.
	 *
	 * @param cpDisplayLayout the cp display layout
	 */
	public static void cacheResult(CPDisplayLayout cpDisplayLayout) {
		getPersistence().cacheResult(cpDisplayLayout);
	}

	/**
	 * Caches the cp display layouts in the entity cache if it is enabled.
	 *
	 * @param cpDisplayLayouts the cp display layouts
	 */
	public static void cacheResult(List<CPDisplayLayout> cpDisplayLayouts) {
		getPersistence().cacheResult(cpDisplayLayouts);
	}

	/**
	 * Creates a new cp display layout with the primary key. Does not add the cp display layout to the database.
	 *
	 * @param CPDisplayLayoutId the primary key for the new cp display layout
	 * @return the new cp display layout
	 */
	public static CPDisplayLayout create(long CPDisplayLayoutId) {
		return getPersistence().create(CPDisplayLayoutId);
	}

	/**
	 * Removes the cp display layout with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPDisplayLayoutId the primary key of the cp display layout
	 * @return the cp display layout that was removed
	 * @throws NoSuchCPDisplayLayoutException if a cp display layout with the primary key could not be found
	 */
	public static CPDisplayLayout remove(long CPDisplayLayoutId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDisplayLayoutException {

		return getPersistence().remove(CPDisplayLayoutId);
	}

	public static CPDisplayLayout updateImpl(CPDisplayLayout cpDisplayLayout) {
		return getPersistence().updateImpl(cpDisplayLayout);
	}

	/**
	 * Returns the cp display layout with the primary key or throws a <code>NoSuchCPDisplayLayoutException</code> if it could not be found.
	 *
	 * @param CPDisplayLayoutId the primary key of the cp display layout
	 * @return the cp display layout
	 * @throws NoSuchCPDisplayLayoutException if a cp display layout with the primary key could not be found
	 */
	public static CPDisplayLayout findByPrimaryKey(long CPDisplayLayoutId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDisplayLayoutException {

		return getPersistence().findByPrimaryKey(CPDisplayLayoutId);
	}

	/**
	 * Returns the cp display layout with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPDisplayLayoutId the primary key of the cp display layout
	 * @return the cp display layout, or <code>null</code> if a cp display layout with the primary key could not be found
	 */
	public static CPDisplayLayout fetchByPrimaryKey(long CPDisplayLayoutId) {
		return getPersistence().fetchByPrimaryKey(CPDisplayLayoutId);
	}

	/**
	 * Returns all the cp display layouts.
	 *
	 * @return the cp display layouts
	 */
	public static List<CPDisplayLayout> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the cp display layouts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDisplayLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @return the range of cp display layouts
	 */
	public static List<CPDisplayLayout> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the cp display layouts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDisplayLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp display layouts
	 */
	public static List<CPDisplayLayout> findAll(
		int start, int end,
		OrderByComparator<CPDisplayLayout> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp display layouts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDisplayLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp display layouts
	 */
	public static List<CPDisplayLayout> findAll(
		int start, int end,
		OrderByComparator<CPDisplayLayout> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the cp display layouts from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of cp display layouts.
	 *
	 * @return the number of cp display layouts
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CPDisplayLayoutPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(CPDisplayLayoutPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile CPDisplayLayoutPersistence _persistence;

}
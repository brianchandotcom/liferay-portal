/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.term.service.persistence;

import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the commerce term entry service. This utility wraps <code>com.liferay.commerce.term.service.persistence.impl.CommerceTermEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceTermEntryPersistence
 * @generated
 */
public class CommerceTermEntryUtil {

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
	public static void clearCache(CommerceTermEntry commerceTermEntry) {
		getPersistence().clearCache(commerceTermEntry);
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
	public static Map<Serializable, CommerceTermEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceTermEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceTermEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceTermEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceTermEntry update(
		CommerceTermEntry commerceTermEntry) {

		return getPersistence().update(commerceTermEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceTermEntry update(
		CommerceTermEntry commerceTermEntry, ServiceContext serviceContext) {

		return getPersistence().update(commerceTermEntry, serviceContext);
	}

	/**
	 * Returns all the commerce term entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce term entries
	 */
	public static List<CommerceTermEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the commerce term entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @return the range of matching commerce term entries
	 */
	public static List<CommerceTermEntry> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce term entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce term entries
	 */
	public static List<CommerceTermEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce term entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce term entries
	 */
	public static List<CommerceTermEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce term entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	public static CommerceTermEntry findByUuid_First(
			String uuid, OrderByComparator<CommerceTermEntry> orderByComparator)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first commerce term entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	public static CommerceTermEntry fetchByUuid_First(
		String uuid, OrderByComparator<CommerceTermEntry> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last commerce term entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	public static CommerceTermEntry findByUuid_Last(
			String uuid, OrderByComparator<CommerceTermEntry> orderByComparator)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last commerce term entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	public static CommerceTermEntry fetchByUuid_Last(
		String uuid, OrderByComparator<CommerceTermEntry> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the commerce term entries before and after the current commerce term entry in the ordered set where uuid = &#63;.
	 *
	 * @param commerceTermEntryId the primary key of the current commerce term entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce term entry
	 * @throws NoSuchTermEntryException if a commerce term entry with the primary key could not be found
	 */
	public static CommerceTermEntry[] findByUuid_PrevAndNext(
			long commerceTermEntryId, String uuid,
			OrderByComparator<CommerceTermEntry> orderByComparator)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryException {

		return getPersistence().findByUuid_PrevAndNext(
			commerceTermEntryId, uuid, orderByComparator);
	}

	/**
	 * Returns all the commerce term entries that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce term entries that the user has permission to view
	 */
	public static List<CommerceTermEntry> filterFindByUuid(String uuid) {
		return getPersistence().filterFindByUuid(uuid);
	}

	/**
	 * Returns a range of all the commerce term entries that the user has permission to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @return the range of matching commerce term entries that the user has permission to view
	 */
	public static List<CommerceTermEntry> filterFindByUuid(
		String uuid, int start, int end) {

		return getPersistence().filterFindByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce term entries that the user has permissions to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce term entries that the user has permission to view
	 */
	public static List<CommerceTermEntry> filterFindByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return getPersistence().filterFindByUuid(
			uuid, start, end, orderByComparator);
	}

	/**
	 * Returns the commerce term entries before and after the current commerce term entry in the ordered set of commerce term entries that the user has permission to view where uuid = &#63;.
	 *
	 * @param commerceTermEntryId the primary key of the current commerce term entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce term entry
	 * @throws NoSuchTermEntryException if a commerce term entry with the primary key could not be found
	 */
	public static CommerceTermEntry[] filterFindByUuid_PrevAndNext(
			long commerceTermEntryId, String uuid,
			OrderByComparator<CommerceTermEntry> orderByComparator)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryException {

		return getPersistence().filterFindByUuid_PrevAndNext(
			commerceTermEntryId, uuid, orderByComparator);
	}

	/**
	 * Removes all the commerce term entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of commerce term entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce term entries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the number of commerce term entries that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce term entries that the user has permission to view
	 */
	public static int filterCountByUuid(String uuid) {
		return getPersistence().filterCountByUuid(uuid);
	}

	/**
	 * Returns all the commerce term entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce term entries
	 */
	public static List<CommerceTermEntry> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the commerce term entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @return the range of matching commerce term entries
	 */
	public static List<CommerceTermEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce term entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce term entries
	 */
	public static List<CommerceTermEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce term entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce term entries
	 */
	public static List<CommerceTermEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce term entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	public static CommerceTermEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommerceTermEntry> orderByComparator)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first commerce term entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	public static CommerceTermEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce term entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	public static CommerceTermEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CommerceTermEntry> orderByComparator)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce term entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	public static CommerceTermEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the commerce term entries before and after the current commerce term entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceTermEntryId the primary key of the current commerce term entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce term entry
	 * @throws NoSuchTermEntryException if a commerce term entry with the primary key could not be found
	 */
	public static CommerceTermEntry[] findByUuid_C_PrevAndNext(
			long commerceTermEntryId, String uuid, long companyId,
			OrderByComparator<CommerceTermEntry> orderByComparator)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryException {

		return getPersistence().findByUuid_C_PrevAndNext(
			commerceTermEntryId, uuid, companyId, orderByComparator);
	}

	/**
	 * Returns all the commerce term entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce term entries that the user has permission to view
	 */
	public static List<CommerceTermEntry> filterFindByUuid_C(
		String uuid, long companyId) {

		return getPersistence().filterFindByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the commerce term entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @return the range of matching commerce term entries that the user has permission to view
	 */
	public static List<CommerceTermEntry> filterFindByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().filterFindByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce term entries that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce term entries that the user has permission to view
	 */
	public static List<CommerceTermEntry> filterFindByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return getPersistence().filterFindByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the commerce term entries before and after the current commerce term entry in the ordered set of commerce term entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceTermEntryId the primary key of the current commerce term entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce term entry
	 * @throws NoSuchTermEntryException if a commerce term entry with the primary key could not be found
	 */
	public static CommerceTermEntry[] filterFindByUuid_C_PrevAndNext(
			long commerceTermEntryId, String uuid, long companyId,
			OrderByComparator<CommerceTermEntry> orderByComparator)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryException {

		return getPersistence().filterFindByUuid_C_PrevAndNext(
			commerceTermEntryId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the commerce term entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of commerce term entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce term entries
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of commerce term entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce term entries that the user has permission to view
	 */
	public static int filterCountByUuid_C(String uuid, long companyId) {
		return getPersistence().filterCountByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the commerce term entries where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching commerce term entries
	 */
	public static List<CommerceTermEntry> findByC_A(
		long companyId, boolean active) {

		return getPersistence().findByC_A(companyId, active);
	}

	/**
	 * Returns a range of all the commerce term entries where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @return the range of matching commerce term entries
	 */
	public static List<CommerceTermEntry> findByC_A(
		long companyId, boolean active, int start, int end) {

		return getPersistence().findByC_A(companyId, active, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce term entries where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce term entries
	 */
	public static List<CommerceTermEntry> findByC_A(
		long companyId, boolean active, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return getPersistence().findByC_A(
			companyId, active, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce term entries where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce term entries
	 */
	public static List<CommerceTermEntry> findByC_A(
		long companyId, boolean active, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_A(
			companyId, active, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce term entry in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	public static CommerceTermEntry findByC_A_First(
			long companyId, boolean active,
			OrderByComparator<CommerceTermEntry> orderByComparator)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryException {

		return getPersistence().findByC_A_First(
			companyId, active, orderByComparator);
	}

	/**
	 * Returns the first commerce term entry in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	public static CommerceTermEntry fetchByC_A_First(
		long companyId, boolean active,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return getPersistence().fetchByC_A_First(
			companyId, active, orderByComparator);
	}

	/**
	 * Returns the last commerce term entry in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	public static CommerceTermEntry findByC_A_Last(
			long companyId, boolean active,
			OrderByComparator<CommerceTermEntry> orderByComparator)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryException {

		return getPersistence().findByC_A_Last(
			companyId, active, orderByComparator);
	}

	/**
	 * Returns the last commerce term entry in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	public static CommerceTermEntry fetchByC_A_Last(
		long companyId, boolean active,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return getPersistence().fetchByC_A_Last(
			companyId, active, orderByComparator);
	}

	/**
	 * Returns the commerce term entries before and after the current commerce term entry in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param commerceTermEntryId the primary key of the current commerce term entry
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce term entry
	 * @throws NoSuchTermEntryException if a commerce term entry with the primary key could not be found
	 */
	public static CommerceTermEntry[] findByC_A_PrevAndNext(
			long commerceTermEntryId, long companyId, boolean active,
			OrderByComparator<CommerceTermEntry> orderByComparator)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryException {

		return getPersistence().findByC_A_PrevAndNext(
			commerceTermEntryId, companyId, active, orderByComparator);
	}

	/**
	 * Returns all the commerce term entries that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching commerce term entries that the user has permission to view
	 */
	public static List<CommerceTermEntry> filterFindByC_A(
		long companyId, boolean active) {

		return getPersistence().filterFindByC_A(companyId, active);
	}

	/**
	 * Returns a range of all the commerce term entries that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @return the range of matching commerce term entries that the user has permission to view
	 */
	public static List<CommerceTermEntry> filterFindByC_A(
		long companyId, boolean active, int start, int end) {

		return getPersistence().filterFindByC_A(companyId, active, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce term entries that the user has permissions to view where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce term entries that the user has permission to view
	 */
	public static List<CommerceTermEntry> filterFindByC_A(
		long companyId, boolean active, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return getPersistence().filterFindByC_A(
			companyId, active, start, end, orderByComparator);
	}

	/**
	 * Returns the commerce term entries before and after the current commerce term entry in the ordered set of commerce term entries that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * @param commerceTermEntryId the primary key of the current commerce term entry
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce term entry
	 * @throws NoSuchTermEntryException if a commerce term entry with the primary key could not be found
	 */
	public static CommerceTermEntry[] filterFindByC_A_PrevAndNext(
			long commerceTermEntryId, long companyId, boolean active,
			OrderByComparator<CommerceTermEntry> orderByComparator)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryException {

		return getPersistence().filterFindByC_A_PrevAndNext(
			commerceTermEntryId, companyId, active, orderByComparator);
	}

	/**
	 * Removes all the commerce term entries where companyId = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 */
	public static void removeByC_A(long companyId, boolean active) {
		getPersistence().removeByC_A(companyId, active);
	}

	/**
	 * Returns the number of commerce term entries where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching commerce term entries
	 */
	public static int countByC_A(long companyId, boolean active) {
		return getPersistence().countByC_A(companyId, active);
	}

	/**
	 * Returns the number of commerce term entries that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching commerce term entries that the user has permission to view
	 */
	public static int filterCountByC_A(long companyId, boolean active) {
		return getPersistence().filterCountByC_A(companyId, active);
	}

	/**
	 * Returns the commerce term entry where companyId = &#63; and name = &#63; or throws a <code>NoSuchTermEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	public static CommerceTermEntry findByC_N(long companyId, String name)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryException {

		return getPersistence().findByC_N(companyId, name);
	}

	/**
	 * Returns the commerce term entry where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	public static CommerceTermEntry fetchByC_N(long companyId, String name) {
		return getPersistence().fetchByC_N(companyId, name);
	}

	/**
	 * Returns the commerce term entry where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	public static CommerceTermEntry fetchByC_N(
		long companyId, String name, boolean useFinderCache) {

		return getPersistence().fetchByC_N(companyId, name, useFinderCache);
	}

	/**
	 * Removes the commerce term entry where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the commerce term entry that was removed
	 */
	public static CommerceTermEntry removeByC_N(long companyId, String name)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryException {

		return getPersistence().removeByC_N(companyId, name);
	}

	/**
	 * Returns the number of commerce term entries where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching commerce term entries
	 */
	public static int countByC_N(long companyId, String name) {
		return getPersistence().countByC_N(companyId, name);
	}

	/**
	 * Returns all the commerce term entries where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching commerce term entries
	 */
	public static List<CommerceTermEntry> findByC_LikeType(
		long companyId, String type) {

		return getPersistence().findByC_LikeType(companyId, type);
	}

	/**
	 * Returns a range of all the commerce term entries where companyId = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @return the range of matching commerce term entries
	 */
	public static List<CommerceTermEntry> findByC_LikeType(
		long companyId, String type, int start, int end) {

		return getPersistence().findByC_LikeType(companyId, type, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce term entries where companyId = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce term entries
	 */
	public static List<CommerceTermEntry> findByC_LikeType(
		long companyId, String type, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return getPersistence().findByC_LikeType(
			companyId, type, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce term entries where companyId = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce term entries
	 */
	public static List<CommerceTermEntry> findByC_LikeType(
		long companyId, String type, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_LikeType(
			companyId, type, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce term entry in the ordered set where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	public static CommerceTermEntry findByC_LikeType_First(
			long companyId, String type,
			OrderByComparator<CommerceTermEntry> orderByComparator)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryException {

		return getPersistence().findByC_LikeType_First(
			companyId, type, orderByComparator);
	}

	/**
	 * Returns the first commerce term entry in the ordered set where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	public static CommerceTermEntry fetchByC_LikeType_First(
		long companyId, String type,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return getPersistence().fetchByC_LikeType_First(
			companyId, type, orderByComparator);
	}

	/**
	 * Returns the last commerce term entry in the ordered set where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	public static CommerceTermEntry findByC_LikeType_Last(
			long companyId, String type,
			OrderByComparator<CommerceTermEntry> orderByComparator)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryException {

		return getPersistence().findByC_LikeType_Last(
			companyId, type, orderByComparator);
	}

	/**
	 * Returns the last commerce term entry in the ordered set where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	public static CommerceTermEntry fetchByC_LikeType_Last(
		long companyId, String type,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return getPersistence().fetchByC_LikeType_Last(
			companyId, type, orderByComparator);
	}

	/**
	 * Returns the commerce term entries before and after the current commerce term entry in the ordered set where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param commerceTermEntryId the primary key of the current commerce term entry
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce term entry
	 * @throws NoSuchTermEntryException if a commerce term entry with the primary key could not be found
	 */
	public static CommerceTermEntry[] findByC_LikeType_PrevAndNext(
			long commerceTermEntryId, long companyId, String type,
			OrderByComparator<CommerceTermEntry> orderByComparator)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryException {

		return getPersistence().findByC_LikeType_PrevAndNext(
			commerceTermEntryId, companyId, type, orderByComparator);
	}

	/**
	 * Returns all the commerce term entries that the user has permission to view where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching commerce term entries that the user has permission to view
	 */
	public static List<CommerceTermEntry> filterFindByC_LikeType(
		long companyId, String type) {

		return getPersistence().filterFindByC_LikeType(companyId, type);
	}

	/**
	 * Returns a range of all the commerce term entries that the user has permission to view where companyId = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @return the range of matching commerce term entries that the user has permission to view
	 */
	public static List<CommerceTermEntry> filterFindByC_LikeType(
		long companyId, String type, int start, int end) {

		return getPersistence().filterFindByC_LikeType(
			companyId, type, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce term entries that the user has permissions to view where companyId = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce term entries that the user has permission to view
	 */
	public static List<CommerceTermEntry> filterFindByC_LikeType(
		long companyId, String type, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return getPersistence().filterFindByC_LikeType(
			companyId, type, start, end, orderByComparator);
	}

	/**
	 * Returns the commerce term entries before and after the current commerce term entry in the ordered set of commerce term entries that the user has permission to view where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param commerceTermEntryId the primary key of the current commerce term entry
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce term entry
	 * @throws NoSuchTermEntryException if a commerce term entry with the primary key could not be found
	 */
	public static CommerceTermEntry[] filterFindByC_LikeType_PrevAndNext(
			long commerceTermEntryId, long companyId, String type,
			OrderByComparator<CommerceTermEntry> orderByComparator)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryException {

		return getPersistence().filterFindByC_LikeType_PrevAndNext(
			commerceTermEntryId, companyId, type, orderByComparator);
	}

	/**
	 * Removes all the commerce term entries where companyId = &#63; and type LIKE &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 */
	public static void removeByC_LikeType(long companyId, String type) {
		getPersistence().removeByC_LikeType(companyId, type);
	}

	/**
	 * Returns the number of commerce term entries where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching commerce term entries
	 */
	public static int countByC_LikeType(long companyId, String type) {
		return getPersistence().countByC_LikeType(companyId, type);
	}

	/**
	 * Returns the number of commerce term entries that the user has permission to view where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching commerce term entries that the user has permission to view
	 */
	public static int filterCountByC_LikeType(long companyId, String type) {
		return getPersistence().filterCountByC_LikeType(companyId, type);
	}

	/**
	 * Returns all the commerce term entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching commerce term entries
	 */
	public static List<CommerceTermEntry> findByLtD_S(
		Date displayDate, int status) {

		return getPersistence().findByLtD_S(displayDate, status);
	}

	/**
	 * Returns a range of all the commerce term entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @return the range of matching commerce term entries
	 */
	public static List<CommerceTermEntry> findByLtD_S(
		Date displayDate, int status, int start, int end) {

		return getPersistence().findByLtD_S(displayDate, status, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce term entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce term entries
	 */
	public static List<CommerceTermEntry> findByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return getPersistence().findByLtD_S(
			displayDate, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce term entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce term entries
	 */
	public static List<CommerceTermEntry> findByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByLtD_S(
			displayDate, status, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce term entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	public static CommerceTermEntry findByLtD_S_First(
			Date displayDate, int status,
			OrderByComparator<CommerceTermEntry> orderByComparator)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryException {

		return getPersistence().findByLtD_S_First(
			displayDate, status, orderByComparator);
	}

	/**
	 * Returns the first commerce term entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	public static CommerceTermEntry fetchByLtD_S_First(
		Date displayDate, int status,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return getPersistence().fetchByLtD_S_First(
			displayDate, status, orderByComparator);
	}

	/**
	 * Returns the last commerce term entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	public static CommerceTermEntry findByLtD_S_Last(
			Date displayDate, int status,
			OrderByComparator<CommerceTermEntry> orderByComparator)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryException {

		return getPersistence().findByLtD_S_Last(
			displayDate, status, orderByComparator);
	}

	/**
	 * Returns the last commerce term entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	public static CommerceTermEntry fetchByLtD_S_Last(
		Date displayDate, int status,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return getPersistence().fetchByLtD_S_Last(
			displayDate, status, orderByComparator);
	}

	/**
	 * Returns the commerce term entries before and after the current commerce term entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param commerceTermEntryId the primary key of the current commerce term entry
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce term entry
	 * @throws NoSuchTermEntryException if a commerce term entry with the primary key could not be found
	 */
	public static CommerceTermEntry[] findByLtD_S_PrevAndNext(
			long commerceTermEntryId, Date displayDate, int status,
			OrderByComparator<CommerceTermEntry> orderByComparator)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryException {

		return getPersistence().findByLtD_S_PrevAndNext(
			commerceTermEntryId, displayDate, status, orderByComparator);
	}

	/**
	 * Returns all the commerce term entries that the user has permission to view where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching commerce term entries that the user has permission to view
	 */
	public static List<CommerceTermEntry> filterFindByLtD_S(
		Date displayDate, int status) {

		return getPersistence().filterFindByLtD_S(displayDate, status);
	}

	/**
	 * Returns a range of all the commerce term entries that the user has permission to view where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @return the range of matching commerce term entries that the user has permission to view
	 */
	public static List<CommerceTermEntry> filterFindByLtD_S(
		Date displayDate, int status, int start, int end) {

		return getPersistence().filterFindByLtD_S(
			displayDate, status, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce term entries that the user has permissions to view where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce term entries that the user has permission to view
	 */
	public static List<CommerceTermEntry> filterFindByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return getPersistence().filterFindByLtD_S(
			displayDate, status, start, end, orderByComparator);
	}

	/**
	 * Returns the commerce term entries before and after the current commerce term entry in the ordered set of commerce term entries that the user has permission to view where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param commerceTermEntryId the primary key of the current commerce term entry
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce term entry
	 * @throws NoSuchTermEntryException if a commerce term entry with the primary key could not be found
	 */
	public static CommerceTermEntry[] filterFindByLtD_S_PrevAndNext(
			long commerceTermEntryId, Date displayDate, int status,
			OrderByComparator<CommerceTermEntry> orderByComparator)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryException {

		return getPersistence().filterFindByLtD_S_PrevAndNext(
			commerceTermEntryId, displayDate, status, orderByComparator);
	}

	/**
	 * Removes all the commerce term entries where displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 */
	public static void removeByLtD_S(Date displayDate, int status) {
		getPersistence().removeByLtD_S(displayDate, status);
	}

	/**
	 * Returns the number of commerce term entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching commerce term entries
	 */
	public static int countByLtD_S(Date displayDate, int status) {
		return getPersistence().countByLtD_S(displayDate, status);
	}

	/**
	 * Returns the number of commerce term entries that the user has permission to view where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching commerce term entries that the user has permission to view
	 */
	public static int filterCountByLtD_S(Date displayDate, int status) {
		return getPersistence().filterCountByLtD_S(displayDate, status);
	}

	/**
	 * Returns all the commerce term entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the matching commerce term entries
	 */
	public static List<CommerceTermEntry> findByLtE_S(
		Date expirationDate, int status) {

		return getPersistence().findByLtE_S(expirationDate, status);
	}

	/**
	 * Returns a range of all the commerce term entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @return the range of matching commerce term entries
	 */
	public static List<CommerceTermEntry> findByLtE_S(
		Date expirationDate, int status, int start, int end) {

		return getPersistence().findByLtE_S(expirationDate, status, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce term entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce term entries
	 */
	public static List<CommerceTermEntry> findByLtE_S(
		Date expirationDate, int status, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return getPersistence().findByLtE_S(
			expirationDate, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce term entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce term entries
	 */
	public static List<CommerceTermEntry> findByLtE_S(
		Date expirationDate, int status, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByLtE_S(
			expirationDate, status, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce term entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	public static CommerceTermEntry findByLtE_S_First(
			Date expirationDate, int status,
			OrderByComparator<CommerceTermEntry> orderByComparator)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryException {

		return getPersistence().findByLtE_S_First(
			expirationDate, status, orderByComparator);
	}

	/**
	 * Returns the first commerce term entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	public static CommerceTermEntry fetchByLtE_S_First(
		Date expirationDate, int status,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return getPersistence().fetchByLtE_S_First(
			expirationDate, status, orderByComparator);
	}

	/**
	 * Returns the last commerce term entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	public static CommerceTermEntry findByLtE_S_Last(
			Date expirationDate, int status,
			OrderByComparator<CommerceTermEntry> orderByComparator)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryException {

		return getPersistence().findByLtE_S_Last(
			expirationDate, status, orderByComparator);
	}

	/**
	 * Returns the last commerce term entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	public static CommerceTermEntry fetchByLtE_S_Last(
		Date expirationDate, int status,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return getPersistence().fetchByLtE_S_Last(
			expirationDate, status, orderByComparator);
	}

	/**
	 * Returns the commerce term entries before and after the current commerce term entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param commerceTermEntryId the primary key of the current commerce term entry
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce term entry
	 * @throws NoSuchTermEntryException if a commerce term entry with the primary key could not be found
	 */
	public static CommerceTermEntry[] findByLtE_S_PrevAndNext(
			long commerceTermEntryId, Date expirationDate, int status,
			OrderByComparator<CommerceTermEntry> orderByComparator)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryException {

		return getPersistence().findByLtE_S_PrevAndNext(
			commerceTermEntryId, expirationDate, status, orderByComparator);
	}

	/**
	 * Returns all the commerce term entries that the user has permission to view where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the matching commerce term entries that the user has permission to view
	 */
	public static List<CommerceTermEntry> filterFindByLtE_S(
		Date expirationDate, int status) {

		return getPersistence().filterFindByLtE_S(expirationDate, status);
	}

	/**
	 * Returns a range of all the commerce term entries that the user has permission to view where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @return the range of matching commerce term entries that the user has permission to view
	 */
	public static List<CommerceTermEntry> filterFindByLtE_S(
		Date expirationDate, int status, int start, int end) {

		return getPersistence().filterFindByLtE_S(
			expirationDate, status, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce term entries that the user has permissions to view where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce term entries that the user has permission to view
	 */
	public static List<CommerceTermEntry> filterFindByLtE_S(
		Date expirationDate, int status, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return getPersistence().filterFindByLtE_S(
			expirationDate, status, start, end, orderByComparator);
	}

	/**
	 * Returns the commerce term entries before and after the current commerce term entry in the ordered set of commerce term entries that the user has permission to view where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param commerceTermEntryId the primary key of the current commerce term entry
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce term entry
	 * @throws NoSuchTermEntryException if a commerce term entry with the primary key could not be found
	 */
	public static CommerceTermEntry[] filterFindByLtE_S_PrevAndNext(
			long commerceTermEntryId, Date expirationDate, int status,
			OrderByComparator<CommerceTermEntry> orderByComparator)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryException {

		return getPersistence().filterFindByLtE_S_PrevAndNext(
			commerceTermEntryId, expirationDate, status, orderByComparator);
	}

	/**
	 * Removes all the commerce term entries where expirationDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 */
	public static void removeByLtE_S(Date expirationDate, int status) {
		getPersistence().removeByLtE_S(expirationDate, status);
	}

	/**
	 * Returns the number of commerce term entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the number of matching commerce term entries
	 */
	public static int countByLtE_S(Date expirationDate, int status) {
		return getPersistence().countByLtE_S(expirationDate, status);
	}

	/**
	 * Returns the number of commerce term entries that the user has permission to view where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the number of matching commerce term entries that the user has permission to view
	 */
	public static int filterCountByLtE_S(Date expirationDate, int status) {
		return getPersistence().filterCountByLtE_S(expirationDate, status);
	}

	/**
	 * Returns all the commerce term entries where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @return the matching commerce term entries
	 */
	public static List<CommerceTermEntry> findByC_A_LikeType(
		long companyId, boolean active, String type) {

		return getPersistence().findByC_A_LikeType(companyId, active, type);
	}

	/**
	 * Returns a range of all the commerce term entries where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @return the range of matching commerce term entries
	 */
	public static List<CommerceTermEntry> findByC_A_LikeType(
		long companyId, boolean active, String type, int start, int end) {

		return getPersistence().findByC_A_LikeType(
			companyId, active, type, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce term entries where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce term entries
	 */
	public static List<CommerceTermEntry> findByC_A_LikeType(
		long companyId, boolean active, String type, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return getPersistence().findByC_A_LikeType(
			companyId, active, type, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce term entries where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce term entries
	 */
	public static List<CommerceTermEntry> findByC_A_LikeType(
		long companyId, boolean active, String type, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_A_LikeType(
			companyId, active, type, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce term entry in the ordered set where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	public static CommerceTermEntry findByC_A_LikeType_First(
			long companyId, boolean active, String type,
			OrderByComparator<CommerceTermEntry> orderByComparator)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryException {

		return getPersistence().findByC_A_LikeType_First(
			companyId, active, type, orderByComparator);
	}

	/**
	 * Returns the first commerce term entry in the ordered set where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	public static CommerceTermEntry fetchByC_A_LikeType_First(
		long companyId, boolean active, String type,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return getPersistence().fetchByC_A_LikeType_First(
			companyId, active, type, orderByComparator);
	}

	/**
	 * Returns the last commerce term entry in the ordered set where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	public static CommerceTermEntry findByC_A_LikeType_Last(
			long companyId, boolean active, String type,
			OrderByComparator<CommerceTermEntry> orderByComparator)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryException {

		return getPersistence().findByC_A_LikeType_Last(
			companyId, active, type, orderByComparator);
	}

	/**
	 * Returns the last commerce term entry in the ordered set where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	public static CommerceTermEntry fetchByC_A_LikeType_Last(
		long companyId, boolean active, String type,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return getPersistence().fetchByC_A_LikeType_Last(
			companyId, active, type, orderByComparator);
	}

	/**
	 * Returns the commerce term entries before and after the current commerce term entry in the ordered set where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param commerceTermEntryId the primary key of the current commerce term entry
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce term entry
	 * @throws NoSuchTermEntryException if a commerce term entry with the primary key could not be found
	 */
	public static CommerceTermEntry[] findByC_A_LikeType_PrevAndNext(
			long commerceTermEntryId, long companyId, boolean active,
			String type, OrderByComparator<CommerceTermEntry> orderByComparator)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryException {

		return getPersistence().findByC_A_LikeType_PrevAndNext(
			commerceTermEntryId, companyId, active, type, orderByComparator);
	}

	/**
	 * Returns all the commerce term entries that the user has permission to view where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @return the matching commerce term entries that the user has permission to view
	 */
	public static List<CommerceTermEntry> filterFindByC_A_LikeType(
		long companyId, boolean active, String type) {

		return getPersistence().filterFindByC_A_LikeType(
			companyId, active, type);
	}

	/**
	 * Returns a range of all the commerce term entries that the user has permission to view where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @return the range of matching commerce term entries that the user has permission to view
	 */
	public static List<CommerceTermEntry> filterFindByC_A_LikeType(
		long companyId, boolean active, String type, int start, int end) {

		return getPersistence().filterFindByC_A_LikeType(
			companyId, active, type, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce term entries that the user has permissions to view where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce term entries that the user has permission to view
	 */
	public static List<CommerceTermEntry> filterFindByC_A_LikeType(
		long companyId, boolean active, String type, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return getPersistence().filterFindByC_A_LikeType(
			companyId, active, type, start, end, orderByComparator);
	}

	/**
	 * Returns the commerce term entries before and after the current commerce term entry in the ordered set of commerce term entries that the user has permission to view where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param commerceTermEntryId the primary key of the current commerce term entry
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce term entry
	 * @throws NoSuchTermEntryException if a commerce term entry with the primary key could not be found
	 */
	public static CommerceTermEntry[] filterFindByC_A_LikeType_PrevAndNext(
			long commerceTermEntryId, long companyId, boolean active,
			String type, OrderByComparator<CommerceTermEntry> orderByComparator)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryException {

		return getPersistence().filterFindByC_A_LikeType_PrevAndNext(
			commerceTermEntryId, companyId, active, type, orderByComparator);
	}

	/**
	 * Removes all the commerce term entries where companyId = &#63; and active = &#63; and type LIKE &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 */
	public static void removeByC_A_LikeType(
		long companyId, boolean active, String type) {

		getPersistence().removeByC_A_LikeType(companyId, active, type);
	}

	/**
	 * Returns the number of commerce term entries where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @return the number of matching commerce term entries
	 */
	public static int countByC_A_LikeType(
		long companyId, boolean active, String type) {

		return getPersistence().countByC_A_LikeType(companyId, active, type);
	}

	/**
	 * Returns the number of commerce term entries that the user has permission to view where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @return the number of matching commerce term entries that the user has permission to view
	 */
	public static int filterCountByC_A_LikeType(
		long companyId, boolean active, String type) {

		return getPersistence().filterCountByC_A_LikeType(
			companyId, active, type);
	}

	/**
	 * Returns the commerce term entry where companyId = &#63; and priority = &#63; and type = &#63; or throws a <code>NoSuchTermEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param priority the priority
	 * @param type the type
	 * @return the matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	public static CommerceTermEntry findByC_P_T(
			long companyId, double priority, String type)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryException {

		return getPersistence().findByC_P_T(companyId, priority, type);
	}

	/**
	 * Returns the commerce term entry where companyId = &#63; and priority = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param priority the priority
	 * @param type the type
	 * @return the matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	public static CommerceTermEntry fetchByC_P_T(
		long companyId, double priority, String type) {

		return getPersistence().fetchByC_P_T(companyId, priority, type);
	}

	/**
	 * Returns the commerce term entry where companyId = &#63; and priority = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param priority the priority
	 * @param type the type
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	public static CommerceTermEntry fetchByC_P_T(
		long companyId, double priority, String type, boolean useFinderCache) {

		return getPersistence().fetchByC_P_T(
			companyId, priority, type, useFinderCache);
	}

	/**
	 * Removes the commerce term entry where companyId = &#63; and priority = &#63; and type = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param priority the priority
	 * @param type the type
	 * @return the commerce term entry that was removed
	 */
	public static CommerceTermEntry removeByC_P_T(
			long companyId, double priority, String type)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryException {

		return getPersistence().removeByC_P_T(companyId, priority, type);
	}

	/**
	 * Returns the number of commerce term entries where companyId = &#63; and priority = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param priority the priority
	 * @param type the type
	 * @return the number of matching commerce term entries
	 */
	public static int countByC_P_T(
		long companyId, double priority, String type) {

		return getPersistence().countByC_P_T(companyId, priority, type);
	}

	/**
	 * Returns the commerce term entry where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchTermEntryException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	public static CommerceTermEntry findByERC_C(
			String externalReferenceCode, long companyId)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryException {

		return getPersistence().findByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns the commerce term entry where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	public static CommerceTermEntry fetchByERC_C(
		String externalReferenceCode, long companyId) {

		return getPersistence().fetchByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns the commerce term entry where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	public static CommerceTermEntry fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache) {

		return getPersistence().fetchByERC_C(
			externalReferenceCode, companyId, useFinderCache);
	}

	/**
	 * Removes the commerce term entry where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the commerce term entry that was removed
	 */
	public static CommerceTermEntry removeByERC_C(
			String externalReferenceCode, long companyId)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryException {

		return getPersistence().removeByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns the number of commerce term entries where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching commerce term entries
	 */
	public static int countByERC_C(
		String externalReferenceCode, long companyId) {

		return getPersistence().countByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Caches the commerce term entry in the entity cache if it is enabled.
	 *
	 * @param commerceTermEntry the commerce term entry
	 */
	public static void cacheResult(CommerceTermEntry commerceTermEntry) {
		getPersistence().cacheResult(commerceTermEntry);
	}

	/**
	 * Caches the commerce term entries in the entity cache if it is enabled.
	 *
	 * @param commerceTermEntries the commerce term entries
	 */
	public static void cacheResult(
		List<CommerceTermEntry> commerceTermEntries) {

		getPersistence().cacheResult(commerceTermEntries);
	}

	/**
	 * Creates a new commerce term entry with the primary key. Does not add the commerce term entry to the database.
	 *
	 * @param commerceTermEntryId the primary key for the new commerce term entry
	 * @return the new commerce term entry
	 */
	public static CommerceTermEntry create(long commerceTermEntryId) {
		return getPersistence().create(commerceTermEntryId);
	}

	/**
	 * Removes the commerce term entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceTermEntryId the primary key of the commerce term entry
	 * @return the commerce term entry that was removed
	 * @throws NoSuchTermEntryException if a commerce term entry with the primary key could not be found
	 */
	public static CommerceTermEntry remove(long commerceTermEntryId)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryException {

		return getPersistence().remove(commerceTermEntryId);
	}

	public static CommerceTermEntry updateImpl(
		CommerceTermEntry commerceTermEntry) {

		return getPersistence().updateImpl(commerceTermEntry);
	}

	/**
	 * Returns the commerce term entry with the primary key or throws a <code>NoSuchTermEntryException</code> if it could not be found.
	 *
	 * @param commerceTermEntryId the primary key of the commerce term entry
	 * @return the commerce term entry
	 * @throws NoSuchTermEntryException if a commerce term entry with the primary key could not be found
	 */
	public static CommerceTermEntry findByPrimaryKey(long commerceTermEntryId)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryException {

		return getPersistence().findByPrimaryKey(commerceTermEntryId);
	}

	/**
	 * Returns the commerce term entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceTermEntryId the primary key of the commerce term entry
	 * @return the commerce term entry, or <code>null</code> if a commerce term entry with the primary key could not be found
	 */
	public static CommerceTermEntry fetchByPrimaryKey(
		long commerceTermEntryId) {

		return getPersistence().fetchByPrimaryKey(commerceTermEntryId);
	}

	/**
	 * Returns all the commerce term entries.
	 *
	 * @return the commerce term entries
	 */
	public static List<CommerceTermEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce term entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @return the range of commerce term entries
	 */
	public static List<CommerceTermEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce term entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce term entries
	 */
	public static List<CommerceTermEntry> findAll(
		int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce term entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce term entries
	 */
	public static List<CommerceTermEntry> findAll(
		int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce term entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce term entries.
	 *
	 * @return the number of commerce term entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceTermEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		CommerceTermEntryPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile CommerceTermEntryPersistence _persistence;

}
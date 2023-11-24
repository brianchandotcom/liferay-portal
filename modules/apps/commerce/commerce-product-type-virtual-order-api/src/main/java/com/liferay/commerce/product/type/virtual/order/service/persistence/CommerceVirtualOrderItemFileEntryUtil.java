/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.order.service.persistence;

import com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItemFileEntry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the commerce virtual order item file entry service. This utility wraps <code>com.liferay.commerce.product.type.virtual.order.service.persistence.impl.CommerceVirtualOrderItemFileEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceVirtualOrderItemFileEntryPersistence
 * @generated
 */
public class CommerceVirtualOrderItemFileEntryUtil {

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
		CommerceVirtualOrderItemFileEntry commerceVirtualOrderItemFileEntry) {

		getPersistence().clearCache(commerceVirtualOrderItemFileEntry);
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
	public static Map<Serializable, CommerceVirtualOrderItemFileEntry>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceVirtualOrderItemFileEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceVirtualOrderItemFileEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceVirtualOrderItemFileEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceVirtualOrderItemFileEntry>
			orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceVirtualOrderItemFileEntry update(
		CommerceVirtualOrderItemFileEntry commerceVirtualOrderItemFileEntry) {

		return getPersistence().update(commerceVirtualOrderItemFileEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceVirtualOrderItemFileEntry update(
		CommerceVirtualOrderItemFileEntry commerceVirtualOrderItemFileEntry,
		ServiceContext serviceContext) {

		return getPersistence().update(
			commerceVirtualOrderItemFileEntry, serviceContext);
	}

	/**
	 * Returns all the commerce virtual order item file entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce virtual order item file entries
	 */
	public static List<CommerceVirtualOrderItemFileEntry> findByUuid(
		String uuid) {

		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the commerce virtual order item file entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce virtual order item file entries
	 * @param end the upper bound of the range of commerce virtual order item file entries (not inclusive)
	 * @return the range of matching commerce virtual order item file entries
	 */
	public static List<CommerceVirtualOrderItemFileEntry> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce virtual order item file entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce virtual order item file entries
	 * @param end the upper bound of the range of commerce virtual order item file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce virtual order item file entries
	 */
	public static List<CommerceVirtualOrderItemFileEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceVirtualOrderItemFileEntry>
			orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce virtual order item file entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce virtual order item file entries
	 * @param end the upper bound of the range of commerce virtual order item file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce virtual order item file entries
	 */
	public static List<CommerceVirtualOrderItemFileEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceVirtualOrderItemFileEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce virtual order item file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a matching commerce virtual order item file entry could not be found
	 */
	public static CommerceVirtualOrderItemFileEntry findByUuid_First(
			String uuid,
			OrderByComparator<CommerceVirtualOrderItemFileEntry>
				orderByComparator)
		throws com.liferay.commerce.product.type.virtual.order.exception.
			NoSuchVirtualOrderItemFileEntryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first commerce virtual order item file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce virtual order item file entry, or <code>null</code> if a matching commerce virtual order item file entry could not be found
	 */
	public static CommerceVirtualOrderItemFileEntry fetchByUuid_First(
		String uuid,
		OrderByComparator<CommerceVirtualOrderItemFileEntry>
			orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last commerce virtual order item file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a matching commerce virtual order item file entry could not be found
	 */
	public static CommerceVirtualOrderItemFileEntry findByUuid_Last(
			String uuid,
			OrderByComparator<CommerceVirtualOrderItemFileEntry>
				orderByComparator)
		throws com.liferay.commerce.product.type.virtual.order.exception.
			NoSuchVirtualOrderItemFileEntryException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last commerce virtual order item file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce virtual order item file entry, or <code>null</code> if a matching commerce virtual order item file entry could not be found
	 */
	public static CommerceVirtualOrderItemFileEntry fetchByUuid_Last(
		String uuid,
		OrderByComparator<CommerceVirtualOrderItemFileEntry>
			orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the commerce virtual order item file entries before and after the current commerce virtual order item file entry in the ordered set where uuid = &#63;.
	 *
	 * @param commerceVirtualOrderItemFileEntryId the primary key of the current commerce virtual order item file entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a commerce virtual order item file entry with the primary key could not be found
	 */
	public static CommerceVirtualOrderItemFileEntry[] findByUuid_PrevAndNext(
			long commerceVirtualOrderItemFileEntryId, String uuid,
			OrderByComparator<CommerceVirtualOrderItemFileEntry>
				orderByComparator)
		throws com.liferay.commerce.product.type.virtual.order.exception.
			NoSuchVirtualOrderItemFileEntryException {

		return getPersistence().findByUuid_PrevAndNext(
			commerceVirtualOrderItemFileEntryId, uuid, orderByComparator);
	}

	/**
	 * Removes all the commerce virtual order item file entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of commerce virtual order item file entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce virtual order item file entries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the commerce virtual order item file entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchVirtualOrderItemFileEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a matching commerce virtual order item file entry could not be found
	 */
	public static CommerceVirtualOrderItemFileEntry findByUUID_G(
			String uuid, long groupId)
		throws com.liferay.commerce.product.type.virtual.order.exception.
			NoSuchVirtualOrderItemFileEntryException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the commerce virtual order item file entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce virtual order item file entry, or <code>null</code> if a matching commerce virtual order item file entry could not be found
	 */
	public static CommerceVirtualOrderItemFileEntry fetchByUUID_G(
		String uuid, long groupId) {

		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the commerce virtual order item file entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce virtual order item file entry, or <code>null</code> if a matching commerce virtual order item file entry could not be found
	 */
	public static CommerceVirtualOrderItemFileEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the commerce virtual order item file entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the commerce virtual order item file entry that was removed
	 */
	public static CommerceVirtualOrderItemFileEntry removeByUUID_G(
			String uuid, long groupId)
		throws com.liferay.commerce.product.type.virtual.order.exception.
			NoSuchVirtualOrderItemFileEntryException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of commerce virtual order item file entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching commerce virtual order item file entries
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the commerce virtual order item file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce virtual order item file entries
	 */
	public static List<CommerceVirtualOrderItemFileEntry> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the commerce virtual order item file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce virtual order item file entries
	 * @param end the upper bound of the range of commerce virtual order item file entries (not inclusive)
	 * @return the range of matching commerce virtual order item file entries
	 */
	public static List<CommerceVirtualOrderItemFileEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce virtual order item file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce virtual order item file entries
	 * @param end the upper bound of the range of commerce virtual order item file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce virtual order item file entries
	 */
	public static List<CommerceVirtualOrderItemFileEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceVirtualOrderItemFileEntry>
			orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce virtual order item file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce virtual order item file entries
	 * @param end the upper bound of the range of commerce virtual order item file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce virtual order item file entries
	 */
	public static List<CommerceVirtualOrderItemFileEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceVirtualOrderItemFileEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce virtual order item file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a matching commerce virtual order item file entry could not be found
	 */
	public static CommerceVirtualOrderItemFileEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommerceVirtualOrderItemFileEntry>
				orderByComparator)
		throws com.liferay.commerce.product.type.virtual.order.exception.
			NoSuchVirtualOrderItemFileEntryException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first commerce virtual order item file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce virtual order item file entry, or <code>null</code> if a matching commerce virtual order item file entry could not be found
	 */
	public static CommerceVirtualOrderItemFileEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommerceVirtualOrderItemFileEntry>
			orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce virtual order item file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a matching commerce virtual order item file entry could not be found
	 */
	public static CommerceVirtualOrderItemFileEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CommerceVirtualOrderItemFileEntry>
				orderByComparator)
		throws com.liferay.commerce.product.type.virtual.order.exception.
			NoSuchVirtualOrderItemFileEntryException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce virtual order item file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce virtual order item file entry, or <code>null</code> if a matching commerce virtual order item file entry could not be found
	 */
	public static CommerceVirtualOrderItemFileEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CommerceVirtualOrderItemFileEntry>
			orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the commerce virtual order item file entries before and after the current commerce virtual order item file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceVirtualOrderItemFileEntryId the primary key of the current commerce virtual order item file entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a commerce virtual order item file entry with the primary key could not be found
	 */
	public static CommerceVirtualOrderItemFileEntry[] findByUuid_C_PrevAndNext(
			long commerceVirtualOrderItemFileEntryId, String uuid,
			long companyId,
			OrderByComparator<CommerceVirtualOrderItemFileEntry>
				orderByComparator)
		throws com.liferay.commerce.product.type.virtual.order.exception.
			NoSuchVirtualOrderItemFileEntryException {

		return getPersistence().findByUuid_C_PrevAndNext(
			commerceVirtualOrderItemFileEntryId, uuid, companyId,
			orderByComparator);
	}

	/**
	 * Removes all the commerce virtual order item file entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of commerce virtual order item file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce virtual order item file entries
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the commerce virtual order item file entries where commerceVirtualOrderItemId = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @return the matching commerce virtual order item file entries
	 */
	public static List<CommerceVirtualOrderItemFileEntry>
		findByCommerceVirtualOrderItemId(long commerceVirtualOrderItemId) {

		return getPersistence().findByCommerceVirtualOrderItemId(
			commerceVirtualOrderItemId);
	}

	/**
	 * Returns a range of all the commerce virtual order item file entries where commerceVirtualOrderItemId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param start the lower bound of the range of commerce virtual order item file entries
	 * @param end the upper bound of the range of commerce virtual order item file entries (not inclusive)
	 * @return the range of matching commerce virtual order item file entries
	 */
	public static List<CommerceVirtualOrderItemFileEntry>
		findByCommerceVirtualOrderItemId(
			long commerceVirtualOrderItemId, int start, int end) {

		return getPersistence().findByCommerceVirtualOrderItemId(
			commerceVirtualOrderItemId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce virtual order item file entries where commerceVirtualOrderItemId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param start the lower bound of the range of commerce virtual order item file entries
	 * @param end the upper bound of the range of commerce virtual order item file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce virtual order item file entries
	 */
	public static List<CommerceVirtualOrderItemFileEntry>
		findByCommerceVirtualOrderItemId(
			long commerceVirtualOrderItemId, int start, int end,
			OrderByComparator<CommerceVirtualOrderItemFileEntry>
				orderByComparator) {

		return getPersistence().findByCommerceVirtualOrderItemId(
			commerceVirtualOrderItemId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce virtual order item file entries where commerceVirtualOrderItemId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param start the lower bound of the range of commerce virtual order item file entries
	 * @param end the upper bound of the range of commerce virtual order item file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce virtual order item file entries
	 */
	public static List<CommerceVirtualOrderItemFileEntry>
		findByCommerceVirtualOrderItemId(
			long commerceVirtualOrderItemId, int start, int end,
			OrderByComparator<CommerceVirtualOrderItemFileEntry>
				orderByComparator,
			boolean useFinderCache) {

		return getPersistence().findByCommerceVirtualOrderItemId(
			commerceVirtualOrderItemId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce virtual order item file entry in the ordered set where commerceVirtualOrderItemId = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a matching commerce virtual order item file entry could not be found
	 */
	public static CommerceVirtualOrderItemFileEntry
			findByCommerceVirtualOrderItemId_First(
				long commerceVirtualOrderItemId,
				OrderByComparator<CommerceVirtualOrderItemFileEntry>
					orderByComparator)
		throws com.liferay.commerce.product.type.virtual.order.exception.
			NoSuchVirtualOrderItemFileEntryException {

		return getPersistence().findByCommerceVirtualOrderItemId_First(
			commerceVirtualOrderItemId, orderByComparator);
	}

	/**
	 * Returns the first commerce virtual order item file entry in the ordered set where commerceVirtualOrderItemId = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce virtual order item file entry, or <code>null</code> if a matching commerce virtual order item file entry could not be found
	 */
	public static CommerceVirtualOrderItemFileEntry
		fetchByCommerceVirtualOrderItemId_First(
			long commerceVirtualOrderItemId,
			OrderByComparator<CommerceVirtualOrderItemFileEntry>
				orderByComparator) {

		return getPersistence().fetchByCommerceVirtualOrderItemId_First(
			commerceVirtualOrderItemId, orderByComparator);
	}

	/**
	 * Returns the last commerce virtual order item file entry in the ordered set where commerceVirtualOrderItemId = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a matching commerce virtual order item file entry could not be found
	 */
	public static CommerceVirtualOrderItemFileEntry
			findByCommerceVirtualOrderItemId_Last(
				long commerceVirtualOrderItemId,
				OrderByComparator<CommerceVirtualOrderItemFileEntry>
					orderByComparator)
		throws com.liferay.commerce.product.type.virtual.order.exception.
			NoSuchVirtualOrderItemFileEntryException {

		return getPersistence().findByCommerceVirtualOrderItemId_Last(
			commerceVirtualOrderItemId, orderByComparator);
	}

	/**
	 * Returns the last commerce virtual order item file entry in the ordered set where commerceVirtualOrderItemId = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce virtual order item file entry, or <code>null</code> if a matching commerce virtual order item file entry could not be found
	 */
	public static CommerceVirtualOrderItemFileEntry
		fetchByCommerceVirtualOrderItemId_Last(
			long commerceVirtualOrderItemId,
			OrderByComparator<CommerceVirtualOrderItemFileEntry>
				orderByComparator) {

		return getPersistence().fetchByCommerceVirtualOrderItemId_Last(
			commerceVirtualOrderItemId, orderByComparator);
	}

	/**
	 * Returns the commerce virtual order item file entries before and after the current commerce virtual order item file entry in the ordered set where commerceVirtualOrderItemId = &#63;.
	 *
	 * @param commerceVirtualOrderItemFileEntryId the primary key of the current commerce virtual order item file entry
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a commerce virtual order item file entry with the primary key could not be found
	 */
	public static CommerceVirtualOrderItemFileEntry[]
			findByCommerceVirtualOrderItemId_PrevAndNext(
				long commerceVirtualOrderItemFileEntryId,
				long commerceVirtualOrderItemId,
				OrderByComparator<CommerceVirtualOrderItemFileEntry>
					orderByComparator)
		throws com.liferay.commerce.product.type.virtual.order.exception.
			NoSuchVirtualOrderItemFileEntryException {

		return getPersistence().findByCommerceVirtualOrderItemId_PrevAndNext(
			commerceVirtualOrderItemFileEntryId, commerceVirtualOrderItemId,
			orderByComparator);
	}

	/**
	 * Removes all the commerce virtual order item file entries where commerceVirtualOrderItemId = &#63; from the database.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 */
	public static void removeByCommerceVirtualOrderItemId(
		long commerceVirtualOrderItemId) {

		getPersistence().removeByCommerceVirtualOrderItemId(
			commerceVirtualOrderItemId);
	}

	/**
	 * Returns the number of commerce virtual order item file entries where commerceVirtualOrderItemId = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @return the number of matching commerce virtual order item file entries
	 */
	public static int countByCommerceVirtualOrderItemId(
		long commerceVirtualOrderItemId) {

		return getPersistence().countByCommerceVirtualOrderItemId(
			commerceVirtualOrderItemId);
	}

	/**
	 * Returns all the commerce virtual order item file entries where commerceVirtualOrderItemId = &#63; and fileEntryId = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param fileEntryId the file entry ID
	 * @return the matching commerce virtual order item file entries
	 */
	public static List<CommerceVirtualOrderItemFileEntry> findByC_F(
		long commerceVirtualOrderItemId, long fileEntryId) {

		return getPersistence().findByC_F(
			commerceVirtualOrderItemId, fileEntryId);
	}

	/**
	 * Returns a range of all the commerce virtual order item file entries where commerceVirtualOrderItemId = &#63; and fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of commerce virtual order item file entries
	 * @param end the upper bound of the range of commerce virtual order item file entries (not inclusive)
	 * @return the range of matching commerce virtual order item file entries
	 */
	public static List<CommerceVirtualOrderItemFileEntry> findByC_F(
		long commerceVirtualOrderItemId, long fileEntryId, int start, int end) {

		return getPersistence().findByC_F(
			commerceVirtualOrderItemId, fileEntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce virtual order item file entries where commerceVirtualOrderItemId = &#63; and fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of commerce virtual order item file entries
	 * @param end the upper bound of the range of commerce virtual order item file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce virtual order item file entries
	 */
	public static List<CommerceVirtualOrderItemFileEntry> findByC_F(
		long commerceVirtualOrderItemId, long fileEntryId, int start, int end,
		OrderByComparator<CommerceVirtualOrderItemFileEntry>
			orderByComparator) {

		return getPersistence().findByC_F(
			commerceVirtualOrderItemId, fileEntryId, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce virtual order item file entries where commerceVirtualOrderItemId = &#63; and fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of commerce virtual order item file entries
	 * @param end the upper bound of the range of commerce virtual order item file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce virtual order item file entries
	 */
	public static List<CommerceVirtualOrderItemFileEntry> findByC_F(
		long commerceVirtualOrderItemId, long fileEntryId, int start, int end,
		OrderByComparator<CommerceVirtualOrderItemFileEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_F(
			commerceVirtualOrderItemId, fileEntryId, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce virtual order item file entry in the ordered set where commerceVirtualOrderItemId = &#63; and fileEntryId = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a matching commerce virtual order item file entry could not be found
	 */
	public static CommerceVirtualOrderItemFileEntry findByC_F_First(
			long commerceVirtualOrderItemId, long fileEntryId,
			OrderByComparator<CommerceVirtualOrderItemFileEntry>
				orderByComparator)
		throws com.liferay.commerce.product.type.virtual.order.exception.
			NoSuchVirtualOrderItemFileEntryException {

		return getPersistence().findByC_F_First(
			commerceVirtualOrderItemId, fileEntryId, orderByComparator);
	}

	/**
	 * Returns the first commerce virtual order item file entry in the ordered set where commerceVirtualOrderItemId = &#63; and fileEntryId = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce virtual order item file entry, or <code>null</code> if a matching commerce virtual order item file entry could not be found
	 */
	public static CommerceVirtualOrderItemFileEntry fetchByC_F_First(
		long commerceVirtualOrderItemId, long fileEntryId,
		OrderByComparator<CommerceVirtualOrderItemFileEntry>
			orderByComparator) {

		return getPersistence().fetchByC_F_First(
			commerceVirtualOrderItemId, fileEntryId, orderByComparator);
	}

	/**
	 * Returns the last commerce virtual order item file entry in the ordered set where commerceVirtualOrderItemId = &#63; and fileEntryId = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a matching commerce virtual order item file entry could not be found
	 */
	public static CommerceVirtualOrderItemFileEntry findByC_F_Last(
			long commerceVirtualOrderItemId, long fileEntryId,
			OrderByComparator<CommerceVirtualOrderItemFileEntry>
				orderByComparator)
		throws com.liferay.commerce.product.type.virtual.order.exception.
			NoSuchVirtualOrderItemFileEntryException {

		return getPersistence().findByC_F_Last(
			commerceVirtualOrderItemId, fileEntryId, orderByComparator);
	}

	/**
	 * Returns the last commerce virtual order item file entry in the ordered set where commerceVirtualOrderItemId = &#63; and fileEntryId = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce virtual order item file entry, or <code>null</code> if a matching commerce virtual order item file entry could not be found
	 */
	public static CommerceVirtualOrderItemFileEntry fetchByC_F_Last(
		long commerceVirtualOrderItemId, long fileEntryId,
		OrderByComparator<CommerceVirtualOrderItemFileEntry>
			orderByComparator) {

		return getPersistence().fetchByC_F_Last(
			commerceVirtualOrderItemId, fileEntryId, orderByComparator);
	}

	/**
	 * Returns the commerce virtual order item file entries before and after the current commerce virtual order item file entry in the ordered set where commerceVirtualOrderItemId = &#63; and fileEntryId = &#63;.
	 *
	 * @param commerceVirtualOrderItemFileEntryId the primary key of the current commerce virtual order item file entry
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a commerce virtual order item file entry with the primary key could not be found
	 */
	public static CommerceVirtualOrderItemFileEntry[] findByC_F_PrevAndNext(
			long commerceVirtualOrderItemFileEntryId,
			long commerceVirtualOrderItemId, long fileEntryId,
			OrderByComparator<CommerceVirtualOrderItemFileEntry>
				orderByComparator)
		throws com.liferay.commerce.product.type.virtual.order.exception.
			NoSuchVirtualOrderItemFileEntryException {

		return getPersistence().findByC_F_PrevAndNext(
			commerceVirtualOrderItemFileEntryId, commerceVirtualOrderItemId,
			fileEntryId, orderByComparator);
	}

	/**
	 * Removes all the commerce virtual order item file entries where commerceVirtualOrderItemId = &#63; and fileEntryId = &#63; from the database.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param fileEntryId the file entry ID
	 */
	public static void removeByC_F(
		long commerceVirtualOrderItemId, long fileEntryId) {

		getPersistence().removeByC_F(commerceVirtualOrderItemId, fileEntryId);
	}

	/**
	 * Returns the number of commerce virtual order item file entries where commerceVirtualOrderItemId = &#63; and fileEntryId = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param fileEntryId the file entry ID
	 * @return the number of matching commerce virtual order item file entries
	 */
	public static int countByC_F(
		long commerceVirtualOrderItemId, long fileEntryId) {

		return getPersistence().countByC_F(
			commerceVirtualOrderItemId, fileEntryId);
	}

	/**
	 * Caches the commerce virtual order item file entry in the entity cache if it is enabled.
	 *
	 * @param commerceVirtualOrderItemFileEntry the commerce virtual order item file entry
	 */
	public static void cacheResult(
		CommerceVirtualOrderItemFileEntry commerceVirtualOrderItemFileEntry) {

		getPersistence().cacheResult(commerceVirtualOrderItemFileEntry);
	}

	/**
	 * Caches the commerce virtual order item file entries in the entity cache if it is enabled.
	 *
	 * @param commerceVirtualOrderItemFileEntries the commerce virtual order item file entries
	 */
	public static void cacheResult(
		List<CommerceVirtualOrderItemFileEntry>
			commerceVirtualOrderItemFileEntries) {

		getPersistence().cacheResult(commerceVirtualOrderItemFileEntries);
	}

	/**
	 * Creates a new commerce virtual order item file entry with the primary key. Does not add the commerce virtual order item file entry to the database.
	 *
	 * @param commerceVirtualOrderItemFileEntryId the primary key for the new commerce virtual order item file entry
	 * @return the new commerce virtual order item file entry
	 */
	public static CommerceVirtualOrderItemFileEntry create(
		long commerceVirtualOrderItemFileEntryId) {

		return getPersistence().create(commerceVirtualOrderItemFileEntryId);
	}

	/**
	 * Removes the commerce virtual order item file entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceVirtualOrderItemFileEntryId the primary key of the commerce virtual order item file entry
	 * @return the commerce virtual order item file entry that was removed
	 * @throws NoSuchVirtualOrderItemFileEntryException if a commerce virtual order item file entry with the primary key could not be found
	 */
	public static CommerceVirtualOrderItemFileEntry remove(
			long commerceVirtualOrderItemFileEntryId)
		throws com.liferay.commerce.product.type.virtual.order.exception.
			NoSuchVirtualOrderItemFileEntryException {

		return getPersistence().remove(commerceVirtualOrderItemFileEntryId);
	}

	public static CommerceVirtualOrderItemFileEntry updateImpl(
		CommerceVirtualOrderItemFileEntry commerceVirtualOrderItemFileEntry) {

		return getPersistence().updateImpl(commerceVirtualOrderItemFileEntry);
	}

	/**
	 * Returns the commerce virtual order item file entry with the primary key or throws a <code>NoSuchVirtualOrderItemFileEntryException</code> if it could not be found.
	 *
	 * @param commerceVirtualOrderItemFileEntryId the primary key of the commerce virtual order item file entry
	 * @return the commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a commerce virtual order item file entry with the primary key could not be found
	 */
	public static CommerceVirtualOrderItemFileEntry findByPrimaryKey(
			long commerceVirtualOrderItemFileEntryId)
		throws com.liferay.commerce.product.type.virtual.order.exception.
			NoSuchVirtualOrderItemFileEntryException {

		return getPersistence().findByPrimaryKey(
			commerceVirtualOrderItemFileEntryId);
	}

	/**
	 * Returns the commerce virtual order item file entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceVirtualOrderItemFileEntryId the primary key of the commerce virtual order item file entry
	 * @return the commerce virtual order item file entry, or <code>null</code> if a commerce virtual order item file entry with the primary key could not be found
	 */
	public static CommerceVirtualOrderItemFileEntry fetchByPrimaryKey(
		long commerceVirtualOrderItemFileEntryId) {

		return getPersistence().fetchByPrimaryKey(
			commerceVirtualOrderItemFileEntryId);
	}

	/**
	 * Returns all the commerce virtual order item file entries.
	 *
	 * @return the commerce virtual order item file entries
	 */
	public static List<CommerceVirtualOrderItemFileEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce virtual order item file entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce virtual order item file entries
	 * @param end the upper bound of the range of commerce virtual order item file entries (not inclusive)
	 * @return the range of commerce virtual order item file entries
	 */
	public static List<CommerceVirtualOrderItemFileEntry> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce virtual order item file entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce virtual order item file entries
	 * @param end the upper bound of the range of commerce virtual order item file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce virtual order item file entries
	 */
	public static List<CommerceVirtualOrderItemFileEntry> findAll(
		int start, int end,
		OrderByComparator<CommerceVirtualOrderItemFileEntry>
			orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce virtual order item file entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce virtual order item file entries
	 * @param end the upper bound of the range of commerce virtual order item file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce virtual order item file entries
	 */
	public static List<CommerceVirtualOrderItemFileEntry> findAll(
		int start, int end,
		OrderByComparator<CommerceVirtualOrderItemFileEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce virtual order item file entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce virtual order item file entries.
	 *
	 * @return the number of commerce virtual order item file entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceVirtualOrderItemFileEntryPersistence
		getPersistence() {

		return _persistence;
	}

	public static void setPersistence(
		CommerceVirtualOrderItemFileEntryPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile CommerceVirtualOrderItemFileEntryPersistence
		_persistence;

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.order.service.persistence;

import com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the commerce virtual order item service. This utility wraps <code>com.liferay.commerce.product.type.virtual.order.service.persistence.impl.CommerceVirtualOrderItemPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceVirtualOrderItemPersistence
 * @generated
 */
public class CommerceVirtualOrderItemUtil {

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
		CommerceVirtualOrderItem commerceVirtualOrderItem) {

		getPersistence().clearCache(commerceVirtualOrderItem);
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
	public static Map<Serializable, CommerceVirtualOrderItem>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceVirtualOrderItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceVirtualOrderItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceVirtualOrderItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceVirtualOrderItem> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceVirtualOrderItem update(
		CommerceVirtualOrderItem commerceVirtualOrderItem) {

		return getPersistence().update(commerceVirtualOrderItem);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceVirtualOrderItem update(
		CommerceVirtualOrderItem commerceVirtualOrderItem,
		ServiceContext serviceContext) {

		return getPersistence().update(
			commerceVirtualOrderItem, serviceContext);
	}

	/**
	 * Returns all the commerce virtual order items where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce virtual order items
	 */
	public static List<CommerceVirtualOrderItem> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the commerce virtual order items where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce virtual order items
	 * @param end the upper bound of the range of commerce virtual order items (not inclusive)
	 * @return the range of matching commerce virtual order items
	 */
	public static List<CommerceVirtualOrderItem> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce virtual order items where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce virtual order items
	 * @param end the upper bound of the range of commerce virtual order items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce virtual order items
	 */
	public static List<CommerceVirtualOrderItem> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceVirtualOrderItem> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce virtual order items where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce virtual order items
	 * @param end the upper bound of the range of commerce virtual order items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce virtual order items
	 */
	public static List<CommerceVirtualOrderItem> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceVirtualOrderItem> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce virtual order item in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce virtual order item
	 * @throws NoSuchVirtualOrderItemException if a matching commerce virtual order item could not be found
	 */
	public static CommerceVirtualOrderItem findByUuid_First(
			String uuid,
			OrderByComparator<CommerceVirtualOrderItem> orderByComparator)
		throws com.liferay.commerce.product.type.virtual.order.exception.
			NoSuchVirtualOrderItemException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first commerce virtual order item in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce virtual order item, or <code>null</code> if a matching commerce virtual order item could not be found
	 */
	public static CommerceVirtualOrderItem fetchByUuid_First(
		String uuid,
		OrderByComparator<CommerceVirtualOrderItem> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last commerce virtual order item in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce virtual order item
	 * @throws NoSuchVirtualOrderItemException if a matching commerce virtual order item could not be found
	 */
	public static CommerceVirtualOrderItem findByUuid_Last(
			String uuid,
			OrderByComparator<CommerceVirtualOrderItem> orderByComparator)
		throws com.liferay.commerce.product.type.virtual.order.exception.
			NoSuchVirtualOrderItemException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last commerce virtual order item in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce virtual order item, or <code>null</code> if a matching commerce virtual order item could not be found
	 */
	public static CommerceVirtualOrderItem fetchByUuid_Last(
		String uuid,
		OrderByComparator<CommerceVirtualOrderItem> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the commerce virtual order items before and after the current commerce virtual order item in the ordered set where uuid = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the primary key of the current commerce virtual order item
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce virtual order item
	 * @throws NoSuchVirtualOrderItemException if a commerce virtual order item with the primary key could not be found
	 */
	public static CommerceVirtualOrderItem[] findByUuid_PrevAndNext(
			long commerceVirtualOrderItemId, String uuid,
			OrderByComparator<CommerceVirtualOrderItem> orderByComparator)
		throws com.liferay.commerce.product.type.virtual.order.exception.
			NoSuchVirtualOrderItemException {

		return getPersistence().findByUuid_PrevAndNext(
			commerceVirtualOrderItemId, uuid, orderByComparator);
	}

	/**
	 * Removes all the commerce virtual order items where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of commerce virtual order items where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce virtual order items
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the commerce virtual order item where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchVirtualOrderItemException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce virtual order item
	 * @throws NoSuchVirtualOrderItemException if a matching commerce virtual order item could not be found
	 */
	public static CommerceVirtualOrderItem findByUUID_G(
			String uuid, long groupId)
		throws com.liferay.commerce.product.type.virtual.order.exception.
			NoSuchVirtualOrderItemException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the commerce virtual order item where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce virtual order item, or <code>null</code> if a matching commerce virtual order item could not be found
	 */
	public static CommerceVirtualOrderItem fetchByUUID_G(
		String uuid, long groupId) {

		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the commerce virtual order item where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce virtual order item, or <code>null</code> if a matching commerce virtual order item could not be found
	 */
	public static CommerceVirtualOrderItem fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the commerce virtual order item where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the commerce virtual order item that was removed
	 */
	public static CommerceVirtualOrderItem removeByUUID_G(
			String uuid, long groupId)
		throws com.liferay.commerce.product.type.virtual.order.exception.
			NoSuchVirtualOrderItemException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of commerce virtual order items where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching commerce virtual order items
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the commerce virtual order items where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce virtual order items
	 */
	public static List<CommerceVirtualOrderItem> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the commerce virtual order items where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce virtual order items
	 * @param end the upper bound of the range of commerce virtual order items (not inclusive)
	 * @return the range of matching commerce virtual order items
	 */
	public static List<CommerceVirtualOrderItem> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce virtual order items where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce virtual order items
	 * @param end the upper bound of the range of commerce virtual order items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce virtual order items
	 */
	public static List<CommerceVirtualOrderItem> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceVirtualOrderItem> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce virtual order items where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce virtual order items
	 * @param end the upper bound of the range of commerce virtual order items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce virtual order items
	 */
	public static List<CommerceVirtualOrderItem> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceVirtualOrderItem> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce virtual order item in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce virtual order item
	 * @throws NoSuchVirtualOrderItemException if a matching commerce virtual order item could not be found
	 */
	public static CommerceVirtualOrderItem findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommerceVirtualOrderItem> orderByComparator)
		throws com.liferay.commerce.product.type.virtual.order.exception.
			NoSuchVirtualOrderItemException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first commerce virtual order item in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce virtual order item, or <code>null</code> if a matching commerce virtual order item could not be found
	 */
	public static CommerceVirtualOrderItem fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommerceVirtualOrderItem> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce virtual order item in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce virtual order item
	 * @throws NoSuchVirtualOrderItemException if a matching commerce virtual order item could not be found
	 */
	public static CommerceVirtualOrderItem findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CommerceVirtualOrderItem> orderByComparator)
		throws com.liferay.commerce.product.type.virtual.order.exception.
			NoSuchVirtualOrderItemException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce virtual order item in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce virtual order item, or <code>null</code> if a matching commerce virtual order item could not be found
	 */
	public static CommerceVirtualOrderItem fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CommerceVirtualOrderItem> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the commerce virtual order items before and after the current commerce virtual order item in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the primary key of the current commerce virtual order item
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce virtual order item
	 * @throws NoSuchVirtualOrderItemException if a commerce virtual order item with the primary key could not be found
	 */
	public static CommerceVirtualOrderItem[] findByUuid_C_PrevAndNext(
			long commerceVirtualOrderItemId, String uuid, long companyId,
			OrderByComparator<CommerceVirtualOrderItem> orderByComparator)
		throws com.liferay.commerce.product.type.virtual.order.exception.
			NoSuchVirtualOrderItemException {

		return getPersistence().findByUuid_C_PrevAndNext(
			commerceVirtualOrderItemId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the commerce virtual order items where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of commerce virtual order items where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce virtual order items
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the commerce virtual order item where commerceOrderItemId = &#63; or throws a <code>NoSuchVirtualOrderItemException</code> if it could not be found.
	 *
	 * @param commerceOrderItemId the commerce order item ID
	 * @return the matching commerce virtual order item
	 * @throws NoSuchVirtualOrderItemException if a matching commerce virtual order item could not be found
	 */
	public static CommerceVirtualOrderItem findByCommerceOrderItemId(
			long commerceOrderItemId)
		throws com.liferay.commerce.product.type.virtual.order.exception.
			NoSuchVirtualOrderItemException {

		return getPersistence().findByCommerceOrderItemId(commerceOrderItemId);
	}

	/**
	 * Returns the commerce virtual order item where commerceOrderItemId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceOrderItemId the commerce order item ID
	 * @return the matching commerce virtual order item, or <code>null</code> if a matching commerce virtual order item could not be found
	 */
	public static CommerceVirtualOrderItem fetchByCommerceOrderItemId(
		long commerceOrderItemId) {

		return getPersistence().fetchByCommerceOrderItemId(commerceOrderItemId);
	}

	/**
	 * Returns the commerce virtual order item where commerceOrderItemId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceOrderItemId the commerce order item ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce virtual order item, or <code>null</code> if a matching commerce virtual order item could not be found
	 */
	public static CommerceVirtualOrderItem fetchByCommerceOrderItemId(
		long commerceOrderItemId, boolean useFinderCache) {

		return getPersistence().fetchByCommerceOrderItemId(
			commerceOrderItemId, useFinderCache);
	}

	/**
	 * Removes the commerce virtual order item where commerceOrderItemId = &#63; from the database.
	 *
	 * @param commerceOrderItemId the commerce order item ID
	 * @return the commerce virtual order item that was removed
	 */
	public static CommerceVirtualOrderItem removeByCommerceOrderItemId(
			long commerceOrderItemId)
		throws com.liferay.commerce.product.type.virtual.order.exception.
			NoSuchVirtualOrderItemException {

		return getPersistence().removeByCommerceOrderItemId(
			commerceOrderItemId);
	}

	/**
	 * Returns the number of commerce virtual order items where commerceOrderItemId = &#63;.
	 *
	 * @param commerceOrderItemId the commerce order item ID
	 * @return the number of matching commerce virtual order items
	 */
	public static int countByCommerceOrderItemId(long commerceOrderItemId) {
		return getPersistence().countByCommerceOrderItemId(commerceOrderItemId);
	}

	/**
	 * Caches the commerce virtual order item in the entity cache if it is enabled.
	 *
	 * @param commerceVirtualOrderItem the commerce virtual order item
	 */
	public static void cacheResult(
		CommerceVirtualOrderItem commerceVirtualOrderItem) {

		getPersistence().cacheResult(commerceVirtualOrderItem);
	}

	/**
	 * Caches the commerce virtual order items in the entity cache if it is enabled.
	 *
	 * @param commerceVirtualOrderItems the commerce virtual order items
	 */
	public static void cacheResult(
		List<CommerceVirtualOrderItem> commerceVirtualOrderItems) {

		getPersistence().cacheResult(commerceVirtualOrderItems);
	}

	/**
	 * Creates a new commerce virtual order item with the primary key. Does not add the commerce virtual order item to the database.
	 *
	 * @param commerceVirtualOrderItemId the primary key for the new commerce virtual order item
	 * @return the new commerce virtual order item
	 */
	public static CommerceVirtualOrderItem create(
		long commerceVirtualOrderItemId) {

		return getPersistence().create(commerceVirtualOrderItemId);
	}

	/**
	 * Removes the commerce virtual order item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceVirtualOrderItemId the primary key of the commerce virtual order item
	 * @return the commerce virtual order item that was removed
	 * @throws NoSuchVirtualOrderItemException if a commerce virtual order item with the primary key could not be found
	 */
	public static CommerceVirtualOrderItem remove(
			long commerceVirtualOrderItemId)
		throws com.liferay.commerce.product.type.virtual.order.exception.
			NoSuchVirtualOrderItemException {

		return getPersistence().remove(commerceVirtualOrderItemId);
	}

	public static CommerceVirtualOrderItem updateImpl(
		CommerceVirtualOrderItem commerceVirtualOrderItem) {

		return getPersistence().updateImpl(commerceVirtualOrderItem);
	}

	/**
	 * Returns the commerce virtual order item with the primary key or throws a <code>NoSuchVirtualOrderItemException</code> if it could not be found.
	 *
	 * @param commerceVirtualOrderItemId the primary key of the commerce virtual order item
	 * @return the commerce virtual order item
	 * @throws NoSuchVirtualOrderItemException if a commerce virtual order item with the primary key could not be found
	 */
	public static CommerceVirtualOrderItem findByPrimaryKey(
			long commerceVirtualOrderItemId)
		throws com.liferay.commerce.product.type.virtual.order.exception.
			NoSuchVirtualOrderItemException {

		return getPersistence().findByPrimaryKey(commerceVirtualOrderItemId);
	}

	/**
	 * Returns the commerce virtual order item with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceVirtualOrderItemId the primary key of the commerce virtual order item
	 * @return the commerce virtual order item, or <code>null</code> if a commerce virtual order item with the primary key could not be found
	 */
	public static CommerceVirtualOrderItem fetchByPrimaryKey(
		long commerceVirtualOrderItemId) {

		return getPersistence().fetchByPrimaryKey(commerceVirtualOrderItemId);
	}

	/**
	 * Returns all the commerce virtual order items.
	 *
	 * @return the commerce virtual order items
	 */
	public static List<CommerceVirtualOrderItem> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce virtual order items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce virtual order items
	 * @param end the upper bound of the range of commerce virtual order items (not inclusive)
	 * @return the range of commerce virtual order items
	 */
	public static List<CommerceVirtualOrderItem> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce virtual order items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce virtual order items
	 * @param end the upper bound of the range of commerce virtual order items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce virtual order items
	 */
	public static List<CommerceVirtualOrderItem> findAll(
		int start, int end,
		OrderByComparator<CommerceVirtualOrderItem> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce virtual order items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce virtual order items
	 * @param end the upper bound of the range of commerce virtual order items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce virtual order items
	 */
	public static List<CommerceVirtualOrderItem> findAll(
		int start, int end,
		OrderByComparator<CommerceVirtualOrderItem> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce virtual order items from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce virtual order items.
	 *
	 * @return the number of commerce virtual order items
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceVirtualOrderItemPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		CommerceVirtualOrderItemPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile CommerceVirtualOrderItemPersistence _persistence;

}
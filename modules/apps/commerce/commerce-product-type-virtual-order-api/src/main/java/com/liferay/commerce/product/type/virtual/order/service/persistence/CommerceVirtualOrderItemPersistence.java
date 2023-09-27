/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.order.service.persistence;

import com.liferay.commerce.product.type.virtual.order.exception.NoSuchVirtualOrderItemException;
import com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce virtual order item service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceVirtualOrderItemUtil
 * @generated
 */
@ProviderType
public interface CommerceVirtualOrderItemPersistence
	extends BasePersistence<CommerceVirtualOrderItem> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceVirtualOrderItemUtil} to access the commerce virtual order item persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce virtual order items where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce virtual order items
	 */
	public java.util.List<CommerceVirtualOrderItem> findByUuid(String uuid);

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
	public java.util.List<CommerceVirtualOrderItem> findByUuid(
		String uuid, int start, int end);

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
	public java.util.List<CommerceVirtualOrderItem> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceVirtualOrderItem> orderByComparator);

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
	public java.util.List<CommerceVirtualOrderItem> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceVirtualOrderItem> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce virtual order item in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce virtual order item
	 * @throws NoSuchVirtualOrderItemException if a matching commerce virtual order item could not be found
	 */
	public CommerceVirtualOrderItem findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceVirtualOrderItem> orderByComparator)
		throws NoSuchVirtualOrderItemException;

	/**
	 * Returns the first commerce virtual order item in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce virtual order item, or <code>null</code> if a matching commerce virtual order item could not be found
	 */
	public CommerceVirtualOrderItem fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceVirtualOrderItem> orderByComparator);

	/**
	 * Returns the last commerce virtual order item in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce virtual order item
	 * @throws NoSuchVirtualOrderItemException if a matching commerce virtual order item could not be found
	 */
	public CommerceVirtualOrderItem findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceVirtualOrderItem> orderByComparator)
		throws NoSuchVirtualOrderItemException;

	/**
	 * Returns the last commerce virtual order item in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce virtual order item, or <code>null</code> if a matching commerce virtual order item could not be found
	 */
	public CommerceVirtualOrderItem fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceVirtualOrderItem> orderByComparator);

	/**
	 * Returns the commerce virtual order items before and after the current commerce virtual order item in the ordered set where uuid = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the primary key of the current commerce virtual order item
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce virtual order item
	 * @throws NoSuchVirtualOrderItemException if a commerce virtual order item with the primary key could not be found
	 */
	public CommerceVirtualOrderItem[] findByUuid_PrevAndNext(
			long commerceVirtualOrderItemId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceVirtualOrderItem> orderByComparator)
		throws NoSuchVirtualOrderItemException;

	/**
	 * Removes all the commerce virtual order items where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of commerce virtual order items where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce virtual order items
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the commerce virtual order item where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchVirtualOrderItemException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce virtual order item
	 * @throws NoSuchVirtualOrderItemException if a matching commerce virtual order item could not be found
	 */
	public CommerceVirtualOrderItem findByUUID_G(String uuid, long groupId)
		throws NoSuchVirtualOrderItemException;

	/**
	 * Returns the commerce virtual order item where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce virtual order item, or <code>null</code> if a matching commerce virtual order item could not be found
	 */
	public CommerceVirtualOrderItem fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the commerce virtual order item where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce virtual order item, or <code>null</code> if a matching commerce virtual order item could not be found
	 */
	public CommerceVirtualOrderItem fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the commerce virtual order item where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the commerce virtual order item that was removed
	 */
	public CommerceVirtualOrderItem removeByUUID_G(String uuid, long groupId)
		throws NoSuchVirtualOrderItemException;

	/**
	 * Returns the number of commerce virtual order items where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching commerce virtual order items
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the commerce virtual order items where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce virtual order items
	 */
	public java.util.List<CommerceVirtualOrderItem> findByUuid_C(
		String uuid, long companyId);

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
	public java.util.List<CommerceVirtualOrderItem> findByUuid_C(
		String uuid, long companyId, int start, int end);

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
	public java.util.List<CommerceVirtualOrderItem> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceVirtualOrderItem> orderByComparator);

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
	public java.util.List<CommerceVirtualOrderItem> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceVirtualOrderItem> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce virtual order item in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce virtual order item
	 * @throws NoSuchVirtualOrderItemException if a matching commerce virtual order item could not be found
	 */
	public CommerceVirtualOrderItem findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceVirtualOrderItem> orderByComparator)
		throws NoSuchVirtualOrderItemException;

	/**
	 * Returns the first commerce virtual order item in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce virtual order item, or <code>null</code> if a matching commerce virtual order item could not be found
	 */
	public CommerceVirtualOrderItem fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceVirtualOrderItem> orderByComparator);

	/**
	 * Returns the last commerce virtual order item in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce virtual order item
	 * @throws NoSuchVirtualOrderItemException if a matching commerce virtual order item could not be found
	 */
	public CommerceVirtualOrderItem findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceVirtualOrderItem> orderByComparator)
		throws NoSuchVirtualOrderItemException;

	/**
	 * Returns the last commerce virtual order item in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce virtual order item, or <code>null</code> if a matching commerce virtual order item could not be found
	 */
	public CommerceVirtualOrderItem fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceVirtualOrderItem> orderByComparator);

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
	public CommerceVirtualOrderItem[] findByUuid_C_PrevAndNext(
			long commerceVirtualOrderItemId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceVirtualOrderItem> orderByComparator)
		throws NoSuchVirtualOrderItemException;

	/**
	 * Removes all the commerce virtual order items where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of commerce virtual order items where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce virtual order items
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns the commerce virtual order item where commerceOrderItemId = &#63; or throws a <code>NoSuchVirtualOrderItemException</code> if it could not be found.
	 *
	 * @param commerceOrderItemId the commerce order item ID
	 * @return the matching commerce virtual order item
	 * @throws NoSuchVirtualOrderItemException if a matching commerce virtual order item could not be found
	 */
	public CommerceVirtualOrderItem findByCommerceOrderItemId(
			long commerceOrderItemId)
		throws NoSuchVirtualOrderItemException;

	/**
	 * Returns the commerce virtual order item where commerceOrderItemId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceOrderItemId the commerce order item ID
	 * @return the matching commerce virtual order item, or <code>null</code> if a matching commerce virtual order item could not be found
	 */
	public CommerceVirtualOrderItem fetchByCommerceOrderItemId(
		long commerceOrderItemId);

	/**
	 * Returns the commerce virtual order item where commerceOrderItemId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceOrderItemId the commerce order item ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce virtual order item, or <code>null</code> if a matching commerce virtual order item could not be found
	 */
	public CommerceVirtualOrderItem fetchByCommerceOrderItemId(
		long commerceOrderItemId, boolean useFinderCache);

	/**
	 * Removes the commerce virtual order item where commerceOrderItemId = &#63; from the database.
	 *
	 * @param commerceOrderItemId the commerce order item ID
	 * @return the commerce virtual order item that was removed
	 */
	public CommerceVirtualOrderItem removeByCommerceOrderItemId(
			long commerceOrderItemId)
		throws NoSuchVirtualOrderItemException;

	/**
	 * Returns the number of commerce virtual order items where commerceOrderItemId = &#63;.
	 *
	 * @param commerceOrderItemId the commerce order item ID
	 * @return the number of matching commerce virtual order items
	 */
	public int countByCommerceOrderItemId(long commerceOrderItemId);

	/**
	 * Caches the commerce virtual order item in the entity cache if it is enabled.
	 *
	 * @param commerceVirtualOrderItem the commerce virtual order item
	 */
	public void cacheResult(CommerceVirtualOrderItem commerceVirtualOrderItem);

	/**
	 * Caches the commerce virtual order items in the entity cache if it is enabled.
	 *
	 * @param commerceVirtualOrderItems the commerce virtual order items
	 */
	public void cacheResult(
		java.util.List<CommerceVirtualOrderItem> commerceVirtualOrderItems);

	/**
	 * Creates a new commerce virtual order item with the primary key. Does not add the commerce virtual order item to the database.
	 *
	 * @param commerceVirtualOrderItemId the primary key for the new commerce virtual order item
	 * @return the new commerce virtual order item
	 */
	public CommerceVirtualOrderItem create(long commerceVirtualOrderItemId);

	/**
	 * Removes the commerce virtual order item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceVirtualOrderItemId the primary key of the commerce virtual order item
	 * @return the commerce virtual order item that was removed
	 * @throws NoSuchVirtualOrderItemException if a commerce virtual order item with the primary key could not be found
	 */
	public CommerceVirtualOrderItem remove(long commerceVirtualOrderItemId)
		throws NoSuchVirtualOrderItemException;

	public CommerceVirtualOrderItem updateImpl(
		CommerceVirtualOrderItem commerceVirtualOrderItem);

	/**
	 * Returns the commerce virtual order item with the primary key or throws a <code>NoSuchVirtualOrderItemException</code> if it could not be found.
	 *
	 * @param commerceVirtualOrderItemId the primary key of the commerce virtual order item
	 * @return the commerce virtual order item
	 * @throws NoSuchVirtualOrderItemException if a commerce virtual order item with the primary key could not be found
	 */
	public CommerceVirtualOrderItem findByPrimaryKey(
			long commerceVirtualOrderItemId)
		throws NoSuchVirtualOrderItemException;

	/**
	 * Returns the commerce virtual order item with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceVirtualOrderItemId the primary key of the commerce virtual order item
	 * @return the commerce virtual order item, or <code>null</code> if a commerce virtual order item with the primary key could not be found
	 */
	public CommerceVirtualOrderItem fetchByPrimaryKey(
		long commerceVirtualOrderItemId);

	/**
	 * Returns all the commerce virtual order items.
	 *
	 * @return the commerce virtual order items
	 */
	public java.util.List<CommerceVirtualOrderItem> findAll();

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
	public java.util.List<CommerceVirtualOrderItem> findAll(int start, int end);

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
	public java.util.List<CommerceVirtualOrderItem> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceVirtualOrderItem> orderByComparator);

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
	public java.util.List<CommerceVirtualOrderItem> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceVirtualOrderItem> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce virtual order items from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce virtual order items.
	 *
	 * @return the number of commerce virtual order items
	 */
	public int countAll();

}
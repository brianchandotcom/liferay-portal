/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.wish.list.service.persistence;

import com.liferay.commerce.wish.list.model.CommerceWishListItem;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the commerce wish list item service. This utility wraps <code>com.liferay.commerce.wish.list.service.persistence.impl.CommerceWishListItemPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see CommerceWishListItemPersistence
 * @generated
 */
public class CommerceWishListItemUtil {

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
	public static void clearCache(CommerceWishListItem commerceWishListItem) {
		getPersistence().clearCache(commerceWishListItem);
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
	public static Map<Serializable, CommerceWishListItem> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceWishListItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceWishListItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceWishListItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceWishListItem update(
		CommerceWishListItem commerceWishListItem) {

		return getPersistence().update(commerceWishListItem);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceWishListItem update(
		CommerceWishListItem commerceWishListItem,
		ServiceContext serviceContext) {

		return getPersistence().update(commerceWishListItem, serviceContext);
	}

	/**
	 * Returns all the commerce wish list items where commerceWishListId = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @return the matching commerce wish list items
	 */
	public static List<CommerceWishListItem> findByCommerceWishListId(
		long commerceWishListId) {

		return getPersistence().findByCommerceWishListId(commerceWishListId);
	}

	/**
	 * Returns a range of all the commerce wish list items where commerceWishListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @return the range of matching commerce wish list items
	 */
	public static List<CommerceWishListItem> findByCommerceWishListId(
		long commerceWishListId, int start, int end) {

		return getPersistence().findByCommerceWishListId(
			commerceWishListId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce wish list items where commerceWishListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce wish list items
	 */
	public static List<CommerceWishListItem> findByCommerceWishListId(
		long commerceWishListId, int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator) {

		return getPersistence().findByCommerceWishListId(
			commerceWishListId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce wish list items where commerceWishListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce wish list items
	 */
	public static List<CommerceWishListItem> findByCommerceWishListId(
		long commerceWishListId, int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCommerceWishListId(
			commerceWishListId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce wish list item in the ordered set where commerceWishListId = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list item
	 * @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	 */
	public static CommerceWishListItem findByCommerceWishListId_First(
			long commerceWishListId,
			OrderByComparator<CommerceWishListItem> orderByComparator)
		throws com.liferay.commerce.wish.list.exception.
			NoSuchWishListItemException {

		return getPersistence().findByCommerceWishListId_First(
			commerceWishListId, orderByComparator);
	}

	/**
	 * Returns the first commerce wish list item in the ordered set where commerceWishListId = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	 */
	public static CommerceWishListItem fetchByCommerceWishListId_First(
		long commerceWishListId,
		OrderByComparator<CommerceWishListItem> orderByComparator) {

		return getPersistence().fetchByCommerceWishListId_First(
			commerceWishListId, orderByComparator);
	}

	/**
	 * Returns the last commerce wish list item in the ordered set where commerceWishListId = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list item
	 * @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	 */
	public static CommerceWishListItem findByCommerceWishListId_Last(
			long commerceWishListId,
			OrderByComparator<CommerceWishListItem> orderByComparator)
		throws com.liferay.commerce.wish.list.exception.
			NoSuchWishListItemException {

		return getPersistence().findByCommerceWishListId_Last(
			commerceWishListId, orderByComparator);
	}

	/**
	 * Returns the last commerce wish list item in the ordered set where commerceWishListId = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	 */
	public static CommerceWishListItem fetchByCommerceWishListId_Last(
		long commerceWishListId,
		OrderByComparator<CommerceWishListItem> orderByComparator) {

		return getPersistence().fetchByCommerceWishListId_Last(
			commerceWishListId, orderByComparator);
	}

	/**
	 * Returns the commerce wish list items before and after the current commerce wish list item in the ordered set where commerceWishListId = &#63;.
	 *
	 * @param commerceWishListItemId the primary key of the current commerce wish list item
	 * @param commerceWishListId the commerce wish list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce wish list item
	 * @throws NoSuchWishListItemException if a commerce wish list item with the primary key could not be found
	 */
	public static CommerceWishListItem[] findByCommerceWishListId_PrevAndNext(
			long commerceWishListItemId, long commerceWishListId,
			OrderByComparator<CommerceWishListItem> orderByComparator)
		throws com.liferay.commerce.wish.list.exception.
			NoSuchWishListItemException {

		return getPersistence().findByCommerceWishListId_PrevAndNext(
			commerceWishListItemId, commerceWishListId, orderByComparator);
	}

	/**
	 * Removes all the commerce wish list items where commerceWishListId = &#63; from the database.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 */
	public static void removeByCommerceWishListId(long commerceWishListId) {
		getPersistence().removeByCommerceWishListId(commerceWishListId);
	}

	/**
	 * Returns the number of commerce wish list items where commerceWishListId = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @return the number of matching commerce wish list items
	 */
	public static int countByCommerceWishListId(long commerceWishListId) {
		return getPersistence().countByCommerceWishListId(commerceWishListId);
	}

	/**
	 * Returns all the commerce wish list items where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @return the matching commerce wish list items
	 */
	public static List<CommerceWishListItem> findByCPInstanceUuid(
		String CPInstanceUuid) {

		return getPersistence().findByCPInstanceUuid(CPInstanceUuid);
	}

	/**
	 * Returns a range of all the commerce wish list items where CPInstanceUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListItemModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @return the range of matching commerce wish list items
	 */
	public static List<CommerceWishListItem> findByCPInstanceUuid(
		String CPInstanceUuid, int start, int end) {

		return getPersistence().findByCPInstanceUuid(
			CPInstanceUuid, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce wish list items where CPInstanceUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListItemModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce wish list items
	 */
	public static List<CommerceWishListItem> findByCPInstanceUuid(
		String CPInstanceUuid, int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator) {

		return getPersistence().findByCPInstanceUuid(
			CPInstanceUuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce wish list items where CPInstanceUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListItemModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce wish list items
	 */
	public static List<CommerceWishListItem> findByCPInstanceUuid(
		String CPInstanceUuid, int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCPInstanceUuid(
			CPInstanceUuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce wish list item in the ordered set where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list item
	 * @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	 */
	public static CommerceWishListItem findByCPInstanceUuid_First(
			String CPInstanceUuid,
			OrderByComparator<CommerceWishListItem> orderByComparator)
		throws com.liferay.commerce.wish.list.exception.
			NoSuchWishListItemException {

		return getPersistence().findByCPInstanceUuid_First(
			CPInstanceUuid, orderByComparator);
	}

	/**
	 * Returns the first commerce wish list item in the ordered set where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	 */
	public static CommerceWishListItem fetchByCPInstanceUuid_First(
		String CPInstanceUuid,
		OrderByComparator<CommerceWishListItem> orderByComparator) {

		return getPersistence().fetchByCPInstanceUuid_First(
			CPInstanceUuid, orderByComparator);
	}

	/**
	 * Returns the last commerce wish list item in the ordered set where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list item
	 * @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	 */
	public static CommerceWishListItem findByCPInstanceUuid_Last(
			String CPInstanceUuid,
			OrderByComparator<CommerceWishListItem> orderByComparator)
		throws com.liferay.commerce.wish.list.exception.
			NoSuchWishListItemException {

		return getPersistence().findByCPInstanceUuid_Last(
			CPInstanceUuid, orderByComparator);
	}

	/**
	 * Returns the last commerce wish list item in the ordered set where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	 */
	public static CommerceWishListItem fetchByCPInstanceUuid_Last(
		String CPInstanceUuid,
		OrderByComparator<CommerceWishListItem> orderByComparator) {

		return getPersistence().fetchByCPInstanceUuid_Last(
			CPInstanceUuid, orderByComparator);
	}

	/**
	 * Returns the commerce wish list items before and after the current commerce wish list item in the ordered set where CPInstanceUuid = &#63;.
	 *
	 * @param commerceWishListItemId the primary key of the current commerce wish list item
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce wish list item
	 * @throws NoSuchWishListItemException if a commerce wish list item with the primary key could not be found
	 */
	public static CommerceWishListItem[] findByCPInstanceUuid_PrevAndNext(
			long commerceWishListItemId, String CPInstanceUuid,
			OrderByComparator<CommerceWishListItem> orderByComparator)
		throws com.liferay.commerce.wish.list.exception.
			NoSuchWishListItemException {

		return getPersistence().findByCPInstanceUuid_PrevAndNext(
			commerceWishListItemId, CPInstanceUuid, orderByComparator);
	}

	/**
	 * Removes all the commerce wish list items where CPInstanceUuid = &#63; from the database.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 */
	public static void removeByCPInstanceUuid(String CPInstanceUuid) {
		getPersistence().removeByCPInstanceUuid(CPInstanceUuid);
	}

	/**
	 * Returns the number of commerce wish list items where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @return the number of matching commerce wish list items
	 */
	public static int countByCPInstanceUuid(String CPInstanceUuid) {
		return getPersistence().countByCPInstanceUuid(CPInstanceUuid);
	}

	/**
	 * Returns all the commerce wish list items where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @return the matching commerce wish list items
	 */
	public static List<CommerceWishListItem> findByCProductId(long CProductId) {
		return getPersistence().findByCProductId(CProductId);
	}

	/**
	 * Returns a range of all the commerce wish list items where CProductId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListItemModelImpl</code>.
	 * </p>
	 *
	 * @param CProductId the c product ID
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @return the range of matching commerce wish list items
	 */
	public static List<CommerceWishListItem> findByCProductId(
		long CProductId, int start, int end) {

		return getPersistence().findByCProductId(CProductId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce wish list items where CProductId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListItemModelImpl</code>.
	 * </p>
	 *
	 * @param CProductId the c product ID
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce wish list items
	 */
	public static List<CommerceWishListItem> findByCProductId(
		long CProductId, int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator) {

		return getPersistence().findByCProductId(
			CProductId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce wish list items where CProductId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListItemModelImpl</code>.
	 * </p>
	 *
	 * @param CProductId the c product ID
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce wish list items
	 */
	public static List<CommerceWishListItem> findByCProductId(
		long CProductId, int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCProductId(
			CProductId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce wish list item in the ordered set where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list item
	 * @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	 */
	public static CommerceWishListItem findByCProductId_First(
			long CProductId,
			OrderByComparator<CommerceWishListItem> orderByComparator)
		throws com.liferay.commerce.wish.list.exception.
			NoSuchWishListItemException {

		return getPersistence().findByCProductId_First(
			CProductId, orderByComparator);
	}

	/**
	 * Returns the first commerce wish list item in the ordered set where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	 */
	public static CommerceWishListItem fetchByCProductId_First(
		long CProductId,
		OrderByComparator<CommerceWishListItem> orderByComparator) {

		return getPersistence().fetchByCProductId_First(
			CProductId, orderByComparator);
	}

	/**
	 * Returns the last commerce wish list item in the ordered set where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list item
	 * @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	 */
	public static CommerceWishListItem findByCProductId_Last(
			long CProductId,
			OrderByComparator<CommerceWishListItem> orderByComparator)
		throws com.liferay.commerce.wish.list.exception.
			NoSuchWishListItemException {

		return getPersistence().findByCProductId_Last(
			CProductId, orderByComparator);
	}

	/**
	 * Returns the last commerce wish list item in the ordered set where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	 */
	public static CommerceWishListItem fetchByCProductId_Last(
		long CProductId,
		OrderByComparator<CommerceWishListItem> orderByComparator) {

		return getPersistence().fetchByCProductId_Last(
			CProductId, orderByComparator);
	}

	/**
	 * Returns the commerce wish list items before and after the current commerce wish list item in the ordered set where CProductId = &#63;.
	 *
	 * @param commerceWishListItemId the primary key of the current commerce wish list item
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce wish list item
	 * @throws NoSuchWishListItemException if a commerce wish list item with the primary key could not be found
	 */
	public static CommerceWishListItem[] findByCProductId_PrevAndNext(
			long commerceWishListItemId, long CProductId,
			OrderByComparator<CommerceWishListItem> orderByComparator)
		throws com.liferay.commerce.wish.list.exception.
			NoSuchWishListItemException {

		return getPersistence().findByCProductId_PrevAndNext(
			commerceWishListItemId, CProductId, orderByComparator);
	}

	/**
	 * Removes all the commerce wish list items where CProductId = &#63; from the database.
	 *
	 * @param CProductId the c product ID
	 */
	public static void removeByCProductId(long CProductId) {
		getPersistence().removeByCProductId(CProductId);
	}

	/**
	 * Returns the number of commerce wish list items where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @return the number of matching commerce wish list items
	 */
	public static int countByCProductId(long CProductId) {
		return getPersistence().countByCProductId(CProductId);
	}

	/**
	 * Returns all the commerce wish list items where commerceWishListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @return the matching commerce wish list items
	 */
	public static List<CommerceWishListItem> findByCW_CPI(
		long commerceWishListId, String CPInstanceUuid) {

		return getPersistence().findByCW_CPI(
			commerceWishListId, CPInstanceUuid);
	}

	/**
	 * Returns a range of all the commerce wish list items where commerceWishListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @return the range of matching commerce wish list items
	 */
	public static List<CommerceWishListItem> findByCW_CPI(
		long commerceWishListId, String CPInstanceUuid, int start, int end) {

		return getPersistence().findByCW_CPI(
			commerceWishListId, CPInstanceUuid, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce wish list items where commerceWishListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce wish list items
	 */
	public static List<CommerceWishListItem> findByCW_CPI(
		long commerceWishListId, String CPInstanceUuid, int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator) {

		return getPersistence().findByCW_CPI(
			commerceWishListId, CPInstanceUuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce wish list items where commerceWishListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce wish list items
	 */
	public static List<CommerceWishListItem> findByCW_CPI(
		long commerceWishListId, String CPInstanceUuid, int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCW_CPI(
			commerceWishListId, CPInstanceUuid, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce wish list item in the ordered set where commerceWishListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list item
	 * @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	 */
	public static CommerceWishListItem findByCW_CPI_First(
			long commerceWishListId, String CPInstanceUuid,
			OrderByComparator<CommerceWishListItem> orderByComparator)
		throws com.liferay.commerce.wish.list.exception.
			NoSuchWishListItemException {

		return getPersistence().findByCW_CPI_First(
			commerceWishListId, CPInstanceUuid, orderByComparator);
	}

	/**
	 * Returns the first commerce wish list item in the ordered set where commerceWishListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	 */
	public static CommerceWishListItem fetchByCW_CPI_First(
		long commerceWishListId, String CPInstanceUuid,
		OrderByComparator<CommerceWishListItem> orderByComparator) {

		return getPersistence().fetchByCW_CPI_First(
			commerceWishListId, CPInstanceUuid, orderByComparator);
	}

	/**
	 * Returns the last commerce wish list item in the ordered set where commerceWishListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list item
	 * @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	 */
	public static CommerceWishListItem findByCW_CPI_Last(
			long commerceWishListId, String CPInstanceUuid,
			OrderByComparator<CommerceWishListItem> orderByComparator)
		throws com.liferay.commerce.wish.list.exception.
			NoSuchWishListItemException {

		return getPersistence().findByCW_CPI_Last(
			commerceWishListId, CPInstanceUuid, orderByComparator);
	}

	/**
	 * Returns the last commerce wish list item in the ordered set where commerceWishListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	 */
	public static CommerceWishListItem fetchByCW_CPI_Last(
		long commerceWishListId, String CPInstanceUuid,
		OrderByComparator<CommerceWishListItem> orderByComparator) {

		return getPersistence().fetchByCW_CPI_Last(
			commerceWishListId, CPInstanceUuid, orderByComparator);
	}

	/**
	 * Returns the commerce wish list items before and after the current commerce wish list item in the ordered set where commerceWishListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commerceWishListItemId the primary key of the current commerce wish list item
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce wish list item
	 * @throws NoSuchWishListItemException if a commerce wish list item with the primary key could not be found
	 */
	public static CommerceWishListItem[] findByCW_CPI_PrevAndNext(
			long commerceWishListItemId, long commerceWishListId,
			String CPInstanceUuid,
			OrderByComparator<CommerceWishListItem> orderByComparator)
		throws com.liferay.commerce.wish.list.exception.
			NoSuchWishListItemException {

		return getPersistence().findByCW_CPI_PrevAndNext(
			commerceWishListItemId, commerceWishListId, CPInstanceUuid,
			orderByComparator);
	}

	/**
	 * Removes all the commerce wish list items where commerceWishListId = &#63; and CPInstanceUuid = &#63; from the database.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 */
	public static void removeByCW_CPI(
		long commerceWishListId, String CPInstanceUuid) {

		getPersistence().removeByCW_CPI(commerceWishListId, CPInstanceUuid);
	}

	/**
	 * Returns the number of commerce wish list items where commerceWishListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @return the number of matching commerce wish list items
	 */
	public static int countByCW_CPI(
		long commerceWishListId, String CPInstanceUuid) {

		return getPersistence().countByCW_CPI(
			commerceWishListId, CPInstanceUuid);
	}

	/**
	 * Returns all the commerce wish list items where commerceWishListId = &#63; and CProductId = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CProductId the c product ID
	 * @return the matching commerce wish list items
	 */
	public static List<CommerceWishListItem> findByCW_CP(
		long commerceWishListId, long CProductId) {

		return getPersistence().findByCW_CP(commerceWishListId, CProductId);
	}

	/**
	 * Returns a range of all the commerce wish list items where commerceWishListId = &#63; and CProductId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CProductId the c product ID
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @return the range of matching commerce wish list items
	 */
	public static List<CommerceWishListItem> findByCW_CP(
		long commerceWishListId, long CProductId, int start, int end) {

		return getPersistence().findByCW_CP(
			commerceWishListId, CProductId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce wish list items where commerceWishListId = &#63; and CProductId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CProductId the c product ID
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce wish list items
	 */
	public static List<CommerceWishListItem> findByCW_CP(
		long commerceWishListId, long CProductId, int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator) {

		return getPersistence().findByCW_CP(
			commerceWishListId, CProductId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce wish list items where commerceWishListId = &#63; and CProductId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CProductId the c product ID
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce wish list items
	 */
	public static List<CommerceWishListItem> findByCW_CP(
		long commerceWishListId, long CProductId, int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCW_CP(
			commerceWishListId, CProductId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce wish list item in the ordered set where commerceWishListId = &#63; and CProductId = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list item
	 * @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	 */
	public static CommerceWishListItem findByCW_CP_First(
			long commerceWishListId, long CProductId,
			OrderByComparator<CommerceWishListItem> orderByComparator)
		throws com.liferay.commerce.wish.list.exception.
			NoSuchWishListItemException {

		return getPersistence().findByCW_CP_First(
			commerceWishListId, CProductId, orderByComparator);
	}

	/**
	 * Returns the first commerce wish list item in the ordered set where commerceWishListId = &#63; and CProductId = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	 */
	public static CommerceWishListItem fetchByCW_CP_First(
		long commerceWishListId, long CProductId,
		OrderByComparator<CommerceWishListItem> orderByComparator) {

		return getPersistence().fetchByCW_CP_First(
			commerceWishListId, CProductId, orderByComparator);
	}

	/**
	 * Returns the last commerce wish list item in the ordered set where commerceWishListId = &#63; and CProductId = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list item
	 * @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	 */
	public static CommerceWishListItem findByCW_CP_Last(
			long commerceWishListId, long CProductId,
			OrderByComparator<CommerceWishListItem> orderByComparator)
		throws com.liferay.commerce.wish.list.exception.
			NoSuchWishListItemException {

		return getPersistence().findByCW_CP_Last(
			commerceWishListId, CProductId, orderByComparator);
	}

	/**
	 * Returns the last commerce wish list item in the ordered set where commerceWishListId = &#63; and CProductId = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	 */
	public static CommerceWishListItem fetchByCW_CP_Last(
		long commerceWishListId, long CProductId,
		OrderByComparator<CommerceWishListItem> orderByComparator) {

		return getPersistence().fetchByCW_CP_Last(
			commerceWishListId, CProductId, orderByComparator);
	}

	/**
	 * Returns the commerce wish list items before and after the current commerce wish list item in the ordered set where commerceWishListId = &#63; and CProductId = &#63;.
	 *
	 * @param commerceWishListItemId the primary key of the current commerce wish list item
	 * @param commerceWishListId the commerce wish list ID
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce wish list item
	 * @throws NoSuchWishListItemException if a commerce wish list item with the primary key could not be found
	 */
	public static CommerceWishListItem[] findByCW_CP_PrevAndNext(
			long commerceWishListItemId, long commerceWishListId,
			long CProductId,
			OrderByComparator<CommerceWishListItem> orderByComparator)
		throws com.liferay.commerce.wish.list.exception.
			NoSuchWishListItemException {

		return getPersistence().findByCW_CP_PrevAndNext(
			commerceWishListItemId, commerceWishListId, CProductId,
			orderByComparator);
	}

	/**
	 * Removes all the commerce wish list items where commerceWishListId = &#63; and CProductId = &#63; from the database.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CProductId the c product ID
	 */
	public static void removeByCW_CP(long commerceWishListId, long CProductId) {
		getPersistence().removeByCW_CP(commerceWishListId, CProductId);
	}

	/**
	 * Returns the number of commerce wish list items where commerceWishListId = &#63; and CProductId = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CProductId the c product ID
	 * @return the number of matching commerce wish list items
	 */
	public static int countByCW_CP(long commerceWishListId, long CProductId) {
		return getPersistence().countByCW_CP(commerceWishListId, CProductId);
	}

	/**
	 * Returns the commerce wish list item where commerceWishListId = &#63; and CPInstanceUuid = &#63; and CProductId = &#63; or throws a <code>NoSuchWishListItemException</code> if it could not be found.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param CProductId the c product ID
	 * @return the matching commerce wish list item
	 * @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	 */
	public static CommerceWishListItem findByCW_CPI_CP(
			long commerceWishListId, String CPInstanceUuid, long CProductId)
		throws com.liferay.commerce.wish.list.exception.
			NoSuchWishListItemException {

		return getPersistence().findByCW_CPI_CP(
			commerceWishListId, CPInstanceUuid, CProductId);
	}

	/**
	 * Returns the commerce wish list item where commerceWishListId = &#63; and CPInstanceUuid = &#63; and CProductId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param CProductId the c product ID
	 * @return the matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	 */
	public static CommerceWishListItem fetchByCW_CPI_CP(
		long commerceWishListId, String CPInstanceUuid, long CProductId) {

		return getPersistence().fetchByCW_CPI_CP(
			commerceWishListId, CPInstanceUuid, CProductId);
	}

	/**
	 * Returns the commerce wish list item where commerceWishListId = &#63; and CPInstanceUuid = &#63; and CProductId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param CProductId the c product ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	 */
	public static CommerceWishListItem fetchByCW_CPI_CP(
		long commerceWishListId, String CPInstanceUuid, long CProductId,
		boolean useFinderCache) {

		return getPersistence().fetchByCW_CPI_CP(
			commerceWishListId, CPInstanceUuid, CProductId, useFinderCache);
	}

	/**
	 * Removes the commerce wish list item where commerceWishListId = &#63; and CPInstanceUuid = &#63; and CProductId = &#63; from the database.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param CProductId the c product ID
	 * @return the commerce wish list item that was removed
	 */
	public static CommerceWishListItem removeByCW_CPI_CP(
			long commerceWishListId, String CPInstanceUuid, long CProductId)
		throws com.liferay.commerce.wish.list.exception.
			NoSuchWishListItemException {

		return getPersistence().removeByCW_CPI_CP(
			commerceWishListId, CPInstanceUuid, CProductId);
	}

	/**
	 * Returns the number of commerce wish list items where commerceWishListId = &#63; and CPInstanceUuid = &#63; and CProductId = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param CProductId the c product ID
	 * @return the number of matching commerce wish list items
	 */
	public static int countByCW_CPI_CP(
		long commerceWishListId, String CPInstanceUuid, long CProductId) {

		return getPersistence().countByCW_CPI_CP(
			commerceWishListId, CPInstanceUuid, CProductId);
	}

	/**
	 * Caches the commerce wish list item in the entity cache if it is enabled.
	 *
	 * @param commerceWishListItem the commerce wish list item
	 */
	public static void cacheResult(CommerceWishListItem commerceWishListItem) {
		getPersistence().cacheResult(commerceWishListItem);
	}

	/**
	 * Caches the commerce wish list items in the entity cache if it is enabled.
	 *
	 * @param commerceWishListItems the commerce wish list items
	 */
	public static void cacheResult(
		List<CommerceWishListItem> commerceWishListItems) {

		getPersistence().cacheResult(commerceWishListItems);
	}

	/**
	 * Creates a new commerce wish list item with the primary key. Does not add the commerce wish list item to the database.
	 *
	 * @param commerceWishListItemId the primary key for the new commerce wish list item
	 * @return the new commerce wish list item
	 */
	public static CommerceWishListItem create(long commerceWishListItemId) {
		return getPersistence().create(commerceWishListItemId);
	}

	/**
	 * Removes the commerce wish list item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceWishListItemId the primary key of the commerce wish list item
	 * @return the commerce wish list item that was removed
	 * @throws NoSuchWishListItemException if a commerce wish list item with the primary key could not be found
	 */
	public static CommerceWishListItem remove(long commerceWishListItemId)
		throws com.liferay.commerce.wish.list.exception.
			NoSuchWishListItemException {

		return getPersistence().remove(commerceWishListItemId);
	}

	public static CommerceWishListItem updateImpl(
		CommerceWishListItem commerceWishListItem) {

		return getPersistence().updateImpl(commerceWishListItem);
	}

	/**
	 * Returns the commerce wish list item with the primary key or throws a <code>NoSuchWishListItemException</code> if it could not be found.
	 *
	 * @param commerceWishListItemId the primary key of the commerce wish list item
	 * @return the commerce wish list item
	 * @throws NoSuchWishListItemException if a commerce wish list item with the primary key could not be found
	 */
	public static CommerceWishListItem findByPrimaryKey(
			long commerceWishListItemId)
		throws com.liferay.commerce.wish.list.exception.
			NoSuchWishListItemException {

		return getPersistence().findByPrimaryKey(commerceWishListItemId);
	}

	/**
	 * Returns the commerce wish list item with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceWishListItemId the primary key of the commerce wish list item
	 * @return the commerce wish list item, or <code>null</code> if a commerce wish list item with the primary key could not be found
	 */
	public static CommerceWishListItem fetchByPrimaryKey(
		long commerceWishListItemId) {

		return getPersistence().fetchByPrimaryKey(commerceWishListItemId);
	}

	/**
	 * Returns all the commerce wish list items.
	 *
	 * @return the commerce wish list items
	 */
	public static List<CommerceWishListItem> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce wish list items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @return the range of commerce wish list items
	 */
	public static List<CommerceWishListItem> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce wish list items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce wish list items
	 */
	public static List<CommerceWishListItem> findAll(
		int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce wish list items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce wish list items
	 */
	public static List<CommerceWishListItem> findAll(
		int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce wish list items from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce wish list items.
	 *
	 * @return the number of commerce wish list items
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceWishListItemPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		CommerceWishListItemPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile CommerceWishListItemPersistence _persistence;

}
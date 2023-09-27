/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.wish.list.service.persistence;

import com.liferay.commerce.wish.list.exception.NoSuchWishListItemException;
import com.liferay.commerce.wish.list.model.CommerceWishListItem;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce wish list item service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see CommerceWishListItemUtil
 * @generated
 */
@ProviderType
public interface CommerceWishListItemPersistence
	extends BasePersistence<CommerceWishListItem> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceWishListItemUtil} to access the commerce wish list item persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce wish list items where commerceWishListId = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @return the matching commerce wish list items
	 */
	public java.util.List<CommerceWishListItem> findByCommerceWishListId(
		long commerceWishListId);

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
	public java.util.List<CommerceWishListItem> findByCommerceWishListId(
		long commerceWishListId, int start, int end);

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
	public java.util.List<CommerceWishListItem> findByCommerceWishListId(
		long commerceWishListId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem>
			orderByComparator);

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
	public java.util.List<CommerceWishListItem> findByCommerceWishListId(
		long commerceWishListId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce wish list item in the ordered set where commerceWishListId = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list item
	 * @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	 */
	public CommerceWishListItem findByCommerceWishListId_First(
			long commerceWishListId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException;

	/**
	 * Returns the first commerce wish list item in the ordered set where commerceWishListId = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	 */
	public CommerceWishListItem fetchByCommerceWishListId_First(
		long commerceWishListId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem>
			orderByComparator);

	/**
	 * Returns the last commerce wish list item in the ordered set where commerceWishListId = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list item
	 * @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	 */
	public CommerceWishListItem findByCommerceWishListId_Last(
			long commerceWishListId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException;

	/**
	 * Returns the last commerce wish list item in the ordered set where commerceWishListId = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	 */
	public CommerceWishListItem fetchByCommerceWishListId_Last(
		long commerceWishListId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem>
			orderByComparator);

	/**
	 * Returns the commerce wish list items before and after the current commerce wish list item in the ordered set where commerceWishListId = &#63;.
	 *
	 * @param commerceWishListItemId the primary key of the current commerce wish list item
	 * @param commerceWishListId the commerce wish list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce wish list item
	 * @throws NoSuchWishListItemException if a commerce wish list item with the primary key could not be found
	 */
	public CommerceWishListItem[] findByCommerceWishListId_PrevAndNext(
			long commerceWishListItemId, long commerceWishListId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException;

	/**
	 * Removes all the commerce wish list items where commerceWishListId = &#63; from the database.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 */
	public void removeByCommerceWishListId(long commerceWishListId);

	/**
	 * Returns the number of commerce wish list items where commerceWishListId = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @return the number of matching commerce wish list items
	 */
	public int countByCommerceWishListId(long commerceWishListId);

	/**
	 * Returns all the commerce wish list items where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @return the matching commerce wish list items
	 */
	public java.util.List<CommerceWishListItem> findByCPInstanceUuid(
		String CPInstanceUuid);

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
	public java.util.List<CommerceWishListItem> findByCPInstanceUuid(
		String CPInstanceUuid, int start, int end);

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
	public java.util.List<CommerceWishListItem> findByCPInstanceUuid(
		String CPInstanceUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem>
			orderByComparator);

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
	public java.util.List<CommerceWishListItem> findByCPInstanceUuid(
		String CPInstanceUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce wish list item in the ordered set where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list item
	 * @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	 */
	public CommerceWishListItem findByCPInstanceUuid_First(
			String CPInstanceUuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException;

	/**
	 * Returns the first commerce wish list item in the ordered set where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	 */
	public CommerceWishListItem fetchByCPInstanceUuid_First(
		String CPInstanceUuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem>
			orderByComparator);

	/**
	 * Returns the last commerce wish list item in the ordered set where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list item
	 * @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	 */
	public CommerceWishListItem findByCPInstanceUuid_Last(
			String CPInstanceUuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException;

	/**
	 * Returns the last commerce wish list item in the ordered set where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	 */
	public CommerceWishListItem fetchByCPInstanceUuid_Last(
		String CPInstanceUuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem>
			orderByComparator);

	/**
	 * Returns the commerce wish list items before and after the current commerce wish list item in the ordered set where CPInstanceUuid = &#63;.
	 *
	 * @param commerceWishListItemId the primary key of the current commerce wish list item
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce wish list item
	 * @throws NoSuchWishListItemException if a commerce wish list item with the primary key could not be found
	 */
	public CommerceWishListItem[] findByCPInstanceUuid_PrevAndNext(
			long commerceWishListItemId, String CPInstanceUuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException;

	/**
	 * Removes all the commerce wish list items where CPInstanceUuid = &#63; from the database.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 */
	public void removeByCPInstanceUuid(String CPInstanceUuid);

	/**
	 * Returns the number of commerce wish list items where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @return the number of matching commerce wish list items
	 */
	public int countByCPInstanceUuid(String CPInstanceUuid);

	/**
	 * Returns all the commerce wish list items where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @return the matching commerce wish list items
	 */
	public java.util.List<CommerceWishListItem> findByCProductId(
		long CProductId);

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
	public java.util.List<CommerceWishListItem> findByCProductId(
		long CProductId, int start, int end);

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
	public java.util.List<CommerceWishListItem> findByCProductId(
		long CProductId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem>
			orderByComparator);

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
	public java.util.List<CommerceWishListItem> findByCProductId(
		long CProductId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce wish list item in the ordered set where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list item
	 * @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	 */
	public CommerceWishListItem findByCProductId_First(
			long CProductId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException;

	/**
	 * Returns the first commerce wish list item in the ordered set where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	 */
	public CommerceWishListItem fetchByCProductId_First(
		long CProductId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem>
			orderByComparator);

	/**
	 * Returns the last commerce wish list item in the ordered set where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list item
	 * @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	 */
	public CommerceWishListItem findByCProductId_Last(
			long CProductId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException;

	/**
	 * Returns the last commerce wish list item in the ordered set where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	 */
	public CommerceWishListItem fetchByCProductId_Last(
		long CProductId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem>
			orderByComparator);

	/**
	 * Returns the commerce wish list items before and after the current commerce wish list item in the ordered set where CProductId = &#63;.
	 *
	 * @param commerceWishListItemId the primary key of the current commerce wish list item
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce wish list item
	 * @throws NoSuchWishListItemException if a commerce wish list item with the primary key could not be found
	 */
	public CommerceWishListItem[] findByCProductId_PrevAndNext(
			long commerceWishListItemId, long CProductId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException;

	/**
	 * Removes all the commerce wish list items where CProductId = &#63; from the database.
	 *
	 * @param CProductId the c product ID
	 */
	public void removeByCProductId(long CProductId);

	/**
	 * Returns the number of commerce wish list items where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @return the number of matching commerce wish list items
	 */
	public int countByCProductId(long CProductId);

	/**
	 * Returns all the commerce wish list items where commerceWishListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @return the matching commerce wish list items
	 */
	public java.util.List<CommerceWishListItem> findByCW_CPI(
		long commerceWishListId, String CPInstanceUuid);

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
	public java.util.List<CommerceWishListItem> findByCW_CPI(
		long commerceWishListId, String CPInstanceUuid, int start, int end);

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
	public java.util.List<CommerceWishListItem> findByCW_CPI(
		long commerceWishListId, String CPInstanceUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem>
			orderByComparator);

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
	public java.util.List<CommerceWishListItem> findByCW_CPI(
		long commerceWishListId, String CPInstanceUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce wish list item in the ordered set where commerceWishListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list item
	 * @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	 */
	public CommerceWishListItem findByCW_CPI_First(
			long commerceWishListId, String CPInstanceUuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException;

	/**
	 * Returns the first commerce wish list item in the ordered set where commerceWishListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	 */
	public CommerceWishListItem fetchByCW_CPI_First(
		long commerceWishListId, String CPInstanceUuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem>
			orderByComparator);

	/**
	 * Returns the last commerce wish list item in the ordered set where commerceWishListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list item
	 * @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	 */
	public CommerceWishListItem findByCW_CPI_Last(
			long commerceWishListId, String CPInstanceUuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException;

	/**
	 * Returns the last commerce wish list item in the ordered set where commerceWishListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	 */
	public CommerceWishListItem fetchByCW_CPI_Last(
		long commerceWishListId, String CPInstanceUuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem>
			orderByComparator);

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
	public CommerceWishListItem[] findByCW_CPI_PrevAndNext(
			long commerceWishListItemId, long commerceWishListId,
			String CPInstanceUuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException;

	/**
	 * Removes all the commerce wish list items where commerceWishListId = &#63; and CPInstanceUuid = &#63; from the database.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 */
	public void removeByCW_CPI(long commerceWishListId, String CPInstanceUuid);

	/**
	 * Returns the number of commerce wish list items where commerceWishListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @return the number of matching commerce wish list items
	 */
	public int countByCW_CPI(long commerceWishListId, String CPInstanceUuid);

	/**
	 * Returns all the commerce wish list items where commerceWishListId = &#63; and CProductId = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CProductId the c product ID
	 * @return the matching commerce wish list items
	 */
	public java.util.List<CommerceWishListItem> findByCW_CP(
		long commerceWishListId, long CProductId);

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
	public java.util.List<CommerceWishListItem> findByCW_CP(
		long commerceWishListId, long CProductId, int start, int end);

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
	public java.util.List<CommerceWishListItem> findByCW_CP(
		long commerceWishListId, long CProductId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem>
			orderByComparator);

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
	public java.util.List<CommerceWishListItem> findByCW_CP(
		long commerceWishListId, long CProductId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce wish list item in the ordered set where commerceWishListId = &#63; and CProductId = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list item
	 * @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	 */
	public CommerceWishListItem findByCW_CP_First(
			long commerceWishListId, long CProductId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException;

	/**
	 * Returns the first commerce wish list item in the ordered set where commerceWishListId = &#63; and CProductId = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	 */
	public CommerceWishListItem fetchByCW_CP_First(
		long commerceWishListId, long CProductId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem>
			orderByComparator);

	/**
	 * Returns the last commerce wish list item in the ordered set where commerceWishListId = &#63; and CProductId = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list item
	 * @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	 */
	public CommerceWishListItem findByCW_CP_Last(
			long commerceWishListId, long CProductId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException;

	/**
	 * Returns the last commerce wish list item in the ordered set where commerceWishListId = &#63; and CProductId = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	 */
	public CommerceWishListItem fetchByCW_CP_Last(
		long commerceWishListId, long CProductId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem>
			orderByComparator);

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
	public CommerceWishListItem[] findByCW_CP_PrevAndNext(
			long commerceWishListItemId, long commerceWishListId,
			long CProductId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException;

	/**
	 * Removes all the commerce wish list items where commerceWishListId = &#63; and CProductId = &#63; from the database.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CProductId the c product ID
	 */
	public void removeByCW_CP(long commerceWishListId, long CProductId);

	/**
	 * Returns the number of commerce wish list items where commerceWishListId = &#63; and CProductId = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CProductId the c product ID
	 * @return the number of matching commerce wish list items
	 */
	public int countByCW_CP(long commerceWishListId, long CProductId);

	/**
	 * Returns the commerce wish list item where commerceWishListId = &#63; and CPInstanceUuid = &#63; and CProductId = &#63; or throws a <code>NoSuchWishListItemException</code> if it could not be found.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param CProductId the c product ID
	 * @return the matching commerce wish list item
	 * @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	 */
	public CommerceWishListItem findByCW_CPI_CP(
			long commerceWishListId, String CPInstanceUuid, long CProductId)
		throws NoSuchWishListItemException;

	/**
	 * Returns the commerce wish list item where commerceWishListId = &#63; and CPInstanceUuid = &#63; and CProductId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param CProductId the c product ID
	 * @return the matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	 */
	public CommerceWishListItem fetchByCW_CPI_CP(
		long commerceWishListId, String CPInstanceUuid, long CProductId);

	/**
	 * Returns the commerce wish list item where commerceWishListId = &#63; and CPInstanceUuid = &#63; and CProductId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param CProductId the c product ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	 */
	public CommerceWishListItem fetchByCW_CPI_CP(
		long commerceWishListId, String CPInstanceUuid, long CProductId,
		boolean useFinderCache);

	/**
	 * Removes the commerce wish list item where commerceWishListId = &#63; and CPInstanceUuid = &#63; and CProductId = &#63; from the database.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param CProductId the c product ID
	 * @return the commerce wish list item that was removed
	 */
	public CommerceWishListItem removeByCW_CPI_CP(
			long commerceWishListId, String CPInstanceUuid, long CProductId)
		throws NoSuchWishListItemException;

	/**
	 * Returns the number of commerce wish list items where commerceWishListId = &#63; and CPInstanceUuid = &#63; and CProductId = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param CProductId the c product ID
	 * @return the number of matching commerce wish list items
	 */
	public int countByCW_CPI_CP(
		long commerceWishListId, String CPInstanceUuid, long CProductId);

	/**
	 * Caches the commerce wish list item in the entity cache if it is enabled.
	 *
	 * @param commerceWishListItem the commerce wish list item
	 */
	public void cacheResult(CommerceWishListItem commerceWishListItem);

	/**
	 * Caches the commerce wish list items in the entity cache if it is enabled.
	 *
	 * @param commerceWishListItems the commerce wish list items
	 */
	public void cacheResult(
		java.util.List<CommerceWishListItem> commerceWishListItems);

	/**
	 * Creates a new commerce wish list item with the primary key. Does not add the commerce wish list item to the database.
	 *
	 * @param commerceWishListItemId the primary key for the new commerce wish list item
	 * @return the new commerce wish list item
	 */
	public CommerceWishListItem create(long commerceWishListItemId);

	/**
	 * Removes the commerce wish list item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceWishListItemId the primary key of the commerce wish list item
	 * @return the commerce wish list item that was removed
	 * @throws NoSuchWishListItemException if a commerce wish list item with the primary key could not be found
	 */
	public CommerceWishListItem remove(long commerceWishListItemId)
		throws NoSuchWishListItemException;

	public CommerceWishListItem updateImpl(
		CommerceWishListItem commerceWishListItem);

	/**
	 * Returns the commerce wish list item with the primary key or throws a <code>NoSuchWishListItemException</code> if it could not be found.
	 *
	 * @param commerceWishListItemId the primary key of the commerce wish list item
	 * @return the commerce wish list item
	 * @throws NoSuchWishListItemException if a commerce wish list item with the primary key could not be found
	 */
	public CommerceWishListItem findByPrimaryKey(long commerceWishListItemId)
		throws NoSuchWishListItemException;

	/**
	 * Returns the commerce wish list item with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceWishListItemId the primary key of the commerce wish list item
	 * @return the commerce wish list item, or <code>null</code> if a commerce wish list item with the primary key could not be found
	 */
	public CommerceWishListItem fetchByPrimaryKey(long commerceWishListItemId);

	/**
	 * Returns all the commerce wish list items.
	 *
	 * @return the commerce wish list items
	 */
	public java.util.List<CommerceWishListItem> findAll();

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
	public java.util.List<CommerceWishListItem> findAll(int start, int end);

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
	public java.util.List<CommerceWishListItem> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem>
			orderByComparator);

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
	public java.util.List<CommerceWishListItem> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce wish list items from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce wish list items.
	 *
	 * @return the number of commerce wish list items
	 */
	public int countAll();

}
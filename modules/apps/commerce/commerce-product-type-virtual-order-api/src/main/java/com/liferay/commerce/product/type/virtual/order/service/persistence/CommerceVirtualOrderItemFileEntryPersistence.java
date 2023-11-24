/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.order.service.persistence;

import com.liferay.commerce.product.type.virtual.order.exception.NoSuchVirtualOrderItemFileEntryException;
import com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItemFileEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce virtual order item file entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceVirtualOrderItemFileEntryUtil
 * @generated
 */
@ProviderType
public interface CommerceVirtualOrderItemFileEntryPersistence
	extends BasePersistence<CommerceVirtualOrderItemFileEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceVirtualOrderItemFileEntryUtil} to access the commerce virtual order item file entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce virtual order item file entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce virtual order item file entries
	 */
	public java.util.List<CommerceVirtualOrderItemFileEntry> findByUuid(
		String uuid);

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
	public java.util.List<CommerceVirtualOrderItemFileEntry> findByUuid(
		String uuid, int start, int end);

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
	public java.util.List<CommerceVirtualOrderItemFileEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceVirtualOrderItemFileEntry> orderByComparator);

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
	public java.util.List<CommerceVirtualOrderItemFileEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceVirtualOrderItemFileEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce virtual order item file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a matching commerce virtual order item file entry could not be found
	 */
	public CommerceVirtualOrderItemFileEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceVirtualOrderItemFileEntry> orderByComparator)
		throws NoSuchVirtualOrderItemFileEntryException;

	/**
	 * Returns the first commerce virtual order item file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce virtual order item file entry, or <code>null</code> if a matching commerce virtual order item file entry could not be found
	 */
	public CommerceVirtualOrderItemFileEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceVirtualOrderItemFileEntry> orderByComparator);

	/**
	 * Returns the last commerce virtual order item file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a matching commerce virtual order item file entry could not be found
	 */
	public CommerceVirtualOrderItemFileEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceVirtualOrderItemFileEntry> orderByComparator)
		throws NoSuchVirtualOrderItemFileEntryException;

	/**
	 * Returns the last commerce virtual order item file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce virtual order item file entry, or <code>null</code> if a matching commerce virtual order item file entry could not be found
	 */
	public CommerceVirtualOrderItemFileEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceVirtualOrderItemFileEntry> orderByComparator);

	/**
	 * Returns the commerce virtual order item file entries before and after the current commerce virtual order item file entry in the ordered set where uuid = &#63;.
	 *
	 * @param commerceVirtualOrderItemFileEntryId the primary key of the current commerce virtual order item file entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a commerce virtual order item file entry with the primary key could not be found
	 */
	public CommerceVirtualOrderItemFileEntry[] findByUuid_PrevAndNext(
			long commerceVirtualOrderItemFileEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceVirtualOrderItemFileEntry> orderByComparator)
		throws NoSuchVirtualOrderItemFileEntryException;

	/**
	 * Removes all the commerce virtual order item file entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of commerce virtual order item file entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce virtual order item file entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the commerce virtual order item file entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchVirtualOrderItemFileEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a matching commerce virtual order item file entry could not be found
	 */
	public CommerceVirtualOrderItemFileEntry findByUUID_G(
			String uuid, long groupId)
		throws NoSuchVirtualOrderItemFileEntryException;

	/**
	 * Returns the commerce virtual order item file entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce virtual order item file entry, or <code>null</code> if a matching commerce virtual order item file entry could not be found
	 */
	public CommerceVirtualOrderItemFileEntry fetchByUUID_G(
		String uuid, long groupId);

	/**
	 * Returns the commerce virtual order item file entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce virtual order item file entry, or <code>null</code> if a matching commerce virtual order item file entry could not be found
	 */
	public CommerceVirtualOrderItemFileEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the commerce virtual order item file entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the commerce virtual order item file entry that was removed
	 */
	public CommerceVirtualOrderItemFileEntry removeByUUID_G(
			String uuid, long groupId)
		throws NoSuchVirtualOrderItemFileEntryException;

	/**
	 * Returns the number of commerce virtual order item file entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching commerce virtual order item file entries
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the commerce virtual order item file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce virtual order item file entries
	 */
	public java.util.List<CommerceVirtualOrderItemFileEntry> findByUuid_C(
		String uuid, long companyId);

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
	public java.util.List<CommerceVirtualOrderItemFileEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

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
	public java.util.List<CommerceVirtualOrderItemFileEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceVirtualOrderItemFileEntry> orderByComparator);

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
	public java.util.List<CommerceVirtualOrderItemFileEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceVirtualOrderItemFileEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce virtual order item file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a matching commerce virtual order item file entry could not be found
	 */
	public CommerceVirtualOrderItemFileEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceVirtualOrderItemFileEntry> orderByComparator)
		throws NoSuchVirtualOrderItemFileEntryException;

	/**
	 * Returns the first commerce virtual order item file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce virtual order item file entry, or <code>null</code> if a matching commerce virtual order item file entry could not be found
	 */
	public CommerceVirtualOrderItemFileEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceVirtualOrderItemFileEntry> orderByComparator);

	/**
	 * Returns the last commerce virtual order item file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a matching commerce virtual order item file entry could not be found
	 */
	public CommerceVirtualOrderItemFileEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceVirtualOrderItemFileEntry> orderByComparator)
		throws NoSuchVirtualOrderItemFileEntryException;

	/**
	 * Returns the last commerce virtual order item file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce virtual order item file entry, or <code>null</code> if a matching commerce virtual order item file entry could not be found
	 */
	public CommerceVirtualOrderItemFileEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceVirtualOrderItemFileEntry> orderByComparator);

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
	public CommerceVirtualOrderItemFileEntry[] findByUuid_C_PrevAndNext(
			long commerceVirtualOrderItemFileEntryId, String uuid,
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceVirtualOrderItemFileEntry> orderByComparator)
		throws NoSuchVirtualOrderItemFileEntryException;

	/**
	 * Removes all the commerce virtual order item file entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of commerce virtual order item file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce virtual order item file entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the commerce virtual order item file entries where commerceVirtualOrderItemId = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @return the matching commerce virtual order item file entries
	 */
	public java.util.List<CommerceVirtualOrderItemFileEntry>
		findByCommerceVirtualOrderItemId(long commerceVirtualOrderItemId);

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
	public java.util.List<CommerceVirtualOrderItemFileEntry>
		findByCommerceVirtualOrderItemId(
			long commerceVirtualOrderItemId, int start, int end);

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
	public java.util.List<CommerceVirtualOrderItemFileEntry>
		findByCommerceVirtualOrderItemId(
			long commerceVirtualOrderItemId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceVirtualOrderItemFileEntry> orderByComparator);

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
	public java.util.List<CommerceVirtualOrderItemFileEntry>
		findByCommerceVirtualOrderItemId(
			long commerceVirtualOrderItemId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceVirtualOrderItemFileEntry> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first commerce virtual order item file entry in the ordered set where commerceVirtualOrderItemId = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a matching commerce virtual order item file entry could not be found
	 */
	public CommerceVirtualOrderItemFileEntry
			findByCommerceVirtualOrderItemId_First(
				long commerceVirtualOrderItemId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceVirtualOrderItemFileEntry> orderByComparator)
		throws NoSuchVirtualOrderItemFileEntryException;

	/**
	 * Returns the first commerce virtual order item file entry in the ordered set where commerceVirtualOrderItemId = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce virtual order item file entry, or <code>null</code> if a matching commerce virtual order item file entry could not be found
	 */
	public CommerceVirtualOrderItemFileEntry
		fetchByCommerceVirtualOrderItemId_First(
			long commerceVirtualOrderItemId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceVirtualOrderItemFileEntry> orderByComparator);

	/**
	 * Returns the last commerce virtual order item file entry in the ordered set where commerceVirtualOrderItemId = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a matching commerce virtual order item file entry could not be found
	 */
	public CommerceVirtualOrderItemFileEntry
			findByCommerceVirtualOrderItemId_Last(
				long commerceVirtualOrderItemId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceVirtualOrderItemFileEntry> orderByComparator)
		throws NoSuchVirtualOrderItemFileEntryException;

	/**
	 * Returns the last commerce virtual order item file entry in the ordered set where commerceVirtualOrderItemId = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce virtual order item file entry, or <code>null</code> if a matching commerce virtual order item file entry could not be found
	 */
	public CommerceVirtualOrderItemFileEntry
		fetchByCommerceVirtualOrderItemId_Last(
			long commerceVirtualOrderItemId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceVirtualOrderItemFileEntry> orderByComparator);

	/**
	 * Returns the commerce virtual order item file entries before and after the current commerce virtual order item file entry in the ordered set where commerceVirtualOrderItemId = &#63;.
	 *
	 * @param commerceVirtualOrderItemFileEntryId the primary key of the current commerce virtual order item file entry
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a commerce virtual order item file entry with the primary key could not be found
	 */
	public CommerceVirtualOrderItemFileEntry[]
			findByCommerceVirtualOrderItemId_PrevAndNext(
				long commerceVirtualOrderItemFileEntryId,
				long commerceVirtualOrderItemId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceVirtualOrderItemFileEntry> orderByComparator)
		throws NoSuchVirtualOrderItemFileEntryException;

	/**
	 * Removes all the commerce virtual order item file entries where commerceVirtualOrderItemId = &#63; from the database.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 */
	public void removeByCommerceVirtualOrderItemId(
		long commerceVirtualOrderItemId);

	/**
	 * Returns the number of commerce virtual order item file entries where commerceVirtualOrderItemId = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @return the number of matching commerce virtual order item file entries
	 */
	public int countByCommerceVirtualOrderItemId(
		long commerceVirtualOrderItemId);

	/**
	 * Returns all the commerce virtual order item file entries where commerceVirtualOrderItemId = &#63; and fileEntryId = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param fileEntryId the file entry ID
	 * @return the matching commerce virtual order item file entries
	 */
	public java.util.List<CommerceVirtualOrderItemFileEntry> findByC_F(
		long commerceVirtualOrderItemId, long fileEntryId);

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
	public java.util.List<CommerceVirtualOrderItemFileEntry> findByC_F(
		long commerceVirtualOrderItemId, long fileEntryId, int start, int end);

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
	public java.util.List<CommerceVirtualOrderItemFileEntry> findByC_F(
		long commerceVirtualOrderItemId, long fileEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceVirtualOrderItemFileEntry> orderByComparator);

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
	public java.util.List<CommerceVirtualOrderItemFileEntry> findByC_F(
		long commerceVirtualOrderItemId, long fileEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceVirtualOrderItemFileEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce virtual order item file entry in the ordered set where commerceVirtualOrderItemId = &#63; and fileEntryId = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a matching commerce virtual order item file entry could not be found
	 */
	public CommerceVirtualOrderItemFileEntry findByC_F_First(
			long commerceVirtualOrderItemId, long fileEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceVirtualOrderItemFileEntry> orderByComparator)
		throws NoSuchVirtualOrderItemFileEntryException;

	/**
	 * Returns the first commerce virtual order item file entry in the ordered set where commerceVirtualOrderItemId = &#63; and fileEntryId = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce virtual order item file entry, or <code>null</code> if a matching commerce virtual order item file entry could not be found
	 */
	public CommerceVirtualOrderItemFileEntry fetchByC_F_First(
		long commerceVirtualOrderItemId, long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceVirtualOrderItemFileEntry> orderByComparator);

	/**
	 * Returns the last commerce virtual order item file entry in the ordered set where commerceVirtualOrderItemId = &#63; and fileEntryId = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a matching commerce virtual order item file entry could not be found
	 */
	public CommerceVirtualOrderItemFileEntry findByC_F_Last(
			long commerceVirtualOrderItemId, long fileEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceVirtualOrderItemFileEntry> orderByComparator)
		throws NoSuchVirtualOrderItemFileEntryException;

	/**
	 * Returns the last commerce virtual order item file entry in the ordered set where commerceVirtualOrderItemId = &#63; and fileEntryId = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce virtual order item file entry, or <code>null</code> if a matching commerce virtual order item file entry could not be found
	 */
	public CommerceVirtualOrderItemFileEntry fetchByC_F_Last(
		long commerceVirtualOrderItemId, long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceVirtualOrderItemFileEntry> orderByComparator);

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
	public CommerceVirtualOrderItemFileEntry[] findByC_F_PrevAndNext(
			long commerceVirtualOrderItemFileEntryId,
			long commerceVirtualOrderItemId, long fileEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceVirtualOrderItemFileEntry> orderByComparator)
		throws NoSuchVirtualOrderItemFileEntryException;

	/**
	 * Removes all the commerce virtual order item file entries where commerceVirtualOrderItemId = &#63; and fileEntryId = &#63; from the database.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param fileEntryId the file entry ID
	 */
	public void removeByC_F(long commerceVirtualOrderItemId, long fileEntryId);

	/**
	 * Returns the number of commerce virtual order item file entries where commerceVirtualOrderItemId = &#63; and fileEntryId = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param fileEntryId the file entry ID
	 * @return the number of matching commerce virtual order item file entries
	 */
	public int countByC_F(long commerceVirtualOrderItemId, long fileEntryId);

	/**
	 * Caches the commerce virtual order item file entry in the entity cache if it is enabled.
	 *
	 * @param commerceVirtualOrderItemFileEntry the commerce virtual order item file entry
	 */
	public void cacheResult(
		CommerceVirtualOrderItemFileEntry commerceVirtualOrderItemFileEntry);

	/**
	 * Caches the commerce virtual order item file entries in the entity cache if it is enabled.
	 *
	 * @param commerceVirtualOrderItemFileEntries the commerce virtual order item file entries
	 */
	public void cacheResult(
		java.util.List<CommerceVirtualOrderItemFileEntry>
			commerceVirtualOrderItemFileEntries);

	/**
	 * Creates a new commerce virtual order item file entry with the primary key. Does not add the commerce virtual order item file entry to the database.
	 *
	 * @param commerceVirtualOrderItemFileEntryId the primary key for the new commerce virtual order item file entry
	 * @return the new commerce virtual order item file entry
	 */
	public CommerceVirtualOrderItemFileEntry create(
		long commerceVirtualOrderItemFileEntryId);

	/**
	 * Removes the commerce virtual order item file entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceVirtualOrderItemFileEntryId the primary key of the commerce virtual order item file entry
	 * @return the commerce virtual order item file entry that was removed
	 * @throws NoSuchVirtualOrderItemFileEntryException if a commerce virtual order item file entry with the primary key could not be found
	 */
	public CommerceVirtualOrderItemFileEntry remove(
			long commerceVirtualOrderItemFileEntryId)
		throws NoSuchVirtualOrderItemFileEntryException;

	public CommerceVirtualOrderItemFileEntry updateImpl(
		CommerceVirtualOrderItemFileEntry commerceVirtualOrderItemFileEntry);

	/**
	 * Returns the commerce virtual order item file entry with the primary key or throws a <code>NoSuchVirtualOrderItemFileEntryException</code> if it could not be found.
	 *
	 * @param commerceVirtualOrderItemFileEntryId the primary key of the commerce virtual order item file entry
	 * @return the commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a commerce virtual order item file entry with the primary key could not be found
	 */
	public CommerceVirtualOrderItemFileEntry findByPrimaryKey(
			long commerceVirtualOrderItemFileEntryId)
		throws NoSuchVirtualOrderItemFileEntryException;

	/**
	 * Returns the commerce virtual order item file entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceVirtualOrderItemFileEntryId the primary key of the commerce virtual order item file entry
	 * @return the commerce virtual order item file entry, or <code>null</code> if a commerce virtual order item file entry with the primary key could not be found
	 */
	public CommerceVirtualOrderItemFileEntry fetchByPrimaryKey(
		long commerceVirtualOrderItemFileEntryId);

	/**
	 * Returns all the commerce virtual order item file entries.
	 *
	 * @return the commerce virtual order item file entries
	 */
	public java.util.List<CommerceVirtualOrderItemFileEntry> findAll();

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
	public java.util.List<CommerceVirtualOrderItemFileEntry> findAll(
		int start, int end);

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
	public java.util.List<CommerceVirtualOrderItemFileEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceVirtualOrderItemFileEntry> orderByComparator);

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
	public java.util.List<CommerceVirtualOrderItemFileEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceVirtualOrderItemFileEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce virtual order item file entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce virtual order item file entries.
	 *
	 * @return the number of commerce virtual order item file entries
	 */
	public int countAll();

}
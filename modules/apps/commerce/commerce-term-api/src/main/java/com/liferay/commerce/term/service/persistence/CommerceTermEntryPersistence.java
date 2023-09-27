/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.term.service.persistence;

import com.liferay.commerce.term.exception.NoSuchTermEntryException;
import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce term entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceTermEntryUtil
 * @generated
 */
@ProviderType
public interface CommerceTermEntryPersistence
	extends BasePersistence<CommerceTermEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceTermEntryUtil} to access the commerce term entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce term entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce term entries
	 */
	public java.util.List<CommerceTermEntry> findByUuid(String uuid);

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
	public java.util.List<CommerceTermEntry> findByUuid(
		String uuid, int start, int end);

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
	public java.util.List<CommerceTermEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
			orderByComparator);

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
	public java.util.List<CommerceTermEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce term entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	public CommerceTermEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
				orderByComparator)
		throws NoSuchTermEntryException;

	/**
	 * Returns the first commerce term entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	public CommerceTermEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
			orderByComparator);

	/**
	 * Returns the last commerce term entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	public CommerceTermEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
				orderByComparator)
		throws NoSuchTermEntryException;

	/**
	 * Returns the last commerce term entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	public CommerceTermEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
			orderByComparator);

	/**
	 * Returns the commerce term entries before and after the current commerce term entry in the ordered set where uuid = &#63;.
	 *
	 * @param commerceTermEntryId the primary key of the current commerce term entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce term entry
	 * @throws NoSuchTermEntryException if a commerce term entry with the primary key could not be found
	 */
	public CommerceTermEntry[] findByUuid_PrevAndNext(
			long commerceTermEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
				orderByComparator)
		throws NoSuchTermEntryException;

	/**
	 * Returns all the commerce term entries that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce term entries that the user has permission to view
	 */
	public java.util.List<CommerceTermEntry> filterFindByUuid(String uuid);

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
	public java.util.List<CommerceTermEntry> filterFindByUuid(
		String uuid, int start, int end);

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
	public java.util.List<CommerceTermEntry> filterFindByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
			orderByComparator);

	/**
	 * Returns the commerce term entries before and after the current commerce term entry in the ordered set of commerce term entries that the user has permission to view where uuid = &#63;.
	 *
	 * @param commerceTermEntryId the primary key of the current commerce term entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce term entry
	 * @throws NoSuchTermEntryException if a commerce term entry with the primary key could not be found
	 */
	public CommerceTermEntry[] filterFindByUuid_PrevAndNext(
			long commerceTermEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
				orderByComparator)
		throws NoSuchTermEntryException;

	/**
	 * Removes all the commerce term entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of commerce term entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce term entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the number of commerce term entries that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce term entries that the user has permission to view
	 */
	public int filterCountByUuid(String uuid);

	/**
	 * Returns all the commerce term entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce term entries
	 */
	public java.util.List<CommerceTermEntry> findByUuid_C(
		String uuid, long companyId);

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
	public java.util.List<CommerceTermEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

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
	public java.util.List<CommerceTermEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
			orderByComparator);

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
	public java.util.List<CommerceTermEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce term entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	public CommerceTermEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
				orderByComparator)
		throws NoSuchTermEntryException;

	/**
	 * Returns the first commerce term entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	public CommerceTermEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
			orderByComparator);

	/**
	 * Returns the last commerce term entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	public CommerceTermEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
				orderByComparator)
		throws NoSuchTermEntryException;

	/**
	 * Returns the last commerce term entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	public CommerceTermEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
			orderByComparator);

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
	public CommerceTermEntry[] findByUuid_C_PrevAndNext(
			long commerceTermEntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
				orderByComparator)
		throws NoSuchTermEntryException;

	/**
	 * Returns all the commerce term entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce term entries that the user has permission to view
	 */
	public java.util.List<CommerceTermEntry> filterFindByUuid_C(
		String uuid, long companyId);

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
	public java.util.List<CommerceTermEntry> filterFindByUuid_C(
		String uuid, long companyId, int start, int end);

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
	public java.util.List<CommerceTermEntry> filterFindByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
			orderByComparator);

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
	public CommerceTermEntry[] filterFindByUuid_C_PrevAndNext(
			long commerceTermEntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
				orderByComparator)
		throws NoSuchTermEntryException;

	/**
	 * Removes all the commerce term entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of commerce term entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce term entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of commerce term entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce term entries that the user has permission to view
	 */
	public int filterCountByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the commerce term entries where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching commerce term entries
	 */
	public java.util.List<CommerceTermEntry> findByC_A(
		long companyId, boolean active);

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
	public java.util.List<CommerceTermEntry> findByC_A(
		long companyId, boolean active, int start, int end);

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
	public java.util.List<CommerceTermEntry> findByC_A(
		long companyId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
			orderByComparator);

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
	public java.util.List<CommerceTermEntry> findByC_A(
		long companyId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce term entry in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	public CommerceTermEntry findByC_A_First(
			long companyId, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
				orderByComparator)
		throws NoSuchTermEntryException;

	/**
	 * Returns the first commerce term entry in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	public CommerceTermEntry fetchByC_A_First(
		long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
			orderByComparator);

	/**
	 * Returns the last commerce term entry in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	public CommerceTermEntry findByC_A_Last(
			long companyId, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
				orderByComparator)
		throws NoSuchTermEntryException;

	/**
	 * Returns the last commerce term entry in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	public CommerceTermEntry fetchByC_A_Last(
		long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
			orderByComparator);

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
	public CommerceTermEntry[] findByC_A_PrevAndNext(
			long commerceTermEntryId, long companyId, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
				orderByComparator)
		throws NoSuchTermEntryException;

	/**
	 * Returns all the commerce term entries that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching commerce term entries that the user has permission to view
	 */
	public java.util.List<CommerceTermEntry> filterFindByC_A(
		long companyId, boolean active);

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
	public java.util.List<CommerceTermEntry> filterFindByC_A(
		long companyId, boolean active, int start, int end);

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
	public java.util.List<CommerceTermEntry> filterFindByC_A(
		long companyId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
			orderByComparator);

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
	public CommerceTermEntry[] filterFindByC_A_PrevAndNext(
			long commerceTermEntryId, long companyId, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
				orderByComparator)
		throws NoSuchTermEntryException;

	/**
	 * Removes all the commerce term entries where companyId = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 */
	public void removeByC_A(long companyId, boolean active);

	/**
	 * Returns the number of commerce term entries where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching commerce term entries
	 */
	public int countByC_A(long companyId, boolean active);

	/**
	 * Returns the number of commerce term entries that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching commerce term entries that the user has permission to view
	 */
	public int filterCountByC_A(long companyId, boolean active);

	/**
	 * Returns the commerce term entry where companyId = &#63; and name = &#63; or throws a <code>NoSuchTermEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	public CommerceTermEntry findByC_N(long companyId, String name)
		throws NoSuchTermEntryException;

	/**
	 * Returns the commerce term entry where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	public CommerceTermEntry fetchByC_N(long companyId, String name);

	/**
	 * Returns the commerce term entry where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	public CommerceTermEntry fetchByC_N(
		long companyId, String name, boolean useFinderCache);

	/**
	 * Removes the commerce term entry where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the commerce term entry that was removed
	 */
	public CommerceTermEntry removeByC_N(long companyId, String name)
		throws NoSuchTermEntryException;

	/**
	 * Returns the number of commerce term entries where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching commerce term entries
	 */
	public int countByC_N(long companyId, String name);

	/**
	 * Returns all the commerce term entries where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching commerce term entries
	 */
	public java.util.List<CommerceTermEntry> findByC_LikeType(
		long companyId, String type);

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
	public java.util.List<CommerceTermEntry> findByC_LikeType(
		long companyId, String type, int start, int end);

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
	public java.util.List<CommerceTermEntry> findByC_LikeType(
		long companyId, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
			orderByComparator);

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
	public java.util.List<CommerceTermEntry> findByC_LikeType(
		long companyId, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce term entry in the ordered set where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	public CommerceTermEntry findByC_LikeType_First(
			long companyId, String type,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
				orderByComparator)
		throws NoSuchTermEntryException;

	/**
	 * Returns the first commerce term entry in the ordered set where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	public CommerceTermEntry fetchByC_LikeType_First(
		long companyId, String type,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
			orderByComparator);

	/**
	 * Returns the last commerce term entry in the ordered set where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	public CommerceTermEntry findByC_LikeType_Last(
			long companyId, String type,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
				orderByComparator)
		throws NoSuchTermEntryException;

	/**
	 * Returns the last commerce term entry in the ordered set where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	public CommerceTermEntry fetchByC_LikeType_Last(
		long companyId, String type,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
			orderByComparator);

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
	public CommerceTermEntry[] findByC_LikeType_PrevAndNext(
			long commerceTermEntryId, long companyId, String type,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
				orderByComparator)
		throws NoSuchTermEntryException;

	/**
	 * Returns all the commerce term entries that the user has permission to view where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching commerce term entries that the user has permission to view
	 */
	public java.util.List<CommerceTermEntry> filterFindByC_LikeType(
		long companyId, String type);

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
	public java.util.List<CommerceTermEntry> filterFindByC_LikeType(
		long companyId, String type, int start, int end);

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
	public java.util.List<CommerceTermEntry> filterFindByC_LikeType(
		long companyId, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
			orderByComparator);

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
	public CommerceTermEntry[] filterFindByC_LikeType_PrevAndNext(
			long commerceTermEntryId, long companyId, String type,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
				orderByComparator)
		throws NoSuchTermEntryException;

	/**
	 * Removes all the commerce term entries where companyId = &#63; and type LIKE &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 */
	public void removeByC_LikeType(long companyId, String type);

	/**
	 * Returns the number of commerce term entries where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching commerce term entries
	 */
	public int countByC_LikeType(long companyId, String type);

	/**
	 * Returns the number of commerce term entries that the user has permission to view where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching commerce term entries that the user has permission to view
	 */
	public int filterCountByC_LikeType(long companyId, String type);

	/**
	 * Returns all the commerce term entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching commerce term entries
	 */
	public java.util.List<CommerceTermEntry> findByLtD_S(
		Date displayDate, int status);

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
	public java.util.List<CommerceTermEntry> findByLtD_S(
		Date displayDate, int status, int start, int end);

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
	public java.util.List<CommerceTermEntry> findByLtD_S(
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
			orderByComparator);

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
	public java.util.List<CommerceTermEntry> findByLtD_S(
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce term entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	public CommerceTermEntry findByLtD_S_First(
			Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
				orderByComparator)
		throws NoSuchTermEntryException;

	/**
	 * Returns the first commerce term entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	public CommerceTermEntry fetchByLtD_S_First(
		Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
			orderByComparator);

	/**
	 * Returns the last commerce term entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	public CommerceTermEntry findByLtD_S_Last(
			Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
				orderByComparator)
		throws NoSuchTermEntryException;

	/**
	 * Returns the last commerce term entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	public CommerceTermEntry fetchByLtD_S_Last(
		Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
			orderByComparator);

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
	public CommerceTermEntry[] findByLtD_S_PrevAndNext(
			long commerceTermEntryId, Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
				orderByComparator)
		throws NoSuchTermEntryException;

	/**
	 * Returns all the commerce term entries that the user has permission to view where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching commerce term entries that the user has permission to view
	 */
	public java.util.List<CommerceTermEntry> filterFindByLtD_S(
		Date displayDate, int status);

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
	public java.util.List<CommerceTermEntry> filterFindByLtD_S(
		Date displayDate, int status, int start, int end);

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
	public java.util.List<CommerceTermEntry> filterFindByLtD_S(
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
			orderByComparator);

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
	public CommerceTermEntry[] filterFindByLtD_S_PrevAndNext(
			long commerceTermEntryId, Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
				orderByComparator)
		throws NoSuchTermEntryException;

	/**
	 * Removes all the commerce term entries where displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 */
	public void removeByLtD_S(Date displayDate, int status);

	/**
	 * Returns the number of commerce term entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching commerce term entries
	 */
	public int countByLtD_S(Date displayDate, int status);

	/**
	 * Returns the number of commerce term entries that the user has permission to view where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching commerce term entries that the user has permission to view
	 */
	public int filterCountByLtD_S(Date displayDate, int status);

	/**
	 * Returns all the commerce term entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the matching commerce term entries
	 */
	public java.util.List<CommerceTermEntry> findByLtE_S(
		Date expirationDate, int status);

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
	public java.util.List<CommerceTermEntry> findByLtE_S(
		Date expirationDate, int status, int start, int end);

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
	public java.util.List<CommerceTermEntry> findByLtE_S(
		Date expirationDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
			orderByComparator);

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
	public java.util.List<CommerceTermEntry> findByLtE_S(
		Date expirationDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce term entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	public CommerceTermEntry findByLtE_S_First(
			Date expirationDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
				orderByComparator)
		throws NoSuchTermEntryException;

	/**
	 * Returns the first commerce term entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	public CommerceTermEntry fetchByLtE_S_First(
		Date expirationDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
			orderByComparator);

	/**
	 * Returns the last commerce term entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	public CommerceTermEntry findByLtE_S_Last(
			Date expirationDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
				orderByComparator)
		throws NoSuchTermEntryException;

	/**
	 * Returns the last commerce term entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	public CommerceTermEntry fetchByLtE_S_Last(
		Date expirationDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
			orderByComparator);

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
	public CommerceTermEntry[] findByLtE_S_PrevAndNext(
			long commerceTermEntryId, Date expirationDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
				orderByComparator)
		throws NoSuchTermEntryException;

	/**
	 * Returns all the commerce term entries that the user has permission to view where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the matching commerce term entries that the user has permission to view
	 */
	public java.util.List<CommerceTermEntry> filterFindByLtE_S(
		Date expirationDate, int status);

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
	public java.util.List<CommerceTermEntry> filterFindByLtE_S(
		Date expirationDate, int status, int start, int end);

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
	public java.util.List<CommerceTermEntry> filterFindByLtE_S(
		Date expirationDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
			orderByComparator);

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
	public CommerceTermEntry[] filterFindByLtE_S_PrevAndNext(
			long commerceTermEntryId, Date expirationDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
				orderByComparator)
		throws NoSuchTermEntryException;

	/**
	 * Removes all the commerce term entries where expirationDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 */
	public void removeByLtE_S(Date expirationDate, int status);

	/**
	 * Returns the number of commerce term entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the number of matching commerce term entries
	 */
	public int countByLtE_S(Date expirationDate, int status);

	/**
	 * Returns the number of commerce term entries that the user has permission to view where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the number of matching commerce term entries that the user has permission to view
	 */
	public int filterCountByLtE_S(Date expirationDate, int status);

	/**
	 * Returns all the commerce term entries where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @return the matching commerce term entries
	 */
	public java.util.List<CommerceTermEntry> findByC_A_LikeType(
		long companyId, boolean active, String type);

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
	public java.util.List<CommerceTermEntry> findByC_A_LikeType(
		long companyId, boolean active, String type, int start, int end);

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
	public java.util.List<CommerceTermEntry> findByC_A_LikeType(
		long companyId, boolean active, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
			orderByComparator);

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
	public java.util.List<CommerceTermEntry> findByC_A_LikeType(
		long companyId, boolean active, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
			orderByComparator,
		boolean useFinderCache);

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
	public CommerceTermEntry findByC_A_LikeType_First(
			long companyId, boolean active, String type,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
				orderByComparator)
		throws NoSuchTermEntryException;

	/**
	 * Returns the first commerce term entry in the ordered set where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	public CommerceTermEntry fetchByC_A_LikeType_First(
		long companyId, boolean active, String type,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
			orderByComparator);

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
	public CommerceTermEntry findByC_A_LikeType_Last(
			long companyId, boolean active, String type,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
				orderByComparator)
		throws NoSuchTermEntryException;

	/**
	 * Returns the last commerce term entry in the ordered set where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	public CommerceTermEntry fetchByC_A_LikeType_Last(
		long companyId, boolean active, String type,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
			orderByComparator);

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
	public CommerceTermEntry[] findByC_A_LikeType_PrevAndNext(
			long commerceTermEntryId, long companyId, boolean active,
			String type,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
				orderByComparator)
		throws NoSuchTermEntryException;

	/**
	 * Returns all the commerce term entries that the user has permission to view where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @return the matching commerce term entries that the user has permission to view
	 */
	public java.util.List<CommerceTermEntry> filterFindByC_A_LikeType(
		long companyId, boolean active, String type);

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
	public java.util.List<CommerceTermEntry> filterFindByC_A_LikeType(
		long companyId, boolean active, String type, int start, int end);

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
	public java.util.List<CommerceTermEntry> filterFindByC_A_LikeType(
		long companyId, boolean active, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
			orderByComparator);

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
	public CommerceTermEntry[] filterFindByC_A_LikeType_PrevAndNext(
			long commerceTermEntryId, long companyId, boolean active,
			String type,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
				orderByComparator)
		throws NoSuchTermEntryException;

	/**
	 * Removes all the commerce term entries where companyId = &#63; and active = &#63; and type LIKE &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 */
	public void removeByC_A_LikeType(
		long companyId, boolean active, String type);

	/**
	 * Returns the number of commerce term entries where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @return the number of matching commerce term entries
	 */
	public int countByC_A_LikeType(long companyId, boolean active, String type);

	/**
	 * Returns the number of commerce term entries that the user has permission to view where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @return the number of matching commerce term entries that the user has permission to view
	 */
	public int filterCountByC_A_LikeType(
		long companyId, boolean active, String type);

	/**
	 * Returns the commerce term entry where companyId = &#63; and priority = &#63; and type = &#63; or throws a <code>NoSuchTermEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param priority the priority
	 * @param type the type
	 * @return the matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	public CommerceTermEntry findByC_P_T(
			long companyId, double priority, String type)
		throws NoSuchTermEntryException;

	/**
	 * Returns the commerce term entry where companyId = &#63; and priority = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param priority the priority
	 * @param type the type
	 * @return the matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	public CommerceTermEntry fetchByC_P_T(
		long companyId, double priority, String type);

	/**
	 * Returns the commerce term entry where companyId = &#63; and priority = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param priority the priority
	 * @param type the type
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	public CommerceTermEntry fetchByC_P_T(
		long companyId, double priority, String type, boolean useFinderCache);

	/**
	 * Removes the commerce term entry where companyId = &#63; and priority = &#63; and type = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param priority the priority
	 * @param type the type
	 * @return the commerce term entry that was removed
	 */
	public CommerceTermEntry removeByC_P_T(
			long companyId, double priority, String type)
		throws NoSuchTermEntryException;

	/**
	 * Returns the number of commerce term entries where companyId = &#63; and priority = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param priority the priority
	 * @param type the type
	 * @return the number of matching commerce term entries
	 */
	public int countByC_P_T(long companyId, double priority, String type);

	/**
	 * Returns the commerce term entry where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchTermEntryException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	public CommerceTermEntry findByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchTermEntryException;

	/**
	 * Returns the commerce term entry where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	public CommerceTermEntry fetchByERC_C(
		String externalReferenceCode, long companyId);

	/**
	 * Returns the commerce term entry where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	public CommerceTermEntry fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache);

	/**
	 * Removes the commerce term entry where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the commerce term entry that was removed
	 */
	public CommerceTermEntry removeByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchTermEntryException;

	/**
	 * Returns the number of commerce term entries where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching commerce term entries
	 */
	public int countByERC_C(String externalReferenceCode, long companyId);

	/**
	 * Caches the commerce term entry in the entity cache if it is enabled.
	 *
	 * @param commerceTermEntry the commerce term entry
	 */
	public void cacheResult(CommerceTermEntry commerceTermEntry);

	/**
	 * Caches the commerce term entries in the entity cache if it is enabled.
	 *
	 * @param commerceTermEntries the commerce term entries
	 */
	public void cacheResult(
		java.util.List<CommerceTermEntry> commerceTermEntries);

	/**
	 * Creates a new commerce term entry with the primary key. Does not add the commerce term entry to the database.
	 *
	 * @param commerceTermEntryId the primary key for the new commerce term entry
	 * @return the new commerce term entry
	 */
	public CommerceTermEntry create(long commerceTermEntryId);

	/**
	 * Removes the commerce term entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceTermEntryId the primary key of the commerce term entry
	 * @return the commerce term entry that was removed
	 * @throws NoSuchTermEntryException if a commerce term entry with the primary key could not be found
	 */
	public CommerceTermEntry remove(long commerceTermEntryId)
		throws NoSuchTermEntryException;

	public CommerceTermEntry updateImpl(CommerceTermEntry commerceTermEntry);

	/**
	 * Returns the commerce term entry with the primary key or throws a <code>NoSuchTermEntryException</code> if it could not be found.
	 *
	 * @param commerceTermEntryId the primary key of the commerce term entry
	 * @return the commerce term entry
	 * @throws NoSuchTermEntryException if a commerce term entry with the primary key could not be found
	 */
	public CommerceTermEntry findByPrimaryKey(long commerceTermEntryId)
		throws NoSuchTermEntryException;

	/**
	 * Returns the commerce term entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceTermEntryId the primary key of the commerce term entry
	 * @return the commerce term entry, or <code>null</code> if a commerce term entry with the primary key could not be found
	 */
	public CommerceTermEntry fetchByPrimaryKey(long commerceTermEntryId);

	/**
	 * Returns all the commerce term entries.
	 *
	 * @return the commerce term entries
	 */
	public java.util.List<CommerceTermEntry> findAll();

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
	public java.util.List<CommerceTermEntry> findAll(int start, int end);

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
	public java.util.List<CommerceTermEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
			orderByComparator);

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
	public java.util.List<CommerceTermEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce term entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce term entries.
	 *
	 * @return the number of commerce term entries
	 */
	public int countAll();

}
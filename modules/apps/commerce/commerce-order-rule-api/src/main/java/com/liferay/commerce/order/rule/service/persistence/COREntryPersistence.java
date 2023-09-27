/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.rule.service.persistence;

import com.liferay.commerce.order.rule.exception.NoSuchCOREntryException;
import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the cor entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @see COREntryUtil
 * @generated
 */
@ProviderType
public interface COREntryPersistence extends BasePersistence<COREntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link COREntryUtil} to access the cor entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the cor entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cor entries
	 */
	public java.util.List<COREntry> findByUuid(String uuid);

	/**
	 * Returns a range of all the cor entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cor entries
	 * @param end the upper bound of the range of cor entries (not inclusive)
	 * @return the range of matching cor entries
	 */
	public java.util.List<COREntry> findByUuid(String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the cor entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cor entries
	 * @param end the upper bound of the range of cor entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cor entries
	 */
	public java.util.List<COREntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<COREntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cor entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cor entries
	 * @param end the upper bound of the range of cor entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cor entries
	 */
	public java.util.List<COREntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<COREntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cor entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cor entry
	 * @throws NoSuchCOREntryException if a matching cor entry could not be found
	 */
	public COREntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<COREntry>
				orderByComparator)
		throws NoSuchCOREntryException;

	/**
	 * Returns the first cor entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cor entry, or <code>null</code> if a matching cor entry could not be found
	 */
	public COREntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<COREntry>
			orderByComparator);

	/**
	 * Returns the last cor entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cor entry
	 * @throws NoSuchCOREntryException if a matching cor entry could not be found
	 */
	public COREntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<COREntry>
				orderByComparator)
		throws NoSuchCOREntryException;

	/**
	 * Returns the last cor entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cor entry, or <code>null</code> if a matching cor entry could not be found
	 */
	public COREntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<COREntry>
			orderByComparator);

	/**
	 * Returns the cor entries before and after the current cor entry in the ordered set where uuid = &#63;.
	 *
	 * @param COREntryId the primary key of the current cor entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cor entry
	 * @throws NoSuchCOREntryException if a cor entry with the primary key could not be found
	 */
	public COREntry[] findByUuid_PrevAndNext(
			long COREntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<COREntry>
				orderByComparator)
		throws NoSuchCOREntryException;

	/**
	 * Returns all the cor entries that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cor entries that the user has permission to view
	 */
	public java.util.List<COREntry> filterFindByUuid(String uuid);

	/**
	 * Returns a range of all the cor entries that the user has permission to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cor entries
	 * @param end the upper bound of the range of cor entries (not inclusive)
	 * @return the range of matching cor entries that the user has permission to view
	 */
	public java.util.List<COREntry> filterFindByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the cor entries that the user has permissions to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cor entries
	 * @param end the upper bound of the range of cor entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cor entries that the user has permission to view
	 */
	public java.util.List<COREntry> filterFindByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<COREntry>
			orderByComparator);

	/**
	 * Returns the cor entries before and after the current cor entry in the ordered set of cor entries that the user has permission to view where uuid = &#63;.
	 *
	 * @param COREntryId the primary key of the current cor entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cor entry
	 * @throws NoSuchCOREntryException if a cor entry with the primary key could not be found
	 */
	public COREntry[] filterFindByUuid_PrevAndNext(
			long COREntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<COREntry>
				orderByComparator)
		throws NoSuchCOREntryException;

	/**
	 * Removes all the cor entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of cor entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cor entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the number of cor entries that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cor entries that the user has permission to view
	 */
	public int filterCountByUuid(String uuid);

	/**
	 * Returns all the cor entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cor entries
	 */
	public java.util.List<COREntry> findByUuid_C(String uuid, long companyId);

	/**
	 * Returns a range of all the cor entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cor entries
	 * @param end the upper bound of the range of cor entries (not inclusive)
	 * @return the range of matching cor entries
	 */
	public java.util.List<COREntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the cor entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cor entries
	 * @param end the upper bound of the range of cor entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cor entries
	 */
	public java.util.List<COREntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<COREntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cor entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cor entries
	 * @param end the upper bound of the range of cor entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cor entries
	 */
	public java.util.List<COREntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<COREntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cor entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cor entry
	 * @throws NoSuchCOREntryException if a matching cor entry could not be found
	 */
	public COREntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<COREntry>
				orderByComparator)
		throws NoSuchCOREntryException;

	/**
	 * Returns the first cor entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cor entry, or <code>null</code> if a matching cor entry could not be found
	 */
	public COREntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<COREntry>
			orderByComparator);

	/**
	 * Returns the last cor entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cor entry
	 * @throws NoSuchCOREntryException if a matching cor entry could not be found
	 */
	public COREntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<COREntry>
				orderByComparator)
		throws NoSuchCOREntryException;

	/**
	 * Returns the last cor entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cor entry, or <code>null</code> if a matching cor entry could not be found
	 */
	public COREntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<COREntry>
			orderByComparator);

	/**
	 * Returns the cor entries before and after the current cor entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param COREntryId the primary key of the current cor entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cor entry
	 * @throws NoSuchCOREntryException if a cor entry with the primary key could not be found
	 */
	public COREntry[] findByUuid_C_PrevAndNext(
			long COREntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<COREntry>
				orderByComparator)
		throws NoSuchCOREntryException;

	/**
	 * Returns all the cor entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cor entries that the user has permission to view
	 */
	public java.util.List<COREntry> filterFindByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the cor entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cor entries
	 * @param end the upper bound of the range of cor entries (not inclusive)
	 * @return the range of matching cor entries that the user has permission to view
	 */
	public java.util.List<COREntry> filterFindByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the cor entries that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cor entries
	 * @param end the upper bound of the range of cor entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cor entries that the user has permission to view
	 */
	public java.util.List<COREntry> filterFindByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<COREntry>
			orderByComparator);

	/**
	 * Returns the cor entries before and after the current cor entry in the ordered set of cor entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param COREntryId the primary key of the current cor entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cor entry
	 * @throws NoSuchCOREntryException if a cor entry with the primary key could not be found
	 */
	public COREntry[] filterFindByUuid_C_PrevAndNext(
			long COREntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<COREntry>
				orderByComparator)
		throws NoSuchCOREntryException;

	/**
	 * Removes all the cor entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of cor entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cor entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of cor entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cor entries that the user has permission to view
	 */
	public int filterCountByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the cor entries where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching cor entries
	 */
	public java.util.List<COREntry> findByC_A(long companyId, boolean active);

	/**
	 * Returns a range of all the cor entries where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of cor entries
	 * @param end the upper bound of the range of cor entries (not inclusive)
	 * @return the range of matching cor entries
	 */
	public java.util.List<COREntry> findByC_A(
		long companyId, boolean active, int start, int end);

	/**
	 * Returns an ordered range of all the cor entries where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of cor entries
	 * @param end the upper bound of the range of cor entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cor entries
	 */
	public java.util.List<COREntry> findByC_A(
		long companyId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<COREntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cor entries where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of cor entries
	 * @param end the upper bound of the range of cor entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cor entries
	 */
	public java.util.List<COREntry> findByC_A(
		long companyId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<COREntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cor entry in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cor entry
	 * @throws NoSuchCOREntryException if a matching cor entry could not be found
	 */
	public COREntry findByC_A_First(
			long companyId, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator<COREntry>
				orderByComparator)
		throws NoSuchCOREntryException;

	/**
	 * Returns the first cor entry in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cor entry, or <code>null</code> if a matching cor entry could not be found
	 */
	public COREntry fetchByC_A_First(
		long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<COREntry>
			orderByComparator);

	/**
	 * Returns the last cor entry in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cor entry
	 * @throws NoSuchCOREntryException if a matching cor entry could not be found
	 */
	public COREntry findByC_A_Last(
			long companyId, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator<COREntry>
				orderByComparator)
		throws NoSuchCOREntryException;

	/**
	 * Returns the last cor entry in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cor entry, or <code>null</code> if a matching cor entry could not be found
	 */
	public COREntry fetchByC_A_Last(
		long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<COREntry>
			orderByComparator);

	/**
	 * Returns the cor entries before and after the current cor entry in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param COREntryId the primary key of the current cor entry
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cor entry
	 * @throws NoSuchCOREntryException if a cor entry with the primary key could not be found
	 */
	public COREntry[] findByC_A_PrevAndNext(
			long COREntryId, long companyId, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator<COREntry>
				orderByComparator)
		throws NoSuchCOREntryException;

	/**
	 * Returns all the cor entries that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching cor entries that the user has permission to view
	 */
	public java.util.List<COREntry> filterFindByC_A(
		long companyId, boolean active);

	/**
	 * Returns a range of all the cor entries that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of cor entries
	 * @param end the upper bound of the range of cor entries (not inclusive)
	 * @return the range of matching cor entries that the user has permission to view
	 */
	public java.util.List<COREntry> filterFindByC_A(
		long companyId, boolean active, int start, int end);

	/**
	 * Returns an ordered range of all the cor entries that the user has permissions to view where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of cor entries
	 * @param end the upper bound of the range of cor entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cor entries that the user has permission to view
	 */
	public java.util.List<COREntry> filterFindByC_A(
		long companyId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<COREntry>
			orderByComparator);

	/**
	 * Returns the cor entries before and after the current cor entry in the ordered set of cor entries that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * @param COREntryId the primary key of the current cor entry
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cor entry
	 * @throws NoSuchCOREntryException if a cor entry with the primary key could not be found
	 */
	public COREntry[] filterFindByC_A_PrevAndNext(
			long COREntryId, long companyId, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator<COREntry>
				orderByComparator)
		throws NoSuchCOREntryException;

	/**
	 * Removes all the cor entries where companyId = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 */
	public void removeByC_A(long companyId, boolean active);

	/**
	 * Returns the number of cor entries where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching cor entries
	 */
	public int countByC_A(long companyId, boolean active);

	/**
	 * Returns the number of cor entries that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching cor entries that the user has permission to view
	 */
	public int filterCountByC_A(long companyId, boolean active);

	/**
	 * Returns all the cor entries where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching cor entries
	 */
	public java.util.List<COREntry> findByC_LikeType(
		long companyId, String type);

	/**
	 * Returns a range of all the cor entries where companyId = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of cor entries
	 * @param end the upper bound of the range of cor entries (not inclusive)
	 * @return the range of matching cor entries
	 */
	public java.util.List<COREntry> findByC_LikeType(
		long companyId, String type, int start, int end);

	/**
	 * Returns an ordered range of all the cor entries where companyId = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of cor entries
	 * @param end the upper bound of the range of cor entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cor entries
	 */
	public java.util.List<COREntry> findByC_LikeType(
		long companyId, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<COREntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cor entries where companyId = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of cor entries
	 * @param end the upper bound of the range of cor entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cor entries
	 */
	public java.util.List<COREntry> findByC_LikeType(
		long companyId, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<COREntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cor entry in the ordered set where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cor entry
	 * @throws NoSuchCOREntryException if a matching cor entry could not be found
	 */
	public COREntry findByC_LikeType_First(
			long companyId, String type,
			com.liferay.portal.kernel.util.OrderByComparator<COREntry>
				orderByComparator)
		throws NoSuchCOREntryException;

	/**
	 * Returns the first cor entry in the ordered set where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cor entry, or <code>null</code> if a matching cor entry could not be found
	 */
	public COREntry fetchByC_LikeType_First(
		long companyId, String type,
		com.liferay.portal.kernel.util.OrderByComparator<COREntry>
			orderByComparator);

	/**
	 * Returns the last cor entry in the ordered set where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cor entry
	 * @throws NoSuchCOREntryException if a matching cor entry could not be found
	 */
	public COREntry findByC_LikeType_Last(
			long companyId, String type,
			com.liferay.portal.kernel.util.OrderByComparator<COREntry>
				orderByComparator)
		throws NoSuchCOREntryException;

	/**
	 * Returns the last cor entry in the ordered set where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cor entry, or <code>null</code> if a matching cor entry could not be found
	 */
	public COREntry fetchByC_LikeType_Last(
		long companyId, String type,
		com.liferay.portal.kernel.util.OrderByComparator<COREntry>
			orderByComparator);

	/**
	 * Returns the cor entries before and after the current cor entry in the ordered set where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param COREntryId the primary key of the current cor entry
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cor entry
	 * @throws NoSuchCOREntryException if a cor entry with the primary key could not be found
	 */
	public COREntry[] findByC_LikeType_PrevAndNext(
			long COREntryId, long companyId, String type,
			com.liferay.portal.kernel.util.OrderByComparator<COREntry>
				orderByComparator)
		throws NoSuchCOREntryException;

	/**
	 * Returns all the cor entries that the user has permission to view where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching cor entries that the user has permission to view
	 */
	public java.util.List<COREntry> filterFindByC_LikeType(
		long companyId, String type);

	/**
	 * Returns a range of all the cor entries that the user has permission to view where companyId = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of cor entries
	 * @param end the upper bound of the range of cor entries (not inclusive)
	 * @return the range of matching cor entries that the user has permission to view
	 */
	public java.util.List<COREntry> filterFindByC_LikeType(
		long companyId, String type, int start, int end);

	/**
	 * Returns an ordered range of all the cor entries that the user has permissions to view where companyId = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of cor entries
	 * @param end the upper bound of the range of cor entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cor entries that the user has permission to view
	 */
	public java.util.List<COREntry> filterFindByC_LikeType(
		long companyId, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<COREntry>
			orderByComparator);

	/**
	 * Returns the cor entries before and after the current cor entry in the ordered set of cor entries that the user has permission to view where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param COREntryId the primary key of the current cor entry
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cor entry
	 * @throws NoSuchCOREntryException if a cor entry with the primary key could not be found
	 */
	public COREntry[] filterFindByC_LikeType_PrevAndNext(
			long COREntryId, long companyId, String type,
			com.liferay.portal.kernel.util.OrderByComparator<COREntry>
				orderByComparator)
		throws NoSuchCOREntryException;

	/**
	 * Removes all the cor entries where companyId = &#63; and type LIKE &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 */
	public void removeByC_LikeType(long companyId, String type);

	/**
	 * Returns the number of cor entries where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching cor entries
	 */
	public int countByC_LikeType(long companyId, String type);

	/**
	 * Returns the number of cor entries that the user has permission to view where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching cor entries that the user has permission to view
	 */
	public int filterCountByC_LikeType(long companyId, String type);

	/**
	 * Returns all the cor entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching cor entries
	 */
	public java.util.List<COREntry> findByLtD_S(Date displayDate, int status);

	/**
	 * Returns a range of all the cor entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cor entries
	 * @param end the upper bound of the range of cor entries (not inclusive)
	 * @return the range of matching cor entries
	 */
	public java.util.List<COREntry> findByLtD_S(
		Date displayDate, int status, int start, int end);

	/**
	 * Returns an ordered range of all the cor entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cor entries
	 * @param end the upper bound of the range of cor entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cor entries
	 */
	public java.util.List<COREntry> findByLtD_S(
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<COREntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cor entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cor entries
	 * @param end the upper bound of the range of cor entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cor entries
	 */
	public java.util.List<COREntry> findByLtD_S(
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<COREntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cor entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cor entry
	 * @throws NoSuchCOREntryException if a matching cor entry could not be found
	 */
	public COREntry findByLtD_S_First(
			Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<COREntry>
				orderByComparator)
		throws NoSuchCOREntryException;

	/**
	 * Returns the first cor entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cor entry, or <code>null</code> if a matching cor entry could not be found
	 */
	public COREntry fetchByLtD_S_First(
		Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<COREntry>
			orderByComparator);

	/**
	 * Returns the last cor entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cor entry
	 * @throws NoSuchCOREntryException if a matching cor entry could not be found
	 */
	public COREntry findByLtD_S_Last(
			Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<COREntry>
				orderByComparator)
		throws NoSuchCOREntryException;

	/**
	 * Returns the last cor entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cor entry, or <code>null</code> if a matching cor entry could not be found
	 */
	public COREntry fetchByLtD_S_Last(
		Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<COREntry>
			orderByComparator);

	/**
	 * Returns the cor entries before and after the current cor entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param COREntryId the primary key of the current cor entry
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cor entry
	 * @throws NoSuchCOREntryException if a cor entry with the primary key could not be found
	 */
	public COREntry[] findByLtD_S_PrevAndNext(
			long COREntryId, Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<COREntry>
				orderByComparator)
		throws NoSuchCOREntryException;

	/**
	 * Returns all the cor entries that the user has permission to view where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching cor entries that the user has permission to view
	 */
	public java.util.List<COREntry> filterFindByLtD_S(
		Date displayDate, int status);

	/**
	 * Returns a range of all the cor entries that the user has permission to view where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cor entries
	 * @param end the upper bound of the range of cor entries (not inclusive)
	 * @return the range of matching cor entries that the user has permission to view
	 */
	public java.util.List<COREntry> filterFindByLtD_S(
		Date displayDate, int status, int start, int end);

	/**
	 * Returns an ordered range of all the cor entries that the user has permissions to view where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cor entries
	 * @param end the upper bound of the range of cor entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cor entries that the user has permission to view
	 */
	public java.util.List<COREntry> filterFindByLtD_S(
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<COREntry>
			orderByComparator);

	/**
	 * Returns the cor entries before and after the current cor entry in the ordered set of cor entries that the user has permission to view where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param COREntryId the primary key of the current cor entry
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cor entry
	 * @throws NoSuchCOREntryException if a cor entry with the primary key could not be found
	 */
	public COREntry[] filterFindByLtD_S_PrevAndNext(
			long COREntryId, Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<COREntry>
				orderByComparator)
		throws NoSuchCOREntryException;

	/**
	 * Removes all the cor entries where displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 */
	public void removeByLtD_S(Date displayDate, int status);

	/**
	 * Returns the number of cor entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching cor entries
	 */
	public int countByLtD_S(Date displayDate, int status);

	/**
	 * Returns the number of cor entries that the user has permission to view where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching cor entries that the user has permission to view
	 */
	public int filterCountByLtD_S(Date displayDate, int status);

	/**
	 * Returns all the cor entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the matching cor entries
	 */
	public java.util.List<COREntry> findByLtE_S(
		Date expirationDate, int status);

	/**
	 * Returns a range of all the cor entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of cor entries
	 * @param end the upper bound of the range of cor entries (not inclusive)
	 * @return the range of matching cor entries
	 */
	public java.util.List<COREntry> findByLtE_S(
		Date expirationDate, int status, int start, int end);

	/**
	 * Returns an ordered range of all the cor entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of cor entries
	 * @param end the upper bound of the range of cor entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cor entries
	 */
	public java.util.List<COREntry> findByLtE_S(
		Date expirationDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<COREntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cor entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of cor entries
	 * @param end the upper bound of the range of cor entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cor entries
	 */
	public java.util.List<COREntry> findByLtE_S(
		Date expirationDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<COREntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cor entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cor entry
	 * @throws NoSuchCOREntryException if a matching cor entry could not be found
	 */
	public COREntry findByLtE_S_First(
			Date expirationDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<COREntry>
				orderByComparator)
		throws NoSuchCOREntryException;

	/**
	 * Returns the first cor entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cor entry, or <code>null</code> if a matching cor entry could not be found
	 */
	public COREntry fetchByLtE_S_First(
		Date expirationDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<COREntry>
			orderByComparator);

	/**
	 * Returns the last cor entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cor entry
	 * @throws NoSuchCOREntryException if a matching cor entry could not be found
	 */
	public COREntry findByLtE_S_Last(
			Date expirationDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<COREntry>
				orderByComparator)
		throws NoSuchCOREntryException;

	/**
	 * Returns the last cor entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cor entry, or <code>null</code> if a matching cor entry could not be found
	 */
	public COREntry fetchByLtE_S_Last(
		Date expirationDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<COREntry>
			orderByComparator);

	/**
	 * Returns the cor entries before and after the current cor entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param COREntryId the primary key of the current cor entry
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cor entry
	 * @throws NoSuchCOREntryException if a cor entry with the primary key could not be found
	 */
	public COREntry[] findByLtE_S_PrevAndNext(
			long COREntryId, Date expirationDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<COREntry>
				orderByComparator)
		throws NoSuchCOREntryException;

	/**
	 * Returns all the cor entries that the user has permission to view where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the matching cor entries that the user has permission to view
	 */
	public java.util.List<COREntry> filterFindByLtE_S(
		Date expirationDate, int status);

	/**
	 * Returns a range of all the cor entries that the user has permission to view where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of cor entries
	 * @param end the upper bound of the range of cor entries (not inclusive)
	 * @return the range of matching cor entries that the user has permission to view
	 */
	public java.util.List<COREntry> filterFindByLtE_S(
		Date expirationDate, int status, int start, int end);

	/**
	 * Returns an ordered range of all the cor entries that the user has permissions to view where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of cor entries
	 * @param end the upper bound of the range of cor entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cor entries that the user has permission to view
	 */
	public java.util.List<COREntry> filterFindByLtE_S(
		Date expirationDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<COREntry>
			orderByComparator);

	/**
	 * Returns the cor entries before and after the current cor entry in the ordered set of cor entries that the user has permission to view where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param COREntryId the primary key of the current cor entry
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cor entry
	 * @throws NoSuchCOREntryException if a cor entry with the primary key could not be found
	 */
	public COREntry[] filterFindByLtE_S_PrevAndNext(
			long COREntryId, Date expirationDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<COREntry>
				orderByComparator)
		throws NoSuchCOREntryException;

	/**
	 * Removes all the cor entries where expirationDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 */
	public void removeByLtE_S(Date expirationDate, int status);

	/**
	 * Returns the number of cor entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the number of matching cor entries
	 */
	public int countByLtE_S(Date expirationDate, int status);

	/**
	 * Returns the number of cor entries that the user has permission to view where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the number of matching cor entries that the user has permission to view
	 */
	public int filterCountByLtE_S(Date expirationDate, int status);

	/**
	 * Returns all the cor entries where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @return the matching cor entries
	 */
	public java.util.List<COREntry> findByC_A_LikeType(
		long companyId, boolean active, String type);

	/**
	 * Returns a range of all the cor entries where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of cor entries
	 * @param end the upper bound of the range of cor entries (not inclusive)
	 * @return the range of matching cor entries
	 */
	public java.util.List<COREntry> findByC_A_LikeType(
		long companyId, boolean active, String type, int start, int end);

	/**
	 * Returns an ordered range of all the cor entries where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of cor entries
	 * @param end the upper bound of the range of cor entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cor entries
	 */
	public java.util.List<COREntry> findByC_A_LikeType(
		long companyId, boolean active, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<COREntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cor entries where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of cor entries
	 * @param end the upper bound of the range of cor entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cor entries
	 */
	public java.util.List<COREntry> findByC_A_LikeType(
		long companyId, boolean active, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<COREntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cor entry in the ordered set where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cor entry
	 * @throws NoSuchCOREntryException if a matching cor entry could not be found
	 */
	public COREntry findByC_A_LikeType_First(
			long companyId, boolean active, String type,
			com.liferay.portal.kernel.util.OrderByComparator<COREntry>
				orderByComparator)
		throws NoSuchCOREntryException;

	/**
	 * Returns the first cor entry in the ordered set where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cor entry, or <code>null</code> if a matching cor entry could not be found
	 */
	public COREntry fetchByC_A_LikeType_First(
		long companyId, boolean active, String type,
		com.liferay.portal.kernel.util.OrderByComparator<COREntry>
			orderByComparator);

	/**
	 * Returns the last cor entry in the ordered set where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cor entry
	 * @throws NoSuchCOREntryException if a matching cor entry could not be found
	 */
	public COREntry findByC_A_LikeType_Last(
			long companyId, boolean active, String type,
			com.liferay.portal.kernel.util.OrderByComparator<COREntry>
				orderByComparator)
		throws NoSuchCOREntryException;

	/**
	 * Returns the last cor entry in the ordered set where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cor entry, or <code>null</code> if a matching cor entry could not be found
	 */
	public COREntry fetchByC_A_LikeType_Last(
		long companyId, boolean active, String type,
		com.liferay.portal.kernel.util.OrderByComparator<COREntry>
			orderByComparator);

	/**
	 * Returns the cor entries before and after the current cor entry in the ordered set where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param COREntryId the primary key of the current cor entry
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cor entry
	 * @throws NoSuchCOREntryException if a cor entry with the primary key could not be found
	 */
	public COREntry[] findByC_A_LikeType_PrevAndNext(
			long COREntryId, long companyId, boolean active, String type,
			com.liferay.portal.kernel.util.OrderByComparator<COREntry>
				orderByComparator)
		throws NoSuchCOREntryException;

	/**
	 * Returns all the cor entries that the user has permission to view where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @return the matching cor entries that the user has permission to view
	 */
	public java.util.List<COREntry> filterFindByC_A_LikeType(
		long companyId, boolean active, String type);

	/**
	 * Returns a range of all the cor entries that the user has permission to view where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of cor entries
	 * @param end the upper bound of the range of cor entries (not inclusive)
	 * @return the range of matching cor entries that the user has permission to view
	 */
	public java.util.List<COREntry> filterFindByC_A_LikeType(
		long companyId, boolean active, String type, int start, int end);

	/**
	 * Returns an ordered range of all the cor entries that the user has permissions to view where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of cor entries
	 * @param end the upper bound of the range of cor entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cor entries that the user has permission to view
	 */
	public java.util.List<COREntry> filterFindByC_A_LikeType(
		long companyId, boolean active, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<COREntry>
			orderByComparator);

	/**
	 * Returns the cor entries before and after the current cor entry in the ordered set of cor entries that the user has permission to view where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param COREntryId the primary key of the current cor entry
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cor entry
	 * @throws NoSuchCOREntryException if a cor entry with the primary key could not be found
	 */
	public COREntry[] filterFindByC_A_LikeType_PrevAndNext(
			long COREntryId, long companyId, boolean active, String type,
			com.liferay.portal.kernel.util.OrderByComparator<COREntry>
				orderByComparator)
		throws NoSuchCOREntryException;

	/**
	 * Removes all the cor entries where companyId = &#63; and active = &#63; and type LIKE &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 */
	public void removeByC_A_LikeType(
		long companyId, boolean active, String type);

	/**
	 * Returns the number of cor entries where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @return the number of matching cor entries
	 */
	public int countByC_A_LikeType(long companyId, boolean active, String type);

	/**
	 * Returns the number of cor entries that the user has permission to view where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @return the number of matching cor entries that the user has permission to view
	 */
	public int filterCountByC_A_LikeType(
		long companyId, boolean active, String type);

	/**
	 * Returns the cor entry where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchCOREntryException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching cor entry
	 * @throws NoSuchCOREntryException if a matching cor entry could not be found
	 */
	public COREntry findByERC_C(String externalReferenceCode, long companyId)
		throws NoSuchCOREntryException;

	/**
	 * Returns the cor entry where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching cor entry, or <code>null</code> if a matching cor entry could not be found
	 */
	public COREntry fetchByERC_C(String externalReferenceCode, long companyId);

	/**
	 * Returns the cor entry where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cor entry, or <code>null</code> if a matching cor entry could not be found
	 */
	public COREntry fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache);

	/**
	 * Removes the cor entry where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the cor entry that was removed
	 */
	public COREntry removeByERC_C(String externalReferenceCode, long companyId)
		throws NoSuchCOREntryException;

	/**
	 * Returns the number of cor entries where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching cor entries
	 */
	public int countByERC_C(String externalReferenceCode, long companyId);

	/**
	 * Caches the cor entry in the entity cache if it is enabled.
	 *
	 * @param corEntry the cor entry
	 */
	public void cacheResult(COREntry corEntry);

	/**
	 * Caches the cor entries in the entity cache if it is enabled.
	 *
	 * @param corEntries the cor entries
	 */
	public void cacheResult(java.util.List<COREntry> corEntries);

	/**
	 * Creates a new cor entry with the primary key. Does not add the cor entry to the database.
	 *
	 * @param COREntryId the primary key for the new cor entry
	 * @return the new cor entry
	 */
	public COREntry create(long COREntryId);

	/**
	 * Removes the cor entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param COREntryId the primary key of the cor entry
	 * @return the cor entry that was removed
	 * @throws NoSuchCOREntryException if a cor entry with the primary key could not be found
	 */
	public COREntry remove(long COREntryId) throws NoSuchCOREntryException;

	public COREntry updateImpl(COREntry corEntry);

	/**
	 * Returns the cor entry with the primary key or throws a <code>NoSuchCOREntryException</code> if it could not be found.
	 *
	 * @param COREntryId the primary key of the cor entry
	 * @return the cor entry
	 * @throws NoSuchCOREntryException if a cor entry with the primary key could not be found
	 */
	public COREntry findByPrimaryKey(long COREntryId)
		throws NoSuchCOREntryException;

	/**
	 * Returns the cor entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param COREntryId the primary key of the cor entry
	 * @return the cor entry, or <code>null</code> if a cor entry with the primary key could not be found
	 */
	public COREntry fetchByPrimaryKey(long COREntryId);

	/**
	 * Returns all the cor entries.
	 *
	 * @return the cor entries
	 */
	public java.util.List<COREntry> findAll();

	/**
	 * Returns a range of all the cor entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cor entries
	 * @param end the upper bound of the range of cor entries (not inclusive)
	 * @return the range of cor entries
	 */
	public java.util.List<COREntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the cor entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cor entries
	 * @param end the upper bound of the range of cor entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cor entries
	 */
	public java.util.List<COREntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<COREntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cor entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cor entries
	 * @param end the upper bound of the range of cor entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cor entries
	 */
	public java.util.List<COREntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<COREntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the cor entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of cor entries.
	 *
	 * @return the number of cor entries
	 */
	public int countAll();

}
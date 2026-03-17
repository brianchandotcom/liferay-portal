/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat730.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.compat730.exception.NoSuchERCVersionedEntryException;
import com.liferay.portal.tools.service.builder.test.compat730.model.ERCVersionedEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the erc versioned entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ERCVersionedEntryUtil
 * @generated
 */
@ProviderType
public interface ERCVersionedEntryPersistence
	extends BasePersistence<ERCVersionedEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ERCVersionedEntryUtil} to access the erc versioned entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the erc versioned entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching erc versioned entries
	 */
	public java.util.List<ERCVersionedEntry> findByUuid(String uuid);

	/**
	 * Returns a range of all the erc versioned entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @return the range of matching erc versioned entries
	 */
	public java.util.List<ERCVersionedEntry> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the erc versioned entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching erc versioned entries
	 */
	public java.util.List<ERCVersionedEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ERCVersionedEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the erc versioned entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching erc versioned entries
	 */
	public java.util.List<ERCVersionedEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ERCVersionedEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first erc versioned entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a matching erc versioned entry could not be found
	 */
	public ERCVersionedEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ERCVersionedEntry>
				orderByComparator)
		throws NoSuchERCVersionedEntryException;

	/**
	 * Returns the first erc versioned entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry, or <code>null</code> if a matching erc versioned entry could not be found
	 */
	public ERCVersionedEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ERCVersionedEntry>
			orderByComparator);

	/**
	 * Returns the last erc versioned entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a matching erc versioned entry could not be found
	 */
	public ERCVersionedEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ERCVersionedEntry>
				orderByComparator)
		throws NoSuchERCVersionedEntryException;

	/**
	 * Returns the last erc versioned entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry, or <code>null</code> if a matching erc versioned entry could not be found
	 */
	public ERCVersionedEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ERCVersionedEntry>
			orderByComparator);

	/**
	 * Returns the erc versioned entries before and after the current erc versioned entry in the ordered set where uuid = &#63;.
	 *
	 * @param ercVersionedEntryId the primary key of the current erc versioned entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a erc versioned entry with the primary key could not be found
	 */
	public ERCVersionedEntry[] findByUuid_PrevAndNext(
			long ercVersionedEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ERCVersionedEntry>
				orderByComparator)
		throws NoSuchERCVersionedEntryException;

	/**
	 * Removes all the erc versioned entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of erc versioned entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching erc versioned entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the erc versioned entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchERCVersionedEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a matching erc versioned entry could not be found
	 */
	public ERCVersionedEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchERCVersionedEntryException;

	/**
	 * Returns the erc versioned entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching erc versioned entry, or <code>null</code> if a matching erc versioned entry could not be found
	 */
	public ERCVersionedEntry fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the erc versioned entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching erc versioned entry, or <code>null</code> if a matching erc versioned entry could not be found
	 */
	public ERCVersionedEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the erc versioned entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the erc versioned entry that was removed
	 */
	public ERCVersionedEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchERCVersionedEntryException;

	/**
	 * Returns the number of erc versioned entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching erc versioned entries
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the erc versioned entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching erc versioned entries
	 */
	public java.util.List<ERCVersionedEntry> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the erc versioned entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @return the range of matching erc versioned entries
	 */
	public java.util.List<ERCVersionedEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the erc versioned entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching erc versioned entries
	 */
	public java.util.List<ERCVersionedEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ERCVersionedEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the erc versioned entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching erc versioned entries
	 */
	public java.util.List<ERCVersionedEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ERCVersionedEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first erc versioned entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a matching erc versioned entry could not be found
	 */
	public ERCVersionedEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ERCVersionedEntry>
				orderByComparator)
		throws NoSuchERCVersionedEntryException;

	/**
	 * Returns the first erc versioned entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry, or <code>null</code> if a matching erc versioned entry could not be found
	 */
	public ERCVersionedEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ERCVersionedEntry>
			orderByComparator);

	/**
	 * Returns the last erc versioned entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a matching erc versioned entry could not be found
	 */
	public ERCVersionedEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ERCVersionedEntry>
				orderByComparator)
		throws NoSuchERCVersionedEntryException;

	/**
	 * Returns the last erc versioned entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry, or <code>null</code> if a matching erc versioned entry could not be found
	 */
	public ERCVersionedEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ERCVersionedEntry>
			orderByComparator);

	/**
	 * Returns the erc versioned entries before and after the current erc versioned entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param ercVersionedEntryId the primary key of the current erc versioned entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a erc versioned entry with the primary key could not be found
	 */
	public ERCVersionedEntry[] findByUuid_C_PrevAndNext(
			long ercVersionedEntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ERCVersionedEntry>
				orderByComparator)
		throws NoSuchERCVersionedEntryException;

	/**
	 * Removes all the erc versioned entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of erc versioned entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching erc versioned entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns the erc versioned entry where groupId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchERCVersionedEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a matching erc versioned entry could not be found
	 */
	public ERCVersionedEntry findByG_ERC(
			long groupId, String externalReferenceCode)
		throws NoSuchERCVersionedEntryException;

	/**
	 * Returns the erc versioned entry where groupId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching erc versioned entry, or <code>null</code> if a matching erc versioned entry could not be found
	 */
	public ERCVersionedEntry fetchByG_ERC(
		long groupId, String externalReferenceCode);

	/**
	 * Returns the erc versioned entry where groupId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching erc versioned entry, or <code>null</code> if a matching erc versioned entry could not be found
	 */
	public ERCVersionedEntry fetchByG_ERC(
		long groupId, String externalReferenceCode, boolean useFinderCache);

	/**
	 * Removes the erc versioned entry where groupId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the erc versioned entry that was removed
	 */
	public ERCVersionedEntry removeByG_ERC(
			long groupId, String externalReferenceCode)
		throws NoSuchERCVersionedEntryException;

	/**
	 * Returns the number of erc versioned entries where groupId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching erc versioned entries
	 */
	public int countByG_ERC(long groupId, String externalReferenceCode);

	/**
	 * Caches the erc versioned entry in the entity cache if it is enabled.
	 *
	 * @param ercVersionedEntry the erc versioned entry
	 */
	public void cacheResult(ERCVersionedEntry ercVersionedEntry);

	/**
	 * Caches the erc versioned entries in the entity cache if it is enabled.
	 *
	 * @param ercVersionedEntries the erc versioned entries
	 */
	public void cacheResult(
		java.util.List<ERCVersionedEntry> ercVersionedEntries);

	/**
	 * Creates a new erc versioned entry with the primary key. Does not add the erc versioned entry to the database.
	 *
	 * @param ercVersionedEntryId the primary key for the new erc versioned entry
	 * @return the new erc versioned entry
	 */
	public ERCVersionedEntry create(long ercVersionedEntryId);

	/**
	 * Removes the erc versioned entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ercVersionedEntryId the primary key of the erc versioned entry
	 * @return the erc versioned entry that was removed
	 * @throws NoSuchERCVersionedEntryException if a erc versioned entry with the primary key could not be found
	 */
	public ERCVersionedEntry remove(long ercVersionedEntryId)
		throws NoSuchERCVersionedEntryException;

	public ERCVersionedEntry updateImpl(ERCVersionedEntry ercVersionedEntry);

	/**
	 * Returns the erc versioned entry with the primary key or throws a <code>NoSuchERCVersionedEntryException</code> if it could not be found.
	 *
	 * @param ercVersionedEntryId the primary key of the erc versioned entry
	 * @return the erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a erc versioned entry with the primary key could not be found
	 */
	public ERCVersionedEntry findByPrimaryKey(long ercVersionedEntryId)
		throws NoSuchERCVersionedEntryException;

	/**
	 * Returns the erc versioned entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ercVersionedEntryId the primary key of the erc versioned entry
	 * @return the erc versioned entry, or <code>null</code> if a erc versioned entry with the primary key could not be found
	 */
	public ERCVersionedEntry fetchByPrimaryKey(long ercVersionedEntryId);

	/**
	 * Returns all the erc versioned entries.
	 *
	 * @return the erc versioned entries
	 */
	public java.util.List<ERCVersionedEntry> findAll();

	/**
	 * Returns a range of all the erc versioned entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @return the range of erc versioned entries
	 */
	public java.util.List<ERCVersionedEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the erc versioned entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of erc versioned entries
	 */
	public java.util.List<ERCVersionedEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ERCVersionedEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the erc versioned entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of erc versioned entries
	 */
	public java.util.List<ERCVersionedEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ERCVersionedEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the erc versioned entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of erc versioned entries.
	 *
	 * @return the number of erc versioned entries
	 */
	public int countAll();

}
// SB-Hash:-916622218
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.sequence.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.sequence.exception.NoSuchSequenceEntryException;
import com.liferay.portal.tools.service.builder.test.sequence.model.SequenceEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the sequence entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SequenceEntryUtil
 * @generated
 */
@ProviderType
public interface SequenceEntryPersistence
	extends BasePersistence<SequenceEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SequenceEntryUtil} to access the sequence entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the sequence entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching sequence entries
	 */
	public java.util.List<SequenceEntry> findByUuid(String uuid);

	/**
	 * Returns a range of all the sequence entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SequenceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sequence entries
	 * @param end the upper bound of the range of sequence entries (not inclusive)
	 * @return the range of matching sequence entries
	 */
	public java.util.List<SequenceEntry> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the sequence entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SequenceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sequence entries
	 * @param end the upper bound of the range of sequence entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sequence entries
	 */
	public java.util.List<SequenceEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SequenceEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the sequence entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SequenceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sequence entries
	 * @param end the upper bound of the range of sequence entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sequence entries
	 */
	public java.util.List<SequenceEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SequenceEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first sequence entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sequence entry
	 * @throws NoSuchSequenceEntryException if a matching sequence entry could not be found
	 */
	public SequenceEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<SequenceEntry>
				orderByComparator)
		throws NoSuchSequenceEntryException;

	/**
	 * Returns the first sequence entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sequence entry, or <code>null</code> if a matching sequence entry could not be found
	 */
	public SequenceEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<SequenceEntry>
			orderByComparator);

	/**
	 * Returns the last sequence entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sequence entry
	 * @throws NoSuchSequenceEntryException if a matching sequence entry could not be found
	 */
	public SequenceEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<SequenceEntry>
				orderByComparator)
		throws NoSuchSequenceEntryException;

	/**
	 * Returns the last sequence entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sequence entry, or <code>null</code> if a matching sequence entry could not be found
	 */
	public SequenceEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<SequenceEntry>
			orderByComparator);

	/**
	 * Returns the sequence entries before and after the current sequence entry in the ordered set where uuid = &#63;.
	 *
	 * @param sequenceEntryId the primary key of the current sequence entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sequence entry
	 * @throws NoSuchSequenceEntryException if a sequence entry with the primary key could not be found
	 */
	public SequenceEntry[] findByUuid_PrevAndNext(
			long sequenceEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<SequenceEntry>
				orderByComparator)
		throws NoSuchSequenceEntryException;

	/**
	 * Removes all the sequence entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of sequence entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching sequence entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the sequence entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching sequence entries
	 */
	public java.util.List<SequenceEntry> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the sequence entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SequenceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sequence entries
	 * @param end the upper bound of the range of sequence entries (not inclusive)
	 * @return the range of matching sequence entries
	 */
	public java.util.List<SequenceEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the sequence entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SequenceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sequence entries
	 * @param end the upper bound of the range of sequence entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sequence entries
	 */
	public java.util.List<SequenceEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SequenceEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the sequence entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SequenceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sequence entries
	 * @param end the upper bound of the range of sequence entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sequence entries
	 */
	public java.util.List<SequenceEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SequenceEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first sequence entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sequence entry
	 * @throws NoSuchSequenceEntryException if a matching sequence entry could not be found
	 */
	public SequenceEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SequenceEntry>
				orderByComparator)
		throws NoSuchSequenceEntryException;

	/**
	 * Returns the first sequence entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sequence entry, or <code>null</code> if a matching sequence entry could not be found
	 */
	public SequenceEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SequenceEntry>
			orderByComparator);

	/**
	 * Returns the last sequence entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sequence entry
	 * @throws NoSuchSequenceEntryException if a matching sequence entry could not be found
	 */
	public SequenceEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SequenceEntry>
				orderByComparator)
		throws NoSuchSequenceEntryException;

	/**
	 * Returns the last sequence entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sequence entry, or <code>null</code> if a matching sequence entry could not be found
	 */
	public SequenceEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SequenceEntry>
			orderByComparator);

	/**
	 * Returns the sequence entries before and after the current sequence entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param sequenceEntryId the primary key of the current sequence entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sequence entry
	 * @throws NoSuchSequenceEntryException if a sequence entry with the primary key could not be found
	 */
	public SequenceEntry[] findByUuid_C_PrevAndNext(
			long sequenceEntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SequenceEntry>
				orderByComparator)
		throws NoSuchSequenceEntryException;

	/**
	 * Removes all the sequence entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of sequence entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching sequence entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Caches the sequence entry in the entity cache if it is enabled.
	 *
	 * @param sequenceEntry the sequence entry
	 */
	public void cacheResult(SequenceEntry sequenceEntry);

	/**
	 * Caches the sequence entries in the entity cache if it is enabled.
	 *
	 * @param sequenceEntries the sequence entries
	 */
	public void cacheResult(java.util.List<SequenceEntry> sequenceEntries);

	/**
	 * Creates a new sequence entry with the primary key. Does not add the sequence entry to the database.
	 *
	 * @param sequenceEntryId the primary key for the new sequence entry
	 * @return the new sequence entry
	 */
	public SequenceEntry create(long sequenceEntryId);

	/**
	 * Removes the sequence entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param sequenceEntryId the primary key of the sequence entry
	 * @return the sequence entry that was removed
	 * @throws NoSuchSequenceEntryException if a sequence entry with the primary key could not be found
	 */
	public SequenceEntry remove(long sequenceEntryId)
		throws NoSuchSequenceEntryException;

	public SequenceEntry updateImpl(SequenceEntry sequenceEntry);

	/**
	 * Returns the sequence entry with the primary key or throws a <code>NoSuchSequenceEntryException</code> if it could not be found.
	 *
	 * @param sequenceEntryId the primary key of the sequence entry
	 * @return the sequence entry
	 * @throws NoSuchSequenceEntryException if a sequence entry with the primary key could not be found
	 */
	public SequenceEntry findByPrimaryKey(long sequenceEntryId)
		throws NoSuchSequenceEntryException;

	/**
	 * Returns the sequence entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param sequenceEntryId the primary key of the sequence entry
	 * @return the sequence entry, or <code>null</code> if a sequence entry with the primary key could not be found
	 */
	public SequenceEntry fetchByPrimaryKey(long sequenceEntryId);

	/**
	 * Returns all the sequence entries.
	 *
	 * @return the sequence entries
	 */
	public java.util.List<SequenceEntry> findAll();

	/**
	 * Returns a range of all the sequence entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SequenceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sequence entries
	 * @param end the upper bound of the range of sequence entries (not inclusive)
	 * @return the range of sequence entries
	 */
	public java.util.List<SequenceEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the sequence entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SequenceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sequence entries
	 * @param end the upper bound of the range of sequence entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of sequence entries
	 */
	public java.util.List<SequenceEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SequenceEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the sequence entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SequenceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sequence entries
	 * @param end the upper bound of the range of sequence entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of sequence entries
	 */
	public java.util.List<SequenceEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SequenceEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the sequence entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of sequence entries.
	 *
	 * @return the number of sequence entries
	 */
	public int countAll();

}
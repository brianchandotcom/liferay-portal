/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.view.state.service.persistence;

import com.liferay.frontend.view.state.exception.NoSuchCustomEntryException;
import com.liferay.frontend.view.state.model.FVSCustomEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the fvs custom entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FVSCustomEntryUtil
 * @generated
 */
@ProviderType
public interface FVSCustomEntryPersistence
	extends BasePersistence<FVSCustomEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link FVSCustomEntryUtil} to access the fvs custom entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the fvs custom entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching fvs custom entries
	 */
	public java.util.List<FVSCustomEntry> findByUuid(String uuid);

	/**
	 * Returns a range of all the fvs custom entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSCustomEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fvs custom entries
	 * @param end the upper bound of the range of fvs custom entries (not inclusive)
	 * @return the range of matching fvs custom entries
	 */
	public java.util.List<FVSCustomEntry> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the fvs custom entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSCustomEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fvs custom entries
	 * @param end the upper bound of the range of fvs custom entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fvs custom entries
	 */
	public java.util.List<FVSCustomEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FVSCustomEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fvs custom entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSCustomEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fvs custom entries
	 * @param end the upper bound of the range of fvs custom entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fvs custom entries
	 */
	public java.util.List<FVSCustomEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FVSCustomEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fvs custom entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs custom entry
	 * @throws NoSuchCustomEntryException if a matching fvs custom entry could not be found
	 */
	public FVSCustomEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<FVSCustomEntry>
				orderByComparator)
		throws NoSuchCustomEntryException;

	/**
	 * Returns the first fvs custom entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs custom entry, or <code>null</code> if a matching fvs custom entry could not be found
	 */
	public FVSCustomEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<FVSCustomEntry>
			orderByComparator);

	/**
	 * Returns the last fvs custom entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs custom entry
	 * @throws NoSuchCustomEntryException if a matching fvs custom entry could not be found
	 */
	public FVSCustomEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<FVSCustomEntry>
				orderByComparator)
		throws NoSuchCustomEntryException;

	/**
	 * Returns the last fvs custom entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs custom entry, or <code>null</code> if a matching fvs custom entry could not be found
	 */
	public FVSCustomEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<FVSCustomEntry>
			orderByComparator);

	/**
	 * Returns the fvs custom entries before and after the current fvs custom entry in the ordered set where uuid = &#63;.
	 *
	 * @param fvsCustomEntryId the primary key of the current fvs custom entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fvs custom entry
	 * @throws NoSuchCustomEntryException if a fvs custom entry with the primary key could not be found
	 */
	public FVSCustomEntry[] findByUuid_PrevAndNext(
			long fvsCustomEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<FVSCustomEntry>
				orderByComparator)
		throws NoSuchCustomEntryException;

	/**
	 * Removes all the fvs custom entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of fvs custom entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching fvs custom entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the fvs custom entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching fvs custom entries
	 */
	public java.util.List<FVSCustomEntry> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the fvs custom entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSCustomEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fvs custom entries
	 * @param end the upper bound of the range of fvs custom entries (not inclusive)
	 * @return the range of matching fvs custom entries
	 */
	public java.util.List<FVSCustomEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the fvs custom entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSCustomEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fvs custom entries
	 * @param end the upper bound of the range of fvs custom entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fvs custom entries
	 */
	public java.util.List<FVSCustomEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FVSCustomEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fvs custom entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSCustomEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fvs custom entries
	 * @param end the upper bound of the range of fvs custom entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fvs custom entries
	 */
	public java.util.List<FVSCustomEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FVSCustomEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fvs custom entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs custom entry
	 * @throws NoSuchCustomEntryException if a matching fvs custom entry could not be found
	 */
	public FVSCustomEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<FVSCustomEntry>
				orderByComparator)
		throws NoSuchCustomEntryException;

	/**
	 * Returns the first fvs custom entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs custom entry, or <code>null</code> if a matching fvs custom entry could not be found
	 */
	public FVSCustomEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<FVSCustomEntry>
			orderByComparator);

	/**
	 * Returns the last fvs custom entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs custom entry
	 * @throws NoSuchCustomEntryException if a matching fvs custom entry could not be found
	 */
	public FVSCustomEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<FVSCustomEntry>
				orderByComparator)
		throws NoSuchCustomEntryException;

	/**
	 * Returns the last fvs custom entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs custom entry, or <code>null</code> if a matching fvs custom entry could not be found
	 */
	public FVSCustomEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<FVSCustomEntry>
			orderByComparator);

	/**
	 * Returns the fvs custom entries before and after the current fvs custom entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param fvsCustomEntryId the primary key of the current fvs custom entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fvs custom entry
	 * @throws NoSuchCustomEntryException if a fvs custom entry with the primary key could not be found
	 */
	public FVSCustomEntry[] findByUuid_C_PrevAndNext(
			long fvsCustomEntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<FVSCustomEntry>
				orderByComparator)
		throws NoSuchCustomEntryException;

	/**
	 * Removes all the fvs custom entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of fvs custom entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching fvs custom entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Caches the fvs custom entry in the entity cache if it is enabled.
	 *
	 * @param fvsCustomEntry the fvs custom entry
	 */
	public void cacheResult(FVSCustomEntry fvsCustomEntry);

	/**
	 * Caches the fvs custom entries in the entity cache if it is enabled.
	 *
	 * @param fvsCustomEntries the fvs custom entries
	 */
	public void cacheResult(java.util.List<FVSCustomEntry> fvsCustomEntries);

	/**
	 * Creates a new fvs custom entry with the primary key. Does not add the fvs custom entry to the database.
	 *
	 * @param fvsCustomEntryId the primary key for the new fvs custom entry
	 * @return the new fvs custom entry
	 */
	public FVSCustomEntry create(long fvsCustomEntryId);

	/**
	 * Removes the fvs custom entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fvsCustomEntryId the primary key of the fvs custom entry
	 * @return the fvs custom entry that was removed
	 * @throws NoSuchCustomEntryException if a fvs custom entry with the primary key could not be found
	 */
	public FVSCustomEntry remove(long fvsCustomEntryId)
		throws NoSuchCustomEntryException;

	public FVSCustomEntry updateImpl(FVSCustomEntry fvsCustomEntry);

	/**
	 * Returns the fvs custom entry with the primary key or throws a <code>NoSuchCustomEntryException</code> if it could not be found.
	 *
	 * @param fvsCustomEntryId the primary key of the fvs custom entry
	 * @return the fvs custom entry
	 * @throws NoSuchCustomEntryException if a fvs custom entry with the primary key could not be found
	 */
	public FVSCustomEntry findByPrimaryKey(long fvsCustomEntryId)
		throws NoSuchCustomEntryException;

	/**
	 * Returns the fvs custom entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fvsCustomEntryId the primary key of the fvs custom entry
	 * @return the fvs custom entry, or <code>null</code> if a fvs custom entry with the primary key could not be found
	 */
	public FVSCustomEntry fetchByPrimaryKey(long fvsCustomEntryId);

	/**
	 * Returns all the fvs custom entries.
	 *
	 * @return the fvs custom entries
	 */
	public java.util.List<FVSCustomEntry> findAll();

	/**
	 * Returns a range of all the fvs custom entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSCustomEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs custom entries
	 * @param end the upper bound of the range of fvs custom entries (not inclusive)
	 * @return the range of fvs custom entries
	 */
	public java.util.List<FVSCustomEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the fvs custom entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSCustomEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs custom entries
	 * @param end the upper bound of the range of fvs custom entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of fvs custom entries
	 */
	public java.util.List<FVSCustomEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FVSCustomEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fvs custom entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSCustomEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs custom entries
	 * @param end the upper bound of the range of fvs custom entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of fvs custom entries
	 */
	public java.util.List<FVSCustomEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FVSCustomEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the fvs custom entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of fvs custom entries.
	 *
	 * @return the number of fvs custom entries
	 */
	public int countAll();

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.view.state.service.persistence;

import com.liferay.frontend.view.state.exception.NoSuchActiveEntryException;
import com.liferay.frontend.view.state.model.FVSActiveEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the fvs active entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FVSActiveEntryUtil
 * @generated
 */
@ProviderType
public interface FVSActiveEntryPersistence
	extends BasePersistence<FVSActiveEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link FVSActiveEntryUtil} to access the fvs active entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the fvs active entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching fvs active entries
	 */
	public java.util.List<FVSActiveEntry> findByUuid(String uuid);

	/**
	 * Returns a range of all the fvs active entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSActiveEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fvs active entries
	 * @param end the upper bound of the range of fvs active entries (not inclusive)
	 * @return the range of matching fvs active entries
	 */
	public java.util.List<FVSActiveEntry> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the fvs active entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSActiveEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fvs active entries
	 * @param end the upper bound of the range of fvs active entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fvs active entries
	 */
	public java.util.List<FVSActiveEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FVSActiveEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fvs active entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSActiveEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fvs active entries
	 * @param end the upper bound of the range of fvs active entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fvs active entries
	 */
	public java.util.List<FVSActiveEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FVSActiveEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fvs active entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs active entry
	 * @throws NoSuchActiveEntryException if a matching fvs active entry could not be found
	 */
	public FVSActiveEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<FVSActiveEntry>
				orderByComparator)
		throws NoSuchActiveEntryException;

	/**
	 * Returns the first fvs active entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs active entry, or <code>null</code> if a matching fvs active entry could not be found
	 */
	public FVSActiveEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<FVSActiveEntry>
			orderByComparator);

	/**
	 * Returns the last fvs active entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs active entry
	 * @throws NoSuchActiveEntryException if a matching fvs active entry could not be found
	 */
	public FVSActiveEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<FVSActiveEntry>
				orderByComparator)
		throws NoSuchActiveEntryException;

	/**
	 * Returns the last fvs active entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs active entry, or <code>null</code> if a matching fvs active entry could not be found
	 */
	public FVSActiveEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<FVSActiveEntry>
			orderByComparator);

	/**
	 * Returns the fvs active entries before and after the current fvs active entry in the ordered set where uuid = &#63;.
	 *
	 * @param fvsActiveEntryId the primary key of the current fvs active entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fvs active entry
	 * @throws NoSuchActiveEntryException if a fvs active entry with the primary key could not be found
	 */
	public FVSActiveEntry[] findByUuid_PrevAndNext(
			long fvsActiveEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<FVSActiveEntry>
				orderByComparator)
		throws NoSuchActiveEntryException;

	/**
	 * Removes all the fvs active entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of fvs active entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching fvs active entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the fvs active entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching fvs active entries
	 */
	public java.util.List<FVSActiveEntry> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the fvs active entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSActiveEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fvs active entries
	 * @param end the upper bound of the range of fvs active entries (not inclusive)
	 * @return the range of matching fvs active entries
	 */
	public java.util.List<FVSActiveEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the fvs active entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSActiveEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fvs active entries
	 * @param end the upper bound of the range of fvs active entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fvs active entries
	 */
	public java.util.List<FVSActiveEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FVSActiveEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fvs active entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSActiveEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fvs active entries
	 * @param end the upper bound of the range of fvs active entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fvs active entries
	 */
	public java.util.List<FVSActiveEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FVSActiveEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fvs active entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs active entry
	 * @throws NoSuchActiveEntryException if a matching fvs active entry could not be found
	 */
	public FVSActiveEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<FVSActiveEntry>
				orderByComparator)
		throws NoSuchActiveEntryException;

	/**
	 * Returns the first fvs active entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs active entry, or <code>null</code> if a matching fvs active entry could not be found
	 */
	public FVSActiveEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<FVSActiveEntry>
			orderByComparator);

	/**
	 * Returns the last fvs active entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs active entry
	 * @throws NoSuchActiveEntryException if a matching fvs active entry could not be found
	 */
	public FVSActiveEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<FVSActiveEntry>
				orderByComparator)
		throws NoSuchActiveEntryException;

	/**
	 * Returns the last fvs active entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs active entry, or <code>null</code> if a matching fvs active entry could not be found
	 */
	public FVSActiveEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<FVSActiveEntry>
			orderByComparator);

	/**
	 * Returns the fvs active entries before and after the current fvs active entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param fvsActiveEntryId the primary key of the current fvs active entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fvs active entry
	 * @throws NoSuchActiveEntryException if a fvs active entry with the primary key could not be found
	 */
	public FVSActiveEntry[] findByUuid_C_PrevAndNext(
			long fvsActiveEntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<FVSActiveEntry>
				orderByComparator)
		throws NoSuchActiveEntryException;

	/**
	 * Removes all the fvs active entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of fvs active entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching fvs active entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns the fvs active entry where userId = &#63; and clayDataSetDisplayId = &#63; and plid = &#63; and portletId = &#63; or throws a <code>NoSuchActiveEntryException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param clayDataSetDisplayId the clay data set display ID
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the matching fvs active entry
	 * @throws NoSuchActiveEntryException if a matching fvs active entry could not be found
	 */
	public FVSActiveEntry findByU_CDSDI_P_P(
			long userId, String clayDataSetDisplayId, long plid,
			String portletId)
		throws NoSuchActiveEntryException;

	/**
	 * Returns the fvs active entry where userId = &#63; and clayDataSetDisplayId = &#63; and plid = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param clayDataSetDisplayId the clay data set display ID
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the matching fvs active entry, or <code>null</code> if a matching fvs active entry could not be found
	 */
	public FVSActiveEntry fetchByU_CDSDI_P_P(
		long userId, String clayDataSetDisplayId, long plid, String portletId);

	/**
	 * Returns the fvs active entry where userId = &#63; and clayDataSetDisplayId = &#63; and plid = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param clayDataSetDisplayId the clay data set display ID
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching fvs active entry, or <code>null</code> if a matching fvs active entry could not be found
	 */
	public FVSActiveEntry fetchByU_CDSDI_P_P(
		long userId, String clayDataSetDisplayId, long plid, String portletId,
		boolean useFinderCache);

	/**
	 * Removes the fvs active entry where userId = &#63; and clayDataSetDisplayId = &#63; and plid = &#63; and portletId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param clayDataSetDisplayId the clay data set display ID
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the fvs active entry that was removed
	 */
	public FVSActiveEntry removeByU_CDSDI_P_P(
			long userId, String clayDataSetDisplayId, long plid,
			String portletId)
		throws NoSuchActiveEntryException;

	/**
	 * Returns the number of fvs active entries where userId = &#63; and clayDataSetDisplayId = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param userId the user ID
	 * @param clayDataSetDisplayId the clay data set display ID
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the number of matching fvs active entries
	 */
	public int countByU_CDSDI_P_P(
		long userId, String clayDataSetDisplayId, long plid, String portletId);

	/**
	 * Caches the fvs active entry in the entity cache if it is enabled.
	 *
	 * @param fvsActiveEntry the fvs active entry
	 */
	public void cacheResult(FVSActiveEntry fvsActiveEntry);

	/**
	 * Caches the fvs active entries in the entity cache if it is enabled.
	 *
	 * @param fvsActiveEntries the fvs active entries
	 */
	public void cacheResult(java.util.List<FVSActiveEntry> fvsActiveEntries);

	/**
	 * Creates a new fvs active entry with the primary key. Does not add the fvs active entry to the database.
	 *
	 * @param fvsActiveEntryId the primary key for the new fvs active entry
	 * @return the new fvs active entry
	 */
	public FVSActiveEntry create(long fvsActiveEntryId);

	/**
	 * Removes the fvs active entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fvsActiveEntryId the primary key of the fvs active entry
	 * @return the fvs active entry that was removed
	 * @throws NoSuchActiveEntryException if a fvs active entry with the primary key could not be found
	 */
	public FVSActiveEntry remove(long fvsActiveEntryId)
		throws NoSuchActiveEntryException;

	public FVSActiveEntry updateImpl(FVSActiveEntry fvsActiveEntry);

	/**
	 * Returns the fvs active entry with the primary key or throws a <code>NoSuchActiveEntryException</code> if it could not be found.
	 *
	 * @param fvsActiveEntryId the primary key of the fvs active entry
	 * @return the fvs active entry
	 * @throws NoSuchActiveEntryException if a fvs active entry with the primary key could not be found
	 */
	public FVSActiveEntry findByPrimaryKey(long fvsActiveEntryId)
		throws NoSuchActiveEntryException;

	/**
	 * Returns the fvs active entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fvsActiveEntryId the primary key of the fvs active entry
	 * @return the fvs active entry, or <code>null</code> if a fvs active entry with the primary key could not be found
	 */
	public FVSActiveEntry fetchByPrimaryKey(long fvsActiveEntryId);

	/**
	 * Returns all the fvs active entries.
	 *
	 * @return the fvs active entries
	 */
	public java.util.List<FVSActiveEntry> findAll();

	/**
	 * Returns a range of all the fvs active entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSActiveEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs active entries
	 * @param end the upper bound of the range of fvs active entries (not inclusive)
	 * @return the range of fvs active entries
	 */
	public java.util.List<FVSActiveEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the fvs active entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSActiveEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs active entries
	 * @param end the upper bound of the range of fvs active entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of fvs active entries
	 */
	public java.util.List<FVSActiveEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FVSActiveEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fvs active entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSActiveEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs active entries
	 * @param end the upper bound of the range of fvs active entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of fvs active entries
	 */
	public java.util.List<FVSActiveEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FVSActiveEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the fvs active entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of fvs active entries.
	 *
	 * @return the number of fvs active entries
	 */
	public int countAll();

}
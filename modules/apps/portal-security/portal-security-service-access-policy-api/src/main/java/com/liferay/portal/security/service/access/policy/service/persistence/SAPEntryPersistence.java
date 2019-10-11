/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.security.service.access.policy.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.security.service.access.policy.exception.NoSuchEntryException;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the sap entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SAPEntryUtil
 * @generated
 */
@ProviderType
public interface SAPEntryPersistence extends BasePersistence<SAPEntry> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SAPEntryUtil} to access the sap entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the sap entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching sap entries
	 */
	public java.util.List<SAPEntry> findByUuid(String uuid);

	/**
	 * Returns a range of all the sap entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @return the range of matching sap entries
	 */
	public java.util.List<SAPEntry> findByUuid(String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the sap entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sap entries
	 */
	public java.util.List<SAPEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SAPEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the sap entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sap entries
	 */
	public java.util.List<SAPEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SAPEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first sap entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sap entry
	 * @throws NoSuchEntryException if a matching sap entry could not be found
	 */
	public SAPEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<SAPEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first sap entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sap entry, or <code>null</code> if a matching sap entry could not be found
	 */
	public SAPEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<SAPEntry>
			orderByComparator);

	/**
	 * Returns the last sap entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sap entry
	 * @throws NoSuchEntryException if a matching sap entry could not be found
	 */
	public SAPEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<SAPEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last sap entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sap entry, or <code>null</code> if a matching sap entry could not be found
	 */
	public SAPEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<SAPEntry>
			orderByComparator);

	/**
	 * Returns the sap entries before and after the current sap entry in the ordered set where uuid = &#63;.
	 *
	 * @param sapEntryId the primary key of the current sap entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sap entry
	 * @throws NoSuchEntryException if a sap entry with the primary key could not be found
	 */
	public SAPEntry[] findByUuid_PrevAndNext(
			long sapEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<SAPEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns all the sap entries that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching sap entries that the user has permission to view
	 */
	public java.util.List<SAPEntry> filterFindByUuid(String uuid);

	/**
	 * Returns a range of all the sap entries that the user has permission to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @return the range of matching sap entries that the user has permission to view
	 */
	public java.util.List<SAPEntry> filterFindByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the sap entries that the user has permissions to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sap entries that the user has permission to view
	 */
	public java.util.List<SAPEntry> filterFindByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SAPEntry>
			orderByComparator);

	/**
	 * Returns the sap entries before and after the current sap entry in the ordered set of sap entries that the user has permission to view where uuid = &#63;.
	 *
	 * @param sapEntryId the primary key of the current sap entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sap entry
	 * @throws NoSuchEntryException if a sap entry with the primary key could not be found
	 */
	public SAPEntry[] filterFindByUuid_PrevAndNext(
			long sapEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<SAPEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the sap entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of sap entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching sap entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the number of sap entries that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching sap entries that the user has permission to view
	 */
	public int filterCountByUuid(String uuid);

	/**
	 * Returns all the sap entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching sap entries
	 */
	public java.util.List<SAPEntry> findByUuid_C(String uuid, long companyId);

	/**
	 * Returns a range of all the sap entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @return the range of matching sap entries
	 */
	public java.util.List<SAPEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the sap entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sap entries
	 */
	public java.util.List<SAPEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SAPEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the sap entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sap entries
	 */
	public java.util.List<SAPEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SAPEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first sap entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sap entry
	 * @throws NoSuchEntryException if a matching sap entry could not be found
	 */
	public SAPEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SAPEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first sap entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sap entry, or <code>null</code> if a matching sap entry could not be found
	 */
	public SAPEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SAPEntry>
			orderByComparator);

	/**
	 * Returns the last sap entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sap entry
	 * @throws NoSuchEntryException if a matching sap entry could not be found
	 */
	public SAPEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SAPEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last sap entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sap entry, or <code>null</code> if a matching sap entry could not be found
	 */
	public SAPEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SAPEntry>
			orderByComparator);

	/**
	 * Returns the sap entries before and after the current sap entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param sapEntryId the primary key of the current sap entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sap entry
	 * @throws NoSuchEntryException if a sap entry with the primary key could not be found
	 */
	public SAPEntry[] findByUuid_C_PrevAndNext(
			long sapEntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SAPEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns all the sap entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching sap entries that the user has permission to view
	 */
	public java.util.List<SAPEntry> filterFindByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the sap entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @return the range of matching sap entries that the user has permission to view
	 */
	public java.util.List<SAPEntry> filterFindByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the sap entries that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sap entries that the user has permission to view
	 */
	public java.util.List<SAPEntry> filterFindByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SAPEntry>
			orderByComparator);

	/**
	 * Returns the sap entries before and after the current sap entry in the ordered set of sap entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param sapEntryId the primary key of the current sap entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sap entry
	 * @throws NoSuchEntryException if a sap entry with the primary key could not be found
	 */
	public SAPEntry[] filterFindByUuid_C_PrevAndNext(
			long sapEntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SAPEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the sap entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of sap entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching sap entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of sap entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching sap entries that the user has permission to view
	 */
	public int filterCountByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the sap entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching sap entries
	 */
	public java.util.List<SAPEntry> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the sap entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @return the range of matching sap entries
	 */
	public java.util.List<SAPEntry> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the sap entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sap entries
	 */
	public java.util.List<SAPEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SAPEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the sap entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sap entries
	 */
	public java.util.List<SAPEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SAPEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first sap entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sap entry
	 * @throws NoSuchEntryException if a matching sap entry could not be found
	 */
	public SAPEntry findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SAPEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first sap entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sap entry, or <code>null</code> if a matching sap entry could not be found
	 */
	public SAPEntry fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SAPEntry>
			orderByComparator);

	/**
	 * Returns the last sap entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sap entry
	 * @throws NoSuchEntryException if a matching sap entry could not be found
	 */
	public SAPEntry findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SAPEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last sap entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sap entry, or <code>null</code> if a matching sap entry could not be found
	 */
	public SAPEntry fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SAPEntry>
			orderByComparator);

	/**
	 * Returns the sap entries before and after the current sap entry in the ordered set where companyId = &#63;.
	 *
	 * @param sapEntryId the primary key of the current sap entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sap entry
	 * @throws NoSuchEntryException if a sap entry with the primary key could not be found
	 */
	public SAPEntry[] findByCompanyId_PrevAndNext(
			long sapEntryId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SAPEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns all the sap entries that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching sap entries that the user has permission to view
	 */
	public java.util.List<SAPEntry> filterFindByCompanyId(long companyId);

	/**
	 * Returns a range of all the sap entries that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @return the range of matching sap entries that the user has permission to view
	 */
	public java.util.List<SAPEntry> filterFindByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the sap entries that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sap entries that the user has permission to view
	 */
	public java.util.List<SAPEntry> filterFindByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SAPEntry>
			orderByComparator);

	/**
	 * Returns the sap entries before and after the current sap entry in the ordered set of sap entries that the user has permission to view where companyId = &#63;.
	 *
	 * @param sapEntryId the primary key of the current sap entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sap entry
	 * @throws NoSuchEntryException if a sap entry with the primary key could not be found
	 */
	public SAPEntry[] filterFindByCompanyId_PrevAndNext(
			long sapEntryId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SAPEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the sap entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of sap entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching sap entries
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns the number of sap entries that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching sap entries that the user has permission to view
	 */
	public int filterCountByCompanyId(long companyId);

	/**
	 * Returns all the sap entries where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default sap entry
	 * @return the matching sap entries
	 */
	public java.util.List<SAPEntry> findByC_D(
		long companyId, boolean defaultSAPEntry);

	/**
	 * Returns a range of all the sap entries where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default sap entry
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @return the range of matching sap entries
	 */
	public java.util.List<SAPEntry> findByC_D(
		long companyId, boolean defaultSAPEntry, int start, int end);

	/**
	 * Returns an ordered range of all the sap entries where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default sap entry
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sap entries
	 */
	public java.util.List<SAPEntry> findByC_D(
		long companyId, boolean defaultSAPEntry, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SAPEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the sap entries where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default sap entry
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sap entries
	 */
	public java.util.List<SAPEntry> findByC_D(
		long companyId, boolean defaultSAPEntry, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SAPEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first sap entry in the ordered set where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default sap entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sap entry
	 * @throws NoSuchEntryException if a matching sap entry could not be found
	 */
	public SAPEntry findByC_D_First(
			long companyId, boolean defaultSAPEntry,
			com.liferay.portal.kernel.util.OrderByComparator<SAPEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first sap entry in the ordered set where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default sap entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sap entry, or <code>null</code> if a matching sap entry could not be found
	 */
	public SAPEntry fetchByC_D_First(
		long companyId, boolean defaultSAPEntry,
		com.liferay.portal.kernel.util.OrderByComparator<SAPEntry>
			orderByComparator);

	/**
	 * Returns the last sap entry in the ordered set where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default sap entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sap entry
	 * @throws NoSuchEntryException if a matching sap entry could not be found
	 */
	public SAPEntry findByC_D_Last(
			long companyId, boolean defaultSAPEntry,
			com.liferay.portal.kernel.util.OrderByComparator<SAPEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last sap entry in the ordered set where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default sap entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sap entry, or <code>null</code> if a matching sap entry could not be found
	 */
	public SAPEntry fetchByC_D_Last(
		long companyId, boolean defaultSAPEntry,
		com.liferay.portal.kernel.util.OrderByComparator<SAPEntry>
			orderByComparator);

	/**
	 * Returns the sap entries before and after the current sap entry in the ordered set where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * @param sapEntryId the primary key of the current sap entry
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default sap entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sap entry
	 * @throws NoSuchEntryException if a sap entry with the primary key could not be found
	 */
	public SAPEntry[] findByC_D_PrevAndNext(
			long sapEntryId, long companyId, boolean defaultSAPEntry,
			com.liferay.portal.kernel.util.OrderByComparator<SAPEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns all the sap entries that the user has permission to view where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default sap entry
	 * @return the matching sap entries that the user has permission to view
	 */
	public java.util.List<SAPEntry> filterFindByC_D(
		long companyId, boolean defaultSAPEntry);

	/**
	 * Returns a range of all the sap entries that the user has permission to view where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default sap entry
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @return the range of matching sap entries that the user has permission to view
	 */
	public java.util.List<SAPEntry> filterFindByC_D(
		long companyId, boolean defaultSAPEntry, int start, int end);

	/**
	 * Returns an ordered range of all the sap entries that the user has permissions to view where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default sap entry
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sap entries that the user has permission to view
	 */
	public java.util.List<SAPEntry> filterFindByC_D(
		long companyId, boolean defaultSAPEntry, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SAPEntry>
			orderByComparator);

	/**
	 * Returns the sap entries before and after the current sap entry in the ordered set of sap entries that the user has permission to view where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * @param sapEntryId the primary key of the current sap entry
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default sap entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sap entry
	 * @throws NoSuchEntryException if a sap entry with the primary key could not be found
	 */
	public SAPEntry[] filterFindByC_D_PrevAndNext(
			long sapEntryId, long companyId, boolean defaultSAPEntry,
			com.liferay.portal.kernel.util.OrderByComparator<SAPEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the sap entries where companyId = &#63; and defaultSAPEntry = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default sap entry
	 */
	public void removeByC_D(long companyId, boolean defaultSAPEntry);

	/**
	 * Returns the number of sap entries where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default sap entry
	 * @return the number of matching sap entries
	 */
	public int countByC_D(long companyId, boolean defaultSAPEntry);

	/**
	 * Returns the number of sap entries that the user has permission to view where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default sap entry
	 * @return the number of matching sap entries that the user has permission to view
	 */
	public int filterCountByC_D(long companyId, boolean defaultSAPEntry);

	/**
	 * Returns the sap entry where companyId = &#63; and name = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching sap entry
	 * @throws NoSuchEntryException if a matching sap entry could not be found
	 */
	public SAPEntry findByC_N(long companyId, String name)
		throws NoSuchEntryException;

	/**
	 * Returns the sap entry where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching sap entry, or <code>null</code> if a matching sap entry could not be found
	 */
	public SAPEntry fetchByC_N(long companyId, String name);

	/**
	 * Returns the sap entry where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching sap entry, or <code>null</code> if a matching sap entry could not be found
	 */
	public SAPEntry fetchByC_N(
		long companyId, String name, boolean useFinderCache);

	/**
	 * Removes the sap entry where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the sap entry that was removed
	 */
	public SAPEntry removeByC_N(long companyId, String name)
		throws NoSuchEntryException;

	/**
	 * Returns the number of sap entries where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching sap entries
	 */
	public int countByC_N(long companyId, String name);

	/**
	 * Caches the sap entry in the entity cache if it is enabled.
	 *
	 * @param sapEntry the sap entry
	 */
	public void cacheResult(SAPEntry sapEntry);

	/**
	 * Caches the sap entries in the entity cache if it is enabled.
	 *
	 * @param sapEntries the sap entries
	 */
	public void cacheResult(java.util.List<SAPEntry> sapEntries);

	/**
	 * Creates a new sap entry with the primary key. Does not add the sap entry to the database.
	 *
	 * @param sapEntryId the primary key for the new sap entry
	 * @return the new sap entry
	 */
	public SAPEntry create(long sapEntryId);

	/**
	 * Removes the sap entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param sapEntryId the primary key of the sap entry
	 * @return the sap entry that was removed
	 * @throws NoSuchEntryException if a sap entry with the primary key could not be found
	 */
	public SAPEntry remove(long sapEntryId) throws NoSuchEntryException;

	public SAPEntry updateImpl(SAPEntry sapEntry);

	/**
	 * Returns the sap entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param sapEntryId the primary key of the sap entry
	 * @return the sap entry
	 * @throws NoSuchEntryException if a sap entry with the primary key could not be found
	 */
	public SAPEntry findByPrimaryKey(long sapEntryId)
		throws NoSuchEntryException;

	/**
	 * Returns the sap entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param sapEntryId the primary key of the sap entry
	 * @return the sap entry, or <code>null</code> if a sap entry with the primary key could not be found
	 */
	public SAPEntry fetchByPrimaryKey(long sapEntryId);

	/**
	 * Returns all the sap entries.
	 *
	 * @return the sap entries
	 */
	public java.util.List<SAPEntry> findAll();

	/**
	 * Returns a range of all the sap entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @return the range of sap entries
	 */
	public java.util.List<SAPEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the sap entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of sap entries
	 */
	public java.util.List<SAPEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SAPEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the sap entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of sap entries
	 */
	public java.util.List<SAPEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SAPEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the sap entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of sap entries.
	 *
	 * @return the number of sap entries
	 */
	public int countAll();

}
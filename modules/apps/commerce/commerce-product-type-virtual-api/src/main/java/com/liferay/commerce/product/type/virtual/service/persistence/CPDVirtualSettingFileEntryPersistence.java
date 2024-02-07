/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.service.persistence;

import com.liferay.commerce.product.type.virtual.exception.NoSuchCPDVirtualSettingFileEntryException;
import com.liferay.commerce.product.type.virtual.model.CPDVirtualSettingFileEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the cpd virtual setting file entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPDVirtualSettingFileEntryUtil
 * @generated
 */
@ProviderType
public interface CPDVirtualSettingFileEntryPersistence
	extends BasePersistence<CPDVirtualSettingFileEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPDVirtualSettingFileEntryUtil} to access the cpd virtual setting file entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the cpd virtual setting file entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cpd virtual setting file entries
	 */
	public java.util.List<CPDVirtualSettingFileEntry> findByUuid(String uuid);

	/**
	 * Returns a range of all the cpd virtual setting file entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDVirtualSettingFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cpd virtual setting file entries
	 * @param end the upper bound of the range of cpd virtual setting file entries (not inclusive)
	 * @return the range of matching cpd virtual setting file entries
	 */
	public java.util.List<CPDVirtualSettingFileEntry> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the cpd virtual setting file entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDVirtualSettingFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cpd virtual setting file entries
	 * @param end the upper bound of the range of cpd virtual setting file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cpd virtual setting file entries
	 */
	public java.util.List<CPDVirtualSettingFileEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPDVirtualSettingFileEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the cpd virtual setting file entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDVirtualSettingFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cpd virtual setting file entries
	 * @param end the upper bound of the range of cpd virtual setting file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cpd virtual setting file entries
	 */
	public java.util.List<CPDVirtualSettingFileEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPDVirtualSettingFileEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cpd virtual setting file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cpd virtual setting file entry
	 * @throws NoSuchCPDVirtualSettingFileEntryException if a matching cpd virtual setting file entry could not be found
	 */
	public CPDVirtualSettingFileEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPDVirtualSettingFileEntry> orderByComparator)
		throws NoSuchCPDVirtualSettingFileEntryException;

	/**
	 * Returns the first cpd virtual setting file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cpd virtual setting file entry, or <code>null</code> if a matching cpd virtual setting file entry could not be found
	 */
	public CPDVirtualSettingFileEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPDVirtualSettingFileEntry> orderByComparator);

	/**
	 * Returns the last cpd virtual setting file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cpd virtual setting file entry
	 * @throws NoSuchCPDVirtualSettingFileEntryException if a matching cpd virtual setting file entry could not be found
	 */
	public CPDVirtualSettingFileEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPDVirtualSettingFileEntry> orderByComparator)
		throws NoSuchCPDVirtualSettingFileEntryException;

	/**
	 * Returns the last cpd virtual setting file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cpd virtual setting file entry, or <code>null</code> if a matching cpd virtual setting file entry could not be found
	 */
	public CPDVirtualSettingFileEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPDVirtualSettingFileEntry> orderByComparator);

	/**
	 * Returns the cpd virtual setting file entries before and after the current cpd virtual setting file entry in the ordered set where uuid = &#63;.
	 *
	 * @param CPDefinitionVirtualSettingFileEntryId the primary key of the current cpd virtual setting file entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cpd virtual setting file entry
	 * @throws NoSuchCPDVirtualSettingFileEntryException if a cpd virtual setting file entry with the primary key could not be found
	 */
	public CPDVirtualSettingFileEntry[] findByUuid_PrevAndNext(
			long CPDefinitionVirtualSettingFileEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPDVirtualSettingFileEntry> orderByComparator)
		throws NoSuchCPDVirtualSettingFileEntryException;

	/**
	 * Removes all the cpd virtual setting file entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of cpd virtual setting file entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cpd virtual setting file entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the cpd virtual setting file entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchCPDVirtualSettingFileEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cpd virtual setting file entry
	 * @throws NoSuchCPDVirtualSettingFileEntryException if a matching cpd virtual setting file entry could not be found
	 */
	public CPDVirtualSettingFileEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchCPDVirtualSettingFileEntryException;

	/**
	 * Returns the cpd virtual setting file entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cpd virtual setting file entry, or <code>null</code> if a matching cpd virtual setting file entry could not be found
	 */
	public CPDVirtualSettingFileEntry fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the cpd virtual setting file entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cpd virtual setting file entry, or <code>null</code> if a matching cpd virtual setting file entry could not be found
	 */
	public CPDVirtualSettingFileEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the cpd virtual setting file entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the cpd virtual setting file entry that was removed
	 */
	public CPDVirtualSettingFileEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchCPDVirtualSettingFileEntryException;

	/**
	 * Returns the number of cpd virtual setting file entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching cpd virtual setting file entries
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the cpd virtual setting file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cpd virtual setting file entries
	 */
	public java.util.List<CPDVirtualSettingFileEntry> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the cpd virtual setting file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDVirtualSettingFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cpd virtual setting file entries
	 * @param end the upper bound of the range of cpd virtual setting file entries (not inclusive)
	 * @return the range of matching cpd virtual setting file entries
	 */
	public java.util.List<CPDVirtualSettingFileEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the cpd virtual setting file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDVirtualSettingFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cpd virtual setting file entries
	 * @param end the upper bound of the range of cpd virtual setting file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cpd virtual setting file entries
	 */
	public java.util.List<CPDVirtualSettingFileEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPDVirtualSettingFileEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the cpd virtual setting file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDVirtualSettingFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cpd virtual setting file entries
	 * @param end the upper bound of the range of cpd virtual setting file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cpd virtual setting file entries
	 */
	public java.util.List<CPDVirtualSettingFileEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPDVirtualSettingFileEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cpd virtual setting file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cpd virtual setting file entry
	 * @throws NoSuchCPDVirtualSettingFileEntryException if a matching cpd virtual setting file entry could not be found
	 */
	public CPDVirtualSettingFileEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPDVirtualSettingFileEntry> orderByComparator)
		throws NoSuchCPDVirtualSettingFileEntryException;

	/**
	 * Returns the first cpd virtual setting file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cpd virtual setting file entry, or <code>null</code> if a matching cpd virtual setting file entry could not be found
	 */
	public CPDVirtualSettingFileEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPDVirtualSettingFileEntry> orderByComparator);

	/**
	 * Returns the last cpd virtual setting file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cpd virtual setting file entry
	 * @throws NoSuchCPDVirtualSettingFileEntryException if a matching cpd virtual setting file entry could not be found
	 */
	public CPDVirtualSettingFileEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPDVirtualSettingFileEntry> orderByComparator)
		throws NoSuchCPDVirtualSettingFileEntryException;

	/**
	 * Returns the last cpd virtual setting file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cpd virtual setting file entry, or <code>null</code> if a matching cpd virtual setting file entry could not be found
	 */
	public CPDVirtualSettingFileEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPDVirtualSettingFileEntry> orderByComparator);

	/**
	 * Returns the cpd virtual setting file entries before and after the current cpd virtual setting file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CPDefinitionVirtualSettingFileEntryId the primary key of the current cpd virtual setting file entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cpd virtual setting file entry
	 * @throws NoSuchCPDVirtualSettingFileEntryException if a cpd virtual setting file entry with the primary key could not be found
	 */
	public CPDVirtualSettingFileEntry[] findByUuid_C_PrevAndNext(
			long CPDefinitionVirtualSettingFileEntryId, String uuid,
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPDVirtualSettingFileEntry> orderByComparator)
		throws NoSuchCPDVirtualSettingFileEntryException;

	/**
	 * Removes all the cpd virtual setting file entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of cpd virtual setting file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cpd virtual setting file entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the cpd virtual setting file entries where CPDefinitionVirtualSettingId = &#63;.
	 *
	 * @param CPDefinitionVirtualSettingId the cp definition virtual setting ID
	 * @return the matching cpd virtual setting file entries
	 */
	public java.util.List<CPDVirtualSettingFileEntry>
		findByCPDefinitionVirtualSettingId(long CPDefinitionVirtualSettingId);

	/**
	 * Returns a range of all the cpd virtual setting file entries where CPDefinitionVirtualSettingId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDVirtualSettingFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionVirtualSettingId the cp definition virtual setting ID
	 * @param start the lower bound of the range of cpd virtual setting file entries
	 * @param end the upper bound of the range of cpd virtual setting file entries (not inclusive)
	 * @return the range of matching cpd virtual setting file entries
	 */
	public java.util.List<CPDVirtualSettingFileEntry>
		findByCPDefinitionVirtualSettingId(
			long CPDefinitionVirtualSettingId, int start, int end);

	/**
	 * Returns an ordered range of all the cpd virtual setting file entries where CPDefinitionVirtualSettingId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDVirtualSettingFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionVirtualSettingId the cp definition virtual setting ID
	 * @param start the lower bound of the range of cpd virtual setting file entries
	 * @param end the upper bound of the range of cpd virtual setting file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cpd virtual setting file entries
	 */
	public java.util.List<CPDVirtualSettingFileEntry>
		findByCPDefinitionVirtualSettingId(
			long CPDefinitionVirtualSettingId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPDVirtualSettingFileEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the cpd virtual setting file entries where CPDefinitionVirtualSettingId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDVirtualSettingFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionVirtualSettingId the cp definition virtual setting ID
	 * @param start the lower bound of the range of cpd virtual setting file entries
	 * @param end the upper bound of the range of cpd virtual setting file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cpd virtual setting file entries
	 */
	public java.util.List<CPDVirtualSettingFileEntry>
		findByCPDefinitionVirtualSettingId(
			long CPDefinitionVirtualSettingId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPDVirtualSettingFileEntry> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first cpd virtual setting file entry in the ordered set where CPDefinitionVirtualSettingId = &#63;.
	 *
	 * @param CPDefinitionVirtualSettingId the cp definition virtual setting ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cpd virtual setting file entry
	 * @throws NoSuchCPDVirtualSettingFileEntryException if a matching cpd virtual setting file entry could not be found
	 */
	public CPDVirtualSettingFileEntry findByCPDefinitionVirtualSettingId_First(
			long CPDefinitionVirtualSettingId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPDVirtualSettingFileEntry> orderByComparator)
		throws NoSuchCPDVirtualSettingFileEntryException;

	/**
	 * Returns the first cpd virtual setting file entry in the ordered set where CPDefinitionVirtualSettingId = &#63;.
	 *
	 * @param CPDefinitionVirtualSettingId the cp definition virtual setting ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cpd virtual setting file entry, or <code>null</code> if a matching cpd virtual setting file entry could not be found
	 */
	public CPDVirtualSettingFileEntry fetchByCPDefinitionVirtualSettingId_First(
		long CPDefinitionVirtualSettingId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPDVirtualSettingFileEntry> orderByComparator);

	/**
	 * Returns the last cpd virtual setting file entry in the ordered set where CPDefinitionVirtualSettingId = &#63;.
	 *
	 * @param CPDefinitionVirtualSettingId the cp definition virtual setting ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cpd virtual setting file entry
	 * @throws NoSuchCPDVirtualSettingFileEntryException if a matching cpd virtual setting file entry could not be found
	 */
	public CPDVirtualSettingFileEntry findByCPDefinitionVirtualSettingId_Last(
			long CPDefinitionVirtualSettingId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPDVirtualSettingFileEntry> orderByComparator)
		throws NoSuchCPDVirtualSettingFileEntryException;

	/**
	 * Returns the last cpd virtual setting file entry in the ordered set where CPDefinitionVirtualSettingId = &#63;.
	 *
	 * @param CPDefinitionVirtualSettingId the cp definition virtual setting ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cpd virtual setting file entry, or <code>null</code> if a matching cpd virtual setting file entry could not be found
	 */
	public CPDVirtualSettingFileEntry fetchByCPDefinitionVirtualSettingId_Last(
		long CPDefinitionVirtualSettingId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPDVirtualSettingFileEntry> orderByComparator);

	/**
	 * Returns the cpd virtual setting file entries before and after the current cpd virtual setting file entry in the ordered set where CPDefinitionVirtualSettingId = &#63;.
	 *
	 * @param CPDefinitionVirtualSettingFileEntryId the primary key of the current cpd virtual setting file entry
	 * @param CPDefinitionVirtualSettingId the cp definition virtual setting ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cpd virtual setting file entry
	 * @throws NoSuchCPDVirtualSettingFileEntryException if a cpd virtual setting file entry with the primary key could not be found
	 */
	public CPDVirtualSettingFileEntry[]
			findByCPDefinitionVirtualSettingId_PrevAndNext(
				long CPDefinitionVirtualSettingFileEntryId,
				long CPDefinitionVirtualSettingId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CPDVirtualSettingFileEntry> orderByComparator)
		throws NoSuchCPDVirtualSettingFileEntryException;

	/**
	 * Removes all the cpd virtual setting file entries where CPDefinitionVirtualSettingId = &#63; from the database.
	 *
	 * @param CPDefinitionVirtualSettingId the cp definition virtual setting ID
	 */
	public void removeByCPDefinitionVirtualSettingId(
		long CPDefinitionVirtualSettingId);

	/**
	 * Returns the number of cpd virtual setting file entries where CPDefinitionVirtualSettingId = &#63;.
	 *
	 * @param CPDefinitionVirtualSettingId the cp definition virtual setting ID
	 * @return the number of matching cpd virtual setting file entries
	 */
	public int countByCPDefinitionVirtualSettingId(
		long CPDefinitionVirtualSettingId);

	/**
	 * Returns all the cpd virtual setting file entries where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @return the matching cpd virtual setting file entries
	 */
	public java.util.List<CPDVirtualSettingFileEntry> findByFileEntryId(
		long fileEntryId);

	/**
	 * Returns a range of all the cpd virtual setting file entries where fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDVirtualSettingFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of cpd virtual setting file entries
	 * @param end the upper bound of the range of cpd virtual setting file entries (not inclusive)
	 * @return the range of matching cpd virtual setting file entries
	 */
	public java.util.List<CPDVirtualSettingFileEntry> findByFileEntryId(
		long fileEntryId, int start, int end);

	/**
	 * Returns an ordered range of all the cpd virtual setting file entries where fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDVirtualSettingFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of cpd virtual setting file entries
	 * @param end the upper bound of the range of cpd virtual setting file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cpd virtual setting file entries
	 */
	public java.util.List<CPDVirtualSettingFileEntry> findByFileEntryId(
		long fileEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPDVirtualSettingFileEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the cpd virtual setting file entries where fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDVirtualSettingFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of cpd virtual setting file entries
	 * @param end the upper bound of the range of cpd virtual setting file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cpd virtual setting file entries
	 */
	public java.util.List<CPDVirtualSettingFileEntry> findByFileEntryId(
		long fileEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPDVirtualSettingFileEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cpd virtual setting file entry in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cpd virtual setting file entry
	 * @throws NoSuchCPDVirtualSettingFileEntryException if a matching cpd virtual setting file entry could not be found
	 */
	public CPDVirtualSettingFileEntry findByFileEntryId_First(
			long fileEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPDVirtualSettingFileEntry> orderByComparator)
		throws NoSuchCPDVirtualSettingFileEntryException;

	/**
	 * Returns the first cpd virtual setting file entry in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cpd virtual setting file entry, or <code>null</code> if a matching cpd virtual setting file entry could not be found
	 */
	public CPDVirtualSettingFileEntry fetchByFileEntryId_First(
		long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPDVirtualSettingFileEntry> orderByComparator);

	/**
	 * Returns the last cpd virtual setting file entry in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cpd virtual setting file entry
	 * @throws NoSuchCPDVirtualSettingFileEntryException if a matching cpd virtual setting file entry could not be found
	 */
	public CPDVirtualSettingFileEntry findByFileEntryId_Last(
			long fileEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPDVirtualSettingFileEntry> orderByComparator)
		throws NoSuchCPDVirtualSettingFileEntryException;

	/**
	 * Returns the last cpd virtual setting file entry in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cpd virtual setting file entry, or <code>null</code> if a matching cpd virtual setting file entry could not be found
	 */
	public CPDVirtualSettingFileEntry fetchByFileEntryId_Last(
		long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPDVirtualSettingFileEntry> orderByComparator);

	/**
	 * Returns the cpd virtual setting file entries before and after the current cpd virtual setting file entry in the ordered set where fileEntryId = &#63;.
	 *
	 * @param CPDefinitionVirtualSettingFileEntryId the primary key of the current cpd virtual setting file entry
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cpd virtual setting file entry
	 * @throws NoSuchCPDVirtualSettingFileEntryException if a cpd virtual setting file entry with the primary key could not be found
	 */
	public CPDVirtualSettingFileEntry[] findByFileEntryId_PrevAndNext(
			long CPDefinitionVirtualSettingFileEntryId, long fileEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPDVirtualSettingFileEntry> orderByComparator)
		throws NoSuchCPDVirtualSettingFileEntryException;

	/**
	 * Removes all the cpd virtual setting file entries where fileEntryId = &#63; from the database.
	 *
	 * @param fileEntryId the file entry ID
	 */
	public void removeByFileEntryId(long fileEntryId);

	/**
	 * Returns the number of cpd virtual setting file entries where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @return the number of matching cpd virtual setting file entries
	 */
	public int countByFileEntryId(long fileEntryId);

	/**
	 * Caches the cpd virtual setting file entry in the entity cache if it is enabled.
	 *
	 * @param cpdVirtualSettingFileEntry the cpd virtual setting file entry
	 */
	public void cacheResult(
		CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry);

	/**
	 * Caches the cpd virtual setting file entries in the entity cache if it is enabled.
	 *
	 * @param cpdVirtualSettingFileEntries the cpd virtual setting file entries
	 */
	public void cacheResult(
		java.util.List<CPDVirtualSettingFileEntry>
			cpdVirtualSettingFileEntries);

	/**
	 * Creates a new cpd virtual setting file entry with the primary key. Does not add the cpd virtual setting file entry to the database.
	 *
	 * @param CPDefinitionVirtualSettingFileEntryId the primary key for the new cpd virtual setting file entry
	 * @return the new cpd virtual setting file entry
	 */
	public CPDVirtualSettingFileEntry create(
		long CPDefinitionVirtualSettingFileEntryId);

	/**
	 * Removes the cpd virtual setting file entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPDefinitionVirtualSettingFileEntryId the primary key of the cpd virtual setting file entry
	 * @return the cpd virtual setting file entry that was removed
	 * @throws NoSuchCPDVirtualSettingFileEntryException if a cpd virtual setting file entry with the primary key could not be found
	 */
	public CPDVirtualSettingFileEntry remove(
			long CPDefinitionVirtualSettingFileEntryId)
		throws NoSuchCPDVirtualSettingFileEntryException;

	public CPDVirtualSettingFileEntry updateImpl(
		CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry);

	/**
	 * Returns the cpd virtual setting file entry with the primary key or throws a <code>NoSuchCPDVirtualSettingFileEntryException</code> if it could not be found.
	 *
	 * @param CPDefinitionVirtualSettingFileEntryId the primary key of the cpd virtual setting file entry
	 * @return the cpd virtual setting file entry
	 * @throws NoSuchCPDVirtualSettingFileEntryException if a cpd virtual setting file entry with the primary key could not be found
	 */
	public CPDVirtualSettingFileEntry findByPrimaryKey(
			long CPDefinitionVirtualSettingFileEntryId)
		throws NoSuchCPDVirtualSettingFileEntryException;

	/**
	 * Returns the cpd virtual setting file entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPDefinitionVirtualSettingFileEntryId the primary key of the cpd virtual setting file entry
	 * @return the cpd virtual setting file entry, or <code>null</code> if a cpd virtual setting file entry with the primary key could not be found
	 */
	public CPDVirtualSettingFileEntry fetchByPrimaryKey(
		long CPDefinitionVirtualSettingFileEntryId);

	/**
	 * Returns all the cpd virtual setting file entries.
	 *
	 * @return the cpd virtual setting file entries
	 */
	public java.util.List<CPDVirtualSettingFileEntry> findAll();

	/**
	 * Returns a range of all the cpd virtual setting file entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDVirtualSettingFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cpd virtual setting file entries
	 * @param end the upper bound of the range of cpd virtual setting file entries (not inclusive)
	 * @return the range of cpd virtual setting file entries
	 */
	public java.util.List<CPDVirtualSettingFileEntry> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the cpd virtual setting file entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDVirtualSettingFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cpd virtual setting file entries
	 * @param end the upper bound of the range of cpd virtual setting file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cpd virtual setting file entries
	 */
	public java.util.List<CPDVirtualSettingFileEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPDVirtualSettingFileEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the cpd virtual setting file entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDVirtualSettingFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cpd virtual setting file entries
	 * @param end the upper bound of the range of cpd virtual setting file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cpd virtual setting file entries
	 */
	public java.util.List<CPDVirtualSettingFileEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPDVirtualSettingFileEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the cpd virtual setting file entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of cpd virtual setting file entries.
	 *
	 * @return the number of cpd virtual setting file entries
	 */
	public int countAll();

}
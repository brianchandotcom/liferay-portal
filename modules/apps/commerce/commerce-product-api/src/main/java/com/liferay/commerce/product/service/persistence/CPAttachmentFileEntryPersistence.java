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

package com.liferay.commerce.product.service.persistence;

import com.liferay.commerce.product.exception.NoSuchCPAttachmentFileEntryException;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the cp attachment file entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPAttachmentFileEntryUtil
 * @generated
 */
@ProviderType
public interface CPAttachmentFileEntryPersistence
	extends BasePersistence<CPAttachmentFileEntry>,
			CTPersistence<CPAttachmentFileEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPAttachmentFileEntryUtil} to access the cp attachment file entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the cp attachment file entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp attachment file entries
	 */
	public java.util.List<CPAttachmentFileEntry> findByUuid(String uuid);

	/**
	 * Returns a range of all the cp attachment file entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @return the range of matching cp attachment file entries
	 */
	public java.util.List<CPAttachmentFileEntry> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the cp attachment file entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp attachment file entries
	 */
	public java.util.List<CPAttachmentFileEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPAttachmentFileEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cp attachment file entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp attachment file entries
	 */
	public java.util.List<CPAttachmentFileEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPAttachmentFileEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp attachment file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException;

	/**
	 * Returns the first cp attachment file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPAttachmentFileEntry>
			orderByComparator);

	/**
	 * Returns the last cp attachment file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException;

	/**
	 * Returns the last cp attachment file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPAttachmentFileEntry>
			orderByComparator);

	/**
	 * Returns the cp attachment file entries before and after the current cp attachment file entry in the ordered set where uuid = &#63;.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the current cp attachment file entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	public CPAttachmentFileEntry[] findByUuid_PrevAndNext(
			long CPAttachmentFileEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException;

	/**
	 * Removes all the cp attachment file entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of cp attachment file entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp attachment file entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the cp attachment file entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchCPAttachmentFileEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchCPAttachmentFileEntryException;

	/**
	 * Returns the cp attachment file entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the cp attachment file entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the cp attachment file entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the cp attachment file entry that was removed
	 */
	public CPAttachmentFileEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchCPAttachmentFileEntryException;

	/**
	 * Returns the number of cp attachment file entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching cp attachment file entries
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the cp attachment file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp attachment file entries
	 */
	public java.util.List<CPAttachmentFileEntry> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the cp attachment file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @return the range of matching cp attachment file entries
	 */
	public java.util.List<CPAttachmentFileEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the cp attachment file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp attachment file entries
	 */
	public java.util.List<CPAttachmentFileEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPAttachmentFileEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cp attachment file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp attachment file entries
	 */
	public java.util.List<CPAttachmentFileEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPAttachmentFileEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp attachment file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException;

	/**
	 * Returns the first cp attachment file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPAttachmentFileEntry>
			orderByComparator);

	/**
	 * Returns the last cp attachment file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException;

	/**
	 * Returns the last cp attachment file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPAttachmentFileEntry>
			orderByComparator);

	/**
	 * Returns the cp attachment file entries before and after the current cp attachment file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the current cp attachment file entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	public CPAttachmentFileEntry[] findByUuid_C_PrevAndNext(
			long CPAttachmentFileEntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException;

	/**
	 * Removes all the cp attachment file entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of cp attachment file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp attachment file entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the cp attachment file entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching cp attachment file entries
	 */
	public java.util.List<CPAttachmentFileEntry> findByC_C(
		long classNameId, long classPK);

	/**
	 * Returns a range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @return the range of matching cp attachment file entries
	 */
	public java.util.List<CPAttachmentFileEntry> findByC_C(
		long classNameId, long classPK, int start, int end);

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp attachment file entries
	 */
	public java.util.List<CPAttachmentFileEntry> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPAttachmentFileEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp attachment file entries
	 */
	public java.util.List<CPAttachmentFileEntry> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPAttachmentFileEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry findByC_C_First(
			long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException;

	/**
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry fetchByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<CPAttachmentFileEntry>
			orderByComparator);

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry findByC_C_Last(
			long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException;

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry fetchByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<CPAttachmentFileEntry>
			orderByComparator);

	/**
	 * Returns the cp attachment file entries before and after the current cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the current cp attachment file entry
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	public CPAttachmentFileEntry[] findByC_C_PrevAndNext(
			long CPAttachmentFileEntryId, long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException;

	/**
	 * Removes all the cp attachment file entries where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public void removeByC_C(long classNameId, long classPK);

	/**
	 * Returns the number of cp attachment file entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching cp attachment file entries
	 */
	public int countByC_C(long classNameId, long classPK);

	/**
	 * Returns all the cp attachment file entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching cp attachment file entries
	 */
	public java.util.List<CPAttachmentFileEntry> findByLtD_S(
		Date displayDate, int status);

	/**
	 * Returns a range of all the cp attachment file entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @return the range of matching cp attachment file entries
	 */
	public java.util.List<CPAttachmentFileEntry> findByLtD_S(
		Date displayDate, int status, int start, int end);

	/**
	 * Returns an ordered range of all the cp attachment file entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp attachment file entries
	 */
	public java.util.List<CPAttachmentFileEntry> findByLtD_S(
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPAttachmentFileEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cp attachment file entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp attachment file entries
	 */
	public java.util.List<CPAttachmentFileEntry> findByLtD_S(
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPAttachmentFileEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp attachment file entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry findByLtD_S_First(
			Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException;

	/**
	 * Returns the first cp attachment file entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry fetchByLtD_S_First(
		Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPAttachmentFileEntry>
			orderByComparator);

	/**
	 * Returns the last cp attachment file entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry findByLtD_S_Last(
			Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException;

	/**
	 * Returns the last cp attachment file entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry fetchByLtD_S_Last(
		Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPAttachmentFileEntry>
			orderByComparator);

	/**
	 * Returns the cp attachment file entries before and after the current cp attachment file entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the current cp attachment file entry
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	public CPAttachmentFileEntry[] findByLtD_S_PrevAndNext(
			long CPAttachmentFileEntryId, Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException;

	/**
	 * Removes all the cp attachment file entries where displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 */
	public void removeByLtD_S(Date displayDate, int status);

	/**
	 * Returns the number of cp attachment file entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching cp attachment file entries
	 */
	public int countByLtD_S(Date displayDate, int status);

	/**
	 * Returns all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and fileEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param fileEntryId the file entry ID
	 * @return the matching cp attachment file entries
	 */
	public java.util.List<CPAttachmentFileEntry> findByC_C_F(
		long classNameId, long classPK, long fileEntryId);

	/**
	 * Returns a range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @return the range of matching cp attachment file entries
	 */
	public java.util.List<CPAttachmentFileEntry> findByC_C_F(
		long classNameId, long classPK, long fileEntryId, int start, int end);

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp attachment file entries
	 */
	public java.util.List<CPAttachmentFileEntry> findByC_C_F(
		long classNameId, long classPK, long fileEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPAttachmentFileEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp attachment file entries
	 */
	public java.util.List<CPAttachmentFileEntry> findByC_C_F(
		long classNameId, long classPK, long fileEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPAttachmentFileEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and fileEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry findByC_C_F_First(
			long classNameId, long classPK, long fileEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException;

	/**
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and fileEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry fetchByC_C_F_First(
		long classNameId, long classPK, long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CPAttachmentFileEntry>
			orderByComparator);

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and fileEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry findByC_C_F_Last(
			long classNameId, long classPK, long fileEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException;

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and fileEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry fetchByC_C_F_Last(
		long classNameId, long classPK, long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CPAttachmentFileEntry>
			orderByComparator);

	/**
	 * Returns the cp attachment file entries before and after the current cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and fileEntryId = &#63;.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the current cp attachment file entry
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	public CPAttachmentFileEntry[] findByC_C_F_PrevAndNext(
			long CPAttachmentFileEntryId, long classNameId, long classPK,
			long fileEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException;

	/**
	 * Removes all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and fileEntryId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param fileEntryId the file entry ID
	 */
	public void removeByC_C_F(long classNameId, long classPK, long fileEntryId);

	/**
	 * Returns the number of cp attachment file entries where classNameId = &#63; and classPK = &#63; and fileEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param fileEntryId the file entry ID
	 * @return the number of matching cp attachment file entries
	 */
	public int countByC_C_F(long classNameId, long classPK, long fileEntryId);

	/**
	 * Returns all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and cdnURL = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param cdnURL the cdn url
	 * @return the matching cp attachment file entries
	 */
	public java.util.List<CPAttachmentFileEntry> findByC_C_C(
		long classNameId, long classPK, String cdnURL);

	/**
	 * Returns a range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and cdnURL = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param cdnURL the cdn url
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @return the range of matching cp attachment file entries
	 */
	public java.util.List<CPAttachmentFileEntry> findByC_C_C(
		long classNameId, long classPK, String cdnURL, int start, int end);

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and cdnURL = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param cdnURL the cdn url
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp attachment file entries
	 */
	public java.util.List<CPAttachmentFileEntry> findByC_C_C(
		long classNameId, long classPK, String cdnURL, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPAttachmentFileEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and cdnURL = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param cdnURL the cdn url
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp attachment file entries
	 */
	public java.util.List<CPAttachmentFileEntry> findByC_C_C(
		long classNameId, long classPK, String cdnURL, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPAttachmentFileEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and cdnURL = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param cdnURL the cdn url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry findByC_C_C_First(
			long classNameId, long classPK, String cdnURL,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException;

	/**
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and cdnURL = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param cdnURL the cdn url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry fetchByC_C_C_First(
		long classNameId, long classPK, String cdnURL,
		com.liferay.portal.kernel.util.OrderByComparator<CPAttachmentFileEntry>
			orderByComparator);

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and cdnURL = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param cdnURL the cdn url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry findByC_C_C_Last(
			long classNameId, long classPK, String cdnURL,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException;

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and cdnURL = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param cdnURL the cdn url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry fetchByC_C_C_Last(
		long classNameId, long classPK, String cdnURL,
		com.liferay.portal.kernel.util.OrderByComparator<CPAttachmentFileEntry>
			orderByComparator);

	/**
	 * Returns the cp attachment file entries before and after the current cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and cdnURL = &#63;.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the current cp attachment file entry
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param cdnURL the cdn url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	public CPAttachmentFileEntry[] findByC_C_C_PrevAndNext(
			long CPAttachmentFileEntryId, long classNameId, long classPK,
			String cdnURL,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException;

	/**
	 * Removes all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and cdnURL = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param cdnURL the cdn url
	 */
	public void removeByC_C_C(long classNameId, long classPK, String cdnURL);

	/**
	 * Returns the number of cp attachment file entries where classNameId = &#63; and classPK = &#63; and cdnURL = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param cdnURL the cdn url
	 * @return the number of matching cp attachment file entries
	 */
	public int countByC_C_C(long classNameId, long classPK, String cdnURL);

	/**
	 * Returns all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching cp attachment file entries
	 */
	public java.util.List<CPAttachmentFileEntry> findByC_C_LtD_S(
		long classNameId, long classPK, Date displayDate, int status);

	/**
	 * Returns a range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @return the range of matching cp attachment file entries
	 */
	public java.util.List<CPAttachmentFileEntry> findByC_C_LtD_S(
		long classNameId, long classPK, Date displayDate, int status, int start,
		int end);

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp attachment file entries
	 */
	public java.util.List<CPAttachmentFileEntry> findByC_C_LtD_S(
		long classNameId, long classPK, Date displayDate, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPAttachmentFileEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp attachment file entries
	 */
	public java.util.List<CPAttachmentFileEntry> findByC_C_LtD_S(
		long classNameId, long classPK, Date displayDate, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPAttachmentFileEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry findByC_C_LtD_S_First(
			long classNameId, long classPK, Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException;

	/**
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry fetchByC_C_LtD_S_First(
		long classNameId, long classPK, Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPAttachmentFileEntry>
			orderByComparator);

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry findByC_C_LtD_S_Last(
			long classNameId, long classPK, Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException;

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry fetchByC_C_LtD_S_Last(
		long classNameId, long classPK, Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPAttachmentFileEntry>
			orderByComparator);

	/**
	 * Returns the cp attachment file entries before and after the current cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the current cp attachment file entry
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	public CPAttachmentFileEntry[] findByC_C_LtD_S_PrevAndNext(
			long CPAttachmentFileEntryId, long classNameId, long classPK,
			Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException;

	/**
	 * Removes all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 */
	public void removeByC_C_LtD_S(
		long classNameId, long classPK, Date displayDate, int status);

	/**
	 * Returns the number of cp attachment file entries where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching cp attachment file entries
	 */
	public int countByC_C_LtD_S(
		long classNameId, long classPK, Date displayDate, int status);

	/**
	 * Returns all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @return the matching cp attachment file entries
	 */
	public java.util.List<CPAttachmentFileEntry> findByC_C_T_ST(
		long classNameId, long classPK, int type, int status);

	/**
	 * Returns a range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @return the range of matching cp attachment file entries
	 */
	public java.util.List<CPAttachmentFileEntry> findByC_C_T_ST(
		long classNameId, long classPK, int type, int status, int start,
		int end);

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp attachment file entries
	 */
	public java.util.List<CPAttachmentFileEntry> findByC_C_T_ST(
		long classNameId, long classPK, int type, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPAttachmentFileEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp attachment file entries
	 */
	public java.util.List<CPAttachmentFileEntry> findByC_C_T_ST(
		long classNameId, long classPK, int type, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPAttachmentFileEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry findByC_C_T_ST_First(
			long classNameId, long classPK, int type, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException;

	/**
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry fetchByC_C_T_ST_First(
		long classNameId, long classPK, int type, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPAttachmentFileEntry>
			orderByComparator);

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry findByC_C_T_ST_Last(
			long classNameId, long classPK, int type, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException;

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry fetchByC_C_T_ST_Last(
		long classNameId, long classPK, int type, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPAttachmentFileEntry>
			orderByComparator);

	/**
	 * Returns the cp attachment file entries before and after the current cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the current cp attachment file entry
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	public CPAttachmentFileEntry[] findByC_C_T_ST_PrevAndNext(
			long CPAttachmentFileEntryId, long classNameId, long classPK,
			int type, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException;

	/**
	 * Removes all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 */
	public void removeByC_C_T_ST(
		long classNameId, long classPK, int type, int status);

	/**
	 * Returns the number of cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @return the number of matching cp attachment file entries
	 */
	public int countByC_C_T_ST(
		long classNameId, long classPK, int type, int status);

	/**
	 * Returns all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @return the matching cp attachment file entries
	 */
	public java.util.List<CPAttachmentFileEntry> findByC_C_T_NotST(
		long classNameId, long classPK, int type, int status);

	/**
	 * Returns a range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @return the range of matching cp attachment file entries
	 */
	public java.util.List<CPAttachmentFileEntry> findByC_C_T_NotST(
		long classNameId, long classPK, int type, int status, int start,
		int end);

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp attachment file entries
	 */
	public java.util.List<CPAttachmentFileEntry> findByC_C_T_NotST(
		long classNameId, long classPK, int type, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPAttachmentFileEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp attachment file entries
	 */
	public java.util.List<CPAttachmentFileEntry> findByC_C_T_NotST(
		long classNameId, long classPK, int type, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPAttachmentFileEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry findByC_C_T_NotST_First(
			long classNameId, long classPK, int type, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException;

	/**
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry fetchByC_C_T_NotST_First(
		long classNameId, long classPK, int type, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPAttachmentFileEntry>
			orderByComparator);

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry findByC_C_T_NotST_Last(
			long classNameId, long classPK, int type, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException;

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry fetchByC_C_T_NotST_Last(
		long classNameId, long classPK, int type, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPAttachmentFileEntry>
			orderByComparator);

	/**
	 * Returns the cp attachment file entries before and after the current cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63;.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the current cp attachment file entry
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	public CPAttachmentFileEntry[] findByC_C_T_NotST_PrevAndNext(
			long CPAttachmentFileEntryId, long classNameId, long classPK,
			int type, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException;

	/**
	 * Removes all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 */
	public void removeByC_C_T_NotST(
		long classNameId, long classPK, int type, int status);

	/**
	 * Returns the number of cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @return the number of matching cp attachment file entries
	 */
	public int countByC_C_T_NotST(
		long classNameId, long classPK, int type, int status);

	/**
	 * Returns the cp attachment file entry where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchCPAttachmentFileEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry findByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchCPAttachmentFileEntryException;

	/**
	 * Returns the cp attachment file entry where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry fetchByC_ERC(
		long companyId, String externalReferenceCode);

	/**
	 * Returns the cp attachment file entry where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public CPAttachmentFileEntry fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache);

	/**
	 * Removes the cp attachment file entry where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the cp attachment file entry that was removed
	 */
	public CPAttachmentFileEntry removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchCPAttachmentFileEntryException;

	/**
	 * Returns the number of cp attachment file entries where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching cp attachment file entries
	 */
	public int countByC_ERC(long companyId, String externalReferenceCode);

	/**
	 * Caches the cp attachment file entry in the entity cache if it is enabled.
	 *
	 * @param cpAttachmentFileEntry the cp attachment file entry
	 */
	public void cacheResult(CPAttachmentFileEntry cpAttachmentFileEntry);

	/**
	 * Caches the cp attachment file entries in the entity cache if it is enabled.
	 *
	 * @param cpAttachmentFileEntries the cp attachment file entries
	 */
	public void cacheResult(
		java.util.List<CPAttachmentFileEntry> cpAttachmentFileEntries);

	/**
	 * Creates a new cp attachment file entry with the primary key. Does not add the cp attachment file entry to the database.
	 *
	 * @param CPAttachmentFileEntryId the primary key for the new cp attachment file entry
	 * @return the new cp attachment file entry
	 */
	public CPAttachmentFileEntry create(long CPAttachmentFileEntryId);

	/**
	 * Removes the cp attachment file entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the cp attachment file entry
	 * @return the cp attachment file entry that was removed
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	public CPAttachmentFileEntry remove(long CPAttachmentFileEntryId)
		throws NoSuchCPAttachmentFileEntryException;

	public CPAttachmentFileEntry updateImpl(
		CPAttachmentFileEntry cpAttachmentFileEntry);

	/**
	 * Returns the cp attachment file entry with the primary key or throws a <code>NoSuchCPAttachmentFileEntryException</code> if it could not be found.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the cp attachment file entry
	 * @return the cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	public CPAttachmentFileEntry findByPrimaryKey(long CPAttachmentFileEntryId)
		throws NoSuchCPAttachmentFileEntryException;

	/**
	 * Returns the cp attachment file entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the cp attachment file entry
	 * @return the cp attachment file entry, or <code>null</code> if a cp attachment file entry with the primary key could not be found
	 */
	public CPAttachmentFileEntry fetchByPrimaryKey(
		long CPAttachmentFileEntryId);

	/**
	 * Returns all the cp attachment file entries.
	 *
	 * @return the cp attachment file entries
	 */
	public java.util.List<CPAttachmentFileEntry> findAll();

	/**
	 * Returns a range of all the cp attachment file entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @return the range of cp attachment file entries
	 */
	public java.util.List<CPAttachmentFileEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the cp attachment file entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp attachment file entries
	 */
	public java.util.List<CPAttachmentFileEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPAttachmentFileEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cp attachment file entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp attachment file entries
	 */
	public java.util.List<CPAttachmentFileEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPAttachmentFileEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the cp attachment file entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of cp attachment file entries.
	 *
	 * @return the number of cp attachment file entries
	 */
	public int countAll();

}
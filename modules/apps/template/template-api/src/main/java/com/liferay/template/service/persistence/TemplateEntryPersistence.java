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

package com.liferay.template.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.template.exception.NoSuchTemplateEntryException;
import com.liferay.template.model.TemplateEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the template entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TemplateEntryUtil
 * @generated
 */
@ProviderType
public interface TemplateEntryPersistence
	extends BasePersistence<TemplateEntry>, CTPersistence<TemplateEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link TemplateEntryUtil} to access the template entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the template entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching template entries
	 */
	public java.util.List<TemplateEntry> findByUuid(String uuid);

	/**
	 * Returns a range of all the template entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @return the range of matching template entries
	 */
	public java.util.List<TemplateEntry> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the template entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching template entries
	 */
	public java.util.List<TemplateEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the template entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching template entries
	 */
	public java.util.List<TemplateEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first template entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching template entry
	 * @throws NoSuchTemplateEntryException if a matching template entry could not be found
	 */
	public TemplateEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
				orderByComparator)
		throws NoSuchTemplateEntryException;

	/**
	 * Returns the first template entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching template entry, or <code>null</code> if a matching template entry could not be found
	 */
	public TemplateEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
			orderByComparator);

	/**
	 * Returns the last template entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching template entry
	 * @throws NoSuchTemplateEntryException if a matching template entry could not be found
	 */
	public TemplateEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
				orderByComparator)
		throws NoSuchTemplateEntryException;

	/**
	 * Returns the last template entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching template entry, or <code>null</code> if a matching template entry could not be found
	 */
	public TemplateEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
			orderByComparator);

	/**
	 * Returns the template entries before and after the current template entry in the ordered set where uuid = &#63;.
	 *
	 * @param templateEntryId the primary key of the current template entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next template entry
	 * @throws NoSuchTemplateEntryException if a template entry with the primary key could not be found
	 */
	public TemplateEntry[] findByUuid_PrevAndNext(
			long templateEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
				orderByComparator)
		throws NoSuchTemplateEntryException;

	/**
	 * Removes all the template entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of template entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching template entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the template entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchTemplateEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching template entry
	 * @throws NoSuchTemplateEntryException if a matching template entry could not be found
	 */
	public TemplateEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchTemplateEntryException;

	/**
	 * Returns the template entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching template entry, or <code>null</code> if a matching template entry could not be found
	 */
	public TemplateEntry fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the template entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching template entry, or <code>null</code> if a matching template entry could not be found
	 */
	public TemplateEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the template entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the template entry that was removed
	 */
	public TemplateEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchTemplateEntryException;

	/**
	 * Returns the number of template entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching template entries
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the template entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching template entries
	 */
	public java.util.List<TemplateEntry> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the template entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @return the range of matching template entries
	 */
	public java.util.List<TemplateEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the template entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching template entries
	 */
	public java.util.List<TemplateEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the template entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching template entries
	 */
	public java.util.List<TemplateEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first template entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching template entry
	 * @throws NoSuchTemplateEntryException if a matching template entry could not be found
	 */
	public TemplateEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
				orderByComparator)
		throws NoSuchTemplateEntryException;

	/**
	 * Returns the first template entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching template entry, or <code>null</code> if a matching template entry could not be found
	 */
	public TemplateEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
			orderByComparator);

	/**
	 * Returns the last template entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching template entry
	 * @throws NoSuchTemplateEntryException if a matching template entry could not be found
	 */
	public TemplateEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
				orderByComparator)
		throws NoSuchTemplateEntryException;

	/**
	 * Returns the last template entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching template entry, or <code>null</code> if a matching template entry could not be found
	 */
	public TemplateEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
			orderByComparator);

	/**
	 * Returns the template entries before and after the current template entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param templateEntryId the primary key of the current template entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next template entry
	 * @throws NoSuchTemplateEntryException if a template entry with the primary key could not be found
	 */
	public TemplateEntry[] findByUuid_C_PrevAndNext(
			long templateEntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
				orderByComparator)
		throws NoSuchTemplateEntryException;

	/**
	 * Removes all the template entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of template entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching template entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the template entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching template entries
	 */
	public java.util.List<TemplateEntry> findByGroupId(long groupId);

	/**
	 * Returns a range of all the template entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @return the range of matching template entries
	 */
	public java.util.List<TemplateEntry> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the template entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching template entries
	 */
	public java.util.List<TemplateEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the template entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching template entries
	 */
	public java.util.List<TemplateEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first template entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching template entry
	 * @throws NoSuchTemplateEntryException if a matching template entry could not be found
	 */
	public TemplateEntry findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
				orderByComparator)
		throws NoSuchTemplateEntryException;

	/**
	 * Returns the first template entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching template entry, or <code>null</code> if a matching template entry could not be found
	 */
	public TemplateEntry fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
			orderByComparator);

	/**
	 * Returns the last template entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching template entry
	 * @throws NoSuchTemplateEntryException if a matching template entry could not be found
	 */
	public TemplateEntry findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
				orderByComparator)
		throws NoSuchTemplateEntryException;

	/**
	 * Returns the last template entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching template entry, or <code>null</code> if a matching template entry could not be found
	 */
	public TemplateEntry fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
			orderByComparator);

	/**
	 * Returns the template entries before and after the current template entry in the ordered set where groupId = &#63;.
	 *
	 * @param templateEntryId the primary key of the current template entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next template entry
	 * @throws NoSuchTemplateEntryException if a template entry with the primary key could not be found
	 */
	public TemplateEntry[] findByGroupId_PrevAndNext(
			long templateEntryId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
				orderByComparator)
		throws NoSuchTemplateEntryException;

	/**
	 * Returns all the template entries where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @return the matching template entries
	 */
	public java.util.List<TemplateEntry> findByGroupId(long[] groupIds);

	/**
	 * Returns a range of all the template entries where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @return the range of matching template entries
	 */
	public java.util.List<TemplateEntry> findByGroupId(
		long[] groupIds, int start, int end);

	/**
	 * Returns an ordered range of all the template entries where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching template entries
	 */
	public java.util.List<TemplateEntry> findByGroupId(
		long[] groupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the template entries where groupId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching template entries
	 */
	public java.util.List<TemplateEntry> findByGroupId(
		long[] groupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the template entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of template entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching template entries
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns the number of template entries where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching template entries
	 */
	public int countByGroupId(long[] groupIds);

	/**
	 * Returns the template entry where ddmTemplateId = &#63; or throws a <code>NoSuchTemplateEntryException</code> if it could not be found.
	 *
	 * @param ddmTemplateId the ddm template ID
	 * @return the matching template entry
	 * @throws NoSuchTemplateEntryException if a matching template entry could not be found
	 */
	public TemplateEntry findByDDMTemplateId(long ddmTemplateId)
		throws NoSuchTemplateEntryException;

	/**
	 * Returns the template entry where ddmTemplateId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param ddmTemplateId the ddm template ID
	 * @return the matching template entry, or <code>null</code> if a matching template entry could not be found
	 */
	public TemplateEntry fetchByDDMTemplateId(long ddmTemplateId);

	/**
	 * Returns the template entry where ddmTemplateId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param ddmTemplateId the ddm template ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching template entry, or <code>null</code> if a matching template entry could not be found
	 */
	public TemplateEntry fetchByDDMTemplateId(
		long ddmTemplateId, boolean useFinderCache);

	/**
	 * Removes the template entry where ddmTemplateId = &#63; from the database.
	 *
	 * @param ddmTemplateId the ddm template ID
	 * @return the template entry that was removed
	 */
	public TemplateEntry removeByDDMTemplateId(long ddmTemplateId)
		throws NoSuchTemplateEntryException;

	/**
	 * Returns the number of template entries where ddmTemplateId = &#63;.
	 *
	 * @param ddmTemplateId the ddm template ID
	 * @return the number of matching template entries
	 */
	public int countByDDMTemplateId(long ddmTemplateId);

	/**
	 * Returns all the template entries where groupId = &#63; and infoItemClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @return the matching template entries
	 */
	public java.util.List<TemplateEntry> findByG_IICN(
		long groupId, String infoItemClassName);

	/**
	 * Returns a range of all the template entries where groupId = &#63; and infoItemClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @return the range of matching template entries
	 */
	public java.util.List<TemplateEntry> findByG_IICN(
		long groupId, String infoItemClassName, int start, int end);

	/**
	 * Returns an ordered range of all the template entries where groupId = &#63; and infoItemClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching template entries
	 */
	public java.util.List<TemplateEntry> findByG_IICN(
		long groupId, String infoItemClassName, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the template entries where groupId = &#63; and infoItemClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching template entries
	 */
	public java.util.List<TemplateEntry> findByG_IICN(
		long groupId, String infoItemClassName, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first template entry in the ordered set where groupId = &#63; and infoItemClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching template entry
	 * @throws NoSuchTemplateEntryException if a matching template entry could not be found
	 */
	public TemplateEntry findByG_IICN_First(
			long groupId, String infoItemClassName,
			com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
				orderByComparator)
		throws NoSuchTemplateEntryException;

	/**
	 * Returns the first template entry in the ordered set where groupId = &#63; and infoItemClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching template entry, or <code>null</code> if a matching template entry could not be found
	 */
	public TemplateEntry fetchByG_IICN_First(
		long groupId, String infoItemClassName,
		com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
			orderByComparator);

	/**
	 * Returns the last template entry in the ordered set where groupId = &#63; and infoItemClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching template entry
	 * @throws NoSuchTemplateEntryException if a matching template entry could not be found
	 */
	public TemplateEntry findByG_IICN_Last(
			long groupId, String infoItemClassName,
			com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
				orderByComparator)
		throws NoSuchTemplateEntryException;

	/**
	 * Returns the last template entry in the ordered set where groupId = &#63; and infoItemClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching template entry, or <code>null</code> if a matching template entry could not be found
	 */
	public TemplateEntry fetchByG_IICN_Last(
		long groupId, String infoItemClassName,
		com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
			orderByComparator);

	/**
	 * Returns the template entries before and after the current template entry in the ordered set where groupId = &#63; and infoItemClassName = &#63;.
	 *
	 * @param templateEntryId the primary key of the current template entry
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next template entry
	 * @throws NoSuchTemplateEntryException if a template entry with the primary key could not be found
	 */
	public TemplateEntry[] findByG_IICN_PrevAndNext(
			long templateEntryId, long groupId, String infoItemClassName,
			com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
				orderByComparator)
		throws NoSuchTemplateEntryException;

	/**
	 * Removes all the template entries where groupId = &#63; and infoItemClassName = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 */
	public void removeByG_IICN(long groupId, String infoItemClassName);

	/**
	 * Returns the number of template entries where groupId = &#63; and infoItemClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @return the number of matching template entries
	 */
	public int countByG_IICN(long groupId, String infoItemClassName);

	/**
	 * Returns all the template entries where groupId = &#63; and infoItemClassName = &#63; and infoItemFormVariationKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @param infoItemFormVariationKey the info item form variation key
	 * @return the matching template entries
	 */
	public java.util.List<TemplateEntry> findByG_IICN_IIFVK(
		long groupId, String infoItemClassName,
		String infoItemFormVariationKey);

	/**
	 * Returns a range of all the template entries where groupId = &#63; and infoItemClassName = &#63; and infoItemFormVariationKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @param infoItemFormVariationKey the info item form variation key
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @return the range of matching template entries
	 */
	public java.util.List<TemplateEntry> findByG_IICN_IIFVK(
		long groupId, String infoItemClassName, String infoItemFormVariationKey,
		int start, int end);

	/**
	 * Returns an ordered range of all the template entries where groupId = &#63; and infoItemClassName = &#63; and infoItemFormVariationKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @param infoItemFormVariationKey the info item form variation key
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching template entries
	 */
	public java.util.List<TemplateEntry> findByG_IICN_IIFVK(
		long groupId, String infoItemClassName, String infoItemFormVariationKey,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the template entries where groupId = &#63; and infoItemClassName = &#63; and infoItemFormVariationKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @param infoItemFormVariationKey the info item form variation key
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching template entries
	 */
	public java.util.List<TemplateEntry> findByG_IICN_IIFVK(
		long groupId, String infoItemClassName, String infoItemFormVariationKey,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first template entry in the ordered set where groupId = &#63; and infoItemClassName = &#63; and infoItemFormVariationKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @param infoItemFormVariationKey the info item form variation key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching template entry
	 * @throws NoSuchTemplateEntryException if a matching template entry could not be found
	 */
	public TemplateEntry findByG_IICN_IIFVK_First(
			long groupId, String infoItemClassName,
			String infoItemFormVariationKey,
			com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
				orderByComparator)
		throws NoSuchTemplateEntryException;

	/**
	 * Returns the first template entry in the ordered set where groupId = &#63; and infoItemClassName = &#63; and infoItemFormVariationKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @param infoItemFormVariationKey the info item form variation key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching template entry, or <code>null</code> if a matching template entry could not be found
	 */
	public TemplateEntry fetchByG_IICN_IIFVK_First(
		long groupId, String infoItemClassName, String infoItemFormVariationKey,
		com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
			orderByComparator);

	/**
	 * Returns the last template entry in the ordered set where groupId = &#63; and infoItemClassName = &#63; and infoItemFormVariationKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @param infoItemFormVariationKey the info item form variation key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching template entry
	 * @throws NoSuchTemplateEntryException if a matching template entry could not be found
	 */
	public TemplateEntry findByG_IICN_IIFVK_Last(
			long groupId, String infoItemClassName,
			String infoItemFormVariationKey,
			com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
				orderByComparator)
		throws NoSuchTemplateEntryException;

	/**
	 * Returns the last template entry in the ordered set where groupId = &#63; and infoItemClassName = &#63; and infoItemFormVariationKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @param infoItemFormVariationKey the info item form variation key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching template entry, or <code>null</code> if a matching template entry could not be found
	 */
	public TemplateEntry fetchByG_IICN_IIFVK_Last(
		long groupId, String infoItemClassName, String infoItemFormVariationKey,
		com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
			orderByComparator);

	/**
	 * Returns the template entries before and after the current template entry in the ordered set where groupId = &#63; and infoItemClassName = &#63; and infoItemFormVariationKey = &#63;.
	 *
	 * @param templateEntryId the primary key of the current template entry
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @param infoItemFormVariationKey the info item form variation key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next template entry
	 * @throws NoSuchTemplateEntryException if a template entry with the primary key could not be found
	 */
	public TemplateEntry[] findByG_IICN_IIFVK_PrevAndNext(
			long templateEntryId, long groupId, String infoItemClassName,
			String infoItemFormVariationKey,
			com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
				orderByComparator)
		throws NoSuchTemplateEntryException;

	/**
	 * Returns all the template entries where groupId = any &#63; and infoItemClassName = &#63; and infoItemFormVariationKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param infoItemClassName the info item class name
	 * @param infoItemFormVariationKey the info item form variation key
	 * @return the matching template entries
	 */
	public java.util.List<TemplateEntry> findByG_IICN_IIFVK(
		long[] groupIds, String infoItemClassName,
		String infoItemFormVariationKey);

	/**
	 * Returns a range of all the template entries where groupId = any &#63; and infoItemClassName = &#63; and infoItemFormVariationKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param infoItemClassName the info item class name
	 * @param infoItemFormVariationKey the info item form variation key
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @return the range of matching template entries
	 */
	public java.util.List<TemplateEntry> findByG_IICN_IIFVK(
		long[] groupIds, String infoItemClassName,
		String infoItemFormVariationKey, int start, int end);

	/**
	 * Returns an ordered range of all the template entries where groupId = any &#63; and infoItemClassName = &#63; and infoItemFormVariationKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param infoItemClassName the info item class name
	 * @param infoItemFormVariationKey the info item form variation key
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching template entries
	 */
	public java.util.List<TemplateEntry> findByG_IICN_IIFVK(
		long[] groupIds, String infoItemClassName,
		String infoItemFormVariationKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the template entries where groupId = &#63; and infoItemClassName = &#63; and infoItemFormVariationKey = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @param infoItemFormVariationKey the info item form variation key
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching template entries
	 */
	public java.util.List<TemplateEntry> findByG_IICN_IIFVK(
		long[] groupIds, String infoItemClassName,
		String infoItemFormVariationKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the template entries where groupId = &#63; and infoItemClassName = &#63; and infoItemFormVariationKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @param infoItemFormVariationKey the info item form variation key
	 */
	public void removeByG_IICN_IIFVK(
		long groupId, String infoItemClassName,
		String infoItemFormVariationKey);

	/**
	 * Returns the number of template entries where groupId = &#63; and infoItemClassName = &#63; and infoItemFormVariationKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param infoItemClassName the info item class name
	 * @param infoItemFormVariationKey the info item form variation key
	 * @return the number of matching template entries
	 */
	public int countByG_IICN_IIFVK(
		long groupId, String infoItemClassName,
		String infoItemFormVariationKey);

	/**
	 * Returns the number of template entries where groupId = any &#63; and infoItemClassName = &#63; and infoItemFormVariationKey = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param infoItemClassName the info item class name
	 * @param infoItemFormVariationKey the info item form variation key
	 * @return the number of matching template entries
	 */
	public int countByG_IICN_IIFVK(
		long[] groupIds, String infoItemClassName,
		String infoItemFormVariationKey);

	/**
	 * Caches the template entry in the entity cache if it is enabled.
	 *
	 * @param templateEntry the template entry
	 */
	public void cacheResult(TemplateEntry templateEntry);

	/**
	 * Caches the template entries in the entity cache if it is enabled.
	 *
	 * @param templateEntries the template entries
	 */
	public void cacheResult(java.util.List<TemplateEntry> templateEntries);

	/**
	 * Creates a new template entry with the primary key. Does not add the template entry to the database.
	 *
	 * @param templateEntryId the primary key for the new template entry
	 * @return the new template entry
	 */
	public TemplateEntry create(long templateEntryId);

	/**
	 * Removes the template entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param templateEntryId the primary key of the template entry
	 * @return the template entry that was removed
	 * @throws NoSuchTemplateEntryException if a template entry with the primary key could not be found
	 */
	public TemplateEntry remove(long templateEntryId)
		throws NoSuchTemplateEntryException;

	public TemplateEntry updateImpl(TemplateEntry templateEntry);

	/**
	 * Returns the template entry with the primary key or throws a <code>NoSuchTemplateEntryException</code> if it could not be found.
	 *
	 * @param templateEntryId the primary key of the template entry
	 * @return the template entry
	 * @throws NoSuchTemplateEntryException if a template entry with the primary key could not be found
	 */
	public TemplateEntry findByPrimaryKey(long templateEntryId)
		throws NoSuchTemplateEntryException;

	/**
	 * Returns the template entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param templateEntryId the primary key of the template entry
	 * @return the template entry, or <code>null</code> if a template entry with the primary key could not be found
	 */
	public TemplateEntry fetchByPrimaryKey(long templateEntryId);

	/**
	 * Returns all the template entries.
	 *
	 * @return the template entries
	 */
	public java.util.List<TemplateEntry> findAll();

	/**
	 * Returns a range of all the template entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @return the range of template entries
	 */
	public java.util.List<TemplateEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the template entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of template entries
	 */
	public java.util.List<TemplateEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the template entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of template entries
	 * @param end the upper bound of the range of template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of template entries
	 */
	public java.util.List<TemplateEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<TemplateEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the template entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of template entries.
	 *
	 * @return the number of template entries
	 */
	public int countAll();

}
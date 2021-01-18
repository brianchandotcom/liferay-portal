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

package com.liferay.translation.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.translation.exception.NoSuchEntryException;
import com.liferay.translation.model.TranslationEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the translation entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TranslationEntryUtil
 * @generated
 */
@ProviderType
public interface TranslationEntryPersistence
	extends BasePersistence<TranslationEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link TranslationEntryUtil} to access the translation entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the translation entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching translation entries
	 */
	public java.util.List<TranslationEntry> findByUuid(String uuid);

	/**
	 * Returns a range of all the translation entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TranslationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of translation entries
	 * @param end the upper bound of the range of translation entries (not inclusive)
	 * @return the range of matching translation entries
	 */
	public java.util.List<TranslationEntry> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the translation entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TranslationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of translation entries
	 * @param end the upper bound of the range of translation entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching translation entries
	 */
	public java.util.List<TranslationEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<TranslationEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the translation entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TranslationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of translation entries
	 * @param end the upper bound of the range of translation entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching translation entries
	 */
	public java.util.List<TranslationEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<TranslationEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first translation entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching translation entry
	 * @throws NoSuchEntryException if a matching translation entry could not be found
	 */
	public TranslationEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<TranslationEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first translation entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching translation entry, or <code>null</code> if a matching translation entry could not be found
	 */
	public TranslationEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<TranslationEntry>
			orderByComparator);

	/**
	 * Returns the last translation entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching translation entry
	 * @throws NoSuchEntryException if a matching translation entry could not be found
	 */
	public TranslationEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<TranslationEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last translation entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching translation entry, or <code>null</code> if a matching translation entry could not be found
	 */
	public TranslationEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<TranslationEntry>
			orderByComparator);

	/**
	 * Returns the translation entries before and after the current translation entry in the ordered set where uuid = &#63;.
	 *
	 * @param translationEntryId the primary key of the current translation entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next translation entry
	 * @throws NoSuchEntryException if a translation entry with the primary key could not be found
	 */
	public TranslationEntry[] findByUuid_PrevAndNext(
			long translationEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<TranslationEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the translation entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of translation entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching translation entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the translation entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching translation entry
	 * @throws NoSuchEntryException if a matching translation entry could not be found
	 */
	public TranslationEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException;

	/**
	 * Returns the translation entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching translation entry, or <code>null</code> if a matching translation entry could not be found
	 */
	public TranslationEntry fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the translation entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching translation entry, or <code>null</code> if a matching translation entry could not be found
	 */
	public TranslationEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the translation entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the translation entry that was removed
	 */
	public TranslationEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException;

	/**
	 * Returns the number of translation entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching translation entries
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the translation entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching translation entries
	 */
	public java.util.List<TranslationEntry> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the translation entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TranslationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of translation entries
	 * @param end the upper bound of the range of translation entries (not inclusive)
	 * @return the range of matching translation entries
	 */
	public java.util.List<TranslationEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the translation entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TranslationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of translation entries
	 * @param end the upper bound of the range of translation entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching translation entries
	 */
	public java.util.List<TranslationEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<TranslationEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the translation entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TranslationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of translation entries
	 * @param end the upper bound of the range of translation entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching translation entries
	 */
	public java.util.List<TranslationEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<TranslationEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first translation entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching translation entry
	 * @throws NoSuchEntryException if a matching translation entry could not be found
	 */
	public TranslationEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<TranslationEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first translation entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching translation entry, or <code>null</code> if a matching translation entry could not be found
	 */
	public TranslationEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<TranslationEntry>
			orderByComparator);

	/**
	 * Returns the last translation entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching translation entry
	 * @throws NoSuchEntryException if a matching translation entry could not be found
	 */
	public TranslationEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<TranslationEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last translation entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching translation entry, or <code>null</code> if a matching translation entry could not be found
	 */
	public TranslationEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<TranslationEntry>
			orderByComparator);

	/**
	 * Returns the translation entries before and after the current translation entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param translationEntryId the primary key of the current translation entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next translation entry
	 * @throws NoSuchEntryException if a translation entry with the primary key could not be found
	 */
	public TranslationEntry[] findByUuid_C_PrevAndNext(
			long translationEntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<TranslationEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the translation entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of translation entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching translation entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the translation entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching translation entries
	 */
	public java.util.List<TranslationEntry> findByC_C(
		long classNameId, long classPK);

	/**
	 * Returns a range of all the translation entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TranslationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of translation entries
	 * @param end the upper bound of the range of translation entries (not inclusive)
	 * @return the range of matching translation entries
	 */
	public java.util.List<TranslationEntry> findByC_C(
		long classNameId, long classPK, int start, int end);

	/**
	 * Returns an ordered range of all the translation entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TranslationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of translation entries
	 * @param end the upper bound of the range of translation entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching translation entries
	 */
	public java.util.List<TranslationEntry> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<TranslationEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the translation entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TranslationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of translation entries
	 * @param end the upper bound of the range of translation entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching translation entries
	 */
	public java.util.List<TranslationEntry> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<TranslationEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first translation entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching translation entry
	 * @throws NoSuchEntryException if a matching translation entry could not be found
	 */
	public TranslationEntry findByC_C_First(
			long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<TranslationEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first translation entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching translation entry, or <code>null</code> if a matching translation entry could not be found
	 */
	public TranslationEntry fetchByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<TranslationEntry>
			orderByComparator);

	/**
	 * Returns the last translation entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching translation entry
	 * @throws NoSuchEntryException if a matching translation entry could not be found
	 */
	public TranslationEntry findByC_C_Last(
			long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<TranslationEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last translation entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching translation entry, or <code>null</code> if a matching translation entry could not be found
	 */
	public TranslationEntry fetchByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<TranslationEntry>
			orderByComparator);

	/**
	 * Returns the translation entries before and after the current translation entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param translationEntryId the primary key of the current translation entry
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next translation entry
	 * @throws NoSuchEntryException if a translation entry with the primary key could not be found
	 */
	public TranslationEntry[] findByC_C_PrevAndNext(
			long translationEntryId, long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<TranslationEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the translation entries where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public void removeByC_C(long classNameId, long classPK);

	/**
	 * Returns the number of translation entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching translation entries
	 */
	public int countByC_C(long classNameId, long classPK);

	/**
	 * Returns the translation entry where classNameId = &#63; and classPK = &#63; and languageId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @return the matching translation entry
	 * @throws NoSuchEntryException if a matching translation entry could not be found
	 */
	public TranslationEntry findByC_C_L(
			long classNameId, long classPK, String languageId)
		throws NoSuchEntryException;

	/**
	 * Returns the translation entry where classNameId = &#63; and classPK = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @return the matching translation entry, or <code>null</code> if a matching translation entry could not be found
	 */
	public TranslationEntry fetchByC_C_L(
		long classNameId, long classPK, String languageId);

	/**
	 * Returns the translation entry where classNameId = &#63; and classPK = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching translation entry, or <code>null</code> if a matching translation entry could not be found
	 */
	public TranslationEntry fetchByC_C_L(
		long classNameId, long classPK, String languageId,
		boolean useFinderCache);

	/**
	 * Removes the translation entry where classNameId = &#63; and classPK = &#63; and languageId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @return the translation entry that was removed
	 */
	public TranslationEntry removeByC_C_L(
			long classNameId, long classPK, String languageId)
		throws NoSuchEntryException;

	/**
	 * Returns the number of translation entries where classNameId = &#63; and classPK = &#63; and languageId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @return the number of matching translation entries
	 */
	public int countByC_C_L(long classNameId, long classPK, String languageId);

	/**
	 * Caches the translation entry in the entity cache if it is enabled.
	 *
	 * @param translationEntry the translation entry
	 */
	public void cacheResult(TranslationEntry translationEntry);

	/**
	 * Caches the translation entries in the entity cache if it is enabled.
	 *
	 * @param translationEntries the translation entries
	 */
	public void cacheResult(
		java.util.List<TranslationEntry> translationEntries);

	/**
	 * Creates a new translation entry with the primary key. Does not add the translation entry to the database.
	 *
	 * @param translationEntryId the primary key for the new translation entry
	 * @return the new translation entry
	 */
	public TranslationEntry create(long translationEntryId);

	/**
	 * Removes the translation entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param translationEntryId the primary key of the translation entry
	 * @return the translation entry that was removed
	 * @throws NoSuchEntryException if a translation entry with the primary key could not be found
	 */
	public TranslationEntry remove(long translationEntryId)
		throws NoSuchEntryException;

	public TranslationEntry updateImpl(TranslationEntry translationEntry);

	/**
	 * Returns the translation entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param translationEntryId the primary key of the translation entry
	 * @return the translation entry
	 * @throws NoSuchEntryException if a translation entry with the primary key could not be found
	 */
	public TranslationEntry findByPrimaryKey(long translationEntryId)
		throws NoSuchEntryException;

	/**
	 * Returns the translation entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param translationEntryId the primary key of the translation entry
	 * @return the translation entry, or <code>null</code> if a translation entry with the primary key could not be found
	 */
	public TranslationEntry fetchByPrimaryKey(long translationEntryId);

	/**
	 * Returns all the translation entries.
	 *
	 * @return the translation entries
	 */
	public java.util.List<TranslationEntry> findAll();

	/**
	 * Returns a range of all the translation entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TranslationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of translation entries
	 * @param end the upper bound of the range of translation entries (not inclusive)
	 * @return the range of translation entries
	 */
	public java.util.List<TranslationEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the translation entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TranslationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of translation entries
	 * @param end the upper bound of the range of translation entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of translation entries
	 */
	public java.util.List<TranslationEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<TranslationEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the translation entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TranslationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of translation entries
	 * @param end the upper bound of the range of translation entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of translation entries
	 */
	public java.util.List<TranslationEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<TranslationEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the translation entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of translation entries.
	 *
	 * @return the number of translation entries
	 */
	public int countAll();

}
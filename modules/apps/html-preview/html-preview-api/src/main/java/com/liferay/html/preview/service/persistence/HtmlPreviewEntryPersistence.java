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

package com.liferay.html.preview.service.persistence;

import com.liferay.html.preview.exception.NoSuchHtmlPreviewEntryException;
import com.liferay.html.preview.model.HtmlPreviewEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the html preview entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see HtmlPreviewEntryUtil
 * @generated
 */
@ProviderType
public interface HtmlPreviewEntryPersistence
	extends BasePersistence<HtmlPreviewEntry> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link HtmlPreviewEntryUtil} to access the html preview entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the html preview entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchHtmlPreviewEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching html preview entry
	 * @throws NoSuchHtmlPreviewEntryException if a matching html preview entry could not be found
	 */
	public HtmlPreviewEntry findByG_C_C(
			long groupId, long classNameId, long classPK)
		throws NoSuchHtmlPreviewEntryException;

	/**
	 * Returns the html preview entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching html preview entry, or <code>null</code> if a matching html preview entry could not be found
	 */
	public HtmlPreviewEntry fetchByG_C_C(
		long groupId, long classNameId, long classPK);

	/**
	 * Returns the html preview entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching html preview entry, or <code>null</code> if a matching html preview entry could not be found
	 */
	public HtmlPreviewEntry fetchByG_C_C(
		long groupId, long classNameId, long classPK, boolean useFinderCache);

	/**
	 * Removes the html preview entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the html preview entry that was removed
	 */
	public HtmlPreviewEntry removeByG_C_C(
			long groupId, long classNameId, long classPK)
		throws NoSuchHtmlPreviewEntryException;

	/**
	 * Returns the number of html preview entries where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching html preview entries
	 */
	public int countByG_C_C(long groupId, long classNameId, long classPK);

	/**
	 * Caches the html preview entry in the entity cache if it is enabled.
	 *
	 * @param htmlPreviewEntry the html preview entry
	 */
	public void cacheResult(HtmlPreviewEntry htmlPreviewEntry);

	/**
	 * Caches the html preview entries in the entity cache if it is enabled.
	 *
	 * @param htmlPreviewEntries the html preview entries
	 */
	public void cacheResult(
		java.util.List<HtmlPreviewEntry> htmlPreviewEntries);

	/**
	 * Creates a new html preview entry with the primary key. Does not add the html preview entry to the database.
	 *
	 * @param htmlPreviewEntryId the primary key for the new html preview entry
	 * @return the new html preview entry
	 */
	public HtmlPreviewEntry create(long htmlPreviewEntryId);

	/**
	 * Removes the html preview entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param htmlPreviewEntryId the primary key of the html preview entry
	 * @return the html preview entry that was removed
	 * @throws NoSuchHtmlPreviewEntryException if a html preview entry with the primary key could not be found
	 */
	public HtmlPreviewEntry remove(long htmlPreviewEntryId)
		throws NoSuchHtmlPreviewEntryException;

	public HtmlPreviewEntry updateImpl(HtmlPreviewEntry htmlPreviewEntry);

	/**
	 * Returns the html preview entry with the primary key or throws a <code>NoSuchHtmlPreviewEntryException</code> if it could not be found.
	 *
	 * @param htmlPreviewEntryId the primary key of the html preview entry
	 * @return the html preview entry
	 * @throws NoSuchHtmlPreviewEntryException if a html preview entry with the primary key could not be found
	 */
	public HtmlPreviewEntry findByPrimaryKey(long htmlPreviewEntryId)
		throws NoSuchHtmlPreviewEntryException;

	/**
	 * Returns the html preview entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param htmlPreviewEntryId the primary key of the html preview entry
	 * @return the html preview entry, or <code>null</code> if a html preview entry with the primary key could not be found
	 */
	public HtmlPreviewEntry fetchByPrimaryKey(long htmlPreviewEntryId);

	/**
	 * Returns all the html preview entries.
	 *
	 * @return the html preview entries
	 */
	public java.util.List<HtmlPreviewEntry> findAll();

	/**
	 * Returns a range of all the html preview entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>HtmlPreviewEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of html preview entries
	 * @param end the upper bound of the range of html preview entries (not inclusive)
	 * @return the range of html preview entries
	 */
	public java.util.List<HtmlPreviewEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the html preview entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>HtmlPreviewEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of html preview entries
	 * @param end the upper bound of the range of html preview entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of html preview entries
	 */
	public java.util.List<HtmlPreviewEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<HtmlPreviewEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the html preview entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>HtmlPreviewEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of html preview entries
	 * @param end the upper bound of the range of html preview entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of html preview entries
	 */
	public java.util.List<HtmlPreviewEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<HtmlPreviewEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the html preview entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of html preview entries.
	 *
	 * @return the number of html preview entries
	 */
	public int countAll();

}
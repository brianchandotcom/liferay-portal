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

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchLocalizedEntryException;
import com.liferay.portal.tools.service.builder.test.model.LocalizedEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the localized entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LocalizedEntryUtil
 * @generated
 */
@ProviderType
public interface LocalizedEntryPersistence
	extends BasePersistence<LocalizedEntry> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LocalizedEntryUtil} to access the localized entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Caches the localized entry in the entity cache if it is enabled.
	 *
	 * @param localizedEntry the localized entry
	 */
	public void cacheResult(LocalizedEntry localizedEntry);

	/**
	 * Caches the localized entries in the entity cache if it is enabled.
	 *
	 * @param localizedEntries the localized entries
	 */
	public void cacheResult(java.util.List<LocalizedEntry> localizedEntries);

	/**
	 * Creates a new localized entry with the primary key. Does not add the localized entry to the database.
	 *
	 * @param localizedEntryId the primary key for the new localized entry
	 * @return the new localized entry
	 */
	public LocalizedEntry create(long localizedEntryId);

	/**
	 * Removes the localized entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param localizedEntryId the primary key of the localized entry
	 * @return the localized entry that was removed
	 * @throws NoSuchLocalizedEntryException if a localized entry with the primary key could not be found
	 */
	public LocalizedEntry remove(long localizedEntryId)
		throws NoSuchLocalizedEntryException;

	public LocalizedEntry updateImpl(LocalizedEntry localizedEntry);

	/**
	 * Returns the localized entry with the primary key or throws a <code>NoSuchLocalizedEntryException</code> if it could not be found.
	 *
	 * @param localizedEntryId the primary key of the localized entry
	 * @return the localized entry
	 * @throws NoSuchLocalizedEntryException if a localized entry with the primary key could not be found
	 */
	public LocalizedEntry findByPrimaryKey(long localizedEntryId)
		throws NoSuchLocalizedEntryException;

	/**
	 * Returns the localized entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param localizedEntryId the primary key of the localized entry
	 * @return the localized entry, or <code>null</code> if a localized entry with the primary key could not be found
	 */
	public LocalizedEntry fetchByPrimaryKey(long localizedEntryId);

	/**
	 * Returns all the localized entries.
	 *
	 * @return the localized entries
	 */
	public java.util.List<LocalizedEntry> findAll();

	/**
	 * Returns a range of all the localized entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LocalizedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of localized entries
	 * @param end the upper bound of the range of localized entries (not inclusive)
	 * @return the range of localized entries
	 */
	public java.util.List<LocalizedEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the localized entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LocalizedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of localized entries
	 * @param end the upper bound of the range of localized entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of localized entries
	 */
	public java.util.List<LocalizedEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LocalizedEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the localized entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LocalizedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of localized entries
	 * @param end the upper bound of the range of localized entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of localized entries
	 */
	public java.util.List<LocalizedEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LocalizedEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the localized entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of localized entries.
	 *
	 * @return the number of localized entries
	 */
	public int countAll();

}
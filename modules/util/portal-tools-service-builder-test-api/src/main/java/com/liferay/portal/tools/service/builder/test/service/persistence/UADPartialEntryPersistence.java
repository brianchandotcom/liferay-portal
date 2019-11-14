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
import com.liferay.portal.tools.service.builder.test.exception.NoSuchUADPartialEntryException;
import com.liferay.portal.tools.service.builder.test.model.UADPartialEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the uad partial entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UADPartialEntryUtil
 * @generated
 */
@ProviderType
public interface UADPartialEntryPersistence
	extends BasePersistence<UADPartialEntry> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link UADPartialEntryUtil} to access the uad partial entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Caches the uad partial entry in the entity cache if it is enabled.
	 *
	 * @param uadPartialEntry the uad partial entry
	 */
	public void cacheResult(UADPartialEntry uadPartialEntry);

	/**
	 * Caches the uad partial entries in the entity cache if it is enabled.
	 *
	 * @param uadPartialEntries the uad partial entries
	 */
	public void cacheResult(java.util.List<UADPartialEntry> uadPartialEntries);

	/**
	 * Creates a new uad partial entry with the primary key. Does not add the uad partial entry to the database.
	 *
	 * @param uadPartialEntryId the primary key for the new uad partial entry
	 * @return the new uad partial entry
	 */
	public UADPartialEntry create(long uadPartialEntryId);

	/**
	 * Removes the uad partial entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param uadPartialEntryId the primary key of the uad partial entry
	 * @return the uad partial entry that was removed
	 * @throws NoSuchUADPartialEntryException if a uad partial entry with the primary key could not be found
	 */
	public UADPartialEntry remove(long uadPartialEntryId)
		throws NoSuchUADPartialEntryException;

	public UADPartialEntry updateImpl(UADPartialEntry uadPartialEntry);

	/**
	 * Returns the uad partial entry with the primary key or throws a <code>NoSuchUADPartialEntryException</code> if it could not be found.
	 *
	 * @param uadPartialEntryId the primary key of the uad partial entry
	 * @return the uad partial entry
	 * @throws NoSuchUADPartialEntryException if a uad partial entry with the primary key could not be found
	 */
	public UADPartialEntry findByPrimaryKey(long uadPartialEntryId)
		throws NoSuchUADPartialEntryException;

	/**
	 * Returns the uad partial entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param uadPartialEntryId the primary key of the uad partial entry
	 * @return the uad partial entry, or <code>null</code> if a uad partial entry with the primary key could not be found
	 */
	public UADPartialEntry fetchByPrimaryKey(long uadPartialEntryId);

	/**
	 * Returns all the uad partial entries.
	 *
	 * @return the uad partial entries
	 */
	public java.util.List<UADPartialEntry> findAll();

	/**
	 * Returns a range of all the uad partial entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UADPartialEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of uad partial entries
	 * @param end the upper bound of the range of uad partial entries (not inclusive)
	 * @return the range of uad partial entries
	 */
	public java.util.List<UADPartialEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the uad partial entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UADPartialEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of uad partial entries
	 * @param end the upper bound of the range of uad partial entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of uad partial entries
	 */
	public java.util.List<UADPartialEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<UADPartialEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the uad partial entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UADPartialEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of uad partial entries
	 * @param end the upper bound of the range of uad partial entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of uad partial entries
	 */
	public java.util.List<UADPartialEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<UADPartialEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the uad partial entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of uad partial entries.
	 *
	 * @return the number of uad partial entries
	 */
	public int countAll();

}
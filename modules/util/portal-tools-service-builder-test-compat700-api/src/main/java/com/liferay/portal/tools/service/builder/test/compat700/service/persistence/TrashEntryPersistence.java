/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat700.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.compat700.exception.NoSuchTrashEntryException;
import com.liferay.portal.tools.service.builder.test.compat700.model.TrashEntry;

import java.io.Serializable;

import java.util.Map;
import java.util.Set;

/**
 * The persistence interface for the trash entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TrashEntryUtil
 * @generated
 */
@ProviderType
public interface TrashEntryPersistence extends BasePersistence<TrashEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link TrashEntryUtil} to access the trash entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */
	@Override
	public Map<Serializable, TrashEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys);

	/**
	 * Caches the trash entry in the entity cache if it is enabled.
	 *
	 * @param trashEntry the trash entry
	 */
	public void cacheResult(TrashEntry trashEntry);

	/**
	 * Caches the trash entries in the entity cache if it is enabled.
	 *
	 * @param trashEntries the trash entries
	 */
	public void cacheResult(java.util.List<TrashEntry> trashEntries);

	/**
	 * Creates a new trash entry with the primary key. Does not add the trash entry to the database.
	 *
	 * @param trashEntryId the primary key for the new trash entry
	 * @return the new trash entry
	 */
	public TrashEntry create(long trashEntryId);

	/**
	 * Removes the trash entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param trashEntryId the primary key of the trash entry
	 * @return the trash entry that was removed
	 * @throws NoSuchTrashEntryException if a trash entry with the primary key could not be found
	 */
	public TrashEntry remove(long trashEntryId)
		throws NoSuchTrashEntryException;

	public TrashEntry updateImpl(TrashEntry trashEntry);

	/**
	 * Returns the trash entry with the primary key or throws a <code>NoSuchTrashEntryException</code> if it could not be found.
	 *
	 * @param trashEntryId the primary key of the trash entry
	 * @return the trash entry
	 * @throws NoSuchTrashEntryException if a trash entry with the primary key could not be found
	 */
	public TrashEntry findByPrimaryKey(long trashEntryId)
		throws NoSuchTrashEntryException;

	/**
	 * Returns the trash entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param trashEntryId the primary key of the trash entry
	 * @return the trash entry, or <code>null</code> if a trash entry with the primary key could not be found
	 */
	public TrashEntry fetchByPrimaryKey(long trashEntryId);

	/**
	 * Returns all the trash entries.
	 *
	 * @return the trash entries
	 */
	public java.util.List<TrashEntry> findAll();

	/**
	 * Returns a range of all the trash entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TrashEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of trash entries
	 * @param end the upper bound of the range of trash entries (not inclusive)
	 * @return the range of trash entries
	 */
	public java.util.List<TrashEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the trash entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TrashEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of trash entries
	 * @param end the upper bound of the range of trash entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of trash entries
	 */
	public java.util.List<TrashEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<TrashEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the trash entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TrashEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of trash entries
	 * @param end the upper bound of the range of trash entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of trash entries
	 */
	public java.util.List<TrashEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<TrashEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the trash entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of trash entries.
	 *
	 * @return the number of trash entries
	 */
	public int countAll();

}
// SB-Hash:1177128763
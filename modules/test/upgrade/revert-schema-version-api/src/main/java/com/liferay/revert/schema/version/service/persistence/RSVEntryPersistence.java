/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.revert.schema.version.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.revert.schema.version.exception.NoSuchEntryException;
import com.liferay.revert.schema.version.model.RSVEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the rsv entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RSVEntryUtil
 * @generated
 */
@ProviderType
public interface RSVEntryPersistence extends BasePersistence<RSVEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link RSVEntryUtil} to access the rsv entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Caches the rsv entry in the entity cache if it is enabled.
	 *
	 * @param rsvEntry the rsv entry
	 */
	public void cacheResult(RSVEntry rsvEntry);

	/**
	 * Caches the rsv entries in the entity cache if it is enabled.
	 *
	 * @param rsvEntries the rsv entries
	 */
	public void cacheResult(java.util.List<RSVEntry> rsvEntries);

	/**
	 * Creates a new rsv entry with the primary key. Does not add the rsv entry to the database.
	 *
	 * @param rsvEntryId the primary key for the new rsv entry
	 * @return the new rsv entry
	 */
	public RSVEntry create(long rsvEntryId);

	/**
	 * Removes the rsv entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param rsvEntryId the primary key of the rsv entry
	 * @return the rsv entry that was removed
	 * @throws NoSuchEntryException if a rsv entry with the primary key could not be found
	 */
	public RSVEntry remove(long rsvEntryId) throws NoSuchEntryException;

	public RSVEntry updateImpl(RSVEntry rsvEntry);

	/**
	 * Returns the rsv entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param rsvEntryId the primary key of the rsv entry
	 * @return the rsv entry
	 * @throws NoSuchEntryException if a rsv entry with the primary key could not be found
	 */
	public RSVEntry findByPrimaryKey(long rsvEntryId)
		throws NoSuchEntryException;

	/**
	 * Returns the rsv entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param rsvEntryId the primary key of the rsv entry
	 * @return the rsv entry, or <code>null</code> if a rsv entry with the primary key could not be found
	 */
	public RSVEntry fetchByPrimaryKey(long rsvEntryId);

	/**
	 * Returns all the rsv entries.
	 *
	 * @return the rsv entries
	 */
	public java.util.List<RSVEntry> findAll();

	/**
	 * Returns a range of all the rsv entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RSVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of rsv entries
	 * @param end the upper bound of the range of rsv entries (not inclusive)
	 * @return the range of rsv entries
	 */
	public java.util.List<RSVEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the rsv entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RSVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of rsv entries
	 * @param end the upper bound of the range of rsv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of rsv entries
	 */
	public java.util.List<RSVEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RSVEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the rsv entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RSVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of rsv entries
	 * @param end the upper bound of the range of rsv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of rsv entries
	 */
	public java.util.List<RSVEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RSVEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the rsv entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of rsv entries.
	 *
	 * @return the number of rsv entries
	 */
	public int countAll();

}
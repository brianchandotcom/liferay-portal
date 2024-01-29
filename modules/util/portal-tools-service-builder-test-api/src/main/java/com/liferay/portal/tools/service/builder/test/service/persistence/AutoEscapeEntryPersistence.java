/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchAutoEscapeEntryException;
import com.liferay.portal.tools.service.builder.test.model.AutoEscapeEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the auto escape entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AutoEscapeEntryUtil
 * @generated
 */
@ProviderType
public interface AutoEscapeEntryPersistence
	extends BasePersistence<AutoEscapeEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AutoEscapeEntryUtil} to access the auto escape entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Caches the auto escape entry in the entity cache if it is enabled.
	 *
	 * @param autoEscapeEntry the auto escape entry
	 */
	public void cacheResult(AutoEscapeEntry autoEscapeEntry);

	/**
	 * Caches the auto escape entries in the entity cache if it is enabled.
	 *
	 * @param autoEscapeEntries the auto escape entries
	 */
	public void cacheResult(java.util.List<AutoEscapeEntry> autoEscapeEntries);

	/**
	 * Creates a new auto escape entry with the primary key. Does not add the auto escape entry to the database.
	 *
	 * @param autoEscapeEntryId the primary key for the new auto escape entry
	 * @return the new auto escape entry
	 */
	public AutoEscapeEntry create(long autoEscapeEntryId);

	/**
	 * Removes the auto escape entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param autoEscapeEntryId the primary key of the auto escape entry
	 * @return the auto escape entry that was removed
	 * @throws NoSuchAutoEscapeEntryException if a auto escape entry with the primary key could not be found
	 */
	public AutoEscapeEntry remove(long autoEscapeEntryId)
		throws NoSuchAutoEscapeEntryException;

	public AutoEscapeEntry updateImpl(AutoEscapeEntry autoEscapeEntry);

	/**
	 * Returns the auto escape entry with the primary key or throws a <code>NoSuchAutoEscapeEntryException</code> if it could not be found.
	 *
	 * @param autoEscapeEntryId the primary key of the auto escape entry
	 * @return the auto escape entry
	 * @throws NoSuchAutoEscapeEntryException if a auto escape entry with the primary key could not be found
	 */
	public AutoEscapeEntry findByPrimaryKey(long autoEscapeEntryId)
		throws NoSuchAutoEscapeEntryException;

	/**
	 * Returns the auto escape entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param autoEscapeEntryId the primary key of the auto escape entry
	 * @return the auto escape entry, or <code>null</code> if a auto escape entry with the primary key could not be found
	 */
	public AutoEscapeEntry fetchByPrimaryKey(long autoEscapeEntryId);

	/**
	 * Returns all the auto escape entries.
	 *
	 * @return the auto escape entries
	 */
	public java.util.List<AutoEscapeEntry> findAll();

	/**
	 * Returns a range of all the auto escape entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AutoEscapeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of auto escape entries
	 * @param end the upper bound of the range of auto escape entries (not inclusive)
	 * @return the range of auto escape entries
	 */
	public java.util.List<AutoEscapeEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the auto escape entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AutoEscapeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of auto escape entries
	 * @param end the upper bound of the range of auto escape entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of auto escape entries
	 */
	public java.util.List<AutoEscapeEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AutoEscapeEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the auto escape entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AutoEscapeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of auto escape entries
	 * @param end the upper bound of the range of auto escape entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of auto escape entries
	 */
	public java.util.List<AutoEscapeEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AutoEscapeEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the auto escape entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of auto escape entries.
	 *
	 * @return the number of auto escape entries
	 */
	public int countAll();

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchManyColumnsEntryException;
import com.liferay.portal.tools.service.builder.test.model.ManyColumnsEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the many columns entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ManyColumnsEntryUtil
 * @generated
 */
@ProviderType
public interface ManyColumnsEntryPersistence
	extends BasePersistence<ManyColumnsEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ManyColumnsEntryUtil} to access the many columns entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Caches the many columns entry in the entity cache if it is enabled.
	 *
	 * @param manyColumnsEntry the many columns entry
	 */
	public void cacheResult(ManyColumnsEntry manyColumnsEntry);

	/**
	 * Caches the many columns entries in the entity cache if it is enabled.
	 *
	 * @param manyColumnsEntries the many columns entries
	 */
	public void cacheResult(
		java.util.List<ManyColumnsEntry> manyColumnsEntries);

	/**
	 * Creates a new many columns entry with the primary key. Does not add the many columns entry to the database.
	 *
	 * @param manyColumnsEntryId the primary key for the new many columns entry
	 * @return the new many columns entry
	 */
	public ManyColumnsEntry create(long manyColumnsEntryId);

	/**
	 * Removes the many columns entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param manyColumnsEntryId the primary key of the many columns entry
	 * @return the many columns entry that was removed
	 * @throws NoSuchManyColumnsEntryException if a many columns entry with the primary key could not be found
	 */
	public ManyColumnsEntry remove(long manyColumnsEntryId)
		throws NoSuchManyColumnsEntryException;

	public ManyColumnsEntry updateImpl(ManyColumnsEntry manyColumnsEntry);

	/**
	 * Returns the many columns entry with the primary key or throws a <code>NoSuchManyColumnsEntryException</code> if it could not be found.
	 *
	 * @param manyColumnsEntryId the primary key of the many columns entry
	 * @return the many columns entry
	 * @throws NoSuchManyColumnsEntryException if a many columns entry with the primary key could not be found
	 */
	public ManyColumnsEntry findByPrimaryKey(long manyColumnsEntryId)
		throws NoSuchManyColumnsEntryException;

	/**
	 * Returns the many columns entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param manyColumnsEntryId the primary key of the many columns entry
	 * @return the many columns entry, or <code>null</code> if a many columns entry with the primary key could not be found
	 */
	public ManyColumnsEntry fetchByPrimaryKey(long manyColumnsEntryId);

	/**
	 * Returns all the many columns entries.
	 *
	 * @return the many columns entries
	 */
	public java.util.List<ManyColumnsEntry> findAll();

	/**
	 * Returns a range of all the many columns entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ManyColumnsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of many columns entries
	 * @param end the upper bound of the range of many columns entries (not inclusive)
	 * @return the range of many columns entries
	 */
	public java.util.List<ManyColumnsEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the many columns entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ManyColumnsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of many columns entries
	 * @param end the upper bound of the range of many columns entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of many columns entries
	 */
	public java.util.List<ManyColumnsEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ManyColumnsEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the many columns entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ManyColumnsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of many columns entries
	 * @param end the upper bound of the range of many columns entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of many columns entries
	 */
	public java.util.List<ManyColumnsEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ManyColumnsEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the many columns entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of many columns entries.
	 *
	 * @return the number of many columns entries
	 */
	public int countAll();

}
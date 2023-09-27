/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchDataLimitEntryException;
import com.liferay.portal.tools.service.builder.test.model.DataLimitEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the data limit entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DataLimitEntryUtil
 * @generated
 */
@ProviderType
public interface DataLimitEntryPersistence
	extends BasePersistence<DataLimitEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DataLimitEntryUtil} to access the data limit entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Caches the data limit entry in the entity cache if it is enabled.
	 *
	 * @param dataLimitEntry the data limit entry
	 */
	public void cacheResult(DataLimitEntry dataLimitEntry);

	/**
	 * Caches the data limit entries in the entity cache if it is enabled.
	 *
	 * @param dataLimitEntries the data limit entries
	 */
	public void cacheResult(java.util.List<DataLimitEntry> dataLimitEntries);

	/**
	 * Creates a new data limit entry with the primary key. Does not add the data limit entry to the database.
	 *
	 * @param dataLimitEntryId the primary key for the new data limit entry
	 * @return the new data limit entry
	 */
	public DataLimitEntry create(long dataLimitEntryId);

	/**
	 * Removes the data limit entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dataLimitEntryId the primary key of the data limit entry
	 * @return the data limit entry that was removed
	 * @throws NoSuchDataLimitEntryException if a data limit entry with the primary key could not be found
	 */
	public DataLimitEntry remove(long dataLimitEntryId)
		throws NoSuchDataLimitEntryException;

	public DataLimitEntry updateImpl(DataLimitEntry dataLimitEntry);

	/**
	 * Returns the data limit entry with the primary key or throws a <code>NoSuchDataLimitEntryException</code> if it could not be found.
	 *
	 * @param dataLimitEntryId the primary key of the data limit entry
	 * @return the data limit entry
	 * @throws NoSuchDataLimitEntryException if a data limit entry with the primary key could not be found
	 */
	public DataLimitEntry findByPrimaryKey(long dataLimitEntryId)
		throws NoSuchDataLimitEntryException;

	/**
	 * Returns the data limit entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param dataLimitEntryId the primary key of the data limit entry
	 * @return the data limit entry, or <code>null</code> if a data limit entry with the primary key could not be found
	 */
	public DataLimitEntry fetchByPrimaryKey(long dataLimitEntryId);

	/**
	 * Returns all the data limit entries.
	 *
	 * @return the data limit entries
	 */
	public java.util.List<DataLimitEntry> findAll();

	/**
	 * Returns a range of all the data limit entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DataLimitEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of data limit entries
	 * @param end the upper bound of the range of data limit entries (not inclusive)
	 * @return the range of data limit entries
	 */
	public java.util.List<DataLimitEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the data limit entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DataLimitEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of data limit entries
	 * @param end the upper bound of the range of data limit entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of data limit entries
	 */
	public java.util.List<DataLimitEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DataLimitEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the data limit entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DataLimitEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of data limit entries
	 * @param end the upper bound of the range of data limit entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of data limit entries
	 */
	public java.util.List<DataLimitEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DataLimitEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the data limit entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of data limit entries.
	 *
	 * @return the number of data limit entries
	 */
	public int countAll();

}
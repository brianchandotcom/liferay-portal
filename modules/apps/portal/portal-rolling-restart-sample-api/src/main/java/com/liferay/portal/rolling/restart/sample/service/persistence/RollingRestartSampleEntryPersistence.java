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

package com.liferay.portal.rolling.restart.sample.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.rolling.restart.sample.exception.NoSuchRollingRestartSampleEntryException;
import com.liferay.portal.rolling.restart.sample.model.RollingRestartSampleEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the rolling restart sample entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RollingRestartSampleEntryUtil
 * @generated
 */
@ProviderType
public interface RollingRestartSampleEntryPersistence
	extends BasePersistence<RollingRestartSampleEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link RollingRestartSampleEntryUtil} to access the rolling restart sample entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Caches the rolling restart sample entry in the entity cache if it is enabled.
	 *
	 * @param rollingRestartSampleEntry the rolling restart sample entry
	 */
	public void cacheResult(
		RollingRestartSampleEntry rollingRestartSampleEntry);

	/**
	 * Caches the rolling restart sample entries in the entity cache if it is enabled.
	 *
	 * @param rollingRestartSampleEntries the rolling restart sample entries
	 */
	public void cacheResult(
		java.util.List<RollingRestartSampleEntry> rollingRestartSampleEntries);

	/**
	 * Creates a new rolling restart sample entry with the primary key. Does not add the rolling restart sample entry to the database.
	 *
	 * @param entryId the primary key for the new rolling restart sample entry
	 * @return the new rolling restart sample entry
	 */
	public RollingRestartSampleEntry create(long entryId);

	/**
	 * Removes the rolling restart sample entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param entryId the primary key of the rolling restart sample entry
	 * @return the rolling restart sample entry that was removed
	 * @throws NoSuchRollingRestartSampleEntryException if a rolling restart sample entry with the primary key could not be found
	 */
	public RollingRestartSampleEntry remove(long entryId)
		throws NoSuchRollingRestartSampleEntryException;

	public RollingRestartSampleEntry updateImpl(
		RollingRestartSampleEntry rollingRestartSampleEntry);

	/**
	 * Returns the rolling restart sample entry with the primary key or throws a <code>NoSuchRollingRestartSampleEntryException</code> if it could not be found.
	 *
	 * @param entryId the primary key of the rolling restart sample entry
	 * @return the rolling restart sample entry
	 * @throws NoSuchRollingRestartSampleEntryException if a rolling restart sample entry with the primary key could not be found
	 */
	public RollingRestartSampleEntry findByPrimaryKey(long entryId)
		throws NoSuchRollingRestartSampleEntryException;

	/**
	 * Returns the rolling restart sample entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param entryId the primary key of the rolling restart sample entry
	 * @return the rolling restart sample entry, or <code>null</code> if a rolling restart sample entry with the primary key could not be found
	 */
	public RollingRestartSampleEntry fetchByPrimaryKey(long entryId);

	/**
	 * Returns all the rolling restart sample entries.
	 *
	 * @return the rolling restart sample entries
	 */
	public java.util.List<RollingRestartSampleEntry> findAll();

	/**
	 * Returns a range of all the rolling restart sample entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RollingRestartSampleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of rolling restart sample entries
	 * @param end the upper bound of the range of rolling restart sample entries (not inclusive)
	 * @return the range of rolling restart sample entries
	 */
	public java.util.List<RollingRestartSampleEntry> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the rolling restart sample entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RollingRestartSampleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of rolling restart sample entries
	 * @param end the upper bound of the range of rolling restart sample entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of rolling restart sample entries
	 */
	public java.util.List<RollingRestartSampleEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<RollingRestartSampleEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the rolling restart sample entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RollingRestartSampleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of rolling restart sample entries
	 * @param end the upper bound of the range of rolling restart sample entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of rolling restart sample entries
	 */
	public java.util.List<RollingRestartSampleEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<RollingRestartSampleEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the rolling restart sample entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of rolling restart sample entries.
	 *
	 * @return the number of rolling restart sample entries
	 */
	public int countAll();

}
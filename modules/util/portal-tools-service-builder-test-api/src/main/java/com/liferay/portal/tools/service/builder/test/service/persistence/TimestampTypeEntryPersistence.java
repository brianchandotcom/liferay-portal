/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchTimestampTypeEntryException;
import com.liferay.portal.tools.service.builder.test.model.TimestampTypeEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the timestamp type entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TimestampTypeEntryUtil
 * @generated
 */
@ProviderType
public interface TimestampTypeEntryPersistence
	extends BasePersistence<TimestampTypeEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link TimestampTypeEntryUtil} to access the timestamp type entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Creates a new timestamp type entry with the primary key. Does not add the timestamp type entry to the database.
	 *
	 * @param timestampTypeEntryId the primary key for the new timestamp type entry
	 * @return the new timestamp type entry
	 */
	public TimestampTypeEntry create(long timestampTypeEntryId);

	/**
	 * Removes the timestamp type entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param timestampTypeEntryId the primary key of the timestamp type entry
	 * @return the timestamp type entry that was removed
	 * @throws NoSuchTimestampTypeEntryException if a timestamp type entry with the primary key could not be found
	 */
	public TimestampTypeEntry remove(long timestampTypeEntryId)
		throws NoSuchTimestampTypeEntryException;

	public TimestampTypeEntry updateImpl(TimestampTypeEntry timestampTypeEntry);

	/**
	 * Returns the timestamp type entry with the primary key or throws a <code>NoSuchTimestampTypeEntryException</code> if it could not be found.
	 *
	 * @param timestampTypeEntryId the primary key of the timestamp type entry
	 * @return the timestamp type entry
	 * @throws NoSuchTimestampTypeEntryException if a timestamp type entry with the primary key could not be found
	 */
	public TimestampTypeEntry findByPrimaryKey(long timestampTypeEntryId)
		throws NoSuchTimestampTypeEntryException;

	/**
	 * Returns the timestamp type entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param timestampTypeEntryId the primary key of the timestamp type entry
	 * @return the timestamp type entry, or <code>null</code> if a timestamp type entry with the primary key could not be found
	 */
	public TimestampTypeEntry fetchByPrimaryKey(long timestampTypeEntryId);

}
// LIFERAY-SERVICE-BUILDER-HASH:1266262262
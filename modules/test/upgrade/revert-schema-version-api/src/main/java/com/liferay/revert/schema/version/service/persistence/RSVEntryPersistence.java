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

}
// LIFERAY-SERVICE-BUILDER-HASH:-1626968862
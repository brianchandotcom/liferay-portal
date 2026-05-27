/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchUniqueFinderEntryException;
import com.liferay.portal.tools.service.builder.test.model.UniqueFinderEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the unique finder entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UniqueFinderEntryUtil
 * @generated
 */
@ProviderType
public interface UniqueFinderEntryPersistence
	extends BasePersistence<UniqueFinderEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link UniqueFinderEntryUtil} to access the unique finder entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the unique finder entry where name = &#63; or throws a <code>NoSuchUniqueFinderEntryException</code> if it could not be found.
	 *
	 * @param name the name
	 * @return the matching unique finder entry
	 * @throws NoSuchUniqueFinderEntryException if a matching unique finder entry could not be found
	 */
	public UniqueFinderEntry findByName(String name)
		throws NoSuchUniqueFinderEntryException;

	/**
	 * Returns the unique finder entry where name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching unique finder entry, or <code>null</code> if a matching unique finder entry could not be found
	 */
	public UniqueFinderEntry fetchByName(String name, boolean useFinderCache);

	/**
	 * Removes the unique finder entry where name = &#63; from the database.
	 *
	 * @param name the name
	 * @return the unique finder entry that was removed
	 */
	public UniqueFinderEntry removeByName(String name)
		throws NoSuchUniqueFinderEntryException;

	/**
	 * Returns the number of unique finder entries where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching unique finder entries
	 */
	public int countByName(String name);

	/**
	 * Creates a new unique finder entry with the primary key. Does not add the unique finder entry to the database.
	 *
	 * @param uniqueFinderEntryId the primary key for the new unique finder entry
	 * @return the new unique finder entry
	 */
	public UniqueFinderEntry create(long uniqueFinderEntryId);

	/**
	 * Removes the unique finder entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param uniqueFinderEntryId the primary key of the unique finder entry
	 * @return the unique finder entry that was removed
	 * @throws NoSuchUniqueFinderEntryException if a unique finder entry with the primary key could not be found
	 */
	public UniqueFinderEntry remove(long uniqueFinderEntryId)
		throws NoSuchUniqueFinderEntryException;

	public UniqueFinderEntry updateImpl(UniqueFinderEntry uniqueFinderEntry);

	/**
	 * Returns the unique finder entry with the primary key or throws a <code>NoSuchUniqueFinderEntryException</code> if it could not be found.
	 *
	 * @param uniqueFinderEntryId the primary key of the unique finder entry
	 * @return the unique finder entry
	 * @throws NoSuchUniqueFinderEntryException if a unique finder entry with the primary key could not be found
	 */
	public UniqueFinderEntry findByPrimaryKey(long uniqueFinderEntryId)
		throws NoSuchUniqueFinderEntryException;

	/**
	 * Returns the unique finder entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param uniqueFinderEntryId the primary key of the unique finder entry
	 * @return the unique finder entry, or <code>null</code> if a unique finder entry with the primary key could not be found
	 */
	public UniqueFinderEntry fetchByPrimaryKey(long uniqueFinderEntryId);

	/**
	 * Returns the unique finder entry where name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param name the name
	 * @return the matching unique finder entry, or <code>null</code> if a matching unique finder entry could not be found
	 */
	public default UniqueFinderEntry fetchByName(String name) {
		return fetchByName(name, true);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:2049386339
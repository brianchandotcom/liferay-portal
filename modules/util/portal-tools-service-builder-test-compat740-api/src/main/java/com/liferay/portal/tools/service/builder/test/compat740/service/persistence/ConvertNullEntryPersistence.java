/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat740.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.compat740.exception.NoSuchConvertNullEntryException;
import com.liferay.portal.tools.service.builder.test.compat740.model.ConvertNullEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the convert null entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ConvertNullEntryUtil
 * @generated
 */
@ProviderType
public interface ConvertNullEntryPersistence
	extends BasePersistence<ConvertNullEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ConvertNullEntryUtil} to access the convert null entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the convert null entry where name = &#63; or throws a <code>NoSuchConvertNullEntryException</code> if it could not be found.
	 *
	 * @param name the name
	 * @return the matching convert null entry
	 * @throws NoSuchConvertNullEntryException if a matching convert null entry could not be found
	 */
	public ConvertNullEntry findByName(String name)
		throws NoSuchConvertNullEntryException;

	/**
	 * Returns the convert null entry where name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching convert null entry, or <code>null</code> if a matching convert null entry could not be found
	 */
	public ConvertNullEntry fetchByName(String name, boolean useFinderCache);

	/**
	 * Removes the convert null entry where name = &#63; from the database.
	 *
	 * @param name the name
	 * @return the convert null entry that was removed
	 */
	public ConvertNullEntry removeByName(String name)
		throws NoSuchConvertNullEntryException;

	/**
	 * Returns the number of convert null entries where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching convert null entries
	 */
	public int countByName(String name);

	/**
	 * Creates a new convert null entry with the primary key. Does not add the convert null entry to the database.
	 *
	 * @param convertNullEntryId the primary key for the new convert null entry
	 * @return the new convert null entry
	 */
	public ConvertNullEntry create(long convertNullEntryId);

	/**
	 * Removes the convert null entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param convertNullEntryId the primary key of the convert null entry
	 * @return the convert null entry that was removed
	 * @throws NoSuchConvertNullEntryException if a convert null entry with the primary key could not be found
	 */
	public ConvertNullEntry remove(long convertNullEntryId)
		throws NoSuchConvertNullEntryException;

	public ConvertNullEntry updateImpl(ConvertNullEntry convertNullEntry);

	/**
	 * Returns the convert null entry with the primary key or throws a <code>NoSuchConvertNullEntryException</code> if it could not be found.
	 *
	 * @param convertNullEntryId the primary key of the convert null entry
	 * @return the convert null entry
	 * @throws NoSuchConvertNullEntryException if a convert null entry with the primary key could not be found
	 */
	public ConvertNullEntry findByPrimaryKey(long convertNullEntryId)
		throws NoSuchConvertNullEntryException;

	/**
	 * Returns the convert null entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param convertNullEntryId the primary key of the convert null entry
	 * @return the convert null entry, or <code>null</code> if a convert null entry with the primary key could not be found
	 */
	public ConvertNullEntry fetchByPrimaryKey(long convertNullEntryId);

	/**
	 * Returns the convert null entry where name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param name the name
	 * @return the matching convert null entry, or <code>null</code> if a matching convert null entry could not be found
	 */
	public default ConvertNullEntry fetchByName(String name) {
		return fetchByName(name, true);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-1563578228
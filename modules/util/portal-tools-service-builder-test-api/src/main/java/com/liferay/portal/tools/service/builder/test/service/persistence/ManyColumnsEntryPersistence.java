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

}
// LIFERAY-SERVICE-BUILDER-HASH:754447734
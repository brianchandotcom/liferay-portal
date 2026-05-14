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

}
// LIFERAY-SERVICE-BUILDER-HASH:436977222
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat740.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.compat740.exception.NoSuchTrashEntryException;
import com.liferay.portal.tools.service.builder.test.compat740.model.TrashEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the trash entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TrashEntryUtil
 * @generated
 */
@ProviderType
public interface TrashEntryPersistence extends BasePersistence<TrashEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link TrashEntryUtil} to access the trash entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Creates a new trash entry with the primary key. Does not add the trash entry to the database.
	 *
	 * @param trashEntryId the primary key for the new trash entry
	 * @return the new trash entry
	 */
	public TrashEntry create(long trashEntryId);

	/**
	 * Removes the trash entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param trashEntryId the primary key of the trash entry
	 * @return the trash entry that was removed
	 * @throws NoSuchTrashEntryException if a trash entry with the primary key could not be found
	 */
	public TrashEntry remove(long trashEntryId)
		throws NoSuchTrashEntryException;

	public TrashEntry updateImpl(TrashEntry trashEntry);

	/**
	 * Returns the trash entry with the primary key or throws a <code>NoSuchTrashEntryException</code> if it could not be found.
	 *
	 * @param trashEntryId the primary key of the trash entry
	 * @return the trash entry
	 * @throws NoSuchTrashEntryException if a trash entry with the primary key could not be found
	 */
	public TrashEntry findByPrimaryKey(long trashEntryId)
		throws NoSuchTrashEntryException;

	/**
	 * Returns the trash entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param trashEntryId the primary key of the trash entry
	 * @return the trash entry, or <code>null</code> if a trash entry with the primary key could not be found
	 */
	public TrashEntry fetchByPrimaryKey(long trashEntryId);

}
// LIFERAY-SERVICE-BUILDER-HASH:-1672666569
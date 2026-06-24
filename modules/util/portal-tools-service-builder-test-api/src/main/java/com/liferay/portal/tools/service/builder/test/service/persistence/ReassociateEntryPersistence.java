/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchReassociateEntryException;
import com.liferay.portal.tools.service.builder.test.model.ReassociateEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the reassociate entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ReassociateEntryUtil
 * @generated
 */
@ProviderType
public interface ReassociateEntryPersistence
	extends BasePersistence<ReassociateEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ReassociateEntryUtil} to access the reassociate entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Creates a new reassociate entry with the primary key. Does not add the reassociate entry to the database.
	 *
	 * @param reassociateEntryId the primary key for the new reassociate entry
	 * @return the new reassociate entry
	 */
	public ReassociateEntry create(long reassociateEntryId);

	/**
	 * Removes the reassociate entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param reassociateEntryId the primary key of the reassociate entry
	 * @return the reassociate entry that was removed
	 * @throws NoSuchReassociateEntryException if a reassociate entry with the primary key could not be found
	 */
	public ReassociateEntry remove(long reassociateEntryId)
		throws NoSuchReassociateEntryException;

	public ReassociateEntry updateImpl(ReassociateEntry reassociateEntry);

	/**
	 * Returns the reassociate entry with the primary key or throws a <code>NoSuchReassociateEntryException</code> if it could not be found.
	 *
	 * @param reassociateEntryId the primary key of the reassociate entry
	 * @return the reassociate entry
	 * @throws NoSuchReassociateEntryException if a reassociate entry with the primary key could not be found
	 */
	public ReassociateEntry findByPrimaryKey(long reassociateEntryId)
		throws NoSuchReassociateEntryException;

	/**
	 * Returns the reassociate entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param reassociateEntryId the primary key of the reassociate entry
	 * @return the reassociate entry, or <code>null</code> if a reassociate entry with the primary key could not be found
	 */
	public ReassociateEntry fetchByPrimaryKey(long reassociateEntryId);

}
// LIFERAY-SERVICE-BUILDER-HASH:930991194
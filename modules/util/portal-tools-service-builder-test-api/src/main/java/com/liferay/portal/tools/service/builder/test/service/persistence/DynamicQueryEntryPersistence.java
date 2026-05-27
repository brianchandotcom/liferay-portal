/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchDynamicQueryEntryException;
import com.liferay.portal.tools.service.builder.test.model.DynamicQueryEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the dynamic query entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DynamicQueryEntryUtil
 * @generated
 */
@ProviderType
public interface DynamicQueryEntryPersistence
	extends BasePersistence<DynamicQueryEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DynamicQueryEntryUtil} to access the dynamic query entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Creates a new dynamic query entry with the primary key. Does not add the dynamic query entry to the database.
	 *
	 * @param dynamicQueryEntryId the primary key for the new dynamic query entry
	 * @return the new dynamic query entry
	 */
	public DynamicQueryEntry create(long dynamicQueryEntryId);

	/**
	 * Removes the dynamic query entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dynamicQueryEntryId the primary key of the dynamic query entry
	 * @return the dynamic query entry that was removed
	 * @throws NoSuchDynamicQueryEntryException if a dynamic query entry with the primary key could not be found
	 */
	public DynamicQueryEntry remove(long dynamicQueryEntryId)
		throws NoSuchDynamicQueryEntryException;

	public DynamicQueryEntry updateImpl(DynamicQueryEntry dynamicQueryEntry);

	/**
	 * Returns the dynamic query entry with the primary key or throws a <code>NoSuchDynamicQueryEntryException</code> if it could not be found.
	 *
	 * @param dynamicQueryEntryId the primary key of the dynamic query entry
	 * @return the dynamic query entry
	 * @throws NoSuchDynamicQueryEntryException if a dynamic query entry with the primary key could not be found
	 */
	public DynamicQueryEntry findByPrimaryKey(long dynamicQueryEntryId)
		throws NoSuchDynamicQueryEntryException;

	/**
	 * Returns the dynamic query entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param dynamicQueryEntryId the primary key of the dynamic query entry
	 * @return the dynamic query entry, or <code>null</code> if a dynamic query entry with the primary key could not be found
	 */
	public DynamicQueryEntry fetchByPrimaryKey(long dynamicQueryEntryId);

}
// LIFERAY-SERVICE-BUILDER-HASH:-1879537672
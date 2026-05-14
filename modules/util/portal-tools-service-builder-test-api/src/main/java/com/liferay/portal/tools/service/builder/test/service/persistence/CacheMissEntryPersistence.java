/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchCacheMissEntryException;
import com.liferay.portal.tools.service.builder.test.model.CacheMissEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the cache miss entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CacheMissEntryUtil
 * @generated
 */
@ProviderType
public interface CacheMissEntryPersistence
	extends BasePersistence<CacheMissEntry>, CTPersistence<CacheMissEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CacheMissEntryUtil} to access the cache miss entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Creates a new cache miss entry with the primary key. Does not add the cache miss entry to the database.
	 *
	 * @param cacheMissEntryId the primary key for the new cache miss entry
	 * @return the new cache miss entry
	 */
	public CacheMissEntry create(long cacheMissEntryId);

	/**
	 * Removes the cache miss entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param cacheMissEntryId the primary key of the cache miss entry
	 * @return the cache miss entry that was removed
	 * @throws NoSuchCacheMissEntryException if a cache miss entry with the primary key could not be found
	 */
	public CacheMissEntry remove(long cacheMissEntryId)
		throws NoSuchCacheMissEntryException;

	public CacheMissEntry updateImpl(CacheMissEntry cacheMissEntry);

	/**
	 * Returns the cache miss entry with the primary key or throws a <code>NoSuchCacheMissEntryException</code> if it could not be found.
	 *
	 * @param cacheMissEntryId the primary key of the cache miss entry
	 * @return the cache miss entry
	 * @throws NoSuchCacheMissEntryException if a cache miss entry with the primary key could not be found
	 */
	public CacheMissEntry findByPrimaryKey(long cacheMissEntryId)
		throws NoSuchCacheMissEntryException;

	/**
	 * Returns the cache miss entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param cacheMissEntryId the primary key of the cache miss entry
	 * @return the cache miss entry, or <code>null</code> if a cache miss entry with the primary key could not be found
	 */
	public CacheMissEntry fetchByPrimaryKey(long cacheMissEntryId);

}
// LIFERAY-SERVICE-BUILDER-HASH:-476643536
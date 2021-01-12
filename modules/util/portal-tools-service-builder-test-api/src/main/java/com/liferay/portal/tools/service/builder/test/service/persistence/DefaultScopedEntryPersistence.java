/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchDefaultScopedEntryException;
import com.liferay.portal.tools.service.builder.test.model.DefaultScopedEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the default scoped entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DefaultScopedEntryUtil
 * @generated
 */
@ProviderType
public interface DefaultScopedEntryPersistence
	extends BasePersistence<DefaultScopedEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DefaultScopedEntryUtil} to access the default scoped entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the default scoped entry where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchDefaultScopedEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching default scoped entry
	 * @throws NoSuchDefaultScopedEntryException if a matching default scoped entry could not be found
	 */
	public DefaultScopedEntry findByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchDefaultScopedEntryException;

	/**
	 * Returns the default scoped entry where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching default scoped entry, or <code>null</code> if a matching default scoped entry could not be found
	 */
	public DefaultScopedEntry fetchByC_ERC(
		long companyId, String externalReferenceCode);

	/**
	 * Returns the default scoped entry where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching default scoped entry, or <code>null</code> if a matching default scoped entry could not be found
	 */
	public DefaultScopedEntry fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache);

	/**
	 * Removes the default scoped entry where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the default scoped entry that was removed
	 */
	public DefaultScopedEntry removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchDefaultScopedEntryException;

	/**
	 * Returns the number of default scoped entries where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching default scoped entries
	 */
	public int countByC_ERC(long companyId, String externalReferenceCode);

	/**
	 * Caches the default scoped entry in the entity cache if it is enabled.
	 *
	 * @param defaultScopedEntry the default scoped entry
	 */
	public void cacheResult(DefaultScopedEntry defaultScopedEntry);

	/**
	 * Caches the default scoped entries in the entity cache if it is enabled.
	 *
	 * @param defaultScopedEntries the default scoped entries
	 */
	public void cacheResult(
		java.util.List<DefaultScopedEntry> defaultScopedEntries);

	/**
	 * Creates a new default scoped entry with the primary key. Does not add the default scoped entry to the database.
	 *
	 * @param DefaultScopedEntryId the primary key for the new default scoped entry
	 * @return the new default scoped entry
	 */
	public DefaultScopedEntry create(long DefaultScopedEntryId);

	/**
	 * Removes the default scoped entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param DefaultScopedEntryId the primary key of the default scoped entry
	 * @return the default scoped entry that was removed
	 * @throws NoSuchDefaultScopedEntryException if a default scoped entry with the primary key could not be found
	 */
	public DefaultScopedEntry remove(long DefaultScopedEntryId)
		throws NoSuchDefaultScopedEntryException;

	public DefaultScopedEntry updateImpl(DefaultScopedEntry defaultScopedEntry);

	/**
	 * Returns the default scoped entry with the primary key or throws a <code>NoSuchDefaultScopedEntryException</code> if it could not be found.
	 *
	 * @param DefaultScopedEntryId the primary key of the default scoped entry
	 * @return the default scoped entry
	 * @throws NoSuchDefaultScopedEntryException if a default scoped entry with the primary key could not be found
	 */
	public DefaultScopedEntry findByPrimaryKey(long DefaultScopedEntryId)
		throws NoSuchDefaultScopedEntryException;

	/**
	 * Returns the default scoped entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param DefaultScopedEntryId the primary key of the default scoped entry
	 * @return the default scoped entry, or <code>null</code> if a default scoped entry with the primary key could not be found
	 */
	public DefaultScopedEntry fetchByPrimaryKey(long DefaultScopedEntryId);

	/**
	 * Returns all the default scoped entries.
	 *
	 * @return the default scoped entries
	 */
	public java.util.List<DefaultScopedEntry> findAll();

	/**
	 * Returns a range of all the default scoped entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DefaultScopedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of default scoped entries
	 * @param end the upper bound of the range of default scoped entries (not inclusive)
	 * @return the range of default scoped entries
	 */
	public java.util.List<DefaultScopedEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the default scoped entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DefaultScopedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of default scoped entries
	 * @param end the upper bound of the range of default scoped entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of default scoped entries
	 */
	public java.util.List<DefaultScopedEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DefaultScopedEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the default scoped entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DefaultScopedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of default scoped entries
	 * @param end the upper bound of the range of default scoped entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of default scoped entries
	 */
	public java.util.List<DefaultScopedEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DefaultScopedEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the default scoped entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of default scoped entries.
	 *
	 * @return the number of default scoped entries
	 */
	public int countAll();

}
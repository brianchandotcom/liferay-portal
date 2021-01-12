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
import com.liferay.portal.tools.service.builder.test.exception.NoSuchCompanyScopedEntryException;
import com.liferay.portal.tools.service.builder.test.model.CompanyScopedEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the company scoped entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CompanyScopedEntryUtil
 * @generated
 */
@ProviderType
public interface CompanyScopedEntryPersistence
	extends BasePersistence<CompanyScopedEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CompanyScopedEntryUtil} to access the company scoped entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the company scoped entry where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchCompanyScopedEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching company scoped entry
	 * @throws NoSuchCompanyScopedEntryException if a matching company scoped entry could not be found
	 */
	public CompanyScopedEntry findByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchCompanyScopedEntryException;

	/**
	 * Returns the company scoped entry where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching company scoped entry, or <code>null</code> if a matching company scoped entry could not be found
	 */
	public CompanyScopedEntry fetchByC_ERC(
		long companyId, String externalReferenceCode);

	/**
	 * Returns the company scoped entry where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching company scoped entry, or <code>null</code> if a matching company scoped entry could not be found
	 */
	public CompanyScopedEntry fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache);

	/**
	 * Removes the company scoped entry where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the company scoped entry that was removed
	 */
	public CompanyScopedEntry removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchCompanyScopedEntryException;

	/**
	 * Returns the number of company scoped entries where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching company scoped entries
	 */
	public int countByC_ERC(long companyId, String externalReferenceCode);

	/**
	 * Caches the company scoped entry in the entity cache if it is enabled.
	 *
	 * @param companyScopedEntry the company scoped entry
	 */
	public void cacheResult(CompanyScopedEntry companyScopedEntry);

	/**
	 * Caches the company scoped entries in the entity cache if it is enabled.
	 *
	 * @param companyScopedEntries the company scoped entries
	 */
	public void cacheResult(
		java.util.List<CompanyScopedEntry> companyScopedEntries);

	/**
	 * Creates a new company scoped entry with the primary key. Does not add the company scoped entry to the database.
	 *
	 * @param CompanyScopedEntryId the primary key for the new company scoped entry
	 * @return the new company scoped entry
	 */
	public CompanyScopedEntry create(long CompanyScopedEntryId);

	/**
	 * Removes the company scoped entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CompanyScopedEntryId the primary key of the company scoped entry
	 * @return the company scoped entry that was removed
	 * @throws NoSuchCompanyScopedEntryException if a company scoped entry with the primary key could not be found
	 */
	public CompanyScopedEntry remove(long CompanyScopedEntryId)
		throws NoSuchCompanyScopedEntryException;

	public CompanyScopedEntry updateImpl(CompanyScopedEntry companyScopedEntry);

	/**
	 * Returns the company scoped entry with the primary key or throws a <code>NoSuchCompanyScopedEntryException</code> if it could not be found.
	 *
	 * @param CompanyScopedEntryId the primary key of the company scoped entry
	 * @return the company scoped entry
	 * @throws NoSuchCompanyScopedEntryException if a company scoped entry with the primary key could not be found
	 */
	public CompanyScopedEntry findByPrimaryKey(long CompanyScopedEntryId)
		throws NoSuchCompanyScopedEntryException;

	/**
	 * Returns the company scoped entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CompanyScopedEntryId the primary key of the company scoped entry
	 * @return the company scoped entry, or <code>null</code> if a company scoped entry with the primary key could not be found
	 */
	public CompanyScopedEntry fetchByPrimaryKey(long CompanyScopedEntryId);

	/**
	 * Returns all the company scoped entries.
	 *
	 * @return the company scoped entries
	 */
	public java.util.List<CompanyScopedEntry> findAll();

	/**
	 * Returns a range of all the company scoped entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CompanyScopedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of company scoped entries
	 * @param end the upper bound of the range of company scoped entries (not inclusive)
	 * @return the range of company scoped entries
	 */
	public java.util.List<CompanyScopedEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the company scoped entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CompanyScopedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of company scoped entries
	 * @param end the upper bound of the range of company scoped entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of company scoped entries
	 */
	public java.util.List<CompanyScopedEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CompanyScopedEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the company scoped entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CompanyScopedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of company scoped entries
	 * @param end the upper bound of the range of company scoped entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of company scoped entries
	 */
	public java.util.List<CompanyScopedEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CompanyScopedEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the company scoped entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of company scoped entries.
	 *
	 * @return the number of company scoped entries
	 */
	public int countAll();

}
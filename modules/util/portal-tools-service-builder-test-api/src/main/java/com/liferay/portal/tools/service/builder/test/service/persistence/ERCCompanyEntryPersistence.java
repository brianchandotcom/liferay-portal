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
import com.liferay.portal.tools.service.builder.test.exception.NoSuchERCCompanyEntryException;
import com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the erc company entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ERCCompanyEntryUtil
 * @generated
 */
@ProviderType
public interface ERCCompanyEntryPersistence
	extends BasePersistence<ERCCompanyEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ERCCompanyEntryUtil} to access the erc company entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the erc company entry where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchERCCompanyEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching erc company entry
	 * @throws NoSuchERCCompanyEntryException if a matching erc company entry could not be found
	 */
	public ERCCompanyEntry findByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchERCCompanyEntryException;

	/**
	 * Returns the erc company entry where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching erc company entry, or <code>null</code> if a matching erc company entry could not be found
	 */
	public ERCCompanyEntry fetchByC_ERC(
		long companyId, String externalReferenceCode);

	/**
	 * Returns the erc company entry where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching erc company entry, or <code>null</code> if a matching erc company entry could not be found
	 */
	public ERCCompanyEntry fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache);

	/**
	 * Removes the erc company entry where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the erc company entry that was removed
	 */
	public ERCCompanyEntry removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchERCCompanyEntryException;

	/**
	 * Returns the number of erc company entries where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching erc company entries
	 */
	public int countByC_ERC(long companyId, String externalReferenceCode);

	/**
	 * Caches the erc company entry in the entity cache if it is enabled.
	 *
	 * @param ercCompanyEntry the erc company entry
	 */
	public void cacheResult(ERCCompanyEntry ercCompanyEntry);

	/**
	 * Caches the erc company entries in the entity cache if it is enabled.
	 *
	 * @param ercCompanyEntries the erc company entries
	 */
	public void cacheResult(java.util.List<ERCCompanyEntry> ercCompanyEntries);

	/**
	 * Creates a new erc company entry with the primary key. Does not add the erc company entry to the database.
	 *
	 * @param ercCompanyEntryId the primary key for the new erc company entry
	 * @return the new erc company entry
	 */
	public ERCCompanyEntry create(long ercCompanyEntryId);

	/**
	 * Removes the erc company entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ercCompanyEntryId the primary key of the erc company entry
	 * @return the erc company entry that was removed
	 * @throws NoSuchERCCompanyEntryException if a erc company entry with the primary key could not be found
	 */
	public ERCCompanyEntry remove(long ercCompanyEntryId)
		throws NoSuchERCCompanyEntryException;

	public ERCCompanyEntry updateImpl(ERCCompanyEntry ercCompanyEntry);

	/**
	 * Returns the erc company entry with the primary key or throws a <code>NoSuchERCCompanyEntryException</code> if it could not be found.
	 *
	 * @param ercCompanyEntryId the primary key of the erc company entry
	 * @return the erc company entry
	 * @throws NoSuchERCCompanyEntryException if a erc company entry with the primary key could not be found
	 */
	public ERCCompanyEntry findByPrimaryKey(long ercCompanyEntryId)
		throws NoSuchERCCompanyEntryException;

	/**
	 * Returns the erc company entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ercCompanyEntryId the primary key of the erc company entry
	 * @return the erc company entry, or <code>null</code> if a erc company entry with the primary key could not be found
	 */
	public ERCCompanyEntry fetchByPrimaryKey(long ercCompanyEntryId);

	/**
	 * Returns all the erc company entries.
	 *
	 * @return the erc company entries
	 */
	public java.util.List<ERCCompanyEntry> findAll();

	/**
	 * Returns a range of all the erc company entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCCompanyEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc company entries
	 * @param end the upper bound of the range of erc company entries (not inclusive)
	 * @return the range of erc company entries
	 */
	public java.util.List<ERCCompanyEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the erc company entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCCompanyEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc company entries
	 * @param end the upper bound of the range of erc company entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of erc company entries
	 */
	public java.util.List<ERCCompanyEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ERCCompanyEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the erc company entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCCompanyEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc company entries
	 * @param end the upper bound of the range of erc company entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of erc company entries
	 */
	public java.util.List<ERCCompanyEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ERCCompanyEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the erc company entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of erc company entries.
	 *
	 * @return the number of erc company entries
	 */
	public int countAll();

}
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
import com.liferay.portal.tools.service.builder.test.exception.NoSuchERCGroupEntryException;
import com.liferay.portal.tools.service.builder.test.model.ERCGroupEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the erc group entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ERCGroupEntryUtil
 * @generated
 */
@ProviderType
public interface ERCGroupEntryPersistence
	extends BasePersistence<ERCGroupEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ERCGroupEntryUtil} to access the erc group entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the erc group entry where groupId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchERCGroupEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching erc group entry
	 * @throws NoSuchERCGroupEntryException if a matching erc group entry could not be found
	 */
	public ERCGroupEntry findByG_ERC(long groupId, String externalReferenceCode)
		throws NoSuchERCGroupEntryException;

	/**
	 * Returns the erc group entry where groupId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching erc group entry, or <code>null</code> if a matching erc group entry could not be found
	 */
	public ERCGroupEntry fetchByG_ERC(
		long groupId, String externalReferenceCode);

	/**
	 * Returns the erc group entry where groupId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching erc group entry, or <code>null</code> if a matching erc group entry could not be found
	 */
	public ERCGroupEntry fetchByG_ERC(
		long groupId, String externalReferenceCode, boolean useFinderCache);

	/**
	 * Removes the erc group entry where groupId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the erc group entry that was removed
	 */
	public ERCGroupEntry removeByG_ERC(
			long groupId, String externalReferenceCode)
		throws NoSuchERCGroupEntryException;

	/**
	 * Returns the number of erc group entries where groupId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching erc group entries
	 */
	public int countByG_ERC(long groupId, String externalReferenceCode);

	/**
	 * Caches the erc group entry in the entity cache if it is enabled.
	 *
	 * @param ercGroupEntry the erc group entry
	 */
	public void cacheResult(ERCGroupEntry ercGroupEntry);

	/**
	 * Caches the erc group entries in the entity cache if it is enabled.
	 *
	 * @param ercGroupEntries the erc group entries
	 */
	public void cacheResult(java.util.List<ERCGroupEntry> ercGroupEntries);

	/**
	 * Creates a new erc group entry with the primary key. Does not add the erc group entry to the database.
	 *
	 * @param ercGroupEntryId the primary key for the new erc group entry
	 * @return the new erc group entry
	 */
	public ERCGroupEntry create(long ercGroupEntryId);

	/**
	 * Removes the erc group entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ercGroupEntryId the primary key of the erc group entry
	 * @return the erc group entry that was removed
	 * @throws NoSuchERCGroupEntryException if a erc group entry with the primary key could not be found
	 */
	public ERCGroupEntry remove(long ercGroupEntryId)
		throws NoSuchERCGroupEntryException;

	public ERCGroupEntry updateImpl(ERCGroupEntry ercGroupEntry);

	/**
	 * Returns the erc group entry with the primary key or throws a <code>NoSuchERCGroupEntryException</code> if it could not be found.
	 *
	 * @param ercGroupEntryId the primary key of the erc group entry
	 * @return the erc group entry
	 * @throws NoSuchERCGroupEntryException if a erc group entry with the primary key could not be found
	 */
	public ERCGroupEntry findByPrimaryKey(long ercGroupEntryId)
		throws NoSuchERCGroupEntryException;

	/**
	 * Returns the erc group entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ercGroupEntryId the primary key of the erc group entry
	 * @return the erc group entry, or <code>null</code> if a erc group entry with the primary key could not be found
	 */
	public ERCGroupEntry fetchByPrimaryKey(long ercGroupEntryId);

	/**
	 * Returns all the erc group entries.
	 *
	 * @return the erc group entries
	 */
	public java.util.List<ERCGroupEntry> findAll();

	/**
	 * Returns a range of all the erc group entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCGroupEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc group entries
	 * @param end the upper bound of the range of erc group entries (not inclusive)
	 * @return the range of erc group entries
	 */
	public java.util.List<ERCGroupEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the erc group entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCGroupEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc group entries
	 * @param end the upper bound of the range of erc group entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of erc group entries
	 */
	public java.util.List<ERCGroupEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ERCGroupEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the erc group entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCGroupEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc group entries
	 * @param end the upper bound of the range of erc group entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of erc group entries
	 */
	public java.util.List<ERCGroupEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ERCGroupEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the erc group entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of erc group entries.
	 *
	 * @return the number of erc group entries
	 */
	public int countAll();

}
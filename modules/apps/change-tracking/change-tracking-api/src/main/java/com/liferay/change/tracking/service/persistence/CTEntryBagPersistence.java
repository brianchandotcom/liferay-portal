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

package com.liferay.change.tracking.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.change.tracking.exception.NoSuchEntryBagException;
import com.liferay.change.tracking.model.CTEntryBag;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the ct entry bag service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTEntryBagUtil
 * @generated
 */
@ProviderType
public interface CTEntryBagPersistence extends BasePersistence<CTEntryBag> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CTEntryBagUtil} to access the ct entry bag persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the ct entry bags where ownerCTEntryId = &#63; and ctCollectionId = &#63;.
	*
	* @param ownerCTEntryId the owner ct entry ID
	* @param ctCollectionId the ct collection ID
	* @return the matching ct entry bags
	*/
	public java.util.List<CTEntryBag> findByO_C(long ownerCTEntryId,
		long ctCollectionId);

	/**
	* Returns a range of all the ct entry bags where ownerCTEntryId = &#63; and ctCollectionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryBagModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param ownerCTEntryId the owner ct entry ID
	* @param ctCollectionId the ct collection ID
	* @param start the lower bound of the range of ct entry bags
	* @param end the upper bound of the range of ct entry bags (not inclusive)
	* @return the range of matching ct entry bags
	*/
	public java.util.List<CTEntryBag> findByO_C(long ownerCTEntryId,
		long ctCollectionId, int start, int end);

	/**
	* Returns an ordered range of all the ct entry bags where ownerCTEntryId = &#63; and ctCollectionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryBagModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param ownerCTEntryId the owner ct entry ID
	* @param ctCollectionId the ct collection ID
	* @param start the lower bound of the range of ct entry bags
	* @param end the upper bound of the range of ct entry bags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching ct entry bags
	*/
	public java.util.List<CTEntryBag> findByO_C(long ownerCTEntryId,
		long ctCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntryBag> orderByComparator);

	/**
	* Returns an ordered range of all the ct entry bags where ownerCTEntryId = &#63; and ctCollectionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryBagModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param ownerCTEntryId the owner ct entry ID
	* @param ctCollectionId the ct collection ID
	* @param start the lower bound of the range of ct entry bags
	* @param end the upper bound of the range of ct entry bags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching ct entry bags
	*/
	public java.util.List<CTEntryBag> findByO_C(long ownerCTEntryId,
		long ctCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntryBag> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first ct entry bag in the ordered set where ownerCTEntryId = &#63; and ctCollectionId = &#63;.
	*
	* @param ownerCTEntryId the owner ct entry ID
	* @param ctCollectionId the ct collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ct entry bag
	* @throws NoSuchEntryBagException if a matching ct entry bag could not be found
	*/
	public CTEntryBag findByO_C_First(long ownerCTEntryId, long ctCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntryBag> orderByComparator)
		throws NoSuchEntryBagException;

	/**
	* Returns the first ct entry bag in the ordered set where ownerCTEntryId = &#63; and ctCollectionId = &#63;.
	*
	* @param ownerCTEntryId the owner ct entry ID
	* @param ctCollectionId the ct collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ct entry bag, or <code>null</code> if a matching ct entry bag could not be found
	*/
	public CTEntryBag fetchByO_C_First(long ownerCTEntryId,
		long ctCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntryBag> orderByComparator);

	/**
	* Returns the last ct entry bag in the ordered set where ownerCTEntryId = &#63; and ctCollectionId = &#63;.
	*
	* @param ownerCTEntryId the owner ct entry ID
	* @param ctCollectionId the ct collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ct entry bag
	* @throws NoSuchEntryBagException if a matching ct entry bag could not be found
	*/
	public CTEntryBag findByO_C_Last(long ownerCTEntryId, long ctCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntryBag> orderByComparator)
		throws NoSuchEntryBagException;

	/**
	* Returns the last ct entry bag in the ordered set where ownerCTEntryId = &#63; and ctCollectionId = &#63;.
	*
	* @param ownerCTEntryId the owner ct entry ID
	* @param ctCollectionId the ct collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ct entry bag, or <code>null</code> if a matching ct entry bag could not be found
	*/
	public CTEntryBag fetchByO_C_Last(long ownerCTEntryId, long ctCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntryBag> orderByComparator);

	/**
	* Returns the ct entry bags before and after the current ct entry bag in the ordered set where ownerCTEntryId = &#63; and ctCollectionId = &#63;.
	*
	* @param ctEntryBagId the primary key of the current ct entry bag
	* @param ownerCTEntryId the owner ct entry ID
	* @param ctCollectionId the ct collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next ct entry bag
	* @throws NoSuchEntryBagException if a ct entry bag with the primary key could not be found
	*/
	public CTEntryBag[] findByO_C_PrevAndNext(long ctEntryBagId,
		long ownerCTEntryId, long ctCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntryBag> orderByComparator)
		throws NoSuchEntryBagException;

	/**
	* Removes all the ct entry bags where ownerCTEntryId = &#63; and ctCollectionId = &#63; from the database.
	*
	* @param ownerCTEntryId the owner ct entry ID
	* @param ctCollectionId the ct collection ID
	*/
	public void removeByO_C(long ownerCTEntryId, long ctCollectionId);

	/**
	* Returns the number of ct entry bags where ownerCTEntryId = &#63; and ctCollectionId = &#63;.
	*
	* @param ownerCTEntryId the owner ct entry ID
	* @param ctCollectionId the ct collection ID
	* @return the number of matching ct entry bags
	*/
	public int countByO_C(long ownerCTEntryId, long ctCollectionId);

	/**
	* Caches the ct entry bag in the entity cache if it is enabled.
	*
	* @param ctEntryBag the ct entry bag
	*/
	public void cacheResult(CTEntryBag ctEntryBag);

	/**
	* Caches the ct entry bags in the entity cache if it is enabled.
	*
	* @param ctEntryBags the ct entry bags
	*/
	public void cacheResult(java.util.List<CTEntryBag> ctEntryBags);

	/**
	* Creates a new ct entry bag with the primary key. Does not add the ct entry bag to the database.
	*
	* @param ctEntryBagId the primary key for the new ct entry bag
	* @return the new ct entry bag
	*/
	public CTEntryBag create(long ctEntryBagId);

	/**
	* Removes the ct entry bag with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param ctEntryBagId the primary key of the ct entry bag
	* @return the ct entry bag that was removed
	* @throws NoSuchEntryBagException if a ct entry bag with the primary key could not be found
	*/
	public CTEntryBag remove(long ctEntryBagId) throws NoSuchEntryBagException;

	public CTEntryBag updateImpl(CTEntryBag ctEntryBag);

	/**
	* Returns the ct entry bag with the primary key or throws a <code>NoSuchEntryBagException</code> if it could not be found.
	*
	* @param ctEntryBagId the primary key of the ct entry bag
	* @return the ct entry bag
	* @throws NoSuchEntryBagException if a ct entry bag with the primary key could not be found
	*/
	public CTEntryBag findByPrimaryKey(long ctEntryBagId)
		throws NoSuchEntryBagException;

	/**
	* Returns the ct entry bag with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param ctEntryBagId the primary key of the ct entry bag
	* @return the ct entry bag, or <code>null</code> if a ct entry bag with the primary key could not be found
	*/
	public CTEntryBag fetchByPrimaryKey(long ctEntryBagId);

	/**
	* Returns all the ct entry bags.
	*
	* @return the ct entry bags
	*/
	public java.util.List<CTEntryBag> findAll();

	/**
	* Returns a range of all the ct entry bags.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryBagModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ct entry bags
	* @param end the upper bound of the range of ct entry bags (not inclusive)
	* @return the range of ct entry bags
	*/
	public java.util.List<CTEntryBag> findAll(int start, int end);

	/**
	* Returns an ordered range of all the ct entry bags.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryBagModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ct entry bags
	* @param end the upper bound of the range of ct entry bags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of ct entry bags
	*/
	public java.util.List<CTEntryBag> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntryBag> orderByComparator);

	/**
	* Returns an ordered range of all the ct entry bags.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryBagModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ct entry bags
	* @param end the upper bound of the range of ct entry bags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of ct entry bags
	*/
	public java.util.List<CTEntryBag> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntryBag> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the ct entry bags from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of ct entry bags.
	*
	* @return the number of ct entry bags
	*/
	public int countAll();

	/**
	* Returns the primaryKeys of ct entries associated with the ct entry bag.
	*
	* @param pk the primary key of the ct entry bag
	* @return long[] of the primaryKeys of ct entries associated with the ct entry bag
	*/
	public long[] getCTEntryPrimaryKeys(long pk);

	/**
	* Returns all the ct entries associated with the ct entry bag.
	*
	* @param pk the primary key of the ct entry bag
	* @return the ct entries associated with the ct entry bag
	*/
	public java.util.List<com.liferay.change.tracking.model.CTEntry> getCTEntries(
		long pk);

	/**
	* Returns a range of all the ct entries associated with the ct entry bag.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryBagModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the ct entry bag
	* @param start the lower bound of the range of ct entry bags
	* @param end the upper bound of the range of ct entry bags (not inclusive)
	* @return the range of ct entries associated with the ct entry bag
	*/
	public java.util.List<com.liferay.change.tracking.model.CTEntry> getCTEntries(
		long pk, int start, int end);

	/**
	* Returns an ordered range of all the ct entries associated with the ct entry bag.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryBagModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the ct entry bag
	* @param start the lower bound of the range of ct entry bags
	* @param end the upper bound of the range of ct entry bags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of ct entries associated with the ct entry bag
	*/
	public java.util.List<com.liferay.change.tracking.model.CTEntry> getCTEntries(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.change.tracking.model.CTEntry> orderByComparator);

	/**
	* Returns the number of ct entries associated with the ct entry bag.
	*
	* @param pk the primary key of the ct entry bag
	* @return the number of ct entries associated with the ct entry bag
	*/
	public int getCTEntriesSize(long pk);

	/**
	* Returns <code>true</code> if the ct entry is associated with the ct entry bag.
	*
	* @param pk the primary key of the ct entry bag
	* @param ctEntryPK the primary key of the ct entry
	* @return <code>true</code> if the ct entry is associated with the ct entry bag; <code>false</code> otherwise
	*/
	public boolean containsCTEntry(long pk, long ctEntryPK);

	/**
	* Returns <code>true</code> if the ct entry bag has any ct entries associated with it.
	*
	* @param pk the primary key of the ct entry bag to check for associations with ct entries
	* @return <code>true</code> if the ct entry bag has any ct entries associated with it; <code>false</code> otherwise
	*/
	public boolean containsCTEntries(long pk);

	/**
	* Adds an association between the ct entry bag and the ct entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the ct entry bag
	* @param ctEntryPK the primary key of the ct entry
	*/
	public void addCTEntry(long pk, long ctEntryPK);

	/**
	* Adds an association between the ct entry bag and the ct entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the ct entry bag
	* @param ctEntry the ct entry
	*/
	public void addCTEntry(long pk,
		com.liferay.change.tracking.model.CTEntry ctEntry);

	/**
	* Adds an association between the ct entry bag and the ct entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the ct entry bag
	* @param ctEntryPKs the primary keys of the ct entries
	*/
	public void addCTEntries(long pk, long[] ctEntryPKs);

	/**
	* Adds an association between the ct entry bag and the ct entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the ct entry bag
	* @param ctEntries the ct entries
	*/
	public void addCTEntries(long pk,
		java.util.List<com.liferay.change.tracking.model.CTEntry> ctEntries);

	/**
	* Clears all associations between the ct entry bag and its ct entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the ct entry bag to clear the associated ct entries from
	*/
	public void clearCTEntries(long pk);

	/**
	* Removes the association between the ct entry bag and the ct entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the ct entry bag
	* @param ctEntryPK the primary key of the ct entry
	*/
	public void removeCTEntry(long pk, long ctEntryPK);

	/**
	* Removes the association between the ct entry bag and the ct entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the ct entry bag
	* @param ctEntry the ct entry
	*/
	public void removeCTEntry(long pk,
		com.liferay.change.tracking.model.CTEntry ctEntry);

	/**
	* Removes the association between the ct entry bag and the ct entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the ct entry bag
	* @param ctEntryPKs the primary keys of the ct entries
	*/
	public void removeCTEntries(long pk, long[] ctEntryPKs);

	/**
	* Removes the association between the ct entry bag and the ct entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the ct entry bag
	* @param ctEntries the ct entries
	*/
	public void removeCTEntries(long pk,
		java.util.List<com.liferay.change.tracking.model.CTEntry> ctEntries);

	/**
	* Sets the ct entries associated with the ct entry bag, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the ct entry bag
	* @param ctEntryPKs the primary keys of the ct entries to be associated with the ct entry bag
	*/
	public void setCTEntries(long pk, long[] ctEntryPKs);

	/**
	* Sets the ct entries associated with the ct entry bag, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the ct entry bag
	* @param ctEntries the ct entries to be associated with the ct entry bag
	*/
	public void setCTEntries(long pk,
		java.util.List<com.liferay.change.tracking.model.CTEntry> ctEntries);
}
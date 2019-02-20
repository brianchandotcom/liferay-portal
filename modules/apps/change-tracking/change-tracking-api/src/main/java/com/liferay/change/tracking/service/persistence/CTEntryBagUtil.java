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

import com.liferay.change.tracking.model.CTEntryBag;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the ct entry bag service. This utility wraps <code>com.liferay.change.tracking.service.persistence.impl.CTEntryBagPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTEntryBagPersistence
 * @generated
 */
@ProviderType
public class CTEntryBagUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(CTEntryBag ctEntryBag) {
		getPersistence().clearCache(ctEntryBag);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, CTEntryBag> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CTEntryBag> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CTEntryBag> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CTEntryBag> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CTEntryBag> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CTEntryBag update(CTEntryBag ctEntryBag) {
		return getPersistence().update(ctEntryBag);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CTEntryBag update(CTEntryBag ctEntryBag,
		ServiceContext serviceContext) {
		return getPersistence().update(ctEntryBag, serviceContext);
	}

	/**
	* Returns all the ct entry bags where ownerCTEntryId = &#63; and ctCollectionId = &#63;.
	*
	* @param ownerCTEntryId the owner ct entry ID
	* @param ctCollectionId the ct collection ID
	* @return the matching ct entry bags
	*/
	public static List<CTEntryBag> findByO_C(long ownerCTEntryId,
		long ctCollectionId) {
		return getPersistence().findByO_C(ownerCTEntryId, ctCollectionId);
	}

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
	public static List<CTEntryBag> findByO_C(long ownerCTEntryId,
		long ctCollectionId, int start, int end) {
		return getPersistence()
				   .findByO_C(ownerCTEntryId, ctCollectionId, start, end);
	}

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
	public static List<CTEntryBag> findByO_C(long ownerCTEntryId,
		long ctCollectionId, int start, int end,
		OrderByComparator<CTEntryBag> orderByComparator) {
		return getPersistence()
				   .findByO_C(ownerCTEntryId, ctCollectionId, start, end,
			orderByComparator);
	}

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
	public static List<CTEntryBag> findByO_C(long ownerCTEntryId,
		long ctCollectionId, int start, int end,
		OrderByComparator<CTEntryBag> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByO_C(ownerCTEntryId, ctCollectionId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first ct entry bag in the ordered set where ownerCTEntryId = &#63; and ctCollectionId = &#63;.
	*
	* @param ownerCTEntryId the owner ct entry ID
	* @param ctCollectionId the ct collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ct entry bag
	* @throws NoSuchEntryBagException if a matching ct entry bag could not be found
	*/
	public static CTEntryBag findByO_C_First(long ownerCTEntryId,
		long ctCollectionId, OrderByComparator<CTEntryBag> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchEntryBagException {
		return getPersistence()
				   .findByO_C_First(ownerCTEntryId, ctCollectionId,
			orderByComparator);
	}

	/**
	* Returns the first ct entry bag in the ordered set where ownerCTEntryId = &#63; and ctCollectionId = &#63;.
	*
	* @param ownerCTEntryId the owner ct entry ID
	* @param ctCollectionId the ct collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ct entry bag, or <code>null</code> if a matching ct entry bag could not be found
	*/
	public static CTEntryBag fetchByO_C_First(long ownerCTEntryId,
		long ctCollectionId, OrderByComparator<CTEntryBag> orderByComparator) {
		return getPersistence()
				   .fetchByO_C_First(ownerCTEntryId, ctCollectionId,
			orderByComparator);
	}

	/**
	* Returns the last ct entry bag in the ordered set where ownerCTEntryId = &#63; and ctCollectionId = &#63;.
	*
	* @param ownerCTEntryId the owner ct entry ID
	* @param ctCollectionId the ct collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ct entry bag
	* @throws NoSuchEntryBagException if a matching ct entry bag could not be found
	*/
	public static CTEntryBag findByO_C_Last(long ownerCTEntryId,
		long ctCollectionId, OrderByComparator<CTEntryBag> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchEntryBagException {
		return getPersistence()
				   .findByO_C_Last(ownerCTEntryId, ctCollectionId,
			orderByComparator);
	}

	/**
	* Returns the last ct entry bag in the ordered set where ownerCTEntryId = &#63; and ctCollectionId = &#63;.
	*
	* @param ownerCTEntryId the owner ct entry ID
	* @param ctCollectionId the ct collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ct entry bag, or <code>null</code> if a matching ct entry bag could not be found
	*/
	public static CTEntryBag fetchByO_C_Last(long ownerCTEntryId,
		long ctCollectionId, OrderByComparator<CTEntryBag> orderByComparator) {
		return getPersistence()
				   .fetchByO_C_Last(ownerCTEntryId, ctCollectionId,
			orderByComparator);
	}

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
	public static CTEntryBag[] findByO_C_PrevAndNext(long ctEntryBagId,
		long ownerCTEntryId, long ctCollectionId,
		OrderByComparator<CTEntryBag> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchEntryBagException {
		return getPersistence()
				   .findByO_C_PrevAndNext(ctEntryBagId, ownerCTEntryId,
			ctCollectionId, orderByComparator);
	}

	/**
	* Removes all the ct entry bags where ownerCTEntryId = &#63; and ctCollectionId = &#63; from the database.
	*
	* @param ownerCTEntryId the owner ct entry ID
	* @param ctCollectionId the ct collection ID
	*/
	public static void removeByO_C(long ownerCTEntryId, long ctCollectionId) {
		getPersistence().removeByO_C(ownerCTEntryId, ctCollectionId);
	}

	/**
	* Returns the number of ct entry bags where ownerCTEntryId = &#63; and ctCollectionId = &#63;.
	*
	* @param ownerCTEntryId the owner ct entry ID
	* @param ctCollectionId the ct collection ID
	* @return the number of matching ct entry bags
	*/
	public static int countByO_C(long ownerCTEntryId, long ctCollectionId) {
		return getPersistence().countByO_C(ownerCTEntryId, ctCollectionId);
	}

	/**
	* Caches the ct entry bag in the entity cache if it is enabled.
	*
	* @param ctEntryBag the ct entry bag
	*/
	public static void cacheResult(CTEntryBag ctEntryBag) {
		getPersistence().cacheResult(ctEntryBag);
	}

	/**
	* Caches the ct entry bags in the entity cache if it is enabled.
	*
	* @param ctEntryBags the ct entry bags
	*/
	public static void cacheResult(List<CTEntryBag> ctEntryBags) {
		getPersistence().cacheResult(ctEntryBags);
	}

	/**
	* Creates a new ct entry bag with the primary key. Does not add the ct entry bag to the database.
	*
	* @param ctEntryBagId the primary key for the new ct entry bag
	* @return the new ct entry bag
	*/
	public static CTEntryBag create(long ctEntryBagId) {
		return getPersistence().create(ctEntryBagId);
	}

	/**
	* Removes the ct entry bag with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param ctEntryBagId the primary key of the ct entry bag
	* @return the ct entry bag that was removed
	* @throws NoSuchEntryBagException if a ct entry bag with the primary key could not be found
	*/
	public static CTEntryBag remove(long ctEntryBagId)
		throws com.liferay.change.tracking.exception.NoSuchEntryBagException {
		return getPersistence().remove(ctEntryBagId);
	}

	public static CTEntryBag updateImpl(CTEntryBag ctEntryBag) {
		return getPersistence().updateImpl(ctEntryBag);
	}

	/**
	* Returns the ct entry bag with the primary key or throws a <code>NoSuchEntryBagException</code> if it could not be found.
	*
	* @param ctEntryBagId the primary key of the ct entry bag
	* @return the ct entry bag
	* @throws NoSuchEntryBagException if a ct entry bag with the primary key could not be found
	*/
	public static CTEntryBag findByPrimaryKey(long ctEntryBagId)
		throws com.liferay.change.tracking.exception.NoSuchEntryBagException {
		return getPersistence().findByPrimaryKey(ctEntryBagId);
	}

	/**
	* Returns the ct entry bag with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param ctEntryBagId the primary key of the ct entry bag
	* @return the ct entry bag, or <code>null</code> if a ct entry bag with the primary key could not be found
	*/
	public static CTEntryBag fetchByPrimaryKey(long ctEntryBagId) {
		return getPersistence().fetchByPrimaryKey(ctEntryBagId);
	}

	/**
	* Returns all the ct entry bags.
	*
	* @return the ct entry bags
	*/
	public static List<CTEntryBag> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<CTEntryBag> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<CTEntryBag> findAll(int start, int end,
		OrderByComparator<CTEntryBag> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<CTEntryBag> findAll(int start, int end,
		OrderByComparator<CTEntryBag> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the ct entry bags from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of ct entry bags.
	*
	* @return the number of ct entry bags
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	/**
	* Returns the primaryKeys of ct entries associated with the ct entry bag.
	*
	* @param pk the primary key of the ct entry bag
	* @return long[] of the primaryKeys of ct entries associated with the ct entry bag
	*/
	public static long[] getCTEntryPrimaryKeys(long pk) {
		return getPersistence().getCTEntryPrimaryKeys(pk);
	}

	/**
	* Returns all the ct entries associated with the ct entry bag.
	*
	* @param pk the primary key of the ct entry bag
	* @return the ct entries associated with the ct entry bag
	*/
	public static List<com.liferay.change.tracking.model.CTEntry> getCTEntries(
		long pk) {
		return getPersistence().getCTEntries(pk);
	}

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
	public static List<com.liferay.change.tracking.model.CTEntry> getCTEntries(
		long pk, int start, int end) {
		return getPersistence().getCTEntries(pk, start, end);
	}

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
	public static List<com.liferay.change.tracking.model.CTEntry> getCTEntries(
		long pk, int start, int end,
		OrderByComparator<com.liferay.change.tracking.model.CTEntry> orderByComparator) {
		return getPersistence().getCTEntries(pk, start, end, orderByComparator);
	}

	/**
	* Returns the number of ct entries associated with the ct entry bag.
	*
	* @param pk the primary key of the ct entry bag
	* @return the number of ct entries associated with the ct entry bag
	*/
	public static int getCTEntriesSize(long pk) {
		return getPersistence().getCTEntriesSize(pk);
	}

	/**
	* Returns <code>true</code> if the ct entry is associated with the ct entry bag.
	*
	* @param pk the primary key of the ct entry bag
	* @param ctEntryPK the primary key of the ct entry
	* @return <code>true</code> if the ct entry is associated with the ct entry bag; <code>false</code> otherwise
	*/
	public static boolean containsCTEntry(long pk, long ctEntryPK) {
		return getPersistence().containsCTEntry(pk, ctEntryPK);
	}

	/**
	* Returns <code>true</code> if the ct entry bag has any ct entries associated with it.
	*
	* @param pk the primary key of the ct entry bag to check for associations with ct entries
	* @return <code>true</code> if the ct entry bag has any ct entries associated with it; <code>false</code> otherwise
	*/
	public static boolean containsCTEntries(long pk) {
		return getPersistence().containsCTEntries(pk);
	}

	/**
	* Adds an association between the ct entry bag and the ct entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the ct entry bag
	* @param ctEntryPK the primary key of the ct entry
	*/
	public static void addCTEntry(long pk, long ctEntryPK) {
		getPersistence().addCTEntry(pk, ctEntryPK);
	}

	/**
	* Adds an association between the ct entry bag and the ct entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the ct entry bag
	* @param ctEntry the ct entry
	*/
	public static void addCTEntry(long pk,
		com.liferay.change.tracking.model.CTEntry ctEntry) {
		getPersistence().addCTEntry(pk, ctEntry);
	}

	/**
	* Adds an association between the ct entry bag and the ct entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the ct entry bag
	* @param ctEntryPKs the primary keys of the ct entries
	*/
	public static void addCTEntries(long pk, long[] ctEntryPKs) {
		getPersistence().addCTEntries(pk, ctEntryPKs);
	}

	/**
	* Adds an association between the ct entry bag and the ct entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the ct entry bag
	* @param ctEntries the ct entries
	*/
	public static void addCTEntries(long pk,
		List<com.liferay.change.tracking.model.CTEntry> ctEntries) {
		getPersistence().addCTEntries(pk, ctEntries);
	}

	/**
	* Clears all associations between the ct entry bag and its ct entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the ct entry bag to clear the associated ct entries from
	*/
	public static void clearCTEntries(long pk) {
		getPersistence().clearCTEntries(pk);
	}

	/**
	* Removes the association between the ct entry bag and the ct entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the ct entry bag
	* @param ctEntryPK the primary key of the ct entry
	*/
	public static void removeCTEntry(long pk, long ctEntryPK) {
		getPersistence().removeCTEntry(pk, ctEntryPK);
	}

	/**
	* Removes the association between the ct entry bag and the ct entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the ct entry bag
	* @param ctEntry the ct entry
	*/
	public static void removeCTEntry(long pk,
		com.liferay.change.tracking.model.CTEntry ctEntry) {
		getPersistence().removeCTEntry(pk, ctEntry);
	}

	/**
	* Removes the association between the ct entry bag and the ct entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the ct entry bag
	* @param ctEntryPKs the primary keys of the ct entries
	*/
	public static void removeCTEntries(long pk, long[] ctEntryPKs) {
		getPersistence().removeCTEntries(pk, ctEntryPKs);
	}

	/**
	* Removes the association between the ct entry bag and the ct entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the ct entry bag
	* @param ctEntries the ct entries
	*/
	public static void removeCTEntries(long pk,
		List<com.liferay.change.tracking.model.CTEntry> ctEntries) {
		getPersistence().removeCTEntries(pk, ctEntries);
	}

	/**
	* Sets the ct entries associated with the ct entry bag, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the ct entry bag
	* @param ctEntryPKs the primary keys of the ct entries to be associated with the ct entry bag
	*/
	public static void setCTEntries(long pk, long[] ctEntryPKs) {
		getPersistence().setCTEntries(pk, ctEntryPKs);
	}

	/**
	* Sets the ct entries associated with the ct entry bag, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the ct entry bag
	* @param ctEntries the ct entries to be associated with the ct entry bag
	*/
	public static void setCTEntries(long pk,
		List<com.liferay.change.tracking.model.CTEntry> ctEntries) {
		getPersistence().setCTEntries(pk, ctEntries);
	}

	public static CTEntryBagPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CTEntryBagPersistence, CTEntryBagPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CTEntryBagPersistence.class);

		ServiceTracker<CTEntryBagPersistence, CTEntryBagPersistence> serviceTracker =
			new ServiceTracker<CTEntryBagPersistence, CTEntryBagPersistence>(bundle.getBundleContext(),
				CTEntryBagPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}
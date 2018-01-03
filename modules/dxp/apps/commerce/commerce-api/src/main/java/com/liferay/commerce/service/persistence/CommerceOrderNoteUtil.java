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

package com.liferay.commerce.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommerceOrderNote;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce order note service. This utility wraps {@link com.liferay.commerce.service.persistence.impl.CommerceOrderNotePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderNotePersistence
 * @see com.liferay.commerce.service.persistence.impl.CommerceOrderNotePersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceOrderNoteUtil {
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
	public static void clearCache(CommerceOrderNote commerceOrderNote) {
		getPersistence().clearCache(commerceOrderNote);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceOrderNote> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceOrderNote> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceOrderNote> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceOrderNote> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceOrderNote update(CommerceOrderNote commerceOrderNote) {
		return getPersistence().update(commerceOrderNote);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceOrderNote update(
		CommerceOrderNote commerceOrderNote, ServiceContext serviceContext) {
		return getPersistence().update(commerceOrderNote, serviceContext);
	}

	/**
	* Returns all the commerce order notes where commerceOrderId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @return the matching commerce order notes
	*/
	public static List<CommerceOrderNote> findByCommerceOrderId(
		long commerceOrderId) {
		return getPersistence().findByCommerceOrderId(commerceOrderId);
	}

	/**
	* Returns a range of all the commerce order notes where commerceOrderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderNoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceOrderId the commerce order ID
	* @param start the lower bound of the range of commerce order notes
	* @param end the upper bound of the range of commerce order notes (not inclusive)
	* @return the range of matching commerce order notes
	*/
	public static List<CommerceOrderNote> findByCommerceOrderId(
		long commerceOrderId, int start, int end) {
		return getPersistence()
				   .findByCommerceOrderId(commerceOrderId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce order notes where commerceOrderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderNoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceOrderId the commerce order ID
	* @param start the lower bound of the range of commerce order notes
	* @param end the upper bound of the range of commerce order notes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce order notes
	*/
	public static List<CommerceOrderNote> findByCommerceOrderId(
		long commerceOrderId, int start, int end,
		OrderByComparator<CommerceOrderNote> orderByComparator) {
		return getPersistence()
				   .findByCommerceOrderId(commerceOrderId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce order notes where commerceOrderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderNoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceOrderId the commerce order ID
	* @param start the lower bound of the range of commerce order notes
	* @param end the upper bound of the range of commerce order notes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce order notes
	*/
	public static List<CommerceOrderNote> findByCommerceOrderId(
		long commerceOrderId, int start, int end,
		OrderByComparator<CommerceOrderNote> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCommerceOrderId(commerceOrderId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce order note in the ordered set where commerceOrderId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce order note
	* @throws NoSuchOrderNoteException if a matching commerce order note could not be found
	*/
	public static CommerceOrderNote findByCommerceOrderId_First(
		long commerceOrderId,
		OrderByComparator<CommerceOrderNote> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderNoteException {
		return getPersistence()
				   .findByCommerceOrderId_First(commerceOrderId,
			orderByComparator);
	}

	/**
	* Returns the first commerce order note in the ordered set where commerceOrderId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce order note, or <code>null</code> if a matching commerce order note could not be found
	*/
	public static CommerceOrderNote fetchByCommerceOrderId_First(
		long commerceOrderId,
		OrderByComparator<CommerceOrderNote> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceOrderId_First(commerceOrderId,
			orderByComparator);
	}

	/**
	* Returns the last commerce order note in the ordered set where commerceOrderId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce order note
	* @throws NoSuchOrderNoteException if a matching commerce order note could not be found
	*/
	public static CommerceOrderNote findByCommerceOrderId_Last(
		long commerceOrderId,
		OrderByComparator<CommerceOrderNote> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderNoteException {
		return getPersistence()
				   .findByCommerceOrderId_Last(commerceOrderId,
			orderByComparator);
	}

	/**
	* Returns the last commerce order note in the ordered set where commerceOrderId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce order note, or <code>null</code> if a matching commerce order note could not be found
	*/
	public static CommerceOrderNote fetchByCommerceOrderId_Last(
		long commerceOrderId,
		OrderByComparator<CommerceOrderNote> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceOrderId_Last(commerceOrderId,
			orderByComparator);
	}

	/**
	* Returns the commerce order notes before and after the current commerce order note in the ordered set where commerceOrderId = &#63;.
	*
	* @param commerceOrderNoteId the primary key of the current commerce order note
	* @param commerceOrderId the commerce order ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce order note
	* @throws NoSuchOrderNoteException if a commerce order note with the primary key could not be found
	*/
	public static CommerceOrderNote[] findByCommerceOrderId_PrevAndNext(
		long commerceOrderNoteId, long commerceOrderId,
		OrderByComparator<CommerceOrderNote> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderNoteException {
		return getPersistence()
				   .findByCommerceOrderId_PrevAndNext(commerceOrderNoteId,
			commerceOrderId, orderByComparator);
	}

	/**
	* Removes all the commerce order notes where commerceOrderId = &#63; from the database.
	*
	* @param commerceOrderId the commerce order ID
	*/
	public static void removeByCommerceOrderId(long commerceOrderId) {
		getPersistence().removeByCommerceOrderId(commerceOrderId);
	}

	/**
	* Returns the number of commerce order notes where commerceOrderId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @return the number of matching commerce order notes
	*/
	public static int countByCommerceOrderId(long commerceOrderId) {
		return getPersistence().countByCommerceOrderId(commerceOrderId);
	}

	/**
	* Caches the commerce order note in the entity cache if it is enabled.
	*
	* @param commerceOrderNote the commerce order note
	*/
	public static void cacheResult(CommerceOrderNote commerceOrderNote) {
		getPersistence().cacheResult(commerceOrderNote);
	}

	/**
	* Caches the commerce order notes in the entity cache if it is enabled.
	*
	* @param commerceOrderNotes the commerce order notes
	*/
	public static void cacheResult(List<CommerceOrderNote> commerceOrderNotes) {
		getPersistence().cacheResult(commerceOrderNotes);
	}

	/**
	* Creates a new commerce order note with the primary key. Does not add the commerce order note to the database.
	*
	* @param commerceOrderNoteId the primary key for the new commerce order note
	* @return the new commerce order note
	*/
	public static CommerceOrderNote create(long commerceOrderNoteId) {
		return getPersistence().create(commerceOrderNoteId);
	}

	/**
	* Removes the commerce order note with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceOrderNoteId the primary key of the commerce order note
	* @return the commerce order note that was removed
	* @throws NoSuchOrderNoteException if a commerce order note with the primary key could not be found
	*/
	public static CommerceOrderNote remove(long commerceOrderNoteId)
		throws com.liferay.commerce.exception.NoSuchOrderNoteException {
		return getPersistence().remove(commerceOrderNoteId);
	}

	public static CommerceOrderNote updateImpl(
		CommerceOrderNote commerceOrderNote) {
		return getPersistence().updateImpl(commerceOrderNote);
	}

	/**
	* Returns the commerce order note with the primary key or throws a {@link NoSuchOrderNoteException} if it could not be found.
	*
	* @param commerceOrderNoteId the primary key of the commerce order note
	* @return the commerce order note
	* @throws NoSuchOrderNoteException if a commerce order note with the primary key could not be found
	*/
	public static CommerceOrderNote findByPrimaryKey(long commerceOrderNoteId)
		throws com.liferay.commerce.exception.NoSuchOrderNoteException {
		return getPersistence().findByPrimaryKey(commerceOrderNoteId);
	}

	/**
	* Returns the commerce order note with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceOrderNoteId the primary key of the commerce order note
	* @return the commerce order note, or <code>null</code> if a commerce order note with the primary key could not be found
	*/
	public static CommerceOrderNote fetchByPrimaryKey(long commerceOrderNoteId) {
		return getPersistence().fetchByPrimaryKey(commerceOrderNoteId);
	}

	public static java.util.Map<java.io.Serializable, CommerceOrderNote> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce order notes.
	*
	* @return the commerce order notes
	*/
	public static List<CommerceOrderNote> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce order notes.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderNoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce order notes
	* @param end the upper bound of the range of commerce order notes (not inclusive)
	* @return the range of commerce order notes
	*/
	public static List<CommerceOrderNote> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce order notes.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderNoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce order notes
	* @param end the upper bound of the range of commerce order notes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce order notes
	*/
	public static List<CommerceOrderNote> findAll(int start, int end,
		OrderByComparator<CommerceOrderNote> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce order notes.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderNoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce order notes
	* @param end the upper bound of the range of commerce order notes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce order notes
	*/
	public static List<CommerceOrderNote> findAll(int start, int end,
		OrderByComparator<CommerceOrderNote> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce order notes from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce order notes.
	*
	* @return the number of commerce order notes
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceOrderNotePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceOrderNotePersistence, CommerceOrderNotePersistence> _serviceTracker =
		ServiceTrackerFactory.open(CommerceOrderNotePersistence.class);
}
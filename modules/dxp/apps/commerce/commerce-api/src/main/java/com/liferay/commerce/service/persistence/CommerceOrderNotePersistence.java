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

import com.liferay.commerce.exception.NoSuchOrderNoteException;
import com.liferay.commerce.model.CommerceOrderNote;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce order note service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.persistence.impl.CommerceOrderNotePersistenceImpl
 * @see CommerceOrderNoteUtil
 * @generated
 */
@ProviderType
public interface CommerceOrderNotePersistence extends BasePersistence<CommerceOrderNote> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceOrderNoteUtil} to access the commerce order note persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce order notes where commerceOrderId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @return the matching commerce order notes
	*/
	public java.util.List<CommerceOrderNote> findByCommerceOrderId(
		long commerceOrderId);

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
	public java.util.List<CommerceOrderNote> findByCommerceOrderId(
		long commerceOrderId, int start, int end);

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
	public java.util.List<CommerceOrderNote> findByCommerceOrderId(
		long commerceOrderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote> orderByComparator);

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
	public java.util.List<CommerceOrderNote> findByCommerceOrderId(
		long commerceOrderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce order note in the ordered set where commerceOrderId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce order note
	* @throws NoSuchOrderNoteException if a matching commerce order note could not be found
	*/
	public CommerceOrderNote findByCommerceOrderId_First(long commerceOrderId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote> orderByComparator)
		throws NoSuchOrderNoteException;

	/**
	* Returns the first commerce order note in the ordered set where commerceOrderId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce order note, or <code>null</code> if a matching commerce order note could not be found
	*/
	public CommerceOrderNote fetchByCommerceOrderId_First(
		long commerceOrderId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote> orderByComparator);

	/**
	* Returns the last commerce order note in the ordered set where commerceOrderId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce order note
	* @throws NoSuchOrderNoteException if a matching commerce order note could not be found
	*/
	public CommerceOrderNote findByCommerceOrderId_Last(long commerceOrderId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote> orderByComparator)
		throws NoSuchOrderNoteException;

	/**
	* Returns the last commerce order note in the ordered set where commerceOrderId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce order note, or <code>null</code> if a matching commerce order note could not be found
	*/
	public CommerceOrderNote fetchByCommerceOrderId_Last(long commerceOrderId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote> orderByComparator);

	/**
	* Returns the commerce order notes before and after the current commerce order note in the ordered set where commerceOrderId = &#63;.
	*
	* @param commerceOrderNoteId the primary key of the current commerce order note
	* @param commerceOrderId the commerce order ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce order note
	* @throws NoSuchOrderNoteException if a commerce order note with the primary key could not be found
	*/
	public CommerceOrderNote[] findByCommerceOrderId_PrevAndNext(
		long commerceOrderNoteId, long commerceOrderId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote> orderByComparator)
		throws NoSuchOrderNoteException;

	/**
	* Removes all the commerce order notes where commerceOrderId = &#63; from the database.
	*
	* @param commerceOrderId the commerce order ID
	*/
	public void removeByCommerceOrderId(long commerceOrderId);

	/**
	* Returns the number of commerce order notes where commerceOrderId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @return the number of matching commerce order notes
	*/
	public int countByCommerceOrderId(long commerceOrderId);

	/**
	* Returns all the commerce order notes where commerceOrderId = &#63; and restricted = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param restricted the restricted
	* @return the matching commerce order notes
	*/
	public java.util.List<CommerceOrderNote> findByC_R(long commerceOrderId,
		boolean restricted);

	/**
	* Returns a range of all the commerce order notes where commerceOrderId = &#63; and restricted = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderNoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceOrderId the commerce order ID
	* @param restricted the restricted
	* @param start the lower bound of the range of commerce order notes
	* @param end the upper bound of the range of commerce order notes (not inclusive)
	* @return the range of matching commerce order notes
	*/
	public java.util.List<CommerceOrderNote> findByC_R(long commerceOrderId,
		boolean restricted, int start, int end);

	/**
	* Returns an ordered range of all the commerce order notes where commerceOrderId = &#63; and restricted = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderNoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceOrderId the commerce order ID
	* @param restricted the restricted
	* @param start the lower bound of the range of commerce order notes
	* @param end the upper bound of the range of commerce order notes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce order notes
	*/
	public java.util.List<CommerceOrderNote> findByC_R(long commerceOrderId,
		boolean restricted, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote> orderByComparator);

	/**
	* Returns an ordered range of all the commerce order notes where commerceOrderId = &#63; and restricted = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderNoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceOrderId the commerce order ID
	* @param restricted the restricted
	* @param start the lower bound of the range of commerce order notes
	* @param end the upper bound of the range of commerce order notes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce order notes
	*/
	public java.util.List<CommerceOrderNote> findByC_R(long commerceOrderId,
		boolean restricted, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce order note in the ordered set where commerceOrderId = &#63; and restricted = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param restricted the restricted
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce order note
	* @throws NoSuchOrderNoteException if a matching commerce order note could not be found
	*/
	public CommerceOrderNote findByC_R_First(long commerceOrderId,
		boolean restricted,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote> orderByComparator)
		throws NoSuchOrderNoteException;

	/**
	* Returns the first commerce order note in the ordered set where commerceOrderId = &#63; and restricted = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param restricted the restricted
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce order note, or <code>null</code> if a matching commerce order note could not be found
	*/
	public CommerceOrderNote fetchByC_R_First(long commerceOrderId,
		boolean restricted,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote> orderByComparator);

	/**
	* Returns the last commerce order note in the ordered set where commerceOrderId = &#63; and restricted = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param restricted the restricted
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce order note
	* @throws NoSuchOrderNoteException if a matching commerce order note could not be found
	*/
	public CommerceOrderNote findByC_R_Last(long commerceOrderId,
		boolean restricted,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote> orderByComparator)
		throws NoSuchOrderNoteException;

	/**
	* Returns the last commerce order note in the ordered set where commerceOrderId = &#63; and restricted = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param restricted the restricted
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce order note, or <code>null</code> if a matching commerce order note could not be found
	*/
	public CommerceOrderNote fetchByC_R_Last(long commerceOrderId,
		boolean restricted,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote> orderByComparator);

	/**
	* Returns the commerce order notes before and after the current commerce order note in the ordered set where commerceOrderId = &#63; and restricted = &#63;.
	*
	* @param commerceOrderNoteId the primary key of the current commerce order note
	* @param commerceOrderId the commerce order ID
	* @param restricted the restricted
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce order note
	* @throws NoSuchOrderNoteException if a commerce order note with the primary key could not be found
	*/
	public CommerceOrderNote[] findByC_R_PrevAndNext(long commerceOrderNoteId,
		long commerceOrderId, boolean restricted,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote> orderByComparator)
		throws NoSuchOrderNoteException;

	/**
	* Removes all the commerce order notes where commerceOrderId = &#63; and restricted = &#63; from the database.
	*
	* @param commerceOrderId the commerce order ID
	* @param restricted the restricted
	*/
	public void removeByC_R(long commerceOrderId, boolean restricted);

	/**
	* Returns the number of commerce order notes where commerceOrderId = &#63; and restricted = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param restricted the restricted
	* @return the number of matching commerce order notes
	*/
	public int countByC_R(long commerceOrderId, boolean restricted);

	/**
	* Caches the commerce order note in the entity cache if it is enabled.
	*
	* @param commerceOrderNote the commerce order note
	*/
	public void cacheResult(CommerceOrderNote commerceOrderNote);

	/**
	* Caches the commerce order notes in the entity cache if it is enabled.
	*
	* @param commerceOrderNotes the commerce order notes
	*/
	public void cacheResult(
		java.util.List<CommerceOrderNote> commerceOrderNotes);

	/**
	* Creates a new commerce order note with the primary key. Does not add the commerce order note to the database.
	*
	* @param commerceOrderNoteId the primary key for the new commerce order note
	* @return the new commerce order note
	*/
	public CommerceOrderNote create(long commerceOrderNoteId);

	/**
	* Removes the commerce order note with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceOrderNoteId the primary key of the commerce order note
	* @return the commerce order note that was removed
	* @throws NoSuchOrderNoteException if a commerce order note with the primary key could not be found
	*/
	public CommerceOrderNote remove(long commerceOrderNoteId)
		throws NoSuchOrderNoteException;

	public CommerceOrderNote updateImpl(CommerceOrderNote commerceOrderNote);

	/**
	* Returns the commerce order note with the primary key or throws a {@link NoSuchOrderNoteException} if it could not be found.
	*
	* @param commerceOrderNoteId the primary key of the commerce order note
	* @return the commerce order note
	* @throws NoSuchOrderNoteException if a commerce order note with the primary key could not be found
	*/
	public CommerceOrderNote findByPrimaryKey(long commerceOrderNoteId)
		throws NoSuchOrderNoteException;

	/**
	* Returns the commerce order note with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceOrderNoteId the primary key of the commerce order note
	* @return the commerce order note, or <code>null</code> if a commerce order note with the primary key could not be found
	*/
	public CommerceOrderNote fetchByPrimaryKey(long commerceOrderNoteId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceOrderNote> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce order notes.
	*
	* @return the commerce order notes
	*/
	public java.util.List<CommerceOrderNote> findAll();

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
	public java.util.List<CommerceOrderNote> findAll(int start, int end);

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
	public java.util.List<CommerceOrderNote> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote> orderByComparator);

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
	public java.util.List<CommerceOrderNote> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce order notes from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce order notes.
	*
	* @return the number of commerce order notes
	*/
	public int countAll();
}
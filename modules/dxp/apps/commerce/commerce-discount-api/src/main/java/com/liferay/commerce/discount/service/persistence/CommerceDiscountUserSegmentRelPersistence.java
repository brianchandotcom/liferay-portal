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

package com.liferay.commerce.discount.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.discount.exception.NoSuchDiscountUserSegmentRelException;
import com.liferay.commerce.discount.model.CommerceDiscountUserSegmentRel;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce discount user segment rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.discount.service.persistence.impl.CommerceDiscountUserSegmentRelPersistenceImpl
 * @see CommerceDiscountUserSegmentRelUtil
 * @generated
 */
@ProviderType
public interface CommerceDiscountUserSegmentRelPersistence
	extends BasePersistence<CommerceDiscountUserSegmentRel> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceDiscountUserSegmentRelUtil} to access the commerce discount user segment rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce discount user segment rels where commerceDiscountId = &#63;.
	*
	* @param commerceDiscountId the commerce discount ID
	* @return the matching commerce discount user segment rels
	*/
	public java.util.List<CommerceDiscountUserSegmentRel> findByCommerceDiscountId(
		long commerceDiscountId);

	/**
	* Returns a range of all the commerce discount user segment rels where commerceDiscountId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceDiscountId the commerce discount ID
	* @param start the lower bound of the range of commerce discount user segment rels
	* @param end the upper bound of the range of commerce discount user segment rels (not inclusive)
	* @return the range of matching commerce discount user segment rels
	*/
	public java.util.List<CommerceDiscountUserSegmentRel> findByCommerceDiscountId(
		long commerceDiscountId, int start, int end);

	/**
	* Returns an ordered range of all the commerce discount user segment rels where commerceDiscountId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceDiscountId the commerce discount ID
	* @param start the lower bound of the range of commerce discount user segment rels
	* @param end the upper bound of the range of commerce discount user segment rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce discount user segment rels
	*/
	public java.util.List<CommerceDiscountUserSegmentRel> findByCommerceDiscountId(
		long commerceDiscountId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce discount user segment rels where commerceDiscountId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceDiscountId the commerce discount ID
	* @param start the lower bound of the range of commerce discount user segment rels
	* @param end the upper bound of the range of commerce discount user segment rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce discount user segment rels
	*/
	public java.util.List<CommerceDiscountUserSegmentRel> findByCommerceDiscountId(
		long commerceDiscountId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce discount user segment rel in the ordered set where commerceDiscountId = &#63;.
	*
	* @param commerceDiscountId the commerce discount ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce discount user segment rel
	* @throws NoSuchDiscountUserSegmentRelException if a matching commerce discount user segment rel could not be found
	*/
	public CommerceDiscountUserSegmentRel findByCommerceDiscountId_First(
		long commerceDiscountId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator)
		throws NoSuchDiscountUserSegmentRelException;

	/**
	* Returns the first commerce discount user segment rel in the ordered set where commerceDiscountId = &#63;.
	*
	* @param commerceDiscountId the commerce discount ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce discount user segment rel, or <code>null</code> if a matching commerce discount user segment rel could not be found
	*/
	public CommerceDiscountUserSegmentRel fetchByCommerceDiscountId_First(
		long commerceDiscountId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator);

	/**
	* Returns the last commerce discount user segment rel in the ordered set where commerceDiscountId = &#63;.
	*
	* @param commerceDiscountId the commerce discount ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce discount user segment rel
	* @throws NoSuchDiscountUserSegmentRelException if a matching commerce discount user segment rel could not be found
	*/
	public CommerceDiscountUserSegmentRel findByCommerceDiscountId_Last(
		long commerceDiscountId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator)
		throws NoSuchDiscountUserSegmentRelException;

	/**
	* Returns the last commerce discount user segment rel in the ordered set where commerceDiscountId = &#63;.
	*
	* @param commerceDiscountId the commerce discount ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce discount user segment rel, or <code>null</code> if a matching commerce discount user segment rel could not be found
	*/
	public CommerceDiscountUserSegmentRel fetchByCommerceDiscountId_Last(
		long commerceDiscountId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator);

	/**
	* Returns the commerce discount user segment rels before and after the current commerce discount user segment rel in the ordered set where commerceDiscountId = &#63;.
	*
	* @param commerceDiscountUserSegmentRelId the primary key of the current commerce discount user segment rel
	* @param commerceDiscountId the commerce discount ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce discount user segment rel
	* @throws NoSuchDiscountUserSegmentRelException if a commerce discount user segment rel with the primary key could not be found
	*/
	public CommerceDiscountUserSegmentRel[] findByCommerceDiscountId_PrevAndNext(
		long commerceDiscountUserSegmentRelId, long commerceDiscountId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator)
		throws NoSuchDiscountUserSegmentRelException;

	/**
	* Removes all the commerce discount user segment rels where commerceDiscountId = &#63; from the database.
	*
	* @param commerceDiscountId the commerce discount ID
	*/
	public void removeByCommerceDiscountId(long commerceDiscountId);

	/**
	* Returns the number of commerce discount user segment rels where commerceDiscountId = &#63;.
	*
	* @param commerceDiscountId the commerce discount ID
	* @return the number of matching commerce discount user segment rels
	*/
	public int countByCommerceDiscountId(long commerceDiscountId);

	/**
	* Returns all the commerce discount user segment rels where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @return the matching commerce discount user segment rels
	*/
	public java.util.List<CommerceDiscountUserSegmentRel> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId);

	/**
	* Returns a range of all the commerce discount user segment rels where commerceUserSegmentEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param start the lower bound of the range of commerce discount user segment rels
	* @param end the upper bound of the range of commerce discount user segment rels (not inclusive)
	* @return the range of matching commerce discount user segment rels
	*/
	public java.util.List<CommerceDiscountUserSegmentRel> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId, int start, int end);

	/**
	* Returns an ordered range of all the commerce discount user segment rels where commerceUserSegmentEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param start the lower bound of the range of commerce discount user segment rels
	* @param end the upper bound of the range of commerce discount user segment rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce discount user segment rels
	*/
	public java.util.List<CommerceDiscountUserSegmentRel> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce discount user segment rels where commerceUserSegmentEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param start the lower bound of the range of commerce discount user segment rels
	* @param end the upper bound of the range of commerce discount user segment rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce discount user segment rels
	*/
	public java.util.List<CommerceDiscountUserSegmentRel> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce discount user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce discount user segment rel
	* @throws NoSuchDiscountUserSegmentRelException if a matching commerce discount user segment rel could not be found
	*/
	public CommerceDiscountUserSegmentRel findByCommerceUserSegmentEntryId_First(
		long commerceUserSegmentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator)
		throws NoSuchDiscountUserSegmentRelException;

	/**
	* Returns the first commerce discount user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce discount user segment rel, or <code>null</code> if a matching commerce discount user segment rel could not be found
	*/
	public CommerceDiscountUserSegmentRel fetchByCommerceUserSegmentEntryId_First(
		long commerceUserSegmentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator);

	/**
	* Returns the last commerce discount user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce discount user segment rel
	* @throws NoSuchDiscountUserSegmentRelException if a matching commerce discount user segment rel could not be found
	*/
	public CommerceDiscountUserSegmentRel findByCommerceUserSegmentEntryId_Last(
		long commerceUserSegmentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator)
		throws NoSuchDiscountUserSegmentRelException;

	/**
	* Returns the last commerce discount user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce discount user segment rel, or <code>null</code> if a matching commerce discount user segment rel could not be found
	*/
	public CommerceDiscountUserSegmentRel fetchByCommerceUserSegmentEntryId_Last(
		long commerceUserSegmentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator);

	/**
	* Returns the commerce discount user segment rels before and after the current commerce discount user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceDiscountUserSegmentRelId the primary key of the current commerce discount user segment rel
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce discount user segment rel
	* @throws NoSuchDiscountUserSegmentRelException if a commerce discount user segment rel with the primary key could not be found
	*/
	public CommerceDiscountUserSegmentRel[] findByCommerceUserSegmentEntryId_PrevAndNext(
		long commerceDiscountUserSegmentRelId, long commerceUserSegmentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator)
		throws NoSuchDiscountUserSegmentRelException;

	/**
	* Removes all the commerce discount user segment rels where commerceUserSegmentEntryId = &#63; from the database.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	*/
	public void removeByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId);

	/**
	* Returns the number of commerce discount user segment rels where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @return the number of matching commerce discount user segment rels
	*/
	public int countByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId);

	/**
	* Caches the commerce discount user segment rel in the entity cache if it is enabled.
	*
	* @param commerceDiscountUserSegmentRel the commerce discount user segment rel
	*/
	public void cacheResult(
		CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel);

	/**
	* Caches the commerce discount user segment rels in the entity cache if it is enabled.
	*
	* @param commerceDiscountUserSegmentRels the commerce discount user segment rels
	*/
	public void cacheResult(
		java.util.List<CommerceDiscountUserSegmentRel> commerceDiscountUserSegmentRels);

	/**
	* Creates a new commerce discount user segment rel with the primary key. Does not add the commerce discount user segment rel to the database.
	*
	* @param commerceDiscountUserSegmentRelId the primary key for the new commerce discount user segment rel
	* @return the new commerce discount user segment rel
	*/
	public CommerceDiscountUserSegmentRel create(
		long commerceDiscountUserSegmentRelId);

	/**
	* Removes the commerce discount user segment rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceDiscountUserSegmentRelId the primary key of the commerce discount user segment rel
	* @return the commerce discount user segment rel that was removed
	* @throws NoSuchDiscountUserSegmentRelException if a commerce discount user segment rel with the primary key could not be found
	*/
	public CommerceDiscountUserSegmentRel remove(
		long commerceDiscountUserSegmentRelId)
		throws NoSuchDiscountUserSegmentRelException;

	public CommerceDiscountUserSegmentRel updateImpl(
		CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel);

	/**
	* Returns the commerce discount user segment rel with the primary key or throws a {@link NoSuchDiscountUserSegmentRelException} if it could not be found.
	*
	* @param commerceDiscountUserSegmentRelId the primary key of the commerce discount user segment rel
	* @return the commerce discount user segment rel
	* @throws NoSuchDiscountUserSegmentRelException if a commerce discount user segment rel with the primary key could not be found
	*/
	public CommerceDiscountUserSegmentRel findByPrimaryKey(
		long commerceDiscountUserSegmentRelId)
		throws NoSuchDiscountUserSegmentRelException;

	/**
	* Returns the commerce discount user segment rel with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceDiscountUserSegmentRelId the primary key of the commerce discount user segment rel
	* @return the commerce discount user segment rel, or <code>null</code> if a commerce discount user segment rel with the primary key could not be found
	*/
	public CommerceDiscountUserSegmentRel fetchByPrimaryKey(
		long commerceDiscountUserSegmentRelId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceDiscountUserSegmentRel> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce discount user segment rels.
	*
	* @return the commerce discount user segment rels
	*/
	public java.util.List<CommerceDiscountUserSegmentRel> findAll();

	/**
	* Returns a range of all the commerce discount user segment rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce discount user segment rels
	* @param end the upper bound of the range of commerce discount user segment rels (not inclusive)
	* @return the range of commerce discount user segment rels
	*/
	public java.util.List<CommerceDiscountUserSegmentRel> findAll(int start,
		int end);

	/**
	* Returns an ordered range of all the commerce discount user segment rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce discount user segment rels
	* @param end the upper bound of the range of commerce discount user segment rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce discount user segment rels
	*/
	public java.util.List<CommerceDiscountUserSegmentRel> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce discount user segment rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce discount user segment rels
	* @param end the upper bound of the range of commerce discount user segment rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce discount user segment rels
	*/
	public java.util.List<CommerceDiscountUserSegmentRel> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce discount user segment rels from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce discount user segment rels.
	*
	* @return the number of commerce discount user segment rels
	*/
	public int countAll();

	@Override
	public java.util.Set<String> getBadColumnNames();
}
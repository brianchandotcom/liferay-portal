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

package com.liferay.commerce.shipping.engine.fixed.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.shipping.engine.fixed.exception.NoSuchShippingFixedOptionRelException;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce shipping fixed option rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.shipping.engine.fixed.service.persistence.impl.CommerceShippingFixedOptionRelPersistenceImpl
 * @see CommerceShippingFixedOptionRelUtil
 * @generated
 */
@ProviderType
public interface CommerceShippingFixedOptionRelPersistence
	extends BasePersistence<CommerceShippingFixedOptionRel> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceShippingFixedOptionRelUtil} to access the commerce shipping fixed option rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce shipping fixed option rels where commerceShippingMethodId = &#63;.
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	* @return the matching commerce shipping fixed option rels
	*/
	public java.util.List<CommerceShippingFixedOptionRel> findByCommerceShippingMethodId(
		long commerceShippingMethodId);

	/**
	* Returns a range of all the commerce shipping fixed option rels where commerceShippingMethodId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	* @param start the lower bound of the range of commerce shipping fixed option rels
	* @param end the upper bound of the range of commerce shipping fixed option rels (not inclusive)
	* @return the range of matching commerce shipping fixed option rels
	*/
	public java.util.List<CommerceShippingFixedOptionRel> findByCommerceShippingMethodId(
		long commerceShippingMethodId, int start, int end);

	/**
	* Returns an ordered range of all the commerce shipping fixed option rels where commerceShippingMethodId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	* @param start the lower bound of the range of commerce shipping fixed option rels
	* @param end the upper bound of the range of commerce shipping fixed option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce shipping fixed option rels
	*/
	public java.util.List<CommerceShippingFixedOptionRel> findByCommerceShippingMethodId(
		long commerceShippingMethodId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce shipping fixed option rels where commerceShippingMethodId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	* @param start the lower bound of the range of commerce shipping fixed option rels
	* @param end the upper bound of the range of commerce shipping fixed option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce shipping fixed option rels
	*/
	public java.util.List<CommerceShippingFixedOptionRel> findByCommerceShippingMethodId(
		long commerceShippingMethodId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce shipping fixed option rel in the ordered set where commerceShippingMethodId = &#63;.
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce shipping fixed option rel
	* @throws NoSuchShippingFixedOptionRelException if a matching commerce shipping fixed option rel could not be found
	*/
	public CommerceShippingFixedOptionRel findByCommerceShippingMethodId_First(
		long commerceShippingMethodId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator)
		throws NoSuchShippingFixedOptionRelException;

	/**
	* Returns the first commerce shipping fixed option rel in the ordered set where commerceShippingMethodId = &#63;.
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce shipping fixed option rel, or <code>null</code> if a matching commerce shipping fixed option rel could not be found
	*/
	public CommerceShippingFixedOptionRel fetchByCommerceShippingMethodId_First(
		long commerceShippingMethodId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator);

	/**
	* Returns the last commerce shipping fixed option rel in the ordered set where commerceShippingMethodId = &#63;.
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce shipping fixed option rel
	* @throws NoSuchShippingFixedOptionRelException if a matching commerce shipping fixed option rel could not be found
	*/
	public CommerceShippingFixedOptionRel findByCommerceShippingMethodId_Last(
		long commerceShippingMethodId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator)
		throws NoSuchShippingFixedOptionRelException;

	/**
	* Returns the last commerce shipping fixed option rel in the ordered set where commerceShippingMethodId = &#63;.
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce shipping fixed option rel, or <code>null</code> if a matching commerce shipping fixed option rel could not be found
	*/
	public CommerceShippingFixedOptionRel fetchByCommerceShippingMethodId_Last(
		long commerceShippingMethodId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator);

	/**
	* Returns the commerce shipping fixed option rels before and after the current commerce shipping fixed option rel in the ordered set where commerceShippingMethodId = &#63;.
	*
	* @param commerceShippingFixedOptionRelId the primary key of the current commerce shipping fixed option rel
	* @param commerceShippingMethodId the commerce shipping method ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce shipping fixed option rel
	* @throws NoSuchShippingFixedOptionRelException if a commerce shipping fixed option rel with the primary key could not be found
	*/
	public CommerceShippingFixedOptionRel[] findByCommerceShippingMethodId_PrevAndNext(
		long commerceShippingFixedOptionRelId, long commerceShippingMethodId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator)
		throws NoSuchShippingFixedOptionRelException;

	/**
	* Removes all the commerce shipping fixed option rels where commerceShippingMethodId = &#63; from the database.
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	*/
	public void removeByCommerceShippingMethodId(long commerceShippingMethodId);

	/**
	* Returns the number of commerce shipping fixed option rels where commerceShippingMethodId = &#63;.
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	* @return the number of matching commerce shipping fixed option rels
	*/
	public int countByCommerceShippingMethodId(long commerceShippingMethodId);

	/**
	* Returns all the commerce shipping fixed option rels where commerceShippingFixedOptionId = &#63;.
	*
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	* @return the matching commerce shipping fixed option rels
	*/
	public java.util.List<CommerceShippingFixedOptionRel> findByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId);

	/**
	* Returns a range of all the commerce shipping fixed option rels where commerceShippingFixedOptionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	* @param start the lower bound of the range of commerce shipping fixed option rels
	* @param end the upper bound of the range of commerce shipping fixed option rels (not inclusive)
	* @return the range of matching commerce shipping fixed option rels
	*/
	public java.util.List<CommerceShippingFixedOptionRel> findByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId, int start, int end);

	/**
	* Returns an ordered range of all the commerce shipping fixed option rels where commerceShippingFixedOptionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	* @param start the lower bound of the range of commerce shipping fixed option rels
	* @param end the upper bound of the range of commerce shipping fixed option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce shipping fixed option rels
	*/
	public java.util.List<CommerceShippingFixedOptionRel> findByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce shipping fixed option rels where commerceShippingFixedOptionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	* @param start the lower bound of the range of commerce shipping fixed option rels
	* @param end the upper bound of the range of commerce shipping fixed option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce shipping fixed option rels
	*/
	public java.util.List<CommerceShippingFixedOptionRel> findByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce shipping fixed option rel in the ordered set where commerceShippingFixedOptionId = &#63;.
	*
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce shipping fixed option rel
	* @throws NoSuchShippingFixedOptionRelException if a matching commerce shipping fixed option rel could not be found
	*/
	public CommerceShippingFixedOptionRel findByCommerceShippingFixedOptionId_First(
		long commerceShippingFixedOptionId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator)
		throws NoSuchShippingFixedOptionRelException;

	/**
	* Returns the first commerce shipping fixed option rel in the ordered set where commerceShippingFixedOptionId = &#63;.
	*
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce shipping fixed option rel, or <code>null</code> if a matching commerce shipping fixed option rel could not be found
	*/
	public CommerceShippingFixedOptionRel fetchByCommerceShippingFixedOptionId_First(
		long commerceShippingFixedOptionId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator);

	/**
	* Returns the last commerce shipping fixed option rel in the ordered set where commerceShippingFixedOptionId = &#63;.
	*
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce shipping fixed option rel
	* @throws NoSuchShippingFixedOptionRelException if a matching commerce shipping fixed option rel could not be found
	*/
	public CommerceShippingFixedOptionRel findByCommerceShippingFixedOptionId_Last(
		long commerceShippingFixedOptionId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator)
		throws NoSuchShippingFixedOptionRelException;

	/**
	* Returns the last commerce shipping fixed option rel in the ordered set where commerceShippingFixedOptionId = &#63;.
	*
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce shipping fixed option rel, or <code>null</code> if a matching commerce shipping fixed option rel could not be found
	*/
	public CommerceShippingFixedOptionRel fetchByCommerceShippingFixedOptionId_Last(
		long commerceShippingFixedOptionId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator);

	/**
	* Returns the commerce shipping fixed option rels before and after the current commerce shipping fixed option rel in the ordered set where commerceShippingFixedOptionId = &#63;.
	*
	* @param commerceShippingFixedOptionRelId the primary key of the current commerce shipping fixed option rel
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce shipping fixed option rel
	* @throws NoSuchShippingFixedOptionRelException if a commerce shipping fixed option rel with the primary key could not be found
	*/
	public CommerceShippingFixedOptionRel[] findByCommerceShippingFixedOptionId_PrevAndNext(
		long commerceShippingFixedOptionRelId,
		long commerceShippingFixedOptionId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator)
		throws NoSuchShippingFixedOptionRelException;

	/**
	* Removes all the commerce shipping fixed option rels where commerceShippingFixedOptionId = &#63; from the database.
	*
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	*/
	public void removeByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId);

	/**
	* Returns the number of commerce shipping fixed option rels where commerceShippingFixedOptionId = &#63;.
	*
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	* @return the number of matching commerce shipping fixed option rels
	*/
	public int countByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId);

	/**
	* Caches the commerce shipping fixed option rel in the entity cache if it is enabled.
	*
	* @param commerceShippingFixedOptionRel the commerce shipping fixed option rel
	*/
	public void cacheResult(
		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel);

	/**
	* Caches the commerce shipping fixed option rels in the entity cache if it is enabled.
	*
	* @param commerceShippingFixedOptionRels the commerce shipping fixed option rels
	*/
	public void cacheResult(
		java.util.List<CommerceShippingFixedOptionRel> commerceShippingFixedOptionRels);

	/**
	* Creates a new commerce shipping fixed option rel with the primary key. Does not add the commerce shipping fixed option rel to the database.
	*
	* @param commerceShippingFixedOptionRelId the primary key for the new commerce shipping fixed option rel
	* @return the new commerce shipping fixed option rel
	*/
	public CommerceShippingFixedOptionRel create(
		long commerceShippingFixedOptionRelId);

	/**
	* Removes the commerce shipping fixed option rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceShippingFixedOptionRelId the primary key of the commerce shipping fixed option rel
	* @return the commerce shipping fixed option rel that was removed
	* @throws NoSuchShippingFixedOptionRelException if a commerce shipping fixed option rel with the primary key could not be found
	*/
	public CommerceShippingFixedOptionRel remove(
		long commerceShippingFixedOptionRelId)
		throws NoSuchShippingFixedOptionRelException;

	public CommerceShippingFixedOptionRel updateImpl(
		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel);

	/**
	* Returns the commerce shipping fixed option rel with the primary key or throws a {@link NoSuchShippingFixedOptionRelException} if it could not be found.
	*
	* @param commerceShippingFixedOptionRelId the primary key of the commerce shipping fixed option rel
	* @return the commerce shipping fixed option rel
	* @throws NoSuchShippingFixedOptionRelException if a commerce shipping fixed option rel with the primary key could not be found
	*/
	public CommerceShippingFixedOptionRel findByPrimaryKey(
		long commerceShippingFixedOptionRelId)
		throws NoSuchShippingFixedOptionRelException;

	/**
	* Returns the commerce shipping fixed option rel with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceShippingFixedOptionRelId the primary key of the commerce shipping fixed option rel
	* @return the commerce shipping fixed option rel, or <code>null</code> if a commerce shipping fixed option rel with the primary key could not be found
	*/
	public CommerceShippingFixedOptionRel fetchByPrimaryKey(
		long commerceShippingFixedOptionRelId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceShippingFixedOptionRel> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce shipping fixed option rels.
	*
	* @return the commerce shipping fixed option rels
	*/
	public java.util.List<CommerceShippingFixedOptionRel> findAll();

	/**
	* Returns a range of all the commerce shipping fixed option rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce shipping fixed option rels
	* @param end the upper bound of the range of commerce shipping fixed option rels (not inclusive)
	* @return the range of commerce shipping fixed option rels
	*/
	public java.util.List<CommerceShippingFixedOptionRel> findAll(int start,
		int end);

	/**
	* Returns an ordered range of all the commerce shipping fixed option rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce shipping fixed option rels
	* @param end the upper bound of the range of commerce shipping fixed option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce shipping fixed option rels
	*/
	public java.util.List<CommerceShippingFixedOptionRel> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce shipping fixed option rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce shipping fixed option rels
	* @param end the upper bound of the range of commerce shipping fixed option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce shipping fixed option rels
	*/
	public java.util.List<CommerceShippingFixedOptionRel> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce shipping fixed option rels from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce shipping fixed option rels.
	*
	* @return the number of commerce shipping fixed option rels
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}
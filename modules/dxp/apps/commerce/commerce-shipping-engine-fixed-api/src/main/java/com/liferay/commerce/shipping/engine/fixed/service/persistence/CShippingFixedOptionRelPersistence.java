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

import com.liferay.commerce.shipping.engine.fixed.exception.NoSuchCShippingFixedOptionRelException;
import com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the c shipping fixed option rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.shipping.engine.fixed.service.persistence.impl.CShippingFixedOptionRelPersistenceImpl
 * @see CShippingFixedOptionRelUtil
 * @generated
 */
@ProviderType
public interface CShippingFixedOptionRelPersistence extends BasePersistence<CShippingFixedOptionRel> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CShippingFixedOptionRelUtil} to access the c shipping fixed option rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the c shipping fixed option rels where commerceShippingMethodId = &#63;.
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	* @return the matching c shipping fixed option rels
	*/
	public java.util.List<CShippingFixedOptionRel> findByCommerceShippingMethodId(
		long commerceShippingMethodId);

	/**
	* Returns a range of all the c shipping fixed option rels where commerceShippingMethodId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	* @param start the lower bound of the range of c shipping fixed option rels
	* @param end the upper bound of the range of c shipping fixed option rels (not inclusive)
	* @return the range of matching c shipping fixed option rels
	*/
	public java.util.List<CShippingFixedOptionRel> findByCommerceShippingMethodId(
		long commerceShippingMethodId, int start, int end);

	/**
	* Returns an ordered range of all the c shipping fixed option rels where commerceShippingMethodId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	* @param start the lower bound of the range of c shipping fixed option rels
	* @param end the upper bound of the range of c shipping fixed option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching c shipping fixed option rels
	*/
	public java.util.List<CShippingFixedOptionRel> findByCommerceShippingMethodId(
		long commerceShippingMethodId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CShippingFixedOptionRel> orderByComparator);

	/**
	* Returns an ordered range of all the c shipping fixed option rels where commerceShippingMethodId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	* @param start the lower bound of the range of c shipping fixed option rels
	* @param end the upper bound of the range of c shipping fixed option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching c shipping fixed option rels
	*/
	public java.util.List<CShippingFixedOptionRel> findByCommerceShippingMethodId(
		long commerceShippingMethodId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CShippingFixedOptionRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first c shipping fixed option rel in the ordered set where commerceShippingMethodId = &#63;.
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c shipping fixed option rel
	* @throws NoSuchCShippingFixedOptionRelException if a matching c shipping fixed option rel could not be found
	*/
	public CShippingFixedOptionRel findByCommerceShippingMethodId_First(
		long commerceShippingMethodId,
		com.liferay.portal.kernel.util.OrderByComparator<CShippingFixedOptionRel> orderByComparator)
		throws NoSuchCShippingFixedOptionRelException;

	/**
	* Returns the first c shipping fixed option rel in the ordered set where commerceShippingMethodId = &#63;.
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c shipping fixed option rel, or <code>null</code> if a matching c shipping fixed option rel could not be found
	*/
	public CShippingFixedOptionRel fetchByCommerceShippingMethodId_First(
		long commerceShippingMethodId,
		com.liferay.portal.kernel.util.OrderByComparator<CShippingFixedOptionRel> orderByComparator);

	/**
	* Returns the last c shipping fixed option rel in the ordered set where commerceShippingMethodId = &#63;.
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c shipping fixed option rel
	* @throws NoSuchCShippingFixedOptionRelException if a matching c shipping fixed option rel could not be found
	*/
	public CShippingFixedOptionRel findByCommerceShippingMethodId_Last(
		long commerceShippingMethodId,
		com.liferay.portal.kernel.util.OrderByComparator<CShippingFixedOptionRel> orderByComparator)
		throws NoSuchCShippingFixedOptionRelException;

	/**
	* Returns the last c shipping fixed option rel in the ordered set where commerceShippingMethodId = &#63;.
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c shipping fixed option rel, or <code>null</code> if a matching c shipping fixed option rel could not be found
	*/
	public CShippingFixedOptionRel fetchByCommerceShippingMethodId_Last(
		long commerceShippingMethodId,
		com.liferay.portal.kernel.util.OrderByComparator<CShippingFixedOptionRel> orderByComparator);

	/**
	* Returns the c shipping fixed option rels before and after the current c shipping fixed option rel in the ordered set where commerceShippingMethodId = &#63;.
	*
	* @param CShippingFixedOptionRelId the primary key of the current c shipping fixed option rel
	* @param commerceShippingMethodId the commerce shipping method ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next c shipping fixed option rel
	* @throws NoSuchCShippingFixedOptionRelException if a c shipping fixed option rel with the primary key could not be found
	*/
	public CShippingFixedOptionRel[] findByCommerceShippingMethodId_PrevAndNext(
		long CShippingFixedOptionRelId, long commerceShippingMethodId,
		com.liferay.portal.kernel.util.OrderByComparator<CShippingFixedOptionRel> orderByComparator)
		throws NoSuchCShippingFixedOptionRelException;

	/**
	* Removes all the c shipping fixed option rels where commerceShippingMethodId = &#63; from the database.
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	*/
	public void removeByCommerceShippingMethodId(long commerceShippingMethodId);

	/**
	* Returns the number of c shipping fixed option rels where commerceShippingMethodId = &#63;.
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	* @return the number of matching c shipping fixed option rels
	*/
	public int countByCommerceShippingMethodId(long commerceShippingMethodId);

	/**
	* Returns all the c shipping fixed option rels where commerceShippingFixedOptionId = &#63;.
	*
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	* @return the matching c shipping fixed option rels
	*/
	public java.util.List<CShippingFixedOptionRel> findByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId);

	/**
	* Returns a range of all the c shipping fixed option rels where commerceShippingFixedOptionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	* @param start the lower bound of the range of c shipping fixed option rels
	* @param end the upper bound of the range of c shipping fixed option rels (not inclusive)
	* @return the range of matching c shipping fixed option rels
	*/
	public java.util.List<CShippingFixedOptionRel> findByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId, int start, int end);

	/**
	* Returns an ordered range of all the c shipping fixed option rels where commerceShippingFixedOptionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	* @param start the lower bound of the range of c shipping fixed option rels
	* @param end the upper bound of the range of c shipping fixed option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching c shipping fixed option rels
	*/
	public java.util.List<CShippingFixedOptionRel> findByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CShippingFixedOptionRel> orderByComparator);

	/**
	* Returns an ordered range of all the c shipping fixed option rels where commerceShippingFixedOptionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	* @param start the lower bound of the range of c shipping fixed option rels
	* @param end the upper bound of the range of c shipping fixed option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching c shipping fixed option rels
	*/
	public java.util.List<CShippingFixedOptionRel> findByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CShippingFixedOptionRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first c shipping fixed option rel in the ordered set where commerceShippingFixedOptionId = &#63;.
	*
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c shipping fixed option rel
	* @throws NoSuchCShippingFixedOptionRelException if a matching c shipping fixed option rel could not be found
	*/
	public CShippingFixedOptionRel findByCommerceShippingFixedOptionId_First(
		long commerceShippingFixedOptionId,
		com.liferay.portal.kernel.util.OrderByComparator<CShippingFixedOptionRel> orderByComparator)
		throws NoSuchCShippingFixedOptionRelException;

	/**
	* Returns the first c shipping fixed option rel in the ordered set where commerceShippingFixedOptionId = &#63;.
	*
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c shipping fixed option rel, or <code>null</code> if a matching c shipping fixed option rel could not be found
	*/
	public CShippingFixedOptionRel fetchByCommerceShippingFixedOptionId_First(
		long commerceShippingFixedOptionId,
		com.liferay.portal.kernel.util.OrderByComparator<CShippingFixedOptionRel> orderByComparator);

	/**
	* Returns the last c shipping fixed option rel in the ordered set where commerceShippingFixedOptionId = &#63;.
	*
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c shipping fixed option rel
	* @throws NoSuchCShippingFixedOptionRelException if a matching c shipping fixed option rel could not be found
	*/
	public CShippingFixedOptionRel findByCommerceShippingFixedOptionId_Last(
		long commerceShippingFixedOptionId,
		com.liferay.portal.kernel.util.OrderByComparator<CShippingFixedOptionRel> orderByComparator)
		throws NoSuchCShippingFixedOptionRelException;

	/**
	* Returns the last c shipping fixed option rel in the ordered set where commerceShippingFixedOptionId = &#63;.
	*
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c shipping fixed option rel, or <code>null</code> if a matching c shipping fixed option rel could not be found
	*/
	public CShippingFixedOptionRel fetchByCommerceShippingFixedOptionId_Last(
		long commerceShippingFixedOptionId,
		com.liferay.portal.kernel.util.OrderByComparator<CShippingFixedOptionRel> orderByComparator);

	/**
	* Returns the c shipping fixed option rels before and after the current c shipping fixed option rel in the ordered set where commerceShippingFixedOptionId = &#63;.
	*
	* @param CShippingFixedOptionRelId the primary key of the current c shipping fixed option rel
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next c shipping fixed option rel
	* @throws NoSuchCShippingFixedOptionRelException if a c shipping fixed option rel with the primary key could not be found
	*/
	public CShippingFixedOptionRel[] findByCommerceShippingFixedOptionId_PrevAndNext(
		long CShippingFixedOptionRelId, long commerceShippingFixedOptionId,
		com.liferay.portal.kernel.util.OrderByComparator<CShippingFixedOptionRel> orderByComparator)
		throws NoSuchCShippingFixedOptionRelException;

	/**
	* Removes all the c shipping fixed option rels where commerceShippingFixedOptionId = &#63; from the database.
	*
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	*/
	public void removeByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId);

	/**
	* Returns the number of c shipping fixed option rels where commerceShippingFixedOptionId = &#63;.
	*
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	* @return the number of matching c shipping fixed option rels
	*/
	public int countByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId);

	/**
	* Caches the c shipping fixed option rel in the entity cache if it is enabled.
	*
	* @param cShippingFixedOptionRel the c shipping fixed option rel
	*/
	public void cacheResult(CShippingFixedOptionRel cShippingFixedOptionRel);

	/**
	* Caches the c shipping fixed option rels in the entity cache if it is enabled.
	*
	* @param cShippingFixedOptionRels the c shipping fixed option rels
	*/
	public void cacheResult(
		java.util.List<CShippingFixedOptionRel> cShippingFixedOptionRels);

	/**
	* Creates a new c shipping fixed option rel with the primary key. Does not add the c shipping fixed option rel to the database.
	*
	* @param CShippingFixedOptionRelId the primary key for the new c shipping fixed option rel
	* @return the new c shipping fixed option rel
	*/
	public CShippingFixedOptionRel create(long CShippingFixedOptionRelId);

	/**
	* Removes the c shipping fixed option rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CShippingFixedOptionRelId the primary key of the c shipping fixed option rel
	* @return the c shipping fixed option rel that was removed
	* @throws NoSuchCShippingFixedOptionRelException if a c shipping fixed option rel with the primary key could not be found
	*/
	public CShippingFixedOptionRel remove(long CShippingFixedOptionRelId)
		throws NoSuchCShippingFixedOptionRelException;

	public CShippingFixedOptionRel updateImpl(
		CShippingFixedOptionRel cShippingFixedOptionRel);

	/**
	* Returns the c shipping fixed option rel with the primary key or throws a {@link NoSuchCShippingFixedOptionRelException} if it could not be found.
	*
	* @param CShippingFixedOptionRelId the primary key of the c shipping fixed option rel
	* @return the c shipping fixed option rel
	* @throws NoSuchCShippingFixedOptionRelException if a c shipping fixed option rel with the primary key could not be found
	*/
	public CShippingFixedOptionRel findByPrimaryKey(
		long CShippingFixedOptionRelId)
		throws NoSuchCShippingFixedOptionRelException;

	/**
	* Returns the c shipping fixed option rel with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CShippingFixedOptionRelId the primary key of the c shipping fixed option rel
	* @return the c shipping fixed option rel, or <code>null</code> if a c shipping fixed option rel with the primary key could not be found
	*/
	public CShippingFixedOptionRel fetchByPrimaryKey(
		long CShippingFixedOptionRelId);

	@Override
	public java.util.Map<java.io.Serializable, CShippingFixedOptionRel> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the c shipping fixed option rels.
	*
	* @return the c shipping fixed option rels
	*/
	public java.util.List<CShippingFixedOptionRel> findAll();

	/**
	* Returns a range of all the c shipping fixed option rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of c shipping fixed option rels
	* @param end the upper bound of the range of c shipping fixed option rels (not inclusive)
	* @return the range of c shipping fixed option rels
	*/
	public java.util.List<CShippingFixedOptionRel> findAll(int start, int end);

	/**
	* Returns an ordered range of all the c shipping fixed option rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of c shipping fixed option rels
	* @param end the upper bound of the range of c shipping fixed option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of c shipping fixed option rels
	*/
	public java.util.List<CShippingFixedOptionRel> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CShippingFixedOptionRel> orderByComparator);

	/**
	* Returns an ordered range of all the c shipping fixed option rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of c shipping fixed option rels
	* @param end the upper bound of the range of c shipping fixed option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of c shipping fixed option rels
	*/
	public java.util.List<CShippingFixedOptionRel> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CShippingFixedOptionRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the c shipping fixed option rels from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of c shipping fixed option rels.
	*
	* @return the number of c shipping fixed option rels
	*/
	public int countAll();
}
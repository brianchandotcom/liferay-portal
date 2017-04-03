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

package com.liferay.commerce.product.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.exception.NoSuchProductDefintionOptionRelException;
import com.liferay.commerce.product.model.CommerceProductDefintionOptionRel;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce product defintion option rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.persistence.impl.CommerceProductDefintionOptionRelPersistenceImpl
 * @see CommerceProductDefintionOptionRelUtil
 * @generated
 */
@ProviderType
public interface CommerceProductDefintionOptionRelPersistence
	extends BasePersistence<CommerceProductDefintionOptionRel> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceProductDefintionOptionRelUtil} to access the commerce product defintion option rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce product defintion option rels where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce product defintion option rels
	*/
	public java.util.List<CommerceProductDefintionOptionRel> findByGroupId(
		long groupId);

	/**
	* Returns a range of all the commerce product defintion option rels where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefintionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product defintion option rels
	* @param end the upper bound of the range of commerce product defintion option rels (not inclusive)
	* @return the range of matching commerce product defintion option rels
	*/
	public java.util.List<CommerceProductDefintionOptionRel> findByGroupId(
		long groupId, int start, int end);

	/**
	* Returns an ordered range of all the commerce product defintion option rels where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefintionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product defintion option rels
	* @param end the upper bound of the range of commerce product defintion option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product defintion option rels
	*/
	public java.util.List<CommerceProductDefintionOptionRel> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefintionOptionRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce product defintion option rels where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefintionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product defintion option rels
	* @param end the upper bound of the range of commerce product defintion option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product defintion option rels
	*/
	public java.util.List<CommerceProductDefintionOptionRel> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefintionOptionRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce product defintion option rel in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product defintion option rel
	* @throws NoSuchProductDefintionOptionRelException if a matching commerce product defintion option rel could not be found
	*/
	public CommerceProductDefintionOptionRel findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefintionOptionRel> orderByComparator)
		throws NoSuchProductDefintionOptionRelException;

	/**
	* Returns the first commerce product defintion option rel in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product defintion option rel, or <code>null</code> if a matching commerce product defintion option rel could not be found
	*/
	public CommerceProductDefintionOptionRel fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefintionOptionRel> orderByComparator);

	/**
	* Returns the last commerce product defintion option rel in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product defintion option rel
	* @throws NoSuchProductDefintionOptionRelException if a matching commerce product defintion option rel could not be found
	*/
	public CommerceProductDefintionOptionRel findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefintionOptionRel> orderByComparator)
		throws NoSuchProductDefintionOptionRelException;

	/**
	* Returns the last commerce product defintion option rel in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product defintion option rel, or <code>null</code> if a matching commerce product defintion option rel could not be found
	*/
	public CommerceProductDefintionOptionRel fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefintionOptionRel> orderByComparator);

	/**
	* Returns the commerce product defintion option rels before and after the current commerce product defintion option rel in the ordered set where groupId = &#63;.
	*
	* @param commerceProductDefintionOptionRelId the primary key of the current commerce product defintion option rel
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product defintion option rel
	* @throws NoSuchProductDefintionOptionRelException if a commerce product defintion option rel with the primary key could not be found
	*/
	public CommerceProductDefintionOptionRel[] findByGroupId_PrevAndNext(
		long commerceProductDefintionOptionRelId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefintionOptionRel> orderByComparator)
		throws NoSuchProductDefintionOptionRelException;

	/**
	* Removes all the commerce product defintion option rels where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of commerce product defintion option rels where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce product defintion option rels
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the commerce product defintion option rels where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching commerce product defintion option rels
	*/
	public java.util.List<CommerceProductDefintionOptionRel> findByCompanyId(
		long companyId);

	/**
	* Returns a range of all the commerce product defintion option rels where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefintionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product defintion option rels
	* @param end the upper bound of the range of commerce product defintion option rels (not inclusive)
	* @return the range of matching commerce product defintion option rels
	*/
	public java.util.List<CommerceProductDefintionOptionRel> findByCompanyId(
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the commerce product defintion option rels where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefintionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product defintion option rels
	* @param end the upper bound of the range of commerce product defintion option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product defintion option rels
	*/
	public java.util.List<CommerceProductDefintionOptionRel> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefintionOptionRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce product defintion option rels where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefintionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product defintion option rels
	* @param end the upper bound of the range of commerce product defintion option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product defintion option rels
	*/
	public java.util.List<CommerceProductDefintionOptionRel> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefintionOptionRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce product defintion option rel in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product defintion option rel
	* @throws NoSuchProductDefintionOptionRelException if a matching commerce product defintion option rel could not be found
	*/
	public CommerceProductDefintionOptionRel findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefintionOptionRel> orderByComparator)
		throws NoSuchProductDefintionOptionRelException;

	/**
	* Returns the first commerce product defintion option rel in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product defintion option rel, or <code>null</code> if a matching commerce product defintion option rel could not be found
	*/
	public CommerceProductDefintionOptionRel fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefintionOptionRel> orderByComparator);

	/**
	* Returns the last commerce product defintion option rel in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product defintion option rel
	* @throws NoSuchProductDefintionOptionRelException if a matching commerce product defintion option rel could not be found
	*/
	public CommerceProductDefintionOptionRel findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefintionOptionRel> orderByComparator)
		throws NoSuchProductDefintionOptionRelException;

	/**
	* Returns the last commerce product defintion option rel in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product defintion option rel, or <code>null</code> if a matching commerce product defintion option rel could not be found
	*/
	public CommerceProductDefintionOptionRel fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefintionOptionRel> orderByComparator);

	/**
	* Returns the commerce product defintion option rels before and after the current commerce product defintion option rel in the ordered set where companyId = &#63;.
	*
	* @param commerceProductDefintionOptionRelId the primary key of the current commerce product defintion option rel
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product defintion option rel
	* @throws NoSuchProductDefintionOptionRelException if a commerce product defintion option rel with the primary key could not be found
	*/
	public CommerceProductDefintionOptionRel[] findByCompanyId_PrevAndNext(
		long commerceProductDefintionOptionRelId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefintionOptionRel> orderByComparator)
		throws NoSuchProductDefintionOptionRelException;

	/**
	* Removes all the commerce product defintion option rels where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of commerce product defintion option rels where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching commerce product defintion option rels
	*/
	public int countByCompanyId(long companyId);

	/**
	* Caches the commerce product defintion option rel in the entity cache if it is enabled.
	*
	* @param commerceProductDefintionOptionRel the commerce product defintion option rel
	*/
	public void cacheResult(
		CommerceProductDefintionOptionRel commerceProductDefintionOptionRel);

	/**
	* Caches the commerce product defintion option rels in the entity cache if it is enabled.
	*
	* @param commerceProductDefintionOptionRels the commerce product defintion option rels
	*/
	public void cacheResult(
		java.util.List<CommerceProductDefintionOptionRel> commerceProductDefintionOptionRels);

	/**
	* Creates a new commerce product defintion option rel with the primary key. Does not add the commerce product defintion option rel to the database.
	*
	* @param commerceProductDefintionOptionRelId the primary key for the new commerce product defintion option rel
	* @return the new commerce product defintion option rel
	*/
	public CommerceProductDefintionOptionRel create(
		long commerceProductDefintionOptionRelId);

	/**
	* Removes the commerce product defintion option rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductDefintionOptionRelId the primary key of the commerce product defintion option rel
	* @return the commerce product defintion option rel that was removed
	* @throws NoSuchProductDefintionOptionRelException if a commerce product defintion option rel with the primary key could not be found
	*/
	public CommerceProductDefintionOptionRel remove(
		long commerceProductDefintionOptionRelId)
		throws NoSuchProductDefintionOptionRelException;

	public CommerceProductDefintionOptionRel updateImpl(
		CommerceProductDefintionOptionRel commerceProductDefintionOptionRel);

	/**
	* Returns the commerce product defintion option rel with the primary key or throws a {@link NoSuchProductDefintionOptionRelException} if it could not be found.
	*
	* @param commerceProductDefintionOptionRelId the primary key of the commerce product defintion option rel
	* @return the commerce product defintion option rel
	* @throws NoSuchProductDefintionOptionRelException if a commerce product defintion option rel with the primary key could not be found
	*/
	public CommerceProductDefintionOptionRel findByPrimaryKey(
		long commerceProductDefintionOptionRelId)
		throws NoSuchProductDefintionOptionRelException;

	/**
	* Returns the commerce product defintion option rel with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceProductDefintionOptionRelId the primary key of the commerce product defintion option rel
	* @return the commerce product defintion option rel, or <code>null</code> if a commerce product defintion option rel with the primary key could not be found
	*/
	public CommerceProductDefintionOptionRel fetchByPrimaryKey(
		long commerceProductDefintionOptionRelId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceProductDefintionOptionRel> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce product defintion option rels.
	*
	* @return the commerce product defintion option rels
	*/
	public java.util.List<CommerceProductDefintionOptionRel> findAll();

	/**
	* Returns a range of all the commerce product defintion option rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefintionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product defintion option rels
	* @param end the upper bound of the range of commerce product defintion option rels (not inclusive)
	* @return the range of commerce product defintion option rels
	*/
	public java.util.List<CommerceProductDefintionOptionRel> findAll(
		int start, int end);

	/**
	* Returns an ordered range of all the commerce product defintion option rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefintionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product defintion option rels
	* @param end the upper bound of the range of commerce product defintion option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce product defintion option rels
	*/
	public java.util.List<CommerceProductDefintionOptionRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefintionOptionRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce product defintion option rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefintionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product defintion option rels
	* @param end the upper bound of the range of commerce product defintion option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce product defintion option rels
	*/
	public java.util.List<CommerceProductDefintionOptionRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefintionOptionRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce product defintion option rels from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce product defintion option rels.
	*
	* @return the number of commerce product defintion option rels
	*/
	public int countAll();
}
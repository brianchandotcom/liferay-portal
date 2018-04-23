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

import com.liferay.commerce.product.exception.NoSuchCPRuleUserSegmentRelException;
import com.liferay.commerce.product.model.CPRuleUserSegmentRel;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the cp rule user segment rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.persistence.impl.CPRuleUserSegmentRelPersistenceImpl
 * @see CPRuleUserSegmentRelUtil
 * @generated
 */
@ProviderType
public interface CPRuleUserSegmentRelPersistence extends BasePersistence<CPRuleUserSegmentRel> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPRuleUserSegmentRelUtil} to access the cp rule user segment rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the cp rule user segment rels where CPRuleId = &#63;.
	*
	* @param CPRuleId the cp rule ID
	* @return the matching cp rule user segment rels
	*/
	public java.util.List<CPRuleUserSegmentRel> findByCPRuleId(long CPRuleId);

	/**
	* Returns a range of all the cp rule user segment rels where CPRuleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPRuleId the cp rule ID
	* @param start the lower bound of the range of cp rule user segment rels
	* @param end the upper bound of the range of cp rule user segment rels (not inclusive)
	* @return the range of matching cp rule user segment rels
	*/
	public java.util.List<CPRuleUserSegmentRel> findByCPRuleId(long CPRuleId,
		int start, int end);

	/**
	* Returns an ordered range of all the cp rule user segment rels where CPRuleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPRuleId the cp rule ID
	* @param start the lower bound of the range of cp rule user segment rels
	* @param end the upper bound of the range of cp rule user segment rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp rule user segment rels
	*/
	public java.util.List<CPRuleUserSegmentRel> findByCPRuleId(long CPRuleId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPRuleUserSegmentRel> orderByComparator);

	/**
	* Returns an ordered range of all the cp rule user segment rels where CPRuleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPRuleId the cp rule ID
	* @param start the lower bound of the range of cp rule user segment rels
	* @param end the upper bound of the range of cp rule user segment rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp rule user segment rels
	*/
	public java.util.List<CPRuleUserSegmentRel> findByCPRuleId(long CPRuleId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPRuleUserSegmentRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp rule user segment rel in the ordered set where CPRuleId = &#63;.
	*
	* @param CPRuleId the cp rule ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp rule user segment rel
	* @throws NoSuchCPRuleUserSegmentRelException if a matching cp rule user segment rel could not be found
	*/
	public CPRuleUserSegmentRel findByCPRuleId_First(long CPRuleId,
		com.liferay.portal.kernel.util.OrderByComparator<CPRuleUserSegmentRel> orderByComparator)
		throws NoSuchCPRuleUserSegmentRelException;

	/**
	* Returns the first cp rule user segment rel in the ordered set where CPRuleId = &#63;.
	*
	* @param CPRuleId the cp rule ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp rule user segment rel, or <code>null</code> if a matching cp rule user segment rel could not be found
	*/
	public CPRuleUserSegmentRel fetchByCPRuleId_First(long CPRuleId,
		com.liferay.portal.kernel.util.OrderByComparator<CPRuleUserSegmentRel> orderByComparator);

	/**
	* Returns the last cp rule user segment rel in the ordered set where CPRuleId = &#63;.
	*
	* @param CPRuleId the cp rule ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp rule user segment rel
	* @throws NoSuchCPRuleUserSegmentRelException if a matching cp rule user segment rel could not be found
	*/
	public CPRuleUserSegmentRel findByCPRuleId_Last(long CPRuleId,
		com.liferay.portal.kernel.util.OrderByComparator<CPRuleUserSegmentRel> orderByComparator)
		throws NoSuchCPRuleUserSegmentRelException;

	/**
	* Returns the last cp rule user segment rel in the ordered set where CPRuleId = &#63;.
	*
	* @param CPRuleId the cp rule ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp rule user segment rel, or <code>null</code> if a matching cp rule user segment rel could not be found
	*/
	public CPRuleUserSegmentRel fetchByCPRuleId_Last(long CPRuleId,
		com.liferay.portal.kernel.util.OrderByComparator<CPRuleUserSegmentRel> orderByComparator);

	/**
	* Returns the cp rule user segment rels before and after the current cp rule user segment rel in the ordered set where CPRuleId = &#63;.
	*
	* @param CPRuleUserSegmentRelId the primary key of the current cp rule user segment rel
	* @param CPRuleId the cp rule ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp rule user segment rel
	* @throws NoSuchCPRuleUserSegmentRelException if a cp rule user segment rel with the primary key could not be found
	*/
	public CPRuleUserSegmentRel[] findByCPRuleId_PrevAndNext(
		long CPRuleUserSegmentRelId, long CPRuleId,
		com.liferay.portal.kernel.util.OrderByComparator<CPRuleUserSegmentRel> orderByComparator)
		throws NoSuchCPRuleUserSegmentRelException;

	/**
	* Removes all the cp rule user segment rels where CPRuleId = &#63; from the database.
	*
	* @param CPRuleId the cp rule ID
	*/
	public void removeByCPRuleId(long CPRuleId);

	/**
	* Returns the number of cp rule user segment rels where CPRuleId = &#63;.
	*
	* @param CPRuleId the cp rule ID
	* @return the number of matching cp rule user segment rels
	*/
	public int countByCPRuleId(long CPRuleId);

	/**
	* Returns all the cp rule user segment rels where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @return the matching cp rule user segment rels
	*/
	public java.util.List<CPRuleUserSegmentRel> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId);

	/**
	* Returns a range of all the cp rule user segment rels where commerceUserSegmentEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param start the lower bound of the range of cp rule user segment rels
	* @param end the upper bound of the range of cp rule user segment rels (not inclusive)
	* @return the range of matching cp rule user segment rels
	*/
	public java.util.List<CPRuleUserSegmentRel> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId, int start, int end);

	/**
	* Returns an ordered range of all the cp rule user segment rels where commerceUserSegmentEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param start the lower bound of the range of cp rule user segment rels
	* @param end the upper bound of the range of cp rule user segment rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp rule user segment rels
	*/
	public java.util.List<CPRuleUserSegmentRel> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPRuleUserSegmentRel> orderByComparator);

	/**
	* Returns an ordered range of all the cp rule user segment rels where commerceUserSegmentEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param start the lower bound of the range of cp rule user segment rels
	* @param end the upper bound of the range of cp rule user segment rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp rule user segment rels
	*/
	public java.util.List<CPRuleUserSegmentRel> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPRuleUserSegmentRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp rule user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp rule user segment rel
	* @throws NoSuchCPRuleUserSegmentRelException if a matching cp rule user segment rel could not be found
	*/
	public CPRuleUserSegmentRel findByCommerceUserSegmentEntryId_First(
		long commerceUserSegmentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CPRuleUserSegmentRel> orderByComparator)
		throws NoSuchCPRuleUserSegmentRelException;

	/**
	* Returns the first cp rule user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp rule user segment rel, or <code>null</code> if a matching cp rule user segment rel could not be found
	*/
	public CPRuleUserSegmentRel fetchByCommerceUserSegmentEntryId_First(
		long commerceUserSegmentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CPRuleUserSegmentRel> orderByComparator);

	/**
	* Returns the last cp rule user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp rule user segment rel
	* @throws NoSuchCPRuleUserSegmentRelException if a matching cp rule user segment rel could not be found
	*/
	public CPRuleUserSegmentRel findByCommerceUserSegmentEntryId_Last(
		long commerceUserSegmentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CPRuleUserSegmentRel> orderByComparator)
		throws NoSuchCPRuleUserSegmentRelException;

	/**
	* Returns the last cp rule user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp rule user segment rel, or <code>null</code> if a matching cp rule user segment rel could not be found
	*/
	public CPRuleUserSegmentRel fetchByCommerceUserSegmentEntryId_Last(
		long commerceUserSegmentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CPRuleUserSegmentRel> orderByComparator);

	/**
	* Returns the cp rule user segment rels before and after the current cp rule user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	*
	* @param CPRuleUserSegmentRelId the primary key of the current cp rule user segment rel
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp rule user segment rel
	* @throws NoSuchCPRuleUserSegmentRelException if a cp rule user segment rel with the primary key could not be found
	*/
	public CPRuleUserSegmentRel[] findByCommerceUserSegmentEntryId_PrevAndNext(
		long CPRuleUserSegmentRelId, long commerceUserSegmentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CPRuleUserSegmentRel> orderByComparator)
		throws NoSuchCPRuleUserSegmentRelException;

	/**
	* Removes all the cp rule user segment rels where commerceUserSegmentEntryId = &#63; from the database.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	*/
	public void removeByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId);

	/**
	* Returns the number of cp rule user segment rels where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @return the number of matching cp rule user segment rels
	*/
	public int countByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId);

	/**
	* Caches the cp rule user segment rel in the entity cache if it is enabled.
	*
	* @param cpRuleUserSegmentRel the cp rule user segment rel
	*/
	public void cacheResult(CPRuleUserSegmentRel cpRuleUserSegmentRel);

	/**
	* Caches the cp rule user segment rels in the entity cache if it is enabled.
	*
	* @param cpRuleUserSegmentRels the cp rule user segment rels
	*/
	public void cacheResult(
		java.util.List<CPRuleUserSegmentRel> cpRuleUserSegmentRels);

	/**
	* Creates a new cp rule user segment rel with the primary key. Does not add the cp rule user segment rel to the database.
	*
	* @param CPRuleUserSegmentRelId the primary key for the new cp rule user segment rel
	* @return the new cp rule user segment rel
	*/
	public CPRuleUserSegmentRel create(long CPRuleUserSegmentRelId);

	/**
	* Removes the cp rule user segment rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPRuleUserSegmentRelId the primary key of the cp rule user segment rel
	* @return the cp rule user segment rel that was removed
	* @throws NoSuchCPRuleUserSegmentRelException if a cp rule user segment rel with the primary key could not be found
	*/
	public CPRuleUserSegmentRel remove(long CPRuleUserSegmentRelId)
		throws NoSuchCPRuleUserSegmentRelException;

	public CPRuleUserSegmentRel updateImpl(
		CPRuleUserSegmentRel cpRuleUserSegmentRel);

	/**
	* Returns the cp rule user segment rel with the primary key or throws a {@link NoSuchCPRuleUserSegmentRelException} if it could not be found.
	*
	* @param CPRuleUserSegmentRelId the primary key of the cp rule user segment rel
	* @return the cp rule user segment rel
	* @throws NoSuchCPRuleUserSegmentRelException if a cp rule user segment rel with the primary key could not be found
	*/
	public CPRuleUserSegmentRel findByPrimaryKey(long CPRuleUserSegmentRelId)
		throws NoSuchCPRuleUserSegmentRelException;

	/**
	* Returns the cp rule user segment rel with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPRuleUserSegmentRelId the primary key of the cp rule user segment rel
	* @return the cp rule user segment rel, or <code>null</code> if a cp rule user segment rel with the primary key could not be found
	*/
	public CPRuleUserSegmentRel fetchByPrimaryKey(long CPRuleUserSegmentRelId);

	@Override
	public java.util.Map<java.io.Serializable, CPRuleUserSegmentRel> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the cp rule user segment rels.
	*
	* @return the cp rule user segment rels
	*/
	public java.util.List<CPRuleUserSegmentRel> findAll();

	/**
	* Returns a range of all the cp rule user segment rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp rule user segment rels
	* @param end the upper bound of the range of cp rule user segment rels (not inclusive)
	* @return the range of cp rule user segment rels
	*/
	public java.util.List<CPRuleUserSegmentRel> findAll(int start, int end);

	/**
	* Returns an ordered range of all the cp rule user segment rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp rule user segment rels
	* @param end the upper bound of the range of cp rule user segment rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cp rule user segment rels
	*/
	public java.util.List<CPRuleUserSegmentRel> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPRuleUserSegmentRel> orderByComparator);

	/**
	* Returns an ordered range of all the cp rule user segment rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp rule user segment rels
	* @param end the upper bound of the range of cp rule user segment rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cp rule user segment rels
	*/
	public java.util.List<CPRuleUserSegmentRel> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPRuleUserSegmentRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the cp rule user segment rels from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of cp rule user segment rels.
	*
	* @return the number of cp rule user segment rels
	*/
	public int countAll();
}
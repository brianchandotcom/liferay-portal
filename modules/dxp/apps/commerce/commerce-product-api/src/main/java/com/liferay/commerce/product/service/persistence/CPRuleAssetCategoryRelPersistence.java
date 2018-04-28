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

import com.liferay.commerce.product.exception.NoSuchCPRuleAssetCategoryRelException;
import com.liferay.commerce.product.model.CPRuleAssetCategoryRel;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the cp rule asset category rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.persistence.impl.CPRuleAssetCategoryRelPersistenceImpl
 * @see CPRuleAssetCategoryRelUtil
 * @generated
 */
@ProviderType
public interface CPRuleAssetCategoryRelPersistence extends BasePersistence<CPRuleAssetCategoryRel> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPRuleAssetCategoryRelUtil} to access the cp rule asset category rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the cp rule asset category rels where CPRuleId = &#63;.
	*
	* @param CPRuleId the cp rule ID
	* @return the matching cp rule asset category rels
	*/
	public java.util.List<CPRuleAssetCategoryRel> findByCPRuleId(long CPRuleId);

	/**
	* Returns a range of all the cp rule asset category rels where CPRuleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPRuleId the cp rule ID
	* @param start the lower bound of the range of cp rule asset category rels
	* @param end the upper bound of the range of cp rule asset category rels (not inclusive)
	* @return the range of matching cp rule asset category rels
	*/
	public java.util.List<CPRuleAssetCategoryRel> findByCPRuleId(
		long CPRuleId, int start, int end);

	/**
	* Returns an ordered range of all the cp rule asset category rels where CPRuleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPRuleId the cp rule ID
	* @param start the lower bound of the range of cp rule asset category rels
	* @param end the upper bound of the range of cp rule asset category rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp rule asset category rels
	*/
	public java.util.List<CPRuleAssetCategoryRel> findByCPRuleId(
		long CPRuleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPRuleAssetCategoryRel> orderByComparator);

	/**
	* Returns an ordered range of all the cp rule asset category rels where CPRuleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPRuleId the cp rule ID
	* @param start the lower bound of the range of cp rule asset category rels
	* @param end the upper bound of the range of cp rule asset category rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp rule asset category rels
	*/
	public java.util.List<CPRuleAssetCategoryRel> findByCPRuleId(
		long CPRuleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPRuleAssetCategoryRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp rule asset category rel in the ordered set where CPRuleId = &#63;.
	*
	* @param CPRuleId the cp rule ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp rule asset category rel
	* @throws NoSuchCPRuleAssetCategoryRelException if a matching cp rule asset category rel could not be found
	*/
	public CPRuleAssetCategoryRel findByCPRuleId_First(long CPRuleId,
		com.liferay.portal.kernel.util.OrderByComparator<CPRuleAssetCategoryRel> orderByComparator)
		throws NoSuchCPRuleAssetCategoryRelException;

	/**
	* Returns the first cp rule asset category rel in the ordered set where CPRuleId = &#63;.
	*
	* @param CPRuleId the cp rule ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp rule asset category rel, or <code>null</code> if a matching cp rule asset category rel could not be found
	*/
	public CPRuleAssetCategoryRel fetchByCPRuleId_First(long CPRuleId,
		com.liferay.portal.kernel.util.OrderByComparator<CPRuleAssetCategoryRel> orderByComparator);

	/**
	* Returns the last cp rule asset category rel in the ordered set where CPRuleId = &#63;.
	*
	* @param CPRuleId the cp rule ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp rule asset category rel
	* @throws NoSuchCPRuleAssetCategoryRelException if a matching cp rule asset category rel could not be found
	*/
	public CPRuleAssetCategoryRel findByCPRuleId_Last(long CPRuleId,
		com.liferay.portal.kernel.util.OrderByComparator<CPRuleAssetCategoryRel> orderByComparator)
		throws NoSuchCPRuleAssetCategoryRelException;

	/**
	* Returns the last cp rule asset category rel in the ordered set where CPRuleId = &#63;.
	*
	* @param CPRuleId the cp rule ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp rule asset category rel, or <code>null</code> if a matching cp rule asset category rel could not be found
	*/
	public CPRuleAssetCategoryRel fetchByCPRuleId_Last(long CPRuleId,
		com.liferay.portal.kernel.util.OrderByComparator<CPRuleAssetCategoryRel> orderByComparator);

	/**
	* Returns the cp rule asset category rels before and after the current cp rule asset category rel in the ordered set where CPRuleId = &#63;.
	*
	* @param CPRuleAssetCategoryRelId the primary key of the current cp rule asset category rel
	* @param CPRuleId the cp rule ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp rule asset category rel
	* @throws NoSuchCPRuleAssetCategoryRelException if a cp rule asset category rel with the primary key could not be found
	*/
	public CPRuleAssetCategoryRel[] findByCPRuleId_PrevAndNext(
		long CPRuleAssetCategoryRelId, long CPRuleId,
		com.liferay.portal.kernel.util.OrderByComparator<CPRuleAssetCategoryRel> orderByComparator)
		throws NoSuchCPRuleAssetCategoryRelException;

	/**
	* Removes all the cp rule asset category rels where CPRuleId = &#63; from the database.
	*
	* @param CPRuleId the cp rule ID
	*/
	public void removeByCPRuleId(long CPRuleId);

	/**
	* Returns the number of cp rule asset category rels where CPRuleId = &#63;.
	*
	* @param CPRuleId the cp rule ID
	* @return the number of matching cp rule asset category rels
	*/
	public int countByCPRuleId(long CPRuleId);

	/**
	* Returns all the cp rule asset category rels where assetCategoryId = &#63;.
	*
	* @param assetCategoryId the asset category ID
	* @return the matching cp rule asset category rels
	*/
	public java.util.List<CPRuleAssetCategoryRel> findByAssetCategoryId(
		long assetCategoryId);

	/**
	* Returns a range of all the cp rule asset category rels where assetCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param assetCategoryId the asset category ID
	* @param start the lower bound of the range of cp rule asset category rels
	* @param end the upper bound of the range of cp rule asset category rels (not inclusive)
	* @return the range of matching cp rule asset category rels
	*/
	public java.util.List<CPRuleAssetCategoryRel> findByAssetCategoryId(
		long assetCategoryId, int start, int end);

	/**
	* Returns an ordered range of all the cp rule asset category rels where assetCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param assetCategoryId the asset category ID
	* @param start the lower bound of the range of cp rule asset category rels
	* @param end the upper bound of the range of cp rule asset category rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp rule asset category rels
	*/
	public java.util.List<CPRuleAssetCategoryRel> findByAssetCategoryId(
		long assetCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPRuleAssetCategoryRel> orderByComparator);

	/**
	* Returns an ordered range of all the cp rule asset category rels where assetCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param assetCategoryId the asset category ID
	* @param start the lower bound of the range of cp rule asset category rels
	* @param end the upper bound of the range of cp rule asset category rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp rule asset category rels
	*/
	public java.util.List<CPRuleAssetCategoryRel> findByAssetCategoryId(
		long assetCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPRuleAssetCategoryRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp rule asset category rel in the ordered set where assetCategoryId = &#63;.
	*
	* @param assetCategoryId the asset category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp rule asset category rel
	* @throws NoSuchCPRuleAssetCategoryRelException if a matching cp rule asset category rel could not be found
	*/
	public CPRuleAssetCategoryRel findByAssetCategoryId_First(
		long assetCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator<CPRuleAssetCategoryRel> orderByComparator)
		throws NoSuchCPRuleAssetCategoryRelException;

	/**
	* Returns the first cp rule asset category rel in the ordered set where assetCategoryId = &#63;.
	*
	* @param assetCategoryId the asset category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp rule asset category rel, or <code>null</code> if a matching cp rule asset category rel could not be found
	*/
	public CPRuleAssetCategoryRel fetchByAssetCategoryId_First(
		long assetCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator<CPRuleAssetCategoryRel> orderByComparator);

	/**
	* Returns the last cp rule asset category rel in the ordered set where assetCategoryId = &#63;.
	*
	* @param assetCategoryId the asset category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp rule asset category rel
	* @throws NoSuchCPRuleAssetCategoryRelException if a matching cp rule asset category rel could not be found
	*/
	public CPRuleAssetCategoryRel findByAssetCategoryId_Last(
		long assetCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator<CPRuleAssetCategoryRel> orderByComparator)
		throws NoSuchCPRuleAssetCategoryRelException;

	/**
	* Returns the last cp rule asset category rel in the ordered set where assetCategoryId = &#63;.
	*
	* @param assetCategoryId the asset category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp rule asset category rel, or <code>null</code> if a matching cp rule asset category rel could not be found
	*/
	public CPRuleAssetCategoryRel fetchByAssetCategoryId_Last(
		long assetCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator<CPRuleAssetCategoryRel> orderByComparator);

	/**
	* Returns the cp rule asset category rels before and after the current cp rule asset category rel in the ordered set where assetCategoryId = &#63;.
	*
	* @param CPRuleAssetCategoryRelId the primary key of the current cp rule asset category rel
	* @param assetCategoryId the asset category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp rule asset category rel
	* @throws NoSuchCPRuleAssetCategoryRelException if a cp rule asset category rel with the primary key could not be found
	*/
	public CPRuleAssetCategoryRel[] findByAssetCategoryId_PrevAndNext(
		long CPRuleAssetCategoryRelId, long assetCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator<CPRuleAssetCategoryRel> orderByComparator)
		throws NoSuchCPRuleAssetCategoryRelException;

	/**
	* Removes all the cp rule asset category rels where assetCategoryId = &#63; from the database.
	*
	* @param assetCategoryId the asset category ID
	*/
	public void removeByAssetCategoryId(long assetCategoryId);

	/**
	* Returns the number of cp rule asset category rels where assetCategoryId = &#63;.
	*
	* @param assetCategoryId the asset category ID
	* @return the number of matching cp rule asset category rels
	*/
	public int countByAssetCategoryId(long assetCategoryId);

	/**
	* Caches the cp rule asset category rel in the entity cache if it is enabled.
	*
	* @param cpRuleAssetCategoryRel the cp rule asset category rel
	*/
	public void cacheResult(CPRuleAssetCategoryRel cpRuleAssetCategoryRel);

	/**
	* Caches the cp rule asset category rels in the entity cache if it is enabled.
	*
	* @param cpRuleAssetCategoryRels the cp rule asset category rels
	*/
	public void cacheResult(
		java.util.List<CPRuleAssetCategoryRel> cpRuleAssetCategoryRels);

	/**
	* Creates a new cp rule asset category rel with the primary key. Does not add the cp rule asset category rel to the database.
	*
	* @param CPRuleAssetCategoryRelId the primary key for the new cp rule asset category rel
	* @return the new cp rule asset category rel
	*/
	public CPRuleAssetCategoryRel create(long CPRuleAssetCategoryRelId);

	/**
	* Removes the cp rule asset category rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPRuleAssetCategoryRelId the primary key of the cp rule asset category rel
	* @return the cp rule asset category rel that was removed
	* @throws NoSuchCPRuleAssetCategoryRelException if a cp rule asset category rel with the primary key could not be found
	*/
	public CPRuleAssetCategoryRel remove(long CPRuleAssetCategoryRelId)
		throws NoSuchCPRuleAssetCategoryRelException;

	public CPRuleAssetCategoryRel updateImpl(
		CPRuleAssetCategoryRel cpRuleAssetCategoryRel);

	/**
	* Returns the cp rule asset category rel with the primary key or throws a {@link NoSuchCPRuleAssetCategoryRelException} if it could not be found.
	*
	* @param CPRuleAssetCategoryRelId the primary key of the cp rule asset category rel
	* @return the cp rule asset category rel
	* @throws NoSuchCPRuleAssetCategoryRelException if a cp rule asset category rel with the primary key could not be found
	*/
	public CPRuleAssetCategoryRel findByPrimaryKey(
		long CPRuleAssetCategoryRelId)
		throws NoSuchCPRuleAssetCategoryRelException;

	/**
	* Returns the cp rule asset category rel with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPRuleAssetCategoryRelId the primary key of the cp rule asset category rel
	* @return the cp rule asset category rel, or <code>null</code> if a cp rule asset category rel with the primary key could not be found
	*/
	public CPRuleAssetCategoryRel fetchByPrimaryKey(
		long CPRuleAssetCategoryRelId);

	@Override
	public java.util.Map<java.io.Serializable, CPRuleAssetCategoryRel> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the cp rule asset category rels.
	*
	* @return the cp rule asset category rels
	*/
	public java.util.List<CPRuleAssetCategoryRel> findAll();

	/**
	* Returns a range of all the cp rule asset category rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp rule asset category rels
	* @param end the upper bound of the range of cp rule asset category rels (not inclusive)
	* @return the range of cp rule asset category rels
	*/
	public java.util.List<CPRuleAssetCategoryRel> findAll(int start, int end);

	/**
	* Returns an ordered range of all the cp rule asset category rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp rule asset category rels
	* @param end the upper bound of the range of cp rule asset category rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cp rule asset category rels
	*/
	public java.util.List<CPRuleAssetCategoryRel> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPRuleAssetCategoryRel> orderByComparator);

	/**
	* Returns an ordered range of all the cp rule asset category rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp rule asset category rels
	* @param end the upper bound of the range of cp rule asset category rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cp rule asset category rels
	*/
	public java.util.List<CPRuleAssetCategoryRel> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPRuleAssetCategoryRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the cp rule asset category rels from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of cp rule asset category rels.
	*
	* @return the number of cp rule asset category rels
	*/
	public int countAll();
}
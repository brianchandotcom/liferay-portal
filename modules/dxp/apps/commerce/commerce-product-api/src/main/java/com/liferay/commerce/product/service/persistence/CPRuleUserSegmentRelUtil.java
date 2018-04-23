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

import com.liferay.commerce.product.model.CPRuleUserSegmentRel;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the cp rule user segment rel service. This utility wraps {@link com.liferay.commerce.product.service.persistence.impl.CPRuleUserSegmentRelPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPRuleUserSegmentRelPersistence
 * @see com.liferay.commerce.product.service.persistence.impl.CPRuleUserSegmentRelPersistenceImpl
 * @generated
 */
@ProviderType
public class CPRuleUserSegmentRelUtil {
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
	public static void clearCache(CPRuleUserSegmentRel cpRuleUserSegmentRel) {
		getPersistence().clearCache(cpRuleUserSegmentRel);
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
	public static List<CPRuleUserSegmentRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CPRuleUserSegmentRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CPRuleUserSegmentRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CPRuleUserSegmentRel> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CPRuleUserSegmentRel update(
		CPRuleUserSegmentRel cpRuleUserSegmentRel) {
		return getPersistence().update(cpRuleUserSegmentRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CPRuleUserSegmentRel update(
		CPRuleUserSegmentRel cpRuleUserSegmentRel, ServiceContext serviceContext) {
		return getPersistence().update(cpRuleUserSegmentRel, serviceContext);
	}

	/**
	* Returns all the cp rule user segment rels where CPRuleId = &#63;.
	*
	* @param CPRuleId the cp rule ID
	* @return the matching cp rule user segment rels
	*/
	public static List<CPRuleUserSegmentRel> findByCPRuleId(long CPRuleId) {
		return getPersistence().findByCPRuleId(CPRuleId);
	}

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
	public static List<CPRuleUserSegmentRel> findByCPRuleId(long CPRuleId,
		int start, int end) {
		return getPersistence().findByCPRuleId(CPRuleId, start, end);
	}

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
	public static List<CPRuleUserSegmentRel> findByCPRuleId(long CPRuleId,
		int start, int end,
		OrderByComparator<CPRuleUserSegmentRel> orderByComparator) {
		return getPersistence()
				   .findByCPRuleId(CPRuleId, start, end, orderByComparator);
	}

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
	public static List<CPRuleUserSegmentRel> findByCPRuleId(long CPRuleId,
		int start, int end,
		OrderByComparator<CPRuleUserSegmentRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCPRuleId(CPRuleId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cp rule user segment rel in the ordered set where CPRuleId = &#63;.
	*
	* @param CPRuleId the cp rule ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp rule user segment rel
	* @throws NoSuchCPRuleUserSegmentRelException if a matching cp rule user segment rel could not be found
	*/
	public static CPRuleUserSegmentRel findByCPRuleId_First(long CPRuleId,
		OrderByComparator<CPRuleUserSegmentRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPRuleUserSegmentRelException {
		return getPersistence().findByCPRuleId_First(CPRuleId, orderByComparator);
	}

	/**
	* Returns the first cp rule user segment rel in the ordered set where CPRuleId = &#63;.
	*
	* @param CPRuleId the cp rule ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp rule user segment rel, or <code>null</code> if a matching cp rule user segment rel could not be found
	*/
	public static CPRuleUserSegmentRel fetchByCPRuleId_First(long CPRuleId,
		OrderByComparator<CPRuleUserSegmentRel> orderByComparator) {
		return getPersistence()
				   .fetchByCPRuleId_First(CPRuleId, orderByComparator);
	}

	/**
	* Returns the last cp rule user segment rel in the ordered set where CPRuleId = &#63;.
	*
	* @param CPRuleId the cp rule ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp rule user segment rel
	* @throws NoSuchCPRuleUserSegmentRelException if a matching cp rule user segment rel could not be found
	*/
	public static CPRuleUserSegmentRel findByCPRuleId_Last(long CPRuleId,
		OrderByComparator<CPRuleUserSegmentRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPRuleUserSegmentRelException {
		return getPersistence().findByCPRuleId_Last(CPRuleId, orderByComparator);
	}

	/**
	* Returns the last cp rule user segment rel in the ordered set where CPRuleId = &#63;.
	*
	* @param CPRuleId the cp rule ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp rule user segment rel, or <code>null</code> if a matching cp rule user segment rel could not be found
	*/
	public static CPRuleUserSegmentRel fetchByCPRuleId_Last(long CPRuleId,
		OrderByComparator<CPRuleUserSegmentRel> orderByComparator) {
		return getPersistence().fetchByCPRuleId_Last(CPRuleId, orderByComparator);
	}

	/**
	* Returns the cp rule user segment rels before and after the current cp rule user segment rel in the ordered set where CPRuleId = &#63;.
	*
	* @param CPRuleUserSegmentRelId the primary key of the current cp rule user segment rel
	* @param CPRuleId the cp rule ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp rule user segment rel
	* @throws NoSuchCPRuleUserSegmentRelException if a cp rule user segment rel with the primary key could not be found
	*/
	public static CPRuleUserSegmentRel[] findByCPRuleId_PrevAndNext(
		long CPRuleUserSegmentRelId, long CPRuleId,
		OrderByComparator<CPRuleUserSegmentRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPRuleUserSegmentRelException {
		return getPersistence()
				   .findByCPRuleId_PrevAndNext(CPRuleUserSegmentRelId,
			CPRuleId, orderByComparator);
	}

	/**
	* Removes all the cp rule user segment rels where CPRuleId = &#63; from the database.
	*
	* @param CPRuleId the cp rule ID
	*/
	public static void removeByCPRuleId(long CPRuleId) {
		getPersistence().removeByCPRuleId(CPRuleId);
	}

	/**
	* Returns the number of cp rule user segment rels where CPRuleId = &#63;.
	*
	* @param CPRuleId the cp rule ID
	* @return the number of matching cp rule user segment rels
	*/
	public static int countByCPRuleId(long CPRuleId) {
		return getPersistence().countByCPRuleId(CPRuleId);
	}

	/**
	* Returns all the cp rule user segment rels where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @return the matching cp rule user segment rels
	*/
	public static List<CPRuleUserSegmentRel> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId) {
		return getPersistence()
				   .findByCommerceUserSegmentEntryId(commerceUserSegmentEntryId);
	}

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
	public static List<CPRuleUserSegmentRel> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId, int start, int end) {
		return getPersistence()
				   .findByCommerceUserSegmentEntryId(commerceUserSegmentEntryId,
			start, end);
	}

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
	public static List<CPRuleUserSegmentRel> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId, int start, int end,
		OrderByComparator<CPRuleUserSegmentRel> orderByComparator) {
		return getPersistence()
				   .findByCommerceUserSegmentEntryId(commerceUserSegmentEntryId,
			start, end, orderByComparator);
	}

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
	public static List<CPRuleUserSegmentRel> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId, int start, int end,
		OrderByComparator<CPRuleUserSegmentRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCommerceUserSegmentEntryId(commerceUserSegmentEntryId,
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cp rule user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp rule user segment rel
	* @throws NoSuchCPRuleUserSegmentRelException if a matching cp rule user segment rel could not be found
	*/
	public static CPRuleUserSegmentRel findByCommerceUserSegmentEntryId_First(
		long commerceUserSegmentEntryId,
		OrderByComparator<CPRuleUserSegmentRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPRuleUserSegmentRelException {
		return getPersistence()
				   .findByCommerceUserSegmentEntryId_First(commerceUserSegmentEntryId,
			orderByComparator);
	}

	/**
	* Returns the first cp rule user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp rule user segment rel, or <code>null</code> if a matching cp rule user segment rel could not be found
	*/
	public static CPRuleUserSegmentRel fetchByCommerceUserSegmentEntryId_First(
		long commerceUserSegmentEntryId,
		OrderByComparator<CPRuleUserSegmentRel> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceUserSegmentEntryId_First(commerceUserSegmentEntryId,
			orderByComparator);
	}

	/**
	* Returns the last cp rule user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp rule user segment rel
	* @throws NoSuchCPRuleUserSegmentRelException if a matching cp rule user segment rel could not be found
	*/
	public static CPRuleUserSegmentRel findByCommerceUserSegmentEntryId_Last(
		long commerceUserSegmentEntryId,
		OrderByComparator<CPRuleUserSegmentRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPRuleUserSegmentRelException {
		return getPersistence()
				   .findByCommerceUserSegmentEntryId_Last(commerceUserSegmentEntryId,
			orderByComparator);
	}

	/**
	* Returns the last cp rule user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp rule user segment rel, or <code>null</code> if a matching cp rule user segment rel could not be found
	*/
	public static CPRuleUserSegmentRel fetchByCommerceUserSegmentEntryId_Last(
		long commerceUserSegmentEntryId,
		OrderByComparator<CPRuleUserSegmentRel> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceUserSegmentEntryId_Last(commerceUserSegmentEntryId,
			orderByComparator);
	}

	/**
	* Returns the cp rule user segment rels before and after the current cp rule user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	*
	* @param CPRuleUserSegmentRelId the primary key of the current cp rule user segment rel
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp rule user segment rel
	* @throws NoSuchCPRuleUserSegmentRelException if a cp rule user segment rel with the primary key could not be found
	*/
	public static CPRuleUserSegmentRel[] findByCommerceUserSegmentEntryId_PrevAndNext(
		long CPRuleUserSegmentRelId, long commerceUserSegmentEntryId,
		OrderByComparator<CPRuleUserSegmentRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPRuleUserSegmentRelException {
		return getPersistence()
				   .findByCommerceUserSegmentEntryId_PrevAndNext(CPRuleUserSegmentRelId,
			commerceUserSegmentEntryId, orderByComparator);
	}

	/**
	* Removes all the cp rule user segment rels where commerceUserSegmentEntryId = &#63; from the database.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	*/
	public static void removeByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId) {
		getPersistence()
			.removeByCommerceUserSegmentEntryId(commerceUserSegmentEntryId);
	}

	/**
	* Returns the number of cp rule user segment rels where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @return the number of matching cp rule user segment rels
	*/
	public static int countByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId) {
		return getPersistence()
				   .countByCommerceUserSegmentEntryId(commerceUserSegmentEntryId);
	}

	/**
	* Caches the cp rule user segment rel in the entity cache if it is enabled.
	*
	* @param cpRuleUserSegmentRel the cp rule user segment rel
	*/
	public static void cacheResult(CPRuleUserSegmentRel cpRuleUserSegmentRel) {
		getPersistence().cacheResult(cpRuleUserSegmentRel);
	}

	/**
	* Caches the cp rule user segment rels in the entity cache if it is enabled.
	*
	* @param cpRuleUserSegmentRels the cp rule user segment rels
	*/
	public static void cacheResult(
		List<CPRuleUserSegmentRel> cpRuleUserSegmentRels) {
		getPersistence().cacheResult(cpRuleUserSegmentRels);
	}

	/**
	* Creates a new cp rule user segment rel with the primary key. Does not add the cp rule user segment rel to the database.
	*
	* @param CPRuleUserSegmentRelId the primary key for the new cp rule user segment rel
	* @return the new cp rule user segment rel
	*/
	public static CPRuleUserSegmentRel create(long CPRuleUserSegmentRelId) {
		return getPersistence().create(CPRuleUserSegmentRelId);
	}

	/**
	* Removes the cp rule user segment rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPRuleUserSegmentRelId the primary key of the cp rule user segment rel
	* @return the cp rule user segment rel that was removed
	* @throws NoSuchCPRuleUserSegmentRelException if a cp rule user segment rel with the primary key could not be found
	*/
	public static CPRuleUserSegmentRel remove(long CPRuleUserSegmentRelId)
		throws com.liferay.commerce.product.exception.NoSuchCPRuleUserSegmentRelException {
		return getPersistence().remove(CPRuleUserSegmentRelId);
	}

	public static CPRuleUserSegmentRel updateImpl(
		CPRuleUserSegmentRel cpRuleUserSegmentRel) {
		return getPersistence().updateImpl(cpRuleUserSegmentRel);
	}

	/**
	* Returns the cp rule user segment rel with the primary key or throws a {@link NoSuchCPRuleUserSegmentRelException} if it could not be found.
	*
	* @param CPRuleUserSegmentRelId the primary key of the cp rule user segment rel
	* @return the cp rule user segment rel
	* @throws NoSuchCPRuleUserSegmentRelException if a cp rule user segment rel with the primary key could not be found
	*/
	public static CPRuleUserSegmentRel findByPrimaryKey(
		long CPRuleUserSegmentRelId)
		throws com.liferay.commerce.product.exception.NoSuchCPRuleUserSegmentRelException {
		return getPersistence().findByPrimaryKey(CPRuleUserSegmentRelId);
	}

	/**
	* Returns the cp rule user segment rel with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPRuleUserSegmentRelId the primary key of the cp rule user segment rel
	* @return the cp rule user segment rel, or <code>null</code> if a cp rule user segment rel with the primary key could not be found
	*/
	public static CPRuleUserSegmentRel fetchByPrimaryKey(
		long CPRuleUserSegmentRelId) {
		return getPersistence().fetchByPrimaryKey(CPRuleUserSegmentRelId);
	}

	public static java.util.Map<java.io.Serializable, CPRuleUserSegmentRel> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the cp rule user segment rels.
	*
	* @return the cp rule user segment rels
	*/
	public static List<CPRuleUserSegmentRel> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<CPRuleUserSegmentRel> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<CPRuleUserSegmentRel> findAll(int start, int end,
		OrderByComparator<CPRuleUserSegmentRel> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<CPRuleUserSegmentRel> findAll(int start, int end,
		OrderByComparator<CPRuleUserSegmentRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the cp rule user segment rels from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of cp rule user segment rels.
	*
	* @return the number of cp rule user segment rels
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CPRuleUserSegmentRelPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPRuleUserSegmentRelPersistence, CPRuleUserSegmentRelPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPRuleUserSegmentRelPersistence.class);

		ServiceTracker<CPRuleUserSegmentRelPersistence, CPRuleUserSegmentRelPersistence> serviceTracker =
			new ServiceTracker<CPRuleUserSegmentRelPersistence, CPRuleUserSegmentRelPersistence>(bundle.getBundleContext(),
				CPRuleUserSegmentRelPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}
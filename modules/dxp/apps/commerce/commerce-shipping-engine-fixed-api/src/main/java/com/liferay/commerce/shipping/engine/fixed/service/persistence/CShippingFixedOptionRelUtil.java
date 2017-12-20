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

import com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the c shipping fixed option rel service. This utility wraps {@link com.liferay.commerce.shipping.engine.fixed.service.persistence.impl.CShippingFixedOptionRelPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CShippingFixedOptionRelPersistence
 * @see com.liferay.commerce.shipping.engine.fixed.service.persistence.impl.CShippingFixedOptionRelPersistenceImpl
 * @generated
 */
@ProviderType
public class CShippingFixedOptionRelUtil {
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
	public static void clearCache(
		CShippingFixedOptionRel cShippingFixedOptionRel) {
		getPersistence().clearCache(cShippingFixedOptionRel);
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
	public static List<CShippingFixedOptionRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CShippingFixedOptionRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CShippingFixedOptionRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CShippingFixedOptionRel> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CShippingFixedOptionRel update(
		CShippingFixedOptionRel cShippingFixedOptionRel) {
		return getPersistence().update(cShippingFixedOptionRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CShippingFixedOptionRel update(
		CShippingFixedOptionRel cShippingFixedOptionRel,
		ServiceContext serviceContext) {
		return getPersistence().update(cShippingFixedOptionRel, serviceContext);
	}

	/**
	* Returns all the c shipping fixed option rels where commerceShippingMethodId = &#63;.
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	* @return the matching c shipping fixed option rels
	*/
	public static List<CShippingFixedOptionRel> findByCommerceShippingMethodId(
		long commerceShippingMethodId) {
		return getPersistence()
				   .findByCommerceShippingMethodId(commerceShippingMethodId);
	}

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
	public static List<CShippingFixedOptionRel> findByCommerceShippingMethodId(
		long commerceShippingMethodId, int start, int end) {
		return getPersistence()
				   .findByCommerceShippingMethodId(commerceShippingMethodId,
			start, end);
	}

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
	public static List<CShippingFixedOptionRel> findByCommerceShippingMethodId(
		long commerceShippingMethodId, int start, int end,
		OrderByComparator<CShippingFixedOptionRel> orderByComparator) {
		return getPersistence()
				   .findByCommerceShippingMethodId(commerceShippingMethodId,
			start, end, orderByComparator);
	}

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
	public static List<CShippingFixedOptionRel> findByCommerceShippingMethodId(
		long commerceShippingMethodId, int start, int end,
		OrderByComparator<CShippingFixedOptionRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCommerceShippingMethodId(commerceShippingMethodId,
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first c shipping fixed option rel in the ordered set where commerceShippingMethodId = &#63;.
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c shipping fixed option rel
	* @throws NoSuchCShippingFixedOptionRelException if a matching c shipping fixed option rel could not be found
	*/
	public static CShippingFixedOptionRel findByCommerceShippingMethodId_First(
		long commerceShippingMethodId,
		OrderByComparator<CShippingFixedOptionRel> orderByComparator)
		throws com.liferay.commerce.shipping.engine.fixed.exception.NoSuchCShippingFixedOptionRelException {
		return getPersistence()
				   .findByCommerceShippingMethodId_First(commerceShippingMethodId,
			orderByComparator);
	}

	/**
	* Returns the first c shipping fixed option rel in the ordered set where commerceShippingMethodId = &#63;.
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c shipping fixed option rel, or <code>null</code> if a matching c shipping fixed option rel could not be found
	*/
	public static CShippingFixedOptionRel fetchByCommerceShippingMethodId_First(
		long commerceShippingMethodId,
		OrderByComparator<CShippingFixedOptionRel> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceShippingMethodId_First(commerceShippingMethodId,
			orderByComparator);
	}

	/**
	* Returns the last c shipping fixed option rel in the ordered set where commerceShippingMethodId = &#63;.
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c shipping fixed option rel
	* @throws NoSuchCShippingFixedOptionRelException if a matching c shipping fixed option rel could not be found
	*/
	public static CShippingFixedOptionRel findByCommerceShippingMethodId_Last(
		long commerceShippingMethodId,
		OrderByComparator<CShippingFixedOptionRel> orderByComparator)
		throws com.liferay.commerce.shipping.engine.fixed.exception.NoSuchCShippingFixedOptionRelException {
		return getPersistence()
				   .findByCommerceShippingMethodId_Last(commerceShippingMethodId,
			orderByComparator);
	}

	/**
	* Returns the last c shipping fixed option rel in the ordered set where commerceShippingMethodId = &#63;.
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c shipping fixed option rel, or <code>null</code> if a matching c shipping fixed option rel could not be found
	*/
	public static CShippingFixedOptionRel fetchByCommerceShippingMethodId_Last(
		long commerceShippingMethodId,
		OrderByComparator<CShippingFixedOptionRel> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceShippingMethodId_Last(commerceShippingMethodId,
			orderByComparator);
	}

	/**
	* Returns the c shipping fixed option rels before and after the current c shipping fixed option rel in the ordered set where commerceShippingMethodId = &#63;.
	*
	* @param CShippingFixedOptionRelId the primary key of the current c shipping fixed option rel
	* @param commerceShippingMethodId the commerce shipping method ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next c shipping fixed option rel
	* @throws NoSuchCShippingFixedOptionRelException if a c shipping fixed option rel with the primary key could not be found
	*/
	public static CShippingFixedOptionRel[] findByCommerceShippingMethodId_PrevAndNext(
		long CShippingFixedOptionRelId, long commerceShippingMethodId,
		OrderByComparator<CShippingFixedOptionRel> orderByComparator)
		throws com.liferay.commerce.shipping.engine.fixed.exception.NoSuchCShippingFixedOptionRelException {
		return getPersistence()
				   .findByCommerceShippingMethodId_PrevAndNext(CShippingFixedOptionRelId,
			commerceShippingMethodId, orderByComparator);
	}

	/**
	* Removes all the c shipping fixed option rels where commerceShippingMethodId = &#63; from the database.
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	*/
	public static void removeByCommerceShippingMethodId(
		long commerceShippingMethodId) {
		getPersistence()
			.removeByCommerceShippingMethodId(commerceShippingMethodId);
	}

	/**
	* Returns the number of c shipping fixed option rels where commerceShippingMethodId = &#63;.
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	* @return the number of matching c shipping fixed option rels
	*/
	public static int countByCommerceShippingMethodId(
		long commerceShippingMethodId) {
		return getPersistence()
				   .countByCommerceShippingMethodId(commerceShippingMethodId);
	}

	/**
	* Returns all the c shipping fixed option rels where commerceShippingFixedOptionId = &#63;.
	*
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	* @return the matching c shipping fixed option rels
	*/
	public static List<CShippingFixedOptionRel> findByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId) {
		return getPersistence()
				   .findByCommerceShippingFixedOptionId(commerceShippingFixedOptionId);
	}

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
	public static List<CShippingFixedOptionRel> findByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId, int start, int end) {
		return getPersistence()
				   .findByCommerceShippingFixedOptionId(commerceShippingFixedOptionId,
			start, end);
	}

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
	public static List<CShippingFixedOptionRel> findByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId, int start, int end,
		OrderByComparator<CShippingFixedOptionRel> orderByComparator) {
		return getPersistence()
				   .findByCommerceShippingFixedOptionId(commerceShippingFixedOptionId,
			start, end, orderByComparator);
	}

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
	public static List<CShippingFixedOptionRel> findByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId, int start, int end,
		OrderByComparator<CShippingFixedOptionRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCommerceShippingFixedOptionId(commerceShippingFixedOptionId,
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first c shipping fixed option rel in the ordered set where commerceShippingFixedOptionId = &#63;.
	*
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c shipping fixed option rel
	* @throws NoSuchCShippingFixedOptionRelException if a matching c shipping fixed option rel could not be found
	*/
	public static CShippingFixedOptionRel findByCommerceShippingFixedOptionId_First(
		long commerceShippingFixedOptionId,
		OrderByComparator<CShippingFixedOptionRel> orderByComparator)
		throws com.liferay.commerce.shipping.engine.fixed.exception.NoSuchCShippingFixedOptionRelException {
		return getPersistence()
				   .findByCommerceShippingFixedOptionId_First(commerceShippingFixedOptionId,
			orderByComparator);
	}

	/**
	* Returns the first c shipping fixed option rel in the ordered set where commerceShippingFixedOptionId = &#63;.
	*
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c shipping fixed option rel, or <code>null</code> if a matching c shipping fixed option rel could not be found
	*/
	public static CShippingFixedOptionRel fetchByCommerceShippingFixedOptionId_First(
		long commerceShippingFixedOptionId,
		OrderByComparator<CShippingFixedOptionRel> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceShippingFixedOptionId_First(commerceShippingFixedOptionId,
			orderByComparator);
	}

	/**
	* Returns the last c shipping fixed option rel in the ordered set where commerceShippingFixedOptionId = &#63;.
	*
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c shipping fixed option rel
	* @throws NoSuchCShippingFixedOptionRelException if a matching c shipping fixed option rel could not be found
	*/
	public static CShippingFixedOptionRel findByCommerceShippingFixedOptionId_Last(
		long commerceShippingFixedOptionId,
		OrderByComparator<CShippingFixedOptionRel> orderByComparator)
		throws com.liferay.commerce.shipping.engine.fixed.exception.NoSuchCShippingFixedOptionRelException {
		return getPersistence()
				   .findByCommerceShippingFixedOptionId_Last(commerceShippingFixedOptionId,
			orderByComparator);
	}

	/**
	* Returns the last c shipping fixed option rel in the ordered set where commerceShippingFixedOptionId = &#63;.
	*
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c shipping fixed option rel, or <code>null</code> if a matching c shipping fixed option rel could not be found
	*/
	public static CShippingFixedOptionRel fetchByCommerceShippingFixedOptionId_Last(
		long commerceShippingFixedOptionId,
		OrderByComparator<CShippingFixedOptionRel> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceShippingFixedOptionId_Last(commerceShippingFixedOptionId,
			orderByComparator);
	}

	/**
	* Returns the c shipping fixed option rels before and after the current c shipping fixed option rel in the ordered set where commerceShippingFixedOptionId = &#63;.
	*
	* @param CShippingFixedOptionRelId the primary key of the current c shipping fixed option rel
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next c shipping fixed option rel
	* @throws NoSuchCShippingFixedOptionRelException if a c shipping fixed option rel with the primary key could not be found
	*/
	public static CShippingFixedOptionRel[] findByCommerceShippingFixedOptionId_PrevAndNext(
		long CShippingFixedOptionRelId, long commerceShippingFixedOptionId,
		OrderByComparator<CShippingFixedOptionRel> orderByComparator)
		throws com.liferay.commerce.shipping.engine.fixed.exception.NoSuchCShippingFixedOptionRelException {
		return getPersistence()
				   .findByCommerceShippingFixedOptionId_PrevAndNext(CShippingFixedOptionRelId,
			commerceShippingFixedOptionId, orderByComparator);
	}

	/**
	* Removes all the c shipping fixed option rels where commerceShippingFixedOptionId = &#63; from the database.
	*
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	*/
	public static void removeByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId) {
		getPersistence()
			.removeByCommerceShippingFixedOptionId(commerceShippingFixedOptionId);
	}

	/**
	* Returns the number of c shipping fixed option rels where commerceShippingFixedOptionId = &#63;.
	*
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	* @return the number of matching c shipping fixed option rels
	*/
	public static int countByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId) {
		return getPersistence()
				   .countByCommerceShippingFixedOptionId(commerceShippingFixedOptionId);
	}

	/**
	* Caches the c shipping fixed option rel in the entity cache if it is enabled.
	*
	* @param cShippingFixedOptionRel the c shipping fixed option rel
	*/
	public static void cacheResult(
		CShippingFixedOptionRel cShippingFixedOptionRel) {
		getPersistence().cacheResult(cShippingFixedOptionRel);
	}

	/**
	* Caches the c shipping fixed option rels in the entity cache if it is enabled.
	*
	* @param cShippingFixedOptionRels the c shipping fixed option rels
	*/
	public static void cacheResult(
		List<CShippingFixedOptionRel> cShippingFixedOptionRels) {
		getPersistence().cacheResult(cShippingFixedOptionRels);
	}

	/**
	* Creates a new c shipping fixed option rel with the primary key. Does not add the c shipping fixed option rel to the database.
	*
	* @param CShippingFixedOptionRelId the primary key for the new c shipping fixed option rel
	* @return the new c shipping fixed option rel
	*/
	public static CShippingFixedOptionRel create(long CShippingFixedOptionRelId) {
		return getPersistence().create(CShippingFixedOptionRelId);
	}

	/**
	* Removes the c shipping fixed option rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CShippingFixedOptionRelId the primary key of the c shipping fixed option rel
	* @return the c shipping fixed option rel that was removed
	* @throws NoSuchCShippingFixedOptionRelException if a c shipping fixed option rel with the primary key could not be found
	*/
	public static CShippingFixedOptionRel remove(long CShippingFixedOptionRelId)
		throws com.liferay.commerce.shipping.engine.fixed.exception.NoSuchCShippingFixedOptionRelException {
		return getPersistence().remove(CShippingFixedOptionRelId);
	}

	public static CShippingFixedOptionRel updateImpl(
		CShippingFixedOptionRel cShippingFixedOptionRel) {
		return getPersistence().updateImpl(cShippingFixedOptionRel);
	}

	/**
	* Returns the c shipping fixed option rel with the primary key or throws a {@link NoSuchCShippingFixedOptionRelException} if it could not be found.
	*
	* @param CShippingFixedOptionRelId the primary key of the c shipping fixed option rel
	* @return the c shipping fixed option rel
	* @throws NoSuchCShippingFixedOptionRelException if a c shipping fixed option rel with the primary key could not be found
	*/
	public static CShippingFixedOptionRel findByPrimaryKey(
		long CShippingFixedOptionRelId)
		throws com.liferay.commerce.shipping.engine.fixed.exception.NoSuchCShippingFixedOptionRelException {
		return getPersistence().findByPrimaryKey(CShippingFixedOptionRelId);
	}

	/**
	* Returns the c shipping fixed option rel with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CShippingFixedOptionRelId the primary key of the c shipping fixed option rel
	* @return the c shipping fixed option rel, or <code>null</code> if a c shipping fixed option rel with the primary key could not be found
	*/
	public static CShippingFixedOptionRel fetchByPrimaryKey(
		long CShippingFixedOptionRelId) {
		return getPersistence().fetchByPrimaryKey(CShippingFixedOptionRelId);
	}

	public static java.util.Map<java.io.Serializable, CShippingFixedOptionRel> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the c shipping fixed option rels.
	*
	* @return the c shipping fixed option rels
	*/
	public static List<CShippingFixedOptionRel> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<CShippingFixedOptionRel> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<CShippingFixedOptionRel> findAll(int start, int end,
		OrderByComparator<CShippingFixedOptionRel> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<CShippingFixedOptionRel> findAll(int start, int end,
		OrderByComparator<CShippingFixedOptionRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the c shipping fixed option rels from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of c shipping fixed option rels.
	*
	* @return the number of c shipping fixed option rels
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CShippingFixedOptionRelPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CShippingFixedOptionRelPersistence, CShippingFixedOptionRelPersistence> _serviceTracker =
		ServiceTrackerFactory.open(CShippingFixedOptionRelPersistence.class);
}
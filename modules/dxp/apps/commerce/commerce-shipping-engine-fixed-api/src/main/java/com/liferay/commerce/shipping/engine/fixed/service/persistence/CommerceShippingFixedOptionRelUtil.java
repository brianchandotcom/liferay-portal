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

import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce shipping fixed option rel service. This utility wraps {@link com.liferay.commerce.shipping.engine.fixed.service.persistence.impl.CommerceShippingFixedOptionRelPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingFixedOptionRelPersistence
 * @see com.liferay.commerce.shipping.engine.fixed.service.persistence.impl.CommerceShippingFixedOptionRelPersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceShippingFixedOptionRelUtil {
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
		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel) {
		getPersistence().clearCache(commerceShippingFixedOptionRel);
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
	public static List<CommerceShippingFixedOptionRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceShippingFixedOptionRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceShippingFixedOptionRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceShippingFixedOptionRel update(
		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel) {
		return getPersistence().update(commerceShippingFixedOptionRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceShippingFixedOptionRel update(
		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel,
		ServiceContext serviceContext) {
		return getPersistence()
				   .update(commerceShippingFixedOptionRel, serviceContext);
	}

	/**
	* Returns all the commerce shipping fixed option rels where commerceShippingMethodId = &#63;.
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	* @return the matching commerce shipping fixed option rels
	*/
	public static List<CommerceShippingFixedOptionRel> findByCommerceShippingMethodId(
		long commerceShippingMethodId) {
		return getPersistence()
				   .findByCommerceShippingMethodId(commerceShippingMethodId);
	}

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
	public static List<CommerceShippingFixedOptionRel> findByCommerceShippingMethodId(
		long commerceShippingMethodId, int start, int end) {
		return getPersistence()
				   .findByCommerceShippingMethodId(commerceShippingMethodId,
			start, end);
	}

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
	public static List<CommerceShippingFixedOptionRel> findByCommerceShippingMethodId(
		long commerceShippingMethodId, int start, int end,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator) {
		return getPersistence()
				   .findByCommerceShippingMethodId(commerceShippingMethodId,
			start, end, orderByComparator);
	}

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
	public static List<CommerceShippingFixedOptionRel> findByCommerceShippingMethodId(
		long commerceShippingMethodId, int start, int end,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCommerceShippingMethodId(commerceShippingMethodId,
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce shipping fixed option rel in the ordered set where commerceShippingMethodId = &#63;.
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce shipping fixed option rel
	* @throws NoSuchShippingFixedOptionRelException if a matching commerce shipping fixed option rel could not be found
	*/
	public static CommerceShippingFixedOptionRel findByCommerceShippingMethodId_First(
		long commerceShippingMethodId,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator)
		throws com.liferay.commerce.shipping.engine.fixed.exception.NoSuchShippingFixedOptionRelException {
		return getPersistence()
				   .findByCommerceShippingMethodId_First(commerceShippingMethodId,
			orderByComparator);
	}

	/**
	* Returns the first commerce shipping fixed option rel in the ordered set where commerceShippingMethodId = &#63;.
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce shipping fixed option rel, or <code>null</code> if a matching commerce shipping fixed option rel could not be found
	*/
	public static CommerceShippingFixedOptionRel fetchByCommerceShippingMethodId_First(
		long commerceShippingMethodId,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceShippingMethodId_First(commerceShippingMethodId,
			orderByComparator);
	}

	/**
	* Returns the last commerce shipping fixed option rel in the ordered set where commerceShippingMethodId = &#63;.
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce shipping fixed option rel
	* @throws NoSuchShippingFixedOptionRelException if a matching commerce shipping fixed option rel could not be found
	*/
	public static CommerceShippingFixedOptionRel findByCommerceShippingMethodId_Last(
		long commerceShippingMethodId,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator)
		throws com.liferay.commerce.shipping.engine.fixed.exception.NoSuchShippingFixedOptionRelException {
		return getPersistence()
				   .findByCommerceShippingMethodId_Last(commerceShippingMethodId,
			orderByComparator);
	}

	/**
	* Returns the last commerce shipping fixed option rel in the ordered set where commerceShippingMethodId = &#63;.
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce shipping fixed option rel, or <code>null</code> if a matching commerce shipping fixed option rel could not be found
	*/
	public static CommerceShippingFixedOptionRel fetchByCommerceShippingMethodId_Last(
		long commerceShippingMethodId,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceShippingMethodId_Last(commerceShippingMethodId,
			orderByComparator);
	}

	/**
	* Returns the commerce shipping fixed option rels before and after the current commerce shipping fixed option rel in the ordered set where commerceShippingMethodId = &#63;.
	*
	* @param commerceShippingFixedOptionRelId the primary key of the current commerce shipping fixed option rel
	* @param commerceShippingMethodId the commerce shipping method ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce shipping fixed option rel
	* @throws NoSuchShippingFixedOptionRelException if a commerce shipping fixed option rel with the primary key could not be found
	*/
	public static CommerceShippingFixedOptionRel[] findByCommerceShippingMethodId_PrevAndNext(
		long commerceShippingFixedOptionRelId, long commerceShippingMethodId,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator)
		throws com.liferay.commerce.shipping.engine.fixed.exception.NoSuchShippingFixedOptionRelException {
		return getPersistence()
				   .findByCommerceShippingMethodId_PrevAndNext(commerceShippingFixedOptionRelId,
			commerceShippingMethodId, orderByComparator);
	}

	/**
	* Removes all the commerce shipping fixed option rels where commerceShippingMethodId = &#63; from the database.
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	*/
	public static void removeByCommerceShippingMethodId(
		long commerceShippingMethodId) {
		getPersistence()
			.removeByCommerceShippingMethodId(commerceShippingMethodId);
	}

	/**
	* Returns the number of commerce shipping fixed option rels where commerceShippingMethodId = &#63;.
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	* @return the number of matching commerce shipping fixed option rels
	*/
	public static int countByCommerceShippingMethodId(
		long commerceShippingMethodId) {
		return getPersistence()
				   .countByCommerceShippingMethodId(commerceShippingMethodId);
	}

	/**
	* Returns all the commerce shipping fixed option rels where commerceShippingFixedOptionId = &#63;.
	*
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	* @return the matching commerce shipping fixed option rels
	*/
	public static List<CommerceShippingFixedOptionRel> findByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId) {
		return getPersistence()
				   .findByCommerceShippingFixedOptionId(commerceShippingFixedOptionId);
	}

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
	public static List<CommerceShippingFixedOptionRel> findByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId, int start, int end) {
		return getPersistence()
				   .findByCommerceShippingFixedOptionId(commerceShippingFixedOptionId,
			start, end);
	}

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
	public static List<CommerceShippingFixedOptionRel> findByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId, int start, int end,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator) {
		return getPersistence()
				   .findByCommerceShippingFixedOptionId(commerceShippingFixedOptionId,
			start, end, orderByComparator);
	}

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
	public static List<CommerceShippingFixedOptionRel> findByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId, int start, int end,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCommerceShippingFixedOptionId(commerceShippingFixedOptionId,
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce shipping fixed option rel in the ordered set where commerceShippingFixedOptionId = &#63;.
	*
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce shipping fixed option rel
	* @throws NoSuchShippingFixedOptionRelException if a matching commerce shipping fixed option rel could not be found
	*/
	public static CommerceShippingFixedOptionRel findByCommerceShippingFixedOptionId_First(
		long commerceShippingFixedOptionId,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator)
		throws com.liferay.commerce.shipping.engine.fixed.exception.NoSuchShippingFixedOptionRelException {
		return getPersistence()
				   .findByCommerceShippingFixedOptionId_First(commerceShippingFixedOptionId,
			orderByComparator);
	}

	/**
	* Returns the first commerce shipping fixed option rel in the ordered set where commerceShippingFixedOptionId = &#63;.
	*
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce shipping fixed option rel, or <code>null</code> if a matching commerce shipping fixed option rel could not be found
	*/
	public static CommerceShippingFixedOptionRel fetchByCommerceShippingFixedOptionId_First(
		long commerceShippingFixedOptionId,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceShippingFixedOptionId_First(commerceShippingFixedOptionId,
			orderByComparator);
	}

	/**
	* Returns the last commerce shipping fixed option rel in the ordered set where commerceShippingFixedOptionId = &#63;.
	*
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce shipping fixed option rel
	* @throws NoSuchShippingFixedOptionRelException if a matching commerce shipping fixed option rel could not be found
	*/
	public static CommerceShippingFixedOptionRel findByCommerceShippingFixedOptionId_Last(
		long commerceShippingFixedOptionId,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator)
		throws com.liferay.commerce.shipping.engine.fixed.exception.NoSuchShippingFixedOptionRelException {
		return getPersistence()
				   .findByCommerceShippingFixedOptionId_Last(commerceShippingFixedOptionId,
			orderByComparator);
	}

	/**
	* Returns the last commerce shipping fixed option rel in the ordered set where commerceShippingFixedOptionId = &#63;.
	*
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce shipping fixed option rel, or <code>null</code> if a matching commerce shipping fixed option rel could not be found
	*/
	public static CommerceShippingFixedOptionRel fetchByCommerceShippingFixedOptionId_Last(
		long commerceShippingFixedOptionId,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceShippingFixedOptionId_Last(commerceShippingFixedOptionId,
			orderByComparator);
	}

	/**
	* Returns the commerce shipping fixed option rels before and after the current commerce shipping fixed option rel in the ordered set where commerceShippingFixedOptionId = &#63;.
	*
	* @param commerceShippingFixedOptionRelId the primary key of the current commerce shipping fixed option rel
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce shipping fixed option rel
	* @throws NoSuchShippingFixedOptionRelException if a commerce shipping fixed option rel with the primary key could not be found
	*/
	public static CommerceShippingFixedOptionRel[] findByCommerceShippingFixedOptionId_PrevAndNext(
		long commerceShippingFixedOptionRelId,
		long commerceShippingFixedOptionId,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator)
		throws com.liferay.commerce.shipping.engine.fixed.exception.NoSuchShippingFixedOptionRelException {
		return getPersistence()
				   .findByCommerceShippingFixedOptionId_PrevAndNext(commerceShippingFixedOptionRelId,
			commerceShippingFixedOptionId, orderByComparator);
	}

	/**
	* Removes all the commerce shipping fixed option rels where commerceShippingFixedOptionId = &#63; from the database.
	*
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	*/
	public static void removeByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId) {
		getPersistence()
			.removeByCommerceShippingFixedOptionId(commerceShippingFixedOptionId);
	}

	/**
	* Returns the number of commerce shipping fixed option rels where commerceShippingFixedOptionId = &#63;.
	*
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	* @return the number of matching commerce shipping fixed option rels
	*/
	public static int countByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId) {
		return getPersistence()
				   .countByCommerceShippingFixedOptionId(commerceShippingFixedOptionId);
	}

	/**
	* Caches the commerce shipping fixed option rel in the entity cache if it is enabled.
	*
	* @param commerceShippingFixedOptionRel the commerce shipping fixed option rel
	*/
	public static void cacheResult(
		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel) {
		getPersistence().cacheResult(commerceShippingFixedOptionRel);
	}

	/**
	* Caches the commerce shipping fixed option rels in the entity cache if it is enabled.
	*
	* @param commerceShippingFixedOptionRels the commerce shipping fixed option rels
	*/
	public static void cacheResult(
		List<CommerceShippingFixedOptionRel> commerceShippingFixedOptionRels) {
		getPersistence().cacheResult(commerceShippingFixedOptionRels);
	}

	/**
	* Creates a new commerce shipping fixed option rel with the primary key. Does not add the commerce shipping fixed option rel to the database.
	*
	* @param commerceShippingFixedOptionRelId the primary key for the new commerce shipping fixed option rel
	* @return the new commerce shipping fixed option rel
	*/
	public static CommerceShippingFixedOptionRel create(
		long commerceShippingFixedOptionRelId) {
		return getPersistence().create(commerceShippingFixedOptionRelId);
	}

	/**
	* Removes the commerce shipping fixed option rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceShippingFixedOptionRelId the primary key of the commerce shipping fixed option rel
	* @return the commerce shipping fixed option rel that was removed
	* @throws NoSuchShippingFixedOptionRelException if a commerce shipping fixed option rel with the primary key could not be found
	*/
	public static CommerceShippingFixedOptionRel remove(
		long commerceShippingFixedOptionRelId)
		throws com.liferay.commerce.shipping.engine.fixed.exception.NoSuchShippingFixedOptionRelException {
		return getPersistence().remove(commerceShippingFixedOptionRelId);
	}

	public static CommerceShippingFixedOptionRel updateImpl(
		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel) {
		return getPersistence().updateImpl(commerceShippingFixedOptionRel);
	}

	/**
	* Returns the commerce shipping fixed option rel with the primary key or throws a {@link NoSuchShippingFixedOptionRelException} if it could not be found.
	*
	* @param commerceShippingFixedOptionRelId the primary key of the commerce shipping fixed option rel
	* @return the commerce shipping fixed option rel
	* @throws NoSuchShippingFixedOptionRelException if a commerce shipping fixed option rel with the primary key could not be found
	*/
	public static CommerceShippingFixedOptionRel findByPrimaryKey(
		long commerceShippingFixedOptionRelId)
		throws com.liferay.commerce.shipping.engine.fixed.exception.NoSuchShippingFixedOptionRelException {
		return getPersistence()
				   .findByPrimaryKey(commerceShippingFixedOptionRelId);
	}

	/**
	* Returns the commerce shipping fixed option rel with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceShippingFixedOptionRelId the primary key of the commerce shipping fixed option rel
	* @return the commerce shipping fixed option rel, or <code>null</code> if a commerce shipping fixed option rel with the primary key could not be found
	*/
	public static CommerceShippingFixedOptionRel fetchByPrimaryKey(
		long commerceShippingFixedOptionRelId) {
		return getPersistence()
				   .fetchByPrimaryKey(commerceShippingFixedOptionRelId);
	}

	public static java.util.Map<java.io.Serializable, CommerceShippingFixedOptionRel> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce shipping fixed option rels.
	*
	* @return the commerce shipping fixed option rels
	*/
	public static List<CommerceShippingFixedOptionRel> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<CommerceShippingFixedOptionRel> findAll(int start,
		int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<CommerceShippingFixedOptionRel> findAll(int start,
		int end,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<CommerceShippingFixedOptionRel> findAll(int start,
		int end,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce shipping fixed option rels from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce shipping fixed option rels.
	*
	* @return the number of commerce shipping fixed option rels
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CommerceShippingFixedOptionRelPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceShippingFixedOptionRelPersistence, CommerceShippingFixedOptionRelPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceShippingFixedOptionRelPersistence.class);

		ServiceTracker<CommerceShippingFixedOptionRelPersistence, CommerceShippingFixedOptionRelPersistence> serviceTracker =
			new ServiceTracker<CommerceShippingFixedOptionRelPersistence, CommerceShippingFixedOptionRelPersistence>(bundle.getBundleContext(),
				CommerceShippingFixedOptionRelPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}
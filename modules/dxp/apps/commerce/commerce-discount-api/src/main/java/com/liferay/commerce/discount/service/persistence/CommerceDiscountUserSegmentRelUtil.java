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

import com.liferay.commerce.discount.model.CommerceDiscountUserSegmentRel;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce discount user segment rel service. This utility wraps {@link com.liferay.commerce.discount.service.persistence.impl.CommerceDiscountUserSegmentRelPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceDiscountUserSegmentRelPersistence
 * @see com.liferay.commerce.discount.service.persistence.impl.CommerceDiscountUserSegmentRelPersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceDiscountUserSegmentRelUtil {
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
		CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel) {
		getPersistence().clearCache(commerceDiscountUserSegmentRel);
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
	public static List<CommerceDiscountUserSegmentRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceDiscountUserSegmentRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceDiscountUserSegmentRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceDiscountUserSegmentRel update(
		CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel) {
		return getPersistence().update(commerceDiscountUserSegmentRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceDiscountUserSegmentRel update(
		CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel,
		ServiceContext serviceContext) {
		return getPersistence()
				   .update(commerceDiscountUserSegmentRel, serviceContext);
	}

	/**
	* Returns all the commerce discount user segment rels where commerceDiscountId = &#63;.
	*
	* @param commerceDiscountId the commerce discount ID
	* @return the matching commerce discount user segment rels
	*/
	public static List<CommerceDiscountUserSegmentRel> findBycommerceDiscountId(
		long commerceDiscountId) {
		return getPersistence().findBycommerceDiscountId(commerceDiscountId);
	}

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
	public static List<CommerceDiscountUserSegmentRel> findBycommerceDiscountId(
		long commerceDiscountId, int start, int end) {
		return getPersistence()
				   .findBycommerceDiscountId(commerceDiscountId, start, end);
	}

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
	public static List<CommerceDiscountUserSegmentRel> findBycommerceDiscountId(
		long commerceDiscountId, int start, int end,
		OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator) {
		return getPersistence()
				   .findBycommerceDiscountId(commerceDiscountId, start, end,
			orderByComparator);
	}

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
	public static List<CommerceDiscountUserSegmentRel> findBycommerceDiscountId(
		long commerceDiscountId, int start, int end,
		OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findBycommerceDiscountId(commerceDiscountId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce discount user segment rel in the ordered set where commerceDiscountId = &#63;.
	*
	* @param commerceDiscountId the commerce discount ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce discount user segment rel
	* @throws NoSuchDiscountUserSegmentRelException if a matching commerce discount user segment rel could not be found
	*/
	public static CommerceDiscountUserSegmentRel findBycommerceDiscountId_First(
		long commerceDiscountId,
		OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator)
		throws com.liferay.commerce.discount.exception.NoSuchDiscountUserSegmentRelException {
		return getPersistence()
				   .findBycommerceDiscountId_First(commerceDiscountId,
			orderByComparator);
	}

	/**
	* Returns the first commerce discount user segment rel in the ordered set where commerceDiscountId = &#63;.
	*
	* @param commerceDiscountId the commerce discount ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce discount user segment rel, or <code>null</code> if a matching commerce discount user segment rel could not be found
	*/
	public static CommerceDiscountUserSegmentRel fetchBycommerceDiscountId_First(
		long commerceDiscountId,
		OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator) {
		return getPersistence()
				   .fetchBycommerceDiscountId_First(commerceDiscountId,
			orderByComparator);
	}

	/**
	* Returns the last commerce discount user segment rel in the ordered set where commerceDiscountId = &#63;.
	*
	* @param commerceDiscountId the commerce discount ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce discount user segment rel
	* @throws NoSuchDiscountUserSegmentRelException if a matching commerce discount user segment rel could not be found
	*/
	public static CommerceDiscountUserSegmentRel findBycommerceDiscountId_Last(
		long commerceDiscountId,
		OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator)
		throws com.liferay.commerce.discount.exception.NoSuchDiscountUserSegmentRelException {
		return getPersistence()
				   .findBycommerceDiscountId_Last(commerceDiscountId,
			orderByComparator);
	}

	/**
	* Returns the last commerce discount user segment rel in the ordered set where commerceDiscountId = &#63;.
	*
	* @param commerceDiscountId the commerce discount ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce discount user segment rel, or <code>null</code> if a matching commerce discount user segment rel could not be found
	*/
	public static CommerceDiscountUserSegmentRel fetchBycommerceDiscountId_Last(
		long commerceDiscountId,
		OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator) {
		return getPersistence()
				   .fetchBycommerceDiscountId_Last(commerceDiscountId,
			orderByComparator);
	}

	/**
	* Returns the commerce discount user segment rels before and after the current commerce discount user segment rel in the ordered set where commerceDiscountId = &#63;.
	*
	* @param commerceDiscountUserSegmentRelId the primary key of the current commerce discount user segment rel
	* @param commerceDiscountId the commerce discount ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce discount user segment rel
	* @throws NoSuchDiscountUserSegmentRelException if a commerce discount user segment rel with the primary key could not be found
	*/
	public static CommerceDiscountUserSegmentRel[] findBycommerceDiscountId_PrevAndNext(
		long commerceDiscountUserSegmentRelId, long commerceDiscountId,
		OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator)
		throws com.liferay.commerce.discount.exception.NoSuchDiscountUserSegmentRelException {
		return getPersistence()
				   .findBycommerceDiscountId_PrevAndNext(commerceDiscountUserSegmentRelId,
			commerceDiscountId, orderByComparator);
	}

	/**
	* Removes all the commerce discount user segment rels where commerceDiscountId = &#63; from the database.
	*
	* @param commerceDiscountId the commerce discount ID
	*/
	public static void removeBycommerceDiscountId(long commerceDiscountId) {
		getPersistence().removeBycommerceDiscountId(commerceDiscountId);
	}

	/**
	* Returns the number of commerce discount user segment rels where commerceDiscountId = &#63;.
	*
	* @param commerceDiscountId the commerce discount ID
	* @return the number of matching commerce discount user segment rels
	*/
	public static int countBycommerceDiscountId(long commerceDiscountId) {
		return getPersistence().countBycommerceDiscountId(commerceDiscountId);
	}

	/**
	* Returns all the commerce discount user segment rels where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @return the matching commerce discount user segment rels
	*/
	public static List<CommerceDiscountUserSegmentRel> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId) {
		return getPersistence()
				   .findByCommerceUserSegmentEntryId(commerceUserSegmentEntryId);
	}

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
	public static List<CommerceDiscountUserSegmentRel> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId, int start, int end) {
		return getPersistence()
				   .findByCommerceUserSegmentEntryId(commerceUserSegmentEntryId,
			start, end);
	}

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
	public static List<CommerceDiscountUserSegmentRel> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId, int start, int end,
		OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator) {
		return getPersistence()
				   .findByCommerceUserSegmentEntryId(commerceUserSegmentEntryId,
			start, end, orderByComparator);
	}

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
	public static List<CommerceDiscountUserSegmentRel> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId, int start, int end,
		OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCommerceUserSegmentEntryId(commerceUserSegmentEntryId,
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce discount user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce discount user segment rel
	* @throws NoSuchDiscountUserSegmentRelException if a matching commerce discount user segment rel could not be found
	*/
	public static CommerceDiscountUserSegmentRel findByCommerceUserSegmentEntryId_First(
		long commerceUserSegmentEntryId,
		OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator)
		throws com.liferay.commerce.discount.exception.NoSuchDiscountUserSegmentRelException {
		return getPersistence()
				   .findByCommerceUserSegmentEntryId_First(commerceUserSegmentEntryId,
			orderByComparator);
	}

	/**
	* Returns the first commerce discount user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce discount user segment rel, or <code>null</code> if a matching commerce discount user segment rel could not be found
	*/
	public static CommerceDiscountUserSegmentRel fetchByCommerceUserSegmentEntryId_First(
		long commerceUserSegmentEntryId,
		OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceUserSegmentEntryId_First(commerceUserSegmentEntryId,
			orderByComparator);
	}

	/**
	* Returns the last commerce discount user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce discount user segment rel
	* @throws NoSuchDiscountUserSegmentRelException if a matching commerce discount user segment rel could not be found
	*/
	public static CommerceDiscountUserSegmentRel findByCommerceUserSegmentEntryId_Last(
		long commerceUserSegmentEntryId,
		OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator)
		throws com.liferay.commerce.discount.exception.NoSuchDiscountUserSegmentRelException {
		return getPersistence()
				   .findByCommerceUserSegmentEntryId_Last(commerceUserSegmentEntryId,
			orderByComparator);
	}

	/**
	* Returns the last commerce discount user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce discount user segment rel, or <code>null</code> if a matching commerce discount user segment rel could not be found
	*/
	public static CommerceDiscountUserSegmentRel fetchByCommerceUserSegmentEntryId_Last(
		long commerceUserSegmentEntryId,
		OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceUserSegmentEntryId_Last(commerceUserSegmentEntryId,
			orderByComparator);
	}

	/**
	* Returns the commerce discount user segment rels before and after the current commerce discount user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceDiscountUserSegmentRelId the primary key of the current commerce discount user segment rel
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce discount user segment rel
	* @throws NoSuchDiscountUserSegmentRelException if a commerce discount user segment rel with the primary key could not be found
	*/
	public static CommerceDiscountUserSegmentRel[] findByCommerceUserSegmentEntryId_PrevAndNext(
		long commerceDiscountUserSegmentRelId, long commerceUserSegmentEntryId,
		OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator)
		throws com.liferay.commerce.discount.exception.NoSuchDiscountUserSegmentRelException {
		return getPersistence()
				   .findByCommerceUserSegmentEntryId_PrevAndNext(commerceDiscountUserSegmentRelId,
			commerceUserSegmentEntryId, orderByComparator);
	}

	/**
	* Removes all the commerce discount user segment rels where commerceUserSegmentEntryId = &#63; from the database.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	*/
	public static void removeByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId) {
		getPersistence()
			.removeByCommerceUserSegmentEntryId(commerceUserSegmentEntryId);
	}

	/**
	* Returns the number of commerce discount user segment rels where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @return the number of matching commerce discount user segment rels
	*/
	public static int countByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId) {
		return getPersistence()
				   .countByCommerceUserSegmentEntryId(commerceUserSegmentEntryId);
	}

	/**
	* Caches the commerce discount user segment rel in the entity cache if it is enabled.
	*
	* @param commerceDiscountUserSegmentRel the commerce discount user segment rel
	*/
	public static void cacheResult(
		CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel) {
		getPersistence().cacheResult(commerceDiscountUserSegmentRel);
	}

	/**
	* Caches the commerce discount user segment rels in the entity cache if it is enabled.
	*
	* @param commerceDiscountUserSegmentRels the commerce discount user segment rels
	*/
	public static void cacheResult(
		List<CommerceDiscountUserSegmentRel> commerceDiscountUserSegmentRels) {
		getPersistence().cacheResult(commerceDiscountUserSegmentRels);
	}

	/**
	* Creates a new commerce discount user segment rel with the primary key. Does not add the commerce discount user segment rel to the database.
	*
	* @param commerceDiscountUserSegmentRelId the primary key for the new commerce discount user segment rel
	* @return the new commerce discount user segment rel
	*/
	public static CommerceDiscountUserSegmentRel create(
		long commerceDiscountUserSegmentRelId) {
		return getPersistence().create(commerceDiscountUserSegmentRelId);
	}

	/**
	* Removes the commerce discount user segment rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceDiscountUserSegmentRelId the primary key of the commerce discount user segment rel
	* @return the commerce discount user segment rel that was removed
	* @throws NoSuchDiscountUserSegmentRelException if a commerce discount user segment rel with the primary key could not be found
	*/
	public static CommerceDiscountUserSegmentRel remove(
		long commerceDiscountUserSegmentRelId)
		throws com.liferay.commerce.discount.exception.NoSuchDiscountUserSegmentRelException {
		return getPersistence().remove(commerceDiscountUserSegmentRelId);
	}

	public static CommerceDiscountUserSegmentRel updateImpl(
		CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel) {
		return getPersistence().updateImpl(commerceDiscountUserSegmentRel);
	}

	/**
	* Returns the commerce discount user segment rel with the primary key or throws a {@link NoSuchDiscountUserSegmentRelException} if it could not be found.
	*
	* @param commerceDiscountUserSegmentRelId the primary key of the commerce discount user segment rel
	* @return the commerce discount user segment rel
	* @throws NoSuchDiscountUserSegmentRelException if a commerce discount user segment rel with the primary key could not be found
	*/
	public static CommerceDiscountUserSegmentRel findByPrimaryKey(
		long commerceDiscountUserSegmentRelId)
		throws com.liferay.commerce.discount.exception.NoSuchDiscountUserSegmentRelException {
		return getPersistence()
				   .findByPrimaryKey(commerceDiscountUserSegmentRelId);
	}

	/**
	* Returns the commerce discount user segment rel with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceDiscountUserSegmentRelId the primary key of the commerce discount user segment rel
	* @return the commerce discount user segment rel, or <code>null</code> if a commerce discount user segment rel with the primary key could not be found
	*/
	public static CommerceDiscountUserSegmentRel fetchByPrimaryKey(
		long commerceDiscountUserSegmentRelId) {
		return getPersistence()
				   .fetchByPrimaryKey(commerceDiscountUserSegmentRelId);
	}

	public static java.util.Map<java.io.Serializable, CommerceDiscountUserSegmentRel> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce discount user segment rels.
	*
	* @return the commerce discount user segment rels
	*/
	public static List<CommerceDiscountUserSegmentRel> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<CommerceDiscountUserSegmentRel> findAll(int start,
		int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<CommerceDiscountUserSegmentRel> findAll(int start,
		int end,
		OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<CommerceDiscountUserSegmentRel> findAll(int start,
		int end,
		OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce discount user segment rels from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce discount user segment rels.
	*
	* @return the number of commerce discount user segment rels
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CommerceDiscountUserSegmentRelPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceDiscountUserSegmentRelPersistence, CommerceDiscountUserSegmentRelPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceDiscountUserSegmentRelPersistence.class);

		ServiceTracker<CommerceDiscountUserSegmentRelPersistence, CommerceDiscountUserSegmentRelPersistence> serviceTracker =
			new ServiceTracker<CommerceDiscountUserSegmentRelPersistence, CommerceDiscountUserSegmentRelPersistence>(bundle.getBundleContext(),
				CommerceDiscountUserSegmentRelPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}
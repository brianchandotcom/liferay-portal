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

package com.liferay.commerce.service.persistence;

import com.liferay.commerce.model.CommerceOrderTypeRel;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the commerce order type rel service. This utility wraps <code>com.liferay.commerce.service.persistence.impl.CommerceOrderTypeRelPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderTypeRelPersistence
 * @generated
 */
public class CommerceOrderTypeRelUtil {

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
	public static void clearCache(CommerceOrderTypeRel commerceOrderTypeRel) {
		getPersistence().clearCache(commerceOrderTypeRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, CommerceOrderTypeRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceOrderTypeRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceOrderTypeRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceOrderTypeRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceOrderTypeRel> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceOrderTypeRel update(
		CommerceOrderTypeRel commerceOrderTypeRel) {

		return getPersistence().update(commerceOrderTypeRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceOrderTypeRel update(
		CommerceOrderTypeRel commerceOrderTypeRel,
		ServiceContext serviceContext) {

		return getPersistence().update(commerceOrderTypeRel, serviceContext);
	}

	/**
	 * Returns all the commerce order type rels where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the matching commerce order type rels
	 */
	public static List<CommerceOrderTypeRel> findByCommerceOrderTypeId(
		long commerceOrderTypeId) {

		return getPersistence().findByCommerceOrderTypeId(commerceOrderTypeId);
	}

	/**
	 * Returns a range of all the commerce order type rels where commerceOrderTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param start the lower bound of the range of commerce order type rels
	 * @param end the upper bound of the range of commerce order type rels (not inclusive)
	 * @return the range of matching commerce order type rels
	 */
	public static List<CommerceOrderTypeRel> findByCommerceOrderTypeId(
		long commerceOrderTypeId, int start, int end) {

		return getPersistence().findByCommerceOrderTypeId(
			commerceOrderTypeId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce order type rels where commerceOrderTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param start the lower bound of the range of commerce order type rels
	 * @param end the upper bound of the range of commerce order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order type rels
	 */
	public static List<CommerceOrderTypeRel> findByCommerceOrderTypeId(
		long commerceOrderTypeId, int start, int end,
		OrderByComparator<CommerceOrderTypeRel> orderByComparator) {

		return getPersistence().findByCommerceOrderTypeId(
			commerceOrderTypeId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce order type rels where commerceOrderTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param start the lower bound of the range of commerce order type rels
	 * @param end the upper bound of the range of commerce order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order type rels
	 */
	public static List<CommerceOrderTypeRel> findByCommerceOrderTypeId(
		long commerceOrderTypeId, int start, int end,
		OrderByComparator<CommerceOrderTypeRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCommerceOrderTypeId(
			commerceOrderTypeId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce order type rel in the ordered set where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order type rel
	 * @throws NoSuchOrderTypeRelException if a matching commerce order type rel could not be found
	 */
	public static CommerceOrderTypeRel findByCommerceOrderTypeId_First(
			long commerceOrderTypeId,
			OrderByComparator<CommerceOrderTypeRel> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderTypeRelException {

		return getPersistence().findByCommerceOrderTypeId_First(
			commerceOrderTypeId, orderByComparator);
	}

	/**
	 * Returns the first commerce order type rel in the ordered set where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order type rel, or <code>null</code> if a matching commerce order type rel could not be found
	 */
	public static CommerceOrderTypeRel fetchByCommerceOrderTypeId_First(
		long commerceOrderTypeId,
		OrderByComparator<CommerceOrderTypeRel> orderByComparator) {

		return getPersistence().fetchByCommerceOrderTypeId_First(
			commerceOrderTypeId, orderByComparator);
	}

	/**
	 * Returns the last commerce order type rel in the ordered set where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order type rel
	 * @throws NoSuchOrderTypeRelException if a matching commerce order type rel could not be found
	 */
	public static CommerceOrderTypeRel findByCommerceOrderTypeId_Last(
			long commerceOrderTypeId,
			OrderByComparator<CommerceOrderTypeRel> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderTypeRelException {

		return getPersistence().findByCommerceOrderTypeId_Last(
			commerceOrderTypeId, orderByComparator);
	}

	/**
	 * Returns the last commerce order type rel in the ordered set where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order type rel, or <code>null</code> if a matching commerce order type rel could not be found
	 */
	public static CommerceOrderTypeRel fetchByCommerceOrderTypeId_Last(
		long commerceOrderTypeId,
		OrderByComparator<CommerceOrderTypeRel> orderByComparator) {

		return getPersistence().fetchByCommerceOrderTypeId_Last(
			commerceOrderTypeId, orderByComparator);
	}

	/**
	 * Returns the commerce order type rels before and after the current commerce order type rel in the ordered set where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeRelId the primary key of the current commerce order type rel
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order type rel
	 * @throws NoSuchOrderTypeRelException if a commerce order type rel with the primary key could not be found
	 */
	public static CommerceOrderTypeRel[] findByCommerceOrderTypeId_PrevAndNext(
			long commerceOrderTypeRelId, long commerceOrderTypeId,
			OrderByComparator<CommerceOrderTypeRel> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderTypeRelException {

		return getPersistence().findByCommerceOrderTypeId_PrevAndNext(
			commerceOrderTypeRelId, commerceOrderTypeId, orderByComparator);
	}

	/**
	 * Removes all the commerce order type rels where commerceOrderTypeId = &#63; from the database.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 */
	public static void removeByCommerceOrderTypeId(long commerceOrderTypeId) {
		getPersistence().removeByCommerceOrderTypeId(commerceOrderTypeId);
	}

	/**
	 * Returns the number of commerce order type rels where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the number of matching commerce order type rels
	 */
	public static int countByCommerceOrderTypeId(long commerceOrderTypeId) {
		return getPersistence().countByCommerceOrderTypeId(commerceOrderTypeId);
	}

	/**
	 * Returns all the commerce order type rels where classNameId = &#63; and commerceOrderTypeId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the matching commerce order type rels
	 */
	public static List<CommerceOrderTypeRel> findByC_C(
		long classNameId, long commerceOrderTypeId) {

		return getPersistence().findByC_C(classNameId, commerceOrderTypeId);
	}

	/**
	 * Returns a range of all the commerce order type rels where classNameId = &#63; and commerceOrderTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param start the lower bound of the range of commerce order type rels
	 * @param end the upper bound of the range of commerce order type rels (not inclusive)
	 * @return the range of matching commerce order type rels
	 */
	public static List<CommerceOrderTypeRel> findByC_C(
		long classNameId, long commerceOrderTypeId, int start, int end) {

		return getPersistence().findByC_C(
			classNameId, commerceOrderTypeId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce order type rels where classNameId = &#63; and commerceOrderTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param start the lower bound of the range of commerce order type rels
	 * @param end the upper bound of the range of commerce order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order type rels
	 */
	public static List<CommerceOrderTypeRel> findByC_C(
		long classNameId, long commerceOrderTypeId, int start, int end,
		OrderByComparator<CommerceOrderTypeRel> orderByComparator) {

		return getPersistence().findByC_C(
			classNameId, commerceOrderTypeId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce order type rels where classNameId = &#63; and commerceOrderTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param start the lower bound of the range of commerce order type rels
	 * @param end the upper bound of the range of commerce order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order type rels
	 */
	public static List<CommerceOrderTypeRel> findByC_C(
		long classNameId, long commerceOrderTypeId, int start, int end,
		OrderByComparator<CommerceOrderTypeRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_C(
			classNameId, commerceOrderTypeId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce order type rel in the ordered set where classNameId = &#63; and commerceOrderTypeId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order type rel
	 * @throws NoSuchOrderTypeRelException if a matching commerce order type rel could not be found
	 */
	public static CommerceOrderTypeRel findByC_C_First(
			long classNameId, long commerceOrderTypeId,
			OrderByComparator<CommerceOrderTypeRel> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderTypeRelException {

		return getPersistence().findByC_C_First(
			classNameId, commerceOrderTypeId, orderByComparator);
	}

	/**
	 * Returns the first commerce order type rel in the ordered set where classNameId = &#63; and commerceOrderTypeId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order type rel, or <code>null</code> if a matching commerce order type rel could not be found
	 */
	public static CommerceOrderTypeRel fetchByC_C_First(
		long classNameId, long commerceOrderTypeId,
		OrderByComparator<CommerceOrderTypeRel> orderByComparator) {

		return getPersistence().fetchByC_C_First(
			classNameId, commerceOrderTypeId, orderByComparator);
	}

	/**
	 * Returns the last commerce order type rel in the ordered set where classNameId = &#63; and commerceOrderTypeId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order type rel
	 * @throws NoSuchOrderTypeRelException if a matching commerce order type rel could not be found
	 */
	public static CommerceOrderTypeRel findByC_C_Last(
			long classNameId, long commerceOrderTypeId,
			OrderByComparator<CommerceOrderTypeRel> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderTypeRelException {

		return getPersistence().findByC_C_Last(
			classNameId, commerceOrderTypeId, orderByComparator);
	}

	/**
	 * Returns the last commerce order type rel in the ordered set where classNameId = &#63; and commerceOrderTypeId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order type rel, or <code>null</code> if a matching commerce order type rel could not be found
	 */
	public static CommerceOrderTypeRel fetchByC_C_Last(
		long classNameId, long commerceOrderTypeId,
		OrderByComparator<CommerceOrderTypeRel> orderByComparator) {

		return getPersistence().fetchByC_C_Last(
			classNameId, commerceOrderTypeId, orderByComparator);
	}

	/**
	 * Returns the commerce order type rels before and after the current commerce order type rel in the ordered set where classNameId = &#63; and commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeRelId the primary key of the current commerce order type rel
	 * @param classNameId the class name ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order type rel
	 * @throws NoSuchOrderTypeRelException if a commerce order type rel with the primary key could not be found
	 */
	public static CommerceOrderTypeRel[] findByC_C_PrevAndNext(
			long commerceOrderTypeRelId, long classNameId,
			long commerceOrderTypeId,
			OrderByComparator<CommerceOrderTypeRel> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderTypeRelException {

		return getPersistence().findByC_C_PrevAndNext(
			commerceOrderTypeRelId, classNameId, commerceOrderTypeId,
			orderByComparator);
	}

	/**
	 * Removes all the commerce order type rels where classNameId = &#63; and commerceOrderTypeId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderTypeId the commerce order type ID
	 */
	public static void removeByC_C(long classNameId, long commerceOrderTypeId) {
		getPersistence().removeByC_C(classNameId, commerceOrderTypeId);
	}

	/**
	 * Returns the number of commerce order type rels where classNameId = &#63; and commerceOrderTypeId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the number of matching commerce order type rels
	 */
	public static int countByC_C(long classNameId, long commerceOrderTypeId) {
		return getPersistence().countByC_C(classNameId, commerceOrderTypeId);
	}

	/**
	 * Returns the commerce order type rel where classNameId = &#63; and classPK = &#63; and commerceOrderTypeId = &#63; or throws a <code>NoSuchOrderTypeRelException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the matching commerce order type rel
	 * @throws NoSuchOrderTypeRelException if a matching commerce order type rel could not be found
	 */
	public static CommerceOrderTypeRel findByC_C_C(
			long classNameId, long classPK, long commerceOrderTypeId)
		throws com.liferay.commerce.exception.NoSuchOrderTypeRelException {

		return getPersistence().findByC_C_C(
			classNameId, classPK, commerceOrderTypeId);
	}

	/**
	 * Returns the commerce order type rel where classNameId = &#63; and classPK = &#63; and commerceOrderTypeId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the matching commerce order type rel, or <code>null</code> if a matching commerce order type rel could not be found
	 */
	public static CommerceOrderTypeRel fetchByC_C_C(
		long classNameId, long classPK, long commerceOrderTypeId) {

		return getPersistence().fetchByC_C_C(
			classNameId, classPK, commerceOrderTypeId);
	}

	/**
	 * Returns the commerce order type rel where classNameId = &#63; and classPK = &#63; and commerceOrderTypeId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce order type rel, or <code>null</code> if a matching commerce order type rel could not be found
	 */
	public static CommerceOrderTypeRel fetchByC_C_C(
		long classNameId, long classPK, long commerceOrderTypeId,
		boolean useFinderCache) {

		return getPersistence().fetchByC_C_C(
			classNameId, classPK, commerceOrderTypeId, useFinderCache);
	}

	/**
	 * Removes the commerce order type rel where classNameId = &#63; and classPK = &#63; and commerceOrderTypeId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the commerce order type rel that was removed
	 */
	public static CommerceOrderTypeRel removeByC_C_C(
			long classNameId, long classPK, long commerceOrderTypeId)
		throws com.liferay.commerce.exception.NoSuchOrderTypeRelException {

		return getPersistence().removeByC_C_C(
			classNameId, classPK, commerceOrderTypeId);
	}

	/**
	 * Returns the number of commerce order type rels where classNameId = &#63; and classPK = &#63; and commerceOrderTypeId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the number of matching commerce order type rels
	 */
	public static int countByC_C_C(
		long classNameId, long classPK, long commerceOrderTypeId) {

		return getPersistence().countByC_C_C(
			classNameId, classPK, commerceOrderTypeId);
	}

	/**
	 * Returns the commerce order type rel where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchOrderTypeRelException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce order type rel
	 * @throws NoSuchOrderTypeRelException if a matching commerce order type rel could not be found
	 */
	public static CommerceOrderTypeRel findByC_ERC(
			long companyId, String externalReferenceCode)
		throws com.liferay.commerce.exception.NoSuchOrderTypeRelException {

		return getPersistence().findByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the commerce order type rel where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce order type rel, or <code>null</code> if a matching commerce order type rel could not be found
	 */
	public static CommerceOrderTypeRel fetchByC_ERC(
		long companyId, String externalReferenceCode) {

		return getPersistence().fetchByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the commerce order type rel where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce order type rel, or <code>null</code> if a matching commerce order type rel could not be found
	 */
	public static CommerceOrderTypeRel fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache) {

		return getPersistence().fetchByC_ERC(
			companyId, externalReferenceCode, useFinderCache);
	}

	/**
	 * Removes the commerce order type rel where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the commerce order type rel that was removed
	 */
	public static CommerceOrderTypeRel removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws com.liferay.commerce.exception.NoSuchOrderTypeRelException {

		return getPersistence().removeByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the number of commerce order type rels where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching commerce order type rels
	 */
	public static int countByC_ERC(
		long companyId, String externalReferenceCode) {

		return getPersistence().countByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Caches the commerce order type rel in the entity cache if it is enabled.
	 *
	 * @param commerceOrderTypeRel the commerce order type rel
	 */
	public static void cacheResult(CommerceOrderTypeRel commerceOrderTypeRel) {
		getPersistence().cacheResult(commerceOrderTypeRel);
	}

	/**
	 * Caches the commerce order type rels in the entity cache if it is enabled.
	 *
	 * @param commerceOrderTypeRels the commerce order type rels
	 */
	public static void cacheResult(
		List<CommerceOrderTypeRel> commerceOrderTypeRels) {

		getPersistence().cacheResult(commerceOrderTypeRels);
	}

	/**
	 * Creates a new commerce order type rel with the primary key. Does not add the commerce order type rel to the database.
	 *
	 * @param commerceOrderTypeRelId the primary key for the new commerce order type rel
	 * @return the new commerce order type rel
	 */
	public static CommerceOrderTypeRel create(long commerceOrderTypeRelId) {
		return getPersistence().create(commerceOrderTypeRelId);
	}

	/**
	 * Removes the commerce order type rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceOrderTypeRelId the primary key of the commerce order type rel
	 * @return the commerce order type rel that was removed
	 * @throws NoSuchOrderTypeRelException if a commerce order type rel with the primary key could not be found
	 */
	public static CommerceOrderTypeRel remove(long commerceOrderTypeRelId)
		throws com.liferay.commerce.exception.NoSuchOrderTypeRelException {

		return getPersistence().remove(commerceOrderTypeRelId);
	}

	public static CommerceOrderTypeRel updateImpl(
		CommerceOrderTypeRel commerceOrderTypeRel) {

		return getPersistence().updateImpl(commerceOrderTypeRel);
	}

	/**
	 * Returns the commerce order type rel with the primary key or throws a <code>NoSuchOrderTypeRelException</code> if it could not be found.
	 *
	 * @param commerceOrderTypeRelId the primary key of the commerce order type rel
	 * @return the commerce order type rel
	 * @throws NoSuchOrderTypeRelException if a commerce order type rel with the primary key could not be found
	 */
	public static CommerceOrderTypeRel findByPrimaryKey(
			long commerceOrderTypeRelId)
		throws com.liferay.commerce.exception.NoSuchOrderTypeRelException {

		return getPersistence().findByPrimaryKey(commerceOrderTypeRelId);
	}

	/**
	 * Returns the commerce order type rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceOrderTypeRelId the primary key of the commerce order type rel
	 * @return the commerce order type rel, or <code>null</code> if a commerce order type rel with the primary key could not be found
	 */
	public static CommerceOrderTypeRel fetchByPrimaryKey(
		long commerceOrderTypeRelId) {

		return getPersistence().fetchByPrimaryKey(commerceOrderTypeRelId);
	}

	/**
	 * Returns all the commerce order type rels.
	 *
	 * @return the commerce order type rels
	 */
	public static List<CommerceOrderTypeRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce order type rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order type rels
	 * @param end the upper bound of the range of commerce order type rels (not inclusive)
	 * @return the range of commerce order type rels
	 */
	public static List<CommerceOrderTypeRel> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce order type rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order type rels
	 * @param end the upper bound of the range of commerce order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce order type rels
	 */
	public static List<CommerceOrderTypeRel> findAll(
		int start, int end,
		OrderByComparator<CommerceOrderTypeRel> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce order type rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order type rels
	 * @param end the upper bound of the range of commerce order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce order type rels
	 */
	public static List<CommerceOrderTypeRel> findAll(
		int start, int end,
		OrderByComparator<CommerceOrderTypeRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce order type rels from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce order type rels.
	 *
	 * @return the number of commerce order type rels
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceOrderTypeRelPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceOrderTypeRelPersistence, CommerceOrderTypeRelPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceOrderTypeRelPersistence.class);

		ServiceTracker
			<CommerceOrderTypeRelPersistence, CommerceOrderTypeRelPersistence>
				serviceTracker =
					new ServiceTracker
						<CommerceOrderTypeRelPersistence,
						 CommerceOrderTypeRelPersistence>(
							 bundle.getBundleContext(),
							 CommerceOrderTypeRelPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}
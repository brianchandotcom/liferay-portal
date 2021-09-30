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

package com.liferay.commerce.order.rule.service.persistence;

import com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel;
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
 * The persistence utility for the commerce order rule entry rel service. This utility wraps <code>com.liferay.commerce.order.rule.service.persistence.impl.CommerceOrderRuleEntryRelPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceOrderRuleEntryRelPersistence
 * @generated
 */
public class CommerceOrderRuleEntryRelUtil {

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
		CommerceOrderRuleEntryRel commerceOrderRuleEntryRel) {

		getPersistence().clearCache(commerceOrderRuleEntryRel);
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
	public static Map<Serializable, CommerceOrderRuleEntryRel>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceOrderRuleEntryRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceOrderRuleEntryRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceOrderRuleEntryRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceOrderRuleEntryRel> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceOrderRuleEntryRel update(
		CommerceOrderRuleEntryRel commerceOrderRuleEntryRel) {

		return getPersistence().update(commerceOrderRuleEntryRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceOrderRuleEntryRel update(
		CommerceOrderRuleEntryRel commerceOrderRuleEntryRel,
		ServiceContext serviceContext) {

		return getPersistence().update(
			commerceOrderRuleEntryRel, serviceContext);
	}

	/**
	 * Returns all the commerce order rule entry rels where commerceOrderRuleEntryId = &#63;.
	 *
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @return the matching commerce order rule entry rels
	 */
	public static List<CommerceOrderRuleEntryRel>
		findByCommerceOrderRuleEntryId(long commerceOrderRuleEntryId) {

		return getPersistence().findByCommerceOrderRuleEntryId(
			commerceOrderRuleEntryId);
	}

	/**
	 * Returns a range of all the commerce order rule entry rels where commerceOrderRuleEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param start the lower bound of the range of commerce order rule entry rels
	 * @param end the upper bound of the range of commerce order rule entry rels (not inclusive)
	 * @return the range of matching commerce order rule entry rels
	 */
	public static List<CommerceOrderRuleEntryRel>
		findByCommerceOrderRuleEntryId(
			long commerceOrderRuleEntryId, int start, int end) {

		return getPersistence().findByCommerceOrderRuleEntryId(
			commerceOrderRuleEntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce order rule entry rels where commerceOrderRuleEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param start the lower bound of the range of commerce order rule entry rels
	 * @param end the upper bound of the range of commerce order rule entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order rule entry rels
	 */
	public static List<CommerceOrderRuleEntryRel>
		findByCommerceOrderRuleEntryId(
			long commerceOrderRuleEntryId, int start, int end,
			OrderByComparator<CommerceOrderRuleEntryRel> orderByComparator) {

		return getPersistence().findByCommerceOrderRuleEntryId(
			commerceOrderRuleEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce order rule entry rels where commerceOrderRuleEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param start the lower bound of the range of commerce order rule entry rels
	 * @param end the upper bound of the range of commerce order rule entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order rule entry rels
	 */
	public static List<CommerceOrderRuleEntryRel>
		findByCommerceOrderRuleEntryId(
			long commerceOrderRuleEntryId, int start, int end,
			OrderByComparator<CommerceOrderRuleEntryRel> orderByComparator,
			boolean useFinderCache) {

		return getPersistence().findByCommerceOrderRuleEntryId(
			commerceOrderRuleEntryId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce order rule entry rel in the ordered set where commerceOrderRuleEntryId = &#63;.
	 *
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order rule entry rel
	 * @throws NoSuchOrderRuleEntryRelException if a matching commerce order rule entry rel could not be found
	 */
	public static CommerceOrderRuleEntryRel
			findByCommerceOrderRuleEntryId_First(
				long commerceOrderRuleEntryId,
				OrderByComparator<CommerceOrderRuleEntryRel> orderByComparator)
		throws com.liferay.commerce.order.rule.exception.
			NoSuchOrderRuleEntryRelException {

		return getPersistence().findByCommerceOrderRuleEntryId_First(
			commerceOrderRuleEntryId, orderByComparator);
	}

	/**
	 * Returns the first commerce order rule entry rel in the ordered set where commerceOrderRuleEntryId = &#63;.
	 *
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order rule entry rel, or <code>null</code> if a matching commerce order rule entry rel could not be found
	 */
	public static CommerceOrderRuleEntryRel
		fetchByCommerceOrderRuleEntryId_First(
			long commerceOrderRuleEntryId,
			OrderByComparator<CommerceOrderRuleEntryRel> orderByComparator) {

		return getPersistence().fetchByCommerceOrderRuleEntryId_First(
			commerceOrderRuleEntryId, orderByComparator);
	}

	/**
	 * Returns the last commerce order rule entry rel in the ordered set where commerceOrderRuleEntryId = &#63;.
	 *
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order rule entry rel
	 * @throws NoSuchOrderRuleEntryRelException if a matching commerce order rule entry rel could not be found
	 */
	public static CommerceOrderRuleEntryRel findByCommerceOrderRuleEntryId_Last(
			long commerceOrderRuleEntryId,
			OrderByComparator<CommerceOrderRuleEntryRel> orderByComparator)
		throws com.liferay.commerce.order.rule.exception.
			NoSuchOrderRuleEntryRelException {

		return getPersistence().findByCommerceOrderRuleEntryId_Last(
			commerceOrderRuleEntryId, orderByComparator);
	}

	/**
	 * Returns the last commerce order rule entry rel in the ordered set where commerceOrderRuleEntryId = &#63;.
	 *
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order rule entry rel, or <code>null</code> if a matching commerce order rule entry rel could not be found
	 */
	public static CommerceOrderRuleEntryRel
		fetchByCommerceOrderRuleEntryId_Last(
			long commerceOrderRuleEntryId,
			OrderByComparator<CommerceOrderRuleEntryRel> orderByComparator) {

		return getPersistence().fetchByCommerceOrderRuleEntryId_Last(
			commerceOrderRuleEntryId, orderByComparator);
	}

	/**
	 * Returns the commerce order rule entry rels before and after the current commerce order rule entry rel in the ordered set where commerceOrderRuleEntryId = &#63;.
	 *
	 * @param commerceOrderRuleEntryRelId the primary key of the current commerce order rule entry rel
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order rule entry rel
	 * @throws NoSuchOrderRuleEntryRelException if a commerce order rule entry rel with the primary key could not be found
	 */
	public static CommerceOrderRuleEntryRel[]
			findByCommerceOrderRuleEntryId_PrevAndNext(
				long commerceOrderRuleEntryRelId, long commerceOrderRuleEntryId,
				OrderByComparator<CommerceOrderRuleEntryRel> orderByComparator)
		throws com.liferay.commerce.order.rule.exception.
			NoSuchOrderRuleEntryRelException {

		return getPersistence().findByCommerceOrderRuleEntryId_PrevAndNext(
			commerceOrderRuleEntryRelId, commerceOrderRuleEntryId,
			orderByComparator);
	}

	/**
	 * Removes all the commerce order rule entry rels where commerceOrderRuleEntryId = &#63; from the database.
	 *
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 */
	public static void removeByCommerceOrderRuleEntryId(
		long commerceOrderRuleEntryId) {

		getPersistence().removeByCommerceOrderRuleEntryId(
			commerceOrderRuleEntryId);
	}

	/**
	 * Returns the number of commerce order rule entry rels where commerceOrderRuleEntryId = &#63;.
	 *
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @return the number of matching commerce order rule entry rels
	 */
	public static int countByCommerceOrderRuleEntryId(
		long commerceOrderRuleEntryId) {

		return getPersistence().countByCommerceOrderRuleEntryId(
			commerceOrderRuleEntryId);
	}

	/**
	 * Returns all the commerce order rule entry rels where classNameId = &#63; and commerceOrderRuleEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @return the matching commerce order rule entry rels
	 */
	public static List<CommerceOrderRuleEntryRel> findByC_C(
		long classNameId, long commerceOrderRuleEntryId) {

		return getPersistence().findByC_C(
			classNameId, commerceOrderRuleEntryId);
	}

	/**
	 * Returns a range of all the commerce order rule entry rels where classNameId = &#63; and commerceOrderRuleEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param start the lower bound of the range of commerce order rule entry rels
	 * @param end the upper bound of the range of commerce order rule entry rels (not inclusive)
	 * @return the range of matching commerce order rule entry rels
	 */
	public static List<CommerceOrderRuleEntryRel> findByC_C(
		long classNameId, long commerceOrderRuleEntryId, int start, int end) {

		return getPersistence().findByC_C(
			classNameId, commerceOrderRuleEntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce order rule entry rels where classNameId = &#63; and commerceOrderRuleEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param start the lower bound of the range of commerce order rule entry rels
	 * @param end the upper bound of the range of commerce order rule entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order rule entry rels
	 */
	public static List<CommerceOrderRuleEntryRel> findByC_C(
		long classNameId, long commerceOrderRuleEntryId, int start, int end,
		OrderByComparator<CommerceOrderRuleEntryRel> orderByComparator) {

		return getPersistence().findByC_C(
			classNameId, commerceOrderRuleEntryId, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce order rule entry rels where classNameId = &#63; and commerceOrderRuleEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param start the lower bound of the range of commerce order rule entry rels
	 * @param end the upper bound of the range of commerce order rule entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order rule entry rels
	 */
	public static List<CommerceOrderRuleEntryRel> findByC_C(
		long classNameId, long commerceOrderRuleEntryId, int start, int end,
		OrderByComparator<CommerceOrderRuleEntryRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_C(
			classNameId, commerceOrderRuleEntryId, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce order rule entry rel in the ordered set where classNameId = &#63; and commerceOrderRuleEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order rule entry rel
	 * @throws NoSuchOrderRuleEntryRelException if a matching commerce order rule entry rel could not be found
	 */
	public static CommerceOrderRuleEntryRel findByC_C_First(
			long classNameId, long commerceOrderRuleEntryId,
			OrderByComparator<CommerceOrderRuleEntryRel> orderByComparator)
		throws com.liferay.commerce.order.rule.exception.
			NoSuchOrderRuleEntryRelException {

		return getPersistence().findByC_C_First(
			classNameId, commerceOrderRuleEntryId, orderByComparator);
	}

	/**
	 * Returns the first commerce order rule entry rel in the ordered set where classNameId = &#63; and commerceOrderRuleEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order rule entry rel, or <code>null</code> if a matching commerce order rule entry rel could not be found
	 */
	public static CommerceOrderRuleEntryRel fetchByC_C_First(
		long classNameId, long commerceOrderRuleEntryId,
		OrderByComparator<CommerceOrderRuleEntryRel> orderByComparator) {

		return getPersistence().fetchByC_C_First(
			classNameId, commerceOrderRuleEntryId, orderByComparator);
	}

	/**
	 * Returns the last commerce order rule entry rel in the ordered set where classNameId = &#63; and commerceOrderRuleEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order rule entry rel
	 * @throws NoSuchOrderRuleEntryRelException if a matching commerce order rule entry rel could not be found
	 */
	public static CommerceOrderRuleEntryRel findByC_C_Last(
			long classNameId, long commerceOrderRuleEntryId,
			OrderByComparator<CommerceOrderRuleEntryRel> orderByComparator)
		throws com.liferay.commerce.order.rule.exception.
			NoSuchOrderRuleEntryRelException {

		return getPersistence().findByC_C_Last(
			classNameId, commerceOrderRuleEntryId, orderByComparator);
	}

	/**
	 * Returns the last commerce order rule entry rel in the ordered set where classNameId = &#63; and commerceOrderRuleEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order rule entry rel, or <code>null</code> if a matching commerce order rule entry rel could not be found
	 */
	public static CommerceOrderRuleEntryRel fetchByC_C_Last(
		long classNameId, long commerceOrderRuleEntryId,
		OrderByComparator<CommerceOrderRuleEntryRel> orderByComparator) {

		return getPersistence().fetchByC_C_Last(
			classNameId, commerceOrderRuleEntryId, orderByComparator);
	}

	/**
	 * Returns the commerce order rule entry rels before and after the current commerce order rule entry rel in the ordered set where classNameId = &#63; and commerceOrderRuleEntryId = &#63;.
	 *
	 * @param commerceOrderRuleEntryRelId the primary key of the current commerce order rule entry rel
	 * @param classNameId the class name ID
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order rule entry rel
	 * @throws NoSuchOrderRuleEntryRelException if a commerce order rule entry rel with the primary key could not be found
	 */
	public static CommerceOrderRuleEntryRel[] findByC_C_PrevAndNext(
			long commerceOrderRuleEntryRelId, long classNameId,
			long commerceOrderRuleEntryId,
			OrderByComparator<CommerceOrderRuleEntryRel> orderByComparator)
		throws com.liferay.commerce.order.rule.exception.
			NoSuchOrderRuleEntryRelException {

		return getPersistence().findByC_C_PrevAndNext(
			commerceOrderRuleEntryRelId, classNameId, commerceOrderRuleEntryId,
			orderByComparator);
	}

	/**
	 * Removes all the commerce order rule entry rels where classNameId = &#63; and commerceOrderRuleEntryId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 */
	public static void removeByC_C(
		long classNameId, long commerceOrderRuleEntryId) {

		getPersistence().removeByC_C(classNameId, commerceOrderRuleEntryId);
	}

	/**
	 * Returns the number of commerce order rule entry rels where classNameId = &#63; and commerceOrderRuleEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @return the number of matching commerce order rule entry rels
	 */
	public static int countByC_C(
		long classNameId, long commerceOrderRuleEntryId) {

		return getPersistence().countByC_C(
			classNameId, commerceOrderRuleEntryId);
	}

	/**
	 * Returns the commerce order rule entry rel where classNameId = &#63; and classPK = &#63; and commerceOrderRuleEntryId = &#63; or throws a <code>NoSuchOrderRuleEntryRelException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @return the matching commerce order rule entry rel
	 * @throws NoSuchOrderRuleEntryRelException if a matching commerce order rule entry rel could not be found
	 */
	public static CommerceOrderRuleEntryRel findByC_C_C(
			long classNameId, long classPK, long commerceOrderRuleEntryId)
		throws com.liferay.commerce.order.rule.exception.
			NoSuchOrderRuleEntryRelException {

		return getPersistence().findByC_C_C(
			classNameId, classPK, commerceOrderRuleEntryId);
	}

	/**
	 * Returns the commerce order rule entry rel where classNameId = &#63; and classPK = &#63; and commerceOrderRuleEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @return the matching commerce order rule entry rel, or <code>null</code> if a matching commerce order rule entry rel could not be found
	 */
	public static CommerceOrderRuleEntryRel fetchByC_C_C(
		long classNameId, long classPK, long commerceOrderRuleEntryId) {

		return getPersistence().fetchByC_C_C(
			classNameId, classPK, commerceOrderRuleEntryId);
	}

	/**
	 * Returns the commerce order rule entry rel where classNameId = &#63; and classPK = &#63; and commerceOrderRuleEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce order rule entry rel, or <code>null</code> if a matching commerce order rule entry rel could not be found
	 */
	public static CommerceOrderRuleEntryRel fetchByC_C_C(
		long classNameId, long classPK, long commerceOrderRuleEntryId,
		boolean useFinderCache) {

		return getPersistence().fetchByC_C_C(
			classNameId, classPK, commerceOrderRuleEntryId, useFinderCache);
	}

	/**
	 * Removes the commerce order rule entry rel where classNameId = &#63; and classPK = &#63; and commerceOrderRuleEntryId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @return the commerce order rule entry rel that was removed
	 */
	public static CommerceOrderRuleEntryRel removeByC_C_C(
			long classNameId, long classPK, long commerceOrderRuleEntryId)
		throws com.liferay.commerce.order.rule.exception.
			NoSuchOrderRuleEntryRelException {

		return getPersistence().removeByC_C_C(
			classNameId, classPK, commerceOrderRuleEntryId);
	}

	/**
	 * Returns the number of commerce order rule entry rels where classNameId = &#63; and classPK = &#63; and commerceOrderRuleEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @return the number of matching commerce order rule entry rels
	 */
	public static int countByC_C_C(
		long classNameId, long classPK, long commerceOrderRuleEntryId) {

		return getPersistence().countByC_C_C(
			classNameId, classPK, commerceOrderRuleEntryId);
	}

	/**
	 * Caches the commerce order rule entry rel in the entity cache if it is enabled.
	 *
	 * @param commerceOrderRuleEntryRel the commerce order rule entry rel
	 */
	public static void cacheResult(
		CommerceOrderRuleEntryRel commerceOrderRuleEntryRel) {

		getPersistence().cacheResult(commerceOrderRuleEntryRel);
	}

	/**
	 * Caches the commerce order rule entry rels in the entity cache if it is enabled.
	 *
	 * @param commerceOrderRuleEntryRels the commerce order rule entry rels
	 */
	public static void cacheResult(
		List<CommerceOrderRuleEntryRel> commerceOrderRuleEntryRels) {

		getPersistence().cacheResult(commerceOrderRuleEntryRels);
	}

	/**
	 * Creates a new commerce order rule entry rel with the primary key. Does not add the commerce order rule entry rel to the database.
	 *
	 * @param commerceOrderRuleEntryRelId the primary key for the new commerce order rule entry rel
	 * @return the new commerce order rule entry rel
	 */
	public static CommerceOrderRuleEntryRel create(
		long commerceOrderRuleEntryRelId) {

		return getPersistence().create(commerceOrderRuleEntryRelId);
	}

	/**
	 * Removes the commerce order rule entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceOrderRuleEntryRelId the primary key of the commerce order rule entry rel
	 * @return the commerce order rule entry rel that was removed
	 * @throws NoSuchOrderRuleEntryRelException if a commerce order rule entry rel with the primary key could not be found
	 */
	public static CommerceOrderRuleEntryRel remove(
			long commerceOrderRuleEntryRelId)
		throws com.liferay.commerce.order.rule.exception.
			NoSuchOrderRuleEntryRelException {

		return getPersistence().remove(commerceOrderRuleEntryRelId);
	}

	public static CommerceOrderRuleEntryRel updateImpl(
		CommerceOrderRuleEntryRel commerceOrderRuleEntryRel) {

		return getPersistence().updateImpl(commerceOrderRuleEntryRel);
	}

	/**
	 * Returns the commerce order rule entry rel with the primary key or throws a <code>NoSuchOrderRuleEntryRelException</code> if it could not be found.
	 *
	 * @param commerceOrderRuleEntryRelId the primary key of the commerce order rule entry rel
	 * @return the commerce order rule entry rel
	 * @throws NoSuchOrderRuleEntryRelException if a commerce order rule entry rel with the primary key could not be found
	 */
	public static CommerceOrderRuleEntryRel findByPrimaryKey(
			long commerceOrderRuleEntryRelId)
		throws com.liferay.commerce.order.rule.exception.
			NoSuchOrderRuleEntryRelException {

		return getPersistence().findByPrimaryKey(commerceOrderRuleEntryRelId);
	}

	/**
	 * Returns the commerce order rule entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceOrderRuleEntryRelId the primary key of the commerce order rule entry rel
	 * @return the commerce order rule entry rel, or <code>null</code> if a commerce order rule entry rel with the primary key could not be found
	 */
	public static CommerceOrderRuleEntryRel fetchByPrimaryKey(
		long commerceOrderRuleEntryRelId) {

		return getPersistence().fetchByPrimaryKey(commerceOrderRuleEntryRelId);
	}

	/**
	 * Returns all the commerce order rule entry rels.
	 *
	 * @return the commerce order rule entry rels
	 */
	public static List<CommerceOrderRuleEntryRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce order rule entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order rule entry rels
	 * @param end the upper bound of the range of commerce order rule entry rels (not inclusive)
	 * @return the range of commerce order rule entry rels
	 */
	public static List<CommerceOrderRuleEntryRel> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce order rule entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order rule entry rels
	 * @param end the upper bound of the range of commerce order rule entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce order rule entry rels
	 */
	public static List<CommerceOrderRuleEntryRel> findAll(
		int start, int end,
		OrderByComparator<CommerceOrderRuleEntryRel> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce order rule entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order rule entry rels
	 * @param end the upper bound of the range of commerce order rule entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce order rule entry rels
	 */
	public static List<CommerceOrderRuleEntryRel> findAll(
		int start, int end,
		OrderByComparator<CommerceOrderRuleEntryRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce order rule entry rels from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce order rule entry rels.
	 *
	 * @return the number of commerce order rule entry rels
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceOrderRuleEntryRelPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceOrderRuleEntryRelPersistence,
		 CommerceOrderRuleEntryRelPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceOrderRuleEntryRelPersistence.class);

		ServiceTracker
			<CommerceOrderRuleEntryRelPersistence,
			 CommerceOrderRuleEntryRelPersistence> serviceTracker =
				new ServiceTracker
					<CommerceOrderRuleEntryRelPersistence,
					 CommerceOrderRuleEntryRelPersistence>(
						 bundle.getBundleContext(),
						 CommerceOrderRuleEntryRelPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}
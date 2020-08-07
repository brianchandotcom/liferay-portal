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

package com.liferay.commerce.account.service.persistence;

import com.liferay.commerce.account.model.CommerceAccountGroupCommerceAccountRel;
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
 * The persistence utility for the commerce account group commerce account rel service. This utility wraps <code>com.liferay.commerce.account.service.persistence.impl.CommerceAccountGroupCommerceAccountRelPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceAccountGroupCommerceAccountRelPersistence
 * @generated
 */
public class CommerceAccountGroupCommerceAccountRelUtil {

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
		CommerceAccountGroupCommerceAccountRel
			commerceAccountGroupCommerceAccountRel) {

		getPersistence().clearCache(commerceAccountGroupCommerceAccountRel);
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
	public static Map<Serializable, CommerceAccountGroupCommerceAccountRel>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceAccountGroupCommerceAccountRel>
		findWithDynamicQuery(DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceAccountGroupCommerceAccountRel>
		findWithDynamicQuery(DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceAccountGroupCommerceAccountRel>
		findWithDynamicQuery(
			DynamicQuery dynamicQuery, int start, int end,
			OrderByComparator<CommerceAccountGroupCommerceAccountRel>
				orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceAccountGroupCommerceAccountRel update(
		CommerceAccountGroupCommerceAccountRel
			commerceAccountGroupCommerceAccountRel) {

		return getPersistence().update(commerceAccountGroupCommerceAccountRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceAccountGroupCommerceAccountRel update(
		CommerceAccountGroupCommerceAccountRel
			commerceAccountGroupCommerceAccountRel,
		ServiceContext serviceContext) {

		return getPersistence().update(
			commerceAccountGroupCommerceAccountRel, serviceContext);
	}

	/**
	 * Returns all the commerce account group commerce account rels where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the matching commerce account group commerce account rels
	 */
	public static List<CommerceAccountGroupCommerceAccountRel>
		findByCommerceAccountGroupId(long commerceAccountGroupId) {

		return getPersistence().findByCommerceAccountGroupId(
			commerceAccountGroupId);
	}

	/**
	 * Returns a range of all the commerce account group commerce account rels where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupCommerceAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce account group commerce account rels
	 * @param end the upper bound of the range of commerce account group commerce account rels (not inclusive)
	 * @return the range of matching commerce account group commerce account rels
	 */
	public static List<CommerceAccountGroupCommerceAccountRel>
		findByCommerceAccountGroupId(
			long commerceAccountGroupId, int start, int end) {

		return getPersistence().findByCommerceAccountGroupId(
			commerceAccountGroupId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce account group commerce account rels where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupCommerceAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce account group commerce account rels
	 * @param end the upper bound of the range of commerce account group commerce account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce account group commerce account rels
	 */
	public static List<CommerceAccountGroupCommerceAccountRel>
		findByCommerceAccountGroupId(
			long commerceAccountGroupId, int start, int end,
			OrderByComparator<CommerceAccountGroupCommerceAccountRel>
				orderByComparator) {

		return getPersistence().findByCommerceAccountGroupId(
			commerceAccountGroupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce account group commerce account rels where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupCommerceAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce account group commerce account rels
	 * @param end the upper bound of the range of commerce account group commerce account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce account group commerce account rels
	 */
	public static List<CommerceAccountGroupCommerceAccountRel>
		findByCommerceAccountGroupId(
			long commerceAccountGroupId, int start, int end,
			OrderByComparator<CommerceAccountGroupCommerceAccountRel>
				orderByComparator,
			boolean useFinderCache) {

		return getPersistence().findByCommerceAccountGroupId(
			commerceAccountGroupId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce account group commerce account rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account group commerce account rel
	 * @throws NoSuchAccountGroupCommerceAccountRelException if a matching commerce account group commerce account rel could not be found
	 */
	public static CommerceAccountGroupCommerceAccountRel
			findByCommerceAccountGroupId_First(
				long commerceAccountGroupId,
				OrderByComparator<CommerceAccountGroupCommerceAccountRel>
					orderByComparator)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountGroupCommerceAccountRelException {

		return getPersistence().findByCommerceAccountGroupId_First(
			commerceAccountGroupId, orderByComparator);
	}

	/**
	 * Returns the first commerce account group commerce account rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account group commerce account rel, or <code>null</code> if a matching commerce account group commerce account rel could not be found
	 */
	public static CommerceAccountGroupCommerceAccountRel
		fetchByCommerceAccountGroupId_First(
			long commerceAccountGroupId,
			OrderByComparator<CommerceAccountGroupCommerceAccountRel>
				orderByComparator) {

		return getPersistence().fetchByCommerceAccountGroupId_First(
			commerceAccountGroupId, orderByComparator);
	}

	/**
	 * Returns the last commerce account group commerce account rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account group commerce account rel
	 * @throws NoSuchAccountGroupCommerceAccountRelException if a matching commerce account group commerce account rel could not be found
	 */
	public static CommerceAccountGroupCommerceAccountRel
			findByCommerceAccountGroupId_Last(
				long commerceAccountGroupId,
				OrderByComparator<CommerceAccountGroupCommerceAccountRel>
					orderByComparator)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountGroupCommerceAccountRelException {

		return getPersistence().findByCommerceAccountGroupId_Last(
			commerceAccountGroupId, orderByComparator);
	}

	/**
	 * Returns the last commerce account group commerce account rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account group commerce account rel, or <code>null</code> if a matching commerce account group commerce account rel could not be found
	 */
	public static CommerceAccountGroupCommerceAccountRel
		fetchByCommerceAccountGroupId_Last(
			long commerceAccountGroupId,
			OrderByComparator<CommerceAccountGroupCommerceAccountRel>
				orderByComparator) {

		return getPersistence().fetchByCommerceAccountGroupId_Last(
			commerceAccountGroupId, orderByComparator);
	}

	/**
	 * Returns the commerce account group commerce account rels before and after the current commerce account group commerce account rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupCommerceAccountRelId the primary key of the current commerce account group commerce account rel
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce account group commerce account rel
	 * @throws NoSuchAccountGroupCommerceAccountRelException if a commerce account group commerce account rel with the primary key could not be found
	 */
	public static CommerceAccountGroupCommerceAccountRel[]
			findByCommerceAccountGroupId_PrevAndNext(
				long commerceAccountGroupCommerceAccountRelId,
				long commerceAccountGroupId,
				OrderByComparator<CommerceAccountGroupCommerceAccountRel>
					orderByComparator)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountGroupCommerceAccountRelException {

		return getPersistence().findByCommerceAccountGroupId_PrevAndNext(
			commerceAccountGroupCommerceAccountRelId, commerceAccountGroupId,
			orderByComparator);
	}

	/**
	 * Removes all the commerce account group commerce account rels where commerceAccountGroupId = &#63; from the database.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 */
	public static void removeByCommerceAccountGroupId(
		long commerceAccountGroupId) {

		getPersistence().removeByCommerceAccountGroupId(commerceAccountGroupId);
	}

	/**
	 * Returns the number of commerce account group commerce account rels where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the number of matching commerce account group commerce account rels
	 */
	public static int countByCommerceAccountGroupId(
		long commerceAccountGroupId) {

		return getPersistence().countByCommerceAccountGroupId(
			commerceAccountGroupId);
	}

	/**
	 * Returns all the commerce account group commerce account rels where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @return the matching commerce account group commerce account rels
	 */
	public static List<CommerceAccountGroupCommerceAccountRel>
		findByCommerceAccountId(long commerceAccountId) {

		return getPersistence().findByCommerceAccountId(commerceAccountId);
	}

	/**
	 * Returns a range of all the commerce account group commerce account rels where commerceAccountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupCommerceAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param start the lower bound of the range of commerce account group commerce account rels
	 * @param end the upper bound of the range of commerce account group commerce account rels (not inclusive)
	 * @return the range of matching commerce account group commerce account rels
	 */
	public static List<CommerceAccountGroupCommerceAccountRel>
		findByCommerceAccountId(long commerceAccountId, int start, int end) {

		return getPersistence().findByCommerceAccountId(
			commerceAccountId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce account group commerce account rels where commerceAccountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupCommerceAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param start the lower bound of the range of commerce account group commerce account rels
	 * @param end the upper bound of the range of commerce account group commerce account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce account group commerce account rels
	 */
	public static List<CommerceAccountGroupCommerceAccountRel>
		findByCommerceAccountId(
			long commerceAccountId, int start, int end,
			OrderByComparator<CommerceAccountGroupCommerceAccountRel>
				orderByComparator) {

		return getPersistence().findByCommerceAccountId(
			commerceAccountId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce account group commerce account rels where commerceAccountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupCommerceAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param start the lower bound of the range of commerce account group commerce account rels
	 * @param end the upper bound of the range of commerce account group commerce account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce account group commerce account rels
	 */
	public static List<CommerceAccountGroupCommerceAccountRel>
		findByCommerceAccountId(
			long commerceAccountId, int start, int end,
			OrderByComparator<CommerceAccountGroupCommerceAccountRel>
				orderByComparator,
			boolean useFinderCache) {

		return getPersistence().findByCommerceAccountId(
			commerceAccountId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce account group commerce account rel in the ordered set where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account group commerce account rel
	 * @throws NoSuchAccountGroupCommerceAccountRelException if a matching commerce account group commerce account rel could not be found
	 */
	public static CommerceAccountGroupCommerceAccountRel
			findByCommerceAccountId_First(
				long commerceAccountId,
				OrderByComparator<CommerceAccountGroupCommerceAccountRel>
					orderByComparator)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountGroupCommerceAccountRelException {

		return getPersistence().findByCommerceAccountId_First(
			commerceAccountId, orderByComparator);
	}

	/**
	 * Returns the first commerce account group commerce account rel in the ordered set where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account group commerce account rel, or <code>null</code> if a matching commerce account group commerce account rel could not be found
	 */
	public static CommerceAccountGroupCommerceAccountRel
		fetchByCommerceAccountId_First(
			long commerceAccountId,
			OrderByComparator<CommerceAccountGroupCommerceAccountRel>
				orderByComparator) {

		return getPersistence().fetchByCommerceAccountId_First(
			commerceAccountId, orderByComparator);
	}

	/**
	 * Returns the last commerce account group commerce account rel in the ordered set where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account group commerce account rel
	 * @throws NoSuchAccountGroupCommerceAccountRelException if a matching commerce account group commerce account rel could not be found
	 */
	public static CommerceAccountGroupCommerceAccountRel
			findByCommerceAccountId_Last(
				long commerceAccountId,
				OrderByComparator<CommerceAccountGroupCommerceAccountRel>
					orderByComparator)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountGroupCommerceAccountRelException {

		return getPersistence().findByCommerceAccountId_Last(
			commerceAccountId, orderByComparator);
	}

	/**
	 * Returns the last commerce account group commerce account rel in the ordered set where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account group commerce account rel, or <code>null</code> if a matching commerce account group commerce account rel could not be found
	 */
	public static CommerceAccountGroupCommerceAccountRel
		fetchByCommerceAccountId_Last(
			long commerceAccountId,
			OrderByComparator<CommerceAccountGroupCommerceAccountRel>
				orderByComparator) {

		return getPersistence().fetchByCommerceAccountId_Last(
			commerceAccountId, orderByComparator);
	}

	/**
	 * Returns the commerce account group commerce account rels before and after the current commerce account group commerce account rel in the ordered set where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountGroupCommerceAccountRelId the primary key of the current commerce account group commerce account rel
	 * @param commerceAccountId the commerce account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce account group commerce account rel
	 * @throws NoSuchAccountGroupCommerceAccountRelException if a commerce account group commerce account rel with the primary key could not be found
	 */
	public static CommerceAccountGroupCommerceAccountRel[]
			findByCommerceAccountId_PrevAndNext(
				long commerceAccountGroupCommerceAccountRelId,
				long commerceAccountId,
				OrderByComparator<CommerceAccountGroupCommerceAccountRel>
					orderByComparator)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountGroupCommerceAccountRelException {

		return getPersistence().findByCommerceAccountId_PrevAndNext(
			commerceAccountGroupCommerceAccountRelId, commerceAccountId,
			orderByComparator);
	}

	/**
	 * Removes all the commerce account group commerce account rels where commerceAccountId = &#63; from the database.
	 *
	 * @param commerceAccountId the commerce account ID
	 */
	public static void removeByCommerceAccountId(long commerceAccountId) {
		getPersistence().removeByCommerceAccountId(commerceAccountId);
	}

	/**
	 * Returns the number of commerce account group commerce account rels where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @return the number of matching commerce account group commerce account rels
	 */
	public static int countByCommerceAccountId(long commerceAccountId) {
		return getPersistence().countByCommerceAccountId(commerceAccountId);
	}

	/**
	 * Returns the commerce account group commerce account rel where commerceAccountGroupId = &#63; and commerceAccountId = &#63; or throws a <code>NoSuchAccountGroupCommerceAccountRelException</code> if it could not be found.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param commerceAccountId the commerce account ID
	 * @return the matching commerce account group commerce account rel
	 * @throws NoSuchAccountGroupCommerceAccountRelException if a matching commerce account group commerce account rel could not be found
	 */
	public static CommerceAccountGroupCommerceAccountRel findByC_C(
			long commerceAccountGroupId, long commerceAccountId)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountGroupCommerceAccountRelException {

		return getPersistence().findByC_C(
			commerceAccountGroupId, commerceAccountId);
	}

	/**
	 * Returns the commerce account group commerce account rel where commerceAccountGroupId = &#63; and commerceAccountId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param commerceAccountId the commerce account ID
	 * @return the matching commerce account group commerce account rel, or <code>null</code> if a matching commerce account group commerce account rel could not be found
	 */
	public static CommerceAccountGroupCommerceAccountRel fetchByC_C(
		long commerceAccountGroupId, long commerceAccountId) {

		return getPersistence().fetchByC_C(
			commerceAccountGroupId, commerceAccountId);
	}

	/**
	 * Returns the commerce account group commerce account rel where commerceAccountGroupId = &#63; and commerceAccountId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param commerceAccountId the commerce account ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce account group commerce account rel, or <code>null</code> if a matching commerce account group commerce account rel could not be found
	 */
	public static CommerceAccountGroupCommerceAccountRel fetchByC_C(
		long commerceAccountGroupId, long commerceAccountId,
		boolean useFinderCache) {

		return getPersistence().fetchByC_C(
			commerceAccountGroupId, commerceAccountId, useFinderCache);
	}

	/**
	 * Removes the commerce account group commerce account rel where commerceAccountGroupId = &#63; and commerceAccountId = &#63; from the database.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param commerceAccountId the commerce account ID
	 * @return the commerce account group commerce account rel that was removed
	 */
	public static CommerceAccountGroupCommerceAccountRel removeByC_C(
			long commerceAccountGroupId, long commerceAccountId)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountGroupCommerceAccountRelException {

		return getPersistence().removeByC_C(
			commerceAccountGroupId, commerceAccountId);
	}

	/**
	 * Returns the number of commerce account group commerce account rels where commerceAccountGroupId = &#63; and commerceAccountId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param commerceAccountId the commerce account ID
	 * @return the number of matching commerce account group commerce account rels
	 */
	public static int countByC_C(
		long commerceAccountGroupId, long commerceAccountId) {

		return getPersistence().countByC_C(
			commerceAccountGroupId, commerceAccountId);
	}

	/**
	 * Returns the commerce account group commerce account rel where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchAccountGroupCommerceAccountRelException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce account group commerce account rel
	 * @throws NoSuchAccountGroupCommerceAccountRelException if a matching commerce account group commerce account rel could not be found
	 */
	public static CommerceAccountGroupCommerceAccountRel findByC_ERC(
			long companyId, String externalReferenceCode)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountGroupCommerceAccountRelException {

		return getPersistence().findByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the commerce account group commerce account rel where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce account group commerce account rel, or <code>null</code> if a matching commerce account group commerce account rel could not be found
	 */
	public static CommerceAccountGroupCommerceAccountRel fetchByC_ERC(
		long companyId, String externalReferenceCode) {

		return getPersistence().fetchByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the commerce account group commerce account rel where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce account group commerce account rel, or <code>null</code> if a matching commerce account group commerce account rel could not be found
	 */
	public static CommerceAccountGroupCommerceAccountRel fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache) {

		return getPersistence().fetchByC_ERC(
			companyId, externalReferenceCode, useFinderCache);
	}

	/**
	 * Removes the commerce account group commerce account rel where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the commerce account group commerce account rel that was removed
	 */
	public static CommerceAccountGroupCommerceAccountRel removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountGroupCommerceAccountRelException {

		return getPersistence().removeByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the number of commerce account group commerce account rels where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching commerce account group commerce account rels
	 */
	public static int countByC_ERC(
		long companyId, String externalReferenceCode) {

		return getPersistence().countByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Caches the commerce account group commerce account rel in the entity cache if it is enabled.
	 *
	 * @param commerceAccountGroupCommerceAccountRel the commerce account group commerce account rel
	 */
	public static void cacheResult(
		CommerceAccountGroupCommerceAccountRel
			commerceAccountGroupCommerceAccountRel) {

		getPersistence().cacheResult(commerceAccountGroupCommerceAccountRel);
	}

	/**
	 * Caches the commerce account group commerce account rels in the entity cache if it is enabled.
	 *
	 * @param commerceAccountGroupCommerceAccountRels the commerce account group commerce account rels
	 */
	public static void cacheResult(
		List<CommerceAccountGroupCommerceAccountRel>
			commerceAccountGroupCommerceAccountRels) {

		getPersistence().cacheResult(commerceAccountGroupCommerceAccountRels);
	}

	/**
	 * Creates a new commerce account group commerce account rel with the primary key. Does not add the commerce account group commerce account rel to the database.
	 *
	 * @param commerceAccountGroupCommerceAccountRelId the primary key for the new commerce account group commerce account rel
	 * @return the new commerce account group commerce account rel
	 */
	public static CommerceAccountGroupCommerceAccountRel create(
		long commerceAccountGroupCommerceAccountRelId) {

		return getPersistence().create(
			commerceAccountGroupCommerceAccountRelId);
	}

	/**
	 * Removes the commerce account group commerce account rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceAccountGroupCommerceAccountRelId the primary key of the commerce account group commerce account rel
	 * @return the commerce account group commerce account rel that was removed
	 * @throws NoSuchAccountGroupCommerceAccountRelException if a commerce account group commerce account rel with the primary key could not be found
	 */
	public static CommerceAccountGroupCommerceAccountRel remove(
			long commerceAccountGroupCommerceAccountRelId)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountGroupCommerceAccountRelException {

		return getPersistence().remove(
			commerceAccountGroupCommerceAccountRelId);
	}

	public static CommerceAccountGroupCommerceAccountRel updateImpl(
		CommerceAccountGroupCommerceAccountRel
			commerceAccountGroupCommerceAccountRel) {

		return getPersistence().updateImpl(
			commerceAccountGroupCommerceAccountRel);
	}

	/**
	 * Returns the commerce account group commerce account rel with the primary key or throws a <code>NoSuchAccountGroupCommerceAccountRelException</code> if it could not be found.
	 *
	 * @param commerceAccountGroupCommerceAccountRelId the primary key of the commerce account group commerce account rel
	 * @return the commerce account group commerce account rel
	 * @throws NoSuchAccountGroupCommerceAccountRelException if a commerce account group commerce account rel with the primary key could not be found
	 */
	public static CommerceAccountGroupCommerceAccountRel findByPrimaryKey(
			long commerceAccountGroupCommerceAccountRelId)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountGroupCommerceAccountRelException {

		return getPersistence().findByPrimaryKey(
			commerceAccountGroupCommerceAccountRelId);
	}

	/**
	 * Returns the commerce account group commerce account rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceAccountGroupCommerceAccountRelId the primary key of the commerce account group commerce account rel
	 * @return the commerce account group commerce account rel, or <code>null</code> if a commerce account group commerce account rel with the primary key could not be found
	 */
	public static CommerceAccountGroupCommerceAccountRel fetchByPrimaryKey(
		long commerceAccountGroupCommerceAccountRelId) {

		return getPersistence().fetchByPrimaryKey(
			commerceAccountGroupCommerceAccountRelId);
	}

	/**
	 * Returns all the commerce account group commerce account rels.
	 *
	 * @return the commerce account group commerce account rels
	 */
	public static List<CommerceAccountGroupCommerceAccountRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce account group commerce account rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupCommerceAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce account group commerce account rels
	 * @param end the upper bound of the range of commerce account group commerce account rels (not inclusive)
	 * @return the range of commerce account group commerce account rels
	 */
	public static List<CommerceAccountGroupCommerceAccountRel> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce account group commerce account rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupCommerceAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce account group commerce account rels
	 * @param end the upper bound of the range of commerce account group commerce account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce account group commerce account rels
	 */
	public static List<CommerceAccountGroupCommerceAccountRel> findAll(
		int start, int end,
		OrderByComparator<CommerceAccountGroupCommerceAccountRel>
			orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce account group commerce account rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupCommerceAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce account group commerce account rels
	 * @param end the upper bound of the range of commerce account group commerce account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce account group commerce account rels
	 */
	public static List<CommerceAccountGroupCommerceAccountRel> findAll(
		int start, int end,
		OrderByComparator<CommerceAccountGroupCommerceAccountRel>
			orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce account group commerce account rels from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce account group commerce account rels.
	 *
	 * @return the number of commerce account group commerce account rels
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceAccountGroupCommerceAccountRelPersistence
		getPersistence() {

		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceAccountGroupCommerceAccountRelPersistence,
		 CommerceAccountGroupCommerceAccountRelPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceAccountGroupCommerceAccountRelPersistence.class);

		ServiceTracker
			<CommerceAccountGroupCommerceAccountRelPersistence,
			 CommerceAccountGroupCommerceAccountRelPersistence> serviceTracker =
				new ServiceTracker
					<CommerceAccountGroupCommerceAccountRelPersistence,
					 CommerceAccountGroupCommerceAccountRelPersistence>(
						 bundle.getBundleContext(),
						 CommerceAccountGroupCommerceAccountRelPersistence.
							 class,
						 null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}
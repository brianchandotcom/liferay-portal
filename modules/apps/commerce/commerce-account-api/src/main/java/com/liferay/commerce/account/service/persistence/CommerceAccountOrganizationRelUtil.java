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

import com.liferay.commerce.account.model.CommerceAccountOrganizationRel;
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
 * The persistence utility for the commerce account organization rel service. This utility wraps <code>com.liferay.commerce.account.service.persistence.impl.CommerceAccountOrganizationRelPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceAccountOrganizationRelPersistence
 * @generated
 */
public class CommerceAccountOrganizationRelUtil {

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
		CommerceAccountOrganizationRel commerceAccountOrganizationRel) {

		getPersistence().clearCache(commerceAccountOrganizationRel);
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
	public static Map<Serializable, CommerceAccountOrganizationRel>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceAccountOrganizationRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceAccountOrganizationRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceAccountOrganizationRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceAccountOrganizationRel> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceAccountOrganizationRel update(
		CommerceAccountOrganizationRel commerceAccountOrganizationRel) {

		return getPersistence().update(commerceAccountOrganizationRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceAccountOrganizationRel update(
		CommerceAccountOrganizationRel commerceAccountOrganizationRel,
		ServiceContext serviceContext) {

		return getPersistence().update(
			commerceAccountOrganizationRel, serviceContext);
	}

	/**
	 * Returns all the commerce account organization rels where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @return the matching commerce account organization rels
	 */
	public static List<CommerceAccountOrganizationRel> findByCommerceAccountId(
		long commerceAccountId) {

		return getPersistence().findByCommerceAccountId(commerceAccountId);
	}

	/**
	 * Returns a range of all the commerce account organization rels where commerceAccountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountOrganizationRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param start the lower bound of the range of commerce account organization rels
	 * @param end the upper bound of the range of commerce account organization rels (not inclusive)
	 * @return the range of matching commerce account organization rels
	 */
	public static List<CommerceAccountOrganizationRel> findByCommerceAccountId(
		long commerceAccountId, int start, int end) {

		return getPersistence().findByCommerceAccountId(
			commerceAccountId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce account organization rels where commerceAccountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountOrganizationRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param start the lower bound of the range of commerce account organization rels
	 * @param end the upper bound of the range of commerce account organization rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce account organization rels
	 */
	public static List<CommerceAccountOrganizationRel> findByCommerceAccountId(
		long commerceAccountId, int start, int end,
		OrderByComparator<CommerceAccountOrganizationRel> orderByComparator) {

		return getPersistence().findByCommerceAccountId(
			commerceAccountId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce account organization rels where commerceAccountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountOrganizationRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param start the lower bound of the range of commerce account organization rels
	 * @param end the upper bound of the range of commerce account organization rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce account organization rels
	 */
	public static List<CommerceAccountOrganizationRel> findByCommerceAccountId(
		long commerceAccountId, int start, int end,
		OrderByComparator<CommerceAccountOrganizationRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCommerceAccountId(
			commerceAccountId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce account organization rel in the ordered set where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account organization rel
	 * @throws NoSuchAccountOrganizationRelException if a matching commerce account organization rel could not be found
	 */
	public static CommerceAccountOrganizationRel findByCommerceAccountId_First(
			long commerceAccountId,
			OrderByComparator<CommerceAccountOrganizationRel> orderByComparator)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountOrganizationRelException {

		return getPersistence().findByCommerceAccountId_First(
			commerceAccountId, orderByComparator);
	}

	/**
	 * Returns the first commerce account organization rel in the ordered set where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account organization rel, or <code>null</code> if a matching commerce account organization rel could not be found
	 */
	public static CommerceAccountOrganizationRel fetchByCommerceAccountId_First(
		long commerceAccountId,
		OrderByComparator<CommerceAccountOrganizationRel> orderByComparator) {

		return getPersistence().fetchByCommerceAccountId_First(
			commerceAccountId, orderByComparator);
	}

	/**
	 * Returns the last commerce account organization rel in the ordered set where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account organization rel
	 * @throws NoSuchAccountOrganizationRelException if a matching commerce account organization rel could not be found
	 */
	public static CommerceAccountOrganizationRel findByCommerceAccountId_Last(
			long commerceAccountId,
			OrderByComparator<CommerceAccountOrganizationRel> orderByComparator)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountOrganizationRelException {

		return getPersistence().findByCommerceAccountId_Last(
			commerceAccountId, orderByComparator);
	}

	/**
	 * Returns the last commerce account organization rel in the ordered set where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account organization rel, or <code>null</code> if a matching commerce account organization rel could not be found
	 */
	public static CommerceAccountOrganizationRel fetchByCommerceAccountId_Last(
		long commerceAccountId,
		OrderByComparator<CommerceAccountOrganizationRel> orderByComparator) {

		return getPersistence().fetchByCommerceAccountId_Last(
			commerceAccountId, orderByComparator);
	}

	/**
	 * Returns the commerce account organization rels before and after the current commerce account organization rel in the ordered set where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountOrganizationRelPK the primary key of the current commerce account organization rel
	 * @param commerceAccountId the commerce account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce account organization rel
	 * @throws NoSuchAccountOrganizationRelException if a commerce account organization rel with the primary key could not be found
	 */
	public static CommerceAccountOrganizationRel[]
			findByCommerceAccountId_PrevAndNext(
				CommerceAccountOrganizationRelPK
					commerceAccountOrganizationRelPK,
				long commerceAccountId,
				OrderByComparator<CommerceAccountOrganizationRel>
					orderByComparator)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountOrganizationRelException {

		return getPersistence().findByCommerceAccountId_PrevAndNext(
			commerceAccountOrganizationRelPK, commerceAccountId,
			orderByComparator);
	}

	/**
	 * Removes all the commerce account organization rels where commerceAccountId = &#63; from the database.
	 *
	 * @param commerceAccountId the commerce account ID
	 */
	public static void removeByCommerceAccountId(long commerceAccountId) {
		getPersistence().removeByCommerceAccountId(commerceAccountId);
	}

	/**
	 * Returns the number of commerce account organization rels where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @return the number of matching commerce account organization rels
	 */
	public static int countByCommerceAccountId(long commerceAccountId) {
		return getPersistence().countByCommerceAccountId(commerceAccountId);
	}

	/**
	 * Returns all the commerce account organization rels where organizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @return the matching commerce account organization rels
	 */
	public static List<CommerceAccountOrganizationRel> findByOrganizationId(
		long organizationId) {

		return getPersistence().findByOrganizationId(organizationId);
	}

	/**
	 * Returns a range of all the commerce account organization rels where organizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountOrganizationRelModelImpl</code>.
	 * </p>
	 *
	 * @param organizationId the organization ID
	 * @param start the lower bound of the range of commerce account organization rels
	 * @param end the upper bound of the range of commerce account organization rels (not inclusive)
	 * @return the range of matching commerce account organization rels
	 */
	public static List<CommerceAccountOrganizationRel> findByOrganizationId(
		long organizationId, int start, int end) {

		return getPersistence().findByOrganizationId(
			organizationId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce account organization rels where organizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountOrganizationRelModelImpl</code>.
	 * </p>
	 *
	 * @param organizationId the organization ID
	 * @param start the lower bound of the range of commerce account organization rels
	 * @param end the upper bound of the range of commerce account organization rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce account organization rels
	 */
	public static List<CommerceAccountOrganizationRel> findByOrganizationId(
		long organizationId, int start, int end,
		OrderByComparator<CommerceAccountOrganizationRel> orderByComparator) {

		return getPersistence().findByOrganizationId(
			organizationId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce account organization rels where organizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountOrganizationRelModelImpl</code>.
	 * </p>
	 *
	 * @param organizationId the organization ID
	 * @param start the lower bound of the range of commerce account organization rels
	 * @param end the upper bound of the range of commerce account organization rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce account organization rels
	 */
	public static List<CommerceAccountOrganizationRel> findByOrganizationId(
		long organizationId, int start, int end,
		OrderByComparator<CommerceAccountOrganizationRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByOrganizationId(
			organizationId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce account organization rel in the ordered set where organizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account organization rel
	 * @throws NoSuchAccountOrganizationRelException if a matching commerce account organization rel could not be found
	 */
	public static CommerceAccountOrganizationRel findByOrganizationId_First(
			long organizationId,
			OrderByComparator<CommerceAccountOrganizationRel> orderByComparator)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountOrganizationRelException {

		return getPersistence().findByOrganizationId_First(
			organizationId, orderByComparator);
	}

	/**
	 * Returns the first commerce account organization rel in the ordered set where organizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account organization rel, or <code>null</code> if a matching commerce account organization rel could not be found
	 */
	public static CommerceAccountOrganizationRel fetchByOrganizationId_First(
		long organizationId,
		OrderByComparator<CommerceAccountOrganizationRel> orderByComparator) {

		return getPersistence().fetchByOrganizationId_First(
			organizationId, orderByComparator);
	}

	/**
	 * Returns the last commerce account organization rel in the ordered set where organizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account organization rel
	 * @throws NoSuchAccountOrganizationRelException if a matching commerce account organization rel could not be found
	 */
	public static CommerceAccountOrganizationRel findByOrganizationId_Last(
			long organizationId,
			OrderByComparator<CommerceAccountOrganizationRel> orderByComparator)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountOrganizationRelException {

		return getPersistence().findByOrganizationId_Last(
			organizationId, orderByComparator);
	}

	/**
	 * Returns the last commerce account organization rel in the ordered set where organizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account organization rel, or <code>null</code> if a matching commerce account organization rel could not be found
	 */
	public static CommerceAccountOrganizationRel fetchByOrganizationId_Last(
		long organizationId,
		OrderByComparator<CommerceAccountOrganizationRel> orderByComparator) {

		return getPersistence().fetchByOrganizationId_Last(
			organizationId, orderByComparator);
	}

	/**
	 * Returns the commerce account organization rels before and after the current commerce account organization rel in the ordered set where organizationId = &#63;.
	 *
	 * @param commerceAccountOrganizationRelPK the primary key of the current commerce account organization rel
	 * @param organizationId the organization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce account organization rel
	 * @throws NoSuchAccountOrganizationRelException if a commerce account organization rel with the primary key could not be found
	 */
	public static CommerceAccountOrganizationRel[]
			findByOrganizationId_PrevAndNext(
				CommerceAccountOrganizationRelPK
					commerceAccountOrganizationRelPK,
				long organizationId,
				OrderByComparator<CommerceAccountOrganizationRel>
					orderByComparator)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountOrganizationRelException {

		return getPersistence().findByOrganizationId_PrevAndNext(
			commerceAccountOrganizationRelPK, organizationId,
			orderByComparator);
	}

	/**
	 * Removes all the commerce account organization rels where organizationId = &#63; from the database.
	 *
	 * @param organizationId the organization ID
	 */
	public static void removeByOrganizationId(long organizationId) {
		getPersistence().removeByOrganizationId(organizationId);
	}

	/**
	 * Returns the number of commerce account organization rels where organizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @return the number of matching commerce account organization rels
	 */
	public static int countByOrganizationId(long organizationId) {
		return getPersistence().countByOrganizationId(organizationId);
	}

	/**
	 * Caches the commerce account organization rel in the entity cache if it is enabled.
	 *
	 * @param commerceAccountOrganizationRel the commerce account organization rel
	 */
	public static void cacheResult(
		CommerceAccountOrganizationRel commerceAccountOrganizationRel) {

		getPersistence().cacheResult(commerceAccountOrganizationRel);
	}

	/**
	 * Caches the commerce account organization rels in the entity cache if it is enabled.
	 *
	 * @param commerceAccountOrganizationRels the commerce account organization rels
	 */
	public static void cacheResult(
		List<CommerceAccountOrganizationRel> commerceAccountOrganizationRels) {

		getPersistence().cacheResult(commerceAccountOrganizationRels);
	}

	/**
	 * Creates a new commerce account organization rel with the primary key. Does not add the commerce account organization rel to the database.
	 *
	 * @param commerceAccountOrganizationRelPK the primary key for the new commerce account organization rel
	 * @return the new commerce account organization rel
	 */
	public static CommerceAccountOrganizationRel create(
		CommerceAccountOrganizationRelPK commerceAccountOrganizationRelPK) {

		return getPersistence().create(commerceAccountOrganizationRelPK);
	}

	/**
	 * Removes the commerce account organization rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceAccountOrganizationRelPK the primary key of the commerce account organization rel
	 * @return the commerce account organization rel that was removed
	 * @throws NoSuchAccountOrganizationRelException if a commerce account organization rel with the primary key could not be found
	 */
	public static CommerceAccountOrganizationRel remove(
			CommerceAccountOrganizationRelPK commerceAccountOrganizationRelPK)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountOrganizationRelException {

		return getPersistence().remove(commerceAccountOrganizationRelPK);
	}

	public static CommerceAccountOrganizationRel updateImpl(
		CommerceAccountOrganizationRel commerceAccountOrganizationRel) {

		return getPersistence().updateImpl(commerceAccountOrganizationRel);
	}

	/**
	 * Returns the commerce account organization rel with the primary key or throws a <code>NoSuchAccountOrganizationRelException</code> if it could not be found.
	 *
	 * @param commerceAccountOrganizationRelPK the primary key of the commerce account organization rel
	 * @return the commerce account organization rel
	 * @throws NoSuchAccountOrganizationRelException if a commerce account organization rel with the primary key could not be found
	 */
	public static CommerceAccountOrganizationRel findByPrimaryKey(
			CommerceAccountOrganizationRelPK commerceAccountOrganizationRelPK)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountOrganizationRelException {

		return getPersistence().findByPrimaryKey(
			commerceAccountOrganizationRelPK);
	}

	/**
	 * Returns the commerce account organization rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceAccountOrganizationRelPK the primary key of the commerce account organization rel
	 * @return the commerce account organization rel, or <code>null</code> if a commerce account organization rel with the primary key could not be found
	 */
	public static CommerceAccountOrganizationRel fetchByPrimaryKey(
		CommerceAccountOrganizationRelPK commerceAccountOrganizationRelPK) {

		return getPersistence().fetchByPrimaryKey(
			commerceAccountOrganizationRelPK);
	}

	/**
	 * Returns all the commerce account organization rels.
	 *
	 * @return the commerce account organization rels
	 */
	public static List<CommerceAccountOrganizationRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce account organization rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountOrganizationRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce account organization rels
	 * @param end the upper bound of the range of commerce account organization rels (not inclusive)
	 * @return the range of commerce account organization rels
	 */
	public static List<CommerceAccountOrganizationRel> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce account organization rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountOrganizationRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce account organization rels
	 * @param end the upper bound of the range of commerce account organization rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce account organization rels
	 */
	public static List<CommerceAccountOrganizationRel> findAll(
		int start, int end,
		OrderByComparator<CommerceAccountOrganizationRel> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce account organization rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountOrganizationRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce account organization rels
	 * @param end the upper bound of the range of commerce account organization rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce account organization rels
	 */
	public static List<CommerceAccountOrganizationRel> findAll(
		int start, int end,
		OrderByComparator<CommerceAccountOrganizationRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce account organization rels from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce account organization rels.
	 *
	 * @return the number of commerce account organization rels
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static Set<String> getCompoundPKColumnNames() {
		return getPersistence().getCompoundPKColumnNames();
	}

	public static CommerceAccountOrganizationRelPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceAccountOrganizationRelPersistence,
		 CommerceAccountOrganizationRelPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceAccountOrganizationRelPersistence.class);

		ServiceTracker
			<CommerceAccountOrganizationRelPersistence,
			 CommerceAccountOrganizationRelPersistence> serviceTracker =
				new ServiceTracker
					<CommerceAccountOrganizationRelPersistence,
					 CommerceAccountOrganizationRelPersistence>(
						 bundle.getBundleContext(),
						 CommerceAccountOrganizationRelPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}
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

package com.liferay.commerce.payment.service.persistence;

import com.liferay.commerce.payment.model.CommercePaymentEntryAudit;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the commerce payment entry audit service. This utility wraps <code>com.liferay.commerce.payment.service.persistence.impl.CommercePaymentEntryAuditPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommercePaymentEntryAuditPersistence
 * @generated
 */
public class CommercePaymentEntryAuditUtil {

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
		CommercePaymentEntryAudit commercePaymentEntryAudit) {

		getPersistence().clearCache(commercePaymentEntryAudit);
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
	public static Map<Serializable, CommercePaymentEntryAudit>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommercePaymentEntryAudit> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommercePaymentEntryAudit> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommercePaymentEntryAudit> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommercePaymentEntryAudit> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommercePaymentEntryAudit update(
		CommercePaymentEntryAudit commercePaymentEntryAudit) {

		return getPersistence().update(commercePaymentEntryAudit);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommercePaymentEntryAudit update(
		CommercePaymentEntryAudit commercePaymentEntryAudit,
		ServiceContext serviceContext) {

		return getPersistence().update(
			commercePaymentEntryAudit, serviceContext);
	}

	/**
	 * Returns all the commerce payment entry audits where commercePaymentEntryId = &#63;.
	 *
	 * @param commercePaymentEntryId the commerce payment entry ID
	 * @return the matching commerce payment entry audits
	 */
	public static List<CommercePaymentEntryAudit> findByCommercePaymentEntryId(
		long commercePaymentEntryId) {

		return getPersistence().findByCommercePaymentEntryId(
			commercePaymentEntryId);
	}

	/**
	 * Returns a range of all the commerce payment entry audits where commercePaymentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentEntryAuditModelImpl</code>.
	 * </p>
	 *
	 * @param commercePaymentEntryId the commerce payment entry ID
	 * @param start the lower bound of the range of commerce payment entry audits
	 * @param end the upper bound of the range of commerce payment entry audits (not inclusive)
	 * @return the range of matching commerce payment entry audits
	 */
	public static List<CommercePaymentEntryAudit> findByCommercePaymentEntryId(
		long commercePaymentEntryId, int start, int end) {

		return getPersistence().findByCommercePaymentEntryId(
			commercePaymentEntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce payment entry audits where commercePaymentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentEntryAuditModelImpl</code>.
	 * </p>
	 *
	 * @param commercePaymentEntryId the commerce payment entry ID
	 * @param start the lower bound of the range of commerce payment entry audits
	 * @param end the upper bound of the range of commerce payment entry audits (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce payment entry audits
	 */
	public static List<CommercePaymentEntryAudit> findByCommercePaymentEntryId(
		long commercePaymentEntryId, int start, int end,
		OrderByComparator<CommercePaymentEntryAudit> orderByComparator) {

		return getPersistence().findByCommercePaymentEntryId(
			commercePaymentEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce payment entry audits where commercePaymentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentEntryAuditModelImpl</code>.
	 * </p>
	 *
	 * @param commercePaymentEntryId the commerce payment entry ID
	 * @param start the lower bound of the range of commerce payment entry audits
	 * @param end the upper bound of the range of commerce payment entry audits (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce payment entry audits
	 */
	public static List<CommercePaymentEntryAudit> findByCommercePaymentEntryId(
		long commercePaymentEntryId, int start, int end,
		OrderByComparator<CommercePaymentEntryAudit> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCommercePaymentEntryId(
			commercePaymentEntryId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce payment entry audit in the ordered set where commercePaymentEntryId = &#63;.
	 *
	 * @param commercePaymentEntryId the commerce payment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce payment entry audit
	 * @throws NoSuchPaymentEntryAuditException if a matching commerce payment entry audit could not be found
	 */
	public static CommercePaymentEntryAudit findByCommercePaymentEntryId_First(
			long commercePaymentEntryId,
			OrderByComparator<CommercePaymentEntryAudit> orderByComparator)
		throws com.liferay.commerce.payment.exception.
			NoSuchPaymentEntryAuditException {

		return getPersistence().findByCommercePaymentEntryId_First(
			commercePaymentEntryId, orderByComparator);
	}

	/**
	 * Returns the first commerce payment entry audit in the ordered set where commercePaymentEntryId = &#63;.
	 *
	 * @param commercePaymentEntryId the commerce payment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce payment entry audit, or <code>null</code> if a matching commerce payment entry audit could not be found
	 */
	public static CommercePaymentEntryAudit fetchByCommercePaymentEntryId_First(
		long commercePaymentEntryId,
		OrderByComparator<CommercePaymentEntryAudit> orderByComparator) {

		return getPersistence().fetchByCommercePaymentEntryId_First(
			commercePaymentEntryId, orderByComparator);
	}

	/**
	 * Returns the last commerce payment entry audit in the ordered set where commercePaymentEntryId = &#63;.
	 *
	 * @param commercePaymentEntryId the commerce payment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce payment entry audit
	 * @throws NoSuchPaymentEntryAuditException if a matching commerce payment entry audit could not be found
	 */
	public static CommercePaymentEntryAudit findByCommercePaymentEntryId_Last(
			long commercePaymentEntryId,
			OrderByComparator<CommercePaymentEntryAudit> orderByComparator)
		throws com.liferay.commerce.payment.exception.
			NoSuchPaymentEntryAuditException {

		return getPersistence().findByCommercePaymentEntryId_Last(
			commercePaymentEntryId, orderByComparator);
	}

	/**
	 * Returns the last commerce payment entry audit in the ordered set where commercePaymentEntryId = &#63;.
	 *
	 * @param commercePaymentEntryId the commerce payment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce payment entry audit, or <code>null</code> if a matching commerce payment entry audit could not be found
	 */
	public static CommercePaymentEntryAudit fetchByCommercePaymentEntryId_Last(
		long commercePaymentEntryId,
		OrderByComparator<CommercePaymentEntryAudit> orderByComparator) {

		return getPersistence().fetchByCommercePaymentEntryId_Last(
			commercePaymentEntryId, orderByComparator);
	}

	/**
	 * Returns the commerce payment entry audits before and after the current commerce payment entry audit in the ordered set where commercePaymentEntryId = &#63;.
	 *
	 * @param commercePaymentEntryAuditId the primary key of the current commerce payment entry audit
	 * @param commercePaymentEntryId the commerce payment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce payment entry audit
	 * @throws NoSuchPaymentEntryAuditException if a commerce payment entry audit with the primary key could not be found
	 */
	public static CommercePaymentEntryAudit[]
			findByCommercePaymentEntryId_PrevAndNext(
				long commercePaymentEntryAuditId, long commercePaymentEntryId,
				OrderByComparator<CommercePaymentEntryAudit> orderByComparator)
		throws com.liferay.commerce.payment.exception.
			NoSuchPaymentEntryAuditException {

		return getPersistence().findByCommercePaymentEntryId_PrevAndNext(
			commercePaymentEntryAuditId, commercePaymentEntryId,
			orderByComparator);
	}

	/**
	 * Returns all the commerce payment entry audits that the user has permission to view where commercePaymentEntryId = &#63;.
	 *
	 * @param commercePaymentEntryId the commerce payment entry ID
	 * @return the matching commerce payment entry audits that the user has permission to view
	 */
	public static List<CommercePaymentEntryAudit>
		filterFindByCommercePaymentEntryId(long commercePaymentEntryId) {

		return getPersistence().filterFindByCommercePaymentEntryId(
			commercePaymentEntryId);
	}

	/**
	 * Returns a range of all the commerce payment entry audits that the user has permission to view where commercePaymentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentEntryAuditModelImpl</code>.
	 * </p>
	 *
	 * @param commercePaymentEntryId the commerce payment entry ID
	 * @param start the lower bound of the range of commerce payment entry audits
	 * @param end the upper bound of the range of commerce payment entry audits (not inclusive)
	 * @return the range of matching commerce payment entry audits that the user has permission to view
	 */
	public static List<CommercePaymentEntryAudit>
		filterFindByCommercePaymentEntryId(
			long commercePaymentEntryId, int start, int end) {

		return getPersistence().filterFindByCommercePaymentEntryId(
			commercePaymentEntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce payment entry audits that the user has permissions to view where commercePaymentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentEntryAuditModelImpl</code>.
	 * </p>
	 *
	 * @param commercePaymentEntryId the commerce payment entry ID
	 * @param start the lower bound of the range of commerce payment entry audits
	 * @param end the upper bound of the range of commerce payment entry audits (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce payment entry audits that the user has permission to view
	 */
	public static List<CommercePaymentEntryAudit>
		filterFindByCommercePaymentEntryId(
			long commercePaymentEntryId, int start, int end,
			OrderByComparator<CommercePaymentEntryAudit> orderByComparator) {

		return getPersistence().filterFindByCommercePaymentEntryId(
			commercePaymentEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns the commerce payment entry audits before and after the current commerce payment entry audit in the ordered set of commerce payment entry audits that the user has permission to view where commercePaymentEntryId = &#63;.
	 *
	 * @param commercePaymentEntryAuditId the primary key of the current commerce payment entry audit
	 * @param commercePaymentEntryId the commerce payment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce payment entry audit
	 * @throws NoSuchPaymentEntryAuditException if a commerce payment entry audit with the primary key could not be found
	 */
	public static CommercePaymentEntryAudit[]
			filterFindByCommercePaymentEntryId_PrevAndNext(
				long commercePaymentEntryAuditId, long commercePaymentEntryId,
				OrderByComparator<CommercePaymentEntryAudit> orderByComparator)
		throws com.liferay.commerce.payment.exception.
			NoSuchPaymentEntryAuditException {

		return getPersistence().filterFindByCommercePaymentEntryId_PrevAndNext(
			commercePaymentEntryAuditId, commercePaymentEntryId,
			orderByComparator);
	}

	/**
	 * Removes all the commerce payment entry audits where commercePaymentEntryId = &#63; from the database.
	 *
	 * @param commercePaymentEntryId the commerce payment entry ID
	 */
	public static void removeByCommercePaymentEntryId(
		long commercePaymentEntryId) {

		getPersistence().removeByCommercePaymentEntryId(commercePaymentEntryId);
	}

	/**
	 * Returns the number of commerce payment entry audits where commercePaymentEntryId = &#63;.
	 *
	 * @param commercePaymentEntryId the commerce payment entry ID
	 * @return the number of matching commerce payment entry audits
	 */
	public static int countByCommercePaymentEntryId(
		long commercePaymentEntryId) {

		return getPersistence().countByCommercePaymentEntryId(
			commercePaymentEntryId);
	}

	/**
	 * Returns the number of commerce payment entry audits that the user has permission to view where commercePaymentEntryId = &#63;.
	 *
	 * @param commercePaymentEntryId the commerce payment entry ID
	 * @return the number of matching commerce payment entry audits that the user has permission to view
	 */
	public static int filterCountByCommercePaymentEntryId(
		long commercePaymentEntryId) {

		return getPersistence().filterCountByCommercePaymentEntryId(
			commercePaymentEntryId);
	}

	/**
	 * Caches the commerce payment entry audit in the entity cache if it is enabled.
	 *
	 * @param commercePaymentEntryAudit the commerce payment entry audit
	 */
	public static void cacheResult(
		CommercePaymentEntryAudit commercePaymentEntryAudit) {

		getPersistence().cacheResult(commercePaymentEntryAudit);
	}

	/**
	 * Caches the commerce payment entry audits in the entity cache if it is enabled.
	 *
	 * @param commercePaymentEntryAudits the commerce payment entry audits
	 */
	public static void cacheResult(
		List<CommercePaymentEntryAudit> commercePaymentEntryAudits) {

		getPersistence().cacheResult(commercePaymentEntryAudits);
	}

	/**
	 * Creates a new commerce payment entry audit with the primary key. Does not add the commerce payment entry audit to the database.
	 *
	 * @param commercePaymentEntryAuditId the primary key for the new commerce payment entry audit
	 * @return the new commerce payment entry audit
	 */
	public static CommercePaymentEntryAudit create(
		long commercePaymentEntryAuditId) {

		return getPersistence().create(commercePaymentEntryAuditId);
	}

	/**
	 * Removes the commerce payment entry audit with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commercePaymentEntryAuditId the primary key of the commerce payment entry audit
	 * @return the commerce payment entry audit that was removed
	 * @throws NoSuchPaymentEntryAuditException if a commerce payment entry audit with the primary key could not be found
	 */
	public static CommercePaymentEntryAudit remove(
			long commercePaymentEntryAuditId)
		throws com.liferay.commerce.payment.exception.
			NoSuchPaymentEntryAuditException {

		return getPersistence().remove(commercePaymentEntryAuditId);
	}

	public static CommercePaymentEntryAudit updateImpl(
		CommercePaymentEntryAudit commercePaymentEntryAudit) {

		return getPersistence().updateImpl(commercePaymentEntryAudit);
	}

	/**
	 * Returns the commerce payment entry audit with the primary key or throws a <code>NoSuchPaymentEntryAuditException</code> if it could not be found.
	 *
	 * @param commercePaymentEntryAuditId the primary key of the commerce payment entry audit
	 * @return the commerce payment entry audit
	 * @throws NoSuchPaymentEntryAuditException if a commerce payment entry audit with the primary key could not be found
	 */
	public static CommercePaymentEntryAudit findByPrimaryKey(
			long commercePaymentEntryAuditId)
		throws com.liferay.commerce.payment.exception.
			NoSuchPaymentEntryAuditException {

		return getPersistence().findByPrimaryKey(commercePaymentEntryAuditId);
	}

	/**
	 * Returns the commerce payment entry audit with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commercePaymentEntryAuditId the primary key of the commerce payment entry audit
	 * @return the commerce payment entry audit, or <code>null</code> if a commerce payment entry audit with the primary key could not be found
	 */
	public static CommercePaymentEntryAudit fetchByPrimaryKey(
		long commercePaymentEntryAuditId) {

		return getPersistence().fetchByPrimaryKey(commercePaymentEntryAuditId);
	}

	/**
	 * Returns all the commerce payment entry audits.
	 *
	 * @return the commerce payment entry audits
	 */
	public static List<CommercePaymentEntryAudit> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce payment entry audits.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentEntryAuditModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce payment entry audits
	 * @param end the upper bound of the range of commerce payment entry audits (not inclusive)
	 * @return the range of commerce payment entry audits
	 */
	public static List<CommercePaymentEntryAudit> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce payment entry audits.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentEntryAuditModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce payment entry audits
	 * @param end the upper bound of the range of commerce payment entry audits (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce payment entry audits
	 */
	public static List<CommercePaymentEntryAudit> findAll(
		int start, int end,
		OrderByComparator<CommercePaymentEntryAudit> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce payment entry audits.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentEntryAuditModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce payment entry audits
	 * @param end the upper bound of the range of commerce payment entry audits (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce payment entry audits
	 */
	public static List<CommercePaymentEntryAudit> findAll(
		int start, int end,
		OrderByComparator<CommercePaymentEntryAudit> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce payment entry audits from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce payment entry audits.
	 *
	 * @return the number of commerce payment entry audits
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommercePaymentEntryAuditPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		CommercePaymentEntryAuditPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile CommercePaymentEntryAuditPersistence _persistence;

}
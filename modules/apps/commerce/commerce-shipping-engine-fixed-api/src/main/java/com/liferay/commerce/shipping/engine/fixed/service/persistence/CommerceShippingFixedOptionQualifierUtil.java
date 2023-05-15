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

import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionQualifier;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the commerce shipping fixed option qualifier service. This utility wraps <code>com.liferay.commerce.shipping.engine.fixed.service.persistence.impl.CommerceShippingFixedOptionQualifierPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingFixedOptionQualifierPersistence
 * @generated
 */
public class CommerceShippingFixedOptionQualifierUtil {

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
		CommerceShippingFixedOptionQualifier
			commerceShippingFixedOptionQualifier) {

		getPersistence().clearCache(commerceShippingFixedOptionQualifier);
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
	public static Map<Serializable, CommerceShippingFixedOptionQualifier>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceShippingFixedOptionQualifier>
		findWithDynamicQuery(DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceShippingFixedOptionQualifier>
		findWithDynamicQuery(DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceShippingFixedOptionQualifier>
		findWithDynamicQuery(
			DynamicQuery dynamicQuery, int start, int end,
			OrderByComparator<CommerceShippingFixedOptionQualifier>
				orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceShippingFixedOptionQualifier update(
		CommerceShippingFixedOptionQualifier
			commerceShippingFixedOptionQualifier) {

		return getPersistence().update(commerceShippingFixedOptionQualifier);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceShippingFixedOptionQualifier update(
		CommerceShippingFixedOptionQualifier
			commerceShippingFixedOptionQualifier,
		ServiceContext serviceContext) {

		return getPersistence().update(
			commerceShippingFixedOptionQualifier, serviceContext);
	}

	/**
	 * Returns all the commerce shipping fixed option qualifiers where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @return the matching commerce shipping fixed option qualifiers
	 */
	public static List<CommerceShippingFixedOptionQualifier>
		findByCommerceShippingFixedOptionId(
			long commerceShippingFixedOptionId) {

		return getPersistence().findByCommerceShippingFixedOptionId(
			commerceShippingFixedOptionId);
	}

	/**
	 * Returns a range of all the commerce shipping fixed option qualifiers where commerceShippingFixedOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param start the lower bound of the range of commerce shipping fixed option qualifiers
	 * @param end the upper bound of the range of commerce shipping fixed option qualifiers (not inclusive)
	 * @return the range of matching commerce shipping fixed option qualifiers
	 */
	public static List<CommerceShippingFixedOptionQualifier>
		findByCommerceShippingFixedOptionId(
			long commerceShippingFixedOptionId, int start, int end) {

		return getPersistence().findByCommerceShippingFixedOptionId(
			commerceShippingFixedOptionId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce shipping fixed option qualifiers where commerceShippingFixedOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param start the lower bound of the range of commerce shipping fixed option qualifiers
	 * @param end the upper bound of the range of commerce shipping fixed option qualifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipping fixed option qualifiers
	 */
	public static List<CommerceShippingFixedOptionQualifier>
		findByCommerceShippingFixedOptionId(
			long commerceShippingFixedOptionId, int start, int end,
			OrderByComparator<CommerceShippingFixedOptionQualifier>
				orderByComparator) {

		return getPersistence().findByCommerceShippingFixedOptionId(
			commerceShippingFixedOptionId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce shipping fixed option qualifiers where commerceShippingFixedOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param start the lower bound of the range of commerce shipping fixed option qualifiers
	 * @param end the upper bound of the range of commerce shipping fixed option qualifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce shipping fixed option qualifiers
	 */
	public static List<CommerceShippingFixedOptionQualifier>
		findByCommerceShippingFixedOptionId(
			long commerceShippingFixedOptionId, int start, int end,
			OrderByComparator<CommerceShippingFixedOptionQualifier>
				orderByComparator,
			boolean useFinderCache) {

		return getPersistence().findByCommerceShippingFixedOptionId(
			commerceShippingFixedOptionId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce shipping fixed option qualifier in the ordered set where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping fixed option qualifier
	 * @throws NoSuchShippingFixedOptionQualifierException if a matching commerce shipping fixed option qualifier could not be found
	 */
	public static CommerceShippingFixedOptionQualifier
			findByCommerceShippingFixedOptionId_First(
				long commerceShippingFixedOptionId,
				OrderByComparator<CommerceShippingFixedOptionQualifier>
					orderByComparator)
		throws com.liferay.commerce.shipping.engine.fixed.exception.
			NoSuchShippingFixedOptionQualifierException {

		return getPersistence().findByCommerceShippingFixedOptionId_First(
			commerceShippingFixedOptionId, orderByComparator);
	}

	/**
	 * Returns the first commerce shipping fixed option qualifier in the ordered set where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping fixed option qualifier, or <code>null</code> if a matching commerce shipping fixed option qualifier could not be found
	 */
	public static CommerceShippingFixedOptionQualifier
		fetchByCommerceShippingFixedOptionId_First(
			long commerceShippingFixedOptionId,
			OrderByComparator<CommerceShippingFixedOptionQualifier>
				orderByComparator) {

		return getPersistence().fetchByCommerceShippingFixedOptionId_First(
			commerceShippingFixedOptionId, orderByComparator);
	}

	/**
	 * Returns the last commerce shipping fixed option qualifier in the ordered set where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping fixed option qualifier
	 * @throws NoSuchShippingFixedOptionQualifierException if a matching commerce shipping fixed option qualifier could not be found
	 */
	public static CommerceShippingFixedOptionQualifier
			findByCommerceShippingFixedOptionId_Last(
				long commerceShippingFixedOptionId,
				OrderByComparator<CommerceShippingFixedOptionQualifier>
					orderByComparator)
		throws com.liferay.commerce.shipping.engine.fixed.exception.
			NoSuchShippingFixedOptionQualifierException {

		return getPersistence().findByCommerceShippingFixedOptionId_Last(
			commerceShippingFixedOptionId, orderByComparator);
	}

	/**
	 * Returns the last commerce shipping fixed option qualifier in the ordered set where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping fixed option qualifier, or <code>null</code> if a matching commerce shipping fixed option qualifier could not be found
	 */
	public static CommerceShippingFixedOptionQualifier
		fetchByCommerceShippingFixedOptionId_Last(
			long commerceShippingFixedOptionId,
			OrderByComparator<CommerceShippingFixedOptionQualifier>
				orderByComparator) {

		return getPersistence().fetchByCommerceShippingFixedOptionId_Last(
			commerceShippingFixedOptionId, orderByComparator);
	}

	/**
	 * Returns the commerce shipping fixed option qualifiers before and after the current commerce shipping fixed option qualifier in the ordered set where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionQualifierId the primary key of the current commerce shipping fixed option qualifier
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce shipping fixed option qualifier
	 * @throws NoSuchShippingFixedOptionQualifierException if a commerce shipping fixed option qualifier with the primary key could not be found
	 */
	public static CommerceShippingFixedOptionQualifier[]
			findByCommerceShippingFixedOptionId_PrevAndNext(
				long commerceShippingFixedOptionQualifierId,
				long commerceShippingFixedOptionId,
				OrderByComparator<CommerceShippingFixedOptionQualifier>
					orderByComparator)
		throws com.liferay.commerce.shipping.engine.fixed.exception.
			NoSuchShippingFixedOptionQualifierException {

		return getPersistence().findByCommerceShippingFixedOptionId_PrevAndNext(
			commerceShippingFixedOptionQualifierId,
			commerceShippingFixedOptionId, orderByComparator);
	}

	/**
	 * Removes all the commerce shipping fixed option qualifiers where commerceShippingFixedOptionId = &#63; from the database.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 */
	public static void removeByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId) {

		getPersistence().removeByCommerceShippingFixedOptionId(
			commerceShippingFixedOptionId);
	}

	/**
	 * Returns the number of commerce shipping fixed option qualifiers where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @return the number of matching commerce shipping fixed option qualifiers
	 */
	public static int countByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId) {

		return getPersistence().countByCommerceShippingFixedOptionId(
			commerceShippingFixedOptionId);
	}

	/**
	 * Returns all the commerce shipping fixed option qualifiers where classNameId = &#63; and commerceShippingFixedOptionId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @return the matching commerce shipping fixed option qualifiers
	 */
	public static List<CommerceShippingFixedOptionQualifier> findByC_C(
		long classNameId, long commerceShippingFixedOptionId) {

		return getPersistence().findByC_C(
			classNameId, commerceShippingFixedOptionId);
	}

	/**
	 * Returns a range of all the commerce shipping fixed option qualifiers where classNameId = &#63; and commerceShippingFixedOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param start the lower bound of the range of commerce shipping fixed option qualifiers
	 * @param end the upper bound of the range of commerce shipping fixed option qualifiers (not inclusive)
	 * @return the range of matching commerce shipping fixed option qualifiers
	 */
	public static List<CommerceShippingFixedOptionQualifier> findByC_C(
		long classNameId, long commerceShippingFixedOptionId, int start,
		int end) {

		return getPersistence().findByC_C(
			classNameId, commerceShippingFixedOptionId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce shipping fixed option qualifiers where classNameId = &#63; and commerceShippingFixedOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param start the lower bound of the range of commerce shipping fixed option qualifiers
	 * @param end the upper bound of the range of commerce shipping fixed option qualifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipping fixed option qualifiers
	 */
	public static List<CommerceShippingFixedOptionQualifier> findByC_C(
		long classNameId, long commerceShippingFixedOptionId, int start,
		int end,
		OrderByComparator<CommerceShippingFixedOptionQualifier>
			orderByComparator) {

		return getPersistence().findByC_C(
			classNameId, commerceShippingFixedOptionId, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce shipping fixed option qualifiers where classNameId = &#63; and commerceShippingFixedOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param start the lower bound of the range of commerce shipping fixed option qualifiers
	 * @param end the upper bound of the range of commerce shipping fixed option qualifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce shipping fixed option qualifiers
	 */
	public static List<CommerceShippingFixedOptionQualifier> findByC_C(
		long classNameId, long commerceShippingFixedOptionId, int start,
		int end,
		OrderByComparator<CommerceShippingFixedOptionQualifier>
			orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_C(
			classNameId, commerceShippingFixedOptionId, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce shipping fixed option qualifier in the ordered set where classNameId = &#63; and commerceShippingFixedOptionId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping fixed option qualifier
	 * @throws NoSuchShippingFixedOptionQualifierException if a matching commerce shipping fixed option qualifier could not be found
	 */
	public static CommerceShippingFixedOptionQualifier findByC_C_First(
			long classNameId, long commerceShippingFixedOptionId,
			OrderByComparator<CommerceShippingFixedOptionQualifier>
				orderByComparator)
		throws com.liferay.commerce.shipping.engine.fixed.exception.
			NoSuchShippingFixedOptionQualifierException {

		return getPersistence().findByC_C_First(
			classNameId, commerceShippingFixedOptionId, orderByComparator);
	}

	/**
	 * Returns the first commerce shipping fixed option qualifier in the ordered set where classNameId = &#63; and commerceShippingFixedOptionId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping fixed option qualifier, or <code>null</code> if a matching commerce shipping fixed option qualifier could not be found
	 */
	public static CommerceShippingFixedOptionQualifier fetchByC_C_First(
		long classNameId, long commerceShippingFixedOptionId,
		OrderByComparator<CommerceShippingFixedOptionQualifier>
			orderByComparator) {

		return getPersistence().fetchByC_C_First(
			classNameId, commerceShippingFixedOptionId, orderByComparator);
	}

	/**
	 * Returns the last commerce shipping fixed option qualifier in the ordered set where classNameId = &#63; and commerceShippingFixedOptionId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping fixed option qualifier
	 * @throws NoSuchShippingFixedOptionQualifierException if a matching commerce shipping fixed option qualifier could not be found
	 */
	public static CommerceShippingFixedOptionQualifier findByC_C_Last(
			long classNameId, long commerceShippingFixedOptionId,
			OrderByComparator<CommerceShippingFixedOptionQualifier>
				orderByComparator)
		throws com.liferay.commerce.shipping.engine.fixed.exception.
			NoSuchShippingFixedOptionQualifierException {

		return getPersistence().findByC_C_Last(
			classNameId, commerceShippingFixedOptionId, orderByComparator);
	}

	/**
	 * Returns the last commerce shipping fixed option qualifier in the ordered set where classNameId = &#63; and commerceShippingFixedOptionId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping fixed option qualifier, or <code>null</code> if a matching commerce shipping fixed option qualifier could not be found
	 */
	public static CommerceShippingFixedOptionQualifier fetchByC_C_Last(
		long classNameId, long commerceShippingFixedOptionId,
		OrderByComparator<CommerceShippingFixedOptionQualifier>
			orderByComparator) {

		return getPersistence().fetchByC_C_Last(
			classNameId, commerceShippingFixedOptionId, orderByComparator);
	}

	/**
	 * Returns the commerce shipping fixed option qualifiers before and after the current commerce shipping fixed option qualifier in the ordered set where classNameId = &#63; and commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionQualifierId the primary key of the current commerce shipping fixed option qualifier
	 * @param classNameId the class name ID
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce shipping fixed option qualifier
	 * @throws NoSuchShippingFixedOptionQualifierException if a commerce shipping fixed option qualifier with the primary key could not be found
	 */
	public static CommerceShippingFixedOptionQualifier[] findByC_C_PrevAndNext(
			long commerceShippingFixedOptionQualifierId, long classNameId,
			long commerceShippingFixedOptionId,
			OrderByComparator<CommerceShippingFixedOptionQualifier>
				orderByComparator)
		throws com.liferay.commerce.shipping.engine.fixed.exception.
			NoSuchShippingFixedOptionQualifierException {

		return getPersistence().findByC_C_PrevAndNext(
			commerceShippingFixedOptionQualifierId, classNameId,
			commerceShippingFixedOptionId, orderByComparator);
	}

	/**
	 * Removes all the commerce shipping fixed option qualifiers where classNameId = &#63; and commerceShippingFixedOptionId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 */
	public static void removeByC_C(
		long classNameId, long commerceShippingFixedOptionId) {

		getPersistence().removeByC_C(
			classNameId, commerceShippingFixedOptionId);
	}

	/**
	 * Returns the number of commerce shipping fixed option qualifiers where classNameId = &#63; and commerceShippingFixedOptionId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @return the number of matching commerce shipping fixed option qualifiers
	 */
	public static int countByC_C(
		long classNameId, long commerceShippingFixedOptionId) {

		return getPersistence().countByC_C(
			classNameId, commerceShippingFixedOptionId);
	}

	/**
	 * Returns the commerce shipping fixed option qualifier where classNameId = &#63; and classPK = &#63; and commerceShippingFixedOptionId = &#63; or throws a <code>NoSuchShippingFixedOptionQualifierException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @return the matching commerce shipping fixed option qualifier
	 * @throws NoSuchShippingFixedOptionQualifierException if a matching commerce shipping fixed option qualifier could not be found
	 */
	public static CommerceShippingFixedOptionQualifier findByC_C_C(
			long classNameId, long classPK, long commerceShippingFixedOptionId)
		throws com.liferay.commerce.shipping.engine.fixed.exception.
			NoSuchShippingFixedOptionQualifierException {

		return getPersistence().findByC_C_C(
			classNameId, classPK, commerceShippingFixedOptionId);
	}

	/**
	 * Returns the commerce shipping fixed option qualifier where classNameId = &#63; and classPK = &#63; and commerceShippingFixedOptionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @return the matching commerce shipping fixed option qualifier, or <code>null</code> if a matching commerce shipping fixed option qualifier could not be found
	 */
	public static CommerceShippingFixedOptionQualifier fetchByC_C_C(
		long classNameId, long classPK, long commerceShippingFixedOptionId) {

		return getPersistence().fetchByC_C_C(
			classNameId, classPK, commerceShippingFixedOptionId);
	}

	/**
	 * Returns the commerce shipping fixed option qualifier where classNameId = &#63; and classPK = &#63; and commerceShippingFixedOptionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce shipping fixed option qualifier, or <code>null</code> if a matching commerce shipping fixed option qualifier could not be found
	 */
	public static CommerceShippingFixedOptionQualifier fetchByC_C_C(
		long classNameId, long classPK, long commerceShippingFixedOptionId,
		boolean useFinderCache) {

		return getPersistence().fetchByC_C_C(
			classNameId, classPK, commerceShippingFixedOptionId,
			useFinderCache);
	}

	/**
	 * Removes the commerce shipping fixed option qualifier where classNameId = &#63; and classPK = &#63; and commerceShippingFixedOptionId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @return the commerce shipping fixed option qualifier that was removed
	 */
	public static CommerceShippingFixedOptionQualifier removeByC_C_C(
			long classNameId, long classPK, long commerceShippingFixedOptionId)
		throws com.liferay.commerce.shipping.engine.fixed.exception.
			NoSuchShippingFixedOptionQualifierException {

		return getPersistence().removeByC_C_C(
			classNameId, classPK, commerceShippingFixedOptionId);
	}

	/**
	 * Returns the number of commerce shipping fixed option qualifiers where classNameId = &#63; and classPK = &#63; and commerceShippingFixedOptionId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @return the number of matching commerce shipping fixed option qualifiers
	 */
	public static int countByC_C_C(
		long classNameId, long classPK, long commerceShippingFixedOptionId) {

		return getPersistence().countByC_C_C(
			classNameId, classPK, commerceShippingFixedOptionId);
	}

	/**
	 * Caches the commerce shipping fixed option qualifier in the entity cache if it is enabled.
	 *
	 * @param commerceShippingFixedOptionQualifier the commerce shipping fixed option qualifier
	 */
	public static void cacheResult(
		CommerceShippingFixedOptionQualifier
			commerceShippingFixedOptionQualifier) {

		getPersistence().cacheResult(commerceShippingFixedOptionQualifier);
	}

	/**
	 * Caches the commerce shipping fixed option qualifiers in the entity cache if it is enabled.
	 *
	 * @param commerceShippingFixedOptionQualifiers the commerce shipping fixed option qualifiers
	 */
	public static void cacheResult(
		List<CommerceShippingFixedOptionQualifier>
			commerceShippingFixedOptionQualifiers) {

		getPersistence().cacheResult(commerceShippingFixedOptionQualifiers);
	}

	/**
	 * Creates a new commerce shipping fixed option qualifier with the primary key. Does not add the commerce shipping fixed option qualifier to the database.
	 *
	 * @param commerceShippingFixedOptionQualifierId the primary key for the new commerce shipping fixed option qualifier
	 * @return the new commerce shipping fixed option qualifier
	 */
	public static CommerceShippingFixedOptionQualifier create(
		long commerceShippingFixedOptionQualifierId) {

		return getPersistence().create(commerceShippingFixedOptionQualifierId);
	}

	/**
	 * Removes the commerce shipping fixed option qualifier with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceShippingFixedOptionQualifierId the primary key of the commerce shipping fixed option qualifier
	 * @return the commerce shipping fixed option qualifier that was removed
	 * @throws NoSuchShippingFixedOptionQualifierException if a commerce shipping fixed option qualifier with the primary key could not be found
	 */
	public static CommerceShippingFixedOptionQualifier remove(
			long commerceShippingFixedOptionQualifierId)
		throws com.liferay.commerce.shipping.engine.fixed.exception.
			NoSuchShippingFixedOptionQualifierException {

		return getPersistence().remove(commerceShippingFixedOptionQualifierId);
	}

	public static CommerceShippingFixedOptionQualifier updateImpl(
		CommerceShippingFixedOptionQualifier
			commerceShippingFixedOptionQualifier) {

		return getPersistence().updateImpl(
			commerceShippingFixedOptionQualifier);
	}

	/**
	 * Returns the commerce shipping fixed option qualifier with the primary key or throws a <code>NoSuchShippingFixedOptionQualifierException</code> if it could not be found.
	 *
	 * @param commerceShippingFixedOptionQualifierId the primary key of the commerce shipping fixed option qualifier
	 * @return the commerce shipping fixed option qualifier
	 * @throws NoSuchShippingFixedOptionQualifierException if a commerce shipping fixed option qualifier with the primary key could not be found
	 */
	public static CommerceShippingFixedOptionQualifier findByPrimaryKey(
			long commerceShippingFixedOptionQualifierId)
		throws com.liferay.commerce.shipping.engine.fixed.exception.
			NoSuchShippingFixedOptionQualifierException {

		return getPersistence().findByPrimaryKey(
			commerceShippingFixedOptionQualifierId);
	}

	/**
	 * Returns the commerce shipping fixed option qualifier with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceShippingFixedOptionQualifierId the primary key of the commerce shipping fixed option qualifier
	 * @return the commerce shipping fixed option qualifier, or <code>null</code> if a commerce shipping fixed option qualifier with the primary key could not be found
	 */
	public static CommerceShippingFixedOptionQualifier fetchByPrimaryKey(
		long commerceShippingFixedOptionQualifierId) {

		return getPersistence().fetchByPrimaryKey(
			commerceShippingFixedOptionQualifierId);
	}

	/**
	 * Returns all the commerce shipping fixed option qualifiers.
	 *
	 * @return the commerce shipping fixed option qualifiers
	 */
	public static List<CommerceShippingFixedOptionQualifier> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce shipping fixed option qualifiers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping fixed option qualifiers
	 * @param end the upper bound of the range of commerce shipping fixed option qualifiers (not inclusive)
	 * @return the range of commerce shipping fixed option qualifiers
	 */
	public static List<CommerceShippingFixedOptionQualifier> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce shipping fixed option qualifiers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping fixed option qualifiers
	 * @param end the upper bound of the range of commerce shipping fixed option qualifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce shipping fixed option qualifiers
	 */
	public static List<CommerceShippingFixedOptionQualifier> findAll(
		int start, int end,
		OrderByComparator<CommerceShippingFixedOptionQualifier>
			orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce shipping fixed option qualifiers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping fixed option qualifiers
	 * @param end the upper bound of the range of commerce shipping fixed option qualifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce shipping fixed option qualifiers
	 */
	public static List<CommerceShippingFixedOptionQualifier> findAll(
		int start, int end,
		OrderByComparator<CommerceShippingFixedOptionQualifier>
			orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce shipping fixed option qualifiers from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce shipping fixed option qualifiers.
	 *
	 * @return the number of commerce shipping fixed option qualifiers
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceShippingFixedOptionQualifierPersistence
		getPersistence() {

		return _persistence;
	}

	public static void setPersistence(
		CommerceShippingFixedOptionQualifierPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile CommerceShippingFixedOptionQualifierPersistence
		_persistence;

}
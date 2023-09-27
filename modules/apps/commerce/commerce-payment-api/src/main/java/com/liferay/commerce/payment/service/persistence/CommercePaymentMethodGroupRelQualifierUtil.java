/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.service.persistence;

import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRelQualifier;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the commerce payment method group rel qualifier service. This utility wraps <code>com.liferay.commerce.payment.service.persistence.impl.CommercePaymentMethodGroupRelQualifierPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommercePaymentMethodGroupRelQualifierPersistence
 * @generated
 */
public class CommercePaymentMethodGroupRelQualifierUtil {

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
		CommercePaymentMethodGroupRelQualifier
			commercePaymentMethodGroupRelQualifier) {

		getPersistence().clearCache(commercePaymentMethodGroupRelQualifier);
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
	public static Map<Serializable, CommercePaymentMethodGroupRelQualifier>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommercePaymentMethodGroupRelQualifier>
		findWithDynamicQuery(DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommercePaymentMethodGroupRelQualifier>
		findWithDynamicQuery(DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommercePaymentMethodGroupRelQualifier>
		findWithDynamicQuery(
			DynamicQuery dynamicQuery, int start, int end,
			OrderByComparator<CommercePaymentMethodGroupRelQualifier>
				orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommercePaymentMethodGroupRelQualifier update(
		CommercePaymentMethodGroupRelQualifier
			commercePaymentMethodGroupRelQualifier) {

		return getPersistence().update(commercePaymentMethodGroupRelQualifier);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommercePaymentMethodGroupRelQualifier update(
		CommercePaymentMethodGroupRelQualifier
			commercePaymentMethodGroupRelQualifier,
		ServiceContext serviceContext) {

		return getPersistence().update(
			commercePaymentMethodGroupRelQualifier, serviceContext);
	}

	/**
	 * Returns all the commerce payment method group rel qualifiers where commercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @return the matching commerce payment method group rel qualifiers
	 */
	public static List<CommercePaymentMethodGroupRelQualifier>
		findByCommercePaymentMethodGroupRelId(
			long commercePaymentMethodGroupRelId) {

		return getPersistence().findByCommercePaymentMethodGroupRelId(
			commercePaymentMethodGroupRelId);
	}

	/**
	 * Returns a range of all the commerce payment method group rel qualifiers where commercePaymentMethodGroupRelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentMethodGroupRelQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param start the lower bound of the range of commerce payment method group rel qualifiers
	 * @param end the upper bound of the range of commerce payment method group rel qualifiers (not inclusive)
	 * @return the range of matching commerce payment method group rel qualifiers
	 */
	public static List<CommercePaymentMethodGroupRelQualifier>
		findByCommercePaymentMethodGroupRelId(
			long commercePaymentMethodGroupRelId, int start, int end) {

		return getPersistence().findByCommercePaymentMethodGroupRelId(
			commercePaymentMethodGroupRelId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce payment method group rel qualifiers where commercePaymentMethodGroupRelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentMethodGroupRelQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param start the lower bound of the range of commerce payment method group rel qualifiers
	 * @param end the upper bound of the range of commerce payment method group rel qualifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce payment method group rel qualifiers
	 */
	public static List<CommercePaymentMethodGroupRelQualifier>
		findByCommercePaymentMethodGroupRelId(
			long commercePaymentMethodGroupRelId, int start, int end,
			OrderByComparator<CommercePaymentMethodGroupRelQualifier>
				orderByComparator) {

		return getPersistence().findByCommercePaymentMethodGroupRelId(
			commercePaymentMethodGroupRelId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce payment method group rel qualifiers where commercePaymentMethodGroupRelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentMethodGroupRelQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param start the lower bound of the range of commerce payment method group rel qualifiers
	 * @param end the upper bound of the range of commerce payment method group rel qualifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce payment method group rel qualifiers
	 */
	public static List<CommercePaymentMethodGroupRelQualifier>
		findByCommercePaymentMethodGroupRelId(
			long commercePaymentMethodGroupRelId, int start, int end,
			OrderByComparator<CommercePaymentMethodGroupRelQualifier>
				orderByComparator,
			boolean useFinderCache) {

		return getPersistence().findByCommercePaymentMethodGroupRelId(
			commercePaymentMethodGroupRelId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce payment method group rel qualifier in the ordered set where commercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce payment method group rel qualifier
	 * @throws NoSuchPaymentMethodGroupRelQualifierException if a matching commerce payment method group rel qualifier could not be found
	 */
	public static CommercePaymentMethodGroupRelQualifier
			findByCommercePaymentMethodGroupRelId_First(
				long commercePaymentMethodGroupRelId,
				OrderByComparator<CommercePaymentMethodGroupRelQualifier>
					orderByComparator)
		throws com.liferay.commerce.payment.exception.
			NoSuchPaymentMethodGroupRelQualifierException {

		return getPersistence().findByCommercePaymentMethodGroupRelId_First(
			commercePaymentMethodGroupRelId, orderByComparator);
	}

	/**
	 * Returns the first commerce payment method group rel qualifier in the ordered set where commercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce payment method group rel qualifier, or <code>null</code> if a matching commerce payment method group rel qualifier could not be found
	 */
	public static CommercePaymentMethodGroupRelQualifier
		fetchByCommercePaymentMethodGroupRelId_First(
			long commercePaymentMethodGroupRelId,
			OrderByComparator<CommercePaymentMethodGroupRelQualifier>
				orderByComparator) {

		return getPersistence().fetchByCommercePaymentMethodGroupRelId_First(
			commercePaymentMethodGroupRelId, orderByComparator);
	}

	/**
	 * Returns the last commerce payment method group rel qualifier in the ordered set where commercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce payment method group rel qualifier
	 * @throws NoSuchPaymentMethodGroupRelQualifierException if a matching commerce payment method group rel qualifier could not be found
	 */
	public static CommercePaymentMethodGroupRelQualifier
			findByCommercePaymentMethodGroupRelId_Last(
				long commercePaymentMethodGroupRelId,
				OrderByComparator<CommercePaymentMethodGroupRelQualifier>
					orderByComparator)
		throws com.liferay.commerce.payment.exception.
			NoSuchPaymentMethodGroupRelQualifierException {

		return getPersistence().findByCommercePaymentMethodGroupRelId_Last(
			commercePaymentMethodGroupRelId, orderByComparator);
	}

	/**
	 * Returns the last commerce payment method group rel qualifier in the ordered set where commercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce payment method group rel qualifier, or <code>null</code> if a matching commerce payment method group rel qualifier could not be found
	 */
	public static CommercePaymentMethodGroupRelQualifier
		fetchByCommercePaymentMethodGroupRelId_Last(
			long commercePaymentMethodGroupRelId,
			OrderByComparator<CommercePaymentMethodGroupRelQualifier>
				orderByComparator) {

		return getPersistence().fetchByCommercePaymentMethodGroupRelId_Last(
			commercePaymentMethodGroupRelId, orderByComparator);
	}

	/**
	 * Returns the commerce payment method group rel qualifiers before and after the current commerce payment method group rel qualifier in the ordered set where commercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param commercePaymentMethodGroupRelQualifierId the primary key of the current commerce payment method group rel qualifier
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce payment method group rel qualifier
	 * @throws NoSuchPaymentMethodGroupRelQualifierException if a commerce payment method group rel qualifier with the primary key could not be found
	 */
	public static CommercePaymentMethodGroupRelQualifier[]
			findByCommercePaymentMethodGroupRelId_PrevAndNext(
				long commercePaymentMethodGroupRelQualifierId,
				long commercePaymentMethodGroupRelId,
				OrderByComparator<CommercePaymentMethodGroupRelQualifier>
					orderByComparator)
		throws com.liferay.commerce.payment.exception.
			NoSuchPaymentMethodGroupRelQualifierException {

		return getPersistence().
			findByCommercePaymentMethodGroupRelId_PrevAndNext(
				commercePaymentMethodGroupRelQualifierId,
				commercePaymentMethodGroupRelId, orderByComparator);
	}

	/**
	 * Removes all the commerce payment method group rel qualifiers where commercePaymentMethodGroupRelId = &#63; from the database.
	 *
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 */
	public static void removeByCommercePaymentMethodGroupRelId(
		long commercePaymentMethodGroupRelId) {

		getPersistence().removeByCommercePaymentMethodGroupRelId(
			commercePaymentMethodGroupRelId);
	}

	/**
	 * Returns the number of commerce payment method group rel qualifiers where commercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @return the number of matching commerce payment method group rel qualifiers
	 */
	public static int countByCommercePaymentMethodGroupRelId(
		long commercePaymentMethodGroupRelId) {

		return getPersistence().countByCommercePaymentMethodGroupRelId(
			commercePaymentMethodGroupRelId);
	}

	/**
	 * Returns all the commerce payment method group rel qualifiers where classNameId = &#63; and commercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @return the matching commerce payment method group rel qualifiers
	 */
	public static List<CommercePaymentMethodGroupRelQualifier> findByC_C(
		long classNameId, long commercePaymentMethodGroupRelId) {

		return getPersistence().findByC_C(
			classNameId, commercePaymentMethodGroupRelId);
	}

	/**
	 * Returns a range of all the commerce payment method group rel qualifiers where classNameId = &#63; and commercePaymentMethodGroupRelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentMethodGroupRelQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param start the lower bound of the range of commerce payment method group rel qualifiers
	 * @param end the upper bound of the range of commerce payment method group rel qualifiers (not inclusive)
	 * @return the range of matching commerce payment method group rel qualifiers
	 */
	public static List<CommercePaymentMethodGroupRelQualifier> findByC_C(
		long classNameId, long commercePaymentMethodGroupRelId, int start,
		int end) {

		return getPersistence().findByC_C(
			classNameId, commercePaymentMethodGroupRelId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce payment method group rel qualifiers where classNameId = &#63; and commercePaymentMethodGroupRelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentMethodGroupRelQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param start the lower bound of the range of commerce payment method group rel qualifiers
	 * @param end the upper bound of the range of commerce payment method group rel qualifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce payment method group rel qualifiers
	 */
	public static List<CommercePaymentMethodGroupRelQualifier> findByC_C(
		long classNameId, long commercePaymentMethodGroupRelId, int start,
		int end,
		OrderByComparator<CommercePaymentMethodGroupRelQualifier>
			orderByComparator) {

		return getPersistence().findByC_C(
			classNameId, commercePaymentMethodGroupRelId, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce payment method group rel qualifiers where classNameId = &#63; and commercePaymentMethodGroupRelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentMethodGroupRelQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param start the lower bound of the range of commerce payment method group rel qualifiers
	 * @param end the upper bound of the range of commerce payment method group rel qualifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce payment method group rel qualifiers
	 */
	public static List<CommercePaymentMethodGroupRelQualifier> findByC_C(
		long classNameId, long commercePaymentMethodGroupRelId, int start,
		int end,
		OrderByComparator<CommercePaymentMethodGroupRelQualifier>
			orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_C(
			classNameId, commercePaymentMethodGroupRelId, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce payment method group rel qualifier in the ordered set where classNameId = &#63; and commercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce payment method group rel qualifier
	 * @throws NoSuchPaymentMethodGroupRelQualifierException if a matching commerce payment method group rel qualifier could not be found
	 */
	public static CommercePaymentMethodGroupRelQualifier findByC_C_First(
			long classNameId, long commercePaymentMethodGroupRelId,
			OrderByComparator<CommercePaymentMethodGroupRelQualifier>
				orderByComparator)
		throws com.liferay.commerce.payment.exception.
			NoSuchPaymentMethodGroupRelQualifierException {

		return getPersistence().findByC_C_First(
			classNameId, commercePaymentMethodGroupRelId, orderByComparator);
	}

	/**
	 * Returns the first commerce payment method group rel qualifier in the ordered set where classNameId = &#63; and commercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce payment method group rel qualifier, or <code>null</code> if a matching commerce payment method group rel qualifier could not be found
	 */
	public static CommercePaymentMethodGroupRelQualifier fetchByC_C_First(
		long classNameId, long commercePaymentMethodGroupRelId,
		OrderByComparator<CommercePaymentMethodGroupRelQualifier>
			orderByComparator) {

		return getPersistence().fetchByC_C_First(
			classNameId, commercePaymentMethodGroupRelId, orderByComparator);
	}

	/**
	 * Returns the last commerce payment method group rel qualifier in the ordered set where classNameId = &#63; and commercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce payment method group rel qualifier
	 * @throws NoSuchPaymentMethodGroupRelQualifierException if a matching commerce payment method group rel qualifier could not be found
	 */
	public static CommercePaymentMethodGroupRelQualifier findByC_C_Last(
			long classNameId, long commercePaymentMethodGroupRelId,
			OrderByComparator<CommercePaymentMethodGroupRelQualifier>
				orderByComparator)
		throws com.liferay.commerce.payment.exception.
			NoSuchPaymentMethodGroupRelQualifierException {

		return getPersistence().findByC_C_Last(
			classNameId, commercePaymentMethodGroupRelId, orderByComparator);
	}

	/**
	 * Returns the last commerce payment method group rel qualifier in the ordered set where classNameId = &#63; and commercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce payment method group rel qualifier, or <code>null</code> if a matching commerce payment method group rel qualifier could not be found
	 */
	public static CommercePaymentMethodGroupRelQualifier fetchByC_C_Last(
		long classNameId, long commercePaymentMethodGroupRelId,
		OrderByComparator<CommercePaymentMethodGroupRelQualifier>
			orderByComparator) {

		return getPersistence().fetchByC_C_Last(
			classNameId, commercePaymentMethodGroupRelId, orderByComparator);
	}

	/**
	 * Returns the commerce payment method group rel qualifiers before and after the current commerce payment method group rel qualifier in the ordered set where classNameId = &#63; and commercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param commercePaymentMethodGroupRelQualifierId the primary key of the current commerce payment method group rel qualifier
	 * @param classNameId the class name ID
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce payment method group rel qualifier
	 * @throws NoSuchPaymentMethodGroupRelQualifierException if a commerce payment method group rel qualifier with the primary key could not be found
	 */
	public static CommercePaymentMethodGroupRelQualifier[]
			findByC_C_PrevAndNext(
				long commercePaymentMethodGroupRelQualifierId, long classNameId,
				long commercePaymentMethodGroupRelId,
				OrderByComparator<CommercePaymentMethodGroupRelQualifier>
					orderByComparator)
		throws com.liferay.commerce.payment.exception.
			NoSuchPaymentMethodGroupRelQualifierException {

		return getPersistence().findByC_C_PrevAndNext(
			commercePaymentMethodGroupRelQualifierId, classNameId,
			commercePaymentMethodGroupRelId, orderByComparator);
	}

	/**
	 * Removes all the commerce payment method group rel qualifiers where classNameId = &#63; and commercePaymentMethodGroupRelId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 */
	public static void removeByC_C(
		long classNameId, long commercePaymentMethodGroupRelId) {

		getPersistence().removeByC_C(
			classNameId, commercePaymentMethodGroupRelId);
	}

	/**
	 * Returns the number of commerce payment method group rel qualifiers where classNameId = &#63; and commercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @return the number of matching commerce payment method group rel qualifiers
	 */
	public static int countByC_C(
		long classNameId, long commercePaymentMethodGroupRelId) {

		return getPersistence().countByC_C(
			classNameId, commercePaymentMethodGroupRelId);
	}

	/**
	 * Returns the commerce payment method group rel qualifier where classNameId = &#63; and classPK = &#63; and commercePaymentMethodGroupRelId = &#63; or throws a <code>NoSuchPaymentMethodGroupRelQualifierException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @return the matching commerce payment method group rel qualifier
	 * @throws NoSuchPaymentMethodGroupRelQualifierException if a matching commerce payment method group rel qualifier could not be found
	 */
	public static CommercePaymentMethodGroupRelQualifier findByC_C_C(
			long classNameId, long classPK,
			long commercePaymentMethodGroupRelId)
		throws com.liferay.commerce.payment.exception.
			NoSuchPaymentMethodGroupRelQualifierException {

		return getPersistence().findByC_C_C(
			classNameId, classPK, commercePaymentMethodGroupRelId);
	}

	/**
	 * Returns the commerce payment method group rel qualifier where classNameId = &#63; and classPK = &#63; and commercePaymentMethodGroupRelId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @return the matching commerce payment method group rel qualifier, or <code>null</code> if a matching commerce payment method group rel qualifier could not be found
	 */
	public static CommercePaymentMethodGroupRelQualifier fetchByC_C_C(
		long classNameId, long classPK, long commercePaymentMethodGroupRelId) {

		return getPersistence().fetchByC_C_C(
			classNameId, classPK, commercePaymentMethodGroupRelId);
	}

	/**
	 * Returns the commerce payment method group rel qualifier where classNameId = &#63; and classPK = &#63; and commercePaymentMethodGroupRelId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce payment method group rel qualifier, or <code>null</code> if a matching commerce payment method group rel qualifier could not be found
	 */
	public static CommercePaymentMethodGroupRelQualifier fetchByC_C_C(
		long classNameId, long classPK, long commercePaymentMethodGroupRelId,
		boolean useFinderCache) {

		return getPersistence().fetchByC_C_C(
			classNameId, classPK, commercePaymentMethodGroupRelId,
			useFinderCache);
	}

	/**
	 * Removes the commerce payment method group rel qualifier where classNameId = &#63; and classPK = &#63; and commercePaymentMethodGroupRelId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @return the commerce payment method group rel qualifier that was removed
	 */
	public static CommercePaymentMethodGroupRelQualifier removeByC_C_C(
			long classNameId, long classPK,
			long commercePaymentMethodGroupRelId)
		throws com.liferay.commerce.payment.exception.
			NoSuchPaymentMethodGroupRelQualifierException {

		return getPersistence().removeByC_C_C(
			classNameId, classPK, commercePaymentMethodGroupRelId);
	}

	/**
	 * Returns the number of commerce payment method group rel qualifiers where classNameId = &#63; and classPK = &#63; and commercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @return the number of matching commerce payment method group rel qualifiers
	 */
	public static int countByC_C_C(
		long classNameId, long classPK, long commercePaymentMethodGroupRelId) {

		return getPersistence().countByC_C_C(
			classNameId, classPK, commercePaymentMethodGroupRelId);
	}

	/**
	 * Caches the commerce payment method group rel qualifier in the entity cache if it is enabled.
	 *
	 * @param commercePaymentMethodGroupRelQualifier the commerce payment method group rel qualifier
	 */
	public static void cacheResult(
		CommercePaymentMethodGroupRelQualifier
			commercePaymentMethodGroupRelQualifier) {

		getPersistence().cacheResult(commercePaymentMethodGroupRelQualifier);
	}

	/**
	 * Caches the commerce payment method group rel qualifiers in the entity cache if it is enabled.
	 *
	 * @param commercePaymentMethodGroupRelQualifiers the commerce payment method group rel qualifiers
	 */
	public static void cacheResult(
		List<CommercePaymentMethodGroupRelQualifier>
			commercePaymentMethodGroupRelQualifiers) {

		getPersistence().cacheResult(commercePaymentMethodGroupRelQualifiers);
	}

	/**
	 * Creates a new commerce payment method group rel qualifier with the primary key. Does not add the commerce payment method group rel qualifier to the database.
	 *
	 * @param commercePaymentMethodGroupRelQualifierId the primary key for the new commerce payment method group rel qualifier
	 * @return the new commerce payment method group rel qualifier
	 */
	public static CommercePaymentMethodGroupRelQualifier create(
		long commercePaymentMethodGroupRelQualifierId) {

		return getPersistence().create(
			commercePaymentMethodGroupRelQualifierId);
	}

	/**
	 * Removes the commerce payment method group rel qualifier with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commercePaymentMethodGroupRelQualifierId the primary key of the commerce payment method group rel qualifier
	 * @return the commerce payment method group rel qualifier that was removed
	 * @throws NoSuchPaymentMethodGroupRelQualifierException if a commerce payment method group rel qualifier with the primary key could not be found
	 */
	public static CommercePaymentMethodGroupRelQualifier remove(
			long commercePaymentMethodGroupRelQualifierId)
		throws com.liferay.commerce.payment.exception.
			NoSuchPaymentMethodGroupRelQualifierException {

		return getPersistence().remove(
			commercePaymentMethodGroupRelQualifierId);
	}

	public static CommercePaymentMethodGroupRelQualifier updateImpl(
		CommercePaymentMethodGroupRelQualifier
			commercePaymentMethodGroupRelQualifier) {

		return getPersistence().updateImpl(
			commercePaymentMethodGroupRelQualifier);
	}

	/**
	 * Returns the commerce payment method group rel qualifier with the primary key or throws a <code>NoSuchPaymentMethodGroupRelQualifierException</code> if it could not be found.
	 *
	 * @param commercePaymentMethodGroupRelQualifierId the primary key of the commerce payment method group rel qualifier
	 * @return the commerce payment method group rel qualifier
	 * @throws NoSuchPaymentMethodGroupRelQualifierException if a commerce payment method group rel qualifier with the primary key could not be found
	 */
	public static CommercePaymentMethodGroupRelQualifier findByPrimaryKey(
			long commercePaymentMethodGroupRelQualifierId)
		throws com.liferay.commerce.payment.exception.
			NoSuchPaymentMethodGroupRelQualifierException {

		return getPersistence().findByPrimaryKey(
			commercePaymentMethodGroupRelQualifierId);
	}

	/**
	 * Returns the commerce payment method group rel qualifier with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commercePaymentMethodGroupRelQualifierId the primary key of the commerce payment method group rel qualifier
	 * @return the commerce payment method group rel qualifier, or <code>null</code> if a commerce payment method group rel qualifier with the primary key could not be found
	 */
	public static CommercePaymentMethodGroupRelQualifier fetchByPrimaryKey(
		long commercePaymentMethodGroupRelQualifierId) {

		return getPersistence().fetchByPrimaryKey(
			commercePaymentMethodGroupRelQualifierId);
	}

	/**
	 * Returns all the commerce payment method group rel qualifiers.
	 *
	 * @return the commerce payment method group rel qualifiers
	 */
	public static List<CommercePaymentMethodGroupRelQualifier> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce payment method group rel qualifiers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentMethodGroupRelQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce payment method group rel qualifiers
	 * @param end the upper bound of the range of commerce payment method group rel qualifiers (not inclusive)
	 * @return the range of commerce payment method group rel qualifiers
	 */
	public static List<CommercePaymentMethodGroupRelQualifier> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce payment method group rel qualifiers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentMethodGroupRelQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce payment method group rel qualifiers
	 * @param end the upper bound of the range of commerce payment method group rel qualifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce payment method group rel qualifiers
	 */
	public static List<CommercePaymentMethodGroupRelQualifier> findAll(
		int start, int end,
		OrderByComparator<CommercePaymentMethodGroupRelQualifier>
			orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce payment method group rel qualifiers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentMethodGroupRelQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce payment method group rel qualifiers
	 * @param end the upper bound of the range of commerce payment method group rel qualifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce payment method group rel qualifiers
	 */
	public static List<CommercePaymentMethodGroupRelQualifier> findAll(
		int start, int end,
		OrderByComparator<CommercePaymentMethodGroupRelQualifier>
			orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce payment method group rel qualifiers from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce payment method group rel qualifiers.
	 *
	 * @return the number of commerce payment method group rel qualifiers
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommercePaymentMethodGroupRelQualifierPersistence
		getPersistence() {

		return _persistence;
	}

	public static void setPersistence(
		CommercePaymentMethodGroupRelQualifierPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile CommercePaymentMethodGroupRelQualifierPersistence
		_persistence;

}
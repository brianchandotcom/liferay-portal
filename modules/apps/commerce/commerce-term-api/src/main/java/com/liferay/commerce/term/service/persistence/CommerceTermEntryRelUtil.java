/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.term.service.persistence;

import com.liferay.commerce.term.model.CommerceTermEntryRel;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the commerce term entry rel service. This utility wraps <code>com.liferay.commerce.term.service.persistence.impl.CommerceTermEntryRelPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceTermEntryRelPersistence
 * @generated
 */
public class CommerceTermEntryRelUtil {

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
	public static void clearCache(CommerceTermEntryRel commerceTermEntryRel) {
		getPersistence().clearCache(commerceTermEntryRel);
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
	public static Map<Serializable, CommerceTermEntryRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceTermEntryRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceTermEntryRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceTermEntryRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceTermEntryRel> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceTermEntryRel update(
		CommerceTermEntryRel commerceTermEntryRel) {

		return getPersistence().update(commerceTermEntryRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceTermEntryRel update(
		CommerceTermEntryRel commerceTermEntryRel,
		ServiceContext serviceContext) {

		return getPersistence().update(commerceTermEntryRel, serviceContext);
	}

	/**
	 * Returns all the commerce term entry rels where commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @return the matching commerce term entry rels
	 */
	public static List<CommerceTermEntryRel> findByCommerceTermEntryId(
		long commerceTermEntryId) {

		return getPersistence().findByCommerceTermEntryId(commerceTermEntryId);
	}

	/**
	 * Returns a range of all the commerce term entry rels where commerceTermEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param start the lower bound of the range of commerce term entry rels
	 * @param end the upper bound of the range of commerce term entry rels (not inclusive)
	 * @return the range of matching commerce term entry rels
	 */
	public static List<CommerceTermEntryRel> findByCommerceTermEntryId(
		long commerceTermEntryId, int start, int end) {

		return getPersistence().findByCommerceTermEntryId(
			commerceTermEntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce term entry rels where commerceTermEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param start the lower bound of the range of commerce term entry rels
	 * @param end the upper bound of the range of commerce term entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce term entry rels
	 */
	public static List<CommerceTermEntryRel> findByCommerceTermEntryId(
		long commerceTermEntryId, int start, int end,
		OrderByComparator<CommerceTermEntryRel> orderByComparator) {

		return getPersistence().findByCommerceTermEntryId(
			commerceTermEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce term entry rels where commerceTermEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param start the lower bound of the range of commerce term entry rels
	 * @param end the upper bound of the range of commerce term entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce term entry rels
	 */
	public static List<CommerceTermEntryRel> findByCommerceTermEntryId(
		long commerceTermEntryId, int start, int end,
		OrderByComparator<CommerceTermEntryRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCommerceTermEntryId(
			commerceTermEntryId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce term entry rel in the ordered set where commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry rel
	 * @throws NoSuchTermEntryRelException if a matching commerce term entry rel could not be found
	 */
	public static CommerceTermEntryRel findByCommerceTermEntryId_First(
			long commerceTermEntryId,
			OrderByComparator<CommerceTermEntryRel> orderByComparator)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryRelException {

		return getPersistence().findByCommerceTermEntryId_First(
			commerceTermEntryId, orderByComparator);
	}

	/**
	 * Returns the first commerce term entry rel in the ordered set where commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry rel, or <code>null</code> if a matching commerce term entry rel could not be found
	 */
	public static CommerceTermEntryRel fetchByCommerceTermEntryId_First(
		long commerceTermEntryId,
		OrderByComparator<CommerceTermEntryRel> orderByComparator) {

		return getPersistence().fetchByCommerceTermEntryId_First(
			commerceTermEntryId, orderByComparator);
	}

	/**
	 * Returns the last commerce term entry rel in the ordered set where commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term entry rel
	 * @throws NoSuchTermEntryRelException if a matching commerce term entry rel could not be found
	 */
	public static CommerceTermEntryRel findByCommerceTermEntryId_Last(
			long commerceTermEntryId,
			OrderByComparator<CommerceTermEntryRel> orderByComparator)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryRelException {

		return getPersistence().findByCommerceTermEntryId_Last(
			commerceTermEntryId, orderByComparator);
	}

	/**
	 * Returns the last commerce term entry rel in the ordered set where commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term entry rel, or <code>null</code> if a matching commerce term entry rel could not be found
	 */
	public static CommerceTermEntryRel fetchByCommerceTermEntryId_Last(
		long commerceTermEntryId,
		OrderByComparator<CommerceTermEntryRel> orderByComparator) {

		return getPersistence().fetchByCommerceTermEntryId_Last(
			commerceTermEntryId, orderByComparator);
	}

	/**
	 * Returns the commerce term entry rels before and after the current commerce term entry rel in the ordered set where commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryRelId the primary key of the current commerce term entry rel
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce term entry rel
	 * @throws NoSuchTermEntryRelException if a commerce term entry rel with the primary key could not be found
	 */
	public static CommerceTermEntryRel[] findByCommerceTermEntryId_PrevAndNext(
			long commerceTermEntryRelId, long commerceTermEntryId,
			OrderByComparator<CommerceTermEntryRel> orderByComparator)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryRelException {

		return getPersistence().findByCommerceTermEntryId_PrevAndNext(
			commerceTermEntryRelId, commerceTermEntryId, orderByComparator);
	}

	/**
	 * Removes all the commerce term entry rels where commerceTermEntryId = &#63; from the database.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 */
	public static void removeByCommerceTermEntryId(long commerceTermEntryId) {
		getPersistence().removeByCommerceTermEntryId(commerceTermEntryId);
	}

	/**
	 * Returns the number of commerce term entry rels where commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @return the number of matching commerce term entry rels
	 */
	public static int countByCommerceTermEntryId(long commerceTermEntryId) {
		return getPersistence().countByCommerceTermEntryId(commerceTermEntryId);
	}

	/**
	 * Returns all the commerce term entry rels where classNameId = &#63; and commerceTermEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceTermEntryId the commerce term entry ID
	 * @return the matching commerce term entry rels
	 */
	public static List<CommerceTermEntryRel> findByC_C(
		long classNameId, long commerceTermEntryId) {

		return getPersistence().findByC_C(classNameId, commerceTermEntryId);
	}

	/**
	 * Returns a range of all the commerce term entry rels where classNameId = &#63; and commerceTermEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param start the lower bound of the range of commerce term entry rels
	 * @param end the upper bound of the range of commerce term entry rels (not inclusive)
	 * @return the range of matching commerce term entry rels
	 */
	public static List<CommerceTermEntryRel> findByC_C(
		long classNameId, long commerceTermEntryId, int start, int end) {

		return getPersistence().findByC_C(
			classNameId, commerceTermEntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce term entry rels where classNameId = &#63; and commerceTermEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param start the lower bound of the range of commerce term entry rels
	 * @param end the upper bound of the range of commerce term entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce term entry rels
	 */
	public static List<CommerceTermEntryRel> findByC_C(
		long classNameId, long commerceTermEntryId, int start, int end,
		OrderByComparator<CommerceTermEntryRel> orderByComparator) {

		return getPersistence().findByC_C(
			classNameId, commerceTermEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce term entry rels where classNameId = &#63; and commerceTermEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param start the lower bound of the range of commerce term entry rels
	 * @param end the upper bound of the range of commerce term entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce term entry rels
	 */
	public static List<CommerceTermEntryRel> findByC_C(
		long classNameId, long commerceTermEntryId, int start, int end,
		OrderByComparator<CommerceTermEntryRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_C(
			classNameId, commerceTermEntryId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce term entry rel in the ordered set where classNameId = &#63; and commerceTermEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry rel
	 * @throws NoSuchTermEntryRelException if a matching commerce term entry rel could not be found
	 */
	public static CommerceTermEntryRel findByC_C_First(
			long classNameId, long commerceTermEntryId,
			OrderByComparator<CommerceTermEntryRel> orderByComparator)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryRelException {

		return getPersistence().findByC_C_First(
			classNameId, commerceTermEntryId, orderByComparator);
	}

	/**
	 * Returns the first commerce term entry rel in the ordered set where classNameId = &#63; and commerceTermEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry rel, or <code>null</code> if a matching commerce term entry rel could not be found
	 */
	public static CommerceTermEntryRel fetchByC_C_First(
		long classNameId, long commerceTermEntryId,
		OrderByComparator<CommerceTermEntryRel> orderByComparator) {

		return getPersistence().fetchByC_C_First(
			classNameId, commerceTermEntryId, orderByComparator);
	}

	/**
	 * Returns the last commerce term entry rel in the ordered set where classNameId = &#63; and commerceTermEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term entry rel
	 * @throws NoSuchTermEntryRelException if a matching commerce term entry rel could not be found
	 */
	public static CommerceTermEntryRel findByC_C_Last(
			long classNameId, long commerceTermEntryId,
			OrderByComparator<CommerceTermEntryRel> orderByComparator)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryRelException {

		return getPersistence().findByC_C_Last(
			classNameId, commerceTermEntryId, orderByComparator);
	}

	/**
	 * Returns the last commerce term entry rel in the ordered set where classNameId = &#63; and commerceTermEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term entry rel, or <code>null</code> if a matching commerce term entry rel could not be found
	 */
	public static CommerceTermEntryRel fetchByC_C_Last(
		long classNameId, long commerceTermEntryId,
		OrderByComparator<CommerceTermEntryRel> orderByComparator) {

		return getPersistence().fetchByC_C_Last(
			classNameId, commerceTermEntryId, orderByComparator);
	}

	/**
	 * Returns the commerce term entry rels before and after the current commerce term entry rel in the ordered set where classNameId = &#63; and commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryRelId the primary key of the current commerce term entry rel
	 * @param classNameId the class name ID
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce term entry rel
	 * @throws NoSuchTermEntryRelException if a commerce term entry rel with the primary key could not be found
	 */
	public static CommerceTermEntryRel[] findByC_C_PrevAndNext(
			long commerceTermEntryRelId, long classNameId,
			long commerceTermEntryId,
			OrderByComparator<CommerceTermEntryRel> orderByComparator)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryRelException {

		return getPersistence().findByC_C_PrevAndNext(
			commerceTermEntryRelId, classNameId, commerceTermEntryId,
			orderByComparator);
	}

	/**
	 * Removes all the commerce term entry rels where classNameId = &#63; and commerceTermEntryId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param commerceTermEntryId the commerce term entry ID
	 */
	public static void removeByC_C(long classNameId, long commerceTermEntryId) {
		getPersistence().removeByC_C(classNameId, commerceTermEntryId);
	}

	/**
	 * Returns the number of commerce term entry rels where classNameId = &#63; and commerceTermEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceTermEntryId the commerce term entry ID
	 * @return the number of matching commerce term entry rels
	 */
	public static int countByC_C(long classNameId, long commerceTermEntryId) {
		return getPersistence().countByC_C(classNameId, commerceTermEntryId);
	}

	/**
	 * Returns the commerce term entry rel where classNameId = &#63; and classPK = &#63; and commerceTermEntryId = &#63; or throws a <code>NoSuchTermEntryRelException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceTermEntryId the commerce term entry ID
	 * @return the matching commerce term entry rel
	 * @throws NoSuchTermEntryRelException if a matching commerce term entry rel could not be found
	 */
	public static CommerceTermEntryRel findByC_C_C(
			long classNameId, long classPK, long commerceTermEntryId)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryRelException {

		return getPersistence().findByC_C_C(
			classNameId, classPK, commerceTermEntryId);
	}

	/**
	 * Returns the commerce term entry rel where classNameId = &#63; and classPK = &#63; and commerceTermEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceTermEntryId the commerce term entry ID
	 * @return the matching commerce term entry rel, or <code>null</code> if a matching commerce term entry rel could not be found
	 */
	public static CommerceTermEntryRel fetchByC_C_C(
		long classNameId, long classPK, long commerceTermEntryId) {

		return getPersistence().fetchByC_C_C(
			classNameId, classPK, commerceTermEntryId);
	}

	/**
	 * Returns the commerce term entry rel where classNameId = &#63; and classPK = &#63; and commerceTermEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce term entry rel, or <code>null</code> if a matching commerce term entry rel could not be found
	 */
	public static CommerceTermEntryRel fetchByC_C_C(
		long classNameId, long classPK, long commerceTermEntryId,
		boolean useFinderCache) {

		return getPersistence().fetchByC_C_C(
			classNameId, classPK, commerceTermEntryId, useFinderCache);
	}

	/**
	 * Removes the commerce term entry rel where classNameId = &#63; and classPK = &#63; and commerceTermEntryId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceTermEntryId the commerce term entry ID
	 * @return the commerce term entry rel that was removed
	 */
	public static CommerceTermEntryRel removeByC_C_C(
			long classNameId, long classPK, long commerceTermEntryId)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryRelException {

		return getPersistence().removeByC_C_C(
			classNameId, classPK, commerceTermEntryId);
	}

	/**
	 * Returns the number of commerce term entry rels where classNameId = &#63; and classPK = &#63; and commerceTermEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceTermEntryId the commerce term entry ID
	 * @return the number of matching commerce term entry rels
	 */
	public static int countByC_C_C(
		long classNameId, long classPK, long commerceTermEntryId) {

		return getPersistence().countByC_C_C(
			classNameId, classPK, commerceTermEntryId);
	}

	/**
	 * Caches the commerce term entry rel in the entity cache if it is enabled.
	 *
	 * @param commerceTermEntryRel the commerce term entry rel
	 */
	public static void cacheResult(CommerceTermEntryRel commerceTermEntryRel) {
		getPersistence().cacheResult(commerceTermEntryRel);
	}

	/**
	 * Caches the commerce term entry rels in the entity cache if it is enabled.
	 *
	 * @param commerceTermEntryRels the commerce term entry rels
	 */
	public static void cacheResult(
		List<CommerceTermEntryRel> commerceTermEntryRels) {

		getPersistence().cacheResult(commerceTermEntryRels);
	}

	/**
	 * Creates a new commerce term entry rel with the primary key. Does not add the commerce term entry rel to the database.
	 *
	 * @param commerceTermEntryRelId the primary key for the new commerce term entry rel
	 * @return the new commerce term entry rel
	 */
	public static CommerceTermEntryRel create(long commerceTermEntryRelId) {
		return getPersistence().create(commerceTermEntryRelId);
	}

	/**
	 * Removes the commerce term entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceTermEntryRelId the primary key of the commerce term entry rel
	 * @return the commerce term entry rel that was removed
	 * @throws NoSuchTermEntryRelException if a commerce term entry rel with the primary key could not be found
	 */
	public static CommerceTermEntryRel remove(long commerceTermEntryRelId)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryRelException {

		return getPersistence().remove(commerceTermEntryRelId);
	}

	public static CommerceTermEntryRel updateImpl(
		CommerceTermEntryRel commerceTermEntryRel) {

		return getPersistence().updateImpl(commerceTermEntryRel);
	}

	/**
	 * Returns the commerce term entry rel with the primary key or throws a <code>NoSuchTermEntryRelException</code> if it could not be found.
	 *
	 * @param commerceTermEntryRelId the primary key of the commerce term entry rel
	 * @return the commerce term entry rel
	 * @throws NoSuchTermEntryRelException if a commerce term entry rel with the primary key could not be found
	 */
	public static CommerceTermEntryRel findByPrimaryKey(
			long commerceTermEntryRelId)
		throws com.liferay.commerce.term.exception.NoSuchTermEntryRelException {

		return getPersistence().findByPrimaryKey(commerceTermEntryRelId);
	}

	/**
	 * Returns the commerce term entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceTermEntryRelId the primary key of the commerce term entry rel
	 * @return the commerce term entry rel, or <code>null</code> if a commerce term entry rel with the primary key could not be found
	 */
	public static CommerceTermEntryRel fetchByPrimaryKey(
		long commerceTermEntryRelId) {

		return getPersistence().fetchByPrimaryKey(commerceTermEntryRelId);
	}

	/**
	 * Returns all the commerce term entry rels.
	 *
	 * @return the commerce term entry rels
	 */
	public static List<CommerceTermEntryRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce term entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce term entry rels
	 * @param end the upper bound of the range of commerce term entry rels (not inclusive)
	 * @return the range of commerce term entry rels
	 */
	public static List<CommerceTermEntryRel> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce term entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce term entry rels
	 * @param end the upper bound of the range of commerce term entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce term entry rels
	 */
	public static List<CommerceTermEntryRel> findAll(
		int start, int end,
		OrderByComparator<CommerceTermEntryRel> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce term entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce term entry rels
	 * @param end the upper bound of the range of commerce term entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce term entry rels
	 */
	public static List<CommerceTermEntryRel> findAll(
		int start, int end,
		OrderByComparator<CommerceTermEntryRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce term entry rels from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce term entry rels.
	 *
	 * @return the number of commerce term entry rels
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceTermEntryRelPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		CommerceTermEntryRelPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile CommerceTermEntryRelPersistence _persistence;

}
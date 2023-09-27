/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.persistence;

import com.liferay.commerce.product.model.CommerceChannelRel;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the commerce channel rel service. This utility wraps <code>com.liferay.commerce.product.service.persistence.impl.CommerceChannelRelPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceChannelRelPersistence
 * @generated
 */
public class CommerceChannelRelUtil {

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
	public static void clearCache(CommerceChannelRel commerceChannelRel) {
		getPersistence().clearCache(commerceChannelRel);
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
	public static Map<Serializable, CommerceChannelRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceChannelRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceChannelRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceChannelRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceChannelRel> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceChannelRel update(
		CommerceChannelRel commerceChannelRel) {

		return getPersistence().update(commerceChannelRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceChannelRel update(
		CommerceChannelRel commerceChannelRel, ServiceContext serviceContext) {

		return getPersistence().update(commerceChannelRel, serviceContext);
	}

	/**
	 * Returns all the commerce channel rels where commerceChannelId = &#63;.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @return the matching commerce channel rels
	 */
	public static List<CommerceChannelRel> findByCommerceChannelId(
		long commerceChannelId) {

		return getPersistence().findByCommerceChannelId(commerceChannelId);
	}

	/**
	 * Returns a range of all the commerce channel rels where commerceChannelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param start the lower bound of the range of commerce channel rels
	 * @param end the upper bound of the range of commerce channel rels (not inclusive)
	 * @return the range of matching commerce channel rels
	 */
	public static List<CommerceChannelRel> findByCommerceChannelId(
		long commerceChannelId, int start, int end) {

		return getPersistence().findByCommerceChannelId(
			commerceChannelId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce channel rels where commerceChannelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param start the lower bound of the range of commerce channel rels
	 * @param end the upper bound of the range of commerce channel rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce channel rels
	 */
	public static List<CommerceChannelRel> findByCommerceChannelId(
		long commerceChannelId, int start, int end,
		OrderByComparator<CommerceChannelRel> orderByComparator) {

		return getPersistence().findByCommerceChannelId(
			commerceChannelId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce channel rels where commerceChannelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param start the lower bound of the range of commerce channel rels
	 * @param end the upper bound of the range of commerce channel rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce channel rels
	 */
	public static List<CommerceChannelRel> findByCommerceChannelId(
		long commerceChannelId, int start, int end,
		OrderByComparator<CommerceChannelRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCommerceChannelId(
			commerceChannelId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce channel rel in the ordered set where commerceChannelId = &#63;.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce channel rel
	 * @throws NoSuchChannelRelException if a matching commerce channel rel could not be found
	 */
	public static CommerceChannelRel findByCommerceChannelId_First(
			long commerceChannelId,
			OrderByComparator<CommerceChannelRel> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchChannelRelException {

		return getPersistence().findByCommerceChannelId_First(
			commerceChannelId, orderByComparator);
	}

	/**
	 * Returns the first commerce channel rel in the ordered set where commerceChannelId = &#63;.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce channel rel, or <code>null</code> if a matching commerce channel rel could not be found
	 */
	public static CommerceChannelRel fetchByCommerceChannelId_First(
		long commerceChannelId,
		OrderByComparator<CommerceChannelRel> orderByComparator) {

		return getPersistence().fetchByCommerceChannelId_First(
			commerceChannelId, orderByComparator);
	}

	/**
	 * Returns the last commerce channel rel in the ordered set where commerceChannelId = &#63;.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce channel rel
	 * @throws NoSuchChannelRelException if a matching commerce channel rel could not be found
	 */
	public static CommerceChannelRel findByCommerceChannelId_Last(
			long commerceChannelId,
			OrderByComparator<CommerceChannelRel> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchChannelRelException {

		return getPersistence().findByCommerceChannelId_Last(
			commerceChannelId, orderByComparator);
	}

	/**
	 * Returns the last commerce channel rel in the ordered set where commerceChannelId = &#63;.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce channel rel, or <code>null</code> if a matching commerce channel rel could not be found
	 */
	public static CommerceChannelRel fetchByCommerceChannelId_Last(
		long commerceChannelId,
		OrderByComparator<CommerceChannelRel> orderByComparator) {

		return getPersistence().fetchByCommerceChannelId_Last(
			commerceChannelId, orderByComparator);
	}

	/**
	 * Returns the commerce channel rels before and after the current commerce channel rel in the ordered set where commerceChannelId = &#63;.
	 *
	 * @param commerceChannelRelId the primary key of the current commerce channel rel
	 * @param commerceChannelId the commerce channel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce channel rel
	 * @throws NoSuchChannelRelException if a commerce channel rel with the primary key could not be found
	 */
	public static CommerceChannelRel[] findByCommerceChannelId_PrevAndNext(
			long commerceChannelRelId, long commerceChannelId,
			OrderByComparator<CommerceChannelRel> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchChannelRelException {

		return getPersistence().findByCommerceChannelId_PrevAndNext(
			commerceChannelRelId, commerceChannelId, orderByComparator);
	}

	/**
	 * Removes all the commerce channel rels where commerceChannelId = &#63; from the database.
	 *
	 * @param commerceChannelId the commerce channel ID
	 */
	public static void removeByCommerceChannelId(long commerceChannelId) {
		getPersistence().removeByCommerceChannelId(commerceChannelId);
	}

	/**
	 * Returns the number of commerce channel rels where commerceChannelId = &#63;.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @return the number of matching commerce channel rels
	 */
	public static int countByCommerceChannelId(long commerceChannelId) {
		return getPersistence().countByCommerceChannelId(commerceChannelId);
	}

	/**
	 * Returns all the commerce channel rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching commerce channel rels
	 */
	public static List<CommerceChannelRel> findByC_C(
		long classNameId, long classPK) {

		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	 * Returns a range of all the commerce channel rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce channel rels
	 * @param end the upper bound of the range of commerce channel rels (not inclusive)
	 * @return the range of matching commerce channel rels
	 */
	public static List<CommerceChannelRel> findByC_C(
		long classNameId, long classPK, int start, int end) {

		return getPersistence().findByC_C(classNameId, classPK, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce channel rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce channel rels
	 * @param end the upper bound of the range of commerce channel rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce channel rels
	 */
	public static List<CommerceChannelRel> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<CommerceChannelRel> orderByComparator) {

		return getPersistence().findByC_C(
			classNameId, classPK, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce channel rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce channel rels
	 * @param end the upper bound of the range of commerce channel rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce channel rels
	 */
	public static List<CommerceChannelRel> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<CommerceChannelRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_C(
			classNameId, classPK, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce channel rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce channel rel
	 * @throws NoSuchChannelRelException if a matching commerce channel rel could not be found
	 */
	public static CommerceChannelRel findByC_C_First(
			long classNameId, long classPK,
			OrderByComparator<CommerceChannelRel> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchChannelRelException {

		return getPersistence().findByC_C_First(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the first commerce channel rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce channel rel, or <code>null</code> if a matching commerce channel rel could not be found
	 */
	public static CommerceChannelRel fetchByC_C_First(
		long classNameId, long classPK,
		OrderByComparator<CommerceChannelRel> orderByComparator) {

		return getPersistence().fetchByC_C_First(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the last commerce channel rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce channel rel
	 * @throws NoSuchChannelRelException if a matching commerce channel rel could not be found
	 */
	public static CommerceChannelRel findByC_C_Last(
			long classNameId, long classPK,
			OrderByComparator<CommerceChannelRel> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchChannelRelException {

		return getPersistence().findByC_C_Last(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the last commerce channel rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce channel rel, or <code>null</code> if a matching commerce channel rel could not be found
	 */
	public static CommerceChannelRel fetchByC_C_Last(
		long classNameId, long classPK,
		OrderByComparator<CommerceChannelRel> orderByComparator) {

		return getPersistence().fetchByC_C_Last(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the commerce channel rels before and after the current commerce channel rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param commerceChannelRelId the primary key of the current commerce channel rel
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce channel rel
	 * @throws NoSuchChannelRelException if a commerce channel rel with the primary key could not be found
	 */
	public static CommerceChannelRel[] findByC_C_PrevAndNext(
			long commerceChannelRelId, long classNameId, long classPK,
			OrderByComparator<CommerceChannelRel> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchChannelRelException {

		return getPersistence().findByC_C_PrevAndNext(
			commerceChannelRelId, classNameId, classPK, orderByComparator);
	}

	/**
	 * Removes all the commerce channel rels where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public static void removeByC_C(long classNameId, long classPK) {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	 * Returns the number of commerce channel rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching commerce channel rels
	 */
	public static int countByC_C(long classNameId, long classPK) {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	 * Returns the commerce channel rel where classNameId = &#63; and classPK = &#63; and commerceChannelId = &#63; or throws a <code>NoSuchChannelRelException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceChannelId the commerce channel ID
	 * @return the matching commerce channel rel
	 * @throws NoSuchChannelRelException if a matching commerce channel rel could not be found
	 */
	public static CommerceChannelRel findByC_C_C(
			long classNameId, long classPK, long commerceChannelId)
		throws com.liferay.commerce.product.exception.
			NoSuchChannelRelException {

		return getPersistence().findByC_C_C(
			classNameId, classPK, commerceChannelId);
	}

	/**
	 * Returns the commerce channel rel where classNameId = &#63; and classPK = &#63; and commerceChannelId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceChannelId the commerce channel ID
	 * @return the matching commerce channel rel, or <code>null</code> if a matching commerce channel rel could not be found
	 */
	public static CommerceChannelRel fetchByC_C_C(
		long classNameId, long classPK, long commerceChannelId) {

		return getPersistence().fetchByC_C_C(
			classNameId, classPK, commerceChannelId);
	}

	/**
	 * Returns the commerce channel rel where classNameId = &#63; and classPK = &#63; and commerceChannelId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceChannelId the commerce channel ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce channel rel, or <code>null</code> if a matching commerce channel rel could not be found
	 */
	public static CommerceChannelRel fetchByC_C_C(
		long classNameId, long classPK, long commerceChannelId,
		boolean useFinderCache) {

		return getPersistence().fetchByC_C_C(
			classNameId, classPK, commerceChannelId, useFinderCache);
	}

	/**
	 * Removes the commerce channel rel where classNameId = &#63; and classPK = &#63; and commerceChannelId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceChannelId the commerce channel ID
	 * @return the commerce channel rel that was removed
	 */
	public static CommerceChannelRel removeByC_C_C(
			long classNameId, long classPK, long commerceChannelId)
		throws com.liferay.commerce.product.exception.
			NoSuchChannelRelException {

		return getPersistence().removeByC_C_C(
			classNameId, classPK, commerceChannelId);
	}

	/**
	 * Returns the number of commerce channel rels where classNameId = &#63; and classPK = &#63; and commerceChannelId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceChannelId the commerce channel ID
	 * @return the number of matching commerce channel rels
	 */
	public static int countByC_C_C(
		long classNameId, long classPK, long commerceChannelId) {

		return getPersistence().countByC_C_C(
			classNameId, classPK, commerceChannelId);
	}

	/**
	 * Caches the commerce channel rel in the entity cache if it is enabled.
	 *
	 * @param commerceChannelRel the commerce channel rel
	 */
	public static void cacheResult(CommerceChannelRel commerceChannelRel) {
		getPersistence().cacheResult(commerceChannelRel);
	}

	/**
	 * Caches the commerce channel rels in the entity cache if it is enabled.
	 *
	 * @param commerceChannelRels the commerce channel rels
	 */
	public static void cacheResult(
		List<CommerceChannelRel> commerceChannelRels) {

		getPersistence().cacheResult(commerceChannelRels);
	}

	/**
	 * Creates a new commerce channel rel with the primary key. Does not add the commerce channel rel to the database.
	 *
	 * @param commerceChannelRelId the primary key for the new commerce channel rel
	 * @return the new commerce channel rel
	 */
	public static CommerceChannelRel create(long commerceChannelRelId) {
		return getPersistence().create(commerceChannelRelId);
	}

	/**
	 * Removes the commerce channel rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceChannelRelId the primary key of the commerce channel rel
	 * @return the commerce channel rel that was removed
	 * @throws NoSuchChannelRelException if a commerce channel rel with the primary key could not be found
	 */
	public static CommerceChannelRel remove(long commerceChannelRelId)
		throws com.liferay.commerce.product.exception.
			NoSuchChannelRelException {

		return getPersistence().remove(commerceChannelRelId);
	}

	public static CommerceChannelRel updateImpl(
		CommerceChannelRel commerceChannelRel) {

		return getPersistence().updateImpl(commerceChannelRel);
	}

	/**
	 * Returns the commerce channel rel with the primary key or throws a <code>NoSuchChannelRelException</code> if it could not be found.
	 *
	 * @param commerceChannelRelId the primary key of the commerce channel rel
	 * @return the commerce channel rel
	 * @throws NoSuchChannelRelException if a commerce channel rel with the primary key could not be found
	 */
	public static CommerceChannelRel findByPrimaryKey(long commerceChannelRelId)
		throws com.liferay.commerce.product.exception.
			NoSuchChannelRelException {

		return getPersistence().findByPrimaryKey(commerceChannelRelId);
	}

	/**
	 * Returns the commerce channel rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceChannelRelId the primary key of the commerce channel rel
	 * @return the commerce channel rel, or <code>null</code> if a commerce channel rel with the primary key could not be found
	 */
	public static CommerceChannelRel fetchByPrimaryKey(
		long commerceChannelRelId) {

		return getPersistence().fetchByPrimaryKey(commerceChannelRelId);
	}

	/**
	 * Returns all the commerce channel rels.
	 *
	 * @return the commerce channel rels
	 */
	public static List<CommerceChannelRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce channel rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce channel rels
	 * @param end the upper bound of the range of commerce channel rels (not inclusive)
	 * @return the range of commerce channel rels
	 */
	public static List<CommerceChannelRel> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce channel rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce channel rels
	 * @param end the upper bound of the range of commerce channel rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce channel rels
	 */
	public static List<CommerceChannelRel> findAll(
		int start, int end,
		OrderByComparator<CommerceChannelRel> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce channel rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce channel rels
	 * @param end the upper bound of the range of commerce channel rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce channel rels
	 */
	public static List<CommerceChannelRel> findAll(
		int start, int end,
		OrderByComparator<CommerceChannelRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce channel rels from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce channel rels.
	 *
	 * @return the number of commerce channel rels
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceChannelRelPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		CommerceChannelRelPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile CommerceChannelRelPersistence _persistence;

}
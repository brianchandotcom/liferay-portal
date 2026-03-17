/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.persistence;

import com.liferay.commerce.product.model.CPConfigurationListRel;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the cp configuration list rel service. This utility wraps <code>com.liferay.commerce.product.service.persistence.impl.CPConfigurationListRelPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPConfigurationListRelPersistence
 * @generated
 */
public class CPConfigurationListRelUtil {

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
		CPConfigurationListRel cpConfigurationListRel) {

		getPersistence().clearCache(cpConfigurationListRel);
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
	public static Map<Serializable, CPConfigurationListRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CPConfigurationListRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CPConfigurationListRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CPConfigurationListRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CPConfigurationListRel> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CPConfigurationListRel update(
		CPConfigurationListRel cpConfigurationListRel) {

		return getPersistence().update(cpConfigurationListRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CPConfigurationListRel update(
		CPConfigurationListRel cpConfigurationListRel,
		ServiceContext serviceContext) {

		return getPersistence().update(cpConfigurationListRel, serviceContext);
	}

	/**
	 * Returns all the cp configuration list rels where CPConfigurationListId = &#63;.
	 *
	 * @param CPConfigurationListId the cp configuration list ID
	 * @return the matching cp configuration list rels
	 */
	public static List<CPConfigurationListRel> findByCPConfigurationListId(
		long CPConfigurationListId) {

		return getPersistence().findByCPConfigurationListId(
			CPConfigurationListId);
	}

	/**
	 * Returns a range of all the cp configuration list rels where CPConfigurationListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param start the lower bound of the range of cp configuration list rels
	 * @param end the upper bound of the range of cp configuration list rels (not inclusive)
	 * @return the range of matching cp configuration list rels
	 */
	public static List<CPConfigurationListRel> findByCPConfigurationListId(
		long CPConfigurationListId, int start, int end) {

		return getPersistence().findByCPConfigurationListId(
			CPConfigurationListId, start, end);
	}

	/**
	 * Returns an ordered range of all the cp configuration list rels where CPConfigurationListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param start the lower bound of the range of cp configuration list rels
	 * @param end the upper bound of the range of cp configuration list rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration list rels
	 */
	public static List<CPConfigurationListRel> findByCPConfigurationListId(
		long CPConfigurationListId, int start, int end,
		OrderByComparator<CPConfigurationListRel> orderByComparator) {

		return getPersistence().findByCPConfigurationListId(
			CPConfigurationListId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp configuration list rels where CPConfigurationListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param start the lower bound of the range of cp configuration list rels
	 * @param end the upper bound of the range of cp configuration list rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration list rels
	 */
	public static List<CPConfigurationListRel> findByCPConfigurationListId(
		long CPConfigurationListId, int start, int end,
		OrderByComparator<CPConfigurationListRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCPConfigurationListId(
			CPConfigurationListId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first cp configuration list rel in the ordered set where CPConfigurationListId = &#63;.
	 *
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list rel
	 * @throws NoSuchCPConfigurationListRelException if a matching cp configuration list rel could not be found
	 */
	public static CPConfigurationListRel findByCPConfigurationListId_First(
			long CPConfigurationListId,
			OrderByComparator<CPConfigurationListRel> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPConfigurationListRelException {

		return getPersistence().findByCPConfigurationListId_First(
			CPConfigurationListId, orderByComparator);
	}

	/**
	 * Returns the first cp configuration list rel in the ordered set where CPConfigurationListId = &#63;.
	 *
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list rel, or <code>null</code> if a matching cp configuration list rel could not be found
	 */
	public static CPConfigurationListRel fetchByCPConfigurationListId_First(
		long CPConfigurationListId,
		OrderByComparator<CPConfigurationListRel> orderByComparator) {

		return getPersistence().fetchByCPConfigurationListId_First(
			CPConfigurationListId, orderByComparator);
	}

	/**
	 * Returns the last cp configuration list rel in the ordered set where CPConfigurationListId = &#63;.
	 *
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration list rel
	 * @throws NoSuchCPConfigurationListRelException if a matching cp configuration list rel could not be found
	 */
	public static CPConfigurationListRel findByCPConfigurationListId_Last(
			long CPConfigurationListId,
			OrderByComparator<CPConfigurationListRel> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPConfigurationListRelException {

		return getPersistence().findByCPConfigurationListId_Last(
			CPConfigurationListId, orderByComparator);
	}

	/**
	 * Returns the last cp configuration list rel in the ordered set where CPConfigurationListId = &#63;.
	 *
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration list rel, or <code>null</code> if a matching cp configuration list rel could not be found
	 */
	public static CPConfigurationListRel fetchByCPConfigurationListId_Last(
		long CPConfigurationListId,
		OrderByComparator<CPConfigurationListRel> orderByComparator) {

		return getPersistence().fetchByCPConfigurationListId_Last(
			CPConfigurationListId, orderByComparator);
	}

	/**
	 * Removes all the cp configuration list rels where CPConfigurationListId = &#63; from the database.
	 *
	 * @param CPConfigurationListId the cp configuration list ID
	 */
	public static void removeByCPConfigurationListId(
		long CPConfigurationListId) {

		getPersistence().removeByCPConfigurationListId(CPConfigurationListId);
	}

	/**
	 * Returns the number of cp configuration list rels where CPConfigurationListId = &#63;.
	 *
	 * @param CPConfigurationListId the cp configuration list ID
	 * @return the number of matching cp configuration list rels
	 */
	public static int countByCPConfigurationListId(long CPConfigurationListId) {
		return getPersistence().countByCPConfigurationListId(
			CPConfigurationListId);
	}

	/**
	 * Returns all the cp configuration list rels where classNameId = &#63; and CPConfigurationListId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param CPConfigurationListId the cp configuration list ID
	 * @return the matching cp configuration list rels
	 */
	public static List<CPConfigurationListRel> findByC_C(
		long classNameId, long CPConfigurationListId) {

		return getPersistence().findByC_C(classNameId, CPConfigurationListId);
	}

	/**
	 * Returns a range of all the cp configuration list rels where classNameId = &#63; and CPConfigurationListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param start the lower bound of the range of cp configuration list rels
	 * @param end the upper bound of the range of cp configuration list rels (not inclusive)
	 * @return the range of matching cp configuration list rels
	 */
	public static List<CPConfigurationListRel> findByC_C(
		long classNameId, long CPConfigurationListId, int start, int end) {

		return getPersistence().findByC_C(
			classNameId, CPConfigurationListId, start, end);
	}

	/**
	 * Returns an ordered range of all the cp configuration list rels where classNameId = &#63; and CPConfigurationListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param start the lower bound of the range of cp configuration list rels
	 * @param end the upper bound of the range of cp configuration list rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration list rels
	 */
	public static List<CPConfigurationListRel> findByC_C(
		long classNameId, long CPConfigurationListId, int start, int end,
		OrderByComparator<CPConfigurationListRel> orderByComparator) {

		return getPersistence().findByC_C(
			classNameId, CPConfigurationListId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp configuration list rels where classNameId = &#63; and CPConfigurationListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param start the lower bound of the range of cp configuration list rels
	 * @param end the upper bound of the range of cp configuration list rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration list rels
	 */
	public static List<CPConfigurationListRel> findByC_C(
		long classNameId, long CPConfigurationListId, int start, int end,
		OrderByComparator<CPConfigurationListRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_C(
			classNameId, CPConfigurationListId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first cp configuration list rel in the ordered set where classNameId = &#63; and CPConfigurationListId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list rel
	 * @throws NoSuchCPConfigurationListRelException if a matching cp configuration list rel could not be found
	 */
	public static CPConfigurationListRel findByC_C_First(
			long classNameId, long CPConfigurationListId,
			OrderByComparator<CPConfigurationListRel> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPConfigurationListRelException {

		return getPersistence().findByC_C_First(
			classNameId, CPConfigurationListId, orderByComparator);
	}

	/**
	 * Returns the first cp configuration list rel in the ordered set where classNameId = &#63; and CPConfigurationListId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list rel, or <code>null</code> if a matching cp configuration list rel could not be found
	 */
	public static CPConfigurationListRel fetchByC_C_First(
		long classNameId, long CPConfigurationListId,
		OrderByComparator<CPConfigurationListRel> orderByComparator) {

		return getPersistence().fetchByC_C_First(
			classNameId, CPConfigurationListId, orderByComparator);
	}

	/**
	 * Returns the last cp configuration list rel in the ordered set where classNameId = &#63; and CPConfigurationListId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration list rel
	 * @throws NoSuchCPConfigurationListRelException if a matching cp configuration list rel could not be found
	 */
	public static CPConfigurationListRel findByC_C_Last(
			long classNameId, long CPConfigurationListId,
			OrderByComparator<CPConfigurationListRel> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPConfigurationListRelException {

		return getPersistence().findByC_C_Last(
			classNameId, CPConfigurationListId, orderByComparator);
	}

	/**
	 * Returns the last cp configuration list rel in the ordered set where classNameId = &#63; and CPConfigurationListId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration list rel, or <code>null</code> if a matching cp configuration list rel could not be found
	 */
	public static CPConfigurationListRel fetchByC_C_Last(
		long classNameId, long CPConfigurationListId,
		OrderByComparator<CPConfigurationListRel> orderByComparator) {

		return getPersistence().fetchByC_C_Last(
			classNameId, CPConfigurationListId, orderByComparator);
	}

	/**
	 * Removes all the cp configuration list rels where classNameId = &#63; and CPConfigurationListId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param CPConfigurationListId the cp configuration list ID
	 */
	public static void removeByC_C(
		long classNameId, long CPConfigurationListId) {

		getPersistence().removeByC_C(classNameId, CPConfigurationListId);
	}

	/**
	 * Returns the number of cp configuration list rels where classNameId = &#63; and CPConfigurationListId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param CPConfigurationListId the cp configuration list ID
	 * @return the number of matching cp configuration list rels
	 */
	public static int countByC_C(long classNameId, long CPConfigurationListId) {
		return getPersistence().countByC_C(classNameId, CPConfigurationListId);
	}

	/**
	 * Returns the cp configuration list rel where classNameId = &#63; and classPK = &#63; and CPConfigurationListId = &#63; or throws a <code>NoSuchCPConfigurationListRelException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param CPConfigurationListId the cp configuration list ID
	 * @return the matching cp configuration list rel
	 * @throws NoSuchCPConfigurationListRelException if a matching cp configuration list rel could not be found
	 */
	public static CPConfigurationListRel findByC_C_C(
			long classNameId, long classPK, long CPConfigurationListId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPConfigurationListRelException {

		return getPersistence().findByC_C_C(
			classNameId, classPK, CPConfigurationListId);
	}

	/**
	 * Returns the cp configuration list rel where classNameId = &#63; and classPK = &#63; and CPConfigurationListId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param CPConfigurationListId the cp configuration list ID
	 * @return the matching cp configuration list rel, or <code>null</code> if a matching cp configuration list rel could not be found
	 */
	public static CPConfigurationListRel fetchByC_C_C(
		long classNameId, long classPK, long CPConfigurationListId) {

		return getPersistence().fetchByC_C_C(
			classNameId, classPK, CPConfigurationListId);
	}

	/**
	 * Returns the cp configuration list rel where classNameId = &#63; and classPK = &#63; and CPConfigurationListId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp configuration list rel, or <code>null</code> if a matching cp configuration list rel could not be found
	 */
	public static CPConfigurationListRel fetchByC_C_C(
		long classNameId, long classPK, long CPConfigurationListId,
		boolean useFinderCache) {

		return getPersistence().fetchByC_C_C(
			classNameId, classPK, CPConfigurationListId, useFinderCache);
	}

	/**
	 * Removes the cp configuration list rel where classNameId = &#63; and classPK = &#63; and CPConfigurationListId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param CPConfigurationListId the cp configuration list ID
	 * @return the cp configuration list rel that was removed
	 */
	public static CPConfigurationListRel removeByC_C_C(
			long classNameId, long classPK, long CPConfigurationListId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPConfigurationListRelException {

		return getPersistence().removeByC_C_C(
			classNameId, classPK, CPConfigurationListId);
	}

	/**
	 * Returns the number of cp configuration list rels where classNameId = &#63; and classPK = &#63; and CPConfigurationListId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param CPConfigurationListId the cp configuration list ID
	 * @return the number of matching cp configuration list rels
	 */
	public static int countByC_C_C(
		long classNameId, long classPK, long CPConfigurationListId) {

		return getPersistence().countByC_C_C(
			classNameId, classPK, CPConfigurationListId);
	}

	/**
	 * Caches the cp configuration list rel in the entity cache if it is enabled.
	 *
	 * @param cpConfigurationListRel the cp configuration list rel
	 */
	public static void cacheResult(
		CPConfigurationListRel cpConfigurationListRel) {

		getPersistence().cacheResult(cpConfigurationListRel);
	}

	/**
	 * Caches the cp configuration list rels in the entity cache if it is enabled.
	 *
	 * @param cpConfigurationListRels the cp configuration list rels
	 */
	public static void cacheResult(
		List<CPConfigurationListRel> cpConfigurationListRels) {

		getPersistence().cacheResult(cpConfigurationListRels);
	}

	/**
	 * Creates a new cp configuration list rel with the primary key. Does not add the cp configuration list rel to the database.
	 *
	 * @param CPConfigurationListRelId the primary key for the new cp configuration list rel
	 * @return the new cp configuration list rel
	 */
	public static CPConfigurationListRel create(long CPConfigurationListRelId) {
		return getPersistence().create(CPConfigurationListRelId);
	}

	/**
	 * Removes the cp configuration list rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPConfigurationListRelId the primary key of the cp configuration list rel
	 * @return the cp configuration list rel that was removed
	 * @throws NoSuchCPConfigurationListRelException if a cp configuration list rel with the primary key could not be found
	 */
	public static CPConfigurationListRel remove(long CPConfigurationListRelId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPConfigurationListRelException {

		return getPersistence().remove(CPConfigurationListRelId);
	}

	public static CPConfigurationListRel updateImpl(
		CPConfigurationListRel cpConfigurationListRel) {

		return getPersistence().updateImpl(cpConfigurationListRel);
	}

	/**
	 * Returns the cp configuration list rel with the primary key or throws a <code>NoSuchCPConfigurationListRelException</code> if it could not be found.
	 *
	 * @param CPConfigurationListRelId the primary key of the cp configuration list rel
	 * @return the cp configuration list rel
	 * @throws NoSuchCPConfigurationListRelException if a cp configuration list rel with the primary key could not be found
	 */
	public static CPConfigurationListRel findByPrimaryKey(
			long CPConfigurationListRelId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPConfigurationListRelException {

		return getPersistence().findByPrimaryKey(CPConfigurationListRelId);
	}

	/**
	 * Returns the cp configuration list rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPConfigurationListRelId the primary key of the cp configuration list rel
	 * @return the cp configuration list rel, or <code>null</code> if a cp configuration list rel with the primary key could not be found
	 */
	public static CPConfigurationListRel fetchByPrimaryKey(
		long CPConfigurationListRelId) {

		return getPersistence().fetchByPrimaryKey(CPConfigurationListRelId);
	}

	/**
	 * Returns all the cp configuration list rels.
	 *
	 * @return the cp configuration list rels
	 */
	public static List<CPConfigurationListRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the cp configuration list rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp configuration list rels
	 * @param end the upper bound of the range of cp configuration list rels (not inclusive)
	 * @return the range of cp configuration list rels
	 */
	public static List<CPConfigurationListRel> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the cp configuration list rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp configuration list rels
	 * @param end the upper bound of the range of cp configuration list rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp configuration list rels
	 */
	public static List<CPConfigurationListRel> findAll(
		int start, int end,
		OrderByComparator<CPConfigurationListRel> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp configuration list rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp configuration list rels
	 * @param end the upper bound of the range of cp configuration list rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp configuration list rels
	 */
	public static List<CPConfigurationListRel> findAll(
		int start, int end,
		OrderByComparator<CPConfigurationListRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the cp configuration list rels from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of cp configuration list rels.
	 *
	 * @return the number of cp configuration list rels
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CPConfigurationListRelPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		CPConfigurationListRelPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile CPConfigurationListRelPersistence _persistence;

}
// LIFERAY-SERVICE-BUILDER-HASH:-184820119
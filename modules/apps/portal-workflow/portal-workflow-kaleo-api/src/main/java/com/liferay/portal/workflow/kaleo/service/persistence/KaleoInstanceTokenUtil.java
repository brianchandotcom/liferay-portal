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

package com.liferay.portal.workflow.kaleo.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the kaleo instance token service. This utility wraps <code>com.liferay.portal.workflow.kaleo.service.persistence.impl.KaleoInstanceTokenPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoInstanceTokenPersistence
 * @generated
 */
public class KaleoInstanceTokenUtil {

	/**
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
	public static void clearCache(KaleoInstanceToken kaleoInstanceToken) {
		getPersistence().clearCache(kaleoInstanceToken);
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
	public static Map<Serializable, KaleoInstanceToken> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<KaleoInstanceToken> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<KaleoInstanceToken> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<KaleoInstanceToken> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<KaleoInstanceToken> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static KaleoInstanceToken update(
		KaleoInstanceToken kaleoInstanceToken) {

		return getPersistence().update(kaleoInstanceToken);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static KaleoInstanceToken update(
		KaleoInstanceToken kaleoInstanceToken, ServiceContext serviceContext) {

		return getPersistence().update(kaleoInstanceToken, serviceContext);
	}

	/**
	 * Returns all the kaleo instance tokens where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching kaleo instance tokens
	 */
	public static List<KaleoInstanceToken> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the kaleo instance tokens where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo instance tokens
	 * @param end the upper bound of the range of kaleo instance tokens (not inclusive)
	 * @return the range of matching kaleo instance tokens
	 */
	public static List<KaleoInstanceToken> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the kaleo instance tokens where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo instance tokens
	 * @param end the upper bound of the range of kaleo instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo instance tokens
	 */
	public static List<KaleoInstanceToken> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<KaleoInstanceToken> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the kaleo instance tokens where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo instance tokens
	 * @param end the upper bound of the range of kaleo instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo instance tokens
	 */
	public static List<KaleoInstanceToken> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<KaleoInstanceToken> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first kaleo instance token in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance token
	 * @throws NoSuchInstanceTokenException if a matching kaleo instance token could not be found
	 */
	public static KaleoInstanceToken findByCompanyId_First(
			long companyId,
			OrderByComparator<KaleoInstanceToken> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchInstanceTokenException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first kaleo instance token in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance token, or <code>null</code> if a matching kaleo instance token could not be found
	 */
	public static KaleoInstanceToken fetchByCompanyId_First(
		long companyId,
		OrderByComparator<KaleoInstanceToken> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last kaleo instance token in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance token
	 * @throws NoSuchInstanceTokenException if a matching kaleo instance token could not be found
	 */
	public static KaleoInstanceToken findByCompanyId_Last(
			long companyId,
			OrderByComparator<KaleoInstanceToken> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchInstanceTokenException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last kaleo instance token in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance token, or <code>null</code> if a matching kaleo instance token could not be found
	 */
	public static KaleoInstanceToken fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<KaleoInstanceToken> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the kaleo instance tokens before and after the current kaleo instance token in the ordered set where companyId = &#63;.
	 *
	 * @param kaleoInstanceTokenId the primary key of the current kaleo instance token
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo instance token
	 * @throws NoSuchInstanceTokenException if a kaleo instance token with the primary key could not be found
	 */
	public static KaleoInstanceToken[] findByCompanyId_PrevAndNext(
			long kaleoInstanceTokenId, long companyId,
			OrderByComparator<KaleoInstanceToken> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchInstanceTokenException {

		return getPersistence().findByCompanyId_PrevAndNext(
			kaleoInstanceTokenId, companyId, orderByComparator);
	}

	/**
	 * Removes all the kaleo instance tokens where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of kaleo instance tokens where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching kaleo instance tokens
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns all the kaleo instance tokens where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the matching kaleo instance tokens
	 */
	public static List<KaleoInstanceToken> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId) {

		return getPersistence().findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId);
	}

	/**
	 * Returns a range of all the kaleo instance tokens where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo instance tokens
	 * @param end the upper bound of the range of kaleo instance tokens (not inclusive)
	 * @return the range of matching kaleo instance tokens
	 */
	public static List<KaleoInstanceToken> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end) {

		return getPersistence().findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, start, end);
	}

	/**
	 * Returns an ordered range of all the kaleo instance tokens where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo instance tokens
	 * @param end the upper bound of the range of kaleo instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo instance tokens
	 */
	public static List<KaleoInstanceToken> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end,
		OrderByComparator<KaleoInstanceToken> orderByComparator) {

		return getPersistence().findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the kaleo instance tokens where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo instance tokens
	 * @param end the upper bound of the range of kaleo instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo instance tokens
	 */
	public static List<KaleoInstanceToken> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end,
		OrderByComparator<KaleoInstanceToken> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first kaleo instance token in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance token
	 * @throws NoSuchInstanceTokenException if a matching kaleo instance token could not be found
	 */
	public static KaleoInstanceToken findByKaleoDefinitionVersionId_First(
			long kaleoDefinitionVersionId,
			OrderByComparator<KaleoInstanceToken> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchInstanceTokenException {

		return getPersistence().findByKaleoDefinitionVersionId_First(
			kaleoDefinitionVersionId, orderByComparator);
	}

	/**
	 * Returns the first kaleo instance token in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance token, or <code>null</code> if a matching kaleo instance token could not be found
	 */
	public static KaleoInstanceToken fetchByKaleoDefinitionVersionId_First(
		long kaleoDefinitionVersionId,
		OrderByComparator<KaleoInstanceToken> orderByComparator) {

		return getPersistence().fetchByKaleoDefinitionVersionId_First(
			kaleoDefinitionVersionId, orderByComparator);
	}

	/**
	 * Returns the last kaleo instance token in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance token
	 * @throws NoSuchInstanceTokenException if a matching kaleo instance token could not be found
	 */
	public static KaleoInstanceToken findByKaleoDefinitionVersionId_Last(
			long kaleoDefinitionVersionId,
			OrderByComparator<KaleoInstanceToken> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchInstanceTokenException {

		return getPersistence().findByKaleoDefinitionVersionId_Last(
			kaleoDefinitionVersionId, orderByComparator);
	}

	/**
	 * Returns the last kaleo instance token in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance token, or <code>null</code> if a matching kaleo instance token could not be found
	 */
	public static KaleoInstanceToken fetchByKaleoDefinitionVersionId_Last(
		long kaleoDefinitionVersionId,
		OrderByComparator<KaleoInstanceToken> orderByComparator) {

		return getPersistence().fetchByKaleoDefinitionVersionId_Last(
			kaleoDefinitionVersionId, orderByComparator);
	}

	/**
	 * Returns the kaleo instance tokens before and after the current kaleo instance token in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoInstanceTokenId the primary key of the current kaleo instance token
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo instance token
	 * @throws NoSuchInstanceTokenException if a kaleo instance token with the primary key could not be found
	 */
	public static KaleoInstanceToken[]
			findByKaleoDefinitionVersionId_PrevAndNext(
				long kaleoInstanceTokenId, long kaleoDefinitionVersionId,
				OrderByComparator<KaleoInstanceToken> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchInstanceTokenException {

		return getPersistence().findByKaleoDefinitionVersionId_PrevAndNext(
			kaleoInstanceTokenId, kaleoDefinitionVersionId, orderByComparator);
	}

	/**
	 * Removes all the kaleo instance tokens where kaleoDefinitionVersionId = &#63; from the database.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 */
	public static void removeByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId) {

		getPersistence().removeByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId);
	}

	/**
	 * Returns the number of kaleo instance tokens where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the number of matching kaleo instance tokens
	 */
	public static int countByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId) {

		return getPersistence().countByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId);
	}

	/**
	 * Returns all the kaleo instance tokens where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @return the matching kaleo instance tokens
	 */
	public static List<KaleoInstanceToken> findByKaleoInstanceId(
		long kaleoInstanceId) {

		return getPersistence().findByKaleoInstanceId(kaleoInstanceId);
	}

	/**
	 * Returns a range of all the kaleo instance tokens where kaleoInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param start the lower bound of the range of kaleo instance tokens
	 * @param end the upper bound of the range of kaleo instance tokens (not inclusive)
	 * @return the range of matching kaleo instance tokens
	 */
	public static List<KaleoInstanceToken> findByKaleoInstanceId(
		long kaleoInstanceId, int start, int end) {

		return getPersistence().findByKaleoInstanceId(
			kaleoInstanceId, start, end);
	}

	/**
	 * Returns an ordered range of all the kaleo instance tokens where kaleoInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param start the lower bound of the range of kaleo instance tokens
	 * @param end the upper bound of the range of kaleo instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo instance tokens
	 */
	public static List<KaleoInstanceToken> findByKaleoInstanceId(
		long kaleoInstanceId, int start, int end,
		OrderByComparator<KaleoInstanceToken> orderByComparator) {

		return getPersistence().findByKaleoInstanceId(
			kaleoInstanceId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the kaleo instance tokens where kaleoInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param start the lower bound of the range of kaleo instance tokens
	 * @param end the upper bound of the range of kaleo instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo instance tokens
	 */
	public static List<KaleoInstanceToken> findByKaleoInstanceId(
		long kaleoInstanceId, int start, int end,
		OrderByComparator<KaleoInstanceToken> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByKaleoInstanceId(
			kaleoInstanceId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first kaleo instance token in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance token
	 * @throws NoSuchInstanceTokenException if a matching kaleo instance token could not be found
	 */
	public static KaleoInstanceToken findByKaleoInstanceId_First(
			long kaleoInstanceId,
			OrderByComparator<KaleoInstanceToken> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchInstanceTokenException {

		return getPersistence().findByKaleoInstanceId_First(
			kaleoInstanceId, orderByComparator);
	}

	/**
	 * Returns the first kaleo instance token in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance token, or <code>null</code> if a matching kaleo instance token could not be found
	 */
	public static KaleoInstanceToken fetchByKaleoInstanceId_First(
		long kaleoInstanceId,
		OrderByComparator<KaleoInstanceToken> orderByComparator) {

		return getPersistence().fetchByKaleoInstanceId_First(
			kaleoInstanceId, orderByComparator);
	}

	/**
	 * Returns the last kaleo instance token in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance token
	 * @throws NoSuchInstanceTokenException if a matching kaleo instance token could not be found
	 */
	public static KaleoInstanceToken findByKaleoInstanceId_Last(
			long kaleoInstanceId,
			OrderByComparator<KaleoInstanceToken> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchInstanceTokenException {

		return getPersistence().findByKaleoInstanceId_Last(
			kaleoInstanceId, orderByComparator);
	}

	/**
	 * Returns the last kaleo instance token in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance token, or <code>null</code> if a matching kaleo instance token could not be found
	 */
	public static KaleoInstanceToken fetchByKaleoInstanceId_Last(
		long kaleoInstanceId,
		OrderByComparator<KaleoInstanceToken> orderByComparator) {

		return getPersistence().fetchByKaleoInstanceId_Last(
			kaleoInstanceId, orderByComparator);
	}

	/**
	 * Returns the kaleo instance tokens before and after the current kaleo instance token in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceTokenId the primary key of the current kaleo instance token
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo instance token
	 * @throws NoSuchInstanceTokenException if a kaleo instance token with the primary key could not be found
	 */
	public static KaleoInstanceToken[] findByKaleoInstanceId_PrevAndNext(
			long kaleoInstanceTokenId, long kaleoInstanceId,
			OrderByComparator<KaleoInstanceToken> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchInstanceTokenException {

		return getPersistence().findByKaleoInstanceId_PrevAndNext(
			kaleoInstanceTokenId, kaleoInstanceId, orderByComparator);
	}

	/**
	 * Removes all the kaleo instance tokens where kaleoInstanceId = &#63; from the database.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 */
	public static void removeByKaleoInstanceId(long kaleoInstanceId) {
		getPersistence().removeByKaleoInstanceId(kaleoInstanceId);
	}

	/**
	 * Returns the number of kaleo instance tokens where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @return the number of matching kaleo instance tokens
	 */
	public static int countByKaleoInstanceId(long kaleoInstanceId) {
		return getPersistence().countByKaleoInstanceId(kaleoInstanceId);
	}

	/**
	 * Returns all the kaleo instance tokens where companyId = &#63; and parentKaleoInstanceTokenId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @return the matching kaleo instance tokens
	 */
	public static List<KaleoInstanceToken> findByC_PKITI(
		long companyId, long parentKaleoInstanceTokenId) {

		return getPersistence().findByC_PKITI(
			companyId, parentKaleoInstanceTokenId);
	}

	/**
	 * Returns a range of all the kaleo instance tokens where companyId = &#63; and parentKaleoInstanceTokenId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @param start the lower bound of the range of kaleo instance tokens
	 * @param end the upper bound of the range of kaleo instance tokens (not inclusive)
	 * @return the range of matching kaleo instance tokens
	 */
	public static List<KaleoInstanceToken> findByC_PKITI(
		long companyId, long parentKaleoInstanceTokenId, int start, int end) {

		return getPersistence().findByC_PKITI(
			companyId, parentKaleoInstanceTokenId, start, end);
	}

	/**
	 * Returns an ordered range of all the kaleo instance tokens where companyId = &#63; and parentKaleoInstanceTokenId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @param start the lower bound of the range of kaleo instance tokens
	 * @param end the upper bound of the range of kaleo instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo instance tokens
	 */
	public static List<KaleoInstanceToken> findByC_PKITI(
		long companyId, long parentKaleoInstanceTokenId, int start, int end,
		OrderByComparator<KaleoInstanceToken> orderByComparator) {

		return getPersistence().findByC_PKITI(
			companyId, parentKaleoInstanceTokenId, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the kaleo instance tokens where companyId = &#63; and parentKaleoInstanceTokenId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @param start the lower bound of the range of kaleo instance tokens
	 * @param end the upper bound of the range of kaleo instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo instance tokens
	 */
	public static List<KaleoInstanceToken> findByC_PKITI(
		long companyId, long parentKaleoInstanceTokenId, int start, int end,
		OrderByComparator<KaleoInstanceToken> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_PKITI(
			companyId, parentKaleoInstanceTokenId, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first kaleo instance token in the ordered set where companyId = &#63; and parentKaleoInstanceTokenId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance token
	 * @throws NoSuchInstanceTokenException if a matching kaleo instance token could not be found
	 */
	public static KaleoInstanceToken findByC_PKITI_First(
			long companyId, long parentKaleoInstanceTokenId,
			OrderByComparator<KaleoInstanceToken> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchInstanceTokenException {

		return getPersistence().findByC_PKITI_First(
			companyId, parentKaleoInstanceTokenId, orderByComparator);
	}

	/**
	 * Returns the first kaleo instance token in the ordered set where companyId = &#63; and parentKaleoInstanceTokenId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance token, or <code>null</code> if a matching kaleo instance token could not be found
	 */
	public static KaleoInstanceToken fetchByC_PKITI_First(
		long companyId, long parentKaleoInstanceTokenId,
		OrderByComparator<KaleoInstanceToken> orderByComparator) {

		return getPersistence().fetchByC_PKITI_First(
			companyId, parentKaleoInstanceTokenId, orderByComparator);
	}

	/**
	 * Returns the last kaleo instance token in the ordered set where companyId = &#63; and parentKaleoInstanceTokenId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance token
	 * @throws NoSuchInstanceTokenException if a matching kaleo instance token could not be found
	 */
	public static KaleoInstanceToken findByC_PKITI_Last(
			long companyId, long parentKaleoInstanceTokenId,
			OrderByComparator<KaleoInstanceToken> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchInstanceTokenException {

		return getPersistence().findByC_PKITI_Last(
			companyId, parentKaleoInstanceTokenId, orderByComparator);
	}

	/**
	 * Returns the last kaleo instance token in the ordered set where companyId = &#63; and parentKaleoInstanceTokenId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance token, or <code>null</code> if a matching kaleo instance token could not be found
	 */
	public static KaleoInstanceToken fetchByC_PKITI_Last(
		long companyId, long parentKaleoInstanceTokenId,
		OrderByComparator<KaleoInstanceToken> orderByComparator) {

		return getPersistence().fetchByC_PKITI_Last(
			companyId, parentKaleoInstanceTokenId, orderByComparator);
	}

	/**
	 * Returns the kaleo instance tokens before and after the current kaleo instance token in the ordered set where companyId = &#63; and parentKaleoInstanceTokenId = &#63;.
	 *
	 * @param kaleoInstanceTokenId the primary key of the current kaleo instance token
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo instance token
	 * @throws NoSuchInstanceTokenException if a kaleo instance token with the primary key could not be found
	 */
	public static KaleoInstanceToken[] findByC_PKITI_PrevAndNext(
			long kaleoInstanceTokenId, long companyId,
			long parentKaleoInstanceTokenId,
			OrderByComparator<KaleoInstanceToken> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchInstanceTokenException {

		return getPersistence().findByC_PKITI_PrevAndNext(
			kaleoInstanceTokenId, companyId, parentKaleoInstanceTokenId,
			orderByComparator);
	}

	/**
	 * Removes all the kaleo instance tokens where companyId = &#63; and parentKaleoInstanceTokenId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 */
	public static void removeByC_PKITI(
		long companyId, long parentKaleoInstanceTokenId) {

		getPersistence().removeByC_PKITI(companyId, parentKaleoInstanceTokenId);
	}

	/**
	 * Returns the number of kaleo instance tokens where companyId = &#63; and parentKaleoInstanceTokenId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @return the number of matching kaleo instance tokens
	 */
	public static int countByC_PKITI(
		long companyId, long parentKaleoInstanceTokenId) {

		return getPersistence().countByC_PKITI(
			companyId, parentKaleoInstanceTokenId);
	}

	/**
	 * Returns all the kaleo instance tokens where companyId = &#63; and parentKaleoInstanceTokenId = &#63; and completionDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @param completionDate the completion date
	 * @return the matching kaleo instance tokens
	 */
	public static List<KaleoInstanceToken> findByC_PKITI_CD(
		long companyId, long parentKaleoInstanceTokenId, Date completionDate) {

		return getPersistence().findByC_PKITI_CD(
			companyId, parentKaleoInstanceTokenId, completionDate);
	}

	/**
	 * Returns a range of all the kaleo instance tokens where companyId = &#63; and parentKaleoInstanceTokenId = &#63; and completionDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @param completionDate the completion date
	 * @param start the lower bound of the range of kaleo instance tokens
	 * @param end the upper bound of the range of kaleo instance tokens (not inclusive)
	 * @return the range of matching kaleo instance tokens
	 */
	public static List<KaleoInstanceToken> findByC_PKITI_CD(
		long companyId, long parentKaleoInstanceTokenId, Date completionDate,
		int start, int end) {

		return getPersistence().findByC_PKITI_CD(
			companyId, parentKaleoInstanceTokenId, completionDate, start, end);
	}

	/**
	 * Returns an ordered range of all the kaleo instance tokens where companyId = &#63; and parentKaleoInstanceTokenId = &#63; and completionDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @param completionDate the completion date
	 * @param start the lower bound of the range of kaleo instance tokens
	 * @param end the upper bound of the range of kaleo instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo instance tokens
	 */
	public static List<KaleoInstanceToken> findByC_PKITI_CD(
		long companyId, long parentKaleoInstanceTokenId, Date completionDate,
		int start, int end,
		OrderByComparator<KaleoInstanceToken> orderByComparator) {

		return getPersistence().findByC_PKITI_CD(
			companyId, parentKaleoInstanceTokenId, completionDate, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the kaleo instance tokens where companyId = &#63; and parentKaleoInstanceTokenId = &#63; and completionDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @param completionDate the completion date
	 * @param start the lower bound of the range of kaleo instance tokens
	 * @param end the upper bound of the range of kaleo instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo instance tokens
	 */
	public static List<KaleoInstanceToken> findByC_PKITI_CD(
		long companyId, long parentKaleoInstanceTokenId, Date completionDate,
		int start, int end,
		OrderByComparator<KaleoInstanceToken> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_PKITI_CD(
			companyId, parentKaleoInstanceTokenId, completionDate, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first kaleo instance token in the ordered set where companyId = &#63; and parentKaleoInstanceTokenId = &#63; and completionDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @param completionDate the completion date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance token
	 * @throws NoSuchInstanceTokenException if a matching kaleo instance token could not be found
	 */
	public static KaleoInstanceToken findByC_PKITI_CD_First(
			long companyId, long parentKaleoInstanceTokenId,
			Date completionDate,
			OrderByComparator<KaleoInstanceToken> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchInstanceTokenException {

		return getPersistence().findByC_PKITI_CD_First(
			companyId, parentKaleoInstanceTokenId, completionDate,
			orderByComparator);
	}

	/**
	 * Returns the first kaleo instance token in the ordered set where companyId = &#63; and parentKaleoInstanceTokenId = &#63; and completionDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @param completionDate the completion date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance token, or <code>null</code> if a matching kaleo instance token could not be found
	 */
	public static KaleoInstanceToken fetchByC_PKITI_CD_First(
		long companyId, long parentKaleoInstanceTokenId, Date completionDate,
		OrderByComparator<KaleoInstanceToken> orderByComparator) {

		return getPersistence().fetchByC_PKITI_CD_First(
			companyId, parentKaleoInstanceTokenId, completionDate,
			orderByComparator);
	}

	/**
	 * Returns the last kaleo instance token in the ordered set where companyId = &#63; and parentKaleoInstanceTokenId = &#63; and completionDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @param completionDate the completion date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance token
	 * @throws NoSuchInstanceTokenException if a matching kaleo instance token could not be found
	 */
	public static KaleoInstanceToken findByC_PKITI_CD_Last(
			long companyId, long parentKaleoInstanceTokenId,
			Date completionDate,
			OrderByComparator<KaleoInstanceToken> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchInstanceTokenException {

		return getPersistence().findByC_PKITI_CD_Last(
			companyId, parentKaleoInstanceTokenId, completionDate,
			orderByComparator);
	}

	/**
	 * Returns the last kaleo instance token in the ordered set where companyId = &#63; and parentKaleoInstanceTokenId = &#63; and completionDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @param completionDate the completion date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance token, or <code>null</code> if a matching kaleo instance token could not be found
	 */
	public static KaleoInstanceToken fetchByC_PKITI_CD_Last(
		long companyId, long parentKaleoInstanceTokenId, Date completionDate,
		OrderByComparator<KaleoInstanceToken> orderByComparator) {

		return getPersistence().fetchByC_PKITI_CD_Last(
			companyId, parentKaleoInstanceTokenId, completionDate,
			orderByComparator);
	}

	/**
	 * Returns the kaleo instance tokens before and after the current kaleo instance token in the ordered set where companyId = &#63; and parentKaleoInstanceTokenId = &#63; and completionDate = &#63;.
	 *
	 * @param kaleoInstanceTokenId the primary key of the current kaleo instance token
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @param completionDate the completion date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo instance token
	 * @throws NoSuchInstanceTokenException if a kaleo instance token with the primary key could not be found
	 */
	public static KaleoInstanceToken[] findByC_PKITI_CD_PrevAndNext(
			long kaleoInstanceTokenId, long companyId,
			long parentKaleoInstanceTokenId, Date completionDate,
			OrderByComparator<KaleoInstanceToken> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchInstanceTokenException {

		return getPersistence().findByC_PKITI_CD_PrevAndNext(
			kaleoInstanceTokenId, companyId, parentKaleoInstanceTokenId,
			completionDate, orderByComparator);
	}

	/**
	 * Removes all the kaleo instance tokens where companyId = &#63; and parentKaleoInstanceTokenId = &#63; and completionDate = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @param completionDate the completion date
	 */
	public static void removeByC_PKITI_CD(
		long companyId, long parentKaleoInstanceTokenId, Date completionDate) {

		getPersistence().removeByC_PKITI_CD(
			companyId, parentKaleoInstanceTokenId, completionDate);
	}

	/**
	 * Returns the number of kaleo instance tokens where companyId = &#63; and parentKaleoInstanceTokenId = &#63; and completionDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @param completionDate the completion date
	 * @return the number of matching kaleo instance tokens
	 */
	public static int countByC_PKITI_CD(
		long companyId, long parentKaleoInstanceTokenId, Date completionDate) {

		return getPersistence().countByC_PKITI_CD(
			companyId, parentKaleoInstanceTokenId, completionDate);
	}

	/**
	 * Caches the kaleo instance token in the entity cache if it is enabled.
	 *
	 * @param kaleoInstanceToken the kaleo instance token
	 */
	public static void cacheResult(KaleoInstanceToken kaleoInstanceToken) {
		getPersistence().cacheResult(kaleoInstanceToken);
	}

	/**
	 * Caches the kaleo instance tokens in the entity cache if it is enabled.
	 *
	 * @param kaleoInstanceTokens the kaleo instance tokens
	 */
	public static void cacheResult(
		List<KaleoInstanceToken> kaleoInstanceTokens) {

		getPersistence().cacheResult(kaleoInstanceTokens);
	}

	/**
	 * Creates a new kaleo instance token with the primary key. Does not add the kaleo instance token to the database.
	 *
	 * @param kaleoInstanceTokenId the primary key for the new kaleo instance token
	 * @return the new kaleo instance token
	 */
	public static KaleoInstanceToken create(long kaleoInstanceTokenId) {
		return getPersistence().create(kaleoInstanceTokenId);
	}

	/**
	 * Removes the kaleo instance token with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoInstanceTokenId the primary key of the kaleo instance token
	 * @return the kaleo instance token that was removed
	 * @throws NoSuchInstanceTokenException if a kaleo instance token with the primary key could not be found
	 */
	public static KaleoInstanceToken remove(long kaleoInstanceTokenId)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchInstanceTokenException {

		return getPersistence().remove(kaleoInstanceTokenId);
	}

	public static KaleoInstanceToken updateImpl(
		KaleoInstanceToken kaleoInstanceToken) {

		return getPersistence().updateImpl(kaleoInstanceToken);
	}

	/**
	 * Returns the kaleo instance token with the primary key or throws a <code>NoSuchInstanceTokenException</code> if it could not be found.
	 *
	 * @param kaleoInstanceTokenId the primary key of the kaleo instance token
	 * @return the kaleo instance token
	 * @throws NoSuchInstanceTokenException if a kaleo instance token with the primary key could not be found
	 */
	public static KaleoInstanceToken findByPrimaryKey(long kaleoInstanceTokenId)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchInstanceTokenException {

		return getPersistence().findByPrimaryKey(kaleoInstanceTokenId);
	}

	/**
	 * Returns the kaleo instance token with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param kaleoInstanceTokenId the primary key of the kaleo instance token
	 * @return the kaleo instance token, or <code>null</code> if a kaleo instance token with the primary key could not be found
	 */
	public static KaleoInstanceToken fetchByPrimaryKey(
		long kaleoInstanceTokenId) {

		return getPersistence().fetchByPrimaryKey(kaleoInstanceTokenId);
	}

	/**
	 * Returns all the kaleo instance tokens.
	 *
	 * @return the kaleo instance tokens
	 */
	public static List<KaleoInstanceToken> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the kaleo instance tokens.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo instance tokens
	 * @param end the upper bound of the range of kaleo instance tokens (not inclusive)
	 * @return the range of kaleo instance tokens
	 */
	public static List<KaleoInstanceToken> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the kaleo instance tokens.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo instance tokens
	 * @param end the upper bound of the range of kaleo instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of kaleo instance tokens
	 */
	public static List<KaleoInstanceToken> findAll(
		int start, int end,
		OrderByComparator<KaleoInstanceToken> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the kaleo instance tokens.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo instance tokens
	 * @param end the upper bound of the range of kaleo instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of kaleo instance tokens
	 */
	public static List<KaleoInstanceToken> findAll(
		int start, int end,
		OrderByComparator<KaleoInstanceToken> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the kaleo instance tokens from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of kaleo instance tokens.
	 *
	 * @return the number of kaleo instance tokens
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static KaleoInstanceTokenPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<KaleoInstanceTokenPersistence, KaleoInstanceTokenPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			KaleoInstanceTokenPersistence.class);

		ServiceTracker
			<KaleoInstanceTokenPersistence, KaleoInstanceTokenPersistence>
				serviceTracker =
					new ServiceTracker
						<KaleoInstanceTokenPersistence,
						 KaleoInstanceTokenPersistence>(
							 bundle.getBundleContext(),
							 KaleoInstanceTokenPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}
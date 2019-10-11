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
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the kaleo task assignment instance service. This utility wraps <code>com.liferay.portal.workflow.kaleo.service.persistence.impl.KaleoTaskAssignmentInstancePersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTaskAssignmentInstancePersistence
 * @generated
 */
public class KaleoTaskAssignmentInstanceUtil {

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
	public static void clearCache(
		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance) {

		getPersistence().clearCache(kaleoTaskAssignmentInstance);
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
	public static Map<Serializable, KaleoTaskAssignmentInstance>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<KaleoTaskAssignmentInstance> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<KaleoTaskAssignmentInstance> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<KaleoTaskAssignmentInstance> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static KaleoTaskAssignmentInstance update(
		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance) {

		return getPersistence().update(kaleoTaskAssignmentInstance);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static KaleoTaskAssignmentInstance update(
		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance,
		ServiceContext serviceContext) {

		return getPersistence().update(
			kaleoTaskAssignmentInstance, serviceContext);
	}

	/**
	 * Returns all the kaleo task assignment instances where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching kaleo task assignment instances
	 */
	public static List<KaleoTaskAssignmentInstance> findByCompanyId(
		long companyId) {

		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the kaleo task assignment instances where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @return the range of matching kaleo task assignment instances
	 */
	public static List<KaleoTaskAssignmentInstance> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignment instances where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task assignment instances
	 */
	public static List<KaleoTaskAssignmentInstance> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignment instances where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo task assignment instances
	 */
	public static List<KaleoTaskAssignmentInstance> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first kaleo task assignment instance in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a matching kaleo task assignment instance could not be found
	 */
	public static KaleoTaskAssignmentInstance findByCompanyId_First(
			long companyId,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchTaskAssignmentInstanceException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first kaleo task assignment instance in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task assignment instance, or <code>null</code> if a matching kaleo task assignment instance could not be found
	 */
	public static KaleoTaskAssignmentInstance fetchByCompanyId_First(
		long companyId,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last kaleo task assignment instance in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a matching kaleo task assignment instance could not be found
	 */
	public static KaleoTaskAssignmentInstance findByCompanyId_Last(
			long companyId,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchTaskAssignmentInstanceException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last kaleo task assignment instance in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task assignment instance, or <code>null</code> if a matching kaleo task assignment instance could not be found
	 */
	public static KaleoTaskAssignmentInstance fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the kaleo task assignment instances before and after the current kaleo task assignment instance in the ordered set where companyId = &#63;.
	 *
	 * @param kaleoTaskAssignmentInstanceId the primary key of the current kaleo task assignment instance
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a kaleo task assignment instance with the primary key could not be found
	 */
	public static KaleoTaskAssignmentInstance[] findByCompanyId_PrevAndNext(
			long kaleoTaskAssignmentInstanceId, long companyId,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchTaskAssignmentInstanceException {

		return getPersistence().findByCompanyId_PrevAndNext(
			kaleoTaskAssignmentInstanceId, companyId, orderByComparator);
	}

	/**
	 * Removes all the kaleo task assignment instances where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of kaleo task assignment instances where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching kaleo task assignment instances
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns all the kaleo task assignment instances where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the matching kaleo task assignment instances
	 */
	public static List<KaleoTaskAssignmentInstance>
		findByKaleoDefinitionVersionId(long kaleoDefinitionVersionId) {

		return getPersistence().findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId);
	}

	/**
	 * Returns a range of all the kaleo task assignment instances where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @return the range of matching kaleo task assignment instances
	 */
	public static List<KaleoTaskAssignmentInstance>
		findByKaleoDefinitionVersionId(
			long kaleoDefinitionVersionId, int start, int end) {

		return getPersistence().findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, start, end);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignment instances where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task assignment instances
	 */
	public static List<KaleoTaskAssignmentInstance>
		findByKaleoDefinitionVersionId(
			long kaleoDefinitionVersionId, int start, int end,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		return getPersistence().findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignment instances where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo task assignment instances
	 */
	public static List<KaleoTaskAssignmentInstance>
		findByKaleoDefinitionVersionId(
			long kaleoDefinitionVersionId, int start, int end,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator,
			boolean useFinderCache) {

		return getPersistence().findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first kaleo task assignment instance in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a matching kaleo task assignment instance could not be found
	 */
	public static KaleoTaskAssignmentInstance
			findByKaleoDefinitionVersionId_First(
				long kaleoDefinitionVersionId,
				OrderByComparator<KaleoTaskAssignmentInstance>
					orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchTaskAssignmentInstanceException {

		return getPersistence().findByKaleoDefinitionVersionId_First(
			kaleoDefinitionVersionId, orderByComparator);
	}

	/**
	 * Returns the first kaleo task assignment instance in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task assignment instance, or <code>null</code> if a matching kaleo task assignment instance could not be found
	 */
	public static KaleoTaskAssignmentInstance
		fetchByKaleoDefinitionVersionId_First(
			long kaleoDefinitionVersionId,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		return getPersistence().fetchByKaleoDefinitionVersionId_First(
			kaleoDefinitionVersionId, orderByComparator);
	}

	/**
	 * Returns the last kaleo task assignment instance in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a matching kaleo task assignment instance could not be found
	 */
	public static KaleoTaskAssignmentInstance
			findByKaleoDefinitionVersionId_Last(
				long kaleoDefinitionVersionId,
				OrderByComparator<KaleoTaskAssignmentInstance>
					orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchTaskAssignmentInstanceException {

		return getPersistence().findByKaleoDefinitionVersionId_Last(
			kaleoDefinitionVersionId, orderByComparator);
	}

	/**
	 * Returns the last kaleo task assignment instance in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task assignment instance, or <code>null</code> if a matching kaleo task assignment instance could not be found
	 */
	public static KaleoTaskAssignmentInstance
		fetchByKaleoDefinitionVersionId_Last(
			long kaleoDefinitionVersionId,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		return getPersistence().fetchByKaleoDefinitionVersionId_Last(
			kaleoDefinitionVersionId, orderByComparator);
	}

	/**
	 * Returns the kaleo task assignment instances before and after the current kaleo task assignment instance in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoTaskAssignmentInstanceId the primary key of the current kaleo task assignment instance
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a kaleo task assignment instance with the primary key could not be found
	 */
	public static KaleoTaskAssignmentInstance[]
			findByKaleoDefinitionVersionId_PrevAndNext(
				long kaleoTaskAssignmentInstanceId,
				long kaleoDefinitionVersionId,
				OrderByComparator<KaleoTaskAssignmentInstance>
					orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchTaskAssignmentInstanceException {

		return getPersistence().findByKaleoDefinitionVersionId_PrevAndNext(
			kaleoTaskAssignmentInstanceId, kaleoDefinitionVersionId,
			orderByComparator);
	}

	/**
	 * Removes all the kaleo task assignment instances where kaleoDefinitionVersionId = &#63; from the database.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 */
	public static void removeByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId) {

		getPersistence().removeByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId);
	}

	/**
	 * Returns the number of kaleo task assignment instances where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the number of matching kaleo task assignment instances
	 */
	public static int countByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId) {

		return getPersistence().countByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId);
	}

	/**
	 * Returns all the kaleo task assignment instances where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @return the matching kaleo task assignment instances
	 */
	public static List<KaleoTaskAssignmentInstance> findByKaleoInstanceId(
		long kaleoInstanceId) {

		return getPersistence().findByKaleoInstanceId(kaleoInstanceId);
	}

	/**
	 * Returns a range of all the kaleo task assignment instances where kaleoInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @return the range of matching kaleo task assignment instances
	 */
	public static List<KaleoTaskAssignmentInstance> findByKaleoInstanceId(
		long kaleoInstanceId, int start, int end) {

		return getPersistence().findByKaleoInstanceId(
			kaleoInstanceId, start, end);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignment instances where kaleoInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task assignment instances
	 */
	public static List<KaleoTaskAssignmentInstance> findByKaleoInstanceId(
		long kaleoInstanceId, int start, int end,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		return getPersistence().findByKaleoInstanceId(
			kaleoInstanceId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignment instances where kaleoInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo task assignment instances
	 */
	public static List<KaleoTaskAssignmentInstance> findByKaleoInstanceId(
		long kaleoInstanceId, int start, int end,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByKaleoInstanceId(
			kaleoInstanceId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first kaleo task assignment instance in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a matching kaleo task assignment instance could not be found
	 */
	public static KaleoTaskAssignmentInstance findByKaleoInstanceId_First(
			long kaleoInstanceId,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchTaskAssignmentInstanceException {

		return getPersistence().findByKaleoInstanceId_First(
			kaleoInstanceId, orderByComparator);
	}

	/**
	 * Returns the first kaleo task assignment instance in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task assignment instance, or <code>null</code> if a matching kaleo task assignment instance could not be found
	 */
	public static KaleoTaskAssignmentInstance fetchByKaleoInstanceId_First(
		long kaleoInstanceId,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		return getPersistence().fetchByKaleoInstanceId_First(
			kaleoInstanceId, orderByComparator);
	}

	/**
	 * Returns the last kaleo task assignment instance in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a matching kaleo task assignment instance could not be found
	 */
	public static KaleoTaskAssignmentInstance findByKaleoInstanceId_Last(
			long kaleoInstanceId,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchTaskAssignmentInstanceException {

		return getPersistence().findByKaleoInstanceId_Last(
			kaleoInstanceId, orderByComparator);
	}

	/**
	 * Returns the last kaleo task assignment instance in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task assignment instance, or <code>null</code> if a matching kaleo task assignment instance could not be found
	 */
	public static KaleoTaskAssignmentInstance fetchByKaleoInstanceId_Last(
		long kaleoInstanceId,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		return getPersistence().fetchByKaleoInstanceId_Last(
			kaleoInstanceId, orderByComparator);
	}

	/**
	 * Returns the kaleo task assignment instances before and after the current kaleo task assignment instance in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoTaskAssignmentInstanceId the primary key of the current kaleo task assignment instance
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a kaleo task assignment instance with the primary key could not be found
	 */
	public static KaleoTaskAssignmentInstance[]
			findByKaleoInstanceId_PrevAndNext(
				long kaleoTaskAssignmentInstanceId, long kaleoInstanceId,
				OrderByComparator<KaleoTaskAssignmentInstance>
					orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchTaskAssignmentInstanceException {

		return getPersistence().findByKaleoInstanceId_PrevAndNext(
			kaleoTaskAssignmentInstanceId, kaleoInstanceId, orderByComparator);
	}

	/**
	 * Removes all the kaleo task assignment instances where kaleoInstanceId = &#63; from the database.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 */
	public static void removeByKaleoInstanceId(long kaleoInstanceId) {
		getPersistence().removeByKaleoInstanceId(kaleoInstanceId);
	}

	/**
	 * Returns the number of kaleo task assignment instances where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @return the number of matching kaleo task assignment instances
	 */
	public static int countByKaleoInstanceId(long kaleoInstanceId) {
		return getPersistence().countByKaleoInstanceId(kaleoInstanceId);
	}

	/**
	 * Returns all the kaleo task assignment instances where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @return the matching kaleo task assignment instances
	 */
	public static List<KaleoTaskAssignmentInstance>
		findBykaleoTaskInstanceTokenId(long kaleoTaskInstanceTokenId) {

		return getPersistence().findBykaleoTaskInstanceTokenId(
			kaleoTaskInstanceTokenId);
	}

	/**
	 * Returns a range of all the kaleo task assignment instances where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @return the range of matching kaleo task assignment instances
	 */
	public static List<KaleoTaskAssignmentInstance>
		findBykaleoTaskInstanceTokenId(
			long kaleoTaskInstanceTokenId, int start, int end) {

		return getPersistence().findBykaleoTaskInstanceTokenId(
			kaleoTaskInstanceTokenId, start, end);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignment instances where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task assignment instances
	 */
	public static List<KaleoTaskAssignmentInstance>
		findBykaleoTaskInstanceTokenId(
			long kaleoTaskInstanceTokenId, int start, int end,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		return getPersistence().findBykaleoTaskInstanceTokenId(
			kaleoTaskInstanceTokenId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignment instances where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo task assignment instances
	 */
	public static List<KaleoTaskAssignmentInstance>
		findBykaleoTaskInstanceTokenId(
			long kaleoTaskInstanceTokenId, int start, int end,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator,
			boolean useFinderCache) {

		return getPersistence().findBykaleoTaskInstanceTokenId(
			kaleoTaskInstanceTokenId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first kaleo task assignment instance in the ordered set where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a matching kaleo task assignment instance could not be found
	 */
	public static KaleoTaskAssignmentInstance
			findBykaleoTaskInstanceTokenId_First(
				long kaleoTaskInstanceTokenId,
				OrderByComparator<KaleoTaskAssignmentInstance>
					orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchTaskAssignmentInstanceException {

		return getPersistence().findBykaleoTaskInstanceTokenId_First(
			kaleoTaskInstanceTokenId, orderByComparator);
	}

	/**
	 * Returns the first kaleo task assignment instance in the ordered set where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task assignment instance, or <code>null</code> if a matching kaleo task assignment instance could not be found
	 */
	public static KaleoTaskAssignmentInstance
		fetchBykaleoTaskInstanceTokenId_First(
			long kaleoTaskInstanceTokenId,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		return getPersistence().fetchBykaleoTaskInstanceTokenId_First(
			kaleoTaskInstanceTokenId, orderByComparator);
	}

	/**
	 * Returns the last kaleo task assignment instance in the ordered set where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a matching kaleo task assignment instance could not be found
	 */
	public static KaleoTaskAssignmentInstance
			findBykaleoTaskInstanceTokenId_Last(
				long kaleoTaskInstanceTokenId,
				OrderByComparator<KaleoTaskAssignmentInstance>
					orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchTaskAssignmentInstanceException {

		return getPersistence().findBykaleoTaskInstanceTokenId_Last(
			kaleoTaskInstanceTokenId, orderByComparator);
	}

	/**
	 * Returns the last kaleo task assignment instance in the ordered set where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task assignment instance, or <code>null</code> if a matching kaleo task assignment instance could not be found
	 */
	public static KaleoTaskAssignmentInstance
		fetchBykaleoTaskInstanceTokenId_Last(
			long kaleoTaskInstanceTokenId,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		return getPersistence().fetchBykaleoTaskInstanceTokenId_Last(
			kaleoTaskInstanceTokenId, orderByComparator);
	}

	/**
	 * Returns the kaleo task assignment instances before and after the current kaleo task assignment instance in the ordered set where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * @param kaleoTaskAssignmentInstanceId the primary key of the current kaleo task assignment instance
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a kaleo task assignment instance with the primary key could not be found
	 */
	public static KaleoTaskAssignmentInstance[]
			findBykaleoTaskInstanceTokenId_PrevAndNext(
				long kaleoTaskAssignmentInstanceId,
				long kaleoTaskInstanceTokenId,
				OrderByComparator<KaleoTaskAssignmentInstance>
					orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchTaskAssignmentInstanceException {

		return getPersistence().findBykaleoTaskInstanceTokenId_PrevAndNext(
			kaleoTaskAssignmentInstanceId, kaleoTaskInstanceTokenId,
			orderByComparator);
	}

	/**
	 * Removes all the kaleo task assignment instances where kaleoTaskInstanceTokenId = &#63; from the database.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 */
	public static void removeBykaleoTaskInstanceTokenId(
		long kaleoTaskInstanceTokenId) {

		getPersistence().removeBykaleoTaskInstanceTokenId(
			kaleoTaskInstanceTokenId);
	}

	/**
	 * Returns the number of kaleo task assignment instances where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @return the number of matching kaleo task assignment instances
	 */
	public static int countBykaleoTaskInstanceTokenId(
		long kaleoTaskInstanceTokenId) {

		return getPersistence().countBykaleoTaskInstanceTokenId(
			kaleoTaskInstanceTokenId);
	}

	/**
	 * Returns all the kaleo task assignment instances where assigneeClassName = &#63;.
	 *
	 * @param assigneeClassName the assignee class name
	 * @return the matching kaleo task assignment instances
	 */
	public static List<KaleoTaskAssignmentInstance> findByassigneeClassName(
		String assigneeClassName) {

		return getPersistence().findByassigneeClassName(assigneeClassName);
	}

	/**
	 * Returns a range of all the kaleo task assignment instances where assigneeClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param assigneeClassName the assignee class name
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @return the range of matching kaleo task assignment instances
	 */
	public static List<KaleoTaskAssignmentInstance> findByassigneeClassName(
		String assigneeClassName, int start, int end) {

		return getPersistence().findByassigneeClassName(
			assigneeClassName, start, end);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignment instances where assigneeClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param assigneeClassName the assignee class name
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task assignment instances
	 */
	public static List<KaleoTaskAssignmentInstance> findByassigneeClassName(
		String assigneeClassName, int start, int end,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		return getPersistence().findByassigneeClassName(
			assigneeClassName, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignment instances where assigneeClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param assigneeClassName the assignee class name
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo task assignment instances
	 */
	public static List<KaleoTaskAssignmentInstance> findByassigneeClassName(
		String assigneeClassName, int start, int end,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByassigneeClassName(
			assigneeClassName, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first kaleo task assignment instance in the ordered set where assigneeClassName = &#63;.
	 *
	 * @param assigneeClassName the assignee class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a matching kaleo task assignment instance could not be found
	 */
	public static KaleoTaskAssignmentInstance findByassigneeClassName_First(
			String assigneeClassName,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchTaskAssignmentInstanceException {

		return getPersistence().findByassigneeClassName_First(
			assigneeClassName, orderByComparator);
	}

	/**
	 * Returns the first kaleo task assignment instance in the ordered set where assigneeClassName = &#63;.
	 *
	 * @param assigneeClassName the assignee class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task assignment instance, or <code>null</code> if a matching kaleo task assignment instance could not be found
	 */
	public static KaleoTaskAssignmentInstance fetchByassigneeClassName_First(
		String assigneeClassName,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		return getPersistence().fetchByassigneeClassName_First(
			assigneeClassName, orderByComparator);
	}

	/**
	 * Returns the last kaleo task assignment instance in the ordered set where assigneeClassName = &#63;.
	 *
	 * @param assigneeClassName the assignee class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a matching kaleo task assignment instance could not be found
	 */
	public static KaleoTaskAssignmentInstance findByassigneeClassName_Last(
			String assigneeClassName,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchTaskAssignmentInstanceException {

		return getPersistence().findByassigneeClassName_Last(
			assigneeClassName, orderByComparator);
	}

	/**
	 * Returns the last kaleo task assignment instance in the ordered set where assigneeClassName = &#63;.
	 *
	 * @param assigneeClassName the assignee class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task assignment instance, or <code>null</code> if a matching kaleo task assignment instance could not be found
	 */
	public static KaleoTaskAssignmentInstance fetchByassigneeClassName_Last(
		String assigneeClassName,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		return getPersistence().fetchByassigneeClassName_Last(
			assigneeClassName, orderByComparator);
	}

	/**
	 * Returns the kaleo task assignment instances before and after the current kaleo task assignment instance in the ordered set where assigneeClassName = &#63;.
	 *
	 * @param kaleoTaskAssignmentInstanceId the primary key of the current kaleo task assignment instance
	 * @param assigneeClassName the assignee class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a kaleo task assignment instance with the primary key could not be found
	 */
	public static KaleoTaskAssignmentInstance[]
			findByassigneeClassName_PrevAndNext(
				long kaleoTaskAssignmentInstanceId, String assigneeClassName,
				OrderByComparator<KaleoTaskAssignmentInstance>
					orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchTaskAssignmentInstanceException {

		return getPersistence().findByassigneeClassName_PrevAndNext(
			kaleoTaskAssignmentInstanceId, assigneeClassName,
			orderByComparator);
	}

	/**
	 * Removes all the kaleo task assignment instances where assigneeClassName = &#63; from the database.
	 *
	 * @param assigneeClassName the assignee class name
	 */
	public static void removeByassigneeClassName(String assigneeClassName) {
		getPersistence().removeByassigneeClassName(assigneeClassName);
	}

	/**
	 * Returns the number of kaleo task assignment instances where assigneeClassName = &#63;.
	 *
	 * @param assigneeClassName the assignee class name
	 * @return the number of matching kaleo task assignment instances
	 */
	public static int countByassigneeClassName(String assigneeClassName) {
		return getPersistence().countByassigneeClassName(assigneeClassName);
	}

	/**
	 * Returns all the kaleo task assignment instances where groupId = &#63; and assigneeClassPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param assigneeClassPK the assignee class pk
	 * @return the matching kaleo task assignment instances
	 */
	public static List<KaleoTaskAssignmentInstance> findByG_ACPK(
		long groupId, long assigneeClassPK) {

		return getPersistence().findByG_ACPK(groupId, assigneeClassPK);
	}

	/**
	 * Returns a range of all the kaleo task assignment instances where groupId = &#63; and assigneeClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param assigneeClassPK the assignee class pk
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @return the range of matching kaleo task assignment instances
	 */
	public static List<KaleoTaskAssignmentInstance> findByG_ACPK(
		long groupId, long assigneeClassPK, int start, int end) {

		return getPersistence().findByG_ACPK(
			groupId, assigneeClassPK, start, end);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignment instances where groupId = &#63; and assigneeClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param assigneeClassPK the assignee class pk
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task assignment instances
	 */
	public static List<KaleoTaskAssignmentInstance> findByG_ACPK(
		long groupId, long assigneeClassPK, int start, int end,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		return getPersistence().findByG_ACPK(
			groupId, assigneeClassPK, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignment instances where groupId = &#63; and assigneeClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param assigneeClassPK the assignee class pk
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo task assignment instances
	 */
	public static List<KaleoTaskAssignmentInstance> findByG_ACPK(
		long groupId, long assigneeClassPK, int start, int end,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_ACPK(
			groupId, assigneeClassPK, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first kaleo task assignment instance in the ordered set where groupId = &#63; and assigneeClassPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param assigneeClassPK the assignee class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a matching kaleo task assignment instance could not be found
	 */
	public static KaleoTaskAssignmentInstance findByG_ACPK_First(
			long groupId, long assigneeClassPK,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchTaskAssignmentInstanceException {

		return getPersistence().findByG_ACPK_First(
			groupId, assigneeClassPK, orderByComparator);
	}

	/**
	 * Returns the first kaleo task assignment instance in the ordered set where groupId = &#63; and assigneeClassPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param assigneeClassPK the assignee class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task assignment instance, or <code>null</code> if a matching kaleo task assignment instance could not be found
	 */
	public static KaleoTaskAssignmentInstance fetchByG_ACPK_First(
		long groupId, long assigneeClassPK,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		return getPersistence().fetchByG_ACPK_First(
			groupId, assigneeClassPK, orderByComparator);
	}

	/**
	 * Returns the last kaleo task assignment instance in the ordered set where groupId = &#63; and assigneeClassPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param assigneeClassPK the assignee class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a matching kaleo task assignment instance could not be found
	 */
	public static KaleoTaskAssignmentInstance findByG_ACPK_Last(
			long groupId, long assigneeClassPK,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchTaskAssignmentInstanceException {

		return getPersistence().findByG_ACPK_Last(
			groupId, assigneeClassPK, orderByComparator);
	}

	/**
	 * Returns the last kaleo task assignment instance in the ordered set where groupId = &#63; and assigneeClassPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param assigneeClassPK the assignee class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task assignment instance, or <code>null</code> if a matching kaleo task assignment instance could not be found
	 */
	public static KaleoTaskAssignmentInstance fetchByG_ACPK_Last(
		long groupId, long assigneeClassPK,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		return getPersistence().fetchByG_ACPK_Last(
			groupId, assigneeClassPK, orderByComparator);
	}

	/**
	 * Returns the kaleo task assignment instances before and after the current kaleo task assignment instance in the ordered set where groupId = &#63; and assigneeClassPK = &#63;.
	 *
	 * @param kaleoTaskAssignmentInstanceId the primary key of the current kaleo task assignment instance
	 * @param groupId the group ID
	 * @param assigneeClassPK the assignee class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a kaleo task assignment instance with the primary key could not be found
	 */
	public static KaleoTaskAssignmentInstance[] findByG_ACPK_PrevAndNext(
			long kaleoTaskAssignmentInstanceId, long groupId,
			long assigneeClassPK,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchTaskAssignmentInstanceException {

		return getPersistence().findByG_ACPK_PrevAndNext(
			kaleoTaskAssignmentInstanceId, groupId, assigneeClassPK,
			orderByComparator);
	}

	/**
	 * Removes all the kaleo task assignment instances where groupId = &#63; and assigneeClassPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param assigneeClassPK the assignee class pk
	 */
	public static void removeByG_ACPK(long groupId, long assigneeClassPK) {
		getPersistence().removeByG_ACPK(groupId, assigneeClassPK);
	}

	/**
	 * Returns the number of kaleo task assignment instances where groupId = &#63; and assigneeClassPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param assigneeClassPK the assignee class pk
	 * @return the number of matching kaleo task assignment instances
	 */
	public static int countByG_ACPK(long groupId, long assigneeClassPK) {
		return getPersistence().countByG_ACPK(groupId, assigneeClassPK);
	}

	/**
	 * Returns all the kaleo task assignment instances where kaleoTaskInstanceTokenId = &#63; and assigneeClassName = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param assigneeClassName the assignee class name
	 * @return the matching kaleo task assignment instances
	 */
	public static List<KaleoTaskAssignmentInstance> findByKTITI_ACN(
		long kaleoTaskInstanceTokenId, String assigneeClassName) {

		return getPersistence().findByKTITI_ACN(
			kaleoTaskInstanceTokenId, assigneeClassName);
	}

	/**
	 * Returns a range of all the kaleo task assignment instances where kaleoTaskInstanceTokenId = &#63; and assigneeClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param assigneeClassName the assignee class name
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @return the range of matching kaleo task assignment instances
	 */
	public static List<KaleoTaskAssignmentInstance> findByKTITI_ACN(
		long kaleoTaskInstanceTokenId, String assigneeClassName, int start,
		int end) {

		return getPersistence().findByKTITI_ACN(
			kaleoTaskInstanceTokenId, assigneeClassName, start, end);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignment instances where kaleoTaskInstanceTokenId = &#63; and assigneeClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param assigneeClassName the assignee class name
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task assignment instances
	 */
	public static List<KaleoTaskAssignmentInstance> findByKTITI_ACN(
		long kaleoTaskInstanceTokenId, String assigneeClassName, int start,
		int end,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		return getPersistence().findByKTITI_ACN(
			kaleoTaskInstanceTokenId, assigneeClassName, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignment instances where kaleoTaskInstanceTokenId = &#63; and assigneeClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param assigneeClassName the assignee class name
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo task assignment instances
	 */
	public static List<KaleoTaskAssignmentInstance> findByKTITI_ACN(
		long kaleoTaskInstanceTokenId, String assigneeClassName, int start,
		int end,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByKTITI_ACN(
			kaleoTaskInstanceTokenId, assigneeClassName, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first kaleo task assignment instance in the ordered set where kaleoTaskInstanceTokenId = &#63; and assigneeClassName = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param assigneeClassName the assignee class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a matching kaleo task assignment instance could not be found
	 */
	public static KaleoTaskAssignmentInstance findByKTITI_ACN_First(
			long kaleoTaskInstanceTokenId, String assigneeClassName,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchTaskAssignmentInstanceException {

		return getPersistence().findByKTITI_ACN_First(
			kaleoTaskInstanceTokenId, assigneeClassName, orderByComparator);
	}

	/**
	 * Returns the first kaleo task assignment instance in the ordered set where kaleoTaskInstanceTokenId = &#63; and assigneeClassName = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param assigneeClassName the assignee class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task assignment instance, or <code>null</code> if a matching kaleo task assignment instance could not be found
	 */
	public static KaleoTaskAssignmentInstance fetchByKTITI_ACN_First(
		long kaleoTaskInstanceTokenId, String assigneeClassName,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		return getPersistence().fetchByKTITI_ACN_First(
			kaleoTaskInstanceTokenId, assigneeClassName, orderByComparator);
	}

	/**
	 * Returns the last kaleo task assignment instance in the ordered set where kaleoTaskInstanceTokenId = &#63; and assigneeClassName = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param assigneeClassName the assignee class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a matching kaleo task assignment instance could not be found
	 */
	public static KaleoTaskAssignmentInstance findByKTITI_ACN_Last(
			long kaleoTaskInstanceTokenId, String assigneeClassName,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchTaskAssignmentInstanceException {

		return getPersistence().findByKTITI_ACN_Last(
			kaleoTaskInstanceTokenId, assigneeClassName, orderByComparator);
	}

	/**
	 * Returns the last kaleo task assignment instance in the ordered set where kaleoTaskInstanceTokenId = &#63; and assigneeClassName = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param assigneeClassName the assignee class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task assignment instance, or <code>null</code> if a matching kaleo task assignment instance could not be found
	 */
	public static KaleoTaskAssignmentInstance fetchByKTITI_ACN_Last(
		long kaleoTaskInstanceTokenId, String assigneeClassName,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		return getPersistence().fetchByKTITI_ACN_Last(
			kaleoTaskInstanceTokenId, assigneeClassName, orderByComparator);
	}

	/**
	 * Returns the kaleo task assignment instances before and after the current kaleo task assignment instance in the ordered set where kaleoTaskInstanceTokenId = &#63; and assigneeClassName = &#63;.
	 *
	 * @param kaleoTaskAssignmentInstanceId the primary key of the current kaleo task assignment instance
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param assigneeClassName the assignee class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a kaleo task assignment instance with the primary key could not be found
	 */
	public static KaleoTaskAssignmentInstance[] findByKTITI_ACN_PrevAndNext(
			long kaleoTaskAssignmentInstanceId, long kaleoTaskInstanceTokenId,
			String assigneeClassName,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchTaskAssignmentInstanceException {

		return getPersistence().findByKTITI_ACN_PrevAndNext(
			kaleoTaskAssignmentInstanceId, kaleoTaskInstanceTokenId,
			assigneeClassName, orderByComparator);
	}

	/**
	 * Removes all the kaleo task assignment instances where kaleoTaskInstanceTokenId = &#63; and assigneeClassName = &#63; from the database.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param assigneeClassName the assignee class name
	 */
	public static void removeByKTITI_ACN(
		long kaleoTaskInstanceTokenId, String assigneeClassName) {

		getPersistence().removeByKTITI_ACN(
			kaleoTaskInstanceTokenId, assigneeClassName);
	}

	/**
	 * Returns the number of kaleo task assignment instances where kaleoTaskInstanceTokenId = &#63; and assigneeClassName = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param assigneeClassName the assignee class name
	 * @return the number of matching kaleo task assignment instances
	 */
	public static int countByKTITI_ACN(
		long kaleoTaskInstanceTokenId, String assigneeClassName) {

		return getPersistence().countByKTITI_ACN(
			kaleoTaskInstanceTokenId, assigneeClassName);
	}

	/**
	 * Returns all the kaleo task assignment instances where assigneeClassName = &#63; and assigneeClassPK = &#63;.
	 *
	 * @param assigneeClassName the assignee class name
	 * @param assigneeClassPK the assignee class pk
	 * @return the matching kaleo task assignment instances
	 */
	public static List<KaleoTaskAssignmentInstance> findByACN_ACPK(
		String assigneeClassName, long assigneeClassPK) {

		return getPersistence().findByACN_ACPK(
			assigneeClassName, assigneeClassPK);
	}

	/**
	 * Returns a range of all the kaleo task assignment instances where assigneeClassName = &#63; and assigneeClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param assigneeClassName the assignee class name
	 * @param assigneeClassPK the assignee class pk
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @return the range of matching kaleo task assignment instances
	 */
	public static List<KaleoTaskAssignmentInstance> findByACN_ACPK(
		String assigneeClassName, long assigneeClassPK, int start, int end) {

		return getPersistence().findByACN_ACPK(
			assigneeClassName, assigneeClassPK, start, end);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignment instances where assigneeClassName = &#63; and assigneeClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param assigneeClassName the assignee class name
	 * @param assigneeClassPK the assignee class pk
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task assignment instances
	 */
	public static List<KaleoTaskAssignmentInstance> findByACN_ACPK(
		String assigneeClassName, long assigneeClassPK, int start, int end,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		return getPersistence().findByACN_ACPK(
			assigneeClassName, assigneeClassPK, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignment instances where assigneeClassName = &#63; and assigneeClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param assigneeClassName the assignee class name
	 * @param assigneeClassPK the assignee class pk
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo task assignment instances
	 */
	public static List<KaleoTaskAssignmentInstance> findByACN_ACPK(
		String assigneeClassName, long assigneeClassPK, int start, int end,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByACN_ACPK(
			assigneeClassName, assigneeClassPK, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first kaleo task assignment instance in the ordered set where assigneeClassName = &#63; and assigneeClassPK = &#63;.
	 *
	 * @param assigneeClassName the assignee class name
	 * @param assigneeClassPK the assignee class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a matching kaleo task assignment instance could not be found
	 */
	public static KaleoTaskAssignmentInstance findByACN_ACPK_First(
			String assigneeClassName, long assigneeClassPK,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchTaskAssignmentInstanceException {

		return getPersistence().findByACN_ACPK_First(
			assigneeClassName, assigneeClassPK, orderByComparator);
	}

	/**
	 * Returns the first kaleo task assignment instance in the ordered set where assigneeClassName = &#63; and assigneeClassPK = &#63;.
	 *
	 * @param assigneeClassName the assignee class name
	 * @param assigneeClassPK the assignee class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task assignment instance, or <code>null</code> if a matching kaleo task assignment instance could not be found
	 */
	public static KaleoTaskAssignmentInstance fetchByACN_ACPK_First(
		String assigneeClassName, long assigneeClassPK,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		return getPersistence().fetchByACN_ACPK_First(
			assigneeClassName, assigneeClassPK, orderByComparator);
	}

	/**
	 * Returns the last kaleo task assignment instance in the ordered set where assigneeClassName = &#63; and assigneeClassPK = &#63;.
	 *
	 * @param assigneeClassName the assignee class name
	 * @param assigneeClassPK the assignee class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a matching kaleo task assignment instance could not be found
	 */
	public static KaleoTaskAssignmentInstance findByACN_ACPK_Last(
			String assigneeClassName, long assigneeClassPK,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchTaskAssignmentInstanceException {

		return getPersistence().findByACN_ACPK_Last(
			assigneeClassName, assigneeClassPK, orderByComparator);
	}

	/**
	 * Returns the last kaleo task assignment instance in the ordered set where assigneeClassName = &#63; and assigneeClassPK = &#63;.
	 *
	 * @param assigneeClassName the assignee class name
	 * @param assigneeClassPK the assignee class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task assignment instance, or <code>null</code> if a matching kaleo task assignment instance could not be found
	 */
	public static KaleoTaskAssignmentInstance fetchByACN_ACPK_Last(
		String assigneeClassName, long assigneeClassPK,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		return getPersistence().fetchByACN_ACPK_Last(
			assigneeClassName, assigneeClassPK, orderByComparator);
	}

	/**
	 * Returns the kaleo task assignment instances before and after the current kaleo task assignment instance in the ordered set where assigneeClassName = &#63; and assigneeClassPK = &#63;.
	 *
	 * @param kaleoTaskAssignmentInstanceId the primary key of the current kaleo task assignment instance
	 * @param assigneeClassName the assignee class name
	 * @param assigneeClassPK the assignee class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a kaleo task assignment instance with the primary key could not be found
	 */
	public static KaleoTaskAssignmentInstance[] findByACN_ACPK_PrevAndNext(
			long kaleoTaskAssignmentInstanceId, String assigneeClassName,
			long assigneeClassPK,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchTaskAssignmentInstanceException {

		return getPersistence().findByACN_ACPK_PrevAndNext(
			kaleoTaskAssignmentInstanceId, assigneeClassName, assigneeClassPK,
			orderByComparator);
	}

	/**
	 * Removes all the kaleo task assignment instances where assigneeClassName = &#63; and assigneeClassPK = &#63; from the database.
	 *
	 * @param assigneeClassName the assignee class name
	 * @param assigneeClassPK the assignee class pk
	 */
	public static void removeByACN_ACPK(
		String assigneeClassName, long assigneeClassPK) {

		getPersistence().removeByACN_ACPK(assigneeClassName, assigneeClassPK);
	}

	/**
	 * Returns the number of kaleo task assignment instances where assigneeClassName = &#63; and assigneeClassPK = &#63;.
	 *
	 * @param assigneeClassName the assignee class name
	 * @param assigneeClassPK the assignee class pk
	 * @return the number of matching kaleo task assignment instances
	 */
	public static int countByACN_ACPK(
		String assigneeClassName, long assigneeClassPK) {

		return getPersistence().countByACN_ACPK(
			assigneeClassName, assigneeClassPK);
	}

	/**
	 * Caches the kaleo task assignment instance in the entity cache if it is enabled.
	 *
	 * @param kaleoTaskAssignmentInstance the kaleo task assignment instance
	 */
	public static void cacheResult(
		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance) {

		getPersistence().cacheResult(kaleoTaskAssignmentInstance);
	}

	/**
	 * Caches the kaleo task assignment instances in the entity cache if it is enabled.
	 *
	 * @param kaleoTaskAssignmentInstances the kaleo task assignment instances
	 */
	public static void cacheResult(
		List<KaleoTaskAssignmentInstance> kaleoTaskAssignmentInstances) {

		getPersistence().cacheResult(kaleoTaskAssignmentInstances);
	}

	/**
	 * Creates a new kaleo task assignment instance with the primary key. Does not add the kaleo task assignment instance to the database.
	 *
	 * @param kaleoTaskAssignmentInstanceId the primary key for the new kaleo task assignment instance
	 * @return the new kaleo task assignment instance
	 */
	public static KaleoTaskAssignmentInstance create(
		long kaleoTaskAssignmentInstanceId) {

		return getPersistence().create(kaleoTaskAssignmentInstanceId);
	}

	/**
	 * Removes the kaleo task assignment instance with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTaskAssignmentInstanceId the primary key of the kaleo task assignment instance
	 * @return the kaleo task assignment instance that was removed
	 * @throws NoSuchTaskAssignmentInstanceException if a kaleo task assignment instance with the primary key could not be found
	 */
	public static KaleoTaskAssignmentInstance remove(
			long kaleoTaskAssignmentInstanceId)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchTaskAssignmentInstanceException {

		return getPersistence().remove(kaleoTaskAssignmentInstanceId);
	}

	public static KaleoTaskAssignmentInstance updateImpl(
		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance) {

		return getPersistence().updateImpl(kaleoTaskAssignmentInstance);
	}

	/**
	 * Returns the kaleo task assignment instance with the primary key or throws a <code>NoSuchTaskAssignmentInstanceException</code> if it could not be found.
	 *
	 * @param kaleoTaskAssignmentInstanceId the primary key of the kaleo task assignment instance
	 * @return the kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a kaleo task assignment instance with the primary key could not be found
	 */
	public static KaleoTaskAssignmentInstance findByPrimaryKey(
			long kaleoTaskAssignmentInstanceId)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchTaskAssignmentInstanceException {

		return getPersistence().findByPrimaryKey(kaleoTaskAssignmentInstanceId);
	}

	/**
	 * Returns the kaleo task assignment instance with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param kaleoTaskAssignmentInstanceId the primary key of the kaleo task assignment instance
	 * @return the kaleo task assignment instance, or <code>null</code> if a kaleo task assignment instance with the primary key could not be found
	 */
	public static KaleoTaskAssignmentInstance fetchByPrimaryKey(
		long kaleoTaskAssignmentInstanceId) {

		return getPersistence().fetchByPrimaryKey(
			kaleoTaskAssignmentInstanceId);
	}

	/**
	 * Returns all the kaleo task assignment instances.
	 *
	 * @return the kaleo task assignment instances
	 */
	public static List<KaleoTaskAssignmentInstance> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the kaleo task assignment instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @return the range of kaleo task assignment instances
	 */
	public static List<KaleoTaskAssignmentInstance> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignment instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of kaleo task assignment instances
	 */
	public static List<KaleoTaskAssignmentInstance> findAll(
		int start, int end,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignment instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of kaleo task assignment instances
	 */
	public static List<KaleoTaskAssignmentInstance> findAll(
		int start, int end,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the kaleo task assignment instances from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of kaleo task assignment instances.
	 *
	 * @return the number of kaleo task assignment instances
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static KaleoTaskAssignmentInstancePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<KaleoTaskAssignmentInstancePersistence,
		 KaleoTaskAssignmentInstancePersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			KaleoTaskAssignmentInstancePersistence.class);

		ServiceTracker
			<KaleoTaskAssignmentInstancePersistence,
			 KaleoTaskAssignmentInstancePersistence> serviceTracker =
				new ServiceTracker
					<KaleoTaskAssignmentInstancePersistence,
					 KaleoTaskAssignmentInstancePersistence>(
						 bundle.getBundleContext(),
						 KaleoTaskAssignmentInstancePersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}
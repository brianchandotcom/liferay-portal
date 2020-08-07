/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.data.integration.service.persistence;

import com.liferay.commerce.data.integration.model.CommerceDataIntegrationProcessLog;
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
 * The persistence utility for the commerce data integration process log service. This utility wraps <code>com.liferay.commerce.data.integration.service.persistence.impl.CommerceDataIntegrationProcessLogPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceDataIntegrationProcessLogPersistence
 * @generated
 */
public class CommerceDataIntegrationProcessLogUtil {

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
		CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog) {

		getPersistence().clearCache(commerceDataIntegrationProcessLog);
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
	public static Map<Serializable, CommerceDataIntegrationProcessLog>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceDataIntegrationProcessLog> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceDataIntegrationProcessLog> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceDataIntegrationProcessLog> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceDataIntegrationProcessLog>
			orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceDataIntegrationProcessLog update(
		CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog) {

		return getPersistence().update(commerceDataIntegrationProcessLog);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceDataIntegrationProcessLog update(
		CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog,
		ServiceContext serviceContext) {

		return getPersistence().update(
			commerceDataIntegrationProcessLog, serviceContext);
	}

	/**
	 * Returns all the commerce data integration process logs where CDataIntegrationProcessId = &#63;.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @return the matching commerce data integration process logs
	 */
	public static List<CommerceDataIntegrationProcessLog>
		findByCDataIntegrationProcessId(long CDataIntegrationProcessId) {

		return getPersistence().findByCDataIntegrationProcessId(
			CDataIntegrationProcessId);
	}

	/**
	 * Returns a range of all the commerce data integration process logs where CDataIntegrationProcessId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param start the lower bound of the range of commerce data integration process logs
	 * @param end the upper bound of the range of commerce data integration process logs (not inclusive)
	 * @return the range of matching commerce data integration process logs
	 */
	public static List<CommerceDataIntegrationProcessLog>
		findByCDataIntegrationProcessId(
			long CDataIntegrationProcessId, int start, int end) {

		return getPersistence().findByCDataIntegrationProcessId(
			CDataIntegrationProcessId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce data integration process logs where CDataIntegrationProcessId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param start the lower bound of the range of commerce data integration process logs
	 * @param end the upper bound of the range of commerce data integration process logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce data integration process logs
	 */
	public static List<CommerceDataIntegrationProcessLog>
		findByCDataIntegrationProcessId(
			long CDataIntegrationProcessId, int start, int end,
			OrderByComparator<CommerceDataIntegrationProcessLog>
				orderByComparator) {

		return getPersistence().findByCDataIntegrationProcessId(
			CDataIntegrationProcessId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce data integration process logs where CDataIntegrationProcessId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param start the lower bound of the range of commerce data integration process logs
	 * @param end the upper bound of the range of commerce data integration process logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce data integration process logs
	 */
	public static List<CommerceDataIntegrationProcessLog>
		findByCDataIntegrationProcessId(
			long CDataIntegrationProcessId, int start, int end,
			OrderByComparator<CommerceDataIntegrationProcessLog>
				orderByComparator,
			boolean useFinderCache) {

		return getPersistence().findByCDataIntegrationProcessId(
			CDataIntegrationProcessId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce data integration process log in the ordered set where CDataIntegrationProcessId = &#63;.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce data integration process log
	 * @throws NoSuchDataIntegrationProcessLogException if a matching commerce data integration process log could not be found
	 */
	public static CommerceDataIntegrationProcessLog
			findByCDataIntegrationProcessId_First(
				long CDataIntegrationProcessId,
				OrderByComparator<CommerceDataIntegrationProcessLog>
					orderByComparator)
		throws com.liferay.commerce.data.integration.exception.
			NoSuchDataIntegrationProcessLogException {

		return getPersistence().findByCDataIntegrationProcessId_First(
			CDataIntegrationProcessId, orderByComparator);
	}

	/**
	 * Returns the first commerce data integration process log in the ordered set where CDataIntegrationProcessId = &#63;.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce data integration process log, or <code>null</code> if a matching commerce data integration process log could not be found
	 */
	public static CommerceDataIntegrationProcessLog
		fetchByCDataIntegrationProcessId_First(
			long CDataIntegrationProcessId,
			OrderByComparator<CommerceDataIntegrationProcessLog>
				orderByComparator) {

		return getPersistence().fetchByCDataIntegrationProcessId_First(
			CDataIntegrationProcessId, orderByComparator);
	}

	/**
	 * Returns the last commerce data integration process log in the ordered set where CDataIntegrationProcessId = &#63;.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce data integration process log
	 * @throws NoSuchDataIntegrationProcessLogException if a matching commerce data integration process log could not be found
	 */
	public static CommerceDataIntegrationProcessLog
			findByCDataIntegrationProcessId_Last(
				long CDataIntegrationProcessId,
				OrderByComparator<CommerceDataIntegrationProcessLog>
					orderByComparator)
		throws com.liferay.commerce.data.integration.exception.
			NoSuchDataIntegrationProcessLogException {

		return getPersistence().findByCDataIntegrationProcessId_Last(
			CDataIntegrationProcessId, orderByComparator);
	}

	/**
	 * Returns the last commerce data integration process log in the ordered set where CDataIntegrationProcessId = &#63;.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce data integration process log, or <code>null</code> if a matching commerce data integration process log could not be found
	 */
	public static CommerceDataIntegrationProcessLog
		fetchByCDataIntegrationProcessId_Last(
			long CDataIntegrationProcessId,
			OrderByComparator<CommerceDataIntegrationProcessLog>
				orderByComparator) {

		return getPersistence().fetchByCDataIntegrationProcessId_Last(
			CDataIntegrationProcessId, orderByComparator);
	}

	/**
	 * Returns the commerce data integration process logs before and after the current commerce data integration process log in the ordered set where CDataIntegrationProcessId = &#63;.
	 *
	 * @param commerceDataIntegrationProcessLogId the primary key of the current commerce data integration process log
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce data integration process log
	 * @throws NoSuchDataIntegrationProcessLogException if a commerce data integration process log with the primary key could not be found
	 */
	public static CommerceDataIntegrationProcessLog[]
			findByCDataIntegrationProcessId_PrevAndNext(
				long commerceDataIntegrationProcessLogId,
				long CDataIntegrationProcessId,
				OrderByComparator<CommerceDataIntegrationProcessLog>
					orderByComparator)
		throws com.liferay.commerce.data.integration.exception.
			NoSuchDataIntegrationProcessLogException {

		return getPersistence().findByCDataIntegrationProcessId_PrevAndNext(
			commerceDataIntegrationProcessLogId, CDataIntegrationProcessId,
			orderByComparator);
	}

	/**
	 * Removes all the commerce data integration process logs where CDataIntegrationProcessId = &#63; from the database.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 */
	public static void removeByCDataIntegrationProcessId(
		long CDataIntegrationProcessId) {

		getPersistence().removeByCDataIntegrationProcessId(
			CDataIntegrationProcessId);
	}

	/**
	 * Returns the number of commerce data integration process logs where CDataIntegrationProcessId = &#63;.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @return the number of matching commerce data integration process logs
	 */
	public static int countByCDataIntegrationProcessId(
		long CDataIntegrationProcessId) {

		return getPersistence().countByCDataIntegrationProcessId(
			CDataIntegrationProcessId);
	}

	/**
	 * Returns all the commerce data integration process logs where CDataIntegrationProcessId = &#63; and status = &#63;.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param status the status
	 * @return the matching commerce data integration process logs
	 */
	public static List<CommerceDataIntegrationProcessLog> findByC_S(
		long CDataIntegrationProcessId, int status) {

		return getPersistence().findByC_S(CDataIntegrationProcessId, status);
	}

	/**
	 * Returns a range of all the commerce data integration process logs where CDataIntegrationProcessId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce data integration process logs
	 * @param end the upper bound of the range of commerce data integration process logs (not inclusive)
	 * @return the range of matching commerce data integration process logs
	 */
	public static List<CommerceDataIntegrationProcessLog> findByC_S(
		long CDataIntegrationProcessId, int status, int start, int end) {

		return getPersistence().findByC_S(
			CDataIntegrationProcessId, status, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce data integration process logs where CDataIntegrationProcessId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce data integration process logs
	 * @param end the upper bound of the range of commerce data integration process logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce data integration process logs
	 */
	public static List<CommerceDataIntegrationProcessLog> findByC_S(
		long CDataIntegrationProcessId, int status, int start, int end,
		OrderByComparator<CommerceDataIntegrationProcessLog>
			orderByComparator) {

		return getPersistence().findByC_S(
			CDataIntegrationProcessId, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce data integration process logs where CDataIntegrationProcessId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce data integration process logs
	 * @param end the upper bound of the range of commerce data integration process logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce data integration process logs
	 */
	public static List<CommerceDataIntegrationProcessLog> findByC_S(
		long CDataIntegrationProcessId, int status, int start, int end,
		OrderByComparator<CommerceDataIntegrationProcessLog> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_S(
			CDataIntegrationProcessId, status, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce data integration process log in the ordered set where CDataIntegrationProcessId = &#63; and status = &#63;.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce data integration process log
	 * @throws NoSuchDataIntegrationProcessLogException if a matching commerce data integration process log could not be found
	 */
	public static CommerceDataIntegrationProcessLog findByC_S_First(
			long CDataIntegrationProcessId, int status,
			OrderByComparator<CommerceDataIntegrationProcessLog>
				orderByComparator)
		throws com.liferay.commerce.data.integration.exception.
			NoSuchDataIntegrationProcessLogException {

		return getPersistence().findByC_S_First(
			CDataIntegrationProcessId, status, orderByComparator);
	}

	/**
	 * Returns the first commerce data integration process log in the ordered set where CDataIntegrationProcessId = &#63; and status = &#63;.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce data integration process log, or <code>null</code> if a matching commerce data integration process log could not be found
	 */
	public static CommerceDataIntegrationProcessLog fetchByC_S_First(
		long CDataIntegrationProcessId, int status,
		OrderByComparator<CommerceDataIntegrationProcessLog>
			orderByComparator) {

		return getPersistence().fetchByC_S_First(
			CDataIntegrationProcessId, status, orderByComparator);
	}

	/**
	 * Returns the last commerce data integration process log in the ordered set where CDataIntegrationProcessId = &#63; and status = &#63;.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce data integration process log
	 * @throws NoSuchDataIntegrationProcessLogException if a matching commerce data integration process log could not be found
	 */
	public static CommerceDataIntegrationProcessLog findByC_S_Last(
			long CDataIntegrationProcessId, int status,
			OrderByComparator<CommerceDataIntegrationProcessLog>
				orderByComparator)
		throws com.liferay.commerce.data.integration.exception.
			NoSuchDataIntegrationProcessLogException {

		return getPersistence().findByC_S_Last(
			CDataIntegrationProcessId, status, orderByComparator);
	}

	/**
	 * Returns the last commerce data integration process log in the ordered set where CDataIntegrationProcessId = &#63; and status = &#63;.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce data integration process log, or <code>null</code> if a matching commerce data integration process log could not be found
	 */
	public static CommerceDataIntegrationProcessLog fetchByC_S_Last(
		long CDataIntegrationProcessId, int status,
		OrderByComparator<CommerceDataIntegrationProcessLog>
			orderByComparator) {

		return getPersistence().fetchByC_S_Last(
			CDataIntegrationProcessId, status, orderByComparator);
	}

	/**
	 * Returns the commerce data integration process logs before and after the current commerce data integration process log in the ordered set where CDataIntegrationProcessId = &#63; and status = &#63;.
	 *
	 * @param commerceDataIntegrationProcessLogId the primary key of the current commerce data integration process log
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce data integration process log
	 * @throws NoSuchDataIntegrationProcessLogException if a commerce data integration process log with the primary key could not be found
	 */
	public static CommerceDataIntegrationProcessLog[] findByC_S_PrevAndNext(
			long commerceDataIntegrationProcessLogId,
			long CDataIntegrationProcessId, int status,
			OrderByComparator<CommerceDataIntegrationProcessLog>
				orderByComparator)
		throws com.liferay.commerce.data.integration.exception.
			NoSuchDataIntegrationProcessLogException {

		return getPersistence().findByC_S_PrevAndNext(
			commerceDataIntegrationProcessLogId, CDataIntegrationProcessId,
			status, orderByComparator);
	}

	/**
	 * Removes all the commerce data integration process logs where CDataIntegrationProcessId = &#63; and status = &#63; from the database.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param status the status
	 */
	public static void removeByC_S(long CDataIntegrationProcessId, int status) {
		getPersistence().removeByC_S(CDataIntegrationProcessId, status);
	}

	/**
	 * Returns the number of commerce data integration process logs where CDataIntegrationProcessId = &#63; and status = &#63;.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param status the status
	 * @return the number of matching commerce data integration process logs
	 */
	public static int countByC_S(long CDataIntegrationProcessId, int status) {
		return getPersistence().countByC_S(CDataIntegrationProcessId, status);
	}

	/**
	 * Caches the commerce data integration process log in the entity cache if it is enabled.
	 *
	 * @param commerceDataIntegrationProcessLog the commerce data integration process log
	 */
	public static void cacheResult(
		CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog) {

		getPersistence().cacheResult(commerceDataIntegrationProcessLog);
	}

	/**
	 * Caches the commerce data integration process logs in the entity cache if it is enabled.
	 *
	 * @param commerceDataIntegrationProcessLogs the commerce data integration process logs
	 */
	public static void cacheResult(
		List<CommerceDataIntegrationProcessLog>
			commerceDataIntegrationProcessLogs) {

		getPersistence().cacheResult(commerceDataIntegrationProcessLogs);
	}

	/**
	 * Creates a new commerce data integration process log with the primary key. Does not add the commerce data integration process log to the database.
	 *
	 * @param commerceDataIntegrationProcessLogId the primary key for the new commerce data integration process log
	 * @return the new commerce data integration process log
	 */
	public static CommerceDataIntegrationProcessLog create(
		long commerceDataIntegrationProcessLogId) {

		return getPersistence().create(commerceDataIntegrationProcessLogId);
	}

	/**
	 * Removes the commerce data integration process log with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceDataIntegrationProcessLogId the primary key of the commerce data integration process log
	 * @return the commerce data integration process log that was removed
	 * @throws NoSuchDataIntegrationProcessLogException if a commerce data integration process log with the primary key could not be found
	 */
	public static CommerceDataIntegrationProcessLog remove(
			long commerceDataIntegrationProcessLogId)
		throws com.liferay.commerce.data.integration.exception.
			NoSuchDataIntegrationProcessLogException {

		return getPersistence().remove(commerceDataIntegrationProcessLogId);
	}

	public static CommerceDataIntegrationProcessLog updateImpl(
		CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog) {

		return getPersistence().updateImpl(commerceDataIntegrationProcessLog);
	}

	/**
	 * Returns the commerce data integration process log with the primary key or throws a <code>NoSuchDataIntegrationProcessLogException</code> if it could not be found.
	 *
	 * @param commerceDataIntegrationProcessLogId the primary key of the commerce data integration process log
	 * @return the commerce data integration process log
	 * @throws NoSuchDataIntegrationProcessLogException if a commerce data integration process log with the primary key could not be found
	 */
	public static CommerceDataIntegrationProcessLog findByPrimaryKey(
			long commerceDataIntegrationProcessLogId)
		throws com.liferay.commerce.data.integration.exception.
			NoSuchDataIntegrationProcessLogException {

		return getPersistence().findByPrimaryKey(
			commerceDataIntegrationProcessLogId);
	}

	/**
	 * Returns the commerce data integration process log with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceDataIntegrationProcessLogId the primary key of the commerce data integration process log
	 * @return the commerce data integration process log, or <code>null</code> if a commerce data integration process log with the primary key could not be found
	 */
	public static CommerceDataIntegrationProcessLog fetchByPrimaryKey(
		long commerceDataIntegrationProcessLogId) {

		return getPersistence().fetchByPrimaryKey(
			commerceDataIntegrationProcessLogId);
	}

	/**
	 * Returns all the commerce data integration process logs.
	 *
	 * @return the commerce data integration process logs
	 */
	public static List<CommerceDataIntegrationProcessLog> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce data integration process logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce data integration process logs
	 * @param end the upper bound of the range of commerce data integration process logs (not inclusive)
	 * @return the range of commerce data integration process logs
	 */
	public static List<CommerceDataIntegrationProcessLog> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce data integration process logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce data integration process logs
	 * @param end the upper bound of the range of commerce data integration process logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce data integration process logs
	 */
	public static List<CommerceDataIntegrationProcessLog> findAll(
		int start, int end,
		OrderByComparator<CommerceDataIntegrationProcessLog>
			orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce data integration process logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce data integration process logs
	 * @param end the upper bound of the range of commerce data integration process logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce data integration process logs
	 */
	public static List<CommerceDataIntegrationProcessLog> findAll(
		int start, int end,
		OrderByComparator<CommerceDataIntegrationProcessLog> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce data integration process logs from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce data integration process logs.
	 *
	 * @return the number of commerce data integration process logs
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceDataIntegrationProcessLogPersistence
		getPersistence() {

		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceDataIntegrationProcessLogPersistence,
		 CommerceDataIntegrationProcessLogPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceDataIntegrationProcessLogPersistence.class);

		ServiceTracker
			<CommerceDataIntegrationProcessLogPersistence,
			 CommerceDataIntegrationProcessLogPersistence> serviceTracker =
				new ServiceTracker
					<CommerceDataIntegrationProcessLogPersistence,
					 CommerceDataIntegrationProcessLogPersistence>(
						 bundle.getBundleContext(),
						 CommerceDataIntegrationProcessLogPersistence.class,
						 null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}
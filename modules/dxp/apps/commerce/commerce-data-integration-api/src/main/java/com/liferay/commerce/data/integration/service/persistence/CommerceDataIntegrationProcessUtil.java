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

import com.liferay.commerce.data.integration.model.CommerceDataIntegrationProcess;
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
 * The persistence utility for the commerce data integration process service. This utility wraps <code>com.liferay.commerce.data.integration.service.persistence.impl.CommerceDataIntegrationProcessPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceDataIntegrationProcessPersistence
 * @generated
 */
public class CommerceDataIntegrationProcessUtil {

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
		CommerceDataIntegrationProcess commerceDataIntegrationProcess) {

		getPersistence().clearCache(commerceDataIntegrationProcess);
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
	public static Map<Serializable, CommerceDataIntegrationProcess>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceDataIntegrationProcess> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceDataIntegrationProcess> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceDataIntegrationProcess> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceDataIntegrationProcess> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceDataIntegrationProcess update(
		CommerceDataIntegrationProcess commerceDataIntegrationProcess) {

		return getPersistence().update(commerceDataIntegrationProcess);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceDataIntegrationProcess update(
		CommerceDataIntegrationProcess commerceDataIntegrationProcess,
		ServiceContext serviceContext) {

		return getPersistence().update(
			commerceDataIntegrationProcess, serviceContext);
	}

	/**
	 * Returns all the commerce data integration processes where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce data integration processes
	 */
	public static List<CommerceDataIntegrationProcess> findByCompanyId(
		long companyId) {

		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the commerce data integration processes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce data integration processes
	 * @param end the upper bound of the range of commerce data integration processes (not inclusive)
	 * @return the range of matching commerce data integration processes
	 */
	public static List<CommerceDataIntegrationProcess> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce data integration processes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce data integration processes
	 * @param end the upper bound of the range of commerce data integration processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce data integration processes
	 */
	public static List<CommerceDataIntegrationProcess> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceDataIntegrationProcess> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce data integration processes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce data integration processes
	 * @param end the upper bound of the range of commerce data integration processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce data integration processes
	 */
	public static List<CommerceDataIntegrationProcess> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceDataIntegrationProcess> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce data integration process in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce data integration process
	 * @throws NoSuchDataIntegrationProcessException if a matching commerce data integration process could not be found
	 */
	public static CommerceDataIntegrationProcess findByCompanyId_First(
			long companyId,
			OrderByComparator<CommerceDataIntegrationProcess> orderByComparator)
		throws com.liferay.commerce.data.integration.exception.
			NoSuchDataIntegrationProcessException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first commerce data integration process in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce data integration process, or <code>null</code> if a matching commerce data integration process could not be found
	 */
	public static CommerceDataIntegrationProcess fetchByCompanyId_First(
		long companyId,
		OrderByComparator<CommerceDataIntegrationProcess> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce data integration process in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce data integration process
	 * @throws NoSuchDataIntegrationProcessException if a matching commerce data integration process could not be found
	 */
	public static CommerceDataIntegrationProcess findByCompanyId_Last(
			long companyId,
			OrderByComparator<CommerceDataIntegrationProcess> orderByComparator)
		throws com.liferay.commerce.data.integration.exception.
			NoSuchDataIntegrationProcessException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce data integration process in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce data integration process, or <code>null</code> if a matching commerce data integration process could not be found
	 */
	public static CommerceDataIntegrationProcess fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<CommerceDataIntegrationProcess> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the commerce data integration processes before and after the current commerce data integration process in the ordered set where companyId = &#63;.
	 *
	 * @param commerceDataIntegrationProcessId the primary key of the current commerce data integration process
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce data integration process
	 * @throws NoSuchDataIntegrationProcessException if a commerce data integration process with the primary key could not be found
	 */
	public static CommerceDataIntegrationProcess[] findByCompanyId_PrevAndNext(
			long commerceDataIntegrationProcessId, long companyId,
			OrderByComparator<CommerceDataIntegrationProcess> orderByComparator)
		throws com.liferay.commerce.data.integration.exception.
			NoSuchDataIntegrationProcessException {

		return getPersistence().findByCompanyId_PrevAndNext(
			commerceDataIntegrationProcessId, companyId, orderByComparator);
	}

	/**
	 * Returns all the commerce data integration processes that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce data integration processes that the user has permission to view
	 */
	public static List<CommerceDataIntegrationProcess> filterFindByCompanyId(
		long companyId) {

		return getPersistence().filterFindByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the commerce data integration processes that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce data integration processes
	 * @param end the upper bound of the range of commerce data integration processes (not inclusive)
	 * @return the range of matching commerce data integration processes that the user has permission to view
	 */
	public static List<CommerceDataIntegrationProcess> filterFindByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().filterFindByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce data integration processes that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce data integration processes
	 * @param end the upper bound of the range of commerce data integration processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce data integration processes that the user has permission to view
	 */
	public static List<CommerceDataIntegrationProcess> filterFindByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceDataIntegrationProcess> orderByComparator) {

		return getPersistence().filterFindByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the commerce data integration processes before and after the current commerce data integration process in the ordered set of commerce data integration processes that the user has permission to view where companyId = &#63;.
	 *
	 * @param commerceDataIntegrationProcessId the primary key of the current commerce data integration process
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce data integration process
	 * @throws NoSuchDataIntegrationProcessException if a commerce data integration process with the primary key could not be found
	 */
	public static CommerceDataIntegrationProcess[]
			filterFindByCompanyId_PrevAndNext(
				long commerceDataIntegrationProcessId, long companyId,
				OrderByComparator<CommerceDataIntegrationProcess>
					orderByComparator)
		throws com.liferay.commerce.data.integration.exception.
			NoSuchDataIntegrationProcessException {

		return getPersistence().filterFindByCompanyId_PrevAndNext(
			commerceDataIntegrationProcessId, companyId, orderByComparator);
	}

	/**
	 * Removes all the commerce data integration processes where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of commerce data integration processes where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce data integration processes
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns the number of commerce data integration processes that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce data integration processes that the user has permission to view
	 */
	public static int filterCountByCompanyId(long companyId) {
		return getPersistence().filterCountByCompanyId(companyId);
	}

	/**
	 * Returns the commerce data integration process where companyId = &#63; and name = &#63; or throws a <code>NoSuchDataIntegrationProcessException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching commerce data integration process
	 * @throws NoSuchDataIntegrationProcessException if a matching commerce data integration process could not be found
	 */
	public static CommerceDataIntegrationProcess findByC_N(
			long companyId, String name)
		throws com.liferay.commerce.data.integration.exception.
			NoSuchDataIntegrationProcessException {

		return getPersistence().findByC_N(companyId, name);
	}

	/**
	 * Returns the commerce data integration process where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching commerce data integration process, or <code>null</code> if a matching commerce data integration process could not be found
	 */
	public static CommerceDataIntegrationProcess fetchByC_N(
		long companyId, String name) {

		return getPersistence().fetchByC_N(companyId, name);
	}

	/**
	 * Returns the commerce data integration process where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce data integration process, or <code>null</code> if a matching commerce data integration process could not be found
	 */
	public static CommerceDataIntegrationProcess fetchByC_N(
		long companyId, String name, boolean useFinderCache) {

		return getPersistence().fetchByC_N(companyId, name, useFinderCache);
	}

	/**
	 * Removes the commerce data integration process where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the commerce data integration process that was removed
	 */
	public static CommerceDataIntegrationProcess removeByC_N(
			long companyId, String name)
		throws com.liferay.commerce.data.integration.exception.
			NoSuchDataIntegrationProcessException {

		return getPersistence().removeByC_N(companyId, name);
	}

	/**
	 * Returns the number of commerce data integration processes where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching commerce data integration processes
	 */
	public static int countByC_N(long companyId, String name) {
		return getPersistence().countByC_N(companyId, name);
	}

	/**
	 * Returns all the commerce data integration processes where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching commerce data integration processes
	 */
	public static List<CommerceDataIntegrationProcess> findByC_T(
		long companyId, String type) {

		return getPersistence().findByC_T(companyId, type);
	}

	/**
	 * Returns a range of all the commerce data integration processes where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce data integration processes
	 * @param end the upper bound of the range of commerce data integration processes (not inclusive)
	 * @return the range of matching commerce data integration processes
	 */
	public static List<CommerceDataIntegrationProcess> findByC_T(
		long companyId, String type, int start, int end) {

		return getPersistence().findByC_T(companyId, type, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce data integration processes where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce data integration processes
	 * @param end the upper bound of the range of commerce data integration processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce data integration processes
	 */
	public static List<CommerceDataIntegrationProcess> findByC_T(
		long companyId, String type, int start, int end,
		OrderByComparator<CommerceDataIntegrationProcess> orderByComparator) {

		return getPersistence().findByC_T(
			companyId, type, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce data integration processes where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce data integration processes
	 * @param end the upper bound of the range of commerce data integration processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce data integration processes
	 */
	public static List<CommerceDataIntegrationProcess> findByC_T(
		long companyId, String type, int start, int end,
		OrderByComparator<CommerceDataIntegrationProcess> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_T(
			companyId, type, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce data integration process in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce data integration process
	 * @throws NoSuchDataIntegrationProcessException if a matching commerce data integration process could not be found
	 */
	public static CommerceDataIntegrationProcess findByC_T_First(
			long companyId, String type,
			OrderByComparator<CommerceDataIntegrationProcess> orderByComparator)
		throws com.liferay.commerce.data.integration.exception.
			NoSuchDataIntegrationProcessException {

		return getPersistence().findByC_T_First(
			companyId, type, orderByComparator);
	}

	/**
	 * Returns the first commerce data integration process in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce data integration process, or <code>null</code> if a matching commerce data integration process could not be found
	 */
	public static CommerceDataIntegrationProcess fetchByC_T_First(
		long companyId, String type,
		OrderByComparator<CommerceDataIntegrationProcess> orderByComparator) {

		return getPersistence().fetchByC_T_First(
			companyId, type, orderByComparator);
	}

	/**
	 * Returns the last commerce data integration process in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce data integration process
	 * @throws NoSuchDataIntegrationProcessException if a matching commerce data integration process could not be found
	 */
	public static CommerceDataIntegrationProcess findByC_T_Last(
			long companyId, String type,
			OrderByComparator<CommerceDataIntegrationProcess> orderByComparator)
		throws com.liferay.commerce.data.integration.exception.
			NoSuchDataIntegrationProcessException {

		return getPersistence().findByC_T_Last(
			companyId, type, orderByComparator);
	}

	/**
	 * Returns the last commerce data integration process in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce data integration process, or <code>null</code> if a matching commerce data integration process could not be found
	 */
	public static CommerceDataIntegrationProcess fetchByC_T_Last(
		long companyId, String type,
		OrderByComparator<CommerceDataIntegrationProcess> orderByComparator) {

		return getPersistence().fetchByC_T_Last(
			companyId, type, orderByComparator);
	}

	/**
	 * Returns the commerce data integration processes before and after the current commerce data integration process in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param commerceDataIntegrationProcessId the primary key of the current commerce data integration process
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce data integration process
	 * @throws NoSuchDataIntegrationProcessException if a commerce data integration process with the primary key could not be found
	 */
	public static CommerceDataIntegrationProcess[] findByC_T_PrevAndNext(
			long commerceDataIntegrationProcessId, long companyId, String type,
			OrderByComparator<CommerceDataIntegrationProcess> orderByComparator)
		throws com.liferay.commerce.data.integration.exception.
			NoSuchDataIntegrationProcessException {

		return getPersistence().findByC_T_PrevAndNext(
			commerceDataIntegrationProcessId, companyId, type,
			orderByComparator);
	}

	/**
	 * Returns all the commerce data integration processes that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching commerce data integration processes that the user has permission to view
	 */
	public static List<CommerceDataIntegrationProcess> filterFindByC_T(
		long companyId, String type) {

		return getPersistence().filterFindByC_T(companyId, type);
	}

	/**
	 * Returns a range of all the commerce data integration processes that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce data integration processes
	 * @param end the upper bound of the range of commerce data integration processes (not inclusive)
	 * @return the range of matching commerce data integration processes that the user has permission to view
	 */
	public static List<CommerceDataIntegrationProcess> filterFindByC_T(
		long companyId, String type, int start, int end) {

		return getPersistence().filterFindByC_T(companyId, type, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce data integration processes that the user has permissions to view where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce data integration processes
	 * @param end the upper bound of the range of commerce data integration processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce data integration processes that the user has permission to view
	 */
	public static List<CommerceDataIntegrationProcess> filterFindByC_T(
		long companyId, String type, int start, int end,
		OrderByComparator<CommerceDataIntegrationProcess> orderByComparator) {

		return getPersistence().filterFindByC_T(
			companyId, type, start, end, orderByComparator);
	}

	/**
	 * Returns the commerce data integration processes before and after the current commerce data integration process in the ordered set of commerce data integration processes that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * @param commerceDataIntegrationProcessId the primary key of the current commerce data integration process
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce data integration process
	 * @throws NoSuchDataIntegrationProcessException if a commerce data integration process with the primary key could not be found
	 */
	public static CommerceDataIntegrationProcess[] filterFindByC_T_PrevAndNext(
			long commerceDataIntegrationProcessId, long companyId, String type,
			OrderByComparator<CommerceDataIntegrationProcess> orderByComparator)
		throws com.liferay.commerce.data.integration.exception.
			NoSuchDataIntegrationProcessException {

		return getPersistence().filterFindByC_T_PrevAndNext(
			commerceDataIntegrationProcessId, companyId, type,
			orderByComparator);
	}

	/**
	 * Removes all the commerce data integration processes where companyId = &#63; and type = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 */
	public static void removeByC_T(long companyId, String type) {
		getPersistence().removeByC_T(companyId, type);
	}

	/**
	 * Returns the number of commerce data integration processes where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching commerce data integration processes
	 */
	public static int countByC_T(long companyId, String type) {
		return getPersistence().countByC_T(companyId, type);
	}

	/**
	 * Returns the number of commerce data integration processes that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching commerce data integration processes that the user has permission to view
	 */
	public static int filterCountByC_T(long companyId, String type) {
		return getPersistence().filterCountByC_T(companyId, type);
	}

	/**
	 * Caches the commerce data integration process in the entity cache if it is enabled.
	 *
	 * @param commerceDataIntegrationProcess the commerce data integration process
	 */
	public static void cacheResult(
		CommerceDataIntegrationProcess commerceDataIntegrationProcess) {

		getPersistence().cacheResult(commerceDataIntegrationProcess);
	}

	/**
	 * Caches the commerce data integration processes in the entity cache if it is enabled.
	 *
	 * @param commerceDataIntegrationProcesses the commerce data integration processes
	 */
	public static void cacheResult(
		List<CommerceDataIntegrationProcess> commerceDataIntegrationProcesses) {

		getPersistence().cacheResult(commerceDataIntegrationProcesses);
	}

	/**
	 * Creates a new commerce data integration process with the primary key. Does not add the commerce data integration process to the database.
	 *
	 * @param commerceDataIntegrationProcessId the primary key for the new commerce data integration process
	 * @return the new commerce data integration process
	 */
	public static CommerceDataIntegrationProcess create(
		long commerceDataIntegrationProcessId) {

		return getPersistence().create(commerceDataIntegrationProcessId);
	}

	/**
	 * Removes the commerce data integration process with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceDataIntegrationProcessId the primary key of the commerce data integration process
	 * @return the commerce data integration process that was removed
	 * @throws NoSuchDataIntegrationProcessException if a commerce data integration process with the primary key could not be found
	 */
	public static CommerceDataIntegrationProcess remove(
			long commerceDataIntegrationProcessId)
		throws com.liferay.commerce.data.integration.exception.
			NoSuchDataIntegrationProcessException {

		return getPersistence().remove(commerceDataIntegrationProcessId);
	}

	public static CommerceDataIntegrationProcess updateImpl(
		CommerceDataIntegrationProcess commerceDataIntegrationProcess) {

		return getPersistence().updateImpl(commerceDataIntegrationProcess);
	}

	/**
	 * Returns the commerce data integration process with the primary key or throws a <code>NoSuchDataIntegrationProcessException</code> if it could not be found.
	 *
	 * @param commerceDataIntegrationProcessId the primary key of the commerce data integration process
	 * @return the commerce data integration process
	 * @throws NoSuchDataIntegrationProcessException if a commerce data integration process with the primary key could not be found
	 */
	public static CommerceDataIntegrationProcess findByPrimaryKey(
			long commerceDataIntegrationProcessId)
		throws com.liferay.commerce.data.integration.exception.
			NoSuchDataIntegrationProcessException {

		return getPersistence().findByPrimaryKey(
			commerceDataIntegrationProcessId);
	}

	/**
	 * Returns the commerce data integration process with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceDataIntegrationProcessId the primary key of the commerce data integration process
	 * @return the commerce data integration process, or <code>null</code> if a commerce data integration process with the primary key could not be found
	 */
	public static CommerceDataIntegrationProcess fetchByPrimaryKey(
		long commerceDataIntegrationProcessId) {

		return getPersistence().fetchByPrimaryKey(
			commerceDataIntegrationProcessId);
	}

	/**
	 * Returns all the commerce data integration processes.
	 *
	 * @return the commerce data integration processes
	 */
	public static List<CommerceDataIntegrationProcess> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce data integration processes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce data integration processes
	 * @param end the upper bound of the range of commerce data integration processes (not inclusive)
	 * @return the range of commerce data integration processes
	 */
	public static List<CommerceDataIntegrationProcess> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce data integration processes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce data integration processes
	 * @param end the upper bound of the range of commerce data integration processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce data integration processes
	 */
	public static List<CommerceDataIntegrationProcess> findAll(
		int start, int end,
		OrderByComparator<CommerceDataIntegrationProcess> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce data integration processes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce data integration processes
	 * @param end the upper bound of the range of commerce data integration processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce data integration processes
	 */
	public static List<CommerceDataIntegrationProcess> findAll(
		int start, int end,
		OrderByComparator<CommerceDataIntegrationProcess> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce data integration processes from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce data integration processes.
	 *
	 * @return the number of commerce data integration processes
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceDataIntegrationProcessPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceDataIntegrationProcessPersistence,
		 CommerceDataIntegrationProcessPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceDataIntegrationProcessPersistence.class);

		ServiceTracker
			<CommerceDataIntegrationProcessPersistence,
			 CommerceDataIntegrationProcessPersistence> serviceTracker =
				new ServiceTracker
					<CommerceDataIntegrationProcessPersistence,
					 CommerceDataIntegrationProcessPersistence>(
						 bundle.getBundleContext(),
						 CommerceDataIntegrationProcessPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}
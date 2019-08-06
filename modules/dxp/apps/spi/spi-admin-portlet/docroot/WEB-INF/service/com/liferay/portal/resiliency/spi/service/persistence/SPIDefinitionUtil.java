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

package com.liferay.portal.resiliency.spi.service.persistence;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.resiliency.spi.model.SPIDefinition;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence utility for the spi definition service. This utility wraps <code>com.liferay.portal.resiliency.spi.service.persistence.impl.SPIDefinitionPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Michael C. Han
 * @see SPIDefinitionPersistence
 * @generated
 */
@ProviderType
public class SPIDefinitionUtil {

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
	public static void clearCache(SPIDefinition spiDefinition) {
		getPersistence().clearCache(spiDefinition);
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
	public static Map<Serializable, SPIDefinition> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<SPIDefinition> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SPIDefinition> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SPIDefinition> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<SPIDefinition> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static SPIDefinition update(SPIDefinition spiDefinition) {
		return getPersistence().update(spiDefinition);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static SPIDefinition update(
		SPIDefinition spiDefinition, ServiceContext serviceContext) {

		return getPersistence().update(spiDefinition, serviceContext);
	}

	/**
	 * Returns all the spi definitions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching spi definitions
	 */
	public static List<SPIDefinition> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the spi definitions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @return the range of matching spi definitions
	 */
	public static List<SPIDefinition> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the spi definitions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching spi definitions
	 */
	public static List<SPIDefinition> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<SPIDefinition> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the spi definitions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching spi definitions
	 */
	public static List<SPIDefinition> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<SPIDefinition> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first spi definition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching spi definition
	 * @throws NoSuchDefinitionException if a matching spi definition could not be found
	 */
	public static SPIDefinition findByCompanyId_First(
			long companyId, OrderByComparator<SPIDefinition> orderByComparator)
		throws com.liferay.portal.resiliency.spi.exception.
			NoSuchDefinitionException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first spi definition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching spi definition, or <code>null</code> if a matching spi definition could not be found
	 */
	public static SPIDefinition fetchByCompanyId_First(
		long companyId, OrderByComparator<SPIDefinition> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last spi definition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching spi definition
	 * @throws NoSuchDefinitionException if a matching spi definition could not be found
	 */
	public static SPIDefinition findByCompanyId_Last(
			long companyId, OrderByComparator<SPIDefinition> orderByComparator)
		throws com.liferay.portal.resiliency.spi.exception.
			NoSuchDefinitionException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last spi definition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching spi definition, or <code>null</code> if a matching spi definition could not be found
	 */
	public static SPIDefinition fetchByCompanyId_Last(
		long companyId, OrderByComparator<SPIDefinition> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the spi definitions before and after the current spi definition in the ordered set where companyId = &#63;.
	 *
	 * @param spiDefinitionId the primary key of the current spi definition
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next spi definition
	 * @throws NoSuchDefinitionException if a spi definition with the primary key could not be found
	 */
	public static SPIDefinition[] findByCompanyId_PrevAndNext(
			long spiDefinitionId, long companyId,
			OrderByComparator<SPIDefinition> orderByComparator)
		throws com.liferay.portal.resiliency.spi.exception.
			NoSuchDefinitionException {

		return getPersistence().findByCompanyId_PrevAndNext(
			spiDefinitionId, companyId, orderByComparator);
	}

	/**
	 * Returns all the spi definitions that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching spi definitions that the user has permission to view
	 */
	public static List<SPIDefinition> filterFindByCompanyId(long companyId) {
		return getPersistence().filterFindByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the spi definitions that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @return the range of matching spi definitions that the user has permission to view
	 */
	public static List<SPIDefinition> filterFindByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().filterFindByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the spi definitions that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching spi definitions that the user has permission to view
	 */
	public static List<SPIDefinition> filterFindByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<SPIDefinition> orderByComparator) {

		return getPersistence().filterFindByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the spi definitions before and after the current spi definition in the ordered set of spi definitions that the user has permission to view where companyId = &#63;.
	 *
	 * @param spiDefinitionId the primary key of the current spi definition
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next spi definition
	 * @throws NoSuchDefinitionException if a spi definition with the primary key could not be found
	 */
	public static SPIDefinition[] filterFindByCompanyId_PrevAndNext(
			long spiDefinitionId, long companyId,
			OrderByComparator<SPIDefinition> orderByComparator)
		throws com.liferay.portal.resiliency.spi.exception.
			NoSuchDefinitionException {

		return getPersistence().filterFindByCompanyId_PrevAndNext(
			spiDefinitionId, companyId, orderByComparator);
	}

	/**
	 * Removes all the spi definitions where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of spi definitions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching spi definitions
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns the number of spi definitions that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching spi definitions that the user has permission to view
	 */
	public static int filterCountByCompanyId(long companyId) {
		return getPersistence().filterCountByCompanyId(companyId);
	}

	/**
	 * Returns the spi definition where companyId = &#63; and name = &#63; or throws a <code>NoSuchDefinitionException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching spi definition
	 * @throws NoSuchDefinitionException if a matching spi definition could not be found
	 */
	public static SPIDefinition findByC_N(long companyId, String name)
		throws com.liferay.portal.resiliency.spi.exception.
			NoSuchDefinitionException {

		return getPersistence().findByC_N(companyId, name);
	}

	/**
	 * Returns the spi definition where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching spi definition, or <code>null</code> if a matching spi definition could not be found
	 */
	public static SPIDefinition fetchByC_N(long companyId, String name) {
		return getPersistence().fetchByC_N(companyId, name);
	}

	/**
	 * Returns the spi definition where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching spi definition, or <code>null</code> if a matching spi definition could not be found
	 */
	public static SPIDefinition fetchByC_N(
		long companyId, String name, boolean useFinderCache) {

		return getPersistence().fetchByC_N(companyId, name, useFinderCache);
	}

	/**
	 * Removes the spi definition where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the spi definition that was removed
	 */
	public static SPIDefinition removeByC_N(long companyId, String name)
		throws com.liferay.portal.resiliency.spi.exception.
			NoSuchDefinitionException {

		return getPersistence().removeByC_N(companyId, name);
	}

	/**
	 * Returns the number of spi definitions where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching spi definitions
	 */
	public static int countByC_N(long companyId, String name) {
		return getPersistence().countByC_N(companyId, name);
	}

	/**
	 * Returns all the spi definitions where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching spi definitions
	 */
	public static List<SPIDefinition> findByC_S(long companyId, int status) {
		return getPersistence().findByC_S(companyId, status);
	}

	/**
	 * Returns a range of all the spi definitions where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @return the range of matching spi definitions
	 */
	public static List<SPIDefinition> findByC_S(
		long companyId, int status, int start, int end) {

		return getPersistence().findByC_S(companyId, status, start, end);
	}

	/**
	 * Returns an ordered range of all the spi definitions where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching spi definitions
	 */
	public static List<SPIDefinition> findByC_S(
		long companyId, int status, int start, int end,
		OrderByComparator<SPIDefinition> orderByComparator) {

		return getPersistence().findByC_S(
			companyId, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the spi definitions where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching spi definitions
	 */
	public static List<SPIDefinition> findByC_S(
		long companyId, int status, int start, int end,
		OrderByComparator<SPIDefinition> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_S(
			companyId, status, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first spi definition in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching spi definition
	 * @throws NoSuchDefinitionException if a matching spi definition could not be found
	 */
	public static SPIDefinition findByC_S_First(
			long companyId, int status,
			OrderByComparator<SPIDefinition> orderByComparator)
		throws com.liferay.portal.resiliency.spi.exception.
			NoSuchDefinitionException {

		return getPersistence().findByC_S_First(
			companyId, status, orderByComparator);
	}

	/**
	 * Returns the first spi definition in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching spi definition, or <code>null</code> if a matching spi definition could not be found
	 */
	public static SPIDefinition fetchByC_S_First(
		long companyId, int status,
		OrderByComparator<SPIDefinition> orderByComparator) {

		return getPersistence().fetchByC_S_First(
			companyId, status, orderByComparator);
	}

	/**
	 * Returns the last spi definition in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching spi definition
	 * @throws NoSuchDefinitionException if a matching spi definition could not be found
	 */
	public static SPIDefinition findByC_S_Last(
			long companyId, int status,
			OrderByComparator<SPIDefinition> orderByComparator)
		throws com.liferay.portal.resiliency.spi.exception.
			NoSuchDefinitionException {

		return getPersistence().findByC_S_Last(
			companyId, status, orderByComparator);
	}

	/**
	 * Returns the last spi definition in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching spi definition, or <code>null</code> if a matching spi definition could not be found
	 */
	public static SPIDefinition fetchByC_S_Last(
		long companyId, int status,
		OrderByComparator<SPIDefinition> orderByComparator) {

		return getPersistence().fetchByC_S_Last(
			companyId, status, orderByComparator);
	}

	/**
	 * Returns the spi definitions before and after the current spi definition in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param spiDefinitionId the primary key of the current spi definition
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next spi definition
	 * @throws NoSuchDefinitionException if a spi definition with the primary key could not be found
	 */
	public static SPIDefinition[] findByC_S_PrevAndNext(
			long spiDefinitionId, long companyId, int status,
			OrderByComparator<SPIDefinition> orderByComparator)
		throws com.liferay.portal.resiliency.spi.exception.
			NoSuchDefinitionException {

		return getPersistence().findByC_S_PrevAndNext(
			spiDefinitionId, companyId, status, orderByComparator);
	}

	/**
	 * Returns all the spi definitions that the user has permission to view where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching spi definitions that the user has permission to view
	 */
	public static List<SPIDefinition> filterFindByC_S(
		long companyId, int status) {

		return getPersistence().filterFindByC_S(companyId, status);
	}

	/**
	 * Returns a range of all the spi definitions that the user has permission to view where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @return the range of matching spi definitions that the user has permission to view
	 */
	public static List<SPIDefinition> filterFindByC_S(
		long companyId, int status, int start, int end) {

		return getPersistence().filterFindByC_S(companyId, status, start, end);
	}

	/**
	 * Returns an ordered range of all the spi definitions that the user has permissions to view where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching spi definitions that the user has permission to view
	 */
	public static List<SPIDefinition> filterFindByC_S(
		long companyId, int status, int start, int end,
		OrderByComparator<SPIDefinition> orderByComparator) {

		return getPersistence().filterFindByC_S(
			companyId, status, start, end, orderByComparator);
	}

	/**
	 * Returns the spi definitions before and after the current spi definition in the ordered set of spi definitions that the user has permission to view where companyId = &#63; and status = &#63;.
	 *
	 * @param spiDefinitionId the primary key of the current spi definition
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next spi definition
	 * @throws NoSuchDefinitionException if a spi definition with the primary key could not be found
	 */
	public static SPIDefinition[] filterFindByC_S_PrevAndNext(
			long spiDefinitionId, long companyId, int status,
			OrderByComparator<SPIDefinition> orderByComparator)
		throws com.liferay.portal.resiliency.spi.exception.
			NoSuchDefinitionException {

		return getPersistence().filterFindByC_S_PrevAndNext(
			spiDefinitionId, companyId, status, orderByComparator);
	}

	/**
	 * Returns all the spi definitions that the user has permission to view where companyId = &#63; and status = any &#63;.
	 *
	 * @param companyId the company ID
	 * @param statuses the statuses
	 * @return the matching spi definitions that the user has permission to view
	 */
	public static List<SPIDefinition> filterFindByC_S(
		long companyId, int[] statuses) {

		return getPersistence().filterFindByC_S(companyId, statuses);
	}

	/**
	 * Returns a range of all the spi definitions that the user has permission to view where companyId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @return the range of matching spi definitions that the user has permission to view
	 */
	public static List<SPIDefinition> filterFindByC_S(
		long companyId, int[] statuses, int start, int end) {

		return getPersistence().filterFindByC_S(
			companyId, statuses, start, end);
	}

	/**
	 * Returns an ordered range of all the spi definitions that the user has permission to view where companyId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching spi definitions that the user has permission to view
	 */
	public static List<SPIDefinition> filterFindByC_S(
		long companyId, int[] statuses, int start, int end,
		OrderByComparator<SPIDefinition> orderByComparator) {

		return getPersistence().filterFindByC_S(
			companyId, statuses, start, end, orderByComparator);
	}

	/**
	 * Returns all the spi definitions where companyId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param statuses the statuses
	 * @return the matching spi definitions
	 */
	public static List<SPIDefinition> findByC_S(
		long companyId, int[] statuses) {

		return getPersistence().findByC_S(companyId, statuses);
	}

	/**
	 * Returns a range of all the spi definitions where companyId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @return the range of matching spi definitions
	 */
	public static List<SPIDefinition> findByC_S(
		long companyId, int[] statuses, int start, int end) {

		return getPersistence().findByC_S(companyId, statuses, start, end);
	}

	/**
	 * Returns an ordered range of all the spi definitions where companyId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching spi definitions
	 */
	public static List<SPIDefinition> findByC_S(
		long companyId, int[] statuses, int start, int end,
		OrderByComparator<SPIDefinition> orderByComparator) {

		return getPersistence().findByC_S(
			companyId, statuses, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the spi definitions where companyId = &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching spi definitions
	 */
	public static List<SPIDefinition> findByC_S(
		long companyId, int[] statuses, int start, int end,
		OrderByComparator<SPIDefinition> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_S(
			companyId, statuses, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the spi definitions where companyId = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 */
	public static void removeByC_S(long companyId, int status) {
		getPersistence().removeByC_S(companyId, status);
	}

	/**
	 * Returns the number of spi definitions where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching spi definitions
	 */
	public static int countByC_S(long companyId, int status) {
		return getPersistence().countByC_S(companyId, status);
	}

	/**
	 * Returns the number of spi definitions where companyId = &#63; and status = any &#63;.
	 *
	 * @param companyId the company ID
	 * @param statuses the statuses
	 * @return the number of matching spi definitions
	 */
	public static int countByC_S(long companyId, int[] statuses) {
		return getPersistence().countByC_S(companyId, statuses);
	}

	/**
	 * Returns the number of spi definitions that the user has permission to view where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching spi definitions that the user has permission to view
	 */
	public static int filterCountByC_S(long companyId, int status) {
		return getPersistence().filterCountByC_S(companyId, status);
	}

	/**
	 * Returns the number of spi definitions that the user has permission to view where companyId = &#63; and status = any &#63;.
	 *
	 * @param companyId the company ID
	 * @param statuses the statuses
	 * @return the number of matching spi definitions that the user has permission to view
	 */
	public static int filterCountByC_S(long companyId, int[] statuses) {
		return getPersistence().filterCountByC_S(companyId, statuses);
	}

	/**
	 * Returns the spi definition where connectorAddress = &#63; and connectorPort = &#63; or throws a <code>NoSuchDefinitionException</code> if it could not be found.
	 *
	 * @param connectorAddress the connector address
	 * @param connectorPort the connector port
	 * @return the matching spi definition
	 * @throws NoSuchDefinitionException if a matching spi definition could not be found
	 */
	public static SPIDefinition findByCA_CP(
			String connectorAddress, int connectorPort)
		throws com.liferay.portal.resiliency.spi.exception.
			NoSuchDefinitionException {

		return getPersistence().findByCA_CP(connectorAddress, connectorPort);
	}

	/**
	 * Returns the spi definition where connectorAddress = &#63; and connectorPort = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param connectorAddress the connector address
	 * @param connectorPort the connector port
	 * @return the matching spi definition, or <code>null</code> if a matching spi definition could not be found
	 */
	public static SPIDefinition fetchByCA_CP(
		String connectorAddress, int connectorPort) {

		return getPersistence().fetchByCA_CP(connectorAddress, connectorPort);
	}

	/**
	 * Returns the spi definition where connectorAddress = &#63; and connectorPort = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param connectorAddress the connector address
	 * @param connectorPort the connector port
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching spi definition, or <code>null</code> if a matching spi definition could not be found
	 */
	public static SPIDefinition fetchByCA_CP(
		String connectorAddress, int connectorPort, boolean useFinderCache) {

		return getPersistence().fetchByCA_CP(
			connectorAddress, connectorPort, useFinderCache);
	}

	/**
	 * Removes the spi definition where connectorAddress = &#63; and connectorPort = &#63; from the database.
	 *
	 * @param connectorAddress the connector address
	 * @param connectorPort the connector port
	 * @return the spi definition that was removed
	 */
	public static SPIDefinition removeByCA_CP(
			String connectorAddress, int connectorPort)
		throws com.liferay.portal.resiliency.spi.exception.
			NoSuchDefinitionException {

		return getPersistence().removeByCA_CP(connectorAddress, connectorPort);
	}

	/**
	 * Returns the number of spi definitions where connectorAddress = &#63; and connectorPort = &#63;.
	 *
	 * @param connectorAddress the connector address
	 * @param connectorPort the connector port
	 * @return the number of matching spi definitions
	 */
	public static int countByCA_CP(String connectorAddress, int connectorPort) {
		return getPersistence().countByCA_CP(connectorAddress, connectorPort);
	}

	/**
	 * Caches the spi definition in the entity cache if it is enabled.
	 *
	 * @param spiDefinition the spi definition
	 */
	public static void cacheResult(SPIDefinition spiDefinition) {
		getPersistence().cacheResult(spiDefinition);
	}

	/**
	 * Caches the spi definitions in the entity cache if it is enabled.
	 *
	 * @param spiDefinitions the spi definitions
	 */
	public static void cacheResult(List<SPIDefinition> spiDefinitions) {
		getPersistence().cacheResult(spiDefinitions);
	}

	/**
	 * Creates a new spi definition with the primary key. Does not add the spi definition to the database.
	 *
	 * @param spiDefinitionId the primary key for the new spi definition
	 * @return the new spi definition
	 */
	public static SPIDefinition create(long spiDefinitionId) {
		return getPersistence().create(spiDefinitionId);
	}

	/**
	 * Removes the spi definition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param spiDefinitionId the primary key of the spi definition
	 * @return the spi definition that was removed
	 * @throws NoSuchDefinitionException if a spi definition with the primary key could not be found
	 */
	public static SPIDefinition remove(long spiDefinitionId)
		throws com.liferay.portal.resiliency.spi.exception.
			NoSuchDefinitionException {

		return getPersistence().remove(spiDefinitionId);
	}

	public static SPIDefinition updateImpl(SPIDefinition spiDefinition) {
		return getPersistence().updateImpl(spiDefinition);
	}

	/**
	 * Returns the spi definition with the primary key or throws a <code>NoSuchDefinitionException</code> if it could not be found.
	 *
	 * @param spiDefinitionId the primary key of the spi definition
	 * @return the spi definition
	 * @throws NoSuchDefinitionException if a spi definition with the primary key could not be found
	 */
	public static SPIDefinition findByPrimaryKey(long spiDefinitionId)
		throws com.liferay.portal.resiliency.spi.exception.
			NoSuchDefinitionException {

		return getPersistence().findByPrimaryKey(spiDefinitionId);
	}

	/**
	 * Returns the spi definition with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param spiDefinitionId the primary key of the spi definition
	 * @return the spi definition, or <code>null</code> if a spi definition with the primary key could not be found
	 */
	public static SPIDefinition fetchByPrimaryKey(long spiDefinitionId) {
		return getPersistence().fetchByPrimaryKey(spiDefinitionId);
	}

	/**
	 * Returns all the spi definitions.
	 *
	 * @return the spi definitions
	 */
	public static List<SPIDefinition> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the spi definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @return the range of spi definitions
	 */
	public static List<SPIDefinition> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the spi definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of spi definitions
	 */
	public static List<SPIDefinition> findAll(
		int start, int end,
		OrderByComparator<SPIDefinition> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the spi definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of spi definitions
	 */
	public static List<SPIDefinition> findAll(
		int start, int end, OrderByComparator<SPIDefinition> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the spi definitions from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of spi definitions.
	 *
	 * @return the number of spi definitions
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static SPIDefinitionPersistence getPersistence() {
		if (_persistence == null) {
			_persistence =
				(SPIDefinitionPersistence)PortletBeanLocatorUtil.locate(
					com.liferay.portal.resiliency.spi.service.
						ServletContextUtil.getServletContextName(),
					SPIDefinitionPersistence.class.getName());
		}

		return _persistence;
	}

	private static SPIDefinitionPersistence _persistence;

}
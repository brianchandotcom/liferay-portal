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

package com.liferay.commerce.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CPDAvailabilityEstimate;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the cpd availability estimate service. This utility wraps {@link com.liferay.commerce.service.persistence.impl.CPDAvailabilityEstimatePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CPDAvailabilityEstimatePersistence
 * @see com.liferay.commerce.service.persistence.impl.CPDAvailabilityEstimatePersistenceImpl
 * @generated
 */
@ProviderType
public class CPDAvailabilityEstimateUtil {
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
		CPDAvailabilityEstimate cpdAvailabilityEstimate) {
		getPersistence().clearCache(cpdAvailabilityEstimate);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CPDAvailabilityEstimate> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CPDAvailabilityEstimate> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CPDAvailabilityEstimate> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CPDAvailabilityEstimate update(
		CPDAvailabilityEstimate cpdAvailabilityEstimate) {
		return getPersistence().update(cpdAvailabilityEstimate);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CPDAvailabilityEstimate update(
		CPDAvailabilityEstimate cpdAvailabilityEstimate,
		ServiceContext serviceContext) {
		return getPersistence().update(cpdAvailabilityEstimate, serviceContext);
	}

	/**
	* Returns all the cpd availability estimates where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cpd availability estimates
	*/
	public static List<CPDAvailabilityEstimate> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the cpd availability estimates where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cpd availability estimates
	* @param end the upper bound of the range of cpd availability estimates (not inclusive)
	* @return the range of matching cpd availability estimates
	*/
	public static List<CPDAvailabilityEstimate> findByUuid(String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the cpd availability estimates where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cpd availability estimates
	* @param end the upper bound of the range of cpd availability estimates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cpd availability estimates
	*/
	public static List<CPDAvailabilityEstimate> findByUuid(String uuid,
		int start, int end,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cpd availability estimates where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cpd availability estimates
	* @param end the upper bound of the range of cpd availability estimates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cpd availability estimates
	*/
	public static List<CPDAvailabilityEstimate> findByUuid(String uuid,
		int start, int end,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cpd availability estimate in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cpd availability estimate
	* @throws NoSuchCPDAvailabilityEstimateException if a matching cpd availability estimate could not be found
	*/
	public static CPDAvailabilityEstimate findByUuid_First(String uuid,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCPDAvailabilityEstimateException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first cpd availability estimate in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cpd availability estimate, or <code>null</code> if a matching cpd availability estimate could not be found
	*/
	public static CPDAvailabilityEstimate fetchByUuid_First(String uuid,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last cpd availability estimate in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cpd availability estimate
	* @throws NoSuchCPDAvailabilityEstimateException if a matching cpd availability estimate could not be found
	*/
	public static CPDAvailabilityEstimate findByUuid_Last(String uuid,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCPDAvailabilityEstimateException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last cpd availability estimate in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cpd availability estimate, or <code>null</code> if a matching cpd availability estimate could not be found
	*/
	public static CPDAvailabilityEstimate fetchByUuid_Last(String uuid,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the cpd availability estimates before and after the current cpd availability estimate in the ordered set where uuid = &#63;.
	*
	* @param CPDAvailabilityEstimateId the primary key of the current cpd availability estimate
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cpd availability estimate
	* @throws NoSuchCPDAvailabilityEstimateException if a cpd availability estimate with the primary key could not be found
	*/
	public static CPDAvailabilityEstimate[] findByUuid_PrevAndNext(
		long CPDAvailabilityEstimateId, String uuid,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCPDAvailabilityEstimateException {
		return getPersistence()
				   .findByUuid_PrevAndNext(CPDAvailabilityEstimateId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the cpd availability estimates where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of cpd availability estimates where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cpd availability estimates
	*/
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the cpd availability estimate where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPDAvailabilityEstimateException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cpd availability estimate
	* @throws NoSuchCPDAvailabilityEstimateException if a matching cpd availability estimate could not be found
	*/
	public static CPDAvailabilityEstimate findByUUID_G(String uuid, long groupId)
		throws com.liferay.commerce.exception.NoSuchCPDAvailabilityEstimateException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cpd availability estimate where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cpd availability estimate, or <code>null</code> if a matching cpd availability estimate could not be found
	*/
	public static CPDAvailabilityEstimate fetchByUUID_G(String uuid,
		long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cpd availability estimate where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cpd availability estimate, or <code>null</code> if a matching cpd availability estimate could not be found
	*/
	public static CPDAvailabilityEstimate fetchByUUID_G(String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the cpd availability estimate where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cpd availability estimate that was removed
	*/
	public static CPDAvailabilityEstimate removeByUUID_G(String uuid,
		long groupId)
		throws com.liferay.commerce.exception.NoSuchCPDAvailabilityEstimateException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of cpd availability estimates where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cpd availability estimates
	*/
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the cpd availability estimates where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cpd availability estimates
	*/
	public static List<CPDAvailabilityEstimate> findByUuid_C(String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the cpd availability estimates where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cpd availability estimates
	* @param end the upper bound of the range of cpd availability estimates (not inclusive)
	* @return the range of matching cpd availability estimates
	*/
	public static List<CPDAvailabilityEstimate> findByUuid_C(String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the cpd availability estimates where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cpd availability estimates
	* @param end the upper bound of the range of cpd availability estimates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cpd availability estimates
	*/
	public static List<CPDAvailabilityEstimate> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cpd availability estimates where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cpd availability estimates
	* @param end the upper bound of the range of cpd availability estimates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cpd availability estimates
	*/
	public static List<CPDAvailabilityEstimate> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cpd availability estimate in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cpd availability estimate
	* @throws NoSuchCPDAvailabilityEstimateException if a matching cpd availability estimate could not be found
	*/
	public static CPDAvailabilityEstimate findByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCPDAvailabilityEstimateException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first cpd availability estimate in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cpd availability estimate, or <code>null</code> if a matching cpd availability estimate could not be found
	*/
	public static CPDAvailabilityEstimate fetchByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cpd availability estimate in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cpd availability estimate
	* @throws NoSuchCPDAvailabilityEstimateException if a matching cpd availability estimate could not be found
	*/
	public static CPDAvailabilityEstimate findByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCPDAvailabilityEstimateException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cpd availability estimate in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cpd availability estimate, or <code>null</code> if a matching cpd availability estimate could not be found
	*/
	public static CPDAvailabilityEstimate fetchByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the cpd availability estimates before and after the current cpd availability estimate in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CPDAvailabilityEstimateId the primary key of the current cpd availability estimate
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cpd availability estimate
	* @throws NoSuchCPDAvailabilityEstimateException if a cpd availability estimate with the primary key could not be found
	*/
	public static CPDAvailabilityEstimate[] findByUuid_C_PrevAndNext(
		long CPDAvailabilityEstimateId, String uuid, long companyId,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCPDAvailabilityEstimateException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(CPDAvailabilityEstimateId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the cpd availability estimates where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of cpd availability estimates where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cpd availability estimates
	*/
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns the cpd availability estimate where CPDefinitionId = &#63; or throws a {@link NoSuchCPDAvailabilityEstimateException} if it could not be found.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the matching cpd availability estimate
	* @throws NoSuchCPDAvailabilityEstimateException if a matching cpd availability estimate could not be found
	*/
	public static CPDAvailabilityEstimate findByCPDefinitionId(
		long CPDefinitionId)
		throws com.liferay.commerce.exception.NoSuchCPDAvailabilityEstimateException {
		return getPersistence().findByCPDefinitionId(CPDefinitionId);
	}

	/**
	* Returns the cpd availability estimate where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the matching cpd availability estimate, or <code>null</code> if a matching cpd availability estimate could not be found
	*/
	public static CPDAvailabilityEstimate fetchByCPDefinitionId(
		long CPDefinitionId) {
		return getPersistence().fetchByCPDefinitionId(CPDefinitionId);
	}

	/**
	* Returns the cpd availability estimate where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param CPDefinitionId the cp definition ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cpd availability estimate, or <code>null</code> if a matching cpd availability estimate could not be found
	*/
	public static CPDAvailabilityEstimate fetchByCPDefinitionId(
		long CPDefinitionId, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByCPDefinitionId(CPDefinitionId, retrieveFromCache);
	}

	/**
	* Removes the cpd availability estimate where CPDefinitionId = &#63; from the database.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the cpd availability estimate that was removed
	*/
	public static CPDAvailabilityEstimate removeByCPDefinitionId(
		long CPDefinitionId)
		throws com.liferay.commerce.exception.NoSuchCPDAvailabilityEstimateException {
		return getPersistence().removeByCPDefinitionId(CPDefinitionId);
	}

	/**
	* Returns the number of cpd availability estimates where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the number of matching cpd availability estimates
	*/
	public static int countByCPDefinitionId(long CPDefinitionId) {
		return getPersistence().countByCPDefinitionId(CPDefinitionId);
	}

	/**
	* Returns all the cpd availability estimates where commerceAvailabilityEstimateId = &#63;.
	*
	* @param commerceAvailabilityEstimateId the commerce availability estimate ID
	* @return the matching cpd availability estimates
	*/
	public static List<CPDAvailabilityEstimate> findByCommerceAvailabilityEstimateId(
		long commerceAvailabilityEstimateId) {
		return getPersistence()
				   .findByCommerceAvailabilityEstimateId(commerceAvailabilityEstimateId);
	}

	/**
	* Returns a range of all the cpd availability estimates where commerceAvailabilityEstimateId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceAvailabilityEstimateId the commerce availability estimate ID
	* @param start the lower bound of the range of cpd availability estimates
	* @param end the upper bound of the range of cpd availability estimates (not inclusive)
	* @return the range of matching cpd availability estimates
	*/
	public static List<CPDAvailabilityEstimate> findByCommerceAvailabilityEstimateId(
		long commerceAvailabilityEstimateId, int start, int end) {
		return getPersistence()
				   .findByCommerceAvailabilityEstimateId(commerceAvailabilityEstimateId,
			start, end);
	}

	/**
	* Returns an ordered range of all the cpd availability estimates where commerceAvailabilityEstimateId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceAvailabilityEstimateId the commerce availability estimate ID
	* @param start the lower bound of the range of cpd availability estimates
	* @param end the upper bound of the range of cpd availability estimates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cpd availability estimates
	*/
	public static List<CPDAvailabilityEstimate> findByCommerceAvailabilityEstimateId(
		long commerceAvailabilityEstimateId, int start, int end,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator) {
		return getPersistence()
				   .findByCommerceAvailabilityEstimateId(commerceAvailabilityEstimateId,
			start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cpd availability estimates where commerceAvailabilityEstimateId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceAvailabilityEstimateId the commerce availability estimate ID
	* @param start the lower bound of the range of cpd availability estimates
	* @param end the upper bound of the range of cpd availability estimates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cpd availability estimates
	*/
	public static List<CPDAvailabilityEstimate> findByCommerceAvailabilityEstimateId(
		long commerceAvailabilityEstimateId, int start, int end,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCommerceAvailabilityEstimateId(commerceAvailabilityEstimateId,
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cpd availability estimate in the ordered set where commerceAvailabilityEstimateId = &#63;.
	*
	* @param commerceAvailabilityEstimateId the commerce availability estimate ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cpd availability estimate
	* @throws NoSuchCPDAvailabilityEstimateException if a matching cpd availability estimate could not be found
	*/
	public static CPDAvailabilityEstimate findByCommerceAvailabilityEstimateId_First(
		long commerceAvailabilityEstimateId,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCPDAvailabilityEstimateException {
		return getPersistence()
				   .findByCommerceAvailabilityEstimateId_First(commerceAvailabilityEstimateId,
			orderByComparator);
	}

	/**
	* Returns the first cpd availability estimate in the ordered set where commerceAvailabilityEstimateId = &#63;.
	*
	* @param commerceAvailabilityEstimateId the commerce availability estimate ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cpd availability estimate, or <code>null</code> if a matching cpd availability estimate could not be found
	*/
	public static CPDAvailabilityEstimate fetchByCommerceAvailabilityEstimateId_First(
		long commerceAvailabilityEstimateId,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceAvailabilityEstimateId_First(commerceAvailabilityEstimateId,
			orderByComparator);
	}

	/**
	* Returns the last cpd availability estimate in the ordered set where commerceAvailabilityEstimateId = &#63;.
	*
	* @param commerceAvailabilityEstimateId the commerce availability estimate ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cpd availability estimate
	* @throws NoSuchCPDAvailabilityEstimateException if a matching cpd availability estimate could not be found
	*/
	public static CPDAvailabilityEstimate findByCommerceAvailabilityEstimateId_Last(
		long commerceAvailabilityEstimateId,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCPDAvailabilityEstimateException {
		return getPersistence()
				   .findByCommerceAvailabilityEstimateId_Last(commerceAvailabilityEstimateId,
			orderByComparator);
	}

	/**
	* Returns the last cpd availability estimate in the ordered set where commerceAvailabilityEstimateId = &#63;.
	*
	* @param commerceAvailabilityEstimateId the commerce availability estimate ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cpd availability estimate, or <code>null</code> if a matching cpd availability estimate could not be found
	*/
	public static CPDAvailabilityEstimate fetchByCommerceAvailabilityEstimateId_Last(
		long commerceAvailabilityEstimateId,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceAvailabilityEstimateId_Last(commerceAvailabilityEstimateId,
			orderByComparator);
	}

	/**
	* Returns the cpd availability estimates before and after the current cpd availability estimate in the ordered set where commerceAvailabilityEstimateId = &#63;.
	*
	* @param CPDAvailabilityEstimateId the primary key of the current cpd availability estimate
	* @param commerceAvailabilityEstimateId the commerce availability estimate ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cpd availability estimate
	* @throws NoSuchCPDAvailabilityEstimateException if a cpd availability estimate with the primary key could not be found
	*/
	public static CPDAvailabilityEstimate[] findByCommerceAvailabilityEstimateId_PrevAndNext(
		long CPDAvailabilityEstimateId, long commerceAvailabilityEstimateId,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCPDAvailabilityEstimateException {
		return getPersistence()
				   .findByCommerceAvailabilityEstimateId_PrevAndNext(CPDAvailabilityEstimateId,
			commerceAvailabilityEstimateId, orderByComparator);
	}

	/**
	* Removes all the cpd availability estimates where commerceAvailabilityEstimateId = &#63; from the database.
	*
	* @param commerceAvailabilityEstimateId the commerce availability estimate ID
	*/
	public static void removeByCommerceAvailabilityEstimateId(
		long commerceAvailabilityEstimateId) {
		getPersistence()
			.removeByCommerceAvailabilityEstimateId(commerceAvailabilityEstimateId);
	}

	/**
	* Returns the number of cpd availability estimates where commerceAvailabilityEstimateId = &#63;.
	*
	* @param commerceAvailabilityEstimateId the commerce availability estimate ID
	* @return the number of matching cpd availability estimates
	*/
	public static int countByCommerceAvailabilityEstimateId(
		long commerceAvailabilityEstimateId) {
		return getPersistence()
				   .countByCommerceAvailabilityEstimateId(commerceAvailabilityEstimateId);
	}

	/**
	* Caches the cpd availability estimate in the entity cache if it is enabled.
	*
	* @param cpdAvailabilityEstimate the cpd availability estimate
	*/
	public static void cacheResult(
		CPDAvailabilityEstimate cpdAvailabilityEstimate) {
		getPersistence().cacheResult(cpdAvailabilityEstimate);
	}

	/**
	* Caches the cpd availability estimates in the entity cache if it is enabled.
	*
	* @param cpdAvailabilityEstimates the cpd availability estimates
	*/
	public static void cacheResult(
		List<CPDAvailabilityEstimate> cpdAvailabilityEstimates) {
		getPersistence().cacheResult(cpdAvailabilityEstimates);
	}

	/**
	* Creates a new cpd availability estimate with the primary key. Does not add the cpd availability estimate to the database.
	*
	* @param CPDAvailabilityEstimateId the primary key for the new cpd availability estimate
	* @return the new cpd availability estimate
	*/
	public static CPDAvailabilityEstimate create(long CPDAvailabilityEstimateId) {
		return getPersistence().create(CPDAvailabilityEstimateId);
	}

	/**
	* Removes the cpd availability estimate with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDAvailabilityEstimateId the primary key of the cpd availability estimate
	* @return the cpd availability estimate that was removed
	* @throws NoSuchCPDAvailabilityEstimateException if a cpd availability estimate with the primary key could not be found
	*/
	public static CPDAvailabilityEstimate remove(long CPDAvailabilityEstimateId)
		throws com.liferay.commerce.exception.NoSuchCPDAvailabilityEstimateException {
		return getPersistence().remove(CPDAvailabilityEstimateId);
	}

	public static CPDAvailabilityEstimate updateImpl(
		CPDAvailabilityEstimate cpdAvailabilityEstimate) {
		return getPersistence().updateImpl(cpdAvailabilityEstimate);
	}

	/**
	* Returns the cpd availability estimate with the primary key or throws a {@link NoSuchCPDAvailabilityEstimateException} if it could not be found.
	*
	* @param CPDAvailabilityEstimateId the primary key of the cpd availability estimate
	* @return the cpd availability estimate
	* @throws NoSuchCPDAvailabilityEstimateException if a cpd availability estimate with the primary key could not be found
	*/
	public static CPDAvailabilityEstimate findByPrimaryKey(
		long CPDAvailabilityEstimateId)
		throws com.liferay.commerce.exception.NoSuchCPDAvailabilityEstimateException {
		return getPersistence().findByPrimaryKey(CPDAvailabilityEstimateId);
	}

	/**
	* Returns the cpd availability estimate with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPDAvailabilityEstimateId the primary key of the cpd availability estimate
	* @return the cpd availability estimate, or <code>null</code> if a cpd availability estimate with the primary key could not be found
	*/
	public static CPDAvailabilityEstimate fetchByPrimaryKey(
		long CPDAvailabilityEstimateId) {
		return getPersistence().fetchByPrimaryKey(CPDAvailabilityEstimateId);
	}

	public static java.util.Map<java.io.Serializable, CPDAvailabilityEstimate> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the cpd availability estimates.
	*
	* @return the cpd availability estimates
	*/
	public static List<CPDAvailabilityEstimate> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the cpd availability estimates.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cpd availability estimates
	* @param end the upper bound of the range of cpd availability estimates (not inclusive)
	* @return the range of cpd availability estimates
	*/
	public static List<CPDAvailabilityEstimate> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the cpd availability estimates.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cpd availability estimates
	* @param end the upper bound of the range of cpd availability estimates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cpd availability estimates
	*/
	public static List<CPDAvailabilityEstimate> findAll(int start, int end,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cpd availability estimates.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cpd availability estimates
	* @param end the upper bound of the range of cpd availability estimates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cpd availability estimates
	*/
	public static List<CPDAvailabilityEstimate> findAll(int start, int end,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the cpd availability estimates from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of cpd availability estimates.
	*
	* @return the number of cpd availability estimates
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CPDAvailabilityEstimatePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPDAvailabilityEstimatePersistence, CPDAvailabilityEstimatePersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPDAvailabilityEstimatePersistence.class);

		ServiceTracker<CPDAvailabilityEstimatePersistence, CPDAvailabilityEstimatePersistence> serviceTracker =
			new ServiceTracker<CPDAvailabilityEstimatePersistence, CPDAvailabilityEstimatePersistence>(bundle.getBundleContext(),
				CPDAvailabilityEstimatePersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}
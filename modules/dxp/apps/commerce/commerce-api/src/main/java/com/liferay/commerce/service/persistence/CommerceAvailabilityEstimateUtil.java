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

import com.liferay.commerce.model.CommerceAvailabilityEstimate;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce availability estimate service. This utility wraps {@link com.liferay.commerce.service.persistence.impl.CommerceAvailabilityEstimatePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAvailabilityEstimatePersistence
 * @see com.liferay.commerce.service.persistence.impl.CommerceAvailabilityEstimatePersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceAvailabilityEstimateUtil {
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
		CommerceAvailabilityEstimate commerceAvailabilityEstimate) {
		getPersistence().clearCache(commerceAvailabilityEstimate);
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
	public static List<CommerceAvailabilityEstimate> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceAvailabilityEstimate> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceAvailabilityEstimate> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceAvailabilityEstimate update(
		CommerceAvailabilityEstimate commerceAvailabilityEstimate) {
		return getPersistence().update(commerceAvailabilityEstimate);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceAvailabilityEstimate update(
		CommerceAvailabilityEstimate commerceAvailabilityEstimate,
		ServiceContext serviceContext) {
		return getPersistence()
				   .update(commerceAvailabilityEstimate, serviceContext);
	}

	/**
	* Returns all the commerce availability estimates where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching commerce availability estimates
	*/
	public static List<CommerceAvailabilityEstimate> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the commerce availability estimates where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce availability estimates
	* @param end the upper bound of the range of commerce availability estimates (not inclusive)
	* @return the range of matching commerce availability estimates
	*/
	public static List<CommerceAvailabilityEstimate> findByUuid(String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the commerce availability estimates where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce availability estimates
	* @param end the upper bound of the range of commerce availability estimates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce availability estimates
	*/
	public static List<CommerceAvailabilityEstimate> findByUuid(String uuid,
		int start, int end,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce availability estimates where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce availability estimates
	* @param end the upper bound of the range of commerce availability estimates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce availability estimates
	*/
	public static List<CommerceAvailabilityEstimate> findByUuid(String uuid,
		int start, int end,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce availability estimate in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce availability estimate
	* @throws NoSuchAvailabilityEstimateException if a matching commerce availability estimate could not be found
	*/
	public static CommerceAvailabilityEstimate findByUuid_First(String uuid,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAvailabilityEstimateException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first commerce availability estimate in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce availability estimate, or <code>null</code> if a matching commerce availability estimate could not be found
	*/
	public static CommerceAvailabilityEstimate fetchByUuid_First(String uuid,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last commerce availability estimate in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce availability estimate
	* @throws NoSuchAvailabilityEstimateException if a matching commerce availability estimate could not be found
	*/
	public static CommerceAvailabilityEstimate findByUuid_Last(String uuid,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAvailabilityEstimateException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last commerce availability estimate in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce availability estimate, or <code>null</code> if a matching commerce availability estimate could not be found
	*/
	public static CommerceAvailabilityEstimate fetchByUuid_Last(String uuid,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the commerce availability estimates before and after the current commerce availability estimate in the ordered set where uuid = &#63;.
	*
	* @param commerceAvailabilityEstimateId the primary key of the current commerce availability estimate
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce availability estimate
	* @throws NoSuchAvailabilityEstimateException if a commerce availability estimate with the primary key could not be found
	*/
	public static CommerceAvailabilityEstimate[] findByUuid_PrevAndNext(
		long commerceAvailabilityEstimateId, String uuid,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAvailabilityEstimateException {
		return getPersistence()
				   .findByUuid_PrevAndNext(commerceAvailabilityEstimateId,
			uuid, orderByComparator);
	}

	/**
	* Removes all the commerce availability estimates where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of commerce availability estimates where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching commerce availability estimates
	*/
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the commerce availability estimate where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchAvailabilityEstimateException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce availability estimate
	* @throws NoSuchAvailabilityEstimateException if a matching commerce availability estimate could not be found
	*/
	public static CommerceAvailabilityEstimate findByUUID_G(String uuid,
		long groupId)
		throws com.liferay.commerce.exception.NoSuchAvailabilityEstimateException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the commerce availability estimate where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce availability estimate, or <code>null</code> if a matching commerce availability estimate could not be found
	*/
	public static CommerceAvailabilityEstimate fetchByUUID_G(String uuid,
		long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the commerce availability estimate where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce availability estimate, or <code>null</code> if a matching commerce availability estimate could not be found
	*/
	public static CommerceAvailabilityEstimate fetchByUUID_G(String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the commerce availability estimate where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the commerce availability estimate that was removed
	*/
	public static CommerceAvailabilityEstimate removeByUUID_G(String uuid,
		long groupId)
		throws com.liferay.commerce.exception.NoSuchAvailabilityEstimateException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of commerce availability estimates where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching commerce availability estimates
	*/
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the commerce availability estimates where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching commerce availability estimates
	*/
	public static List<CommerceAvailabilityEstimate> findByUuid_C(String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the commerce availability estimates where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce availability estimates
	* @param end the upper bound of the range of commerce availability estimates (not inclusive)
	* @return the range of matching commerce availability estimates
	*/
	public static List<CommerceAvailabilityEstimate> findByUuid_C(String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce availability estimates where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce availability estimates
	* @param end the upper bound of the range of commerce availability estimates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce availability estimates
	*/
	public static List<CommerceAvailabilityEstimate> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce availability estimates where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce availability estimates
	* @param end the upper bound of the range of commerce availability estimates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce availability estimates
	*/
	public static List<CommerceAvailabilityEstimate> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce availability estimate in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce availability estimate
	* @throws NoSuchAvailabilityEstimateException if a matching commerce availability estimate could not be found
	*/
	public static CommerceAvailabilityEstimate findByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAvailabilityEstimateException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first commerce availability estimate in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce availability estimate, or <code>null</code> if a matching commerce availability estimate could not be found
	*/
	public static CommerceAvailabilityEstimate fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last commerce availability estimate in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce availability estimate
	* @throws NoSuchAvailabilityEstimateException if a matching commerce availability estimate could not be found
	*/
	public static CommerceAvailabilityEstimate findByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAvailabilityEstimateException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last commerce availability estimate in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce availability estimate, or <code>null</code> if a matching commerce availability estimate could not be found
	*/
	public static CommerceAvailabilityEstimate fetchByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the commerce availability estimates before and after the current commerce availability estimate in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param commerceAvailabilityEstimateId the primary key of the current commerce availability estimate
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce availability estimate
	* @throws NoSuchAvailabilityEstimateException if a commerce availability estimate with the primary key could not be found
	*/
	public static CommerceAvailabilityEstimate[] findByUuid_C_PrevAndNext(
		long commerceAvailabilityEstimateId, String uuid, long companyId,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAvailabilityEstimateException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(commerceAvailabilityEstimateId,
			uuid, companyId, orderByComparator);
	}

	/**
	* Removes all the commerce availability estimates where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of commerce availability estimates where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching commerce availability estimates
	*/
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the commerce availability estimates where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce availability estimates
	*/
	public static List<CommerceAvailabilityEstimate> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the commerce availability estimates where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce availability estimates
	* @param end the upper bound of the range of commerce availability estimates (not inclusive)
	* @return the range of matching commerce availability estimates
	*/
	public static List<CommerceAvailabilityEstimate> findByGroupId(
		long groupId, int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce availability estimates where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce availability estimates
	* @param end the upper bound of the range of commerce availability estimates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce availability estimates
	*/
	public static List<CommerceAvailabilityEstimate> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce availability estimates where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce availability estimates
	* @param end the upper bound of the range of commerce availability estimates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce availability estimates
	*/
	public static List<CommerceAvailabilityEstimate> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce availability estimate in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce availability estimate
	* @throws NoSuchAvailabilityEstimateException if a matching commerce availability estimate could not be found
	*/
	public static CommerceAvailabilityEstimate findByGroupId_First(
		long groupId,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAvailabilityEstimateException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first commerce availability estimate in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce availability estimate, or <code>null</code> if a matching commerce availability estimate could not be found
	*/
	public static CommerceAvailabilityEstimate fetchByGroupId_First(
		long groupId,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce availability estimate in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce availability estimate
	* @throws NoSuchAvailabilityEstimateException if a matching commerce availability estimate could not be found
	*/
	public static CommerceAvailabilityEstimate findByGroupId_Last(
		long groupId,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAvailabilityEstimateException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce availability estimate in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce availability estimate, or <code>null</code> if a matching commerce availability estimate could not be found
	*/
	public static CommerceAvailabilityEstimate fetchByGroupId_Last(
		long groupId,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the commerce availability estimates before and after the current commerce availability estimate in the ordered set where groupId = &#63;.
	*
	* @param commerceAvailabilityEstimateId the primary key of the current commerce availability estimate
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce availability estimate
	* @throws NoSuchAvailabilityEstimateException if a commerce availability estimate with the primary key could not be found
	*/
	public static CommerceAvailabilityEstimate[] findByGroupId_PrevAndNext(
		long commerceAvailabilityEstimateId, long groupId,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAvailabilityEstimateException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(commerceAvailabilityEstimateId,
			groupId, orderByComparator);
	}

	/**
	* Removes all the commerce availability estimates where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of commerce availability estimates where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce availability estimates
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Caches the commerce availability estimate in the entity cache if it is enabled.
	*
	* @param commerceAvailabilityEstimate the commerce availability estimate
	*/
	public static void cacheResult(
		CommerceAvailabilityEstimate commerceAvailabilityEstimate) {
		getPersistence().cacheResult(commerceAvailabilityEstimate);
	}

	/**
	* Caches the commerce availability estimates in the entity cache if it is enabled.
	*
	* @param commerceAvailabilityEstimates the commerce availability estimates
	*/
	public static void cacheResult(
		List<CommerceAvailabilityEstimate> commerceAvailabilityEstimates) {
		getPersistence().cacheResult(commerceAvailabilityEstimates);
	}

	/**
	* Creates a new commerce availability estimate with the primary key. Does not add the commerce availability estimate to the database.
	*
	* @param commerceAvailabilityEstimateId the primary key for the new commerce availability estimate
	* @return the new commerce availability estimate
	*/
	public static CommerceAvailabilityEstimate create(
		long commerceAvailabilityEstimateId) {
		return getPersistence().create(commerceAvailabilityEstimateId);
	}

	/**
	* Removes the commerce availability estimate with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceAvailabilityEstimateId the primary key of the commerce availability estimate
	* @return the commerce availability estimate that was removed
	* @throws NoSuchAvailabilityEstimateException if a commerce availability estimate with the primary key could not be found
	*/
	public static CommerceAvailabilityEstimate remove(
		long commerceAvailabilityEstimateId)
		throws com.liferay.commerce.exception.NoSuchAvailabilityEstimateException {
		return getPersistence().remove(commerceAvailabilityEstimateId);
	}

	public static CommerceAvailabilityEstimate updateImpl(
		CommerceAvailabilityEstimate commerceAvailabilityEstimate) {
		return getPersistence().updateImpl(commerceAvailabilityEstimate);
	}

	/**
	* Returns the commerce availability estimate with the primary key or throws a {@link NoSuchAvailabilityEstimateException} if it could not be found.
	*
	* @param commerceAvailabilityEstimateId the primary key of the commerce availability estimate
	* @return the commerce availability estimate
	* @throws NoSuchAvailabilityEstimateException if a commerce availability estimate with the primary key could not be found
	*/
	public static CommerceAvailabilityEstimate findByPrimaryKey(
		long commerceAvailabilityEstimateId)
		throws com.liferay.commerce.exception.NoSuchAvailabilityEstimateException {
		return getPersistence().findByPrimaryKey(commerceAvailabilityEstimateId);
	}

	/**
	* Returns the commerce availability estimate with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceAvailabilityEstimateId the primary key of the commerce availability estimate
	* @return the commerce availability estimate, or <code>null</code> if a commerce availability estimate with the primary key could not be found
	*/
	public static CommerceAvailabilityEstimate fetchByPrimaryKey(
		long commerceAvailabilityEstimateId) {
		return getPersistence().fetchByPrimaryKey(commerceAvailabilityEstimateId);
	}

	public static java.util.Map<java.io.Serializable, CommerceAvailabilityEstimate> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce availability estimates.
	*
	* @return the commerce availability estimates
	*/
	public static List<CommerceAvailabilityEstimate> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce availability estimates.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce availability estimates
	* @param end the upper bound of the range of commerce availability estimates (not inclusive)
	* @return the range of commerce availability estimates
	*/
	public static List<CommerceAvailabilityEstimate> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce availability estimates.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce availability estimates
	* @param end the upper bound of the range of commerce availability estimates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce availability estimates
	*/
	public static List<CommerceAvailabilityEstimate> findAll(int start,
		int end,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce availability estimates.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce availability estimates
	* @param end the upper bound of the range of commerce availability estimates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce availability estimates
	*/
	public static List<CommerceAvailabilityEstimate> findAll(int start,
		int end,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce availability estimates from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce availability estimates.
	*
	* @return the number of commerce availability estimates
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CommerceAvailabilityEstimatePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceAvailabilityEstimatePersistence, CommerceAvailabilityEstimatePersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceAvailabilityEstimatePersistence.class);

		ServiceTracker<CommerceAvailabilityEstimatePersistence, CommerceAvailabilityEstimatePersistence> serviceTracker =
			new ServiceTracker<CommerceAvailabilityEstimatePersistence, CommerceAvailabilityEstimatePersistence>(bundle.getBundleContext(),
				CommerceAvailabilityEstimatePersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}
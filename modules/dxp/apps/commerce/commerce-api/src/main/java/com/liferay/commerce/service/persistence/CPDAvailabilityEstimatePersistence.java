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

import com.liferay.commerce.exception.NoSuchCPDAvailabilityEstimateException;
import com.liferay.commerce.model.CPDAvailabilityEstimate;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the cpd availability estimate service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.persistence.impl.CPDAvailabilityEstimatePersistenceImpl
 * @see CPDAvailabilityEstimateUtil
 * @generated
 */
@ProviderType
public interface CPDAvailabilityEstimatePersistence extends BasePersistence<CPDAvailabilityEstimate> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPDAvailabilityEstimateUtil} to access the cpd availability estimate persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the cpd availability estimates where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cpd availability estimates
	*/
	public java.util.List<CPDAvailabilityEstimate> findByUuid(String uuid);

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
	public java.util.List<CPDAvailabilityEstimate> findByUuid(String uuid,
		int start, int end);

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
	public java.util.List<CPDAvailabilityEstimate> findByUuid(String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDAvailabilityEstimate> orderByComparator);

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
	public java.util.List<CPDAvailabilityEstimate> findByUuid(String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDAvailabilityEstimate> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cpd availability estimate in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cpd availability estimate
	* @throws NoSuchCPDAvailabilityEstimateException if a matching cpd availability estimate could not be found
	*/
	public CPDAvailabilityEstimate findByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDAvailabilityEstimate> orderByComparator)
		throws NoSuchCPDAvailabilityEstimateException;

	/**
	* Returns the first cpd availability estimate in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cpd availability estimate, or <code>null</code> if a matching cpd availability estimate could not be found
	*/
	public CPDAvailabilityEstimate fetchByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDAvailabilityEstimate> orderByComparator);

	/**
	* Returns the last cpd availability estimate in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cpd availability estimate
	* @throws NoSuchCPDAvailabilityEstimateException if a matching cpd availability estimate could not be found
	*/
	public CPDAvailabilityEstimate findByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDAvailabilityEstimate> orderByComparator)
		throws NoSuchCPDAvailabilityEstimateException;

	/**
	* Returns the last cpd availability estimate in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cpd availability estimate, or <code>null</code> if a matching cpd availability estimate could not be found
	*/
	public CPDAvailabilityEstimate fetchByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDAvailabilityEstimate> orderByComparator);

	/**
	* Returns the cpd availability estimates before and after the current cpd availability estimate in the ordered set where uuid = &#63;.
	*
	* @param CPDAvailabilityEstimateId the primary key of the current cpd availability estimate
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cpd availability estimate
	* @throws NoSuchCPDAvailabilityEstimateException if a cpd availability estimate with the primary key could not be found
	*/
	public CPDAvailabilityEstimate[] findByUuid_PrevAndNext(
		long CPDAvailabilityEstimateId, String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDAvailabilityEstimate> orderByComparator)
		throws NoSuchCPDAvailabilityEstimateException;

	/**
	* Removes all the cpd availability estimates where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(String uuid);

	/**
	* Returns the number of cpd availability estimates where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cpd availability estimates
	*/
	public int countByUuid(String uuid);

	/**
	* Returns the cpd availability estimate where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPDAvailabilityEstimateException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cpd availability estimate
	* @throws NoSuchCPDAvailabilityEstimateException if a matching cpd availability estimate could not be found
	*/
	public CPDAvailabilityEstimate findByUUID_G(String uuid, long groupId)
		throws NoSuchCPDAvailabilityEstimateException;

	/**
	* Returns the cpd availability estimate where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cpd availability estimate, or <code>null</code> if a matching cpd availability estimate could not be found
	*/
	public CPDAvailabilityEstimate fetchByUUID_G(String uuid, long groupId);

	/**
	* Returns the cpd availability estimate where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cpd availability estimate, or <code>null</code> if a matching cpd availability estimate could not be found
	*/
	public CPDAvailabilityEstimate fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the cpd availability estimate where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cpd availability estimate that was removed
	*/
	public CPDAvailabilityEstimate removeByUUID_G(String uuid, long groupId)
		throws NoSuchCPDAvailabilityEstimateException;

	/**
	* Returns the number of cpd availability estimates where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cpd availability estimates
	*/
	public int countByUUID_G(String uuid, long groupId);

	/**
	* Returns all the cpd availability estimates where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cpd availability estimates
	*/
	public java.util.List<CPDAvailabilityEstimate> findByUuid_C(String uuid,
		long companyId);

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
	public java.util.List<CPDAvailabilityEstimate> findByUuid_C(String uuid,
		long companyId, int start, int end);

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
	public java.util.List<CPDAvailabilityEstimate> findByUuid_C(String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDAvailabilityEstimate> orderByComparator);

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
	public java.util.List<CPDAvailabilityEstimate> findByUuid_C(String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDAvailabilityEstimate> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cpd availability estimate in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cpd availability estimate
	* @throws NoSuchCPDAvailabilityEstimateException if a matching cpd availability estimate could not be found
	*/
	public CPDAvailabilityEstimate findByUuid_C_First(String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDAvailabilityEstimate> orderByComparator)
		throws NoSuchCPDAvailabilityEstimateException;

	/**
	* Returns the first cpd availability estimate in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cpd availability estimate, or <code>null</code> if a matching cpd availability estimate could not be found
	*/
	public CPDAvailabilityEstimate fetchByUuid_C_First(String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDAvailabilityEstimate> orderByComparator);

	/**
	* Returns the last cpd availability estimate in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cpd availability estimate
	* @throws NoSuchCPDAvailabilityEstimateException if a matching cpd availability estimate could not be found
	*/
	public CPDAvailabilityEstimate findByUuid_C_Last(String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDAvailabilityEstimate> orderByComparator)
		throws NoSuchCPDAvailabilityEstimateException;

	/**
	* Returns the last cpd availability estimate in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cpd availability estimate, or <code>null</code> if a matching cpd availability estimate could not be found
	*/
	public CPDAvailabilityEstimate fetchByUuid_C_Last(String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDAvailabilityEstimate> orderByComparator);

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
	public CPDAvailabilityEstimate[] findByUuid_C_PrevAndNext(
		long CPDAvailabilityEstimateId, String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDAvailabilityEstimate> orderByComparator)
		throws NoSuchCPDAvailabilityEstimateException;

	/**
	* Removes all the cpd availability estimates where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(String uuid, long companyId);

	/**
	* Returns the number of cpd availability estimates where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cpd availability estimates
	*/
	public int countByUuid_C(String uuid, long companyId);

	/**
	* Returns the cpd availability estimate where CPDefinitionId = &#63; or throws a {@link NoSuchCPDAvailabilityEstimateException} if it could not be found.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the matching cpd availability estimate
	* @throws NoSuchCPDAvailabilityEstimateException if a matching cpd availability estimate could not be found
	*/
	public CPDAvailabilityEstimate findByCPDefinitionId(long CPDefinitionId)
		throws NoSuchCPDAvailabilityEstimateException;

	/**
	* Returns the cpd availability estimate where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the matching cpd availability estimate, or <code>null</code> if a matching cpd availability estimate could not be found
	*/
	public CPDAvailabilityEstimate fetchByCPDefinitionId(long CPDefinitionId);

	/**
	* Returns the cpd availability estimate where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param CPDefinitionId the cp definition ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cpd availability estimate, or <code>null</code> if a matching cpd availability estimate could not be found
	*/
	public CPDAvailabilityEstimate fetchByCPDefinitionId(long CPDefinitionId,
		boolean retrieveFromCache);

	/**
	* Removes the cpd availability estimate where CPDefinitionId = &#63; from the database.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the cpd availability estimate that was removed
	*/
	public CPDAvailabilityEstimate removeByCPDefinitionId(long CPDefinitionId)
		throws NoSuchCPDAvailabilityEstimateException;

	/**
	* Returns the number of cpd availability estimates where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the number of matching cpd availability estimates
	*/
	public int countByCPDefinitionId(long CPDefinitionId);

	/**
	* Returns all the cpd availability estimates where commerceAvailabilityEstimateId = &#63;.
	*
	* @param commerceAvailabilityEstimateId the commerce availability estimate ID
	* @return the matching cpd availability estimates
	*/
	public java.util.List<CPDAvailabilityEstimate> findByCommerceAvailabilityEstimateId(
		long commerceAvailabilityEstimateId);

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
	public java.util.List<CPDAvailabilityEstimate> findByCommerceAvailabilityEstimateId(
		long commerceAvailabilityEstimateId, int start, int end);

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
	public java.util.List<CPDAvailabilityEstimate> findByCommerceAvailabilityEstimateId(
		long commerceAvailabilityEstimateId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDAvailabilityEstimate> orderByComparator);

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
	public java.util.List<CPDAvailabilityEstimate> findByCommerceAvailabilityEstimateId(
		long commerceAvailabilityEstimateId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDAvailabilityEstimate> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cpd availability estimate in the ordered set where commerceAvailabilityEstimateId = &#63;.
	*
	* @param commerceAvailabilityEstimateId the commerce availability estimate ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cpd availability estimate
	* @throws NoSuchCPDAvailabilityEstimateException if a matching cpd availability estimate could not be found
	*/
	public CPDAvailabilityEstimate findByCommerceAvailabilityEstimateId_First(
		long commerceAvailabilityEstimateId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDAvailabilityEstimate> orderByComparator)
		throws NoSuchCPDAvailabilityEstimateException;

	/**
	* Returns the first cpd availability estimate in the ordered set where commerceAvailabilityEstimateId = &#63;.
	*
	* @param commerceAvailabilityEstimateId the commerce availability estimate ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cpd availability estimate, or <code>null</code> if a matching cpd availability estimate could not be found
	*/
	public CPDAvailabilityEstimate fetchByCommerceAvailabilityEstimateId_First(
		long commerceAvailabilityEstimateId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDAvailabilityEstimate> orderByComparator);

	/**
	* Returns the last cpd availability estimate in the ordered set where commerceAvailabilityEstimateId = &#63;.
	*
	* @param commerceAvailabilityEstimateId the commerce availability estimate ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cpd availability estimate
	* @throws NoSuchCPDAvailabilityEstimateException if a matching cpd availability estimate could not be found
	*/
	public CPDAvailabilityEstimate findByCommerceAvailabilityEstimateId_Last(
		long commerceAvailabilityEstimateId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDAvailabilityEstimate> orderByComparator)
		throws NoSuchCPDAvailabilityEstimateException;

	/**
	* Returns the last cpd availability estimate in the ordered set where commerceAvailabilityEstimateId = &#63;.
	*
	* @param commerceAvailabilityEstimateId the commerce availability estimate ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cpd availability estimate, or <code>null</code> if a matching cpd availability estimate could not be found
	*/
	public CPDAvailabilityEstimate fetchByCommerceAvailabilityEstimateId_Last(
		long commerceAvailabilityEstimateId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDAvailabilityEstimate> orderByComparator);

	/**
	* Returns the cpd availability estimates before and after the current cpd availability estimate in the ordered set where commerceAvailabilityEstimateId = &#63;.
	*
	* @param CPDAvailabilityEstimateId the primary key of the current cpd availability estimate
	* @param commerceAvailabilityEstimateId the commerce availability estimate ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cpd availability estimate
	* @throws NoSuchCPDAvailabilityEstimateException if a cpd availability estimate with the primary key could not be found
	*/
	public CPDAvailabilityEstimate[] findByCommerceAvailabilityEstimateId_PrevAndNext(
		long CPDAvailabilityEstimateId, long commerceAvailabilityEstimateId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDAvailabilityEstimate> orderByComparator)
		throws NoSuchCPDAvailabilityEstimateException;

	/**
	* Removes all the cpd availability estimates where commerceAvailabilityEstimateId = &#63; from the database.
	*
	* @param commerceAvailabilityEstimateId the commerce availability estimate ID
	*/
	public void removeByCommerceAvailabilityEstimateId(
		long commerceAvailabilityEstimateId);

	/**
	* Returns the number of cpd availability estimates where commerceAvailabilityEstimateId = &#63;.
	*
	* @param commerceAvailabilityEstimateId the commerce availability estimate ID
	* @return the number of matching cpd availability estimates
	*/
	public int countByCommerceAvailabilityEstimateId(
		long commerceAvailabilityEstimateId);

	/**
	* Caches the cpd availability estimate in the entity cache if it is enabled.
	*
	* @param cpdAvailabilityEstimate the cpd availability estimate
	*/
	public void cacheResult(CPDAvailabilityEstimate cpdAvailabilityEstimate);

	/**
	* Caches the cpd availability estimates in the entity cache if it is enabled.
	*
	* @param cpdAvailabilityEstimates the cpd availability estimates
	*/
	public void cacheResult(
		java.util.List<CPDAvailabilityEstimate> cpdAvailabilityEstimates);

	/**
	* Creates a new cpd availability estimate with the primary key. Does not add the cpd availability estimate to the database.
	*
	* @param CPDAvailabilityEstimateId the primary key for the new cpd availability estimate
	* @return the new cpd availability estimate
	*/
	public CPDAvailabilityEstimate create(long CPDAvailabilityEstimateId);

	/**
	* Removes the cpd availability estimate with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDAvailabilityEstimateId the primary key of the cpd availability estimate
	* @return the cpd availability estimate that was removed
	* @throws NoSuchCPDAvailabilityEstimateException if a cpd availability estimate with the primary key could not be found
	*/
	public CPDAvailabilityEstimate remove(long CPDAvailabilityEstimateId)
		throws NoSuchCPDAvailabilityEstimateException;

	public CPDAvailabilityEstimate updateImpl(
		CPDAvailabilityEstimate cpdAvailabilityEstimate);

	/**
	* Returns the cpd availability estimate with the primary key or throws a {@link NoSuchCPDAvailabilityEstimateException} if it could not be found.
	*
	* @param CPDAvailabilityEstimateId the primary key of the cpd availability estimate
	* @return the cpd availability estimate
	* @throws NoSuchCPDAvailabilityEstimateException if a cpd availability estimate with the primary key could not be found
	*/
	public CPDAvailabilityEstimate findByPrimaryKey(
		long CPDAvailabilityEstimateId)
		throws NoSuchCPDAvailabilityEstimateException;

	/**
	* Returns the cpd availability estimate with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPDAvailabilityEstimateId the primary key of the cpd availability estimate
	* @return the cpd availability estimate, or <code>null</code> if a cpd availability estimate with the primary key could not be found
	*/
	public CPDAvailabilityEstimate fetchByPrimaryKey(
		long CPDAvailabilityEstimateId);

	@Override
	public java.util.Map<java.io.Serializable, CPDAvailabilityEstimate> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the cpd availability estimates.
	*
	* @return the cpd availability estimates
	*/
	public java.util.List<CPDAvailabilityEstimate> findAll();

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
	public java.util.List<CPDAvailabilityEstimate> findAll(int start, int end);

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
	public java.util.List<CPDAvailabilityEstimate> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDAvailabilityEstimate> orderByComparator);

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
	public java.util.List<CPDAvailabilityEstimate> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDAvailabilityEstimate> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the cpd availability estimates from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of cpd availability estimates.
	*
	* @return the number of cpd availability estimates
	*/
	public int countAll();

	@Override
	public java.util.Set<String> getBadColumnNames();
}
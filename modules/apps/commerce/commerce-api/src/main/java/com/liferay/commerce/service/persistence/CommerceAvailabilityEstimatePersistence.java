/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.service.persistence;

import com.liferay.commerce.exception.NoSuchAvailabilityEstimateException;
import com.liferay.commerce.model.CommerceAvailabilityEstimate;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce availability estimate service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAvailabilityEstimateUtil
 * @generated
 */
@ProviderType
public interface CommerceAvailabilityEstimatePersistence
	extends BasePersistence<CommerceAvailabilityEstimate> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceAvailabilityEstimateUtil} to access the commerce availability estimate persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce availability estimates where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce availability estimates
	 */
	public java.util.List<CommerceAvailabilityEstimate> findByUuid(String uuid);

	/**
	 * Returns a range of all the commerce availability estimates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAvailabilityEstimateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce availability estimates
	 * @param end the upper bound of the range of commerce availability estimates (not inclusive)
	 * @return the range of matching commerce availability estimates
	 */
	public java.util.List<CommerceAvailabilityEstimate> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the commerce availability estimates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAvailabilityEstimateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce availability estimates
	 * @param end the upper bound of the range of commerce availability estimates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce availability estimates
	 */
	public java.util.List<CommerceAvailabilityEstimate> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceAvailabilityEstimate> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce availability estimates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAvailabilityEstimateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce availability estimates
	 * @param end the upper bound of the range of commerce availability estimates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce availability estimates
	 */
	public java.util.List<CommerceAvailabilityEstimate> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceAvailabilityEstimate> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce availability estimate in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce availability estimate
	 * @throws NoSuchAvailabilityEstimateException if a matching commerce availability estimate could not be found
	 */
	public CommerceAvailabilityEstimate findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAvailabilityEstimate> orderByComparator)
		throws NoSuchAvailabilityEstimateException;

	/**
	 * Returns the first commerce availability estimate in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce availability estimate, or <code>null</code> if a matching commerce availability estimate could not be found
	 */
	public CommerceAvailabilityEstimate fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceAvailabilityEstimate> orderByComparator);

	/**
	 * Returns the last commerce availability estimate in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce availability estimate
	 * @throws NoSuchAvailabilityEstimateException if a matching commerce availability estimate could not be found
	 */
	public CommerceAvailabilityEstimate findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAvailabilityEstimate> orderByComparator)
		throws NoSuchAvailabilityEstimateException;

	/**
	 * Returns the last commerce availability estimate in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce availability estimate, or <code>null</code> if a matching commerce availability estimate could not be found
	 */
	public CommerceAvailabilityEstimate fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceAvailabilityEstimate> orderByComparator);

	/**
	 * Returns the commerce availability estimates before and after the current commerce availability estimate in the ordered set where uuid = &#63;.
	 *
	 * @param commerceAvailabilityEstimateId the primary key of the current commerce availability estimate
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce availability estimate
	 * @throws NoSuchAvailabilityEstimateException if a commerce availability estimate with the primary key could not be found
	 */
	public CommerceAvailabilityEstimate[] findByUuid_PrevAndNext(
			long commerceAvailabilityEstimateId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAvailabilityEstimate> orderByComparator)
		throws NoSuchAvailabilityEstimateException;

	/**
	 * Removes all the commerce availability estimates where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of commerce availability estimates where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce availability estimates
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the commerce availability estimates where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce availability estimates
	 */
	public java.util.List<CommerceAvailabilityEstimate> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the commerce availability estimates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAvailabilityEstimateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce availability estimates
	 * @param end the upper bound of the range of commerce availability estimates (not inclusive)
	 * @return the range of matching commerce availability estimates
	 */
	public java.util.List<CommerceAvailabilityEstimate> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce availability estimates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAvailabilityEstimateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce availability estimates
	 * @param end the upper bound of the range of commerce availability estimates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce availability estimates
	 */
	public java.util.List<CommerceAvailabilityEstimate> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceAvailabilityEstimate> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce availability estimates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAvailabilityEstimateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce availability estimates
	 * @param end the upper bound of the range of commerce availability estimates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce availability estimates
	 */
	public java.util.List<CommerceAvailabilityEstimate> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceAvailabilityEstimate> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce availability estimate in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce availability estimate
	 * @throws NoSuchAvailabilityEstimateException if a matching commerce availability estimate could not be found
	 */
	public CommerceAvailabilityEstimate findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAvailabilityEstimate> orderByComparator)
		throws NoSuchAvailabilityEstimateException;

	/**
	 * Returns the first commerce availability estimate in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce availability estimate, or <code>null</code> if a matching commerce availability estimate could not be found
	 */
	public CommerceAvailabilityEstimate fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceAvailabilityEstimate> orderByComparator);

	/**
	 * Returns the last commerce availability estimate in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce availability estimate
	 * @throws NoSuchAvailabilityEstimateException if a matching commerce availability estimate could not be found
	 */
	public CommerceAvailabilityEstimate findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAvailabilityEstimate> orderByComparator)
		throws NoSuchAvailabilityEstimateException;

	/**
	 * Returns the last commerce availability estimate in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce availability estimate, or <code>null</code> if a matching commerce availability estimate could not be found
	 */
	public CommerceAvailabilityEstimate fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceAvailabilityEstimate> orderByComparator);

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
	public CommerceAvailabilityEstimate[] findByUuid_C_PrevAndNext(
			long commerceAvailabilityEstimateId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAvailabilityEstimate> orderByComparator)
		throws NoSuchAvailabilityEstimateException;

	/**
	 * Removes all the commerce availability estimates where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of commerce availability estimates where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce availability estimates
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the commerce availability estimates where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce availability estimates
	 */
	public java.util.List<CommerceAvailabilityEstimate> findByCompanyId(
		long companyId);

	/**
	 * Returns a range of all the commerce availability estimates where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAvailabilityEstimateModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce availability estimates
	 * @param end the upper bound of the range of commerce availability estimates (not inclusive)
	 * @return the range of matching commerce availability estimates
	 */
	public java.util.List<CommerceAvailabilityEstimate> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce availability estimates where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAvailabilityEstimateModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce availability estimates
	 * @param end the upper bound of the range of commerce availability estimates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce availability estimates
	 */
	public java.util.List<CommerceAvailabilityEstimate> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceAvailabilityEstimate> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce availability estimates where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAvailabilityEstimateModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce availability estimates
	 * @param end the upper bound of the range of commerce availability estimates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce availability estimates
	 */
	public java.util.List<CommerceAvailabilityEstimate> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceAvailabilityEstimate> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce availability estimate in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce availability estimate
	 * @throws NoSuchAvailabilityEstimateException if a matching commerce availability estimate could not be found
	 */
	public CommerceAvailabilityEstimate findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAvailabilityEstimate> orderByComparator)
		throws NoSuchAvailabilityEstimateException;

	/**
	 * Returns the first commerce availability estimate in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce availability estimate, or <code>null</code> if a matching commerce availability estimate could not be found
	 */
	public CommerceAvailabilityEstimate fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceAvailabilityEstimate> orderByComparator);

	/**
	 * Returns the last commerce availability estimate in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce availability estimate
	 * @throws NoSuchAvailabilityEstimateException if a matching commerce availability estimate could not be found
	 */
	public CommerceAvailabilityEstimate findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAvailabilityEstimate> orderByComparator)
		throws NoSuchAvailabilityEstimateException;

	/**
	 * Returns the last commerce availability estimate in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce availability estimate, or <code>null</code> if a matching commerce availability estimate could not be found
	 */
	public CommerceAvailabilityEstimate fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceAvailabilityEstimate> orderByComparator);

	/**
	 * Returns the commerce availability estimates before and after the current commerce availability estimate in the ordered set where companyId = &#63;.
	 *
	 * @param commerceAvailabilityEstimateId the primary key of the current commerce availability estimate
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce availability estimate
	 * @throws NoSuchAvailabilityEstimateException if a commerce availability estimate with the primary key could not be found
	 */
	public CommerceAvailabilityEstimate[] findByCompanyId_PrevAndNext(
			long commerceAvailabilityEstimateId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAvailabilityEstimate> orderByComparator)
		throws NoSuchAvailabilityEstimateException;

	/**
	 * Removes all the commerce availability estimates where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of commerce availability estimates where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce availability estimates
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Caches the commerce availability estimate in the entity cache if it is enabled.
	 *
	 * @param commerceAvailabilityEstimate the commerce availability estimate
	 */
	public void cacheResult(
		CommerceAvailabilityEstimate commerceAvailabilityEstimate);

	/**
	 * Caches the commerce availability estimates in the entity cache if it is enabled.
	 *
	 * @param commerceAvailabilityEstimates the commerce availability estimates
	 */
	public void cacheResult(
		java.util.List<CommerceAvailabilityEstimate>
			commerceAvailabilityEstimates);

	/**
	 * Creates a new commerce availability estimate with the primary key. Does not add the commerce availability estimate to the database.
	 *
	 * @param commerceAvailabilityEstimateId the primary key for the new commerce availability estimate
	 * @return the new commerce availability estimate
	 */
	public CommerceAvailabilityEstimate create(
		long commerceAvailabilityEstimateId);

	/**
	 * Removes the commerce availability estimate with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceAvailabilityEstimateId the primary key of the commerce availability estimate
	 * @return the commerce availability estimate that was removed
	 * @throws NoSuchAvailabilityEstimateException if a commerce availability estimate with the primary key could not be found
	 */
	public CommerceAvailabilityEstimate remove(
			long commerceAvailabilityEstimateId)
		throws NoSuchAvailabilityEstimateException;

	public CommerceAvailabilityEstimate updateImpl(
		CommerceAvailabilityEstimate commerceAvailabilityEstimate);

	/**
	 * Returns the commerce availability estimate with the primary key or throws a <code>NoSuchAvailabilityEstimateException</code> if it could not be found.
	 *
	 * @param commerceAvailabilityEstimateId the primary key of the commerce availability estimate
	 * @return the commerce availability estimate
	 * @throws NoSuchAvailabilityEstimateException if a commerce availability estimate with the primary key could not be found
	 */
	public CommerceAvailabilityEstimate findByPrimaryKey(
			long commerceAvailabilityEstimateId)
		throws NoSuchAvailabilityEstimateException;

	/**
	 * Returns the commerce availability estimate with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceAvailabilityEstimateId the primary key of the commerce availability estimate
	 * @return the commerce availability estimate, or <code>null</code> if a commerce availability estimate with the primary key could not be found
	 */
	public CommerceAvailabilityEstimate fetchByPrimaryKey(
		long commerceAvailabilityEstimateId);

	/**
	 * Returns all the commerce availability estimates.
	 *
	 * @return the commerce availability estimates
	 */
	public java.util.List<CommerceAvailabilityEstimate> findAll();

	/**
	 * Returns a range of all the commerce availability estimates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAvailabilityEstimateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce availability estimates
	 * @param end the upper bound of the range of commerce availability estimates (not inclusive)
	 * @return the range of commerce availability estimates
	 */
	public java.util.List<CommerceAvailabilityEstimate> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the commerce availability estimates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAvailabilityEstimateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce availability estimates
	 * @param end the upper bound of the range of commerce availability estimates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce availability estimates
	 */
	public java.util.List<CommerceAvailabilityEstimate> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceAvailabilityEstimate> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce availability estimates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAvailabilityEstimateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce availability estimates
	 * @param end the upper bound of the range of commerce availability estimates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce availability estimates
	 */
	public java.util.List<CommerceAvailabilityEstimate> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceAvailabilityEstimate> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce availability estimates from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce availability estimates.
	 *
	 * @return the number of commerce availability estimates
	 */
	public int countAll();

}
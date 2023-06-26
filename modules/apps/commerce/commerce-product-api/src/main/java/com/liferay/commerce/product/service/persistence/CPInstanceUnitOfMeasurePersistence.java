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

package com.liferay.commerce.product.service.persistence;

import com.liferay.commerce.product.exception.NoSuchCPInstanceUnitOfMeasureException;
import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the cp instance unit of measure service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPInstanceUnitOfMeasureUtil
 * @generated
 */
@ProviderType
public interface CPInstanceUnitOfMeasurePersistence
	extends BasePersistence<CPInstanceUnitOfMeasure>,
			CTPersistence<CPInstanceUnitOfMeasure> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPInstanceUnitOfMeasureUtil} to access the cp instance unit of measure persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the cp instance unit of measures where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp instance unit of measures
	 */
	public java.util.List<CPInstanceUnitOfMeasure> findByUuid(String uuid);

	/**
	 * Returns a range of all the cp instance unit of measures where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @return the range of matching cp instance unit of measures
	 */
	public java.util.List<CPInstanceUnitOfMeasure> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the cp instance unit of measures where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp instance unit of measures
	 */
	public java.util.List<CPInstanceUnitOfMeasure> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceUnitOfMeasure> orderByComparator);

	/**
	 * Returns an ordered range of all the cp instance unit of measures where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instance unit of measures
	 */
	public java.util.List<CPInstanceUnitOfMeasure> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceUnitOfMeasure> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp instance unit of measure in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	public CPInstanceUnitOfMeasure findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException;

	/**
	 * Returns the first cp instance unit of measure in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	public CPInstanceUnitOfMeasure fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceUnitOfMeasure> orderByComparator);

	/**
	 * Returns the last cp instance unit of measure in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	public CPInstanceUnitOfMeasure findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException;

	/**
	 * Returns the last cp instance unit of measure in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	public CPInstanceUnitOfMeasure fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceUnitOfMeasure> orderByComparator);

	/**
	 * Returns the cp instance unit of measures before and after the current cp instance unit of measure in the ordered set where uuid = &#63;.
	 *
	 * @param CPInstanceUnitOfMeasureId the primary key of the current cp instance unit of measure
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a cp instance unit of measure with the primary key could not be found
	 */
	public CPInstanceUnitOfMeasure[] findByUuid_PrevAndNext(
			long CPInstanceUnitOfMeasureId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException;

	/**
	 * Removes all the cp instance unit of measures where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of cp instance unit of measures where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp instance unit of measures
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the cp instance unit of measures where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp instance unit of measures
	 */
	public java.util.List<CPInstanceUnitOfMeasure> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the cp instance unit of measures where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @return the range of matching cp instance unit of measures
	 */
	public java.util.List<CPInstanceUnitOfMeasure> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the cp instance unit of measures where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp instance unit of measures
	 */
	public java.util.List<CPInstanceUnitOfMeasure> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceUnitOfMeasure> orderByComparator);

	/**
	 * Returns an ordered range of all the cp instance unit of measures where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instance unit of measures
	 */
	public java.util.List<CPInstanceUnitOfMeasure> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceUnitOfMeasure> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp instance unit of measure in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	public CPInstanceUnitOfMeasure findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException;

	/**
	 * Returns the first cp instance unit of measure in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	public CPInstanceUnitOfMeasure fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceUnitOfMeasure> orderByComparator);

	/**
	 * Returns the last cp instance unit of measure in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	public CPInstanceUnitOfMeasure findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException;

	/**
	 * Returns the last cp instance unit of measure in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	public CPInstanceUnitOfMeasure fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceUnitOfMeasure> orderByComparator);

	/**
	 * Returns the cp instance unit of measures before and after the current cp instance unit of measure in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CPInstanceUnitOfMeasureId the primary key of the current cp instance unit of measure
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a cp instance unit of measure with the primary key could not be found
	 */
	public CPInstanceUnitOfMeasure[] findByUuid_C_PrevAndNext(
			long CPInstanceUnitOfMeasureId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException;

	/**
	 * Removes all the cp instance unit of measures where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of cp instance unit of measures where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp instance unit of measures
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the cp instance unit of measures where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @return the matching cp instance unit of measures
	 */
	public java.util.List<CPInstanceUnitOfMeasure> findByCPInstanceId(
		long CPInstanceId);

	/**
	 * Returns a range of all the cp instance unit of measures where CPInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @return the range of matching cp instance unit of measures
	 */
	public java.util.List<CPInstanceUnitOfMeasure> findByCPInstanceId(
		long CPInstanceId, int start, int end);

	/**
	 * Returns an ordered range of all the cp instance unit of measures where CPInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp instance unit of measures
	 */
	public java.util.List<CPInstanceUnitOfMeasure> findByCPInstanceId(
		long CPInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceUnitOfMeasure> orderByComparator);

	/**
	 * Returns an ordered range of all the cp instance unit of measures where CPInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instance unit of measures
	 */
	public java.util.List<CPInstanceUnitOfMeasure> findByCPInstanceId(
		long CPInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceUnitOfMeasure> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp instance unit of measure in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	public CPInstanceUnitOfMeasure findByCPInstanceId_First(
			long CPInstanceId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException;

	/**
	 * Returns the first cp instance unit of measure in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	public CPInstanceUnitOfMeasure fetchByCPInstanceId_First(
		long CPInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceUnitOfMeasure> orderByComparator);

	/**
	 * Returns the last cp instance unit of measure in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	public CPInstanceUnitOfMeasure findByCPInstanceId_Last(
			long CPInstanceId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException;

	/**
	 * Returns the last cp instance unit of measure in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	public CPInstanceUnitOfMeasure fetchByCPInstanceId_Last(
		long CPInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceUnitOfMeasure> orderByComparator);

	/**
	 * Returns the cp instance unit of measures before and after the current cp instance unit of measure in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceUnitOfMeasureId the primary key of the current cp instance unit of measure
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a cp instance unit of measure with the primary key could not be found
	 */
	public CPInstanceUnitOfMeasure[] findByCPInstanceId_PrevAndNext(
			long CPInstanceUnitOfMeasureId, long CPInstanceId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException;

	/**
	 * Removes all the cp instance unit of measures where CPInstanceId = &#63; from the database.
	 *
	 * @param CPInstanceId the cp instance ID
	 */
	public void removeByCPInstanceId(long CPInstanceId);

	/**
	 * Returns the number of cp instance unit of measures where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @return the number of matching cp instance unit of measures
	 */
	public int countByCPInstanceId(long CPInstanceId);

	/**
	 * Returns all the cp instance unit of measures where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @return the matching cp instance unit of measures
	 */
	public java.util.List<CPInstanceUnitOfMeasure> findByC_SKU(
		long companyId, String sku);

	/**
	 * Returns a range of all the cp instance unit of measures where companyId = &#63; and sku = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @return the range of matching cp instance unit of measures
	 */
	public java.util.List<CPInstanceUnitOfMeasure> findByC_SKU(
		long companyId, String sku, int start, int end);

	/**
	 * Returns an ordered range of all the cp instance unit of measures where companyId = &#63; and sku = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp instance unit of measures
	 */
	public java.util.List<CPInstanceUnitOfMeasure> findByC_SKU(
		long companyId, String sku, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceUnitOfMeasure> orderByComparator);

	/**
	 * Returns an ordered range of all the cp instance unit of measures where companyId = &#63; and sku = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instance unit of measures
	 */
	public java.util.List<CPInstanceUnitOfMeasure> findByC_SKU(
		long companyId, String sku, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceUnitOfMeasure> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp instance unit of measure in the ordered set where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	public CPInstanceUnitOfMeasure findByC_SKU_First(
			long companyId, String sku,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException;

	/**
	 * Returns the first cp instance unit of measure in the ordered set where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	public CPInstanceUnitOfMeasure fetchByC_SKU_First(
		long companyId, String sku,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceUnitOfMeasure> orderByComparator);

	/**
	 * Returns the last cp instance unit of measure in the ordered set where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	public CPInstanceUnitOfMeasure findByC_SKU_Last(
			long companyId, String sku,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException;

	/**
	 * Returns the last cp instance unit of measure in the ordered set where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	public CPInstanceUnitOfMeasure fetchByC_SKU_Last(
		long companyId, String sku,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceUnitOfMeasure> orderByComparator);

	/**
	 * Returns the cp instance unit of measures before and after the current cp instance unit of measure in the ordered set where companyId = &#63; and sku = &#63;.
	 *
	 * @param CPInstanceUnitOfMeasureId the primary key of the current cp instance unit of measure
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a cp instance unit of measure with the primary key could not be found
	 */
	public CPInstanceUnitOfMeasure[] findByC_SKU_PrevAndNext(
			long CPInstanceUnitOfMeasureId, long companyId, String sku,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException;

	/**
	 * Removes all the cp instance unit of measures where companyId = &#63; and sku = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 */
	public void removeByC_SKU(long companyId, String sku);

	/**
	 * Returns the number of cp instance unit of measures where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @return the number of matching cp instance unit of measures
	 */
	public int countByC_SKU(long companyId, String sku);

	/**
	 * Returns the cp instance unit of measure where CPInstanceId = &#63; and key = &#63; or throws a <code>NoSuchCPInstanceUnitOfMeasureException</code> if it could not be found.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param key the key
	 * @return the matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	public CPInstanceUnitOfMeasure findByC_K(long CPInstanceId, String key)
		throws NoSuchCPInstanceUnitOfMeasureException;

	/**
	 * Returns the cp instance unit of measure where CPInstanceId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param key the key
	 * @return the matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	public CPInstanceUnitOfMeasure fetchByC_K(long CPInstanceId, String key);

	/**
	 * Returns the cp instance unit of measure where CPInstanceId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param key the key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	public CPInstanceUnitOfMeasure fetchByC_K(
		long CPInstanceId, String key, boolean useFinderCache);

	/**
	 * Removes the cp instance unit of measure where CPInstanceId = &#63; and key = &#63; from the database.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param key the key
	 * @return the cp instance unit of measure that was removed
	 */
	public CPInstanceUnitOfMeasure removeByC_K(long CPInstanceId, String key)
		throws NoSuchCPInstanceUnitOfMeasureException;

	/**
	 * Returns the number of cp instance unit of measures where CPInstanceId = &#63; and key = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param key the key
	 * @return the number of matching cp instance unit of measures
	 */
	public int countByC_K(long CPInstanceId, String key);

	/**
	 * Returns all the cp instance unit of measures where key = &#63; and sku = &#63;.
	 *
	 * @param key the key
	 * @param sku the sku
	 * @return the matching cp instance unit of measures
	 */
	public java.util.List<CPInstanceUnitOfMeasure> findByK_SKU(
		String key, String sku);

	/**
	 * Returns a range of all the cp instance unit of measures where key = &#63; and sku = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param key the key
	 * @param sku the sku
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @return the range of matching cp instance unit of measures
	 */
	public java.util.List<CPInstanceUnitOfMeasure> findByK_SKU(
		String key, String sku, int start, int end);

	/**
	 * Returns an ordered range of all the cp instance unit of measures where key = &#63; and sku = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param key the key
	 * @param sku the sku
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp instance unit of measures
	 */
	public java.util.List<CPInstanceUnitOfMeasure> findByK_SKU(
		String key, String sku, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceUnitOfMeasure> orderByComparator);

	/**
	 * Returns an ordered range of all the cp instance unit of measures where key = &#63; and sku = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param key the key
	 * @param sku the sku
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instance unit of measures
	 */
	public java.util.List<CPInstanceUnitOfMeasure> findByK_SKU(
		String key, String sku, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceUnitOfMeasure> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp instance unit of measure in the ordered set where key = &#63; and sku = &#63;.
	 *
	 * @param key the key
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	public CPInstanceUnitOfMeasure findByK_SKU_First(
			String key, String sku,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException;

	/**
	 * Returns the first cp instance unit of measure in the ordered set where key = &#63; and sku = &#63;.
	 *
	 * @param key the key
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	public CPInstanceUnitOfMeasure fetchByK_SKU_First(
		String key, String sku,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceUnitOfMeasure> orderByComparator);

	/**
	 * Returns the last cp instance unit of measure in the ordered set where key = &#63; and sku = &#63;.
	 *
	 * @param key the key
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	public CPInstanceUnitOfMeasure findByK_SKU_Last(
			String key, String sku,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException;

	/**
	 * Returns the last cp instance unit of measure in the ordered set where key = &#63; and sku = &#63;.
	 *
	 * @param key the key
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	public CPInstanceUnitOfMeasure fetchByK_SKU_Last(
		String key, String sku,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceUnitOfMeasure> orderByComparator);

	/**
	 * Returns the cp instance unit of measures before and after the current cp instance unit of measure in the ordered set where key = &#63; and sku = &#63;.
	 *
	 * @param CPInstanceUnitOfMeasureId the primary key of the current cp instance unit of measure
	 * @param key the key
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a cp instance unit of measure with the primary key could not be found
	 */
	public CPInstanceUnitOfMeasure[] findByK_SKU_PrevAndNext(
			long CPInstanceUnitOfMeasureId, String key, String sku,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException;

	/**
	 * Removes all the cp instance unit of measures where key = &#63; and sku = &#63; from the database.
	 *
	 * @param key the key
	 * @param sku the sku
	 */
	public void removeByK_SKU(String key, String sku);

	/**
	 * Returns the number of cp instance unit of measures where key = &#63; and sku = &#63;.
	 *
	 * @param key the key
	 * @param sku the sku
	 * @return the number of matching cp instance unit of measures
	 */
	public int countByK_SKU(String key, String sku);

	/**
	 * Caches the cp instance unit of measure in the entity cache if it is enabled.
	 *
	 * @param cpInstanceUnitOfMeasure the cp instance unit of measure
	 */
	public void cacheResult(CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure);

	/**
	 * Caches the cp instance unit of measures in the entity cache if it is enabled.
	 *
	 * @param cpInstanceUnitOfMeasures the cp instance unit of measures
	 */
	public void cacheResult(
		java.util.List<CPInstanceUnitOfMeasure> cpInstanceUnitOfMeasures);

	/**
	 * Creates a new cp instance unit of measure with the primary key. Does not add the cp instance unit of measure to the database.
	 *
	 * @param CPInstanceUnitOfMeasureId the primary key for the new cp instance unit of measure
	 * @return the new cp instance unit of measure
	 */
	public CPInstanceUnitOfMeasure create(long CPInstanceUnitOfMeasureId);

	/**
	 * Removes the cp instance unit of measure with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPInstanceUnitOfMeasureId the primary key of the cp instance unit of measure
	 * @return the cp instance unit of measure that was removed
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a cp instance unit of measure with the primary key could not be found
	 */
	public CPInstanceUnitOfMeasure remove(long CPInstanceUnitOfMeasureId)
		throws NoSuchCPInstanceUnitOfMeasureException;

	public CPInstanceUnitOfMeasure updateImpl(
		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure);

	/**
	 * Returns the cp instance unit of measure with the primary key or throws a <code>NoSuchCPInstanceUnitOfMeasureException</code> if it could not be found.
	 *
	 * @param CPInstanceUnitOfMeasureId the primary key of the cp instance unit of measure
	 * @return the cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a cp instance unit of measure with the primary key could not be found
	 */
	public CPInstanceUnitOfMeasure findByPrimaryKey(
			long CPInstanceUnitOfMeasureId)
		throws NoSuchCPInstanceUnitOfMeasureException;

	/**
	 * Returns the cp instance unit of measure with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPInstanceUnitOfMeasureId the primary key of the cp instance unit of measure
	 * @return the cp instance unit of measure, or <code>null</code> if a cp instance unit of measure with the primary key could not be found
	 */
	public CPInstanceUnitOfMeasure fetchByPrimaryKey(
		long CPInstanceUnitOfMeasureId);

	/**
	 * Returns all the cp instance unit of measures.
	 *
	 * @return the cp instance unit of measures
	 */
	public java.util.List<CPInstanceUnitOfMeasure> findAll();

	/**
	 * Returns a range of all the cp instance unit of measures.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @return the range of cp instance unit of measures
	 */
	public java.util.List<CPInstanceUnitOfMeasure> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the cp instance unit of measures.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp instance unit of measures
	 */
	public java.util.List<CPInstanceUnitOfMeasure> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceUnitOfMeasure> orderByComparator);

	/**
	 * Returns an ordered range of all the cp instance unit of measures.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp instance unit of measures
	 */
	public java.util.List<CPInstanceUnitOfMeasure> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceUnitOfMeasure> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the cp instance unit of measures from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of cp instance unit of measures.
	 *
	 * @return the number of cp instance unit of measures
	 */
	public int countAll();

}
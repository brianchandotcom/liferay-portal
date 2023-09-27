/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public java.util.List<CPInstanceUnitOfMeasure> findByC_S(
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
	public java.util.List<CPInstanceUnitOfMeasure> findByC_S(
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
	public java.util.List<CPInstanceUnitOfMeasure> findByC_S(
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
	public java.util.List<CPInstanceUnitOfMeasure> findByC_S(
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
	public CPInstanceUnitOfMeasure findByC_S_First(
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
	public CPInstanceUnitOfMeasure fetchByC_S_First(
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
	public CPInstanceUnitOfMeasure findByC_S_Last(
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
	public CPInstanceUnitOfMeasure fetchByC_S_Last(
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
	public CPInstanceUnitOfMeasure[] findByC_S_PrevAndNext(
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
	public void removeByC_S(long companyId, String sku);

	/**
	 * Returns the number of cp instance unit of measures where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @return the number of matching cp instance unit of measures
	 */
	public int countByC_S(long companyId, String sku);

	/**
	 * Returns all the cp instance unit of measures where CPInstanceId = &#63; and active = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param active the active
	 * @return the matching cp instance unit of measures
	 */
	public java.util.List<CPInstanceUnitOfMeasure> findByC_A(
		long CPInstanceId, boolean active);

	/**
	 * Returns a range of all the cp instance unit of measures where CPInstanceId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param active the active
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @return the range of matching cp instance unit of measures
	 */
	public java.util.List<CPInstanceUnitOfMeasure> findByC_A(
		long CPInstanceId, boolean active, int start, int end);

	/**
	 * Returns an ordered range of all the cp instance unit of measures where CPInstanceId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param active the active
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp instance unit of measures
	 */
	public java.util.List<CPInstanceUnitOfMeasure> findByC_A(
		long CPInstanceId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceUnitOfMeasure> orderByComparator);

	/**
	 * Returns an ordered range of all the cp instance unit of measures where CPInstanceId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param active the active
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instance unit of measures
	 */
	public java.util.List<CPInstanceUnitOfMeasure> findByC_A(
		long CPInstanceId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceUnitOfMeasure> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp instance unit of measure in the ordered set where CPInstanceId = &#63; and active = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	public CPInstanceUnitOfMeasure findByC_A_First(
			long CPInstanceId, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException;

	/**
	 * Returns the first cp instance unit of measure in the ordered set where CPInstanceId = &#63; and active = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	public CPInstanceUnitOfMeasure fetchByC_A_First(
		long CPInstanceId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceUnitOfMeasure> orderByComparator);

	/**
	 * Returns the last cp instance unit of measure in the ordered set where CPInstanceId = &#63; and active = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	public CPInstanceUnitOfMeasure findByC_A_Last(
			long CPInstanceId, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException;

	/**
	 * Returns the last cp instance unit of measure in the ordered set where CPInstanceId = &#63; and active = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	public CPInstanceUnitOfMeasure fetchByC_A_Last(
		long CPInstanceId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceUnitOfMeasure> orderByComparator);

	/**
	 * Returns the cp instance unit of measures before and after the current cp instance unit of measure in the ordered set where CPInstanceId = &#63; and active = &#63;.
	 *
	 * @param CPInstanceUnitOfMeasureId the primary key of the current cp instance unit of measure
	 * @param CPInstanceId the cp instance ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a cp instance unit of measure with the primary key could not be found
	 */
	public CPInstanceUnitOfMeasure[] findByC_A_PrevAndNext(
			long CPInstanceUnitOfMeasureId, long CPInstanceId, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException;

	/**
	 * Removes all the cp instance unit of measures where CPInstanceId = &#63; and active = &#63; from the database.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param active the active
	 */
	public void removeByC_A(long CPInstanceId, boolean active);

	/**
	 * Returns the number of cp instance unit of measures where CPInstanceId = &#63; and active = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param active the active
	 * @return the number of matching cp instance unit of measures
	 */
	public int countByC_A(long CPInstanceId, boolean active);

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
	 * Returns all the cp instance unit of measures where CPInstanceId = &#63; and primary = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param primary the primary
	 * @return the matching cp instance unit of measures
	 */
	public java.util.List<CPInstanceUnitOfMeasure> findByC_P(
		long CPInstanceId, boolean primary);

	/**
	 * Returns a range of all the cp instance unit of measures where CPInstanceId = &#63; and primary = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param primary the primary
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @return the range of matching cp instance unit of measures
	 */
	public java.util.List<CPInstanceUnitOfMeasure> findByC_P(
		long CPInstanceId, boolean primary, int start, int end);

	/**
	 * Returns an ordered range of all the cp instance unit of measures where CPInstanceId = &#63; and primary = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param primary the primary
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp instance unit of measures
	 */
	public java.util.List<CPInstanceUnitOfMeasure> findByC_P(
		long CPInstanceId, boolean primary, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceUnitOfMeasure> orderByComparator);

	/**
	 * Returns an ordered range of all the cp instance unit of measures where CPInstanceId = &#63; and primary = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param primary the primary
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instance unit of measures
	 */
	public java.util.List<CPInstanceUnitOfMeasure> findByC_P(
		long CPInstanceId, boolean primary, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceUnitOfMeasure> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp instance unit of measure in the ordered set where CPInstanceId = &#63; and primary = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param primary the primary
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	public CPInstanceUnitOfMeasure findByC_P_First(
			long CPInstanceId, boolean primary,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException;

	/**
	 * Returns the first cp instance unit of measure in the ordered set where CPInstanceId = &#63; and primary = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param primary the primary
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	public CPInstanceUnitOfMeasure fetchByC_P_First(
		long CPInstanceId, boolean primary,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceUnitOfMeasure> orderByComparator);

	/**
	 * Returns the last cp instance unit of measure in the ordered set where CPInstanceId = &#63; and primary = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param primary the primary
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	public CPInstanceUnitOfMeasure findByC_P_Last(
			long CPInstanceId, boolean primary,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException;

	/**
	 * Returns the last cp instance unit of measure in the ordered set where CPInstanceId = &#63; and primary = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param primary the primary
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	public CPInstanceUnitOfMeasure fetchByC_P_Last(
		long CPInstanceId, boolean primary,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceUnitOfMeasure> orderByComparator);

	/**
	 * Returns the cp instance unit of measures before and after the current cp instance unit of measure in the ordered set where CPInstanceId = &#63; and primary = &#63;.
	 *
	 * @param CPInstanceUnitOfMeasureId the primary key of the current cp instance unit of measure
	 * @param CPInstanceId the cp instance ID
	 * @param primary the primary
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a cp instance unit of measure with the primary key could not be found
	 */
	public CPInstanceUnitOfMeasure[] findByC_P_PrevAndNext(
			long CPInstanceUnitOfMeasureId, long CPInstanceId, boolean primary,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException;

	/**
	 * Removes all the cp instance unit of measures where CPInstanceId = &#63; and primary = &#63; from the database.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param primary the primary
	 */
	public void removeByC_P(long CPInstanceId, boolean primary);

	/**
	 * Returns the number of cp instance unit of measures where CPInstanceId = &#63; and primary = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param primary the primary
	 * @return the number of matching cp instance unit of measures
	 */
	public int countByC_P(long CPInstanceId, boolean primary);

	/**
	 * Returns all the cp instance unit of measures where companyId = &#63; and key = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param sku the sku
	 * @return the matching cp instance unit of measures
	 */
	public java.util.List<CPInstanceUnitOfMeasure> findByC_K_S(
		long companyId, String key, String sku);

	/**
	 * Returns a range of all the cp instance unit of measures where companyId = &#63; and key = &#63; and sku = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param sku the sku
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @return the range of matching cp instance unit of measures
	 */
	public java.util.List<CPInstanceUnitOfMeasure> findByC_K_S(
		long companyId, String key, String sku, int start, int end);

	/**
	 * Returns an ordered range of all the cp instance unit of measures where companyId = &#63; and key = &#63; and sku = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param sku the sku
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp instance unit of measures
	 */
	public java.util.List<CPInstanceUnitOfMeasure> findByC_K_S(
		long companyId, String key, String sku, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceUnitOfMeasure> orderByComparator);

	/**
	 * Returns an ordered range of all the cp instance unit of measures where companyId = &#63; and key = &#63; and sku = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param sku the sku
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instance unit of measures
	 */
	public java.util.List<CPInstanceUnitOfMeasure> findByC_K_S(
		long companyId, String key, String sku, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceUnitOfMeasure> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp instance unit of measure in the ordered set where companyId = &#63; and key = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	public CPInstanceUnitOfMeasure findByC_K_S_First(
			long companyId, String key, String sku,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException;

	/**
	 * Returns the first cp instance unit of measure in the ordered set where companyId = &#63; and key = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	public CPInstanceUnitOfMeasure fetchByC_K_S_First(
		long companyId, String key, String sku,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceUnitOfMeasure> orderByComparator);

	/**
	 * Returns the last cp instance unit of measure in the ordered set where companyId = &#63; and key = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	public CPInstanceUnitOfMeasure findByC_K_S_Last(
			long companyId, String key, String sku,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException;

	/**
	 * Returns the last cp instance unit of measure in the ordered set where companyId = &#63; and key = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	public CPInstanceUnitOfMeasure fetchByC_K_S_Last(
		long companyId, String key, String sku,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceUnitOfMeasure> orderByComparator);

	/**
	 * Returns the cp instance unit of measures before and after the current cp instance unit of measure in the ordered set where companyId = &#63; and key = &#63; and sku = &#63;.
	 *
	 * @param CPInstanceUnitOfMeasureId the primary key of the current cp instance unit of measure
	 * @param companyId the company ID
	 * @param key the key
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a cp instance unit of measure with the primary key could not be found
	 */
	public CPInstanceUnitOfMeasure[] findByC_K_S_PrevAndNext(
			long CPInstanceUnitOfMeasureId, long companyId, String key,
			String sku,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPInstanceUnitOfMeasure> orderByComparator)
		throws NoSuchCPInstanceUnitOfMeasureException;

	/**
	 * Removes all the cp instance unit of measures where companyId = &#63; and key = &#63; and sku = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param sku the sku
	 */
	public void removeByC_K_S(long companyId, String key, String sku);

	/**
	 * Returns the number of cp instance unit of measures where companyId = &#63; and key = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param sku the sku
	 * @return the number of matching cp instance unit of measures
	 */
	public int countByC_K_S(long companyId, String key, String sku);

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
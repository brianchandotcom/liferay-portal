/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.persistence;

import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the cp instance unit of measure service. This utility wraps <code>com.liferay.commerce.product.service.persistence.impl.CPInstanceUnitOfMeasurePersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPInstanceUnitOfMeasurePersistence
 * @generated
 */
public class CPInstanceUnitOfMeasureUtil {

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
		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure) {

		getPersistence().clearCache(cpInstanceUnitOfMeasure);
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
	public static Map<Serializable, CPInstanceUnitOfMeasure> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CPInstanceUnitOfMeasure> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CPInstanceUnitOfMeasure> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CPInstanceUnitOfMeasure> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CPInstanceUnitOfMeasure update(
		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure) {

		return getPersistence().update(cpInstanceUnitOfMeasure);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CPInstanceUnitOfMeasure update(
		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure,
		ServiceContext serviceContext) {

		return getPersistence().update(cpInstanceUnitOfMeasure, serviceContext);
	}

	/**
	 * Returns all the cp instance unit of measures where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp instance unit of measures
	 */
	public static List<CPInstanceUnitOfMeasure> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

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
	public static List<CPInstanceUnitOfMeasure> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

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
	public static List<CPInstanceUnitOfMeasure> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

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
	public static List<CPInstanceUnitOfMeasure> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cp instance unit of measure in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	public static CPInstanceUnitOfMeasure findByUuid_First(
			String uuid,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceUnitOfMeasureException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first cp instance unit of measure in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	public static CPInstanceUnitOfMeasure fetchByUuid_First(
		String uuid,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last cp instance unit of measure in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	public static CPInstanceUnitOfMeasure findByUuid_Last(
			String uuid,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceUnitOfMeasureException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last cp instance unit of measure in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	public static CPInstanceUnitOfMeasure fetchByUuid_Last(
		String uuid,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the cp instance unit of measures before and after the current cp instance unit of measure in the ordered set where uuid = &#63;.
	 *
	 * @param CPInstanceUnitOfMeasureId the primary key of the current cp instance unit of measure
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a cp instance unit of measure with the primary key could not be found
	 */
	public static CPInstanceUnitOfMeasure[] findByUuid_PrevAndNext(
			long CPInstanceUnitOfMeasureId, String uuid,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceUnitOfMeasureException {

		return getPersistence().findByUuid_PrevAndNext(
			CPInstanceUnitOfMeasureId, uuid, orderByComparator);
	}

	/**
	 * Removes all the cp instance unit of measures where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of cp instance unit of measures where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp instance unit of measures
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the cp instance unit of measures where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp instance unit of measures
	 */
	public static List<CPInstanceUnitOfMeasure> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

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
	public static List<CPInstanceUnitOfMeasure> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

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
	public static List<CPInstanceUnitOfMeasure> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

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
	public static List<CPInstanceUnitOfMeasure> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cp instance unit of measure in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	public static CPInstanceUnitOfMeasure findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceUnitOfMeasureException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first cp instance unit of measure in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	public static CPInstanceUnitOfMeasure fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last cp instance unit of measure in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	public static CPInstanceUnitOfMeasure findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceUnitOfMeasureException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last cp instance unit of measure in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	public static CPInstanceUnitOfMeasure fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

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
	public static CPInstanceUnitOfMeasure[] findByUuid_C_PrevAndNext(
			long CPInstanceUnitOfMeasureId, String uuid, long companyId,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceUnitOfMeasureException {

		return getPersistence().findByUuid_C_PrevAndNext(
			CPInstanceUnitOfMeasureId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the cp instance unit of measures where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of cp instance unit of measures where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp instance unit of measures
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the cp instance unit of measures where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @return the matching cp instance unit of measures
	 */
	public static List<CPInstanceUnitOfMeasure> findByCPInstanceId(
		long CPInstanceId) {

		return getPersistence().findByCPInstanceId(CPInstanceId);
	}

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
	public static List<CPInstanceUnitOfMeasure> findByCPInstanceId(
		long CPInstanceId, int start, int end) {

		return getPersistence().findByCPInstanceId(CPInstanceId, start, end);
	}

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
	public static List<CPInstanceUnitOfMeasure> findByCPInstanceId(
		long CPInstanceId, int start, int end,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		return getPersistence().findByCPInstanceId(
			CPInstanceId, start, end, orderByComparator);
	}

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
	public static List<CPInstanceUnitOfMeasure> findByCPInstanceId(
		long CPInstanceId, int start, int end,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCPInstanceId(
			CPInstanceId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cp instance unit of measure in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	public static CPInstanceUnitOfMeasure findByCPInstanceId_First(
			long CPInstanceId,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceUnitOfMeasureException {

		return getPersistence().findByCPInstanceId_First(
			CPInstanceId, orderByComparator);
	}

	/**
	 * Returns the first cp instance unit of measure in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	public static CPInstanceUnitOfMeasure fetchByCPInstanceId_First(
		long CPInstanceId,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		return getPersistence().fetchByCPInstanceId_First(
			CPInstanceId, orderByComparator);
	}

	/**
	 * Returns the last cp instance unit of measure in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	public static CPInstanceUnitOfMeasure findByCPInstanceId_Last(
			long CPInstanceId,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceUnitOfMeasureException {

		return getPersistence().findByCPInstanceId_Last(
			CPInstanceId, orderByComparator);
	}

	/**
	 * Returns the last cp instance unit of measure in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	public static CPInstanceUnitOfMeasure fetchByCPInstanceId_Last(
		long CPInstanceId,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		return getPersistence().fetchByCPInstanceId_Last(
			CPInstanceId, orderByComparator);
	}

	/**
	 * Returns the cp instance unit of measures before and after the current cp instance unit of measure in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceUnitOfMeasureId the primary key of the current cp instance unit of measure
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a cp instance unit of measure with the primary key could not be found
	 */
	public static CPInstanceUnitOfMeasure[] findByCPInstanceId_PrevAndNext(
			long CPInstanceUnitOfMeasureId, long CPInstanceId,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceUnitOfMeasureException {

		return getPersistence().findByCPInstanceId_PrevAndNext(
			CPInstanceUnitOfMeasureId, CPInstanceId, orderByComparator);
	}

	/**
	 * Removes all the cp instance unit of measures where CPInstanceId = &#63; from the database.
	 *
	 * @param CPInstanceId the cp instance ID
	 */
	public static void removeByCPInstanceId(long CPInstanceId) {
		getPersistence().removeByCPInstanceId(CPInstanceId);
	}

	/**
	 * Returns the number of cp instance unit of measures where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @return the number of matching cp instance unit of measures
	 */
	public static int countByCPInstanceId(long CPInstanceId) {
		return getPersistence().countByCPInstanceId(CPInstanceId);
	}

	/**
	 * Returns all the cp instance unit of measures where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @return the matching cp instance unit of measures
	 */
	public static List<CPInstanceUnitOfMeasure> findByC_S(
		long companyId, String sku) {

		return getPersistence().findByC_S(companyId, sku);
	}

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
	public static List<CPInstanceUnitOfMeasure> findByC_S(
		long companyId, String sku, int start, int end) {

		return getPersistence().findByC_S(companyId, sku, start, end);
	}

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
	public static List<CPInstanceUnitOfMeasure> findByC_S(
		long companyId, String sku, int start, int end,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		return getPersistence().findByC_S(
			companyId, sku, start, end, orderByComparator);
	}

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
	public static List<CPInstanceUnitOfMeasure> findByC_S(
		long companyId, String sku, int start, int end,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_S(
			companyId, sku, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cp instance unit of measure in the ordered set where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	public static CPInstanceUnitOfMeasure findByC_S_First(
			long companyId, String sku,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceUnitOfMeasureException {

		return getPersistence().findByC_S_First(
			companyId, sku, orderByComparator);
	}

	/**
	 * Returns the first cp instance unit of measure in the ordered set where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	public static CPInstanceUnitOfMeasure fetchByC_S_First(
		long companyId, String sku,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		return getPersistence().fetchByC_S_First(
			companyId, sku, orderByComparator);
	}

	/**
	 * Returns the last cp instance unit of measure in the ordered set where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	public static CPInstanceUnitOfMeasure findByC_S_Last(
			long companyId, String sku,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceUnitOfMeasureException {

		return getPersistence().findByC_S_Last(
			companyId, sku, orderByComparator);
	}

	/**
	 * Returns the last cp instance unit of measure in the ordered set where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	public static CPInstanceUnitOfMeasure fetchByC_S_Last(
		long companyId, String sku,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		return getPersistence().fetchByC_S_Last(
			companyId, sku, orderByComparator);
	}

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
	public static CPInstanceUnitOfMeasure[] findByC_S_PrevAndNext(
			long CPInstanceUnitOfMeasureId, long companyId, String sku,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceUnitOfMeasureException {

		return getPersistence().findByC_S_PrevAndNext(
			CPInstanceUnitOfMeasureId, companyId, sku, orderByComparator);
	}

	/**
	 * Removes all the cp instance unit of measures where companyId = &#63; and sku = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 */
	public static void removeByC_S(long companyId, String sku) {
		getPersistence().removeByC_S(companyId, sku);
	}

	/**
	 * Returns the number of cp instance unit of measures where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @return the number of matching cp instance unit of measures
	 */
	public static int countByC_S(long companyId, String sku) {
		return getPersistence().countByC_S(companyId, sku);
	}

	/**
	 * Returns all the cp instance unit of measures where CPInstanceId = &#63; and active = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param active the active
	 * @return the matching cp instance unit of measures
	 */
	public static List<CPInstanceUnitOfMeasure> findByC_A(
		long CPInstanceId, boolean active) {

		return getPersistence().findByC_A(CPInstanceId, active);
	}

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
	public static List<CPInstanceUnitOfMeasure> findByC_A(
		long CPInstanceId, boolean active, int start, int end) {

		return getPersistence().findByC_A(CPInstanceId, active, start, end);
	}

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
	public static List<CPInstanceUnitOfMeasure> findByC_A(
		long CPInstanceId, boolean active, int start, int end,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		return getPersistence().findByC_A(
			CPInstanceId, active, start, end, orderByComparator);
	}

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
	public static List<CPInstanceUnitOfMeasure> findByC_A(
		long CPInstanceId, boolean active, int start, int end,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_A(
			CPInstanceId, active, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first cp instance unit of measure in the ordered set where CPInstanceId = &#63; and active = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	public static CPInstanceUnitOfMeasure findByC_A_First(
			long CPInstanceId, boolean active,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceUnitOfMeasureException {

		return getPersistence().findByC_A_First(
			CPInstanceId, active, orderByComparator);
	}

	/**
	 * Returns the first cp instance unit of measure in the ordered set where CPInstanceId = &#63; and active = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	public static CPInstanceUnitOfMeasure fetchByC_A_First(
		long CPInstanceId, boolean active,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		return getPersistence().fetchByC_A_First(
			CPInstanceId, active, orderByComparator);
	}

	/**
	 * Returns the last cp instance unit of measure in the ordered set where CPInstanceId = &#63; and active = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	public static CPInstanceUnitOfMeasure findByC_A_Last(
			long CPInstanceId, boolean active,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceUnitOfMeasureException {

		return getPersistence().findByC_A_Last(
			CPInstanceId, active, orderByComparator);
	}

	/**
	 * Returns the last cp instance unit of measure in the ordered set where CPInstanceId = &#63; and active = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	public static CPInstanceUnitOfMeasure fetchByC_A_Last(
		long CPInstanceId, boolean active,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		return getPersistence().fetchByC_A_Last(
			CPInstanceId, active, orderByComparator);
	}

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
	public static CPInstanceUnitOfMeasure[] findByC_A_PrevAndNext(
			long CPInstanceUnitOfMeasureId, long CPInstanceId, boolean active,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceUnitOfMeasureException {

		return getPersistence().findByC_A_PrevAndNext(
			CPInstanceUnitOfMeasureId, CPInstanceId, active, orderByComparator);
	}

	/**
	 * Removes all the cp instance unit of measures where CPInstanceId = &#63; and active = &#63; from the database.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param active the active
	 */
	public static void removeByC_A(long CPInstanceId, boolean active) {
		getPersistence().removeByC_A(CPInstanceId, active);
	}

	/**
	 * Returns the number of cp instance unit of measures where CPInstanceId = &#63; and active = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param active the active
	 * @return the number of matching cp instance unit of measures
	 */
	public static int countByC_A(long CPInstanceId, boolean active) {
		return getPersistence().countByC_A(CPInstanceId, active);
	}

	/**
	 * Returns the cp instance unit of measure where CPInstanceId = &#63; and key = &#63; or throws a <code>NoSuchCPInstanceUnitOfMeasureException</code> if it could not be found.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param key the key
	 * @return the matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	public static CPInstanceUnitOfMeasure findByC_K(
			long CPInstanceId, String key)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceUnitOfMeasureException {

		return getPersistence().findByC_K(CPInstanceId, key);
	}

	/**
	 * Returns the cp instance unit of measure where CPInstanceId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param key the key
	 * @return the matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	public static CPInstanceUnitOfMeasure fetchByC_K(
		long CPInstanceId, String key) {

		return getPersistence().fetchByC_K(CPInstanceId, key);
	}

	/**
	 * Returns the cp instance unit of measure where CPInstanceId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param key the key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	public static CPInstanceUnitOfMeasure fetchByC_K(
		long CPInstanceId, String key, boolean useFinderCache) {

		return getPersistence().fetchByC_K(CPInstanceId, key, useFinderCache);
	}

	/**
	 * Removes the cp instance unit of measure where CPInstanceId = &#63; and key = &#63; from the database.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param key the key
	 * @return the cp instance unit of measure that was removed
	 */
	public static CPInstanceUnitOfMeasure removeByC_K(
			long CPInstanceId, String key)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceUnitOfMeasureException {

		return getPersistence().removeByC_K(CPInstanceId, key);
	}

	/**
	 * Returns the number of cp instance unit of measures where CPInstanceId = &#63; and key = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param key the key
	 * @return the number of matching cp instance unit of measures
	 */
	public static int countByC_K(long CPInstanceId, String key) {
		return getPersistence().countByC_K(CPInstanceId, key);
	}

	/**
	 * Returns all the cp instance unit of measures where CPInstanceId = &#63; and primary = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param primary the primary
	 * @return the matching cp instance unit of measures
	 */
	public static List<CPInstanceUnitOfMeasure> findByC_P(
		long CPInstanceId, boolean primary) {

		return getPersistence().findByC_P(CPInstanceId, primary);
	}

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
	public static List<CPInstanceUnitOfMeasure> findByC_P(
		long CPInstanceId, boolean primary, int start, int end) {

		return getPersistence().findByC_P(CPInstanceId, primary, start, end);
	}

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
	public static List<CPInstanceUnitOfMeasure> findByC_P(
		long CPInstanceId, boolean primary, int start, int end,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		return getPersistence().findByC_P(
			CPInstanceId, primary, start, end, orderByComparator);
	}

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
	public static List<CPInstanceUnitOfMeasure> findByC_P(
		long CPInstanceId, boolean primary, int start, int end,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_P(
			CPInstanceId, primary, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first cp instance unit of measure in the ordered set where CPInstanceId = &#63; and primary = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param primary the primary
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	public static CPInstanceUnitOfMeasure findByC_P_First(
			long CPInstanceId, boolean primary,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceUnitOfMeasureException {

		return getPersistence().findByC_P_First(
			CPInstanceId, primary, orderByComparator);
	}

	/**
	 * Returns the first cp instance unit of measure in the ordered set where CPInstanceId = &#63; and primary = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param primary the primary
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	public static CPInstanceUnitOfMeasure fetchByC_P_First(
		long CPInstanceId, boolean primary,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		return getPersistence().fetchByC_P_First(
			CPInstanceId, primary, orderByComparator);
	}

	/**
	 * Returns the last cp instance unit of measure in the ordered set where CPInstanceId = &#63; and primary = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param primary the primary
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a matching cp instance unit of measure could not be found
	 */
	public static CPInstanceUnitOfMeasure findByC_P_Last(
			long CPInstanceId, boolean primary,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceUnitOfMeasureException {

		return getPersistence().findByC_P_Last(
			CPInstanceId, primary, orderByComparator);
	}

	/**
	 * Returns the last cp instance unit of measure in the ordered set where CPInstanceId = &#63; and primary = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param primary the primary
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	public static CPInstanceUnitOfMeasure fetchByC_P_Last(
		long CPInstanceId, boolean primary,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		return getPersistence().fetchByC_P_Last(
			CPInstanceId, primary, orderByComparator);
	}

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
	public static CPInstanceUnitOfMeasure[] findByC_P_PrevAndNext(
			long CPInstanceUnitOfMeasureId, long CPInstanceId, boolean primary,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceUnitOfMeasureException {

		return getPersistence().findByC_P_PrevAndNext(
			CPInstanceUnitOfMeasureId, CPInstanceId, primary,
			orderByComparator);
	}

	/**
	 * Removes all the cp instance unit of measures where CPInstanceId = &#63; and primary = &#63; from the database.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param primary the primary
	 */
	public static void removeByC_P(long CPInstanceId, boolean primary) {
		getPersistence().removeByC_P(CPInstanceId, primary);
	}

	/**
	 * Returns the number of cp instance unit of measures where CPInstanceId = &#63; and primary = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param primary the primary
	 * @return the number of matching cp instance unit of measures
	 */
	public static int countByC_P(long CPInstanceId, boolean primary) {
		return getPersistence().countByC_P(CPInstanceId, primary);
	}

	/**
	 * Returns all the cp instance unit of measures where companyId = &#63; and key = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param sku the sku
	 * @return the matching cp instance unit of measures
	 */
	public static List<CPInstanceUnitOfMeasure> findByC_K_S(
		long companyId, String key, String sku) {

		return getPersistence().findByC_K_S(companyId, key, sku);
	}

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
	public static List<CPInstanceUnitOfMeasure> findByC_K_S(
		long companyId, String key, String sku, int start, int end) {

		return getPersistence().findByC_K_S(companyId, key, sku, start, end);
	}

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
	public static List<CPInstanceUnitOfMeasure> findByC_K_S(
		long companyId, String key, String sku, int start, int end,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		return getPersistence().findByC_K_S(
			companyId, key, sku, start, end, orderByComparator);
	}

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
	public static List<CPInstanceUnitOfMeasure> findByC_K_S(
		long companyId, String key, String sku, int start, int end,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_K_S(
			companyId, key, sku, start, end, orderByComparator, useFinderCache);
	}

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
	public static CPInstanceUnitOfMeasure findByC_K_S_First(
			long companyId, String key, String sku,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceUnitOfMeasureException {

		return getPersistence().findByC_K_S_First(
			companyId, key, sku, orderByComparator);
	}

	/**
	 * Returns the first cp instance unit of measure in the ordered set where companyId = &#63; and key = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	public static CPInstanceUnitOfMeasure fetchByC_K_S_First(
		long companyId, String key, String sku,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		return getPersistence().fetchByC_K_S_First(
			companyId, key, sku, orderByComparator);
	}

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
	public static CPInstanceUnitOfMeasure findByC_K_S_Last(
			long companyId, String key, String sku,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceUnitOfMeasureException {

		return getPersistence().findByC_K_S_Last(
			companyId, key, sku, orderByComparator);
	}

	/**
	 * Returns the last cp instance unit of measure in the ordered set where companyId = &#63; and key = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	public static CPInstanceUnitOfMeasure fetchByC_K_S_Last(
		long companyId, String key, String sku,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		return getPersistence().fetchByC_K_S_Last(
			companyId, key, sku, orderByComparator);
	}

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
	public static CPInstanceUnitOfMeasure[] findByC_K_S_PrevAndNext(
			long CPInstanceUnitOfMeasureId, long companyId, String key,
			String sku,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceUnitOfMeasureException {

		return getPersistence().findByC_K_S_PrevAndNext(
			CPInstanceUnitOfMeasureId, companyId, key, sku, orderByComparator);
	}

	/**
	 * Removes all the cp instance unit of measures where companyId = &#63; and key = &#63; and sku = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param sku the sku
	 */
	public static void removeByC_K_S(long companyId, String key, String sku) {
		getPersistence().removeByC_K_S(companyId, key, sku);
	}

	/**
	 * Returns the number of cp instance unit of measures where companyId = &#63; and key = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param sku the sku
	 * @return the number of matching cp instance unit of measures
	 */
	public static int countByC_K_S(long companyId, String key, String sku) {
		return getPersistence().countByC_K_S(companyId, key, sku);
	}

	/**
	 * Caches the cp instance unit of measure in the entity cache if it is enabled.
	 *
	 * @param cpInstanceUnitOfMeasure the cp instance unit of measure
	 */
	public static void cacheResult(
		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure) {

		getPersistence().cacheResult(cpInstanceUnitOfMeasure);
	}

	/**
	 * Caches the cp instance unit of measures in the entity cache if it is enabled.
	 *
	 * @param cpInstanceUnitOfMeasures the cp instance unit of measures
	 */
	public static void cacheResult(
		List<CPInstanceUnitOfMeasure> cpInstanceUnitOfMeasures) {

		getPersistence().cacheResult(cpInstanceUnitOfMeasures);
	}

	/**
	 * Creates a new cp instance unit of measure with the primary key. Does not add the cp instance unit of measure to the database.
	 *
	 * @param CPInstanceUnitOfMeasureId the primary key for the new cp instance unit of measure
	 * @return the new cp instance unit of measure
	 */
	public static CPInstanceUnitOfMeasure create(
		long CPInstanceUnitOfMeasureId) {

		return getPersistence().create(CPInstanceUnitOfMeasureId);
	}

	/**
	 * Removes the cp instance unit of measure with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPInstanceUnitOfMeasureId the primary key of the cp instance unit of measure
	 * @return the cp instance unit of measure that was removed
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a cp instance unit of measure with the primary key could not be found
	 */
	public static CPInstanceUnitOfMeasure remove(long CPInstanceUnitOfMeasureId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceUnitOfMeasureException {

		return getPersistence().remove(CPInstanceUnitOfMeasureId);
	}

	public static CPInstanceUnitOfMeasure updateImpl(
		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure) {

		return getPersistence().updateImpl(cpInstanceUnitOfMeasure);
	}

	/**
	 * Returns the cp instance unit of measure with the primary key or throws a <code>NoSuchCPInstanceUnitOfMeasureException</code> if it could not be found.
	 *
	 * @param CPInstanceUnitOfMeasureId the primary key of the cp instance unit of measure
	 * @return the cp instance unit of measure
	 * @throws NoSuchCPInstanceUnitOfMeasureException if a cp instance unit of measure with the primary key could not be found
	 */
	public static CPInstanceUnitOfMeasure findByPrimaryKey(
			long CPInstanceUnitOfMeasureId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceUnitOfMeasureException {

		return getPersistence().findByPrimaryKey(CPInstanceUnitOfMeasureId);
	}

	/**
	 * Returns the cp instance unit of measure with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPInstanceUnitOfMeasureId the primary key of the cp instance unit of measure
	 * @return the cp instance unit of measure, or <code>null</code> if a cp instance unit of measure with the primary key could not be found
	 */
	public static CPInstanceUnitOfMeasure fetchByPrimaryKey(
		long CPInstanceUnitOfMeasureId) {

		return getPersistence().fetchByPrimaryKey(CPInstanceUnitOfMeasureId);
	}

	/**
	 * Returns all the cp instance unit of measures.
	 *
	 * @return the cp instance unit of measures
	 */
	public static List<CPInstanceUnitOfMeasure> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<CPInstanceUnitOfMeasure> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<CPInstanceUnitOfMeasure> findAll(
		int start, int end,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<CPInstanceUnitOfMeasure> findAll(
		int start, int end,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the cp instance unit of measures from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of cp instance unit of measures.
	 *
	 * @return the number of cp instance unit of measures
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CPInstanceUnitOfMeasurePersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		CPInstanceUnitOfMeasurePersistence persistence) {

		_persistence = persistence;
	}

	private static volatile CPInstanceUnitOfMeasurePersistence _persistence;

}
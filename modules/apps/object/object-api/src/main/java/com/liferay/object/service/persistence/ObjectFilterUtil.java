/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.persistence;

import com.liferay.object.model.ObjectFilter;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the object filter service. This utility wraps <code>com.liferay.object.service.persistence.impl.ObjectFilterPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see ObjectFilterPersistence
 * @generated
 */
public class ObjectFilterUtil {

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
	public static void clearCache(ObjectFilter objectFilter) {
		getPersistence().clearCache(objectFilter);
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
	public static Map<Serializable, ObjectFilter> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ObjectFilter> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ObjectFilter> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ObjectFilter> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ObjectFilter> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ObjectFilter update(ObjectFilter objectFilter) {
		return getPersistence().update(objectFilter);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ObjectFilter update(
		ObjectFilter objectFilter, ServiceContext serviceContext) {

		return getPersistence().update(objectFilter, serviceContext);
	}

	/**
	 * Returns all the object filters where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object filters
	 */
	public static List<ObjectFilter> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the object filters where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFilterModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object filters
	 * @param end the upper bound of the range of object filters (not inclusive)
	 * @return the range of matching object filters
	 */
	public static List<ObjectFilter> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the object filters where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFilterModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object filters
	 * @param end the upper bound of the range of object filters (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object filters
	 */
	public static List<ObjectFilter> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectFilter> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object filters where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFilterModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object filters
	 * @param end the upper bound of the range of object filters (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object filters
	 */
	public static List<ObjectFilter> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectFilter> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object filter in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object filter
	 * @throws NoSuchObjectFilterException if a matching object filter could not be found
	 */
	public static ObjectFilter findByUuid_First(
			String uuid, OrderByComparator<ObjectFilter> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFilterException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first object filter in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object filter, or <code>null</code> if a matching object filter could not be found
	 */
	public static ObjectFilter fetchByUuid_First(
		String uuid, OrderByComparator<ObjectFilter> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last object filter in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object filter
	 * @throws NoSuchObjectFilterException if a matching object filter could not be found
	 */
	public static ObjectFilter findByUuid_Last(
			String uuid, OrderByComparator<ObjectFilter> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFilterException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last object filter in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object filter, or <code>null</code> if a matching object filter could not be found
	 */
	public static ObjectFilter fetchByUuid_Last(
		String uuid, OrderByComparator<ObjectFilter> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the object filters before and after the current object filter in the ordered set where uuid = &#63;.
	 *
	 * @param objectFilterId the primary key of the current object filter
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object filter
	 * @throws NoSuchObjectFilterException if a object filter with the primary key could not be found
	 */
	public static ObjectFilter[] findByUuid_PrevAndNext(
			long objectFilterId, String uuid,
			OrderByComparator<ObjectFilter> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFilterException {

		return getPersistence().findByUuid_PrevAndNext(
			objectFilterId, uuid, orderByComparator);
	}

	/**
	 * Removes all the object filters where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of object filters where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object filters
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the object filters where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object filters
	 */
	public static List<ObjectFilter> findByUuid_C(String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the object filters where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFilterModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object filters
	 * @param end the upper bound of the range of object filters (not inclusive)
	 * @return the range of matching object filters
	 */
	public static List<ObjectFilter> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the object filters where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFilterModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object filters
	 * @param end the upper bound of the range of object filters (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object filters
	 */
	public static List<ObjectFilter> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectFilter> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object filters where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFilterModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object filters
	 * @param end the upper bound of the range of object filters (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object filters
	 */
	public static List<ObjectFilter> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectFilter> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object filter in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object filter
	 * @throws NoSuchObjectFilterException if a matching object filter could not be found
	 */
	public static ObjectFilter findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ObjectFilter> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFilterException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first object filter in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object filter, or <code>null</code> if a matching object filter could not be found
	 */
	public static ObjectFilter fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ObjectFilter> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last object filter in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object filter
	 * @throws NoSuchObjectFilterException if a matching object filter could not be found
	 */
	public static ObjectFilter findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ObjectFilter> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFilterException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last object filter in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object filter, or <code>null</code> if a matching object filter could not be found
	 */
	public static ObjectFilter fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ObjectFilter> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the object filters before and after the current object filter in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectFilterId the primary key of the current object filter
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object filter
	 * @throws NoSuchObjectFilterException if a object filter with the primary key could not be found
	 */
	public static ObjectFilter[] findByUuid_C_PrevAndNext(
			long objectFilterId, String uuid, long companyId,
			OrderByComparator<ObjectFilter> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFilterException {

		return getPersistence().findByUuid_C_PrevAndNext(
			objectFilterId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the object filters where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of object filters where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object filters
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the object filters where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @return the matching object filters
	 */
	public static List<ObjectFilter> findByObjectFieldId(long objectFieldId) {
		return getPersistence().findByObjectFieldId(objectFieldId);
	}

	/**
	 * Returns a range of all the object filters where objectFieldId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFilterModelImpl</code>.
	 * </p>
	 *
	 * @param objectFieldId the object field ID
	 * @param start the lower bound of the range of object filters
	 * @param end the upper bound of the range of object filters (not inclusive)
	 * @return the range of matching object filters
	 */
	public static List<ObjectFilter> findByObjectFieldId(
		long objectFieldId, int start, int end) {

		return getPersistence().findByObjectFieldId(objectFieldId, start, end);
	}

	/**
	 * Returns an ordered range of all the object filters where objectFieldId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFilterModelImpl</code>.
	 * </p>
	 *
	 * @param objectFieldId the object field ID
	 * @param start the lower bound of the range of object filters
	 * @param end the upper bound of the range of object filters (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object filters
	 */
	public static List<ObjectFilter> findByObjectFieldId(
		long objectFieldId, int start, int end,
		OrderByComparator<ObjectFilter> orderByComparator) {

		return getPersistence().findByObjectFieldId(
			objectFieldId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object filters where objectFieldId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFilterModelImpl</code>.
	 * </p>
	 *
	 * @param objectFieldId the object field ID
	 * @param start the lower bound of the range of object filters
	 * @param end the upper bound of the range of object filters (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object filters
	 */
	public static List<ObjectFilter> findByObjectFieldId(
		long objectFieldId, int start, int end,
		OrderByComparator<ObjectFilter> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByObjectFieldId(
			objectFieldId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object filter in the ordered set where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object filter
	 * @throws NoSuchObjectFilterException if a matching object filter could not be found
	 */
	public static ObjectFilter findByObjectFieldId_First(
			long objectFieldId,
			OrderByComparator<ObjectFilter> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFilterException {

		return getPersistence().findByObjectFieldId_First(
			objectFieldId, orderByComparator);
	}

	/**
	 * Returns the first object filter in the ordered set where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object filter, or <code>null</code> if a matching object filter could not be found
	 */
	public static ObjectFilter fetchByObjectFieldId_First(
		long objectFieldId, OrderByComparator<ObjectFilter> orderByComparator) {

		return getPersistence().fetchByObjectFieldId_First(
			objectFieldId, orderByComparator);
	}

	/**
	 * Returns the last object filter in the ordered set where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object filter
	 * @throws NoSuchObjectFilterException if a matching object filter could not be found
	 */
	public static ObjectFilter findByObjectFieldId_Last(
			long objectFieldId,
			OrderByComparator<ObjectFilter> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFilterException {

		return getPersistence().findByObjectFieldId_Last(
			objectFieldId, orderByComparator);
	}

	/**
	 * Returns the last object filter in the ordered set where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object filter, or <code>null</code> if a matching object filter could not be found
	 */
	public static ObjectFilter fetchByObjectFieldId_Last(
		long objectFieldId, OrderByComparator<ObjectFilter> orderByComparator) {

		return getPersistence().fetchByObjectFieldId_Last(
			objectFieldId, orderByComparator);
	}

	/**
	 * Returns the object filters before and after the current object filter in the ordered set where objectFieldId = &#63;.
	 *
	 * @param objectFilterId the primary key of the current object filter
	 * @param objectFieldId the object field ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object filter
	 * @throws NoSuchObjectFilterException if a object filter with the primary key could not be found
	 */
	public static ObjectFilter[] findByObjectFieldId_PrevAndNext(
			long objectFilterId, long objectFieldId,
			OrderByComparator<ObjectFilter> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFilterException {

		return getPersistence().findByObjectFieldId_PrevAndNext(
			objectFilterId, objectFieldId, orderByComparator);
	}

	/**
	 * Removes all the object filters where objectFieldId = &#63; from the database.
	 *
	 * @param objectFieldId the object field ID
	 */
	public static void removeByObjectFieldId(long objectFieldId) {
		getPersistence().removeByObjectFieldId(objectFieldId);
	}

	/**
	 * Returns the number of object filters where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @return the number of matching object filters
	 */
	public static int countByObjectFieldId(long objectFieldId) {
		return getPersistence().countByObjectFieldId(objectFieldId);
	}

	/**
	 * Caches the object filter in the entity cache if it is enabled.
	 *
	 * @param objectFilter the object filter
	 */
	public static void cacheResult(ObjectFilter objectFilter) {
		getPersistence().cacheResult(objectFilter);
	}

	/**
	 * Caches the object filters in the entity cache if it is enabled.
	 *
	 * @param objectFilters the object filters
	 */
	public static void cacheResult(List<ObjectFilter> objectFilters) {
		getPersistence().cacheResult(objectFilters);
	}

	/**
	 * Creates a new object filter with the primary key. Does not add the object filter to the database.
	 *
	 * @param objectFilterId the primary key for the new object filter
	 * @return the new object filter
	 */
	public static ObjectFilter create(long objectFilterId) {
		return getPersistence().create(objectFilterId);
	}

	/**
	 * Removes the object filter with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectFilterId the primary key of the object filter
	 * @return the object filter that was removed
	 * @throws NoSuchObjectFilterException if a object filter with the primary key could not be found
	 */
	public static ObjectFilter remove(long objectFilterId)
		throws com.liferay.object.exception.NoSuchObjectFilterException {

		return getPersistence().remove(objectFilterId);
	}

	public static ObjectFilter updateImpl(ObjectFilter objectFilter) {
		return getPersistence().updateImpl(objectFilter);
	}

	/**
	 * Returns the object filter with the primary key or throws a <code>NoSuchObjectFilterException</code> if it could not be found.
	 *
	 * @param objectFilterId the primary key of the object filter
	 * @return the object filter
	 * @throws NoSuchObjectFilterException if a object filter with the primary key could not be found
	 */
	public static ObjectFilter findByPrimaryKey(long objectFilterId)
		throws com.liferay.object.exception.NoSuchObjectFilterException {

		return getPersistence().findByPrimaryKey(objectFilterId);
	}

	/**
	 * Returns the object filter with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectFilterId the primary key of the object filter
	 * @return the object filter, or <code>null</code> if a object filter with the primary key could not be found
	 */
	public static ObjectFilter fetchByPrimaryKey(long objectFilterId) {
		return getPersistence().fetchByPrimaryKey(objectFilterId);
	}

	/**
	 * Returns all the object filters.
	 *
	 * @return the object filters
	 */
	public static List<ObjectFilter> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the object filters.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFilterModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object filters
	 * @param end the upper bound of the range of object filters (not inclusive)
	 * @return the range of object filters
	 */
	public static List<ObjectFilter> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the object filters.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFilterModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object filters
	 * @param end the upper bound of the range of object filters (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object filters
	 */
	public static List<ObjectFilter> findAll(
		int start, int end, OrderByComparator<ObjectFilter> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object filters.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFilterModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object filters
	 * @param end the upper bound of the range of object filters (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object filters
	 */
	public static List<ObjectFilter> findAll(
		int start, int end, OrderByComparator<ObjectFilter> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the object filters from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of object filters.
	 *
	 * @return the number of object filters
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ObjectFilterPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(ObjectFilterPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile ObjectFilterPersistence _persistence;

}
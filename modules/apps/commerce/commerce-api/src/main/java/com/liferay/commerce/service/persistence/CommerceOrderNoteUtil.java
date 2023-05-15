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

package com.liferay.commerce.service.persistence;

import com.liferay.commerce.model.CommerceOrderNote;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the commerce order note service. This utility wraps <code>com.liferay.commerce.service.persistence.impl.CommerceOrderNotePersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderNotePersistence
 * @generated
 */
public class CommerceOrderNoteUtil {

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
	public static void clearCache(CommerceOrderNote commerceOrderNote) {
		getPersistence().clearCache(commerceOrderNote);
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
	public static Map<Serializable, CommerceOrderNote> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceOrderNote> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceOrderNote> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceOrderNote> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceOrderNote> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceOrderNote update(
		CommerceOrderNote commerceOrderNote) {

		return getPersistence().update(commerceOrderNote);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceOrderNote update(
		CommerceOrderNote commerceOrderNote, ServiceContext serviceContext) {

		return getPersistence().update(commerceOrderNote, serviceContext);
	}

	/**
	 * Returns all the commerce order notes where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce order notes
	 */
	public static List<CommerceOrderNote> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the commerce order notes where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderNoteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @return the range of matching commerce order notes
	 */
	public static List<CommerceOrderNote> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce order notes where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderNoteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order notes
	 */
	public static List<CommerceOrderNote> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceOrderNote> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce order notes where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderNoteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order notes
	 */
	public static List<CommerceOrderNote> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceOrderNote> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce order note in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order note
	 * @throws NoSuchOrderNoteException if a matching commerce order note could not be found
	 */
	public static CommerceOrderNote findByUuid_First(
			String uuid, OrderByComparator<CommerceOrderNote> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderNoteException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first commerce order note in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order note, or <code>null</code> if a matching commerce order note could not be found
	 */
	public static CommerceOrderNote fetchByUuid_First(
		String uuid, OrderByComparator<CommerceOrderNote> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last commerce order note in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order note
	 * @throws NoSuchOrderNoteException if a matching commerce order note could not be found
	 */
	public static CommerceOrderNote findByUuid_Last(
			String uuid, OrderByComparator<CommerceOrderNote> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderNoteException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last commerce order note in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order note, or <code>null</code> if a matching commerce order note could not be found
	 */
	public static CommerceOrderNote fetchByUuid_Last(
		String uuid, OrderByComparator<CommerceOrderNote> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the commerce order notes before and after the current commerce order note in the ordered set where uuid = &#63;.
	 *
	 * @param commerceOrderNoteId the primary key of the current commerce order note
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order note
	 * @throws NoSuchOrderNoteException if a commerce order note with the primary key could not be found
	 */
	public static CommerceOrderNote[] findByUuid_PrevAndNext(
			long commerceOrderNoteId, String uuid,
			OrderByComparator<CommerceOrderNote> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderNoteException {

		return getPersistence().findByUuid_PrevAndNext(
			commerceOrderNoteId, uuid, orderByComparator);
	}

	/**
	 * Removes all the commerce order notes where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of commerce order notes where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce order notes
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the commerce order note where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchOrderNoteException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce order note
	 * @throws NoSuchOrderNoteException if a matching commerce order note could not be found
	 */
	public static CommerceOrderNote findByUUID_G(String uuid, long groupId)
		throws com.liferay.commerce.exception.NoSuchOrderNoteException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the commerce order note where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce order note, or <code>null</code> if a matching commerce order note could not be found
	 */
	public static CommerceOrderNote fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the commerce order note where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce order note, or <code>null</code> if a matching commerce order note could not be found
	 */
	public static CommerceOrderNote fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the commerce order note where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the commerce order note that was removed
	 */
	public static CommerceOrderNote removeByUUID_G(String uuid, long groupId)
		throws com.liferay.commerce.exception.NoSuchOrderNoteException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of commerce order notes where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching commerce order notes
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the commerce order notes where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce order notes
	 */
	public static List<CommerceOrderNote> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the commerce order notes where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderNoteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @return the range of matching commerce order notes
	 */
	public static List<CommerceOrderNote> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce order notes where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderNoteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order notes
	 */
	public static List<CommerceOrderNote> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceOrderNote> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce order notes where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderNoteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order notes
	 */
	public static List<CommerceOrderNote> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceOrderNote> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce order note in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order note
	 * @throws NoSuchOrderNoteException if a matching commerce order note could not be found
	 */
	public static CommerceOrderNote findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommerceOrderNote> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderNoteException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first commerce order note in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order note, or <code>null</code> if a matching commerce order note could not be found
	 */
	public static CommerceOrderNote fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommerceOrderNote> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce order note in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order note
	 * @throws NoSuchOrderNoteException if a matching commerce order note could not be found
	 */
	public static CommerceOrderNote findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CommerceOrderNote> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderNoteException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce order note in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order note, or <code>null</code> if a matching commerce order note could not be found
	 */
	public static CommerceOrderNote fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CommerceOrderNote> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the commerce order notes before and after the current commerce order note in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceOrderNoteId the primary key of the current commerce order note
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order note
	 * @throws NoSuchOrderNoteException if a commerce order note with the primary key could not be found
	 */
	public static CommerceOrderNote[] findByUuid_C_PrevAndNext(
			long commerceOrderNoteId, String uuid, long companyId,
			OrderByComparator<CommerceOrderNote> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderNoteException {

		return getPersistence().findByUuid_C_PrevAndNext(
			commerceOrderNoteId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the commerce order notes where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of commerce order notes where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce order notes
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the commerce order notes where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @return the matching commerce order notes
	 */
	public static List<CommerceOrderNote> findByCommerceOrderId(
		long commerceOrderId) {

		return getPersistence().findByCommerceOrderId(commerceOrderId);
	}

	/**
	 * Returns a range of all the commerce order notes where commerceOrderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderNoteModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @return the range of matching commerce order notes
	 */
	public static List<CommerceOrderNote> findByCommerceOrderId(
		long commerceOrderId, int start, int end) {

		return getPersistence().findByCommerceOrderId(
			commerceOrderId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce order notes where commerceOrderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderNoteModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order notes
	 */
	public static List<CommerceOrderNote> findByCommerceOrderId(
		long commerceOrderId, int start, int end,
		OrderByComparator<CommerceOrderNote> orderByComparator) {

		return getPersistence().findByCommerceOrderId(
			commerceOrderId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce order notes where commerceOrderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderNoteModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order notes
	 */
	public static List<CommerceOrderNote> findByCommerceOrderId(
		long commerceOrderId, int start, int end,
		OrderByComparator<CommerceOrderNote> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCommerceOrderId(
			commerceOrderId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce order note in the ordered set where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order note
	 * @throws NoSuchOrderNoteException if a matching commerce order note could not be found
	 */
	public static CommerceOrderNote findByCommerceOrderId_First(
			long commerceOrderId,
			OrderByComparator<CommerceOrderNote> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderNoteException {

		return getPersistence().findByCommerceOrderId_First(
			commerceOrderId, orderByComparator);
	}

	/**
	 * Returns the first commerce order note in the ordered set where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order note, or <code>null</code> if a matching commerce order note could not be found
	 */
	public static CommerceOrderNote fetchByCommerceOrderId_First(
		long commerceOrderId,
		OrderByComparator<CommerceOrderNote> orderByComparator) {

		return getPersistence().fetchByCommerceOrderId_First(
			commerceOrderId, orderByComparator);
	}

	/**
	 * Returns the last commerce order note in the ordered set where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order note
	 * @throws NoSuchOrderNoteException if a matching commerce order note could not be found
	 */
	public static CommerceOrderNote findByCommerceOrderId_Last(
			long commerceOrderId,
			OrderByComparator<CommerceOrderNote> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderNoteException {

		return getPersistence().findByCommerceOrderId_Last(
			commerceOrderId, orderByComparator);
	}

	/**
	 * Returns the last commerce order note in the ordered set where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order note, or <code>null</code> if a matching commerce order note could not be found
	 */
	public static CommerceOrderNote fetchByCommerceOrderId_Last(
		long commerceOrderId,
		OrderByComparator<CommerceOrderNote> orderByComparator) {

		return getPersistence().fetchByCommerceOrderId_Last(
			commerceOrderId, orderByComparator);
	}

	/**
	 * Returns the commerce order notes before and after the current commerce order note in the ordered set where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderNoteId the primary key of the current commerce order note
	 * @param commerceOrderId the commerce order ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order note
	 * @throws NoSuchOrderNoteException if a commerce order note with the primary key could not be found
	 */
	public static CommerceOrderNote[] findByCommerceOrderId_PrevAndNext(
			long commerceOrderNoteId, long commerceOrderId,
			OrderByComparator<CommerceOrderNote> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderNoteException {

		return getPersistence().findByCommerceOrderId_PrevAndNext(
			commerceOrderNoteId, commerceOrderId, orderByComparator);
	}

	/**
	 * Removes all the commerce order notes where commerceOrderId = &#63; from the database.
	 *
	 * @param commerceOrderId the commerce order ID
	 */
	public static void removeByCommerceOrderId(long commerceOrderId) {
		getPersistence().removeByCommerceOrderId(commerceOrderId);
	}

	/**
	 * Returns the number of commerce order notes where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @return the number of matching commerce order notes
	 */
	public static int countByCommerceOrderId(long commerceOrderId) {
		return getPersistence().countByCommerceOrderId(commerceOrderId);
	}

	/**
	 * Returns all the commerce order notes where commerceOrderId = &#63; and restricted = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 * @return the matching commerce order notes
	 */
	public static List<CommerceOrderNote> findByC_R(
		long commerceOrderId, boolean restricted) {

		return getPersistence().findByC_R(commerceOrderId, restricted);
	}

	/**
	 * Returns a range of all the commerce order notes where commerceOrderId = &#63; and restricted = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderNoteModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @return the range of matching commerce order notes
	 */
	public static List<CommerceOrderNote> findByC_R(
		long commerceOrderId, boolean restricted, int start, int end) {

		return getPersistence().findByC_R(
			commerceOrderId, restricted, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce order notes where commerceOrderId = &#63; and restricted = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderNoteModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order notes
	 */
	public static List<CommerceOrderNote> findByC_R(
		long commerceOrderId, boolean restricted, int start, int end,
		OrderByComparator<CommerceOrderNote> orderByComparator) {

		return getPersistence().findByC_R(
			commerceOrderId, restricted, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce order notes where commerceOrderId = &#63; and restricted = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderNoteModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order notes
	 */
	public static List<CommerceOrderNote> findByC_R(
		long commerceOrderId, boolean restricted, int start, int end,
		OrderByComparator<CommerceOrderNote> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_R(
			commerceOrderId, restricted, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce order note in the ordered set where commerceOrderId = &#63; and restricted = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order note
	 * @throws NoSuchOrderNoteException if a matching commerce order note could not be found
	 */
	public static CommerceOrderNote findByC_R_First(
			long commerceOrderId, boolean restricted,
			OrderByComparator<CommerceOrderNote> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderNoteException {

		return getPersistence().findByC_R_First(
			commerceOrderId, restricted, orderByComparator);
	}

	/**
	 * Returns the first commerce order note in the ordered set where commerceOrderId = &#63; and restricted = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order note, or <code>null</code> if a matching commerce order note could not be found
	 */
	public static CommerceOrderNote fetchByC_R_First(
		long commerceOrderId, boolean restricted,
		OrderByComparator<CommerceOrderNote> orderByComparator) {

		return getPersistence().fetchByC_R_First(
			commerceOrderId, restricted, orderByComparator);
	}

	/**
	 * Returns the last commerce order note in the ordered set where commerceOrderId = &#63; and restricted = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order note
	 * @throws NoSuchOrderNoteException if a matching commerce order note could not be found
	 */
	public static CommerceOrderNote findByC_R_Last(
			long commerceOrderId, boolean restricted,
			OrderByComparator<CommerceOrderNote> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderNoteException {

		return getPersistence().findByC_R_Last(
			commerceOrderId, restricted, orderByComparator);
	}

	/**
	 * Returns the last commerce order note in the ordered set where commerceOrderId = &#63; and restricted = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order note, or <code>null</code> if a matching commerce order note could not be found
	 */
	public static CommerceOrderNote fetchByC_R_Last(
		long commerceOrderId, boolean restricted,
		OrderByComparator<CommerceOrderNote> orderByComparator) {

		return getPersistence().fetchByC_R_Last(
			commerceOrderId, restricted, orderByComparator);
	}

	/**
	 * Returns the commerce order notes before and after the current commerce order note in the ordered set where commerceOrderId = &#63; and restricted = &#63;.
	 *
	 * @param commerceOrderNoteId the primary key of the current commerce order note
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order note
	 * @throws NoSuchOrderNoteException if a commerce order note with the primary key could not be found
	 */
	public static CommerceOrderNote[] findByC_R_PrevAndNext(
			long commerceOrderNoteId, long commerceOrderId, boolean restricted,
			OrderByComparator<CommerceOrderNote> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderNoteException {

		return getPersistence().findByC_R_PrevAndNext(
			commerceOrderNoteId, commerceOrderId, restricted,
			orderByComparator);
	}

	/**
	 * Removes all the commerce order notes where commerceOrderId = &#63; and restricted = &#63; from the database.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 */
	public static void removeByC_R(long commerceOrderId, boolean restricted) {
		getPersistence().removeByC_R(commerceOrderId, restricted);
	}

	/**
	 * Returns the number of commerce order notes where commerceOrderId = &#63; and restricted = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 * @return the number of matching commerce order notes
	 */
	public static int countByC_R(long commerceOrderId, boolean restricted) {
		return getPersistence().countByC_R(commerceOrderId, restricted);
	}

	/**
	 * Returns the commerce order note where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchOrderNoteException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching commerce order note
	 * @throws NoSuchOrderNoteException if a matching commerce order note could not be found
	 */
	public static CommerceOrderNote findByERC_C(
			String externalReferenceCode, long companyId)
		throws com.liferay.commerce.exception.NoSuchOrderNoteException {

		return getPersistence().findByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns the commerce order note where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching commerce order note, or <code>null</code> if a matching commerce order note could not be found
	 */
	public static CommerceOrderNote fetchByERC_C(
		String externalReferenceCode, long companyId) {

		return getPersistence().fetchByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns the commerce order note where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce order note, or <code>null</code> if a matching commerce order note could not be found
	 */
	public static CommerceOrderNote fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache) {

		return getPersistence().fetchByERC_C(
			externalReferenceCode, companyId, useFinderCache);
	}

	/**
	 * Removes the commerce order note where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the commerce order note that was removed
	 */
	public static CommerceOrderNote removeByERC_C(
			String externalReferenceCode, long companyId)
		throws com.liferay.commerce.exception.NoSuchOrderNoteException {

		return getPersistence().removeByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns the number of commerce order notes where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching commerce order notes
	 */
	public static int countByERC_C(
		String externalReferenceCode, long companyId) {

		return getPersistence().countByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Caches the commerce order note in the entity cache if it is enabled.
	 *
	 * @param commerceOrderNote the commerce order note
	 */
	public static void cacheResult(CommerceOrderNote commerceOrderNote) {
		getPersistence().cacheResult(commerceOrderNote);
	}

	/**
	 * Caches the commerce order notes in the entity cache if it is enabled.
	 *
	 * @param commerceOrderNotes the commerce order notes
	 */
	public static void cacheResult(List<CommerceOrderNote> commerceOrderNotes) {
		getPersistence().cacheResult(commerceOrderNotes);
	}

	/**
	 * Creates a new commerce order note with the primary key. Does not add the commerce order note to the database.
	 *
	 * @param commerceOrderNoteId the primary key for the new commerce order note
	 * @return the new commerce order note
	 */
	public static CommerceOrderNote create(long commerceOrderNoteId) {
		return getPersistence().create(commerceOrderNoteId);
	}

	/**
	 * Removes the commerce order note with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceOrderNoteId the primary key of the commerce order note
	 * @return the commerce order note that was removed
	 * @throws NoSuchOrderNoteException if a commerce order note with the primary key could not be found
	 */
	public static CommerceOrderNote remove(long commerceOrderNoteId)
		throws com.liferay.commerce.exception.NoSuchOrderNoteException {

		return getPersistence().remove(commerceOrderNoteId);
	}

	public static CommerceOrderNote updateImpl(
		CommerceOrderNote commerceOrderNote) {

		return getPersistence().updateImpl(commerceOrderNote);
	}

	/**
	 * Returns the commerce order note with the primary key or throws a <code>NoSuchOrderNoteException</code> if it could not be found.
	 *
	 * @param commerceOrderNoteId the primary key of the commerce order note
	 * @return the commerce order note
	 * @throws NoSuchOrderNoteException if a commerce order note with the primary key could not be found
	 */
	public static CommerceOrderNote findByPrimaryKey(long commerceOrderNoteId)
		throws com.liferay.commerce.exception.NoSuchOrderNoteException {

		return getPersistence().findByPrimaryKey(commerceOrderNoteId);
	}

	/**
	 * Returns the commerce order note with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceOrderNoteId the primary key of the commerce order note
	 * @return the commerce order note, or <code>null</code> if a commerce order note with the primary key could not be found
	 */
	public static CommerceOrderNote fetchByPrimaryKey(
		long commerceOrderNoteId) {

		return getPersistence().fetchByPrimaryKey(commerceOrderNoteId);
	}

	/**
	 * Returns all the commerce order notes.
	 *
	 * @return the commerce order notes
	 */
	public static List<CommerceOrderNote> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce order notes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderNoteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @return the range of commerce order notes
	 */
	public static List<CommerceOrderNote> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce order notes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderNoteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce order notes
	 */
	public static List<CommerceOrderNote> findAll(
		int start, int end,
		OrderByComparator<CommerceOrderNote> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce order notes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderNoteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce order notes
	 */
	public static List<CommerceOrderNote> findAll(
		int start, int end,
		OrderByComparator<CommerceOrderNote> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce order notes from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce order notes.
	 *
	 * @return the number of commerce order notes
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceOrderNotePersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		CommerceOrderNotePersistence persistence) {

		_persistence = persistence;
	}

	private static volatile CommerceOrderNotePersistence _persistence;

}
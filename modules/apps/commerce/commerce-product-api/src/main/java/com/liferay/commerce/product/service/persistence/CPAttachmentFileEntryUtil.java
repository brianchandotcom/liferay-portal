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

import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the cp attachment file entry service. This utility wraps <code>com.liferay.commerce.product.service.persistence.impl.CPAttachmentFileEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPAttachmentFileEntryPersistence
 * @generated
 */
public class CPAttachmentFileEntryUtil {

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
	public static void clearCache(CPAttachmentFileEntry cpAttachmentFileEntry) {
		getPersistence().clearCache(cpAttachmentFileEntry);
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
	public static Map<Serializable, CPAttachmentFileEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CPAttachmentFileEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CPAttachmentFileEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CPAttachmentFileEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CPAttachmentFileEntry update(
		CPAttachmentFileEntry cpAttachmentFileEntry) {

		return getPersistence().update(cpAttachmentFileEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CPAttachmentFileEntry update(
		CPAttachmentFileEntry cpAttachmentFileEntry,
		ServiceContext serviceContext) {

		return getPersistence().update(cpAttachmentFileEntry, serviceContext);
	}

	/**
	 * Returns all the cp attachment file entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp attachment file entries
	 */
	public static List<CPAttachmentFileEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the cp attachment file entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @return the range of matching cp attachment file entries
	 */
	public static List<CPAttachmentFileEntry> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp attachment file entries
	 */
	public static List<CPAttachmentFileEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp attachment file entries
	 */
	public static List<CPAttachmentFileEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cp attachment file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry findByUuid_First(
			String uuid,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPAttachmentFileEntryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first cp attachment file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry fetchByUuid_First(
		String uuid,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry findByUuid_Last(
			String uuid,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPAttachmentFileEntryException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry fetchByUuid_Last(
		String uuid,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the cp attachment file entries before and after the current cp attachment file entry in the ordered set where uuid = &#63;.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the current cp attachment file entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	public static CPAttachmentFileEntry[] findByUuid_PrevAndNext(
			long CPAttachmentFileEntryId, String uuid,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPAttachmentFileEntryException {

		return getPersistence().findByUuid_PrevAndNext(
			CPAttachmentFileEntryId, uuid, orderByComparator);
	}

	/**
	 * Removes all the cp attachment file entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of cp attachment file entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp attachment file entries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the cp attachment file entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchCPAttachmentFileEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry findByUUID_G(String uuid, long groupId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPAttachmentFileEntryException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the cp attachment file entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry fetchByUUID_G(
		String uuid, long groupId) {

		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the cp attachment file entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the cp attachment file entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the cp attachment file entry that was removed
	 */
	public static CPAttachmentFileEntry removeByUUID_G(
			String uuid, long groupId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPAttachmentFileEntryException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of cp attachment file entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching cp attachment file entries
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the cp attachment file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp attachment file entries
	 */
	public static List<CPAttachmentFileEntry> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the cp attachment file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @return the range of matching cp attachment file entries
	 */
	public static List<CPAttachmentFileEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp attachment file entries
	 */
	public static List<CPAttachmentFileEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp attachment file entries
	 */
	public static List<CPAttachmentFileEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cp attachment file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPAttachmentFileEntryException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first cp attachment file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPAttachmentFileEntryException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the cp attachment file entries before and after the current cp attachment file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the current cp attachment file entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	public static CPAttachmentFileEntry[] findByUuid_C_PrevAndNext(
			long CPAttachmentFileEntryId, String uuid, long companyId,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPAttachmentFileEntryException {

		return getPersistence().findByUuid_C_PrevAndNext(
			CPAttachmentFileEntryId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the cp attachment file entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of cp attachment file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp attachment file entries
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the cp attachment file entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching cp attachment file entries
	 */
	public static List<CPAttachmentFileEntry> findByC_C(
		long classNameId, long classPK) {

		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	 * Returns a range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @return the range of matching cp attachment file entries
	 */
	public static List<CPAttachmentFileEntry> findByC_C(
		long classNameId, long classPK, int start, int end) {

		return getPersistence().findByC_C(classNameId, classPK, start, end);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp attachment file entries
	 */
	public static List<CPAttachmentFileEntry> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		return getPersistence().findByC_C(
			classNameId, classPK, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp attachment file entries
	 */
	public static List<CPAttachmentFileEntry> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_C(
			classNameId, classPK, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry findByC_C_First(
			long classNameId, long classPK,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPAttachmentFileEntryException {

		return getPersistence().findByC_C_First(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry fetchByC_C_First(
		long classNameId, long classPK,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		return getPersistence().fetchByC_C_First(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry findByC_C_Last(
			long classNameId, long classPK,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPAttachmentFileEntryException {

		return getPersistence().findByC_C_Last(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry fetchByC_C_Last(
		long classNameId, long classPK,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		return getPersistence().fetchByC_C_Last(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the cp attachment file entries before and after the current cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the current cp attachment file entry
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	public static CPAttachmentFileEntry[] findByC_C_PrevAndNext(
			long CPAttachmentFileEntryId, long classNameId, long classPK,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPAttachmentFileEntryException {

		return getPersistence().findByC_C_PrevAndNext(
			CPAttachmentFileEntryId, classNameId, classPK, orderByComparator);
	}

	/**
	 * Removes all the cp attachment file entries where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public static void removeByC_C(long classNameId, long classPK) {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	 * Returns the number of cp attachment file entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching cp attachment file entries
	 */
	public static int countByC_C(long classNameId, long classPK) {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	 * Returns all the cp attachment file entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching cp attachment file entries
	 */
	public static List<CPAttachmentFileEntry> findByLtD_S(
		Date displayDate, int status) {

		return getPersistence().findByLtD_S(displayDate, status);
	}

	/**
	 * Returns a range of all the cp attachment file entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @return the range of matching cp attachment file entries
	 */
	public static List<CPAttachmentFileEntry> findByLtD_S(
		Date displayDate, int status, int start, int end) {

		return getPersistence().findByLtD_S(displayDate, status, start, end);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp attachment file entries
	 */
	public static List<CPAttachmentFileEntry> findByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		return getPersistence().findByLtD_S(
			displayDate, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp attachment file entries
	 */
	public static List<CPAttachmentFileEntry> findByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByLtD_S(
			displayDate, status, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cp attachment file entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry findByLtD_S_First(
			Date displayDate, int status,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPAttachmentFileEntryException {

		return getPersistence().findByLtD_S_First(
			displayDate, status, orderByComparator);
	}

	/**
	 * Returns the first cp attachment file entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry fetchByLtD_S_First(
		Date displayDate, int status,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		return getPersistence().fetchByLtD_S_First(
			displayDate, status, orderByComparator);
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry findByLtD_S_Last(
			Date displayDate, int status,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPAttachmentFileEntryException {

		return getPersistence().findByLtD_S_Last(
			displayDate, status, orderByComparator);
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry fetchByLtD_S_Last(
		Date displayDate, int status,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		return getPersistence().fetchByLtD_S_Last(
			displayDate, status, orderByComparator);
	}

	/**
	 * Returns the cp attachment file entries before and after the current cp attachment file entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the current cp attachment file entry
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	public static CPAttachmentFileEntry[] findByLtD_S_PrevAndNext(
			long CPAttachmentFileEntryId, Date displayDate, int status,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPAttachmentFileEntryException {

		return getPersistence().findByLtD_S_PrevAndNext(
			CPAttachmentFileEntryId, displayDate, status, orderByComparator);
	}

	/**
	 * Removes all the cp attachment file entries where displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 */
	public static void removeByLtD_S(Date displayDate, int status) {
		getPersistence().removeByLtD_S(displayDate, status);
	}

	/**
	 * Returns the number of cp attachment file entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching cp attachment file entries
	 */
	public static int countByLtD_S(Date displayDate, int status) {
		return getPersistence().countByLtD_S(displayDate, status);
	}

	/**
	 * Returns all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and fileEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param fileEntryId the file entry ID
	 * @return the matching cp attachment file entries
	 */
	public static List<CPAttachmentFileEntry> findByC_C_F(
		long classNameId, long classPK, long fileEntryId) {

		return getPersistence().findByC_C_F(classNameId, classPK, fileEntryId);
	}

	/**
	 * Returns a range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @return the range of matching cp attachment file entries
	 */
	public static List<CPAttachmentFileEntry> findByC_C_F(
		long classNameId, long classPK, long fileEntryId, int start, int end) {

		return getPersistence().findByC_C_F(
			classNameId, classPK, fileEntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp attachment file entries
	 */
	public static List<CPAttachmentFileEntry> findByC_C_F(
		long classNameId, long classPK, long fileEntryId, int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		return getPersistence().findByC_C_F(
			classNameId, classPK, fileEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp attachment file entries
	 */
	public static List<CPAttachmentFileEntry> findByC_C_F(
		long classNameId, long classPK, long fileEntryId, int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_C_F(
			classNameId, classPK, fileEntryId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and fileEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry findByC_C_F_First(
			long classNameId, long classPK, long fileEntryId,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPAttachmentFileEntryException {

		return getPersistence().findByC_C_F_First(
			classNameId, classPK, fileEntryId, orderByComparator);
	}

	/**
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and fileEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry fetchByC_C_F_First(
		long classNameId, long classPK, long fileEntryId,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		return getPersistence().fetchByC_C_F_First(
			classNameId, classPK, fileEntryId, orderByComparator);
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and fileEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry findByC_C_F_Last(
			long classNameId, long classPK, long fileEntryId,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPAttachmentFileEntryException {

		return getPersistence().findByC_C_F_Last(
			classNameId, classPK, fileEntryId, orderByComparator);
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and fileEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry fetchByC_C_F_Last(
		long classNameId, long classPK, long fileEntryId,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		return getPersistence().fetchByC_C_F_Last(
			classNameId, classPK, fileEntryId, orderByComparator);
	}

	/**
	 * Returns the cp attachment file entries before and after the current cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and fileEntryId = &#63;.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the current cp attachment file entry
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	public static CPAttachmentFileEntry[] findByC_C_F_PrevAndNext(
			long CPAttachmentFileEntryId, long classNameId, long classPK,
			long fileEntryId,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPAttachmentFileEntryException {

		return getPersistence().findByC_C_F_PrevAndNext(
			CPAttachmentFileEntryId, classNameId, classPK, fileEntryId,
			orderByComparator);
	}

	/**
	 * Removes all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and fileEntryId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param fileEntryId the file entry ID
	 */
	public static void removeByC_C_F(
		long classNameId, long classPK, long fileEntryId) {

		getPersistence().removeByC_C_F(classNameId, classPK, fileEntryId);
	}

	/**
	 * Returns the number of cp attachment file entries where classNameId = &#63; and classPK = &#63; and fileEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param fileEntryId the file entry ID
	 * @return the number of matching cp attachment file entries
	 */
	public static int countByC_C_F(
		long classNameId, long classPK, long fileEntryId) {

		return getPersistence().countByC_C_F(classNameId, classPK, fileEntryId);
	}

	/**
	 * Returns all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and cdnURL = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param cdnURL the cdn url
	 * @return the matching cp attachment file entries
	 */
	public static List<CPAttachmentFileEntry> findByC_C_C(
		long classNameId, long classPK, String cdnURL) {

		return getPersistence().findByC_C_C(classNameId, classPK, cdnURL);
	}

	/**
	 * Returns a range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and cdnURL = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param cdnURL the cdn url
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @return the range of matching cp attachment file entries
	 */
	public static List<CPAttachmentFileEntry> findByC_C_C(
		long classNameId, long classPK, String cdnURL, int start, int end) {

		return getPersistence().findByC_C_C(
			classNameId, classPK, cdnURL, start, end);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and cdnURL = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param cdnURL the cdn url
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp attachment file entries
	 */
	public static List<CPAttachmentFileEntry> findByC_C_C(
		long classNameId, long classPK, String cdnURL, int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		return getPersistence().findByC_C_C(
			classNameId, classPK, cdnURL, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and cdnURL = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param cdnURL the cdn url
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp attachment file entries
	 */
	public static List<CPAttachmentFileEntry> findByC_C_C(
		long classNameId, long classPK, String cdnURL, int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_C_C(
			classNameId, classPK, cdnURL, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and cdnURL = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param cdnURL the cdn url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry findByC_C_C_First(
			long classNameId, long classPK, String cdnURL,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPAttachmentFileEntryException {

		return getPersistence().findByC_C_C_First(
			classNameId, classPK, cdnURL, orderByComparator);
	}

	/**
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and cdnURL = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param cdnURL the cdn url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry fetchByC_C_C_First(
		long classNameId, long classPK, String cdnURL,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		return getPersistence().fetchByC_C_C_First(
			classNameId, classPK, cdnURL, orderByComparator);
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and cdnURL = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param cdnURL the cdn url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry findByC_C_C_Last(
			long classNameId, long classPK, String cdnURL,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPAttachmentFileEntryException {

		return getPersistence().findByC_C_C_Last(
			classNameId, classPK, cdnURL, orderByComparator);
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and cdnURL = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param cdnURL the cdn url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry fetchByC_C_C_Last(
		long classNameId, long classPK, String cdnURL,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		return getPersistence().fetchByC_C_C_Last(
			classNameId, classPK, cdnURL, orderByComparator);
	}

	/**
	 * Returns the cp attachment file entries before and after the current cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and cdnURL = &#63;.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the current cp attachment file entry
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param cdnURL the cdn url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	public static CPAttachmentFileEntry[] findByC_C_C_PrevAndNext(
			long CPAttachmentFileEntryId, long classNameId, long classPK,
			String cdnURL,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPAttachmentFileEntryException {

		return getPersistence().findByC_C_C_PrevAndNext(
			CPAttachmentFileEntryId, classNameId, classPK, cdnURL,
			orderByComparator);
	}

	/**
	 * Removes all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and cdnURL = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param cdnURL the cdn url
	 */
	public static void removeByC_C_C(
		long classNameId, long classPK, String cdnURL) {

		getPersistence().removeByC_C_C(classNameId, classPK, cdnURL);
	}

	/**
	 * Returns the number of cp attachment file entries where classNameId = &#63; and classPK = &#63; and cdnURL = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param cdnURL the cdn url
	 * @return the number of matching cp attachment file entries
	 */
	public static int countByC_C_C(
		long classNameId, long classPK, String cdnURL) {

		return getPersistence().countByC_C_C(classNameId, classPK, cdnURL);
	}

	/**
	 * Returns all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching cp attachment file entries
	 */
	public static List<CPAttachmentFileEntry> findByC_C_LtD_S(
		long classNameId, long classPK, Date displayDate, int status) {

		return getPersistence().findByC_C_LtD_S(
			classNameId, classPK, displayDate, status);
	}

	/**
	 * Returns a range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @return the range of matching cp attachment file entries
	 */
	public static List<CPAttachmentFileEntry> findByC_C_LtD_S(
		long classNameId, long classPK, Date displayDate, int status, int start,
		int end) {

		return getPersistence().findByC_C_LtD_S(
			classNameId, classPK, displayDate, status, start, end);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp attachment file entries
	 */
	public static List<CPAttachmentFileEntry> findByC_C_LtD_S(
		long classNameId, long classPK, Date displayDate, int status, int start,
		int end, OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		return getPersistence().findByC_C_LtD_S(
			classNameId, classPK, displayDate, status, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp attachment file entries
	 */
	public static List<CPAttachmentFileEntry> findByC_C_LtD_S(
		long classNameId, long classPK, Date displayDate, int status, int start,
		int end, OrderByComparator<CPAttachmentFileEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_C_LtD_S(
			classNameId, classPK, displayDate, status, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry findByC_C_LtD_S_First(
			long classNameId, long classPK, Date displayDate, int status,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPAttachmentFileEntryException {

		return getPersistence().findByC_C_LtD_S_First(
			classNameId, classPK, displayDate, status, orderByComparator);
	}

	/**
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry fetchByC_C_LtD_S_First(
		long classNameId, long classPK, Date displayDate, int status,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		return getPersistence().fetchByC_C_LtD_S_First(
			classNameId, classPK, displayDate, status, orderByComparator);
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry findByC_C_LtD_S_Last(
			long classNameId, long classPK, Date displayDate, int status,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPAttachmentFileEntryException {

		return getPersistence().findByC_C_LtD_S_Last(
			classNameId, classPK, displayDate, status, orderByComparator);
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry fetchByC_C_LtD_S_Last(
		long classNameId, long classPK, Date displayDate, int status,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		return getPersistence().fetchByC_C_LtD_S_Last(
			classNameId, classPK, displayDate, status, orderByComparator);
	}

	/**
	 * Returns the cp attachment file entries before and after the current cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the current cp attachment file entry
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	public static CPAttachmentFileEntry[] findByC_C_LtD_S_PrevAndNext(
			long CPAttachmentFileEntryId, long classNameId, long classPK,
			Date displayDate, int status,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPAttachmentFileEntryException {

		return getPersistence().findByC_C_LtD_S_PrevAndNext(
			CPAttachmentFileEntryId, classNameId, classPK, displayDate, status,
			orderByComparator);
	}

	/**
	 * Removes all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 */
	public static void removeByC_C_LtD_S(
		long classNameId, long classPK, Date displayDate, int status) {

		getPersistence().removeByC_C_LtD_S(
			classNameId, classPK, displayDate, status);
	}

	/**
	 * Returns the number of cp attachment file entries where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching cp attachment file entries
	 */
	public static int countByC_C_LtD_S(
		long classNameId, long classPK, Date displayDate, int status) {

		return getPersistence().countByC_C_LtD_S(
			classNameId, classPK, displayDate, status);
	}

	/**
	 * Returns all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @return the matching cp attachment file entries
	 */
	public static List<CPAttachmentFileEntry> findByC_C_T_ST(
		long classNameId, long classPK, int type, int status) {

		return getPersistence().findByC_C_T_ST(
			classNameId, classPK, type, status);
	}

	/**
	 * Returns a range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @return the range of matching cp attachment file entries
	 */
	public static List<CPAttachmentFileEntry> findByC_C_T_ST(
		long classNameId, long classPK, int type, int status, int start,
		int end) {

		return getPersistence().findByC_C_T_ST(
			classNameId, classPK, type, status, start, end);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp attachment file entries
	 */
	public static List<CPAttachmentFileEntry> findByC_C_T_ST(
		long classNameId, long classPK, int type, int status, int start,
		int end, OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		return getPersistence().findByC_C_T_ST(
			classNameId, classPK, type, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp attachment file entries
	 */
	public static List<CPAttachmentFileEntry> findByC_C_T_ST(
		long classNameId, long classPK, int type, int status, int start,
		int end, OrderByComparator<CPAttachmentFileEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_C_T_ST(
			classNameId, classPK, type, status, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry findByC_C_T_ST_First(
			long classNameId, long classPK, int type, int status,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPAttachmentFileEntryException {

		return getPersistence().findByC_C_T_ST_First(
			classNameId, classPK, type, status, orderByComparator);
	}

	/**
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry fetchByC_C_T_ST_First(
		long classNameId, long classPK, int type, int status,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		return getPersistence().fetchByC_C_T_ST_First(
			classNameId, classPK, type, status, orderByComparator);
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry findByC_C_T_ST_Last(
			long classNameId, long classPK, int type, int status,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPAttachmentFileEntryException {

		return getPersistence().findByC_C_T_ST_Last(
			classNameId, classPK, type, status, orderByComparator);
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry fetchByC_C_T_ST_Last(
		long classNameId, long classPK, int type, int status,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		return getPersistence().fetchByC_C_T_ST_Last(
			classNameId, classPK, type, status, orderByComparator);
	}

	/**
	 * Returns the cp attachment file entries before and after the current cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the current cp attachment file entry
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	public static CPAttachmentFileEntry[] findByC_C_T_ST_PrevAndNext(
			long CPAttachmentFileEntryId, long classNameId, long classPK,
			int type, int status,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPAttachmentFileEntryException {

		return getPersistence().findByC_C_T_ST_PrevAndNext(
			CPAttachmentFileEntryId, classNameId, classPK, type, status,
			orderByComparator);
	}

	/**
	 * Removes all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 */
	public static void removeByC_C_T_ST(
		long classNameId, long classPK, int type, int status) {

		getPersistence().removeByC_C_T_ST(classNameId, classPK, type, status);
	}

	/**
	 * Returns the number of cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @return the number of matching cp attachment file entries
	 */
	public static int countByC_C_T_ST(
		long classNameId, long classPK, int type, int status) {

		return getPersistence().countByC_C_T_ST(
			classNameId, classPK, type, status);
	}

	/**
	 * Returns all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @return the matching cp attachment file entries
	 */
	public static List<CPAttachmentFileEntry> findByC_C_T_NotST(
		long classNameId, long classPK, int type, int status) {

		return getPersistence().findByC_C_T_NotST(
			classNameId, classPK, type, status);
	}

	/**
	 * Returns a range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @return the range of matching cp attachment file entries
	 */
	public static List<CPAttachmentFileEntry> findByC_C_T_NotST(
		long classNameId, long classPK, int type, int status, int start,
		int end) {

		return getPersistence().findByC_C_T_NotST(
			classNameId, classPK, type, status, start, end);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp attachment file entries
	 */
	public static List<CPAttachmentFileEntry> findByC_C_T_NotST(
		long classNameId, long classPK, int type, int status, int start,
		int end, OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		return getPersistence().findByC_C_T_NotST(
			classNameId, classPK, type, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp attachment file entries
	 */
	public static List<CPAttachmentFileEntry> findByC_C_T_NotST(
		long classNameId, long classPK, int type, int status, int start,
		int end, OrderByComparator<CPAttachmentFileEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_C_T_NotST(
			classNameId, classPK, type, status, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry findByC_C_T_NotST_First(
			long classNameId, long classPK, int type, int status,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPAttachmentFileEntryException {

		return getPersistence().findByC_C_T_NotST_First(
			classNameId, classPK, type, status, orderByComparator);
	}

	/**
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry fetchByC_C_T_NotST_First(
		long classNameId, long classPK, int type, int status,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		return getPersistence().fetchByC_C_T_NotST_First(
			classNameId, classPK, type, status, orderByComparator);
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry findByC_C_T_NotST_Last(
			long classNameId, long classPK, int type, int status,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPAttachmentFileEntryException {

		return getPersistence().findByC_C_T_NotST_Last(
			classNameId, classPK, type, status, orderByComparator);
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry fetchByC_C_T_NotST_Last(
		long classNameId, long classPK, int type, int status,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		return getPersistence().fetchByC_C_T_NotST_Last(
			classNameId, classPK, type, status, orderByComparator);
	}

	/**
	 * Returns the cp attachment file entries before and after the current cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63;.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the current cp attachment file entry
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	public static CPAttachmentFileEntry[] findByC_C_T_NotST_PrevAndNext(
			long CPAttachmentFileEntryId, long classNameId, long classPK,
			int type, int status,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPAttachmentFileEntryException {

		return getPersistence().findByC_C_T_NotST_PrevAndNext(
			CPAttachmentFileEntryId, classNameId, classPK, type, status,
			orderByComparator);
	}

	/**
	 * Removes all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 */
	public static void removeByC_C_T_NotST(
		long classNameId, long classPK, int type, int status) {

		getPersistence().removeByC_C_T_NotST(
			classNameId, classPK, type, status);
	}

	/**
	 * Returns the number of cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @return the number of matching cp attachment file entries
	 */
	public static int countByC_C_T_NotST(
		long classNameId, long classPK, int type, int status) {

		return getPersistence().countByC_C_T_NotST(
			classNameId, classPK, type, status);
	}

	/**
	 * Returns the cp attachment file entry where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchCPAttachmentFileEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry findByC_ERC(
			long companyId, String externalReferenceCode)
		throws com.liferay.commerce.product.exception.
			NoSuchCPAttachmentFileEntryException {

		return getPersistence().findByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the cp attachment file entry where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry fetchByC_ERC(
		long companyId, String externalReferenceCode) {

		return getPersistence().fetchByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the cp attachment file entry where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	public static CPAttachmentFileEntry fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache) {

		return getPersistence().fetchByC_ERC(
			companyId, externalReferenceCode, useFinderCache);
	}

	/**
	 * Removes the cp attachment file entry where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the cp attachment file entry that was removed
	 */
	public static CPAttachmentFileEntry removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws com.liferay.commerce.product.exception.
			NoSuchCPAttachmentFileEntryException {

		return getPersistence().removeByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the number of cp attachment file entries where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching cp attachment file entries
	 */
	public static int countByC_ERC(
		long companyId, String externalReferenceCode) {

		return getPersistence().countByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Caches the cp attachment file entry in the entity cache if it is enabled.
	 *
	 * @param cpAttachmentFileEntry the cp attachment file entry
	 */
	public static void cacheResult(
		CPAttachmentFileEntry cpAttachmentFileEntry) {

		getPersistence().cacheResult(cpAttachmentFileEntry);
	}

	/**
	 * Caches the cp attachment file entries in the entity cache if it is enabled.
	 *
	 * @param cpAttachmentFileEntries the cp attachment file entries
	 */
	public static void cacheResult(
		List<CPAttachmentFileEntry> cpAttachmentFileEntries) {

		getPersistence().cacheResult(cpAttachmentFileEntries);
	}

	/**
	 * Creates a new cp attachment file entry with the primary key. Does not add the cp attachment file entry to the database.
	 *
	 * @param CPAttachmentFileEntryId the primary key for the new cp attachment file entry
	 * @return the new cp attachment file entry
	 */
	public static CPAttachmentFileEntry create(long CPAttachmentFileEntryId) {
		return getPersistence().create(CPAttachmentFileEntryId);
	}

	/**
	 * Removes the cp attachment file entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the cp attachment file entry
	 * @return the cp attachment file entry that was removed
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	public static CPAttachmentFileEntry remove(long CPAttachmentFileEntryId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPAttachmentFileEntryException {

		return getPersistence().remove(CPAttachmentFileEntryId);
	}

	public static CPAttachmentFileEntry updateImpl(
		CPAttachmentFileEntry cpAttachmentFileEntry) {

		return getPersistence().updateImpl(cpAttachmentFileEntry);
	}

	/**
	 * Returns the cp attachment file entry with the primary key or throws a <code>NoSuchCPAttachmentFileEntryException</code> if it could not be found.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the cp attachment file entry
	 * @return the cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	public static CPAttachmentFileEntry findByPrimaryKey(
			long CPAttachmentFileEntryId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPAttachmentFileEntryException {

		return getPersistence().findByPrimaryKey(CPAttachmentFileEntryId);
	}

	/**
	 * Returns the cp attachment file entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the cp attachment file entry
	 * @return the cp attachment file entry, or <code>null</code> if a cp attachment file entry with the primary key could not be found
	 */
	public static CPAttachmentFileEntry fetchByPrimaryKey(
		long CPAttachmentFileEntryId) {

		return getPersistence().fetchByPrimaryKey(CPAttachmentFileEntryId);
	}

	/**
	 * Returns all the cp attachment file entries.
	 *
	 * @return the cp attachment file entries
	 */
	public static List<CPAttachmentFileEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the cp attachment file entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @return the range of cp attachment file entries
	 */
	public static List<CPAttachmentFileEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp attachment file entries
	 */
	public static List<CPAttachmentFileEntry> findAll(
		int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp attachment file entries
	 */
	public static List<CPAttachmentFileEntry> findAll(
		int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the cp attachment file entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of cp attachment file entries.
	 *
	 * @return the number of cp attachment file entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CPAttachmentFileEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CPAttachmentFileEntryPersistence, CPAttachmentFileEntryPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CPAttachmentFileEntryPersistence.class);

		ServiceTracker
			<CPAttachmentFileEntryPersistence, CPAttachmentFileEntryPersistence>
				serviceTracker =
					new ServiceTracker
						<CPAttachmentFileEntryPersistence,
						 CPAttachmentFileEntryPersistence>(
							 bundle.getBundleContext(),
							 CPAttachmentFileEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}
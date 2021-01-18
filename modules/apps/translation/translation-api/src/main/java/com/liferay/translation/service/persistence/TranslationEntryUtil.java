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

package com.liferay.translation.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.translation.model.TranslationEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the translation entry service. This utility wraps <code>com.liferay.translation.service.persistence.impl.TranslationEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TranslationEntryPersistence
 * @generated
 */
public class TranslationEntryUtil {

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
	public static void clearCache(TranslationEntry translationEntry) {
		getPersistence().clearCache(translationEntry);
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
	public static Map<Serializable, TranslationEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<TranslationEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<TranslationEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<TranslationEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<TranslationEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static TranslationEntry update(TranslationEntry translationEntry) {
		return getPersistence().update(translationEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static TranslationEntry update(
		TranslationEntry translationEntry, ServiceContext serviceContext) {

		return getPersistence().update(translationEntry, serviceContext);
	}

	/**
	 * Returns all the translation entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching translation entries
	 */
	public static List<TranslationEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the translation entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TranslationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of translation entries
	 * @param end the upper bound of the range of translation entries (not inclusive)
	 * @return the range of matching translation entries
	 */
	public static List<TranslationEntry> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the translation entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TranslationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of translation entries
	 * @param end the upper bound of the range of translation entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching translation entries
	 */
	public static List<TranslationEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<TranslationEntry> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the translation entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TranslationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of translation entries
	 * @param end the upper bound of the range of translation entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching translation entries
	 */
	public static List<TranslationEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<TranslationEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first translation entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching translation entry
	 * @throws NoSuchEntryException if a matching translation entry could not be found
	 */
	public static TranslationEntry findByUuid_First(
			String uuid, OrderByComparator<TranslationEntry> orderByComparator)
		throws com.liferay.translation.exception.NoSuchEntryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first translation entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching translation entry, or <code>null</code> if a matching translation entry could not be found
	 */
	public static TranslationEntry fetchByUuid_First(
		String uuid, OrderByComparator<TranslationEntry> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last translation entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching translation entry
	 * @throws NoSuchEntryException if a matching translation entry could not be found
	 */
	public static TranslationEntry findByUuid_Last(
			String uuid, OrderByComparator<TranslationEntry> orderByComparator)
		throws com.liferay.translation.exception.NoSuchEntryException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last translation entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching translation entry, or <code>null</code> if a matching translation entry could not be found
	 */
	public static TranslationEntry fetchByUuid_Last(
		String uuid, OrderByComparator<TranslationEntry> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the translation entries before and after the current translation entry in the ordered set where uuid = &#63;.
	 *
	 * @param translationEntryId the primary key of the current translation entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next translation entry
	 * @throws NoSuchEntryException if a translation entry with the primary key could not be found
	 */
	public static TranslationEntry[] findByUuid_PrevAndNext(
			long translationEntryId, String uuid,
			OrderByComparator<TranslationEntry> orderByComparator)
		throws com.liferay.translation.exception.NoSuchEntryException {

		return getPersistence().findByUuid_PrevAndNext(
			translationEntryId, uuid, orderByComparator);
	}

	/**
	 * Removes all the translation entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of translation entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching translation entries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the translation entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching translation entry
	 * @throws NoSuchEntryException if a matching translation entry could not be found
	 */
	public static TranslationEntry findByUUID_G(String uuid, long groupId)
		throws com.liferay.translation.exception.NoSuchEntryException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the translation entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching translation entry, or <code>null</code> if a matching translation entry could not be found
	 */
	public static TranslationEntry fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the translation entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching translation entry, or <code>null</code> if a matching translation entry could not be found
	 */
	public static TranslationEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the translation entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the translation entry that was removed
	 */
	public static TranslationEntry removeByUUID_G(String uuid, long groupId)
		throws com.liferay.translation.exception.NoSuchEntryException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of translation entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching translation entries
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the translation entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching translation entries
	 */
	public static List<TranslationEntry> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the translation entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TranslationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of translation entries
	 * @param end the upper bound of the range of translation entries (not inclusive)
	 * @return the range of matching translation entries
	 */
	public static List<TranslationEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the translation entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TranslationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of translation entries
	 * @param end the upper bound of the range of translation entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching translation entries
	 */
	public static List<TranslationEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<TranslationEntry> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the translation entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TranslationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of translation entries
	 * @param end the upper bound of the range of translation entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching translation entries
	 */
	public static List<TranslationEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<TranslationEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first translation entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching translation entry
	 * @throws NoSuchEntryException if a matching translation entry could not be found
	 */
	public static TranslationEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<TranslationEntry> orderByComparator)
		throws com.liferay.translation.exception.NoSuchEntryException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first translation entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching translation entry, or <code>null</code> if a matching translation entry could not be found
	 */
	public static TranslationEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<TranslationEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last translation entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching translation entry
	 * @throws NoSuchEntryException if a matching translation entry could not be found
	 */
	public static TranslationEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<TranslationEntry> orderByComparator)
		throws com.liferay.translation.exception.NoSuchEntryException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last translation entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching translation entry, or <code>null</code> if a matching translation entry could not be found
	 */
	public static TranslationEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<TranslationEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the translation entries before and after the current translation entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param translationEntryId the primary key of the current translation entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next translation entry
	 * @throws NoSuchEntryException if a translation entry with the primary key could not be found
	 */
	public static TranslationEntry[] findByUuid_C_PrevAndNext(
			long translationEntryId, String uuid, long companyId,
			OrderByComparator<TranslationEntry> orderByComparator)
		throws com.liferay.translation.exception.NoSuchEntryException {

		return getPersistence().findByUuid_C_PrevAndNext(
			translationEntryId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the translation entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of translation entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching translation entries
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the translation entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching translation entries
	 */
	public static List<TranslationEntry> findByC_C(
		long classNameId, long classPK) {

		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	 * Returns a range of all the translation entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TranslationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of translation entries
	 * @param end the upper bound of the range of translation entries (not inclusive)
	 * @return the range of matching translation entries
	 */
	public static List<TranslationEntry> findByC_C(
		long classNameId, long classPK, int start, int end) {

		return getPersistence().findByC_C(classNameId, classPK, start, end);
	}

	/**
	 * Returns an ordered range of all the translation entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TranslationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of translation entries
	 * @param end the upper bound of the range of translation entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching translation entries
	 */
	public static List<TranslationEntry> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<TranslationEntry> orderByComparator) {

		return getPersistence().findByC_C(
			classNameId, classPK, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the translation entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TranslationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of translation entries
	 * @param end the upper bound of the range of translation entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching translation entries
	 */
	public static List<TranslationEntry> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<TranslationEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_C(
			classNameId, classPK, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first translation entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching translation entry
	 * @throws NoSuchEntryException if a matching translation entry could not be found
	 */
	public static TranslationEntry findByC_C_First(
			long classNameId, long classPK,
			OrderByComparator<TranslationEntry> orderByComparator)
		throws com.liferay.translation.exception.NoSuchEntryException {

		return getPersistence().findByC_C_First(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the first translation entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching translation entry, or <code>null</code> if a matching translation entry could not be found
	 */
	public static TranslationEntry fetchByC_C_First(
		long classNameId, long classPK,
		OrderByComparator<TranslationEntry> orderByComparator) {

		return getPersistence().fetchByC_C_First(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the last translation entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching translation entry
	 * @throws NoSuchEntryException if a matching translation entry could not be found
	 */
	public static TranslationEntry findByC_C_Last(
			long classNameId, long classPK,
			OrderByComparator<TranslationEntry> orderByComparator)
		throws com.liferay.translation.exception.NoSuchEntryException {

		return getPersistence().findByC_C_Last(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the last translation entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching translation entry, or <code>null</code> if a matching translation entry could not be found
	 */
	public static TranslationEntry fetchByC_C_Last(
		long classNameId, long classPK,
		OrderByComparator<TranslationEntry> orderByComparator) {

		return getPersistence().fetchByC_C_Last(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the translation entries before and after the current translation entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param translationEntryId the primary key of the current translation entry
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next translation entry
	 * @throws NoSuchEntryException if a translation entry with the primary key could not be found
	 */
	public static TranslationEntry[] findByC_C_PrevAndNext(
			long translationEntryId, long classNameId, long classPK,
			OrderByComparator<TranslationEntry> orderByComparator)
		throws com.liferay.translation.exception.NoSuchEntryException {

		return getPersistence().findByC_C_PrevAndNext(
			translationEntryId, classNameId, classPK, orderByComparator);
	}

	/**
	 * Removes all the translation entries where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public static void removeByC_C(long classNameId, long classPK) {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	 * Returns the number of translation entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching translation entries
	 */
	public static int countByC_C(long classNameId, long classPK) {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	 * Returns the translation entry where classNameId = &#63; and classPK = &#63; and languageId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @return the matching translation entry
	 * @throws NoSuchEntryException if a matching translation entry could not be found
	 */
	public static TranslationEntry findByC_C_L(
			long classNameId, long classPK, String languageId)
		throws com.liferay.translation.exception.NoSuchEntryException {

		return getPersistence().findByC_C_L(classNameId, classPK, languageId);
	}

	/**
	 * Returns the translation entry where classNameId = &#63; and classPK = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @return the matching translation entry, or <code>null</code> if a matching translation entry could not be found
	 */
	public static TranslationEntry fetchByC_C_L(
		long classNameId, long classPK, String languageId) {

		return getPersistence().fetchByC_C_L(classNameId, classPK, languageId);
	}

	/**
	 * Returns the translation entry where classNameId = &#63; and classPK = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching translation entry, or <code>null</code> if a matching translation entry could not be found
	 */
	public static TranslationEntry fetchByC_C_L(
		long classNameId, long classPK, String languageId,
		boolean useFinderCache) {

		return getPersistence().fetchByC_C_L(
			classNameId, classPK, languageId, useFinderCache);
	}

	/**
	 * Removes the translation entry where classNameId = &#63; and classPK = &#63; and languageId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @return the translation entry that was removed
	 */
	public static TranslationEntry removeByC_C_L(
			long classNameId, long classPK, String languageId)
		throws com.liferay.translation.exception.NoSuchEntryException {

		return getPersistence().removeByC_C_L(classNameId, classPK, languageId);
	}

	/**
	 * Returns the number of translation entries where classNameId = &#63; and classPK = &#63; and languageId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @return the number of matching translation entries
	 */
	public static int countByC_C_L(
		long classNameId, long classPK, String languageId) {

		return getPersistence().countByC_C_L(classNameId, classPK, languageId);
	}

	/**
	 * Caches the translation entry in the entity cache if it is enabled.
	 *
	 * @param translationEntry the translation entry
	 */
	public static void cacheResult(TranslationEntry translationEntry) {
		getPersistence().cacheResult(translationEntry);
	}

	/**
	 * Caches the translation entries in the entity cache if it is enabled.
	 *
	 * @param translationEntries the translation entries
	 */
	public static void cacheResult(List<TranslationEntry> translationEntries) {
		getPersistence().cacheResult(translationEntries);
	}

	/**
	 * Creates a new translation entry with the primary key. Does not add the translation entry to the database.
	 *
	 * @param translationEntryId the primary key for the new translation entry
	 * @return the new translation entry
	 */
	public static TranslationEntry create(long translationEntryId) {
		return getPersistence().create(translationEntryId);
	}

	/**
	 * Removes the translation entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param translationEntryId the primary key of the translation entry
	 * @return the translation entry that was removed
	 * @throws NoSuchEntryException if a translation entry with the primary key could not be found
	 */
	public static TranslationEntry remove(long translationEntryId)
		throws com.liferay.translation.exception.NoSuchEntryException {

		return getPersistence().remove(translationEntryId);
	}

	public static TranslationEntry updateImpl(
		TranslationEntry translationEntry) {

		return getPersistence().updateImpl(translationEntry);
	}

	/**
	 * Returns the translation entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param translationEntryId the primary key of the translation entry
	 * @return the translation entry
	 * @throws NoSuchEntryException if a translation entry with the primary key could not be found
	 */
	public static TranslationEntry findByPrimaryKey(long translationEntryId)
		throws com.liferay.translation.exception.NoSuchEntryException {

		return getPersistence().findByPrimaryKey(translationEntryId);
	}

	/**
	 * Returns the translation entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param translationEntryId the primary key of the translation entry
	 * @return the translation entry, or <code>null</code> if a translation entry with the primary key could not be found
	 */
	public static TranslationEntry fetchByPrimaryKey(long translationEntryId) {
		return getPersistence().fetchByPrimaryKey(translationEntryId);
	}

	/**
	 * Returns all the translation entries.
	 *
	 * @return the translation entries
	 */
	public static List<TranslationEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the translation entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TranslationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of translation entries
	 * @param end the upper bound of the range of translation entries (not inclusive)
	 * @return the range of translation entries
	 */
	public static List<TranslationEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the translation entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TranslationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of translation entries
	 * @param end the upper bound of the range of translation entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of translation entries
	 */
	public static List<TranslationEntry> findAll(
		int start, int end,
		OrderByComparator<TranslationEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the translation entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TranslationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of translation entries
	 * @param end the upper bound of the range of translation entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of translation entries
	 */
	public static List<TranslationEntry> findAll(
		int start, int end,
		OrderByComparator<TranslationEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the translation entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of translation entries.
	 *
	 * @return the number of translation entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static TranslationEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<TranslationEntryPersistence, TranslationEntryPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			TranslationEntryPersistence.class);

		ServiceTracker<TranslationEntryPersistence, TranslationEntryPersistence>
			serviceTracker =
				new ServiceTracker
					<TranslationEntryPersistence, TranslationEntryPersistence>(
						bundle.getBundleContext(),
						TranslationEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}
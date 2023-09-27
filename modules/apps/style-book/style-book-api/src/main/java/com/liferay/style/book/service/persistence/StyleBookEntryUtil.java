/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.style.book.model.StyleBookEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the style book entry service. This utility wraps <code>com.liferay.style.book.service.persistence.impl.StyleBookEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see StyleBookEntryPersistence
 * @generated
 */
public class StyleBookEntryUtil {

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
	public static void clearCache(StyleBookEntry styleBookEntry) {
		getPersistence().clearCache(styleBookEntry);
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
	public static Map<Serializable, StyleBookEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<StyleBookEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<StyleBookEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<StyleBookEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static StyleBookEntry update(StyleBookEntry styleBookEntry) {
		return getPersistence().update(styleBookEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static StyleBookEntry update(
		StyleBookEntry styleBookEntry, ServiceContext serviceContext) {

		return getPersistence().update(styleBookEntry, serviceContext);
	}

	/**
	 * Returns all the style book entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching style book entries
	 */
	public static List<StyleBookEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the style book entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	public static List<StyleBookEntry> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the style book entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	public static List<StyleBookEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the style book entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	public static List<StyleBookEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first style book entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public static StyleBookEntry findByUuid_First(
			String uuid, OrderByComparator<StyleBookEntry> orderByComparator)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first style book entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public static StyleBookEntry fetchByUuid_First(
		String uuid, OrderByComparator<StyleBookEntry> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last style book entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public static StyleBookEntry findByUuid_Last(
			String uuid, OrderByComparator<StyleBookEntry> orderByComparator)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last style book entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public static StyleBookEntry fetchByUuid_Last(
		String uuid, OrderByComparator<StyleBookEntry> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where uuid = &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	public static StyleBookEntry[] findByUuid_PrevAndNext(
			long styleBookEntryId, String uuid,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().findByUuid_PrevAndNext(
			styleBookEntryId, uuid, orderByComparator);
	}

	/**
	 * Removes all the style book entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of style book entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching style book entries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the style book entries where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @return the matching style book entries
	 */
	public static List<StyleBookEntry> findByUuid_Head(
		String uuid, boolean head) {

		return getPersistence().findByUuid_Head(uuid, head);
	}

	/**
	 * Returns a range of all the style book entries where uuid = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	public static List<StyleBookEntry> findByUuid_Head(
		String uuid, boolean head, int start, int end) {

		return getPersistence().findByUuid_Head(uuid, head, start, end);
	}

	/**
	 * Returns an ordered range of all the style book entries where uuid = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	public static List<StyleBookEntry> findByUuid_Head(
		String uuid, boolean head, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return getPersistence().findByUuid_Head(
			uuid, head, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the style book entries where uuid = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	public static List<StyleBookEntry> findByUuid_Head(
		String uuid, boolean head, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_Head(
			uuid, head, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first style book entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public static StyleBookEntry findByUuid_Head_First(
			String uuid, boolean head,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().findByUuid_Head_First(
			uuid, head, orderByComparator);
	}

	/**
	 * Returns the first style book entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public static StyleBookEntry fetchByUuid_Head_First(
		String uuid, boolean head,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return getPersistence().fetchByUuid_Head_First(
			uuid, head, orderByComparator);
	}

	/**
	 * Returns the last style book entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public static StyleBookEntry findByUuid_Head_Last(
			String uuid, boolean head,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().findByUuid_Head_Last(
			uuid, head, orderByComparator);
	}

	/**
	 * Returns the last style book entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public static StyleBookEntry fetchByUuid_Head_Last(
		String uuid, boolean head,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return getPersistence().fetchByUuid_Head_Last(
			uuid, head, orderByComparator);
	}

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	public static StyleBookEntry[] findByUuid_Head_PrevAndNext(
			long styleBookEntryId, String uuid, boolean head,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().findByUuid_Head_PrevAndNext(
			styleBookEntryId, uuid, head, orderByComparator);
	}

	/**
	 * Removes all the style book entries where uuid = &#63; and head = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 */
	public static void removeByUuid_Head(String uuid, boolean head) {
		getPersistence().removeByUuid_Head(uuid, head);
	}

	/**
	 * Returns the number of style book entries where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @return the number of matching style book entries
	 */
	public static int countByUuid_Head(String uuid, boolean head) {
		return getPersistence().countByUuid_Head(uuid, head);
	}

	/**
	 * Returns all the style book entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching style book entries
	 */
	public static List<StyleBookEntry> findByUUID_G(String uuid, long groupId) {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns a range of all the style book entries where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	public static List<StyleBookEntry> findByUUID_G(
		String uuid, long groupId, int start, int end) {

		return getPersistence().findByUUID_G(uuid, groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the style book entries where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	public static List<StyleBookEntry> findByUUID_G(
		String uuid, long groupId, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return getPersistence().findByUUID_G(
			uuid, groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the style book entries where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	public static List<StyleBookEntry> findByUUID_G(
		String uuid, long groupId, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUUID_G(
			uuid, groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first style book entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public static StyleBookEntry findByUUID_G_First(
			String uuid, long groupId,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().findByUUID_G_First(
			uuid, groupId, orderByComparator);
	}

	/**
	 * Returns the first style book entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public static StyleBookEntry fetchByUUID_G_First(
		String uuid, long groupId,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return getPersistence().fetchByUUID_G_First(
			uuid, groupId, orderByComparator);
	}

	/**
	 * Returns the last style book entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public static StyleBookEntry findByUUID_G_Last(
			String uuid, long groupId,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().findByUUID_G_Last(
			uuid, groupId, orderByComparator);
	}

	/**
	 * Returns the last style book entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public static StyleBookEntry fetchByUUID_G_Last(
		String uuid, long groupId,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return getPersistence().fetchByUUID_G_Last(
			uuid, groupId, orderByComparator);
	}

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	public static StyleBookEntry[] findByUUID_G_PrevAndNext(
			long styleBookEntryId, String uuid, long groupId,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().findByUUID_G_PrevAndNext(
			styleBookEntryId, uuid, groupId, orderByComparator);
	}

	/**
	 * Removes all the style book entries where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 */
	public static void removeByUUID_G(String uuid, long groupId) {
		getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of style book entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching style book entries
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the style book entry where uuid = &#63; and groupId = &#63; and head = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @return the matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public static StyleBookEntry findByUUID_G_Head(
			String uuid, long groupId, boolean head)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().findByUUID_G_Head(uuid, groupId, head);
	}

	/**
	 * Returns the style book entry where uuid = &#63; and groupId = &#63; and head = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @return the matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public static StyleBookEntry fetchByUUID_G_Head(
		String uuid, long groupId, boolean head) {

		return getPersistence().fetchByUUID_G_Head(uuid, groupId, head);
	}

	/**
	 * Returns the style book entry where uuid = &#63; and groupId = &#63; and head = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public static StyleBookEntry fetchByUUID_G_Head(
		String uuid, long groupId, boolean head, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G_Head(
			uuid, groupId, head, useFinderCache);
	}

	/**
	 * Removes the style book entry where uuid = &#63; and groupId = &#63; and head = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @return the style book entry that was removed
	 */
	public static StyleBookEntry removeByUUID_G_Head(
			String uuid, long groupId, boolean head)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().removeByUUID_G_Head(uuid, groupId, head);
	}

	/**
	 * Returns the number of style book entries where uuid = &#63; and groupId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @return the number of matching style book entries
	 */
	public static int countByUUID_G_Head(
		String uuid, long groupId, boolean head) {

		return getPersistence().countByUUID_G_Head(uuid, groupId, head);
	}

	/**
	 * Returns all the style book entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching style book entries
	 */
	public static List<StyleBookEntry> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the style book entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	public static List<StyleBookEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the style book entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	public static List<StyleBookEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the style book entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	public static List<StyleBookEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first style book entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public static StyleBookEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first style book entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public static StyleBookEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last style book entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public static StyleBookEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last style book entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public static StyleBookEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	public static StyleBookEntry[] findByUuid_C_PrevAndNext(
			long styleBookEntryId, String uuid, long companyId,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().findByUuid_C_PrevAndNext(
			styleBookEntryId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the style book entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of style book entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching style book entries
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the style book entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @return the matching style book entries
	 */
	public static List<StyleBookEntry> findByUuid_C_Head(
		String uuid, long companyId, boolean head) {

		return getPersistence().findByUuid_C_Head(uuid, companyId, head);
	}

	/**
	 * Returns a range of all the style book entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	public static List<StyleBookEntry> findByUuid_C_Head(
		String uuid, long companyId, boolean head, int start, int end) {

		return getPersistence().findByUuid_C_Head(
			uuid, companyId, head, start, end);
	}

	/**
	 * Returns an ordered range of all the style book entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	public static List<StyleBookEntry> findByUuid_C_Head(
		String uuid, long companyId, boolean head, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return getPersistence().findByUuid_C_Head(
			uuid, companyId, head, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the style book entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	public static List<StyleBookEntry> findByUuid_C_Head(
		String uuid, long companyId, boolean head, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C_Head(
			uuid, companyId, head, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first style book entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public static StyleBookEntry findByUuid_C_Head_First(
			String uuid, long companyId, boolean head,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().findByUuid_C_Head_First(
			uuid, companyId, head, orderByComparator);
	}

	/**
	 * Returns the first style book entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public static StyleBookEntry fetchByUuid_C_Head_First(
		String uuid, long companyId, boolean head,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_Head_First(
			uuid, companyId, head, orderByComparator);
	}

	/**
	 * Returns the last style book entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public static StyleBookEntry findByUuid_C_Head_Last(
			String uuid, long companyId, boolean head,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().findByUuid_C_Head_Last(
			uuid, companyId, head, orderByComparator);
	}

	/**
	 * Returns the last style book entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public static StyleBookEntry fetchByUuid_C_Head_Last(
		String uuid, long companyId, boolean head,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_Head_Last(
			uuid, companyId, head, orderByComparator);
	}

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	public static StyleBookEntry[] findByUuid_C_Head_PrevAndNext(
			long styleBookEntryId, String uuid, long companyId, boolean head,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().findByUuid_C_Head_PrevAndNext(
			styleBookEntryId, uuid, companyId, head, orderByComparator);
	}

	/**
	 * Removes all the style book entries where uuid = &#63; and companyId = &#63; and head = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 */
	public static void removeByUuid_C_Head(
		String uuid, long companyId, boolean head) {

		getPersistence().removeByUuid_C_Head(uuid, companyId, head);
	}

	/**
	 * Returns the number of style book entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @return the number of matching style book entries
	 */
	public static int countByUuid_C_Head(
		String uuid, long companyId, boolean head) {

		return getPersistence().countByUuid_C_Head(uuid, companyId, head);
	}

	/**
	 * Returns all the style book entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching style book entries
	 */
	public static List<StyleBookEntry> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	 * Returns a range of all the style book entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	public static List<StyleBookEntry> findByGroupId(
		long groupId, int start, int end) {

		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	public static List<StyleBookEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	public static List<StyleBookEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public static StyleBookEntry findByGroupId_First(
			long groupId, OrderByComparator<StyleBookEntry> orderByComparator)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public static StyleBookEntry fetchByGroupId_First(
		long groupId, OrderByComparator<StyleBookEntry> orderByComparator) {

		return getPersistence().fetchByGroupId_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public static StyleBookEntry findByGroupId_Last(
			long groupId, OrderByComparator<StyleBookEntry> orderByComparator)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public static StyleBookEntry fetchByGroupId_Last(
		long groupId, OrderByComparator<StyleBookEntry> orderByComparator) {

		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where groupId = &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	public static StyleBookEntry[] findByGroupId_PrevAndNext(
			long styleBookEntryId, long groupId,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().findByGroupId_PrevAndNext(
			styleBookEntryId, groupId, orderByComparator);
	}

	/**
	 * Removes all the style book entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	 * Returns the number of style book entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching style book entries
	 */
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	 * Returns all the style book entries where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @return the matching style book entries
	 */
	public static List<StyleBookEntry> findByGroupId_Head(
		long groupId, boolean head) {

		return getPersistence().findByGroupId_Head(groupId, head);
	}

	/**
	 * Returns a range of all the style book entries where groupId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	public static List<StyleBookEntry> findByGroupId_Head(
		long groupId, boolean head, int start, int end) {

		return getPersistence().findByGroupId_Head(groupId, head, start, end);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	public static List<StyleBookEntry> findByGroupId_Head(
		long groupId, boolean head, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return getPersistence().findByGroupId_Head(
			groupId, head, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	public static List<StyleBookEntry> findByGroupId_Head(
		long groupId, boolean head, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId_Head(
			groupId, head, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public static StyleBookEntry findByGroupId_Head_First(
			long groupId, boolean head,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().findByGroupId_Head_First(
			groupId, head, orderByComparator);
	}

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public static StyleBookEntry fetchByGroupId_Head_First(
		long groupId, boolean head,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return getPersistence().fetchByGroupId_Head_First(
			groupId, head, orderByComparator);
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public static StyleBookEntry findByGroupId_Head_Last(
			long groupId, boolean head,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().findByGroupId_Head_Last(
			groupId, head, orderByComparator);
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public static StyleBookEntry fetchByGroupId_Head_Last(
		long groupId, boolean head,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return getPersistence().fetchByGroupId_Head_Last(
			groupId, head, orderByComparator);
	}

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	public static StyleBookEntry[] findByGroupId_Head_PrevAndNext(
			long styleBookEntryId, long groupId, boolean head,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().findByGroupId_Head_PrevAndNext(
			styleBookEntryId, groupId, head, orderByComparator);
	}

	/**
	 * Removes all the style book entries where groupId = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 */
	public static void removeByGroupId_Head(long groupId, boolean head) {
		getPersistence().removeByGroupId_Head(groupId, head);
	}

	/**
	 * Returns the number of style book entries where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @return the number of matching style book entries
	 */
	public static int countByGroupId_Head(long groupId, boolean head) {
		return getPersistence().countByGroupId_Head(groupId, head);
	}

	/**
	 * Returns all the style book entries where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @return the matching style book entries
	 */
	public static List<StyleBookEntry> findByG_D(
		long groupId, boolean defaultStyleBookEntry) {

		return getPersistence().findByG_D(groupId, defaultStyleBookEntry);
	}

	/**
	 * Returns a range of all the style book entries where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	public static List<StyleBookEntry> findByG_D(
		long groupId, boolean defaultStyleBookEntry, int start, int end) {

		return getPersistence().findByG_D(
			groupId, defaultStyleBookEntry, start, end);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	public static List<StyleBookEntry> findByG_D(
		long groupId, boolean defaultStyleBookEntry, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return getPersistence().findByG_D(
			groupId, defaultStyleBookEntry, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	public static List<StyleBookEntry> findByG_D(
		long groupId, boolean defaultStyleBookEntry, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_D(
			groupId, defaultStyleBookEntry, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public static StyleBookEntry findByG_D_First(
			long groupId, boolean defaultStyleBookEntry,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().findByG_D_First(
			groupId, defaultStyleBookEntry, orderByComparator);
	}

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public static StyleBookEntry fetchByG_D_First(
		long groupId, boolean defaultStyleBookEntry,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return getPersistence().fetchByG_D_First(
			groupId, defaultStyleBookEntry, orderByComparator);
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public static StyleBookEntry findByG_D_Last(
			long groupId, boolean defaultStyleBookEntry,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().findByG_D_Last(
			groupId, defaultStyleBookEntry, orderByComparator);
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public static StyleBookEntry fetchByG_D_Last(
		long groupId, boolean defaultStyleBookEntry,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return getPersistence().fetchByG_D_Last(
			groupId, defaultStyleBookEntry, orderByComparator);
	}

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	public static StyleBookEntry[] findByG_D_PrevAndNext(
			long styleBookEntryId, long groupId, boolean defaultStyleBookEntry,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().findByG_D_PrevAndNext(
			styleBookEntryId, groupId, defaultStyleBookEntry,
			orderByComparator);
	}

	/**
	 * Removes all the style book entries where groupId = &#63; and defaultStyleBookEntry = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 */
	public static void removeByG_D(
		long groupId, boolean defaultStyleBookEntry) {

		getPersistence().removeByG_D(groupId, defaultStyleBookEntry);
	}

	/**
	 * Returns the number of style book entries where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @return the number of matching style book entries
	 */
	public static int countByG_D(long groupId, boolean defaultStyleBookEntry) {
		return getPersistence().countByG_D(groupId, defaultStyleBookEntry);
	}

	/**
	 * Returns all the style book entries where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 * @return the matching style book entries
	 */
	public static List<StyleBookEntry> findByG_D_Head(
		long groupId, boolean defaultStyleBookEntry, boolean head) {

		return getPersistence().findByG_D_Head(
			groupId, defaultStyleBookEntry, head);
	}

	/**
	 * Returns a range of all the style book entries where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	public static List<StyleBookEntry> findByG_D_Head(
		long groupId, boolean defaultStyleBookEntry, boolean head, int start,
		int end) {

		return getPersistence().findByG_D_Head(
			groupId, defaultStyleBookEntry, head, start, end);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	public static List<StyleBookEntry> findByG_D_Head(
		long groupId, boolean defaultStyleBookEntry, boolean head, int start,
		int end, OrderByComparator<StyleBookEntry> orderByComparator) {

		return getPersistence().findByG_D_Head(
			groupId, defaultStyleBookEntry, head, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	public static List<StyleBookEntry> findByG_D_Head(
		long groupId, boolean defaultStyleBookEntry, boolean head, int start,
		int end, OrderByComparator<StyleBookEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_D_Head(
			groupId, defaultStyleBookEntry, head, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public static StyleBookEntry findByG_D_Head_First(
			long groupId, boolean defaultStyleBookEntry, boolean head,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().findByG_D_Head_First(
			groupId, defaultStyleBookEntry, head, orderByComparator);
	}

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public static StyleBookEntry fetchByG_D_Head_First(
		long groupId, boolean defaultStyleBookEntry, boolean head,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return getPersistence().fetchByG_D_Head_First(
			groupId, defaultStyleBookEntry, head, orderByComparator);
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public static StyleBookEntry findByG_D_Head_Last(
			long groupId, boolean defaultStyleBookEntry, boolean head,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().findByG_D_Head_Last(
			groupId, defaultStyleBookEntry, head, orderByComparator);
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public static StyleBookEntry fetchByG_D_Head_Last(
		long groupId, boolean defaultStyleBookEntry, boolean head,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return getPersistence().fetchByG_D_Head_Last(
			groupId, defaultStyleBookEntry, head, orderByComparator);
	}

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	public static StyleBookEntry[] findByG_D_Head_PrevAndNext(
			long styleBookEntryId, long groupId, boolean defaultStyleBookEntry,
			boolean head, OrderByComparator<StyleBookEntry> orderByComparator)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().findByG_D_Head_PrevAndNext(
			styleBookEntryId, groupId, defaultStyleBookEntry, head,
			orderByComparator);
	}

	/**
	 * Removes all the style book entries where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 */
	public static void removeByG_D_Head(
		long groupId, boolean defaultStyleBookEntry, boolean head) {

		getPersistence().removeByG_D_Head(groupId, defaultStyleBookEntry, head);
	}

	/**
	 * Returns the number of style book entries where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 * @return the number of matching style book entries
	 */
	public static int countByG_D_Head(
		long groupId, boolean defaultStyleBookEntry, boolean head) {

		return getPersistence().countByG_D_Head(
			groupId, defaultStyleBookEntry, head);
	}

	/**
	 * Returns all the style book entries where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching style book entries
	 */
	public static List<StyleBookEntry> findByG_LikeN(
		long groupId, String name) {

		return getPersistence().findByG_LikeN(groupId, name);
	}

	/**
	 * Returns a range of all the style book entries where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	public static List<StyleBookEntry> findByG_LikeN(
		long groupId, String name, int start, int end) {

		return getPersistence().findByG_LikeN(groupId, name, start, end);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	public static List<StyleBookEntry> findByG_LikeN(
		long groupId, String name, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return getPersistence().findByG_LikeN(
			groupId, name, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	public static List<StyleBookEntry> findByG_LikeN(
		long groupId, String name, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_LikeN(
			groupId, name, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public static StyleBookEntry findByG_LikeN_First(
			long groupId, String name,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().findByG_LikeN_First(
			groupId, name, orderByComparator);
	}

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public static StyleBookEntry fetchByG_LikeN_First(
		long groupId, String name,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return getPersistence().fetchByG_LikeN_First(
			groupId, name, orderByComparator);
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public static StyleBookEntry findByG_LikeN_Last(
			long groupId, String name,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().findByG_LikeN_Last(
			groupId, name, orderByComparator);
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public static StyleBookEntry fetchByG_LikeN_Last(
		long groupId, String name,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return getPersistence().fetchByG_LikeN_Last(
			groupId, name, orderByComparator);
	}

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	public static StyleBookEntry[] findByG_LikeN_PrevAndNext(
			long styleBookEntryId, long groupId, String name,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().findByG_LikeN_PrevAndNext(
			styleBookEntryId, groupId, name, orderByComparator);
	}

	/**
	 * Removes all the style book entries where groupId = &#63; and name LIKE &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 */
	public static void removeByG_LikeN(long groupId, String name) {
		getPersistence().removeByG_LikeN(groupId, name);
	}

	/**
	 * Returns the number of style book entries where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching style book entries
	 */
	public static int countByG_LikeN(long groupId, String name) {
		return getPersistence().countByG_LikeN(groupId, name);
	}

	/**
	 * Returns all the style book entries where groupId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 * @return the matching style book entries
	 */
	public static List<StyleBookEntry> findByG_LikeN_Head(
		long groupId, String name, boolean head) {

		return getPersistence().findByG_LikeN_Head(groupId, name, head);
	}

	/**
	 * Returns a range of all the style book entries where groupId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	public static List<StyleBookEntry> findByG_LikeN_Head(
		long groupId, String name, boolean head, int start, int end) {

		return getPersistence().findByG_LikeN_Head(
			groupId, name, head, start, end);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	public static List<StyleBookEntry> findByG_LikeN_Head(
		long groupId, String name, boolean head, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return getPersistence().findByG_LikeN_Head(
			groupId, name, head, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	public static List<StyleBookEntry> findByG_LikeN_Head(
		long groupId, String name, boolean head, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_LikeN_Head(
			groupId, name, head, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public static StyleBookEntry findByG_LikeN_Head_First(
			long groupId, String name, boolean head,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().findByG_LikeN_Head_First(
			groupId, name, head, orderByComparator);
	}

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public static StyleBookEntry fetchByG_LikeN_Head_First(
		long groupId, String name, boolean head,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return getPersistence().fetchByG_LikeN_Head_First(
			groupId, name, head, orderByComparator);
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public static StyleBookEntry findByG_LikeN_Head_Last(
			long groupId, String name, boolean head,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().findByG_LikeN_Head_Last(
			groupId, name, head, orderByComparator);
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public static StyleBookEntry fetchByG_LikeN_Head_Last(
		long groupId, String name, boolean head,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return getPersistence().fetchByG_LikeN_Head_Last(
			groupId, name, head, orderByComparator);
	}

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where groupId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	public static StyleBookEntry[] findByG_LikeN_Head_PrevAndNext(
			long styleBookEntryId, long groupId, String name, boolean head,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().findByG_LikeN_Head_PrevAndNext(
			styleBookEntryId, groupId, name, head, orderByComparator);
	}

	/**
	 * Removes all the style book entries where groupId = &#63; and name LIKE &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 */
	public static void removeByG_LikeN_Head(
		long groupId, String name, boolean head) {

		getPersistence().removeByG_LikeN_Head(groupId, name, head);
	}

	/**
	 * Returns the number of style book entries where groupId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 * @return the number of matching style book entries
	 */
	public static int countByG_LikeN_Head(
		long groupId, String name, boolean head) {

		return getPersistence().countByG_LikeN_Head(groupId, name, head);
	}

	/**
	 * Returns all the style book entries where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @return the matching style book entries
	 */
	public static List<StyleBookEntry> findByG_SBEK(
		long groupId, String styleBookEntryKey) {

		return getPersistence().findByG_SBEK(groupId, styleBookEntryKey);
	}

	/**
	 * Returns a range of all the style book entries where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	public static List<StyleBookEntry> findByG_SBEK(
		long groupId, String styleBookEntryKey, int start, int end) {

		return getPersistence().findByG_SBEK(
			groupId, styleBookEntryKey, start, end);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	public static List<StyleBookEntry> findByG_SBEK(
		long groupId, String styleBookEntryKey, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return getPersistence().findByG_SBEK(
			groupId, styleBookEntryKey, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	public static List<StyleBookEntry> findByG_SBEK(
		long groupId, String styleBookEntryKey, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_SBEK(
			groupId, styleBookEntryKey, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public static StyleBookEntry findByG_SBEK_First(
			long groupId, String styleBookEntryKey,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().findByG_SBEK_First(
			groupId, styleBookEntryKey, orderByComparator);
	}

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public static StyleBookEntry fetchByG_SBEK_First(
		long groupId, String styleBookEntryKey,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return getPersistence().fetchByG_SBEK_First(
			groupId, styleBookEntryKey, orderByComparator);
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public static StyleBookEntry findByG_SBEK_Last(
			long groupId, String styleBookEntryKey,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().findByG_SBEK_Last(
			groupId, styleBookEntryKey, orderByComparator);
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public static StyleBookEntry fetchByG_SBEK_Last(
		long groupId, String styleBookEntryKey,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return getPersistence().fetchByG_SBEK_Last(
			groupId, styleBookEntryKey, orderByComparator);
	}

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	public static StyleBookEntry[] findByG_SBEK_PrevAndNext(
			long styleBookEntryId, long groupId, String styleBookEntryKey,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().findByG_SBEK_PrevAndNext(
			styleBookEntryId, groupId, styleBookEntryKey, orderByComparator);
	}

	/**
	 * Removes all the style book entries where groupId = &#63; and styleBookEntryKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 */
	public static void removeByG_SBEK(long groupId, String styleBookEntryKey) {
		getPersistence().removeByG_SBEK(groupId, styleBookEntryKey);
	}

	/**
	 * Returns the number of style book entries where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @return the number of matching style book entries
	 */
	public static int countByG_SBEK(long groupId, String styleBookEntryKey) {
		return getPersistence().countByG_SBEK(groupId, styleBookEntryKey);
	}

	/**
	 * Returns the style book entry where groupId = &#63; and styleBookEntryKey = &#63; and head = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param head the head
	 * @return the matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public static StyleBookEntry findByG_SBEK_Head(
			long groupId, String styleBookEntryKey, boolean head)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().findByG_SBEK_Head(
			groupId, styleBookEntryKey, head);
	}

	/**
	 * Returns the style book entry where groupId = &#63; and styleBookEntryKey = &#63; and head = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param head the head
	 * @return the matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public static StyleBookEntry fetchByG_SBEK_Head(
		long groupId, String styleBookEntryKey, boolean head) {

		return getPersistence().fetchByG_SBEK_Head(
			groupId, styleBookEntryKey, head);
	}

	/**
	 * Returns the style book entry where groupId = &#63; and styleBookEntryKey = &#63; and head = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param head the head
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public static StyleBookEntry fetchByG_SBEK_Head(
		long groupId, String styleBookEntryKey, boolean head,
		boolean useFinderCache) {

		return getPersistence().fetchByG_SBEK_Head(
			groupId, styleBookEntryKey, head, useFinderCache);
	}

	/**
	 * Removes the style book entry where groupId = &#63; and styleBookEntryKey = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param head the head
	 * @return the style book entry that was removed
	 */
	public static StyleBookEntry removeByG_SBEK_Head(
			long groupId, String styleBookEntryKey, boolean head)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().removeByG_SBEK_Head(
			groupId, styleBookEntryKey, head);
	}

	/**
	 * Returns the number of style book entries where groupId = &#63; and styleBookEntryKey = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param head the head
	 * @return the number of matching style book entries
	 */
	public static int countByG_SBEK_Head(
		long groupId, String styleBookEntryKey, boolean head) {

		return getPersistence().countByG_SBEK_Head(
			groupId, styleBookEntryKey, head);
	}

	/**
	 * Returns the style book entry where headId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param headId the head ID
	 * @return the matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public static StyleBookEntry findByHeadId(long headId)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().findByHeadId(headId);
	}

	/**
	 * Returns the style book entry where headId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param headId the head ID
	 * @return the matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public static StyleBookEntry fetchByHeadId(long headId) {
		return getPersistence().fetchByHeadId(headId);
	}

	/**
	 * Returns the style book entry where headId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param headId the head ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public static StyleBookEntry fetchByHeadId(
		long headId, boolean useFinderCache) {

		return getPersistence().fetchByHeadId(headId, useFinderCache);
	}

	/**
	 * Removes the style book entry where headId = &#63; from the database.
	 *
	 * @param headId the head ID
	 * @return the style book entry that was removed
	 */
	public static StyleBookEntry removeByHeadId(long headId)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().removeByHeadId(headId);
	}

	/**
	 * Returns the number of style book entries where headId = &#63;.
	 *
	 * @param headId the head ID
	 * @return the number of matching style book entries
	 */
	public static int countByHeadId(long headId) {
		return getPersistence().countByHeadId(headId);
	}

	/**
	 * Caches the style book entry in the entity cache if it is enabled.
	 *
	 * @param styleBookEntry the style book entry
	 */
	public static void cacheResult(StyleBookEntry styleBookEntry) {
		getPersistence().cacheResult(styleBookEntry);
	}

	/**
	 * Caches the style book entries in the entity cache if it is enabled.
	 *
	 * @param styleBookEntries the style book entries
	 */
	public static void cacheResult(List<StyleBookEntry> styleBookEntries) {
		getPersistence().cacheResult(styleBookEntries);
	}

	/**
	 * Creates a new style book entry with the primary key. Does not add the style book entry to the database.
	 *
	 * @param styleBookEntryId the primary key for the new style book entry
	 * @return the new style book entry
	 */
	public static StyleBookEntry create(long styleBookEntryId) {
		return getPersistence().create(styleBookEntryId);
	}

	/**
	 * Removes the style book entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param styleBookEntryId the primary key of the style book entry
	 * @return the style book entry that was removed
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	public static StyleBookEntry remove(long styleBookEntryId)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().remove(styleBookEntryId);
	}

	public static StyleBookEntry updateImpl(StyleBookEntry styleBookEntry) {
		return getPersistence().updateImpl(styleBookEntry);
	}

	/**
	 * Returns the style book entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param styleBookEntryId the primary key of the style book entry
	 * @return the style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	public static StyleBookEntry findByPrimaryKey(long styleBookEntryId)
		throws com.liferay.style.book.exception.NoSuchEntryException {

		return getPersistence().findByPrimaryKey(styleBookEntryId);
	}

	/**
	 * Returns the style book entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param styleBookEntryId the primary key of the style book entry
	 * @return the style book entry, or <code>null</code> if a style book entry with the primary key could not be found
	 */
	public static StyleBookEntry fetchByPrimaryKey(long styleBookEntryId) {
		return getPersistence().fetchByPrimaryKey(styleBookEntryId);
	}

	/**
	 * Returns all the style book entries.
	 *
	 * @return the style book entries
	 */
	public static List<StyleBookEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the style book entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of style book entries
	 */
	public static List<StyleBookEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the style book entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of style book entries
	 */
	public static List<StyleBookEntry> findAll(
		int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the style book entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of style book entries
	 */
	public static List<StyleBookEntry> findAll(
		int start, int end, OrderByComparator<StyleBookEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the style book entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of style book entries.
	 *
	 * @return the number of style book entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static StyleBookEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(StyleBookEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile StyleBookEntryPersistence _persistence;

}
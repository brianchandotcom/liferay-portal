/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.persistence;

import com.liferay.object.model.ObjectFolderItem;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the object folder item service. This utility wraps <code>com.liferay.object.service.persistence.impl.ObjectFolderItemPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see ObjectFolderItemPersistence
 * @generated
 */
public class ObjectFolderItemUtil {

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
	public static void clearCache(ObjectFolderItem objectFolderItem) {
		getPersistence().clearCache(objectFolderItem);
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
	public static Map<Serializable, ObjectFolderItem> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ObjectFolderItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ObjectFolderItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ObjectFolderItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ObjectFolderItem> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ObjectFolderItem update(ObjectFolderItem objectFolderItem) {
		return getPersistence().update(objectFolderItem);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ObjectFolderItem update(
		ObjectFolderItem objectFolderItem, ServiceContext serviceContext) {

		return getPersistence().update(objectFolderItem, serviceContext);
	}

	/**
	 * Returns all the object folder items where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object folder items
	 */
	public static List<ObjectFolderItem> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the object folder items where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @return the range of matching object folder items
	 */
	public static List<ObjectFolderItem> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the object folder items where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object folder items
	 */
	public static List<ObjectFolderItem> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectFolderItem> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object folder items where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object folder items
	 */
	public static List<ObjectFolderItem> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectFolderItem> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object folder item in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object folder item
	 * @throws NoSuchObjectFolderItemException if a matching object folder item could not be found
	 */
	public static ObjectFolderItem findByUuid_First(
			String uuid, OrderByComparator<ObjectFolderItem> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFolderItemException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first object folder item in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object folder item, or <code>null</code> if a matching object folder item could not be found
	 */
	public static ObjectFolderItem fetchByUuid_First(
		String uuid, OrderByComparator<ObjectFolderItem> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last object folder item in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object folder item
	 * @throws NoSuchObjectFolderItemException if a matching object folder item could not be found
	 */
	public static ObjectFolderItem findByUuid_Last(
			String uuid, OrderByComparator<ObjectFolderItem> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFolderItemException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last object folder item in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object folder item, or <code>null</code> if a matching object folder item could not be found
	 */
	public static ObjectFolderItem fetchByUuid_Last(
		String uuid, OrderByComparator<ObjectFolderItem> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the object folder items before and after the current object folder item in the ordered set where uuid = &#63;.
	 *
	 * @param objectFolderItemId the primary key of the current object folder item
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object folder item
	 * @throws NoSuchObjectFolderItemException if a object folder item with the primary key could not be found
	 */
	public static ObjectFolderItem[] findByUuid_PrevAndNext(
			long objectFolderItemId, String uuid,
			OrderByComparator<ObjectFolderItem> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFolderItemException {

		return getPersistence().findByUuid_PrevAndNext(
			objectFolderItemId, uuid, orderByComparator);
	}

	/**
	 * Removes all the object folder items where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of object folder items where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object folder items
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the object folder items where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object folder items
	 */
	public static List<ObjectFolderItem> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the object folder items where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @return the range of matching object folder items
	 */
	public static List<ObjectFolderItem> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the object folder items where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object folder items
	 */
	public static List<ObjectFolderItem> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectFolderItem> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object folder items where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object folder items
	 */
	public static List<ObjectFolderItem> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectFolderItem> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object folder item in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object folder item
	 * @throws NoSuchObjectFolderItemException if a matching object folder item could not be found
	 */
	public static ObjectFolderItem findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ObjectFolderItem> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFolderItemException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first object folder item in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object folder item, or <code>null</code> if a matching object folder item could not be found
	 */
	public static ObjectFolderItem fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ObjectFolderItem> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last object folder item in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object folder item
	 * @throws NoSuchObjectFolderItemException if a matching object folder item could not be found
	 */
	public static ObjectFolderItem findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ObjectFolderItem> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFolderItemException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last object folder item in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object folder item, or <code>null</code> if a matching object folder item could not be found
	 */
	public static ObjectFolderItem fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ObjectFolderItem> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the object folder items before and after the current object folder item in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectFolderItemId the primary key of the current object folder item
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object folder item
	 * @throws NoSuchObjectFolderItemException if a object folder item with the primary key could not be found
	 */
	public static ObjectFolderItem[] findByUuid_C_PrevAndNext(
			long objectFolderItemId, String uuid, long companyId,
			OrderByComparator<ObjectFolderItem> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFolderItemException {

		return getPersistence().findByUuid_C_PrevAndNext(
			objectFolderItemId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the object folder items where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of object folder items where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object folder items
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the object folder items where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @return the matching object folder items
	 */
	public static List<ObjectFolderItem> findByObjectDefinitionId(
		long objectDefinitionId) {

		return getPersistence().findByObjectDefinitionId(objectDefinitionId);
	}

	/**
	 * Returns a range of all the object folder items where objectDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @return the range of matching object folder items
	 */
	public static List<ObjectFolderItem> findByObjectDefinitionId(
		long objectDefinitionId, int start, int end) {

		return getPersistence().findByObjectDefinitionId(
			objectDefinitionId, start, end);
	}

	/**
	 * Returns an ordered range of all the object folder items where objectDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object folder items
	 */
	public static List<ObjectFolderItem> findByObjectDefinitionId(
		long objectDefinitionId, int start, int end,
		OrderByComparator<ObjectFolderItem> orderByComparator) {

		return getPersistence().findByObjectDefinitionId(
			objectDefinitionId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object folder items where objectDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object folder items
	 */
	public static List<ObjectFolderItem> findByObjectDefinitionId(
		long objectDefinitionId, int start, int end,
		OrderByComparator<ObjectFolderItem> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByObjectDefinitionId(
			objectDefinitionId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object folder item in the ordered set where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object folder item
	 * @throws NoSuchObjectFolderItemException if a matching object folder item could not be found
	 */
	public static ObjectFolderItem findByObjectDefinitionId_First(
			long objectDefinitionId,
			OrderByComparator<ObjectFolderItem> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFolderItemException {

		return getPersistence().findByObjectDefinitionId_First(
			objectDefinitionId, orderByComparator);
	}

	/**
	 * Returns the first object folder item in the ordered set where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object folder item, or <code>null</code> if a matching object folder item could not be found
	 */
	public static ObjectFolderItem fetchByObjectDefinitionId_First(
		long objectDefinitionId,
		OrderByComparator<ObjectFolderItem> orderByComparator) {

		return getPersistence().fetchByObjectDefinitionId_First(
			objectDefinitionId, orderByComparator);
	}

	/**
	 * Returns the last object folder item in the ordered set where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object folder item
	 * @throws NoSuchObjectFolderItemException if a matching object folder item could not be found
	 */
	public static ObjectFolderItem findByObjectDefinitionId_Last(
			long objectDefinitionId,
			OrderByComparator<ObjectFolderItem> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFolderItemException {

		return getPersistence().findByObjectDefinitionId_Last(
			objectDefinitionId, orderByComparator);
	}

	/**
	 * Returns the last object folder item in the ordered set where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object folder item, or <code>null</code> if a matching object folder item could not be found
	 */
	public static ObjectFolderItem fetchByObjectDefinitionId_Last(
		long objectDefinitionId,
		OrderByComparator<ObjectFolderItem> orderByComparator) {

		return getPersistence().fetchByObjectDefinitionId_Last(
			objectDefinitionId, orderByComparator);
	}

	/**
	 * Returns the object folder items before and after the current object folder item in the ordered set where objectDefinitionId = &#63;.
	 *
	 * @param objectFolderItemId the primary key of the current object folder item
	 * @param objectDefinitionId the object definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object folder item
	 * @throws NoSuchObjectFolderItemException if a object folder item with the primary key could not be found
	 */
	public static ObjectFolderItem[] findByObjectDefinitionId_PrevAndNext(
			long objectFolderItemId, long objectDefinitionId,
			OrderByComparator<ObjectFolderItem> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFolderItemException {

		return getPersistence().findByObjectDefinitionId_PrevAndNext(
			objectFolderItemId, objectDefinitionId, orderByComparator);
	}

	/**
	 * Removes all the object folder items where objectDefinitionId = &#63; from the database.
	 *
	 * @param objectDefinitionId the object definition ID
	 */
	public static void removeByObjectDefinitionId(long objectDefinitionId) {
		getPersistence().removeByObjectDefinitionId(objectDefinitionId);
	}

	/**
	 * Returns the number of object folder items where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @return the number of matching object folder items
	 */
	public static int countByObjectDefinitionId(long objectDefinitionId) {
		return getPersistence().countByObjectDefinitionId(objectDefinitionId);
	}

	/**
	 * Returns all the object folder items where objectFolderId = &#63;.
	 *
	 * @param objectFolderId the object folder ID
	 * @return the matching object folder items
	 */
	public static List<ObjectFolderItem> findByObjectFolderId(
		long objectFolderId) {

		return getPersistence().findByObjectFolderId(objectFolderId);
	}

	/**
	 * Returns a range of all the object folder items where objectFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param objectFolderId the object folder ID
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @return the range of matching object folder items
	 */
	public static List<ObjectFolderItem> findByObjectFolderId(
		long objectFolderId, int start, int end) {

		return getPersistence().findByObjectFolderId(
			objectFolderId, start, end);
	}

	/**
	 * Returns an ordered range of all the object folder items where objectFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param objectFolderId the object folder ID
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object folder items
	 */
	public static List<ObjectFolderItem> findByObjectFolderId(
		long objectFolderId, int start, int end,
		OrderByComparator<ObjectFolderItem> orderByComparator) {

		return getPersistence().findByObjectFolderId(
			objectFolderId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object folder items where objectFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param objectFolderId the object folder ID
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object folder items
	 */
	public static List<ObjectFolderItem> findByObjectFolderId(
		long objectFolderId, int start, int end,
		OrderByComparator<ObjectFolderItem> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByObjectFolderId(
			objectFolderId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object folder item in the ordered set where objectFolderId = &#63;.
	 *
	 * @param objectFolderId the object folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object folder item
	 * @throws NoSuchObjectFolderItemException if a matching object folder item could not be found
	 */
	public static ObjectFolderItem findByObjectFolderId_First(
			long objectFolderId,
			OrderByComparator<ObjectFolderItem> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFolderItemException {

		return getPersistence().findByObjectFolderId_First(
			objectFolderId, orderByComparator);
	}

	/**
	 * Returns the first object folder item in the ordered set where objectFolderId = &#63;.
	 *
	 * @param objectFolderId the object folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object folder item, or <code>null</code> if a matching object folder item could not be found
	 */
	public static ObjectFolderItem fetchByObjectFolderId_First(
		long objectFolderId,
		OrderByComparator<ObjectFolderItem> orderByComparator) {

		return getPersistence().fetchByObjectFolderId_First(
			objectFolderId, orderByComparator);
	}

	/**
	 * Returns the last object folder item in the ordered set where objectFolderId = &#63;.
	 *
	 * @param objectFolderId the object folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object folder item
	 * @throws NoSuchObjectFolderItemException if a matching object folder item could not be found
	 */
	public static ObjectFolderItem findByObjectFolderId_Last(
			long objectFolderId,
			OrderByComparator<ObjectFolderItem> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFolderItemException {

		return getPersistence().findByObjectFolderId_Last(
			objectFolderId, orderByComparator);
	}

	/**
	 * Returns the last object folder item in the ordered set where objectFolderId = &#63;.
	 *
	 * @param objectFolderId the object folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object folder item, or <code>null</code> if a matching object folder item could not be found
	 */
	public static ObjectFolderItem fetchByObjectFolderId_Last(
		long objectFolderId,
		OrderByComparator<ObjectFolderItem> orderByComparator) {

		return getPersistence().fetchByObjectFolderId_Last(
			objectFolderId, orderByComparator);
	}

	/**
	 * Returns the object folder items before and after the current object folder item in the ordered set where objectFolderId = &#63;.
	 *
	 * @param objectFolderItemId the primary key of the current object folder item
	 * @param objectFolderId the object folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object folder item
	 * @throws NoSuchObjectFolderItemException if a object folder item with the primary key could not be found
	 */
	public static ObjectFolderItem[] findByObjectFolderId_PrevAndNext(
			long objectFolderItemId, long objectFolderId,
			OrderByComparator<ObjectFolderItem> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFolderItemException {

		return getPersistence().findByObjectFolderId_PrevAndNext(
			objectFolderItemId, objectFolderId, orderByComparator);
	}

	/**
	 * Removes all the object folder items where objectFolderId = &#63; from the database.
	 *
	 * @param objectFolderId the object folder ID
	 */
	public static void removeByObjectFolderId(long objectFolderId) {
		getPersistence().removeByObjectFolderId(objectFolderId);
	}

	/**
	 * Returns the number of object folder items where objectFolderId = &#63;.
	 *
	 * @param objectFolderId the object folder ID
	 * @return the number of matching object folder items
	 */
	public static int countByObjectFolderId(long objectFolderId) {
		return getPersistence().countByObjectFolderId(objectFolderId);
	}

	/**
	 * Returns the object folder item where objectDefinitionId = &#63; and objectFolderId = &#63; or throws a <code>NoSuchObjectFolderItemException</code> if it could not be found.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param objectFolderId the object folder ID
	 * @return the matching object folder item
	 * @throws NoSuchObjectFolderItemException if a matching object folder item could not be found
	 */
	public static ObjectFolderItem findByODI_OFI(
			long objectDefinitionId, long objectFolderId)
		throws com.liferay.object.exception.NoSuchObjectFolderItemException {

		return getPersistence().findByODI_OFI(
			objectDefinitionId, objectFolderId);
	}

	/**
	 * Returns the object folder item where objectDefinitionId = &#63; and objectFolderId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param objectFolderId the object folder ID
	 * @return the matching object folder item, or <code>null</code> if a matching object folder item could not be found
	 */
	public static ObjectFolderItem fetchByODI_OFI(
		long objectDefinitionId, long objectFolderId) {

		return getPersistence().fetchByODI_OFI(
			objectDefinitionId, objectFolderId);
	}

	/**
	 * Returns the object folder item where objectDefinitionId = &#63; and objectFolderId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param objectFolderId the object folder ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching object folder item, or <code>null</code> if a matching object folder item could not be found
	 */
	public static ObjectFolderItem fetchByODI_OFI(
		long objectDefinitionId, long objectFolderId, boolean useFinderCache) {

		return getPersistence().fetchByODI_OFI(
			objectDefinitionId, objectFolderId, useFinderCache);
	}

	/**
	 * Removes the object folder item where objectDefinitionId = &#63; and objectFolderId = &#63; from the database.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param objectFolderId the object folder ID
	 * @return the object folder item that was removed
	 */
	public static ObjectFolderItem removeByODI_OFI(
			long objectDefinitionId, long objectFolderId)
		throws com.liferay.object.exception.NoSuchObjectFolderItemException {

		return getPersistence().removeByODI_OFI(
			objectDefinitionId, objectFolderId);
	}

	/**
	 * Returns the number of object folder items where objectDefinitionId = &#63; and objectFolderId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param objectFolderId the object folder ID
	 * @return the number of matching object folder items
	 */
	public static int countByODI_OFI(
		long objectDefinitionId, long objectFolderId) {

		return getPersistence().countByODI_OFI(
			objectDefinitionId, objectFolderId);
	}

	/**
	 * Caches the object folder item in the entity cache if it is enabled.
	 *
	 * @param objectFolderItem the object folder item
	 */
	public static void cacheResult(ObjectFolderItem objectFolderItem) {
		getPersistence().cacheResult(objectFolderItem);
	}

	/**
	 * Caches the object folder items in the entity cache if it is enabled.
	 *
	 * @param objectFolderItems the object folder items
	 */
	public static void cacheResult(List<ObjectFolderItem> objectFolderItems) {
		getPersistence().cacheResult(objectFolderItems);
	}

	/**
	 * Creates a new object folder item with the primary key. Does not add the object folder item to the database.
	 *
	 * @param objectFolderItemId the primary key for the new object folder item
	 * @return the new object folder item
	 */
	public static ObjectFolderItem create(long objectFolderItemId) {
		return getPersistence().create(objectFolderItemId);
	}

	/**
	 * Removes the object folder item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectFolderItemId the primary key of the object folder item
	 * @return the object folder item that was removed
	 * @throws NoSuchObjectFolderItemException if a object folder item with the primary key could not be found
	 */
	public static ObjectFolderItem remove(long objectFolderItemId)
		throws com.liferay.object.exception.NoSuchObjectFolderItemException {

		return getPersistence().remove(objectFolderItemId);
	}

	public static ObjectFolderItem updateImpl(
		ObjectFolderItem objectFolderItem) {

		return getPersistence().updateImpl(objectFolderItem);
	}

	/**
	 * Returns the object folder item with the primary key or throws a <code>NoSuchObjectFolderItemException</code> if it could not be found.
	 *
	 * @param objectFolderItemId the primary key of the object folder item
	 * @return the object folder item
	 * @throws NoSuchObjectFolderItemException if a object folder item with the primary key could not be found
	 */
	public static ObjectFolderItem findByPrimaryKey(long objectFolderItemId)
		throws com.liferay.object.exception.NoSuchObjectFolderItemException {

		return getPersistence().findByPrimaryKey(objectFolderItemId);
	}

	/**
	 * Returns the object folder item with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectFolderItemId the primary key of the object folder item
	 * @return the object folder item, or <code>null</code> if a object folder item with the primary key could not be found
	 */
	public static ObjectFolderItem fetchByPrimaryKey(long objectFolderItemId) {
		return getPersistence().fetchByPrimaryKey(objectFolderItemId);
	}

	/**
	 * Returns all the object folder items.
	 *
	 * @return the object folder items
	 */
	public static List<ObjectFolderItem> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the object folder items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @return the range of object folder items
	 */
	public static List<ObjectFolderItem> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the object folder items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object folder items
	 */
	public static List<ObjectFolderItem> findAll(
		int start, int end,
		OrderByComparator<ObjectFolderItem> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object folder items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object folder items
	 */
	public static List<ObjectFolderItem> findAll(
		int start, int end,
		OrderByComparator<ObjectFolderItem> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the object folder items from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of object folder items.
	 *
	 * @return the number of object folder items
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ObjectFolderItemPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(ObjectFolderItemPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile ObjectFolderItemPersistence _persistence;

}
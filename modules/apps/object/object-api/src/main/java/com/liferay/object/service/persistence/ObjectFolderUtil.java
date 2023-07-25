/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.persistence;

import com.liferay.object.model.ObjectFolder;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the object folder service. This utility wraps <code>com.liferay.object.service.persistence.impl.ObjectFolderPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see ObjectFolderPersistence
 * @generated
 */
public class ObjectFolderUtil {

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
	public static void clearCache(ObjectFolder objectFolder) {
		getPersistence().clearCache(objectFolder);
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
	public static Map<Serializable, ObjectFolder> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ObjectFolder> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ObjectFolder> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ObjectFolder> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ObjectFolder> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ObjectFolder update(ObjectFolder objectFolder) {
		return getPersistence().update(objectFolder);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ObjectFolder update(
		ObjectFolder objectFolder, ServiceContext serviceContext) {

		return getPersistence().update(objectFolder, serviceContext);
	}

	/**
	 * Returns all the object folders where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object folders
	 */
	public static List<ObjectFolder> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the object folders where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object folders
	 * @param end the upper bound of the range of object folders (not inclusive)
	 * @return the range of matching object folders
	 */
	public static List<ObjectFolder> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the object folders where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object folders
	 * @param end the upper bound of the range of object folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object folders
	 */
	public static List<ObjectFolder> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectFolder> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object folders where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object folders
	 * @param end the upper bound of the range of object folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object folders
	 */
	public static List<ObjectFolder> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectFolder> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object folder in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object folder
	 * @throws NoSuchObjectFolderException if a matching object folder could not be found
	 */
	public static ObjectFolder findByUuid_First(
			String uuid, OrderByComparator<ObjectFolder> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFolderException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first object folder in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object folder, or <code>null</code> if a matching object folder could not be found
	 */
	public static ObjectFolder fetchByUuid_First(
		String uuid, OrderByComparator<ObjectFolder> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last object folder in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object folder
	 * @throws NoSuchObjectFolderException if a matching object folder could not be found
	 */
	public static ObjectFolder findByUuid_Last(
			String uuid, OrderByComparator<ObjectFolder> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFolderException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last object folder in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object folder, or <code>null</code> if a matching object folder could not be found
	 */
	public static ObjectFolder fetchByUuid_Last(
		String uuid, OrderByComparator<ObjectFolder> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the object folders before and after the current object folder in the ordered set where uuid = &#63;.
	 *
	 * @param objectFolderId the primary key of the current object folder
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object folder
	 * @throws NoSuchObjectFolderException if a object folder with the primary key could not be found
	 */
	public static ObjectFolder[] findByUuid_PrevAndNext(
			long objectFolderId, String uuid,
			OrderByComparator<ObjectFolder> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFolderException {

		return getPersistence().findByUuid_PrevAndNext(
			objectFolderId, uuid, orderByComparator);
	}

	/**
	 * Returns all the object folders that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object folders that the user has permission to view
	 */
	public static List<ObjectFolder> filterFindByUuid(String uuid) {
		return getPersistence().filterFindByUuid(uuid);
	}

	/**
	 * Returns a range of all the object folders that the user has permission to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object folders
	 * @param end the upper bound of the range of object folders (not inclusive)
	 * @return the range of matching object folders that the user has permission to view
	 */
	public static List<ObjectFolder> filterFindByUuid(
		String uuid, int start, int end) {

		return getPersistence().filterFindByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the object folders that the user has permissions to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object folders
	 * @param end the upper bound of the range of object folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object folders that the user has permission to view
	 */
	public static List<ObjectFolder> filterFindByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectFolder> orderByComparator) {

		return getPersistence().filterFindByUuid(
			uuid, start, end, orderByComparator);
	}

	/**
	 * Returns the object folders before and after the current object folder in the ordered set of object folders that the user has permission to view where uuid = &#63;.
	 *
	 * @param objectFolderId the primary key of the current object folder
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object folder
	 * @throws NoSuchObjectFolderException if a object folder with the primary key could not be found
	 */
	public static ObjectFolder[] filterFindByUuid_PrevAndNext(
			long objectFolderId, String uuid,
			OrderByComparator<ObjectFolder> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFolderException {

		return getPersistence().filterFindByUuid_PrevAndNext(
			objectFolderId, uuid, orderByComparator);
	}

	/**
	 * Removes all the object folders where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of object folders where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object folders
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the number of object folders that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object folders that the user has permission to view
	 */
	public static int filterCountByUuid(String uuid) {
		return getPersistence().filterCountByUuid(uuid);
	}

	/**
	 * Returns all the object folders where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object folders
	 */
	public static List<ObjectFolder> findByUuid_C(String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the object folders where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object folders
	 * @param end the upper bound of the range of object folders (not inclusive)
	 * @return the range of matching object folders
	 */
	public static List<ObjectFolder> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the object folders where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object folders
	 * @param end the upper bound of the range of object folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object folders
	 */
	public static List<ObjectFolder> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectFolder> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object folders where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object folders
	 * @param end the upper bound of the range of object folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object folders
	 */
	public static List<ObjectFolder> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectFolder> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object folder
	 * @throws NoSuchObjectFolderException if a matching object folder could not be found
	 */
	public static ObjectFolder findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ObjectFolder> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFolderException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first object folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object folder, or <code>null</code> if a matching object folder could not be found
	 */
	public static ObjectFolder fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ObjectFolder> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last object folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object folder
	 * @throws NoSuchObjectFolderException if a matching object folder could not be found
	 */
	public static ObjectFolder findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ObjectFolder> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFolderException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last object folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object folder, or <code>null</code> if a matching object folder could not be found
	 */
	public static ObjectFolder fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ObjectFolder> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the object folders before and after the current object folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectFolderId the primary key of the current object folder
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object folder
	 * @throws NoSuchObjectFolderException if a object folder with the primary key could not be found
	 */
	public static ObjectFolder[] findByUuid_C_PrevAndNext(
			long objectFolderId, String uuid, long companyId,
			OrderByComparator<ObjectFolder> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFolderException {

		return getPersistence().findByUuid_C_PrevAndNext(
			objectFolderId, uuid, companyId, orderByComparator);
	}

	/**
	 * Returns all the object folders that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object folders that the user has permission to view
	 */
	public static List<ObjectFolder> filterFindByUuid_C(
		String uuid, long companyId) {

		return getPersistence().filterFindByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the object folders that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object folders
	 * @param end the upper bound of the range of object folders (not inclusive)
	 * @return the range of matching object folders that the user has permission to view
	 */
	public static List<ObjectFolder> filterFindByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().filterFindByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the object folders that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object folders
	 * @param end the upper bound of the range of object folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object folders that the user has permission to view
	 */
	public static List<ObjectFolder> filterFindByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectFolder> orderByComparator) {

		return getPersistence().filterFindByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the object folders before and after the current object folder in the ordered set of object folders that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectFolderId the primary key of the current object folder
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object folder
	 * @throws NoSuchObjectFolderException if a object folder with the primary key could not be found
	 */
	public static ObjectFolder[] filterFindByUuid_C_PrevAndNext(
			long objectFolderId, String uuid, long companyId,
			OrderByComparator<ObjectFolder> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFolderException {

		return getPersistence().filterFindByUuid_C_PrevAndNext(
			objectFolderId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the object folders where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of object folders where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object folders
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of object folders that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object folders that the user has permission to view
	 */
	public static int filterCountByUuid_C(String uuid, long companyId) {
		return getPersistence().filterCountByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the object folders where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching object folders
	 */
	public static List<ObjectFolder> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the object folders where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object folders
	 * @param end the upper bound of the range of object folders (not inclusive)
	 * @return the range of matching object folders
	 */
	public static List<ObjectFolder> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the object folders where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object folders
	 * @param end the upper bound of the range of object folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object folders
	 */
	public static List<ObjectFolder> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<ObjectFolder> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object folders where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object folders
	 * @param end the upper bound of the range of object folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object folders
	 */
	public static List<ObjectFolder> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<ObjectFolder> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object folder in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object folder
	 * @throws NoSuchObjectFolderException if a matching object folder could not be found
	 */
	public static ObjectFolder findByCompanyId_First(
			long companyId, OrderByComparator<ObjectFolder> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFolderException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first object folder in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object folder, or <code>null</code> if a matching object folder could not be found
	 */
	public static ObjectFolder fetchByCompanyId_First(
		long companyId, OrderByComparator<ObjectFolder> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last object folder in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object folder
	 * @throws NoSuchObjectFolderException if a matching object folder could not be found
	 */
	public static ObjectFolder findByCompanyId_Last(
			long companyId, OrderByComparator<ObjectFolder> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFolderException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last object folder in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object folder, or <code>null</code> if a matching object folder could not be found
	 */
	public static ObjectFolder fetchByCompanyId_Last(
		long companyId, OrderByComparator<ObjectFolder> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the object folders before and after the current object folder in the ordered set where companyId = &#63;.
	 *
	 * @param objectFolderId the primary key of the current object folder
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object folder
	 * @throws NoSuchObjectFolderException if a object folder with the primary key could not be found
	 */
	public static ObjectFolder[] findByCompanyId_PrevAndNext(
			long objectFolderId, long companyId,
			OrderByComparator<ObjectFolder> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFolderException {

		return getPersistence().findByCompanyId_PrevAndNext(
			objectFolderId, companyId, orderByComparator);
	}

	/**
	 * Returns all the object folders that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching object folders that the user has permission to view
	 */
	public static List<ObjectFolder> filterFindByCompanyId(long companyId) {
		return getPersistence().filterFindByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the object folders that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object folders
	 * @param end the upper bound of the range of object folders (not inclusive)
	 * @return the range of matching object folders that the user has permission to view
	 */
	public static List<ObjectFolder> filterFindByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().filterFindByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the object folders that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object folders
	 * @param end the upper bound of the range of object folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object folders that the user has permission to view
	 */
	public static List<ObjectFolder> filterFindByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<ObjectFolder> orderByComparator) {

		return getPersistence().filterFindByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the object folders before and after the current object folder in the ordered set of object folders that the user has permission to view where companyId = &#63;.
	 *
	 * @param objectFolderId the primary key of the current object folder
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object folder
	 * @throws NoSuchObjectFolderException if a object folder with the primary key could not be found
	 */
	public static ObjectFolder[] filterFindByCompanyId_PrevAndNext(
			long objectFolderId, long companyId,
			OrderByComparator<ObjectFolder> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFolderException {

		return getPersistence().filterFindByCompanyId_PrevAndNext(
			objectFolderId, companyId, orderByComparator);
	}

	/**
	 * Removes all the object folders where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of object folders where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching object folders
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns the number of object folders that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching object folders that the user has permission to view
	 */
	public static int filterCountByCompanyId(long companyId) {
		return getPersistence().filterCountByCompanyId(companyId);
	}

	/**
	 * Returns the object folder where companyId = &#63; and name = &#63; or throws a <code>NoSuchObjectFolderException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching object folder
	 * @throws NoSuchObjectFolderException if a matching object folder could not be found
	 */
	public static ObjectFolder findByC_N(long companyId, String name)
		throws com.liferay.object.exception.NoSuchObjectFolderException {

		return getPersistence().findByC_N(companyId, name);
	}

	/**
	 * Returns the object folder where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching object folder, or <code>null</code> if a matching object folder could not be found
	 */
	public static ObjectFolder fetchByC_N(long companyId, String name) {
		return getPersistence().fetchByC_N(companyId, name);
	}

	/**
	 * Returns the object folder where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching object folder, or <code>null</code> if a matching object folder could not be found
	 */
	public static ObjectFolder fetchByC_N(
		long companyId, String name, boolean useFinderCache) {

		return getPersistence().fetchByC_N(companyId, name, useFinderCache);
	}

	/**
	 * Removes the object folder where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the object folder that was removed
	 */
	public static ObjectFolder removeByC_N(long companyId, String name)
		throws com.liferay.object.exception.NoSuchObjectFolderException {

		return getPersistence().removeByC_N(companyId, name);
	}

	/**
	 * Returns the number of object folders where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching object folders
	 */
	public static int countByC_N(long companyId, String name) {
		return getPersistence().countByC_N(companyId, name);
	}

	/**
	 * Returns the object folder where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchObjectFolderException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching object folder
	 * @throws NoSuchObjectFolderException if a matching object folder could not be found
	 */
	public static ObjectFolder findByERC_C(
			String externalReferenceCode, long companyId)
		throws com.liferay.object.exception.NoSuchObjectFolderException {

		return getPersistence().findByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns the object folder where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching object folder, or <code>null</code> if a matching object folder could not be found
	 */
	public static ObjectFolder fetchByERC_C(
		String externalReferenceCode, long companyId) {

		return getPersistence().fetchByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns the object folder where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching object folder, or <code>null</code> if a matching object folder could not be found
	 */
	public static ObjectFolder fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache) {

		return getPersistence().fetchByERC_C(
			externalReferenceCode, companyId, useFinderCache);
	}

	/**
	 * Removes the object folder where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the object folder that was removed
	 */
	public static ObjectFolder removeByERC_C(
			String externalReferenceCode, long companyId)
		throws com.liferay.object.exception.NoSuchObjectFolderException {

		return getPersistence().removeByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns the number of object folders where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching object folders
	 */
	public static int countByERC_C(
		String externalReferenceCode, long companyId) {

		return getPersistence().countByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Caches the object folder in the entity cache if it is enabled.
	 *
	 * @param objectFolder the object folder
	 */
	public static void cacheResult(ObjectFolder objectFolder) {
		getPersistence().cacheResult(objectFolder);
	}

	/**
	 * Caches the object folders in the entity cache if it is enabled.
	 *
	 * @param objectFolders the object folders
	 */
	public static void cacheResult(List<ObjectFolder> objectFolders) {
		getPersistence().cacheResult(objectFolders);
	}

	/**
	 * Creates a new object folder with the primary key. Does not add the object folder to the database.
	 *
	 * @param objectFolderId the primary key for the new object folder
	 * @return the new object folder
	 */
	public static ObjectFolder create(long objectFolderId) {
		return getPersistence().create(objectFolderId);
	}

	/**
	 * Removes the object folder with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectFolderId the primary key of the object folder
	 * @return the object folder that was removed
	 * @throws NoSuchObjectFolderException if a object folder with the primary key could not be found
	 */
	public static ObjectFolder remove(long objectFolderId)
		throws com.liferay.object.exception.NoSuchObjectFolderException {

		return getPersistence().remove(objectFolderId);
	}

	public static ObjectFolder updateImpl(ObjectFolder objectFolder) {
		return getPersistence().updateImpl(objectFolder);
	}

	/**
	 * Returns the object folder with the primary key or throws a <code>NoSuchObjectFolderException</code> if it could not be found.
	 *
	 * @param objectFolderId the primary key of the object folder
	 * @return the object folder
	 * @throws NoSuchObjectFolderException if a object folder with the primary key could not be found
	 */
	public static ObjectFolder findByPrimaryKey(long objectFolderId)
		throws com.liferay.object.exception.NoSuchObjectFolderException {

		return getPersistence().findByPrimaryKey(objectFolderId);
	}

	/**
	 * Returns the object folder with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectFolderId the primary key of the object folder
	 * @return the object folder, or <code>null</code> if a object folder with the primary key could not be found
	 */
	public static ObjectFolder fetchByPrimaryKey(long objectFolderId) {
		return getPersistence().fetchByPrimaryKey(objectFolderId);
	}

	/**
	 * Returns all the object folders.
	 *
	 * @return the object folders
	 */
	public static List<ObjectFolder> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the object folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object folders
	 * @param end the upper bound of the range of object folders (not inclusive)
	 * @return the range of object folders
	 */
	public static List<ObjectFolder> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the object folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object folders
	 * @param end the upper bound of the range of object folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object folders
	 */
	public static List<ObjectFolder> findAll(
		int start, int end, OrderByComparator<ObjectFolder> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object folders
	 * @param end the upper bound of the range of object folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object folders
	 */
	public static List<ObjectFolder> findAll(
		int start, int end, OrderByComparator<ObjectFolder> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the object folders from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of object folders.
	 *
	 * @return the number of object folders
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ObjectFolderPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(ObjectFolderPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile ObjectFolderPersistence _persistence;

}
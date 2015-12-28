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

package com.liferay.portlet.documentlibrary.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.documentlibrary.model.DLFileShortcut;

import java.util.List;

/**
 * The persistence utility for the document library file shortcut service. This utility wraps {@link com.liferay.portlet.documentlibrary.service.persistence.impl.DLFileShortcutPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileShortcutPersistence
 * @see com.liferay.portlet.documentlibrary.service.persistence.impl.DLFileShortcutPersistenceImpl
 * @generated
 */
@ProviderType
public class DLFileShortcutUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(DLFileShortcut dlFileShortcut) {
		getPersistence().clearCache(dlFileShortcut);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DLFileShortcut> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DLFileShortcut> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DLFileShortcut> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DLFileShortcut> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static DLFileShortcut update(DLFileShortcut dlFileShortcut) {
		return getPersistence().update(dlFileShortcut);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static DLFileShortcut update(DLFileShortcut dlFileShortcut,
		ServiceContext serviceContext) {
		return getPersistence().update(dlFileShortcut, serviceContext);
	}

	/**
	* Returns all the document library file shortcuts where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching document library file shortcuts
	*/
	public static List<DLFileShortcut> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the document library file shortcuts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @return the range of matching document library file shortcuts
	*/
	public static List<DLFileShortcut> findByUuid(java.lang.String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the document library file shortcuts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file shortcuts
	*/
	public static List<DLFileShortcut> findByUuid(java.lang.String uuid,
		int start, int end, OrderByComparator<DLFileShortcut> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the document library file shortcuts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file shortcuts
	*/
	public static List<DLFileShortcut> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<DLFileShortcut> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first document library file shortcut in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file shortcut
	* @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	*/
	public static DLFileShortcut findByUuid_First(java.lang.String uuid,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first document library file shortcut in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	*/
	public static DLFileShortcut fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<DLFileShortcut> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last document library file shortcut in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file shortcut
	* @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	*/
	public static DLFileShortcut findByUuid_Last(java.lang.String uuid,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last document library file shortcut in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	*/
	public static DLFileShortcut fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<DLFileShortcut> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set where uuid = &#63;.
	*
	* @param fileShortcutId the primary key of the current document library file shortcut
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file shortcut
	* @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	*/
	public static DLFileShortcut[] findByUuid_PrevAndNext(long fileShortcutId,
		java.lang.String uuid,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByUuid_PrevAndNext(fileShortcutId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the document library file shortcuts where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of document library file shortcuts where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching document library file shortcuts
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the document library file shortcut where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchFileShortcutException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching document library file shortcut
	* @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	*/
	public static DLFileShortcut findByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the document library file shortcut where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	*/
	public static DLFileShortcut fetchByUUID_G(java.lang.String uuid,
		long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the document library file shortcut where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	*/
	public static DLFileShortcut fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the document library file shortcut where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the document library file shortcut that was removed
	*/
	public static DLFileShortcut removeByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of document library file shortcuts where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching document library file shortcuts
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the document library file shortcuts where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching document library file shortcuts
	*/
	public static List<DLFileShortcut> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the document library file shortcuts where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @return the range of matching document library file shortcuts
	*/
	public static List<DLFileShortcut> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the document library file shortcuts where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file shortcuts
	*/
	public static List<DLFileShortcut> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<DLFileShortcut> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the document library file shortcuts where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file shortcuts
	*/
	public static List<DLFileShortcut> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<DLFileShortcut> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first document library file shortcut in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file shortcut
	* @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	*/
	public static DLFileShortcut findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<DLFileShortcut> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first document library file shortcut in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	*/
	public static DLFileShortcut fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<DLFileShortcut> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last document library file shortcut in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file shortcut
	* @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	*/
	public static DLFileShortcut findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<DLFileShortcut> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last document library file shortcut in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	*/
	public static DLFileShortcut fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<DLFileShortcut> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param fileShortcutId the primary key of the current document library file shortcut
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file shortcut
	* @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	*/
	public static DLFileShortcut[] findByUuid_C_PrevAndNext(
		long fileShortcutId, java.lang.String uuid, long companyId,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(fileShortcutId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the document library file shortcuts where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of document library file shortcuts where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching document library file shortcuts
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the document library file shortcuts where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching document library file shortcuts
	*/
	public static List<DLFileShortcut> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the document library file shortcuts where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @return the range of matching document library file shortcuts
	*/
	public static List<DLFileShortcut> findByCompanyId(long companyId,
		int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the document library file shortcuts where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file shortcuts
	*/
	public static List<DLFileShortcut> findByCompanyId(long companyId,
		int start, int end, OrderByComparator<DLFileShortcut> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the document library file shortcuts where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file shortcuts
	*/
	public static List<DLFileShortcut> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<DLFileShortcut> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first document library file shortcut in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file shortcut
	* @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	*/
	public static DLFileShortcut findByCompanyId_First(long companyId,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first document library file shortcut in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	*/
	public static DLFileShortcut fetchByCompanyId_First(long companyId,
		OrderByComparator<DLFileShortcut> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last document library file shortcut in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file shortcut
	* @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	*/
	public static DLFileShortcut findByCompanyId_Last(long companyId,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last document library file shortcut in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	*/
	public static DLFileShortcut fetchByCompanyId_Last(long companyId,
		OrderByComparator<DLFileShortcut> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set where companyId = &#63;.
	*
	* @param fileShortcutId the primary key of the current document library file shortcut
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file shortcut
	* @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	*/
	public static DLFileShortcut[] findByCompanyId_PrevAndNext(
		long fileShortcutId, long companyId,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(fileShortcutId, companyId,
			orderByComparator);
	}

	/**
	* Removes all the document library file shortcuts where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of document library file shortcuts where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching document library file shortcuts
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns all the document library file shortcuts where toFileEntryId = &#63;.
	*
	* @param toFileEntryId the to file entry ID
	* @return the matching document library file shortcuts
	*/
	public static List<DLFileShortcut> findByToFileEntryId(long toFileEntryId) {
		return getPersistence().findByToFileEntryId(toFileEntryId);
	}

	/**
	* Returns a range of all the document library file shortcuts where toFileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param toFileEntryId the to file entry ID
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @return the range of matching document library file shortcuts
	*/
	public static List<DLFileShortcut> findByToFileEntryId(long toFileEntryId,
		int start, int end) {
		return getPersistence().findByToFileEntryId(toFileEntryId, start, end);
	}

	/**
	* Returns an ordered range of all the document library file shortcuts where toFileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param toFileEntryId the to file entry ID
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file shortcuts
	*/
	public static List<DLFileShortcut> findByToFileEntryId(long toFileEntryId,
		int start, int end, OrderByComparator<DLFileShortcut> orderByComparator) {
		return getPersistence()
				   .findByToFileEntryId(toFileEntryId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the document library file shortcuts where toFileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param toFileEntryId the to file entry ID
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file shortcuts
	*/
	public static List<DLFileShortcut> findByToFileEntryId(long toFileEntryId,
		int start, int end,
		OrderByComparator<DLFileShortcut> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByToFileEntryId(toFileEntryId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first document library file shortcut in the ordered set where toFileEntryId = &#63;.
	*
	* @param toFileEntryId the to file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file shortcut
	* @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	*/
	public static DLFileShortcut findByToFileEntryId_First(long toFileEntryId,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByToFileEntryId_First(toFileEntryId, orderByComparator);
	}

	/**
	* Returns the first document library file shortcut in the ordered set where toFileEntryId = &#63;.
	*
	* @param toFileEntryId the to file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	*/
	public static DLFileShortcut fetchByToFileEntryId_First(
		long toFileEntryId, OrderByComparator<DLFileShortcut> orderByComparator) {
		return getPersistence()
				   .fetchByToFileEntryId_First(toFileEntryId, orderByComparator);
	}

	/**
	* Returns the last document library file shortcut in the ordered set where toFileEntryId = &#63;.
	*
	* @param toFileEntryId the to file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file shortcut
	* @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	*/
	public static DLFileShortcut findByToFileEntryId_Last(long toFileEntryId,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByToFileEntryId_Last(toFileEntryId, orderByComparator);
	}

	/**
	* Returns the last document library file shortcut in the ordered set where toFileEntryId = &#63;.
	*
	* @param toFileEntryId the to file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	*/
	public static DLFileShortcut fetchByToFileEntryId_Last(long toFileEntryId,
		OrderByComparator<DLFileShortcut> orderByComparator) {
		return getPersistence()
				   .fetchByToFileEntryId_Last(toFileEntryId, orderByComparator);
	}

	/**
	* Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set where toFileEntryId = &#63;.
	*
	* @param fileShortcutId the primary key of the current document library file shortcut
	* @param toFileEntryId the to file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file shortcut
	* @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	*/
	public static DLFileShortcut[] findByToFileEntryId_PrevAndNext(
		long fileShortcutId, long toFileEntryId,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByToFileEntryId_PrevAndNext(fileShortcutId,
			toFileEntryId, orderByComparator);
	}

	/**
	* Removes all the document library file shortcuts where toFileEntryId = &#63; from the database.
	*
	* @param toFileEntryId the to file entry ID
	*/
	public static void removeByToFileEntryId(long toFileEntryId) {
		getPersistence().removeByToFileEntryId(toFileEntryId);
	}

	/**
	* Returns the number of document library file shortcuts where toFileEntryId = &#63;.
	*
	* @param toFileEntryId the to file entry ID
	* @return the number of matching document library file shortcuts
	*/
	public static int countByToFileEntryId(long toFileEntryId) {
		return getPersistence().countByToFileEntryId(toFileEntryId);
	}

	/**
	* Returns all the document library file shortcuts where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @return the matching document library file shortcuts
	*/
	public static List<DLFileShortcut> findByG_F(long groupId, long folderId) {
		return getPersistence().findByG_F(groupId, folderId);
	}

	/**
	* Returns a range of all the document library file shortcuts where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @return the range of matching document library file shortcuts
	*/
	public static List<DLFileShortcut> findByG_F(long groupId, long folderId,
		int start, int end) {
		return getPersistence().findByG_F(groupId, folderId, start, end);
	}

	/**
	* Returns an ordered range of all the document library file shortcuts where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file shortcuts
	*/
	public static List<DLFileShortcut> findByG_F(long groupId, long folderId,
		int start, int end, OrderByComparator<DLFileShortcut> orderByComparator) {
		return getPersistence()
				   .findByG_F(groupId, folderId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the document library file shortcuts where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file shortcuts
	*/
	public static List<DLFileShortcut> findByG_F(long groupId, long folderId,
		int start, int end,
		OrderByComparator<DLFileShortcut> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_F(groupId, folderId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file shortcut
	* @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	*/
	public static DLFileShortcut findByG_F_First(long groupId, long folderId,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByG_F_First(groupId, folderId, orderByComparator);
	}

	/**
	* Returns the first document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	*/
	public static DLFileShortcut fetchByG_F_First(long groupId, long folderId,
		OrderByComparator<DLFileShortcut> orderByComparator) {
		return getPersistence()
				   .fetchByG_F_First(groupId, folderId, orderByComparator);
	}

	/**
	* Returns the last document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file shortcut
	* @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	*/
	public static DLFileShortcut findByG_F_Last(long groupId, long folderId,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByG_F_Last(groupId, folderId, orderByComparator);
	}

	/**
	* Returns the last document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	*/
	public static DLFileShortcut fetchByG_F_Last(long groupId, long folderId,
		OrderByComparator<DLFileShortcut> orderByComparator) {
		return getPersistence()
				   .fetchByG_F_Last(groupId, folderId, orderByComparator);
	}

	/**
	* Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* @param fileShortcutId the primary key of the current document library file shortcut
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file shortcut
	* @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	*/
	public static DLFileShortcut[] findByG_F_PrevAndNext(long fileShortcutId,
		long groupId, long folderId,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByG_F_PrevAndNext(fileShortcutId, groupId, folderId,
			orderByComparator);
	}

	/**
	* Returns all the document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @return the matching document library file shortcuts that the user has permission to view
	*/
	public static List<DLFileShortcut> filterFindByG_F(long groupId,
		long folderId) {
		return getPersistence().filterFindByG_F(groupId, folderId);
	}

	/**
	* Returns a range of all the document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @return the range of matching document library file shortcuts that the user has permission to view
	*/
	public static List<DLFileShortcut> filterFindByG_F(long groupId,
		long folderId, int start, int end) {
		return getPersistence().filterFindByG_F(groupId, folderId, start, end);
	}

	/**
	* Returns an ordered range of all the document library file shortcuts that the user has permissions to view where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file shortcuts that the user has permission to view
	*/
	public static List<DLFileShortcut> filterFindByG_F(long groupId,
		long folderId, int start, int end,
		OrderByComparator<DLFileShortcut> orderByComparator) {
		return getPersistence()
				   .filterFindByG_F(groupId, folderId, start, end,
			orderByComparator);
	}

	/**
	* Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set of document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63;.
	*
	* @param fileShortcutId the primary key of the current document library file shortcut
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file shortcut
	* @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	*/
	public static DLFileShortcut[] filterFindByG_F_PrevAndNext(
		long fileShortcutId, long groupId, long folderId,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .filterFindByG_F_PrevAndNext(fileShortcutId, groupId,
			folderId, orderByComparator);
	}

	/**
	* Removes all the document library file shortcuts where groupId = &#63; and folderId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	*/
	public static void removeByG_F(long groupId, long folderId) {
		getPersistence().removeByG_F(groupId, folderId);
	}

	/**
	* Returns the number of document library file shortcuts where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @return the number of matching document library file shortcuts
	*/
	public static int countByG_F(long groupId, long folderId) {
		return getPersistence().countByG_F(groupId, folderId);
	}

	/**
	* Returns the number of document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @return the number of matching document library file shortcuts that the user has permission to view
	*/
	public static int filterCountByG_F(long groupId, long folderId) {
		return getPersistence().filterCountByG_F(groupId, folderId);
	}

	/**
	* Returns all the document library file shortcuts where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the matching document library file shortcuts
	*/
	public static List<DLFileShortcut> findByC_NotS(long companyId, int status) {
		return getPersistence().findByC_NotS(companyId, status);
	}

	/**
	* Returns a range of all the document library file shortcuts where companyId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @return the range of matching document library file shortcuts
	*/
	public static List<DLFileShortcut> findByC_NotS(long companyId, int status,
		int start, int end) {
		return getPersistence().findByC_NotS(companyId, status, start, end);
	}

	/**
	* Returns an ordered range of all the document library file shortcuts where companyId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file shortcuts
	*/
	public static List<DLFileShortcut> findByC_NotS(long companyId, int status,
		int start, int end, OrderByComparator<DLFileShortcut> orderByComparator) {
		return getPersistence()
				   .findByC_NotS(companyId, status, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the document library file shortcuts where companyId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file shortcuts
	*/
	public static List<DLFileShortcut> findByC_NotS(long companyId, int status,
		int start, int end,
		OrderByComparator<DLFileShortcut> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_NotS(companyId, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first document library file shortcut in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file shortcut
	* @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	*/
	public static DLFileShortcut findByC_NotS_First(long companyId, int status,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByC_NotS_First(companyId, status, orderByComparator);
	}

	/**
	* Returns the first document library file shortcut in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	*/
	public static DLFileShortcut fetchByC_NotS_First(long companyId,
		int status, OrderByComparator<DLFileShortcut> orderByComparator) {
		return getPersistence()
				   .fetchByC_NotS_First(companyId, status, orderByComparator);
	}

	/**
	* Returns the last document library file shortcut in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file shortcut
	* @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	*/
	public static DLFileShortcut findByC_NotS_Last(long companyId, int status,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByC_NotS_Last(companyId, status, orderByComparator);
	}

	/**
	* Returns the last document library file shortcut in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	*/
	public static DLFileShortcut fetchByC_NotS_Last(long companyId, int status,
		OrderByComparator<DLFileShortcut> orderByComparator) {
		return getPersistence()
				   .fetchByC_NotS_Last(companyId, status, orderByComparator);
	}

	/**
	* Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param fileShortcutId the primary key of the current document library file shortcut
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file shortcut
	* @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	*/
	public static DLFileShortcut[] findByC_NotS_PrevAndNext(
		long fileShortcutId, long companyId, int status,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByC_NotS_PrevAndNext(fileShortcutId, companyId, status,
			orderByComparator);
	}

	/**
	* Removes all the document library file shortcuts where companyId = &#63; and status &ne; &#63; from the database.
	*
	* @param companyId the company ID
	* @param status the status
	*/
	public static void removeByC_NotS(long companyId, int status) {
		getPersistence().removeByC_NotS(companyId, status);
	}

	/**
	* Returns the number of document library file shortcuts where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the number of matching document library file shortcuts
	*/
	public static int countByC_NotS(long companyId, int status) {
		return getPersistence().countByC_NotS(companyId, status);
	}

	/**
	* Returns all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @return the matching document library file shortcuts
	*/
	public static List<DLFileShortcut> findByG_F_A(long groupId, long folderId,
		boolean active) {
		return getPersistence().findByG_F_A(groupId, folderId, active);
	}

	/**
	* Returns a range of all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @return the range of matching document library file shortcuts
	*/
	public static List<DLFileShortcut> findByG_F_A(long groupId, long folderId,
		boolean active, int start, int end) {
		return getPersistence()
				   .findByG_F_A(groupId, folderId, active, start, end);
	}

	/**
	* Returns an ordered range of all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file shortcuts
	*/
	public static List<DLFileShortcut> findByG_F_A(long groupId, long folderId,
		boolean active, int start, int end,
		OrderByComparator<DLFileShortcut> orderByComparator) {
		return getPersistence()
				   .findByG_F_A(groupId, folderId, active, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file shortcuts
	*/
	public static List<DLFileShortcut> findByG_F_A(long groupId, long folderId,
		boolean active, int start, int end,
		OrderByComparator<DLFileShortcut> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_F_A(groupId, folderId, active, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file shortcut
	* @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	*/
	public static DLFileShortcut findByG_F_A_First(long groupId, long folderId,
		boolean active, OrderByComparator<DLFileShortcut> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByG_F_A_First(groupId, folderId, active,
			orderByComparator);
	}

	/**
	* Returns the first document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	*/
	public static DLFileShortcut fetchByG_F_A_First(long groupId,
		long folderId, boolean active,
		OrderByComparator<DLFileShortcut> orderByComparator) {
		return getPersistence()
				   .fetchByG_F_A_First(groupId, folderId, active,
			orderByComparator);
	}

	/**
	* Returns the last document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file shortcut
	* @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	*/
	public static DLFileShortcut findByG_F_A_Last(long groupId, long folderId,
		boolean active, OrderByComparator<DLFileShortcut> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByG_F_A_Last(groupId, folderId, active,
			orderByComparator);
	}

	/**
	* Returns the last document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	*/
	public static DLFileShortcut fetchByG_F_A_Last(long groupId, long folderId,
		boolean active, OrderByComparator<DLFileShortcut> orderByComparator) {
		return getPersistence()
				   .fetchByG_F_A_Last(groupId, folderId, active,
			orderByComparator);
	}

	/**
	* Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* @param fileShortcutId the primary key of the current document library file shortcut
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file shortcut
	* @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	*/
	public static DLFileShortcut[] findByG_F_A_PrevAndNext(
		long fileShortcutId, long groupId, long folderId, boolean active,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByG_F_A_PrevAndNext(fileShortcutId, groupId, folderId,
			active, orderByComparator);
	}

	/**
	* Returns all the document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @return the matching document library file shortcuts that the user has permission to view
	*/
	public static List<DLFileShortcut> filterFindByG_F_A(long groupId,
		long folderId, boolean active) {
		return getPersistence().filterFindByG_F_A(groupId, folderId, active);
	}

	/**
	* Returns a range of all the document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @return the range of matching document library file shortcuts that the user has permission to view
	*/
	public static List<DLFileShortcut> filterFindByG_F_A(long groupId,
		long folderId, boolean active, int start, int end) {
		return getPersistence()
				   .filterFindByG_F_A(groupId, folderId, active, start, end);
	}

	/**
	* Returns an ordered range of all the document library file shortcuts that the user has permissions to view where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file shortcuts that the user has permission to view
	*/
	public static List<DLFileShortcut> filterFindByG_F_A(long groupId,
		long folderId, boolean active, int start, int end,
		OrderByComparator<DLFileShortcut> orderByComparator) {
		return getPersistence()
				   .filterFindByG_F_A(groupId, folderId, active, start, end,
			orderByComparator);
	}

	/**
	* Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set of document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* @param fileShortcutId the primary key of the current document library file shortcut
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file shortcut
	* @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	*/
	public static DLFileShortcut[] filterFindByG_F_A_PrevAndNext(
		long fileShortcutId, long groupId, long folderId, boolean active,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .filterFindByG_F_A_PrevAndNext(fileShortcutId, groupId,
			folderId, active, orderByComparator);
	}

	/**
	* Removes all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63; from the database.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	*/
	public static void removeByG_F_A(long groupId, long folderId, boolean active) {
		getPersistence().removeByG_F_A(groupId, folderId, active);
	}

	/**
	* Returns the number of document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @return the number of matching document library file shortcuts
	*/
	public static int countByG_F_A(long groupId, long folderId, boolean active) {
		return getPersistence().countByG_F_A(groupId, folderId, active);
	}

	/**
	* Returns the number of document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @return the number of matching document library file shortcuts that the user has permission to view
	*/
	public static int filterCountByG_F_A(long groupId, long folderId,
		boolean active) {
		return getPersistence().filterCountByG_F_A(groupId, folderId, active);
	}

	/**
	* Returns all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param status the status
	* @return the matching document library file shortcuts
	*/
	public static List<DLFileShortcut> findByG_F_A_S(long groupId,
		long folderId, boolean active, int status) {
		return getPersistence().findByG_F_A_S(groupId, folderId, active, status);
	}

	/**
	* Returns a range of all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param status the status
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @return the range of matching document library file shortcuts
	*/
	public static List<DLFileShortcut> findByG_F_A_S(long groupId,
		long folderId, boolean active, int status, int start, int end) {
		return getPersistence()
				   .findByG_F_A_S(groupId, folderId, active, status, start, end);
	}

	/**
	* Returns an ordered range of all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param status the status
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file shortcuts
	*/
	public static List<DLFileShortcut> findByG_F_A_S(long groupId,
		long folderId, boolean active, int status, int start, int end,
		OrderByComparator<DLFileShortcut> orderByComparator) {
		return getPersistence()
				   .findByG_F_A_S(groupId, folderId, active, status, start,
			end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param status the status
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file shortcuts
	*/
	public static List<DLFileShortcut> findByG_F_A_S(long groupId,
		long folderId, boolean active, int status, int start, int end,
		OrderByComparator<DLFileShortcut> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_F_A_S(groupId, folderId, active, status, start,
			end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file shortcut
	* @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	*/
	public static DLFileShortcut findByG_F_A_S_First(long groupId,
		long folderId, boolean active, int status,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByG_F_A_S_First(groupId, folderId, active, status,
			orderByComparator);
	}

	/**
	* Returns the first document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	*/
	public static DLFileShortcut fetchByG_F_A_S_First(long groupId,
		long folderId, boolean active, int status,
		OrderByComparator<DLFileShortcut> orderByComparator) {
		return getPersistence()
				   .fetchByG_F_A_S_First(groupId, folderId, active, status,
			orderByComparator);
	}

	/**
	* Returns the last document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file shortcut
	* @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	*/
	public static DLFileShortcut findByG_F_A_S_Last(long groupId,
		long folderId, boolean active, int status,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByG_F_A_S_Last(groupId, folderId, active, status,
			orderByComparator);
	}

	/**
	* Returns the last document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	*/
	public static DLFileShortcut fetchByG_F_A_S_Last(long groupId,
		long folderId, boolean active, int status,
		OrderByComparator<DLFileShortcut> orderByComparator) {
		return getPersistence()
				   .fetchByG_F_A_S_Last(groupId, folderId, active, status,
			orderByComparator);
	}

	/**
	* Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	*
	* @param fileShortcutId the primary key of the current document library file shortcut
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file shortcut
	* @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	*/
	public static DLFileShortcut[] findByG_F_A_S_PrevAndNext(
		long fileShortcutId, long groupId, long folderId, boolean active,
		int status, OrderByComparator<DLFileShortcut> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByG_F_A_S_PrevAndNext(fileShortcutId, groupId,
			folderId, active, status, orderByComparator);
	}

	/**
	* Returns all the document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param status the status
	* @return the matching document library file shortcuts that the user has permission to view
	*/
	public static List<DLFileShortcut> filterFindByG_F_A_S(long groupId,
		long folderId, boolean active, int status) {
		return getPersistence()
				   .filterFindByG_F_A_S(groupId, folderId, active, status);
	}

	/**
	* Returns a range of all the document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param status the status
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @return the range of matching document library file shortcuts that the user has permission to view
	*/
	public static List<DLFileShortcut> filterFindByG_F_A_S(long groupId,
		long folderId, boolean active, int status, int start, int end) {
		return getPersistence()
				   .filterFindByG_F_A_S(groupId, folderId, active, status,
			start, end);
	}

	/**
	* Returns an ordered range of all the document library file shortcuts that the user has permissions to view where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param status the status
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file shortcuts that the user has permission to view
	*/
	public static List<DLFileShortcut> filterFindByG_F_A_S(long groupId,
		long folderId, boolean active, int status, int start, int end,
		OrderByComparator<DLFileShortcut> orderByComparator) {
		return getPersistence()
				   .filterFindByG_F_A_S(groupId, folderId, active, status,
			start, end, orderByComparator);
	}

	/**
	* Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set of document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	*
	* @param fileShortcutId the primary key of the current document library file shortcut
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file shortcut
	* @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	*/
	public static DLFileShortcut[] filterFindByG_F_A_S_PrevAndNext(
		long fileShortcutId, long groupId, long folderId, boolean active,
		int status, OrderByComparator<DLFileShortcut> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .filterFindByG_F_A_S_PrevAndNext(fileShortcutId, groupId,
			folderId, active, status, orderByComparator);
	}

	/**
	* Removes all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param status the status
	*/
	public static void removeByG_F_A_S(long groupId, long folderId,
		boolean active, int status) {
		getPersistence().removeByG_F_A_S(groupId, folderId, active, status);
	}

	/**
	* Returns the number of document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param status the status
	* @return the number of matching document library file shortcuts
	*/
	public static int countByG_F_A_S(long groupId, long folderId,
		boolean active, int status) {
		return getPersistence().countByG_F_A_S(groupId, folderId, active, status);
	}

	/**
	* Returns the number of document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param status the status
	* @return the number of matching document library file shortcuts that the user has permission to view
	*/
	public static int filterCountByG_F_A_S(long groupId, long folderId,
		boolean active, int status) {
		return getPersistence()
				   .filterCountByG_F_A_S(groupId, folderId, active, status);
	}

	/**
	* Caches the document library file shortcut in the entity cache if it is enabled.
	*
	* @param dlFileShortcut the document library file shortcut
	*/
	public static void cacheResult(DLFileShortcut dlFileShortcut) {
		getPersistence().cacheResult(dlFileShortcut);
	}

	/**
	* Caches the document library file shortcuts in the entity cache if it is enabled.
	*
	* @param dlFileShortcuts the document library file shortcuts
	*/
	public static void cacheResult(List<DLFileShortcut> dlFileShortcuts) {
		getPersistence().cacheResult(dlFileShortcuts);
	}

	/**
	* Creates a new document library file shortcut with the primary key. Does not add the document library file shortcut to the database.
	*
	* @param fileShortcutId the primary key for the new document library file shortcut
	* @return the new document library file shortcut
	*/
	public static DLFileShortcut create(long fileShortcutId) {
		return getPersistence().create(fileShortcutId);
	}

	/**
	* Removes the document library file shortcut with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileShortcutId the primary key of the document library file shortcut
	* @return the document library file shortcut that was removed
	* @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	*/
	public static DLFileShortcut remove(long fileShortcutId)
		throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence().remove(fileShortcutId);
	}

	public static DLFileShortcut updateImpl(DLFileShortcut dlFileShortcut) {
		return getPersistence().updateImpl(dlFileShortcut);
	}

	/**
	* Returns the document library file shortcut with the primary key or throws a {@link NoSuchFileShortcutException} if it could not be found.
	*
	* @param fileShortcutId the primary key of the document library file shortcut
	* @return the document library file shortcut
	* @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	*/
	public static DLFileShortcut findByPrimaryKey(long fileShortcutId)
		throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence().findByPrimaryKey(fileShortcutId);
	}

	/**
	* Returns the document library file shortcut with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param fileShortcutId the primary key of the document library file shortcut
	* @return the document library file shortcut, or <code>null</code> if a document library file shortcut with the primary key could not be found
	*/
	public static DLFileShortcut fetchByPrimaryKey(long fileShortcutId) {
		return getPersistence().fetchByPrimaryKey(fileShortcutId);
	}

	public static java.util.Map<java.io.Serializable, DLFileShortcut> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the document library file shortcuts.
	*
	* @return the document library file shortcuts
	*/
	public static List<DLFileShortcut> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the document library file shortcuts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @return the range of document library file shortcuts
	*/
	public static List<DLFileShortcut> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the document library file shortcuts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of document library file shortcuts
	*/
	public static List<DLFileShortcut> findAll(int start, int end,
		OrderByComparator<DLFileShortcut> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the document library file shortcuts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of document library file shortcuts
	*/
	public static List<DLFileShortcut> findAll(int start, int end,
		OrderByComparator<DLFileShortcut> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the document library file shortcuts from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of document library file shortcuts.
	*
	* @return the number of document library file shortcuts
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static DLFileShortcutPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (DLFileShortcutPersistence)PortalBeanLocatorUtil.locate(DLFileShortcutPersistence.class.getName());

			ReferenceRegistry.registerReference(DLFileShortcutUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static DLFileShortcutPersistence _persistence;
}
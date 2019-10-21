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

package com.liferay.data.engine.service.persistence;

import com.liferay.data.engine.model.DEDataDefinitionFieldLink;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the de data definition field link service. This utility wraps <code>com.liferay.data.engine.service.persistence.impl.DEDataDefinitionFieldLinkPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DEDataDefinitionFieldLinkPersistence
 * @generated
 */
public class DEDataDefinitionFieldLinkUtil {

	/**
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
		DEDataDefinitionFieldLink deDataDefinitionFieldLink) {

		getPersistence().clearCache(deDataDefinitionFieldLink);
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
	public static Map<Serializable, DEDataDefinitionFieldLink>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DEDataDefinitionFieldLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DEDataDefinitionFieldLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DEDataDefinitionFieldLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static DEDataDefinitionFieldLink update(
		DEDataDefinitionFieldLink deDataDefinitionFieldLink) {

		return getPersistence().update(deDataDefinitionFieldLink);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static DEDataDefinitionFieldLink update(
		DEDataDefinitionFieldLink deDataDefinitionFieldLink,
		ServiceContext serviceContext) {

		return getPersistence().update(
			deDataDefinitionFieldLink, serviceContext);
	}

	/**
	 * Returns all the de data definition field links where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching de data definition field links
	 */
	public static List<DEDataDefinitionFieldLink> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the de data definition field links where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataDefinitionFieldLinkModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of de data definition field links
	 * @param end the upper bound of the range of de data definition field links (not inclusive)
	 * @return the range of matching de data definition field links
	 */
	public static List<DEDataDefinitionFieldLink> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the de data definition field links where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataDefinitionFieldLinkModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of de data definition field links
	 * @param end the upper bound of the range of de data definition field links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching de data definition field links
	 */
	public static List<DEDataDefinitionFieldLink> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the de data definition field links where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataDefinitionFieldLinkModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of de data definition field links
	 * @param end the upper bound of the range of de data definition field links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching de data definition field links
	 */
	public static List<DEDataDefinitionFieldLink> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first de data definition field link in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data definition field link
	 * @throws NoSuchDataDefinitionFieldLinkException if a matching de data definition field link could not be found
	 */
	public static DEDataDefinitionFieldLink findByUuid_First(
			String uuid,
			OrderByComparator<DEDataDefinitionFieldLink> orderByComparator)
		throws com.liferay.data.engine.exception.
			NoSuchDataDefinitionFieldLinkException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first de data definition field link in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data definition field link, or <code>null</code> if a matching de data definition field link could not be found
	 */
	public static DEDataDefinitionFieldLink fetchByUuid_First(
		String uuid,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last de data definition field link in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data definition field link
	 * @throws NoSuchDataDefinitionFieldLinkException if a matching de data definition field link could not be found
	 */
	public static DEDataDefinitionFieldLink findByUuid_Last(
			String uuid,
			OrderByComparator<DEDataDefinitionFieldLink> orderByComparator)
		throws com.liferay.data.engine.exception.
			NoSuchDataDefinitionFieldLinkException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last de data definition field link in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data definition field link, or <code>null</code> if a matching de data definition field link could not be found
	 */
	public static DEDataDefinitionFieldLink fetchByUuid_Last(
		String uuid,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the de data definition field links before and after the current de data definition field link in the ordered set where uuid = &#63;.
	 *
	 * @param deDataDefinitionFieldLinkId the primary key of the current de data definition field link
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next de data definition field link
	 * @throws NoSuchDataDefinitionFieldLinkException if a de data definition field link with the primary key could not be found
	 */
	public static DEDataDefinitionFieldLink[] findByUuid_PrevAndNext(
			long deDataDefinitionFieldLinkId, String uuid,
			OrderByComparator<DEDataDefinitionFieldLink> orderByComparator)
		throws com.liferay.data.engine.exception.
			NoSuchDataDefinitionFieldLinkException {

		return getPersistence().findByUuid_PrevAndNext(
			deDataDefinitionFieldLinkId, uuid, orderByComparator);
	}

	/**
	 * Removes all the de data definition field links where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of de data definition field links where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching de data definition field links
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the de data definition field link where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchDataDefinitionFieldLinkException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching de data definition field link
	 * @throws NoSuchDataDefinitionFieldLinkException if a matching de data definition field link could not be found
	 */
	public static DEDataDefinitionFieldLink findByUUID_G(
			String uuid, long groupId)
		throws com.liferay.data.engine.exception.
			NoSuchDataDefinitionFieldLinkException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the de data definition field link where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching de data definition field link, or <code>null</code> if a matching de data definition field link could not be found
	 */
	public static DEDataDefinitionFieldLink fetchByUUID_G(
		String uuid, long groupId) {

		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the de data definition field link where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching de data definition field link, or <code>null</code> if a matching de data definition field link could not be found
	 */
	public static DEDataDefinitionFieldLink fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the de data definition field link where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the de data definition field link that was removed
	 */
	public static DEDataDefinitionFieldLink removeByUUID_G(
			String uuid, long groupId)
		throws com.liferay.data.engine.exception.
			NoSuchDataDefinitionFieldLinkException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of de data definition field links where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching de data definition field links
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the de data definition field links where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching de data definition field links
	 */
	public static List<DEDataDefinitionFieldLink> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the de data definition field links where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataDefinitionFieldLinkModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of de data definition field links
	 * @param end the upper bound of the range of de data definition field links (not inclusive)
	 * @return the range of matching de data definition field links
	 */
	public static List<DEDataDefinitionFieldLink> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the de data definition field links where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataDefinitionFieldLinkModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of de data definition field links
	 * @param end the upper bound of the range of de data definition field links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching de data definition field links
	 */
	public static List<DEDataDefinitionFieldLink> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the de data definition field links where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataDefinitionFieldLinkModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of de data definition field links
	 * @param end the upper bound of the range of de data definition field links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching de data definition field links
	 */
	public static List<DEDataDefinitionFieldLink> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first de data definition field link in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data definition field link
	 * @throws NoSuchDataDefinitionFieldLinkException if a matching de data definition field link could not be found
	 */
	public static DEDataDefinitionFieldLink findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<DEDataDefinitionFieldLink> orderByComparator)
		throws com.liferay.data.engine.exception.
			NoSuchDataDefinitionFieldLinkException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first de data definition field link in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data definition field link, or <code>null</code> if a matching de data definition field link could not be found
	 */
	public static DEDataDefinitionFieldLink fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last de data definition field link in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data definition field link
	 * @throws NoSuchDataDefinitionFieldLinkException if a matching de data definition field link could not be found
	 */
	public static DEDataDefinitionFieldLink findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<DEDataDefinitionFieldLink> orderByComparator)
		throws com.liferay.data.engine.exception.
			NoSuchDataDefinitionFieldLinkException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last de data definition field link in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data definition field link, or <code>null</code> if a matching de data definition field link could not be found
	 */
	public static DEDataDefinitionFieldLink fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the de data definition field links before and after the current de data definition field link in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param deDataDefinitionFieldLinkId the primary key of the current de data definition field link
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next de data definition field link
	 * @throws NoSuchDataDefinitionFieldLinkException if a de data definition field link with the primary key could not be found
	 */
	public static DEDataDefinitionFieldLink[] findByUuid_C_PrevAndNext(
			long deDataDefinitionFieldLinkId, String uuid, long companyId,
			OrderByComparator<DEDataDefinitionFieldLink> orderByComparator)
		throws com.liferay.data.engine.exception.
			NoSuchDataDefinitionFieldLinkException {

		return getPersistence().findByUuid_C_PrevAndNext(
			deDataDefinitionFieldLinkId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the de data definition field links where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of de data definition field links where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching de data definition field links
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the de data definition field links where ddmStructureId = &#63;.
	 *
	 * @param ddmStructureId the ddm structure ID
	 * @return the matching de data definition field links
	 */
	public static List<DEDataDefinitionFieldLink> findByDDMStructureId(
		long ddmStructureId) {

		return getPersistence().findByDDMStructureId(ddmStructureId);
	}

	/**
	 * Returns a range of all the de data definition field links where ddmStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataDefinitionFieldLinkModelImpl</code>.
	 * </p>
	 *
	 * @param ddmStructureId the ddm structure ID
	 * @param start the lower bound of the range of de data definition field links
	 * @param end the upper bound of the range of de data definition field links (not inclusive)
	 * @return the range of matching de data definition field links
	 */
	public static List<DEDataDefinitionFieldLink> findByDDMStructureId(
		long ddmStructureId, int start, int end) {

		return getPersistence().findByDDMStructureId(
			ddmStructureId, start, end);
	}

	/**
	 * Returns an ordered range of all the de data definition field links where ddmStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataDefinitionFieldLinkModelImpl</code>.
	 * </p>
	 *
	 * @param ddmStructureId the ddm structure ID
	 * @param start the lower bound of the range of de data definition field links
	 * @param end the upper bound of the range of de data definition field links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching de data definition field links
	 */
	public static List<DEDataDefinitionFieldLink> findByDDMStructureId(
		long ddmStructureId, int start, int end,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator) {

		return getPersistence().findByDDMStructureId(
			ddmStructureId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the de data definition field links where ddmStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataDefinitionFieldLinkModelImpl</code>.
	 * </p>
	 *
	 * @param ddmStructureId the ddm structure ID
	 * @param start the lower bound of the range of de data definition field links
	 * @param end the upper bound of the range of de data definition field links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching de data definition field links
	 */
	public static List<DEDataDefinitionFieldLink> findByDDMStructureId(
		long ddmStructureId, int start, int end,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByDDMStructureId(
			ddmStructureId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first de data definition field link in the ordered set where ddmStructureId = &#63;.
	 *
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data definition field link
	 * @throws NoSuchDataDefinitionFieldLinkException if a matching de data definition field link could not be found
	 */
	public static DEDataDefinitionFieldLink findByDDMStructureId_First(
			long ddmStructureId,
			OrderByComparator<DEDataDefinitionFieldLink> orderByComparator)
		throws com.liferay.data.engine.exception.
			NoSuchDataDefinitionFieldLinkException {

		return getPersistence().findByDDMStructureId_First(
			ddmStructureId, orderByComparator);
	}

	/**
	 * Returns the first de data definition field link in the ordered set where ddmStructureId = &#63;.
	 *
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data definition field link, or <code>null</code> if a matching de data definition field link could not be found
	 */
	public static DEDataDefinitionFieldLink fetchByDDMStructureId_First(
		long ddmStructureId,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator) {

		return getPersistence().fetchByDDMStructureId_First(
			ddmStructureId, orderByComparator);
	}

	/**
	 * Returns the last de data definition field link in the ordered set where ddmStructureId = &#63;.
	 *
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data definition field link
	 * @throws NoSuchDataDefinitionFieldLinkException if a matching de data definition field link could not be found
	 */
	public static DEDataDefinitionFieldLink findByDDMStructureId_Last(
			long ddmStructureId,
			OrderByComparator<DEDataDefinitionFieldLink> orderByComparator)
		throws com.liferay.data.engine.exception.
			NoSuchDataDefinitionFieldLinkException {

		return getPersistence().findByDDMStructureId_Last(
			ddmStructureId, orderByComparator);
	}

	/**
	 * Returns the last de data definition field link in the ordered set where ddmStructureId = &#63;.
	 *
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data definition field link, or <code>null</code> if a matching de data definition field link could not be found
	 */
	public static DEDataDefinitionFieldLink fetchByDDMStructureId_Last(
		long ddmStructureId,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator) {

		return getPersistence().fetchByDDMStructureId_Last(
			ddmStructureId, orderByComparator);
	}

	/**
	 * Returns the de data definition field links before and after the current de data definition field link in the ordered set where ddmStructureId = &#63;.
	 *
	 * @param deDataDefinitionFieldLinkId the primary key of the current de data definition field link
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next de data definition field link
	 * @throws NoSuchDataDefinitionFieldLinkException if a de data definition field link with the primary key could not be found
	 */
	public static DEDataDefinitionFieldLink[] findByDDMStructureId_PrevAndNext(
			long deDataDefinitionFieldLinkId, long ddmStructureId,
			OrderByComparator<DEDataDefinitionFieldLink> orderByComparator)
		throws com.liferay.data.engine.exception.
			NoSuchDataDefinitionFieldLinkException {

		return getPersistence().findByDDMStructureId_PrevAndNext(
			deDataDefinitionFieldLinkId, ddmStructureId, orderByComparator);
	}

	/**
	 * Removes all the de data definition field links where ddmStructureId = &#63; from the database.
	 *
	 * @param ddmStructureId the ddm structure ID
	 */
	public static void removeByDDMStructureId(long ddmStructureId) {
		getPersistence().removeByDDMStructureId(ddmStructureId);
	}

	/**
	 * Returns the number of de data definition field links where ddmStructureId = &#63;.
	 *
	 * @param ddmStructureId the ddm structure ID
	 * @return the number of matching de data definition field links
	 */
	public static int countByDDMStructureId(long ddmStructureId) {
		return getPersistence().countByDDMStructureId(ddmStructureId);
	}

	/**
	 * Returns all the de data definition field links where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching de data definition field links
	 */
	public static List<DEDataDefinitionFieldLink> findByC_C(
		long classNameId, long classPK) {

		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	 * Returns a range of all the de data definition field links where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataDefinitionFieldLinkModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of de data definition field links
	 * @param end the upper bound of the range of de data definition field links (not inclusive)
	 * @return the range of matching de data definition field links
	 */
	public static List<DEDataDefinitionFieldLink> findByC_C(
		long classNameId, long classPK, int start, int end) {

		return getPersistence().findByC_C(classNameId, classPK, start, end);
	}

	/**
	 * Returns an ordered range of all the de data definition field links where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataDefinitionFieldLinkModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of de data definition field links
	 * @param end the upper bound of the range of de data definition field links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching de data definition field links
	 */
	public static List<DEDataDefinitionFieldLink> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator) {

		return getPersistence().findByC_C(
			classNameId, classPK, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the de data definition field links where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataDefinitionFieldLinkModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of de data definition field links
	 * @param end the upper bound of the range of de data definition field links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching de data definition field links
	 */
	public static List<DEDataDefinitionFieldLink> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_C(
			classNameId, classPK, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first de data definition field link in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data definition field link
	 * @throws NoSuchDataDefinitionFieldLinkException if a matching de data definition field link could not be found
	 */
	public static DEDataDefinitionFieldLink findByC_C_First(
			long classNameId, long classPK,
			OrderByComparator<DEDataDefinitionFieldLink> orderByComparator)
		throws com.liferay.data.engine.exception.
			NoSuchDataDefinitionFieldLinkException {

		return getPersistence().findByC_C_First(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the first de data definition field link in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data definition field link, or <code>null</code> if a matching de data definition field link could not be found
	 */
	public static DEDataDefinitionFieldLink fetchByC_C_First(
		long classNameId, long classPK,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator) {

		return getPersistence().fetchByC_C_First(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the last de data definition field link in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data definition field link
	 * @throws NoSuchDataDefinitionFieldLinkException if a matching de data definition field link could not be found
	 */
	public static DEDataDefinitionFieldLink findByC_C_Last(
			long classNameId, long classPK,
			OrderByComparator<DEDataDefinitionFieldLink> orderByComparator)
		throws com.liferay.data.engine.exception.
			NoSuchDataDefinitionFieldLinkException {

		return getPersistence().findByC_C_Last(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the last de data definition field link in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data definition field link, or <code>null</code> if a matching de data definition field link could not be found
	 */
	public static DEDataDefinitionFieldLink fetchByC_C_Last(
		long classNameId, long classPK,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator) {

		return getPersistence().fetchByC_C_Last(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the de data definition field links before and after the current de data definition field link in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param deDataDefinitionFieldLinkId the primary key of the current de data definition field link
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next de data definition field link
	 * @throws NoSuchDataDefinitionFieldLinkException if a de data definition field link with the primary key could not be found
	 */
	public static DEDataDefinitionFieldLink[] findByC_C_PrevAndNext(
			long deDataDefinitionFieldLinkId, long classNameId, long classPK,
			OrderByComparator<DEDataDefinitionFieldLink> orderByComparator)
		throws com.liferay.data.engine.exception.
			NoSuchDataDefinitionFieldLinkException {

		return getPersistence().findByC_C_PrevAndNext(
			deDataDefinitionFieldLinkId, classNameId, classPK,
			orderByComparator);
	}

	/**
	 * Removes all the de data definition field links where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public static void removeByC_C(long classNameId, long classPK) {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	 * Returns the number of de data definition field links where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching de data definition field links
	 */
	public static int countByC_C(long classNameId, long classPK) {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	 * Returns all the de data definition field links where classNameId = &#63; and ddmStructureId = &#63; and fieldName = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param ddmStructureId the ddm structure ID
	 * @param fieldName the field name
	 * @return the matching de data definition field links
	 */
	public static List<DEDataDefinitionFieldLink> findByC_DDMSI_F(
		long classNameId, long ddmStructureId, String fieldName) {

		return getPersistence().findByC_DDMSI_F(
			classNameId, ddmStructureId, fieldName);
	}

	/**
	 * Returns a range of all the de data definition field links where classNameId = &#63; and ddmStructureId = &#63; and fieldName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataDefinitionFieldLinkModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param ddmStructureId the ddm structure ID
	 * @param fieldName the field name
	 * @param start the lower bound of the range of de data definition field links
	 * @param end the upper bound of the range of de data definition field links (not inclusive)
	 * @return the range of matching de data definition field links
	 */
	public static List<DEDataDefinitionFieldLink> findByC_DDMSI_F(
		long classNameId, long ddmStructureId, String fieldName, int start,
		int end) {

		return getPersistence().findByC_DDMSI_F(
			classNameId, ddmStructureId, fieldName, start, end);
	}

	/**
	 * Returns an ordered range of all the de data definition field links where classNameId = &#63; and ddmStructureId = &#63; and fieldName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataDefinitionFieldLinkModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param ddmStructureId the ddm structure ID
	 * @param fieldName the field name
	 * @param start the lower bound of the range of de data definition field links
	 * @param end the upper bound of the range of de data definition field links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching de data definition field links
	 */
	public static List<DEDataDefinitionFieldLink> findByC_DDMSI_F(
		long classNameId, long ddmStructureId, String fieldName, int start,
		int end,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator) {

		return getPersistence().findByC_DDMSI_F(
			classNameId, ddmStructureId, fieldName, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the de data definition field links where classNameId = &#63; and ddmStructureId = &#63; and fieldName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataDefinitionFieldLinkModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param ddmStructureId the ddm structure ID
	 * @param fieldName the field name
	 * @param start the lower bound of the range of de data definition field links
	 * @param end the upper bound of the range of de data definition field links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching de data definition field links
	 */
	public static List<DEDataDefinitionFieldLink> findByC_DDMSI_F(
		long classNameId, long ddmStructureId, String fieldName, int start,
		int end, OrderByComparator<DEDataDefinitionFieldLink> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_DDMSI_F(
			classNameId, ddmStructureId, fieldName, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first de data definition field link in the ordered set where classNameId = &#63; and ddmStructureId = &#63; and fieldName = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param ddmStructureId the ddm structure ID
	 * @param fieldName the field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data definition field link
	 * @throws NoSuchDataDefinitionFieldLinkException if a matching de data definition field link could not be found
	 */
	public static DEDataDefinitionFieldLink findByC_DDMSI_F_First(
			long classNameId, long ddmStructureId, String fieldName,
			OrderByComparator<DEDataDefinitionFieldLink> orderByComparator)
		throws com.liferay.data.engine.exception.
			NoSuchDataDefinitionFieldLinkException {

		return getPersistence().findByC_DDMSI_F_First(
			classNameId, ddmStructureId, fieldName, orderByComparator);
	}

	/**
	 * Returns the first de data definition field link in the ordered set where classNameId = &#63; and ddmStructureId = &#63; and fieldName = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param ddmStructureId the ddm structure ID
	 * @param fieldName the field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data definition field link, or <code>null</code> if a matching de data definition field link could not be found
	 */
	public static DEDataDefinitionFieldLink fetchByC_DDMSI_F_First(
		long classNameId, long ddmStructureId, String fieldName,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator) {

		return getPersistence().fetchByC_DDMSI_F_First(
			classNameId, ddmStructureId, fieldName, orderByComparator);
	}

	/**
	 * Returns the last de data definition field link in the ordered set where classNameId = &#63; and ddmStructureId = &#63; and fieldName = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param ddmStructureId the ddm structure ID
	 * @param fieldName the field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data definition field link
	 * @throws NoSuchDataDefinitionFieldLinkException if a matching de data definition field link could not be found
	 */
	public static DEDataDefinitionFieldLink findByC_DDMSI_F_Last(
			long classNameId, long ddmStructureId, String fieldName,
			OrderByComparator<DEDataDefinitionFieldLink> orderByComparator)
		throws com.liferay.data.engine.exception.
			NoSuchDataDefinitionFieldLinkException {

		return getPersistence().findByC_DDMSI_F_Last(
			classNameId, ddmStructureId, fieldName, orderByComparator);
	}

	/**
	 * Returns the last de data definition field link in the ordered set where classNameId = &#63; and ddmStructureId = &#63; and fieldName = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param ddmStructureId the ddm structure ID
	 * @param fieldName the field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data definition field link, or <code>null</code> if a matching de data definition field link could not be found
	 */
	public static DEDataDefinitionFieldLink fetchByC_DDMSI_F_Last(
		long classNameId, long ddmStructureId, String fieldName,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator) {

		return getPersistence().fetchByC_DDMSI_F_Last(
			classNameId, ddmStructureId, fieldName, orderByComparator);
	}

	/**
	 * Returns the de data definition field links before and after the current de data definition field link in the ordered set where classNameId = &#63; and ddmStructureId = &#63; and fieldName = &#63;.
	 *
	 * @param deDataDefinitionFieldLinkId the primary key of the current de data definition field link
	 * @param classNameId the class name ID
	 * @param ddmStructureId the ddm structure ID
	 * @param fieldName the field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next de data definition field link
	 * @throws NoSuchDataDefinitionFieldLinkException if a de data definition field link with the primary key could not be found
	 */
	public static DEDataDefinitionFieldLink[] findByC_DDMSI_F_PrevAndNext(
			long deDataDefinitionFieldLinkId, long classNameId,
			long ddmStructureId, String fieldName,
			OrderByComparator<DEDataDefinitionFieldLink> orderByComparator)
		throws com.liferay.data.engine.exception.
			NoSuchDataDefinitionFieldLinkException {

		return getPersistence().findByC_DDMSI_F_PrevAndNext(
			deDataDefinitionFieldLinkId, classNameId, ddmStructureId, fieldName,
			orderByComparator);
	}

	/**
	 * Removes all the de data definition field links where classNameId = &#63; and ddmStructureId = &#63; and fieldName = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param ddmStructureId the ddm structure ID
	 * @param fieldName the field name
	 */
	public static void removeByC_DDMSI_F(
		long classNameId, long ddmStructureId, String fieldName) {

		getPersistence().removeByC_DDMSI_F(
			classNameId, ddmStructureId, fieldName);
	}

	/**
	 * Returns the number of de data definition field links where classNameId = &#63; and ddmStructureId = &#63; and fieldName = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param ddmStructureId the ddm structure ID
	 * @param fieldName the field name
	 * @return the number of matching de data definition field links
	 */
	public static int countByC_DDMSI_F(
		long classNameId, long ddmStructureId, String fieldName) {

		return getPersistence().countByC_DDMSI_F(
			classNameId, ddmStructureId, fieldName);
	}

	/**
	 * Returns the de data definition field link where classNameId = &#63; and classPK = &#63; and ddmStructureId = &#63; and fieldName = &#63; or throws a <code>NoSuchDataDefinitionFieldLinkException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param ddmStructureId the ddm structure ID
	 * @param fieldName the field name
	 * @return the matching de data definition field link
	 * @throws NoSuchDataDefinitionFieldLinkException if a matching de data definition field link could not be found
	 */
	public static DEDataDefinitionFieldLink findByC_C_DDMSI_F(
			long classNameId, long classPK, long ddmStructureId,
			String fieldName)
		throws com.liferay.data.engine.exception.
			NoSuchDataDefinitionFieldLinkException {

		return getPersistence().findByC_C_DDMSI_F(
			classNameId, classPK, ddmStructureId, fieldName);
	}

	/**
	 * Returns the de data definition field link where classNameId = &#63; and classPK = &#63; and ddmStructureId = &#63; and fieldName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param ddmStructureId the ddm structure ID
	 * @param fieldName the field name
	 * @return the matching de data definition field link, or <code>null</code> if a matching de data definition field link could not be found
	 */
	public static DEDataDefinitionFieldLink fetchByC_C_DDMSI_F(
		long classNameId, long classPK, long ddmStructureId, String fieldName) {

		return getPersistence().fetchByC_C_DDMSI_F(
			classNameId, classPK, ddmStructureId, fieldName);
	}

	/**
	 * Returns the de data definition field link where classNameId = &#63; and classPK = &#63; and ddmStructureId = &#63; and fieldName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param ddmStructureId the ddm structure ID
	 * @param fieldName the field name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching de data definition field link, or <code>null</code> if a matching de data definition field link could not be found
	 */
	public static DEDataDefinitionFieldLink fetchByC_C_DDMSI_F(
		long classNameId, long classPK, long ddmStructureId, String fieldName,
		boolean useFinderCache) {

		return getPersistence().fetchByC_C_DDMSI_F(
			classNameId, classPK, ddmStructureId, fieldName, useFinderCache);
	}

	/**
	 * Removes the de data definition field link where classNameId = &#63; and classPK = &#63; and ddmStructureId = &#63; and fieldName = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param ddmStructureId the ddm structure ID
	 * @param fieldName the field name
	 * @return the de data definition field link that was removed
	 */
	public static DEDataDefinitionFieldLink removeByC_C_DDMSI_F(
			long classNameId, long classPK, long ddmStructureId,
			String fieldName)
		throws com.liferay.data.engine.exception.
			NoSuchDataDefinitionFieldLinkException {

		return getPersistence().removeByC_C_DDMSI_F(
			classNameId, classPK, ddmStructureId, fieldName);
	}

	/**
	 * Returns the number of de data definition field links where classNameId = &#63; and classPK = &#63; and ddmStructureId = &#63; and fieldName = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param ddmStructureId the ddm structure ID
	 * @param fieldName the field name
	 * @return the number of matching de data definition field links
	 */
	public static int countByC_C_DDMSI_F(
		long classNameId, long classPK, long ddmStructureId, String fieldName) {

		return getPersistence().countByC_C_DDMSI_F(
			classNameId, classPK, ddmStructureId, fieldName);
	}

	/**
	 * Caches the de data definition field link in the entity cache if it is enabled.
	 *
	 * @param deDataDefinitionFieldLink the de data definition field link
	 */
	public static void cacheResult(
		DEDataDefinitionFieldLink deDataDefinitionFieldLink) {

		getPersistence().cacheResult(deDataDefinitionFieldLink);
	}

	/**
	 * Caches the de data definition field links in the entity cache if it is enabled.
	 *
	 * @param deDataDefinitionFieldLinks the de data definition field links
	 */
	public static void cacheResult(
		List<DEDataDefinitionFieldLink> deDataDefinitionFieldLinks) {

		getPersistence().cacheResult(deDataDefinitionFieldLinks);
	}

	/**
	 * Creates a new de data definition field link with the primary key. Does not add the de data definition field link to the database.
	 *
	 * @param deDataDefinitionFieldLinkId the primary key for the new de data definition field link
	 * @return the new de data definition field link
	 */
	public static DEDataDefinitionFieldLink create(
		long deDataDefinitionFieldLinkId) {

		return getPersistence().create(deDataDefinitionFieldLinkId);
	}

	/**
	 * Removes the de data definition field link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param deDataDefinitionFieldLinkId the primary key of the de data definition field link
	 * @return the de data definition field link that was removed
	 * @throws NoSuchDataDefinitionFieldLinkException if a de data definition field link with the primary key could not be found
	 */
	public static DEDataDefinitionFieldLink remove(
			long deDataDefinitionFieldLinkId)
		throws com.liferay.data.engine.exception.
			NoSuchDataDefinitionFieldLinkException {

		return getPersistence().remove(deDataDefinitionFieldLinkId);
	}

	public static DEDataDefinitionFieldLink updateImpl(
		DEDataDefinitionFieldLink deDataDefinitionFieldLink) {

		return getPersistence().updateImpl(deDataDefinitionFieldLink);
	}

	/**
	 * Returns the de data definition field link with the primary key or throws a <code>NoSuchDataDefinitionFieldLinkException</code> if it could not be found.
	 *
	 * @param deDataDefinitionFieldLinkId the primary key of the de data definition field link
	 * @return the de data definition field link
	 * @throws NoSuchDataDefinitionFieldLinkException if a de data definition field link with the primary key could not be found
	 */
	public static DEDataDefinitionFieldLink findByPrimaryKey(
			long deDataDefinitionFieldLinkId)
		throws com.liferay.data.engine.exception.
			NoSuchDataDefinitionFieldLinkException {

		return getPersistence().findByPrimaryKey(deDataDefinitionFieldLinkId);
	}

	/**
	 * Returns the de data definition field link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param deDataDefinitionFieldLinkId the primary key of the de data definition field link
	 * @return the de data definition field link, or <code>null</code> if a de data definition field link with the primary key could not be found
	 */
	public static DEDataDefinitionFieldLink fetchByPrimaryKey(
		long deDataDefinitionFieldLinkId) {

		return getPersistence().fetchByPrimaryKey(deDataDefinitionFieldLinkId);
	}

	/**
	 * Returns all the de data definition field links.
	 *
	 * @return the de data definition field links
	 */
	public static List<DEDataDefinitionFieldLink> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the de data definition field links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataDefinitionFieldLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of de data definition field links
	 * @param end the upper bound of the range of de data definition field links (not inclusive)
	 * @return the range of de data definition field links
	 */
	public static List<DEDataDefinitionFieldLink> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the de data definition field links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataDefinitionFieldLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of de data definition field links
	 * @param end the upper bound of the range of de data definition field links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of de data definition field links
	 */
	public static List<DEDataDefinitionFieldLink> findAll(
		int start, int end,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the de data definition field links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataDefinitionFieldLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of de data definition field links
	 * @param end the upper bound of the range of de data definition field links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of de data definition field links
	 */
	public static List<DEDataDefinitionFieldLink> findAll(
		int start, int end,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the de data definition field links from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of de data definition field links.
	 *
	 * @return the number of de data definition field links
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static DEDataDefinitionFieldLinkPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<DEDataDefinitionFieldLinkPersistence,
		 DEDataDefinitionFieldLinkPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			DEDataDefinitionFieldLinkPersistence.class);

		ServiceTracker
			<DEDataDefinitionFieldLinkPersistence,
			 DEDataDefinitionFieldLinkPersistence> serviceTracker =
				new ServiceTracker
					<DEDataDefinitionFieldLinkPersistence,
					 DEDataDefinitionFieldLinkPersistence>(
						 bundle.getBundleContext(),
						 DEDataDefinitionFieldLinkPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}
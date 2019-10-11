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

package com.liferay.layout.page.template.service.persistence;

import com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel;
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
 * The persistence utility for the layout page template structure rel service. This utility wraps <code>com.liferay.layout.page.template.service.persistence.impl.LayoutPageTemplateStructureRelPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateStructureRelPersistence
 * @generated
 */
public class LayoutPageTemplateStructureRelUtil {

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
		LayoutPageTemplateStructureRel layoutPageTemplateStructureRel) {

		getPersistence().clearCache(layoutPageTemplateStructureRel);
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
	public static Map<Serializable, LayoutPageTemplateStructureRel>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<LayoutPageTemplateStructureRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LayoutPageTemplateStructureRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LayoutPageTemplateStructureRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<LayoutPageTemplateStructureRel> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static LayoutPageTemplateStructureRel update(
		LayoutPageTemplateStructureRel layoutPageTemplateStructureRel) {

		return getPersistence().update(layoutPageTemplateStructureRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static LayoutPageTemplateStructureRel update(
		LayoutPageTemplateStructureRel layoutPageTemplateStructureRel,
		ServiceContext serviceContext) {

		return getPersistence().update(
			layoutPageTemplateStructureRel, serviceContext);
	}

	/**
	 * Returns all the layout page template structure rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching layout page template structure rels
	 */
	public static List<LayoutPageTemplateStructureRel> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the layout page template structure rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout page template structure rels
	 * @param end the upper bound of the range of layout page template structure rels (not inclusive)
	 * @return the range of matching layout page template structure rels
	 */
	public static List<LayoutPageTemplateStructureRel> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the layout page template structure rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout page template structure rels
	 * @param end the upper bound of the range of layout page template structure rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template structure rels
	 */
	public static List<LayoutPageTemplateStructureRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LayoutPageTemplateStructureRel> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout page template structure rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout page template structure rels
	 * @param end the upper bound of the range of layout page template structure rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout page template structure rels
	 */
	public static List<LayoutPageTemplateStructureRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LayoutPageTemplateStructureRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first layout page template structure rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template structure rel
	 * @throws NoSuchPageTemplateStructureRelException if a matching layout page template structure rel could not be found
	 */
	public static LayoutPageTemplateStructureRel findByUuid_First(
			String uuid,
			OrderByComparator<LayoutPageTemplateStructureRel> orderByComparator)
		throws com.liferay.layout.page.template.exception.
			NoSuchPageTemplateStructureRelException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first layout page template structure rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template structure rel, or <code>null</code> if a matching layout page template structure rel could not be found
	 */
	public static LayoutPageTemplateStructureRel fetchByUuid_First(
		String uuid,
		OrderByComparator<LayoutPageTemplateStructureRel> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last layout page template structure rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template structure rel
	 * @throws NoSuchPageTemplateStructureRelException if a matching layout page template structure rel could not be found
	 */
	public static LayoutPageTemplateStructureRel findByUuid_Last(
			String uuid,
			OrderByComparator<LayoutPageTemplateStructureRel> orderByComparator)
		throws com.liferay.layout.page.template.exception.
			NoSuchPageTemplateStructureRelException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last layout page template structure rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template structure rel, or <code>null</code> if a matching layout page template structure rel could not be found
	 */
	public static LayoutPageTemplateStructureRel fetchByUuid_Last(
		String uuid,
		OrderByComparator<LayoutPageTemplateStructureRel> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the layout page template structure rels before and after the current layout page template structure rel in the ordered set where uuid = &#63;.
	 *
	 * @param layoutPageTemplateStructureRelId the primary key of the current layout page template structure rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template structure rel
	 * @throws NoSuchPageTemplateStructureRelException if a layout page template structure rel with the primary key could not be found
	 */
	public static LayoutPageTemplateStructureRel[] findByUuid_PrevAndNext(
			long layoutPageTemplateStructureRelId, String uuid,
			OrderByComparator<LayoutPageTemplateStructureRel> orderByComparator)
		throws com.liferay.layout.page.template.exception.
			NoSuchPageTemplateStructureRelException {

		return getPersistence().findByUuid_PrevAndNext(
			layoutPageTemplateStructureRelId, uuid, orderByComparator);
	}

	/**
	 * Removes all the layout page template structure rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of layout page template structure rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching layout page template structure rels
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the layout page template structure rel where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchPageTemplateStructureRelException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout page template structure rel
	 * @throws NoSuchPageTemplateStructureRelException if a matching layout page template structure rel could not be found
	 */
	public static LayoutPageTemplateStructureRel findByUUID_G(
			String uuid, long groupId)
		throws com.liferay.layout.page.template.exception.
			NoSuchPageTemplateStructureRelException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the layout page template structure rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout page template structure rel, or <code>null</code> if a matching layout page template structure rel could not be found
	 */
	public static LayoutPageTemplateStructureRel fetchByUUID_G(
		String uuid, long groupId) {

		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the layout page template structure rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout page template structure rel, or <code>null</code> if a matching layout page template structure rel could not be found
	 */
	public static LayoutPageTemplateStructureRel fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the layout page template structure rel where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the layout page template structure rel that was removed
	 */
	public static LayoutPageTemplateStructureRel removeByUUID_G(
			String uuid, long groupId)
		throws com.liferay.layout.page.template.exception.
			NoSuchPageTemplateStructureRelException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of layout page template structure rels where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching layout page template structure rels
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the layout page template structure rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching layout page template structure rels
	 */
	public static List<LayoutPageTemplateStructureRel> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the layout page template structure rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout page template structure rels
	 * @param end the upper bound of the range of layout page template structure rels (not inclusive)
	 * @return the range of matching layout page template structure rels
	 */
	public static List<LayoutPageTemplateStructureRel> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the layout page template structure rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout page template structure rels
	 * @param end the upper bound of the range of layout page template structure rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template structure rels
	 */
	public static List<LayoutPageTemplateStructureRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutPageTemplateStructureRel> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout page template structure rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout page template structure rels
	 * @param end the upper bound of the range of layout page template structure rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout page template structure rels
	 */
	public static List<LayoutPageTemplateStructureRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutPageTemplateStructureRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first layout page template structure rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template structure rel
	 * @throws NoSuchPageTemplateStructureRelException if a matching layout page template structure rel could not be found
	 */
	public static LayoutPageTemplateStructureRel findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<LayoutPageTemplateStructureRel> orderByComparator)
		throws com.liferay.layout.page.template.exception.
			NoSuchPageTemplateStructureRelException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first layout page template structure rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template structure rel, or <code>null</code> if a matching layout page template structure rel could not be found
	 */
	public static LayoutPageTemplateStructureRel fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<LayoutPageTemplateStructureRel> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last layout page template structure rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template structure rel
	 * @throws NoSuchPageTemplateStructureRelException if a matching layout page template structure rel could not be found
	 */
	public static LayoutPageTemplateStructureRel findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<LayoutPageTemplateStructureRel> orderByComparator)
		throws com.liferay.layout.page.template.exception.
			NoSuchPageTemplateStructureRelException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last layout page template structure rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template structure rel, or <code>null</code> if a matching layout page template structure rel could not be found
	 */
	public static LayoutPageTemplateStructureRel fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<LayoutPageTemplateStructureRel> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the layout page template structure rels before and after the current layout page template structure rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param layoutPageTemplateStructureRelId the primary key of the current layout page template structure rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template structure rel
	 * @throws NoSuchPageTemplateStructureRelException if a layout page template structure rel with the primary key could not be found
	 */
	public static LayoutPageTemplateStructureRel[] findByUuid_C_PrevAndNext(
			long layoutPageTemplateStructureRelId, String uuid, long companyId,
			OrderByComparator<LayoutPageTemplateStructureRel> orderByComparator)
		throws com.liferay.layout.page.template.exception.
			NoSuchPageTemplateStructureRelException {

		return getPersistence().findByUuid_C_PrevAndNext(
			layoutPageTemplateStructureRelId, uuid, companyId,
			orderByComparator);
	}

	/**
	 * Removes all the layout page template structure rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of layout page template structure rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching layout page template structure rels
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the layout page template structure rels where layoutPageTemplateStructureId = &#63;.
	 *
	 * @param layoutPageTemplateStructureId the layout page template structure ID
	 * @return the matching layout page template structure rels
	 */
	public static List<LayoutPageTemplateStructureRel>
		findByLayoutPageTemplateStructureId(
			long layoutPageTemplateStructureId) {

		return getPersistence().findByLayoutPageTemplateStructureId(
			layoutPageTemplateStructureId);
	}

	/**
	 * Returns a range of all the layout page template structure rels where layoutPageTemplateStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureRelModelImpl</code>.
	 * </p>
	 *
	 * @param layoutPageTemplateStructureId the layout page template structure ID
	 * @param start the lower bound of the range of layout page template structure rels
	 * @param end the upper bound of the range of layout page template structure rels (not inclusive)
	 * @return the range of matching layout page template structure rels
	 */
	public static List<LayoutPageTemplateStructureRel>
		findByLayoutPageTemplateStructureId(
			long layoutPageTemplateStructureId, int start, int end) {

		return getPersistence().findByLayoutPageTemplateStructureId(
			layoutPageTemplateStructureId, start, end);
	}

	/**
	 * Returns an ordered range of all the layout page template structure rels where layoutPageTemplateStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureRelModelImpl</code>.
	 * </p>
	 *
	 * @param layoutPageTemplateStructureId the layout page template structure ID
	 * @param start the lower bound of the range of layout page template structure rels
	 * @param end the upper bound of the range of layout page template structure rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template structure rels
	 */
	public static List<LayoutPageTemplateStructureRel>
		findByLayoutPageTemplateStructureId(
			long layoutPageTemplateStructureId, int start, int end,
			OrderByComparator<LayoutPageTemplateStructureRel>
				orderByComparator) {

		return getPersistence().findByLayoutPageTemplateStructureId(
			layoutPageTemplateStructureId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout page template structure rels where layoutPageTemplateStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureRelModelImpl</code>.
	 * </p>
	 *
	 * @param layoutPageTemplateStructureId the layout page template structure ID
	 * @param start the lower bound of the range of layout page template structure rels
	 * @param end the upper bound of the range of layout page template structure rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout page template structure rels
	 */
	public static List<LayoutPageTemplateStructureRel>
		findByLayoutPageTemplateStructureId(
			long layoutPageTemplateStructureId, int start, int end,
			OrderByComparator<LayoutPageTemplateStructureRel> orderByComparator,
			boolean useFinderCache) {

		return getPersistence().findByLayoutPageTemplateStructureId(
			layoutPageTemplateStructureId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first layout page template structure rel in the ordered set where layoutPageTemplateStructureId = &#63;.
	 *
	 * @param layoutPageTemplateStructureId the layout page template structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template structure rel
	 * @throws NoSuchPageTemplateStructureRelException if a matching layout page template structure rel could not be found
	 */
	public static LayoutPageTemplateStructureRel
			findByLayoutPageTemplateStructureId_First(
				long layoutPageTemplateStructureId,
				OrderByComparator<LayoutPageTemplateStructureRel>
					orderByComparator)
		throws com.liferay.layout.page.template.exception.
			NoSuchPageTemplateStructureRelException {

		return getPersistence().findByLayoutPageTemplateStructureId_First(
			layoutPageTemplateStructureId, orderByComparator);
	}

	/**
	 * Returns the first layout page template structure rel in the ordered set where layoutPageTemplateStructureId = &#63;.
	 *
	 * @param layoutPageTemplateStructureId the layout page template structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template structure rel, or <code>null</code> if a matching layout page template structure rel could not be found
	 */
	public static LayoutPageTemplateStructureRel
		fetchByLayoutPageTemplateStructureId_First(
			long layoutPageTemplateStructureId,
			OrderByComparator<LayoutPageTemplateStructureRel>
				orderByComparator) {

		return getPersistence().fetchByLayoutPageTemplateStructureId_First(
			layoutPageTemplateStructureId, orderByComparator);
	}

	/**
	 * Returns the last layout page template structure rel in the ordered set where layoutPageTemplateStructureId = &#63;.
	 *
	 * @param layoutPageTemplateStructureId the layout page template structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template structure rel
	 * @throws NoSuchPageTemplateStructureRelException if a matching layout page template structure rel could not be found
	 */
	public static LayoutPageTemplateStructureRel
			findByLayoutPageTemplateStructureId_Last(
				long layoutPageTemplateStructureId,
				OrderByComparator<LayoutPageTemplateStructureRel>
					orderByComparator)
		throws com.liferay.layout.page.template.exception.
			NoSuchPageTemplateStructureRelException {

		return getPersistence().findByLayoutPageTemplateStructureId_Last(
			layoutPageTemplateStructureId, orderByComparator);
	}

	/**
	 * Returns the last layout page template structure rel in the ordered set where layoutPageTemplateStructureId = &#63;.
	 *
	 * @param layoutPageTemplateStructureId the layout page template structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template structure rel, or <code>null</code> if a matching layout page template structure rel could not be found
	 */
	public static LayoutPageTemplateStructureRel
		fetchByLayoutPageTemplateStructureId_Last(
			long layoutPageTemplateStructureId,
			OrderByComparator<LayoutPageTemplateStructureRel>
				orderByComparator) {

		return getPersistence().fetchByLayoutPageTemplateStructureId_Last(
			layoutPageTemplateStructureId, orderByComparator);
	}

	/**
	 * Returns the layout page template structure rels before and after the current layout page template structure rel in the ordered set where layoutPageTemplateStructureId = &#63;.
	 *
	 * @param layoutPageTemplateStructureRelId the primary key of the current layout page template structure rel
	 * @param layoutPageTemplateStructureId the layout page template structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template structure rel
	 * @throws NoSuchPageTemplateStructureRelException if a layout page template structure rel with the primary key could not be found
	 */
	public static LayoutPageTemplateStructureRel[]
			findByLayoutPageTemplateStructureId_PrevAndNext(
				long layoutPageTemplateStructureRelId,
				long layoutPageTemplateStructureId,
				OrderByComparator<LayoutPageTemplateStructureRel>
					orderByComparator)
		throws com.liferay.layout.page.template.exception.
			NoSuchPageTemplateStructureRelException {

		return getPersistence().findByLayoutPageTemplateStructureId_PrevAndNext(
			layoutPageTemplateStructureRelId, layoutPageTemplateStructureId,
			orderByComparator);
	}

	/**
	 * Removes all the layout page template structure rels where layoutPageTemplateStructureId = &#63; from the database.
	 *
	 * @param layoutPageTemplateStructureId the layout page template structure ID
	 */
	public static void removeByLayoutPageTemplateStructureId(
		long layoutPageTemplateStructureId) {

		getPersistence().removeByLayoutPageTemplateStructureId(
			layoutPageTemplateStructureId);
	}

	/**
	 * Returns the number of layout page template structure rels where layoutPageTemplateStructureId = &#63;.
	 *
	 * @param layoutPageTemplateStructureId the layout page template structure ID
	 * @return the number of matching layout page template structure rels
	 */
	public static int countByLayoutPageTemplateStructureId(
		long layoutPageTemplateStructureId) {

		return getPersistence().countByLayoutPageTemplateStructureId(
			layoutPageTemplateStructureId);
	}

	/**
	 * Returns all the layout page template structure rels where segmentsExperienceId = &#63;.
	 *
	 * @param segmentsExperienceId the segments experience ID
	 * @return the matching layout page template structure rels
	 */
	public static List<LayoutPageTemplateStructureRel>
		findBySegmentsExperienceId(long segmentsExperienceId) {

		return getPersistence().findBySegmentsExperienceId(
			segmentsExperienceId);
	}

	/**
	 * Returns a range of all the layout page template structure rels where segmentsExperienceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureRelModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsExperienceId the segments experience ID
	 * @param start the lower bound of the range of layout page template structure rels
	 * @param end the upper bound of the range of layout page template structure rels (not inclusive)
	 * @return the range of matching layout page template structure rels
	 */
	public static List<LayoutPageTemplateStructureRel>
		findBySegmentsExperienceId(
			long segmentsExperienceId, int start, int end) {

		return getPersistence().findBySegmentsExperienceId(
			segmentsExperienceId, start, end);
	}

	/**
	 * Returns an ordered range of all the layout page template structure rels where segmentsExperienceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureRelModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsExperienceId the segments experience ID
	 * @param start the lower bound of the range of layout page template structure rels
	 * @param end the upper bound of the range of layout page template structure rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template structure rels
	 */
	public static List<LayoutPageTemplateStructureRel>
		findBySegmentsExperienceId(
			long segmentsExperienceId, int start, int end,
			OrderByComparator<LayoutPageTemplateStructureRel>
				orderByComparator) {

		return getPersistence().findBySegmentsExperienceId(
			segmentsExperienceId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout page template structure rels where segmentsExperienceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureRelModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsExperienceId the segments experience ID
	 * @param start the lower bound of the range of layout page template structure rels
	 * @param end the upper bound of the range of layout page template structure rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout page template structure rels
	 */
	public static List<LayoutPageTemplateStructureRel>
		findBySegmentsExperienceId(
			long segmentsExperienceId, int start, int end,
			OrderByComparator<LayoutPageTemplateStructureRel> orderByComparator,
			boolean useFinderCache) {

		return getPersistence().findBySegmentsExperienceId(
			segmentsExperienceId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first layout page template structure rel in the ordered set where segmentsExperienceId = &#63;.
	 *
	 * @param segmentsExperienceId the segments experience ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template structure rel
	 * @throws NoSuchPageTemplateStructureRelException if a matching layout page template structure rel could not be found
	 */
	public static LayoutPageTemplateStructureRel
			findBySegmentsExperienceId_First(
				long segmentsExperienceId,
				OrderByComparator<LayoutPageTemplateStructureRel>
					orderByComparator)
		throws com.liferay.layout.page.template.exception.
			NoSuchPageTemplateStructureRelException {

		return getPersistence().findBySegmentsExperienceId_First(
			segmentsExperienceId, orderByComparator);
	}

	/**
	 * Returns the first layout page template structure rel in the ordered set where segmentsExperienceId = &#63;.
	 *
	 * @param segmentsExperienceId the segments experience ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template structure rel, or <code>null</code> if a matching layout page template structure rel could not be found
	 */
	public static LayoutPageTemplateStructureRel
		fetchBySegmentsExperienceId_First(
			long segmentsExperienceId,
			OrderByComparator<LayoutPageTemplateStructureRel>
				orderByComparator) {

		return getPersistence().fetchBySegmentsExperienceId_First(
			segmentsExperienceId, orderByComparator);
	}

	/**
	 * Returns the last layout page template structure rel in the ordered set where segmentsExperienceId = &#63;.
	 *
	 * @param segmentsExperienceId the segments experience ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template structure rel
	 * @throws NoSuchPageTemplateStructureRelException if a matching layout page template structure rel could not be found
	 */
	public static LayoutPageTemplateStructureRel
			findBySegmentsExperienceId_Last(
				long segmentsExperienceId,
				OrderByComparator<LayoutPageTemplateStructureRel>
					orderByComparator)
		throws com.liferay.layout.page.template.exception.
			NoSuchPageTemplateStructureRelException {

		return getPersistence().findBySegmentsExperienceId_Last(
			segmentsExperienceId, orderByComparator);
	}

	/**
	 * Returns the last layout page template structure rel in the ordered set where segmentsExperienceId = &#63;.
	 *
	 * @param segmentsExperienceId the segments experience ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template structure rel, or <code>null</code> if a matching layout page template structure rel could not be found
	 */
	public static LayoutPageTemplateStructureRel
		fetchBySegmentsExperienceId_Last(
			long segmentsExperienceId,
			OrderByComparator<LayoutPageTemplateStructureRel>
				orderByComparator) {

		return getPersistence().fetchBySegmentsExperienceId_Last(
			segmentsExperienceId, orderByComparator);
	}

	/**
	 * Returns the layout page template structure rels before and after the current layout page template structure rel in the ordered set where segmentsExperienceId = &#63;.
	 *
	 * @param layoutPageTemplateStructureRelId the primary key of the current layout page template structure rel
	 * @param segmentsExperienceId the segments experience ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template structure rel
	 * @throws NoSuchPageTemplateStructureRelException if a layout page template structure rel with the primary key could not be found
	 */
	public static LayoutPageTemplateStructureRel[]
			findBySegmentsExperienceId_PrevAndNext(
				long layoutPageTemplateStructureRelId,
				long segmentsExperienceId,
				OrderByComparator<LayoutPageTemplateStructureRel>
					orderByComparator)
		throws com.liferay.layout.page.template.exception.
			NoSuchPageTemplateStructureRelException {

		return getPersistence().findBySegmentsExperienceId_PrevAndNext(
			layoutPageTemplateStructureRelId, segmentsExperienceId,
			orderByComparator);
	}

	/**
	 * Removes all the layout page template structure rels where segmentsExperienceId = &#63; from the database.
	 *
	 * @param segmentsExperienceId the segments experience ID
	 */
	public static void removeBySegmentsExperienceId(long segmentsExperienceId) {
		getPersistence().removeBySegmentsExperienceId(segmentsExperienceId);
	}

	/**
	 * Returns the number of layout page template structure rels where segmentsExperienceId = &#63;.
	 *
	 * @param segmentsExperienceId the segments experience ID
	 * @return the number of matching layout page template structure rels
	 */
	public static int countBySegmentsExperienceId(long segmentsExperienceId) {
		return getPersistence().countBySegmentsExperienceId(
			segmentsExperienceId);
	}

	/**
	 * Returns the layout page template structure rel where layoutPageTemplateStructureId = &#63; and segmentsExperienceId = &#63; or throws a <code>NoSuchPageTemplateStructureRelException</code> if it could not be found.
	 *
	 * @param layoutPageTemplateStructureId the layout page template structure ID
	 * @param segmentsExperienceId the segments experience ID
	 * @return the matching layout page template structure rel
	 * @throws NoSuchPageTemplateStructureRelException if a matching layout page template structure rel could not be found
	 */
	public static LayoutPageTemplateStructureRel findByL_S(
			long layoutPageTemplateStructureId, long segmentsExperienceId)
		throws com.liferay.layout.page.template.exception.
			NoSuchPageTemplateStructureRelException {

		return getPersistence().findByL_S(
			layoutPageTemplateStructureId, segmentsExperienceId);
	}

	/**
	 * Returns the layout page template structure rel where layoutPageTemplateStructureId = &#63; and segmentsExperienceId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param layoutPageTemplateStructureId the layout page template structure ID
	 * @param segmentsExperienceId the segments experience ID
	 * @return the matching layout page template structure rel, or <code>null</code> if a matching layout page template structure rel could not be found
	 */
	public static LayoutPageTemplateStructureRel fetchByL_S(
		long layoutPageTemplateStructureId, long segmentsExperienceId) {

		return getPersistence().fetchByL_S(
			layoutPageTemplateStructureId, segmentsExperienceId);
	}

	/**
	 * Returns the layout page template structure rel where layoutPageTemplateStructureId = &#63; and segmentsExperienceId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param layoutPageTemplateStructureId the layout page template structure ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout page template structure rel, or <code>null</code> if a matching layout page template structure rel could not be found
	 */
	public static LayoutPageTemplateStructureRel fetchByL_S(
		long layoutPageTemplateStructureId, long segmentsExperienceId,
		boolean useFinderCache) {

		return getPersistence().fetchByL_S(
			layoutPageTemplateStructureId, segmentsExperienceId,
			useFinderCache);
	}

	/**
	 * Removes the layout page template structure rel where layoutPageTemplateStructureId = &#63; and segmentsExperienceId = &#63; from the database.
	 *
	 * @param layoutPageTemplateStructureId the layout page template structure ID
	 * @param segmentsExperienceId the segments experience ID
	 * @return the layout page template structure rel that was removed
	 */
	public static LayoutPageTemplateStructureRel removeByL_S(
			long layoutPageTemplateStructureId, long segmentsExperienceId)
		throws com.liferay.layout.page.template.exception.
			NoSuchPageTemplateStructureRelException {

		return getPersistence().removeByL_S(
			layoutPageTemplateStructureId, segmentsExperienceId);
	}

	/**
	 * Returns the number of layout page template structure rels where layoutPageTemplateStructureId = &#63; and segmentsExperienceId = &#63;.
	 *
	 * @param layoutPageTemplateStructureId the layout page template structure ID
	 * @param segmentsExperienceId the segments experience ID
	 * @return the number of matching layout page template structure rels
	 */
	public static int countByL_S(
		long layoutPageTemplateStructureId, long segmentsExperienceId) {

		return getPersistence().countByL_S(
			layoutPageTemplateStructureId, segmentsExperienceId);
	}

	/**
	 * Caches the layout page template structure rel in the entity cache if it is enabled.
	 *
	 * @param layoutPageTemplateStructureRel the layout page template structure rel
	 */
	public static void cacheResult(
		LayoutPageTemplateStructureRel layoutPageTemplateStructureRel) {

		getPersistence().cacheResult(layoutPageTemplateStructureRel);
	}

	/**
	 * Caches the layout page template structure rels in the entity cache if it is enabled.
	 *
	 * @param layoutPageTemplateStructureRels the layout page template structure rels
	 */
	public static void cacheResult(
		List<LayoutPageTemplateStructureRel> layoutPageTemplateStructureRels) {

		getPersistence().cacheResult(layoutPageTemplateStructureRels);
	}

	/**
	 * Creates a new layout page template structure rel with the primary key. Does not add the layout page template structure rel to the database.
	 *
	 * @param layoutPageTemplateStructureRelId the primary key for the new layout page template structure rel
	 * @return the new layout page template structure rel
	 */
	public static LayoutPageTemplateStructureRel create(
		long layoutPageTemplateStructureRelId) {

		return getPersistence().create(layoutPageTemplateStructureRelId);
	}

	/**
	 * Removes the layout page template structure rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPageTemplateStructureRelId the primary key of the layout page template structure rel
	 * @return the layout page template structure rel that was removed
	 * @throws NoSuchPageTemplateStructureRelException if a layout page template structure rel with the primary key could not be found
	 */
	public static LayoutPageTemplateStructureRel remove(
			long layoutPageTemplateStructureRelId)
		throws com.liferay.layout.page.template.exception.
			NoSuchPageTemplateStructureRelException {

		return getPersistence().remove(layoutPageTemplateStructureRelId);
	}

	public static LayoutPageTemplateStructureRel updateImpl(
		LayoutPageTemplateStructureRel layoutPageTemplateStructureRel) {

		return getPersistence().updateImpl(layoutPageTemplateStructureRel);
	}

	/**
	 * Returns the layout page template structure rel with the primary key or throws a <code>NoSuchPageTemplateStructureRelException</code> if it could not be found.
	 *
	 * @param layoutPageTemplateStructureRelId the primary key of the layout page template structure rel
	 * @return the layout page template structure rel
	 * @throws NoSuchPageTemplateStructureRelException if a layout page template structure rel with the primary key could not be found
	 */
	public static LayoutPageTemplateStructureRel findByPrimaryKey(
			long layoutPageTemplateStructureRelId)
		throws com.liferay.layout.page.template.exception.
			NoSuchPageTemplateStructureRelException {

		return getPersistence().findByPrimaryKey(
			layoutPageTemplateStructureRelId);
	}

	/**
	 * Returns the layout page template structure rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutPageTemplateStructureRelId the primary key of the layout page template structure rel
	 * @return the layout page template structure rel, or <code>null</code> if a layout page template structure rel with the primary key could not be found
	 */
	public static LayoutPageTemplateStructureRel fetchByPrimaryKey(
		long layoutPageTemplateStructureRelId) {

		return getPersistence().fetchByPrimaryKey(
			layoutPageTemplateStructureRelId);
	}

	/**
	 * Returns all the layout page template structure rels.
	 *
	 * @return the layout page template structure rels
	 */
	public static List<LayoutPageTemplateStructureRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the layout page template structure rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template structure rels
	 * @param end the upper bound of the range of layout page template structure rels (not inclusive)
	 * @return the range of layout page template structure rels
	 */
	public static List<LayoutPageTemplateStructureRel> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the layout page template structure rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template structure rels
	 * @param end the upper bound of the range of layout page template structure rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout page template structure rels
	 */
	public static List<LayoutPageTemplateStructureRel> findAll(
		int start, int end,
		OrderByComparator<LayoutPageTemplateStructureRel> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout page template structure rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template structure rels
	 * @param end the upper bound of the range of layout page template structure rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of layout page template structure rels
	 */
	public static List<LayoutPageTemplateStructureRel> findAll(
		int start, int end,
		OrderByComparator<LayoutPageTemplateStructureRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the layout page template structure rels from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of layout page template structure rels.
	 *
	 * @return the number of layout page template structure rels
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static LayoutPageTemplateStructureRelPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<LayoutPageTemplateStructureRelPersistence,
		 LayoutPageTemplateStructureRelPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			LayoutPageTemplateStructureRelPersistence.class);

		ServiceTracker
			<LayoutPageTemplateStructureRelPersistence,
			 LayoutPageTemplateStructureRelPersistence> serviceTracker =
				new ServiceTracker
					<LayoutPageTemplateStructureRelPersistence,
					 LayoutPageTemplateStructureRelPersistence>(
						 bundle.getBundleContext(),
						 LayoutPageTemplateStructureRelPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}
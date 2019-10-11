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

package com.liferay.dynamic.data.mapping.service.persistence;

import com.liferay.dynamic.data.mapping.model.DDMStructureLink;
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
 * The persistence utility for the ddm structure link service. This utility wraps <code>com.liferay.dynamic.data.mapping.service.persistence.impl.DDMStructureLinkPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureLinkPersistence
 * @generated
 */
public class DDMStructureLinkUtil {

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
	public static void clearCache(DDMStructureLink ddmStructureLink) {
		getPersistence().clearCache(ddmStructureLink);
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
	public static Map<Serializable, DDMStructureLink> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DDMStructureLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DDMStructureLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DDMStructureLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DDMStructureLink> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static DDMStructureLink update(DDMStructureLink ddmStructureLink) {
		return getPersistence().update(ddmStructureLink);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static DDMStructureLink update(
		DDMStructureLink ddmStructureLink, ServiceContext serviceContext) {

		return getPersistence().update(ddmStructureLink, serviceContext);
	}

	/**
	 * Returns all the ddm structure links where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @return the matching ddm structure links
	 */
	public static List<DDMStructureLink> findByClassNameId(long classNameId) {
		return getPersistence().findByClassNameId(classNameId);
	}

	/**
	 * Returns a range of all the ddm structure links where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLinkModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of ddm structure links
	 * @param end the upper bound of the range of ddm structure links (not inclusive)
	 * @return the range of matching ddm structure links
	 */
	public static List<DDMStructureLink> findByClassNameId(
		long classNameId, int start, int end) {

		return getPersistence().findByClassNameId(classNameId, start, end);
	}

	/**
	 * Returns an ordered range of all the ddm structure links where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLinkModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of ddm structure links
	 * @param end the upper bound of the range of ddm structure links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm structure links
	 */
	public static List<DDMStructureLink> findByClassNameId(
		long classNameId, int start, int end,
		OrderByComparator<DDMStructureLink> orderByComparator) {

		return getPersistence().findByClassNameId(
			classNameId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm structure links where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLinkModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of ddm structure links
	 * @param end the upper bound of the range of ddm structure links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm structure links
	 */
	public static List<DDMStructureLink> findByClassNameId(
		long classNameId, int start, int end,
		OrderByComparator<DDMStructureLink> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByClassNameId(
			classNameId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ddm structure link in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure link
	 * @throws NoSuchStructureLinkException if a matching ddm structure link could not be found
	 */
	public static DDMStructureLink findByClassNameId_First(
			long classNameId,
			OrderByComparator<DDMStructureLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureLinkException {

		return getPersistence().findByClassNameId_First(
			classNameId, orderByComparator);
	}

	/**
	 * Returns the first ddm structure link in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure link, or <code>null</code> if a matching ddm structure link could not be found
	 */
	public static DDMStructureLink fetchByClassNameId_First(
		long classNameId,
		OrderByComparator<DDMStructureLink> orderByComparator) {

		return getPersistence().fetchByClassNameId_First(
			classNameId, orderByComparator);
	}

	/**
	 * Returns the last ddm structure link in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure link
	 * @throws NoSuchStructureLinkException if a matching ddm structure link could not be found
	 */
	public static DDMStructureLink findByClassNameId_Last(
			long classNameId,
			OrderByComparator<DDMStructureLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureLinkException {

		return getPersistence().findByClassNameId_Last(
			classNameId, orderByComparator);
	}

	/**
	 * Returns the last ddm structure link in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure link, or <code>null</code> if a matching ddm structure link could not be found
	 */
	public static DDMStructureLink fetchByClassNameId_Last(
		long classNameId,
		OrderByComparator<DDMStructureLink> orderByComparator) {

		return getPersistence().fetchByClassNameId_Last(
			classNameId, orderByComparator);
	}

	/**
	 * Returns the ddm structure links before and after the current ddm structure link in the ordered set where classNameId = &#63;.
	 *
	 * @param structureLinkId the primary key of the current ddm structure link
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm structure link
	 * @throws NoSuchStructureLinkException if a ddm structure link with the primary key could not be found
	 */
	public static DDMStructureLink[] findByClassNameId_PrevAndNext(
			long structureLinkId, long classNameId,
			OrderByComparator<DDMStructureLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureLinkException {

		return getPersistence().findByClassNameId_PrevAndNext(
			structureLinkId, classNameId, orderByComparator);
	}

	/**
	 * Removes all the ddm structure links where classNameId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 */
	public static void removeByClassNameId(long classNameId) {
		getPersistence().removeByClassNameId(classNameId);
	}

	/**
	 * Returns the number of ddm structure links where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @return the number of matching ddm structure links
	 */
	public static int countByClassNameId(long classNameId) {
		return getPersistence().countByClassNameId(classNameId);
	}

	/**
	 * Returns all the ddm structure links where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @return the matching ddm structure links
	 */
	public static List<DDMStructureLink> findByStructureId(long structureId) {
		return getPersistence().findByStructureId(structureId);
	}

	/**
	 * Returns a range of all the ddm structure links where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLinkModelImpl</code>.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of ddm structure links
	 * @param end the upper bound of the range of ddm structure links (not inclusive)
	 * @return the range of matching ddm structure links
	 */
	public static List<DDMStructureLink> findByStructureId(
		long structureId, int start, int end) {

		return getPersistence().findByStructureId(structureId, start, end);
	}

	/**
	 * Returns an ordered range of all the ddm structure links where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLinkModelImpl</code>.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of ddm structure links
	 * @param end the upper bound of the range of ddm structure links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm structure links
	 */
	public static List<DDMStructureLink> findByStructureId(
		long structureId, int start, int end,
		OrderByComparator<DDMStructureLink> orderByComparator) {

		return getPersistence().findByStructureId(
			structureId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm structure links where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLinkModelImpl</code>.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of ddm structure links
	 * @param end the upper bound of the range of ddm structure links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm structure links
	 */
	public static List<DDMStructureLink> findByStructureId(
		long structureId, int start, int end,
		OrderByComparator<DDMStructureLink> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByStructureId(
			structureId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ddm structure link in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure link
	 * @throws NoSuchStructureLinkException if a matching ddm structure link could not be found
	 */
	public static DDMStructureLink findByStructureId_First(
			long structureId,
			OrderByComparator<DDMStructureLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureLinkException {

		return getPersistence().findByStructureId_First(
			structureId, orderByComparator);
	}

	/**
	 * Returns the first ddm structure link in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure link, or <code>null</code> if a matching ddm structure link could not be found
	 */
	public static DDMStructureLink fetchByStructureId_First(
		long structureId,
		OrderByComparator<DDMStructureLink> orderByComparator) {

		return getPersistence().fetchByStructureId_First(
			structureId, orderByComparator);
	}

	/**
	 * Returns the last ddm structure link in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure link
	 * @throws NoSuchStructureLinkException if a matching ddm structure link could not be found
	 */
	public static DDMStructureLink findByStructureId_Last(
			long structureId,
			OrderByComparator<DDMStructureLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureLinkException {

		return getPersistence().findByStructureId_Last(
			structureId, orderByComparator);
	}

	/**
	 * Returns the last ddm structure link in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure link, or <code>null</code> if a matching ddm structure link could not be found
	 */
	public static DDMStructureLink fetchByStructureId_Last(
		long structureId,
		OrderByComparator<DDMStructureLink> orderByComparator) {

		return getPersistence().fetchByStructureId_Last(
			structureId, orderByComparator);
	}

	/**
	 * Returns the ddm structure links before and after the current ddm structure link in the ordered set where structureId = &#63;.
	 *
	 * @param structureLinkId the primary key of the current ddm structure link
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm structure link
	 * @throws NoSuchStructureLinkException if a ddm structure link with the primary key could not be found
	 */
	public static DDMStructureLink[] findByStructureId_PrevAndNext(
			long structureLinkId, long structureId,
			OrderByComparator<DDMStructureLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureLinkException {

		return getPersistence().findByStructureId_PrevAndNext(
			structureLinkId, structureId, orderByComparator);
	}

	/**
	 * Removes all the ddm structure links where structureId = &#63; from the database.
	 *
	 * @param structureId the structure ID
	 */
	public static void removeByStructureId(long structureId) {
		getPersistence().removeByStructureId(structureId);
	}

	/**
	 * Returns the number of ddm structure links where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @return the number of matching ddm structure links
	 */
	public static int countByStructureId(long structureId) {
		return getPersistence().countByStructureId(structureId);
	}

	/**
	 * Returns all the ddm structure links where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching ddm structure links
	 */
	public static List<DDMStructureLink> findByC_C(
		long classNameId, long classPK) {

		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	 * Returns a range of all the ddm structure links where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLinkModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of ddm structure links
	 * @param end the upper bound of the range of ddm structure links (not inclusive)
	 * @return the range of matching ddm structure links
	 */
	public static List<DDMStructureLink> findByC_C(
		long classNameId, long classPK, int start, int end) {

		return getPersistence().findByC_C(classNameId, classPK, start, end);
	}

	/**
	 * Returns an ordered range of all the ddm structure links where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLinkModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of ddm structure links
	 * @param end the upper bound of the range of ddm structure links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm structure links
	 */
	public static List<DDMStructureLink> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<DDMStructureLink> orderByComparator) {

		return getPersistence().findByC_C(
			classNameId, classPK, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm structure links where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLinkModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of ddm structure links
	 * @param end the upper bound of the range of ddm structure links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm structure links
	 */
	public static List<DDMStructureLink> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<DDMStructureLink> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_C(
			classNameId, classPK, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first ddm structure link in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure link
	 * @throws NoSuchStructureLinkException if a matching ddm structure link could not be found
	 */
	public static DDMStructureLink findByC_C_First(
			long classNameId, long classPK,
			OrderByComparator<DDMStructureLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureLinkException {

		return getPersistence().findByC_C_First(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the first ddm structure link in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure link, or <code>null</code> if a matching ddm structure link could not be found
	 */
	public static DDMStructureLink fetchByC_C_First(
		long classNameId, long classPK,
		OrderByComparator<DDMStructureLink> orderByComparator) {

		return getPersistence().fetchByC_C_First(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the last ddm structure link in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure link
	 * @throws NoSuchStructureLinkException if a matching ddm structure link could not be found
	 */
	public static DDMStructureLink findByC_C_Last(
			long classNameId, long classPK,
			OrderByComparator<DDMStructureLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureLinkException {

		return getPersistence().findByC_C_Last(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the last ddm structure link in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure link, or <code>null</code> if a matching ddm structure link could not be found
	 */
	public static DDMStructureLink fetchByC_C_Last(
		long classNameId, long classPK,
		OrderByComparator<DDMStructureLink> orderByComparator) {

		return getPersistence().fetchByC_C_Last(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the ddm structure links before and after the current ddm structure link in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param structureLinkId the primary key of the current ddm structure link
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm structure link
	 * @throws NoSuchStructureLinkException if a ddm structure link with the primary key could not be found
	 */
	public static DDMStructureLink[] findByC_C_PrevAndNext(
			long structureLinkId, long classNameId, long classPK,
			OrderByComparator<DDMStructureLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureLinkException {

		return getPersistence().findByC_C_PrevAndNext(
			structureLinkId, classNameId, classPK, orderByComparator);
	}

	/**
	 * Removes all the ddm structure links where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public static void removeByC_C(long classNameId, long classPK) {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	 * Returns the number of ddm structure links where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching ddm structure links
	 */
	public static int countByC_C(long classNameId, long classPK) {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	 * Returns the ddm structure link where classNameId = &#63; and classPK = &#63; and structureId = &#63; or throws a <code>NoSuchStructureLinkException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param structureId the structure ID
	 * @return the matching ddm structure link
	 * @throws NoSuchStructureLinkException if a matching ddm structure link could not be found
	 */
	public static DDMStructureLink findByC_C_S(
			long classNameId, long classPK, long structureId)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureLinkException {

		return getPersistence().findByC_C_S(classNameId, classPK, structureId);
	}

	/**
	 * Returns the ddm structure link where classNameId = &#63; and classPK = &#63; and structureId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param structureId the structure ID
	 * @return the matching ddm structure link, or <code>null</code> if a matching ddm structure link could not be found
	 */
	public static DDMStructureLink fetchByC_C_S(
		long classNameId, long classPK, long structureId) {

		return getPersistence().fetchByC_C_S(classNameId, classPK, structureId);
	}

	/**
	 * Returns the ddm structure link where classNameId = &#63; and classPK = &#63; and structureId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param structureId the structure ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ddm structure link, or <code>null</code> if a matching ddm structure link could not be found
	 */
	public static DDMStructureLink fetchByC_C_S(
		long classNameId, long classPK, long structureId,
		boolean useFinderCache) {

		return getPersistence().fetchByC_C_S(
			classNameId, classPK, structureId, useFinderCache);
	}

	/**
	 * Removes the ddm structure link where classNameId = &#63; and classPK = &#63; and structureId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param structureId the structure ID
	 * @return the ddm structure link that was removed
	 */
	public static DDMStructureLink removeByC_C_S(
			long classNameId, long classPK, long structureId)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureLinkException {

		return getPersistence().removeByC_C_S(
			classNameId, classPK, structureId);
	}

	/**
	 * Returns the number of ddm structure links where classNameId = &#63; and classPK = &#63; and structureId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param structureId the structure ID
	 * @return the number of matching ddm structure links
	 */
	public static int countByC_C_S(
		long classNameId, long classPK, long structureId) {

		return getPersistence().countByC_C_S(classNameId, classPK, structureId);
	}

	/**
	 * Caches the ddm structure link in the entity cache if it is enabled.
	 *
	 * @param ddmStructureLink the ddm structure link
	 */
	public static void cacheResult(DDMStructureLink ddmStructureLink) {
		getPersistence().cacheResult(ddmStructureLink);
	}

	/**
	 * Caches the ddm structure links in the entity cache if it is enabled.
	 *
	 * @param ddmStructureLinks the ddm structure links
	 */
	public static void cacheResult(List<DDMStructureLink> ddmStructureLinks) {
		getPersistence().cacheResult(ddmStructureLinks);
	}

	/**
	 * Creates a new ddm structure link with the primary key. Does not add the ddm structure link to the database.
	 *
	 * @param structureLinkId the primary key for the new ddm structure link
	 * @return the new ddm structure link
	 */
	public static DDMStructureLink create(long structureLinkId) {
		return getPersistence().create(structureLinkId);
	}

	/**
	 * Removes the ddm structure link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param structureLinkId the primary key of the ddm structure link
	 * @return the ddm structure link that was removed
	 * @throws NoSuchStructureLinkException if a ddm structure link with the primary key could not be found
	 */
	public static DDMStructureLink remove(long structureLinkId)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureLinkException {

		return getPersistence().remove(structureLinkId);
	}

	public static DDMStructureLink updateImpl(
		DDMStructureLink ddmStructureLink) {

		return getPersistence().updateImpl(ddmStructureLink);
	}

	/**
	 * Returns the ddm structure link with the primary key or throws a <code>NoSuchStructureLinkException</code> if it could not be found.
	 *
	 * @param structureLinkId the primary key of the ddm structure link
	 * @return the ddm structure link
	 * @throws NoSuchStructureLinkException if a ddm structure link with the primary key could not be found
	 */
	public static DDMStructureLink findByPrimaryKey(long structureLinkId)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureLinkException {

		return getPersistence().findByPrimaryKey(structureLinkId);
	}

	/**
	 * Returns the ddm structure link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param structureLinkId the primary key of the ddm structure link
	 * @return the ddm structure link, or <code>null</code> if a ddm structure link with the primary key could not be found
	 */
	public static DDMStructureLink fetchByPrimaryKey(long structureLinkId) {
		return getPersistence().fetchByPrimaryKey(structureLinkId);
	}

	/**
	 * Returns all the ddm structure links.
	 *
	 * @return the ddm structure links
	 */
	public static List<DDMStructureLink> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the ddm structure links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm structure links
	 * @param end the upper bound of the range of ddm structure links (not inclusive)
	 * @return the range of ddm structure links
	 */
	public static List<DDMStructureLink> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the ddm structure links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm structure links
	 * @param end the upper bound of the range of ddm structure links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ddm structure links
	 */
	public static List<DDMStructureLink> findAll(
		int start, int end,
		OrderByComparator<DDMStructureLink> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm structure links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm structure links
	 * @param end the upper bound of the range of ddm structure links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ddm structure links
	 */
	public static List<DDMStructureLink> findAll(
		int start, int end,
		OrderByComparator<DDMStructureLink> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the ddm structure links from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of ddm structure links.
	 *
	 * @return the number of ddm structure links
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static DDMStructureLinkPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<DDMStructureLinkPersistence, DDMStructureLinkPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			DDMStructureLinkPersistence.class);

		ServiceTracker<DDMStructureLinkPersistence, DDMStructureLinkPersistence>
			serviceTracker =
				new ServiceTracker
					<DDMStructureLinkPersistence, DDMStructureLinkPersistence>(
						bundle.getBundleContext(),
						DDMStructureLinkPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}
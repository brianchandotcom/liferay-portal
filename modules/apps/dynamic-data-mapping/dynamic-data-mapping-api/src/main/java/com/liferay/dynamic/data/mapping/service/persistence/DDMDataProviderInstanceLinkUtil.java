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

import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink;
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
 * The persistence utility for the ddm data provider instance link service. This utility wraps <code>com.liferay.dynamic.data.mapping.service.persistence.impl.DDMDataProviderInstanceLinkPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMDataProviderInstanceLinkPersistence
 * @generated
 */
public class DDMDataProviderInstanceLinkUtil {

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
		DDMDataProviderInstanceLink ddmDataProviderInstanceLink) {

		getPersistence().clearCache(ddmDataProviderInstanceLink);
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
	public static Map<Serializable, DDMDataProviderInstanceLink>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DDMDataProviderInstanceLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DDMDataProviderInstanceLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DDMDataProviderInstanceLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DDMDataProviderInstanceLink> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static DDMDataProviderInstanceLink update(
		DDMDataProviderInstanceLink ddmDataProviderInstanceLink) {

		return getPersistence().update(ddmDataProviderInstanceLink);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static DDMDataProviderInstanceLink update(
		DDMDataProviderInstanceLink ddmDataProviderInstanceLink,
		ServiceContext serviceContext) {

		return getPersistence().update(
			ddmDataProviderInstanceLink, serviceContext);
	}

	/**
	 * Returns all the ddm data provider instance links where dataProviderInstanceId = &#63;.
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 * @return the matching ddm data provider instance links
	 */
	public static List<DDMDataProviderInstanceLink>
		findByDataProviderInstanceId(long dataProviderInstanceId) {

		return getPersistence().findByDataProviderInstanceId(
			dataProviderInstanceId);
	}

	/**
	 * Returns a range of all the ddm data provider instance links where dataProviderInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceLinkModelImpl</code>.
	 * </p>
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 * @param start the lower bound of the range of ddm data provider instance links
	 * @param end the upper bound of the range of ddm data provider instance links (not inclusive)
	 * @return the range of matching ddm data provider instance links
	 */
	public static List<DDMDataProviderInstanceLink>
		findByDataProviderInstanceId(
			long dataProviderInstanceId, int start, int end) {

		return getPersistence().findByDataProviderInstanceId(
			dataProviderInstanceId, start, end);
	}

	/**
	 * Returns an ordered range of all the ddm data provider instance links where dataProviderInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceLinkModelImpl</code>.
	 * </p>
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 * @param start the lower bound of the range of ddm data provider instance links
	 * @param end the upper bound of the range of ddm data provider instance links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm data provider instance links
	 */
	public static List<DDMDataProviderInstanceLink>
		findByDataProviderInstanceId(
			long dataProviderInstanceId, int start, int end,
			OrderByComparator<DDMDataProviderInstanceLink> orderByComparator) {

		return getPersistence().findByDataProviderInstanceId(
			dataProviderInstanceId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm data provider instance links where dataProviderInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceLinkModelImpl</code>.
	 * </p>
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 * @param start the lower bound of the range of ddm data provider instance links
	 * @param end the upper bound of the range of ddm data provider instance links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm data provider instance links
	 */
	public static List<DDMDataProviderInstanceLink>
		findByDataProviderInstanceId(
			long dataProviderInstanceId, int start, int end,
			OrderByComparator<DDMDataProviderInstanceLink> orderByComparator,
			boolean useFinderCache) {

		return getPersistence().findByDataProviderInstanceId(
			dataProviderInstanceId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first ddm data provider instance link in the ordered set where dataProviderInstanceId = &#63;.
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm data provider instance link
	 * @throws NoSuchDataProviderInstanceLinkException if a matching ddm data provider instance link could not be found
	 */
	public static DDMDataProviderInstanceLink
			findByDataProviderInstanceId_First(
				long dataProviderInstanceId,
				OrderByComparator<DDMDataProviderInstanceLink>
					orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchDataProviderInstanceLinkException {

		return getPersistence().findByDataProviderInstanceId_First(
			dataProviderInstanceId, orderByComparator);
	}

	/**
	 * Returns the first ddm data provider instance link in the ordered set where dataProviderInstanceId = &#63;.
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm data provider instance link, or <code>null</code> if a matching ddm data provider instance link could not be found
	 */
	public static DDMDataProviderInstanceLink
		fetchByDataProviderInstanceId_First(
			long dataProviderInstanceId,
			OrderByComparator<DDMDataProviderInstanceLink> orderByComparator) {

		return getPersistence().fetchByDataProviderInstanceId_First(
			dataProviderInstanceId, orderByComparator);
	}

	/**
	 * Returns the last ddm data provider instance link in the ordered set where dataProviderInstanceId = &#63;.
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm data provider instance link
	 * @throws NoSuchDataProviderInstanceLinkException if a matching ddm data provider instance link could not be found
	 */
	public static DDMDataProviderInstanceLink findByDataProviderInstanceId_Last(
			long dataProviderInstanceId,
			OrderByComparator<DDMDataProviderInstanceLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchDataProviderInstanceLinkException {

		return getPersistence().findByDataProviderInstanceId_Last(
			dataProviderInstanceId, orderByComparator);
	}

	/**
	 * Returns the last ddm data provider instance link in the ordered set where dataProviderInstanceId = &#63;.
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm data provider instance link, or <code>null</code> if a matching ddm data provider instance link could not be found
	 */
	public static DDMDataProviderInstanceLink
		fetchByDataProviderInstanceId_Last(
			long dataProviderInstanceId,
			OrderByComparator<DDMDataProviderInstanceLink> orderByComparator) {

		return getPersistence().fetchByDataProviderInstanceId_Last(
			dataProviderInstanceId, orderByComparator);
	}

	/**
	 * Returns the ddm data provider instance links before and after the current ddm data provider instance link in the ordered set where dataProviderInstanceId = &#63;.
	 *
	 * @param dataProviderInstanceLinkId the primary key of the current ddm data provider instance link
	 * @param dataProviderInstanceId the data provider instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm data provider instance link
	 * @throws NoSuchDataProviderInstanceLinkException if a ddm data provider instance link with the primary key could not be found
	 */
	public static DDMDataProviderInstanceLink[]
			findByDataProviderInstanceId_PrevAndNext(
				long dataProviderInstanceLinkId, long dataProviderInstanceId,
				OrderByComparator<DDMDataProviderInstanceLink>
					orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchDataProviderInstanceLinkException {

		return getPersistence().findByDataProviderInstanceId_PrevAndNext(
			dataProviderInstanceLinkId, dataProviderInstanceId,
			orderByComparator);
	}

	/**
	 * Removes all the ddm data provider instance links where dataProviderInstanceId = &#63; from the database.
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 */
	public static void removeByDataProviderInstanceId(
		long dataProviderInstanceId) {

		getPersistence().removeByDataProviderInstanceId(dataProviderInstanceId);
	}

	/**
	 * Returns the number of ddm data provider instance links where dataProviderInstanceId = &#63;.
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 * @return the number of matching ddm data provider instance links
	 */
	public static int countByDataProviderInstanceId(
		long dataProviderInstanceId) {

		return getPersistence().countByDataProviderInstanceId(
			dataProviderInstanceId);
	}

	/**
	 * Returns all the ddm data provider instance links where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @return the matching ddm data provider instance links
	 */
	public static List<DDMDataProviderInstanceLink> findByStructureId(
		long structureId) {

		return getPersistence().findByStructureId(structureId);
	}

	/**
	 * Returns a range of all the ddm data provider instance links where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceLinkModelImpl</code>.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of ddm data provider instance links
	 * @param end the upper bound of the range of ddm data provider instance links (not inclusive)
	 * @return the range of matching ddm data provider instance links
	 */
	public static List<DDMDataProviderInstanceLink> findByStructureId(
		long structureId, int start, int end) {

		return getPersistence().findByStructureId(structureId, start, end);
	}

	/**
	 * Returns an ordered range of all the ddm data provider instance links where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceLinkModelImpl</code>.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of ddm data provider instance links
	 * @param end the upper bound of the range of ddm data provider instance links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm data provider instance links
	 */
	public static List<DDMDataProviderInstanceLink> findByStructureId(
		long structureId, int start, int end,
		OrderByComparator<DDMDataProviderInstanceLink> orderByComparator) {

		return getPersistence().findByStructureId(
			structureId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm data provider instance links where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceLinkModelImpl</code>.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of ddm data provider instance links
	 * @param end the upper bound of the range of ddm data provider instance links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm data provider instance links
	 */
	public static List<DDMDataProviderInstanceLink> findByStructureId(
		long structureId, int start, int end,
		OrderByComparator<DDMDataProviderInstanceLink> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByStructureId(
			structureId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ddm data provider instance link in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm data provider instance link
	 * @throws NoSuchDataProviderInstanceLinkException if a matching ddm data provider instance link could not be found
	 */
	public static DDMDataProviderInstanceLink findByStructureId_First(
			long structureId,
			OrderByComparator<DDMDataProviderInstanceLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchDataProviderInstanceLinkException {

		return getPersistence().findByStructureId_First(
			structureId, orderByComparator);
	}

	/**
	 * Returns the first ddm data provider instance link in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm data provider instance link, or <code>null</code> if a matching ddm data provider instance link could not be found
	 */
	public static DDMDataProviderInstanceLink fetchByStructureId_First(
		long structureId,
		OrderByComparator<DDMDataProviderInstanceLink> orderByComparator) {

		return getPersistence().fetchByStructureId_First(
			structureId, orderByComparator);
	}

	/**
	 * Returns the last ddm data provider instance link in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm data provider instance link
	 * @throws NoSuchDataProviderInstanceLinkException if a matching ddm data provider instance link could not be found
	 */
	public static DDMDataProviderInstanceLink findByStructureId_Last(
			long structureId,
			OrderByComparator<DDMDataProviderInstanceLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchDataProviderInstanceLinkException {

		return getPersistence().findByStructureId_Last(
			structureId, orderByComparator);
	}

	/**
	 * Returns the last ddm data provider instance link in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm data provider instance link, or <code>null</code> if a matching ddm data provider instance link could not be found
	 */
	public static DDMDataProviderInstanceLink fetchByStructureId_Last(
		long structureId,
		OrderByComparator<DDMDataProviderInstanceLink> orderByComparator) {

		return getPersistence().fetchByStructureId_Last(
			structureId, orderByComparator);
	}

	/**
	 * Returns the ddm data provider instance links before and after the current ddm data provider instance link in the ordered set where structureId = &#63;.
	 *
	 * @param dataProviderInstanceLinkId the primary key of the current ddm data provider instance link
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm data provider instance link
	 * @throws NoSuchDataProviderInstanceLinkException if a ddm data provider instance link with the primary key could not be found
	 */
	public static DDMDataProviderInstanceLink[] findByStructureId_PrevAndNext(
			long dataProviderInstanceLinkId, long structureId,
			OrderByComparator<DDMDataProviderInstanceLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchDataProviderInstanceLinkException {

		return getPersistence().findByStructureId_PrevAndNext(
			dataProviderInstanceLinkId, structureId, orderByComparator);
	}

	/**
	 * Removes all the ddm data provider instance links where structureId = &#63; from the database.
	 *
	 * @param structureId the structure ID
	 */
	public static void removeByStructureId(long structureId) {
		getPersistence().removeByStructureId(structureId);
	}

	/**
	 * Returns the number of ddm data provider instance links where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @return the number of matching ddm data provider instance links
	 */
	public static int countByStructureId(long structureId) {
		return getPersistence().countByStructureId(structureId);
	}

	/**
	 * Returns the ddm data provider instance link where dataProviderInstanceId = &#63; and structureId = &#63; or throws a <code>NoSuchDataProviderInstanceLinkException</code> if it could not be found.
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 * @param structureId the structure ID
	 * @return the matching ddm data provider instance link
	 * @throws NoSuchDataProviderInstanceLinkException if a matching ddm data provider instance link could not be found
	 */
	public static DDMDataProviderInstanceLink findByD_S(
			long dataProviderInstanceId, long structureId)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchDataProviderInstanceLinkException {

		return getPersistence().findByD_S(dataProviderInstanceId, structureId);
	}

	/**
	 * Returns the ddm data provider instance link where dataProviderInstanceId = &#63; and structureId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 * @param structureId the structure ID
	 * @return the matching ddm data provider instance link, or <code>null</code> if a matching ddm data provider instance link could not be found
	 */
	public static DDMDataProviderInstanceLink fetchByD_S(
		long dataProviderInstanceId, long structureId) {

		return getPersistence().fetchByD_S(dataProviderInstanceId, structureId);
	}

	/**
	 * Returns the ddm data provider instance link where dataProviderInstanceId = &#63; and structureId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 * @param structureId the structure ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ddm data provider instance link, or <code>null</code> if a matching ddm data provider instance link could not be found
	 */
	public static DDMDataProviderInstanceLink fetchByD_S(
		long dataProviderInstanceId, long structureId, boolean useFinderCache) {

		return getPersistence().fetchByD_S(
			dataProviderInstanceId, structureId, useFinderCache);
	}

	/**
	 * Removes the ddm data provider instance link where dataProviderInstanceId = &#63; and structureId = &#63; from the database.
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 * @param structureId the structure ID
	 * @return the ddm data provider instance link that was removed
	 */
	public static DDMDataProviderInstanceLink removeByD_S(
			long dataProviderInstanceId, long structureId)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchDataProviderInstanceLinkException {

		return getPersistence().removeByD_S(
			dataProviderInstanceId, structureId);
	}

	/**
	 * Returns the number of ddm data provider instance links where dataProviderInstanceId = &#63; and structureId = &#63;.
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 * @param structureId the structure ID
	 * @return the number of matching ddm data provider instance links
	 */
	public static int countByD_S(
		long dataProviderInstanceId, long structureId) {

		return getPersistence().countByD_S(dataProviderInstanceId, structureId);
	}

	/**
	 * Caches the ddm data provider instance link in the entity cache if it is enabled.
	 *
	 * @param ddmDataProviderInstanceLink the ddm data provider instance link
	 */
	public static void cacheResult(
		DDMDataProviderInstanceLink ddmDataProviderInstanceLink) {

		getPersistence().cacheResult(ddmDataProviderInstanceLink);
	}

	/**
	 * Caches the ddm data provider instance links in the entity cache if it is enabled.
	 *
	 * @param ddmDataProviderInstanceLinks the ddm data provider instance links
	 */
	public static void cacheResult(
		List<DDMDataProviderInstanceLink> ddmDataProviderInstanceLinks) {

		getPersistence().cacheResult(ddmDataProviderInstanceLinks);
	}

	/**
	 * Creates a new ddm data provider instance link with the primary key. Does not add the ddm data provider instance link to the database.
	 *
	 * @param dataProviderInstanceLinkId the primary key for the new ddm data provider instance link
	 * @return the new ddm data provider instance link
	 */
	public static DDMDataProviderInstanceLink create(
		long dataProviderInstanceLinkId) {

		return getPersistence().create(dataProviderInstanceLinkId);
	}

	/**
	 * Removes the ddm data provider instance link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dataProviderInstanceLinkId the primary key of the ddm data provider instance link
	 * @return the ddm data provider instance link that was removed
	 * @throws NoSuchDataProviderInstanceLinkException if a ddm data provider instance link with the primary key could not be found
	 */
	public static DDMDataProviderInstanceLink remove(
			long dataProviderInstanceLinkId)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchDataProviderInstanceLinkException {

		return getPersistence().remove(dataProviderInstanceLinkId);
	}

	public static DDMDataProviderInstanceLink updateImpl(
		DDMDataProviderInstanceLink ddmDataProviderInstanceLink) {

		return getPersistence().updateImpl(ddmDataProviderInstanceLink);
	}

	/**
	 * Returns the ddm data provider instance link with the primary key or throws a <code>NoSuchDataProviderInstanceLinkException</code> if it could not be found.
	 *
	 * @param dataProviderInstanceLinkId the primary key of the ddm data provider instance link
	 * @return the ddm data provider instance link
	 * @throws NoSuchDataProviderInstanceLinkException if a ddm data provider instance link with the primary key could not be found
	 */
	public static DDMDataProviderInstanceLink findByPrimaryKey(
			long dataProviderInstanceLinkId)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchDataProviderInstanceLinkException {

		return getPersistence().findByPrimaryKey(dataProviderInstanceLinkId);
	}

	/**
	 * Returns the ddm data provider instance link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param dataProviderInstanceLinkId the primary key of the ddm data provider instance link
	 * @return the ddm data provider instance link, or <code>null</code> if a ddm data provider instance link with the primary key could not be found
	 */
	public static DDMDataProviderInstanceLink fetchByPrimaryKey(
		long dataProviderInstanceLinkId) {

		return getPersistence().fetchByPrimaryKey(dataProviderInstanceLinkId);
	}

	/**
	 * Returns all the ddm data provider instance links.
	 *
	 * @return the ddm data provider instance links
	 */
	public static List<DDMDataProviderInstanceLink> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the ddm data provider instance links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm data provider instance links
	 * @param end the upper bound of the range of ddm data provider instance links (not inclusive)
	 * @return the range of ddm data provider instance links
	 */
	public static List<DDMDataProviderInstanceLink> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the ddm data provider instance links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm data provider instance links
	 * @param end the upper bound of the range of ddm data provider instance links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ddm data provider instance links
	 */
	public static List<DDMDataProviderInstanceLink> findAll(
		int start, int end,
		OrderByComparator<DDMDataProviderInstanceLink> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm data provider instance links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm data provider instance links
	 * @param end the upper bound of the range of ddm data provider instance links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ddm data provider instance links
	 */
	public static List<DDMDataProviderInstanceLink> findAll(
		int start, int end,
		OrderByComparator<DDMDataProviderInstanceLink> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the ddm data provider instance links from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of ddm data provider instance links.
	 *
	 * @return the number of ddm data provider instance links
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static DDMDataProviderInstanceLinkPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<DDMDataProviderInstanceLinkPersistence,
		 DDMDataProviderInstanceLinkPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			DDMDataProviderInstanceLinkPersistence.class);

		ServiceTracker
			<DDMDataProviderInstanceLinkPersistence,
			 DDMDataProviderInstanceLinkPersistence> serviceTracker =
				new ServiceTracker
					<DDMDataProviderInstanceLinkPersistence,
					 DDMDataProviderInstanceLinkPersistence>(
						 bundle.getBundleContext(),
						 DDMDataProviderInstanceLinkPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}
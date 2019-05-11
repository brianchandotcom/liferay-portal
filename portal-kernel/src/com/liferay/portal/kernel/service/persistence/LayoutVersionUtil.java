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

package com.liferay.portal.kernel.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.model.LayoutVersion;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence utility for the layout version service. This utility wraps <code>com.liferay.portal.service.persistence.impl.LayoutVersionPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutVersionPersistence
 * @generated
 */
@ProviderType
public class LayoutVersionUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(LayoutVersion layoutVersion) {
		getPersistence().clearCache(layoutVersion);
	}

	/**
	 * @see BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, LayoutVersion> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<LayoutVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LayoutVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LayoutVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static LayoutVersion update(LayoutVersion layoutVersion) {
		return getPersistence().update(layoutVersion);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static LayoutVersion update(
		LayoutVersion layoutVersion, ServiceContext serviceContext) {

		return getPersistence().update(layoutVersion, serviceContext);
	}

	/**
	 * Returns all the layout versions where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the matching layout versions
	 */
	public static List<LayoutVersion> findByPlid(long plid) {
		return getPersistence().findByPlid(plid);
	}

	/**
	 * Returns a range of all the layout versions where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public static List<LayoutVersion> findByPlid(
		long plid, int start, int end) {

		return getPersistence().findByPlid(plid, start, end);
	}

	/**
	 * Returns an ordered range of all the layout versions where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByPlid(
		long plid, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findByPlid(plid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByPlid(
		long plid, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByPlid(
			plid, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first layout version in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByPlid_First(
			long plid, OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByPlid_First(plid, orderByComparator);
	}

	/**
	 * Returns the first layout version in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByPlid_First(
		long plid, OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByPlid_First(plid, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByPlid_Last(
			long plid, OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByPlid_Last(plid, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByPlid_Last(
		long plid, OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByPlid_Last(plid, orderByComparator);
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where plid = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion[] findByPlid_PrevAndNext(
			long layoutVersionId, long plid,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByPlid_PrevAndNext(
			layoutVersionId, plid, orderByComparator);
	}

	/**
	 * Removes all the layout versions where plid = &#63; from the database.
	 *
	 * @param plid the plid
	 */
	public static void removeByPlid(long plid) {
		getPersistence().removeByPlid(plid);
	}

	/**
	 * Returns the number of layout versions where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the number of matching layout versions
	 */
	public static int countByPlid(long plid) {
		return getPersistence().countByPlid(plid);
	}

	/**
	 * Returns the layout version where plid = &#63; and version = &#63; or throws a <code>NoSuchLayoutVersionException</code> if it could not be found.
	 *
	 * @param plid the plid
	 * @param version the version
	 * @return the matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByPlid_Version(long plid, int version)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByPlid_Version(plid, version);
	}

	/**
	 * Returns the layout version where plid = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param plid the plid
	 * @param version the version
	 * @return the matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByPlid_Version(long plid, int version) {
		return getPersistence().fetchByPlid_Version(plid, version);
	}

	/**
	 * Returns the layout version where plid = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param plid the plid
	 * @param version the version
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByPlid_Version(
		long plid, int version, boolean retrieveFromCache) {

		return getPersistence().fetchByPlid_Version(
			plid, version, retrieveFromCache);
	}

	/**
	 * Removes the layout version where plid = &#63; and version = &#63; from the database.
	 *
	 * @param plid the plid
	 * @param version the version
	 * @return the layout version that was removed
	 */
	public static LayoutVersion removeByPlid_Version(long plid, int version)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().removeByPlid_Version(plid, version);
	}

	/**
	 * Returns the number of layout versions where plid = &#63; and version = &#63;.
	 *
	 * @param plid the plid
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public static int countByPlid_Version(long plid, int version) {
		return getPersistence().countByPlid_Version(plid, version);
	}

	/**
	 * Returns all the layout versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching layout versions
	 */
	public static List<LayoutVersion> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the layout versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public static List<LayoutVersion> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the layout versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first layout version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByUuid_First(
			String uuid, OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first layout version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByUuid_First(
		String uuid, OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByUuid_Last(
			String uuid, OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByUuid_Last(
		String uuid, OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where uuid = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion[] findByUuid_PrevAndNext(
			long layoutVersionId, String uuid,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByUuid_PrevAndNext(
			layoutVersionId, uuid, orderByComparator);
	}

	/**
	 * Removes all the layout versions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of layout versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching layout versions
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the layout versions where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @return the matching layout versions
	 */
	public static List<LayoutVersion> findByUuid_Version(
		String uuid, int version) {

		return getPersistence().findByUuid_Version(uuid, version);
	}

	/**
	 * Returns a range of all the layout versions where uuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public static List<LayoutVersion> findByUuid_Version(
		String uuid, int version, int start, int end) {

		return getPersistence().findByUuid_Version(uuid, version, start, end);
	}

	/**
	 * Returns an ordered range of all the layout versions where uuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByUuid_Version(
		String uuid, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findByUuid_Version(
			uuid, version, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions where uuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByUuid_Version(
		String uuid, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByUuid_Version(
			uuid, version, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first layout version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByUuid_Version_First(
			String uuid, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByUuid_Version_First(
			uuid, version, orderByComparator);
	}

	/**
	 * Returns the first layout version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByUuid_Version_First(
		String uuid, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByUuid_Version_First(
			uuid, version, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByUuid_Version_Last(
			String uuid, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByUuid_Version_Last(
			uuid, version, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByUuid_Version_Last(
		String uuid, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByUuid_Version_Last(
			uuid, version, orderByComparator);
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion[] findByUuid_Version_PrevAndNext(
			long layoutVersionId, String uuid, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByUuid_Version_PrevAndNext(
			layoutVersionId, uuid, version, orderByComparator);
	}

	/**
	 * Removes all the layout versions where uuid = &#63; and version = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 */
	public static void removeByUuid_Version(String uuid, int version) {
		getPersistence().removeByUuid_Version(uuid, version);
	}

	/**
	 * Returns the number of layout versions where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public static int countByUuid_Version(String uuid, int version) {
		return getPersistence().countByUuid_Version(uuid, version);
	}

	/**
	 * Returns all the layout versions where uuid = &#63; and groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @return the matching layout versions
	 */
	public static List<LayoutVersion> findByUUID_G_P(
		String uuid, long groupId, boolean privateLayout) {

		return getPersistence().findByUUID_G_P(uuid, groupId, privateLayout);
	}

	/**
	 * Returns a range of all the layout versions where uuid = &#63; and groupId = &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public static List<LayoutVersion> findByUUID_G_P(
		String uuid, long groupId, boolean privateLayout, int start, int end) {

		return getPersistence().findByUUID_G_P(
			uuid, groupId, privateLayout, start, end);
	}

	/**
	 * Returns an ordered range of all the layout versions where uuid = &#63; and groupId = &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByUUID_G_P(
		String uuid, long groupId, boolean privateLayout, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findByUUID_G_P(
			uuid, groupId, privateLayout, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions where uuid = &#63; and groupId = &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByUUID_G_P(
		String uuid, long groupId, boolean privateLayout, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByUUID_G_P(
			uuid, groupId, privateLayout, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Returns the first layout version in the ordered set where uuid = &#63; and groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByUUID_G_P_First(
			String uuid, long groupId, boolean privateLayout,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByUUID_G_P_First(
			uuid, groupId, privateLayout, orderByComparator);
	}

	/**
	 * Returns the first layout version in the ordered set where uuid = &#63; and groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByUUID_G_P_First(
		String uuid, long groupId, boolean privateLayout,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByUUID_G_P_First(
			uuid, groupId, privateLayout, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where uuid = &#63; and groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByUUID_G_P_Last(
			String uuid, long groupId, boolean privateLayout,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByUUID_G_P_Last(
			uuid, groupId, privateLayout, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where uuid = &#63; and groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByUUID_G_P_Last(
		String uuid, long groupId, boolean privateLayout,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByUUID_G_P_Last(
			uuid, groupId, privateLayout, orderByComparator);
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where uuid = &#63; and groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion[] findByUUID_G_P_PrevAndNext(
			long layoutVersionId, String uuid, long groupId,
			boolean privateLayout,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByUUID_G_P_PrevAndNext(
			layoutVersionId, uuid, groupId, privateLayout, orderByComparator);
	}

	/**
	 * Removes all the layout versions where uuid = &#63; and groupId = &#63; and privateLayout = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 */
	public static void removeByUUID_G_P(
		String uuid, long groupId, boolean privateLayout) {

		getPersistence().removeByUUID_G_P(uuid, groupId, privateLayout);
	}

	/**
	 * Returns the number of layout versions where uuid = &#63; and groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @return the number of matching layout versions
	 */
	public static int countByUUID_G_P(
		String uuid, long groupId, boolean privateLayout) {

		return getPersistence().countByUUID_G_P(uuid, groupId, privateLayout);
	}

	/**
	 * Returns the layout version where uuid = &#63; and groupId = &#63; and privateLayout = &#63; and version = &#63; or throws a <code>NoSuchLayoutVersionException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @return the matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByUUID_G_P_Version(
			String uuid, long groupId, boolean privateLayout, int version)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByUUID_G_P_Version(
			uuid, groupId, privateLayout, version);
	}

	/**
	 * Returns the layout version where uuid = &#63; and groupId = &#63; and privateLayout = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @return the matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByUUID_G_P_Version(
		String uuid, long groupId, boolean privateLayout, int version) {

		return getPersistence().fetchByUUID_G_P_Version(
			uuid, groupId, privateLayout, version);
	}

	/**
	 * Returns the layout version where uuid = &#63; and groupId = &#63; and privateLayout = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByUUID_G_P_Version(
		String uuid, long groupId, boolean privateLayout, int version,
		boolean retrieveFromCache) {

		return getPersistence().fetchByUUID_G_P_Version(
			uuid, groupId, privateLayout, version, retrieveFromCache);
	}

	/**
	 * Removes the layout version where uuid = &#63; and groupId = &#63; and privateLayout = &#63; and version = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @return the layout version that was removed
	 */
	public static LayoutVersion removeByUUID_G_P_Version(
			String uuid, long groupId, boolean privateLayout, int version)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().removeByUUID_G_P_Version(
			uuid, groupId, privateLayout, version);
	}

	/**
	 * Returns the number of layout versions where uuid = &#63; and groupId = &#63; and privateLayout = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public static int countByUUID_G_P_Version(
		String uuid, long groupId, boolean privateLayout, int version) {

		return getPersistence().countByUUID_G_P_Version(
			uuid, groupId, privateLayout, version);
	}

	/**
	 * Returns all the layout versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching layout versions
	 */
	public static List<LayoutVersion> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the layout versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public static List<LayoutVersion> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the layout versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first layout version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first layout version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion[] findByUuid_C_PrevAndNext(
			long layoutVersionId, String uuid, long companyId,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByUuid_C_PrevAndNext(
			layoutVersionId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the layout versions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of layout versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching layout versions
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the layout versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @return the matching layout versions
	 */
	public static List<LayoutVersion> findByUuid_C_Version(
		String uuid, long companyId, int version) {

		return getPersistence().findByUuid_C_Version(uuid, companyId, version);
	}

	/**
	 * Returns a range of all the layout versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public static List<LayoutVersion> findByUuid_C_Version(
		String uuid, long companyId, int version, int start, int end) {

		return getPersistence().findByUuid_C_Version(
			uuid, companyId, version, start, end);
	}

	/**
	 * Returns an ordered range of all the layout versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByUuid_C_Version(
		String uuid, long companyId, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findByUuid_C_Version(
			uuid, companyId, version, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByUuid_C_Version(
		String uuid, long companyId, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByUuid_C_Version(
			uuid, companyId, version, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Returns the first layout version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByUuid_C_Version_First(
			String uuid, long companyId, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByUuid_C_Version_First(
			uuid, companyId, version, orderByComparator);
	}

	/**
	 * Returns the first layout version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByUuid_C_Version_First(
		String uuid, long companyId, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByUuid_C_Version_First(
			uuid, companyId, version, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByUuid_C_Version_Last(
			String uuid, long companyId, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByUuid_C_Version_Last(
			uuid, companyId, version, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByUuid_C_Version_Last(
		String uuid, long companyId, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByUuid_C_Version_Last(
			uuid, companyId, version, orderByComparator);
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion[] findByUuid_C_Version_PrevAndNext(
			long layoutVersionId, String uuid, long companyId, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByUuid_C_Version_PrevAndNext(
			layoutVersionId, uuid, companyId, version, orderByComparator);
	}

	/**
	 * Removes all the layout versions where uuid = &#63; and companyId = &#63; and version = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 */
	public static void removeByUuid_C_Version(
		String uuid, long companyId, int version) {

		getPersistence().removeByUuid_C_Version(uuid, companyId, version);
	}

	/**
	 * Returns the number of layout versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public static int countByUuid_C_Version(
		String uuid, long companyId, int version) {

		return getPersistence().countByUuid_C_Version(uuid, companyId, version);
	}

	/**
	 * Returns all the layout versions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching layout versions
	 */
	public static List<LayoutVersion> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	 * Returns a range of all the layout versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public static List<LayoutVersion> findByGroupId(
		long groupId, int start, int end) {

		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByGroupId_First(
			long groupId, OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByGroupId_First(
		long groupId, OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByGroupId_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByGroupId_Last(
			long groupId, OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByGroupId_Last(
		long groupId, OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion[] findByGroupId_PrevAndNext(
			long layoutVersionId, long groupId,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByGroupId_PrevAndNext(
			layoutVersionId, groupId, orderByComparator);
	}

	/**
	 * Removes all the layout versions where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	 * Returns the number of layout versions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching layout versions
	 */
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	 * Returns all the layout versions where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @return the matching layout versions
	 */
	public static List<LayoutVersion> findByGroupId_Version(
		long groupId, int version) {

		return getPersistence().findByGroupId_Version(groupId, version);
	}

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public static List<LayoutVersion> findByGroupId_Version(
		long groupId, int version, int start, int end) {

		return getPersistence().findByGroupId_Version(
			groupId, version, start, end);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByGroupId_Version(
		long groupId, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findByGroupId_Version(
			groupId, version, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByGroupId_Version(
		long groupId, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByGroupId_Version(
			groupId, version, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByGroupId_Version_First(
			long groupId, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByGroupId_Version_First(
			groupId, version, orderByComparator);
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByGroupId_Version_First(
		long groupId, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByGroupId_Version_First(
			groupId, version, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByGroupId_Version_Last(
			long groupId, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByGroupId_Version_Last(
			groupId, version, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByGroupId_Version_Last(
		long groupId, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByGroupId_Version_Last(
			groupId, version, orderByComparator);
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion[] findByGroupId_Version_PrevAndNext(
			long layoutVersionId, long groupId, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByGroupId_Version_PrevAndNext(
			layoutVersionId, groupId, version, orderByComparator);
	}

	/**
	 * Removes all the layout versions where groupId = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 */
	public static void removeByGroupId_Version(long groupId, int version) {
		getPersistence().removeByGroupId_Version(groupId, version);
	}

	/**
	 * Returns the number of layout versions where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public static int countByGroupId_Version(long groupId, int version) {
		return getPersistence().countByGroupId_Version(groupId, version);
	}

	/**
	 * Returns all the layout versions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching layout versions
	 */
	public static List<LayoutVersion> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the layout versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public static List<LayoutVersion> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the layout versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first layout version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByCompanyId_First(
			long companyId, OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first layout version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByCompanyId_First(
		long companyId, OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByCompanyId_Last(
			long companyId, OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByCompanyId_Last(
		long companyId, OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where companyId = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion[] findByCompanyId_PrevAndNext(
			long layoutVersionId, long companyId,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByCompanyId_PrevAndNext(
			layoutVersionId, companyId, orderByComparator);
	}

	/**
	 * Removes all the layout versions where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of layout versions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching layout versions
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns all the layout versions where companyId = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @return the matching layout versions
	 */
	public static List<LayoutVersion> findByCompanyId_Version(
		long companyId, int version) {

		return getPersistence().findByCompanyId_Version(companyId, version);
	}

	/**
	 * Returns a range of all the layout versions where companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public static List<LayoutVersion> findByCompanyId_Version(
		long companyId, int version, int start, int end) {

		return getPersistence().findByCompanyId_Version(
			companyId, version, start, end);
	}

	/**
	 * Returns an ordered range of all the layout versions where companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByCompanyId_Version(
		long companyId, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findByCompanyId_Version(
			companyId, version, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions where companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByCompanyId_Version(
		long companyId, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByCompanyId_Version(
			companyId, version, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Returns the first layout version in the ordered set where companyId = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByCompanyId_Version_First(
			long companyId, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByCompanyId_Version_First(
			companyId, version, orderByComparator);
	}

	/**
	 * Returns the first layout version in the ordered set where companyId = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByCompanyId_Version_First(
		long companyId, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByCompanyId_Version_First(
			companyId, version, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where companyId = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByCompanyId_Version_Last(
			long companyId, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByCompanyId_Version_Last(
			companyId, version, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where companyId = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByCompanyId_Version_Last(
		long companyId, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByCompanyId_Version_Last(
			companyId, version, orderByComparator);
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where companyId = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion[] findByCompanyId_Version_PrevAndNext(
			long layoutVersionId, long companyId, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByCompanyId_Version_PrevAndNext(
			layoutVersionId, companyId, version, orderByComparator);
	}

	/**
	 * Removes all the layout versions where companyId = &#63; and version = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 */
	public static void removeByCompanyId_Version(long companyId, int version) {
		getPersistence().removeByCompanyId_Version(companyId, version);
	}

	/**
	 * Returns the number of layout versions where companyId = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public static int countByCompanyId_Version(long companyId, int version) {
		return getPersistence().countByCompanyId_Version(companyId, version);
	}

	/**
	 * Returns all the layout versions where parentPlid = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @return the matching layout versions
	 */
	public static List<LayoutVersion> findByParentPlid(long parentPlid) {
		return getPersistence().findByParentPlid(parentPlid);
	}

	/**
	 * Returns a range of all the layout versions where parentPlid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param parentPlid the parent plid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public static List<LayoutVersion> findByParentPlid(
		long parentPlid, int start, int end) {

		return getPersistence().findByParentPlid(parentPlid, start, end);
	}

	/**
	 * Returns an ordered range of all the layout versions where parentPlid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param parentPlid the parent plid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByParentPlid(
		long parentPlid, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findByParentPlid(
			parentPlid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions where parentPlid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param parentPlid the parent plid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByParentPlid(
		long parentPlid, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByParentPlid(
			parentPlid, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first layout version in the ordered set where parentPlid = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByParentPlid_First(
			long parentPlid, OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByParentPlid_First(
			parentPlid, orderByComparator);
	}

	/**
	 * Returns the first layout version in the ordered set where parentPlid = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByParentPlid_First(
		long parentPlid, OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByParentPlid_First(
			parentPlid, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where parentPlid = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByParentPlid_Last(
			long parentPlid, OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByParentPlid_Last(
			parentPlid, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where parentPlid = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByParentPlid_Last(
		long parentPlid, OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByParentPlid_Last(
			parentPlid, orderByComparator);
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where parentPlid = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param parentPlid the parent plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion[] findByParentPlid_PrevAndNext(
			long layoutVersionId, long parentPlid,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByParentPlid_PrevAndNext(
			layoutVersionId, parentPlid, orderByComparator);
	}

	/**
	 * Removes all the layout versions where parentPlid = &#63; from the database.
	 *
	 * @param parentPlid the parent plid
	 */
	public static void removeByParentPlid(long parentPlid) {
		getPersistence().removeByParentPlid(parentPlid);
	}

	/**
	 * Returns the number of layout versions where parentPlid = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @return the number of matching layout versions
	 */
	public static int countByParentPlid(long parentPlid) {
		return getPersistence().countByParentPlid(parentPlid);
	}

	/**
	 * Returns all the layout versions where parentPlid = &#63; and version = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @param version the version
	 * @return the matching layout versions
	 */
	public static List<LayoutVersion> findByParentPlid_Version(
		long parentPlid, int version) {

		return getPersistence().findByParentPlid_Version(parentPlid, version);
	}

	/**
	 * Returns a range of all the layout versions where parentPlid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param parentPlid the parent plid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public static List<LayoutVersion> findByParentPlid_Version(
		long parentPlid, int version, int start, int end) {

		return getPersistence().findByParentPlid_Version(
			parentPlid, version, start, end);
	}

	/**
	 * Returns an ordered range of all the layout versions where parentPlid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param parentPlid the parent plid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByParentPlid_Version(
		long parentPlid, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findByParentPlid_Version(
			parentPlid, version, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions where parentPlid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param parentPlid the parent plid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByParentPlid_Version(
		long parentPlid, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByParentPlid_Version(
			parentPlid, version, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Returns the first layout version in the ordered set where parentPlid = &#63; and version = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByParentPlid_Version_First(
			long parentPlid, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByParentPlid_Version_First(
			parentPlid, version, orderByComparator);
	}

	/**
	 * Returns the first layout version in the ordered set where parentPlid = &#63; and version = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByParentPlid_Version_First(
		long parentPlid, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByParentPlid_Version_First(
			parentPlid, version, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where parentPlid = &#63; and version = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByParentPlid_Version_Last(
			long parentPlid, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByParentPlid_Version_Last(
			parentPlid, version, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where parentPlid = &#63; and version = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByParentPlid_Version_Last(
		long parentPlid, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByParentPlid_Version_Last(
			parentPlid, version, orderByComparator);
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where parentPlid = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param parentPlid the parent plid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion[] findByParentPlid_Version_PrevAndNext(
			long layoutVersionId, long parentPlid, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByParentPlid_Version_PrevAndNext(
			layoutVersionId, parentPlid, version, orderByComparator);
	}

	/**
	 * Removes all the layout versions where parentPlid = &#63; and version = &#63; from the database.
	 *
	 * @param parentPlid the parent plid
	 * @param version the version
	 */
	public static void removeByParentPlid_Version(
		long parentPlid, int version) {

		getPersistence().removeByParentPlid_Version(parentPlid, version);
	}

	/**
	 * Returns the number of layout versions where parentPlid = &#63; and version = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public static int countByParentPlid_Version(long parentPlid, int version) {
		return getPersistence().countByParentPlid_Version(parentPlid, version);
	}

	/**
	 * Returns all the layout versions where iconImageId = &#63;.
	 *
	 * @param iconImageId the icon image ID
	 * @return the matching layout versions
	 */
	public static List<LayoutVersion> findByIconImageId(long iconImageId) {
		return getPersistence().findByIconImageId(iconImageId);
	}

	/**
	 * Returns a range of all the layout versions where iconImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param iconImageId the icon image ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public static List<LayoutVersion> findByIconImageId(
		long iconImageId, int start, int end) {

		return getPersistence().findByIconImageId(iconImageId, start, end);
	}

	/**
	 * Returns an ordered range of all the layout versions where iconImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param iconImageId the icon image ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByIconImageId(
		long iconImageId, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findByIconImageId(
			iconImageId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions where iconImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param iconImageId the icon image ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByIconImageId(
		long iconImageId, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByIconImageId(
			iconImageId, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first layout version in the ordered set where iconImageId = &#63;.
	 *
	 * @param iconImageId the icon image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByIconImageId_First(
			long iconImageId,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByIconImageId_First(
			iconImageId, orderByComparator);
	}

	/**
	 * Returns the first layout version in the ordered set where iconImageId = &#63;.
	 *
	 * @param iconImageId the icon image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByIconImageId_First(
		long iconImageId, OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByIconImageId_First(
			iconImageId, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where iconImageId = &#63;.
	 *
	 * @param iconImageId the icon image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByIconImageId_Last(
			long iconImageId,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByIconImageId_Last(
			iconImageId, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where iconImageId = &#63;.
	 *
	 * @param iconImageId the icon image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByIconImageId_Last(
		long iconImageId, OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByIconImageId_Last(
			iconImageId, orderByComparator);
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where iconImageId = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param iconImageId the icon image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion[] findByIconImageId_PrevAndNext(
			long layoutVersionId, long iconImageId,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByIconImageId_PrevAndNext(
			layoutVersionId, iconImageId, orderByComparator);
	}

	/**
	 * Removes all the layout versions where iconImageId = &#63; from the database.
	 *
	 * @param iconImageId the icon image ID
	 */
	public static void removeByIconImageId(long iconImageId) {
		getPersistence().removeByIconImageId(iconImageId);
	}

	/**
	 * Returns the number of layout versions where iconImageId = &#63;.
	 *
	 * @param iconImageId the icon image ID
	 * @return the number of matching layout versions
	 */
	public static int countByIconImageId(long iconImageId) {
		return getPersistence().countByIconImageId(iconImageId);
	}

	/**
	 * Returns all the layout versions where iconImageId = &#63; and version = &#63;.
	 *
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @return the matching layout versions
	 */
	public static List<LayoutVersion> findByIconImageId_Version(
		long iconImageId, int version) {

		return getPersistence().findByIconImageId_Version(iconImageId, version);
	}

	/**
	 * Returns a range of all the layout versions where iconImageId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public static List<LayoutVersion> findByIconImageId_Version(
		long iconImageId, int version, int start, int end) {

		return getPersistence().findByIconImageId_Version(
			iconImageId, version, start, end);
	}

	/**
	 * Returns an ordered range of all the layout versions where iconImageId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByIconImageId_Version(
		long iconImageId, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findByIconImageId_Version(
			iconImageId, version, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions where iconImageId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByIconImageId_Version(
		long iconImageId, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByIconImageId_Version(
			iconImageId, version, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Returns the first layout version in the ordered set where iconImageId = &#63; and version = &#63;.
	 *
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByIconImageId_Version_First(
			long iconImageId, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByIconImageId_Version_First(
			iconImageId, version, orderByComparator);
	}

	/**
	 * Returns the first layout version in the ordered set where iconImageId = &#63; and version = &#63;.
	 *
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByIconImageId_Version_First(
		long iconImageId, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByIconImageId_Version_First(
			iconImageId, version, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where iconImageId = &#63; and version = &#63;.
	 *
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByIconImageId_Version_Last(
			long iconImageId, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByIconImageId_Version_Last(
			iconImageId, version, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where iconImageId = &#63; and version = &#63;.
	 *
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByIconImageId_Version_Last(
		long iconImageId, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByIconImageId_Version_Last(
			iconImageId, version, orderByComparator);
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where iconImageId = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion[] findByIconImageId_Version_PrevAndNext(
			long layoutVersionId, long iconImageId, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByIconImageId_Version_PrevAndNext(
			layoutVersionId, iconImageId, version, orderByComparator);
	}

	/**
	 * Removes all the layout versions where iconImageId = &#63; and version = &#63; from the database.
	 *
	 * @param iconImageId the icon image ID
	 * @param version the version
	 */
	public static void removeByIconImageId_Version(
		long iconImageId, int version) {

		getPersistence().removeByIconImageId_Version(iconImageId, version);
	}

	/**
	 * Returns the number of layout versions where iconImageId = &#63; and version = &#63;.
	 *
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public static int countByIconImageId_Version(
		long iconImageId, int version) {

		return getPersistence().countByIconImageId_Version(
			iconImageId, version);
	}

	/**
	 * Returns all the layout versions where layoutPrototypeUuid = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @return the matching layout versions
	 */
	public static List<LayoutVersion> findByLayoutPrototypeUuid(
		String layoutPrototypeUuid) {

		return getPersistence().findByLayoutPrototypeUuid(layoutPrototypeUuid);
	}

	/**
	 * Returns a range of all the layout versions where layoutPrototypeUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public static List<LayoutVersion> findByLayoutPrototypeUuid(
		String layoutPrototypeUuid, int start, int end) {

		return getPersistence().findByLayoutPrototypeUuid(
			layoutPrototypeUuid, start, end);
	}

	/**
	 * Returns an ordered range of all the layout versions where layoutPrototypeUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByLayoutPrototypeUuid(
		String layoutPrototypeUuid, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findByLayoutPrototypeUuid(
			layoutPrototypeUuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions where layoutPrototypeUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByLayoutPrototypeUuid(
		String layoutPrototypeUuid, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByLayoutPrototypeUuid(
			layoutPrototypeUuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Returns the first layout version in the ordered set where layoutPrototypeUuid = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByLayoutPrototypeUuid_First(
			String layoutPrototypeUuid,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByLayoutPrototypeUuid_First(
			layoutPrototypeUuid, orderByComparator);
	}

	/**
	 * Returns the first layout version in the ordered set where layoutPrototypeUuid = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByLayoutPrototypeUuid_First(
		String layoutPrototypeUuid,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByLayoutPrototypeUuid_First(
			layoutPrototypeUuid, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where layoutPrototypeUuid = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByLayoutPrototypeUuid_Last(
			String layoutPrototypeUuid,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByLayoutPrototypeUuid_Last(
			layoutPrototypeUuid, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where layoutPrototypeUuid = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByLayoutPrototypeUuid_Last(
		String layoutPrototypeUuid,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByLayoutPrototypeUuid_Last(
			layoutPrototypeUuid, orderByComparator);
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where layoutPrototypeUuid = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion[] findByLayoutPrototypeUuid_PrevAndNext(
			long layoutVersionId, String layoutPrototypeUuid,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByLayoutPrototypeUuid_PrevAndNext(
			layoutVersionId, layoutPrototypeUuid, orderByComparator);
	}

	/**
	 * Removes all the layout versions where layoutPrototypeUuid = &#63; from the database.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 */
	public static void removeByLayoutPrototypeUuid(String layoutPrototypeUuid) {
		getPersistence().removeByLayoutPrototypeUuid(layoutPrototypeUuid);
	}

	/**
	 * Returns the number of layout versions where layoutPrototypeUuid = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @return the number of matching layout versions
	 */
	public static int countByLayoutPrototypeUuid(String layoutPrototypeUuid) {
		return getPersistence().countByLayoutPrototypeUuid(layoutPrototypeUuid);
	}

	/**
	 * Returns all the layout versions where layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @return the matching layout versions
	 */
	public static List<LayoutVersion> findByLayoutPrototypeUuid_Version(
		String layoutPrototypeUuid, int version) {

		return getPersistence().findByLayoutPrototypeUuid_Version(
			layoutPrototypeUuid, version);
	}

	/**
	 * Returns a range of all the layout versions where layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public static List<LayoutVersion> findByLayoutPrototypeUuid_Version(
		String layoutPrototypeUuid, int version, int start, int end) {

		return getPersistence().findByLayoutPrototypeUuid_Version(
			layoutPrototypeUuid, version, start, end);
	}

	/**
	 * Returns an ordered range of all the layout versions where layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByLayoutPrototypeUuid_Version(
		String layoutPrototypeUuid, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findByLayoutPrototypeUuid_Version(
			layoutPrototypeUuid, version, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions where layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByLayoutPrototypeUuid_Version(
		String layoutPrototypeUuid, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByLayoutPrototypeUuid_Version(
			layoutPrototypeUuid, version, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Returns the first layout version in the ordered set where layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByLayoutPrototypeUuid_Version_First(
			String layoutPrototypeUuid, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByLayoutPrototypeUuid_Version_First(
			layoutPrototypeUuid, version, orderByComparator);
	}

	/**
	 * Returns the first layout version in the ordered set where layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByLayoutPrototypeUuid_Version_First(
		String layoutPrototypeUuid, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByLayoutPrototypeUuid_Version_First(
			layoutPrototypeUuid, version, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByLayoutPrototypeUuid_Version_Last(
			String layoutPrototypeUuid, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByLayoutPrototypeUuid_Version_Last(
			layoutPrototypeUuid, version, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByLayoutPrototypeUuid_Version_Last(
		String layoutPrototypeUuid, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByLayoutPrototypeUuid_Version_Last(
			layoutPrototypeUuid, version, orderByComparator);
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion[] findByLayoutPrototypeUuid_Version_PrevAndNext(
			long layoutVersionId, String layoutPrototypeUuid, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByLayoutPrototypeUuid_Version_PrevAndNext(
			layoutVersionId, layoutPrototypeUuid, version, orderByComparator);
	}

	/**
	 * Removes all the layout versions where layoutPrototypeUuid = &#63; and version = &#63; from the database.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 */
	public static void removeByLayoutPrototypeUuid_Version(
		String layoutPrototypeUuid, int version) {

		getPersistence().removeByLayoutPrototypeUuid_Version(
			layoutPrototypeUuid, version);
	}

	/**
	 * Returns the number of layout versions where layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public static int countByLayoutPrototypeUuid_Version(
		String layoutPrototypeUuid, int version) {

		return getPersistence().countByLayoutPrototypeUuid_Version(
			layoutPrototypeUuid, version);
	}

	/**
	 * Returns all the layout versions where sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @return the matching layout versions
	 */
	public static List<LayoutVersion> findBySourcePrototypeLayoutUuid(
		String sourcePrototypeLayoutUuid) {

		return getPersistence().findBySourcePrototypeLayoutUuid(
			sourcePrototypeLayoutUuid);
	}

	/**
	 * Returns a range of all the layout versions where sourcePrototypeLayoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public static List<LayoutVersion> findBySourcePrototypeLayoutUuid(
		String sourcePrototypeLayoutUuid, int start, int end) {

		return getPersistence().findBySourcePrototypeLayoutUuid(
			sourcePrototypeLayoutUuid, start, end);
	}

	/**
	 * Returns an ordered range of all the layout versions where sourcePrototypeLayoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findBySourcePrototypeLayoutUuid(
		String sourcePrototypeLayoutUuid, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findBySourcePrototypeLayoutUuid(
			sourcePrototypeLayoutUuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions where sourcePrototypeLayoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findBySourcePrototypeLayoutUuid(
		String sourcePrototypeLayoutUuid, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findBySourcePrototypeLayoutUuid(
			sourcePrototypeLayoutUuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Returns the first layout version in the ordered set where sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findBySourcePrototypeLayoutUuid_First(
			String sourcePrototypeLayoutUuid,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findBySourcePrototypeLayoutUuid_First(
			sourcePrototypeLayoutUuid, orderByComparator);
	}

	/**
	 * Returns the first layout version in the ordered set where sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchBySourcePrototypeLayoutUuid_First(
		String sourcePrototypeLayoutUuid,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchBySourcePrototypeLayoutUuid_First(
			sourcePrototypeLayoutUuid, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findBySourcePrototypeLayoutUuid_Last(
			String sourcePrototypeLayoutUuid,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findBySourcePrototypeLayoutUuid_Last(
			sourcePrototypeLayoutUuid, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchBySourcePrototypeLayoutUuid_Last(
		String sourcePrototypeLayoutUuid,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchBySourcePrototypeLayoutUuid_Last(
			sourcePrototypeLayoutUuid, orderByComparator);
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion[] findBySourcePrototypeLayoutUuid_PrevAndNext(
			long layoutVersionId, String sourcePrototypeLayoutUuid,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findBySourcePrototypeLayoutUuid_PrevAndNext(
			layoutVersionId, sourcePrototypeLayoutUuid, orderByComparator);
	}

	/**
	 * Removes all the layout versions where sourcePrototypeLayoutUuid = &#63; from the database.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 */
	public static void removeBySourcePrototypeLayoutUuid(
		String sourcePrototypeLayoutUuid) {

		getPersistence().removeBySourcePrototypeLayoutUuid(
			sourcePrototypeLayoutUuid);
	}

	/**
	 * Returns the number of layout versions where sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @return the number of matching layout versions
	 */
	public static int countBySourcePrototypeLayoutUuid(
		String sourcePrototypeLayoutUuid) {

		return getPersistence().countBySourcePrototypeLayoutUuid(
			sourcePrototypeLayoutUuid);
	}

	/**
	 * Returns all the layout versions where sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @return the matching layout versions
	 */
	public static List<LayoutVersion> findBySourcePrototypeLayoutUuid_Version(
		String sourcePrototypeLayoutUuid, int version) {

		return getPersistence().findBySourcePrototypeLayoutUuid_Version(
			sourcePrototypeLayoutUuid, version);
	}

	/**
	 * Returns a range of all the layout versions where sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public static List<LayoutVersion> findBySourcePrototypeLayoutUuid_Version(
		String sourcePrototypeLayoutUuid, int version, int start, int end) {

		return getPersistence().findBySourcePrototypeLayoutUuid_Version(
			sourcePrototypeLayoutUuid, version, start, end);
	}

	/**
	 * Returns an ordered range of all the layout versions where sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findBySourcePrototypeLayoutUuid_Version(
		String sourcePrototypeLayoutUuid, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findBySourcePrototypeLayoutUuid_Version(
			sourcePrototypeLayoutUuid, version, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions where sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findBySourcePrototypeLayoutUuid_Version(
		String sourcePrototypeLayoutUuid, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findBySourcePrototypeLayoutUuid_Version(
			sourcePrototypeLayoutUuid, version, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Returns the first layout version in the ordered set where sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findBySourcePrototypeLayoutUuid_Version_First(
			String sourcePrototypeLayoutUuid, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findBySourcePrototypeLayoutUuid_Version_First(
			sourcePrototypeLayoutUuid, version, orderByComparator);
	}

	/**
	 * Returns the first layout version in the ordered set where sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchBySourcePrototypeLayoutUuid_Version_First(
		String sourcePrototypeLayoutUuid, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchBySourcePrototypeLayoutUuid_Version_First(
			sourcePrototypeLayoutUuid, version, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findBySourcePrototypeLayoutUuid_Version_Last(
			String sourcePrototypeLayoutUuid, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findBySourcePrototypeLayoutUuid_Version_Last(
			sourcePrototypeLayoutUuid, version, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchBySourcePrototypeLayoutUuid_Version_Last(
		String sourcePrototypeLayoutUuid, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchBySourcePrototypeLayoutUuid_Version_Last(
			sourcePrototypeLayoutUuid, version, orderByComparator);
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion[]
			findBySourcePrototypeLayoutUuid_Version_PrevAndNext(
				long layoutVersionId, String sourcePrototypeLayoutUuid,
				int version, OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().
			findBySourcePrototypeLayoutUuid_Version_PrevAndNext(
				layoutVersionId, sourcePrototypeLayoutUuid, version,
				orderByComparator);
	}

	/**
	 * Removes all the layout versions where sourcePrototypeLayoutUuid = &#63; and version = &#63; from the database.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 */
	public static void removeBySourcePrototypeLayoutUuid_Version(
		String sourcePrototypeLayoutUuid, int version) {

		getPersistence().removeBySourcePrototypeLayoutUuid_Version(
			sourcePrototypeLayoutUuid, version);
	}

	/**
	 * Returns the number of layout versions where sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public static int countBySourcePrototypeLayoutUuid_Version(
		String sourcePrototypeLayoutUuid, int version) {

		return getPersistence().countBySourcePrototypeLayoutUuid_Version(
			sourcePrototypeLayoutUuid, version);
	}

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @return the matching layout versions
	 */
	public static List<LayoutVersion> findByG_P(
		long groupId, boolean privateLayout) {

		return getPersistence().findByG_P(groupId, privateLayout);
	}

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P(
		long groupId, boolean privateLayout, int start, int end) {

		return getPersistence().findByG_P(groupId, privateLayout, start, end);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P(
		long groupId, boolean privateLayout, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findByG_P(
			groupId, privateLayout, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P(
		long groupId, boolean privateLayout, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByG_P(
			groupId, privateLayout, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByG_P_First(
			long groupId, boolean privateLayout,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_First(
			groupId, privateLayout, orderByComparator);
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByG_P_First(
		long groupId, boolean privateLayout,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByG_P_First(
			groupId, privateLayout, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByG_P_Last(
			long groupId, boolean privateLayout,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_Last(
			groupId, privateLayout, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByG_P_Last(
		long groupId, boolean privateLayout,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByG_P_Last(
			groupId, privateLayout, orderByComparator);
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion[] findByG_P_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_PrevAndNext(
			layoutVersionId, groupId, privateLayout, orderByComparator);
	}

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 */
	public static void removeByG_P(long groupId, boolean privateLayout) {
		getPersistence().removeByG_P(groupId, privateLayout);
	}

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @return the number of matching layout versions
	 */
	public static int countByG_P(long groupId, boolean privateLayout) {
		return getPersistence().countByG_P(groupId, privateLayout);
	}

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @return the matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_Version(
		long groupId, boolean privateLayout, int version) {

		return getPersistence().findByG_P_Version(
			groupId, privateLayout, version);
	}

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_Version(
		long groupId, boolean privateLayout, int version, int start, int end) {

		return getPersistence().findByG_P_Version(
			groupId, privateLayout, version, start, end);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_Version(
		long groupId, boolean privateLayout, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findByG_P_Version(
			groupId, privateLayout, version, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_Version(
		long groupId, boolean privateLayout, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByG_P_Version(
			groupId, privateLayout, version, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByG_P_Version_First(
			long groupId, boolean privateLayout, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_Version_First(
			groupId, privateLayout, version, orderByComparator);
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByG_P_Version_First(
		long groupId, boolean privateLayout, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByG_P_Version_First(
			groupId, privateLayout, version, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByG_P_Version_Last(
			long groupId, boolean privateLayout, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_Version_Last(
			groupId, privateLayout, version, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByG_P_Version_Last(
		long groupId, boolean privateLayout, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByG_P_Version_Last(
			groupId, privateLayout, version, orderByComparator);
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion[] findByG_P_Version_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			int version, OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_Version_PrevAndNext(
			layoutVersionId, groupId, privateLayout, version,
			orderByComparator);
	}

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 */
	public static void removeByG_P_Version(
		long groupId, boolean privateLayout, int version) {

		getPersistence().removeByG_P_Version(groupId, privateLayout, version);
	}

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public static int countByG_P_Version(
		long groupId, boolean privateLayout, int version) {

		return getPersistence().countByG_P_Version(
			groupId, privateLayout, version);
	}

	/**
	 * Returns all the layout versions where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching layout versions
	 */
	public static List<LayoutVersion> findByG_T(long groupId, String type) {
		return getPersistence().findByG_T(groupId, type);
	}

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_T(
		long groupId, String type, int start, int end) {

		return getPersistence().findByG_T(groupId, type, start, end);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_T(
		long groupId, String type, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findByG_T(
			groupId, type, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_T(
		long groupId, String type, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByG_T(
			groupId, type, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByG_T_First(
			long groupId, String type,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_T_First(
			groupId, type, orderByComparator);
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByG_T_First(
		long groupId, String type,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByG_T_First(
			groupId, type, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByG_T_Last(
			long groupId, String type,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_T_Last(
			groupId, type, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByG_T_Last(
		long groupId, String type,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByG_T_Last(
			groupId, type, orderByComparator);
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion[] findByG_T_PrevAndNext(
			long layoutVersionId, long groupId, String type,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_T_PrevAndNext(
			layoutVersionId, groupId, type, orderByComparator);
	}

	/**
	 * Removes all the layout versions where groupId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 */
	public static void removeByG_T(long groupId, String type) {
		getPersistence().removeByG_T(groupId, type);
	}

	/**
	 * Returns the number of layout versions where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching layout versions
	 */
	public static int countByG_T(long groupId, String type) {
		return getPersistence().countByG_T(groupId, type);
	}

	/**
	 * Returns all the layout versions where groupId = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param version the version
	 * @return the matching layout versions
	 */
	public static List<LayoutVersion> findByG_T_Version(
		long groupId, String type, int version) {

		return getPersistence().findByG_T_Version(groupId, type, version);
	}

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and type = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_T_Version(
		long groupId, String type, int version, int start, int end) {

		return getPersistence().findByG_T_Version(
			groupId, type, version, start, end);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and type = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_T_Version(
		long groupId, String type, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findByG_T_Version(
			groupId, type, version, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and type = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_T_Version(
		long groupId, String type, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByG_T_Version(
			groupId, type, version, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByG_T_Version_First(
			long groupId, String type, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_T_Version_First(
			groupId, type, version, orderByComparator);
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByG_T_Version_First(
		long groupId, String type, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByG_T_Version_First(
			groupId, type, version, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByG_T_Version_Last(
			long groupId, String type, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_T_Version_Last(
			groupId, type, version, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByG_T_Version_Last(
		long groupId, String type, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByG_T_Version_Last(
			groupId, type, version, orderByComparator);
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion[] findByG_T_Version_PrevAndNext(
			long layoutVersionId, long groupId, String type, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_T_Version_PrevAndNext(
			layoutVersionId, groupId, type, version, orderByComparator);
	}

	/**
	 * Removes all the layout versions where groupId = &#63; and type = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param version the version
	 */
	public static void removeByG_T_Version(
		long groupId, String type, int version) {

		getPersistence().removeByG_T_Version(groupId, type, version);
	}

	/**
	 * Returns the number of layout versions where groupId = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public static int countByG_T_Version(
		long groupId, String type, int version) {

		return getPersistence().countByG_T_Version(groupId, type, version);
	}

	/**
	 * Returns all the layout versions where companyId = &#63; and layoutPrototypeUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @return the matching layout versions
	 */
	public static List<LayoutVersion> findByC_L(
		long companyId, String layoutPrototypeUuid) {

		return getPersistence().findByC_L(companyId, layoutPrototypeUuid);
	}

	/**
	 * Returns a range of all the layout versions where companyId = &#63; and layoutPrototypeUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public static List<LayoutVersion> findByC_L(
		long companyId, String layoutPrototypeUuid, int start, int end) {

		return getPersistence().findByC_L(
			companyId, layoutPrototypeUuid, start, end);
	}

	/**
	 * Returns an ordered range of all the layout versions where companyId = &#63; and layoutPrototypeUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByC_L(
		long companyId, String layoutPrototypeUuid, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findByC_L(
			companyId, layoutPrototypeUuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions where companyId = &#63; and layoutPrototypeUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByC_L(
		long companyId, String layoutPrototypeUuid, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByC_L(
			companyId, layoutPrototypeUuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Returns the first layout version in the ordered set where companyId = &#63; and layoutPrototypeUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByC_L_First(
			long companyId, String layoutPrototypeUuid,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByC_L_First(
			companyId, layoutPrototypeUuid, orderByComparator);
	}

	/**
	 * Returns the first layout version in the ordered set where companyId = &#63; and layoutPrototypeUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByC_L_First(
		long companyId, String layoutPrototypeUuid,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByC_L_First(
			companyId, layoutPrototypeUuid, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where companyId = &#63; and layoutPrototypeUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByC_L_Last(
			long companyId, String layoutPrototypeUuid,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByC_L_Last(
			companyId, layoutPrototypeUuid, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where companyId = &#63; and layoutPrototypeUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByC_L_Last(
		long companyId, String layoutPrototypeUuid,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByC_L_Last(
			companyId, layoutPrototypeUuid, orderByComparator);
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where companyId = &#63; and layoutPrototypeUuid = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion[] findByC_L_PrevAndNext(
			long layoutVersionId, long companyId, String layoutPrototypeUuid,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByC_L_PrevAndNext(
			layoutVersionId, companyId, layoutPrototypeUuid, orderByComparator);
	}

	/**
	 * Removes all the layout versions where companyId = &#63; and layoutPrototypeUuid = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 */
	public static void removeByC_L(long companyId, String layoutPrototypeUuid) {
		getPersistence().removeByC_L(companyId, layoutPrototypeUuid);
	}

	/**
	 * Returns the number of layout versions where companyId = &#63; and layoutPrototypeUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @return the number of matching layout versions
	 */
	public static int countByC_L(long companyId, String layoutPrototypeUuid) {
		return getPersistence().countByC_L(companyId, layoutPrototypeUuid);
	}

	/**
	 * Returns all the layout versions where companyId = &#63; and layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @return the matching layout versions
	 */
	public static List<LayoutVersion> findByC_L_Version(
		long companyId, String layoutPrototypeUuid, int version) {

		return getPersistence().findByC_L_Version(
			companyId, layoutPrototypeUuid, version);
	}

	/**
	 * Returns a range of all the layout versions where companyId = &#63; and layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public static List<LayoutVersion> findByC_L_Version(
		long companyId, String layoutPrototypeUuid, int version, int start,
		int end) {

		return getPersistence().findByC_L_Version(
			companyId, layoutPrototypeUuid, version, start, end);
	}

	/**
	 * Returns an ordered range of all the layout versions where companyId = &#63; and layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByC_L_Version(
		long companyId, String layoutPrototypeUuid, int version, int start,
		int end, OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findByC_L_Version(
			companyId, layoutPrototypeUuid, version, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions where companyId = &#63; and layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByC_L_Version(
		long companyId, String layoutPrototypeUuid, int version, int start,
		int end, OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByC_L_Version(
			companyId, layoutPrototypeUuid, version, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first layout version in the ordered set where companyId = &#63; and layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByC_L_Version_First(
			long companyId, String layoutPrototypeUuid, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByC_L_Version_First(
			companyId, layoutPrototypeUuid, version, orderByComparator);
	}

	/**
	 * Returns the first layout version in the ordered set where companyId = &#63; and layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByC_L_Version_First(
		long companyId, String layoutPrototypeUuid, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByC_L_Version_First(
			companyId, layoutPrototypeUuid, version, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where companyId = &#63; and layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByC_L_Version_Last(
			long companyId, String layoutPrototypeUuid, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByC_L_Version_Last(
			companyId, layoutPrototypeUuid, version, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where companyId = &#63; and layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByC_L_Version_Last(
		long companyId, String layoutPrototypeUuid, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByC_L_Version_Last(
			companyId, layoutPrototypeUuid, version, orderByComparator);
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where companyId = &#63; and layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion[] findByC_L_Version_PrevAndNext(
			long layoutVersionId, long companyId, String layoutPrototypeUuid,
			int version, OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByC_L_Version_PrevAndNext(
			layoutVersionId, companyId, layoutPrototypeUuid, version,
			orderByComparator);
	}

	/**
	 * Removes all the layout versions where companyId = &#63; and layoutPrototypeUuid = &#63; and version = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 */
	public static void removeByC_L_Version(
		long companyId, String layoutPrototypeUuid, int version) {

		getPersistence().removeByC_L_Version(
			companyId, layoutPrototypeUuid, version);
	}

	/**
	 * Returns the number of layout versions where companyId = &#63; and layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public static int countByC_L_Version(
		long companyId, String layoutPrototypeUuid, int version) {

		return getPersistence().countByC_L_Version(
			companyId, layoutPrototypeUuid, version);
	}

	/**
	 * Returns all the layout versions where privateLayout = &#63; and iconImageId = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @return the matching layout versions
	 */
	public static List<LayoutVersion> findByP_I(
		boolean privateLayout, long iconImageId) {

		return getPersistence().findByP_I(privateLayout, iconImageId);
	}

	/**
	 * Returns a range of all the layout versions where privateLayout = &#63; and iconImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public static List<LayoutVersion> findByP_I(
		boolean privateLayout, long iconImageId, int start, int end) {

		return getPersistence().findByP_I(
			privateLayout, iconImageId, start, end);
	}

	/**
	 * Returns an ordered range of all the layout versions where privateLayout = &#63; and iconImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByP_I(
		boolean privateLayout, long iconImageId, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findByP_I(
			privateLayout, iconImageId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions where privateLayout = &#63; and iconImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByP_I(
		boolean privateLayout, long iconImageId, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByP_I(
			privateLayout, iconImageId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Returns the first layout version in the ordered set where privateLayout = &#63; and iconImageId = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByP_I_First(
			boolean privateLayout, long iconImageId,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByP_I_First(
			privateLayout, iconImageId, orderByComparator);
	}

	/**
	 * Returns the first layout version in the ordered set where privateLayout = &#63; and iconImageId = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByP_I_First(
		boolean privateLayout, long iconImageId,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByP_I_First(
			privateLayout, iconImageId, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where privateLayout = &#63; and iconImageId = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByP_I_Last(
			boolean privateLayout, long iconImageId,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByP_I_Last(
			privateLayout, iconImageId, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where privateLayout = &#63; and iconImageId = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByP_I_Last(
		boolean privateLayout, long iconImageId,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByP_I_Last(
			privateLayout, iconImageId, orderByComparator);
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where privateLayout = &#63; and iconImageId = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion[] findByP_I_PrevAndNext(
			long layoutVersionId, boolean privateLayout, long iconImageId,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByP_I_PrevAndNext(
			layoutVersionId, privateLayout, iconImageId, orderByComparator);
	}

	/**
	 * Removes all the layout versions where privateLayout = &#63; and iconImageId = &#63; from the database.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 */
	public static void removeByP_I(boolean privateLayout, long iconImageId) {
		getPersistence().removeByP_I(privateLayout, iconImageId);
	}

	/**
	 * Returns the number of layout versions where privateLayout = &#63; and iconImageId = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @return the number of matching layout versions
	 */
	public static int countByP_I(boolean privateLayout, long iconImageId) {
		return getPersistence().countByP_I(privateLayout, iconImageId);
	}

	/**
	 * Returns all the layout versions where privateLayout = &#63; and iconImageId = &#63; and version = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @return the matching layout versions
	 */
	public static List<LayoutVersion> findByP_I_Version(
		boolean privateLayout, long iconImageId, int version) {

		return getPersistence().findByP_I_Version(
			privateLayout, iconImageId, version);
	}

	/**
	 * Returns a range of all the layout versions where privateLayout = &#63; and iconImageId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public static List<LayoutVersion> findByP_I_Version(
		boolean privateLayout, long iconImageId, int version, int start,
		int end) {

		return getPersistence().findByP_I_Version(
			privateLayout, iconImageId, version, start, end);
	}

	/**
	 * Returns an ordered range of all the layout versions where privateLayout = &#63; and iconImageId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByP_I_Version(
		boolean privateLayout, long iconImageId, int version, int start,
		int end, OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findByP_I_Version(
			privateLayout, iconImageId, version, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions where privateLayout = &#63; and iconImageId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByP_I_Version(
		boolean privateLayout, long iconImageId, int version, int start,
		int end, OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByP_I_Version(
			privateLayout, iconImageId, version, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Returns the first layout version in the ordered set where privateLayout = &#63; and iconImageId = &#63; and version = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByP_I_Version_First(
			boolean privateLayout, long iconImageId, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByP_I_Version_First(
			privateLayout, iconImageId, version, orderByComparator);
	}

	/**
	 * Returns the first layout version in the ordered set where privateLayout = &#63; and iconImageId = &#63; and version = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByP_I_Version_First(
		boolean privateLayout, long iconImageId, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByP_I_Version_First(
			privateLayout, iconImageId, version, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where privateLayout = &#63; and iconImageId = &#63; and version = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByP_I_Version_Last(
			boolean privateLayout, long iconImageId, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByP_I_Version_Last(
			privateLayout, iconImageId, version, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where privateLayout = &#63; and iconImageId = &#63; and version = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByP_I_Version_Last(
		boolean privateLayout, long iconImageId, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByP_I_Version_Last(
			privateLayout, iconImageId, version, orderByComparator);
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where privateLayout = &#63; and iconImageId = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion[] findByP_I_Version_PrevAndNext(
			long layoutVersionId, boolean privateLayout, long iconImageId,
			int version, OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByP_I_Version_PrevAndNext(
			layoutVersionId, privateLayout, iconImageId, version,
			orderByComparator);
	}

	/**
	 * Removes all the layout versions where privateLayout = &#63; and iconImageId = &#63; and version = &#63; from the database.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param version the version
	 */
	public static void removeByP_I_Version(
		boolean privateLayout, long iconImageId, int version) {

		getPersistence().removeByP_I_Version(
			privateLayout, iconImageId, version);
	}

	/**
	 * Returns the number of layout versions where privateLayout = &#63; and iconImageId = &#63; and version = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public static int countByP_I_Version(
		boolean privateLayout, long iconImageId, int version) {

		return getPersistence().countByP_I_Version(
			privateLayout, iconImageId, version);
	}

	/**
	 * Returns all the layout versions where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching layout versions
	 */
	public static List<LayoutVersion> findByC_C(
		long classNameId, long classPK) {

		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	 * Returns a range of all the layout versions where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public static List<LayoutVersion> findByC_C(
		long classNameId, long classPK, int start, int end) {

		return getPersistence().findByC_C(classNameId, classPK, start, end);
	}

	/**
	 * Returns an ordered range of all the layout versions where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findByC_C(
			classNameId, classPK, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByC_C(
			classNameId, classPK, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Returns the first layout version in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByC_C_First(
			long classNameId, long classPK,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByC_C_First(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the first layout version in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByC_C_First(
		long classNameId, long classPK,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByC_C_First(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByC_C_Last(
			long classNameId, long classPK,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByC_C_Last(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByC_C_Last(
		long classNameId, long classPK,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByC_C_Last(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion[] findByC_C_PrevAndNext(
			long layoutVersionId, long classNameId, long classPK,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByC_C_PrevAndNext(
			layoutVersionId, classNameId, classPK, orderByComparator);
	}

	/**
	 * Removes all the layout versions where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public static void removeByC_C(long classNameId, long classPK) {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	 * Returns the number of layout versions where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching layout versions
	 */
	public static int countByC_C(long classNameId, long classPK) {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	 * Returns all the layout versions where classNameId = &#63; and classPK = &#63; and version = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param version the version
	 * @return the matching layout versions
	 */
	public static List<LayoutVersion> findByC_C_Version(
		long classNameId, long classPK, int version) {

		return getPersistence().findByC_C_Version(
			classNameId, classPK, version);
	}

	/**
	 * Returns a range of all the layout versions where classNameId = &#63; and classPK = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public static List<LayoutVersion> findByC_C_Version(
		long classNameId, long classPK, int version, int start, int end) {

		return getPersistence().findByC_C_Version(
			classNameId, classPK, version, start, end);
	}

	/**
	 * Returns an ordered range of all the layout versions where classNameId = &#63; and classPK = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByC_C_Version(
		long classNameId, long classPK, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findByC_C_Version(
			classNameId, classPK, version, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions where classNameId = &#63; and classPK = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByC_C_Version(
		long classNameId, long classPK, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByC_C_Version(
			classNameId, classPK, version, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Returns the first layout version in the ordered set where classNameId = &#63; and classPK = &#63; and version = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByC_C_Version_First(
			long classNameId, long classPK, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByC_C_Version_First(
			classNameId, classPK, version, orderByComparator);
	}

	/**
	 * Returns the first layout version in the ordered set where classNameId = &#63; and classPK = &#63; and version = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByC_C_Version_First(
		long classNameId, long classPK, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByC_C_Version_First(
			classNameId, classPK, version, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where classNameId = &#63; and classPK = &#63; and version = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByC_C_Version_Last(
			long classNameId, long classPK, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByC_C_Version_Last(
			classNameId, classPK, version, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where classNameId = &#63; and classPK = &#63; and version = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByC_C_Version_Last(
		long classNameId, long classPK, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByC_C_Version_Last(
			classNameId, classPK, version, orderByComparator);
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where classNameId = &#63; and classPK = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion[] findByC_C_Version_PrevAndNext(
			long layoutVersionId, long classNameId, long classPK, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByC_C_Version_PrevAndNext(
			layoutVersionId, classNameId, classPK, version, orderByComparator);
	}

	/**
	 * Removes all the layout versions where classNameId = &#63; and classPK = &#63; and version = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param version the version
	 */
	public static void removeByC_C_Version(
		long classNameId, long classPK, int version) {

		getPersistence().removeByC_C_Version(classNameId, classPK, version);
	}

	/**
	 * Returns the number of layout versions where classNameId = &#63; and classPK = &#63; and version = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public static int countByC_C_Version(
		long classNameId, long classPK, int version) {

		return getPersistence().countByC_C_Version(
			classNameId, classPK, version);
	}

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_L(
		long groupId, boolean privateLayout, long layoutId) {

		return getPersistence().findByG_P_L(groupId, privateLayout, layoutId);
	}

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_L(
		long groupId, boolean privateLayout, long layoutId, int start,
		int end) {

		return getPersistence().findByG_P_L(
			groupId, privateLayout, layoutId, start, end);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_L(
		long groupId, boolean privateLayout, long layoutId, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findByG_P_L(
			groupId, privateLayout, layoutId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_L(
		long groupId, boolean privateLayout, long layoutId, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByG_P_L(
			groupId, privateLayout, layoutId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByG_P_L_First(
			long groupId, boolean privateLayout, long layoutId,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_L_First(
			groupId, privateLayout, layoutId, orderByComparator);
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByG_P_L_First(
		long groupId, boolean privateLayout, long layoutId,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByG_P_L_First(
			groupId, privateLayout, layoutId, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByG_P_L_Last(
			long groupId, boolean privateLayout, long layoutId,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_L_Last(
			groupId, privateLayout, layoutId, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByG_P_L_Last(
		long groupId, boolean privateLayout, long layoutId,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByG_P_L_Last(
			groupId, privateLayout, layoutId, orderByComparator);
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion[] findByG_P_L_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			long layoutId, OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_L_PrevAndNext(
			layoutVersionId, groupId, privateLayout, layoutId,
			orderByComparator);
	}

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 */
	public static void removeByG_P_L(
		long groupId, boolean privateLayout, long layoutId) {

		getPersistence().removeByG_P_L(groupId, privateLayout, layoutId);
	}

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the number of matching layout versions
	 */
	public static int countByG_P_L(
		long groupId, boolean privateLayout, long layoutId) {

		return getPersistence().countByG_P_L(groupId, privateLayout, layoutId);
	}

	/**
	 * Returns the layout version where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and version = &#63; or throws a <code>NoSuchLayoutVersionException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param version the version
	 * @return the matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByG_P_L_Version(
			long groupId, boolean privateLayout, long layoutId, int version)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_L_Version(
			groupId, privateLayout, layoutId, version);
	}

	/**
	 * Returns the layout version where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param version the version
	 * @return the matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByG_P_L_Version(
		long groupId, boolean privateLayout, long layoutId, int version) {

		return getPersistence().fetchByG_P_L_Version(
			groupId, privateLayout, layoutId, version);
	}

	/**
	 * Returns the layout version where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param version the version
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByG_P_L_Version(
		long groupId, boolean privateLayout, long layoutId, int version,
		boolean retrieveFromCache) {

		return getPersistence().fetchByG_P_L_Version(
			groupId, privateLayout, layoutId, version, retrieveFromCache);
	}

	/**
	 * Removes the layout version where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param version the version
	 * @return the layout version that was removed
	 */
	public static LayoutVersion removeByG_P_L_Version(
			long groupId, boolean privateLayout, long layoutId, int version)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().removeByG_P_L_Version(
			groupId, privateLayout, layoutId, version);
	}

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public static int countByG_P_L_Version(
		long groupId, boolean privateLayout, long layoutId, int version) {

		return getPersistence().countByG_P_L_Version(
			groupId, privateLayout, layoutId, version);
	}

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @return the matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_P(
		long groupId, boolean privateLayout, long parentLayoutId) {

		return getPersistence().findByG_P_P(
			groupId, privateLayout, parentLayoutId);
	}

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_P(
		long groupId, boolean privateLayout, long parentLayoutId, int start,
		int end) {

		return getPersistence().findByG_P_P(
			groupId, privateLayout, parentLayoutId, start, end);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_P(
		long groupId, boolean privateLayout, long parentLayoutId, int start,
		int end, OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findByG_P_P(
			groupId, privateLayout, parentLayoutId, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_P(
		long groupId, boolean privateLayout, long parentLayoutId, int start,
		int end, OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByG_P_P(
			groupId, privateLayout, parentLayoutId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByG_P_P_First(
			long groupId, boolean privateLayout, long parentLayoutId,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_P_First(
			groupId, privateLayout, parentLayoutId, orderByComparator);
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByG_P_P_First(
		long groupId, boolean privateLayout, long parentLayoutId,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByG_P_P_First(
			groupId, privateLayout, parentLayoutId, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByG_P_P_Last(
			long groupId, boolean privateLayout, long parentLayoutId,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_P_Last(
			groupId, privateLayout, parentLayoutId, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByG_P_P_Last(
		long groupId, boolean privateLayout, long parentLayoutId,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByG_P_P_Last(
			groupId, privateLayout, parentLayoutId, orderByComparator);
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion[] findByG_P_P_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			long parentLayoutId,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_P_PrevAndNext(
			layoutVersionId, groupId, privateLayout, parentLayoutId,
			orderByComparator);
	}

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 */
	public static void removeByG_P_P(
		long groupId, boolean privateLayout, long parentLayoutId) {

		getPersistence().removeByG_P_P(groupId, privateLayout, parentLayoutId);
	}

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @return the number of matching layout versions
	 */
	public static int countByG_P_P(
		long groupId, boolean privateLayout, long parentLayoutId) {

		return getPersistence().countByG_P_P(
			groupId, privateLayout, parentLayoutId);
	}

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param version the version
	 * @return the matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_P_Version(
		long groupId, boolean privateLayout, long parentLayoutId, int version) {

		return getPersistence().findByG_P_P_Version(
			groupId, privateLayout, parentLayoutId, version);
	}

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_P_Version(
		long groupId, boolean privateLayout, long parentLayoutId, int version,
		int start, int end) {

		return getPersistence().findByG_P_P_Version(
			groupId, privateLayout, parentLayoutId, version, start, end);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_P_Version(
		long groupId, boolean privateLayout, long parentLayoutId, int version,
		int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findByG_P_P_Version(
			groupId, privateLayout, parentLayoutId, version, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_P_Version(
		long groupId, boolean privateLayout, long parentLayoutId, int version,
		int start, int end, OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByG_P_P_Version(
			groupId, privateLayout, parentLayoutId, version, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByG_P_P_Version_First(
			long groupId, boolean privateLayout, long parentLayoutId,
			int version, OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_P_Version_First(
			groupId, privateLayout, parentLayoutId, version, orderByComparator);
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByG_P_P_Version_First(
		long groupId, boolean privateLayout, long parentLayoutId, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByG_P_P_Version_First(
			groupId, privateLayout, parentLayoutId, version, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByG_P_P_Version_Last(
			long groupId, boolean privateLayout, long parentLayoutId,
			int version, OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_P_Version_Last(
			groupId, privateLayout, parentLayoutId, version, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByG_P_P_Version_Last(
		long groupId, boolean privateLayout, long parentLayoutId, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByG_P_P_Version_Last(
			groupId, privateLayout, parentLayoutId, version, orderByComparator);
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion[] findByG_P_P_Version_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			long parentLayoutId, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_P_Version_PrevAndNext(
			layoutVersionId, groupId, privateLayout, parentLayoutId, version,
			orderByComparator);
	}

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param version the version
	 */
	public static void removeByG_P_P_Version(
		long groupId, boolean privateLayout, long parentLayoutId, int version) {

		getPersistence().removeByG_P_P_Version(
			groupId, privateLayout, parentLayoutId, version);
	}

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public static int countByG_P_P_Version(
		long groupId, boolean privateLayout, long parentLayoutId, int version) {

		return getPersistence().countByG_P_P_Version(
			groupId, privateLayout, parentLayoutId, version);
	}

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @return the matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_T(
		long groupId, boolean privateLayout, String type) {

		return getPersistence().findByG_P_T(groupId, privateLayout, type);
	}

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_T(
		long groupId, boolean privateLayout, String type, int start, int end) {

		return getPersistence().findByG_P_T(
			groupId, privateLayout, type, start, end);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_T(
		long groupId, boolean privateLayout, String type, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findByG_P_T(
			groupId, privateLayout, type, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_T(
		long groupId, boolean privateLayout, String type, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByG_P_T(
			groupId, privateLayout, type, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByG_P_T_First(
			long groupId, boolean privateLayout, String type,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_T_First(
			groupId, privateLayout, type, orderByComparator);
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByG_P_T_First(
		long groupId, boolean privateLayout, String type,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByG_P_T_First(
			groupId, privateLayout, type, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByG_P_T_Last(
			long groupId, boolean privateLayout, String type,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_T_Last(
			groupId, privateLayout, type, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByG_P_T_Last(
		long groupId, boolean privateLayout, String type,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByG_P_T_Last(
			groupId, privateLayout, type, orderByComparator);
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion[] findByG_P_T_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			String type, OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_T_PrevAndNext(
			layoutVersionId, groupId, privateLayout, type, orderByComparator);
	}

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 */
	public static void removeByG_P_T(
		long groupId, boolean privateLayout, String type) {

		getPersistence().removeByG_P_T(groupId, privateLayout, type);
	}

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @return the number of matching layout versions
	 */
	public static int countByG_P_T(
		long groupId, boolean privateLayout, String type) {

		return getPersistence().countByG_P_T(groupId, privateLayout, type);
	}

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param version the version
	 * @return the matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_T_Version(
		long groupId, boolean privateLayout, String type, int version) {

		return getPersistence().findByG_P_T_Version(
			groupId, privateLayout, type, version);
	}

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63; and type = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_T_Version(
		long groupId, boolean privateLayout, String type, int version,
		int start, int end) {

		return getPersistence().findByG_P_T_Version(
			groupId, privateLayout, type, version, start, end);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and type = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_T_Version(
		long groupId, boolean privateLayout, String type, int version,
		int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findByG_P_T_Version(
			groupId, privateLayout, type, version, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and type = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_T_Version(
		long groupId, boolean privateLayout, String type, int version,
		int start, int end, OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByG_P_T_Version(
			groupId, privateLayout, type, version, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByG_P_T_Version_First(
			long groupId, boolean privateLayout, String type, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_T_Version_First(
			groupId, privateLayout, type, version, orderByComparator);
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByG_P_T_Version_First(
		long groupId, boolean privateLayout, String type, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByG_P_T_Version_First(
			groupId, privateLayout, type, version, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByG_P_T_Version_Last(
			long groupId, boolean privateLayout, String type, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_T_Version_Last(
			groupId, privateLayout, type, version, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByG_P_T_Version_Last(
		long groupId, boolean privateLayout, String type, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByG_P_T_Version_Last(
			groupId, privateLayout, type, version, orderByComparator);
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion[] findByG_P_T_Version_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			String type, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_T_Version_PrevAndNext(
			layoutVersionId, groupId, privateLayout, type, version,
			orderByComparator);
	}

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; and type = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param version the version
	 */
	public static void removeByG_P_T_Version(
		long groupId, boolean privateLayout, String type, int version) {

		getPersistence().removeByG_P_T_Version(
			groupId, privateLayout, type, version);
	}

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public static int countByG_P_T_Version(
		long groupId, boolean privateLayout, String type, int version) {

		return getPersistence().countByG_P_T_Version(
			groupId, privateLayout, type, version);
	}

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @return the matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_F(
		long groupId, boolean privateLayout, String friendlyURL) {

		return getPersistence().findByG_P_F(
			groupId, privateLayout, friendlyURL);
	}

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_F(
		long groupId, boolean privateLayout, String friendlyURL, int start,
		int end) {

		return getPersistence().findByG_P_F(
			groupId, privateLayout, friendlyURL, start, end);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_F(
		long groupId, boolean privateLayout, String friendlyURL, int start,
		int end, OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findByG_P_F(
			groupId, privateLayout, friendlyURL, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_F(
		long groupId, boolean privateLayout, String friendlyURL, int start,
		int end, OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByG_P_F(
			groupId, privateLayout, friendlyURL, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByG_P_F_First(
			long groupId, boolean privateLayout, String friendlyURL,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_F_First(
			groupId, privateLayout, friendlyURL, orderByComparator);
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByG_P_F_First(
		long groupId, boolean privateLayout, String friendlyURL,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByG_P_F_First(
			groupId, privateLayout, friendlyURL, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByG_P_F_Last(
			long groupId, boolean privateLayout, String friendlyURL,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_F_Last(
			groupId, privateLayout, friendlyURL, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByG_P_F_Last(
		long groupId, boolean privateLayout, String friendlyURL,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByG_P_F_Last(
			groupId, privateLayout, friendlyURL, orderByComparator);
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion[] findByG_P_F_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			String friendlyURL,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_F_PrevAndNext(
			layoutVersionId, groupId, privateLayout, friendlyURL,
			orderByComparator);
	}

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 */
	public static void removeByG_P_F(
		long groupId, boolean privateLayout, String friendlyURL) {

		getPersistence().removeByG_P_F(groupId, privateLayout, friendlyURL);
	}

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @return the number of matching layout versions
	 */
	public static int countByG_P_F(
		long groupId, boolean privateLayout, String friendlyURL) {

		return getPersistence().countByG_P_F(
			groupId, privateLayout, friendlyURL);
	}

	/**
	 * Returns the layout version where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; and version = &#63; or throws a <code>NoSuchLayoutVersionException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @param version the version
	 * @return the matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByG_P_F_Version(
			long groupId, boolean privateLayout, String friendlyURL,
			int version)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_F_Version(
			groupId, privateLayout, friendlyURL, version);
	}

	/**
	 * Returns the layout version where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @param version the version
	 * @return the matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByG_P_F_Version(
		long groupId, boolean privateLayout, String friendlyURL, int version) {

		return getPersistence().fetchByG_P_F_Version(
			groupId, privateLayout, friendlyURL, version);
	}

	/**
	 * Returns the layout version where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @param version the version
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByG_P_F_Version(
		long groupId, boolean privateLayout, String friendlyURL, int version,
		boolean retrieveFromCache) {

		return getPersistence().fetchByG_P_F_Version(
			groupId, privateLayout, friendlyURL, version, retrieveFromCache);
	}

	/**
	 * Removes the layout version where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @param version the version
	 * @return the layout version that was removed
	 */
	public static LayoutVersion removeByG_P_F_Version(
			long groupId, boolean privateLayout, String friendlyURL,
			int version)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().removeByG_P_F_Version(
			groupId, privateLayout, friendlyURL, version);
	}

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public static int countByG_P_F_Version(
		long groupId, boolean privateLayout, String friendlyURL, int version) {

		return getPersistence().countByG_P_F_Version(
			groupId, privateLayout, friendlyURL, version);
	}

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @return the matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_SPLU(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid) {

		return getPersistence().findByG_P_SPLU(
			groupId, privateLayout, sourcePrototypeLayoutUuid);
	}

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_SPLU(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid,
		int start, int end) {

		return getPersistence().findByG_P_SPLU(
			groupId, privateLayout, sourcePrototypeLayoutUuid, start, end);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_SPLU(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid,
		int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findByG_P_SPLU(
			groupId, privateLayout, sourcePrototypeLayoutUuid, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_SPLU(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid,
		int start, int end, OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByG_P_SPLU(
			groupId, privateLayout, sourcePrototypeLayoutUuid, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByG_P_SPLU_First(
			long groupId, boolean privateLayout,
			String sourcePrototypeLayoutUuid,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_SPLU_First(
			groupId, privateLayout, sourcePrototypeLayoutUuid,
			orderByComparator);
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByG_P_SPLU_First(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByG_P_SPLU_First(
			groupId, privateLayout, sourcePrototypeLayoutUuid,
			orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByG_P_SPLU_Last(
			long groupId, boolean privateLayout,
			String sourcePrototypeLayoutUuid,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_SPLU_Last(
			groupId, privateLayout, sourcePrototypeLayoutUuid,
			orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByG_P_SPLU_Last(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByG_P_SPLU_Last(
			groupId, privateLayout, sourcePrototypeLayoutUuid,
			orderByComparator);
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion[] findByG_P_SPLU_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			String sourcePrototypeLayoutUuid,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_SPLU_PrevAndNext(
			layoutVersionId, groupId, privateLayout, sourcePrototypeLayoutUuid,
			orderByComparator);
	}

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 */
	public static void removeByG_P_SPLU(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid) {

		getPersistence().removeByG_P_SPLU(
			groupId, privateLayout, sourcePrototypeLayoutUuid);
	}

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @return the number of matching layout versions
	 */
	public static int countByG_P_SPLU(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid) {

		return getPersistence().countByG_P_SPLU(
			groupId, privateLayout, sourcePrototypeLayoutUuid);
	}

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @return the matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_SPLU_Version(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid,
		int version) {

		return getPersistence().findByG_P_SPLU_Version(
			groupId, privateLayout, sourcePrototypeLayoutUuid, version);
	}

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_SPLU_Version(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid,
		int version, int start, int end) {

		return getPersistence().findByG_P_SPLU_Version(
			groupId, privateLayout, sourcePrototypeLayoutUuid, version, start,
			end);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_SPLU_Version(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid,
		int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findByG_P_SPLU_Version(
			groupId, privateLayout, sourcePrototypeLayoutUuid, version, start,
			end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_SPLU_Version(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid,
		int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByG_P_SPLU_Version(
			groupId, privateLayout, sourcePrototypeLayoutUuid, version, start,
			end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByG_P_SPLU_Version_First(
			long groupId, boolean privateLayout,
			String sourcePrototypeLayoutUuid, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_SPLU_Version_First(
			groupId, privateLayout, sourcePrototypeLayoutUuid, version,
			orderByComparator);
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByG_P_SPLU_Version_First(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid,
		int version, OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByG_P_SPLU_Version_First(
			groupId, privateLayout, sourcePrototypeLayoutUuid, version,
			orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByG_P_SPLU_Version_Last(
			long groupId, boolean privateLayout,
			String sourcePrototypeLayoutUuid, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_SPLU_Version_Last(
			groupId, privateLayout, sourcePrototypeLayoutUuid, version,
			orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByG_P_SPLU_Version_Last(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid,
		int version, OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByG_P_SPLU_Version_Last(
			groupId, privateLayout, sourcePrototypeLayoutUuid, version,
			orderByComparator);
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion[] findByG_P_SPLU_Version_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			String sourcePrototypeLayoutUuid, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_SPLU_Version_PrevAndNext(
			layoutVersionId, groupId, privateLayout, sourcePrototypeLayoutUuid,
			version, orderByComparator);
	}

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 */
	public static void removeByG_P_SPLU_Version(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid,
		int version) {

		getPersistence().removeByG_P_SPLU_Version(
			groupId, privateLayout, sourcePrototypeLayoutUuid, version);
	}

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public static int countByG_P_SPLU_Version(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid,
		int version) {

		return getPersistence().countByG_P_SPLU_Version(
			groupId, privateLayout, sourcePrototypeLayoutUuid, version);
	}

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @return the matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_P_H(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden) {

		return getPersistence().findByG_P_P_H(
			groupId, privateLayout, parentLayoutId, hidden);
	}

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_P_H(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden, int start, int end) {

		return getPersistence().findByG_P_P_H(
			groupId, privateLayout, parentLayoutId, hidden, start, end);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_P_H(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findByG_P_P_H(
			groupId, privateLayout, parentLayoutId, hidden, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_P_H(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByG_P_P_H(
			groupId, privateLayout, parentLayoutId, hidden, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByG_P_P_H_First(
			long groupId, boolean privateLayout, long parentLayoutId,
			boolean hidden, OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_P_H_First(
			groupId, privateLayout, parentLayoutId, hidden, orderByComparator);
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByG_P_P_H_First(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden, OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByG_P_P_H_First(
			groupId, privateLayout, parentLayoutId, hidden, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByG_P_P_H_Last(
			long groupId, boolean privateLayout, long parentLayoutId,
			boolean hidden, OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_P_H_Last(
			groupId, privateLayout, parentLayoutId, hidden, orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByG_P_P_H_Last(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden, OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByG_P_P_H_Last(
			groupId, privateLayout, parentLayoutId, hidden, orderByComparator);
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion[] findByG_P_P_H_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			long parentLayoutId, boolean hidden,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_P_H_PrevAndNext(
			layoutVersionId, groupId, privateLayout, parentLayoutId, hidden,
			orderByComparator);
	}

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 */
	public static void removeByG_P_P_H(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden) {

		getPersistence().removeByG_P_P_H(
			groupId, privateLayout, parentLayoutId, hidden);
	}

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @return the number of matching layout versions
	 */
	public static int countByG_P_P_H(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden) {

		return getPersistence().countByG_P_P_H(
			groupId, privateLayout, parentLayoutId, hidden);
	}

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param version the version
	 * @return the matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_P_H_Version(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden, int version) {

		return getPersistence().findByG_P_P_H_Version(
			groupId, privateLayout, parentLayoutId, hidden, version);
	}

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_P_H_Version(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden, int version, int start, int end) {

		return getPersistence().findByG_P_P_H_Version(
			groupId, privateLayout, parentLayoutId, hidden, version, start,
			end);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_P_H_Version(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findByG_P_P_H_Version(
			groupId, privateLayout, parentLayoutId, hidden, version, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_P_H_Version(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByG_P_P_H_Version(
			groupId, privateLayout, parentLayoutId, hidden, version, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByG_P_P_H_Version_First(
			long groupId, boolean privateLayout, long parentLayoutId,
			boolean hidden, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_P_H_Version_First(
			groupId, privateLayout, parentLayoutId, hidden, version,
			orderByComparator);
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByG_P_P_H_Version_First(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByG_P_P_H_Version_First(
			groupId, privateLayout, parentLayoutId, hidden, version,
			orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByG_P_P_H_Version_Last(
			long groupId, boolean privateLayout, long parentLayoutId,
			boolean hidden, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_P_H_Version_Last(
			groupId, privateLayout, parentLayoutId, hidden, version,
			orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByG_P_P_H_Version_Last(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByG_P_P_H_Version_Last(
			groupId, privateLayout, parentLayoutId, hidden, version,
			orderByComparator);
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion[] findByG_P_P_H_Version_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			long parentLayoutId, boolean hidden, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_P_H_Version_PrevAndNext(
			layoutVersionId, groupId, privateLayout, parentLayoutId, hidden,
			version, orderByComparator);
	}

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param version the version
	 */
	public static void removeByG_P_P_H_Version(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden, int version) {

		getPersistence().removeByG_P_P_H_Version(
			groupId, privateLayout, parentLayoutId, hidden, version);
	}

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public static int countByG_P_P_H_Version(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden, int version) {

		return getPersistence().countByG_P_P_H_Version(
			groupId, privateLayout, parentLayoutId, hidden, version);
	}

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @return the matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_P_LtP(
		long groupId, boolean privateLayout, long parentLayoutId,
		int priority) {

		return getPersistence().findByG_P_P_LtP(
			groupId, privateLayout, parentLayoutId, priority);
	}

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_P_LtP(
		long groupId, boolean privateLayout, long parentLayoutId, int priority,
		int start, int end) {

		return getPersistence().findByG_P_P_LtP(
			groupId, privateLayout, parentLayoutId, priority, start, end);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_P_LtP(
		long groupId, boolean privateLayout, long parentLayoutId, int priority,
		int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findByG_P_P_LtP(
			groupId, privateLayout, parentLayoutId, priority, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_P_LtP(
		long groupId, boolean privateLayout, long parentLayoutId, int priority,
		int start, int end, OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByG_P_P_LtP(
			groupId, privateLayout, parentLayoutId, priority, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByG_P_P_LtP_First(
			long groupId, boolean privateLayout, long parentLayoutId,
			int priority, OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_P_LtP_First(
			groupId, privateLayout, parentLayoutId, priority,
			orderByComparator);
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByG_P_P_LtP_First(
		long groupId, boolean privateLayout, long parentLayoutId, int priority,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByG_P_P_LtP_First(
			groupId, privateLayout, parentLayoutId, priority,
			orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByG_P_P_LtP_Last(
			long groupId, boolean privateLayout, long parentLayoutId,
			int priority, OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_P_LtP_Last(
			groupId, privateLayout, parentLayoutId, priority,
			orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByG_P_P_LtP_Last(
		long groupId, boolean privateLayout, long parentLayoutId, int priority,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByG_P_P_LtP_Last(
			groupId, privateLayout, parentLayoutId, priority,
			orderByComparator);
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion[] findByG_P_P_LtP_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			long parentLayoutId, int priority,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_P_LtP_PrevAndNext(
			layoutVersionId, groupId, privateLayout, parentLayoutId, priority,
			orderByComparator);
	}

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 */
	public static void removeByG_P_P_LtP(
		long groupId, boolean privateLayout, long parentLayoutId,
		int priority) {

		getPersistence().removeByG_P_P_LtP(
			groupId, privateLayout, parentLayoutId, priority);
	}

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @return the number of matching layout versions
	 */
	public static int countByG_P_P_LtP(
		long groupId, boolean privateLayout, long parentLayoutId,
		int priority) {

		return getPersistence().countByG_P_P_LtP(
			groupId, privateLayout, parentLayoutId, priority);
	}

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param version the version
	 * @return the matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_P_LtP_Version(
		long groupId, boolean privateLayout, long parentLayoutId, int priority,
		int version) {

		return getPersistence().findByG_P_P_LtP_Version(
			groupId, privateLayout, parentLayoutId, priority, version);
	}

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_P_LtP_Version(
		long groupId, boolean privateLayout, long parentLayoutId, int priority,
		int version, int start, int end) {

		return getPersistence().findByG_P_P_LtP_Version(
			groupId, privateLayout, parentLayoutId, priority, version, start,
			end);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_P_LtP_Version(
		long groupId, boolean privateLayout, long parentLayoutId, int priority,
		int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findByG_P_P_LtP_Version(
			groupId, privateLayout, parentLayoutId, priority, version, start,
			end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public static List<LayoutVersion> findByG_P_P_LtP_Version(
		long groupId, boolean privateLayout, long parentLayoutId, int priority,
		int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByG_P_P_LtP_Version(
			groupId, privateLayout, parentLayoutId, priority, version, start,
			end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByG_P_P_LtP_Version_First(
			long groupId, boolean privateLayout, long parentLayoutId,
			int priority, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_P_LtP_Version_First(
			groupId, privateLayout, parentLayoutId, priority, version,
			orderByComparator);
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByG_P_P_LtP_Version_First(
		long groupId, boolean privateLayout, long parentLayoutId, int priority,
		int version, OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByG_P_P_LtP_Version_First(
			groupId, privateLayout, parentLayoutId, priority, version,
			orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public static LayoutVersion findByG_P_P_LtP_Version_Last(
			long groupId, boolean privateLayout, long parentLayoutId,
			int priority, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_P_LtP_Version_Last(
			groupId, privateLayout, parentLayoutId, priority, version,
			orderByComparator);
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public static LayoutVersion fetchByG_P_P_LtP_Version_Last(
		long groupId, boolean privateLayout, long parentLayoutId, int priority,
		int version, OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().fetchByG_P_P_LtP_Version_Last(
			groupId, privateLayout, parentLayoutId, priority, version,
			orderByComparator);
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion[] findByG_P_P_LtP_Version_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			long parentLayoutId, int priority, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByG_P_P_LtP_Version_PrevAndNext(
			layoutVersionId, groupId, privateLayout, parentLayoutId, priority,
			version, orderByComparator);
	}

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param version the version
	 */
	public static void removeByG_P_P_LtP_Version(
		long groupId, boolean privateLayout, long parentLayoutId, int priority,
		int version) {

		getPersistence().removeByG_P_P_LtP_Version(
			groupId, privateLayout, parentLayoutId, priority, version);
	}

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public static int countByG_P_P_LtP_Version(
		long groupId, boolean privateLayout, long parentLayoutId, int priority,
		int version) {

		return getPersistence().countByG_P_P_LtP_Version(
			groupId, privateLayout, parentLayoutId, priority, version);
	}

	/**
	 * Caches the layout version in the entity cache if it is enabled.
	 *
	 * @param layoutVersion the layout version
	 */
	public static void cacheResult(LayoutVersion layoutVersion) {
		getPersistence().cacheResult(layoutVersion);
	}

	/**
	 * Caches the layout versions in the entity cache if it is enabled.
	 *
	 * @param layoutVersions the layout versions
	 */
	public static void cacheResult(List<LayoutVersion> layoutVersions) {
		getPersistence().cacheResult(layoutVersions);
	}

	/**
	 * Creates a new layout version with the primary key. Does not add the layout version to the database.
	 *
	 * @param layoutVersionId the primary key for the new layout version
	 * @return the new layout version
	 */
	public static LayoutVersion create(long layoutVersionId) {
		return getPersistence().create(layoutVersionId);
	}

	/**
	 * Removes the layout version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutVersionId the primary key of the layout version
	 * @return the layout version that was removed
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion remove(long layoutVersionId)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().remove(layoutVersionId);
	}

	public static LayoutVersion updateImpl(LayoutVersion layoutVersion) {
		return getPersistence().updateImpl(layoutVersion);
	}

	/**
	 * Returns the layout version with the primary key or throws a <code>NoSuchLayoutVersionException</code> if it could not be found.
	 *
	 * @param layoutVersionId the primary key of the layout version
	 * @return the layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public static LayoutVersion findByPrimaryKey(long layoutVersionId)
		throws com.liferay.portal.kernel.exception.
			NoSuchLayoutVersionException {

		return getPersistence().findByPrimaryKey(layoutVersionId);
	}

	/**
	 * Returns the layout version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutVersionId the primary key of the layout version
	 * @return the layout version, or <code>null</code> if a layout version with the primary key could not be found
	 */
	public static LayoutVersion fetchByPrimaryKey(long layoutVersionId) {
		return getPersistence().fetchByPrimaryKey(layoutVersionId);
	}

	/**
	 * Returns all the layout versions.
	 *
	 * @return the layout versions
	 */
	public static List<LayoutVersion> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the layout versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of layout versions
	 */
	public static List<LayoutVersion> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the layout versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout versions
	 */
	public static List<LayoutVersion> findAll(
		int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of layout versions
	 */
	public static List<LayoutVersion> findAll(
		int start, int end, OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Removes all the layout versions from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of layout versions.
	 *
	 * @return the number of layout versions
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static LayoutVersionPersistence getPersistence() {
		if (_persistence == null) {
			_persistence =
				(LayoutVersionPersistence)PortalBeanLocatorUtil.locate(
					LayoutVersionPersistence.class.getName());
		}

		return _persistence;
	}

	private static LayoutVersionPersistence _persistence;

}
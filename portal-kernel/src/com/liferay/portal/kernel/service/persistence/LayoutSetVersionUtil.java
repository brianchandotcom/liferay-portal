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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.model.LayoutSetVersion;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the layout set version service. This utility wraps <code>com.liferay.portal.service.persistence.impl.LayoutSetVersionPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetVersionPersistence
 * @generated
 */
@ProviderType
public class LayoutSetVersionUtil {
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
	public static void clearCache(LayoutSetVersion layoutSetVersion) {
		getPersistence().clearCache(layoutSetVersion);
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
	public static Map<Serializable, LayoutSetVersion> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<LayoutSetVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LayoutSetVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LayoutSetVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static LayoutSetVersion update(LayoutSetVersion layoutSetVersion) {
		return getPersistence().update(layoutSetVersion);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static LayoutSetVersion update(LayoutSetVersion layoutSetVersion,
		ServiceContext serviceContext) {
		return getPersistence().update(layoutSetVersion, serviceContext);
	}

	/**
	* Returns all the layout set versions where layoutSetId = &#63;.
	*
	* @param layoutSetId the layout set ID
	* @return the matching layout set versions
	*/
	public static List<LayoutSetVersion> findByLayoutSetId(long layoutSetId) {
		return getPersistence().findByLayoutSetId(layoutSetId);
	}

	/**
	* Returns a range of all the layout set versions where layoutSetId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetId the layout set ID
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @return the range of matching layout set versions
	*/
	public static List<LayoutSetVersion> findByLayoutSetId(long layoutSetId,
		int start, int end) {
		return getPersistence().findByLayoutSetId(layoutSetId, start, end);
	}

	/**
	* Returns an ordered range of all the layout set versions where layoutSetId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetId the layout set ID
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout set versions
	*/
	public static List<LayoutSetVersion> findByLayoutSetId(long layoutSetId,
		int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		return getPersistence()
				   .findByLayoutSetId(layoutSetId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout set versions where layoutSetId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetId the layout set ID
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout set versions
	*/
	public static List<LayoutSetVersion> findByLayoutSetId(long layoutSetId,
		int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByLayoutSetId(layoutSetId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first layout set version in the ordered set where layoutSetId = &#63;.
	*
	* @param layoutSetId the layout set ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public static LayoutSetVersion findByLayoutSetId_First(long layoutSetId,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException {
		return getPersistence()
				   .findByLayoutSetId_First(layoutSetId, orderByComparator);
	}

	/**
	* Returns the first layout set version in the ordered set where layoutSetId = &#63;.
	*
	* @param layoutSetId the layout set ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public static LayoutSetVersion fetchByLayoutSetId_First(long layoutSetId,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		return getPersistence()
				   .fetchByLayoutSetId_First(layoutSetId, orderByComparator);
	}

	/**
	* Returns the last layout set version in the ordered set where layoutSetId = &#63;.
	*
	* @param layoutSetId the layout set ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public static LayoutSetVersion findByLayoutSetId_Last(long layoutSetId,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException {
		return getPersistence()
				   .findByLayoutSetId_Last(layoutSetId, orderByComparator);
	}

	/**
	* Returns the last layout set version in the ordered set where layoutSetId = &#63;.
	*
	* @param layoutSetId the layout set ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public static LayoutSetVersion fetchByLayoutSetId_Last(long layoutSetId,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		return getPersistence()
				   .fetchByLayoutSetId_Last(layoutSetId, orderByComparator);
	}

	/**
	* Returns the layout set versions before and after the current layout set version in the ordered set where layoutSetId = &#63;.
	*
	* @param layoutSetVersionId the primary key of the current layout set version
	* @param layoutSetId the layout set ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout set version
	* @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	*/
	public static LayoutSetVersion[] findByLayoutSetId_PrevAndNext(
		long layoutSetVersionId, long layoutSetId,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException {
		return getPersistence()
				   .findByLayoutSetId_PrevAndNext(layoutSetVersionId,
			layoutSetId, orderByComparator);
	}

	/**
	* Removes all the layout set versions where layoutSetId = &#63; from the database.
	*
	* @param layoutSetId the layout set ID
	*/
	public static void removeByLayoutSetId(long layoutSetId) {
		getPersistence().removeByLayoutSetId(layoutSetId);
	}

	/**
	* Returns the number of layout set versions where layoutSetId = &#63;.
	*
	* @param layoutSetId the layout set ID
	* @return the number of matching layout set versions
	*/
	public static int countByLayoutSetId(long layoutSetId) {
		return getPersistence().countByLayoutSetId(layoutSetId);
	}

	/**
	* Returns the layout set version where layoutSetId = &#63; and version = &#63; or throws a <code>NoSuchLayoutSetVersionException</code> if it could not be found.
	*
	* @param layoutSetId the layout set ID
	* @param version the version
	* @return the matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public static LayoutSetVersion findByLayoutSetId_Version(long layoutSetId,
		int version)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException {
		return getPersistence().findByLayoutSetId_Version(layoutSetId, version);
	}

	/**
	* Returns the layout set version where layoutSetId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param layoutSetId the layout set ID
	* @param version the version
	* @return the matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public static LayoutSetVersion fetchByLayoutSetId_Version(
		long layoutSetId, int version) {
		return getPersistence().fetchByLayoutSetId_Version(layoutSetId, version);
	}

	/**
	* Returns the layout set version where layoutSetId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param layoutSetId the layout set ID
	* @param version the version
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public static LayoutSetVersion fetchByLayoutSetId_Version(
		long layoutSetId, int version, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByLayoutSetId_Version(layoutSetId, version,
			retrieveFromCache);
	}

	/**
	* Removes the layout set version where layoutSetId = &#63; and version = &#63; from the database.
	*
	* @param layoutSetId the layout set ID
	* @param version the version
	* @return the layout set version that was removed
	*/
	public static LayoutSetVersion removeByLayoutSetId_Version(
		long layoutSetId, int version)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException {
		return getPersistence().removeByLayoutSetId_Version(layoutSetId, version);
	}

	/**
	* Returns the number of layout set versions where layoutSetId = &#63; and version = &#63;.
	*
	* @param layoutSetId the layout set ID
	* @param version the version
	* @return the number of matching layout set versions
	*/
	public static int countByLayoutSetId_Version(long layoutSetId, int version) {
		return getPersistence().countByLayoutSetId_Version(layoutSetId, version);
	}

	/**
	* Returns all the layout set versions where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching layout set versions
	*/
	public static List<LayoutSetVersion> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the layout set versions where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @return the range of matching layout set versions
	*/
	public static List<LayoutSetVersion> findByGroupId(long groupId, int start,
		int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the layout set versions where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout set versions
	*/
	public static List<LayoutSetVersion> findByGroupId(long groupId, int start,
		int end, OrderByComparator<LayoutSetVersion> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout set versions where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout set versions
	*/
	public static List<LayoutSetVersion> findByGroupId(long groupId, int start,
		int end, OrderByComparator<LayoutSetVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first layout set version in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public static LayoutSetVersion findByGroupId_First(long groupId,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first layout set version in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public static LayoutSetVersion fetchByGroupId_First(long groupId,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last layout set version in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public static LayoutSetVersion findByGroupId_Last(long groupId,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last layout set version in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public static LayoutSetVersion fetchByGroupId_Last(long groupId,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the layout set versions before and after the current layout set version in the ordered set where groupId = &#63;.
	*
	* @param layoutSetVersionId the primary key of the current layout set version
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout set version
	* @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	*/
	public static LayoutSetVersion[] findByGroupId_PrevAndNext(
		long layoutSetVersionId, long groupId,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(layoutSetVersionId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the layout set versions where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of layout set versions where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching layout set versions
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the layout set versions where groupId = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param version the version
	* @return the matching layout set versions
	*/
	public static List<LayoutSetVersion> findByGroupId_Version(long groupId,
		int version) {
		return getPersistence().findByGroupId_Version(groupId, version);
	}

	/**
	* Returns a range of all the layout set versions where groupId = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param version the version
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @return the range of matching layout set versions
	*/
	public static List<LayoutSetVersion> findByGroupId_Version(long groupId,
		int version, int start, int end) {
		return getPersistence()
				   .findByGroupId_Version(groupId, version, start, end);
	}

	/**
	* Returns an ordered range of all the layout set versions where groupId = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param version the version
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout set versions
	*/
	public static List<LayoutSetVersion> findByGroupId_Version(long groupId,
		int version, int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		return getPersistence()
				   .findByGroupId_Version(groupId, version, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout set versions where groupId = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param version the version
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout set versions
	*/
	public static List<LayoutSetVersion> findByGroupId_Version(long groupId,
		int version, int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId_Version(groupId, version, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first layout set version in the ordered set where groupId = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public static LayoutSetVersion findByGroupId_Version_First(long groupId,
		int version, OrderByComparator<LayoutSetVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException {
		return getPersistence()
				   .findByGroupId_Version_First(groupId, version,
			orderByComparator);
	}

	/**
	* Returns the first layout set version in the ordered set where groupId = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public static LayoutSetVersion fetchByGroupId_Version_First(long groupId,
		int version, OrderByComparator<LayoutSetVersion> orderByComparator) {
		return getPersistence()
				   .fetchByGroupId_Version_First(groupId, version,
			orderByComparator);
	}

	/**
	* Returns the last layout set version in the ordered set where groupId = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public static LayoutSetVersion findByGroupId_Version_Last(long groupId,
		int version, OrderByComparator<LayoutSetVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException {
		return getPersistence()
				   .findByGroupId_Version_Last(groupId, version,
			orderByComparator);
	}

	/**
	* Returns the last layout set version in the ordered set where groupId = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public static LayoutSetVersion fetchByGroupId_Version_Last(long groupId,
		int version, OrderByComparator<LayoutSetVersion> orderByComparator) {
		return getPersistence()
				   .fetchByGroupId_Version_Last(groupId, version,
			orderByComparator);
	}

	/**
	* Returns the layout set versions before and after the current layout set version in the ordered set where groupId = &#63; and version = &#63;.
	*
	* @param layoutSetVersionId the primary key of the current layout set version
	* @param groupId the group ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout set version
	* @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	*/
	public static LayoutSetVersion[] findByGroupId_Version_PrevAndNext(
		long layoutSetVersionId, long groupId, int version,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException {
		return getPersistence()
				   .findByGroupId_Version_PrevAndNext(layoutSetVersionId,
			groupId, version, orderByComparator);
	}

	/**
	* Removes all the layout set versions where groupId = &#63; and version = &#63; from the database.
	*
	* @param groupId the group ID
	* @param version the version
	*/
	public static void removeByGroupId_Version(long groupId, int version) {
		getPersistence().removeByGroupId_Version(groupId, version);
	}

	/**
	* Returns the number of layout set versions where groupId = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param version the version
	* @return the number of matching layout set versions
	*/
	public static int countByGroupId_Version(long groupId, int version) {
		return getPersistence().countByGroupId_Version(groupId, version);
	}

	/**
	* Returns all the layout set versions where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @return the matching layout set versions
	*/
	public static List<LayoutSetVersion> findByLayoutSetPrototypeUuid(
		String layoutSetPrototypeUuid) {
		return getPersistence()
				   .findByLayoutSetPrototypeUuid(layoutSetPrototypeUuid);
	}

	/**
	* Returns a range of all the layout set versions where layoutSetPrototypeUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @return the range of matching layout set versions
	*/
	public static List<LayoutSetVersion> findByLayoutSetPrototypeUuid(
		String layoutSetPrototypeUuid, int start, int end) {
		return getPersistence()
				   .findByLayoutSetPrototypeUuid(layoutSetPrototypeUuid, start,
			end);
	}

	/**
	* Returns an ordered range of all the layout set versions where layoutSetPrototypeUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout set versions
	*/
	public static List<LayoutSetVersion> findByLayoutSetPrototypeUuid(
		String layoutSetPrototypeUuid, int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		return getPersistence()
				   .findByLayoutSetPrototypeUuid(layoutSetPrototypeUuid, start,
			end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout set versions where layoutSetPrototypeUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout set versions
	*/
	public static List<LayoutSetVersion> findByLayoutSetPrototypeUuid(
		String layoutSetPrototypeUuid, int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByLayoutSetPrototypeUuid(layoutSetPrototypeUuid, start,
			end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first layout set version in the ordered set where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public static LayoutSetVersion findByLayoutSetPrototypeUuid_First(
		String layoutSetPrototypeUuid,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException {
		return getPersistence()
				   .findByLayoutSetPrototypeUuid_First(layoutSetPrototypeUuid,
			orderByComparator);
	}

	/**
	* Returns the first layout set version in the ordered set where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public static LayoutSetVersion fetchByLayoutSetPrototypeUuid_First(
		String layoutSetPrototypeUuid,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		return getPersistence()
				   .fetchByLayoutSetPrototypeUuid_First(layoutSetPrototypeUuid,
			orderByComparator);
	}

	/**
	* Returns the last layout set version in the ordered set where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public static LayoutSetVersion findByLayoutSetPrototypeUuid_Last(
		String layoutSetPrototypeUuid,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException {
		return getPersistence()
				   .findByLayoutSetPrototypeUuid_Last(layoutSetPrototypeUuid,
			orderByComparator);
	}

	/**
	* Returns the last layout set version in the ordered set where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public static LayoutSetVersion fetchByLayoutSetPrototypeUuid_Last(
		String layoutSetPrototypeUuid,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		return getPersistence()
				   .fetchByLayoutSetPrototypeUuid_Last(layoutSetPrototypeUuid,
			orderByComparator);
	}

	/**
	* Returns the layout set versions before and after the current layout set version in the ordered set where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetVersionId the primary key of the current layout set version
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout set version
	* @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	*/
	public static LayoutSetVersion[] findByLayoutSetPrototypeUuid_PrevAndNext(
		long layoutSetVersionId, String layoutSetPrototypeUuid,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException {
		return getPersistence()
				   .findByLayoutSetPrototypeUuid_PrevAndNext(layoutSetVersionId,
			layoutSetPrototypeUuid, orderByComparator);
	}

	/**
	* Removes all the layout set versions where layoutSetPrototypeUuid = &#63; from the database.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	*/
	public static void removeByLayoutSetPrototypeUuid(
		String layoutSetPrototypeUuid) {
		getPersistence().removeByLayoutSetPrototypeUuid(layoutSetPrototypeUuid);
	}

	/**
	* Returns the number of layout set versions where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @return the number of matching layout set versions
	*/
	public static int countByLayoutSetPrototypeUuid(
		String layoutSetPrototypeUuid) {
		return getPersistence()
				   .countByLayoutSetPrototypeUuid(layoutSetPrototypeUuid);
	}

	/**
	* Returns all the layout set versions where layoutSetPrototypeUuid = &#63; and version = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param version the version
	* @return the matching layout set versions
	*/
	public static List<LayoutSetVersion> findByLayoutSetPrototypeUuid_Version(
		String layoutSetPrototypeUuid, int version) {
		return getPersistence()
				   .findByLayoutSetPrototypeUuid_Version(layoutSetPrototypeUuid,
			version);
	}

	/**
	* Returns a range of all the layout set versions where layoutSetPrototypeUuid = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param version the version
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @return the range of matching layout set versions
	*/
	public static List<LayoutSetVersion> findByLayoutSetPrototypeUuid_Version(
		String layoutSetPrototypeUuid, int version, int start, int end) {
		return getPersistence()
				   .findByLayoutSetPrototypeUuid_Version(layoutSetPrototypeUuid,
			version, start, end);
	}

	/**
	* Returns an ordered range of all the layout set versions where layoutSetPrototypeUuid = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param version the version
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout set versions
	*/
	public static List<LayoutSetVersion> findByLayoutSetPrototypeUuid_Version(
		String layoutSetPrototypeUuid, int version, int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		return getPersistence()
				   .findByLayoutSetPrototypeUuid_Version(layoutSetPrototypeUuid,
			version, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout set versions where layoutSetPrototypeUuid = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param version the version
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout set versions
	*/
	public static List<LayoutSetVersion> findByLayoutSetPrototypeUuid_Version(
		String layoutSetPrototypeUuid, int version, int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByLayoutSetPrototypeUuid_Version(layoutSetPrototypeUuid,
			version, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first layout set version in the ordered set where layoutSetPrototypeUuid = &#63; and version = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public static LayoutSetVersion findByLayoutSetPrototypeUuid_Version_First(
		String layoutSetPrototypeUuid, int version,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException {
		return getPersistence()
				   .findByLayoutSetPrototypeUuid_Version_First(layoutSetPrototypeUuid,
			version, orderByComparator);
	}

	/**
	* Returns the first layout set version in the ordered set where layoutSetPrototypeUuid = &#63; and version = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public static LayoutSetVersion fetchByLayoutSetPrototypeUuid_Version_First(
		String layoutSetPrototypeUuid, int version,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		return getPersistence()
				   .fetchByLayoutSetPrototypeUuid_Version_First(layoutSetPrototypeUuid,
			version, orderByComparator);
	}

	/**
	* Returns the last layout set version in the ordered set where layoutSetPrototypeUuid = &#63; and version = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public static LayoutSetVersion findByLayoutSetPrototypeUuid_Version_Last(
		String layoutSetPrototypeUuid, int version,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException {
		return getPersistence()
				   .findByLayoutSetPrototypeUuid_Version_Last(layoutSetPrototypeUuid,
			version, orderByComparator);
	}

	/**
	* Returns the last layout set version in the ordered set where layoutSetPrototypeUuid = &#63; and version = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public static LayoutSetVersion fetchByLayoutSetPrototypeUuid_Version_Last(
		String layoutSetPrototypeUuid, int version,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		return getPersistence()
				   .fetchByLayoutSetPrototypeUuid_Version_Last(layoutSetPrototypeUuid,
			version, orderByComparator);
	}

	/**
	* Returns the layout set versions before and after the current layout set version in the ordered set where layoutSetPrototypeUuid = &#63; and version = &#63;.
	*
	* @param layoutSetVersionId the primary key of the current layout set version
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout set version
	* @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	*/
	public static LayoutSetVersion[] findByLayoutSetPrototypeUuid_Version_PrevAndNext(
		long layoutSetVersionId, String layoutSetPrototypeUuid, int version,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException {
		return getPersistence()
				   .findByLayoutSetPrototypeUuid_Version_PrevAndNext(layoutSetVersionId,
			layoutSetPrototypeUuid, version, orderByComparator);
	}

	/**
	* Removes all the layout set versions where layoutSetPrototypeUuid = &#63; and version = &#63; from the database.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param version the version
	*/
	public static void removeByLayoutSetPrototypeUuid_Version(
		String layoutSetPrototypeUuid, int version) {
		getPersistence()
			.removeByLayoutSetPrototypeUuid_Version(layoutSetPrototypeUuid,
			version);
	}

	/**
	* Returns the number of layout set versions where layoutSetPrototypeUuid = &#63; and version = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param version the version
	* @return the number of matching layout set versions
	*/
	public static int countByLayoutSetPrototypeUuid_Version(
		String layoutSetPrototypeUuid, int version) {
		return getPersistence()
				   .countByLayoutSetPrototypeUuid_Version(layoutSetPrototypeUuid,
			version);
	}

	/**
	* Returns all the layout set versions where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @return the matching layout set versions
	*/
	public static List<LayoutSetVersion> findByG_P(long groupId,
		boolean privateLayout) {
		return getPersistence().findByG_P(groupId, privateLayout);
	}

	/**
	* Returns a range of all the layout set versions where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @return the range of matching layout set versions
	*/
	public static List<LayoutSetVersion> findByG_P(long groupId,
		boolean privateLayout, int start, int end) {
		return getPersistence().findByG_P(groupId, privateLayout, start, end);
	}

	/**
	* Returns an ordered range of all the layout set versions where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout set versions
	*/
	public static List<LayoutSetVersion> findByG_P(long groupId,
		boolean privateLayout, int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		return getPersistence()
				   .findByG_P(groupId, privateLayout, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout set versions where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout set versions
	*/
	public static List<LayoutSetVersion> findByG_P(long groupId,
		boolean privateLayout, int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_P(groupId, privateLayout, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first layout set version in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public static LayoutSetVersion findByG_P_First(long groupId,
		boolean privateLayout,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException {
		return getPersistence()
				   .findByG_P_First(groupId, privateLayout, orderByComparator);
	}

	/**
	* Returns the first layout set version in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public static LayoutSetVersion fetchByG_P_First(long groupId,
		boolean privateLayout,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		return getPersistence()
				   .fetchByG_P_First(groupId, privateLayout, orderByComparator);
	}

	/**
	* Returns the last layout set version in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public static LayoutSetVersion findByG_P_Last(long groupId,
		boolean privateLayout,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException {
		return getPersistence()
				   .findByG_P_Last(groupId, privateLayout, orderByComparator);
	}

	/**
	* Returns the last layout set version in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public static LayoutSetVersion fetchByG_P_Last(long groupId,
		boolean privateLayout,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		return getPersistence()
				   .fetchByG_P_Last(groupId, privateLayout, orderByComparator);
	}

	/**
	* Returns the layout set versions before and after the current layout set version in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* @param layoutSetVersionId the primary key of the current layout set version
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout set version
	* @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	*/
	public static LayoutSetVersion[] findByG_P_PrevAndNext(
		long layoutSetVersionId, long groupId, boolean privateLayout,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException {
		return getPersistence()
				   .findByG_P_PrevAndNext(layoutSetVersionId, groupId,
			privateLayout, orderByComparator);
	}

	/**
	* Removes all the layout set versions where groupId = &#63; and privateLayout = &#63; from the database.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	*/
	public static void removeByG_P(long groupId, boolean privateLayout) {
		getPersistence().removeByG_P(groupId, privateLayout);
	}

	/**
	* Returns the number of layout set versions where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @return the number of matching layout set versions
	*/
	public static int countByG_P(long groupId, boolean privateLayout) {
		return getPersistence().countByG_P(groupId, privateLayout);
	}

	/**
	* Returns the layout set version where groupId = &#63; and privateLayout = &#63; and version = &#63; or throws a <code>NoSuchLayoutSetVersionException</code> if it could not be found.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param version the version
	* @return the matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public static LayoutSetVersion findByG_P_Version(long groupId,
		boolean privateLayout, int version)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException {
		return getPersistence()
				   .findByG_P_Version(groupId, privateLayout, version);
	}

	/**
	* Returns the layout set version where groupId = &#63; and privateLayout = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param version the version
	* @return the matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public static LayoutSetVersion fetchByG_P_Version(long groupId,
		boolean privateLayout, int version) {
		return getPersistence()
				   .fetchByG_P_Version(groupId, privateLayout, version);
	}

	/**
	* Returns the layout set version where groupId = &#63; and privateLayout = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param version the version
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public static LayoutSetVersion fetchByG_P_Version(long groupId,
		boolean privateLayout, int version, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_P_Version(groupId, privateLayout, version,
			retrieveFromCache);
	}

	/**
	* Removes the layout set version where groupId = &#63; and privateLayout = &#63; and version = &#63; from the database.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param version the version
	* @return the layout set version that was removed
	*/
	public static LayoutSetVersion removeByG_P_Version(long groupId,
		boolean privateLayout, int version)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException {
		return getPersistence()
				   .removeByG_P_Version(groupId, privateLayout, version);
	}

	/**
	* Returns the number of layout set versions where groupId = &#63; and privateLayout = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param version the version
	* @return the number of matching layout set versions
	*/
	public static int countByG_P_Version(long groupId, boolean privateLayout,
		int version) {
		return getPersistence()
				   .countByG_P_Version(groupId, privateLayout, version);
	}

	/**
	* Returns all the layout set versions where privateLayout = &#63; and logoId = &#63;.
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @return the matching layout set versions
	*/
	public static List<LayoutSetVersion> findByP_L(boolean privateLayout,
		long logoId) {
		return getPersistence().findByP_L(privateLayout, logoId);
	}

	/**
	* Returns a range of all the layout set versions where privateLayout = &#63; and logoId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @return the range of matching layout set versions
	*/
	public static List<LayoutSetVersion> findByP_L(boolean privateLayout,
		long logoId, int start, int end) {
		return getPersistence().findByP_L(privateLayout, logoId, start, end);
	}

	/**
	* Returns an ordered range of all the layout set versions where privateLayout = &#63; and logoId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout set versions
	*/
	public static List<LayoutSetVersion> findByP_L(boolean privateLayout,
		long logoId, int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		return getPersistence()
				   .findByP_L(privateLayout, logoId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout set versions where privateLayout = &#63; and logoId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout set versions
	*/
	public static List<LayoutSetVersion> findByP_L(boolean privateLayout,
		long logoId, int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByP_L(privateLayout, logoId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first layout set version in the ordered set where privateLayout = &#63; and logoId = &#63;.
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public static LayoutSetVersion findByP_L_First(boolean privateLayout,
		long logoId, OrderByComparator<LayoutSetVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException {
		return getPersistence()
				   .findByP_L_First(privateLayout, logoId, orderByComparator);
	}

	/**
	* Returns the first layout set version in the ordered set where privateLayout = &#63; and logoId = &#63;.
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public static LayoutSetVersion fetchByP_L_First(boolean privateLayout,
		long logoId, OrderByComparator<LayoutSetVersion> orderByComparator) {
		return getPersistence()
				   .fetchByP_L_First(privateLayout, logoId, orderByComparator);
	}

	/**
	* Returns the last layout set version in the ordered set where privateLayout = &#63; and logoId = &#63;.
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public static LayoutSetVersion findByP_L_Last(boolean privateLayout,
		long logoId, OrderByComparator<LayoutSetVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException {
		return getPersistence()
				   .findByP_L_Last(privateLayout, logoId, orderByComparator);
	}

	/**
	* Returns the last layout set version in the ordered set where privateLayout = &#63; and logoId = &#63;.
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public static LayoutSetVersion fetchByP_L_Last(boolean privateLayout,
		long logoId, OrderByComparator<LayoutSetVersion> orderByComparator) {
		return getPersistence()
				   .fetchByP_L_Last(privateLayout, logoId, orderByComparator);
	}

	/**
	* Returns the layout set versions before and after the current layout set version in the ordered set where privateLayout = &#63; and logoId = &#63;.
	*
	* @param layoutSetVersionId the primary key of the current layout set version
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout set version
	* @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	*/
	public static LayoutSetVersion[] findByP_L_PrevAndNext(
		long layoutSetVersionId, boolean privateLayout, long logoId,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException {
		return getPersistence()
				   .findByP_L_PrevAndNext(layoutSetVersionId, privateLayout,
			logoId, orderByComparator);
	}

	/**
	* Removes all the layout set versions where privateLayout = &#63; and logoId = &#63; from the database.
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	*/
	public static void removeByP_L(boolean privateLayout, long logoId) {
		getPersistence().removeByP_L(privateLayout, logoId);
	}

	/**
	* Returns the number of layout set versions where privateLayout = &#63; and logoId = &#63;.
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @return the number of matching layout set versions
	*/
	public static int countByP_L(boolean privateLayout, long logoId) {
		return getPersistence().countByP_L(privateLayout, logoId);
	}

	/**
	* Returns all the layout set versions where privateLayout = &#63; and logoId = &#63; and version = &#63;.
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @param version the version
	* @return the matching layout set versions
	*/
	public static List<LayoutSetVersion> findByP_L_Version(
		boolean privateLayout, long logoId, int version) {
		return getPersistence().findByP_L_Version(privateLayout, logoId, version);
	}

	/**
	* Returns a range of all the layout set versions where privateLayout = &#63; and logoId = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @param version the version
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @return the range of matching layout set versions
	*/
	public static List<LayoutSetVersion> findByP_L_Version(
		boolean privateLayout, long logoId, int version, int start, int end) {
		return getPersistence()
				   .findByP_L_Version(privateLayout, logoId, version, start, end);
	}

	/**
	* Returns an ordered range of all the layout set versions where privateLayout = &#63; and logoId = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @param version the version
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout set versions
	*/
	public static List<LayoutSetVersion> findByP_L_Version(
		boolean privateLayout, long logoId, int version, int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		return getPersistence()
				   .findByP_L_Version(privateLayout, logoId, version, start,
			end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout set versions where privateLayout = &#63; and logoId = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @param version the version
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout set versions
	*/
	public static List<LayoutSetVersion> findByP_L_Version(
		boolean privateLayout, long logoId, int version, int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByP_L_Version(privateLayout, logoId, version, start,
			end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first layout set version in the ordered set where privateLayout = &#63; and logoId = &#63; and version = &#63;.
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public static LayoutSetVersion findByP_L_Version_First(
		boolean privateLayout, long logoId, int version,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException {
		return getPersistence()
				   .findByP_L_Version_First(privateLayout, logoId, version,
			orderByComparator);
	}

	/**
	* Returns the first layout set version in the ordered set where privateLayout = &#63; and logoId = &#63; and version = &#63;.
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public static LayoutSetVersion fetchByP_L_Version_First(
		boolean privateLayout, long logoId, int version,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		return getPersistence()
				   .fetchByP_L_Version_First(privateLayout, logoId, version,
			orderByComparator);
	}

	/**
	* Returns the last layout set version in the ordered set where privateLayout = &#63; and logoId = &#63; and version = &#63;.
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public static LayoutSetVersion findByP_L_Version_Last(
		boolean privateLayout, long logoId, int version,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException {
		return getPersistence()
				   .findByP_L_Version_Last(privateLayout, logoId, version,
			orderByComparator);
	}

	/**
	* Returns the last layout set version in the ordered set where privateLayout = &#63; and logoId = &#63; and version = &#63;.
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public static LayoutSetVersion fetchByP_L_Version_Last(
		boolean privateLayout, long logoId, int version,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		return getPersistence()
				   .fetchByP_L_Version_Last(privateLayout, logoId, version,
			orderByComparator);
	}

	/**
	* Returns the layout set versions before and after the current layout set version in the ordered set where privateLayout = &#63; and logoId = &#63; and version = &#63;.
	*
	* @param layoutSetVersionId the primary key of the current layout set version
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout set version
	* @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	*/
	public static LayoutSetVersion[] findByP_L_Version_PrevAndNext(
		long layoutSetVersionId, boolean privateLayout, long logoId,
		int version, OrderByComparator<LayoutSetVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException {
		return getPersistence()
				   .findByP_L_Version_PrevAndNext(layoutSetVersionId,
			privateLayout, logoId, version, orderByComparator);
	}

	/**
	* Removes all the layout set versions where privateLayout = &#63; and logoId = &#63; and version = &#63; from the database.
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @param version the version
	*/
	public static void removeByP_L_Version(boolean privateLayout, long logoId,
		int version) {
		getPersistence().removeByP_L_Version(privateLayout, logoId, version);
	}

	/**
	* Returns the number of layout set versions where privateLayout = &#63; and logoId = &#63; and version = &#63;.
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @param version the version
	* @return the number of matching layout set versions
	*/
	public static int countByP_L_Version(boolean privateLayout, long logoId,
		int version) {
		return getPersistence()
				   .countByP_L_Version(privateLayout, logoId, version);
	}

	/**
	* Caches the layout set version in the entity cache if it is enabled.
	*
	* @param layoutSetVersion the layout set version
	*/
	public static void cacheResult(LayoutSetVersion layoutSetVersion) {
		getPersistence().cacheResult(layoutSetVersion);
	}

	/**
	* Caches the layout set versions in the entity cache if it is enabled.
	*
	* @param layoutSetVersions the layout set versions
	*/
	public static void cacheResult(List<LayoutSetVersion> layoutSetVersions) {
		getPersistence().cacheResult(layoutSetVersions);
	}

	/**
	* Creates a new layout set version with the primary key. Does not add the layout set version to the database.
	*
	* @param layoutSetVersionId the primary key for the new layout set version
	* @return the new layout set version
	*/
	public static LayoutSetVersion create(long layoutSetVersionId) {
		return getPersistence().create(layoutSetVersionId);
	}

	/**
	* Removes the layout set version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutSetVersionId the primary key of the layout set version
	* @return the layout set version that was removed
	* @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	*/
	public static LayoutSetVersion remove(long layoutSetVersionId)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException {
		return getPersistence().remove(layoutSetVersionId);
	}

	public static LayoutSetVersion updateImpl(LayoutSetVersion layoutSetVersion) {
		return getPersistence().updateImpl(layoutSetVersion);
	}

	/**
	* Returns the layout set version with the primary key or throws a <code>NoSuchLayoutSetVersionException</code> if it could not be found.
	*
	* @param layoutSetVersionId the primary key of the layout set version
	* @return the layout set version
	* @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	*/
	public static LayoutSetVersion findByPrimaryKey(long layoutSetVersionId)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException {
		return getPersistence().findByPrimaryKey(layoutSetVersionId);
	}

	/**
	* Returns the layout set version with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param layoutSetVersionId the primary key of the layout set version
	* @return the layout set version, or <code>null</code> if a layout set version with the primary key could not be found
	*/
	public static LayoutSetVersion fetchByPrimaryKey(long layoutSetVersionId) {
		return getPersistence().fetchByPrimaryKey(layoutSetVersionId);
	}

	/**
	* Returns all the layout set versions.
	*
	* @return the layout set versions
	*/
	public static List<LayoutSetVersion> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the layout set versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @return the range of layout set versions
	*/
	public static List<LayoutSetVersion> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the layout set versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of layout set versions
	*/
	public static List<LayoutSetVersion> findAll(int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout set versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of layout set versions
	*/
	public static List<LayoutSetVersion> findAll(int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the layout set versions from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of layout set versions.
	*
	* @return the number of layout set versions
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static LayoutSetVersionPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (LayoutSetVersionPersistence)PortalBeanLocatorUtil.locate(LayoutSetVersionPersistence.class.getName());

			ReferenceRegistry.registerReference(LayoutSetVersionUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static LayoutSetVersionPersistence _persistence;
}
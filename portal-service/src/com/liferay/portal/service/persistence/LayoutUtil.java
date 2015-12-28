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

package com.liferay.portal.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the layout service. This utility wraps {@link com.liferay.portal.service.persistence.impl.LayoutPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPersistence
 * @see com.liferay.portal.service.persistence.impl.LayoutPersistenceImpl
 * @generated
 */
@ProviderType
public class LayoutUtil {
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
	public static void clearCache(Layout layout) {
		getPersistence().clearCache(layout);
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
	public static List<Layout> findWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Layout> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Layout> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end, OrderByComparator<Layout> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static Layout update(Layout layout) {
		return getPersistence().update(layout);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static Layout update(Layout layout, ServiceContext serviceContext) {
		return getPersistence().update(layout, serviceContext);
	}

	/**
	* Returns all the layouts where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching layouts
	*/
	public static List<Layout> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the layouts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @return the range of matching layouts
	*/
	public static List<Layout> findByUuid(java.lang.String uuid, int start,
		int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the layouts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layouts
	*/
	public static List<Layout> findByUuid(java.lang.String uuid, int start,
		int end, OrderByComparator<Layout> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the layouts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layouts
	*/
	public static List<Layout> findByUuid(java.lang.String uuid, int start,
		int end, OrderByComparator<Layout> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first layout in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout
	* @throws NoSuchLayoutException if a matching layout could not be found
	*/
	public static Layout findByUuid_First(java.lang.String uuid,
		OrderByComparator<Layout> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first layout in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout, or <code>null</code> if a matching layout could not be found
	*/
	public static Layout fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<Layout> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last layout in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout
	* @throws NoSuchLayoutException if a matching layout could not be found
	*/
	public static Layout findByUuid_Last(java.lang.String uuid,
		OrderByComparator<Layout> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last layout in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout, or <code>null</code> if a matching layout could not be found
	*/
	public static Layout fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<Layout> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the layouts before and after the current layout in the ordered set where uuid = &#63;.
	*
	* @param plid the primary key of the current layout
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout
	* @throws NoSuchLayoutException if a layout with the primary key could not be found
	*/
	public static Layout[] findByUuid_PrevAndNext(long plid,
		java.lang.String uuid, OrderByComparator<Layout> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence()
				   .findByUuid_PrevAndNext(plid, uuid, orderByComparator);
	}

	/**
	* Removes all the layouts where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of layouts where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching layouts
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the layout where uuid = &#63; and groupId = &#63; and privateLayout = &#63; or throws a {@link NoSuchLayoutException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @return the matching layout
	* @throws NoSuchLayoutException if a matching layout could not be found
	*/
	public static Layout findByUUID_G_P(java.lang.String uuid, long groupId,
		boolean privateLayout) throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence().findByUUID_G_P(uuid, groupId, privateLayout);
	}

	/**
	* Returns the layout where uuid = &#63; and groupId = &#63; and privateLayout = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @return the matching layout, or <code>null</code> if a matching layout could not be found
	*/
	public static Layout fetchByUUID_G_P(java.lang.String uuid, long groupId,
		boolean privateLayout) {
		return getPersistence().fetchByUUID_G_P(uuid, groupId, privateLayout);
	}

	/**
	* Returns the layout where uuid = &#63; and groupId = &#63; and privateLayout = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching layout, or <code>null</code> if a matching layout could not be found
	*/
	public static Layout fetchByUUID_G_P(java.lang.String uuid, long groupId,
		boolean privateLayout, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByUUID_G_P(uuid, groupId, privateLayout,
			retrieveFromCache);
	}

	/**
	* Removes the layout where uuid = &#63; and groupId = &#63; and privateLayout = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @return the layout that was removed
	*/
	public static Layout removeByUUID_G_P(java.lang.String uuid, long groupId,
		boolean privateLayout) throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence().removeByUUID_G_P(uuid, groupId, privateLayout);
	}

	/**
	* Returns the number of layouts where uuid = &#63; and groupId = &#63; and privateLayout = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @return the number of matching layouts
	*/
	public static int countByUUID_G_P(java.lang.String uuid, long groupId,
		boolean privateLayout) {
		return getPersistence().countByUUID_G_P(uuid, groupId, privateLayout);
	}

	/**
	* Returns all the layouts where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching layouts
	*/
	public static List<Layout> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the layouts where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @return the range of matching layouts
	*/
	public static List<Layout> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the layouts where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layouts
	*/
	public static List<Layout> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<Layout> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the layouts where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layouts
	*/
	public static List<Layout> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<Layout> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first layout in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout
	* @throws NoSuchLayoutException if a matching layout could not be found
	*/
	public static Layout findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<Layout> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first layout in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout, or <code>null</code> if a matching layout could not be found
	*/
	public static Layout fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<Layout> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last layout in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout
	* @throws NoSuchLayoutException if a matching layout could not be found
	*/
	public static Layout findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<Layout> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last layout in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout, or <code>null</code> if a matching layout could not be found
	*/
	public static Layout fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<Layout> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the layouts before and after the current layout in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param plid the primary key of the current layout
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout
	* @throws NoSuchLayoutException if a layout with the primary key could not be found
	*/
	public static Layout[] findByUuid_C_PrevAndNext(long plid,
		java.lang.String uuid, long companyId,
		OrderByComparator<Layout> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(plid, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the layouts where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of layouts where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching layouts
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the layouts where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching layouts
	*/
	public static List<Layout> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the layouts where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @return the range of matching layouts
	*/
	public static List<Layout> findByGroupId(long groupId, int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the layouts where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layouts
	*/
	public static List<Layout> findByGroupId(long groupId, int start, int end,
		OrderByComparator<Layout> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the layouts where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layouts
	*/
	public static List<Layout> findByGroupId(long groupId, int start, int end,
		OrderByComparator<Layout> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first layout in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout
	* @throws NoSuchLayoutException if a matching layout could not be found
	*/
	public static Layout findByGroupId_First(long groupId,
		OrderByComparator<Layout> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first layout in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout, or <code>null</code> if a matching layout could not be found
	*/
	public static Layout fetchByGroupId_First(long groupId,
		OrderByComparator<Layout> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last layout in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout
	* @throws NoSuchLayoutException if a matching layout could not be found
	*/
	public static Layout findByGroupId_Last(long groupId,
		OrderByComparator<Layout> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last layout in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout, or <code>null</code> if a matching layout could not be found
	*/
	public static Layout fetchByGroupId_Last(long groupId,
		OrderByComparator<Layout> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the layouts before and after the current layout in the ordered set where groupId = &#63;.
	*
	* @param plid the primary key of the current layout
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout
	* @throws NoSuchLayoutException if a layout with the primary key could not be found
	*/
	public static Layout[] findByGroupId_PrevAndNext(long plid, long groupId,
		OrderByComparator<Layout> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(plid, groupId, orderByComparator);
	}

	/**
	* Returns all the layouts that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching layouts that the user has permission to view
	*/
	public static List<Layout> filterFindByGroupId(long groupId) {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the layouts that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @return the range of matching layouts that the user has permission to view
	*/
	public static List<Layout> filterFindByGroupId(long groupId, int start,
		int end) {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the layouts that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layouts that the user has permission to view
	*/
	public static List<Layout> filterFindByGroupId(long groupId, int start,
		int end, OrderByComparator<Layout> orderByComparator) {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the layouts before and after the current layout in the ordered set of layouts that the user has permission to view where groupId = &#63;.
	*
	* @param plid the primary key of the current layout
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout
	* @throws NoSuchLayoutException if a layout with the primary key could not be found
	*/
	public static Layout[] filterFindByGroupId_PrevAndNext(long plid,
		long groupId, OrderByComparator<Layout> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(plid, groupId,
			orderByComparator);
	}

	/**
	* Removes all the layouts where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of layouts where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching layouts
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of layouts that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching layouts that the user has permission to view
	*/
	public static int filterCountByGroupId(long groupId) {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns all the layouts where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching layouts
	*/
	public static List<Layout> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the layouts where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @return the range of matching layouts
	*/
	public static List<Layout> findByCompanyId(long companyId, int start,
		int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the layouts where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layouts
	*/
	public static List<Layout> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<Layout> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the layouts where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layouts
	*/
	public static List<Layout> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<Layout> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first layout in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout
	* @throws NoSuchLayoutException if a matching layout could not be found
	*/
	public static Layout findByCompanyId_First(long companyId,
		OrderByComparator<Layout> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first layout in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout, or <code>null</code> if a matching layout could not be found
	*/
	public static Layout fetchByCompanyId_First(long companyId,
		OrderByComparator<Layout> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last layout in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout
	* @throws NoSuchLayoutException if a matching layout could not be found
	*/
	public static Layout findByCompanyId_Last(long companyId,
		OrderByComparator<Layout> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last layout in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout, or <code>null</code> if a matching layout could not be found
	*/
	public static Layout fetchByCompanyId_Last(long companyId,
		OrderByComparator<Layout> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the layouts before and after the current layout in the ordered set where companyId = &#63;.
	*
	* @param plid the primary key of the current layout
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout
	* @throws NoSuchLayoutException if a layout with the primary key could not be found
	*/
	public static Layout[] findByCompanyId_PrevAndNext(long plid,
		long companyId, OrderByComparator<Layout> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(plid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the layouts where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of layouts where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching layouts
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns the layout where iconImageId = &#63; or throws a {@link NoSuchLayoutException} if it could not be found.
	*
	* @param iconImageId the icon image ID
	* @return the matching layout
	* @throws NoSuchLayoutException if a matching layout could not be found
	*/
	public static Layout findByIconImageId(long iconImageId)
		throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence().findByIconImageId(iconImageId);
	}

	/**
	* Returns the layout where iconImageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param iconImageId the icon image ID
	* @return the matching layout, or <code>null</code> if a matching layout could not be found
	*/
	public static Layout fetchByIconImageId(long iconImageId) {
		return getPersistence().fetchByIconImageId(iconImageId);
	}

	/**
	* Returns the layout where iconImageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param iconImageId the icon image ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching layout, or <code>null</code> if a matching layout could not be found
	*/
	public static Layout fetchByIconImageId(long iconImageId,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByIconImageId(iconImageId, retrieveFromCache);
	}

	/**
	* Removes the layout where iconImageId = &#63; from the database.
	*
	* @param iconImageId the icon image ID
	* @return the layout that was removed
	*/
	public static Layout removeByIconImageId(long iconImageId)
		throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence().removeByIconImageId(iconImageId);
	}

	/**
	* Returns the number of layouts where iconImageId = &#63;.
	*
	* @param iconImageId the icon image ID
	* @return the number of matching layouts
	*/
	public static int countByIconImageId(long iconImageId) {
		return getPersistence().countByIconImageId(iconImageId);
	}

	/**
	* Returns all the layouts where layoutPrototypeUuid = &#63;.
	*
	* @param layoutPrototypeUuid the layout prototype uuid
	* @return the matching layouts
	*/
	public static List<Layout> findByLayoutPrototypeUuid(
		java.lang.String layoutPrototypeUuid) {
		return getPersistence().findByLayoutPrototypeUuid(layoutPrototypeUuid);
	}

	/**
	* Returns a range of all the layouts where layoutPrototypeUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutPrototypeUuid the layout prototype uuid
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @return the range of matching layouts
	*/
	public static List<Layout> findByLayoutPrototypeUuid(
		java.lang.String layoutPrototypeUuid, int start, int end) {
		return getPersistence()
				   .findByLayoutPrototypeUuid(layoutPrototypeUuid, start, end);
	}

	/**
	* Returns an ordered range of all the layouts where layoutPrototypeUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutPrototypeUuid the layout prototype uuid
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layouts
	*/
	public static List<Layout> findByLayoutPrototypeUuid(
		java.lang.String layoutPrototypeUuid, int start, int end,
		OrderByComparator<Layout> orderByComparator) {
		return getPersistence()
				   .findByLayoutPrototypeUuid(layoutPrototypeUuid, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the layouts where layoutPrototypeUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutPrototypeUuid the layout prototype uuid
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layouts
	*/
	public static List<Layout> findByLayoutPrototypeUuid(
		java.lang.String layoutPrototypeUuid, int start, int end,
		OrderByComparator<Layout> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByLayoutPrototypeUuid(layoutPrototypeUuid, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first layout in the ordered set where layoutPrototypeUuid = &#63;.
	*
	* @param layoutPrototypeUuid the layout prototype uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout
	* @throws NoSuchLayoutException if a matching layout could not be found
	*/
	public static Layout findByLayoutPrototypeUuid_First(
		java.lang.String layoutPrototypeUuid,
		OrderByComparator<Layout> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence()
				   .findByLayoutPrototypeUuid_First(layoutPrototypeUuid,
			orderByComparator);
	}

	/**
	* Returns the first layout in the ordered set where layoutPrototypeUuid = &#63;.
	*
	* @param layoutPrototypeUuid the layout prototype uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout, or <code>null</code> if a matching layout could not be found
	*/
	public static Layout fetchByLayoutPrototypeUuid_First(
		java.lang.String layoutPrototypeUuid,
		OrderByComparator<Layout> orderByComparator) {
		return getPersistence()
				   .fetchByLayoutPrototypeUuid_First(layoutPrototypeUuid,
			orderByComparator);
	}

	/**
	* Returns the last layout in the ordered set where layoutPrototypeUuid = &#63;.
	*
	* @param layoutPrototypeUuid the layout prototype uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout
	* @throws NoSuchLayoutException if a matching layout could not be found
	*/
	public static Layout findByLayoutPrototypeUuid_Last(
		java.lang.String layoutPrototypeUuid,
		OrderByComparator<Layout> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence()
				   .findByLayoutPrototypeUuid_Last(layoutPrototypeUuid,
			orderByComparator);
	}

	/**
	* Returns the last layout in the ordered set where layoutPrototypeUuid = &#63;.
	*
	* @param layoutPrototypeUuid the layout prototype uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout, or <code>null</code> if a matching layout could not be found
	*/
	public static Layout fetchByLayoutPrototypeUuid_Last(
		java.lang.String layoutPrototypeUuid,
		OrderByComparator<Layout> orderByComparator) {
		return getPersistence()
				   .fetchByLayoutPrototypeUuid_Last(layoutPrototypeUuid,
			orderByComparator);
	}

	/**
	* Returns the layouts before and after the current layout in the ordered set where layoutPrototypeUuid = &#63;.
	*
	* @param plid the primary key of the current layout
	* @param layoutPrototypeUuid the layout prototype uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout
	* @throws NoSuchLayoutException if a layout with the primary key could not be found
	*/
	public static Layout[] findByLayoutPrototypeUuid_PrevAndNext(long plid,
		java.lang.String layoutPrototypeUuid,
		OrderByComparator<Layout> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence()
				   .findByLayoutPrototypeUuid_PrevAndNext(plid,
			layoutPrototypeUuid, orderByComparator);
	}

	/**
	* Removes all the layouts where layoutPrototypeUuid = &#63; from the database.
	*
	* @param layoutPrototypeUuid the layout prototype uuid
	*/
	public static void removeByLayoutPrototypeUuid(
		java.lang.String layoutPrototypeUuid) {
		getPersistence().removeByLayoutPrototypeUuid(layoutPrototypeUuid);
	}

	/**
	* Returns the number of layouts where layoutPrototypeUuid = &#63;.
	*
	* @param layoutPrototypeUuid the layout prototype uuid
	* @return the number of matching layouts
	*/
	public static int countByLayoutPrototypeUuid(
		java.lang.String layoutPrototypeUuid) {
		return getPersistence().countByLayoutPrototypeUuid(layoutPrototypeUuid);
	}

	/**
	* Returns all the layouts where sourcePrototypeLayoutUuid = &#63;.
	*
	* @param sourcePrototypeLayoutUuid the source prototype layout uuid
	* @return the matching layouts
	*/
	public static List<Layout> findBySourcePrototypeLayoutUuid(
		java.lang.String sourcePrototypeLayoutUuid) {
		return getPersistence()
				   .findBySourcePrototypeLayoutUuid(sourcePrototypeLayoutUuid);
	}

	/**
	* Returns a range of all the layouts where sourcePrototypeLayoutUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param sourcePrototypeLayoutUuid the source prototype layout uuid
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @return the range of matching layouts
	*/
	public static List<Layout> findBySourcePrototypeLayoutUuid(
		java.lang.String sourcePrototypeLayoutUuid, int start, int end) {
		return getPersistence()
				   .findBySourcePrototypeLayoutUuid(sourcePrototypeLayoutUuid,
			start, end);
	}

	/**
	* Returns an ordered range of all the layouts where sourcePrototypeLayoutUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param sourcePrototypeLayoutUuid the source prototype layout uuid
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layouts
	*/
	public static List<Layout> findBySourcePrototypeLayoutUuid(
		java.lang.String sourcePrototypeLayoutUuid, int start, int end,
		OrderByComparator<Layout> orderByComparator) {
		return getPersistence()
				   .findBySourcePrototypeLayoutUuid(sourcePrototypeLayoutUuid,
			start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the layouts where sourcePrototypeLayoutUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param sourcePrototypeLayoutUuid the source prototype layout uuid
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layouts
	*/
	public static List<Layout> findBySourcePrototypeLayoutUuid(
		java.lang.String sourcePrototypeLayoutUuid, int start, int end,
		OrderByComparator<Layout> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findBySourcePrototypeLayoutUuid(sourcePrototypeLayoutUuid,
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first layout in the ordered set where sourcePrototypeLayoutUuid = &#63;.
	*
	* @param sourcePrototypeLayoutUuid the source prototype layout uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout
	* @throws NoSuchLayoutException if a matching layout could not be found
	*/
	public static Layout findBySourcePrototypeLayoutUuid_First(
		java.lang.String sourcePrototypeLayoutUuid,
		OrderByComparator<Layout> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence()
				   .findBySourcePrototypeLayoutUuid_First(sourcePrototypeLayoutUuid,
			orderByComparator);
	}

	/**
	* Returns the first layout in the ordered set where sourcePrototypeLayoutUuid = &#63;.
	*
	* @param sourcePrototypeLayoutUuid the source prototype layout uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout, or <code>null</code> if a matching layout could not be found
	*/
	public static Layout fetchBySourcePrototypeLayoutUuid_First(
		java.lang.String sourcePrototypeLayoutUuid,
		OrderByComparator<Layout> orderByComparator) {
		return getPersistence()
				   .fetchBySourcePrototypeLayoutUuid_First(sourcePrototypeLayoutUuid,
			orderByComparator);
	}

	/**
	* Returns the last layout in the ordered set where sourcePrototypeLayoutUuid = &#63;.
	*
	* @param sourcePrototypeLayoutUuid the source prototype layout uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout
	* @throws NoSuchLayoutException if a matching layout could not be found
	*/
	public static Layout findBySourcePrototypeLayoutUuid_Last(
		java.lang.String sourcePrototypeLayoutUuid,
		OrderByComparator<Layout> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence()
				   .findBySourcePrototypeLayoutUuid_Last(sourcePrototypeLayoutUuid,
			orderByComparator);
	}

	/**
	* Returns the last layout in the ordered set where sourcePrototypeLayoutUuid = &#63;.
	*
	* @param sourcePrototypeLayoutUuid the source prototype layout uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout, or <code>null</code> if a matching layout could not be found
	*/
	public static Layout fetchBySourcePrototypeLayoutUuid_Last(
		java.lang.String sourcePrototypeLayoutUuid,
		OrderByComparator<Layout> orderByComparator) {
		return getPersistence()
				   .fetchBySourcePrototypeLayoutUuid_Last(sourcePrototypeLayoutUuid,
			orderByComparator);
	}

	/**
	* Returns the layouts before and after the current layout in the ordered set where sourcePrototypeLayoutUuid = &#63;.
	*
	* @param plid the primary key of the current layout
	* @param sourcePrototypeLayoutUuid the source prototype layout uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout
	* @throws NoSuchLayoutException if a layout with the primary key could not be found
	*/
	public static Layout[] findBySourcePrototypeLayoutUuid_PrevAndNext(
		long plid, java.lang.String sourcePrototypeLayoutUuid,
		OrderByComparator<Layout> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence()
				   .findBySourcePrototypeLayoutUuid_PrevAndNext(plid,
			sourcePrototypeLayoutUuid, orderByComparator);
	}

	/**
	* Removes all the layouts where sourcePrototypeLayoutUuid = &#63; from the database.
	*
	* @param sourcePrototypeLayoutUuid the source prototype layout uuid
	*/
	public static void removeBySourcePrototypeLayoutUuid(
		java.lang.String sourcePrototypeLayoutUuid) {
		getPersistence()
			.removeBySourcePrototypeLayoutUuid(sourcePrototypeLayoutUuid);
	}

	/**
	* Returns the number of layouts where sourcePrototypeLayoutUuid = &#63;.
	*
	* @param sourcePrototypeLayoutUuid the source prototype layout uuid
	* @return the number of matching layouts
	*/
	public static int countBySourcePrototypeLayoutUuid(
		java.lang.String sourcePrototypeLayoutUuid) {
		return getPersistence()
				   .countBySourcePrototypeLayoutUuid(sourcePrototypeLayoutUuid);
	}

	/**
	* Returns all the layouts where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @return the matching layouts
	*/
	public static List<Layout> findByG_P(long groupId, boolean privateLayout) {
		return getPersistence().findByG_P(groupId, privateLayout);
	}

	/**
	* Returns a range of all the layouts where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @return the range of matching layouts
	*/
	public static List<Layout> findByG_P(long groupId, boolean privateLayout,
		int start, int end) {
		return getPersistence().findByG_P(groupId, privateLayout, start, end);
	}

	/**
	* Returns an ordered range of all the layouts where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layouts
	*/
	public static List<Layout> findByG_P(long groupId, boolean privateLayout,
		int start, int end, OrderByComparator<Layout> orderByComparator) {
		return getPersistence()
				   .findByG_P(groupId, privateLayout, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the layouts where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layouts
	*/
	public static List<Layout> findByG_P(long groupId, boolean privateLayout,
		int start, int end, OrderByComparator<Layout> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_P(groupId, privateLayout, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first layout in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout
	* @throws NoSuchLayoutException if a matching layout could not be found
	*/
	public static Layout findByG_P_First(long groupId, boolean privateLayout,
		OrderByComparator<Layout> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence()
				   .findByG_P_First(groupId, privateLayout, orderByComparator);
	}

	/**
	* Returns the first layout in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout, or <code>null</code> if a matching layout could not be found
	*/
	public static Layout fetchByG_P_First(long groupId, boolean privateLayout,
		OrderByComparator<Layout> orderByComparator) {
		return getPersistence()
				   .fetchByG_P_First(groupId, privateLayout, orderByComparator);
	}

	/**
	* Returns the last layout in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout
	* @throws NoSuchLayoutException if a matching layout could not be found
	*/
	public static Layout findByG_P_Last(long groupId, boolean privateLayout,
		OrderByComparator<Layout> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence()
				   .findByG_P_Last(groupId, privateLayout, orderByComparator);
	}

	/**
	* Returns the last layout in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout, or <code>null</code> if a matching layout could not be found
	*/
	public static Layout fetchByG_P_Last(long groupId, boolean privateLayout,
		OrderByComparator<Layout> orderByComparator) {
		return getPersistence()
				   .fetchByG_P_Last(groupId, privateLayout, orderByComparator);
	}

	/**
	* Returns the layouts before and after the current layout in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* @param plid the primary key of the current layout
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout
	* @throws NoSuchLayoutException if a layout with the primary key could not be found
	*/
	public static Layout[] findByG_P_PrevAndNext(long plid, long groupId,
		boolean privateLayout, OrderByComparator<Layout> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence()
				   .findByG_P_PrevAndNext(plid, groupId, privateLayout,
			orderByComparator);
	}

	/**
	* Returns all the layouts that the user has permission to view where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @return the matching layouts that the user has permission to view
	*/
	public static List<Layout> filterFindByG_P(long groupId,
		boolean privateLayout) {
		return getPersistence().filterFindByG_P(groupId, privateLayout);
	}

	/**
	* Returns a range of all the layouts that the user has permission to view where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @return the range of matching layouts that the user has permission to view
	*/
	public static List<Layout> filterFindByG_P(long groupId,
		boolean privateLayout, int start, int end) {
		return getPersistence()
				   .filterFindByG_P(groupId, privateLayout, start, end);
	}

	/**
	* Returns an ordered range of all the layouts that the user has permissions to view where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layouts that the user has permission to view
	*/
	public static List<Layout> filterFindByG_P(long groupId,
		boolean privateLayout, int start, int end,
		OrderByComparator<Layout> orderByComparator) {
		return getPersistence()
				   .filterFindByG_P(groupId, privateLayout, start, end,
			orderByComparator);
	}

	/**
	* Returns the layouts before and after the current layout in the ordered set of layouts that the user has permission to view where groupId = &#63; and privateLayout = &#63;.
	*
	* @param plid the primary key of the current layout
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout
	* @throws NoSuchLayoutException if a layout with the primary key could not be found
	*/
	public static Layout[] filterFindByG_P_PrevAndNext(long plid, long groupId,
		boolean privateLayout, OrderByComparator<Layout> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence()
				   .filterFindByG_P_PrevAndNext(plid, groupId, privateLayout,
			orderByComparator);
	}

	/**
	* Removes all the layouts where groupId = &#63; and privateLayout = &#63; from the database.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	*/
	public static void removeByG_P(long groupId, boolean privateLayout) {
		getPersistence().removeByG_P(groupId, privateLayout);
	}

	/**
	* Returns the number of layouts where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @return the number of matching layouts
	*/
	public static int countByG_P(long groupId, boolean privateLayout) {
		return getPersistence().countByG_P(groupId, privateLayout);
	}

	/**
	* Returns the number of layouts that the user has permission to view where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @return the number of matching layouts that the user has permission to view
	*/
	public static int filterCountByG_P(long groupId, boolean privateLayout) {
		return getPersistence().filterCountByG_P(groupId, privateLayout);
	}

	/**
	* Returns the layout where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; or throws a {@link NoSuchLayoutException} if it could not be found.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @return the matching layout
	* @throws NoSuchLayoutException if a matching layout could not be found
	*/
	public static Layout findByG_P_L(long groupId, boolean privateLayout,
		long layoutId) throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence().findByG_P_L(groupId, privateLayout, layoutId);
	}

	/**
	* Returns the layout where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @return the matching layout, or <code>null</code> if a matching layout could not be found
	*/
	public static Layout fetchByG_P_L(long groupId, boolean privateLayout,
		long layoutId) {
		return getPersistence().fetchByG_P_L(groupId, privateLayout, layoutId);
	}

	/**
	* Returns the layout where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching layout, or <code>null</code> if a matching layout could not be found
	*/
	public static Layout fetchByG_P_L(long groupId, boolean privateLayout,
		long layoutId, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_P_L(groupId, privateLayout, layoutId,
			retrieveFromCache);
	}

	/**
	* Removes the layout where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @return the layout that was removed
	*/
	public static Layout removeByG_P_L(long groupId, boolean privateLayout,
		long layoutId) throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence().removeByG_P_L(groupId, privateLayout, layoutId);
	}

	/**
	* Returns the number of layouts where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @return the number of matching layouts
	*/
	public static int countByG_P_L(long groupId, boolean privateLayout,
		long layoutId) {
		return getPersistence().countByG_P_L(groupId, privateLayout, layoutId);
	}

	/**
	* Returns all the layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param parentLayoutId the parent layout ID
	* @return the matching layouts
	*/
	public static List<Layout> findByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId) {
		return getPersistence()
				   .findByG_P_P(groupId, privateLayout, parentLayoutId);
	}

	/**
	* Returns a range of all the layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param parentLayoutId the parent layout ID
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @return the range of matching layouts
	*/
	public static List<Layout> findByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId, int start, int end) {
		return getPersistence()
				   .findByG_P_P(groupId, privateLayout, parentLayoutId, start,
			end);
	}

	/**
	* Returns an ordered range of all the layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param parentLayoutId the parent layout ID
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layouts
	*/
	public static List<Layout> findByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId, int start, int end,
		OrderByComparator<Layout> orderByComparator) {
		return getPersistence()
				   .findByG_P_P(groupId, privateLayout, parentLayoutId, start,
			end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param parentLayoutId the parent layout ID
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layouts
	*/
	public static List<Layout> findByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId, int start, int end,
		OrderByComparator<Layout> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_P_P(groupId, privateLayout, parentLayoutId, start,
			end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first layout in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param parentLayoutId the parent layout ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout
	* @throws NoSuchLayoutException if a matching layout could not be found
	*/
	public static Layout findByG_P_P_First(long groupId, boolean privateLayout,
		long parentLayoutId, OrderByComparator<Layout> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence()
				   .findByG_P_P_First(groupId, privateLayout, parentLayoutId,
			orderByComparator);
	}

	/**
	* Returns the first layout in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param parentLayoutId the parent layout ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout, or <code>null</code> if a matching layout could not be found
	*/
	public static Layout fetchByG_P_P_First(long groupId,
		boolean privateLayout, long parentLayoutId,
		OrderByComparator<Layout> orderByComparator) {
		return getPersistence()
				   .fetchByG_P_P_First(groupId, privateLayout, parentLayoutId,
			orderByComparator);
	}

	/**
	* Returns the last layout in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param parentLayoutId the parent layout ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout
	* @throws NoSuchLayoutException if a matching layout could not be found
	*/
	public static Layout findByG_P_P_Last(long groupId, boolean privateLayout,
		long parentLayoutId, OrderByComparator<Layout> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence()
				   .findByG_P_P_Last(groupId, privateLayout, parentLayoutId,
			orderByComparator);
	}

	/**
	* Returns the last layout in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param parentLayoutId the parent layout ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout, or <code>null</code> if a matching layout could not be found
	*/
	public static Layout fetchByG_P_P_Last(long groupId, boolean privateLayout,
		long parentLayoutId, OrderByComparator<Layout> orderByComparator) {
		return getPersistence()
				   .fetchByG_P_P_Last(groupId, privateLayout, parentLayoutId,
			orderByComparator);
	}

	/**
	* Returns the layouts before and after the current layout in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	*
	* @param plid the primary key of the current layout
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param parentLayoutId the parent layout ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout
	* @throws NoSuchLayoutException if a layout with the primary key could not be found
	*/
	public static Layout[] findByG_P_P_PrevAndNext(long plid, long groupId,
		boolean privateLayout, long parentLayoutId,
		OrderByComparator<Layout> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence()
				   .findByG_P_P_PrevAndNext(plid, groupId, privateLayout,
			parentLayoutId, orderByComparator);
	}

	/**
	* Returns all the layouts that the user has permission to view where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param parentLayoutId the parent layout ID
	* @return the matching layouts that the user has permission to view
	*/
	public static List<Layout> filterFindByG_P_P(long groupId,
		boolean privateLayout, long parentLayoutId) {
		return getPersistence()
				   .filterFindByG_P_P(groupId, privateLayout, parentLayoutId);
	}

	/**
	* Returns a range of all the layouts that the user has permission to view where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param parentLayoutId the parent layout ID
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @return the range of matching layouts that the user has permission to view
	*/
	public static List<Layout> filterFindByG_P_P(long groupId,
		boolean privateLayout, long parentLayoutId, int start, int end) {
		return getPersistence()
				   .filterFindByG_P_P(groupId, privateLayout, parentLayoutId,
			start, end);
	}

	/**
	* Returns an ordered range of all the layouts that the user has permissions to view where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param parentLayoutId the parent layout ID
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layouts that the user has permission to view
	*/
	public static List<Layout> filterFindByG_P_P(long groupId,
		boolean privateLayout, long parentLayoutId, int start, int end,
		OrderByComparator<Layout> orderByComparator) {
		return getPersistence()
				   .filterFindByG_P_P(groupId, privateLayout, parentLayoutId,
			start, end, orderByComparator);
	}

	/**
	* Returns the layouts before and after the current layout in the ordered set of layouts that the user has permission to view where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	*
	* @param plid the primary key of the current layout
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param parentLayoutId the parent layout ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout
	* @throws NoSuchLayoutException if a layout with the primary key could not be found
	*/
	public static Layout[] filterFindByG_P_P_PrevAndNext(long plid,
		long groupId, boolean privateLayout, long parentLayoutId,
		OrderByComparator<Layout> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence()
				   .filterFindByG_P_P_PrevAndNext(plid, groupId, privateLayout,
			parentLayoutId, orderByComparator);
	}

	/**
	* Removes all the layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param parentLayoutId the parent layout ID
	*/
	public static void removeByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId) {
		getPersistence().removeByG_P_P(groupId, privateLayout, parentLayoutId);
	}

	/**
	* Returns the number of layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param parentLayoutId the parent layout ID
	* @return the number of matching layouts
	*/
	public static int countByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId) {
		return getPersistence()
				   .countByG_P_P(groupId, privateLayout, parentLayoutId);
	}

	/**
	* Returns the number of layouts that the user has permission to view where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param parentLayoutId the parent layout ID
	* @return the number of matching layouts that the user has permission to view
	*/
	public static int filterCountByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId) {
		return getPersistence()
				   .filterCountByG_P_P(groupId, privateLayout, parentLayoutId);
	}

	/**
	* Returns all the layouts where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param type the type
	* @return the matching layouts
	*/
	public static List<Layout> findByG_P_T(long groupId, boolean privateLayout,
		java.lang.String type) {
		return getPersistence().findByG_P_T(groupId, privateLayout, type);
	}

	/**
	* Returns a range of all the layouts where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param type the type
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @return the range of matching layouts
	*/
	public static List<Layout> findByG_P_T(long groupId, boolean privateLayout,
		java.lang.String type, int start, int end) {
		return getPersistence()
				   .findByG_P_T(groupId, privateLayout, type, start, end);
	}

	/**
	* Returns an ordered range of all the layouts where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param type the type
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layouts
	*/
	public static List<Layout> findByG_P_T(long groupId, boolean privateLayout,
		java.lang.String type, int start, int end,
		OrderByComparator<Layout> orderByComparator) {
		return getPersistence()
				   .findByG_P_T(groupId, privateLayout, type, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the layouts where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param type the type
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layouts
	*/
	public static List<Layout> findByG_P_T(long groupId, boolean privateLayout,
		java.lang.String type, int start, int end,
		OrderByComparator<Layout> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_P_T(groupId, privateLayout, type, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first layout in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout
	* @throws NoSuchLayoutException if a matching layout could not be found
	*/
	public static Layout findByG_P_T_First(long groupId, boolean privateLayout,
		java.lang.String type, OrderByComparator<Layout> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence()
				   .findByG_P_T_First(groupId, privateLayout, type,
			orderByComparator);
	}

	/**
	* Returns the first layout in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout, or <code>null</code> if a matching layout could not be found
	*/
	public static Layout fetchByG_P_T_First(long groupId,
		boolean privateLayout, java.lang.String type,
		OrderByComparator<Layout> orderByComparator) {
		return getPersistence()
				   .fetchByG_P_T_First(groupId, privateLayout, type,
			orderByComparator);
	}

	/**
	* Returns the last layout in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout
	* @throws NoSuchLayoutException if a matching layout could not be found
	*/
	public static Layout findByG_P_T_Last(long groupId, boolean privateLayout,
		java.lang.String type, OrderByComparator<Layout> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence()
				   .findByG_P_T_Last(groupId, privateLayout, type,
			orderByComparator);
	}

	/**
	* Returns the last layout in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout, or <code>null</code> if a matching layout could not be found
	*/
	public static Layout fetchByG_P_T_Last(long groupId, boolean privateLayout,
		java.lang.String type, OrderByComparator<Layout> orderByComparator) {
		return getPersistence()
				   .fetchByG_P_T_Last(groupId, privateLayout, type,
			orderByComparator);
	}

	/**
	* Returns the layouts before and after the current layout in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	*
	* @param plid the primary key of the current layout
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout
	* @throws NoSuchLayoutException if a layout with the primary key could not be found
	*/
	public static Layout[] findByG_P_T_PrevAndNext(long plid, long groupId,
		boolean privateLayout, java.lang.String type,
		OrderByComparator<Layout> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence()
				   .findByG_P_T_PrevAndNext(plid, groupId, privateLayout, type,
			orderByComparator);
	}

	/**
	* Returns all the layouts that the user has permission to view where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param type the type
	* @return the matching layouts that the user has permission to view
	*/
	public static List<Layout> filterFindByG_P_T(long groupId,
		boolean privateLayout, java.lang.String type) {
		return getPersistence().filterFindByG_P_T(groupId, privateLayout, type);
	}

	/**
	* Returns a range of all the layouts that the user has permission to view where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param type the type
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @return the range of matching layouts that the user has permission to view
	*/
	public static List<Layout> filterFindByG_P_T(long groupId,
		boolean privateLayout, java.lang.String type, int start, int end) {
		return getPersistence()
				   .filterFindByG_P_T(groupId, privateLayout, type, start, end);
	}

	/**
	* Returns an ordered range of all the layouts that the user has permissions to view where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param type the type
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layouts that the user has permission to view
	*/
	public static List<Layout> filterFindByG_P_T(long groupId,
		boolean privateLayout, java.lang.String type, int start, int end,
		OrderByComparator<Layout> orderByComparator) {
		return getPersistence()
				   .filterFindByG_P_T(groupId, privateLayout, type, start, end,
			orderByComparator);
	}

	/**
	* Returns the layouts before and after the current layout in the ordered set of layouts that the user has permission to view where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	*
	* @param plid the primary key of the current layout
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout
	* @throws NoSuchLayoutException if a layout with the primary key could not be found
	*/
	public static Layout[] filterFindByG_P_T_PrevAndNext(long plid,
		long groupId, boolean privateLayout, java.lang.String type,
		OrderByComparator<Layout> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence()
				   .filterFindByG_P_T_PrevAndNext(plid, groupId, privateLayout,
			type, orderByComparator);
	}

	/**
	* Removes all the layouts where groupId = &#63; and privateLayout = &#63; and type = &#63; from the database.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param type the type
	*/
	public static void removeByG_P_T(long groupId, boolean privateLayout,
		java.lang.String type) {
		getPersistence().removeByG_P_T(groupId, privateLayout, type);
	}

	/**
	* Returns the number of layouts where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param type the type
	* @return the number of matching layouts
	*/
	public static int countByG_P_T(long groupId, boolean privateLayout,
		java.lang.String type) {
		return getPersistence().countByG_P_T(groupId, privateLayout, type);
	}

	/**
	* Returns the number of layouts that the user has permission to view where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param type the type
	* @return the number of matching layouts that the user has permission to view
	*/
	public static int filterCountByG_P_T(long groupId, boolean privateLayout,
		java.lang.String type) {
		return getPersistence().filterCountByG_P_T(groupId, privateLayout, type);
	}

	/**
	* Returns the layout where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; or throws a {@link NoSuchLayoutException} if it could not be found.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param friendlyURL the friendly u r l
	* @return the matching layout
	* @throws NoSuchLayoutException if a matching layout could not be found
	*/
	public static Layout findByG_P_F(long groupId, boolean privateLayout,
		java.lang.String friendlyURL)
		throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence().findByG_P_F(groupId, privateLayout, friendlyURL);
	}

	/**
	* Returns the layout where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param friendlyURL the friendly u r l
	* @return the matching layout, or <code>null</code> if a matching layout could not be found
	*/
	public static Layout fetchByG_P_F(long groupId, boolean privateLayout,
		java.lang.String friendlyURL) {
		return getPersistence().fetchByG_P_F(groupId, privateLayout, friendlyURL);
	}

	/**
	* Returns the layout where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param friendlyURL the friendly u r l
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching layout, or <code>null</code> if a matching layout could not be found
	*/
	public static Layout fetchByG_P_F(long groupId, boolean privateLayout,
		java.lang.String friendlyURL, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_P_F(groupId, privateLayout, friendlyURL,
			retrieveFromCache);
	}

	/**
	* Removes the layout where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; from the database.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param friendlyURL the friendly u r l
	* @return the layout that was removed
	*/
	public static Layout removeByG_P_F(long groupId, boolean privateLayout,
		java.lang.String friendlyURL)
		throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence()
				   .removeByG_P_F(groupId, privateLayout, friendlyURL);
	}

	/**
	* Returns the number of layouts where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param friendlyURL the friendly u r l
	* @return the number of matching layouts
	*/
	public static int countByG_P_F(long groupId, boolean privateLayout,
		java.lang.String friendlyURL) {
		return getPersistence().countByG_P_F(groupId, privateLayout, friendlyURL);
	}

	/**
	* Returns the layout where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; or throws a {@link NoSuchLayoutException} if it could not be found.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param sourcePrototypeLayoutUuid the source prototype layout uuid
	* @return the matching layout
	* @throws NoSuchLayoutException if a matching layout could not be found
	*/
	public static Layout findByG_P_SPLU(long groupId, boolean privateLayout,
		java.lang.String sourcePrototypeLayoutUuid)
		throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence()
				   .findByG_P_SPLU(groupId, privateLayout,
			sourcePrototypeLayoutUuid);
	}

	/**
	* Returns the layout where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param sourcePrototypeLayoutUuid the source prototype layout uuid
	* @return the matching layout, or <code>null</code> if a matching layout could not be found
	*/
	public static Layout fetchByG_P_SPLU(long groupId, boolean privateLayout,
		java.lang.String sourcePrototypeLayoutUuid) {
		return getPersistence()
				   .fetchByG_P_SPLU(groupId, privateLayout,
			sourcePrototypeLayoutUuid);
	}

	/**
	* Returns the layout where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param sourcePrototypeLayoutUuid the source prototype layout uuid
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching layout, or <code>null</code> if a matching layout could not be found
	*/
	public static Layout fetchByG_P_SPLU(long groupId, boolean privateLayout,
		java.lang.String sourcePrototypeLayoutUuid, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_P_SPLU(groupId, privateLayout,
			sourcePrototypeLayoutUuid, retrieveFromCache);
	}

	/**
	* Removes the layout where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; from the database.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param sourcePrototypeLayoutUuid the source prototype layout uuid
	* @return the layout that was removed
	*/
	public static Layout removeByG_P_SPLU(long groupId, boolean privateLayout,
		java.lang.String sourcePrototypeLayoutUuid)
		throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence()
				   .removeByG_P_SPLU(groupId, privateLayout,
			sourcePrototypeLayoutUuid);
	}

	/**
	* Returns the number of layouts where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param sourcePrototypeLayoutUuid the source prototype layout uuid
	* @return the number of matching layouts
	*/
	public static int countByG_P_SPLU(long groupId, boolean privateLayout,
		java.lang.String sourcePrototypeLayoutUuid) {
		return getPersistence()
				   .countByG_P_SPLU(groupId, privateLayout,
			sourcePrototypeLayoutUuid);
	}

	/**
	* Caches the layout in the entity cache if it is enabled.
	*
	* @param layout the layout
	*/
	public static void cacheResult(Layout layout) {
		getPersistence().cacheResult(layout);
	}

	/**
	* Caches the layouts in the entity cache if it is enabled.
	*
	* @param layouts the layouts
	*/
	public static void cacheResult(List<Layout> layouts) {
		getPersistence().cacheResult(layouts);
	}

	/**
	* Creates a new layout with the primary key. Does not add the layout to the database.
	*
	* @param plid the primary key for the new layout
	* @return the new layout
	*/
	public static Layout create(long plid) {
		return getPersistence().create(plid);
	}

	/**
	* Removes the layout with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param plid the primary key of the layout
	* @return the layout that was removed
	* @throws NoSuchLayoutException if a layout with the primary key could not be found
	*/
	public static Layout remove(long plid)
		throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence().remove(plid);
	}

	public static Layout updateImpl(Layout layout) {
		return getPersistence().updateImpl(layout);
	}

	/**
	* Returns the layout with the primary key or throws a {@link NoSuchLayoutException} if it could not be found.
	*
	* @param plid the primary key of the layout
	* @return the layout
	* @throws NoSuchLayoutException if a layout with the primary key could not be found
	*/
	public static Layout findByPrimaryKey(long plid)
		throws com.liferay.portal.NoSuchLayoutException {
		return getPersistence().findByPrimaryKey(plid);
	}

	/**
	* Returns the layout with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param plid the primary key of the layout
	* @return the layout, or <code>null</code> if a layout with the primary key could not be found
	*/
	public static Layout fetchByPrimaryKey(long plid) {
		return getPersistence().fetchByPrimaryKey(plid);
	}

	public static java.util.Map<java.io.Serializable, Layout> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the layouts.
	*
	* @return the layouts
	*/
	public static List<Layout> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the layouts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @return the range of layouts
	*/
	public static List<Layout> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the layouts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of layouts
	*/
	public static List<Layout> findAll(int start, int end,
		OrderByComparator<Layout> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the layouts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of layouts
	*/
	public static List<Layout> findAll(int start, int end,
		OrderByComparator<Layout> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the layouts from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of layouts.
	*
	* @return the number of layouts
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static LayoutPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (LayoutPersistence)PortalBeanLocatorUtil.locate(LayoutPersistence.class.getName());

			ReferenceRegistry.registerReference(LayoutUtil.class, "_persistence");
		}

		return _persistence;
	}

	private static LayoutPersistence _persistence;
}
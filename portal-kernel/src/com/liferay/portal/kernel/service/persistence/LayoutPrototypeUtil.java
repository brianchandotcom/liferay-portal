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
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the layout prototype service. This utility wraps <code>com.liferay.portal.service.persistence.impl.LayoutPrototypePersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPrototypePersistence
 * @generated
 */
@ProviderType
public class LayoutPrototypeUtil {
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
	public static void clearCache(LayoutPrototype layoutPrototype) {
		getPersistence().clearCache(layoutPrototype);
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
	public static Map<Serializable, LayoutPrototype> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<LayoutPrototype> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LayoutPrototype> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LayoutPrototype> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<LayoutPrototype> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static LayoutPrototype update(LayoutPrototype layoutPrototype) {
		return getPersistence().update(layoutPrototype);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static LayoutPrototype update(LayoutPrototype layoutPrototype,
		ServiceContext serviceContext) {
		return getPersistence().update(layoutPrototype, serviceContext);
	}

	/**
	* Returns all the layout prototypes where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching layout prototypes
	*/
	public static List<LayoutPrototype> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the layout prototypes where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPrototypeModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @return the range of matching layout prototypes
	*/
	public static List<LayoutPrototype> findByUuid(String uuid, int start,
		int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the layout prototypes where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPrototypeModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout prototypes
	*/
	public static List<LayoutPrototype> findByUuid(String uuid, int start,
		int end, OrderByComparator<LayoutPrototype> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout prototypes where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPrototypeModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout prototypes
	*/
	public static List<LayoutPrototype> findByUuid(String uuid, int start,
		int end, OrderByComparator<LayoutPrototype> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first layout prototype in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout prototype
	* @throws NoSuchLayoutPrototypeException if a matching layout prototype could not be found
	*/
	public static LayoutPrototype findByUuid_First(String uuid,
		OrderByComparator<LayoutPrototype> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutPrototypeException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first layout prototype in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout prototype, or <code>null</code> if a matching layout prototype could not be found
	*/
	public static LayoutPrototype fetchByUuid_First(String uuid,
		OrderByComparator<LayoutPrototype> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last layout prototype in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout prototype
	* @throws NoSuchLayoutPrototypeException if a matching layout prototype could not be found
	*/
	public static LayoutPrototype findByUuid_Last(String uuid,
		OrderByComparator<LayoutPrototype> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutPrototypeException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last layout prototype in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout prototype, or <code>null</code> if a matching layout prototype could not be found
	*/
	public static LayoutPrototype fetchByUuid_Last(String uuid,
		OrderByComparator<LayoutPrototype> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the layout prototypes before and after the current layout prototype in the ordered set where uuid = &#63;.
	*
	* @param layoutPrototypeId the primary key of the current layout prototype
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout prototype
	* @throws NoSuchLayoutPrototypeException if a layout prototype with the primary key could not be found
	*/
	public static LayoutPrototype[] findByUuid_PrevAndNext(
		long layoutPrototypeId, String uuid,
		OrderByComparator<LayoutPrototype> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutPrototypeException {
		return getPersistence()
				   .findByUuid_PrevAndNext(layoutPrototypeId, uuid,
			orderByComparator);
	}

	/**
	* Returns all the layout prototypes that the user has permission to view where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching layout prototypes that the user has permission to view
	*/
	public static List<LayoutPrototype> filterFindByUuid(String uuid) {
		return getPersistence().filterFindByUuid(uuid);
	}

	/**
	* Returns a range of all the layout prototypes that the user has permission to view where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPrototypeModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @return the range of matching layout prototypes that the user has permission to view
	*/
	public static List<LayoutPrototype> filterFindByUuid(String uuid,
		int start, int end) {
		return getPersistence().filterFindByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the layout prototypes that the user has permissions to view where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPrototypeModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout prototypes that the user has permission to view
	*/
	public static List<LayoutPrototype> filterFindByUuid(String uuid,
		int start, int end, OrderByComparator<LayoutPrototype> orderByComparator) {
		return getPersistence()
				   .filterFindByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the layout prototypes before and after the current layout prototype in the ordered set of layout prototypes that the user has permission to view where uuid = &#63;.
	*
	* @param layoutPrototypeId the primary key of the current layout prototype
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout prototype
	* @throws NoSuchLayoutPrototypeException if a layout prototype with the primary key could not be found
	*/
	public static LayoutPrototype[] filterFindByUuid_PrevAndNext(
		long layoutPrototypeId, String uuid,
		OrderByComparator<LayoutPrototype> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutPrototypeException {
		return getPersistence()
				   .filterFindByUuid_PrevAndNext(layoutPrototypeId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the layout prototypes where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of layout prototypes where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching layout prototypes
	*/
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the number of layout prototypes that the user has permission to view where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching layout prototypes that the user has permission to view
	*/
	public static int filterCountByUuid(String uuid) {
		return getPersistence().filterCountByUuid(uuid);
	}

	/**
	* Returns all the layout prototypes where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching layout prototypes
	*/
	public static List<LayoutPrototype> findByUuid_C(String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the layout prototypes where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPrototypeModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @return the range of matching layout prototypes
	*/
	public static List<LayoutPrototype> findByUuid_C(String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the layout prototypes where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPrototypeModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout prototypes
	*/
	public static List<LayoutPrototype> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<LayoutPrototype> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout prototypes where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPrototypeModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout prototypes
	*/
	public static List<LayoutPrototype> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<LayoutPrototype> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first layout prototype in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout prototype
	* @throws NoSuchLayoutPrototypeException if a matching layout prototype could not be found
	*/
	public static LayoutPrototype findByUuid_C_First(String uuid,
		long companyId, OrderByComparator<LayoutPrototype> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutPrototypeException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first layout prototype in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout prototype, or <code>null</code> if a matching layout prototype could not be found
	*/
	public static LayoutPrototype fetchByUuid_C_First(String uuid,
		long companyId, OrderByComparator<LayoutPrototype> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last layout prototype in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout prototype
	* @throws NoSuchLayoutPrototypeException if a matching layout prototype could not be found
	*/
	public static LayoutPrototype findByUuid_C_Last(String uuid,
		long companyId, OrderByComparator<LayoutPrototype> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutPrototypeException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last layout prototype in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout prototype, or <code>null</code> if a matching layout prototype could not be found
	*/
	public static LayoutPrototype fetchByUuid_C_Last(String uuid,
		long companyId, OrderByComparator<LayoutPrototype> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the layout prototypes before and after the current layout prototype in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param layoutPrototypeId the primary key of the current layout prototype
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout prototype
	* @throws NoSuchLayoutPrototypeException if a layout prototype with the primary key could not be found
	*/
	public static LayoutPrototype[] findByUuid_C_PrevAndNext(
		long layoutPrototypeId, String uuid, long companyId,
		OrderByComparator<LayoutPrototype> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutPrototypeException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(layoutPrototypeId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Returns all the layout prototypes that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching layout prototypes that the user has permission to view
	*/
	public static List<LayoutPrototype> filterFindByUuid_C(String uuid,
		long companyId) {
		return getPersistence().filterFindByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the layout prototypes that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPrototypeModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @return the range of matching layout prototypes that the user has permission to view
	*/
	public static List<LayoutPrototype> filterFindByUuid_C(String uuid,
		long companyId, int start, int end) {
		return getPersistence().filterFindByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the layout prototypes that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPrototypeModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout prototypes that the user has permission to view
	*/
	public static List<LayoutPrototype> filterFindByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<LayoutPrototype> orderByComparator) {
		return getPersistence()
				   .filterFindByUuid_C(uuid, companyId, start, end,
			orderByComparator);
	}

	/**
	* Returns the layout prototypes before and after the current layout prototype in the ordered set of layout prototypes that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* @param layoutPrototypeId the primary key of the current layout prototype
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout prototype
	* @throws NoSuchLayoutPrototypeException if a layout prototype with the primary key could not be found
	*/
	public static LayoutPrototype[] filterFindByUuid_C_PrevAndNext(
		long layoutPrototypeId, String uuid, long companyId,
		OrderByComparator<LayoutPrototype> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutPrototypeException {
		return getPersistence()
				   .filterFindByUuid_C_PrevAndNext(layoutPrototypeId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the layout prototypes where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of layout prototypes where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching layout prototypes
	*/
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of layout prototypes that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching layout prototypes that the user has permission to view
	*/
	public static int filterCountByUuid_C(String uuid, long companyId) {
		return getPersistence().filterCountByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the layout prototypes where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching layout prototypes
	*/
	public static List<LayoutPrototype> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the layout prototypes where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPrototypeModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @return the range of matching layout prototypes
	*/
	public static List<LayoutPrototype> findByCompanyId(long companyId,
		int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the layout prototypes where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPrototypeModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout prototypes
	*/
	public static List<LayoutPrototype> findByCompanyId(long companyId,
		int start, int end, OrderByComparator<LayoutPrototype> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout prototypes where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPrototypeModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout prototypes
	*/
	public static List<LayoutPrototype> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<LayoutPrototype> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first layout prototype in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout prototype
	* @throws NoSuchLayoutPrototypeException if a matching layout prototype could not be found
	*/
	public static LayoutPrototype findByCompanyId_First(long companyId,
		OrderByComparator<LayoutPrototype> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutPrototypeException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first layout prototype in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout prototype, or <code>null</code> if a matching layout prototype could not be found
	*/
	public static LayoutPrototype fetchByCompanyId_First(long companyId,
		OrderByComparator<LayoutPrototype> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last layout prototype in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout prototype
	* @throws NoSuchLayoutPrototypeException if a matching layout prototype could not be found
	*/
	public static LayoutPrototype findByCompanyId_Last(long companyId,
		OrderByComparator<LayoutPrototype> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutPrototypeException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last layout prototype in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout prototype, or <code>null</code> if a matching layout prototype could not be found
	*/
	public static LayoutPrototype fetchByCompanyId_Last(long companyId,
		OrderByComparator<LayoutPrototype> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the layout prototypes before and after the current layout prototype in the ordered set where companyId = &#63;.
	*
	* @param layoutPrototypeId the primary key of the current layout prototype
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout prototype
	* @throws NoSuchLayoutPrototypeException if a layout prototype with the primary key could not be found
	*/
	public static LayoutPrototype[] findByCompanyId_PrevAndNext(
		long layoutPrototypeId, long companyId,
		OrderByComparator<LayoutPrototype> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutPrototypeException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(layoutPrototypeId, companyId,
			orderByComparator);
	}

	/**
	* Returns all the layout prototypes that the user has permission to view where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching layout prototypes that the user has permission to view
	*/
	public static List<LayoutPrototype> filterFindByCompanyId(long companyId) {
		return getPersistence().filterFindByCompanyId(companyId);
	}

	/**
	* Returns a range of all the layout prototypes that the user has permission to view where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPrototypeModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @return the range of matching layout prototypes that the user has permission to view
	*/
	public static List<LayoutPrototype> filterFindByCompanyId(long companyId,
		int start, int end) {
		return getPersistence().filterFindByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the layout prototypes that the user has permissions to view where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPrototypeModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout prototypes that the user has permission to view
	*/
	public static List<LayoutPrototype> filterFindByCompanyId(long companyId,
		int start, int end, OrderByComparator<LayoutPrototype> orderByComparator) {
		return getPersistence()
				   .filterFindByCompanyId(companyId, start, end,
			orderByComparator);
	}

	/**
	* Returns the layout prototypes before and after the current layout prototype in the ordered set of layout prototypes that the user has permission to view where companyId = &#63;.
	*
	* @param layoutPrototypeId the primary key of the current layout prototype
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout prototype
	* @throws NoSuchLayoutPrototypeException if a layout prototype with the primary key could not be found
	*/
	public static LayoutPrototype[] filterFindByCompanyId_PrevAndNext(
		long layoutPrototypeId, long companyId,
		OrderByComparator<LayoutPrototype> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutPrototypeException {
		return getPersistence()
				   .filterFindByCompanyId_PrevAndNext(layoutPrototypeId,
			companyId, orderByComparator);
	}

	/**
	* Removes all the layout prototypes where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of layout prototypes where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching layout prototypes
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns the number of layout prototypes that the user has permission to view where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching layout prototypes that the user has permission to view
	*/
	public static int filterCountByCompanyId(long companyId) {
		return getPersistence().filterCountByCompanyId(companyId);
	}

	/**
	* Returns all the layout prototypes where companyId = &#63; and active = &#63;.
	*
	* @param companyId the company ID
	* @param active the active
	* @return the matching layout prototypes
	*/
	public static List<LayoutPrototype> findByC_A(long companyId, boolean active) {
		return getPersistence().findByC_A(companyId, active);
	}

	/**
	* Returns a range of all the layout prototypes where companyId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPrototypeModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param active the active
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @return the range of matching layout prototypes
	*/
	public static List<LayoutPrototype> findByC_A(long companyId,
		boolean active, int start, int end) {
		return getPersistence().findByC_A(companyId, active, start, end);
	}

	/**
	* Returns an ordered range of all the layout prototypes where companyId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPrototypeModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param active the active
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout prototypes
	*/
	public static List<LayoutPrototype> findByC_A(long companyId,
		boolean active, int start, int end,
		OrderByComparator<LayoutPrototype> orderByComparator) {
		return getPersistence()
				   .findByC_A(companyId, active, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout prototypes where companyId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPrototypeModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param active the active
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout prototypes
	*/
	public static List<LayoutPrototype> findByC_A(long companyId,
		boolean active, int start, int end,
		OrderByComparator<LayoutPrototype> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_A(companyId, active, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first layout prototype in the ordered set where companyId = &#63; and active = &#63;.
	*
	* @param companyId the company ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout prototype
	* @throws NoSuchLayoutPrototypeException if a matching layout prototype could not be found
	*/
	public static LayoutPrototype findByC_A_First(long companyId,
		boolean active, OrderByComparator<LayoutPrototype> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutPrototypeException {
		return getPersistence()
				   .findByC_A_First(companyId, active, orderByComparator);
	}

	/**
	* Returns the first layout prototype in the ordered set where companyId = &#63; and active = &#63;.
	*
	* @param companyId the company ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout prototype, or <code>null</code> if a matching layout prototype could not be found
	*/
	public static LayoutPrototype fetchByC_A_First(long companyId,
		boolean active, OrderByComparator<LayoutPrototype> orderByComparator) {
		return getPersistence()
				   .fetchByC_A_First(companyId, active, orderByComparator);
	}

	/**
	* Returns the last layout prototype in the ordered set where companyId = &#63; and active = &#63;.
	*
	* @param companyId the company ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout prototype
	* @throws NoSuchLayoutPrototypeException if a matching layout prototype could not be found
	*/
	public static LayoutPrototype findByC_A_Last(long companyId,
		boolean active, OrderByComparator<LayoutPrototype> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutPrototypeException {
		return getPersistence()
				   .findByC_A_Last(companyId, active, orderByComparator);
	}

	/**
	* Returns the last layout prototype in the ordered set where companyId = &#63; and active = &#63;.
	*
	* @param companyId the company ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout prototype, or <code>null</code> if a matching layout prototype could not be found
	*/
	public static LayoutPrototype fetchByC_A_Last(long companyId,
		boolean active, OrderByComparator<LayoutPrototype> orderByComparator) {
		return getPersistence()
				   .fetchByC_A_Last(companyId, active, orderByComparator);
	}

	/**
	* Returns the layout prototypes before and after the current layout prototype in the ordered set where companyId = &#63; and active = &#63;.
	*
	* @param layoutPrototypeId the primary key of the current layout prototype
	* @param companyId the company ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout prototype
	* @throws NoSuchLayoutPrototypeException if a layout prototype with the primary key could not be found
	*/
	public static LayoutPrototype[] findByC_A_PrevAndNext(
		long layoutPrototypeId, long companyId, boolean active,
		OrderByComparator<LayoutPrototype> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutPrototypeException {
		return getPersistence()
				   .findByC_A_PrevAndNext(layoutPrototypeId, companyId, active,
			orderByComparator);
	}

	/**
	* Returns all the layout prototypes that the user has permission to view where companyId = &#63; and active = &#63;.
	*
	* @param companyId the company ID
	* @param active the active
	* @return the matching layout prototypes that the user has permission to view
	*/
	public static List<LayoutPrototype> filterFindByC_A(long companyId,
		boolean active) {
		return getPersistence().filterFindByC_A(companyId, active);
	}

	/**
	* Returns a range of all the layout prototypes that the user has permission to view where companyId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPrototypeModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param active the active
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @return the range of matching layout prototypes that the user has permission to view
	*/
	public static List<LayoutPrototype> filterFindByC_A(long companyId,
		boolean active, int start, int end) {
		return getPersistence().filterFindByC_A(companyId, active, start, end);
	}

	/**
	* Returns an ordered range of all the layout prototypes that the user has permissions to view where companyId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPrototypeModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param active the active
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout prototypes that the user has permission to view
	*/
	public static List<LayoutPrototype> filterFindByC_A(long companyId,
		boolean active, int start, int end,
		OrderByComparator<LayoutPrototype> orderByComparator) {
		return getPersistence()
				   .filterFindByC_A(companyId, active, start, end,
			orderByComparator);
	}

	/**
	* Returns the layout prototypes before and after the current layout prototype in the ordered set of layout prototypes that the user has permission to view where companyId = &#63; and active = &#63;.
	*
	* @param layoutPrototypeId the primary key of the current layout prototype
	* @param companyId the company ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout prototype
	* @throws NoSuchLayoutPrototypeException if a layout prototype with the primary key could not be found
	*/
	public static LayoutPrototype[] filterFindByC_A_PrevAndNext(
		long layoutPrototypeId, long companyId, boolean active,
		OrderByComparator<LayoutPrototype> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutPrototypeException {
		return getPersistence()
				   .filterFindByC_A_PrevAndNext(layoutPrototypeId, companyId,
			active, orderByComparator);
	}

	/**
	* Removes all the layout prototypes where companyId = &#63; and active = &#63; from the database.
	*
	* @param companyId the company ID
	* @param active the active
	*/
	public static void removeByC_A(long companyId, boolean active) {
		getPersistence().removeByC_A(companyId, active);
	}

	/**
	* Returns the number of layout prototypes where companyId = &#63; and active = &#63;.
	*
	* @param companyId the company ID
	* @param active the active
	* @return the number of matching layout prototypes
	*/
	public static int countByC_A(long companyId, boolean active) {
		return getPersistence().countByC_A(companyId, active);
	}

	/**
	* Returns the number of layout prototypes that the user has permission to view where companyId = &#63; and active = &#63;.
	*
	* @param companyId the company ID
	* @param active the active
	* @return the number of matching layout prototypes that the user has permission to view
	*/
	public static int filterCountByC_A(long companyId, boolean active) {
		return getPersistence().filterCountByC_A(companyId, active);
	}

	/**
	* Caches the layout prototype in the entity cache if it is enabled.
	*
	* @param layoutPrototype the layout prototype
	*/
	public static void cacheResult(LayoutPrototype layoutPrototype) {
		getPersistence().cacheResult(layoutPrototype);
	}

	/**
	* Caches the layout prototypes in the entity cache if it is enabled.
	*
	* @param layoutPrototypes the layout prototypes
	*/
	public static void cacheResult(List<LayoutPrototype> layoutPrototypes) {
		getPersistence().cacheResult(layoutPrototypes);
	}

	/**
	* Creates a new layout prototype with the primary key. Does not add the layout prototype to the database.
	*
	* @param layoutPrototypeId the primary key for the new layout prototype
	* @return the new layout prototype
	*/
	public static LayoutPrototype create(long layoutPrototypeId) {
		return getPersistence().create(layoutPrototypeId);
	}

	/**
	* Removes the layout prototype with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutPrototypeId the primary key of the layout prototype
	* @return the layout prototype that was removed
	* @throws NoSuchLayoutPrototypeException if a layout prototype with the primary key could not be found
	*/
	public static LayoutPrototype remove(long layoutPrototypeId)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutPrototypeException {
		return getPersistence().remove(layoutPrototypeId);
	}

	public static LayoutPrototype updateImpl(LayoutPrototype layoutPrototype) {
		return getPersistence().updateImpl(layoutPrototype);
	}

	/**
	* Returns the layout prototype with the primary key or throws a <code>NoSuchLayoutPrototypeException</code> if it could not be found.
	*
	* @param layoutPrototypeId the primary key of the layout prototype
	* @return the layout prototype
	* @throws NoSuchLayoutPrototypeException if a layout prototype with the primary key could not be found
	*/
	public static LayoutPrototype findByPrimaryKey(long layoutPrototypeId)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutPrototypeException {
		return getPersistence().findByPrimaryKey(layoutPrototypeId);
	}

	/**
	* Returns the layout prototype with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param layoutPrototypeId the primary key of the layout prototype
	* @return the layout prototype, or <code>null</code> if a layout prototype with the primary key could not be found
	*/
	public static LayoutPrototype fetchByPrimaryKey(long layoutPrototypeId) {
		return getPersistence().fetchByPrimaryKey(layoutPrototypeId);
	}

	/**
	* Returns all the layout prototypes.
	*
	* @return the layout prototypes
	*/
	public static List<LayoutPrototype> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the layout prototypes.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPrototypeModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @return the range of layout prototypes
	*/
	public static List<LayoutPrototype> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the layout prototypes.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPrototypeModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of layout prototypes
	*/
	public static List<LayoutPrototype> findAll(int start, int end,
		OrderByComparator<LayoutPrototype> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout prototypes.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPrototypeModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of layout prototypes
	*/
	public static List<LayoutPrototype> findAll(int start, int end,
		OrderByComparator<LayoutPrototype> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the layout prototypes from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of layout prototypes.
	*
	* @return the number of layout prototypes
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static LayoutPrototypePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (LayoutPrototypePersistence)PortalBeanLocatorUtil.locate(LayoutPrototypePersistence.class.getName());

			ReferenceRegistry.registerReference(LayoutPrototypeUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static LayoutPrototypePersistence _persistence;
}